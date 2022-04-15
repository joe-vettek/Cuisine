package xueluoanping.cuisine.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import xueluoanping.cuisine.block.entity.MillBlockEntity;
import xueluoanping.cuisine.register.BlockEntityRegister;

public class BlockMill extends HorizontalDirectionalBlock implements EntityBlock {
    protected static final VoxelShape BOTTOM_AABB = Block.box(2D, 0.0D, 2D, 14.0D, 8.4D, 14.0D);

    public BlockMill(Properties properties) {
        super(properties);
    }


    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (worldIn.getBlockEntity(pos) instanceof MillBlockEntity tile) {
            if (player.getItemInHand(handIn).isEmpty()) {
                tile.addPower(30);
               if(!worldIn.isClientSide())
               {
                   player.causeFoodExhaustion(1);
               }
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return BOTTOM_AABB;
    }

    @Override
    public RenderShape getRenderShape(BlockState p_60550_) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    //	it works when the blcok is placed
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker
    (Level worldIn, BlockState blockState, BlockEntityType<T> entityType) {
        return !worldIn.isClientSide ?
                createTickerHelper(entityType,
                        BlockEntityRegister.mill_entity_type.get(),
                        MillBlockEntity::tickEntity) : null;
    }

    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A>
    createTickerHelper(BlockEntityType<A> entityType,
                       BlockEntityType<E> entityType1,
                       BlockEntityTicker<? super E> ticker) {
        return entityType1 == entityType ? (BlockEntityTicker<A>) ticker : null;
    }



    public boolean hasBlockEntity(BlockState state) {
        return true;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState state) {
        if (hasBlockEntity(state)) {
            MillBlockEntity basinBlockEntity = new MillBlockEntity(pPos, state);
            return basinBlockEntity;
        }
        return null;
    }
}
