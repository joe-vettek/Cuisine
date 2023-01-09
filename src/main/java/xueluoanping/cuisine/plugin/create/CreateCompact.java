package xueluoanping.cuisine.plugin.create;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import xueluoanping.cuisine.util.Platform;
import xueluoanping.cuisine.block.entity.BasinBlockEntity;


public class CreateCompact {

	private static final String modid_create = "create";

	public static boolean isLoad() {
		return Platform.isModLoaded(modid_create);
	}


	public static void tickEntity(Level worldIn, BlockPos pos, BlockState blockState, BasinBlockEntity basinBlockEntity) {
//		if (worldIn.getBlockEntity(pos.above(2)) instanceof MechanicalPressTileEntity mechanicalPressTileEntity) {
//			if (mechanicalPressTileEntity.getRunningTickSpeed() != 0
//					&& !basinBlockEntity.isEmpty()
//					&& basinBlockEntity.tank.getFluidAmount() < BasinBlockEntity.Capacity
//					&& basinBlockEntity.tryProcessSqueezing(worldIn)
//			) {
////				worldIn.getServer().sendMessage();
////				try {
////					worldIn.getServer().getPlayerList().getPlayerByName("Dev").sendMessage(
////							new TextComponent(
////									mechanicalPressTileEntity.getRunningTickSpeed()
////											+ "//" + basinBlockEntity.tick),
////							worldIn.getServer().getPlayerList().getPlayerByName("Dev").getUUID());
////				} catch (Exception e) {
////				}
//				if (basinBlockEntity.speedCache != mechanicalPressTileEntity.getRunningTickSpeed()) {
//					basinBlockEntity.speedCache = mechanicalPressTileEntity.getRunningTickSpeed();
//					basinBlockEntity.tick = 0;
//				}
//				double compare = 0.0;
//
//				if (basinBlockEntity.tick == 0) {
//					mechanicalPressTileEntity.start(MechanicalPressTileEntity.Mode.BASIN);
//				}
//				compare = 16.0 / (float) mechanicalPressTileEntity.getRunningTickSpeed() * 7.5;
//				if (basinBlockEntity.tick == (int) compare) {
//					if (basinBlockEntity.processSqueezing(worldIn)) {
//						makeCompactingParticleEffect(worldIn, VecHelper.getCenterOf(basinBlockEntity.getBlockPos()), basinBlockEntity.getStoredItem());
//					}
//				}
//				//at least 2, 4 is better
//				if (basinBlockEntity.tick == (int) (compare * 2)) {
//					basinBlockEntity.tick = -1;
//				}
//				basinBlockEntity.tick++;
//
//
//			}
//		}
	}

	//ParticleEffect should display in client side
	public static void makeCompactingParticleEffect(Level level, Vec3 pos, ItemStack stack) {
////		if (level == null || !level.isClientSide)
////			return;
//		for (int i = 0; i < 20; i++) {
//			Vec3 motion = VecHelper.offsetRandomly(Vec3.ZERO, level.random, .175f)
//					.multiply(1, 0, 1);
//			((ServerLevel) level).sendParticles(new ItemParticleOption(ParticleTypes.ITEM, stack),
//					pos.x, pos.y, pos.z,
//					4,
//					motion.x, motion.y + .25f, motion.z,
//					0.05f);
////			level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, stack), pos.x, pos.y, pos.z, motion.x,
////					motion.y + .25f, motion.z);
//		}
//	}
}}
