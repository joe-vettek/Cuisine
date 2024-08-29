package xueluoanping.cuisine.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import xueluoanping.cuisine.block.BlockFirePit;
import xueluoanping.cuisine.block.entity.handler.FuelHeatHandler;
import xueluoanping.cuisine.register.BlockEntityRegister;

public class PlateBlockEntity extends SyncBlockEntity {
	public PlateBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegister.plate_entity_type.get(), pos, state);
	}

}
