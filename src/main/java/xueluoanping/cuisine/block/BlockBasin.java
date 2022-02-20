package xueluoanping.cuisine.block;

import java.util.List;
import java.util.Random;


import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import snownee.kiwi.block.ModBlock;
import snownee.kiwi.recipe.FullBlockIngredient;
import snownee.kiwi.util.NBTHelper;
import xueluoanping.cuisine.CoreModule;
import xueluoanping.cuisine.api.fluid.FluidTransferUtil;
import xueluoanping.cuisine.api.util.NBTUtils;
import xueluoanping.cuisine.api.util.TextUtils;
import xueluoanping.cuisine.block.tile.BasinBlockEntity;

import static xueluoanping.cuisine.CoreModule.Basin;
import static xueluoanping.cuisine.api.util.DebugUtils.sendPlayerMessageSingle;

//@KiwiModule.RenderLayer(KiwiModule.RenderLayer.Layer.CUTOUT_MIPPED)用于树叶
//@KiwiModule.RenderLayer(KiwiModule.RenderLayer.Layer.TRANSLUCENT)用于玻璃
public class BlockBasin extends ModBlock implements EntityBlock {
	protected static final VoxelShape BOTTOM_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
	protected static final VoxelShape TOP_AABB = Block.box(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	public static final IntegerProperty POWER = BlockStateProperties.POWER;
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

	private final String[] textureName = new String[2];
//	public static final BooleanProperty INVERTED = BlockStateProperties.INVERTED;

	public BlockBasin(Properties builder) {
		super(builder.noOcclusion());
	}


	@Override
	public void fallOn(Level p_152426_, BlockState p_152427_, BlockPos p_152428_, Entity p_152429_, float p_152430_) {
//		p_152426_.getNearestPlayer(p_152429_, 10.0).sendMessage(new TextComponent(p_152429_.getName().getString()), p_152426_.getNearestPlayer(p_152429_, 10.0).getUUID());
		if (p_152426_.isClientSide) {
			assert Minecraft.getInstance().player != null;
			Minecraft.getInstance().player.sendMessage(
					new TextComponent(p_152429_.getName().getString()),
					Minecraft.getInstance().player.getUUID());
		}
		super.fallOn(p_152426_, p_152427_, p_152428_, p_152429_, p_152430_);
	}

//	@Override
//    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance)
//    {
//        entityIn.fall(fallDistance, 0.5F);
//
//        TileEntity tile = worldIn.getTileEntity(pos);
//        if (tile instanceof TileBasin)
//        {
//            TileBasin tileBasin = ((TileBasin) tile);
//            if (fallDistance >= 1 && entityIn instanceof EntityLivingBase)
//            {
//                ItemStack input = tileBasin.stacks.getStackInSlot(0);
//                if (!input.isEmpty() || tileBasin.tank.getFluidAmount() > 0)
//                {
//                    worldIn.playSound(null, pos, SoundEvents.BLOCK_SLIME_STEP, SoundCategory.BLOCKS, 0.5F, worldIn.rand.nextFloat() / 4 + .6F);
//                }
//                if (input.isEmpty())
//                {
//                    return;
//                }
//                if (input.getItem() == Item.getItemFromBlock(Blocks.CACTUS))
//                {
//                    entityIn.attackEntityFrom(DamageSource.CACTUS, 1);
//                }
//                else if (input.getItem() == CuisineRegistry.BASIC_FOOD && input.getMetadata() == ItemBasicFood.Variant.EMPOWERED_CITRON.getMeta() && entityIn instanceof EntityPlayer && tileBasin.tank.getFluidAmount() == 0)
//                {
//                    ItemBasicFood.citronSays((EntityLivingBase) entityIn, "squeeze");
//                }
//                tileBasin.process(Processing.SQUEEZING, input, false);
//                if (entityIn instanceof EntityIronGolem)
//                {
//                    tileBasin.process(Processing.SQUEEZING, input, false);
//                }
//            }
//        }
//    }

	@Override
	public void updateEntityAfterFallOn(BlockGetter p_49821_, Entity p_49822_) {
		super.updateEntityAfterFallOn(p_49821_, p_49822_);
	}


//	@Override
//	public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
//		if (worldIn.isRemote) {
//			return;
//		}
//		TileEntity tile = worldIn.getTileEntity(pos);
//		if (tile instanceof TileBasin) {
//			TileBasin tileBasin = ((TileBasin) tile);
//			if (entityIn.getClass() == EntityItem.class) {
//				if (tileBasin.tickCheckThrowing > 0) {
//					tileBasin.tickCheckThrowing--;
//					return;
//				}
//				List<ItemStack> items = worldIn.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos)).stream().filter(e -> !e.isDead && e.onGround).map(EntityItem::getItem).collect(Collectors.toList());
//				for (ItemStack stack : items) {
//					tileBasin.process(Processing.BASIN_THROWING, stack, false);
//				}
//				tileBasin.tickCheckThrowing = 25;
//			}
//		}
//	}

	public boolean hasBlockEntity(BlockState state) {
		return true;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState state) {
		if (hasBlockEntity(state)) {
			BasinBlockEntity basinBlockEntity = new BasinBlockEntity(pPos, state);

//			basinBlockEntity.setTextureName();

			return basinBlockEntity;
		}
		return null;
	}


	@Override
	public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
		if (world.getBlockEntity(pos) != null &&
				world.getBlockEntity(pos) instanceof BasinBlockEntity entity
		) {
			if (entity.getTextureName() != null)

				return NBTUtils.getBlockFromRegister(entity.getTextureName())
						.getLightEmission(NBTUtils.getBlockFromRegister
								(entity.getTextureName()).defaultBlockState(), world,pos);
		}
		return super.getLightEmission(state, world, pos);
	}

