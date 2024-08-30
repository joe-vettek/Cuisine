package xueluoanping.cuisine.block;

import java.util.List;


import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import xueluoanping.cuisine.block.baseblock.SimpleHorizontalEntityBlock;
import xueluoanping.cuisine.blockentity.AbstractBasinBlockEntity;
import xueluoanping.cuisine.blockentity.BasinBlockEntity;
import xueluoanping.cuisine.register.BlockEntityRegister;

public class BlockBasin extends SimpleHorizontalEntityBlock implements EntityBlock {

    protected static final VoxelShape BOTTOM_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    protected static final VoxelShape TOP_AABB = Block.box(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);


    public static final IntegerProperty LIGHT = BlockStateProperties.LEVEL;

//	Block have only one instance, so can't set special information here.
//	private final String[] textureName = new String[2];


    public BlockBasin(Properties builder) {
        super(builder.noOcclusion().lightLevel(BlockBasin::getLightLevel));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(BlockBasin::new);
    }

    // Add all the properties here, or may cause a null point exception.
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        super.createBlockStateDefinition(p_49915_.add(LIGHT));
    }

    //	Set a ToIntFunction to reflect the function
    private static int getLightLevel(BlockState state) {
        return state.getValue(LIGHT);
    }


    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity p_152429_, float p_152430_) {
//		if (level.isClientSide) {
//			assert Minecraft.getInstance().player != null;
//			Minecraft.getInstance().player.sendMessage(
//					new TextComponent(p_152429_.getName().getString()),
//					Minecraft.getInstance().player.getUUID());
//		}
        if (level.getBlockEntity(pos) != null
                && level.getBlockEntity(pos) instanceof AbstractBasinBlockEntity basinBlockEntity) {
            if (basinBlockEntity.isEmpty()) return;
//			if(!basinBlockEntity.hasNoFluid())return;
            basinBlockEntity.processSqueezing(level);
//			Cuisine.logger("aaa");
        }

        super.fallOn(level, state, pos, p_152429_, p_152430_);
    }

//	@Override
//    public void onFallenUpon(World level, BlockPos pos, Entity entityIn, float fallDistance)
//    {
//        entityIn.fall(fallDistance, 0.5F);
//
//        TileEntity tile = level.getTileEntity(pos);
//        if (tile instanceof TileBasin)
//        {
//            TileBasin tileBasin = ((TileBasin) tile);
//            if (fallDistance >= 1 && entityIn instanceof EntityLivingBase)
//            {
//                ItemStack input = tileBasin.stacks.getStackInSlot(0);
//                if (!input.isEmpty() || tileBasin.tank.getFluidAmount() > 0)
//                {
//                    level.playSound(null, pos, SoundEvents.BLOCK_SLIME_STEP, SoundCategory.BLOCKS, 0.5F, level.rand.nextFloat() / 4 + .6F);
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
//	public void onEntityCollision(World level, BlockPos pos, IBlockState state, Entity entityIn) {
//		if (level.isRemote) {
//			return;
//		}
//		TileEntity tile = level.getTileEntity(pos);
//		if (tile instanceof TileBasin) {
//			TileBasin tileBasin = ((TileBasin) tile);
//			if (entityIn.getClass() == EntityItem.class) {
//				if (tileBasin.tickCheckThrowing > 0) {
//					tileBasin.tickCheckThrowing--;
//					return;
//				}
//				List<ItemStack> items = level.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos)).stream().filter(e -> !e.isDead && e.onGround).map(EntityItem::getItem).collect(Collectors.toList());
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


    // before
    @javax.annotation.Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        int light = 0;

        return this.defaultBlockState().setValue(LIGHT, light);
    }

    // after
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @javax.annotation.Nullable LivingEntity entity, ItemStack stack) {
//        BasinBlockEntity tileEntity = (BasinBlockEntity) level.getBlockEntity(pos);
//        CompoundTag data = BlockItem.getBlockEntityData(stack);
//        if (data != null) {
//            data = data.copy();
//            tileEntity.load(data);
//            tileEntity.setChanged();
////            Cuisine.logger(tileEntity.getUpdateTag());
//        }
    }
