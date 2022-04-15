package xueluoanping.cuisine.client.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public interface BlockDefinition {

    Map<String, Factory<?>> MAP = Maps.newConcurrentMap();
    List<Factory<?>> FACTORIES = Lists.newLinkedList();

    static void registerFactory(Factory<?> factory) {
        MAP.put(factory.getId(), factory);
        if (SimpleBlockDefinition.TYPE.equals(factory.getId())) {
            FACTORIES.add(factory);
        } else {
            FACTORIES.add(0, factory);
        }
    }

    static BlockDefinition fromNBT(CompoundTag tag) {
        Factory<?> factory = MAP.get(tag.getString("Type"));
        if (factory == null)
            return null;
        return factory.fromNBT(tag);
    }

    static BlockDefinition fromBlock(BlockState state, BlockEntity blockEntity, LevelReader level, BlockPos pos) {
        for (Factory<?> factory : FACTORIES) {
            BlockDefinition supplier = factory.fromBlock(state, blockEntity, level, pos);
            if (supplier != null) {
                return supplier;
            }
        }
        return null;
    }

    static BlockDefinition fromItem(ItemStack stack, @Nullable BlockPlaceContext context) {
        if (!stack.isEmpty()) {
            for (Factory<?> factory : FACTORIES) {
                BlockDefinition supplier = factory.fromItem(stack, context);
                if (supplier != null) {
                    return supplier;
                }
            }
        }
        return null;
    }

    Factory<?> getFactory();

    @OnlyIn(Dist.CLIENT)
    BakedModel model();

    @OnlyIn(Dist.CLIENT)
    default IModelData modelData() {
        return EmptyModelData.INSTANCE;
    }

    @OnlyIn(Dist.CLIENT)
    Material renderMaterial(@Nullable Direction direction);

    void save(CompoundTag tag);

    @OnlyIn(Dist.CLIENT)
    boolean canRenderInLayer(RenderType layer);

    boolean canOcclude();

    @OnlyIn(Dist.CLIENT)
    int getColor(BlockState blockState, BlockAndTintGetter level, BlockPos pos, int index);

    Component getDescription();

    void place(Level level, BlockPos pos);

    ItemStack createItem(HitResult target, BlockGetter world, @Nullable BlockPos pos, @Nullable Player player);

    BlockState getBlockState();

    default BlockDefinition getCamoDefinition() {
        return null;
    }

    static BlockDefinition getCamo(BlockDefinition definition) {
        BlockDefinition camo = definition.getCamoDefinition();
        while (camo != null && camo != definition) {
            definition = camo;
            camo = definition.getCamoDefinition();
        }
        return definition;
    }

    default int getLightEmission(BlockGetter level, BlockPos pos) {
        return getBlockState().getLightEmission(level, pos);
    }

    SoundType getSoundType();

    interface Factory<T extends BlockDefinition> {
        T fromNBT(CompoundTag tag);

        String getId();

        @Nullable
        T fromBlock(BlockState state, BlockEntity blockEntity, LevelReader level, BlockPos pos);

        @Nullable
        T fromItem(ItemStack stack, @Nullable BlockPlaceContext context);
    }

}
