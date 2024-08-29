package xueluoanping.cuisine.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import xueluoanping.cuisine.register.BlockEntityRegister;

public class MortarBlockEntity extends SyncBlockEntity {
	public MortarBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegister.mortar_entity_type.get(), pos, state);
	}

}
