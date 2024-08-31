package xueluoanping.cuisine.craft;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.config.General;

import java.lang.reflect.Field;

public class ConfigCondition implements ICondition {
    public static MapCodec<ConfigCondition> CODEC = RecordCodecBuilder.mapCodec(
            builder -> builder
                    .group(
                            Codec.STRING.fieldOf("config").forGetter(c->c.config))
                    .apply(builder, ConfigCondition::new));

    private static final ResourceLocation NAME = Cuisine.rl("config");

    String config = "";

    public ConfigCondition(String x) {
        config = x;
    }



    @Override
    public boolean test(IContext context) {
        return test();
    }

    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }

    public boolean test() {
        try {
            Field field = General.class.getDeclaredField(config);
            field.setAccessible(true);
            return ((ModConfigSpec.BooleanValue) field.get(ModConfigSpec.BooleanValue.class)).get();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public String toString() {
        return NAME.toString() + ":" + config + ":" + test();
    }

}
