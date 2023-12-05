package xueluoanping.cuisine.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import net.minecraftforge.fluids.capability.IFluidHandler;

import net.minecraftforge.items.CapabilityItemHandler;

import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.block.entity.MillBlockEntity;
import xueluoanping.cuisine.register.BlockEntityRegister;

public class BlockMill extends HorizontalDirectionalBlock implements EntityBlock {
	protected static final VoxelShape BOTTOM_AABB = Block.box(2D, 0.0D, 2D, 14.0D, 8.4D, 14.0D);

	public BlockMill(Properties properties) {
		super(properties);
	}


	//	逻辑是左手取出产物(手上有东西就掉落，没东西就给玩家)
	//	右手放入产物（判断是否可以放入），空右手时推动磨一下
	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		if (worldIn.getBlockEntity(pos) instanceof MillBlockEntity tile) {

			// Cuisine.logger(player.isShiftKeyDown(), handIn, player);
			 {
					if (player.getItemInHand(handIn).isEmpty()) {
						tile.addPower(30);

						if (!worldIn.isClientSide()) {
							player.causeFoodExhaustion(1);
						}
						return InteractionResult.SUCCESS;
					} else {
						tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).ifPresent(handler -> {

							if (player.isShiftKeyDown()) {
								if (handler.insertItem(0, player.getItemInHand(handIn), true).isEmpty())
									handler.insertItem(0, player.getItemInHand(handIn), false);

							} else {
								ItemStack itemStack = player.getItemInHand(handIn).copy();
								itemStack.setCount(1);
								//								// insertItem返回的是剩余部分
								if (handler.insertItem(0, itemStack, true).isEmpty()) {
									handler.insertItem(0, itemStack, false);
									player.getItemInHand(handIn).shrink(1);

								}
							}
						});
						return InteractionResult.SUCCESS;
					}
				}

		}
		return super.use(state, worldIn, pos, player, handIn, hit);
	}

	@Override
	public void attack(@NotNull BlockState state, @NotNull Level worldIn, @NotNull BlockPos pos, @NotNull Player player) {
		if (worldIn.getBlockEntity(pos) instanceof MillBlockEntity tile) {
			{
				BlockHitResult hit = rayTraceEyes(worldIn, player, pos);
				if (hit.getType() != HitResult.Type.BLOCK)
					return;
				if (!hit.getBlockPos().equals(pos))
					return;

				if (worldIn.getBlockState(pos) != state)
					return;

				tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.DOWN).ifPresent(handler -> {
					if (handler.getStackInSlot(0).getCount() > 0) {
						int count = handler.getStackInSlot(0).getCount();
						if (!player.isShiftKeyDown()) {
							count = 1;
						}
						ItemStack itemStack = handler.extractItem(0, handler.getStackInSlot(0).getCount(), false);
						if (!player.getInventory().add(itemStack)) {
							dropItemStack(worldIn, pos.relative(hit.getDirection()), player, itemStack);
							worldIn.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
						}
					}
				});

			}

			super.attack(state, worldIn, pos, player);

		}
	}

	public static BlockHitResult rayTraceEyes(@NotNull Level level, @NotNull Player player, @NotNull BlockPos blockPos) {
		Vec3 eyePos = player.getEyePosition(1);
		Vec3 lookVector = player.getViewVector(1);
		Vec3 endPos = eyePos.add(lookVector.scale(eyePos.distanceTo(Vec3.atCenterOf(blockPos)) + 1));
		ClipContext context = new ClipContext(eyePos, endPos, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player);
		return level.clip(context);
	}

	private void dropItemStack(@NotNull Level world, @NotNull BlockPos pos, @NotNull Player
			player, @NotNull ItemStack stack) {
		ItemEntity entity = new ItemEntity(world, pos.getX() + .5f, pos.getY() + .3f, pos.getZ() + .5f, stack);
		Vec3 motion = entity.getDeltaMovement();
		entity.push(-motion.x, -motion.y, -motion.z);
		world.addFreshEntity(entity);
	}

	@Override
	public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext
			p_60558_) {
		return BOTTOM_AABB;
	}

	@Override
	public RenderShape getRenderShape(BlockState p_60550_) {
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}

	//	it works when the blcok is placed
	@Nullable
	@Override
	public <T extends
			BlockEntity> BlockEntityTicker<T> getTicker(Level worldIn, BlockState blockState, BlockEntityType<T> entityType) {
		return !worldIn.isClientSide ?
				createTickerHelper(entityType, BlockEntityRegister.mill_entity_type.get(), MillBlockEntity::tickEntity)
				: null;
	}

	@Nullable
	protected static <E extends BlockEntity, A extends
			BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> entityType, BlockEntityType<E> entityType1, BlockEntityTicker<? super E> ticker) {
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