	//	@Override
//	public TileEntity createTileEntity(World world, IBlockState state) {
//		return state.getMaterial() == Material.WOOD ? new TileBasin() : new TileBasinHeatable();
//	}

	@Override
	public void destroy(LevelAccessor p_49860_, BlockPos p_49861_, BlockState p_49862_) {
		super.destroy(p_49860_, p_49861_, p_49862_);
	}


	//before
	@javax.annotation.Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {

//		sendPlayerMessageSingle(context.getLevel()
//				, new TextComponent(context.getItemInHand().getTag().toString()));
		BlockEntity tileEntity = context.getLevel().getBlockEntity(context.getClickedPos());
//		if (tileEntity instanceof BasinBlockEntity) {
//			((BasinBlockEntity) tileEntity)
//					.setTextureName(NBTUtils
//							.getNameFromRetxtureTag
//									((CompoundTag) context.getItemInHand().getTag().get("BlockEntityTag")).split(":"));
//					sendPlayerMessageSingle(context.getLevel()
//				, new TextComponent("注册纹理名称成功"));
//		}
		return this.defaultBlockState();
	}

	//after
	public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @javax.annotation.Nullable LivingEntity entity, ItemStack stack) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		if (tileEntity instanceof BasinBlockEntity) {
((BasinBlockEntity) tileEntity).setTextureName(NBTUtils.getNameFromRetxtureTag((CompoundTag) stack.getTag().get("BlockEntityTag")).split(":"));
		}
	}
