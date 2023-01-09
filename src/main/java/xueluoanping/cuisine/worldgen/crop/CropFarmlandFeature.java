package xueluoanping.cuisine.worldgen.crop;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.NetherForestVegetationFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.block.nature.BlockCuisineCrops;
import xueluoanping.cuisine.block.nature.BlockDoubleCrops;
import xueluoanping.cuisine.register.CropRegister;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

public class CropFarmlandFeature extends Feature<NoneFeatureConfiguration> {

    public CropFarmlandFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        int i = 0;
        // if(true)   return false;
        long a=System.currentTimeMillis();

        BlockPos baseBlockPos = context.origin();
        WorldGenLevel level = context.level();
        Random random = context.random();
        // ProbabilityFeatureConfiguration probabilityfeatureconfiguration = context.config();
        BlockPos.MutableBlockPos mutableBlockPos = baseBlockPos.mutable();

        Cuisine.logger("CropFarmland Try Place in");
        Block crop = null;
        int baseType = getCropType(level, baseBlockPos);
        BlockPos tryGenerateRootPos = baseBlockPos;
        if (baseType > 0) {
            ArrayList<RegistryObject<Item>> wildCropList = CropRegister.getWildCrops();
            wildCropList.remove(CropRegister.rice_item);
            crop = baseType == 1 ?
                    CropRegister.rice.get() : Block.byItem(wildCropList.get(random.nextInt(wildCropList.size())).get());
            if (baseType == 3)
                crop = CropRegister.chili.get();
            BlockState simpleCrop =
                    crop.defaultBlockState().setValue(BlockCuisineCrops.AGE, ((CropBlock) crop).getMaxAge());
            int xOffset = 0;
            int zOffset = 0;

            if (baseType == 3) {

                int height = (level.getBiome(baseBlockPos).is(new ResourceLocation("soul_sand_valley"))) ?
                        level.dimensionType().height() : 31;
                for (int j =height-1; j>0; j--) {
                    tryGenerateRootPos = tryGenerateRootPos.below();
                    if (j < 5)
                        return false;
                    else if (level.isEmptyBlock(tryGenerateRootPos)
                            && level.getBlockState(tryGenerateRootPos.below()).is(BlockTags.SOUL_SPEED_BLOCKS))
                        break;
                }
            }
            int TryTimes = 4;
            for (int times = TryTimes; times > 0; times--) {
                //                mutableBlockPos.move(xOffset,yOffset,zOffset);
                tryGenerateRootPos = tryGenerateRootPos.offset(xOffset, 0, zOffset);
                if (getCropType(level, tryGenerateRootPos) != baseType
                        || (xOffset == zOffset && times < TryTimes))
                    break;

                if (baseType == 1) {
                    // if (crop.canSustainPlant(crop.defaultBlockState(), level, tryGenerateRootPos, Direction.EAST, (net.minecraftforge.common.IPlantable) crop.asItem()))
                    level.setBlock(baseBlockPos.below().below(),
                            Blocks.DIRT.defaultBlockState(), Block.UPDATE_CLIENTS);
                    level.setBlock(tryGenerateRootPos.below(), simpleCrop.setValue(BlockDoubleCrops.HALF, DoubleBlockHalf.LOWER), Block.UPDATE_CLIENTS);
                    level.setBlock(tryGenerateRootPos, simpleCrop.setValue(BlockDoubleCrops.HALF, DoubleBlockHalf.UPPER), Block.UPDATE_CLIENTS);
                } else if (baseType == 3) {
                    // if (crop.canSustainPlant(crop.defaultBlockState(), level, tryGenerateRootPos, Direction.EAST, (net.minecraftforge.common.IPlantable) crop.asItem()))

                    level.setBlock(tryGenerateRootPos, simpleCrop, Block.UPDATE_CLIENTS);
                } else {
                    level.setBlock(tryGenerateRootPos.below(), Blocks.FARMLAND.defaultBlockState(), Block.UPDATE_CLIENTS);
                    if (crop instanceof BlockDoubleCrops) {
                        if (!level.isEmptyBlock(tryGenerateRootPos.above())
                                && !level.getBlockState(tryGenerateRootPos.above()).canBeReplaced(Fluids.WATER))
                            break;
                        level.setBlock(tryGenerateRootPos, simpleCrop.setValue(BlockDoubleCrops.HALF, DoubleBlockHalf.LOWER), Block.UPDATE_CLIENTS);
                        level.setBlock(tryGenerateRootPos.above(), simpleCrop.setValue(BlockDoubleCrops.HALF, DoubleBlockHalf.UPPER), Block.UPDATE_CLIENTS);
                    } else
                        level.setBlock(tryGenerateRootPos, simpleCrop, Block.UPDATE_CLIENTS);
                }
                i++;
                xOffset = random.nextInt(3) - 1;
                //                int yOffset = random.nextInt(3) - 1;
                zOffset = random.nextInt(3) - 1;
            }
        }
        Cuisine.logger("CropFarmland Try Place in" + level.getBlockState(tryGenerateRootPos.below()), tryGenerateRootPos);
        Cuisine.logger("尝试放置农作物"+ (i > 0)+ "，种类为" + baseType+""+ crop+ "，次数为" + i);

        Cuisine.logger(System.currentTimeMillis()-a+"ms");
        return i > 0;
    }

    private int getCropType(WorldGenLevel level, BlockPos baseBlockPos) {
        int type = 0;
        BlockState state = level.getBlockState(baseBlockPos);
        BlockState state2 = level.getBlockState(baseBlockPos.below());
        // if(!level.canSeeSky(baseBlockPos))
        if (!level.isEmptyBlock(baseBlockPos) && !(state.getBlock() instanceof TallGrassBlock))
            type = -1;
        else if (level.isWaterAt(baseBlockPos.below())
                && !level.isEmptyBlock(baseBlockPos.below().below())
                && !level.isWaterAt(baseBlockPos.below().below())) {
            type = 1;
        } else if (level.getBlockState(baseBlockPos.below()).is(BlockTags.DIRT))
            type = 2;
        else if (level.getBiome(baseBlockPos).is(BiomeTags.IS_NETHER)
            // && level.getBlockState(baseBlockPos.below()).is(BlockTags.SOUL_SPEED_BLOCKS)
        ) {
            type = 3;
        } else
            type = -1;
        return type;
    }
}
