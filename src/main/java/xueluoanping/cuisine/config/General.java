package xueluoanping.cuisine.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class General {
    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec.BooleanValue bool;
    public static ForgeConfigSpec.IntValue volume;

    public static ForgeConfigSpec.BooleanValue useVanillaBreadRecipe;
    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
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
