package xueluoanping.cuisine.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import xueluoanping.cuisine.register.BlockEntityRegister;

public class DrinkroBlockEntity extends SyncBlockEntity {
	public DrinkroBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegister.drinkro_type.get(), pos, state);
	}

}
