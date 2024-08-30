package xueluoanping.cuisine.block.nature.crop;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;
import xueluoanping.cuisine.block.nature.BlockCuisineCrops;
import xueluoanping.cuisine.register.CropRegister;

public class BlockLettuce extends BlockCuisineCrops {
    public BlockLettuce(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return CropRegister.lettuce_item.get();
    }




}
