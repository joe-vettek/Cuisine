package xueluoanping.cuisine.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.register.BlockEntityRegister;

public class ChoppingBoardBlockEntity extends SyncBlockEntity{
	private final ItemStackHandler inventory;

	public ChoppingBoardBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegister.chopping_board_entity_type.get(), pos, state);
		this.inventory=createHandler(1,1);
	}

	@Override
	protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
		inventory.deserializeNBT(pRegistries, pTag.getCompound("inventory"));
		super.loadAdditional(pTag, pRegistries);
	}

	@Override
	protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
		pTag.put("inventory",inventory.serializeNBT(pRegistries));
		super.saveAdditional(pTag, pRegistries);
	}

	public ItemStackHandler getInventory() {
		return inventory;
	}
}
