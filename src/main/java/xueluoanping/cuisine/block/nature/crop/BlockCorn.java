package xueluoanping.cuisine.block.nature.crop;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.PlantType;
import xueluoanping.cuisine.block.nature.BlockCuisineCrops;
import xueluoanping.cuisine.block.nature.BlockDoubleCrops;
import xueluoanping.cuisine.register.CropRegister;

public class BlockCorn extends BlockDoubleCrops {

    protected static final VoxelShape UPPER_NONE =
            Block.box(0.0D, 0.0D, 0.0D, 0.0D,0.0D,0.0D);


    public BlockCorn(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return CropRegister.corn_item.get();
    }


    @Override
    public BlockState getPlant(BlockGetter world, BlockPos pos) {
        return CropRegister.corn.get().defaultBlockState();
    }


    public VoxelShape getShape(BlockState state, BlockGetter source, BlockPos pos, CollisionContext context) {
        if (isUpper(state) && getAge(state) < 2)
            return UPPER_NONE;
        return super.getShape(state, source, pos, context);

    }
    // @Override
    // protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
    //     return super.mayPlaceOn(state, worldIn, pos)
    //             // &&worldIn.getBlockState(pos.above().east()).is(this)
    //             // &&worldIn.getBlockState(pos.above().west()).is(this)
    //             // &&worldIn.getBlockState(pos.above().north()).is(this)
    //             // &&worldIn.getBlockState(pos.above().south()).is(this)
    //             ;
    // }
}
