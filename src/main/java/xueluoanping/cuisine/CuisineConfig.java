package xueluoanping.cuisine;


import com.electronwill.nightconfig.core.Config;

import net.minecraftforge.fml.common.Mod;

import net.minecraftforge.fml.config.ModConfig;
import snownee.kiwi.config.KiwiConfig;

import java.util.Arrays;
import java.util.List;

@KiwiConfig(type = KiwiConfig.ConfigType.COMMON)
@Mod.EventBusSubscriber(modid = Cuisine.MODID)
public final class CuisineConfig
{

    private CuisineConfig()
    {
        throw new UnsupportedOperationException("No instance for you");
    }

	public static void onChanged(String path) {
		// do sth
	}

	public static int intValue = 5;
	@KiwiConfig.Range(min = 0, max = 114514)
	public static long longValue = 6;

	@KiwiConfig.Range(min = 0, max = 10.5)
	public static float floatValue = 7.5f;

	@KiwiConfig.Path("Core.fruit_growing_speed")
	@KiwiConfig.Comment("cuisine.config.general.fruit_growing_speed")
	public static String strValue = "fruit_growing_speed";

	public static boolean booleanValue = true;

	public static List<String> listValue = Arrays.asList("apple");
}
