package xueluoanping.cuisine.block.blockitem;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import xueluoanping.cuisine.block.BlockDrinkro;

public class BlockItemDrinkro extends DoubleHighBlockItem {
    public BlockItemDrinkro(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext pContext, BlockState pState) {
        pContext.getLevel().setBlock(pContext.getClickedPos().above(), pState.setValue(BlockDrinkro.HALF, DoubleBlockHalf.UPPER), Block.UPDATE_CLIENTS);
        return pContext.getLevel().setBlock(pContext.getClickedPos(), pState, Block.UPDATE_ALL_IMMEDIATE);
    }
}