//	@Override
//	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
//		TileEntity te = worldIn.getTileEntity(pos);
//
//		if (te instanceof TileBasin) {
//			StacksUtil.dropInventoryItems(worldIn, pos, ((TileBasin) te).stacks, true);
//			((TileBasin) te).spillFluids();
//		}
//
//		super.breakBlock(worldIn, pos, state);
//	}

	protected static boolean shouldHandlePrecipitation(Level worldIn, Biome.Precipitation precipitation) {
		if (precipitation == Biome.Precipitation.RAIN) {
			return worldIn.getRandom().nextFloat() < 0.05F;
		} else if (precipitation == Biome.Precipitation.SNOW) {
			return worldIn.getRandom().nextFloat() < 0.1F;
		} else {
			return false;
		}
	}

	public void handlePrecipitation(BlockState state, Level worldIn, BlockPos pos, Biome.Precipitation precipitation) {
		if (shouldHandlePrecipitation(worldIn, precipitation)) {
			if (precipitation == Biome.Precipitation.RAIN) {
				worldIn.setBlockAndUpdate(pos, Blocks.WATER_CAULDRON.defaultBlockState());
				worldIn.gameEvent((Entity) null, GameEvent.FLUID_PLACE, pos);
			} else if (precipitation == Biome.Precipitation.SNOW) {
				worldIn.setBlockAndUpdate(pos, Blocks.POWDER_SNOW_CAULDRON.defaultBlockState());
				worldIn.gameEvent((Entity) null, GameEvent.FLUID_PLACE, pos);
			}

		}
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
//		Minecraft.getInstance().player.sendMessage(
//				new TextComponent(p_60503_.getBlock().getName().getString()+"hhh"),
//				Minecraft.getInstance().player.getUUID());

		sendPlayerMessageSingle(worldIn, new TextComponent("玩家手持的是" +
				player.getItemInHand(handIn).getDisplayName().getString()
				+ player.getItemInHand(handIn).getOrCreateTag()));

		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		if (tileEntity instanceof BasinBlockEntity) {

			BasinBlockEntity basinBlockEntity = (BasinBlockEntity) tileEntity;
			ItemStack heldStack = player.getItemInHand(handIn);
			ItemStack offhandStack = player.getOffhandItem();

			//注入液体和加物品暂时同时进行
			if (heldStack.getItem() instanceof BucketItem bucketItem) {
				if (basinBlockEntity.hasNoFluid() || bucketItem.getFluid() == Fluids.EMPTY) {
					if (FluidTransferUtil.interactWithTank(worldIn, pos, player, handIn, hit)) {
						return InteractionResult.SUCCESS;
					}
				} else {
					final FluidStack[] fluidStackDown = new FluidStack[1];
					basinBlockEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, Direction.DOWN)
							.ifPresent(handler -> {
								fluidStackDown[0] = handler.getFluidInTank(0);
							});
					if (fluidStackDown[0].getFluid() == bucketItem.getFluid()) {
						if (FluidTransferUtil.interactWithTank(worldIn, pos, player, handIn, hit)) {
							return InteractionResult.SUCCESS;
						}
					}
				}

			}

			if (basinBlockEntity.isEmpty()) {
				if (!offhandStack.isEmpty()) {
					if (handIn.equals(InteractionHand.MAIN_HAND) && !(heldStack.getItem() instanceof BlockItem)) {
						return InteractionResult.PASS; // Pass to off-hand if that item is placeable
					}
					if (handIn.equals(InteractionHand.OFF_HAND)) {
						return InteractionResult.PASS; // Items in this tag should not be placed from the off-hand
					}
				}
				if (heldStack.isEmpty()) {
					return InteractionResult.PASS;
				} else if (basinBlockEntity.addItem(player.getAbilities().instabuild ? heldStack.copy() : heldStack)) {
					worldIn.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0F, 0.8F);
					return InteractionResult.SUCCESS;
				}

			} else {
				if (handIn.equals(InteractionHand.MAIN_HAND)) {

					if (!player.getInventory().add(basinBlockEntity.removeItem())) {
						Containers.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), basinBlockEntity.removeItem());
					}
				}
				worldIn.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.WOOD_HIT, SoundSource.BLOCKS, 0.25F, 0.5F);
				return InteractionResult.SUCCESS;


			}
		}
		return InteractionResult.SUCCESS;
	}


//	@Override
//	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
//		TileEntity tile = worldIn.getTileEntity(pos);
//		if (tile instanceof TileBasin) {
//			ItemStack held = playerIn.getHeldItem(hand);
//			if (facing == EnumFacing.UP && held.getItem() == Item.getItemFromBlock(Blocks.PISTON)) {
//				return false;
//			}
//			TileBasin tileBasin = (TileBasin) tile;
//			ItemStack inv = tileBasin.stacks.getStackInSlot(0);
//			if (held.isEmpty()) {
//				if (inv.isEmpty()) {
//					return false;
//				} else {
//					StacksUtil.dropInventoryItems(worldIn, pos, tileBasin.stacks, false);
//					return true;
//				}
//			} else {
//				ItemStack heldCopy = ItemHandlerHelper.copyStackWithSize(held, 1); // do not modify the input
//				if (FluidUtil.getFluidHandler(heldCopy) != null) {
//					FluidUtil.interactWithFluidHandler(playerIn, hand, worldIn, pos, facing);
//				} else if (inv.isEmpty()) {
//					playerIn.setHeldItem(hand, tileBasin.stacks.insertItem(0, held, false));
//				} else {
//					StacksUtil.dropInventoryItems(worldIn, pos, tileBasin.stacks, false);
//				}
//				return true;
//			}
//		}
//		return false;
//	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
		super.createBlockStateDefinition(p_49915_.add(POWER, FACING));
	}

	//	比较器暂时不实现
	public int getSignal(BlockState p_52386_, BlockGetter p_52387_, BlockPos p_52388_, Direction p_52389_) {
		return p_52386_.getValue(POWER);
	}

