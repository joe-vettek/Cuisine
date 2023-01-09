package xueluoanping.cuisine.register;

import com.mojang.datafixers.types.Type;


import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xueluoanping.cuisine.block.BlockBasin;
import xueluoanping.cuisine.block.blockitem.BlockBasinItem;
import xueluoanping.cuisine.block.blockitem.BlockFirePitItem;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.block.BlockFirePit;
import xueluoanping.cuisine.block.BlockMill;
import xueluoanping.cuisine.block.entity.BasinBlockEntity;
import xueluoanping.cuisine.block.entity.FirePitBlockEntity;
import xueluoanping.cuisine.block.entity.MillBlockEntity;

import java.util.HashMap;
import java.util.Map;

import static xueluoanping.cuisine.Cuisine.CREATIVE_TAB;

public class BlockEntityRegister {
	public static final DeferredRegister<Item> DREntityBlockItems = DeferredRegister.create(ForgeRegistries.ITEMS, Cuisine.MODID);
	public static final DeferredRegister<Block> DREntityBlocks = DeferredRegister.create(ForgeRegistries.BLOCKS, Cuisine.MODID);
	public static final DeferredRegister<BlockEntityType<?>> DRBlockEntities = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Cuisine.MODID);

	//	mill
	public static final RegistryObject<Block> mill = DREntityBlocks.register("mill", () -> new BlockMill(BlockBehaviour.Properties.of(Material.STONE).strength(2.0f).sound(SoundType.STONE).noOcclusion()));
	public static final RegistryObject<Item> mill_item = DREntityBlockItems.register("mill", () -> new BlockItem(mill.get(), (new Item.Properties()).tab(Cuisine.CREATIVE_TAB)));
	public static final RegistryObject<BlockEntityType<MillBlockEntity>> mill_entity_type = DRBlockEntities.register("mill", () -> BlockEntityType.Builder.of(MillBlockEntity::new, BlockEntityRegister.mill.get()).build(null));

	//	fire_pit
	public static final RegistryObject<Block> fire_pit = DREntityBlocks.register("fire_pit", () -> new BlockFirePit(BlockBehaviour.Properties.of(Material.STONE).strength(2.0f).sound(SoundType.STONE).noOcclusion()));
	public static final RegistryObject<Item> fire_pit_item = DREntityBlockItems.register("fire_pit", () -> new BlockFirePitItem(fire_pit.get(), (new Item.Properties()).tab(Cuisine.CREATIVE_TAB)));
	public static final RegistryObject<BlockEntityType<FirePitBlockEntity>> fire_pit_entity_type = DRBlockEntities.register("fire_pit", () -> BlockEntityType.Builder.of(FirePitBlockEntity::new, BlockEntityRegister.fire_pit.get()).build(null));

	//	basin
	public static final RegistryObject<Block> basin = DREntityBlocks.register("basin", () -> new BlockBasin(BlockBehaviour.Properties.of(Material.STONE)
			.sound(SoundType.STONE).strength(2.0F)
			.noOcclusion().isSuffocating(BlockEntityRegister::predFalse).isRedstoneConductor(BlockEntityRegister::predFalse)));
	public static final RegistryObject<Item> basin_item = DREntityBlockItems.register("basin", () -> new BlockBasinItem(basin.get(), new Item.Properties().tab(CREATIVE_TAB)));
	public static final RegistryObject<BlockEntityType<BasinBlockEntity>> basin_entity_type = DRBlockEntities.register("basin", () -> BlockEntityType.Builder.of(BasinBlockEntity::new, BlockEntityRegister.basin.get()).build(null));


	private static boolean predFalse(BlockState p_235436_0_, BlockGetter p_235436_1_, BlockPos p_235436_2_) {
		return false;
	}
	//
	// public static final EnumObject<DyeColor,Block> TERRACOTTA = new EnumObject.Builder<DyeColor,Block>(DyeColor.class)
	// 		.put(DyeColor.WHITE,      Blocks.WHITE_TERRACOTTA.delegate)
	// 		.put(DyeColor.ORANGE,     Blocks.ORANGE_TERRACOTTA.delegate)
	// 		.put(DyeColor.MAGENTA,    Blocks.MAGENTA_TERRACOTTA.delegate)
	// 		.put(DyeColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_TERRACOTTA.delegate)
	// 		.put(DyeColor.YELLOW,     Blocks.YELLOW_TERRACOTTA.delegate)
	// 		.put(DyeColor.LIME,       Blocks.LIME_TERRACOTTA.delegate)
	// 		.put(DyeColor.PINK,       Blocks.PINK_TERRACOTTA.delegate)
	// 		.put(DyeColor.GRAY,       Blocks.GRAY_TERRACOTTA.delegate)
	// 		.put(DyeColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_TERRACOTTA.delegate)
	// 		.put(DyeColor.CYAN,       Blocks.CYAN_TERRACOTTA.delegate)
	// 		.put(DyeColor.PURPLE,     Blocks.PURPLE_TERRACOTTA.delegate)
	// 		.put(DyeColor.BLUE,       Blocks.BLUE_TERRACOTTA.delegate)
	// 		.put(DyeColor.BROWN,      Blocks.BROWN_TERRACOTTA.delegate)
	// 		.put(DyeColor.GREEN,      Blocks.GREEN_TERRACOTTA.delegate)
	// 		.put(DyeColor.RED,        Blocks.RED_TERRACOTTA.delegate)
	// 		.put(DyeColor.BLACK,      Blocks.BLACK_TERRACOTTA.delegate)
	// 		.build();
	//
	// /* Building blocks */
	//
	// // porcelain
	// public static final EnumObject<DyeColor,Block> PORCELAIN_BLOCK = BLOCKS.registerEnum(DyeColor.values(), "porcelain", (color) -> new Block(Block.Properties.copy(TERRACOTTA.get(color))), DEFAULT_BLOCK_ITEM);

}
