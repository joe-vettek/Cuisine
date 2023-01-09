package xueluoanping.cuisine.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.data.lang.Lang_EN;
import xueluoanping.cuisine.data.lang.Lang_ZH;
import xueluoanping.cuisine.data.loot.CuisineLootTableProvider;
import xueluoanping.cuisine.data.loot.GLMProvider;
import xueluoanping.cuisine.data.material.SimpleMP;
import xueluoanping.cuisine.data.model.BlockStatesDataProvider;
import xueluoanping.cuisine.data.model.ItemModelProvider;
import xueluoanping.cuisine.data.tag.CuisineItemTagsProvider;
import xueluoanping.cuisine.data.tag.TagsDataProvider;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class start {
    public final static String MODID = Cuisine.MODID;

    @SubscribeEvent
    public static void dataGen(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        if (event.includeServer()) {
            Cuisine.logger("Generate recipe");
            // CraftingHelper.register(ConfigCondition.Serializer.INSTANCE);
            // RecipeRegister.registerRecipeSerialziers(null);
            generator.addProvider(new RecipeDataProvider(generator));

            TagsDataProvider blockTags = new TagsDataProvider(generator, Cuisine.MODID, helper);
            generator.addProvider(blockTags);
            generator.addProvider(new CuisineItemTagsProvider(generator, blockTags, MODID, helper));

            generator.addProvider(new CuisineLootTableProvider(generator));
            generator.addProvider(new GLMProvider(generator, MODID));

            generator.addProvider(new Lang_EN(generator));
            generator.addProvider(new Lang_ZH(generator));

            generator.addProvider(new BlockStatesDataProvider(generator, helper));
            generator.addProvider(new ItemModelProvider(generator, helper));

            generator.addProvider(new SimpleMP(generator));
            // try {
            //     // File file=new File("");
            //     // String content= FileUtils.readFileToString(file,"UTF-8");
            //     // Cuisine.logger(content);
            // } catch (Exception ex) {
            //     ex.printStackTrace();
            // }
            //            generator.addProvider(new ItemTags(generator, blockTags, Cuisine.MODID, helper));
        } else if (event.includeClient()) {
            generator.addProvider(new BlockStatesDataProvider(generator, helper));
            //            BlockStates blockStates = new BlockStates(generator, helper);
            //            generator.addProvider(blockStates);
            //            generator.addProvider(new ItemModels(generator, blockStates.models().existingFileHelper));
        }


    }
}
