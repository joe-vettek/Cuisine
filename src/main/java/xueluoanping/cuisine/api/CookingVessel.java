package xueluoanping.cuisine.api;



import net.minecraft.world.item.ItemStack;

import java.util.Optional;

/**
 * A {@code CookingVessel} is something that has ability to hold a
 * {@ link CompositeFood.Builder}.
 */
public interface CookingVessel
{
    Optional<ItemStack> serve();
}
