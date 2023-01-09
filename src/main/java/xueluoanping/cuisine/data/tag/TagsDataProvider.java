package xueluoanping.cuisine.data.tag;

import javax.annotation.Nullable;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import xueluoanping.cuisine.register.*;
import xueluoanping.cuisine.tag.CuisineTags;
import xueluoanping.cuisine.tag.ForgeTags;

public class TagsDataProvider extends BlockTagsProvider {
    public TagsDataProvider(DataGenerator generatorIn, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.registerModTags();
        this.registerMinecraftTags();
        this.registerForgeTags();

        this.registerBlockMineables();
    }

    protected void registerBlockMineables() {
        tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_AXE).add(
				BlockEntityRegister.basin.get()
        );

        tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_AXE).add(
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
		tag(CuisineTags.bamboo_root_spread_on).addTags(Tags.Blocks.COBBLESTONE_MOSSY);
		tag(CuisineTags.bamboo_root_spread_on).addTags(Tags.Blocks.GRAVEL);
    }
}
