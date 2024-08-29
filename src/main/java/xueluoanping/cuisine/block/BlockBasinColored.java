package xueluoanping.cuisine.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import org.jetbrains.annotations.Nullable;

import xueluoanping.cuisine.blockentity.BasinColorBlockEntity;
import xueluoanping.cuisine.register.BlockEntityRegister;

public class BlockBasinColored extends BlockBasin{
	public BlockBasinColored(Properties builder) {
		super(builder);
	}

	@Override
	public RenderShape getRenderShape(BlockState p_60550_) {
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState state) {
		if (hasBlockEntity(state)) {
			BasinColorBlockEntity basinBlockEntity = new BasinColorBlockEntity(pPos, state);

			//			basinBlockEntity.setTextureName();

			return basinBlockEntity;
		}
		return null;
	}
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level worldIn, BlockState blockState, BlockEntityType<T> blockEntityType) {


		return !worldIn.isClientSide ?
				createTickerHelper(blockEntityType, BlockEntityRegister.basin_colored_entity_type.get(), BlockBasinColored::tickEntity) : null;
		//		return EntityBlock.super.getTicker(p_153212_, p_153213_, p_153214_);
	}

	@Nullable
	protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> blockEntityType, BlockEntityType<E> blockEntityType1, BlockEntityTicker<? super E> blockEntityTicker) {
		return blockEntityType1 == blockEntityType ? (BlockEntityTicker<A>) blockEntityTicker : null;
	}

	//write here,server side
	private static void tickEntity(Level worldIn, BlockPos pos, BlockState blockState, BasinColorBlockEntity basinBlockEntity) {
		//		if (CreateCompact.isLoad())
		//			CreateCompact.tickEntity(worldIn, pos, blockState, basinBlockEntity);
		if (worldIn.isRaining() && (basinBlockEntity.hasNoFluid() || basinBlockEntity.tank.getFluid().getFluid() == Fluids.WATER)) {
			basinBlockEntity.tank.fill(new FluidStack(Fluids.WATER, 10), IFluidHandler.FluidAction.EXECUTE);
		}

	}

}
