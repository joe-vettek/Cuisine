package xueluoanping.cuisine.blockentity.firepit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.craft.BasinSqueezingRecipe;
import xueluoanping.cuisine.register.BlockEntityRegister;
import xueluoanping.cuisine.register.RecipeRegister;

import java.util.Optional;

public class BarbecueRackBlockEntity extends AbstractFirepitBlockEntity {
    private final ItemStackHandler inventory;
    private static final int stackLimit = 1;

    private final RecipeHolder<SmokingRecipe>[] recipeHolders = new RecipeHolder[3];
    private final int[] remainTimes = new int[]{0, 0, 0};
    private final int[] maxTimes = new int[]{1, 1, 1};

    public BarbecueRackBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegister.barbecue_rack_entity_type.get(), pos, state);
        this.inventory = createHandler(3, stackLimit);
    }

    @Override
    public void loadAdditional(CompoundTag data, HolderLookup.Provider pRegistries) {
        this.inventory.deserializeNBT(pRegistries, data.getCompound("inventory"));
        int[] aint1 = data.getIntArray("remainTimes");
        System.arraycopy(aint1, 0, this.remainTimes, 0, Math.min(this.remainTimes.length, aint1.length));
        int[] aint2 = data.getIntArray("maxTimes");
        System.arraycopy(aint2, 0, this.maxTimes, 0, Math.min(this.maxTimes.length, aint2.length));

        super.loadAdditional(data, pRegistries);
    }

    @Override
    protected void saveAdditional(CompoundTag data, HolderLookup.Provider pRegistries) {
        data.put("inventory", this.inventory.serializeNBT(pRegistries));
        data.putIntArray("remainTimes", remainTimes);
        data.putIntArray("maxTimes", maxTimes);
        super.saveAdditional(data, pRegistries);
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public float getRemainPercent(int i) {
        return maxTimes[i] > 0 ? remainTimes[i] * 1f / maxTimes[i] : 0;
    }


    @Override
    public void tick() {
        super.tick();

        boolean changed = false;
        // Select recipe
        for (int i = 0; i < inventory.getSlots(); i++) {
            var item = inventory.getStackInSlot(0);
            if (recipeHolders[i] != null) {
                var recipeResult = recipeHolders[i].value().getResultItem(level.registryAccess());
                if (ItemStack.isSameItemSameComponents(item, recipeResult)) {
                    recipeHolders[i] = null;
                    continue;
                }
                boolean isValidInput = recipeHolders[i].value().getIngredients().stream().anyMatch(ingredient -> ingredient.test(item));
                if (!isValidInput) {
                    recipeHolders[i] = null;
                    continue;
                }
            }

            var c = new SingleRecipeInput(item);
            RecipeHolder<SmokingRecipe> recipe = getLevel().getRecipeManager().getRecipeFor(RecipeType.SMOKING, c, getLevel())
                    .orElse(null);

            boolean updateRecipe = false;
            if (recipe != recipeHolders[i]) {
                if (recipe == null) {
                    updateRecipe = true;
                } else if (recipeHolders[i] == null) {
                    updateRecipe = true;
                } else if (recipeHolders[i].id().equals(recipe.id())) {
                    updateRecipe = true;
                }
            }

            if (updateRecipe) {
                recipeHolders[i] = recipe;
                changed = true;
            }
        }

        // normal Tick
        for (int i = 0; i < recipeHolders.length; i++) {
            if (recipeHolders[i] != null) {
                var costTime = recipeHolders[i].value().getCookingTime();
                if (costTime == 0 || costTime != maxTimes[i]) {
                    maxTimes[i] = costTime;
                    changed = true;
                }

                if (remainTimes[i] == 0 && maxTimes[i] > 0) {
                    remainTimes[i] = maxTimes[i];
                    changed = true;
                }

                if (remainTimes[i] == 1) {
                    inventory.setStackInSlot(i, recipeHolders[i].value().assemble(new SingleRecipeInput(inventory.getStackInSlot(i)), level.registryAccess()).copyWithCount(1));
                    changed = true;
                }

                if (remainTimes[i] > 0) {
                    remainTimes[i]--;
                    changed = true;
                }
            }
        }

        if (changed) {
            inventoryChanged();
        }

    }

    public static void tickEntity(Level level, BlockPos pos, BlockState state, BarbecueRackBlockEntity entity1) {
        entity1.tick();
    }
}
