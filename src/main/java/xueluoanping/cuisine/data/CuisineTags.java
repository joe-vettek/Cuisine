package xueluoanping.cuisine.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import xueluoanping.cuisine.register.BlockRegister;
import xueluoanping.cuisine.register.ModContents;

import javax.annotation.Nullable;

public class CuisineTags extends BlockTagsProvider {
    public CuisineTags(DataGenerator generatorIn, String modId, @Nullable ExistingFileHelper existingFileHelper) {
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
                ModContents.basin
        );

        tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE).add(
                BlockRegister.bamboo.get(),
                BlockRegister.bamboo_plant.get()
        );

    }

    protected void registerMinecraftTags() {
        tag(net.minecraft.tags.BlockTags.BAMBOO_PLANTABLE_ON).add(
                BlockRegister.bamboo_plant.get());
        tag(net.minecraft.tags.BlockTags.BAMBOO_PLANTABLE_ON).add(
                BlockRegister.bamboo_root.get());
        tag(xueluoanping.cuisine.tag.CuisineTags.henon_bamboo_plamtable_on).add(
                BlockRegister.bamboo_root.get());
    }

    protected void registerForgeTags() {
    }

    protected void registerModTags() {
    }
}
