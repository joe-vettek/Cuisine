package xueluoanping.cuisine.client.color.item;


import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import xueluoanping.cuisine.items.base.FluidContainerItem;

public class SpiceBottletItemColors implements ItemColor {
    @Override
    public int getColor(ItemStack itemStack, int tintIndex) {
        if (itemStack.getItem() instanceof FluidContainerItem fluidContainerItem && tintIndex == 0) {
            var f = fluidContainerItem.transferToFluidHandler(itemStack).getFluidInTank(0);
            if (!f.isEmpty())
                return IClientFluidTypeExtensions.of(f.getFluid()).getTintColor(f);
        }
        return -1;
    }
}
