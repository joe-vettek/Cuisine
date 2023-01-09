package xueluoanping.cuisine.items;


import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.registries.ILockableRegistry;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.util.CuisineFakePlayer;

public class BehaviorArmDispense extends DefaultDispenseItemBehavior {

    @Override
    public ItemStack execute(BlockSource source, ItemStack stack) {

        Direction facing = source.getBlockState().getValue(BlockStateProperties.FACING);
        BlockPos destination = source.getPos().offset(facing.getNormal());

        ServerLevel currentWorld = source.getLevel();
        //
        //        if (!(currentWorld instanceof ServerLevel))
        //        {
        //            return stack;
        //        }
        // Current it's always serverLevel
        //
        FakePlayer player = CuisineFakePlayer.getInstance(currentWorld);
        player.setPos(source.getPos().getX(), source.getPos().getY(), source.getPos().getZ());
        BlockState targetBlockState = currentWorld.getBlockState(destination);

        // Cuisine.logger(currentWorld.getBlockEntity(destination).serializeNBT());
        // 判定是否是战利品箱子，问题不大
        BlockEntity facingBlockEntity = currentWorld.getBlockEntity(destination);
        if (facingBlockEntity != null)
            if (facingBlockEntity.serializeNBT().contains("LootTable")) {
                return stack;
            }

        BlockHitResult target = new BlockHitResult(new Vec3(0.5F, 0.5F, 0F), facing, player.getOnPos(), true);
        PlayerInteractEvent.RightClickBlock evt = new PlayerInteractEvent.RightClickBlock(player, InteractionHand.MAIN_HAND, destination, target);
        if (!MinecraftForge.EVENT_BUS.post(evt))
            targetBlockState.getBlock().use(targetBlockState, source.getLevel(), destination, player, InteractionHand.MAIN_HAND, target);

        player.closeContainer();
        return stack;
    }
}
