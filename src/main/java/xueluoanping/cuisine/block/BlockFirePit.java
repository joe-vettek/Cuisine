package xueluoanping.cuisine.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.level.block.state.StateDefinition;

import net.minecraft.world.level.block.state.properties.IntegerProperty;

import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.minecraftforge.common.Tags;

import org.jetbrains.annotations.Nullable;

import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.api.HeatHandler;
import xueluoanping.cuisine.block.baseblock.SimpleHorizontalEntityBlock;
import xueluoanping.cuisine.block.entity.AbstractFirepitBlockEntity;
import xueluoanping.cuisine.block.entity.FirePitBlockEntity;
import xueluoanping.cuisine.block.entity.MillBlockEntity;
import xueluoanping.cuisine.block.entity.handler.FuelHeatHandler;
import xueluoanping.cuisine.register.BlockEntityRegister;

import java.util.Random;

public class BlockFirePit extends SimpleHorizontalEntityBlock {

	public static IntegerProperty LIGHT_LEVEL = IntegerProperty.create("light_level", 0, 15);
	protected static final VoxelShape AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);

	public BlockFirePit(Properties properties) {
		super(properties.lightLevel(BlockFirePit::getLightLevel));
	}

	private static int getLightLevel(BlockState state) {

		Cuisine.logger(state);
		return state.getValue(LIGHT_LEVEL);
	}


	@Override
	public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
		// if(level.getBlockEntity(pos) instanceof AbstractFirepitBlockEntity entity){
		// 	FuelHeatHandler handler=entity.getHeatHandler();
		//
		// 	return (int) (handler.getBurnTime() / handler.getMaxBurnTime() * 15);
		// }

		return super.getLightEmission(state, level, pos);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder.add(LIGHT_LEVEL));
	}


	@Override
	public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
		return AABB;
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hitResult) {
		ItemStack stack = player.getItemInHand(handIn);
		if(!level.isClientSide() )
		{
			if (state.is(this)) {
				if (stack.is(Tags.Items.RODS_WOODEN) && stack.getCount() >= 3) {
					stack.shrink(3);
					level.setBlock(pos, BlockEntityRegister.barbecue_rack.get().defaultBlockState().setValue(FACING, state.getValue(FACING)), Block.UPDATE_ALL);
					// player.setItemInHand(handIn,pl);
					return InteractionResult.SUCCESS;
				}
			}
			if (level.getBlockEntity(pos) instanceof AbstractFirepitBlockEntity abstractFirepitBlockEntity) {
				if (abstractFirepitBlockEntity.addFuel(stack))
					return InteractionResult.SUCCESS;
			}
		}
		return super.use(state, level, pos, player, handIn, hitResult);
	}


	@Nullable
	@Override
	public <T extends
			BlockEntity> BlockEntityTicker<T> getTicker(Level worldIn, BlockState blockState, BlockEntityType<T> entityType) {

		// BlockEntityTicker<T> ticker=createTickerHelper(entityType, BlockEntityRegister.fire_pit_entity_type.get(), FirePitBlockEntity::tickEntity);

		return !worldIn.isClientSide ?
				createTickerHelper(entityType, BlockEntityRegister.fire_pit_entity_type.get(), FirePitBlockEntity::tickEntity)
				: null;
	}


	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState state) {
		if (hasBlockEntity(state)) {
			return new FirePitBlockEntity(pPos, state);
		}
		return null;
	}

	@Override
	public void animateTick(BlockState stateIn, Level level, BlockPos pos, Random rand) {
		if (level.getBlockEntity(pos) instanceof FirePitBlockEntity blockEntity) {
			FuelHeatHandler handler = blockEntity.getHeatHandler();

			// Cuisine.logger(stateIn);
			// if(level.getBlockEntity(pos) instanceof AbstractFirepitBlockEntity entity){
			// 	FuelHeatHandler handler=((AbstractFirepitBlockEntity)level.getBlockEntity(pos)).getHeatHandler();
			//
			// 	return (int) (handler.getBurnTime() / handler.getMaxBurnTime() * 15);
			// }

			int heatLevel = handler.getLevel();

			if (heatLevel > 0 && rand.nextInt(15 - heatLevel * 3) == 0)
			{

				level.playLocalSound(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS, 0.7F + 0.15F * heatLevel + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
			}

			for (int i = 0; i < heatLevel; i++)
			{
				float f = (float) (rand.nextFloat() * Math.PI * 2);
				double x = Mth.sin(f) * 0.1D;
				double y = pos.getY() + 0.12D + rand.nextDouble() * 0.05D;
				double z = Mth.cos(f) * 0.1D;
				// if (!hasComponent(stateIn, Component.WOK))
				{
					if (heatLevel > 1) {
						level.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5D + x, y, pos.getZ() + 0.5D + z, x * 0.2, 0.01 * heatLevel, z * 0.2);
					} else {
						level.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5D + x, y, pos.getZ() + 0.5D + z, 0D, 0D, 0D);
					}
				}
				// else
				// {
				// 	if (heatLevel > 1)
				// 	{
				// 		worldIn.spawnAlwaysVisibleParticle(EnumParticleTypes.FLAME.getParticleID(), pos.getX() + 0.5D + x, y, pos.getZ() + 0.5D + z, x * 0.2 * (heatLevel - 0.5), 0.005, z * 0.2 * (heatLevel - 1));
				// 	}
				// 	if (rand.nextInt(5) == 0)
				// 	{
				// 		worldIn.spawnAlwaysVisibleParticle(EnumParticleTypes.SMOKE_NORMAL.getParticleID(), pos.getX() + 0.5D + x * rand.nextDouble() * 0.5D, y + 0.2D, pos.getZ() + 0.5D + z * rand.nextDouble() * 0.5D, 0, 0, 0);
				// 	}
				// }
			}
		}


	}
}
