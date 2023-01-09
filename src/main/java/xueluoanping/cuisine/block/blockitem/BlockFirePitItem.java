package xueluoanping.cuisine.block.blockitem;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import xueluoanping.cuisine.event.type.EnumFirePitState;

public class BlockFirePitItem extends BlockItem {
    public BlockFirePitItem(Block block, Properties properties) {
        super(block, properties);
    }


    @Override
    public MutableComponent getName(ItemStack stack) {

        try {
            if (!stack.hasTag())
                return (MutableComponent) super.getName(stack);
            switch (EnumFirePitState.matchWithoutError(stack)) {
                case WOK:
                    return new TranslatableComponent("block.cuisine.fire_pit_with_wok");
                case STICKS:
                    return new TranslatableComponent("block.cuisine.fire_pit_with_sticks");
                case FRYING_PAN:
                    return new TranslatableComponent("block.cuisine.fire_pit_with_frying_pan");
                default:
                    return (MutableComponent) super.getName(stack);
            }
        } catch (Exception ex) {
        }
        return (MutableComponent) super.getName(stack);
    }
}
