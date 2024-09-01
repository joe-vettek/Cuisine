package xueluoanping.cuisine.blockentity.firepit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import xueluoanping.cuisine.register.BlockEntityRegister;

public class WokOnFirePitbBlockEntity extends AbstractFirepitBlockEntity {
	public WokOnFirePitbBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegister.wok_on_fire_pit_entity_type.get(), pos, state);
	}

	@Override
	public void loadAdditional(CompoundTag data, HolderLookup.Provider pRegistries) {
		super.loadAdditional(data, pRegistries);
	}


	@Override
	protected void saveAdditional(CompoundTag data, HolderLookup.Provider pRegistries) {
		super.saveAdditional(data, pRegistries);
	}

	@Override
	public void tick() {
		super.tick();
	}

	public static void tickEntity(Level level, BlockPos pos, BlockState state, WokOnFirePitbBlockEntity entity1) {
		entity1.tick();
	}
}
