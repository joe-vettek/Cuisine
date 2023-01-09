package xueluoanping.cuisine.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import net.minecraftforge.items.ItemStackHandler;

import net.minecraftforge.items.wrapper.RecipeWrapper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import xueluoanping.cuisine.craft.MillingRecipe;
import xueluoanping.cuisine.register.BlockEntityRegister;
import xueluoanping.cuisine.register.RecipeRegister;

import java.util.List;

public class MillBlockEntity extends SyncBlockEntity {

	private final ItemStackHandler inventory_in;
	private final LazyOptional<IItemHandler> inputHandler;
	private final ItemStackHandler inventory_out;
	private final LazyOptional<IItemHandler> outputHandler;

	private float progress;
	private int power;

	public final FluidTank tankIn;
	private final LazyOptional<FluidTank> tankIn_Handler;
	public final FluidTank tankOut;
	private final LazyOptional<FluidTank> tankOut_Handler;
	private int Capacity = 1000;

	public MillBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegister.mill_entity_type.get(), pos, state);
		inventory_in = createHandlerIn();
		inputHandler = LazyOptional.of(() -> inventory_in);
		inventory_out = createHandler();
		outputHandler = LazyOptional.of(() -> inventory_out);

		tankIn = createFuildHandler();
		tankIn_Handler = LazyOptional.of(() -> tankIn);
		tankOut = createFuildHandler();
		tankOut_Handler = LazyOptional.of(() -> tankOut);

		this.progress = 360;
		this.power = 0;
	}


	@Override
	public void load(CompoundTag tag) {
		inventory_in.deserializeNBT(tag.getCompound("input"));
		inventory_out.deserializeNBT(tag.getCompound("output"));
		tankIn.readFromNBT(tag.getCompound("inputFluid"));
		tankOut.readFromNBT(tag.getCompound("outputFluid"));
		this.progress = tag.contains("progress") ? tag.getInt("progress") : 360;
		this.power = tag.contains("power") ? tag.getInt("power") : 0;
		// Cuisine.logger(tag);
		super.load(tag);
	}

	@Override
	protected void saveAdditional(CompoundTag tag) {
		tag.put("input", inventory_in.serializeNBT());
		tag.put("output", inventory_out.serializeNBT());
		tag.put("inputFluid", tankIn.writeToNBT(new CompoundTag()));
		tag.put("outputFluid", tankOut.writeToNBT(new CompoundTag()));
		tag.putFloat("progress", progress);
		tag.putInt("power", power);
		super.saveAdditional(tag);
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

	//write here,server side
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


	@NotNull
	@Override
	public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {

		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			if (side == Direction.UP) {
				return inputHandler.cast();
			} else
				return outputHandler.cast();
		if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			if (side == Direction.UP) {
				return tankIn_Handler.cast();
			} else
				return tankOut_Handler.cast();
		return super.getCapability(cap, side);
	}


	public boolean processMilling(Level worldIn) {
		List<MillingRecipe> recipeList = worldIn.getRecipeManager().
				getRecipesFor(RecipeRegister.millingType.get(), new RecipeWrapper(inventory_in), worldIn);

		// Cuisine.logger(RecipeRegister.millingType.get(), recipeList.size());
		if (recipeList.isEmpty() || tankOut.getFluidAmount() >= Capacity || inventory_out.getStackInSlot(0).getCount() >= 64) {
			//				return Optional.empty();
			return false;
		}
		try {
			for (int i = 0; i < recipeList.size(); i++) {

				MillingRecipe recipe = recipeList.get(i);
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
