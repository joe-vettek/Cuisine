package xueluoanping.cuisine.blockentity.firepit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import xueluoanping.cuisine.register.BlockEntityRegister;

/**
 * Cuisine的炒锅主要分为两部分，一部分是食材，这部分是纯Item，可以后续再处理与食材的转换关系，另外一部分是调味品。
 * 调味品部分比较复杂，分为液体和固体调味品
 **/
public class WokOnFirePitbBlockEntity extends AbstractFirepitBlockEntity {
    private static final int stackLimit = 1;


    private final ItemStackHandler inventory;
    private final ItemStackHandler seasonings;
    private final FluidTanksHolder seasoningLiquids;


    public WokOnFirePitbBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegister.wok_on_fire_pit_entity_type.get(), pos, state);
        this.inventory = createHandler(4, stackLimit);
        this.seasonings = createHandler(16, 1);
        this.seasoningLiquids = createMulFluidHandler(10, 100);
    }

    @Override
    public void loadAdditional(CompoundTag data, HolderLookup.Provider pRegistries) {
        this.inventory.deserializeNBT(pRegistries, data.getCompound("inventory"));
        this.seasonings.deserializeNBT(pRegistries, data.getCompound("seasonings"));
        this.seasoningLiquids.readFromNBT(pRegistries, data.getCompound("seasoningLiquids"));
        super.loadAdditional(data, pRegistries);
    }


    @Override
    protected void saveAdditional(CompoundTag data, HolderLookup.Provider pRegistries) {
        data.put("inventory", this.inventory.serializeNBT(pRegistries));
        data.put("seasonings", this.seasonings.serializeNBT(pRegistries));
        data.put("seasoningLiquids", this.seasoningLiquids.writeToNBT(pRegistries, new CompoundTag()));
        super.saveAdditional(data, pRegistries);
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public FluidTanksHolder getSeasoningLiquids() {
        return seasoningLiquids;
    }

    public ItemStackHandler getSeasonings() {
        return seasonings;
    }

    @Override
    public void tick() {
        super.tick();
    }

    public static void tickEntity(Level level, BlockPos pos, BlockState state, WokOnFirePitbBlockEntity entity1) {
        entity1.tick();
    }
}
