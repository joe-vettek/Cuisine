package xueluoanping.cuisine.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.block.blockitem.BlockItemCuisineCrop;
import xueluoanping.cuisine.block.nature.crop.*;

import java.util.ArrayList;

public class CropRegister {
    public static final DeferredRegister<Item> DRBlockItems = DeferredRegister.create(Registries.ITEM, Cuisine.MODID);
    public static final DeferredRegister<Block> DRBlocks = DeferredRegister.create(Registries.BLOCK, Cuisine.MODID);

    public static final FoodProperties SESAME = (new FoodProperties.Builder())
            .nutrition(1).saturationModifier(0.2f).build();
    public static final FoodProperties TOMATO = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.4f).build();


    public static final DeferredHolder<Block, BlockPeanut> peanut =
            DRBlocks.register("peanut", () -> new BlockPeanut(Block.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final DeferredHolder<Item, BlockItemCuisineCrop> peanut_item =
            DRBlockItems.register("peanut", () -> new BlockItemCuisineCrop(peanut.get(), RegisterHelper.foodItem(TOMATO)));

    public static final DeferredHolder<Block, BlockSesame> sesame =
            DRBlocks.register("sesame", () -> new BlockSesame(Block.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final DeferredHolder<Item, BlockItemCuisineCrop> sesame_item =
            DRBlockItems.register("sesame", () -> new BlockItemCuisineCrop(sesame.get(), RegisterHelper.foodItem(TOMATO)));

    public static final DeferredHolder<Block, BlockSoybean> soybean =
            DRBlocks.register("soybean", () -> new BlockSoybean(Block.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final DeferredHolder<Item, BlockItemCuisineCrop> soybean_item =
            DRBlockItems.register("soybean", () -> new BlockItemCuisineCrop(soybean.get(), RegisterHelper.foodItem(TOMATO)));

    public static final DeferredHolder<Block, BlockRice> rice =
            DRBlocks.register("rice", () -> new BlockRice(Block.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final DeferredHolder<Item, BlockItemCuisineCrop> rice_item =
            DRBlockItems.register("rice", () -> new BlockItemCuisineCrop(rice.get(), RegisterHelper.basicItem()));

    public static final DeferredHolder<Block, BlockTomato> tomato =
            DRBlocks.register("tomato", () -> new BlockTomato(Block.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final DeferredHolder<Item, BlockItemCuisineCrop> tomato_item =
            DRBlockItems.register("tomato", () -> new BlockItemCuisineCrop(tomato.get(), RegisterHelper.foodItem(TOMATO)));

    public static final DeferredHolder<Block, BlockChili> chili =
            DRBlocks.register("chili", () -> new BlockChili(Block.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final DeferredHolder<Item, BlockItemCuisineCrop> chili_item =
            DRBlockItems.register("chili", () -> new BlockItemCuisineCrop(chili.get(), RegisterHelper.foodItem(TOMATO)));

    public static final DeferredHolder<Block, BlockGarlic> garlic =
            DRBlocks.register("garlic", () -> new BlockGarlic(Block.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final DeferredHolder<Item, BlockItemCuisineCrop> garlic_item =
            DRBlockItems.register("garlic", () -> new BlockItemCuisineCrop(garlic.get(), RegisterHelper.foodItem(TOMATO)));

    public static final DeferredHolder<Block, BlockGinger> ginger =
            DRBlocks.register("ginger", () -> new BlockGinger(Block.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final DeferredHolder<Item, BlockItemCuisineCrop> ginger_item =
            DRBlockItems.register("ginger", () -> new BlockItemCuisineCrop(ginger.get(), RegisterHelper.foodItem(TOMATO)));

    public static final DeferredHolder<Block, BlockSichuanPepper> sichuan_pepper =
            DRBlocks.register("sichuan_pepper", () -> new BlockSichuanPepper(Block.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final DeferredHolder<Item, BlockItemCuisineCrop> sichuan_pepper_item =
            DRBlockItems.register("sichuan_pepper", () -> new BlockItemCuisineCrop(sichuan_pepper.get(), RegisterHelper.foodItem(TOMATO)));

    public static final DeferredHolder<Block, BlockScallion> scallion =
            DRBlocks.register("scallion", () -> new BlockScallion(Block.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final DeferredHolder<Item, BlockItemCuisineCrop> scallion_item =
            DRBlockItems.register("scallion", () -> new BlockItemCuisineCrop(scallion.get(), RegisterHelper.foodItem(TOMATO)));

    public static final DeferredHolder<Block, BlockTurnip> turnip =
            DRBlocks.register("turnip", () -> new BlockTurnip(Block.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final DeferredHolder<Item, BlockItemCuisineCrop> turnip_item =
            DRBlockItems.register("turnip", () -> new BlockItemCuisineCrop(turnip.get(), RegisterHelper.foodItem(TOMATO)));

    public static final DeferredHolder<Block, BlockChineseCabbage> chinese_cabbage =
            DRBlocks.register("chinese_cabbage", () -> new BlockChineseCabbage(Block.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final DeferredHolder<Item, BlockItemCuisineCrop> chinese_cabbage_item =
            DRBlockItems.register("chinese_cabbage", () -> new BlockItemCuisineCrop(chinese_cabbage.get(), RegisterHelper.foodItem(TOMATO)));

    public static final DeferredHolder<Block, BlockLettuce> lettuce =
            DRBlocks.register("lettuce", () -> new BlockLettuce(Block.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final DeferredHolder<Item, BlockItemCuisineCrop> lettuce_item =
            DRBlockItems.register("lettuce", () -> new BlockItemCuisineCrop(lettuce.get(), RegisterHelper.foodItem(TOMATO)));

    // public static final BlockCuisineCrops CORN = new BlockCorn("corn");
    // public static final BlockDoubleCrops CUCUMBER = new BlockDoubleCrops("cucumber", ItemDefinition.of(CROPS, ItemCrops.Variant.CUCUMBER.getMeta()));
    public static final DeferredHolder<Block, BlockCorn> corn =
            DRBlocks.register("corn", () -> new BlockCorn(Block.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final DeferredHolder<Item, BlockItemCuisineCrop> corn_item =
            DRBlockItems.register("corn", () -> new BlockItemCuisineCrop(corn.get(), RegisterHelper.foodItem(TOMATO)));

    public static final DeferredHolder<Block, BlockCucumber> cucumber =
            DRBlocks.register("cucumber", () -> new BlockCucumber(Block.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final DeferredHolder<Item, BlockItemCuisineCrop> cucumber_item =
            DRBlockItems.register("cucumber", () -> new BlockItemCuisineCrop(cucumber.get(), RegisterHelper.foodItem(TOMATO)));

    public static final DeferredHolder<Block, BlockGreenPepper> green_pepper =
            DRBlocks.register("green_pepper", () -> new BlockGreenPepper(Block.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final DeferredHolder<Item, BlockItemCuisineCrop> green_pepper_item =
            DRBlockItems.register("green_pepper", () -> new BlockItemCuisineCrop(green_pepper.get(), RegisterHelper.foodItem(TOMATO)));

    public static final DeferredHolder<Block, BlockRedPepper> red_pepper =
            DRBlocks.register("red_pepper", () -> new BlockRedPepper(Block.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final DeferredHolder<Item, BlockItemCuisineCrop> red_pepper_item =
            DRBlockItems.register("red_pepper", () -> new BlockItemCuisineCrop(red_pepper.get(), RegisterHelper.foodItem(TOMATO)));

    public static final DeferredHolder<Block, BlockLeek> leek =
            DRBlocks.register("leek", () -> new BlockLeek(Block.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final DeferredHolder<Item, BlockItemCuisineCrop> leek_item =
            DRBlockItems.register("leek", () -> new BlockItemCuisineCrop(leek.get(), RegisterHelper.foodItem(TOMATO)));

    public static final DeferredHolder<Block, BlockOnion> onion =
            DRBlocks.register("onion", () -> new BlockOnion(Block.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final DeferredHolder<Item, BlockItemCuisineCrop> onion_item =
            DRBlockItems.register("onion", () -> new BlockItemCuisineCrop(onion.get(), RegisterHelper.foodItem(TOMATO)));

    public static final DeferredHolder<Block, BlockEggplant> eggplant =
            DRBlocks.register("eggplant", () -> new BlockEggplant(Block.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final DeferredHolder<Item, BlockItemCuisineCrop> eggplant_item =
            DRBlockItems.register("eggplant", () -> new BlockItemCuisineCrop(eggplant.get(), RegisterHelper.foodItem(TOMATO)));

    public static final DeferredHolder<Block, BlockSpinach> spinach =
            DRBlocks.register("spinach", () -> new BlockSpinach(Block.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final DeferredHolder<Item, BlockItemCuisineCrop> spinach_item =
            DRBlockItems.register("spinach", () -> new BlockItemCuisineCrop(spinach.get(), RegisterHelper.foodItem(TOMATO)));

    public static ArrayList<DeferredHolder<Item, ? extends Item>> getAllCrops() {
        ArrayList<DeferredHolder<Item, ? extends Item>> itemList = new ArrayList<>();
        itemList.addAll(CropRegister.DRBlockItems.getEntries());
        return itemList;
    }

    public static ArrayList<DeferredHolder<Block, ? extends Block>> getAllCropBlocks() {
        ArrayList<DeferredHolder<Block, ? extends Block>> itemList = new ArrayList<>();
        itemList.addAll(CropRegister.DRBlocks.getEntries());
        return itemList;
    }

    public static ArrayList<DeferredHolder<Item, ? extends Item>> getWildCrops() {
        ArrayList<DeferredHolder<Item, ? extends Item>> itemList = getAllCrops();
        itemList.remove(chili_item);
        itemList.remove(peanut_item);
        itemList.remove(sesame_item);
        itemList.remove(soybean_item);
        itemList.remove(garlic_item);
        return itemList;
    }

    public static ArrayList<DeferredHolder<Item, ? extends Item>> getGrassCrops() {
        ArrayList<DeferredHolder<Item, ? extends Item>> itemList = new ArrayList<>();
        itemList.add(peanut_item);
        itemList.add(sesame_item);
        itemList.add(soybean_item);
        return itemList;
    }
}
