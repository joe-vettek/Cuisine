package xueluoanping.cuisine.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xueluoanping.cuisine.Cuisine;

import java.util.Map;
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class network extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().setLenient()
            .registerTypeHierarchyAdapter(Component.class, new Component.Serializer())
            .create();

    public static final network instance = new network(GSON, "colors_map");

    public network(Gson json, String s) {
        super(json, s);
        Cuisine.logger("data init");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objects, ResourceManager manager, ProfilerFiller profiler) {
        Cuisine.logger("Hello Profile");
        objects.forEach((res,json)->{
            Cuisine.logger(objects.toString(),res);
        });
    }
    @SubscribeEvent
    public static void dataLoading(AddReloadListenerEvent event) {
        event.addListener(network.instance);
    }

}
