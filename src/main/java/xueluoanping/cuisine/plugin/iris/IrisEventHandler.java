package xueluoanping.cuisine.plugin.iris;


import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import xueluoanping.cuisine.plugin.CompatManager;

public class IrisEventHandler {
    public static IrisEventHandler INSTANCE = new IrisEventHandler();


    @SubscribeEvent
    public void onTick(ClientTickEvent.Pre levelTickEvent) {
        if (CompatManager.irisCompat.getAsBoolean()) {
            TSIrisPlugin.checkReload();
        }
    }


}
