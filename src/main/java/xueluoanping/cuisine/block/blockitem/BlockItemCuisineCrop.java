package xueluoanping.cuisine.block.blockitem;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import xueluoanping.cuisine.items.ModItem;
import xueluoanping.cuisine.register.CropRegister;

public class BlockItemCuisineCrop extends BlockItem implements IPlantable {
    public BlockItemCuisineCrop(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public PlantType getPlantType(BlockGetter level, BlockPos pos) {
        if(this== CropRegister.chili_item.get())
            return PlantType.NETHER;
        if(this== CropRegister.rice_item.get())
            return PlantType.NETHER;
        else return PlantType.CROP;
        // return IPlantable.super.getPlantType(level, pos);
    }

    @Override
    public BlockState getPlant(BlockGetter level, BlockPos pos) {
        return getBlock().defaultBlockState();
    }
}
