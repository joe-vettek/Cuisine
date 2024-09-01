package xueluoanping.cuisine.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.block.nature.BlockBamboo;
import xueluoanping.cuisine.block.nature.BlockBambooPlant;
import xueluoanping.cuisine.block.nature.BlockBambooRoot;

public class BlockRegister {

    public static final DeferredRegister<Item> DRBlockItems = DeferredRegister.create(Registries.ITEM, Cuisine.MODID);
    public static final DeferredRegister<Block> DRBlocks = DeferredRegister.create(Registries.BLOCK, Cuisine.MODID);
    
    public static final DeferredHolder<Block, BlockBamboo> bamboo =
            DRBlocks.register("bamboo", ()->new BlockBamboo(BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO).strength(2.0f).sound(SoundType.BAMBOO).noOcclusion()));
    public static final DeferredHolder<Item, BlockItem> bamboo_item =
            DRBlockItems.register("bamboo", ()->new BlockItem(bamboo.get(),(new Item.Properties())));

    public static final DeferredHolder<Block, BlockBambooPlant> bamboo_plant =
            DRBlocks.register("bamboo_plant",
                    ()->new BlockBambooPlant(BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO).strength(2.0f).sound(SoundType.CROP).noOcclusion()));
    public static final DeferredHolder<Item, BlockItem> bamboo_shoot =
            DRBlockItems.register("bamboo_shoot", ()->new BlockItem(bamboo_plant.get(),(new Item.Properties())));

    public static final DeferredHolder<Block, BlockBambooRoot> bamboo_root =
            DRBlocks.register("bamboo_root",
                    ()->new BlockBambooRoot(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK).strength(2.0f).sound(SoundType.GRASS).noOcclusion()));
    public static final DeferredHolder<Item, BlockItem> bamboo_root_item =
            DRBlockItems.register("bamboo_root", ()->new BlockItem(bamboo_root.get(),(new Item.Properties())));


	public static final DeferredHolder<Block, Block> tofu_block =
			DRBlocks.register("tofu_block",
					()->new Block(BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE).noOcclusion()));
	public static final DeferredHolder<Item, BlockItem> tofu_block_item =
			DRBlockItems.register("tofu_block", ()->new BlockItem(tofu_block.get(),(new Item.Properties())));

	//    public static final RegistryObject<Item> obsidianIngot = ITEMS.register("obsidian_ingot", ObsidianIngot::new);
}
