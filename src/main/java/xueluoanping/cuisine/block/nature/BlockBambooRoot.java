package xueluoanping.cuisine.block.nature;

import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import xueluoanping.cuisine.util.MathUtils;
import xueluoanping.cuisine.register.BlockRegister;
import xueluoanping.cuisine.tag.CuisineTags;

public class BlockBambooRoot extends Block {
	public static final IntegerProperty AGE = BlockStateProperties.AGE_5;
	public static final BooleanProperty HUMID = BooleanProperty.create("humid");

	public BlockBambooRoot(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(AGE, 5).setValue(HUMID,false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
		super.createBlockStateDefinition(p_49915_.add(AGE).add(HUMID));
	}

	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return true;
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
//		Cuisine.logger(level.isRainingAt(pos.above()),level.canSeeSky(pos.above()));
		if(random.nextInt(50)!=0)return;
		if (state.getValue(AGE) > 0) {
			if (random.nextInt(4) > 0) return;
			BlockPos pos1 = MathUtils.getRandomSpreadPos(pos, random);
			int yoffset = pos.getY() - pos1.getY();
			if (yoffset != 0 && random.nextInt(10) > 0) pos1=pos1.offset(0, yoffset, 0);
			if (pos == pos1) return;
			if (level.getBlockState(pos1).is(CuisineTags.bamboo_root_spread_on)) {
				boolean flag = level.getBlockState(pos1).getBlock() != BlockRegister.bamboo_root.get() || level.getBlockState(pos1).getValue(AGE) < level.getBlockState(pos).getValue(AGE) - 1;
				if (flag)
				{
					level.setBlock(pos1, BlockRegister.bamboo_root.get().defaultBlockState().setValue(AGE, state.getValue(AGE) - 1), Block.UPDATE_ALL);
					level.setBlock(pos, BlockRegister.bamboo_root.get().defaultBlockState().setValue(AGE, state.getValue(AGE) - 1), Block.UPDATE_ALL);
				}}

		}
		int chance =random.nextInt(14);
		if(chance!=0)return;

		if(level.isRainingAt(pos.above())&& !state.getValue(HUMID))
			level.setBlock(pos, state.setValue(HUMID,true), Block.UPDATE_ALL);
		if(!level.isRainingAt(pos.above())&& state.getValue(HUMID))
		{
			state.setValue(HUMID,false);
			if (level.isEmptyBlock(pos.above()) && level.getRawBrightness(pos.above(), 0) >= 9)
			level.setBlock(pos.above(), BlockRegister.bamboo_plant.get().defaultBlockState(), Block.UPDATE_ALL);
		}

		super.randomTick(state, level, pos, random);
	}


}
