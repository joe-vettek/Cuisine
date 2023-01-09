package xueluoanping.cuisine.block.nature.crop;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.PlantType;
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


    @Override
    public BlockState getPlant(BlockGetter world, BlockPos pos) {
        return CropRegister.sichuan_pepper.get().defaultBlockState();
    }


}
