package xueluoanping.cuisine.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import xueluoanping.cuisine.register.BlockEntityRegister;

public class ChoppingBoardBlockEntity extends SyncBlockEntity{
	public ChoppingBoardBlockEntity( BlockPos pos, BlockState state) {
		super(BlockEntityRegister.chopping_board_entity_type.get(), pos, state);
	}
}
