package xueluoanping.cuisine.register;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import xueluoanping.cuisine.util.Platform;

public class ModConstant {
    public static class DebugKey{
        public static final String try_place_bamboo="try_place_bamboo";

        public static String getRealKey(String key){
            return "debug.info."+key;
        }

        public static String getWord(String keyName){
            return I18n.get(getRealKey(keyName));
        }
    }

}
