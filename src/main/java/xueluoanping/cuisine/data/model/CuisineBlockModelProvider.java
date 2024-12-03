package xueluoanping.cuisine.data.model;


import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.client.model.generators.loaders.ObjModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.block.BlockPlate;
import xueluoanping.cuisine.block.firepit.BlockFirePit;
import xueluoanping.cuisine.register.BlockEntityRegister;

import java.util.List;

public class CuisineBlockModelProvider extends BlockModelProvider {


    public static final String BLOCK = "block/block";

    public CuisineBlockModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }


    @Override
    protected void registerModels() {

        for (BlockPlate.CuisineType cuisineType : BlockPlate.CuisineType.values()) {
            withExistingParent(resource("dish/"+cuisineType.getSerializedName()).getPath(), BLOCK)
                    .texture("particle", ResourceLocation.withDefaultNamespace("block/quartz_block_bottom"))
                    .customLoader(ObjModelBuilder::begin)
                    .flipV(true)
                    .automaticCulling(true)
                    .modelLocation(objResource("dish/" + cuisineType.getSerializedName()));
        }

        for (DeferredHolder<Block, ? extends BlockFirePit> blockDeferredHolder : List.of(BlockEntityRegister.barbecue_rack, BlockEntityRegister.wok_on_fire_pit, BlockEntityRegister.fire_pit)) {
            withExistingParent(blockDeferredHolder.getId().getPath(), BLOCK)
                    .texture("particle", ResourceLocation.withDefaultNamespace("block/cobblestone"))
                    .customLoader(ObjModelBuilder::begin)
                    .flipV(true)
                    .automaticCulling(true)
                    .modelLocation(objResource(blockDeferredHolder.getId().getPath()
                            .replace("wok_on_fire_pit","fire_pit_with_sticks")
                            .replace("barbecue_rack","fire_pit_with_sticks")));
        }


    }


    public ResourceLocation resource(String path) {
        return Cuisine.rl("block/" + path);
    }

    public ResourceLocation objResource(String path) {
        return Cuisine.rl("models/block/" + path+".obj");
    }

}
