package xueluoanping.cuisine.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import xueluoanping.cuisine.register.BlockEntityRegister;

public class BasinBlockEntity extends AbstractBasinBlockEntity {
    public BasinBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegister.basin_entity_type.get(),pos,state);
    }


}
