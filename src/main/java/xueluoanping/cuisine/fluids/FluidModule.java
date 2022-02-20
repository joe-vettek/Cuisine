package xueluoanping.cuisine.fluids;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import snownee.kiwi.AbstractModule;
import snownee.kiwi.KiwiModule;
import snownee.kiwi.loader.event.InitEvent;
import xueluoanping.cuisine.api.events.FurnaceFuelBurnTimeEventHandler;

import java.lang.reflect.Type;

import static xueluoanping.cuisine.Cuisine.MODID;


@KiwiModule.Optional
@KiwiModule(value = "fluid", dependencies = "@core")
@KiwiModule.Subscriber(KiwiModule.Subscriber.Bus.MOD)
@KiwiModule.Category("cuisine.main")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class FluidModule extends AbstractModule {
	public static final ResourceLocation STILL_OIL_TEXTURE = new ResourceLocation("cuisine", "block/cuisine_juice_still");
	public static final ResourceLocation FLOWING_OIL_TEXTURE = new ResourceLocation("cuisine", "block/cuisine_juice_flow");
	private static final int DEFAULT_COLOR = 0xF08A19;
	public static Logger logger = LogManager.getLogger();
//	public static final FlowingFluid FLOWING_WATER = new WaterFluid.Flowing();
//	public static final FlowingFluid WATER = new WaterFluid.Source();

	static FluidAttributes.Builder attributesBuilder = FluidAttributes.builder(STILL_OIL_TEXTURE, FLOWING_OIL_TEXTURE).density(1024).viscosity(1024).color(DEFAULT_COLOR);
	public static ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid.Properties(() -> FluidModule.cuisine_juice, () -> FluidModule.FLOWING_cuisine_juice, attributesBuilder);
	public static FlowingFluid FLOWING_cuisine_juice = new VaporizableFluid.Flowing(properties);
	public static FlowingFluid cuisine_juice = new VaporizableFluid.Source(properties);

	@KiwiModule.NoItem
	@KiwiModule.NoCategory
	public static Block cuisine_juice_block = new LiquidBlock(() -> cuisine_juice, BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops());
	@KiwiModule.NoCategory
	public static Item cuisine_juice_Bucket = new BucketItem(() -> cuisine_juice, new Item.Properties().tab(CreativeModeTab.TAB_MISC));

	@SubscribeEvent
	public static void onRenderTypeSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			setTranslucent(FLOWING_cuisine_juice);
			setTranslucent(cuisine_juice);
		});
	}

	private static void setTranslucent(FlowingFluid fluid) {
		ItemBlockRenderTypes.setRenderLayer(fluid.getSource(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(fluid.getFlowing(), RenderType.translucent());
		Type userInfoType = (Type) cuisine_juice;
		//获取属性
		logger.info(MODID+userInfoType.getTypeName());
	}

	//这个函数似乎要注册forge的事件，但是本模块注册了的是kiwi的
//	@SubscribeEvent
//	public static void onDispenserRegister(FMLCommonSetupEvent event) {
//			}

	@Override
	protected void init(InitEvent event) {
		logger.info(MODID + "init");
		DispenseItemBehavior dispenseitembehavior1 = new DefaultDispenseItemBehavior() {
			private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

			public ItemStack execute(BlockSource p_123561_, ItemStack p_123562_) {
				DispensibleContainerItem dispensiblecontaineritem = (DispensibleContainerItem)p_123562_.getItem();
				BlockPos blockpos = p_123561_.getPos().relative(p_123561_.getBlockState().getValue(DispenserBlock.FACING));
				Level level = p_123561_.getLevel();
				if (dispensiblecontaineritem.emptyContents((Player)null, level, blockpos, (BlockHitResult)null)) {
					dispensiblecontaineritem.checkExtraContent((Player)null, level, p_123562_, blockpos);
					return new ItemStack(Items.BUCKET);
				} else {
					return this.defaultDispenseItemBehavior.dispense(p_123561_, p_123562_);
				}
			}
		};
		DispenserBlock.registerBehavior(()->cuisine_juice_Bucket, dispenseitembehavior1);
		MinecraftForge.EVENT_BUS.register(FurnaceFuelBurnTimeEventHandler.instance);
	}
}
