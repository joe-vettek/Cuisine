package xueluoanping.cuisine.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.NotNull;

import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.craft.BasinSqueezingRecipe;
import xueluoanping.cuisine.register.RecipeRegister;

import java.util.List;

public class AbstractBasinBlockEntity extends SyncBlockEntity {
	private final ItemStackHandler inventory;
	public int tick = 0;
	public int speedCache = 0;


	public final FluidTank tank;

	public static int Capacity = 8000;
	public int tickCheckThrowing = 0;
	private float renderingAmount = 0;
	boolean squeezingFailed = false;

	public AbstractBasinBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
		super(blockEntityType, pos, state);
		inventory = createHandler(1);
		tank = createFuildHandler(Capacity);
	}


	@Override
	public void loadAdditional(CompoundTag compound, HolderLookup.Provider pRegistries) {
		// Cuisine.logger(compound);
		inventory.deserializeNBT(pRegistries,compound.getCompound("Items"));
		tank.readFromNBT(pRegistries,compound.getCompound("Tank"));
		super.loadAdditional(compound,pRegistries);
	}

	@Override
	public void saveAdditional(CompoundTag compound, HolderLookup.Provider pRegistries) {
		compound.put("Items", inventory.serializeNBT(pRegistries));
		compound.put("Tank", tank.writeToNBT(pRegistries,new CompoundTag()));
		super.saveAdditional(compound,pRegistries);
	}





	public boolean addItem(ItemStack itemStack) {
		if (isEmpty() && !itemStack.isEmpty()) {
			inventory.setStackInSlot(0, itemStack.split(itemStack.getMaxStackSize()));
			inventoryChanged();
			return true;
		}
		return false;
	}

	public ItemStack removeAllItem() {
		if (!isEmpty()) {
			ItemStack item = getStoredItem().split(getStoredItem().getMaxStackSize());
			inventoryChanged();
			return item;
		}
		return ItemStack.EMPTY;
	}

	public ItemStack removeOneItem() {
		if (!isEmpty()) {
			if (getStoredItem().getCount() == 1) {
				return removeAllItem();
			} else {
				getStoredItem().setCount(getStoredItem().getCount() - 1);
				//				ItemStack item = getStoredItem().split(getStoredItem().getMaxStackSize());
				inventoryChanged();
				return getStoredItem().copy().split(1);
			}

		}
		return ItemStack.EMPTY;
	}

	public IItemHandler getInventory() {
		return inventory;
	}

	public ItemStack getStoredItem() {
		return inventory.getStackInSlot(0);
	}

	public boolean isEmpty() {
		return inventory.getStackInSlot(0).isEmpty();
	}

	public boolean hasNoFluid() {
		return this.tank.isEmpty();
	}

	protected void inventoryChanged() {
		super.setChanged();
		if (level != null)
			level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
	}

	public FluidTank getTank() {
		return tank;
	}

	public boolean processSqueezing(Level worldIn) {
		var recipeList = worldIn.getRecipeManager().getRecipesFor(RecipeRegister.squeezingType.get(), new RecipeWrapper(inventory), worldIn);

		Cuisine.logger(RecipeRegister.squeezingType.get() + "" + recipeList.size());
		if (recipeList.isEmpty() || tank.getFluidAmount() >= Capacity) {
			//				return Optional.empty();
			return false;
		}
		try {
			for (int i = 0; i < recipeList.size(); i++) {

				BasinSqueezingRecipe recipe = recipeList.get(i).value();
				if (tank.isEmpty() || recipe.getResult().get(0).getFluid() == tank.getFluid().getFluid()) {
					removeOneItem();
					FluidStack stack = recipe.getResult().get(0).copy();
					tank.fill(stack, IFluidHandler.FluidAction.EXECUTE);
					//                    Cuisine.logger(stack.getDisplayName());
					return true;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public boolean tryProcessSqueezing(Level worldIn) {
		var recipeList = worldIn.getRecipeManager().getRecipesFor(RecipeRegister.squeezingType.get(), new RecipeWrapper(inventory), worldIn);
		if (recipeList.isEmpty() || tank.getFluidAmount() >= Capacity) {
			//				return Optional.empty();
			return false;
		}
		try {
			BasinSqueezingRecipe recipe = recipeList.get(0).value();
			if (tank.isEmpty() || recipe.getResult().get(0).getFluid() == tank.getFluid().getFluid()) {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}


}
