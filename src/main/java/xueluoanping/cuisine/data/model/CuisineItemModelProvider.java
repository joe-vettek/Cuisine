package xueluoanping.cuisine.data.model;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.register.BlockEntityRegister;
import xueluoanping.cuisine.register.BlockRegister;
import xueluoanping.cuisine.register.CropRegister;
import xueluoanping.cuisine.register.ItemRegister;

import java.util.ArrayList;
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
        withExistingParent(blockName(BlockRegister.bamboo_root), modLoc("block/" + blockName(BlockRegister.bamboo_root)));

        ArrayList<DeferredHolder<Item, ? extends Item>> itemList = new ArrayList<>();
        itemList.addAll(ItemRegister.DRItems.getEntries());
        // items.remove(ItemRegister.iron_spatula.);
        itemList.addAll(CropRegister.DRBlockItems.getEntries());
        itemList.forEach(item0 -> {
            // try {
            // generate data not need speed
            if (item0.equals(ItemRegister.iron_spatula))
                return;
            Item item = item0.get();
            withExistingParent(itemName(item), HANDHELD).texture("layer0", resourceItem(itemName(item)));
            // } catch (Exception e) {
            // 	e.printStackTrace();
            // }
        });
        ItemRegister.DRItems.getEntries().forEach(itemRegistryObject -> {
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
