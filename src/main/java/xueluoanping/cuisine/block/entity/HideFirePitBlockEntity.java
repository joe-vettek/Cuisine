package xueluoanping.cuisine.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import xueluoanping.cuisine.register.BlockEntityRegister;

public class HideFirePitBlockEntity extends SyncBlockEntity {

    public HideFirePitBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegister.hide_fire_pit_entity_type.get(), pos, state);
    }
}
