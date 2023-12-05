package xueluoanping.cuisine.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import xueluoanping.cuisine.block.entity.handler.FuelHeatHandler;

public class AbstractFirepitBlockEntity extends SyncBlockEntity {

	protected final FuelHeatHandler heatHandler;

	public AbstractFirepitBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
		super(blockEntityType, pos, state);
		this.heatHandler = new FuelHeatHandler(0, 230, 3, 0.6f);
		;
	}

	public boolean addFuel(ItemStack stack) {
		if (FuelHeatHandler.isFuel(stack, true)) {
			heatHandler.addFuel(stack);
			return true;
		} else
			return false;
	}

	@Override
	public void load(CompoundTag data) {

		if (data.contains("heat", CompoundTag.TAG_FLOAT)) {
			heatHandler.setHeat(data.getFloat("heat"));
		}
		if (data.contains("burnTime", CompoundTag.TAG_FLOAT)) {
			heatHandler.setBurnTime(data.getFloat("burnTime"));
		}
		super.load(data);
	}

	@Override
	protected void saveAdditional(CompoundTag data) {
		data.putFloat("heat", heatHandler.getHeat());
		data.putFloat("burnTime", heatHandler.getBurnTime());
		super.saveAdditional(data);
	}

	// @Override
	// use this method in ticker
	public void update() {
		// https://minecraft.gamepedia.com/Biome#Temperature
		heatHandler.setMinHeat(getLevel().getBiome(getBlockPos()).value().getBaseTemperature() * 28);
		heatHandler.update(0);
		inventoryChanged();
	}


	public FuelHeatHandler getHeatHandler() {
		return heatHandler;
	}

}
