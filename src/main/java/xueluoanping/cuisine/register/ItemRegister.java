package xueluoanping.cuisine.register;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.items.ItemBambooCharcoal;
import xueluoanping.cuisine.items.ItemKitchenKnife;
import xueluoanping.cuisine.items.ModItem;

public class ItemRegister {

    public static final FoodProperties DOUGH = (new FoodProperties.Builder())
            .nutrition(1).saturationMod(0.2f).build();


    public static final DeferredRegister<Item> DRItems = DeferredRegister.create(ForgeRegistries.ITEMS, Cuisine.MODID);

    // 机器合成材料，无特殊用途
    public static final RegistryObject<Item> wooden_handle = DRItems.register("wooden_handle", () -> new ModItem(RegisterHelper.basicItem()));
    public static final RegistryObject<Item> wooden_arm = DRItems.register("wooden_arm", () -> new ModItem(RegisterHelper.basicItem().stacksTo(1)));

    // 燃料
    public static final RegistryObject<Item> bamboo_charcoal = DRItems.register("bamboo_charcoal", () -> new ItemBambooCharcoal(RegisterHelper.basicItem()));


    // 磨粉中间产物，无特殊用途
    public static final RegistryObject<Item> flour = DRItems.register("flour", () -> new ModItem(RegisterHelper.basicItem()));
    public static final RegistryObject<Item> rice_powder = DRItems.register("rice_powder", () -> new ModItem(RegisterHelper.basicItem()));

    // 可以吃的简单中间产物
    public static final RegistryObject<Item> dough = DRItems.register("dough", () -> new ModItem(RegisterHelper.foodItem(DOUGH).tab(Cuisine.CREATIVE_TAB)));


    // 调味品
	public static final RegistryObject<Item> chili_powder = DRItems.register("chili_powder", () -> new Item(RegisterHelper.basicItem()));
	public static final RegistryObject<Item> crude_salt = DRItems.register("crude_salt", () -> new Item(RegisterHelper.basicItem()));
	public static final RegistryObject<Item> salt = DRItems.register("salt", () -> new Item(RegisterHelper.basicItem()));
	public static final RegistryObject<Item> unrefined_sugar = DRItems.register("unrefined_sugar", () -> new Item(RegisterHelper.basicItem()));
	public static final RegistryObject<Item> sichuan_pepper_powder = DRItems.register("sichuan_pepper_powder", () -> new Item(RegisterHelper.basicItem()));


    // 	厨具
    public static final RegistryObject<Item> kitchen_knife = DRItems.register("kitchen_knife", () -> new ItemKitchenKnife(RegisterHelper.basicItem().stacksTo(1)));
    public static final RegistryObject<Item> iron_spatula = DRItems.register("iron_spatula", () -> new ModItem(RegisterHelper.basicItem().stacksTo(1)));
	public static final RegistryObject<Item> wok = DRItems.register("wok", () -> new ModItem(RegisterHelper.basicItem().stacksTo(1)));
	public static final RegistryObject<Item> fan = DRItems.register("fan", () -> new ModItem(RegisterHelper.basicItem().stacksTo(1)));
	public static final RegistryObject<Item> mortar_water = DRItems.register("mortar_water", () -> new ModItem(RegisterHelper.basicItem().stacksTo(1)));
	public static final RegistryObject<Item> spice_bottle = DRItems.register("spice_bottle", () -> new ModItem(RegisterHelper.basicItem().stacksTo(1)));


// 	基础食材

	public static final RegistryObject<Item> tofu = DRItems.register("tofu", () -> new Item(RegisterHelper.basicItem()));
	public static final RegistryObject<Item> pickled_cabbage = DRItems.register("pickled_cabbage", () -> new Item(RegisterHelper.basicItem()));
	public static final RegistryObject<Item> pickled_cucumber = DRItems.register("pickled_cucumber", () -> new Item(RegisterHelper.basicItem()));
	public static final RegistryObject<Item> pickled_pepper = DRItems.register("pickled_pepper", () -> new Item(RegisterHelper.basicItem()));
	public static final RegistryObject<Item> pickled_turnip = DRItems.register("pickled_turnip", () -> new Item(RegisterHelper.basicItem()));


}
