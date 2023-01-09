package xueluoanping.cuisine.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.register.CropRegister;
import xueluoanping.cuisine.register.FeatureRegister;

import java.util.List;

// @Mod.EventBusSubscriber(modid = Cuisine.MODID)
// 订阅FML和forge事件不要用上面的
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModContents {

    @SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent event) {
        // if (!Configuration.FARMERS_BUY_FD_CROPS.get()) return;
        //
        Cuisine.logger("onVillagerTrades");

        // 大蒜的获取方式是与村民交易，辣椒也是
        Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
        VillagerProfession profession = event.getType();
        if (profession.getRegistryName() == null)
            return;
        if (profession.getRegistryName().getPath().equals("farmer")) {
            CropRegister.getAllCrops().forEach(itemRegistryObject -> {
                // 前三种是打草获得，辣椒chill是灵魂沙生成，其余是普通耕地生成
                if (itemRegistryObject == CropRegister.chili_item)
                    trades.get(5).add(itemForEmeraldTrade(itemRegistryObject.get(), 4, 4, 20));
                else if (itemRegistryObject == CropRegister.garlic_item)
                    trades.get(4).add(itemForEmeraldTrade(itemRegistryObject.get(), 2, 5, 12));
                else {
                    int level = CropRegister.getGrassCrops().contains(itemRegistryObject) ? 1 : 2;
                    trades.get(level).add(emeraldForItemsTrade(itemRegistryObject.get(), 30 - 4 * level, 16, 2 * level));
                }
            });
        }
    }

    @SubscribeEvent
    public static void onWandererTrades(WandererTradesEvent event) {
        // if (Configuration.WANDERING_TRADER_SELLS_FD_ITEMS.get())
        {
            List<VillagerTrades.ItemListing> trades = event.getGenericTrades();
            CropRegister.getAllCrops().forEach(itemRegistryObject -> {
                if (itemRegistryObject == CropRegister.chili_item)
                    trades.add(itemForEmeraldTrade(itemRegistryObject.get(), 8, 1, 20));
                else if (itemRegistryObject == CropRegister.garlic_item)
                    trades.add(itemForEmeraldTrade(itemRegistryObject.get(), 6, 2, 16));
                else {
                trades.add(itemForEmeraldTrade(itemRegistryObject.get(), 4, 3, 12));}
            });
        }
    }


    // 也可以模仿来加高级物品
    public static BasicItemListing emeraldForItemsTrade(ItemLike item, int count, int maxTrades, int xp) {
        return new BasicItemListing(new ItemStack(item, count), new ItemStack(Items.EMERALD), maxTrades, xp, 0.05F);
    }


    public static BasicItemListing itemForEmeraldTrade(ItemLike item, int amount, int maxTrades, int xp) {
        return new BasicItemListing(amount, new ItemStack(item), maxTrades, xp, 0.05F);
    }

    @SubscribeEvent
    public static void onBiomeLoad(BiomeLoadingEvent event) {
        // setVegetalFeature(event, FeatureRegister.PATCH_BAMBOOSHOOT.get(), true,
        //         0.4F, 1.0F);

        try {
            if (event.getName() == null)
                return;
        } catch (Exception ex) {
            return;
        }


        // zero and two is a limit for freezing and extremely hot place
        if (event.getCategory() != Biome.BiomeCategory.THEEND
                && event.getClimate().temperature > 0
                && event.getClimate().temperature < 2) {
            Cuisine.logger("群系加载中，正在注册事件", event.getName(), ",", FeatureRegister.crop_farmland_placed.getId());
            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeatureRegister.crop_farmland_placed.getHolder().get());
        }

        if (event.getCategory() == Biome.BiomeCategory.FOREST) {
            Cuisine.logger("群系加载中，正在注册事件", event.getName(), ",", FeatureRegister.f3.getId());
            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeatureRegister.f3.getHolder().get());

        }

        // if (event.getCategory() == Biome.BiomeCategory.FOREST) {
        //     Cuisine.logger("群系加载中，正在注册事件", event.getName(), ",", FeatureRegister.crop_farmland_placed.getKey());
        //     event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeatureRegister.PATCH_BAMBOOSHOOT.getHolder().get());
        //
        // }
    }
}
