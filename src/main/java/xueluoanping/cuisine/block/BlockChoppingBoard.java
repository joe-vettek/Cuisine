package xueluoanping.cuisine.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import xueluoanping.cuisine.block.entity.BasinColorBlockEntity;
import xueluoanping.cuisine.block.entity.ChoppingBoardBlockEntity;

public class BlockChoppingBoard extends Block implements EntityBlock {
	public static final VoxelShape BOARD = Block.box(1.6D, 0D, 1.6D, 14.4D, 6.4D, 14.4D);

	public BlockChoppingBoard(Properties properties) {
		super(properties);
	}


	public boolean hasBlockEntity(BlockState state) {
		return true;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState state) {
		if (hasBlockEntity(state)) {
			ChoppingBoardBlockEntity choppingBoardBlockEntity =
					new ChoppingBoardBlockEntity(pPos, state);
			return choppingBoardBlockEntity;
		}
		return null;
	}

	@Override
	public RenderShape getRenderShape(BlockState p_60550_) {
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public boolean isCollisionShapeFullBlock(BlockState p_181242_, BlockGetter p_181243_, BlockPos p_181244_) {
		return false;
	}
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return BOARD;
	}
}
