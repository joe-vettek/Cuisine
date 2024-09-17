package xueluoanping.cuisine.data.lang;

import net.minecraft.client.resources.language.I18n;
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

public class Lang_EN extends LangHelper {
    public Lang_EN(PackOutput gen, ExistingFileHelper helper) {
        super(gen, helper, Cuisine.MODID, "en_us");
        Cuisine.logger("hello");
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
        add("Fluid_type." + standard, name);
        add("block." + standard, name);
        add("item." + standard + "_bucket", name + " Bucket");
    }

    @Override
    protected void addTranslations() {
        add(Cuisine.MODID, "Cuisine: the memory of food");
        addItemGroup(ModContent.CREATIVE_TAB, "Cuisine");

        addBlock(BlockRegister.bamboo_root, "Bamboo Root");
        addBlock(BlockRegister.bamboo_plant, "Henon bamboo");
        addBlock(BlockRegister.bamboo, "Henon Bamboo Shoot");
        addBlock(BlockEntityRegister.fire_pit, "Fire Pit");
        addBlock(BlockEntityRegister.barbecue_rack, "Barbecue Rack");
        addBlock(BlockEntityRegister.wok_on_fire_pit, "Wok on a Fire Pit");
        addBlock(BlockEntityRegister.mill, "Mill");
        addBlock(() -> BlockEntityRegister.basin.get(), "Basin");
        BlockEntityRegister.basinColored.forEach((dyeColor, blockRegistryObject) -> {
            String text = dyeColor == null ? "Earthen Basin" :
                    I18n.get(BlockEntityRegister.colorBlockMap.get(dyeColor).getDescriptionId()).replace(" Terracotta", "") + "-Colored Earthen Basin";
            addBlock(blockRegistryObject, text);
        });
        addHint(() -> BlockEntityRegister.basin.get(), "§4used for：\n§3All pots can be used to crush fruits and vegetables or soak things. Non wooden pots can be heated in a fire pit to obtain coarse salt");
        addFluid(FluidRegister.CUISINE_JUICE, "Strange Juice");
        add("Fluid." + Cuisine.MODID + ".cuisine_juice_with_material", "Juice");


        addItem(ItemRegister.flour, "Flour");
        addItem(ItemRegister.rice_powder, "Rice Powder");
        addItem(ItemRegister.wooden_handle, "Wooden Handle");
        addItem(ItemRegister.wooden_arm, "Wooden Arm");
        addItem(ItemRegister.bamboo_charcoal, "Bamboo Charcoal");
        addItem(ItemRegister.dough, "Dough");

        addItem(ItemRegister.kitchen_knife, "Kitchen Knife");
        addItem(ItemRegister.iron_spatula, "Iron Spatula");

        addBlock(CropRegister.peanut, "Peanut");
        addBlock(CropRegister.sesame, "Sesame");
        addBlock(CropRegister.soybean, "Soybean");
        addBlock(CropRegister.rice, "Rice");
        addBlock(CropRegister.tomato, "Tomato");
        addBlock(CropRegister.chili, "Chili");
        addBlock(CropRegister.garlic, "Garlic");
        addBlock(CropRegister.ginger, "Ginger");
        addBlock(CropRegister.sichuan_pepper, "Sichuan Pepper");
        addBlock(CropRegister.scallion, "Scallion");
        addBlock(CropRegister.turnip, "Turnip");
        addBlock(CropRegister.chinese_cabbage, "Chinese Cabbage");
        addBlock(CropRegister.corn, "Corn");
        addBlock(CropRegister.cucumber, "Cucumber");
        addBlock(CropRegister.green_pepper, "Green Pepper");
        addBlock(CropRegister.red_pepper, "Red Pepper");
        addBlock(CropRegister.leek, "Leek");
        addBlock(CropRegister.lettuce, "Lettuce");
        addBlock(CropRegister.onion, "Onion");
        addBlock(CropRegister.eggplant, "Eggplant");
        addBlock(CropRegister.spinach, "Spinach");

        addItem(ItemRegister.cubed, "Cube");

        addDebugKey(ModConstant.DebugKey.try_place_bamboo, "Try Place Bamboo ");

        addItem(ItemRegister.sliced, "Sliced");
        addItem(ItemRegister.shredded, "Shredded");
        addItem(ItemRegister.diced, "Diced");
        addItem(ItemRegister.minced, "Minced");
        addItem(ItemRegister.paste, "Paste");

        addItem(ItemRegister.tofu, "Tofu");
        addBlock(BlockRegister.tofu_block, "Tofu Block");

        addItem(ItemRegister.chili_powder, "Chili Powder");
        addItem(ItemRegister.crude_salt, "Crude Salt");
        addItem(ItemRegister.salt, "Salt");
        addItem(ItemRegister.unrefined_sugar, "Unrefined Sugar");
        addItem(ItemRegister.sichuan_pepper_powder, "Sichuan Pepper Powder");

        addItem(ItemRegister.spice_bottle, "Spice_bottle");
        addItem(ItemRegister.fan, "Fan");
        addItem(ItemRegister.water_mortar, "Water Mortar");
        addItem(ItemRegister.wok, "Wok");

        addItem(ItemRegister.pickled_cabbage, "Pickled Cabbage");
        addItem(ItemRegister.pickled_cucumber, "Pickled Cucumber");
        addItem(ItemRegister.pickled_pepper, "Pickled Pepper");
        addItem(ItemRegister.pickled_turnip, "Pickled Turnip");

        addItem(ItemRegister.manual, "Cook Manual");


        addBlock(BlockEntityRegister.chopping_board, "Chopping Board");
        addBlock(BlockEntityRegister.mortar, "Mortar");
        addBlock(BlockEntityRegister.jar, "Jar");
        addBlock(BlockEntityRegister.drinkro, "Drinkro™");
        addBlock(BlockEntityRegister.plate, "Plate");

        addFluid(FluidRegister.soy_sauce, "Soy Sauce");
        addFluid(FluidRegister.sesame_oil, "Sesame Oil");
        addFluid(FluidRegister.edible_oil, "Edible Oil");
        addFluid(FluidRegister.rice_vinegar, "Rice Vinegar");
        addFluid(FluidRegister.fruit_vinegar, "Fruit Vinegar");
        addFluid(FluidRegister.soy_milk, "Soy Milk");
    }
}
