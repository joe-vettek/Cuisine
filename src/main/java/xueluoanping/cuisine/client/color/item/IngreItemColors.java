package xueluoanping.cuisine.client.color.item;


import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import xueluoanping.cuisine.items.base.FluidContainerItem;

import java.awt.*;

public class IngreItemColors implements ItemColor {
    @Override
    public int getColor(ItemStack itemStack, int tintIndex) {
        return Color.CYAN.getRGB();
    }
}
