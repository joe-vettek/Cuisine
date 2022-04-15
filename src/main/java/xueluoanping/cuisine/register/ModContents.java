package xueluoanping.cuisine.register;

import com.mojang.datafixers.types.Type;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.api.craft.BasinSqueezingRecipe;
import xueluoanping.cuisine.block.BlockBasin;
import xueluoanping.cuisine.block.blockitem.BlockBasinItem;
import xueluoanping.cuisine.block.entity.BasinBlockEntity;
import xueluoanping.cuisine.network.network;

import static xueluoanping.cuisine.Cuisine.CREATIVE_TAB;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModContents {
    public static Block basin = null;
    public static BlockEntityType<BasinBlockEntity> basinEntityType = null;
    public static BlockItem itemBasin = null;


    public static RecipeType<BasinSqueezingRecipe> TYPE = null;
    public static BasinSqueezingRecipe.Serializer SERIALIZER = null;


    @SubscribeEvent
    public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
        // register a new block here
        Cuisine.logger("Register Block");
        basin = new BlockBasin(BlockBehaviour.Properties.of(Material.STONE)
                .sound(SoundType.STONE).strength(2.0F)
                .noOcclusion().isSuffocating(ModContents::predFalse).isRedstoneConductor(ModContents::predFalse)

        );
        event.getRegistry().register(basin.setRegistryName("basin"));


    }

    private static boolean predFalse(BlockState p_235436_0_, BlockGetter p_235436_1_, BlockPos p_235436_2_) {
        return false;
    }

    @SubscribeEvent
    public static void registerTileEntities(RegistryEvent.Register<BlockEntityType<?>> event) {
        Cuisine.logger("Register Block Entity");
        basinEntityType = (BlockEntityType<BasinBlockEntity>) BlockEntityType.Builder.of(BasinBlockEntity::new, basin).build((Type) null).setRegistryName(new ResourceLocation(Cuisine.MODID, "basin"));
        event.getRegistry().register(basinEntityType);

    }

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        itemBasin = new BlockBasinItem(basin, new Item.Properties().tab(CREATIVE_TAB));
        event.getRegistry().register(itemBasin.setRegistryName(basin.getRegistryName()));
        Cuisine.logger("Register Item");
    }

    @SubscribeEvent
    public static void registerRecipe(final FMLCommonSetupEvent event) {
        Cuisine.logger("Register Misc");
        DispenseItemBehavior dispenseitembehavior1 = new DefaultDispenseItemBehavior() {
            private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

            public ItemStack execute(BlockSource p_123561_, ItemStack p_123562_) {
                DispensibleContainerItem dispensiblecontaineritem = (DispensibleContainerItem) p_123562_.getItem();
                BlockPos blockpos = p_123561_.getPos().relative(p_123561_.getBlockState().getValue(DispenserBlock.FACING));
                Level level = p_123561_.getLevel();
                if (dispensiblecontaineritem.emptyContents((Player) null, level, blockpos, (BlockHitResult) null)) {
                    dispensiblecontaineritem.checkExtraContent((Player) null, level, p_123562_, blockpos);
                    return new ItemStack(Items.BUCKET);
                } else {
                    return this.defaultDispenseItemBehavior.dispense(p_123561_, p_123562_);
                }
            }
        };
        DispenserBlock.registerBehavior(FluidRegister.juice_bucket.get(), dispenseitembehavior1);
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
