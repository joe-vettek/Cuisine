package xueluoanping.cuisine.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.block.BlockFirePit;
import xueluoanping.cuisine.block.entity.handler.FuelHeatHandler;
import xueluoanping.cuisine.register.BlockEntityRegister;

public class FirePitBlockEntity extends AbstractFirepitBlockEntity {
	public FirePitBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegister.fire_pit_entity_type.get(), pos, state);
	}

	//strange,
	// @Override
	// public void load(CompoundTag data) {
	// 	super.load(data);
	// 	// int light_level = (int) (getHeatHandler().getBurnTime() / getHeatHandler().getMaxBurnTime() * 15+1);
	// 	// light_level= Math.min(light_level, 15);
	// 	// Cuisine.logger(getLevel(),data);
	// 	// if(getBlockState().getValue(BlockFirePit.LIGHT_LEVEL)!=light_level)
	// 	// BlockState state=getBlockState().setValue(BlockFirePit.LIGHT_LEVEL, light_level);
	// 	// if(!level.isClientSide())
	// 	// level.setBlockAndUpdate(getBlockPos(), state);
	// 	// Cuisine.logger(getBlockState().getLightEmission());
	// }

	public static void tickEntity(Level level, BlockPos pos, BlockState state, FirePitBlockEntity entity1) {
		// Cuisine.logger("ss");
		// if(level.getBlockEntity(pos) instanceof AbstractFirepitBlockEntity entity)
		{
			FuelHeatHandler handler=entity1.getHeatHandler();
			int light_level =  (int) (handler.getBurnTime()*1.1f / handler.getMaxBurnTime() * 15);
			light_level= Mth.clamp(light_level,0, 15);
			if(state.getValue(BlockFirePit.LIGHT_LEVEL)!=light_level)
			{
				BlockState statenew=state.setValue(BlockFirePit.LIGHT_LEVEL, light_level);
				// return (int) (handler.getBurnTime() / handler.getMaxBurnTime() * 15);
				level.setBlock(pos, statenew,Block.UPDATE_LIMIT);
			}
		}

		entity1.update();
	}
}
