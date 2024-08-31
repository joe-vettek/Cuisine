package xueluoanping.cuisine.data.tag;

import javax.annotation.Nullable;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;

import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import xueluoanping.cuisine.register.*;
import xueluoanping.cuisine.tag.CuisineTags;
import xueluoanping.cuisine.tag.ForgeTags;

import java.util.concurrent.CompletableFuture;

public class TagsDataProvider extends BlockTagsProvider {
    public TagsDataProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output,lookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.registerModTags();
        this.registerMinecraftTags();
        this.registerForgeTags();

        this.registerBlockMineables();
    }

    protected void registerBlockMineables() {
        tag(BlockTags.MINEABLE_WITH_AXE).add(
				BlockEntityRegister.basin.get()
        );

        tag(BlockTags.MINEABLE_WITH_AXE).add(
                BlockRegister.bamboo.get(),
                BlockRegister.bamboo_plant.get()
        );
    }

    protected void registerMinecraftTags() {
        tag(BlockTags.BAMBOO_PLANTABLE_ON).add(
                BlockRegister.bamboo_plant.get());
        tag(BlockTags.BAMBOO_PLANTABLE_ON).add(
                BlockRegister.bamboo_root.get());


    }

    protected void registerForgeTags() {

    }

    protected void registerModTags() {
		tag(CuisineTags.henon_bamboo_plamtable_on).add(
				BlockRegister.bamboo_root.get());

		tag(CuisineTags.bamboo_root_spread_on).addTags(BlockTags.DIRT);
		tag(CuisineTags.bamboo_root_spread_on).addTags(Tags.Blocks.COBBLESTONES_MOSSY);
		tag(CuisineTags.bamboo_root_spread_on).addTags(Tags.Blocks.GRAVELS);
    }


}
