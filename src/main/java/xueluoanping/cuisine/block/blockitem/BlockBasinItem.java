package xueluoanping.cuisine.block.blockitem;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BlockBasinItem extends BlockItem {

    public BlockBasinItem(Block block, Properties properties) {
        super(block, properties);
    }


	@Override
    protected boolean updateCustomBlockEntityTag(BlockPos pos, Level worldIn, Player player, ItemStack stack, BlockState state) {
        return super.updateCustomBlockEntityTag(pos, worldIn, player, stack, state);
    }





//	@Override
//	public MutableComponent getName(ItemStack stack) {
//		try {
//			String[] space = NBTUtils.getNameFromRetxtureStack(stack).split(":");
////			logger.info(TextUtils.getLanguage());
//			return TextUtils.getLanguage().equals("简体中文") ?
//					NBTUtils.getBlockFromRegister(space).getName().append("").append(super.getName(stack)) :
//					NBTUtils.getBlockFromRegister(space).getName().append(" ").append(super.getName(stack));
//		} catch (Exception exception000) {
//			return (MutableComponent) super.getName(stack);
//		}
//
//	}
}
