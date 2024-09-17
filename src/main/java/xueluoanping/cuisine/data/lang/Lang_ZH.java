package xueluoanping.cuisine.data.lang;

import com.google.gson.JsonObject;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.FlowingFluid;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.register.*;

import java.util.function.Supplier;

public class Lang_ZH extends LangHelper {
    public Lang_ZH(PackOutput gen, ExistingFileHelper helper) {
        super(gen, helper, Cuisine.MODID, "zh_cn");
    }

    private void addItemGroup(CreativeModeTab group, String name) {
        add(group.getDisplayName().getString(), name);
    }

    private void addHint(Supplier<? extends Block> key, String hint) {
        add(key.get().getDescriptionId() + ".hint", hint);
    }

    private void addFluid(Supplier<? extends FlowingFluid> key, String name) {
        String path = BuiltInRegistries.FLUID.getKey(key.get()).getPath();
        String standard = Cuisine.MODID + "." + path;
        add("fluid_type." + standard, name);
        add("block." + standard, name);
        add("item." + standard + "_bucket", name + "桶");
    }

    @Override
    protected void addTranslations() {
        add(Cuisine.MODID, "料理工艺•追忆（烟火之忆）");
        addItemGroup(ModContent.CREATIVE_TAB, "料理工艺•追忆");

        addBlock(BlockRegister.bamboo_root, "竹根");
        addBlock(BlockRegister.bamboo_plant, "淡竹笋");
        addBlock(BlockRegister.bamboo, "淡竹");
        addBlock(BlockEntityRegister.fire_pit, "火塘");

        addBlock(BlockEntityRegister.barbecue_rack, "烧烤架");
        addBlock(BlockEntityRegister.wok_on_fire_pit, "火塘与铁锅");
        addBlock(BlockEntityRegister.mill, "磨");
        addBlock(() -> BlockEntityRegister.basin.get(), "盆");
        addHint(BlockEntityRegister.basin::get, "§4用途：\n§3所有的盆都可以用来压碎蔬果或者浸泡物品，非木质盆可以在火坑上加热获取粗盐");

        JsonObject jsonObject = loadLang("zh_cn");

        BlockEntityRegister.basinColored.forEach((dyeColor, blockRegistryObject) -> {
            String s = jsonObject.get(BlockEntityRegister.colorBlockMap.get(dyeColor).getDescriptionId()).getAsString();
            addBlock(blockRegistryObject, s.replace("陶瓦", "") + "陶盆");
        });

        addFluid(FluidRegister.CUISINE_JUICE, "丢失味道的果汁");
        add("fluid." + Cuisine.MODID + ".cuisine_juice_with_material", "汁");


        addItem(ItemRegister.flour, "面粉");
        addItem(ItemRegister.rice_powder, "米粉");
        addItem(ItemRegister.wooden_handle, "木手柄");
        addItem(ItemRegister.wooden_arm, "木手臂");
        addItem(ItemRegister.bamboo_charcoal, "竹炭");
        addItem(ItemRegister.dough, "面团");


        addItem(ItemRegister.kitchen_knife, "菜刀");
        addItem(ItemRegister.iron_spatula, "炒菜铲");

        addBlock(CropRegister.peanut, "花生");
        addBlock(CropRegister.sesame, "芝麻");
        addBlock(CropRegister.soybean, "大豆");
        addBlock(CropRegister.rice, "水稻");
        addBlock(CropRegister.tomato, "西红柿");
        addBlock(CropRegister.chili, "辣椒");
        addBlock(CropRegister.garlic, "大蒜");
        addBlock(CropRegister.ginger, "姜");
        addBlock(CropRegister.sichuan_pepper, "花椒");
        addBlock(CropRegister.scallion, "葱");
        addBlock(CropRegister.turnip, "白萝卜");
        addBlock(CropRegister.chinese_cabbage, "大白菜");
        addBlock(CropRegister.corn, "玉米");
        addBlock(CropRegister.cucumber, "黄瓜");
        addBlock(CropRegister.green_pepper, "青椒");
        addBlock(CropRegister.red_pepper, "红椒");
        addBlock(CropRegister.leek, "韭菜");
        addBlock(CropRegister.lettuce, "生菜");
        addBlock(CropRegister.onion, "洋葱");
        addBlock(CropRegister.eggplant, "茄子");
        addBlock(CropRegister.spinach, "菠菜");

        addItem(ItemRegister.cubed, "块");

        addDebugKey(ModConstant.DebugKey.try_place_bamboo, "尝试放置竹子");


        addItem(ItemRegister.sliced, "片");
        addItem(ItemRegister.shredded, "丝");
        addItem(ItemRegister.diced, "丁");
        addItem(ItemRegister.minced, "碎");
        addItem(ItemRegister.paste, "酱");

        addItem(ItemRegister.tofu, "豆腐");
        addBlock(BlockRegister.tofu_block, "豆腐块");

        addItem(ItemRegister.chili_powder, "辣椒粉");
        addItem(ItemRegister.crude_salt, "粗盐");
        addItem(ItemRegister.salt, "盐");
        addItem(ItemRegister.unrefined_sugar, "粗制糖");
        addItem(ItemRegister.sichuan_pepper_powder, "花椒粉");

        addItem(ItemRegister.spice_bottle, "调味瓶");
        addItem(ItemRegister.fan, "扇子");
        addItem(ItemRegister.water_mortar, "装水的臼");
        addItem(ItemRegister.wok, "铁锅");

        addItem(ItemRegister.pickled_cabbage, "泡菜");
        addItem(ItemRegister.pickled_cucumber, "腌黄瓜");
        addItem(ItemRegister.pickled_pepper, "泡椒");
        addItem(ItemRegister.pickled_turnip, "腌萝卜");

        addItem(ItemRegister.manual, "烹饪笔记");


        addBlock(BlockEntityRegister.chopping_board, "砧板");
        addBlock(BlockEntityRegister.mortar, "臼");
        addBlock(BlockEntityRegister.jar, "罐子");
        addBlock(BlockEntityRegister.drinkro, "Drinkro™");
        addBlock(BlockEntityRegister.plate, "盘子");

        addFluid(FluidRegister.soy_sauce, "酱油");
        addFluid(FluidRegister.sesame_oil, "香油");
        addFluid(FluidRegister.edible_oil, "食用油");
        addFluid(FluidRegister.rice_vinegar, "米醋");
        addFluid(FluidRegister.fruit_vinegar, "果醋");
        addFluid(FluidRegister.soy_milk, "豆浆");

    }


}
