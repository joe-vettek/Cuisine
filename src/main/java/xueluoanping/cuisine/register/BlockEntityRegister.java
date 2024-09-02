package xueluoanping.cuisine.register;


import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
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
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import xueluoanping.cuisine.block.blockitem.BlockItemDish;
import xueluoanping.cuisine.block.blockitem.BlockItemDrinkro;
import xueluoanping.cuisine.block.firepit.BlockBarbecueRack;
import xueluoanping.cuisine.block.BlockBasin;
import xueluoanping.cuisine.block.BlockBasinColored;
import xueluoanping.cuisine.block.BlockChoppingBoard;
import xueluoanping.cuisine.block.BlockDrinkro;
import xueluoanping.cuisine.block.firepit.BlockFirePit;
import xueluoanping.cuisine.block.firepit.BlockFirePitWithWok;
import xueluoanping.cuisine.block.BlockJar;
import xueluoanping.cuisine.block.BlockMortar;
import xueluoanping.cuisine.block.BlockPlate;
import xueluoanping.cuisine.block.blockitem.BlockBasinItem;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.block.BlockMill;
import xueluoanping.cuisine.blockentity.BasinBlockEntity;
import xueluoanping.cuisine.blockentity.BasinColorBlockEntity;
import xueluoanping.cuisine.blockentity.ChoppingBoardBlockEntity;
import xueluoanping.cuisine.blockentity.DrinkroBlockEntity;
import xueluoanping.cuisine.blockentity.firepit.BarbecueRackBlockEntity;
import xueluoanping.cuisine.blockentity.firepit.FirePitBlockEntity;
import xueluoanping.cuisine.blockentity.JarBlockEntity;
import xueluoanping.cuisine.blockentity.MillBlockEntity;
import xueluoanping.cuisine.blockentity.PlateBlockEntity;
import xueluoanping.cuisine.blockentity.firepit.WokOnFirePitbBlockEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class BlockEntityRegister {
	public static final DeferredRegister<Item> DREntityBlockItems = DeferredRegister.create(Registries.ITEM, Cuisine.MODID);
	public static final DeferredRegister<Block> DREntityBlocks = DeferredRegister.create(Registries.BLOCK, Cuisine.MODID);
	public static final DeferredRegister<BlockEntityType<?>> DRBlockEntities = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Cuisine.MODID);

	//	chopping_board
	public static final DeferredHolder<Block, BlockChoppingBoard> chopping_board = DREntityBlocks.register("chopping_board", () -> new BlockChoppingBoard(BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE).noOcclusion()));
	public static final DeferredHolder<Item, BlockItem> chopping_board_item = DREntityBlockItems.register("chopping_board", () -> new BlockItem(chopping_board.get(), (new Item.Properties())));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ChoppingBoardBlockEntity>> chopping_board_entity_type = DRBlockEntities.register("chopping_board", () -> BlockEntityType.Builder.of(ChoppingBoardBlockEntity::new, BlockEntityRegister.chopping_board.get()).build(null));
	//	mill
	public static final DeferredHolder<Block, BlockMill> mill = DREntityBlocks.register("mill", () -> new BlockMill(BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE).noOcclusion()));
	public static final DeferredHolder<Item, BlockItem> mill_item = DREntityBlockItems.register("mill", () -> new BlockItem(mill.get(), (new Item.Properties())));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MillBlockEntity>> mill_entity_type = DRBlockEntities.register("mill", () -> BlockEntityType.Builder.of(MillBlockEntity::new, BlockEntityRegister.mill.get()).build(null));

	//	mortar
	public static final DeferredHolder<Block, BlockMortar> mortar = DREntityBlocks.register("mortar", () -> new BlockMortar(BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE).noOcclusion()));
	public static final DeferredHolder<Item, BlockItem> mortar_item = DREntityBlockItems.register("mortar", () -> new BlockItem(mortar.get(), (new Item.Properties())));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MillBlockEntity>> mortar_entity_type = DRBlockEntities.register("mortar", () -> BlockEntityType.Builder.of(MillBlockEntity::new, BlockEntityRegister.mortar.get()).build(null));

	//	jar
	public static final DeferredHolder<Block, BlockJar> jar = DREntityBlocks.register("jar", () -> new BlockJar(BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE).noOcclusion()));
	public static final DeferredHolder<Item, BlockItem> jar_item = DREntityBlockItems.register("jar", () -> new BlockItem(jar.get(), (new Item.Properties())));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<JarBlockEntity>> jar_entity_type = DRBlockEntities.register("jar", () -> BlockEntityType.Builder.of(JarBlockEntity::new, BlockEntityRegister.jar.get()).build(null));

	//	plate
	public static final DeferredHolder<Block, BlockPlate> plate = DREntityBlocks.register("plate", () -> new BlockPlate(BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE).noOcclusion()));
	public static final DeferredHolder<Item, BlockItem> plate_item = DREntityBlockItems.register("plate", () -> new BlockItemDish(plate.get(), (new Item.Properties())));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PlateBlockEntity>> plate_entity_type = DRBlockEntities.register("plate", () -> BlockEntityType.Builder.of(PlateBlockEntity::new, BlockEntityRegister.plate.get()).build(null));

	//	drinkro
	public static final DeferredHolder<Block, BlockDrinkro> drinkro = DREntityBlocks.register("drinkro", () -> new BlockDrinkro(BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE).noOcclusion()));
	public static final DeferredHolder<Item, BlockItem> drinkro_item = DREntityBlockItems.register("drinkro", () -> new BlockItemDrinkro(drinkro.get(), (new Item.Properties())));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DrinkroBlockEntity>> drinkro_type = DRBlockEntities.register("drinkro", () -> BlockEntityType.Builder.of(DrinkroBlockEntity::new, BlockEntityRegister.drinkro.get()).build(null));

	//	fire_pit
	public static final DeferredHolder<Block, BlockFirePit> fire_pit = DREntityBlocks.register("fire_pit", () -> new BlockFirePit(BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE).noOcclusion()));
	public static final DeferredHolder<Item, BlockItem> fire_pit_item = DREntityBlockItems.register("fire_pit", () -> new BlockItem(fire_pit.get(), RegisterHelper.basicItem()));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FirePitBlockEntity>> fire_pit_entity_type = DRBlockEntities.register("fire_pit", () -> BlockEntityType.Builder.of(FirePitBlockEntity::new, BlockEntityRegister.fire_pit.get()).build(null));

	public static final DeferredHolder<Block, BlockBarbecueRack> barbecue_rack = DREntityBlocks.register("barbecue_rack", () -> new BlockBarbecueRack(BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE).noOcclusion()));
	public static final DeferredHolder<Item, BlockItem> barbecue_rack_item = DREntityBlockItems.register("barbecue_rack", () -> new BlockItem(barbecue_rack.get(), RegisterHelper.basicItem()));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BarbecueRackBlockEntity>> barbecue_rack_entity_type = DRBlockEntities.register("barbecue_rack", () -> BlockEntityType.Builder.of(BarbecueRackBlockEntity::new, BlockEntityRegister.barbecue_rack.get()).build(null));

	public static final DeferredHolder<Block, BlockFirePitWithWok> wok_on_fire_pit = DREntityBlocks.register("wok_on_fire_pit", () -> new BlockFirePitWithWok(BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE).noOcclusion()));
	public static final DeferredHolder<Item, BlockItem> wok_on_fire_pit_item = DREntityBlockItems.register("wok_on_fire_pit", () -> new BlockItem(wok_on_fire_pit.get(), RegisterHelper.basicItem()));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WokOnFirePitbBlockEntity>> wok_on_fire_pit_entity_type = DRBlockEntities.register("wok_on_fire_pit", () -> BlockEntityType.Builder.of(WokOnFirePitbBlockEntity::new, BlockEntityRegister.wok_on_fire_pit.get()).build(null));

	//	basin
	public static final DeferredHolder<Block, BlockBasin> basin = DREntityBlocks.register("basin", () -> new BlockBasin(BlockBehaviour.Properties.of()
			.sound(SoundType.STONE).strength(2.0F)
			.noOcclusion().isSuffocating(BlockEntityRegister::predFalse).isRedstoneConductor(BlockEntityRegister::predFalse)));
	public static final DeferredHolder<Item, BlockBasinItem> basin_item = DREntityBlockItems.register("basin", () -> new BlockBasinItem(basin.get(), new Item.Properties()));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BasinBlockEntity>> basin_entity_type = DRBlockEntities.register("basin", () -> BlockEntityType.Builder.of(BasinBlockEntity::new, BlockEntityRegister.basin.get()).build(null));



	public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<BasinColorBlockEntity>> basin_colored_entity_type;

	private static boolean predFalse(BlockState p_235436_0_, BlockGetter p_235436_1_, BlockPos p_235436_2_) {
		return false;
	}

	//
	public static final Map<DyeColor, DeferredHolder<Block, ? extends BlockBasin>> basinColored = new LinkedHashMap<>();
	// public static final ArrayList<Block> basinBlockList = new ArrayList<>();
	public static final ArrayList<DeferredHolder<Item, BlockBasinItem>> basinItemColored = new ArrayList<>();
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
			put(null, Blocks.TERRACOTTA);
		}
	};

	// public static final Map<Integer, Integer> colorBlockMap0 = new HashMap<>(){{put(0,0);}};
	static {
		colorBlockMap.forEach((dyeColor, block) -> {
			var basinc = DREntityBlocks.register(BuiltInRegistries.BLOCK.getKey(block).getPath() + "_basin", () -> new BlockBasinColored(BlockBehaviour.Properties.ofFullCopy(block)
					.sound(SoundType.STONE).strength(2.0F)
					.noOcclusion().isSuffocating(BlockEntityRegister::predFalse)
					.isRedstoneConductor(BlockEntityRegister::predFalse)));
			// basinc.ifPresent(bbb -> {
			// 	basinBlockList.add(bbb);
			// 	Cuisine.logger("hello");
			// });
			basinColored.put(dyeColor, basinc);
			var basinc_item = DREntityBlockItems.register(basinc.getId().getPath(),
					() -> new BlockBasinItem(basinc.get(), new Item.Properties()));
			// basinc_item.ifPresent(basinItemColored::add);
			basinItemColored.add(basinc_item);
		});
		basin_colored_entity_type = DRBlockEntities.register("basin_colored", () -> BlockEntityType.Builder.of(BasinColorBlockEntity::new, getAllBasinBlock()).build(null));
	}

	public static Block[] getAllBasinBlock() {

		// Block[] blocks = basinColored.toArray(new Block[0]);
		ArrayList<Block> blocks = new ArrayList<>();
		for (var block : new ArrayList<>(basinColored.values())
		) {
			blocks.add(block.get());
		}
		return blocks.toArray(new Block[0]);
	}

}
