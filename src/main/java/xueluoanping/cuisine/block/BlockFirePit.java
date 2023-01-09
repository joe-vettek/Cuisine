package xueluoanping.cuisine.block;


import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import xueluoanping.cuisine.event.type.EnumFirePitState;
import xueluoanping.cuisine.util.MathUtils;
import xueluoanping.cuisine.block.entity.FirePitBlockEntity;
import xueluoanping.cuisine.register.BlockEntityRegister;

import java.util.Random;


//环境光遮蔽可以靠这个解决

public class BlockFirePit extends HorizontalDirectionalBlock implements EntityBlock {
	//	public static final PropertyEnum<Component> COMPONENT = PropertyEnum.create("component", Component.class);
	public static final EnumProperty<EnumFirePitState> COMPONENT = EnumProperty.create("component", EnumFirePitState.class);
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final IntegerProperty FUEL = IntegerProperty.create("fuel", 1, 5);
	protected static final VoxelShape AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
	protected static final VoxelShape AABB_WITH_WOK = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.6D, 16.0D);
	//	protected static final VoxelShape AABB_WITH_WOK_HANDLE = Block.box(-9.1472D, 6.88D, 6.8336D, 0.8D, 9.0224D, 8.829D);
	protected static final VoxelShape AABB_WITH_WOK_HANDLE = Block.box(-9.15D, 6.88D, 6.83D, 0.8D, 9.02D, 8.83D);
	protected static final VoxelShape AABB_STICKS = Block.box(0.0D, 0.0D, 6.4D, 16.0D, 16.0D, 9.6D);

	public BlockFirePit(Properties builder) {
		super(builder.destroyTime(5).noOcclusion().requiresCorrectToolForDrops());
	}


	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
		super.createBlockStateDefinition(p_49915_.add(COMPONENT).add(FACING).add(FUEL));
	}

	@Override
	public boolean isRandomlyTicking(BlockState p_49921_) {
		return super.isRandomlyTicking(p_49921_);
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if (state.getValue(COMPONENT) == EnumFirePitState.NONE
				&& player.getItemInHand(hand).getItem() == Items.STICK) {
			state = state.setValue(COMPONENT, EnumFirePitState.STICKS);
			worldIn.setBlock(pos, state, 0);
			return InteractionResult.SUCCESS;
		}
		if (state.getValue(FUEL) < 5) {
			int burnTime = -1;
			try {
				burnTime = ForgeHooks.getBurnTime(player.getItemInHand(hand), null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (burnTime > 0) {
				burnTime=burnTime/20/8;
				burnTime=Mth.clamp(burnTime,0,5-state.getValue(FUEL));
				worldIn.setBlock(pos, state.setValue(FUEL, 1+burnTime), 0);
			}

			return InteractionResult.SUCCESS;
		}

		return InteractionResult.PASS;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext collisionContext) {
		if (state.getValue(COMPONENT) == EnumFirePitState.WOK) {
			return Shapes.or(AABB_WITH_WOK,
					MathUtils.getShapefromDirection(15.2D, 6.88D, 7.17D, 25.15D, 9.02D, 9.17D, state.getValue(FACING), true));
		}
		if (state.getValue(COMPONENT) == EnumFirePitState.STICKS) {
			return Shapes.or(AABB,
					MathUtils.getShapefromDirection(6.8D, 0.0D, 0.D, 10.0D, 16.0D, 16.0D, state.getValue(FACING), true));
		}
		return AABB;
	}

	public static Logger logger = LogManager.getLogger();



	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		if (!context.getItemInHand().hasTag())
			return this.defaultBlockState().setValue(COMPONENT, EnumFirePitState.NONE).setValue(FACING, context.getHorizontalDirection().getOpposite());
		switch (EnumFirePitState.matchWithoutError(context.getItemInHand())) {
			case WOK:
				return this.defaultBlockState().setValue(COMPONENT, EnumFirePitState.WOK).setValue(FACING, context.getHorizontalDirection().getOpposite());
			case STICKS:
				return this.defaultBlockState().setValue(COMPONENT, EnumFirePitState.STICKS).setValue(FACING, context.getHorizontalDirection().getOpposite());
			case FRYING_PAN:
				return this.defaultBlockState().setValue(COMPONENT, EnumFirePitState.FRYING_PAN).setValue(FACING, context.getHorizontalDirection().getOpposite());
			default:
				return this.defaultBlockState().setValue(COMPONENT, EnumFirePitState.NONE).setValue(FACING, context.getHorizontalDirection().getOpposite());
		}
	}


	//环境光遮蔽
	@Override
	public float getShadeBrightness(BlockState p_60472_, BlockGetter p_60473_, BlockPos p_60474_) {
		return super.getShadeBrightness(p_60472_, p_60473_, p_60474_);
	}

	public boolean hasComponent(BlockState state, EnumFirePitState component) {
		return state.getValue(COMPONENT) == component;
	}

	//客户端随机刻渲染
	@Override
	public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, Random rand) {
		if (worldIn.isClientSide()) {
			int heatLevel = 1;
//			if (stateIn.getValue(COMPONENT) != FirePitState.NONE)
//			{
//				TileEntity tileEntity = worldIn.getTileEntity(pos);
//				if (tileEntity instanceof TileFirePit)
//				{
//					heatLevel = ((TileFirePit) tileEntity).heatHandler.getLevel();
//				}
//			}

			try {
				heatLevel = stateIn.getValue(FUEL);
			} catch (Exception ex) {
			}
			if (heatLevel > 0 && rand.nextInt(15 - heatLevel * 3+1) == 0) {

				worldIn.playLocalSound(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS, 0.7F + 0.15F * heatLevel + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);

			}

			for (int i = 0; i < heatLevel; i++) {
				float f = (float) (rand.nextFloat() * Math.PI * 2);
				double x = Mth.sin(f) * 0.1D;
				double y = pos.getY() + 0.12D + rand.nextDouble() * 0.05D;
				double z = Mth.cos(f) * 0.1D;
				if (!hasComponent(stateIn, EnumFirePitState.WOK)) {
					if (heatLevel > 1) {
						worldIn.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5D + x, y, pos.getZ() + 0.5D + z, x * 0.2, 0.01 * heatLevel, z * 0.2);
					} else {
						worldIn.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5D + x, y, pos.getZ() + 0.5D + z, 0D, 0D, 0D);
					}
				} else {
					if (heatLevel > 1) {
						worldIn.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5D + x, y, pos.getZ() + 0.5D + z, x * 0.2 * (heatLevel - 0.5), 0.005, z * 0.2 * (heatLevel - 1));
					}
					if (rand.nextInt(5) == 0) {
						worldIn.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5D + x * rand.nextDouble() * 0.5D, y + 0.2D, pos.getZ() + 0.5D + z * rand.nextDouble() * 0.5D, 0, 0, 0);
					}
				}
			}
		}

	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	public boolean hasBlockEntity(BlockState state) {
		return true;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new FirePitBlockEntity(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
		return EntityBlock.super.getTicker(p_153212_, p_153213_, p_153214_);
	}

	@Override
	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {
		Item item = BlockEntityRegister.fire_pit_item.get();
		items.add(item.getDefaultInstance());

		ItemStack stack = item.getDefaultInstance();
		stack.getOrCreateTag().putString("Component", EnumFirePitState.WOK.getSerializedName());
		items.add(stack);

		stack = item.getDefaultInstance();
		stack.getOrCreateTag().putString("Component", EnumFirePitState.STICKS.getSerializedName());
		items.add(stack);

		stack = item.getDefaultInstance();
		stack.getOrCreateTag().putString("Component", EnumFirePitState.FRYING_PAN.getSerializedName());
		items.add(stack);
	}
}
