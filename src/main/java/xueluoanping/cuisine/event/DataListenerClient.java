package xueluoanping.cuisine.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xueluoanping.cuisine.craft.BasinSqueezingRecipe;
import xueluoanping.cuisine.register.FluidRegister;
import xueluoanping.cuisine.register.RecipeRegister;
import xueluoanping.cuisine.util.SimpleMathUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class DataListenerClient {
	private static final Map<Item, Color> FluidColorsMap =new HashMap<>();

	@SubscribeEvent
	public static void checkRecipes(RecipesUpdatedEvent event)
	{

		List<BasinSqueezingRecipe> recipeList = event.getRecipeManager().getAllRecipesFor(RecipeRegister.squeezingType.get());
		recipeList.forEach((recipe)->{
			if(recipe.getResult().get(0).getFluid()== FluidRegister.juice.get())
			{
				Item item =recipe.getIngredients().get(0).getItems()[0].getItem();
				Minecraft mc = Minecraft.getInstance();
				Color c = Color.WHITE;
				try {

					try {
						BlockState state = ((BlockItem) item).getBlock().defaultBlockState();
						TextureAtlasSprite texture = mc.getBlockRenderer().getBlockModel(state).getQuads(state, Direction.NORTH, mc.level.random).get(0).getSprite();
						ArrayList<Integer> rlist = new ArrayList<Integer>();
						ArrayList<Integer> glist = new ArrayList<Integer>();
						ArrayList<Integer> blist = new ArrayList<Integer>();
						for (int i = 0; i < texture.getWidth(); i++) {
							for (int j = 0; j < texture.getHeight(); j++) {
								int color = texture.getPixelRGBA(0, i, j);
								int r = color & 0xff;
								int g = (color >> 8) & 0xff;
								int b = (color >> 16) & 0xff;
//                                int a = color >>> 24;
								if(r*g*b>0)
								{
									rlist.add(r);
									glist.add(g);
									blist.add(b);
								}
							}
						}
						c = new Color((int) SimpleMathUtil.getAvg(rlist),(int)SimpleMathUtil.getAvg(glist),(int)SimpleMathUtil.getAvg(blist));
						FluidColorsMap.put(item,c);

					} catch (Exception ex) {
						TextureAtlasSprite texture = mc.getItemRenderer().getModel(new ItemStack(item), mc.level, mc.player, 0).getParticleIcon();
						ArrayList<Integer> rlist = new ArrayList<Integer>();
						ArrayList<Integer> glist = new ArrayList<Integer>();
						ArrayList<Integer> blist = new ArrayList<Integer>();
						for (int i = 0; i < texture.getWidth(); i++) {
							for (int j = 0; j < texture.getHeight(); j++) {
								int color = texture.getPixelRGBA(0, i, j);
								int r = color & 0xff;
								int g = (color >> 8) & 0xff;
								int b = (color >> 16) & 0xff;

								if(r*g*b>0)
								{
									rlist.add(r);
									glist.add(g);
									blist.add(b);
								}
							}
						}
						c = new Color((int)SimpleMathUtil.getAvg(rlist),(int)SimpleMathUtil.getAvg(glist),(int)SimpleMathUtil.getAvg(blist));
						FluidColorsMap.put(item,c);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static Map<Item, Color> getFluidColorsMap(){return FluidColorsMap;}
}
