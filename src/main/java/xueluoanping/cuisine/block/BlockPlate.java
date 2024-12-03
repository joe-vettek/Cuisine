package xueluoanping.cuisine.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class BlockPlate extends Block {

    public enum CuisineType implements StringRepresentable {
        plate, fish0, rice0, meat0, meat1, veges0, veges1, mixed0, mixed1;

        @Override
        public @NotNull String getSerializedName() {
            return this.toString().toLowerCase();
        }
    }
    public static final VoxelShape BOARD = Block.box(2D, 0D, 2D, 14D, 2.5D, 14D);

    public static final EnumProperty<CuisineType> PLATE_TYPE = EnumProperty.create("plate_type", CuisineType.class);

    public BlockPlate(Properties p_49795_) {
        super(p_49795_);
        registerDefaultState(defaultBlockState().setValue(PLATE_TYPE,CuisineType.plate));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(PLATE_TYPE));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return BOARD;
    }
}
