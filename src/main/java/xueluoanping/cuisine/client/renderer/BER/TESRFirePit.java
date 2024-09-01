package xueluoanping.cuisine.client.renderer.BER;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;


import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import xueluoanping.cuisine.blockentity.firepit.AbstractFirepitBlockEntity;
import xueluoanping.cuisine.client.gui.CuisineGUI;

import java.awt.*;

public class TESRFirePit<T extends AbstractFirepitBlockEntity> implements BlockEntityRenderer<T> {
    public TESRFirePit(BlockEntityRendererProvider.Context pContext) {
    }

    @Override
    public void render(T pBlockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLight, int combinedOverlay) {

        boolean need = false;
        if (Minecraft.getInstance().hitResult instanceof BlockHitResult blockHitResult) {
            need = blockHitResult.getBlockPos().compareTo(pBlockEntity.getBlockPos()) == 0;
        }
        // boolean need = Minecraft.getInstance().hitResult.getLocation().closerThan(Vec3.atLowerCornerOf(pBlockEntity.getBlockPos()).add(0.5,0.5,0.5), 0.7071f, 0.7071f);

        if (!need) return;

        poseStack.pushPose();
        Lighting.setupForFlatItems();
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
        bufferIn = Minecraft.getInstance().renderBuffers().bufferSource();
        VertexConsumer builder = bufferIn.getBuffer(RenderType.entitySmoothCutout(CuisineGUI.TEXTURE_ICONS));
        int colorRGB = Color.GRAY.getRGB();

        var entity = Minecraft.getInstance().cameraEntity;
        double d3 = entity.getX();
        double d4 = entity.getEyeY();
        double d5 = entity.getZ();

        // var vec=Minecraft.getInstance().cameraEntity.getLookAngle();

        double x = -d3 + pBlockEntity.getBlockPos().getX();
        double y = -d4 + pBlockEntity.getBlockPos().getY();
        double z = -d5 + pBlockEntity.getBlockPos().getZ();

        // GlStateManager.translate(dx, dy, dz);
        poseStack.translate(0.5, 0, 0.5);

        float scale = 0.8f / Mth.sqrt((float) (x * x + z * z));
        poseStack.translate(z * scale, 0.2, -x * scale);


        // Cuisine way
        // double dx = (x + 0.5);
        // double dz = (z + 0.5);
        // float yaw = (float) (Mth.atan2(dx, dz) * (180D / Math.PI));
        // poseStack.mulPose(new Quaternionf().rotateXYZ(0, yaw * Mth.DEG_TO_RAD, 0));
        // double distance = Mth.sqrt((float) (dx * dx + dz * dz));
        // float pitch = (float) (Mth.atan2(y - 1, distance) * (-180D / Math.PI));
        // poseStack.mulPose(new Quaternionf().rotateXYZ(pitch * Mth.DEG_TO_RAD, 0, 0));

        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        poseStack.mulPose(camera.rotation());

        // poseStack.translate(0.5f, 1.2f, 0);
        poseStack.scale(0.25f, 0.25f, 0.25f);

        // float f = (System.currentTimeMillis() % 5000) / 5000f;
        // float f= 1- pBlockEntity.getBlockState().getValue(BlockFirePit.LIGHT_LEVEL)/15f;
        float light_level = (pBlockEntity.getHeatHandler().getBurnTime() * 1.1f / pBlockEntity.getHeatHandler().getMaxBurnTime());
        // float f=  pBlockEntity.getHeatHandler().;
        float f = 1 - Mth.clamp(light_level, 0.2f, 0.8f);
        float nf = (1 - f);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, CuisineGUI.TEXTURE_ICONS);

        blitRect(poseStack, builder, combinedLight, OverlayTexture.NO_OVERLAY, 0, nf * 80 * 0.0625f, 0, nf * 80 * 0.0625f, 8 * 0.0625f, 80 * 0.0625f, 256, 256, false);
        blitRect(poseStack, builder, combinedLight, OverlayTexture.NO_OVERLAY, 0, 0, 8 * 0.0625f, 0, 8 * 0.0625f, nf * 80 * 0.0625f, 256, 256, false);

        // poseStack.translate(0.8,5.75,0);
        // for (int i = 0; i < 3; i++) {
        //     poseStack.translate(0,-1.25,0);
        //     BakedModel bakedmodel = Minecraft.getInstance().getItemRenderer().getModel(Items.APPLE.getDefaultInstance(), pBlockEntity.getLevel(), Minecraft.getInstance().player, 0);
        //     Minecraft.getInstance().getItemRenderer().render(Items.APPLE.getDefaultInstance(), ItemDisplayContext.GUI, false,
        //             poseStack, bufferIn, 15728880, OverlayTexture.NO_OVERLAY, bakedmodel);
        //
        // }

        // blitRect(poseStack, builder, combinedLight, OverlayTexture.NO_OVERLAY, 16* 0.0625f, 0, 8* 0.0625f, 0, 8* 0.0625f, 80* 0.0625f, 256, 256, false);
        Lighting.setupFor3DItems();
        poseStack.popPose();


    }

    public static void addVertex(VertexConsumer renderer, PoseStack stack, float x, float y, float z, float u, float v, int RGBA, float alpha, int brightness) {
        float red = ((RGBA >> 16) & 0xFF) / 255f;
        float green = ((RGBA >> 8) & 0xFF) / 255f;
        float blue = ((RGBA >> 0) & 0xFF) / 255f;
        renderer.addVertex(stack.last().pose(), x, y, z).setColor(red, green, blue, alpha).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(15728880)/*.lightmap(0, 240)*/.setNormal(stack.last(), 0, 1.0F, 0);
        int light1 = brightness & '\uffff';
        int light2 = brightness >> 16 & '\uffff';
//        renderer.addVertex(stack.last().pose(), x, y, z).color(red, green, blue, alpha).uv(u, v).uv2(light1, light2).overlayCoords(OverlayTexture.NO_OVERLAY).normal(stack.last().normal(), 0, 1.0F, 0);
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
        var normal = matrixStack.last();
        // x0=0;
        // y0=0;
        // x1=8*pixelScale;
        // y1=80*pixelScale;
        //
        // tx0=0;
        // ty0=0;
        // tx1=x1*pixelScale;
        // ty1=y1*pixelScale;
        // packedLight = LightTexture.FULL_BRIGHT;
        builder.addVertex(matrix, x0, y1, 0.0f).setColor(1.0f, 1.0f, 1.0f, 1.0f).setUv(tx0, ty1).setOverlay(overlay).setLight(packedLight).setNormal(normal, 0.0F, -1.0F, 0.0F);
        builder.addVertex(matrix, x1, y1, 0.0f).setColor(1.0f, 1.0f, 1.0f, 1.0f).setUv(tx1, ty1).setOverlay(overlay).setLight(packedLight).setNormal(normal, 0.0F, -1.0F, 0.0F);
        builder.addVertex(matrix, x1, y0, 0.0f).setColor(1.0f, 1.0f, 1.0f, 1.0f).setUv(tx1, ty0).setOverlay(overlay).setLight(packedLight).setNormal(normal, 0.0F, -1.0F, 0.0F);
        builder.addVertex(matrix, x0, y0, 0.0f).setColor(1.0f, 1.0f, 1.0f, 1.0f).setUv(tx0, ty0).setOverlay(overlay).setLight(packedLight).setNormal(normal, 0.0F, -1.0F, 0.0F);

    }
}
