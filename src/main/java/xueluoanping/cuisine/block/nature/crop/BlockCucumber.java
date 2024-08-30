package xueluoanping.cuisine.block.nature.crop;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;
import xueluoanping.cuisine.block.nature.BlockDoubleCrops;
import xueluoanping.cuisine.register.CropRegister;

public class BlockCucumber extends BlockDoubleCrops {
    public BlockCucumber(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return CropRegister.cucumber_item.get();
    }




}
