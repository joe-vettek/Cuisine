package xueluoanping.cuisine.block.firepit;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;

import xueluoanping.cuisine.block.baseblock.SimpleHorizontalEntityBlock;
import xueluoanping.cuisine.blockentity.firepit.BarbecueRackBlockEntity;
import xueluoanping.cuisine.blockentity.firepit.WokOnFirePitbBlockEntity;
import xueluoanping.cuisine.register.BlockEntityRegister;
import xueluoanping.cuisine.util.MathUtils;

public class BlockFirePitWithWok extends BlockFirePit {
    protected static final VoxelShape AABB_WITH_WOK = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.6D, 16.0D);

    public BlockFirePitWithWok(Properties properties) {
        super(properties);
    }


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext collisionContext) {
        return Shapes.or(AABB_WITH_WOK,
                MathUtils.getShapefromDirection(15.2D, 6.88D, 7.17D, 25.15D, 9.02D, 9.17D, state.getValue(FACING).getOpposite().getClockWise(), true));
    }


    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            var result = super.useItemOn(stack, state, level, pos, player, handIn, hitResult);

            if (!result.consumesAction() && !stack.isEmpty() && level.getBlockEntity(pos) instanceof WokOnFirePitbBlockEntity wokOnFirePitbBlockEntity) {
                var iFluidHandlerItem = stack.getCapability(Capabilities.FluidHandler.ITEM);
                if (iFluidHandlerItem != null) {
                    var fluid = iFluidHandlerItem.drain(100, IFluidHandler.FluidAction.SIMULATE);
                    var fluid2Amount = wokOnFirePitbBlockEntity.getSeasoningLiquids().fill(fluid, IFluidHandler.FluidAction.SIMULATE);

                    if (fluid.getAmount() == 100&& fluid2Amount==100) {
                        wokOnFirePitbBlockEntity.getSeasoningLiquids()
                                .fill(iFluidHandlerItem.drain(100, IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
                        return ItemInteractionResult.SUCCESS;
                    }

                    return result;
                }

                var resultItem = ItemHandlerHelper.insertItem(wokOnFirePitbBlockEntity.getInventory(), stack.copyWithCount(1), false);
                if (resultItem.isEmpty())
                    stack.shrink(1);
                return ItemInteractionResult.SUCCESS;
            }
            return result;
        }
        return !stack.isEmpty() ? ItemInteractionResult.CONSUME : ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return BlockEntityRegister.wok_on_fire_pit_entity_type.get().create(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level worldIn, BlockState blockState, BlockEntityType<T> entityType) {
        return !worldIn.isClientSide ?
                createTickerHelper(entityType, BlockEntityRegister.wok_on_fire_pit_entity_type.get(), WokOnFirePitbBlockEntity::tickEntity)
                : null;
    }


}