//	private static void updateSignalStrength(BlockState p_52411_, Level p_52412_, BlockPos p_52413_) {
//		int i = p_52412_.getBrightness(LightLayer.SKY, p_52413_) - p_52412_.getSkyDarken();
//		float f = p_52412_.getSunAngle(1.0F);
//		boolean flag = p_52411_.getValue(INVERTED);
//		if (flag) {
//			i = 15 - i;
//		} else if (i > 0) {
//			float f1 = f < (float) Math.PI ? 0.0F : ((float) Math.PI * 2F);
//			f += (f1 - f) * 0.2F;
//			i = Math.round((float) i * Mth.cos(f));
//		}
//
//		i = Mth.clamp(i, 0, 15);
//		if (p_52411_.getValue(POWER) != i) {
//			p_52412_.setBlock(p_52413_, p_52411_.setValue(POWER, Integer.valueOf(i)), 3);
//		}
//
//	}
//	@SuppressWarnings("deprecation")
//	@Override
//	public boolean hasComparatorInputOverride(IBlockState state) {
//		return true;
//	}

//	@SuppressWarnings("deprecation")
//	@Override
//	public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
//		IFluidHandler handler = FluidUtil.getFluidHandler(worldIn, pos, null);
//		if (handler != null && handler.getTankProperties().length > 0) {
//			IFluidTankProperties tank = handler.getTankProperties()[0];
//			if (tank.getContents() != null) {
//				return 1 + tank.getContents().amount * 14 / tank.getCapacity();
//			}
//		}
//		return 0;
//	}

//联动暂时不去实现
//	@Override
//	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
//		neighborChanged(state, worldIn, pos, this, pos.up());
//	}
//
//	@SuppressWarnings("deprecation")
//	@Override
//	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
//		if (CuisineConfig.GENERAL.enableSqueezer && pos.up().equals(fromPos)) {
//			IBlockState fromState = worldIn.getBlockState(fromPos);
//			if (fromState.getBlock() == Blocks.PISTON) {
//				worldIn.setBlockState(fromPos, CuisineRegistry.SQUEEZER.getDefaultState());
//			}
//		} else if (pos.down().equals(fromPos)) {
//			TileEntity tile = worldIn.getTileEntity(pos);
//			if (tile instanceof TileBasinHeatable) {
//				((TileBasinHeatable) tile).updateHeat();
//			}
//		}
//	}

	@Override
	public void randomTick(BlockState p_60551_, ServerLevel p_60552_, BlockPos p_60553_, Random p_60554_) {
		Minecraft.getInstance().player.sendMessage(
				new TextComponent(p_60552_.isRaining() ? "Raining" : "Not Raining"),
				Minecraft.getInstance().player.getUUID());
		super.randomTick(p_60551_, p_60552_, p_60553_, p_60554_);
	}

	@Override
	public void tick(BlockState p_60462_, ServerLevel p_60463_, BlockPos p_60464_, Random p_60465_) {
		Minecraft.getInstance().player.sendMessage(
				new TextComponent(p_60463_.isRaining() ? "Raining" : "Not Raining"),
				Minecraft.getInstance().player.getUUID());
		super.tick(p_60462_, p_60463_, p_60464_, p_60465_);
	}

	//	it works when the blcok is placed
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level worldIn, BlockState blockState, BlockEntityType<T> p_153214_) {

		return !worldIn.isClientSide ? createTickerHelper(p_153214_, CoreModule.basinBlockEntityBlockEntityType, BlockBasin::tickEntity) : null;
//		return EntityBlock.super.getTicker(p_153212_, p_153213_, p_153214_);
	}

	@Nullable
	protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> p_152133_, BlockEntityType<E> p_152134_, BlockEntityTicker<? super E> p_152135_) {
		return p_152134_ == p_152133_ ? (BlockEntityTicker<A>) p_152135_ : null;
	}

	//write here
	private static void tickEntity(Level worldIn, BlockPos pos, BlockState blockState, BasinBlockEntity basinBlockEntity) {
//		basinBlockEntity.requestModelDataUpdate();

		//		if (worldIn.isClientSide()) {
////			Minecraft.getInstance().player.sendMessage(
////					new TextComponent(worldIn.isRaining() ? "Raining" : "Not Raining"),
////					Minecraft.getInstance().player.getUUID());
////		}
		// tick is working in the server side
//		try {worldIn.getServer().getPlayerList().getPlayerByName("Dev").sendMessage(	new TextComponent(worldIn.isRaining() ? "Raining" : "Not Raining"),
//				worldIn.getServer().getPlayerList().getPlayerByName("Dev").getUUID());
//		} catch (Exception e) {
//		}

	}
//	@Override
//	public void fillWithRain(World worldIn, BlockPos pos) {
//		IFluidHandler handler = FluidUtil.getFluidHandler(worldIn, pos, EnumFacing.UP);
//		if (handler != null) {
//			handler.fill(new FluidStack(FluidRegistry.WATER, 100), true);
//		}
//	}

