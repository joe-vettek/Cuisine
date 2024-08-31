package xueluoanping.cuisine.data.tag;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;

import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import xueluoanping.cuisine.register.BlockRegister;
import xueluoanping.cuisine.register.CropRegister;
import xueluoanping.cuisine.register.ItemRegister;
import xueluoanping.cuisine.tag.ForgeTags;

import java.util.concurrent.CompletableFuture;

public class CuisineItemTagsProvider extends ItemTagsProvider {

    public CuisineItemTagsProvider(
            PackOutput packOutput, CompletableFuture<HolderLookup.Provider> providerCompletableFuture, CompletableFuture<TagsProvider.TagLookup<Block>> tagLookupCompletableFuture,
            String modid, ExistingFileHelper existingFileHelper
    ) {
        super(packOutput, providerCompletableFuture, tagLookupCompletableFuture, modid, existingFileHelper);
    }


    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.registerForgeTags();
    }

    //
    private void registerForgeTags() {
        // Crop
        CropRegister.getAllCrops().forEach(crop -> {
            tag(Tags.Items.CROPS).add(crop.get());
        });
        tag(Tags.Items.CROPS).add(BlockRegister.bamboo_shoot.get());

        // Vegetables
        tag(ForgeTags.VEGETABLES).add(CropRegister.tomato_item.get())
                .add(CropRegister.chili_item.get())
                .add(CropRegister.scallion_item.get())
                .add(CropRegister.turnip_item.get())
                .add(CropRegister.chinese_cabbage_item.get())
                .add(CropRegister.lettuce_item.get())
                .add(CropRegister.cucumber_item.get())
                .add(CropRegister.green_pepper_item.get())
                .add(CropRegister.red_pepper_item.get())
                .add(CropRegister.leek_item.get())
                .add(CropRegister.onion_item.get())
                .add(CropRegister.eggplant_item.get())
                .add(CropRegister.spinach_item.get())
                .add(BlockRegister.bamboo_shoot.get());

        // Grain
        tag(ForgeTags.GRAIN).add(CropRegister.rice_item.get())
                .add(CropRegister.sesame_item.get())
                .add(CropRegister.soybean_item.get())
                .add(CropRegister.corn_item.get());

        // Salad Ingredients
        tag(ForgeTags.SALAD_INGREDIENTS).add(CropRegister.chinese_cabbage_item.get())
                .add(CropRegister.turnip_item.get())
                .add(CropRegister.lettuce_item.get())
                .add(CropRegister.corn_item.get())
                .add(CropRegister.cucumber_item.get())
                .add(CropRegister.red_pepper_item.get())
                .add(CropRegister.green_pepper_item.get());

        // Dough
        tag(ForgeTags.DOUGH).add(ItemRegister.dough.get());
        tag(ForgeTags.DOUGH_WHEAT).add(ItemRegister.dough.get());

        // Chinese Cabbage
        tag(ForgeTags.VEGETABLES_CABBAGE).add(CropRegister.chinese_cabbage_item.get());
        tag(ForgeTags.CROPS_CABBAGE).add(CropRegister.chinese_cabbage_item.get());
        tag(ForgeTags.SALAD_INGREDIENTS_CABBAGE).add(CropRegister.chinese_cabbage_item.get());

        // Tomato
        tag(ForgeTags.VEGETABLES_TOMATO).add(CropRegister.tomato_item.get());
        tag(ForgeTags.CROPS_TOMATO).add(CropRegister.tomato_item.get());

        // Onion
        tag(ForgeTags.VEGETABLES_ONION).add(CropRegister.onion_item.get());
        tag(ForgeTags.CROPS_ONION).add(CropRegister.onion_item.get());

        // Rice
        tag(ForgeTags.GRAIN_RICE).add(CropRegister.rice_item.get());
        tag(ForgeTags.CROPS_RICE).add(CropRegister.rice_item.get());
    }


}
