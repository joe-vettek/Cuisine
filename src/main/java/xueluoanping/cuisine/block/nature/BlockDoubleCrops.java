package xueluoanping.cuisine.block.nature;


import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import xueluoanping.cuisine.Cuisine;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;

public class BlockDoubleCrops extends BlockCuisineCrops {
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    public static final VoxelShape LOWER_FULL =
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public BlockDoubleCrops(Properties properties) {
        super(properties);
        this.registerDefaultState(defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER));
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(HALF));
    }

    public static boolean isUpper(BlockState state) {
        return state.getValue(HALF) == DoubleBlockHalf.UPPER;
    }


    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        if (!isUpper(state)) {
            super.randomTick(state, level, pos, random);
            BlockState stateNew = level.getBlockState(pos);
            if (state != stateNew&&stateNew.is(this))
                level.setBlock(pos.above(), stateNew.setValue(HALF, DoubleBlockHalf.UPPER), Block.UPDATE_ALL);
        }

    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState state1, LevelAccessor level, BlockPos pos, BlockPos pos1) {
        // if (isUpper(state) && pos1 == pos.below()) {
        //     state = state.setValue(AGE, state1.getValue(AGE));
        //     // level.setBlock()
        //     return state;
        // }
        return super.updateShape(state, direction, state1, level, pos, pos1);
    }


    // Copied from BlockDoublePlant
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        // ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        if (!level.isClientSide) {
            if (player.isCreative()) {
                // also use for survival
                preventCreativeDropFromBottomPart(level, pos, state, player);
            } else {
                boolean isUpper = isUpper(state);
                if (isUpper) {
                    BlockState blockstate1 = state.getBlock() instanceof LiquidBlockContainer ?
                            Blocks.WATER.defaultBlockState() :
                            Blocks.AIR.defaultBlockState();
                    dropResources(level.getBlockState(pos.below()), level, pos.below(), (BlockEntity) null, player, player.getMainHandItem());
                    level.setBlock(pos.below(), blockstate1, 3);
                    // level.setBlock(pos, state, 35);
                } else {
                    dropResources(state, level, pos, (BlockEntity) null, player, player.getMainHandItem());
                    level.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), 3);
                }
                // level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);

                level.levelEvent(player, 2001, pos, Block.getId(state));
            }

        }
        super.playerWillDestroy(level, pos, state, player);

    }


    public void playerDestroy(Level p_52865_, Player p_52866_, BlockPos p_52867_, BlockState p_52868_, @Nullable BlockEntity p_52869_, ItemStack p_52870_) {
        super.playerDestroy(p_52865_, p_52866_, p_52867_, Blocks.AIR.defaultBlockState(), p_52869_, p_52870_);
    }

    protected static void preventCreativeDropFromBottomPart(Level level, BlockPos pos, BlockState state, Player player) {
        // DoubleBlockHalf doubleblockhalf = state.getValue(HALF);
        // level.setBlock(pos, Blocks.AIR.defaultBlockState(), 35);
        // level.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), 35);
        // level.setBlock(pos.below(), Blocks.AIR.defaultBlockState(), 35);
        if (isUpper(state)) {
            BlockPos blockpos = pos.below();
            BlockState blockstate = level.getBlockState(blockpos);
            // 先把枝条砍掉，不然产生掉落物
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 35);
            level.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
            if (blockstate.is(state.getBlock()) && !isUpper(blockstate)) {
                BlockState blockstate1 =
                        blockstate.getBlock() instanceof LiquidBlockContainer ?
                                Blocks.WATER.defaultBlockState() :
                                Blocks.AIR.defaultBlockState();
                level.setBlock(blockpos, blockstate1, 35);
                level.levelEvent(player, 2001, blockpos, Block.getId(blockstate));

            }
        } else {
            level.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), 35);
            // 播放粒子特效
            level.levelEvent(player, 2001, pos, Block.getId(state));
        }

    }


    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        if (state.getBlock() != this)
            return super.mayPlaceOn(state, worldIn, pos); // Forge: This function is called during world gen and
        // placement, before this block is set, so if we are not
        // 'here' then assume it's the pre-check.
        if (isUpper(state)) {
            return worldIn.getBlockState(pos.below()).getBlock() == this;
        } else {
            return worldIn.getBlockState(pos.above()).getBlock() == this && super.mayPlaceOn(state, worldIn, pos);
        }
    }


    public void growCrops(Level level, BlockPos pos, BlockState state) {
        // int i = this.getAge(state) + this.getBonemealAgeIncrease(level);
        // int j = this.getMaxAge();
        // if (i > j) {
        //     i = j;
        // }
        //
        // level.setBlock(pos, this.getStateForAge(i), 2);

        if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            super.growCrops(level, pos, state);
            level.setBlock(pos.above(), level.getBlockState(pos).setValue(HALF, DoubleBlockHalf.UPPER), 3);
        } else {
            super.growCrops(level, pos.below(), state);
            level.setBlock(pos, level.getBlockState(pos.below()).setValue(HALF, DoubleBlockHalf.UPPER), 3);

        }
    }


    @Override
    protected void spawnDestroyParticles(Level p_152422_, Player p_152423_, BlockPos p_152424_, BlockState p_152425_) {
        super.spawnDestroyParticles(p_152422_, p_152423_, p_152424_, p_152425_);
    }

    public VoxelShape getShape(BlockState state, BlockGetter source, BlockPos pos, CollisionContext context) {

        if (!isUpper(state))
            return LOWER_FULL;
        else {
            return super.getShape(state, source, pos, context);
        }
    }


    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        // if (!isUpper(state))
        //     return super.canSurvive(state, level, pos)
        //             && level.getBlockState(pos.above()).getBlock().equals(this);
        // else
        //     return level.getBlockState(pos.below()).getBlock().equals(this);
        // return true;
        BlockPos blockpos = pos.below();
        BlockState blockstate = level.getBlockState(blockpos);
        return !isUpper(state) ?
                super.canSurvive(state, level, pos) : blockstate.is(this);

    }


    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        // super.setPlacedBy(level, pos, state, entity, stack);
        state = state.setValue(HALF, DoubleBlockHalf.UPPER);
        level.setBlock(pos.above(), state, 3);
    }

}
