package xueluoanping.cuisine.register;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.block.blockitem.BlockItemCuisineCrop;
import xueluoanping.cuisine.block.nature.crop.*;

import java.util.ArrayList;

public class CropRegister {
    public static final DeferredRegister<Item> DRBlockItems = DeferredRegister.create(ForgeRegistries.ITEMS, Cuisine.MODID);
    public static final DeferredRegister<Block> DRBlocks = DeferredRegister.create(ForgeRegistries.BLOCKS, Cuisine.MODID);

    public static final FoodProperties SESAME = (new FoodProperties.Builder())
            .nutrition(1).saturationMod(0.2f).build();
    public static final FoodProperties TOMATO = (new FoodProperties.Builder())
            .nutrition(2).saturationMod(0.4f).build();


    public static final RegistryObject<Block> peanut =
            DRBlocks.register("peanut", ()->new BlockPeanut(Block.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Item> peanut_item =
            DRBlockItems.register("peanut", ()->new BlockItemCuisineCrop(peanut.get(),RegisterHelper.foodItem(TOMATO)));

    public static final RegistryObject<Block> sesame =
            DRBlocks.register("sesame", ()->new BlockSesame(Block.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Item> sesame_item =
            DRBlockItems.register("sesame", ()->new BlockItemCuisineCrop(sesame.get(),RegisterHelper.foodItem(TOMATO)));

    public static final RegistryObject<Block> soybean =
            DRBlocks.register("soybean", ()->new BlockSoybean(Block.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Item> soybean_item =
            DRBlockItems.register("soybean", ()->new BlockItemCuisineCrop(soybean.get(),RegisterHelper.foodItem(TOMATO)));

    public static final RegistryObject<Block> rice =
            DRBlocks.register("rice", ()->new BlockRice(Block.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Item> rice_item =
            DRBlockItems.register("rice", ()->new BlockItemCuisineCrop(rice.get(),RegisterHelper.basicItem()));

    public static final RegistryObject<Block> tomato =
            DRBlocks.register("tomato", ()->new BlockTomato(Block.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Item> tomato_item =
            DRBlockItems.register("tomato", ()->new BlockItemCuisineCrop(tomato.get(),RegisterHelper.foodItem(TOMATO)));

    public static final RegistryObject<Block> chili =
            DRBlocks.register("chili", ()->new BlockChili(Block.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Item> chili_item =
            DRBlockItems.register("chili", ()->new BlockItemCuisineCrop(chili.get(),RegisterHelper.foodItem(TOMATO)));

    public static final RegistryObject<Block> garlic =
            DRBlocks.register("garlic", ()->new BlockGarlic(Block.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Item> garlic_item =
            DRBlockItems.register("garlic", ()->new BlockItemCuisineCrop(garlic.get(),RegisterHelper.foodItem(TOMATO)));

    public static final RegistryObject<Block> ginger =
            DRBlocks.register("ginger", ()->new BlockGinger(Block.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Item> ginger_item =
            DRBlockItems.register("ginger", ()->new BlockItemCuisineCrop(ginger.get(),RegisterHelper.foodItem(TOMATO)));

    public static final RegistryObject<Block> sichuan_pepper =
            DRBlocks.register("sichuan_pepper", ()->new BlockSichuanPepper(Block.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Item> sichuan_pepper_item =
            DRBlockItems.register("sichuan_pepper", ()->new BlockItemCuisineCrop(sichuan_pepper.get(),RegisterHelper.foodItem(TOMATO)));

    public static final RegistryObject<Block> scallion =
            DRBlocks.register("scallion", ()->new BlockScallion(Block.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Item> scallion_item =
            DRBlockItems.register("scallion", ()->new BlockItemCuisineCrop(scallion.get(),RegisterHelper.foodItem(TOMATO)));

    public static final RegistryObject<Block> turnip =
            DRBlocks.register("turnip", ()->new BlockTurnip(Block.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Item> turnip_item =
            DRBlockItems.register("turnip", ()->new BlockItemCuisineCrop(turnip.get(),RegisterHelper.foodItem(TOMATO)));

    public static final RegistryObject<Block> chinese_cabbage =
            DRBlocks.register("chinese_cabbage", ()->new BlockChineseCabbage(Block.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Item> chinese_cabbage_item =
            DRBlockItems.register("chinese_cabbage", ()->new BlockItemCuisineCrop(chinese_cabbage.get(),RegisterHelper.foodItem(TOMATO)));

    public static final RegistryObject<Block> lettuce =
            DRBlocks.register("lettuce", ()->new BlockLettuce(Block.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Item> lettuce_item =
            DRBlockItems.register("lettuce", ()->new BlockItemCuisineCrop(lettuce.get(),RegisterHelper.foodItem(TOMATO)));

    // public static final BlockCuisineCrops CORN = new BlockCorn("corn");
    // public static final BlockDoubleCrops CUCUMBER = new BlockDoubleCrops("cucumber", ItemDefinition.of(CROPS, ItemCrops.Variant.CUCUMBER.getMeta()));
    public static final RegistryObject<Block> corn =
            DRBlocks.register("corn", ()->new BlockCorn(Block.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Item> corn_item =
            DRBlockItems.register("corn", ()->new BlockItemCuisineCrop(corn.get(),RegisterHelper.foodItem(TOMATO)));

    public static final RegistryObject<Block> cucumber =
            DRBlocks.register("cucumber", ()->new BlockCucumber(Block.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Item> cucumber_item =
            DRBlockItems.register("cucumber", ()->new BlockItemCuisineCrop(cucumber.get(),RegisterHelper.foodItem(TOMATO)));

    public static final RegistryObject<Block> green_pepper =
            DRBlocks.register("green_pepper", ()->new BlockGreenPepper(Block.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Item> green_pepper_item =
            DRBlockItems.register("green_pepper", ()->new BlockItemCuisineCrop(green_pepper.get(),RegisterHelper.foodItem(TOMATO)));

    public static final RegistryObject<Block> red_pepper =
            DRBlocks.register("red_pepper", ()->new BlockRedPepper(Block.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Item> red_pepper_item =
            DRBlockItems.register("red_pepper", ()->new BlockItemCuisineCrop(red_pepper.get(),RegisterHelper.foodItem(TOMATO)));

    public static final RegistryObject<Block> leek =
            DRBlocks.register("leek", ()->new BlockLeek(Block.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Item> leek_item =
            DRBlockItems.register("leek", ()->new BlockItemCuisineCrop(leek.get(),RegisterHelper.foodItem(TOMATO)));

    public static final RegistryObject<Block> onion =
            DRBlocks.register("onion", ()->new BlockOnion(Block.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Item> onion_item =
            DRBlockItems.register("onion", ()->new BlockItemCuisineCrop(onion.get(),RegisterHelper.foodItem(TOMATO)));

    public static final RegistryObject<Block> eggplant =
            DRBlocks.register("eggplant", ()->new BlockEggplant(Block.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Item> eggplant_item =
            DRBlockItems.register("eggplant", ()->new BlockItemCuisineCrop(eggplant.get(),RegisterHelper.foodItem(TOMATO)));

    public static final RegistryObject<Block> spinach =
            DRBlocks.register("spinach", ()->new BlockSpinach(Block.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Item> spinach_item =
            DRBlockItems.register("spinach", ()->new BlockItemCuisineCrop(spinach.get(),RegisterHelper.foodItem(TOMATO)));

    public static ArrayList<RegistryObject<Item>> getAllCrops(){
        ArrayList<RegistryObject<Item>> itemList = new ArrayList<>();
        itemList.addAll(CropRegister.DRBlockItems.getEntries());
        return itemList;
    }

    public static ArrayList<RegistryObject<Block>> getAllCropBlocks(){
        ArrayList<RegistryObject<Block>> itemList = new ArrayList<>();
        itemList.addAll(CropRegister.DRBlocks.getEntries());
        return itemList;
    }

    public static ArrayList<RegistryObject<Item>> getWildCrops(){
        ArrayList<RegistryObject<Item>> itemList = getAllCrops();
        itemList.remove(chili_item);
        itemList.remove(peanut_item);
        itemList.remove(sesame_item);
        itemList.remove(soybean_item);
        itemList.remove(garlic_item);
        return itemList;
    }

    public static ArrayList<RegistryObject<Item>> getGrassCrops(){
        ArrayList<RegistryObject<Item>> itemList = new ArrayList<>();
        itemList.add(peanut_item);
        itemList.add(sesame_item);
        itemList.add(soybean_item);
        return itemList;
    }
}
