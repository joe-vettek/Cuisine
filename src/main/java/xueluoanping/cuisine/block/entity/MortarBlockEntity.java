package xueluoanping.cuisine.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import xueluoanping.cuisine.block.BlockFirePit;
import xueluoanping.cuisine.block.entity.handler.FuelHeatHandler;
import xueluoanping.cuisine.register.BlockEntityRegister;

public class MortarBlockEntity extends SyncBlockEntity {
	public MortarBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegister.mortar_entity_type.get(), pos, state);
	}

}
