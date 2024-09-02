package xueluoanping.cuisine.items.base;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;

public record ItemHolder(ItemStack stack) {
    public static final Codec<ItemHolder> CODEC = Codec.lazyInitialized(
            () -> RecordCodecBuilder.create(
                    item -> item.group(ItemStack.CODEC.fieldOf("item").forGetter(ItemHolder::stack))
                            .apply(item, ItemHolder::new)
            )
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, ItemHolder> STREAM_CODEC = new StreamCodec<>() {
        public ItemHolder decode(RegistryFriendlyByteBuf byteBuf) {
            ItemStack itemstack = ItemStack.OPTIONAL_STREAM_CODEC.decode(byteBuf);
            if (itemstack.isEmpty()) {
                throw new DecoderException("Empty ItemStack not allowed");
            } else {
                return new ItemHolder(itemstack);
            }
        }

        public void encode(RegistryFriendlyByteBuf byteBuf, ItemHolder p_331138_) {
            if (p_331138_.stack().isEmpty()) {
                throw new EncoderException("Empty ItemStack not allowed");
            } else {
                ItemStack.OPTIONAL_STREAM_CODEC.encode(byteBuf, p_331138_.stack());
            }
        }
    };
    @Override
    public boolean equals(Object obj) {
        return stack.equals(obj);
    }

    @Override
    public int hashCode() {
        return stack.hashCode();
    }
}
