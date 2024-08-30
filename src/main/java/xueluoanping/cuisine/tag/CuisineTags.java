package xueluoanping.cuisine.tag;


import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import xueluoanping.cuisine.Cuisine;


/**
 * References to tags under the Cuisine namespace.
 * These tags are used for mod mechanics.
 */
public class CuisineTags {
    // Blocks that are efficiently mined with a Knife.
    public static final TagKey<Block> henon_bamboo_plamtable_on = modBlockTag("plamtable_on/henon_bamboo");
	public static final TagKey<Block> bamboo_root_spread_on = modBlockTag("spread_on/bamboo_root");


    private static TagKey<Item> modItemTag(String path) {
        return TagKey.create(Registries.ITEM, Cuisine.rl(path));
    }

    private static TagKey<Block> modBlockTag(String path) {
        return TagKey.create(Registries.BLOCK,  Cuisine.rl(path));
    }

    private static TagKey<EntityType<?>> modEntityTag(String path) {
        return TagKey.create(Registries.ENTITY_TYPE,  Cuisine.rl(path));
    }
}
