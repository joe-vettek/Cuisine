package xueluoanping.cuisine.blockentity.firepit;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import xueluoanping.cuisine.register.BlockEntityRegister;

public class FirePitBlockEntity extends AbstractFirepitBlockEntity {
	public FirePitBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegister.fire_pit_entity_type.get(), pos, state);
	}



	public static void tickEntity(Level level, BlockPos pos, BlockState state, FirePitBlockEntity entity1) {
		entity1.tick();
	}
}
