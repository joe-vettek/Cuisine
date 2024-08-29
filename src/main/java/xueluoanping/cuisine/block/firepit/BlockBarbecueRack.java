package xueluoanping.cuisine.block.firepit;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import org.jetbrains.annotations.Nullable;

import xueluoanping.cuisine.block.baseblock.SimpleHorizontalEntityBlock;
import xueluoanping.cuisine.register.BlockEntityRegister;
import xueluoanping.cuisine.util.MathUtils;

public class BlockBarbecueRack extends BlockFirePit  {
	public BlockBarbecueRack(Properties properties) {
		super(properties);
	}
	// @Override
	// protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
	// 	super.createBlockStateDefinition(builder.add(FACING));
	// }

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext collisionContext) {
		return Shapes.or(BlockFirePit.AABB,
				MathUtils.getShapefromDirection(6.8D, 0.0D, 0.D, 10.0D, 14.0D, 16.0D, state.getValue(FACING), true));
	}
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return BlockEntityRegister.barbecue_rack_entity_type.get().create(pos,state);
	}


}
