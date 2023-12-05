package xueluoanping.cuisine.client.renderer.tesr;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import xueluoanping.cuisine.block.BlockBasin;
import xueluoanping.cuisine.block.entity.BasinBlockEntity;
import xueluoanping.cuisine.block.entity.BasinColorBlockEntity;
import xueluoanping.cuisine.register.BlockEntityRegister;

public class TESRBasinColored implements BlockEntityRenderer<BasinColorBlockEntity> {
	public TESRBasinColored(BlockEntityRendererProvider.Context pContext) {
	}
	@Override
	public void render(BasinColorBlockEntity basinBlockEntity, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLight, int combinedOverlay) {


		if (!basinBlockEntity.hasLevel() || basinBlockEntity == null)
			return;


		Direction direction = basinBlockEntity.getBlockState().getValue(BlockBasin.FACING).getOpposite();
		ItemStack basinStack = basinBlockEntity.getStoredItem();
		int posLong = (int) basinBlockEntity.getBlockPos().asLong();

		if (!basinBlockEntity.hasNoFluid()) {
			// render the fluid
			matrixStackIn.pushPose();
			TESRBasic.renderFluid(basinBlockEntity, matrixStackIn, bufferIn, combinedLight, partialTicks);
			matrixStackIn.popPose();
		}
		if (!basinStack.isEmpty()) {
			matrixStackIn.pushPose();
			// Center item above the cutting board
			TESRBasinBase.drawBasinStack(basinStack,direction,basinBlockEntity.getLevel(),combinedLight, combinedOverlay, matrixStackIn, bufferIn, posLong);
			matrixStackIn.popPose();
		}
		matrixStackIn.pushPose();
		ItemStack stack = basinBlockEntity.getBlockState().getBlock().asItem().getDefaultInstance();
		TESRBasinBase.drawBasin(stack,combinedLight, combinedOverlay, matrixStackIn, bufferIn, posLong);
		matrixStackIn.popPose();

		// int posLong = (int) basinBlockEntity.getBlockPos().asLong();
		// matrixStackIn.pushPose();
		// matrixStackIn.translate(0.5,0.5,0.5);
		// ItemStack stack = basinBlockEntity.getBlockState().getBlock().getCloneItemStack(null,null,basinBlockEntity.getLevel(),basinBlockEntity.getBlockPos(),null);
		//
		// Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.NONE, combinedLight, combinedOverlay, matrixStackIn, bufferIn, posLong);
		// matrixStackIn.popPose();
	}
}
