package xueluoanping.cuisine.client;


import com.google.common.collect.ImmutableSet;

import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredHolder;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.client.color.item.BucketItemColors;
import xueluoanping.cuisine.client.color.item.IngreItemColors;
import xueluoanping.cuisine.client.color.item.SpiceBottletItemColors;
import xueluoanping.cuisine.client.renderer.BER.*;
import xueluoanping.cuisine.fluids.TeaFluidType;
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
    public static void onRegisterClientExtensionsEvent(RegisterClientExtensionsEvent event) {

        FluidRegister.FLUIDS.getEntries().forEach(holder -> {

            if (holder.get().getFluidType() instanceof TeaFluidType teaFluidType
                    && holder.get() instanceof BaseFlowingFluid.Source baseFlowingFluid)
                event.registerFluidType(TeaFluidType.getIClientFluidTypeExtensions(teaFluidType), baseFlowingFluid.getFluidType());
        });

    }

    @SubscribeEvent
    public static void onClientEvent(FMLClientSetupEvent event) {
        Cuisine.logger("Register Client");
        event.enqueueWork(() -> {

            ArrayList<DeferredHolder<Block, ? extends Block>> cropBlockList = new ArrayList<>();
            cropBlockList.addAll(CropRegister.DRBlocks.getEntries());
            cropBlockList.forEach(crop -> {
                ItemBlockRenderTypes.setRenderLayer(crop.get(), RenderType.cutoutMipped());
            });

            // ItemBlockRenderTypes.setRenderLayer(BlockRegister.bamboo.get(), RenderType.solid());
            // ItemBlockRenderTypes.setRenderLayer(BlockRegister.bamboo_plant.get(), ClientSetup::isGlassLanternValidLayer);

            ItemBlockRenderTypes.setRenderLayer(FluidRegister.CUISINE_JUICE.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(FluidRegister.CUISINE_JUICE_FLOWING.get(), RenderType.translucent());

          

            // ItemProperties.register(BlockEntityRegister.hide_fire_pit_item.get(), new ResourceLocation(Cuisine.MODID, "component"), ClientSetup::firepit);

        });
    }


    @SubscribeEvent

    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        Cuisine.logger("Register Renderer");
        event.registerBlockEntityRenderer(BlockEntityRegister.basin_entity_type.get(), TESRBasin::new);
        event.registerBlockEntityRenderer(BlockEntityRegister.basin_colored_entity_type.get(), TESRBasinColored::new);
        event.registerBlockEntityRenderer(BlockEntityRegister.chopping_board_entity_type.get(), TESRChoppingBoard::new);

        event.registerBlockEntityRenderer(BlockEntityRegister.mill_entity_type.get(), TESRMill::new);
        // event.registerBlockEntityRenderer(BlockEntityRegister.fire_pit_entity_type.get(), TESRFirePit::new);
        event.registerBlockEntityRenderer(BlockEntityRegister.barbecue_rack_entity_type.get(), TESRBarbecueRack::new);
        event.registerBlockEntityRenderer(BlockEntityRegister.wok_on_fire_pit_entity_type.get(), TESWok::new);
    }

    //    go to BlockColors class for more help
    @SubscribeEvent

    public static void onColorSetup(RegisterColorHandlersEvent.Block event) {
        event.register((state, blockAndTintGetter, pos, tintIndex) -> {
            return blockAndTintGetter != null && pos != null ?
                    BiomeColors.getAverageFoliageColor(blockAndTintGetter, pos) : -1;
        }, BlockRegister.bamboo.get(), BlockRegister.bamboo_plant.get(), BlockRegister.bamboo_branch_leaves.get());
        // new BlockColor(){
        //     @Override
        //     public int getColor(BlockState p_92567_, @Nullable BlockAndTintGetter p_92568_, @Nullable BlockPos p_92569_, int p_92570_) {
        //         return 0;
        //     };
        // }
    }

    @SubscribeEvent

    public static void onColorItemSetup(RegisterColorHandlersEvent.Item event) {
        event.register(new IngreItemColors(), ItemRegister.getIngredients().toArray(new Item[0]));

        var buckColors = new BucketItemColors();
        var buckColors2 = new SpiceBottletItemColors();
        FluidRegister.ITEMS.getEntries().forEach(itemRegistryObject -> event.register(buckColors, itemRegistryObject.value()));
        event.register(buckColors2, ItemRegister.spice_bottle.value());
    }

}
