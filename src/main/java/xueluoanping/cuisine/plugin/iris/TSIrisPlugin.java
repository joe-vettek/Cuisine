package xueluoanping.cuisine.plugin.iris;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.irisshaders.iris.Iris;
import net.irisshaders.iris.api.v0.IrisApi;
import net.irisshaders.iris.shaderpack.materialmap.WorldRenderingSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.registries.DeferredHolder;
import xueluoanping.cuisine.block.nature.BlockDoubleCrops;
import xueluoanping.cuisine.register.BlockRegister;
import xueluoanping.cuisine.register.CropRegister;

import java.util.List;

public class TSIrisPlugin {

    private static Object2IntMap<BlockState> blockStateIds = null;

    private static String shaderpack = null;

    public static void checkReload() {
        if (Iris.getIrisConfig() != null) {
            String nowshaderpack = Iris.getIrisConfig().getShaderPackName().orElse(null);
            // if (!Objects.equals(nowshaderpack, shaderpack))
            {
                if (IrisApi.getInstance().isShaderPackInUse()) {
                    if (WorldRenderingSettings.INSTANCE.getBlockStateIds() != blockStateIds) {
                        blockStateIds = WorldRenderingSettings.INSTANCE.getBlockStateIds();
                        if (blockStateIds != null) {
                            shaderpack = nowshaderpack;
                            simpleCopyAddGrassUpper(CropRegister.rice.get());

                            var upper = Blocks.OAK_LEAVES.defaultBlockState()
                                    // .setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER)
                                    ;
                            var lower = Blocks.OAK_LEAVES.defaultBlockState()
                                    // .setValue(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)
                                    ;

                            for (DeferredHolder<Block, ? extends Block> allCropBlock : CropRegister.getAllCropBlocks()) {
                                if (allCropBlock.get() instanceof BlockDoubleCrops) {
                                    if (allCropBlock != CropRegister.rice
                                            && allCropBlock != CropRegister.cucumber
                                    ) {
                                        for (BlockState possibleState : allCropBlock.get().getStateDefinition().getPossibleStates()) {
                                            simpleCopyAdd(BlockDoubleCrops.isUpper(possibleState) ? upper : lower, possibleState);
                                        }
                                    }
                                } else simpleCopyAddSmallWheatLike(allCropBlock.value());
                            }

                        }

                    }
                }
            }
        }
    }

    public static void simpleCopyAddSmallWheatLike(Block block) {
        simpleCopyAdd(Blocks.WHEAT.defaultBlockState(), block);
    }

    public static void simpleCopyAddGrassUpper(Block block) {
        if (shaderpack != null && !shaderpack.toLowerCase().contains("photon"))
            simpleCopyAdd(Blocks.TALL_GRASS.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER), block);
        else simpleCopyAdd(Blocks.VINE.defaultBlockState(), block);
        // simpleCopyAdd(Blocks.OAK_LEAVES.defaultBlockState(), block);
        // simpleCopyAdd(Blocks.TALL_GRASS.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER), block);

    }

    public static void simpleCopyAdd(BlockState state, Block block) {
        if (blockStateIds != null) {
            int idsInt = blockStateIds.getInt(state);
            if (idsInt > 0) {
                block.getStateDefinition().getPossibleStates()
                        .forEach(b -> blockStateIds.putIfAbsent(b, idsInt));
            }
        }
    }

    public static void simpleCopyAdd(BlockState state, BlockState blockState) {
        if (blockStateIds != null) {
            int idsInt = blockStateIds.getInt(state);
            if (idsInt > 0) {
                blockStateIds.putIfAbsent(blockState, idsInt);
            }
        }
    }

}
