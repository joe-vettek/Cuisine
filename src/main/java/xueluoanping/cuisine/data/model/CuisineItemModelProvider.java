package xueluoanping.cuisine.data.model;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.DynamicFluidContainerModel;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.client.model.generators.loaders.DynamicFluidContainerModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.internal.versions.neoforge.NeoForgeVersion;
import net.neoforged.neoforge.registries.DeferredHolder;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.register.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class CuisineItemModelProvider extends ItemModelProvider {


    public static final String GENERATED = "item/generated";
    public static final String HANDHELD = "item/handheld";

    public CuisineItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    private String blockName(Supplier<? extends Block> block) {
        return BuiltInRegistries.BLOCK.getKey(block.get()).getPath();
    }

    @Override
    protected void registerModels() {

        List.of(BlockRegister.bamboo_root,
                BlockRegister.tofu_block,
                BlockEntityRegister.drinkro,
                BlockEntityRegister.jar).forEach(
                b -> simpleParent(blockName(b))
        );
        //
        // DynamicFluidContainerModel
        for (var entry : FluidRegister.ITEMS.getEntries()) {
            // withExistingParent(itemName(entry.value()), GENERATED)
            //         .texture("layer0", Cuisine.rl("item/bucket").toString())
            //         .texture("layer1", Cuisine.rl("item/bucket_overlay").toString());
            withExistingParent(itemName(entry.value()), ResourceLocation.fromNamespaceAndPath(NeoForgeVersion.MOD_ID, "item/bucket"))
                    .customLoader(DynamicFluidContainerModelBuilder::begin)
                    .fluid(((BucketItem) entry.get()).content);

        }


        ArrayList<DeferredHolder<Item, ? extends Item>> itemList = new ArrayList<>();
        itemList.addAll(ItemRegister.DRItems.getEntries());
        // items.remove(ItemRegister.iron_spatula.);
        itemList.addAll(CropRegister.DRBlockItems.getEntries());
        itemList.add(BlockEntityRegister.mortar_item);
        itemList.add(BlockEntityRegister.plate_item);
        for (DeferredHolder<Item, ? extends Item> item0 : itemList) {
            if (item0.equals(ItemRegister.iron_spatula))
                continue;
            if (item0.equals(ItemRegister.wok))
                continue;
            if (item0.equals(ItemRegister.spice_bottle))
                continue;
            if (item0.equals(ItemRegister.cubed))
                continue;
            Item item = item0.get();
            withExistingParent(itemName(item), HANDHELD).texture("layer0", resourceItem(itemName(item)));
        }


        List.of(ItemRegister.cubed).forEach(itemRegistryObject -> {
            Item item = itemRegistryObject.get();
            withExistingParent(itemName(item), GENERATED).texture("layer0", resourceMaterial(itemName(item)));
        });

        BlockEntityRegister.basinItemColored.forEach(itemRegistryObject -> {

            withExistingParent(resourceItem(BuiltInRegistries.ITEM.getKey(itemRegistryObject.get()).getPath()).getPath(),
                    BlockStatesDataProvider.resourceBlock(BuiltInRegistries.BLOCK.getKey(Block.byItem(itemRegistryObject.get())).getPath()));
            // BlockEntityRegister.colorBlockMap.get(dyeColor).getRegistryName()
        });
        // withExistingParent(resourceItem(BlockEntityRegister.fire_pit_item.get().getRegistryName().getPath()).getPath(),
        // 		BlockStatesDataProvider.resourceBlock(BlockEntityRegister.fire_pit.get().getRegistryName().getPath()));
        // // BlockEntityRegister.
        // withExistingParent(resourceItem(BlockEntityRegister.barbecue_rack_item.get().getRegistryName().getPath()).getPath(),
        // 		BlockStatesDataProvider.resourceBlock(BlockEntityRegister.barbecue_rack.get().getRegistryName().getPath()));

        registerExistingCuisineBlockItem(BlockEntityRegister.fire_pit_item);
        registerExistingCuisineBlockItem(BlockEntityRegister.barbecue_rack_item);
        registerExistingCuisineBlockItem(BlockEntityRegister.wok_on_fire_pit_item);
    }

    private void simpleParent(String s) {
        withExistingParent(s, modLoc("block/" + s));
    }

    private void registerExistingCuisineBlockItem(DeferredHolder<Item, BlockItem> registryObject) {
        withExistingParent(resourceItem(registryObject.getId().getPath()).getPath(),
                BlockStatesDataProvider.resourceBlock(BuiltInRegistries.BLOCK.getKey(Block.byItem(registryObject.get())).getPath()));

    }

    private String itemName(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).getPath();
    }

    public ResourceLocation resourceItem(String path) {
        return Cuisine.rl("item/" + path);
    }

    public ResourceLocation resourceMaterial(String path) {
        return Cuisine.rl("item/material/" + path);
    }

}
