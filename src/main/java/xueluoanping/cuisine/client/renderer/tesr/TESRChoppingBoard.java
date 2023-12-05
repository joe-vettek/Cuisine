package xueluoanping.cuisine.client.renderer.tesr;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import xueluoanping.cuisine.block.entity.ChoppingBoardBlockEntity;

public class TESRChoppingBoard implements BlockEntityRenderer<ChoppingBoardBlockEntity> {
	public TESRChoppingBoard(BlockEntityRendererProvider.Context pContext) {
	}
	@Override
	public void render(ChoppingBoardBlockEntity choppingBoardBlockEntity, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLight, int combinedOverlay) {

		if (!choppingBoardBlockEntity.hasLevel() || choppingBoardBlockEntity == null)
			return;
		int posLong = (int) choppingBoardBlockEntity.getBlockPos().asLong();
		matrixStackIn.pushPose();

		ItemStack stack = Blocks.OAK_LOG.asItem().getDefaultInstance();
		matrixStackIn.translate(0.1f,0,0.1f);
		matrixStackIn.scale(0.8f, 0.4f, 0.8f);
		TESRBasinBase.drawBasin(stack, combinedLight, combinedOverlay, matrixStackIn, bufferIn, posLong);
		matrixStackIn.popPose();
	}


}
