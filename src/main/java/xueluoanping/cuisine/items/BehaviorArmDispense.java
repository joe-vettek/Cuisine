package xueluoanping.cuisine.items;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import xueluoanping.cuisine.util.CuisineFakePlayer;

public class BehaviorArmDispense extends DefaultDispenseItemBehavior {

    @Override
    public ItemStack execute(BlockSource source, ItemStack stack) {

        Direction facing = source.state().getValue(BlockStateProperties.FACING);
        BlockPos destination = source.pos().relative(facing);

        ServerLevel level = source.level();
        //
        //        if (!(level instanceof ServerLevel))
        //        {
        //            return stack;
        //        }
        // Current it's always serverLevel
        //
        FakePlayer player = CuisineFakePlayer.getInstance(level);
        player.setPos(source.pos().getX(), source.pos().getY(), source.pos().getZ());
        BlockState targetBlockState = level.getBlockState(destination);

        // Cuisine.logger(level.getBlockEntity(destination).serializeNBT());
        // 判定是否是战利品箱子，问题不大
        BlockEntity facingBlockEntity = level.getBlockEntity(destination);
        if (facingBlockEntity != null)

            if (facingBlockEntity.saveWithFullMetadata(facingBlockEntity.getLevel().registryAccess()).contains("LootTable")) {
                return stack;
            }

        BlockHitResult target = new BlockHitResult(new Vec3(0.5F, 0.5F, 0F), facing, destination, true);
        PlayerInteractEvent.RightClickBlock evt = new PlayerInteractEvent.RightClickBlock(player, InteractionHand.MAIN_HAND, destination, target);
        if (!NeoForge.EVENT_BUS.post(evt).isCanceled())
            targetBlockState.useWithoutItem(source.level(), player, target);

        player.closeContainer();
        return stack;
    }
}
