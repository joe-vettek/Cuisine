package xueluoanping.cuisine.block.nature.crop;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import xueluoanping.cuisine.block.nature.BlockCuisineCrops;
import xueluoanping.cuisine.register.CropRegister;

public class BlockChili extends BlockCuisineCrops {
    public BlockChili(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return CropRegister.chili_item.get();
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter source, BlockPos pos) {
        return state.is(BlockTags.SOUL_FIRE_BASE_BLOCKS);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader source, BlockPos pos) {
        return source.getBlockState(pos.below()).is(BlockTags.SOUL_FIRE_BASE_BLOCKS);
    }


}