//	@Override
//	public void breakBlock(World level, BlockPos pos, IBlockState state) {
//		TileEntity te = level.getTileEntity(pos);
//
//		if (te instanceof TileBasin) {
//			StacksUtil.dropInventoryItems(level, pos, ((TileBasin) te).stacks, true);
//			((TileBasin) te).spillFluids();
//		}
//
//		super.breakBlock(level, pos, state);
//	}

    protected static boolean shouldHandlePrecipitation(Level level, Biome.Precipitation precipitation) {
        if (precipitation == Biome.Precipitation.RAIN) {
            return level.getRandom().nextFloat() < 0.05F;
        } else if (precipitation == Biome.Precipitation.SNOW) {
            return level.getRandom().nextFloat() < 0.1F;
        } else {
            return false;
        }
    }

    public void handlePrecipitation(BlockState state, Level level, BlockPos pos, Biome.Precipitation precipitation) {
        if (shouldHandlePrecipitation(level, precipitation)) {
            if (precipitation == Biome.Precipitation.RAIN) {
                level.setBlockAndUpdate(pos, Blocks.WATER_CAULDRON.defaultBlockState());
                level.gameEvent((Entity) null, GameEvent.FLUID_PLACE, pos);
            } else if (precipitation == Biome.Precipitation.SNOW) {
                level.setBlockAndUpdate(pos, Blocks.POWDER_SNOW_CAULDRON.defaultBlockState());
                level.gameEvent((Entity) null, GameEvent.FLUID_PLACE, pos);
            }

        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        //		Minecraft.getInstance().player.sendMessage(
//				new TextComponent(p_60503_.getBlock().getName().getString()+"hhh"),
//				Minecraft.getInstance().player.getUUID());

//		sendPlayerMessageSingle(level, new TextComponent("玩家手持的是" +
//				player.getItemInHand(handIn).getDisplayName().getString()
//				+ player.getItemInHand(handIn).getOrCreateTag()));

        BlockEntity tileEntity = level.getBlockEntity(pos);
        var handIn = player.getUsedItemHand();
        if (tileEntity instanceof AbstractBasinBlockEntity basinBlockEntity) {

            // BasinBlockEntity basinBlockEntity = (BasinBlockEntity) tileEntity;
            ItemStack heldStack = player.getItemInHand(handIn);
            ItemStack offhandStack = player.getOffhandItem();

            // 注入液体和加物品暂时同时进行
            if (heldStack.getItem() instanceof BucketItem bucketItem) {
                if (level.getCapability(Capabilities.FluidHandler.BLOCK, pos, Direction.UP) instanceof IFluidHandler fluidHandler) {
                    var a = FluidUtil.interactWithFluidHandler(player, handIn, fluidHandler);
                    if (a) return InteractionResult.SUCCESS;
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
                    level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0F, 0.8F);
                    return InteractionResult.SUCCESS;
                }

            } else {
                if (handIn.equals(InteractionHand.MAIN_HAND)) {

                    if (!player.getInventory().add(basinBlockEntity.removeAllItem())) {
                        Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), basinBlockEntity.removeAllItem());
                    }
                }
                level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.WOOD_HIT, SoundSource.BLOCKS, 0.25F, 0.5F);
                return InteractionResult.SUCCESS;


            }
        }
        return InteractionResult.SUCCESS;
    }


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


    //	it works after the block is placed
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {

        return !level.isClientSide ?
                createTickerHelper(blockEntityType, BlockEntityRegister.basin_entity_type.get(), BlockBasin::tickEntity) : null;
//		return EntityBlock.super.getTicker(p_153212_, p_153213_, p_153214_);
    }

    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> blockEntityType, BlockEntityType<E> blockEntityType1, BlockEntityTicker<? super E> blockEntityTicker) {
        return blockEntityType1 == blockEntityType ? (BlockEntityTicker<A>) blockEntityTicker : null;
    }

    // write here,server side
    private static void tickEntity(Level level, BlockPos pos, BlockState blockState, BasinBlockEntity basinBlockEntity) {
//		if (CreateCompact.isLoad())
//			CreateCompact.tickEntity(level, pos, blockState, basinBlockEntity);
// 		Cuisine.logger(11);
        if (level.isRaining() && (basinBlockEntity.hasNoFluid() || basinBlockEntity.tank.getFluid().getFluid() == Fluids.WATER)) {
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
//    public BlockFaceShape getBlockFaceShape(IBlockAccess level, IBlockState state, BlockPos pos, EnumFacing face)
//    {
//        return face == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
//    }

    @Override
    public void neighborChanged(BlockState p_60509_, Level p_60510_, BlockPos p_60511_, Block p_60512_, BlockPos p_60513_, boolean p_60514_) {
        super.neighborChanged(p_60509_, p_60510_, p_60511_, p_60512_, p_60513_, p_60514_);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tileEntity = level.getBlockEntity(pos);
            if (tileEntity instanceof BasinBlockEntity) {
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), ((BasinBlockEntity) tileEntity).getStoredItem());
                level.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    public static Logger logger = LogManager.getLogger();

    @Override
    public MutableComponent getName() {
        return super.getName();
    }

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        pTooltipComponents.add(Component.literal("----------------"));
        pTooltipComponents.add(Component.translatable("block.cuisine.basin.hint"));
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
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
