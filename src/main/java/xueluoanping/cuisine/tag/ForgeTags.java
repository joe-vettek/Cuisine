package xueluoanping.cuisine.tag;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

/**
 * References to tags under the Forge namespace.
 * These tags are generic concepts, meant to be shared with other mods for compatibility.
 */
public class ForgeTags
{

    public static final TagKey<Item> CROPS = forgeItemTag("crops");
    public static final TagKey<Item> CROPS_CABBAGE = forgeItemTag("crops/cabbage");
    public static final TagKey<Item> CROPS_ONION = forgeItemTag("crops/onion");
    public static final TagKey<Item> CROPS_RICE = forgeItemTag("crops/rice");
    public static final TagKey<Item> CROPS_TOMATO = forgeItemTag("crops/tomato");

    public static final TagKey<Item> DOUGH = forgeItemTag("dough");
    public static final TagKey<Item> DOUGH_WHEAT = forgeItemTag("dough/wheat");

    public static final TagKey<Item> SALAD_INGREDIENTS = forgeItemTag("salad_ingredients");
    public static final TagKey<Item> SALAD_INGREDIENTS_CABBAGE = forgeItemTag("salad_ingredients/cabbage");

    public static final TagKey<Item> GRAIN = forgeItemTag("grain");
    public static final TagKey<Item> GRAIN_RICE = forgeItemTag("grain/rice");

    public static final TagKey<Item> VEGETABLES = forgeItemTag("vegetables");
    public static final TagKey<Item> VEGETABLES_CABBAGE = forgeItemTag("vegetables/cabbage");
    public static final TagKey<Item> VEGETABLES_ONION = forgeItemTag("vegetables/onion");
    public static final TagKey<Item> VEGETABLES_TOMATO = forgeItemTag("vegetables/tomato");


    public static final TagKey<Item> TOOLS = forgeItemTag("tools");
    public static final TagKey<Item> TOOLS_AXES = forgeItemTag("tools/axes");
    public static final TagKey<Item> TOOLS_KNIVES = forgeItemTag("tools/knives");
    public static final TagKey<Item> TOOLS_PICKAXES = forgeItemTag("tools/pickaxes");
    public static final TagKey<Item> TOOLS_SHOVELS = forgeItemTag("tools/shovels");

	public static final TagKey<Item> COAL_COKE = forgeItemTag("coal_coke");

    private static TagKey<Block> forgeBlockTag(String path) {
        return TagKey.create(Registries.BLOCK,  ResourceLocation.fromNamespaceAndPath("forge", path));
    }

    private static TagKey<Item> forgeItemTag(String path) {
        return TagKey.create(Registries.ITEM,  ResourceLocation.fromNamespaceAndPath("forge", path));
    }

}
