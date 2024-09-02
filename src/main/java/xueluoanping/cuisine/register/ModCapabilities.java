package xueluoanping.cuisine.register;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.items.base.ItemHolder;

public class ModCapabilities {
    public static final DeferredRegister<DataComponentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, Cuisine.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SimpleFluidContent>> SIMPLE_FLUID=ATTACHMENT_TYPES.register(
            "fluid_data",
            ()->DataComponentType.<SimpleFluidContent>builder().persistent(SimpleFluidContent.CODEC).networkSynchronized(SimpleFluidContent.STREAM_CODEC).build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemHolder>> SIMPLE_ITEM=ATTACHMENT_TYPES.register(
            "item_data",
            ()->DataComponentType.<ItemHolder>builder().persistent(ItemHolder.CODEC).networkSynchronized(ItemHolder.STREAM_CODEC).build());

}
