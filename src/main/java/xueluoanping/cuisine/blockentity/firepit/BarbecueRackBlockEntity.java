package xueluoanping.cuisine.blockentity.firepit;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import xueluoanping.cuisine.register.BlockEntityRegister;

public class BarbecueRackBlockEntity extends AbstractFirepitBlockEntity {
	public BarbecueRackBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegister.barbecue_rack_entity_type.get(), pos, state);
	}

	public static void tickEntity(Level level, BlockPos pos, BlockState state, BarbecueRackBlockEntity entity1) {
		entity1.tick();
	}
}
