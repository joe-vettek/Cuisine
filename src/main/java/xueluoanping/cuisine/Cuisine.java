package xueluoanping.cuisine;


import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xueluoanping.cuisine.config.ClientConfig;
import xueluoanping.cuisine.config.General;
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


    public Cuisine(IEventBus modEventBus, ModContainer modContainer) {
        FMLLoader.getLoadingModList().getMods().forEach((info) -> {
            logger(info.getModId() + "" + MODID);
        });

        ItemRegister.DRItems.register(modEventBus);
        IngredientRegister.DRItems.register(modEventBus);

        BlockRegister.DRBlocks.register(modEventBus);
        BlockRegister.DRBlockItems.register(modEventBus);

        CropRegister.DRBlocks.register(modEventBus);
        CropRegister.DRBlockItems.register(modEventBus);


        BlockEntityRegister.DREntityBlocks.register(modEventBus);
        BlockEntityRegister.DREntityBlockItems.register(modEventBus);
        BlockEntityRegister.DRBlockEntities.register(modEventBus);

        FluidRegister.FLUIDS.register(modEventBus);
        FluidRegister.BLOCKS.register(modEventBus);
        FluidRegister.ITEMS.register(modEventBus);
        FluidRegister.FLUID_TYPES.register(modEventBus);


        FeatureRegister.DRFeatures.register(modEventBus);
        FeatureRegister.DRConfigured.register(modEventBus);
        FeatureRegister.DRPlaced.register(modEventBus);

        RecipeRegister.DRRecipeSerializer.register(modEventBus);
        RecipeRegister.DRRecipeType.register(modEventBus);

        GlobalLootModifierRegistry.GLM.register(modEventBus);
        //        config
        modContainer.registerConfig(ModConfig.Type.COMMON, General.COMMON_CONFIG);
        if (Platform.isPhysicalClient())
            modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.CLIENT_CONFIG);

        ModList.get().isLoaded("create");
    }


    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(Cuisine.MODID, path);
    }

}
