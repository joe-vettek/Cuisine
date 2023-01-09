package xueluoanping.cuisine.worldgen.tree;

import static xueluoanping.cuisine.block.nature.BlockBambooPlant.EAST;
import static xueluoanping.cuisine.block.nature.BlockBambooPlant.NORTH;
import static xueluoanping.cuisine.block.nature.BlockBambooPlant.SOUTH;
import static xueluoanping.cuisine.block.nature.BlockBambooPlant.Type;
import static xueluoanping.cuisine.block.nature.BlockBambooPlant.WEST;

import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.BambooBlock;
import net.minecraft.world.level.block.BambooSaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.block.nature.BlockBambooPlant;
import xueluoanping.cuisine.register.BlockRegister;
import xueluoanping.cuisine.register.ModConstant;

public class SimpleBambooFeature extends Feature<ProbabilityFeatureConfiguration> {
//    private static final BlockState BAMBOO_TRUNK = Blocks.BAMBOO.defaultBlockState().setValue(BambooBlock.AGE, Integer.valueOf(1)).setValue(BambooBlock.LEAVES, BambooLeaves.NONE).setValue(BambooBlock.STAGE, Integer.valueOf(0));
//    private static final BlockState BAMBOO_FINAL_LARGE = BAMBOO_TRUNK.setValue(BambooBlock.LEAVES, BambooLeaves.LARGE).setValue(BambooBlock.STAGE, Integer.valueOf(1));
//    private static final BlockState BAMBOO_TOP_LARGE = BAMBOO_TRUNK.setValue(BambooBlock.LEAVES, BambooLeaves.LARGE);
//    private static final BlockState BAMBOO_TOP_SMALL = BAMBOO_TRUNK.setValue(BambooBlock.LEAVES, BambooLeaves.SMALL);

    private static final BlockState BambooShoot = BlockRegister.bamboo_plant.get().defaultBlockState();
    private static final BlockState Bamboo = BlockRegister.bamboo_plant.get().defaultBlockState()
            .setValue(BlockBambooPlant.TYPE, Type.A_2);

    public SimpleBambooFeature(Codec<ProbabilityFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<ProbabilityFeatureConfiguration> context) {
        int i = 0;
        BlockPos baseBlockPos = context.origin();
        WorldGenLevel level = context.level();
        Random random = context.random();
        ProbabilityFeatureConfiguration probabilityfeatureconfiguration = context.config();
        BlockPos.MutableBlockPos mutableBlockPos = baseBlockPos.mutable();
        BlockPos.MutableBlockPos mutableBlockPos1 = baseBlockPos.mutable();

        Cuisine.logger(ModConstant.DebugKey.getWord(ModConstant.DebugKey.try_place_bamboo),baseBlockPos, mutableBlockPos);
        if (level.isEmptyBlock(baseBlockPos)) {
            for (int times = 4; times > 0; times--) {

                int xOffset = random.nextInt(5) - 2;
//                int yOffset = random.nextInt(3) - 1;
                int zOffset = random.nextInt(5) - 2;

//                mutableBlockPos.move(xOffset,yOffset,zOffset);
                BlockPos tryGenerateRootPos = baseBlockPos.offset(xOffset, 0, zOffset);
                if (level.isWaterAt(tryGenerateRootPos)) continue;
                boolean isShoot = random.nextInt(4) < 2;
                if (isShoot) {
                    if (BambooShoot.canSurvive(level, tryGenerateRootPos) && level.isEmptyBlock(tryGenerateRootPos.above())) {
                        level.setBlock(tryGenerateRootPos, BambooShoot, 2);
                        ++i;
                    }
                } else {
                    int h = getRandomBambooHeight(random);
                    if (BambooShoot.canSurvive(level, tryGenerateRootPos))
                        if (checkIfBambooCanGrow(level, tryGenerateRootPos.mutable(), h)) {
                            growBamboo(level, tryGenerateRootPos.mutable(), h, getRandomLowestBranchHeight(random, h));
                            ++i;
                            if(random.nextInt(3)==0)
                            level.addFreshEntity(EntityType.PANDA.create(level.getLevel(), null, null, null, tryGenerateRootPos.east(), MobSpawnType.SPAWNER, false, false));
                        }
                }

            }
        }
        Cuisine.logger("尝试放置竹子",i > 0);
        return i > 0;
    }

    public static int getRandomBambooHeight(Random random) {
        return 7 + random.nextInt(8);
    }

    public static int getRandomLowestBranchHeight(Random random, int height) {
        return Mth.clamp(5 + random.nextInt(4), 5, height);
    }

    public static boolean checkIfBambooCanGrow(WorldGenLevel level, BlockPos.MutableBlockPos pos, int height) {
        if (level.getBlockState(pos).getBlock() instanceof BlockBambooPlant ||
                level.getBlockState(pos).getBlock() instanceof BambooSaplingBlock
                || level.getBlockState(pos).getBlock() instanceof BambooBlock) return false;
        for (int i = 0; i < height; i++) {
            if (!level.isEmptyBlock(pos.move(Direction.UP, 1)))
                return false;
        }
        return true;
    }

    public static void growBamboo(WorldGenLevel level, BlockPos.MutableBlockPos pos, int height, int lowestBranchHeight) {

        for (int i = 0; i < height; i++) {
//            Cuisine.logger(pos.above(i), lowestBranchHeight);
            BlockState de = Bamboo;
            if (i >= lowestBranchHeight) {
                if (level.getRandom().nextBoolean() && level.isEmptyBlock(pos.above(i).south())) {
                    de = de.setValue(SOUTH, true);
                }
                if (level.getRandom().nextBoolean() && level.isEmptyBlock(pos.above(i).west())) {
                    de = de.setValue(WEST, true);
                }
                if (level.getRandom().nextBoolean() && level.isEmptyBlock(pos.above(i).north())) {
                    de = de.setValue(NORTH, true);
                }
                if (level.getRandom().nextBoolean() && level.isEmptyBlock(pos.above(i).east())) {
                    de = de.setValue(EAST, true);
                }
            }
            level.setBlock(pos.above(i), de, 2);

        }
    }
}
