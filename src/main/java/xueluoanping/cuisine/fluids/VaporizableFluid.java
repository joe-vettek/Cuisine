package xueluoanping.cuisine.fluids;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.SetTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import javax.annotation.Nullable;

import java.util.Random;


public class VaporizableFluid extends ForgeFlowingFluid {



	protected VaporizableFluid(Properties properties) {
		super(properties);
	}

	@Override
	public Fluid getFlowing() {
		return FluidModule.FLOWING_cuisine_juice;
	}

	@Override
	public Fluid getSource() {
		return FluidModule.cuisine_juice;
	}

	@Override
	protected boolean canConvertToSource() {
		return true;
	}

	@Override
	protected void beforeDestroyingBlock(LevelAccessor p_76002_, BlockPos p_76003_, BlockState p_76004_) {

	}
	@Nullable
	@Override
	public ParticleOptions getDripParticle() {
		return ParticleTypes.DRIPPING_WATER;
	}

	@Override
	public void animateTick(Level p_76445_, BlockPos p_76446_, FluidState p_76447_, Random p_76448_) {
		if (!p_76447_.isSource() && !p_76447_.getValue(FALLING)) {
			if (p_76448_.nextInt(64) == 0) {
				p_76445_.playLocalSound((double) p_76446_.getX() + 0.5D, (double) p_76446_.getY() + 0.5D, (double) p_76446_.getZ() + 0.5D, SoundEvents.WATER_AMBIENT, SoundSource.BLOCKS, p_76448_.nextFloat() * 0.25F + 0.75F, p_76448_.nextFloat() + 0.5F, false);
			}
		} else if (p_76448_.nextInt(10) == 0) {
			p_76445_.addParticle(ParticleTypes.UNDERWATER, (double) p_76446_.getX() + p_76448_.nextDouble(), (double) p_76446_.getY() + p_76448_.nextDouble(), (double) p_76446_.getZ() + p_76448_.nextDouble(), 0.0D, 0.0D, 0.0D);
		}

	}
	@Override
	public boolean isSame(Fluid p_76456_) {
		return p_76456_ == FluidModule.cuisine_juice|| p_76456_ == FluidModule.FLOWING_cuisine_juice;
	}

	@Override
	public boolean isEntityInside(FluidState state, LevelReader world, BlockPos pos, Entity entity, double yToTest, SetTag<Fluid> tag, boolean testingHead) {

		return super.isEntityInside(state, world, pos, entity, yToTest, tag, testingHead);
	}

	@Override
	protected int getSlopeFindDistance(LevelReader p_76074_) {
		return 4;
	}

	@Override
	protected int getDropOff(LevelReader p_76087_) {
		return 1;
	}

	@Override
	public Item getBucket() {
		return FluidModule.cuisine_juice_Bucket;
	}

	@Override
	protected FluidAttributes createAttributes() {
		return super.createAttributes();
	}

	@Override
	protected boolean canBeReplacedWith(FluidState p_76127_, BlockGetter p_76128_, BlockPos p_76129_, Fluid p_76130_, Direction p_76131_) {
		return true;
	}

	@Override
	public int getTickDelay(LevelReader p_76120_) {
		return 5;
	}

	@Override
	protected float getExplosionResistance() {
		return 100F;
	}

	@Override
	protected BlockState createLegacyBlock(FluidState p_76136_) {
		return FluidModule.cuisine_juice_block
				.defaultBlockState()
				.setValue(LiquidBlock.LEVEL, Integer.valueOf(getLegacyLevel(p_76136_)));

	}

	@Override
	public boolean isSource(FluidState p_76140_) {
		return false;
	}

	@Override
	public int getAmount(FluidState p_164509_) {
		return 0;
	}

	public static class Flowing extends VaporizableFluid {


		protected Flowing(Properties properties) {
			super(properties);
			this.registerDefaultState(this.getStateDefinition().any().setValue(LEVEL, 7));
		}

		protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> p_76476_) {
			super.createFluidStateDefinition(p_76476_);
			p_76476_.add(LEVEL);
		}

		public int getAmount(FluidState p_76480_) {
			return p_76480_.getValue(LEVEL);
		}

		public boolean isSource(FluidState p_76478_) {
			return false;
		}
	}

	public static class Source extends VaporizableFluid {
		protected Source(Properties properties) {
			super(properties);
		}

		public int getAmount(FluidState p_76485_) {
			return 8;
		}

		public boolean isSource(FluidState p_76483_) {
			return true;
		}
	}
//    @Override
//    public boolean doesVaporize(FluidStack fluidStack)
//    {
//        return true;
//    }

}
