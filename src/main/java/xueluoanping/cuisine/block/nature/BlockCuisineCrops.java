package xueluoanping.cuisine.block.nature;


import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;
import xueluoanping.cuisine.Cuisine;

import javax.annotation.Nullable;
import java.util.Random;

//  借鉴红菜根BeetrootBlock
public class BlockCuisineCrops extends CropBlock {


    public static final int MAX_AGE = 7;
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};

    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;
    public BlockCuisineCrops(Properties properties) {
        super(properties);
    }

    // @Override
    // protected ItemLike getBaseSeedId() {
    //     return super.getBaseSeedId();
    // }
    //
    // public IntegerProperty getAgeProperty() {
    //     return AGE;
    // }
    //
    // public int getMaxAge() {
    //     return MAX_AGE;
    // }
    //
    //
    // public void randomTick(BlockState p_49667_, ServerLevel p_49668_, BlockPos p_49669_, Random p_49670_) {
    //     if (p_49670_.nextInt(3) != 0) {
    //         super.randomTick(p_49667_, p_49668_, p_49669_, p_49670_);
    //     }
    //
    // }
    //
    // protected int getBonemealAgeIncrease(Level p_49663_) {
    //     return super.getBonemealAgeIncrease(p_49663_) / 3;
    // }
    //
    // protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49665_) {
    //     p_49665_.add(AGE);
    // }
    //
    public VoxelShape getShape(BlockState p_49672_, BlockGetter p_49673_, BlockPos p_49674_, CollisionContext p_49675_) {
       int index=p_49672_.getValue(this.getAgeProperty())/2;
       if(p_49672_.getValue(this.getAgeProperty())==6)index=2;
        return SHAPE_BY_AGE[index];
    }
}
