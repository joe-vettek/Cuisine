package xueluoanping.cuisine;

import com.google.common.collect.Sets;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Material;


import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import snownee.kiwi.AbstractModule;
import snownee.kiwi.KiwiModule;
import snownee.kiwi.KiwiModule.Category;
import snownee.kiwi.KiwiModule.Subscriber.Bus;
import snownee.kiwi.block.ModBlock;
import snownee.kiwi.item.ModBlockItem;
import snownee.kiwi.item.ModItem;
import snownee.kiwi.loader.event.ClientInitEvent;
import snownee.kiwi.loader.event.InitEvent;
import snownee.kiwi.loader.event.PostInitEvent;
import snownee.kiwi.loader.event.ServerInitEvent;
import snownee.kiwi.util.EnumUtil;
import xueluoanping.cuisine.block.BlockBasin;
import xueluoanping.cuisine.block.BlockCabbage;
import xueluoanping.cuisine.block.BlockCabinet;
import xueluoanping.cuisine.block.BlockFirePit;
import xueluoanping.cuisine.client.renderer.TESRBasin;
import xueluoanping.cuisine.datagen.CommonBlockTagsProvider;
import xueluoanping.cuisine.block.tile.BasinBlockEntity;
import xueluoanping.cuisine.block.tile.CabinetBlockEntity;

import java.util.Set;

import static xueluoanping.cuisine.Cuisine.MODID;

@KiwiModule.Optional
@KiwiModule
@KiwiModule.Subscriber(Bus.MOD)
@Category("cuisine.main")
public final class CoreModule extends AbstractModule {

	public static final class Foods {
		public static final FoodProperties MANDARIN = new FoodProperties.Builder().nutrition(3).saturationMod(0.3f).build();
		public static final FoodProperties LIME = new FoodProperties.Builder().nutrition(3).saturationMod(0.3f).build();
		public static final FoodProperties CITRON = new FoodProperties.Builder().nutrition(3).saturationMod(0.3f).build();
		public static final FoodProperties POMELO = new FoodProperties.Builder().nutrition(4).saturationMod(0.3f).build();
		public static final FoodProperties ORANGE = new FoodProperties.Builder().nutrition(3).saturationMod(0.5f).build();
		public static final FoodProperties LEMON = new FoodProperties.Builder().nutrition(2).saturationMod(1f).fast().build();
		public static final FoodProperties GRAPEFRUIT = new FoodProperties.Builder().nutrition(6).saturationMod(0.4f).build();
		public static final FoodProperties EMPOWERED_CITRON = new FoodProperties.Builder().nutrition(3).saturationMod(5f).build();
	}

	public static final CreativeModeTab TAB =
			itemCategory("cuisine", "main", () -> new ItemStack(Items.DANDELION), null);


	public static Item ICON = new ModItem(itemProp().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.4f).build()));

	//	Kitchenware
	public static Block POT = new ModBlock(Block.Properties.of(Material.METAL));
	public static Block Fire_Pit = new BlockFirePit(blockProp(Blocks.STONE));
	public static Block Basin = new BlockBasin(blockProp(Blocks.OAK_PLANKS));
	public static BlockEntityType<BasinBlockEntity> basinBlockEntityBlockEntityType = blockEntity(BasinBlockEntity::new, null, Basin);


	@Override
	@OnlyIn(Dist.CLIENT)
	public void clientInit(ClientInitEvent event) {
		// 使用户在放下方块时贴图能立即更新
		logger.info(MODID + "clientInit");
		ModBlockItem.INSTANT_UPDATE_TILES.add(basinBlockEntityBlockEntityType);
		ItemBlockRenderTypes.setRenderLayer(Basin, EnumUtil.BLOCK_RENDER_TYPES::contains);
//		ItemBlockRenderTypes.setRenderLayer(Basin, CoreModule::isGlassLanternValidLayer);
	}

	// does the Glass Lantern render in the given layer (RenderType) - used as Predicate<RenderType> lambda for setRenderLayer
	public static boolean isGlassLanternValidLayer(RenderType layerToCheck) {
		return layerToCheck == RenderType.solid() || layerToCheck == RenderType.cutout() || layerToCheck == RenderType.translucent();
	}

	@SubscribeEvent
	protected void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {

		event.registerBlockEntityRenderer(basinBlockEntityBlockEntityType, TESRBasin::new);
	}

	//	Crop
	public static Block cabbage = new BlockCabbage(Block.Properties.copy(Blocks.CARROTS));

	//	container
	public static final Set<Block> ALL_ITEMS = Sets.newConcurrentHashSet();

	public static Block OAK_CABINET = new BlockCabinet(Block.Properties.copy(Blocks.BARREL));
	public static BlockEntityType<CabinetBlockEntity> CABINET = new BlockEntityType<>(CabinetBlockEntity::new, ALL_ITEMS, null);
	public static Logger logger = LogManager.getLogger();

	@Override
	protected void preInit() {
		logger.info(MODID + "preInit");
	}

	@Override
	protected void init(InitEvent event) {
		logger.info(MODID + "init");
	}

	@Override
	protected void serverInit(ServerInitEvent event) {
		logger.info(MODID + "serverInit");
	}

	@Override
	protected void postInit(PostInitEvent event) {
		logger.info(MODID + "POSTINIT");
		event.enqueueWork(() -> {
		});

	}

	@SubscribeEvent
	public static void onCommonSetupEvent(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {

		});

	}

	@Override
	protected void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		if (event.includeServer()) {
			//generate LootTable
//			KiwiLootTableProvider provider = new KiwiLootTableProvider(generator);
//			provider.add(CommonBlockLoot::new, LootContextParamSets.BLOCK);
//			generator.addProvider(provider);

			//generate blockTag
			CommonBlockTagsProvider blockTagsProvider = new CommonBlockTagsProvider(generator, event.getExistingFileHelper());
			generator.addProvider(blockTagsProvider);

		}


	}

}
