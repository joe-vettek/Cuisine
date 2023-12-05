package xueluoanping.cuisine.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import org.jetbrains.annotations.Nullable;

import xueluoanping.cuisine.block.baseblock.SimpleHorizontalEntityBlock;
import xueluoanping.cuisine.util.MathUtils;

public class BlockFirePitWithWok extends SimpleHorizontalEntityBlock {
	protected static final VoxelShape AABB_WITH_WOK = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.6D, 16.0D);

	public BlockFirePitWithWok(Properties properties) {
		super(properties);
	}


	@Override
	public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext collisionContext) {
		return Shapes.or(AABB_WITH_WOK,
				MathUtils.getShapefromDirection(15.2D, 6.88D, 7.17D, 25.15D, 9.02D, 9.17D, state.getValue(FACING), true));
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
		return null;
	}
}
