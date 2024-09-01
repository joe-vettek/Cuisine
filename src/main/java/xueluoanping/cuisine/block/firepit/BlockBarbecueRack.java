package xueluoanping.cuisine.block.firepit;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;

import xueluoanping.cuisine.block.baseblock.SimpleHorizontalEntityBlock;
import xueluoanping.cuisine.blockentity.firepit.BarbecueRackBlockEntity;
import xueluoanping.cuisine.register.BlockEntityRegister;
import xueluoanping.cuisine.util.MathUtils;

import java.util.Optional;

public class BlockBarbecueRack extends BlockFirePit {
    public BlockBarbecueRack(Properties properties) {
        super(properties);
    }
    // @Override
    // protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    // 	super.createBlockStateDefinition(builder.add(FACING));
    // }


    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            var result = super.useItemOn(stack, state, level, pos, player, handIn, hitResult);

            if (!result.consumesAction() && !stack.isEmpty()) {
                var resultItem = ItemHandlerHelper.insertItem(level.getCapability(Capabilities.ItemHandler.BLOCK, pos, null), stack.copyWithCount(1), false);
                if (resultItem.isEmpty())
                    stack.shrink(1);
                return ItemInteractionResult.SUCCESS;
            }
            return result;
        }
        return !stack.isEmpty() ? ItemInteractionResult.CONSUME : ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {

        Optional.ofNullable(pLevel.getCapability(Capabilities.ItemHandler.BLOCK, pPos, null)).ifPresent(
                iItemHandler -> {
                    for (int i = 0; i < iItemHandler.getSlots(); i++) {
                        if (!iItemHandler.getStackInSlot(i).isEmpty()) {
                            // ItemHandlerHelper.giveItemToPlayer(pPlayer, iItemHandler.getStackInSlot(i));
                            popResource(pLevel, pPos, iItemHandler.getStackInSlot(i));
                            ((IItemHandlerModifiable) iItemHandler).setStackInSlot(i, ItemStack.EMPTY);
                            break;
                        }
                    }
                }
        );
        return super.useWithoutItem(pState, pLevel, pPos, pPlayer, pHitResult);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext collisionContext) {
        return Shapes.or(BlockFirePit.AABB,
                MathUtils.getShapefromDirection(6.8D, 0.0D, 0.D, 10.0D, 14.0D, 16.0D, state.getValue(FACING), true));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return BlockEntityRegister.barbecue_rack_entity_type.get().create(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level worldIn, BlockState blockState, BlockEntityType<T> entityType) {
        return !worldIn.isClientSide ?
                createTickerHelper(entityType, BlockEntityRegister.barbecue_rack_entity_type.get(), BarbecueRackBlockEntity::tickEntity)
                : null;
    }
}
