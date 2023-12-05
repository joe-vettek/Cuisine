package xueluoanping.cuisine.register;

import com.google.common.collect.ImmutableSet;


import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xueluoanping.cuisine.block.BlockBarbecueRack;
import xueluoanping.cuisine.block.BlockBasin;
import xueluoanping.cuisine.block.BlockBasinColored;
import xueluoanping.cuisine.block.BlockChoppingBoard;
import xueluoanping.cuisine.block.BlockFirePit;
import xueluoanping.cuisine.block.BlockFirePitWithWok;
import xueluoanping.cuisine.block.blockitem.BlockBasinItem;
import xueluoanping.cuisine.block.blockitem.HideBlockFirePitItem;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.block.HideBlockFirePit;
import xueluoanping.cuisine.block.BlockMill;
import xueluoanping.cuisine.block.entity.BasinBlockEntity;
import xueluoanping.cuisine.block.entity.BasinColorBlockEntity;
import xueluoanping.cuisine.block.entity.ChoppingBoardBlockEntity;
import xueluoanping.cuisine.block.entity.FirePitBlockEntity;
import xueluoanping.cuisine.block.entity.HideFirePitBlockEntity;
import xueluoanping.cuisine.block.entity.MillBlockEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

import static xueluoanping.cuisine.Cuisine.CREATIVE_TAB;

