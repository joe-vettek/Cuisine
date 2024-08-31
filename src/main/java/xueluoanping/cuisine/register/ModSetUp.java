package xueluoanping.cuisine.register;


import net.minecraft.world.entity.ai.behavior.WorkAtComposter;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.items.BehaviorArmDispense;
import xueluoanping.cuisine.items.Behavior_JuiceBucket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

// 在Forge开发里有两条总线，Mod总线和Forge总线，
// 所有和初始化相关的事件都是在Mod总线内，其他所有事件都在Forge总线内。
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class ModSetUp {
    // public static RecipeType<BasinSqueezingRecipe> TYPE = null;
    // public static BasinSqueezingRecipe.Serializer SERIALIZER = null;
    // static DispenseItemBehavior dispenseItemBehavior_Bucket = new Behavior_JuiceBucket();
    // static DispenseItemBehavior dispenseItemBehavior_woodenArm = new BehaviorArmDispense();

    @SubscribeEvent
    public static void registerDispenserHandler(final FMLCommonSetupEvent event) {

        event.enqueueWork(() -> {
            registerDispenserBehavior(event);
            registerCompostables(event);
            registerCustomVillagerNeed(event);
        });

        // ForgeRegistries.PROFESSIONS.getValue()
        // VillagerTrades.TRADES.get
        // 		EntityVillager.ITradeList


    }

    private static void registerDispenserBehavior(FMLCommonSetupEvent event) {
        Cuisine.logger("Register Dispenser Behavior");
        DispenserBlock.registerBehavior(FluidRegister.JUICE_BUCKET.get(), new Behavior_JuiceBucket());
        DispenserBlock.registerBehavior(ItemRegister.wooden_arm.get(), new BehaviorArmDispense());

    }

    private static void registerCompostables(FMLCommonSetupEvent event) {
        Cuisine.logger("Register Compostables");

        CropRegister.getWildCrops().forEach(itemRegistryObject -> {
            ComposterBlock.COMPOSTABLES.put(itemRegistryObject.get(), 0.65f);
        });
        ComposterBlock.COMPOSTABLES.put(CropRegister.garlic_item.get(), 0.7f);
        CropRegister.getGrassCrops().forEach(itemRegistryObject -> {
            ComposterBlock.COMPOSTABLES.put(itemRegistryObject.get(), 0.5f);
        });
        ComposterBlock.COMPOSTABLES.put(CropRegister.chili_item.get(), 0.7f);

        // WorkAtComposter.COMPOSTABLE_ITEMS = new ArrayList<>(WorkAtComposter.COMPOSTABLE_ITEMS);
        // CropRegister.getAllCrops().forEach(itemRegistryObject -> {
        //     WorkAtComposter.COMPOSTABLE_ITEMS.add(itemRegistryObject.get());
        // });
    }

    public static void registerCustomVillagerNeed( FMLCommonSetupEvent event) {
        Cuisine.logger("Register CustomVillagerNeed");
        // HarvestFarmland 帮忙种地的逻辑是itemstack.getItem() instanceof net.minecraftforge.common.IPlantable
        // event.enqueueWork(() -> {
        //     Villager.FOOD_POINTS = new HashMap<>(Villager.FOOD_POINTS);
        //     Villager.WANTED_ITEMS = new HashSet<>(Villager.WANTED_ITEMS);
        //     CropRegister.getAllCrops().forEach(itemRegistryObject -> {
        //         Villager.FOOD_POINTS.put(itemRegistryObject.get(), 2);
        //         Villager.WANTED_ITEMS.add(itemRegistryObject.get());
        //     });
        // });


    }

    //    @SubscribeEvent
    //    public void registerRecipe(final RegistryEvent.Register<RecipeSerializer<?>> event) {
    //        Cuisine.logger("Register Recipe");
    //        SERIALIZER = (BasinSqueezingRecipe.Serializer) new BasinSqueezingRecipe.Serializer()
    //                .setRegistryName(Cuisine.MODID + ":squeezing");
    //        event.getRegistry().register(SERIALIZER);
    //        TYPE = RecipeType.register(Cuisine.MODID + ":squeezing");
    //
    //    }
}
