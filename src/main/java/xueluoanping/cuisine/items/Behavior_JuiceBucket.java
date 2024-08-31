package xueluoanping.cuisine.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.BlockHitResult;

public class Behavior_JuiceBucket extends DefaultDispenseItemBehavior{
    private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

    public ItemStack execute(BlockSource source, ItemStack stack) {
        DispensibleContainerItem dispensiblecontaineritem = (DispensibleContainerItem) stack.getItem();
        BlockPos blockpos = source.pos().relative(source.state().getValue(DispenserBlock.FACING));
        Level level = source.level();
        if (dispensiblecontaineritem.emptyContents((Player) null, level, blockpos, (BlockHitResult) null)) {
            dispensiblecontaineritem.checkExtraContent((Player) null, level, stack, blockpos);
            return new ItemStack(Items.BUCKET);
        } else {
            return this.defaultDispenseItemBehavior.dispense(source, stack);
        }
    }
}
