package xueluoanping.cuisine.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import xueluoanping.cuisine.register.BlockEntityRegister;

public class PlateBlockEntity extends SyncBlockEntity {
	public PlateBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegister.plate_entity_type.get(), pos, state);
	}

}
