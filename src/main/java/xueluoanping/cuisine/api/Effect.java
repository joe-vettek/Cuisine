package xueluoanping.cuisine.api;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Comparator;
import java.util.List;

public interface Effect
{

    Comparator<Effect> PRIORITY_COMPARATOR = Comparator.comparingInt(Effect::getPriority);

    Comparator<Effect> INVERSE_PRIORITY_COMPARATOR = PRIORITY_COMPARATOR.reversed();

    // void onEaten(ItemStack stack, Player player, CompositeFood compositeFood, List<Ingredient> ingredients, EffectCollector collector);

    int getPriority();

    String getID();

    String getName();

    int getColor();

    String getDescription();

    default boolean showInTooltips()
    {
        return true;
    }
}
