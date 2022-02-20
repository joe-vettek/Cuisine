package xueluoanping.cuisine.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;

import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.jetbrains.annotations.NotNull;

import org.stringtemplate.v4.ST;

import snownee.kiwi.block.entity.RetextureBlockEntity;
import snownee.kiwi.util.NBTHelper;
import xueluoanping.cuisine.CoreModule;
import xueluoanping.cuisine.api.util.NBTUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BasinBlockEntity extends RetextureBlockEntity {
	private final ItemStackHandler inventory;
	private final LazyOptional<IItemHandler> inputHandler;


	private String[] textureName;

	public final betterFluidHandler tank;
	private final LazyOptional<betterFluidHandler> tankHandler;

	public static int Capacity = 8000;
	public int tickCheckThrowing = 0;
	private float renderingAmount = 0;
	boolean squeezingFailed = false;


	public BasinBlockEntity(BlockPos pos, BlockState state) {
		super(CoreModule.basinBlockEntityBlockEntityType, pos, state, "particle"); // all为可变贴图的key，支持多个key
		inventory = createHandler();
		inputHandler = LazyOptional.of(() -> inventory);
		tank = createFuildHandler();
		tankHandler = LazyOptional.of(() -> tank);
		textureName = new String[2];
	}


	@Override
	public void load(CompoundTag compound) {
		inventory.deserializeNBT(compound.getCompound("Items"));
		tank.deserializeNBT(compound.getCompound("Tank"));
		try {
			this.setTextureName(NBTUtils.getNameFromRetxtureTag((CompoundTag) compound.get("Overrides")).split(":"));
//			logger.info("t" + textureName[0]);
		} catch (Exception ex0) {
			ex0.printStackTrace();
//			this.setTextureName(NBTHelper.create().setString("BlockEntityTag", "default").get());
		}
		readPacketData(compound);
//		logger.info("cuisineLoad" + compound.getCompound("Items").toString() + getStoredItem().toString());
		super.load(compound);
	}

	@Override
	public void saveAdditional(CompoundTag compound) {
		compound.put("Items", inventory.serializeNBT());
		compound.put("Tank", tank.serializeNBT());
//		logger.info(tank.serializeNBT());
		try {
//			compound.put("BlockEntityTag", getTextureName());
//			this.setTextureName(NBTUtils.getNameFromRetxtureTag((CompoundTag) compound.get("BlockEntityTag")).split(":"));
		} catch (Exception ex0) {
			compound.put("BlockEntityTag", NBTHelper.create().setString("BlockEntityTag", "default").get());
		}
		writePacketData(compound);
		super.saveAdditional(compound);

	}

	private ItemStackHandler createHandler() {
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

	private betterFluidHandler createFuildHandler() {
		return new betterFluidHandler(Capacity) {
		};
	}

	public String[] getTextureName() {
		return textureName;
	}

	public void setTextureName(String[] textureName) {
		this.textureName = textureName;
	}

	public boolean addItem(ItemStack itemStack) {
		if (isEmpty() && !itemStack.isEmpty()) {
			inventory.setStackInSlot(0, itemStack.split(itemStack.getMaxStackSize()));
			inventoryChanged();
			return true;
		}
		return false;
	}

	@Override
	@Nullable
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag() {
		return saveWithFullMetadata();
	}

	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
		load(pkt.getTag());
	}

	@Override
	public void refresh() {
		super.refresh();
	}

	public ItemStack removeItem() {
		if (!isEmpty()) {
			ItemStack item = getStoredItem().split(getStoredItem().getMaxStackSize());
			inventoryChanged();
			return item;
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

	public static Logger logger = LogManager.getLogger();


	@Override
	public void onLoad() {
		this.getModelData();
		super.onLoad();
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		tankHandler.invalidate();
	}


	@NotNull
	@Override
	public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @org.jetbrains.annotations.Nullable Direction side) {
		if (side == Direction.UP && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return LazyOptional.of(() -> new ItemStackHandler() {
				@Override
				public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
					return stack.getItem() == Items.COBBLESTONE;
				}
			}).cast();
		}
		if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			inventoryChanged();
			return tankHandler.cast();
		}


		return super.getCapability(cap, side);
	}


	public class betterFluidHandler extends FluidTank {

		public betterFluidHandler(int capacity) {
			super(capacity);
		}

		public Tag serializeNBT() {
			NBTHelper nbt = NBTHelper.create();
			return writeToNBT(nbt.get());
		}

		public void deserializeNBT(CompoundTag tank) {
			readFromNBT(tank);
		}
	}
}
