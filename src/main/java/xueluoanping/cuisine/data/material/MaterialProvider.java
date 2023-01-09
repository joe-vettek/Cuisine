package xueluoanping.cuisine.data.material;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import xueluoanping.cuisine.api.prefab.MaterialBuilder;
import xueluoanping.cuisine.api.prefab.SimpleMaterialImpl;
import xueluoanping.cuisine.config.General;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public abstract class MaterialProvider implements DataProvider {
    private final Map<String, JsonElement> data = new TreeMap<>();
    private final DataGenerator gen;
    private final String modid;

    public MaterialProvider(DataGenerator gen, String modid) {
        this.gen = gen;
        this.modid = modid;
    }

    @Override
    public void run(HashCache cache) throws IOException {

        if (!data.isEmpty()) {
            data.forEach((key, value) -> {

                String outPath = "data/" + modid + "/material/" + key + ".json";
                try {
                    DataProvider.save(new GsonBuilder()
                                    .setPrettyPrinting()            // 格式化输出json字符串
                                    .create(), cache, value,
                            this.gen.getOutputFolder().resolve(outPath));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
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


