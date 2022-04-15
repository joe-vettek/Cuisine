package xueluoanping.cuisine.tag;


import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import xueluoanping.cuisine.Cuisine;


/**
 * References to tags under the Farmer's Delight namespace.
 * These tags are used for mod mechanics.
 */
public class CuisineTags {
    // Blocks that are efficiently mined with a Knife.
    public static final TagKey<Block> henon_bamboo_plamtable_on = modBlockTag("plamtable_on/henon_bamboo");


    private static TagKey<Item> modItemTag(String path) {
        return TagKey.create(Registry.ITEM_REGISTRY, Cuisine.rl(path));
    }

    private static TagKey<Block> modBlockTag(String path) {
        return TagKey.create(Registry.BLOCK_REGISTRY,  Cuisine.rl(path));
    }

    private static TagKey<EntityType<?>> modEntityTag(String path) {
        return TagKey.create(Registry.ENTITY_TYPE_REGISTRY,  Cuisine.rl(path));
    }
}
