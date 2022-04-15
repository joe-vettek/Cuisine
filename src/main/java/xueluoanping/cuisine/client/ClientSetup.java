package xueluoanping.cuisine.client;


import com.google.common.collect.ImmutableSet;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.api.type.EnumFirePitState;
import xueluoanping.cuisine.block.blockitem.BlockBasinItem;
import xueluoanping.cuisine.client.model.RetextureModel;
import xueluoanping.cuisine.client.renderer.tesr.TESRBasin;
import xueluoanping.cuisine.client.renderer.tesr.TESRFirePit;
import xueluoanping.cuisine.client.renderer.tesr.TESRMill;
import xueluoanping.cuisine.register.BlockEntityRegister;
import xueluoanping.cuisine.register.BlockRegister;
import xueluoanping.cuisine.register.FluidRegister;
import xueluoanping.cuisine.register.ModContents;

import java.util.Set;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {


    // does the Glass Lantern render in the given layer (RenderType) - used as Predicate<RenderType> lambda for setRenderLayer
    @OnlyIn(Dist.CLIENT)
    public static boolean isGlassLanternValidLayer(RenderType layerToCheck) {
        return layerToCheck == RenderType.cutoutMipped() || layerToCheck == RenderType.translucent();
    }

    public static Set<RenderType> BLOCK_RENDER_TYPES = ImmutableSet.of(RenderType.solid(), RenderType.cutout(), RenderType.cutoutMipped(), RenderType.translucent());

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onClientEvent(FMLClientSetupEvent event) {
        Cuisine.logger("Register Client");
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(ModContents.basin, BLOCK_RENDER_TYPES::contains);
            ItemBlockRenderTypes.setRenderLayer(BlockRegister.bamboo.get(), RenderType.solid());
            ItemBlockRenderTypes.setRenderLayer(BlockRegister.bamboo_plant.get(), ClientSetup::isGlassLanternValidLayer);

            ItemBlockRenderTypes.setRenderLayer(FluidRegister.juice.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(FluidRegister.juice_flowing.get(), RenderType.translucent());

            BlockEntityRenderers.register(BlockEntityRegister.mill_entity_type.get(), TESRMill::new);
            BlockEntityRenderers.register(BlockEntityRegister.fire_pit_entity_type.get(), TESRFirePit::new);
            ItemProperties.register(BlockEntityRegister.fire_pit_item.get(), new ResourceLocation(Cuisine.MODID, "component"),
                    EnumFirePitState::firepit);
            BlockBasinItem.INSTANT_UPDATE_TILES.add(ModContents.basinEntityType);
        });
    }


    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        Cuisine.logger("Register Renderer");
        event.registerBlockEntityRenderer(ModContents.basinEntityType, TESRBasin::new);

    }

    //    go to BlockColors class for more help
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onColorSetup(ColorHandlerEvent.Block event) {
        event.getBlockColors().register((state, blockAndTintGetter, pos, tintIndex) -> {
            return blockAndTintGetter != null && pos != null ? BiomeColors.getAverageFoliageColor(blockAndTintGetter, pos) : -1;
        }, BlockRegister.bamboo.get(), BlockRegister.bamboo_plant.get());

    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void registerModelLoader(ModelRegistryEvent event) {
        ModelLoaderRegistry.registerLoader(new ResourceLocation("kiwi:retexture"), RetextureModel.Loader.INSTANCE);
    }
}
