package xueluoanping.cuisine.register;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.fluids.VaporizableFluid;

public class FluidRegister {
    public static final DeferredRegister<Item> DRFluidBuckets = DeferredRegister.create(ForgeRegistries.ITEMS, Cuisine.MODID);
    public static final DeferredRegister<Block> DRFluidBlocks = DeferredRegister.create(ForgeRegistries.BLOCKS, Cuisine.MODID);
    public static final DeferredRegister<Fluid> DRFluids = DeferredRegister.create(ForgeRegistries.FLUIDS, Cuisine.MODID);

    public static final ResourceLocation STILL_OIL_TEXTURE = new ResourceLocation("cuisine", "block/cuisine_juice_still");
    public static final ResourceLocation FLOWING_OIL_TEXTURE = new ResourceLocation("cuisine", "block/cuisine_juice_flow");
    //	0xAARRGGBB
    private static final int DEFAULT_COLOR = 0xCCF08A19;
    public static FluidAttributes.Builder attributesBuilder = VaporizableFluid.JuiceAttributes.builder(STILL_OIL_TEXTURE, FLOWING_OIL_TEXTURE).density(1024).viscosity(1024).color(DEFAULT_COLOR);
    public static ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid.Properties(FluidRegister.juice, FluidRegister.juice_flowing, attributesBuilder);
    public static final RegistryObject<FlowingFluid> juice = DRFluids.register("cuisine_juice",
            () -> new VaporizableFluid.Source(FluidRegister.properties));
    public static final RegistryObject<FlowingFluid> juice_flowing = DRFluids.register("cuisine_juice_flowing",
            () -> new VaporizableFluid.Flowing(FluidRegister.properties));
    public static final RegistryObject<Block> juice_block =
            DRFluidBlocks.register("cuisine_juice_block", () -> new LiquidBlock(FluidRegister.juice, BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()));
    public static final RegistryObject<Item> juice_bucket =
            DRFluidBuckets.register("cuisine_juice_bucket", () -> new BucketItem(FluidRegister.juice, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

	public static FluidAttributes.Builder soy_sauce_attributesBuilder = VaporizableFluid.JuiceAttributes.builder(STILL_OIL_TEXTURE, FLOWING_OIL_TEXTURE).density(1024).viscosity(1024).color(DEFAULT_COLOR);
	public static ForgeFlowingFluid.Properties soy_sauce_properties = new ForgeFlowingFluid.Properties(FluidRegister.soy_sauce, FluidRegister.soy_sauce_flowing, soy_sauce_attributesBuilder);
	public static final RegistryObject<FlowingFluid> soy_sauce = DRFluids.register("soy_sauce",
			() -> new VaporizableFluid.Source(FluidRegister.soy_sauce_properties));
	public static final RegistryObject<FlowingFluid> soy_sauce_flowing = DRFluids.register("soy_sauce_flowing",
			() -> new VaporizableFluid.Flowing(FluidRegister.soy_sauce_properties));
	public static final RegistryObject<Block> soy_sauce_block =
			DRFluidBlocks.register("soy_sauce_block", () -> new LiquidBlock(FluidRegister.soy_sauce, BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()));
	public static final RegistryObject<Item> soy_sauce_bucket =
			DRFluidBuckets.register("soy_sauce_bucket", () -> new BucketItem(FluidRegister.soy_sauce, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

	public static FluidAttributes.Builder sesame_oil_attributesBuilder = VaporizableFluid.JuiceAttributes.builder(STILL_OIL_TEXTURE, FLOWING_OIL_TEXTURE).density(1024).viscosity(1024).color(DEFAULT_COLOR);
	public static ForgeFlowingFluid.Properties sesame_oil_properties = new ForgeFlowingFluid.Properties(FluidRegister.sesame_oil, FluidRegister.sesame_oil_flowing, attributesBuilder);
	public static final RegistryObject<FlowingFluid> sesame_oil = DRFluids.register("sesame_oil",
			() -> new VaporizableFluid.Source(FluidRegister.sesame_oil_properties));
	public static final RegistryObject<FlowingFluid> sesame_oil_flowing = DRFluids.register("sesame_oil_flowing",
			() -> new VaporizableFluid.Flowing(FluidRegister.sesame_oil_properties));
	public static final RegistryObject<Block> sesame_oil_block =
			DRFluidBlocks.register("sesame_oil_block", () -> new LiquidBlock(FluidRegister.juice, BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()));
	public static final RegistryObject<Item> sesame_oil_bucket =
			DRFluidBuckets.register("sesame_oil_bucket", () -> new BucketItem(FluidRegister.juice, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

	public static FluidAttributes.Builder edible_oil_attributesBuilder = VaporizableFluid.JuiceAttributes.builder(STILL_OIL_TEXTURE, FLOWING_OIL_TEXTURE).density(1024).viscosity(1024).color(DEFAULT_COLOR);
	public static ForgeFlowingFluid.Properties edible_oil_properties = new ForgeFlowingFluid.Properties(FluidRegister.edible_oil, FluidRegister.edible_oil_flowing, attributesBuilder);
	public static final RegistryObject<FlowingFluid> edible_oil = DRFluids.register("edible_oil",
			() -> new VaporizableFluid.Source(FluidRegister.edible_oil_properties));
	public static final RegistryObject<FlowingFluid> edible_oil_flowing = DRFluids.register("edible_oil_flowing",
			() -> new VaporizableFluid.Flowing(FluidRegister.edible_oil_properties));
	public static final RegistryObject<Block> edible_oil_block =
			DRFluidBlocks.register("edible_oil_block", () -> new LiquidBlock(FluidRegister.juice, BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()));
	public static final RegistryObject<Item> edible_oil_bucket =
			DRFluidBuckets.register("edible_oil_bucket", () -> new BucketItem(FluidRegister.juice, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

	public static FluidAttributes.Builder rice_vinegar_attributesBuilder = VaporizableFluid.JuiceAttributes.builder(STILL_OIL_TEXTURE, FLOWING_OIL_TEXTURE).density(1024).viscosity(1024).color(DEFAULT_COLOR);
	public static ForgeFlowingFluid.Properties rice_vinegar_properties = new ForgeFlowingFluid.Properties(FluidRegister.rice_vinegar, FluidRegister.rice_vinegar_flowing, attributesBuilder);
	public static final RegistryObject<FlowingFluid> rice_vinegar = DRFluids.register("rice_vinegar",
			() -> new VaporizableFluid.Source(FluidRegister.rice_vinegar_properties));
	public static final RegistryObject<FlowingFluid> rice_vinegar_flowing = DRFluids.register("rice_vinegar_flowing",
			() -> new VaporizableFluid.Flowing(FluidRegister.rice_vinegar_properties));
	public static final RegistryObject<Block> rice_vinegar_block =
			DRFluidBlocks.register("rice_vinegar_block", () -> new LiquidBlock(FluidRegister.juice, BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()));
	public static final RegistryObject<Item> rice_vinegar_bucket =
			DRFluidBuckets.register("rice_vinegar_bucket", () -> new BucketItem(FluidRegister.juice, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

	public static FluidAttributes.Builder fruit_vinegar_attributesBuilder = VaporizableFluid.JuiceAttributes.builder(STILL_OIL_TEXTURE, FLOWING_OIL_TEXTURE).density(1024).viscosity(1024).color(DEFAULT_COLOR);
	public static ForgeFlowingFluid.Properties fruit_vinegar_properties = new ForgeFlowingFluid.Properties(FluidRegister.fruit_vinegar, FluidRegister.fruit_vinegar_flowing, attributesBuilder);
	public static final RegistryObject<FlowingFluid> fruit_vinegar = DRFluids.register("fruit_vinegar",
			() -> new VaporizableFluid.Source(FluidRegister.fruit_vinegar_properties));
	public static final RegistryObject<FlowingFluid> fruit_vinegar_flowing = DRFluids.register("fruit_vinegar_flowing",
			() -> new VaporizableFluid.Flowing(FluidRegister.fruit_vinegar_properties));
	public static final RegistryObject<Block> fruit_vinegar_block =
			DRFluidBlocks.register("fruit_vinegar_block", () -> new LiquidBlock(FluidRegister.juice, BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()));
	public static final RegistryObject<Item> fruit_vinegar_bucket =
			DRFluidBuckets.register("fruit_vinegar_bucket", () -> new BucketItem(FluidRegister.juice, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));


	public static FluidAttributes.Builder soy_milk_attributesBuilder = VaporizableFluid.JuiceAttributes.builder(STILL_OIL_TEXTURE, FLOWING_OIL_TEXTURE).density(1024).viscosity(1024).color(DEFAULT_COLOR);
	public static ForgeFlowingFluid.Properties soy_milk_properties = new ForgeFlowingFluid.Properties(FluidRegister.soy_milk, FluidRegister.soy_milk_flowing, attributesBuilder);
	public static final RegistryObject<FlowingFluid> soy_milk = DRFluids.register("soy_milk",
			() -> new VaporizableFluid.Source(FluidRegister.soy_milk_properties));
	public static final RegistryObject<FlowingFluid> soy_milk_flowing = DRFluids.register("soy_milk_flowing",
			() -> new VaporizableFluid.Flowing(FluidRegister.soy_milk_properties));
	public static final RegistryObject<Block> soy_milk_block =
			DRFluidBlocks.register("soy_milk_block", () -> new LiquidBlock(FluidRegister.juice, BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()));
	public static final RegistryObject<Item> soy_milk_bucket =
			DRFluidBuckets.register("soy_milk_bucket", () -> new BucketItem(FluidRegister.juice, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

}
