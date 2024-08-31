package xueluoanping.cuisine.event;


import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import xueluoanping.cuisine.network.network;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME)
public class DataListening {


	@SubscribeEvent
    public static void dataLoading(AddReloadListenerEvent event) {
        event.addListener(network.instance);
        event.addListener(network.instance2);
    }

}
