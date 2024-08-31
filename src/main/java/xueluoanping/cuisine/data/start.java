package xueluoanping.cuisine.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.data.lang.Lang_EN;
import xueluoanping.cuisine.data.lang.Lang_ZH;
import xueluoanping.cuisine.data.loot.CuisineLootTableProvider;
import xueluoanping.cuisine.data.loot.GLMProvider;
import xueluoanping.cuisine.data.material.SimpleMP;
import xueluoanping.cuisine.data.model.BlockStatesDataProvider;
import xueluoanping.cuisine.data.model.CuisineItemModelProvider;
import xueluoanping.cuisine.data.tag.CuisineItemTagsProvider;
import xueluoanping.cuisine.data.tag.TagsDataProvider;

import java.util.concurrent.CompletableFuture;


@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class start {
    public final static String MODID = Cuisine.MODID;

    @SubscribeEvent
    public static void dataGen(GatherDataEvent event) {

        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        if (event.includeServer()) {
            if (event.includeServer()) {
                Cuisine.logger("Generate recipe");
                // CraftingHelper.register(ConfigCondition.Serializer.INSTANCE);
                // RecipeRegister.registerRecipeSerialziers(null);
                generator.addProvider(event.includeServer(), new RecipeDataProvider(packOutput, lookupProvider));

                TagsDataProvider blockTags = new TagsDataProvider(packOutput, lookupProvider, Cuisine.MODID, helper);
                generator.addProvider(event.includeServer(), blockTags);
                generator.addProvider(event.includeServer(), new CuisineItemTagsProvider(packOutput, lookupProvider, blockTags.contentsGetter(), MODID, helper));

                generator.addProvider(event.includeServer(), new CuisineLootTableProvider(packOutput, lookupProvider));
                generator.addProvider(event.includeServer(), new GLMProvider(packOutput, lookupProvider, MODID));

                generator.addProvider(event.includeServer(), new Lang_EN(packOutput, helper));
                generator.addProvider(event.includeServer(), new Lang_ZH(packOutput, helper));

                generator.addProvider(event.includeServer(), new SimpleMP(generator));
                // try {
                //     // File file=new File("");
                //     // String content= FileUtils.readFileToString(file,"UTF-8");
                //     // Cuisine.logger(content);
                // } catch (Exception ex) {
                //     ex.printStackTrace();
                // }
                //            generator.addProvider(new ItemTags(generator, blockTags, Cuisine.MODID, helper));
            }
            if (event.includeClient()) {
                generator.addProvider(event.includeClient(), new BlockStatesDataProvider(packOutput, helper));
                generator.addProvider(event.includeClient(), new CuisineItemModelProvider(packOutput, MODID, helper));
                // generator.addProvider(new BlockStatesDataProvider(generator, helper));

                //            BlockStates blockStates = new BlockStates(generator, helper);
                //            generator.addProvider(blockStates);
                //            generator.addProvider(new ItemModels(generator, blockStates.models().existingFileHelper));
            }


        }
    }
}
