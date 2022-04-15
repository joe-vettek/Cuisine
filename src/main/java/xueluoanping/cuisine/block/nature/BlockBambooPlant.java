package xueluoanping.cuisine.block.nature;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.register.BlockRegister;

import java.util.Locale;
import java.util.Random;

/*
 *
 * 竹笋常见于林地。经过一段时间后可生长为竹子。
 * 竹子生长机制:竹子会在雨后的十分钟内尝试在周围的土地生长竹子。生长四次后将再也无法长出新的竹笋。将周围的土地变为耕地有助于竹笋发芽。
 * 技巧：如果单纯为了量产竹子，建议使用沉浸工程的园艺玻璃罩。
 *
 */
//竹子将改为竹鞭泥土蔓延生长竹笋，竹鞭泥土最远蔓延距离为4，下雨时蔓延更快且有几率含水
//用锄头挖掘竹鞭泥土有几率掉落竹笋，挖掘竹笋暂定不获得竹笋或获得老竹笋（老竹笋不适合做菜），耕地变为竹鞭泥土的几率更大
//竹子种类命名为淡竹
public class BlockBambooPlant extends Block implements BonemealableBlock {
    public static final EnumProperty<Type> TYPE = EnumProperty.create("type", Type.class);
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;

    protected static final VoxelShape BaseShape = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 11.0D, 11.0D);
    protected static final VoxelShape LeaveShape = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 11.0D, 11.0D);

    public BlockBambooPlant(Properties properties) {
        super(properties.dynamicShape());
        this.registerDefaultState(this.defaultBlockState().setValue(NORTH, false).setValue(WEST, false).setValue(EAST, false).setValue(SOUTH, false));
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
        stateBuilder.add(TYPE, EAST, NORTH, WEST, SOUTH);
    }

    @Override
    public MaterialColor defaultMaterialColor() {
        return super.defaultMaterialColor();
    }

    @Nullable
    @Override
    public float[] getBeaconColorMultiplier(BlockState state, LevelReader level, BlockPos pos, BlockPos beaconPos) {
        return super.getBeaconColorMultiplier(state, level, pos, beaconPos);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape shape_mix = BaseShape;
        if (state.getValue(EAST)) {
            shape_mix = Shapes.or(shape_mix, LeaveShape.move(1, 0, 0));
        }
        if (state.getValue(WEST)) {
            shape_mix = Shapes.or(shape_mix, LeaveShape.move(-1, 0, 0));
        }
        if (state.getValue(NORTH)) {
            shape_mix = Shapes.or(shape_mix, LeaveShape.move(0, 0, -1));
        }
        if (state.getValue(SOUTH)) {
            shape_mix = Shapes.or(shape_mix, LeaveShape.move(0, 0, 1));
        }
        Vec3 vec3 = state.getOffset(level, pos);
        return shape_mix.move(vec3.x, vec3.y, vec3.z);
    }


    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(TYPE).ordinal() >= 2)
            return getShape(state, level, pos, context);
        return Block.box(0, 0, 0, 0, 0, 0);
    }

    public BlockBehaviour.OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState state1, LevelAccessor level, BlockPos pos, BlockPos pos1) {
//        Cuisine.logger(state,state1);
        switch (direction) {
            case NORTH:
                return state.setValue(NORTH, false);
            case SOUTH:
                return state.setValue(SOUTH, false);
            case EAST:
                return state.setValue(EAST, false);
            case WEST:
                return state.setValue(WEST, false);
        }
        if ((state1.getBlock() instanceof BambooBlock
                || state1.getBlock() instanceof BambooSaplingBlock)
                && state.getValue(TYPE).ordinal() < 2) {
            return state.setValue(TYPE, Type.A_2);
        }

        if (!canSurvive(state, level, pos))
            return Blocks.AIR.defaultBlockState();
        return super.updateShape(state, direction, state1, level, pos, pos1);

    }

    @Override
    public boolean isRandomlyTicking(BlockState p_49921_) {
        return true;
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        if (!state.canSurvive(level, pos)) {
            level.destroyBlock(pos, true);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        if (state.getValue(TYPE).ordinal() > 2) return;
        if (state.getValue(TYPE).ordinal() == 0 && level.isRaining()) {
            Cuisine.logger("Is Raining!");
            if (random.nextInt(14) == 0) {
                level.setBlock(pos, state.setValue(TYPE, Type.A_1), 3);
            }
        }
        if (random.nextInt(14) == 0 && !level.isRaining() && level.isEmptyBlock(pos.above()) && level.getRawBrightness(pos.above(), 0) >= 9) {
            int height=getRandomBambooHeight(level);
            if (state.getValue(TYPE).ordinal() == 1 &&checkIfBambooCanGrow(level,pos,height))
                this.growBamboo(level, pos, height);
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).is(BlockTags.BAMBOO_PLANTABLE_ON) &&
                level.getBlockState(pos.below()).getBlock() != BlockRegister.bamboo.get() &&
                level.getBlockState(pos.below()).getBlock() != Blocks.BAMBOO_SAPLING;
    }


    @Override
    public boolean isValidBonemealTarget(BlockGetter level, BlockPos pos, BlockState state, boolean p_50900_) {
        return level.getBlockState(pos.above()).isAir() && state.getValue(TYPE).ordinal() < 2;
    }

    @Override
    public boolean isBonemealSuccess(Level level, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, Random random, BlockPos pos, BlockState state) {
        if (state.getValue(TYPE).ordinal() == 0) {
            if (random.nextInt(5) > 0) level.setBlock(pos, state.setValue(TYPE, Type.A_1), 3);
        } else if (state.getValue(TYPE).ordinal() == 1) {

            if (random.nextInt(5) > 0) this.growBamboo(level, pos, getRandomBambooHeight(level));
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
//        Cuisine.logger(defaultBlockState());
        return this.defaultBlockState();
    }

    public  static int getRandomBambooHeight(Level level) {
        return 7 + level.getRandom().nextInt(8);
    }

    public static boolean checkIfBambooCanGrow(Level level, BlockPos pos, int height) {
        pos=pos.above();
        for (int i = 0; i < height; i++) {
            if (!level.isEmptyBlock(pos))
                return false;
        }
        return true;
    }

    public static void growBamboo(Level level, BlockPos pos, int height) {
        if (!checkIfBambooCanGrow(level, pos, height)) return;
        int lowestBranchHeight =
                Mth.clamp(5 + level.getRandom().nextInt(4), 5, height);

        for (int i = 0; i < height; i++) {
//            Cuisine.logger(pos.above(i), lowestBranchHeight);
            BlockState de = BlockRegister.bamboo_plant.get().defaultBlockState().setValue(TYPE, Type.A_2);
            if (i >= lowestBranchHeight) {
                if (level.getRandom().nextBoolean() && level.isEmptyBlock(pos.above(i).south())) {
                    de = de.setValue(SOUTH, true);
                }
                if (level.getRandom().nextBoolean() && level.isEmptyBlock(pos.above(i).west())) {
                    de = de.setValue(WEST, true);
                }
                if (level.getRandom().nextBoolean() && level.isEmptyBlock(pos.above(i).north())) {
                    de = de.setValue(NORTH, true);
                }
                if (level.getRandom().nextBoolean() && level.isEmptyBlock(pos.above(i).east())) {
                    de = de.setValue(EAST, true);
                }
            }
            level.setBlock(pos.above(i), de, 3);

        }
    }

    @Override
    public void destroy(LevelAccessor p_49860_, BlockPos p_49861_, BlockState p_49862_) {
        super.destroy(p_49860_, p_49861_, p_49862_);
    }

    @Override
    public float getDestroyProgress(BlockState p_48901_, Player p_48902_, BlockGetter p_48903_, BlockPos p_48904_) {
        return p_48902_.getMainHandItem().canPerformAction(net.minecraftforge.common.ToolActions.AXE_DIG) ? 1.0F
                : super.getDestroyProgress(p_48901_, p_48902_, p_48903_, p_48904_);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        int ordinal = state.getValue(TYPE).ordinal();
        if (ordinal < 2) {
            return BlockRegister.bamboo_shoot.get().getDefaultInstance();
        } else if (ordinal < 7) {
            return BlockRegister.bamboo_item.get().getDefaultInstance();
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState p_60550_) {
        return RenderShape.MODEL;
    }

    public boolean isPathfindable(BlockState p_48906_, BlockGetter p_48907_, BlockPos p_48908_, PathComputationType p_48909_) {
        return false;
    }

    public enum Type implements StringRepresentable {
        // A for Age, B for Branch
        A_0, A_1, A_2, A_3, A_4, A_5, A_6, B_S, B_W, B_N, B_E;

        @Override
        public String getSerializedName() {
            return toString().toLowerCase(Locale.ROOT);
        }
    }
}
