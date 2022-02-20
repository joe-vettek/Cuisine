package xueluoanping.cuisine.block;


import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import snownee.kiwi.block.ModBlock;

public class BlockFirePit extends ModBlock {
//	public static final PropertyEnum<Component> COMPONENT = PropertyEnum.create("component", Component.class);
    public BlockFirePit(Properties builder) {
        super(builder.destroyTime(5).noOcclusion().requiresCorrectToolForDrops());
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
        return super.getLightEmission(state, world, pos);
    }

}
