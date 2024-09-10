package xueluoanping.cuisine.items;


import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;
import xueluoanping.cuisine.items.base.FluidContainerItem;
import xueluoanping.cuisine.items.base.ItemContainerItem;
import xueluoanping.cuisine.items.base.ItemHolder;
import xueluoanping.cuisine.register.FluidRegister;
import xueluoanping.cuisine.register.ItemRegister;
import xueluoanping.cuisine.register.ModCapabilities;

import java.util.List;
import java.util.Optional;

public class ItemSpineBottle extends Item implements FluidContainerItem, ItemContainerItem {
    public ItemSpineBottle(Properties properties) {
        super(properties);
    }

    @Override
    public int getCapacity() {
        return 1000;
    }

    @Override
    public int getStackLimit(int slot, ItemStack stack) {
        return 10;
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        return FluidContainerItem.super.isFluidValid(tank, stack);
    }

    @Override
    public boolean isItemValid(int tank, @NotNull ItemStack stack) {
        return ItemContainerItem.super.isItemValid(tank, stack);
    }


    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        return ItemRegister.spice_bottle.value().getDefaultInstance();
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return transferToItemHandler(stack).getStackInSlot(0).isEmpty()
                && transferToFluidHandler(stack).getFluidInTank(0).isEmpty() ?
                DataComponents.COMMON_ITEM_COMPONENTS.getOrDefault(DataComponents.MAX_STACK_SIZE, 64) : 1;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        FluidUtil.getFluidHandler(stack).ifPresent(f ->
                {
                    if (!f.getFluidInTank(0).isEmpty())
                        tooltip.add(MutableComponent.create((f.getFluidInTank(0).getHoverName()).getContents()).append(String.format(": %d / %dmB", f.getFluidInTank(0).getAmount(), getCapacity())).withStyle(ChatFormatting.GRAY));
                }
        );
        Optional.ofNullable(transferToItemHandler(stack)).ifPresent(f ->
                {
                    if (!f.getStackInSlot(0).isEmpty())
                        tooltip.add(MutableComponent.create((f.getStackInSlot(0).getHoverName()).getContents()).append(String.format(": %d / %d", f.getStackInSlot(0).getCount(), getStackLimit(0, ItemStack.EMPTY))).withStyle(ChatFormatting.GRAY));
                }
        );

    }


    // @Override
    public void fillItemGroup(CreativeModeTab.Output group) {
        for (var fluid : FluidRegister.FLUIDS.getEntries()) {

            if (fluid.get() instanceof BaseFlowingFluid.Source) {
                ItemStack itemStack = new ItemStack(this);
                itemStack.set(ModCapabilities.SIMPLE_FLUID, SimpleFluidContent.copyOf(new FluidStack(fluid.get(), getCapacity())));
                group.accept(itemStack);
            }
        }
        for (var item : List.of(ItemRegister.chili_powder, ItemRegister.crude_salt, ItemRegister.salt, ItemRegister.unrefined_sugar, ItemRegister.sichuan_pepper_powder)) {

            if (item.value() instanceof Item item1) {
                ItemStack itemStack = new ItemStack(this);
                itemStack.setCount(1);
                var sss = new ItemStack(item1);
                sss.setCount(getStackLimit(0,sss));
                // itemStack.set(ModCapabilities.SIMPLE_ITEM, new ItemHolder(sss));
                itemStack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(List.of(sss)));
                group.accept(itemStack);
            }
        }
    }

}
