package xueluoanping.cuisine.register;

import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.wrapper.CombinedInvWrapper;
import net.neoforged.neoforge.registries.RegisterEvent;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.items.base.FluidContainerItem;
import xueluoanping.cuisine.items.base.ItemContainerItem;

import static xueluoanping.cuisine.Cuisine.MODID;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class ModContent {

    public static CreativeModeTab CREATIVE_TAB;

    @SubscribeEvent
    public static void creativeModeTabRegister(RegisterEvent event) {
        // TeaStory.logger(event.getRegistryKey(),BuiltInRegistries.BLOCK.entrySet().stream().toList().size());
        // BuiltInRegistries.BLOCK.entrySet().stream().filter(resourceKeyBlockEntry -> resourceKeyBlockEntry.getKey().toString().contains(TeaStory.MODID)).toList()
        if (event.getRegistryKey() == Registries.CREATIVE_MODE_TAB)
            event.register(Registries.CREATIVE_MODE_TAB, helper -> {
                CREATIVE_TAB = CreativeModeTab.builder().icon(() -> new ItemStack(ItemRegister.kitchen_knife.get()))
                        .title(Component.translatable("itemGroup." + MODID))
                        .displayItems((params, output) -> {
                            BlockRegister.DRBlockItems.getEntries().forEach((reg) -> {
                                output.accept(new ItemStack(reg.get()));
                            });
                            ItemRegister.DRItems.getEntries().forEach((reg) -> {
                                output.accept(new ItemStack(reg.get()));
                            });
                            CropRegister.DRBlockItems.getEntries().forEach((reg) -> {
                                output.accept(new ItemStack(reg.get()));
                            });
                            FluidRegister.ITEMS.getEntries().forEach((reg) -> {
                                output.accept(new ItemStack(reg.get()));
                            });
                            BlockEntityRegister.DREntityBlockItems.getEntries().forEach((reg) -> {
                                output.accept(new ItemStack(reg.get()));
                            });

                            ItemRegister.spice_bottle.value().fillItemGroup(output);

                        })
                        .build();
                helper.register(Cuisine.rl(MODID), CREATIVE_TAB);
            });


    }


    // SimpleFluidContent
    @SubscribeEvent
    public static void onRegisterCapabilitiesEvent(RegisterCapabilitiesEvent event) {
        event.registerItem(Capabilities.FluidHandler.ITEM, (s, a) -> ((FluidContainerItem) s.getItem()).transferToFluidHandler(s),
                ItemRegister.spice_bottle.value());
        event.registerItem(Capabilities.ItemHandler.ITEM, (s, a) -> ((ItemContainerItem) s.getItem()).transferToItemHandler(s),
                ItemRegister.spice_bottle.value());

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntityRegister.basin_entity_type.get(),
                (blockEntity, context) -> blockEntity.isRemoved() ? null : (blockEntity.getInventory()));
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, BlockEntityRegister.basin_entity_type.get(),
                (blockEntity, context) -> blockEntity.isRemoved() ? null : blockEntity.getTank());

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntityRegister.basin_colored_entity_type.get(),
                (blockEntity, context) -> blockEntity.isRemoved() ? null : (blockEntity.getInventory()));
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, BlockEntityRegister.basin_colored_entity_type.get(),
                (blockEntity, context) -> blockEntity.isRemoved() ? null : blockEntity.getTank());

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntityRegister.mill_entity_type.get(),
                (blockEntity, context) -> blockEntity.isRemoved() ? null : (context == Direction.DOWN ? blockEntity.getInventory_in() : blockEntity.getInventory_out()));
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, BlockEntityRegister.mill_entity_type.get(),
                (blockEntity, context) -> blockEntity.isRemoved() ? null : (context == Direction.DOWN ? blockEntity.getTankIn() : blockEntity.getTankOut()));


        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntityRegister.barbecue_rack_entity_type.get(),
                (blockEntity, context) -> blockEntity.isRemoved() ? null : (blockEntity.getInventory()));


        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntityRegister.wok_on_fire_pit_entity_type.get(),
                (blockEntity, context) -> blockEntity.isRemoved() ? null : (new CombinedInvWrapper(blockEntity.getSeasonings(),blockEntity.getInventory())));
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, BlockEntityRegister.wok_on_fire_pit_entity_type.get(),
                (blockEntity, context) -> blockEntity.isRemoved() ? null : blockEntity.getSeasoningLiquids());

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntityRegister.chopping_board_entity_type.get(),
                (blockEntity, context) -> blockEntity.isRemoved() ? null : (blockEntity.getInventory()));
    }


}
