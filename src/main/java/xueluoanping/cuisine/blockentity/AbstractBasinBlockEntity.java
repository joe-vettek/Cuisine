package xueluoanping.cuisine.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import org.jetbrains.annotations.NotNull;

import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.craft.BasinSqueezingRecipe;
import xueluoanping.cuisine.register.RecipeRegister;

import java.util.List;

public class AbstractBasinBlockEntity extends SyncBlockEntity {
	private final ItemStackHandler inventory;
	private final LazyOptional<IItemHandler> inputHandler;
	public int tick = 0;
	public int speedCache = 0;


	public final betterFluidHandler tank;
	private final LazyOptional<betterFluidHandler> tankHandler;

	public static int Capacity = 8000;
	public int tickCheckThrowing = 0;
	private float renderingAmount = 0;
	boolean squeezingFailed = false;

	public AbstractBasinBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
		super(blockEntityType, pos, state);
		inventory = createHandler();
		inputHandler = LazyOptional.of(() -> inventory);
		tank = createFuildHandler();
		tankHandler = LazyOptional.of(() -> tank);
	}


	@Override
	public void load(CompoundTag compound) {
		// Cuisine.logger(compound);
		inventory.deserializeNBT(compound.getCompound("Items"));
		tank.deserializeNBT(compound.getCompound("Tank"));
		super.load(compound);
	}

	@Override
	public void saveAdditional(CompoundTag compound) {
		compound.put("Items", inventory.serializeNBT());
		compound.put("Tank", tank.serializeNBT());
		super.saveAdditional(compound);
	}


	protected ItemStackHandler createHandler() {
		return new ItemStackHandler() {
			@Override
			public int getSlotLimit(int slot) {
				return 1;
			}

			@Override
			protected void onContentsChanged(int slot) {
				inventoryChanged();
			}
		};
	}

	protected betterFluidHandler createFuildHandler() {
		return new betterFluidHandler(Capacity) {
		};
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

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		tankHandler.invalidate();
	}


	@NotNull
	@Override
	public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @org.jetbrains.annotations.Nullable Direction side) {
		//        if (side == Direction.UP && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
		//            return LazyOptional.of(() -> new ItemStackHandler() {
		//                @Override
		//                public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
		//                    return stack.getItem() == Items.COBBLESTONE;
		//                }
		//            }).cast();
		//        }
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return inputHandler.cast();
		}
		if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			inventoryChanged();
			return tankHandler.cast();
		}


		return super.getCapability(cap, side);
	}

	public boolean processSqueezing(Level worldIn) {
		List<BasinSqueezingRecipe> recipeList = worldIn.getRecipeManager().getRecipesFor(RecipeRegister.squeezingType.get(), new RecipeWrapper(inventory), worldIn);

		Cuisine.logger(RecipeRegister.squeezingType.get() + "" + recipeList.size());
		if (recipeList.isEmpty() || tank.getFluidAmount() >= Capacity) {
			//				return Optional.empty();
			return false;
		}
		try {
			for (int i = 0; i < recipeList.size(); i++) {

				BasinSqueezingRecipe recipe = recipeList.get(i);
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
		List<BasinSqueezingRecipe> recipeList = worldIn.getRecipeManager().getRecipesFor(RecipeRegister.squeezingType.get(), new RecipeWrapper(inventory), worldIn);
		if (recipeList.isEmpty() || tank.getFluidAmount() >= Capacity) {
			//				return Optional.empty();
			return false;
		}
		try {
			BasinSqueezingRecipe recipe = recipeList.get(0);
			if (tank.isEmpty() || recipe.getResult().get(0).getFluid() == tank.getFluid().getFluid()) {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public class betterFluidHandler extends FluidTank {

		public betterFluidHandler(int capacity) {
			super(capacity);
		}

		public Tag serializeNBT() {
			CompoundTag nbt = new CompoundTag();
			return writeToNBT(nbt);
		}

		public void deserializeNBT(CompoundTag tank) {
			readFromNBT(tank);
		}
	}
}
