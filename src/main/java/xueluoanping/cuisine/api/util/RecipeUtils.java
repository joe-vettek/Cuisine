package xueluoanping.cuisine.api.util;

import net.minecraft.core.Registry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import snownee.kiwi.recipe.FullBlockIngredient;

import java.util.List;
import java.util.stream.Collectors;

public class RecipeUtils {
	public static List<Block> getSuitableCovers()
	{
		return Registry.BLOCK.stream().filter(i -> FullBlockIngredient.isFullBlock(i.asItem().getDefaultInstance())).collect(Collectors.toList());

	}

}
