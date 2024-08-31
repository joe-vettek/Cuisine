package xueluoanping.cuisine.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;

import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.fluids.TeaFluidType;

public class FluidRegister {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Cuisine.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, Cuisine.MODID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, Cuisine.MODID);
    public static DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, Cuisine.MODID);

    public static final Item.Properties BUCKET_PROPERTIES = new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1);
    public static final ResourceLocation WATER_STILL_TEXTURE = ResourceLocation.tryParse("minecraft:block/water_still");
    public static final ResourceLocation WATER_FLOW_TEXTURE = ResourceLocation.tryParse("minecraft:block/water_flow");

    public static final ResourceLocation STILL_OIL_TEXTURE = Cuisine.rl("block/cuisine_juice_still");
    public static final ResourceLocation FLOWING_OIL_TEXTURE = Cuisine.rl("block/cuisine_juice_flow");
    //	0xAARRGGBB

    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> CUISINE_JUICE = FLUIDS.register("cuisine_juice", () -> new BaseFlowingFluid.Source(FluidRegister.JUICE_FLOWING_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> CUISINE_JUICE_FLOWING = FLUIDS.register("cuisine_juice_flowing", () -> new BaseFlowingFluid.Flowing(FluidRegister.JUICE_FLOWING_PROPERTIES));
    public static final DeferredHolder<Item, BucketItem> JUICE_BUCKET = ITEMS.register("cuisine_juice_bucket", () -> new BucketItem(FluidRegister.CUISINE_JUICE.get(), BUCKET_PROPERTIES));
    public static final DeferredHolder<Block, LiquidBlock> JUICE_BLOCK = BLOCKS.register("cuisine_juice", () -> new LiquidBlock(FluidRegister.CUISINE_JUICE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<FluidType, TeaFluidType> JUICE_TYPE = FLUID_TYPES.register("cuisine_juice", () -> new TeaFluidType(FluidType.Properties.create(), STILL_OIL_TEXTURE, FLOWING_OIL_TEXTURE).color(0xCCF08A19));
    public static final BaseFlowingFluid.Properties JUICE_FLOWING_PROPERTIES = new BaseFlowingFluid.Properties(
            JUICE_TYPE, CUISINE_JUICE, CUISINE_JUICE_FLOWING)
            .bucket(JUICE_BUCKET)
            .block(FluidRegister.JUICE_BLOCK)
            .explosionResistance(100F);


    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> soy_sauce = FLUIDS.register("soy_sauce", () -> new BaseFlowingFluid.Source(FluidRegister.soy_sauce_flowing_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> soy_sauce_flowing = FLUIDS.register("soy_sauce_flowing", () -> new BaseFlowingFluid.Flowing(FluidRegister.soy_sauce_flowing_PROPERTIES));
    public static final DeferredHolder<Item, BucketItem> soy_sauce_BUCKET = ITEMS.register("soy_sauce_bucket", () -> new BucketItem(FluidRegister.soy_sauce.get(), BUCKET_PROPERTIES));
    public static final DeferredHolder<Block, LiquidBlock> soy_sauce_block = BLOCKS.register("soy_sauce", () -> new LiquidBlock(FluidRegister.soy_sauce.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<FluidType, TeaFluidType> soy_sauce_TYPE = FLUID_TYPES.register("soy_sauce", () -> new TeaFluidType(FluidType.Properties.create().density(1001)).color(0xFF4989E3).texture("soy_sauce"));
    public static final BaseFlowingFluid.Properties soy_sauce_flowing_PROPERTIES = new BaseFlowingFluid.Properties(
            soy_sauce_TYPE, soy_sauce, soy_sauce_flowing)
            .bucket(soy_sauce_BUCKET)
            .block(FluidRegister.soy_sauce_block)
            .explosionResistance(100F);

    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> sesame_oil = FLUIDS.register("sesame_oil", () -> new BaseFlowingFluid.Source(FluidRegister.sesame_oil_flowing_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> sesame_oil_flowing = FLUIDS.register("sesame_oil_flowing", () -> new BaseFlowingFluid.Flowing(FluidRegister.sesame_oil_flowing_PROPERTIES));
    public static final DeferredHolder<Item, BucketItem> sesame_oil_BUCKET = ITEMS.register("sesame_oil_bucket", () -> new BucketItem(FluidRegister.sesame_oil.get(), BUCKET_PROPERTIES));
    public static final DeferredHolder<Block, LiquidBlock> sesame_oil_block = BLOCKS.register("sesame_oil", () -> new LiquidBlock(FluidRegister.sesame_oil.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<FluidType, TeaFluidType> sesame_oil_TYPE = FLUID_TYPES.register("sesame_oil", () -> new TeaFluidType(FluidType.Properties.create().density(800)).color(0x88FFFFFF).texture("sesame_oil"));
    public static final BaseFlowingFluid.Properties sesame_oil_flowing_PROPERTIES = new BaseFlowingFluid.Properties(
            sesame_oil_TYPE, sesame_oil, sesame_oil_flowing)
            .bucket(sesame_oil_BUCKET)
            .block(FluidRegister.sesame_oil_block)
            .explosionResistance(100F);


    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> edible_oil = FLUIDS.register("edible_oil", () -> new BaseFlowingFluid.Source(FluidRegister.edible_oil_flowing_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> edible_oil_flowing = FLUIDS.register("edible_oil_flowing", () -> new BaseFlowingFluid.Flowing(FluidRegister.edible_oil_flowing_PROPERTIES));
    public static final DeferredHolder<Item, BucketItem> edible_oil_BUCKET = ITEMS.register("edible_oil_bucket", () -> new BucketItem(FluidRegister.edible_oil.get(), BUCKET_PROPERTIES));
    public static final DeferredHolder<Block, LiquidBlock> edible_oil_block = BLOCKS.register("edible_oil", () -> new LiquidBlock(FluidRegister.edible_oil.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<FluidType, TeaFluidType> edible_oil_TYPE = FLUID_TYPES.register("edible_oil", () -> new TeaFluidType(FluidType.Properties.create().density(800)).color(0x88FFFFFF).texture("edible_oil"));
    public static final BaseFlowingFluid.Properties edible_oil_flowing_PROPERTIES = new BaseFlowingFluid.Properties(
            edible_oil_TYPE, edible_oil, edible_oil_flowing)
            .bucket(edible_oil_BUCKET)
            .block(FluidRegister.edible_oil_block)
            .explosionResistance(100F);

    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> rice_vinegar = FLUIDS.register("rice_vinegar", () -> new BaseFlowingFluid.Source(FluidRegister.rice_vinegar_flowing_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> rice_vinegar_flowing = FLUIDS.register("rice_vinegar_flowing", () -> new BaseFlowingFluid.Flowing(FluidRegister.rice_vinegar_flowing_PROPERTIES));
    public static final DeferredHolder<Item, BucketItem> rice_vinegar_BUCKET = ITEMS.register("rice_vinegar_bucket", () -> new BucketItem(FluidRegister.rice_vinegar.get(), BUCKET_PROPERTIES));
    public static final DeferredHolder<Block, LiquidBlock> rice_vinegar_block = BLOCKS.register("rice_vinegar", () -> new LiquidBlock(FluidRegister.rice_vinegar.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<FluidType, TeaFluidType> rice_vinegar_TYPE = FLUID_TYPES.register("rice_vinegar", () -> new TeaFluidType(FluidType.Properties.create().density(1001)).color(0x77FFFFAA).texture("rice_vinegar"));
    public static final BaseFlowingFluid.Properties rice_vinegar_flowing_PROPERTIES = new BaseFlowingFluid.Properties(
            rice_vinegar_TYPE, rice_vinegar, rice_vinegar_flowing)
            .bucket(rice_vinegar_BUCKET)
            .block(FluidRegister.rice_vinegar_block)
            .explosionResistance(100F);

    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> fruit_vinegar = FLUIDS.register("fruit_vinegar", () -> new BaseFlowingFluid.Source(FluidRegister.fruit_vinegar_flowing_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> fruit_vinegar_flowing = FLUIDS.register("fruit_vinegar_flowing", () -> new BaseFlowingFluid.Flowing(FluidRegister.fruit_vinegar_flowing_PROPERTIES));
    public static final DeferredHolder<Item, BucketItem> fruit_vinegar_BUCKET = ITEMS.register("fruit_vinegar_bucket", () -> new BucketItem(FluidRegister.fruit_vinegar.get(), BUCKET_PROPERTIES));
    public static final DeferredHolder<Block, LiquidBlock> fruit_vinegar_block = BLOCKS.register("fruit_vinegar", () -> new LiquidBlock(FluidRegister.fruit_vinegar.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<FluidType, TeaFluidType> fruit_vinegar_TYPE = FLUID_TYPES.register("fruit_vinegar", () -> new TeaFluidType(FluidType.Properties.create().density(1001)).color(0xEEFFFFFF).texture("fruit_vinegar"));
    public static final BaseFlowingFluid.Properties fruit_vinegar_flowing_PROPERTIES = new BaseFlowingFluid.Properties(
            fruit_vinegar_TYPE, fruit_vinegar, fruit_vinegar_flowing)
            .bucket(fruit_vinegar_BUCKET)
            .block(FluidRegister.fruit_vinegar_block)
            .explosionResistance(100F);

    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> soy_milk = FLUIDS.register("soy_milk", () -> new BaseFlowingFluid.Source(FluidRegister.soy_milk_flowing_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> soy_milk_flowing = FLUIDS.register("soy_milk_flowing", () -> new BaseFlowingFluid.Flowing(FluidRegister.soy_milk_flowing_PROPERTIES));
    public static final DeferredHolder<Item, BucketItem> soy_milk_BUCKET = ITEMS.register("soy_milk_bucket", () -> new BucketItem(FluidRegister.soy_milk.get(), BUCKET_PROPERTIES));
    public static final DeferredHolder<Block, LiquidBlock> soy_milk_block = BLOCKS.register("soy_milk", () -> new LiquidBlock(FluidRegister.soy_milk.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final DeferredHolder<FluidType, TeaFluidType> soy_milk_TYPE = FLUID_TYPES.register("soy_milk", () -> new TeaFluidType(FluidType.Properties.create().density(1001)).texture("soy_milk"));
    public static final BaseFlowingFluid.Properties soy_milk_flowing_PROPERTIES = new BaseFlowingFluid.Properties(
            soy_milk_TYPE, soy_milk, soy_milk_flowing)
            .bucket(soy_milk_BUCKET)
            .block(FluidRegister.soy_milk_block)
            .explosionResistance(100F);

}
