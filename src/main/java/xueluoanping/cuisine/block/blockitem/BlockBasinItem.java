package xueluoanping.cuisine.block.blockitem;

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import xueluoanping.cuisine.api.util.NBTUtils;
import xueluoanping.cuisine.api.util.Platform;
import xueluoanping.cuisine.api.util.TextUtils;
import xueluoanping.cuisine.register.ModContents;

public class BlockBasinItem extends BlockItem {
    public static final Set<BlockEntityType<?>> INSTANT_UPDATE_TILES = Platform.isPhysicalClient() ? Sets.newHashSet() : null;

    public BlockBasinItem(Block p_40565_, Properties p_40566_) {
        super(p_40565_, p_40566_);
    }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos pos, Level worldIn, Player player, ItemStack stack, BlockState state) {
        if (worldIn.isClientSide) {
            BlockEntity tile = worldIn.getBlockEntity(pos);
            if (tile != null && tile.getType()== ModContents.basinEntityType) {
                CompoundTag data = getBlockEntityData(stack);
                if (data != null) {
                    data = data.copy();
                    tile.load(data);
                    tile.setChanged();
                }
            }
        }
        return super.updateCustomBlockEntityTag(pos, worldIn, player, stack, state);
    }





	@Override
	public MutableComponent getName(ItemStack stack) {
		try {
			String[] space = NBTUtils.getNameFromRetxtureStack(stack).split(":");
//			logger.info(TextUtils.getLanguage());
			return TextUtils.getLanguage().equals("简体中文") ?
					NBTUtils.getBlockFromRegister(space).getName().append("").append(super.getName(stack)) :
					NBTUtils.getBlockFromRegister(space).getName().append(" ").append(super.getName(stack));
		} catch (Exception exception000) {
			return (MutableComponent) super.getName(stack);
		}

	}
}
