package snownee.cuisine.world.gen;

import net.minecraft.block.state.IBlockState;
import net.minecraft.world.gen.feature.WorldGenTrees;

public class WorldGenCitrusGenusTree extends WorldGenTrees
{
    /**
     *
     * @param notifyUpdate true if setting new block will cause block update
     * @param woodType The actual block state of block used for "tree body"
     * @param leaveType The actual block state of block used for "tree leaves"
     */
    public WorldGenCitrusGenusTree(boolean notifyUpdate, IBlockState woodType, IBlockState leaveType)
    {
        super(notifyUpdate, 5, woodType, leaveType, false);
    }
}
