package xueluoanping.cuisine.register;

import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.loot.AddItemModifier;
import xueluoanping.cuisine.loot.AddLootTableModifier;

public class GlobalLootModifierRegistry {
    // 别忘了替换MODID哦
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> GLM =
            DeferredRegister.create(ForgeRegistries.Keys.LOOT_MODIFIER_SERIALIZERS, Cuisine.MODID);

    // GlobalLootModifierSerializer<?>
    public static final RegistryObject<GlobalLootModifierSerializer> add_loot_table =
            GLM.register("add_loot_table", AddLootTableModifier.Serializer::new);

    public static final RegistryObject<GlobalLootModifierSerializer> add_item =
            GLM.register("add_item", AddItemModifier.Serializer::new);
}
