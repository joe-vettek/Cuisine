package xueluoanping.cuisine.register;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import xueluoanping.cuisine.Cuisine;

public class RegisterHelper {
    // Helper methods
    public static Item.Properties basicItem() {
        return new Item.Properties();
    }

	public static Item.Properties basicBlockItem() {
		return new Item.Properties();
	}

    public static Item.Properties foodItem(FoodProperties food) {
        return new Item.Properties().food(food);
    }
}
