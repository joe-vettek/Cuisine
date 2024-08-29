package xueluoanping.cuisine.block.firepit;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import org.jetbrains.annotations.Nullable;

import xueluoanping.cuisine.block.baseblock.SimpleHorizontalEntityBlock;
import xueluoanping.cuisine.register.BlockEntityRegister;
import xueluoanping.cuisine.util.MathUtils;

public class BlockFirePitWithWok extends BlockFirePit {
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
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return BlockEntityRegister.wok_on_fire_pit_entity_type.get().create(pos,state);
	}
}
