package xueluoanping.cuisine.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import xueluoanping.cuisine.craft.MillingRecipe;
import xueluoanping.cuisine.register.BlockEntityRegister;
import xueluoanping.cuisine.register.RecipeRegister;

import java.util.List;

public class MillBlockEntity extends SyncBlockEntity {

    private final ItemStackHandler inventory_in;
    private final ItemStackHandler inventory_out;

    private float progress;
    private int power;

    public final FluidTank tankIn;
    public final FluidTank tankOut;
    private int Capacity = 1000;

    public MillBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegister.mill_entity_type.get(), pos, state);
        inventory_in = createHandlerIn();
        inventory_out = createHandler();

        tankIn = createFuildHandler();
        tankOut = createFuildHandler();

        this.progress = 360;
        this.power = 0;
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider pRegistries) {
        inventory_in.deserializeNBT(pRegistries,tag.getCompound("input"));
        inventory_out.deserializeNBT(pRegistries,tag.getCompound("output"));
        tankIn.readFromNBT(pRegistries,tag.getCompound("inputFluid"));
        tankOut.readFromNBT(pRegistries,tag.getCompound("outputFluid"));
        this.progress = tag.contains("progress") ? tag.getInt("progress") : 360;
        this.power = tag.contains("power") ? tag.getInt("power") : 0;
        // Cuisine.logger(tag);
        super.loadAdditional(tag, pRegistries);
    }



    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider pRegistries) {
        tag.put("input", inventory_in.serializeNBT(pRegistries));
        tag.put("output", inventory_out.serializeNBT(pRegistries));
        tag.put("inputFluid", tankIn.writeToNBT(pRegistries,new CompoundTag()));
        tag.put("outputFluid", tankOut.writeToNBT(pRegistries,new CompoundTag()));
        tag.putFloat("progress", progress);
        tag.putInt("power", power);
        super.saveAdditional(tag, pRegistries);
    }

    public float getProgress() {
        return this.progress;
    }

    public boolean addProgress(float period) {
        float addAmount = Math.min(period, 360);
        this.progress -= addAmount;
        if (this.progress <= 0) {
            this.progress += 360;
            if (!getLevel().isClientSide())
                processMilling(getLevel());
        }
        inventoryChanged();
        if (this.progress < 0)
            return false;
        return true;
    }

    public int getPower() {
        return this.power;
    }

    public void addPower(int provide) {
        if (provide < 100 && this.power < 720) {
            this.power += provide;
            inventoryChanged();
        }
    }

    public void subPower(int loss) {
        this.power = Math.max(this.power - loss, 0);
        inventoryChanged();
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler() {

            @Override
            public int getSlotLimit(int slot) {
                return 64;
            }

            @Override
            protected void onContentsChanged(int slot) {
                inventoryChanged();
            }
        };
    }

    private ItemStackHandler createHandlerIn() {
        return new ItemStackHandler() {
            @Override
            public int getSlotLimit(int slot) {
                return 64;
            }

            @Override
            protected void onContentsChanged(int slot) {
                inventoryChanged();
            }


            // 在这里判断是否可用
            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return super.isItemValid(slot, stack);
            }
        };
    }

    private FluidTank createFuildHandler() {
        return new FluidTank(Capacity);
    }

    // write here,server side
    public static void tickEntity(Level worldIn, BlockPos pos, BlockState blockState, MillBlockEntity millBlockEntity) {
        // ItemStackHandler inventory00=millBlockEntity.createHandler();
        // inventory00.insertItem(0, Items.BREAD.getDefaultInstance(),false);
        // worldIn.getRecipeManager().getAllRecipesFor(RecipeType.CRAFTING).clear();

        if (millBlockEntity.getPower() > 0) {
            if (millBlockEntity.getPower() > 360) {
                millBlockEntity.subPower(2);
                millBlockEntity.addProgress(1 + (millBlockEntity.getPower() - 360) / 180);
            } else {
                millBlockEntity.subPower(1);
                millBlockEntity.addProgress(1);
            }
        }
    }

    public ItemStackHandler getInventory_in() {
        return inventory_in;
    }

    public ItemStackHandler getInventory_out() {
        return inventory_out;
    }

    public FluidTank getTankIn() {
        return tankIn;
    }

    public FluidTank getTankOut() {
        return tankOut;
    }


    public boolean processMilling(Level worldIn) {
        var recipeList = worldIn.getRecipeManager().
                getRecipesFor(RecipeRegister.millingType.get(), new RecipeWrapper(inventory_in), worldIn);

        // Cuisine.logger(RecipeRegister.millingType.get(), recipeList.size());
        if (recipeList.isEmpty() || tankOut.getFluidAmount() >= Capacity || inventory_out.getStackInSlot(0).getCount() >= 64) {
            //				return Optional.empty();
            return false;
        }
        try {
            for (int i = 0; i < recipeList.size(); i++) {

                MillingRecipe recipe = recipeList.get(i).value();
                //	由于有四个变量较为复杂，第一种情况，容器全空，接受任何配方
                // 第二种情况，验证为空或者用料匹配配方
                ItemStack stack = recipe.getIngredientsOutput().get(0).getItems()[0].copy();
                stack.setCount(1);
                inventory_in.extractItem(0, 1, false);
                inventory_out.insertItem(0, stack, false);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
