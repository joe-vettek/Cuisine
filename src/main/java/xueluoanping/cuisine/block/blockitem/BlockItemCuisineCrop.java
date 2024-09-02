package xueluoanping.cuisine.block.blockitem;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.SpecialPlantable;
import org.jetbrains.annotations.Nullable;
import xueluoanping.cuisine.items.ModItem;
import xueluoanping.cuisine.register.CropRegister;

public class BlockItemCuisineCrop extends BlockItem implements SpecialPlantable {
    public BlockItemCuisineCrop(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public boolean canPlacePlantAtPosition(ItemStack itemStack, LevelReader level, BlockPos pos, @Nullable Direction direction) {
        return itemStack.is(this) && getBlock().defaultBlockState().canSurvive(level, pos);
    }

    @Override
    public void spawnPlantAtPosition(ItemStack itemStack, LevelAccessor level, BlockPos pos, @Nullable Direction direction) {
        if (itemStack.is(this))
            level.setBlock(pos, getBlock().defaultBlockState(), Block.UPDATE_CLIENTS);
    }

    @Override
    public boolean villagerCanPlantItem(Villager villager) {
        return true;
    }
}
