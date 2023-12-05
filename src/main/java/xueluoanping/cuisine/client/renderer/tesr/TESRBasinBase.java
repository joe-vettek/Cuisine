package xueluoanping.cuisine.client.renderer.tesr;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TESRBasinBase {

	static void drawBasinStack(ItemStack basinStack, Direction direction, Level level, int combinedLight, int combinedOverlay, PoseStack matrixStackIn, MultiBufferSource bufferIn, int posLong) {
		matrixStackIn.translate(0.5D, 0.12D, 0.5D);
		ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
		boolean isBlockItem = itemRenderer.getModel(basinStack, level, null, 0).isGui3d();
		if (isBlockItem) {
			// Block
			matrixStackIn.scale(0.4f, 0.4f, 0.4f);
			matrixStackIn.translate(0.2, 0, 0.2);
		} else {
			// Item
			matrixStackIn.scale(0.5f, 0.5f, 0.5f);
			float f = -direction.toYRot();
			matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(f));
			matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(90.0F));
		}
		int max = basinStack.getCount() == 1 ? 1 : (basinStack.getCount() - 1) / (isBlockItem ? 16 : 8) + 1;
		for (int i = 0; i < max; i++) {
			if (isBlockItem) {
				// Block
				double translation = i % 2 == 0 ? -0.4 : 0.4;
				matrixStackIn.translate(translation, 0.2, translation);
				matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(70));
			} else {
				// Item
				double translation = i % 2 == 0 ? -0.1 : 0.1;
				matrixStackIn.translate(translation, translation, -0.1);
				matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(70));
			}

			Minecraft.getInstance().getItemRenderer().renderStatic(basinStack, ItemTransforms.TransformType.NONE, combinedLight, combinedOverlay, matrixStackIn, bufferIn, posLong);
		}
	}

	static void drawBasin(ItemStack stack, int combinedLight, int combinedOverlay, PoseStack matrixStackIn, MultiBufferSource bufferIn, int posLong) {
		matrixStackIn.translate(0.5,0.5,0.5);
		Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.NONE, combinedLight, combinedOverlay, matrixStackIn, bufferIn, posLong);
	}
}
