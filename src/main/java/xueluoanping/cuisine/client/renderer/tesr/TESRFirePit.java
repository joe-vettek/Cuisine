package xueluoanping.cuisine.client.renderer.tesr;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import xueluoanping.cuisine.block.entity.FirePitBlockEntity;
import xueluoanping.cuisine.client.gui.CuisineGUI;

import java.awt.*;

public class TESRFirePit implements BlockEntityRenderer<FirePitBlockEntity> {
	public TESRFirePit(BlockEntityRendererProvider.Context pContext) {
	}

	@Override
	public void render(FirePitBlockEntity firePitBlockEntity, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLight, int combinedOverlay) {
		matrixStackIn.pushPose();
		// Minecraft.getInstance().getTextureManager().bindForSetup(CuisineGUI.TEXTURE_ICONS);

//        TextureAtlasSprite level=Minecraft.getInstance().getTextureManager().getTexture().apply(CuisineGUI.TEXTURE_ICONS);
		float u0 = 0;
		float u1 = 1f;
		float v0 = 0;
		float v1 = 1f;
		// RenderSystem.setShaderTexture(0, CuisineGUI.TEXTURE_ICONS);
//        Cuisine.logger(RenderSystem.getShaderTexture(0));
		GlStateManager._disableCull();
		// VertexConsumer buffer = bufferIn.getBuffer(RenderType.cutout());
		VertexConsumer builder = bufferIn.getBuffer(RenderType.entitySmoothCutout(CuisineGUI.TEXTURE_ICONS));
		int colorRGB = Color.GRAY.getRGB();
		matrixStackIn.translate(0.5f, 1.2f, 0);
		matrixStackIn.scale(0.25f, 0.25f, 0.25f);
//        matrixStackIn.mulPose(new Quaternion(30,25,332,true));
//        matrixStackIn.scale(9,9,9);
//        addVertex(buffer, matrixStackIn, 1f, 1f, 0f, u1, v1, colorRGB, 1.0f, combinedLight);
//        addVertex(buffer, matrixStackIn, 1f, 0f, 0f, u1, v0, colorRGB, 1.0f, combinedLight);
//        addVertex(buffer, matrixStackIn, 0f, 0f, 0f, u0, v0, colorRGB, 1.0f, combinedLight);
//        addVertex(buffer, matrixStackIn, 0f, 1f, 0f, u0, v1, colorRGB, 1.0f, combinedLight);
		float f = (System.currentTimeMillis()%5000)/5000f;
		f= Mth.clamp(f,0.2f,0.8f);
		float nf=(1-f);


		blitRect(matrixStackIn, builder, combinedLight, OverlayTexture.NO_OVERLAY, 0, nf*80 * 0.0625f, 0, nf*80  * 0.0625f, 8 * 0.0625f, 80 * 0.0625f, 256, 256, false);
		blitRect(matrixStackIn, builder, combinedLight, OverlayTexture.NO_OVERLAY, 0, 0, 8 * 0.0625f, 0, 8 * 0.0625f, nf*80  * 0.0625f, 256, 256, false);

		// blitRect(matrixStackIn, builder, combinedLight, OverlayTexture.NO_OVERLAY, 16* 0.0625f, 0, 8* 0.0625f, 0, 8* 0.0625f, 80* 0.0625f, 256, 256, false);

		matrixStackIn.popPose();
	}

	public static void addVertex(VertexConsumer renderer, PoseStack stack, float x, float y, float z, float u, float v, int RGBA, float alpha, int brightness) {
		float red = ((RGBA >> 16) & 0xFF) / 255f;
		float green = ((RGBA >> 8) & 0xFF) / 255f;
		float blue = ((RGBA >> 0) & 0xFF) / 255f;
		renderer.vertex(stack.last().pose(), x, y, z).color(red, green, blue, alpha).uv(u, v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880)/*.lightmap(0, 240)*/.normal(stack.last().normal(), 0, 1.0F, 0).endVertex();
		int light1 = brightness & '\uffff';
		int light2 = brightness >> 16 & '\uffff';
//        renderer.vertex(stack.last().pose(), x, y, z).color(red, green, blue, alpha).uv(u, v).uv2(light1, light2).overlayCoords(OverlayTexture.NO_OVERLAY).normal(stack.last().normal(), 0, 1.0F, 0).endVertex();
	}

	protected static void blitRect(PoseStack matrixStack, VertexConsumer builder, int packedLight, int overlay, float x0, float y0, float xt, float yt, float width, float height, int tWidth, int tHeight, boolean mirrored) {

		float pixelScale = 0.0625f;
		float tx0 = xt / (tWidth * pixelScale);
		float ty0 = yt / (tHeight * pixelScale);
		float tx1 = tx0 + width / (tWidth * pixelScale);
		float ty1 = ty0 + height / (tHeight * pixelScale);

		float x1 = x0 - width;
		float y1 = y0 + height;

		if (mirrored) {
			x1 *= -1;
		}

		Matrix4f matrix = matrixStack.last().pose();
		Matrix3f normal = matrixStack.last().normal();
		// x0=0;
		// y0=0;
		// x1=8*pixelScale;
		// y1=80*pixelScale;
		//
		// tx0=0;
		// ty0=0;
		// tx1=x1*pixelScale;
		// ty1=y1*pixelScale;

		builder.vertex(matrix, x0, y1, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).uv(tx0, ty1).overlayCoords(overlay).uv2(packedLight).normal(normal, 0, 0, 1).endVertex();
		builder.vertex(matrix, x1, y1, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).uv(tx1, ty1).overlayCoords(overlay).uv2(packedLight).normal(normal, 0, 0, 1).endVertex();
		builder.vertex(matrix, x1, y0, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).uv(tx1, ty0).overlayCoords(overlay).uv2(packedLight).normal(normal, 0, 0, 1).endVertex();
		builder.vertex(matrix, x0, y0, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).uv(tx0, ty0).overlayCoords(overlay).uv2(packedLight).normal(normal, 0, 0, 1).endVertex();

	}
}