public class BlockEntityRegister {
	public static final DeferredRegister<Item> DREntityBlockItems = DeferredRegister.create(ForgeRegistries.ITEMS, Cuisine.MODID);
	public static final DeferredRegister<Block> DREntityBlocks = DeferredRegister.create(ForgeRegistries.BLOCKS, Cuisine.MODID);
	public static final DeferredRegister<BlockEntityType<?>> DRBlockEntities = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Cuisine.MODID);

	//	chopping_board
	public static final RegistryObject<Block> chopping_board = DREntityBlocks.register("chopping_board", () -> new BlockChoppingBoard(BlockBehaviour.Properties.of(Material.STONE).strength(2.0f).sound(SoundType.STONE).noOcclusion()));
	public static final RegistryObject<Item> chopping_board_item = DREntityBlockItems.register("chopping_board", () -> new BlockItem(chopping_board.get(), (new Item.Properties()).tab(Cuisine.CREATIVE_TAB)));
	public static final RegistryObject<BlockEntityType<ChoppingBoardBlockEntity>> chopping_board_entity_type = DRBlockEntities.register("chopping_board", () -> BlockEntityType.Builder.of(ChoppingBoardBlockEntity::new, BlockEntityRegister.chopping_board.get()).build(null));


	//	mill
	public static final RegistryObject<Block> mill = DREntityBlocks.register("mill", () -> new BlockMill(BlockBehaviour.Properties.of(Material.STONE).strength(2.0f).sound(SoundType.STONE).noOcclusion()));
	public static final RegistryObject<Item> mill_item = DREntityBlockItems.register("mill", () -> new BlockItem(mill.get(), (new Item.Properties()).tab(Cuisine.CREATIVE_TAB)));
	public static final RegistryObject<BlockEntityType<MillBlockEntity>> mill_entity_type = DRBlockEntities.register("mill", () -> BlockEntityType.Builder.of(MillBlockEntity::new, BlockEntityRegister.mill.get()).build(null));

	//	fire_pit
	public static final RegistryObject<Block> fire_pit = DREntityBlocks.register("fire_pit", () -> new BlockFirePit(BlockBehaviour.Properties.of(Material.STONE).strength(2.0f).sound(SoundType.STONE).noOcclusion()));
	public static final RegistryObject<Item> fire_pit_item = DREntityBlockItems.register("fire_pit", () -> new BlockItem(fire_pit.get(), RegisterHelper.basicItem()));
	public static final RegistryObject<BlockEntityType<FirePitBlockEntity>> fire_pit_entity_type = DRBlockEntities.register("fire_pit", () -> BlockEntityType.Builder.of(FirePitBlockEntity::new, BlockEntityRegister.fire_pit.get()).build(null));

	public static final RegistryObject<Block> barbecue_rack = DREntityBlocks.register("barbecue_rack", () -> new BlockBarbecueRack(BlockBehaviour.Properties.of(Material.STONE).strength(2.0f).sound(SoundType.STONE).noOcclusion()));
	public static final RegistryObject<Item> barbecue_rack_item = DREntityBlockItems.register("barbecue_rack", () -> new BlockItem(barbecue_rack.get(), RegisterHelper.basicItem()));

	public static final RegistryObject<Block> wok_on_fire_pit = DREntityBlocks.register("wok_on_fire_pit", () -> new BlockFirePitWithWok(BlockBehaviour.Properties.of(Material.STONE).strength(2.0f).sound(SoundType.STONE).noOcclusion()));
	public static final RegistryObject<Item> wok_on_fire_pit_item = DREntityBlockItems.register("wok_on_fire_pit", () -> new BlockItem(wok_on_fire_pit.get(), RegisterHelper.basicItem()));


	public static final RegistryObject<Block> hide_fire_pit = DREntityBlocks.register("fire_pit_hide", () -> new HideBlockFirePit(BlockBehaviour.Properties.of(Material.STONE).strength(2.0f).sound(SoundType.STONE).noOcclusion()));
	public static final RegistryObject<Item> hide_fire_pit_item = DREntityBlockItems.register("fire_pit_hide", () -> new HideBlockFirePitItem(hide_fire_pit.get(), (new Item.Properties()).tab(Cuisine.CREATIVE_TAB)));
	public static final RegistryObject<BlockEntityType<HideFirePitBlockEntity>> hide_fire_pit_entity_type = DRBlockEntities.register("fire_pit_hide", () -> BlockEntityType.Builder.of(HideFirePitBlockEntity::new, BlockEntityRegister.hide_fire_pit.get()).build(null));

	//	basin
	public static final RegistryObject<Block> basin = DREntityBlocks.register("basin", () -> new BlockBasin(BlockBehaviour.Properties.of(Material.STONE)
			.sound(SoundType.STONE).strength(2.0F)
			.noOcclusion().isSuffocating(BlockEntityRegister::predFalse).isRedstoneConductor(BlockEntityRegister::predFalse)));
	public static final RegistryObject<Item> basin_item = DREntityBlockItems.register("basin", () -> new BlockBasinItem(basin.get(), new Item.Properties().tab(CREATIVE_TAB)));
	public static final RegistryObject<BlockEntityType<BasinBlockEntity>> basin_entity_type = DRBlockEntities.register("basin", () -> BlockEntityType.Builder.of(BasinBlockEntity::new, BlockEntityRegister.basin.get()).build(null));


	private static <T extends BlockEntity> Supplier<BlockEntityType<T>> makeType(BlockEntityType.BlockEntitySupplier<T> create,
																				 Supplier<RegistryObject<? extends Block>> valid) {
		return () -> new BlockEntityType<>(create, ImmutableSet.of(valid.get().get()), null);
	}


	public static final RegistryObject<BlockEntityType<BasinColorBlockEntity>> basin_colored_entity_type;

	private static boolean predFalse(BlockState p_235436_0_, BlockGetter p_235436_1_, BlockPos p_235436_2_) {
		return false;
	}

	//
	public static final Map<DyeColor, RegistryObject<Block>> basinColored = new LinkedHashMap<>();
	// public static final ArrayList<Block> basinBlockList = new ArrayList<>();
	public static final ArrayList<RegistryObject<Item>> basinItemColored = new ArrayList<>();
	public static final Map<DyeColor, Block> colorBlockMap = new HashMap<>() {
		{
			put(DyeColor.WHITE, Blocks.WHITE_TERRACOTTA);
			put(DyeColor.ORANGE, Blocks.ORANGE_TERRACOTTA);
			put(DyeColor.MAGENTA, Blocks.MAGENTA_TERRACOTTA);
			put(DyeColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_TERRACOTTA);
			put(DyeColor.YELLOW, Blocks.YELLOW_TERRACOTTA);
			put(DyeColor.LIME, Blocks.LIME_TERRACOTTA);
			put(DyeColor.PINK, Blocks.PINK_TERRACOTTA);
			put(DyeColor.GRAY, Blocks.GRAY_TERRACOTTA);
			put(DyeColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_TERRACOTTA);
			put(DyeColor.CYAN, Blocks.CYAN_TERRACOTTA);
			put(DyeColor.PURPLE, Blocks.PURPLE_TERRACOTTA);
			put(DyeColor.BLUE, Blocks.BLUE_TERRACOTTA);
			put(DyeColor.BROWN, Blocks.BROWN_TERRACOTTA);
			put(DyeColor.GREEN, Blocks.GREEN_TERRACOTTA);
			put(DyeColor.RED, Blocks.RED_TERRACOTTA);
			put(DyeColor.BLACK, Blocks.BLACK_TERRACOTTA);
			put(null,Blocks.TERRACOTTA);
		}
	};

	// public static final Map<Integer, Integer> colorBlockMap0 = new HashMap<>(){{put(0,0);}};
	static {
		colorBlockMap.forEach((dyeColor, block) -> {
			RegistryObject<Block> basinc = DREntityBlocks.register(block.delegate.name().getPath() + "_basin", () -> new BlockBasinColored(BlockBehaviour.Properties.of(Material.STONE, block.defaultMaterialColor())
					.sound(SoundType.STONE).strength(2.0F)
					.noOcclusion().isSuffocating(BlockEntityRegister::predFalse)
					.isRedstoneConductor(BlockEntityRegister::predFalse)));
			// basinc.ifPresent(bbb -> {
			// 	basinBlockList.add(bbb);
			// 	Cuisine.logger("hello");
			// });
			basinColored.put(dyeColor, basinc);
			RegistryObject<Item> basinc_item = DREntityBlockItems.register(basinc.getId().getPath(),
					() -> new BlockBasinItem(basinc.get(), new Item.Properties().tab(CREATIVE_TAB)));
			// basinc_item.ifPresent(basinItemColored::add);
			basinItemColored.add(basinc_item);
		});
		basin_colored_entity_type=DRBlockEntities.register("basin_colored", () -> BlockEntityType.Builder.of(BasinColorBlockEntity::new, getAllBasinBlock()).build(null));
	}

	public static Block[] getAllBasinBlock() {

		// Block[] blocks = basinColored.toArray(new Block[0]);
		ArrayList<Block> blocks = new ArrayList<>();
		for (RegistryObject<Block> block : new ArrayList<>(basinColored.values())
		) {
			blocks.add(block.get());
		}
		Cuisine.logger(blocks.size() + "注册陶瓷盆");
		return blocks.toArray(new Block[0]);
	}

}
