package xueluoanping.cuisine.config;


import net.neoforged.neoforge.common.ModConfigSpec;

public class General {
    public static ModConfigSpec COMMON_CONFIG;
    public static ModConfigSpec.BooleanValue bool;
    public static ModConfigSpec.IntValue volume;

    public static ModConfigSpec.BooleanValue useVanillaBreadRecipe;
    static {
        ModConfigSpec.Builder COMMON_BUILDER = new ModConfigSpec.Builder();
        COMMON_BUILDER.comment("Debug settings").push("debugMode");
        bool = COMMON_BUILDER.comment("Set false to stop output log.").define("debugMode",false);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("General settings").push("general");
        volume=COMMON_BUILDER.comment("Set it to change volume.").defineInRange("volume",32000,3200,96000);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Hard Core").push("Hard Core");
        useVanillaBreadRecipe=COMMON_BUILDER.comment("Don't wheat to bread.").define("useVanillaBreadRecipe",false);
        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}
