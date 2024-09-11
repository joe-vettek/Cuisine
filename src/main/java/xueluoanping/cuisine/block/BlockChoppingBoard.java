package xueluoanping.cuisine.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.blockentity.ChoppingBoardBlockEntity;

import java.util.Optional;

public class BlockChoppingBoard extends Block implements EntityBlock {
    public static final VoxelShape BOARD = Block.box(2D, 0D, 2D, 14D, 4D, 14D);

    public BlockChoppingBoard(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (!pStack.isEmpty()) {
            if (pLevel.getCapability(Capabilities.ItemHandler.BLOCK, pPos, Direction.UP) instanceof IItemHandler iItemHandler) {
                // var item = ItemHandlerHelper.insertItemStacked(iItemHandler, pStack.copy(), false);
                var item = iItemHandler.insertItem(0, pStack.copy(), false);
                if (item.getCount() != pStack.getCount()) {
                    pStack.shrink(1);
                    return ItemInteractionResult.sidedSuccess(pLevel.isClientSide());
                }
            }

        }
        return pLevel.isClientSide()?ItemInteractionResult.SUCCESS:
                ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if (!pLevel.isClientSide()
                && pLevel.getCapability(Capabilities.ItemHandler.BLOCK, pPos, Direction.UP) instanceof IItemHandler iItemHandler) {
            if (iItemHandler.getSlots() > 0
                    && !iItemHandler.getStackInSlot(0).isEmpty()) {
                ItemHandlerHelper.giveItemToPlayer(pPlayer, iItemHandler.extractItem(0, 1, false), pPlayer.getInventory().selected);
                return InteractionResult.SUCCESS;
            }
        }
        return super.useWithoutItem(pState, pLevel, pPos, pPlayer, pHitResult);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState state) {
        return new ChoppingBoardBlockEntity(pPos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_60550_) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public boolean isCollisionShapeFullBlock(BlockState p_181242_, BlockGetter p_181243_, BlockPos p_181244_) {
        return false;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return BOARD;
    }
}