//    @Override
//    @SideOnly(Side.CLIENT)
//    public BlockRenderLayer getRenderLayer()
//    {
//        return BlockRenderLayer.CUTOUT;
//    }


//    @SuppressWarnings("deprecation")
//    @Override
//    public boolean isOpaqueCube(IBlockState state)
//    {
//        return false;
//    }

//	@Override
//	public boolean isCollisionShapeFullBlock(BlockState p_181242_, BlockGetter p_181243_, BlockPos p_181244_) {
//		return false;
//	}

//	@SuppressWarnings("deprecation")
//    @Override
//    public boolean isFullCube(IBlockState state)
//    {
//        return false;
//    }


	//	@Override
//	public VoxelShape getCollisionShape(BlockState p_60572_, BlockGetter p_60573_, BlockPos p_60574_, CollisionContext p_60575_) {
//		return basinShape;
//	}
	public VoxelShape getShape(BlockState p_56390_, BlockGetter p_56391_, BlockPos p_56392_, CollisionContext p_56393_) {
		SlabType slabtype = SlabType.BOTTOM;
		switch (slabtype) {
			case DOUBLE:
				return Shapes.block();
			case TOP:
				return TOP_AABB;
			default:
				return BOTTOM_AABB;
		}
	}

//
//	@SuppressWarnings("deprecation")
//    @Override
//    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
//    {
//        return AABB;
//    }

//    @SuppressWarnings("deprecation")
//    @Override
//    public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side)
//    {
//        return side == EnumFacing.DOWN;
//    }


//    @SuppressWarnings("deprecation")
//    @Override
//    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
//    {
//        return face == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
//    }

	@Override
	public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			BlockEntity tileEntity = worldIn.getBlockEntity(pos);
			if (tileEntity instanceof BasinBlockEntity) {
				Containers.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), ((BasinBlockEntity) tileEntity).getStoredItem());
				worldIn.updateNeighbourForOutputSignal(pos, this);
			}

			super.onRemove(state, worldIn, pos, newState, isMoving);
		}
	}

	public static Logger logger = LogManager.getLogger();

	@Override
	public MutableComponent getName(ItemStack stack) {
		try {
			String[] space = NBTUtils.getNameFromRetxtureStack(stack).split(":");
			this.textureName[0] = space[0];
			this.textureName[1] = space[1];
//			logger.info(TextUtils.getLanguage());
			return TextUtils.getLanguage().equals("简体中文") ?
					NBTUtils.getBlockFromRegister(space).getName().append("").append(super.getName(stack)) :
					NBTUtils.getBlockFromRegister(space).getName().append(" ").append(super.getName(stack));
		} catch (Exception exception000) {
			return super.getName(stack);
		}

	}

	@Override
	public void appendHoverText(ItemStack p_49816_, @Nullable BlockGetter p_49817_, List<Component> p_49818_, TooltipFlag p_49819_) {
		p_49818_.add(new TextComponent("----------------"));
		p_49818_.add(new TranslatableComponent("block.cuisine.basin.hint"));
		super.appendHoverText(p_49816_, p_49817_, p_49818_, p_49819_);
	}

	@Override
	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {


		Registry.BLOCK.forEach((e) ->
				{
//					logger.info(e.getRegistryName().toString());
					if (FullBlockIngredient.isFullBlock(e.asItem().getDefaultInstance())) {
						var stack = Basin.asItem().getDefaultInstance();

						NBTHelper data = NBTHelper.create();
						data.setString("Name", e.getRegistryName().toString());
						try {
							if (e.asItem().getDefaultInstance().getTag() != null) {
								data.setString("Properties", e.defaultBlockState().toString());
							}
						} catch (Exception exception) {
							logger.info("Can't get the properties:" + exception.getMessage() + e.getRegistryName().toString() + "//" + e.delegate.get().defaultBlockState().getProperties());
						}

						NBTHelper blockNBT = NBTHelper.create();
						blockNBT.setTag("Block", data.get());
						blockNBT.setString("Type", "Block");

						NBTHelper particleNBT = NBTHelper.create();
						particleNBT.setTag("particle", blockNBT.get());

						NBTHelper overridesNBT = NBTHelper.create();
						overridesNBT.setTag("Overrides", particleNBT.get());
//						logger.info(overridesNBT.get().getAsString());
						I18n.get("cuisine.block.basin_" + e.getName().getString(0).toString(), stack);
						stack.getOrCreateTag().put("BlockEntityTag", overridesNBT.get());

						items.add(stack);
					}
				}
		);

	}

}
