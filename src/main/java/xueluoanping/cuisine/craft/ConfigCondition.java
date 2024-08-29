package xueluoanping.cuisine.craft;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.conditions.ICondition;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.config.General;

import java.lang.reflect.Field;

public class ConfigCondition implements ICondition {
    private static final ResourceLocation NAME = Cuisine.rl("config");

    String config = "";

    public ConfigCondition(String x) {
        config = x;
    }

    @Override
    public ResourceLocation getID() {
        return NAME;
    }

    @Override
    public boolean test(IContext context) {
        return test();
    }

    @Override
    @SuppressWarnings("removal")
    public boolean test() {
        try {
            Field field = General.class.getDeclaredField(config);
            field.setAccessible(true);
            return ((ForgeConfigSpec.BooleanValue) field.get(ForgeConfigSpec.BooleanValue.class)).get();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public String toString() {
        return NAME.toString() + ":" + config + ":" + test();
    }

    public static class Serializer implements IConditionSerializer<ConfigCondition> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public void write(JsonObject json, ConfigCondition value) {
            json.addProperty("keyName", value.config);
        }

        @Override
        public ConfigCondition read(JsonObject json) {
            Cuisine.logger(json);
            try {
                return new ConfigCondition(json.get("keyName").getAsString());
            } catch (Exception e) {
                throw new RuntimeException();
            }

        }

        @Override
        public ResourceLocation getID() {
            return ConfigCondition.NAME;
        }
    }
}
