package xueluoanping.cuisine.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import xueluoanping.cuisine.block.baseblock.SimpleEntityBlock;

public class BlockJar extends SimpleEntityBlock {
	public static final MapCodec<BlockJar> CODEC = simpleCodec(BlockJar::new);

	public BlockJar(Properties properties) {
		super(properties);
	}

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return CODEC;
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return null;
	}
}
