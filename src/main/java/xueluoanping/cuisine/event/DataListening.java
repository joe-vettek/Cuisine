package xueluoanping.cuisine.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xueluoanping.cuisine.network.network;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DataListening {

    @SubscribeEvent
    public  void dataLoading(AddReloadListenerEvent event) {
        event.addListener(network.instance);
        event.addListener(network.instance2);
    }

}
