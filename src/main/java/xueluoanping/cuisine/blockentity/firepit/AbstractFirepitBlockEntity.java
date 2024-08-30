package xueluoanping.cuisine.blockentity.firepit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import xueluoanping.cuisine.block.firepit.BlockFirePit;
import xueluoanping.cuisine.blockentity.SyncBlockEntity;
import xueluoanping.cuisine.blockentity.handler.FuelHeatHandler;

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
	public void loadAdditional(CompoundTag data, HolderLookup.Provider pRegistries) {

		if (data.contains("heat", CompoundTag.TAG_FLOAT)) {
			heatHandler.setHeat(data.getFloat("heat"));
		}
		if (data.contains("burnTime", CompoundTag.TAG_FLOAT)) {
			heatHandler.setBurnTime(data.getFloat("burnTime"));
		}
		super.loadAdditional(data,pRegistries);
	}

	@Override
	protected void saveAdditional(CompoundTag data, HolderLookup.Provider pRegistries) {
		data.putFloat("heat", heatHandler.getHeat());
		data.putFloat("burnTime", heatHandler.getBurnTime());
		super.saveAdditional(data,pRegistries);
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


	public void tick() {
		FuelHeatHandler handler = getHeatHandler();
		int light_level = (int) (handler.getBurnTime() * 1.1f / handler.getMaxBurnTime() * 15);
		light_level = Mth.clamp(light_level, 0, 15);
		if (getBlockState().getValue(BlockFirePit.LIGHT_LEVEL) != light_level) {
			BlockState statenew = getBlockState().setValue(BlockFirePit.LIGHT_LEVEL, light_level);
			// return (int) (handler.getBurnTime() / handler.getMaxBurnTime() * 15);
			level.setBlock(getBlockPos(), statenew, Block.UPDATE_CLIENTS);
		}
		update();
	}

}
