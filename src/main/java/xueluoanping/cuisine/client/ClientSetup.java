package xueluoanping.cuisine.client;


import com.google.common.collect.ImmutableSet;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.client.renderer.tesr.TESRBasinColored;
import xueluoanping.cuisine.client.renderer.tesr.TESRChoppingBoard;
import xueluoanping.cuisine.event.type.EnumFirePitState;
import xueluoanping.cuisine.client.renderer.tesr.TESRBasin;
import xueluoanping.cuisine.client.renderer.tesr.TESRFirePit;
import xueluoanping.cuisine.client.renderer.tesr.TESRMill;
import xueluoanping.cuisine.register.*;

import java.awt.*;
import java.util.*;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {


    // does the Glass Lantern render in the given layer (RenderType) - used as Predicate<RenderType> lambda for setRenderLayer

    public static boolean isGlassLanternValidLayer(RenderType layerToCheck) {
        return layerToCheck == RenderType.cutoutMipped() || layerToCheck == RenderType.translucent();
    }

    public static Set<RenderType> BLOCK_RENDER_TYPES = ImmutableSet.of(RenderType.solid(), RenderType.cutout(), RenderType.cutoutMipped(), RenderType.translucent());

    @SubscribeEvent
    public static void onClientEvent(FMLClientSetupEvent event) {
        Cuisine.logger("Register Client");
        event.enqueueWork(() -> {

            ArrayList<RegistryObject<Block>> cropBlockList = new ArrayList<>();
            cropBlockList.addAll(CropRegister.DRBlocks.getEntries());
            cropBlockList.forEach(crop -> {
                ItemBlockRenderTypes.setRenderLayer(crop.get(), RenderType.cutoutMipped());
            });


            ItemBlockRenderTypes.setRenderLayer(BlockRegister.bamboo.get(), RenderType.solid());
            ItemBlockRenderTypes.setRenderLayer(BlockRegister.bamboo_plant.get(), ClientSetup::isGlassLanternValidLayer);

            ItemBlockRenderTypes.setRenderLayer(FluidRegister.juice.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(FluidRegister.juice_flowing.get(), RenderType.translucent());

            BlockEntityRenderers.register(BlockEntityRegister.mill_entity_type.get(), TESRMill::new);
            BlockEntityRenderers.register(BlockEntityRegister.fire_pit_entity_type.get(), TESRFirePit::new);

			// ItemProperties.register(BlockEntityRegister.hide_fire_pit_item.get(), new ResourceLocation(Cuisine.MODID, "component"), ClientSetup::firepit);

        });
    }


    @SubscribeEvent

    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        Cuisine.logger("Register Renderer");
        event.registerBlockEntityRenderer(BlockEntityRegister.basin_entity_type.get(), TESRBasin::new);
		event.registerBlockEntityRenderer(BlockEntityRegister.basin_colored_entity_type.get(), TESRBasinColored::new);
		event.registerBlockEntityRenderer(BlockEntityRegister.chopping_board_entity_type.get(), TESRChoppingBoard::new);

    }

    //    go to BlockColors class for more help
    @SubscribeEvent

    public static void onColorSetup(ColorHandlerEvent.Block event) {
        event.getBlockColors().register((state, blockAndTintGetter, pos, tintIndex) -> {
            return blockAndTintGetter != null && pos != null ?
                    BiomeColors.getAverageFoliageColor(blockAndTintGetter, pos) : -1;
        }, BlockRegister.bamboo.get(), BlockRegister.bamboo_plant.get());
        // new BlockColor(){
        //     @Override
        //     public int getColor(BlockState p_92567_, @Nullable BlockAndTintGetter p_92568_, @Nullable BlockPos p_92569_, int p_92570_) {
        //         return 0;
        //     };
        // }
    }

    @SubscribeEvent

    public static void onColorItemSetup(ColorHandlerEvent.Item event) {
        event.getItemColors().register((itemStack, meta) -> {
            return Color.CYAN.getRGB();
        }, IngredientRegister.cubed.get());

    }
    
}
