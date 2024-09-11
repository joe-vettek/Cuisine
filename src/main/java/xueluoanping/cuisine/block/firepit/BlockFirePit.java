package xueluoanping.cuisine.block.firepit;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
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


import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.Nullable;

import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.block.BlockBasin;
import xueluoanping.cuisine.block.baseblock.SimpleHorizontalEntityBlock;
import xueluoanping.cuisine.blockentity.firepit.AbstractFirepitBlockEntity;
import xueluoanping.cuisine.blockentity.firepit.FirePitBlockEntity;
import xueluoanping.cuisine.blockentity.firepit.WokOnFirePitbBlockEntity;
import xueluoanping.cuisine.blockentity.handler.FuelHeatHandler;
import xueluoanping.cuisine.register.BlockEntityRegister;

import java.util.Random;

public class BlockFirePit extends SimpleHorizontalEntityBlock {

    public static IntegerProperty LIGHT_LEVEL = IntegerProperty.create("light_level", 0, 15);
    protected static final VoxelShape AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);

    public BlockFirePit(Properties properties) {
        super(properties.lightLevel(BlockFirePit::getLightLevel));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(BlockFirePit::new);
    }

    private static int getLightLevel(BlockState state) {
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
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            if (state.is(this)) {
                if (stack.is(Tags.Items.RODS_WOODEN) && stack.getCount() >= 3) {
                    stack.shrink(3);
                    level.setBlock(pos, BlockEntityRegister.barbecue_rack.get().defaultBlockState().setValue(FACING, state.getValue(FACING)), Block.UPDATE_ALL);
                    // player.setItemInHand(handIn,pl);
                    return ItemInteractionResult.SUCCESS;
                }
            }
            if (level.getBlockEntity(pos) instanceof AbstractFirepitBlockEntity abstractFirepitBlockEntity) {
                if (abstractFirepitBlockEntity.addFuel(stack))
                    return ItemInteractionResult.SUCCESS;
            }
        }
        return super.useItemOn(stack, state, level, pos, player, handIn, hitResult);
    }


    @Nullable
    @Override
    public <T extends
            BlockEntity> BlockEntityTicker<T> getTicker(Level worldIn, BlockState blockState, BlockEntityType<T> entityType) {

        // BlockEntityTicker<T> ticker=createTickerHelper(entityType, BlockEntityRegister.fire_pit_entity_type.get(), FirePitBlockEntity::tickEntity);

        return !worldIn.isClientSide ?
                createTickerHelper(entityType, BlockEntityRegister.fire_pit_entity_type.get(), ((level, blockPos, blockState1, firePitBlockEntity) -> firePitBlockEntity.tick()))
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
    public void animateTick(BlockState stateIn, Level level, BlockPos pos, RandomSource rand) {
        if (level.getBlockEntity(pos) instanceof AbstractFirepitBlockEntity blockEntity) {
            FuelHeatHandler handler = blockEntity.getHeatHandler();

            int heatLevel = handler.getLevel();

            if (heatLevel > 0 && rand.nextInt(15 - heatLevel * 3) == 0) {

                level.playLocalSound(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS, 0.7F + 0.15F * heatLevel + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
            }

            for (int i = 0; i < heatLevel; i++) {
                float f = (float) (rand.nextFloat() * Math.PI * 2);
                double x = Mth.sin(f) * 0.1D;
                double y = pos.getY() + 0.12D + rand.nextDouble() * 0.05D;
                double z = Mth.cos(f) * 0.1D;
                // if (!hasComponent(stateIn, Component.WOK))
                if (!(blockEntity instanceof WokOnFirePitbBlockEntity)) {
                    if (heatLevel > 1) {
                        level.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5D + x, y, pos.getZ() + 0.5D + z, x * 0.2, 0.01 * heatLevel, z * 0.2);
                    } else {
                        level.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5D + x, y, pos.getZ() + 0.5D + z, 0D, 0D, 0D);
                    }
                }
            }

            if (handler.getBurnTime() > 0) {
                SimpleParticleType simpleparticletype = ParticleTypes.CAMPFIRE_COSY_SMOKE;
                level.addAlwaysVisibleParticle(
                        simpleparticletype,
                        true,
                        (double) pos.getX() + 0.5 + rand.nextDouble() / 3.0 * (double) (rand.nextBoolean() ? 1 : -1),
                        (double) pos.getY() + rand.nextDouble() + rand.nextDouble() + 0.4,
                        (double) pos.getZ() + 0.5 + rand.nextDouble() / 3.0 * (double) (rand.nextBoolean() ? 1 : -1),
                        0.0,
                        0.07,
                        0.0
                );
                if (!(blockEntity instanceof WokOnFirePitbBlockEntity)) {
                    level.addParticle(
                            ParticleTypes.SMOKE,
                            (double) pos.getX() + 0.5 + rand.nextDouble() / 4.0 * (double) (rand.nextBoolean() ? 1 : -1),
                            (double) pos.getY() + 0.4,
                            (double) pos.getZ() + 0.5 + rand.nextDouble() / 4.0 * (double) (rand.nextBoolean() ? 1 : -1),
                            0.0,
                            0.005,
                            0.0
                    );
                }
            }
        }


    }
}