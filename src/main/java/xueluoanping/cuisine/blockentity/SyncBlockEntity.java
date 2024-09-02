package xueluoanping.cuisine.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.ItemStackHandler;
import xueluoanping.cuisine.Cuisine;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

public class SyncBlockEntity extends BlockEntity {
    public SyncBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
        super(blockEntityType, pos, state);
    }


    @Override
    @Nullable
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }
//
//	@Override
//	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
//		load(pkt.getTag());
//	}

    protected void inventoryChanged() {
        super.setChanged();
        if (level != null)
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }

    protected ItemStackHandler createHandler(int size) {
        return new ItemStackHandler(size) {

            @Override
            protected void onContentsChanged(int slot) {
                inventoryChanged();
            }
        };
    }

    protected ItemStackHandler createHandler(int size, int stackLimit) {
        return new ItemStackHandler(size) {

            @Override
            protected int getStackLimit(int slot, ItemStack stack) {
                return stackLimit;
            }

            @Override
            protected void onContentsChanged(int slot) {
                inventoryChanged();
            }
        };
    }

    protected FluidTank createFuildHandler(int size) {
        return new FluidTankHolder(size);
    }

    protected FluidTanksHolder createMulFluidHandler(int tanks, int size) {
        int[] siezs = new int[tanks];
        Arrays.fill(siezs, size);
        return new FluidTanksHolder(tanks, siezs);
    }

    class FluidTankHolder extends FluidTank {
        public FluidTankHolder(int capacity, Predicate<FluidStack> validator) {
            super(capacity, validator);
        }

        public FluidTankHolder(int capacity) {
            super(capacity);
        }

        @Override
        protected void onContentsChanged() {
            inventoryChanged();
        }
    }

    public class FluidTanksHolder implements IFluidHandler {

        private final int tanks;
        private final int[] capacities;
        private final FluidTankHolder[] fluidTanks;

        public FluidTanksHolder(int tanks, int[] capacities) {
            this.tanks = tanks;
            this.capacities = capacities;
            this.fluidTanks = new FluidTankHolder[tanks];
            for (int i = 0; i < fluidTanks.length; i++) {
                fluidTanks[i] = new FluidTankHolder(capacities[i]);
            }
        }

        @Override
        public int getTanks() {
            // int tankCount = 0;
            // FluidStack stack = FluidStack.EMPTY;
            // for (int i = 0; i < tanks; i++) {
            //     var newStack = fluidTanks[i].getFluid();
            //     if (!FluidStack.isSameFluidSameComponents(stack, newStack)) {
            //         tankCount++;
            //         stack = newStack;
            //     }
            // }
            return tanks;
        }

        @Override
        public FluidStack getFluidInTank(int tank) {
            // ArrayList<FluidStack> fluidStackArrayList = new ArrayList<>();
            //
            // FluidStack stack = FluidStack.EMPTY;
            // for (int i = 0; i < tanks; i++) {
            //     var newStack = fluidTanks[i].getFluid().copy();
            //     if (!FluidStack.isSameFluidSameComponents(stack, newStack)) {
            //         fluidStackArrayList.add(newStack);
            //         stack = newStack;
            //     }else {
            //         if (!newStack.isEmpty()&&!stack.isEmpty()){
            //             stack.setAmount(stack.getAmount()+newStack.getAmount());
            //         }
            //     }
            // }
            return fluidTanks[tank].getFluid();
        }

        @Override
        public int getTankCapacity(int tank) {
            return capacities[tank];
        }

        @Override
        public boolean isFluidValid(int tank, FluidStack stack) {
            return fluidTanks[tank].isEmpty() || FluidStack.isSameFluidSameComponents(fluidTanks[tank].getFluid(), stack);
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            int amount = 0;
            int fluidAmount = resource.getAmount();
            for (int i = 0; i < tanks; i++) {
                if (fluidAmount <= 0) break;
                FluidStack stack = resource.copyWithAmount(fluidAmount);
                int fill = fluidTanks[i].fill(stack, action);
                amount += fill;
                fluidAmount -= fill;
            }
            return amount;
        }

        @Override
        public FluidStack drain(FluidStack resource, FluidAction action) {
            FluidStack fluidStack = FluidStack.EMPTY;
            for (int i = 0; i < tanks; i++) {
                var newFluidStack = fluidTanks[i].drain(resource, action);
                if (!newFluidStack.isEmpty()) {
                    if (fluidStack.isEmpty())
                        fluidStack = newFluidStack;
                    else fluidStack.setAmount(fluidStack.getAmount() + newFluidStack.getAmount());
                }
            }
            return fluidStack;
        }

        @Override
        public FluidStack drain(int maxDrain, FluidAction action) {
            FluidStack fluidStack = FluidStack.EMPTY;
            for (int i = 0; i < tanks; i++) {
                var newFluidStack = fluidTanks[i].drain(maxDrain, action);
                if (!newFluidStack.isEmpty()) {
                    fluidStack = newFluidStack;
                    break;
                }
            }
            return fluidStack;
        }

        public FluidTanksHolder readFromNBT(HolderLookup.Provider lookupProvider, CompoundTag nbt) {
            var tankList = nbt.getList("FluidTanks", Tag.TAG_COMPOUND);
            for (int i = 0; i < tankList.size(); i++) {
                fluidTanks[i].readFromNBT(lookupProvider, tankList.getCompound(i));
            }
            return this;
        }

        public CompoundTag writeToNBT(HolderLookup.Provider lookupProvider, CompoundTag nbt) {
            var tankList = new ListTag();
            for (int i = 0; i < fluidTanks.length; i++) {
                tankList.addTag(i, fluidTanks[i].writeToNBT(lookupProvider, new CompoundTag()));
            }
            nbt.put("FluidTanks", tankList);
            return nbt;
        }

    }
}
