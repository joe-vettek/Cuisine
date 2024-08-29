package xueluoanping.cuisine.register;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.block.BlockDitch;
import xueluoanping.cuisine.block.nature.BlockBamboo;
import xueluoanping.cuisine.block.nature.BlockBambooPlant;
import xueluoanping.cuisine.block.nature.BlockBambooRoot;

public class BlockRegister {
    public static final DeferredRegister<Item> DRBlockItems = DeferredRegister.create(ForgeRegistries.ITEMS, Cuisine.MODID);
    public static final DeferredRegister<Block> DRBlocks = DeferredRegister.create(ForgeRegistries.BLOCKS, Cuisine.MODID);

    public static final RegistryObject<Block> bamboo =
            DRBlocks.register("bamboo", ()->new BlockBamboo(BlockBehaviour.Properties.of(Material.BAMBOO).strength(2.0f).sound(SoundType.BAMBOO).noOcclusion()));
    public static final RegistryObject<Item> bamboo_item =
            DRBlockItems.register("bamboo", ()->new BlockItem(bamboo.get(),(new Item.Properties()).tab(Cuisine.CREATIVE_TAB)));

    public static final RegistryObject<Block> bamboo_plant =
            DRBlocks.register("bamboo_plant",
                    ()->new BlockBambooPlant(BlockBehaviour.Properties.of(Material.BAMBOO).strength(2.0f).sound(SoundType.CROP).noOcclusion()));
    public static final RegistryObject<Item> bamboo_shoot =
            DRBlockItems.register("bamboo_shoot", ()->new BlockItem(bamboo_plant.get(),(new Item.Properties()).tab(Cuisine.CREATIVE_TAB)));

    public static final RegistryObject<Block> bamboo_root =
            DRBlocks.register("bamboo_root",
                    ()->new BlockBambooRoot(BlockBehaviour.Properties.of(Material.GRASS).strength(2.0f).sound(SoundType.GRASS).noOcclusion()));
    public static final RegistryObject<Item> bamboo_root_item =
            DRBlockItems.register("bamboo_root", ()->new BlockItem(bamboo_root.get(),(new Item.Properties()).tab(Cuisine.CREATIVE_TAB)));

    public static final RegistryObject<Block> ditch =
            DRBlocks.register("ditch",
                    ()->new BlockDitch(BlockBehaviour.Properties.of(Material.STONE).strength(2.0f).sound(SoundType.STONE).noOcclusion()));
    public static final RegistryObject<Item> ditch_item =
            DRBlockItems.register("ditch", ()->new BlockItem(ditch.get(),(new Item.Properties()).tab(Cuisine.CREATIVE_TAB)));


	public static final RegistryObject<Block> tofu_block =
			DRBlocks.register("tofu_block",
					()->new BlockDitch(BlockBehaviour.Properties.of(Material.STONE).strength(2.0f).sound(SoundType.STONE).noOcclusion()));
	public static final RegistryObject<Item> tofu_block_item =
			DRBlockItems.register("tofu_block", ()->new BlockItem(tofu_block.get(),(new Item.Properties()).tab(Cuisine.CREATIVE_TAB)));

	//    public static final RegistryObject<Item> obsidianIngot = ITEMS.register("obsidian_ingot", ObsidianIngot::new);
}
