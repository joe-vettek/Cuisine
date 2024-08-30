package xueluoanping.cuisine.block.nature.crop;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;

import org.jetbrains.annotations.Nullable;
import xueluoanping.cuisine.block.nature.BlockDoubleCrops;
import xueluoanping.cuisine.register.CropRegister;

public class BlockRice extends BlockDoubleCrops implements LiquidBlockContainer
        // implements LiquidBlockContainer
{
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};

    public BlockRice(Properties properties) {
        super(properties);
        // this.registerDefaultState(defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER));
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return CropRegister.rice_item.get();
    }


    // 能不能放置主要看这里
    // Forge: This function is called during world gen and placement,
    // before this block is set, so if we are not 'here' then assume it's the pre-check.
    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (isUpper(state))
            return super.canSurvive(state, level, pos);
        else if ((level.getRawBrightness(pos, 0) >= 8 || level.canSeeSky(pos))) {
            BlockPos blockpos = pos.below();
            // ensure have water
            if (!(level.isWaterAt(pos))) return false;
            if (state.getBlock() == this) // Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
                return level.getBlockState(blockpos).is(BlockTags.DIRT) && (
                        level.getBlockState(pos.above()).is(this) ||
                                level.isEmptyBlock(pos.above())
                );
            // seldom get here, so in fact during world gen and placement also  pre-check
            return this.mayPlaceOn(level.getBlockState(blockpos), level, blockpos);
        }
        return super.canSurvive(state, level, pos);

    }

    @Override
    protected boolean mayPlaceOn(BlockState state0, BlockGetter resource, BlockPos pos) {
        // 水稻、水、土
        // Cuisine.logger(state0, resource.getBlockState(pos.above()), resource.getBlockState(pos.above().above()));
        BlockState state1 = resource.getBlockState(pos.above());
        BlockState state2 = resource.getBlockState(pos.above().above());
        if (!state1.is(this))
            return state0.is(BlockTags.DIRT)
                    && resource.getBlockState(pos.above()).getFluidState().isSource()
                    && resource.getBlockState(pos.above().above()).isAir();
        else
            return state0.is(BlockTags.DIRT);
        // return false;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (isUpper(state))
            return Fluids.EMPTY.defaultFluidState();
        return Fluids.WATER.getSource(false);
    }


    @Override
    public boolean canPlaceLiquid(@Nullable Player pPlayer, BlockGetter pLevel, BlockPos pPos, BlockState pState, Fluid pFluid) {
        return false;
    }

    @Override
    public boolean placeLiquid(LevelAccessor p_54770_, BlockPos p_54771_, BlockState p_54772_, FluidState p_54773_) {
        return false;
    }

}
