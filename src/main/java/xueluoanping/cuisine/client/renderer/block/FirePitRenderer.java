package xueluoanping.cuisine.client.renderer.block;

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
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.BlockHitResult;
import org.joml.Matrix4f;
import xueluoanping.cuisine.blockentity.firepit.AbstractFirepitBlockEntity;
import xueluoanping.cuisine.client.gui.CuisineGUI;

public class FirePitRenderer<T extends AbstractFirepitBlockEntity> implements BlockEntityRenderer<T> {

    private boolean isLookingAt = false;

    public FirePitRenderer(BlockEntityRendererProvider.Context pContext) {
    }

    public boolean isLookingAt() {
        return isLookingAt && !Minecraft.getInstance().options.hideGui;
    }

    public void setLookingAt(boolean lookingAt) {
        isLookingAt = lookingAt;
    }


    @Override
    public void render(T pBlockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLight, int combinedOverlay) {

        boolean need = false;
        if (Minecraft.getInstance().hitResult instanceof BlockHitResult blockHitResult) {
            need = blockHitResult.getBlockPos().compareTo(pBlockEntity.getBlockPos()) == 0;
        }
        // boolean need = Minecraft.getInstance().hitResult.getLocation().closerThan(Vec3.atLowerCornerOf(pBlockEntity.getBlockPos()).add(0.5,0.5,0.5), 0.7071f, 0.7071f);
        setLookingAt(need);
        if (!isLookingAt()) return;

        poseStack.pushPose();

        toGui(pBlockEntity.getBlockPos(), poseStack);

        Lighting.setupForFlatItems();
        GlStateManager._disableCull();

        // float f = (System.currentTimeMillis() % 5000) / 5000f;
        // float f= 1- pBlockEntity.getBlockState().getValue(BlockFirePit.LIGHT_LEVEL)/15f;
        float light_level = (pBlockEntity.getHeatHandler().getBurnTime() * 1.1f / pBlockEntity.getHeatHandler().getMaxBurnTime());
        // float f=  pBlockEntity.getHeatHandler().;
        float f = 1 - Mth.clamp(light_level, 0.2f, 0.8f);
        float nf = (1 - f);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, CuisineGUI.TEXTURE_ICONS);
        bufferIn = Minecraft.getInstance().renderBuffers().bufferSource();
        VertexConsumer builder = bufferIn.getBuffer(RenderType.entitySmoothCutout(CuisineGUI.TEXTURE_ICONS));

        // 奇怪，倒过来了
        poseStack.scale(1, -1, 1);
        poseStack.translate(0, -5.2, 0);

        blitRect(poseStack, builder, combinedLight, OverlayTexture.NO_OVERLAY, 0, 0, 0, 0, 8, f*80, 256, 256, false);
        blitRect(poseStack, builder, combinedLight, OverlayTexture.NO_OVERLAY, 0,  f * 80, 8,  f * 80, 8, nf * 80, 256, 256, false);

        poseStack.translate(0, 0, 0.1);

        int heatLevel = pBlockEntity.getHeatHandler().getLevel();
        float burnTime = pBlockEntity.getHeatHandler().getBurnTime();

