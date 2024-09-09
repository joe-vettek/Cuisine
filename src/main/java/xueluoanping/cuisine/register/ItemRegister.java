package xueluoanping.cuisine.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.items.ItemBambooCharcoal;
import xueluoanping.cuisine.items.ItemKitchenKnife;
import xueluoanping.cuisine.items.ItemSpineBottle;
import xueluoanping.cuisine.items.ModItem;
import xueluoanping.cuisine.items.ingredients.ItemCubed;

import java.util.List;
import java.util.stream.Stream;

public class ItemRegister {

    public static final FoodProperties DOUGH = (new FoodProperties.Builder())
            .nutrition(1).saturationModifier(0.2f).build();


    public static final DeferredRegister<Item> DRItems = DeferredRegister.create(Registries.ITEM, Cuisine.MODID);

    // 机器合成材料，无特殊用途
    public static final DeferredHolder<Item, ModItem> wooden_handle = DRItems.register("wooden_handle", () -> new ModItem(new Item.Properties()));
    public static final DeferredHolder<Item, Item> wooden_arm = DRItems.register("wooden_arm", () -> new ModItem(new Item.Properties().stacksTo(1)));

    // 燃料
    public static final DeferredHolder<Item, Item> bamboo_charcoal = DRItems.register("bamboo_charcoal", () -> new ItemBambooCharcoal(new Item.Properties()));


    // 磨粉中间产物，无特殊用途
    public static final DeferredHolder<Item, Item> flour = DRItems.register("flour", () -> new ModItem(new Item.Properties()));
    public static final DeferredHolder<Item, Item> rice_powder = DRItems.register("rice_powder", () -> new ModItem(new Item.Properties()));

    // 可以吃的简单中间产物
    public static final DeferredHolder<Item, Item> dough = DRItems.register("dough", () -> new ModItem(RegisterHelper.foodItem(DOUGH)));


    // 调味品
    public static final DeferredHolder<Item, Item> chili_powder = DRItems.register("chili_powder", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> crude_salt = DRItems.register("crude_salt", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> salt = DRItems.register("salt", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> unrefined_sugar = DRItems.register("unrefined_sugar", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> sichuan_pepper_powder = DRItems.register("sichuan_pepper_powder", () -> new Item(new Item.Properties()));


    // 	厨具
    public static final DeferredHolder<Item, Item> kitchen_knife = DRItems.register("kitchen_knife", () -> new ItemKitchenKnife(new Item.Properties().stacksTo(1)));
    public static final DeferredHolder<Item, Item> iron_spatula = DRItems.register("iron_spatula", () -> new ModItem(new Item.Properties().stacksTo(1)));
    public static final DeferredHolder<Item, Item> wok = DRItems.register("wok", () -> new ModItem(new Item.Properties().stacksTo(1)));
    public static final DeferredHolder<Item, Item> fan = DRItems.register("fan", () -> new ModItem(new Item.Properties().stacksTo(1)));
    public static final DeferredHolder<Item, Item> water_mortar = DRItems.register("water_mortar", () -> new ModItem(new Item.Properties().stacksTo(1)));
    // public static final DeferredHolder<Item, Item> spice_bottle = DRItems.register("spice_bottle", () -> new ModItem(new Item.Properties().stacksTo(1)));


    public static DeferredHolder<Item, ItemSpineBottle> spice_bottle = DRItems.register("spice_bottle", () -> new ItemSpineBottle(new Item.Properties()));


// 	基础食材

    public static final DeferredHolder<Item, Item> tofu = DRItems.register("tofu", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> pickled_cabbage = DRItems.register("pickled_cabbage", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> pickled_cucumber = DRItems.register("pickled_cucumber", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> pickled_pepper = DRItems.register("pickled_pepper", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> pickled_turnip = DRItems.register("pickled_turnip", () -> new Item(new Item.Properties()));

    //     ..
    public static final DeferredHolder<Item, Item> cubed = DRItems.register("cubed", () -> new ItemCubed(new Item.Properties()));
    public static final DeferredHolder<Item, Item> sliced = DRItems.register("sliced", () -> new ItemCubed(new Item.Properties()));
    public static final DeferredHolder<Item, Item> shredded = DRItems.register("shredded", () -> new ItemCubed(new Item.Properties()));
    public static final DeferredHolder<Item, Item> diced = DRItems.register("diced", () -> new ItemCubed(new Item.Properties()));
    public static final DeferredHolder<Item, Item> minced = DRItems.register("minced", () -> new ItemCubed(new Item.Properties()));
    public static final DeferredHolder<Item, Item> paste = DRItems.register("paste", () -> new ItemCubed(new Item.Properties()));

    public static final DeferredHolder<Item, Item> manual = DRItems.register("manual", () -> new Item(new Item.Properties()));

    public static List<Item> getIngredients() {
        return Stream.of(ItemRegister.cubed,
                        ItemRegister.sliced,
                        ItemRegister.shredded,
                        ItemRegister.diced,
                        ItemRegister.minced,
                        ItemRegister.paste)
                .map(DeferredHolder::value).toList();
    }

}
