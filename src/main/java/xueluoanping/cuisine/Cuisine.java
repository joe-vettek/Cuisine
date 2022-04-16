package xueluoanping.cuisine;

import net.minecraft.core.NonNullList;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xueluoanping.cuisine.client.model.BlockDefinition;
import xueluoanping.cuisine.client.model.SimpleBlockDefinition;
import xueluoanping.cuisine.config.ClientConfig;
import xueluoanping.cuisine.config.General;
import xueluoanping.cuisine.data.RecipeDataProvider;
import xueluoanping.cuisine.data.TagsDataProvider;
import xueluoanping.cuisine.data.lang.Lang_EN;
import xueluoanping.cuisine.data.lang.Lang_ZH;
import xueluoanping.cuisine.data.model.BlockStatesDataProvider;
import xueluoanping.cuisine.data.model.ItemModelProvider;
import xueluoanping.cuisine.register.*;

import java.io.File;

@Mod(Cuisine.MODID)
public final class Cuisine {
    public static final String MODID = "cuisine";
    public static final String NAME = "Cuisine Inherited";

    public static Logger LOGGER = LogManager.getLogger(Cuisine.NAME);
    public static final CreativeModeTab CREATIVE_TAB = new CreativeModeTab("cuisine") {
        @Override
        public ItemStack makeIcon() {
            return ModContents.itemBasin.getDefaultInstance();
        }

        @Override
        public void fillItemList(NonNullList<ItemStack> itemStackNonNullList) {
            super.fillItemList(itemStackNonNullList);
        }
    };
    private static boolean DebugMode = false;

    public static void logger(Object... x) {
        if (General.bool.get()) {
            String output = "";
            for (Object i : x) {
                output = output + i;
            }
            LOGGER.info(output);
        }

    }


    public static ResourceLocation rl(String path) {
        return new ResourceLocation(Cuisine.MODID, path);
    }

    public Cuisine() {
//        forge run bus --> game running event
//        mod run bus --> game init event
//        used for mod init stage, for instance need to be not static method,  for other need to be static
//        annotation Mod.EventBusSubscriber also need static method
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
//        used for mod run stage
        MinecraftForge.EVENT_BUS.register(this);
//        try to check target mod is loading,
//        if not we not register mod event bus
        FMLLoader.getLoadingModList().getMods().forEach((info) -> {
            logger(info.getModId() + "" + MODID);
        });
        BlockRegister.DRBlocks.register(FMLJavaModLoadingContext.get().getModEventBus());
        BlockRegister.DRBlockItems.register(FMLJavaModLoadingContext.get().getModEventBus());

        BlockEntityRegister.DREntityBlocks.register(FMLJavaModLoadingContext.get().getModEventBus());
        BlockEntityRegister.DREntityBlockItems.register(FMLJavaModLoadingContext.get().getModEventBus());
        BlockEntityRegister.DRBlockEntities.register(FMLJavaModLoadingContext.get().getModEventBus());

        FluidRegister.DRFluids.register(FMLJavaModLoadingContext.get().getModEventBus());
        FluidRegister.DRFluidBlocks.register(FMLJavaModLoadingContext.get().getModEventBus());
        FluidRegister.DRFluidBuckets.register(FMLJavaModLoadingContext.get().getModEventBus());

        BiomeDecorateRegister.DRFeatures.register(FMLJavaModLoadingContext.get().getModEventBus());
        BiomeDecorateRegister.DRConfigured.register(FMLJavaModLoadingContext.get().getModEventBus());
        BiomeDecorateRegister.DRPlaced.register(FMLJavaModLoadingContext.get().getModEventBus());

        RecipeRegister.DRRecipeSerializer.register(FMLJavaModLoadingContext.get().getModEventBus());
        RecipeRegister.DRRecipeType.register(FMLJavaModLoadingContext.get().getModEventBus());


//        config
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, General.COMMON_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.CLIENT_CONFIG);
        ModList.get().isLoaded("create");
    }

    @SubscribeEvent
    public void init(FMLCommonSetupEvent event) {

        BlockDefinition.registerFactory(SimpleBlockDefinition.Factory.INSTANCE);
    }

    @SubscribeEvent
    public void dataGen(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        if (event.includeServer()) {
            Cuisine.logger("Generate recipe");
            TagsDataProvider blockTags = new TagsDataProvider(generator, Cuisine.MODID, helper);
            generator.addProvider(new RecipeDataProvider(generator));
            generator.addProvider(blockTags);
            generator.addProvider(new Lang_EN(generator));
            generator.addProvider(new Lang_ZH(generator));
            generator.addProvider(new BlockStatesDataProvider(generator, helper));

            generator.addProvider(new ItemModelProvider(generator, helper));

            try {
                File file=new File("C:\\Users\\Admin\\Downloads\\料理工艺-18\\src\\generated\\resources\\assets\\cuisine\\lang\\zh_cn.json");
                String content= FileUtils.readFileToString(file,"UTF-8");
                Cuisine.logger(content);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
//            generator.addProvider(new ItemTags(generator, blockTags, Cuisine.MODID, helper));
        } else if (event.includeClient()) {
            generator.addProvider(new BlockStatesDataProvider(generator, helper));
//            BlockStates blockStates = new BlockStates(generator, helper);
//            generator.addProvider(blockStates);
//            generator.addProvider(new ItemModels(generator, blockStates.models().existingFileHelper));
        }


    }

}
