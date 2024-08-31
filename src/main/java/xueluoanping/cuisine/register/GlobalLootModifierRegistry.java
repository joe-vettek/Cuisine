package xueluoanping.cuisine.register;

import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.loot.AddItemModifier;
import xueluoanping.cuisine.loot.AddLootTableModifier;

public class GlobalLootModifierRegistry {
    public static final net.neoforged.neoforge.registries.DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = net.neoforged.neoforge.registries.DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Cuisine.MODID);
    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<AddLootTableModifier>> ADD_LOOT_TABLE = LOOT_MODIFIERS.register("add_loot_table", AddLootTableModifier.CODEC);
    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<AddItemModifier>> ADD_ITEM = LOOT_MODIFIERS.register("add_item", AddItemModifier.CODEC);

}
