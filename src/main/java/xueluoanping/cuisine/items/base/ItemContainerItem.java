package xueluoanping.cuisine.items.base;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
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
                var con = stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
                if (con.getSlots() == 0) return ItemStack.EMPTY;
                return stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY).getStackInSlot(0);
            }

            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                return ItemContainerItem.this.isItemValid(slot, stack);
            }
        };
    }


}
