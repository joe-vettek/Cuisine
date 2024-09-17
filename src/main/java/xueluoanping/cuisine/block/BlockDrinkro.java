package xueluoanping.cuisine.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.Nullable;
import xueluoanping.cuisine.block.baseblock.SimpleHorizontalEntityBlock;
import xueluoanping.cuisine.register.BlockEntityRegister;

public class BlockDrinkro extends SimpleHorizontalEntityBlock {
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    public BlockDrinkro(Properties p_49795_) {
        super(p_49795_);
        registerDefaultState(defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(HALF));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(BlockDrinkro::new);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return pState.getValue(HALF) == DoubleBlockHalf.LOWER ? null : BlockEntityRegister.drinkro_type.get().create(pPos, pState);
    }


    @Override
    public RenderShape getRenderShape(BlockState state) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? RenderShape.MODEL : RenderShape.INVISIBLE;
    }


    @Override
    protected BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
        if ((pDirection == Direction.UP && pState.getValue(HALF) == DoubleBlockHalf.LOWER) ||
                (pDirection == Direction.DOWN && pState.getValue(HALF) == DoubleBlockHalf.UPPER)) {
            if (!pNeighborState.is(this)) {
                return Blocks.AIR.defaultBlockState();
            }
        }
        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }
}
