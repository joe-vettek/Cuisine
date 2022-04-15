package xueluoanping.cuisine.block.blockitem;

import com.google.common.collect.Sets;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import xueluoanping.cuisine.api.util.NBTUtils;
import xueluoanping.cuisine.api.util.Platform;
import xueluoanping.cuisine.register.ModContents;

import java.util.Set;

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
    public void fillItemCategory(CreativeModeTab p_40569_, NonNullList<ItemStack> list) {
        ForgeRegistries.BLOCKS.getValues().forEach(block -> {
            if(isFullBlock(block))
            list.add(NBTUtils.createTagFromTxtureProvider(getDefaultInstance(), block));
        });
        super.fillItemCategory(p_40569_, list);
    }

    public boolean isFullBlock( Block block ) {
        if (block.asItem()==this) {
            return false;
        }
        BlockState state = block.defaultBlockState();
        try {
            if (Block.isShapeFullBlock(state.getOcclusionShape(null, BlockPos.ZERO)))
                return true;
        } catch (Throwable e) {
        }
        return false;
    }
}
