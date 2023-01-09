package xueluoanping.cuisine.data.material;

import com.google.gson.Gson;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.crafting.Ingredient;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.api.Form;
import xueluoanping.cuisine.api.MaterialCategory;
import xueluoanping.cuisine.api.prefab.MaterialBuilder;
import xueluoanping.cuisine.internal.material.MaterialWithEffect;

public class SimpleMP extends MaterialProvider{
    public SimpleMP(DataGenerator gen) {
        super(gen, Cuisine.MODID);
        add(MaterialBuilder.of("example").build(),
                MaterialBuilder.of("peanut").rawColor(-8531).category(MaterialCategory.NUT).form(Form.MINCED, Form.PASTE).build(),
                MaterialBuilder.of("sesame").rawColor(-15000805).category(MaterialCategory.GRAIN).build(),
                MaterialBuilder.of("soybean").rawColor(-2048665).category(MaterialCategory.GRAIN).build(),
                MaterialBuilder.of("ginger").rawColor(-1828).category(MaterialCategory.VEGETABLES).build(),
                MaterialBuilder.of("sichuan_pepper").rawColor(-8511203).category(MaterialCategory.UNKNOWN).build(),
                MaterialBuilder.of("scallion").rawColor(-12609717).form(Form.SLICED, Form.SHREDDED, Form.MINCED, Form.PASTE).build(),
                MaterialBuilder.of("turnip").rawColor(-3557457).form(Form.ALL_FORMS_INCLUDING_JUICE).category(MaterialCategory.VEGETABLES).build(),
                MaterialBuilder.of("chinese_cabbage").rawColor(-1966111).form(Form.SLICED, Form.SHREDDED, Form.MINCED, Form.PASTE).category(MaterialCategory.VEGETABLES).build(),
                MaterialBuilder.of("lettuce").rawColor(-14433485).form(Form.SLICED, Form.SHREDDED, Form.MINCED, Form.PASTE, Form.JUICE).category(MaterialCategory.VEGETABLES).build(),
                MaterialBuilder.of("corn").rawColor(-3227867).saturation(2f).category(MaterialCategory.GRAIN).form(Form.MINCED, Form.JUICE).build(),
                MaterialBuilder.of("cucumber").rawColor(0xdddce7bd).category(MaterialCategory.VEGETABLES).form(Form.ALL_FORMS_INCLUDING_JUICE).build(),
                MaterialBuilder.of("green_pepper").rawColor(-15107820).category(MaterialCategory.VEGETABLES).form(Form.SLICED, Form.SHREDDED, Form.MINCED, Form.PASTE).build(),
                MaterialBuilder.of("red_pepper").rawColor(-8581357).category(MaterialCategory.VEGETABLES).form(Form.SLICED, Form.SHREDDED, Form.MINCED, Form.PASTE).build(),
                MaterialBuilder.of("leek").rawColor(-15100888).category(MaterialCategory.VEGETABLES).form(Form.CUBED, Form.MINCED, Form.PASTE).build(),
                MaterialBuilder.of("onion").rawColor(-17409).category(MaterialCategory.VEGETABLES).form(Form.ALL_FORMS_INCLUDING_JUICE).build(),
                MaterialBuilder.of("eggplant").rawColor(0xdcd295).category(MaterialCategory.VEGETABLES).form(Form.ALL_FORMS).build(),
                MaterialBuilder.of("melon").rawColor(-769226).category(MaterialCategory.FRUIT).form(Form.CUBED, Form.SLICED, Form.DICED, Form.MINCED, Form.PASTE, Form.JUICE).build(),
               MaterialBuilder.of("potato").rawColor(-3764682).heatValue(2).saturation(2f).category(MaterialCategory.GRAIN).form(Form.ALL_FORMS).build(),
                MaterialBuilder.of("beetroot").rawColor(-8442327).category(MaterialCategory.VEGETABLES).form(Form.ALL_FORMS_INCLUDING_JUICE).build(),
                MaterialBuilder.of("mushroom").rawColor(-10006976).category(MaterialCategory.VEGETABLES).form(Form.ALL_FORMS).build(),
                MaterialBuilder.of("egg").rawColor(-3491187).saturation(0.2f).category(MaterialCategory.PROTEIN).build(),
                MaterialBuilder.of("chicken").rawColor(-929599).category(MaterialCategory.MEAT).form(Form.ALL_FORMS).build(),
                MaterialBuilder.of("beef").rawColor(-3392460).category(MaterialCategory.MEAT).form(Form.ALL_FORMS).build(),
                MaterialBuilder.of("pork").rawColor(-2133904).category(MaterialCategory.MEAT).form(Form.ALL_FORMS).build(),
                MaterialBuilder.of("mutton").rawColor(-3917262).saturation(0f).category(MaterialCategory.MEAT).form(Form.ALL_FORMS).build(),
                MaterialBuilder.of("fish").rawColor(-10583426).category(MaterialCategory.FISH).form(Form.ALL_FORMS).build(),
                MaterialBuilder.of("water").rawColor(0x55DDDDFF).boilHeat(100).saturation(-0.1f).form(Form.JUICE).build(),
                MaterialBuilder.of("milk").rawColor(0xCCFFFFFF).boilHeat(100).saturation(-0.1f).category(MaterialCategory.PROTEIN).form(Form.JUICE).build(),
                MaterialBuilder.of("soy_milk").rawColor(-15831787).saturation(-0.1f).category(MaterialCategory.PROTEIN).form(Form.JUICE).build(),
                MaterialBuilder.of("mandarin").rawColor(0xf08a19).saturation(-0.1f).category(MaterialCategory.FRUIT).form(Form.JUICE).build(),
                MaterialBuilder.of("citron").rawColor(0xddcc58).saturation(-0.1f).category(MaterialCategory.FRUIT).form(Form.JUICE).build(),
                MaterialBuilder.of("pomelo").rawColor(0xf7f67e).saturation(-0.1f).category(MaterialCategory.FRUIT).form(Form.JUICE).category(MaterialCategory.FRUIT).form(Form.JUICE).build(),
                MaterialBuilder.of("orange").rawColor(0xf08a19).saturation(-0.1f).category(MaterialCategory.FRUIT).form(Form.JUICE).build(),
                MaterialBuilder.of("lemon").rawColor(0xebca4b).saturation(-0.1f).category(MaterialCategory.FRUIT).form(Form.JUICE).build(),
                MaterialBuilder.of("grapefruit").rawColor(0xf4502b).saturation(-0.1f).category(MaterialCategory.FRUIT).form(Form.JUICE).build(),
                MaterialBuilder.of("lime").rawColor(0xcada76).saturation(-0.1f).category(MaterialCategory.FRUIT).form(Form.JUICE).build()
                );
    }

    @Override
    public String getName() {
        return "in SimpleMaterialProvider: "+Cuisine.MODID;
    }
}
