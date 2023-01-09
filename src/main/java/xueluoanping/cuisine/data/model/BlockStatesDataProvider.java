package xueluoanping.cuisine.data.model;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.block.nature.BlockCuisineCrops;
import xueluoanping.cuisine.block.nature.crop.BlockTomato;
import xueluoanping.cuisine.register.BlockRegister;
import xueluoanping.cuisine.register.CropRegister;
import xueluoanping.cuisine.register.ItemRegister;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockStatesDataProvider extends BlockStateProvider {


    public BlockStatesDataProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Cuisine.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(BlockRegister.bamboo_root.get());


        // this.customStageBlock(CropRegister.tomato.get(), resourceBlock("cross_crop"), "crop", BlockCuisineCrops.AGE, Arrays.asList(0,0, 1,1, 2,2,2, 3 ));

        ArrayList<RegistryObject<Block>> cropBlockList = new ArrayList<>();
        cropBlockList.addAll(CropRegister.DRBlocks.getEntries());
        cropBlockList.remove(CropRegister.rice);
        cropBlockList.remove(CropRegister.corn);
        cropBlockList.remove(CropRegister.cucumber);
        cropBlockList.forEach(crop -> {
            this.customStageBlock(crop.get(), resourceBlock("cross_crop"), "crop", BlockCuisineCrops.AGE, Arrays.asList(0, 0, 1, 1, 2, 2, 2, 3));
        });
    }


    // Thanks vectorwing，great work
    // I am not proud of this method... But hey, it's runData. Only I shall have to deal with it.
    public void customStageBlock(Block block, @Nullable ResourceLocation parent, String textureKey, IntegerProperty ageProperty, List<Integer> suffixes, Property<?>... ignored) {
        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    int ageSuffix = state.getValue(ageProperty);
                    String stageName = blockName(block) + "_stage_";
                    stageName += suffixes.isEmpty() ? ageSuffix : suffixes.get(Math.min(suffixes.size() - 1, ageSuffix));
                    Cuisine.logger(stageName);
                    if (parent == null) {
                        return ConfiguredModel.builder()
                                .modelFile(models().cross(stageName, resourceBlock(stageName))).build();
                    }
                    return ConfiguredModel.builder()
                            .modelFile(models().singleTexture(stageName, parent, textureKey, resourceBlock(stageName))).build();
                }, ignored);
    }

    private String blockName(Block block) {
        return block.getRegistryName().getPath();
    }

    public ResourceLocation resourceBlock(String path) {
        return new ResourceLocation(Cuisine.MODID, "block/" + path);
    }
}
