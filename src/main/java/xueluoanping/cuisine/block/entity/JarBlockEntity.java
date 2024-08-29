package xueluoanping.cuisine.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import xueluoanping.cuisine.register.BlockEntityRegister;

public class JarBlockEntity extends SyncBlockEntity {
	public JarBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegister.jar_entity_type.get(), pos, state);
	}

}
