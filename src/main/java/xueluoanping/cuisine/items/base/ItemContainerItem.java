package xueluoanping.cuisine.items.base;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import xueluoanping.cuisine.register.ModCapabilities;

import javax.annotation.Nonnull;

public interface ItemContainerItem {

    int getStackLimit(int slot, ItemStack stack);

    Item getCraftingRemainingItem();

    default boolean isItemValid(int tank, @Nonnull ItemStack stack) {
        return !stack.is((Item) this);
    }

    default IItemHandler transferToItemHandler(@NotNull ItemStack stack) {
        return new ItemStackHandler(1) {
            @Override
            protected int getStackLimit(int slot, ItemStack stack) {
                return ItemContainerItem.this.getStackLimit(slot, stack);
            }

            @Override
            public ItemStack getStackInSlot(int slot) {
                return stack.getOrDefault(ModCapabilities.SIMPLE_ITEM, new ItemHolder(ItemStack.EMPTY)).stack();
            }

            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                return ItemContainerItem.this.isItemValid(slot, stack);
            }
        };
    }


}
