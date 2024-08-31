package xueluoanping.cuisine.data.material;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import xueluoanping.cuisine.api.prefab.MaterialBuilder;
import xueluoanping.cuisine.api.prefab.SimpleMaterialImpl;
import xueluoanping.cuisine.config.General;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;

public abstract class MaterialProvider implements DataProvider {
    private final Map<String, JsonElement> data = new TreeMap<>();
    private final DataGenerator gen;
    private final String modid;

    public MaterialProvider(DataGenerator gen, String modid) {
        this.gen = gen;
        this.modid = modid;
    }


    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        ArrayList<CompletableFuture<?>> cc = new ArrayList<>();
        if (!data.isEmpty()) {
            data.forEach((key, value) -> {

                String outPath = "data/" + modid + "/material/" + key + ".json";
                var c = DataProvider.saveStable(cache, value,
                        this.gen.getPackOutput().getOutputFolder().resolve(outPath));
                cc.add(c);
            });
        }
        return CompletableFuture.allOf(cc.toArray(new CompletableFuture<?>[0]));
    }

    public void add(SimpleMaterialImpl material) {
        this.data.put(material.getID(), new Gson().toJsonTree(material));
    }

    public void add(SimpleMaterialImpl... material) {
        for (SimpleMaterialImpl i : material) {
            add(i);
        }
    }

}


