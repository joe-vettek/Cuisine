package xueluoanping.cuisine.datagen;

import static snownee.kiwi.data.provider.TagsProviderHelper.*;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import snownee.kiwi.data.provider.KiwiBlockTagsProvider;
import xueluoanping.cuisine.Cuisine;

public class CommonBlockTagsProvider extends KiwiBlockTagsProvider {

	public CommonBlockTagsProvider(DataGenerator pGenerator, ExistingFileHelper existingFileHelper) {
		super(pGenerator, Cuisine.MODID, existingFileHelper);
	}


	@Override
	protected void addTags() {
		getModEntries(modId, registry).forEach(
				$ -> processTools($, false));
//		this.tag("WATER").add(FluidModule.cuisine_juice,FluidModule.FLOWING_cuisine_juice);
	}

}
