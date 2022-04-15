package xueluoanping.cuisine.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xueluoanping.cuisine.register.BiomeDecorateRegister;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WildGeneratorHandler {
    @SubscribeEvent
    public static void onBiomeLoad(BiomeLoadingEvent event) {
        setVegetalFeature(event, BiomeDecorateRegister.PATCH_BAMBOOSHOOT.get(), true,
                0.4F, 1.0F);
        setVegetalFeature(event, BiomeDecorateRegister.f3.get(), true,
                0.4F, 1.0F);
    }

    public static void setVegetalFeature(BiomeLoadingEvent event, PlacedFeature feature, boolean canGen, float low,
                                         float high) {
        BiomeGenerationSettingsBuilder builder = event.getGeneration();
        Biome.ClimateSettings climate = event.getClimate();

        if (event.getCategory()== Biome.BiomeCategory.FOREST) {
            if (canGen) {
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(feature));
            }
        }
    }
}
