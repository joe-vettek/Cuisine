package xueluoanping.cuisine.plugin;


import net.irisshaders.iris.Iris;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.NeoForge;
import xueluoanping.cuisine.plugin.iris.IrisEventHandler;

public class CompatManager {

    private static boolean iris = false;
    private static boolean eclipticseasons = false;

    public static void init(IEventBus loadEventBus) {
        IEventBus gameEventBus = NeoForge.EVENT_BUS;
        if (Platform.isPhysicalClient()) {
            iris = Platform.isModLoaded(Iris.MODID);
            if (iris) {
                gameEventBus.register(IrisEventHandler.INSTANCE);
            }
        }

    }

    public static ModConfigSpec.BooleanValue enable;
    public static ModConfigSpec.BooleanValue irisCompat;


    public static void initConfig(ModConfigSpec.Builder builder,boolean isServer){
        if(isServer){
        }else {
            builder.push("Compat");
            irisCompat = builder.comment("Automatically compatible with shader pack foliage swaying effects.")
                    .define("Iris", true);
            builder.pop();
        }
    }
}
