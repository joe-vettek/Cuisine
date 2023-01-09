package xueluoanping.cuisine.block.blockitem;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.WaterLilyBlockItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import xueluoanping.cuisine.Cuisine;

public class BlockItemRice extends BlockItemCuisineCrop {
    public BlockItemRice(Block p_43436_, Properties p_43437_) {
        super(p_43436_, p_43437_);
    }

    public InteractionResult useOn(UseOnContext p_43439_) {
        return InteractionResult.PASS;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand handIn) {
        // Cuisine.logger(     Villager.WANTED_ITEMS);
        BlockHitResult blockhitresult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        BlockHitResult blockhitresult1 = blockhitresult.withPosition(blockhitresult.getBlockPos());
        InteractionResult interactionresult = super.useOn(new UseOnContext(player, handIn, blockhitresult1));
        // level.setBlock(blockhitresult.getBlockPos().above().above(), Fluids.WATER.defaultFluidState().createLegacyBlock(), Block.UPDATE_ALL);

        return new InteractionResultHolder<>(interactionresult, player.getItemInHand(handIn));
        // return super.use(level, player, handIn);
    }
}
