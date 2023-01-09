package xueluoanping.cuisine.data.model;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.register.BlockRegister;
import xueluoanping.cuisine.register.CropRegister;
import xueluoanping.cuisine.register.IngredientRegister;
import xueluoanping.cuisine.register.ItemRegister;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ItemModelProvider extends net.minecraftforge.client.model.generators.ItemModelProvider {


    public static final String GENERATED = "item/generated";
    public static final String HANDHELD = "item/handheld";

    public ItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Cuisine.MODID, existingFileHelper);
    }

    private String blockName(Supplier<? extends Block> block) {
        return block.get().getRegistryName().getPath();
    }

    @Override
    protected void registerModels() {
        withExistingParent(blockName(BlockRegister.bamboo_root), modLoc("block/" + blockName(BlockRegister.bamboo_root)));

        ArrayList<RegistryObject<Item>> itemList = new ArrayList<>();
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
        IngredientRegister.DRItems.getEntries().forEach(itemRegistryObject -> {
            Item item = itemRegistryObject.get();
            withExistingParent(itemName(item), GENERATED).texture("layer0", resourceMaterial(itemName(item)));
        });
    }

    private String itemName(Item item) {
        return item.getRegistryName().getPath();
    }

    public ResourceLocation resourceItem(String path) {
        return new ResourceLocation(Cuisine.MODID, "item/" + path);
    }
    public ResourceLocation resourceMaterial(String path) {
        return new ResourceLocation(Cuisine.MODID, "item/material/" + path);
    }

}
