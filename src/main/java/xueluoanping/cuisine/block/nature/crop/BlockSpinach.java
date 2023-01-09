package xueluoanping.cuisine.block.nature.crop;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;
import xueluoanping.cuisine.block.nature.BlockCuisineCrops;
import xueluoanping.cuisine.register.CropRegister;

public class BlockSpinach extends BlockCuisineCrops {
    public BlockSpinach(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return CropRegister.spinach_item.get();
    }


    @Override
    public BlockState getPlant(BlockGetter world, BlockPos pos) {
        return CropRegister.spinach.get().defaultBlockState();
    }


}
