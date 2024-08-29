package xueluoanping.cuisine.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import xueluoanping.cuisine.register.BlockEntityRegister;

public class BasinColorBlockEntity extends AbstractBasinBlockEntity{
	public BasinColorBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegister.basin_colored_entity_type.get(),pos, state);
	}
}
