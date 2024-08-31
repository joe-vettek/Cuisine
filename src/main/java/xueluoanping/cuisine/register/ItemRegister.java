package xueluoanping.cuisine.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.items.ItemBambooCharcoal;
import xueluoanping.cuisine.items.ItemKitchenKnife;
import xueluoanping.cuisine.items.ModItem;
import xueluoanping.cuisine.items.ingredients.ItemCubed;

public class ItemRegister {

    public static final FoodProperties DOUGH = (new FoodProperties.Builder())
            .nutrition(1).saturationModifier(0.2f).build();


    public static final DeferredRegister<Item> DRItems = DeferredRegister.create(Registries.ITEM, Cuisine.MODID);

    // 机器合成材料，无特殊用途
    public static final DeferredHolder<Item, ModItem> wooden_handle = DRItems.register("wooden_handle", () -> new ModItem(RegisterHelper.basicItem()));
    public static final DeferredHolder<Item, Item> wooden_arm = DRItems.register("wooden_arm", () -> new ModItem(RegisterHelper.basicItem().stacksTo(1)));

    // 燃料
    public static final DeferredHolder<Item, Item> bamboo_charcoal = DRItems.register("bamboo_charcoal", () -> new ItemBambooCharcoal(RegisterHelper.basicItem()));


    // 磨粉中间产物，无特殊用途
    public static final DeferredHolder<Item, Item> flour = DRItems.register("flour", () -> new ModItem(RegisterHelper.basicItem()));
    public static final DeferredHolder<Item, Item> rice_powder = DRItems.register("rice_powder", () -> new ModItem(RegisterHelper.basicItem()));

    // 可以吃的简单中间产物
    public static final DeferredHolder<Item, Item> dough = DRItems.register("dough", () -> new ModItem(RegisterHelper.foodItem(DOUGH)));


    // 调味品
    public static final DeferredHolder<Item, Item> chili_powder = DRItems.register("chili_powder", () -> new Item(RegisterHelper.basicItem()));
    public static final DeferredHolder<Item, Item> crude_salt = DRItems.register("crude_salt", () -> new Item(RegisterHelper.basicItem()));
    public static final DeferredHolder<Item, Item> salt = DRItems.register("salt", () -> new Item(RegisterHelper.basicItem()));
    public static final DeferredHolder<Item, Item> unrefined_sugar = DRItems.register("unrefined_sugar", () -> new Item(RegisterHelper.basicItem()));
    public static final DeferredHolder<Item, Item> sichuan_pepper_powder = DRItems.register("sichuan_pepper_powder", () -> new Item(RegisterHelper.basicItem()));


    // 	厨具
    public static final DeferredHolder<Item, Item> kitchen_knife = DRItems.register("kitchen_knife", () -> new ItemKitchenKnife(RegisterHelper.basicItem().stacksTo(1)));
    public static final DeferredHolder<Item, Item> iron_spatula = DRItems.register("iron_spatula", () -> new ModItem(RegisterHelper.basicItem().stacksTo(1)));
    public static final DeferredHolder<Item, Item> wok = DRItems.register("wok", () -> new ModItem(RegisterHelper.basicItem().stacksTo(1)));
    public static final DeferredHolder<Item, Item> fan = DRItems.register("fan", () -> new ModItem(RegisterHelper.basicItem().stacksTo(1)));
    public static final DeferredHolder<Item, Item> mortar_water = DRItems.register("mortar_water", () -> new ModItem(RegisterHelper.basicItem().stacksTo(1)));
    public static final DeferredHolder<Item, Item> spice_bottle = DRItems.register("spice_bottle", () -> new ModItem(RegisterHelper.basicItem().stacksTo(1)));


// 	基础食材

    public static final DeferredHolder<Item, Item> tofu = DRItems.register("tofu", () -> new Item(RegisterHelper.basicItem()));
    public static final DeferredHolder<Item, Item> pickled_cabbage = DRItems.register("pickled_cabbage", () -> new Item(RegisterHelper.basicItem()));
    public static final DeferredHolder<Item, Item> pickled_cucumber = DRItems.register("pickled_cucumber", () -> new Item(RegisterHelper.basicItem()));
    public static final DeferredHolder<Item, Item> pickled_pepper = DRItems.register("pickled_pepper", () -> new Item(RegisterHelper.basicItem()));
    public static final DeferredHolder<Item, Item> pickled_turnip = DRItems.register("pickled_turnip", () -> new Item(RegisterHelper.basicItem()));

    //     ..
    public static final DeferredHolder<Item, Item> cubed = DRItems.register("cubed", () -> new ItemCubed(RegisterHelper.basicItem()));


}
