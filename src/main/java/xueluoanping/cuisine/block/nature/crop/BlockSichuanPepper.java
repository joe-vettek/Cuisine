package xueluoanping.cuisine.block.nature.crop;

import net.minecraft.world.level.ItemLike;
import xueluoanping.cuisine.block.nature.BlockCuisineCrops;
import xueluoanping.cuisine.register.CropRegister;

public class BlockSichuanPepper extends BlockCuisineCrops {
    public BlockSichuanPepper(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return CropRegister.sichuan_pepper_item.get();
    }

}
