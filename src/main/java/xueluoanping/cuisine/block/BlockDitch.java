package xueluoanping.cuisine.block;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.LEVEL;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.LEVEL_FLOWING;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class BlockDitch extends Block implements SimpleWaterloggedBlock {
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
	public static final BooleanProperty EAST = BlockStateProperties.EAST;
	public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
	public static final BooleanProperty WEST = BlockStateProperties.WEST;
	public static final BooleanProperty BOTTOM = BooleanProperty.create("bottom");
	public static final BooleanProperty BLOCKED = BooleanProperty.create("blocked");
	public static final IntegerProperty WATER_LEVEL = IntegerProperty.create("water_level", 0, 32);

	public BlockDitch(Properties properties) {
		super(properties);
		this.registerDefaultState(defaultBlockState().setValue(WATERLOGGED, false)
				.setValue(NORTH, false)
				.setValue(EAST, false)
				.setValue(SOUTH, false)
				.setValue(WEST, false)
				.setValue(BOTTOM, false)
				.setValue(BLOCKED, false)
				.setValue(WATER_LEVEL, 0));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
		super.createBlockStateDefinition(p_49915_.add(WATERLOGGED, NORTH, SOUTH, EAST, WEST, BOTTOM, BLOCKED, WATER_LEVEL));
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		try {
//            Cuisine.logger(Fluids.WATER.getFlowing());
			if (state.getValue(WATER_LEVEL) > 0) {
//                Cuisine.logger(state.getValue(WATER_LEVEL) / 4);
				int level = state.getValue(WATER_LEVEL) / 3;
				level = Math.min(level, 7);
				level = Math.max(level, 4);
				return Fluids.WATER.getFlowing(level, false);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	public BlockState getStateForPlacement(BlockPlaceContext context) {

		BlockState baseState = this.defaultBlockState();
		BlockPos pos = context.getClickedPos();

		if (context.getLevel().getBlockState(pos.east()).getBlock() == Blocks.WATER
		) {
			baseState = baseState.setValue(EAST, true);
			baseState = baseState.setValue(WATER_LEVEL, context.getLevel().getBlockState(pos.east()).getValue(LEVEL) * 4);
		}
		if (context.getLevel().getBlockState(pos.west()).getBlock() == Blocks.WATER
		) {
			baseState = baseState.setValue(WEST, true);
			baseState = baseState.setValue(WATER_LEVEL, context.getLevel().getBlockState(pos.west()).getValue(LEVEL) * 4);
		}
		if (context.getLevel().getBlockState(pos.south()).getBlock() == Blocks.WATER
		) {
			baseState = baseState.setValue(SOUTH, true);
			baseState = baseState.setValue(WATER_LEVEL, context.getLevel().getBlockState(pos.south()).getValue(LEVEL) * 4);
		}
		if (context.getLevel().getBlockState(pos.north()).getBlock() == Blocks.WATER
		) {
			baseState = baseState.setValue(NORTH, true);
			baseState = baseState.setValue(WATER_LEVEL, context.getLevel().getBlockState(pos.north()).getValue(LEVEL) * 4);
		}


		FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
		boolean flag = fluidstate.getType() == Fluids.WATER;
		return baseState.setValue(WATERLOGGED, flag);

	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState state1, LevelAccessor accessor, BlockPos pos, BlockPos pos1) {


		if (state.getValue(WATERLOGGED)) {
			state = state.setValue(WATER_LEVEL, 32);
			accessor.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(accessor));
		}
		boolean flag = (state1.getBlock() instanceof BlockDitch) | (state1.getBlock() == Blocks.WATER) | state1.hasProperty(LEVEL_FLOWING);

		if (flag) {
//            这个逻辑很简单，只允许单一方向流动水
//            或许要考虑连通图的思想
			if (state1.getBlock() instanceof BlockDitch)
				if (state1.getValue(WATER_LEVEL) > 0 && state1.getValue(WATER_LEVEL) > state.getValue(WATER_LEVEL)) {
//                Cuisine.logger(state,state1);
					state = state.setValue(WATER_LEVEL, state1.getValue(WATER_LEVEL) - 1);
				}
			if (state1.getBlock() == Blocks.WATER) state = state.setValue(WATER_LEVEL, state1.getValue(LEVEL) * 4);
//			if (state1.hasProperty(LEVEL_FLOWING)) state = state.setValue(WATER_LEVEL, (state1.getValue(LEVEL_FLOWING)-1) * 3);
//			Direction.byName()
			switch (direction) {
				case NORTH:
					return state.setValue(NORTH, true);
				case SOUTH:
					return state.setValue(SOUTH, true);
				case EAST:
					return state.setValue(EAST, true);
				case WEST:
					return state.setValue(WEST, true);
			}
		} else {
			switch (direction) {
				case NORTH:
					return state.setValue(NORTH, false);
				case SOUTH:
					return state.setValue(SOUTH, false);
				case EAST:
					return state.setValue(EAST, false);
				case WEST:
					return state.setValue(WEST, false);
			}
			;
		}
		return super.updateShape(state, direction, state1, accessor, pos, pos1);
	}
}
