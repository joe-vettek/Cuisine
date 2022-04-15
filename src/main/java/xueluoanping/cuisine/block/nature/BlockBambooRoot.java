package xueluoanping.cuisine.block.nature;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.Random;

public class BlockBambooRoot extends Block {
    public static final IntegerProperty AGE_5 = BlockStateProperties.AGE_5;
    public BlockBambooRoot(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(AGE_5,5));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        super.createBlockStateDefinition(p_49915_.add(AGE_5));
    }

    @Override
    public boolean isRandomlyTicking(BlockState p_49921_) {
        return true;
    }

    @Override
    public void randomTick(BlockState p_60551_, ServerLevel p_60552_, BlockPos p_60553_, Random p_60554_) {
        super.randomTick(p_60551_, p_60552_, p_60553_, p_60554_);
    }
}
