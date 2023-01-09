package xueluoanping.cuisine.block;

import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import xueluoanping.cuisine.util.FluidTransferUtil;
import xueluoanping.cuisine.block.entity.BasinBlockEntity;
import xueluoanping.cuisine.register.BlockEntityRegister;

public class BlockBasin extends HorizontalDirectionalBlock implements EntityBlock {

    protected static final VoxelShape BOTTOM_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    protected static final VoxelShape TOP_AABB = Block.box(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);


    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty LIGHT = BlockStateProperties.LEVEL;

//	Block have only one instance, so can't set special information here.
//	private final String[] textureName = new String[2];


    public BlockBasin(Properties builder) {
        super(builder.noOcclusion().lightLevel(BlockBasin::getLightLevel));
    }

    //Add all the properties here, or may cause a null point exception.
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        super.createBlockStateDefinition(p_49915_.add(FACING, LIGHT));
    }

    //	Set a ToIntFunction to reflect the function
    private static int getLightLevel(BlockState state) {
        return state.getValue(LIGHT);
    }


    @Override
    public void fallOn(Level worldIn, BlockState state, BlockPos pos, Entity p_152429_, float p_152430_) {
//		if (worldIn.isClientSide) {
//			assert Minecraft.getInstance().player != null;
//			Minecraft.getInstance().player.sendMessage(
//					new TextComponent(p_152429_.getName().getString()),
//					Minecraft.getInstance().player.getUUID());
//		}
        if (worldIn.getBlockEntity(pos) != null
                && worldIn.getBlockEntity(pos) instanceof BasinBlockEntity basinBlockEntity) {
            if (basinBlockEntity.isEmpty()) return;
//			if(!basinBlockEntity.hasNoFluid())return;
            basinBlockEntity.processSqueezing(worldIn);
//			Cuisine.logger("aaa");
        }

        super.fallOn(worldIn, state, pos, p_152429_, p_152430_);
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
        int light = 0;

        return this.defaultBlockState().setValue(LIGHT, light);
    }

    //after
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @javax.annotation.Nullable LivingEntity entity, ItemStack stack) {
//        BasinBlockEntity tileEntity = (BasinBlockEntity) worldIn.getBlockEntity(pos);
//        CompoundTag data = BlockItem.getBlockEntityData(stack);
//        if (data != null) {
//            data = data.copy();
//            tileEntity.load(data);
//            tileEntity.setChanged();
////            Cuisine.logger(tileEntity.getUpdateTag());
//        }
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

//		sendPlayerMessageSingle(worldIn, new TextComponent("玩家手持的是" +
//				player.getItemInHand(handIn).getDisplayName().getString()
//				+ player.getItemInHand(handIn).getOrCreateTag()));

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

                    if (!player.getInventory().add(basinBlockEntity.removeAllItem())) {
                        Containers.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), basinBlockEntity.removeAllItem());
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
    public boolean isSignalSource(BlockState p_60571_) {
        return true;
    }

    //	比较器暂时不实现
    public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        if (level.getBlockEntity(pos) instanceof BasinBlockEntity tile) {
            return tile.tank.getFluid().getAmount() / tile.tank.getCapacity() * 15;
        }
        return 0;
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

        return !worldIn.isClientSide ? createTickerHelper(p_153214_, BlockEntityRegister.basin_entity_type.get(), BlockBasin::tickEntity) : null;
//		return EntityBlock.super.getTicker(p_153212_, p_153213_, p_153214_);
    }

    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> p_152133_, BlockEntityType<E> p_152134_, BlockEntityTicker<? super E> p_152135_) {
        return p_152134_ == p_152133_ ? (BlockEntityTicker<A>) p_152135_ : null;
    }

    //write here,server side
    private static void tickEntity(Level worldIn, BlockPos pos, BlockState blockState, BasinBlockEntity basinBlockEntity) {
//		if (CreateCompact.isLoad())
//			CreateCompact.tickEntity(worldIn, pos, blockState, basinBlockEntity);
        if (worldIn.isRaining() && (basinBlockEntity.hasNoFluid() || basinBlockEntity.tank.getFluid().getFluid() == Fluids.WATER)) {
            basinBlockEntity.tank.fill(new FluidStack(Fluids.WATER, 10), IFluidHandler.FluidAction.EXECUTE);
        }

    }

    @Override
    public RenderShape getRenderShape(BlockState p_60550_) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }


//    @Override
//    @SideOnly(Side.CLIENT)
//    public BlockRenderLayer getRenderLayer()
//    {
//        return BlockRenderLayer.CUTOUT;
//    }

    @Override
    public boolean isCollisionShapeFullBlock(BlockState p_181242_, BlockGetter p_181243_, BlockPos p_181244_) {
        return false;
    }

    public VoxelShape getShape(BlockState p_56390_, BlockGetter p_56391_, BlockPos p_56392_, CollisionContext p_56393_) {
        return BOTTOM_AABB;
    }

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
    public void neighborChanged(BlockState p_60509_, Level p_60510_, BlockPos p_60511_, Block p_60512_, BlockPos p_60513_, boolean p_60514_) {
        super.neighborChanged(p_60509_, p_60510_, p_60511_, p_60512_, p_60513_, p_60514_);
    }

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
    public MutableComponent getName() {
        return super.getName();
    }


    @Override
    public void appendHoverText(ItemStack p_49816_, @Nullable BlockGetter p_49817_, List<Component> p_49818_, TooltipFlag p_49819_) {
        p_49818_.add(new TextComponent("----------------"));
        p_49818_.add(new TranslatableComponent("block.cuisine.basin.hint"));
        super.appendHoverText(p_49816_, p_49817_, p_49818_, p_49819_);
    }



//	@Override
//	public void fillItemCategory(CreativeModeTab p_40569_, NonNullList<ItemStack> list) {
//		ForgeRegistries.BLOCKS.getValues().forEach(block -> {
//			if(isFullBlock(block))
//				list.add(NBTUtils.createTagFromTxtureProvider(asItem().getDefaultInstance(), block));
//		});
//		super.fillItemCategory(p_40569_, list);
//	}


}
