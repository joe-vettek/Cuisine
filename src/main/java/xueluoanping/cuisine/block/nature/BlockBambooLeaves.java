package xueluoanping.cuisine.block.nature;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.ItemAbilities;
import org.jetbrains.annotations.Nullable;
import xueluoanping.cuisine.register.BlockRegister;
import xueluoanping.cuisine.tag.CuisineTags;

import java.util.Locale;


public class BlockBambooLeaves extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    protected static final VoxelShape BaseShape = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 11.0D, 11.0D);
    protected static final VoxelShape LeaveShape = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 11.0D, 11.0D);

    public BlockBambooLeaves(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.EAST));
        for (BlockState possibleState : getStateDefinition().getPossibleStates()) {
            possibleState.offsetFunction = new BambooLeavesOffset();
        }
    }

    //    must all set
    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 60;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 60;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACING);
    }

    @Override
    public @Nullable Integer getBeaconColorMultiplier(BlockState state, LevelReader level, BlockPos pos, BlockPos beaconPos) {
        return super.getBeaconColorMultiplier(state, level, pos, beaconPos);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape shape_mix = Shapes.empty();
        if (state.getValue(FACING) == Direction.EAST) {
            shape_mix = LeaveShape.move(1, 0, 0);
        }
        if (state.getValue(FACING) == Direction.WEST) {
            shape_mix = LeaveShape.move(-1, 0, 0);
        }
        if (state.getValue(FACING) == Direction.NORTH) {
            shape_mix = LeaveShape.move(0, 0, -1);
        }
        if (state.getValue(FACING) == Direction.SOUTH) {
            shape_mix = LeaveShape.move(0, 0, 1);
        }

        Vec3 vec3 = state.getOffset(level, pos);
        // return shape_mix.move(vec3.x, vec3.y, vec3.z);
        return LeaveShape;
        // return shape_mix;
    }

    public class BambooLeavesOffset implements OffsetFunction {

        @Override
        public Vec3 evaluate(BlockState state, BlockGetter blockGetter, BlockPos pos) {
            // Block block = state.getBlock();
            // pos = pos.relative(state.getValue(FACING));
            // long i = Mth.getSeed(pos.getX(), 0, pos.getZ());
            // // float f = block.getMaxHorizontalOffset();
            // float f = getMaxHorizontalOffset();
            // double d0 = Mth.clamp(((double) ((float) (i & 15L) / 15.0F) - 0.5) * 0.5, (double) (-f), (double) f);
            // double d1 = Mth.clamp(((double) ((float) (i >> 8 & 15L) / 15.0F) - 0.5) * 0.5, (double) (-f), (double) f);
            // return new Vec3(d0, 0.0, d1);
            return BlockRegister.bamboo_plant.value().defaultBlockState().getOffset(blockGetter, pos.relative(state.getValue(FACING)));
            // return new Vec3(0,0,0);
        }
    }


    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getShape(state, level, pos, context);
    }

    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState state1, LevelAccessor level, BlockPos pos, BlockPos pos1) {

        if (!canSurvive(state, level, pos))
            return Blocks.AIR.defaultBlockState();
        return super.updateShape(state, direction, state1, level, pos, pos1);

    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            level.destroyBlock(pos, true);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.relative(state.getValue(FACING))).is(BlockRegister.bamboo_plant.get());
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        //        Cuisine.logger(defaultBlockState());
        return this.defaultBlockState();
    }


    @Override
    public void destroy(LevelAccessor p_49860_, BlockPos p_49861_, BlockState p_49862_) {
        super.destroy(p_49860_, p_49861_, p_49862_);
    }

    @Override
    public float getDestroyProgress(BlockState p_48901_, Player p_48902_, BlockGetter p_48903_, BlockPos p_48904_) {
        return p_48902_.getMainHandItem().canPerformAction(ItemAbilities.AXE_DIG) ? 1.0F
                : super.getDestroyProgress(p_48901_, p_48902_, p_48903_, p_48904_);
    }


    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        return ItemStack.EMPTY;
    }
}
