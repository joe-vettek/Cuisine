package xueluoanping.cuisine.register;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xueluoanping.cuisine.block.blockitem.BlockFirePitItem;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.block.BlockFirePit;
import xueluoanping.cuisine.block.BlockMill;
import xueluoanping.cuisine.block.entity.FirePitBlockEntity;
import xueluoanping.cuisine.block.entity.MillBlockEntity;

public class BlockEntityRegister {
    public static final DeferredRegister<Item> DREntityBlockItems = DeferredRegister.create(ForgeRegistries.ITEMS, Cuisine.MODID);
    public static final DeferredRegister<Block> DREntityBlocks = DeferredRegister.create(ForgeRegistries.BLOCKS, Cuisine.MODID);
    public static final DeferredRegister<BlockEntityType<?>> DRBlockEntities = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Cuisine.MODID);

    public static final RegistryObject<Block> mill =
            DREntityBlocks.register("mill", ()->new BlockMill(BlockBehaviour.Properties.of(Material.STONE).strength(2.0f).sound(SoundType.STONE).noOcclusion()));
    public static final RegistryObject<Item> mill_item =
            DREntityBlockItems.register("mill", ()->new BlockItem(mill.get(),(new Item.Properties()).tab(Cuisine.CREATIVE_TAB)));
    public static final RegistryObject<BlockEntityType<MillBlockEntity>> mill_entity_type = DRBlockEntities
            .register("mill", () -> BlockEntityType.Builder
                    .of(MillBlockEntity::new, BlockEntityRegister.mill.get()).build(null));

    public static final RegistryObject<Block> fire_pit =
            DREntityBlocks.register("fire_pit", ()->new BlockFirePit(BlockBehaviour.Properties.of(Material.STONE).strength(2.0f).sound(SoundType.STONE).noOcclusion()));
    public static final RegistryObject<Item> fire_pit_item =
            DREntityBlockItems.register("fire_pit", ()->new BlockFirePitItem(fire_pit.get(),(new Item.Properties()).tab(Cuisine.CREATIVE_TAB)));
    public static final RegistryObject<BlockEntityType<FirePitBlockEntity>> fire_pit_entity_type = DRBlockEntities
            .register("fire_pit", () -> BlockEntityType.Builder
                    .of(FirePitBlockEntity::new, BlockEntityRegister.fire_pit.get()).build(null));
}
