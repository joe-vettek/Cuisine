package xueluoanping.cuisine.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import xueluoanping.cuisine.register.BlockEntityRegister;

public class FirePitBlockEntity extends SyncBlockEntity {

    public FirePitBlockEntity( BlockPos pos, BlockState state) {
        super(BlockEntityRegister.fire_pit_entity_type.get(), pos, state);
    }
}
