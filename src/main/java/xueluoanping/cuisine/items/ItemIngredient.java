package xueluoanping.cuisine.items;

import net.minecraft.world.item.ItemStack;
import xueluoanping.cuisine.api.CookingVessel;

import java.util.Optional;

public class ItemIngredient extends ModItem implements  CookingVessel {

    public ItemIngredient(Properties properties) {
        super(properties);
    }

    @Override
    public Optional<ItemStack> serve() {
        return Optional.empty();
    }


}
