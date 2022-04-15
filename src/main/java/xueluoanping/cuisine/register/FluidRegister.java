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

}