        if (burnTime > 1) {
            if (burnTime < 100) {
                float irconPos = 16 * (6 + 0);
                float yPos = (80 - burnTime / 10);
                blitRect(poseStack, builder, combinedLight, OverlayTexture.NO_OVERLAY, 4.5f, yPos, irconPos, 16 * 3, 16, 16, 256, 256, false);
            } else if (burnTime < 950) {
                float irconPos = 16 * (6 + 0);
                float yPos = 70;
                blitRect(poseStack, builder, combinedLight, OverlayTexture.NO_OVERLAY, 4.5f, yPos, irconPos, 16 * 3, 16, 16, 256, 256, false);
            } else if (burnTime < 1050) {
                {
                    float irconPos = 16 * (6 + 1);
                    float yPos = (80 - (burnTime - 950) / 10);
                    blitRect(poseStack, builder, combinedLight, OverlayTexture.NO_OVERLAY, 4.5f, yPos, irconPos, 16 * 3, 16, 16, 256, 256, false);
                }
                {
                    float irconPos = 16 * (6 + 0);
                    float yPos = 70 * (1050 - burnTime) / 100;
                    blitRect(poseStack, builder, combinedLight, OverlayTexture.NO_OVERLAY, 4.5f, yPos, irconPos, 16 * 3, 16, 16, 256, 256, false);
                }
            } else if (burnTime < 1950) {
                float irconPos = 16 * (6 + 1);
                float yPos = 70;
                blitRect(poseStack, builder, combinedLight, OverlayTexture.NO_OVERLAY, 4.5f, yPos, irconPos, 16 * 3, 16, 16, 256, 256, false);

                irconPos = 16 * (6 + 0);
                yPos = 0;
                blitRect(poseStack, builder, combinedLight, OverlayTexture.NO_OVERLAY, 4.5f, yPos, irconPos, 16 * 3, 16, 16, 256, 256, false);
            } else if (burnTime < 2050) {
                {
                    float irconPos = 16 * (6 + 2);
                    float yPos = (80 - (burnTime - 1950) / 10);
                    blitRect(poseStack, builder, combinedLight, OverlayTexture.NO_OVERLAY, 4.5f, yPos, irconPos, 16 * 3, 16, 16, 256, 256, false);
                }
                {
                    float irconPos = 16 * (6 + 1);
                    float yPos = 70 * (2050 - burnTime) / 100;
                    blitRect(poseStack, builder, combinedLight, OverlayTexture.NO_OVERLAY, 4.5f, yPos, irconPos, 16 * 3, 16, 16, 256, 256, false);
                }
            } else {
                float irconPos = 16 * (6 + 2);
                float yPos = 70;
                blitRect(poseStack, builder, combinedLight, OverlayTexture.NO_OVERLAY, 4.5f, yPos, irconPos, 16 * 3, 16, 16, 256, 256, false);

                irconPos = 16 * (6 + 1);
                yPos = 0;
                blitRect(poseStack, builder, combinedLight, OverlayTexture.NO_OVERLAY, 4.5f, yPos, irconPos, 16 * 3, 16, 16, 256, 256, false);

            }
        }


        Lighting.setupFor3DItems();
        poseStack.popPose();


    }

    protected void toGui(BlockPos blockPos, PoseStack poseStack) {
        var entity = Minecraft.getInstance().cameraEntity;
        double d3 = entity.getX();
        double d4 = entity.getEyeY();
        double d5 = entity.getZ();

        // var vec=Minecraft.getInstance().cameraEntity.getLookAngle();

        double x = -d3 + blockPos.getX();
        double y = -d4 + blockPos.getY();
        double z = -d5 + blockPos.getZ();

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

    /**
     * @param x0      渲染起点x
     * @param y0      渲染起点y
     * @param xt      图上起点y
     * @param yt      图上起点y
     * @param width   图上宽度
     * @param height  图上高度
     * @param tWidth  图片长度
     * @param tHeight 图片高度
     **/
    protected static void blitRect(PoseStack matrixStack, VertexConsumer builder, int packedLight, int overlay, float x0, float y0, float xt, float yt, float width, float height, int tWidth, int tHeight, boolean mirrored) {

        float pixelScale = 0.0625f;

        x0 = x0 * pixelScale;
        y0 = y0 * pixelScale;
        xt = xt * pixelScale;
        yt = yt * pixelScale;
        width = width * pixelScale;
        height = height * pixelScale;


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


    public void drawCircle(PoseStack.Pose last, float r, float presice, float pef) {
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        var buffer = Tesselator.getInstance().begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION);
        for (int index = 0; index < presice * pef; index++) {
            buffer.addVertex(last, 0, 0, 0).setColor(1, 1, 1, 1);
            float rad = Mth.DEG_TO_RAD * ((index / presice) * 360 + 90);
            buffer.addVertex(last, -Mth.cos(rad) * r, Mth.sin(rad) * r, 0).setColor(1, 1, 1, 1);
            float rad2 = Mth.DEG_TO_RAD * (((index + 1) / presice) * 360 + 90);
            buffer.addVertex(last, -Mth.cos(rad2) * r, Mth.sin(rad2) * r, 0).setColor(1, 1, 1, 1);
        }
        var meta = buffer.build();
        if (meta != null)
            BufferUploader.drawWithShader(meta);
        RenderSystem.disableBlend();
    }
}
