package xueluoanping.cuisine.block.nature.crop;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.WaterLilyBlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.PlantType;
import org.jetbrains.annotations.Nullable;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.block.nature.BlockCuisineCrops;
import xueluoanping.cuisine.block.nature.BlockDoubleCrops;
import xueluoanping.cuisine.register.CropRegister;

import java.util.ArrayList;
import java.util.stream.Collectors;

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


    @Override
    public BlockState getPlant(BlockGetter world, BlockPos pos) {
        return CropRegister.rice.get().defaultBlockState();
    }

    @Override
    public PlantType getPlantType(BlockGetter level, BlockPos pos) {
        return PlantType.WATER;
    }


    // 能不能放置主要看这里
    // Forge: This function is called during world gen and placement,
    // before this block is set, so if we are not 'here' then assume it's the pre-check.
    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
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
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (isUpper(state))
            return Fluids.EMPTY.defaultFluidState();
        return Fluids.WATER.getSource(false);
    }


    @Override
    public boolean canPlaceLiquid(BlockGetter p_54766_, BlockPos p_54767_, BlockState p_54768_, Fluid p_54769_) {
        return false;
    }

    @Override
    public boolean placeLiquid(LevelAccessor p_54770_, BlockPos p_54771_, BlockState p_54772_, FluidState p_54773_) {
        return false;
    }

}
