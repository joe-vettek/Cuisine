package xueluoanping.cuisine.register;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.items.ModItem;
import xueluoanping.cuisine.items.ingredients.ItemCubed;

public class IngredientRegister {
    public static final DeferredRegister<Item> DRItems = DeferredRegister.create(ForgeRegistries.ITEMS, Cuisine.MODID);
    public static final RegistryObject<Item> cubed = DRItems.register("cubed", () -> new ItemCubed(RegisterHelper.basicItem().tab(Cuisine.CREATIVE_TAB)));



}
