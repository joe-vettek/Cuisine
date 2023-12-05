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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xueluoanping.cuisine.config.ClientConfig;
import xueluoanping.cuisine.config.General;
import xueluoanping.cuisine.data.RecipeDataProvider;
import xueluoanping.cuisine.data.material.SimpleMP;
import xueluoanping.cuisine.data.tag.CuisineItemTagsProvider;
import xueluoanping.cuisine.data.tag.TagsDataProvider;
import xueluoanping.cuisine.data.lang.Lang_EN;
import xueluoanping.cuisine.data.lang.Lang_ZH;
import xueluoanping.cuisine.data.loot.CuisineLootTableProvider;
import xueluoanping.cuisine.data.loot.GLMProvider;
import xueluoanping.cuisine.data.model.BlockStatesDataProvider;
import xueluoanping.cuisine.data.model.ItemModelProvider;
import xueluoanping.cuisine.register.*;
import xueluoanping.cuisine.util.Platform;

@Mod(Cuisine.MODID)
public final class Cuisine {
    public static final String MODID = "cuisine";
    public static final String NAME = "Cuisine Inherited";

    public static Logger LOGGER = LogManager.getLogger(Cuisine.NAME);
    public static final CreativeModeTab CREATIVE_TAB = new CreativeModeTab("cuisine") {
        @Override
        public ItemStack makeIcon() {
            return ItemRegister.kitchen_knife.get().getDefaultInstance();
        }

        @Override
        public void fillItemList(NonNullList<ItemStack> itemStackNonNullList) {
            super.fillItemList(itemStackNonNullList);
        }
    };
    private static boolean DebugMode = false;

	// 开发环境未读取config之前可能用不了
    public static void logger(Object... x) {

        // if (General.bool.get())
		{
            StringBuilder output = new StringBuilder();
            for (Object i : x) {
                output.append("，【").append(i).append("】");
            }
            LOGGER.info(output.toString().substring(1));
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
        // MinecraftForge.EVENT_BUS.register(this);
        //        try to check target mod is loading,
        //        if not we not register mod event bus
        FMLLoader.getLoadingModList().getMods().forEach((info) -> {
            logger(info.getModId() + "" + MODID);
        });

        ItemRegister.DRItems.register(FMLJavaModLoadingContext.get().getModEventBus());
        IngredientRegister.DRItems.register(FMLJavaModLoadingContext.get().getModEventBus());

        BlockRegister.DRBlocks.register(FMLJavaModLoadingContext.get().getModEventBus());
        BlockRegister.DRBlockItems.register(FMLJavaModLoadingContext.get().getModEventBus());

        CropRegister.DRBlocks.register(FMLJavaModLoadingContext.get().getModEventBus());
        CropRegister.DRBlockItems.register(FMLJavaModLoadingContext.get().getModEventBus());


        BlockEntityRegister.DREntityBlocks.register(FMLJavaModLoadingContext.get().getModEventBus());
        BlockEntityRegister.DREntityBlockItems.register(FMLJavaModLoadingContext.get().getModEventBus());
        BlockEntityRegister.DRBlockEntities.register(FMLJavaModLoadingContext.get().getModEventBus());

        FluidRegister.DRFluids.register(FMLJavaModLoadingContext.get().getModEventBus());
        FluidRegister.DRFluidBlocks.register(FMLJavaModLoadingContext.get().getModEventBus());
        FluidRegister.DRFluidBuckets.register(FMLJavaModLoadingContext.get().getModEventBus());

        FeatureRegister.DRFeatures.register(FMLJavaModLoadingContext.get().getModEventBus());
        FeatureRegister.DRConfigured.register(FMLJavaModLoadingContext.get().getModEventBus());
        FeatureRegister.DRPlaced.register(FMLJavaModLoadingContext.get().getModEventBus());

        RecipeRegister.DRRecipeSerializer.register(FMLJavaModLoadingContext.get().getModEventBus());
        RecipeRegister.DRRecipeType.register(FMLJavaModLoadingContext.get().getModEventBus());

        GlobalLootModifierRegistry.GLM.register(FMLJavaModLoadingContext.get().getModEventBus());
        //        config
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, General.COMMON_CONFIG);
        if (Platform.isPhysicalClient())
            ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.CLIENT_CONFIG);
        ModList.get().isLoaded("create");
    }

    public static ResourceLocation res(String n) {
        return new ResourceLocation(MODID, n);
    }

    public static String str(String n) {
        return MODID + ":" + n;
    }
}
