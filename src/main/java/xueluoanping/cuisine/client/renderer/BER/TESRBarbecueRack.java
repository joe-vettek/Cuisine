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
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import xueluoanping.cuisine.block.baseblock.SimpleHorizontalEntityBlock;
import xueluoanping.cuisine.blockentity.firepit.BarbecueRackBlockEntity;
import xueluoanping.cuisine.blockentity.firepit.FirePitBlockEntity;
import xueluoanping.cuisine.client.gui.CuisineGUI;

import java.awt.*;

public class TESRBarbecueRack extends TESRFirePit<BarbecueRackBlockEntity> {


    public TESRBarbecueRack(BlockEntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public void render(BarbecueRackBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLight, int combinedOverlay) {
        super.render(blockEntity, partialTicks, poseStack, bufferIn, combinedLight, combinedOverlay);
        poseStack.pushPose();

        poseStack.translate(0.5, 0.7, 0.5);
        poseStack.scale(0.5f, 0.5f, 0.5f);
        // GlStateManager.rotate(facing.getHorizontalAngle(), 0, 1, 0.1F);
        var facing = blockEntity.getBlockState().getValue(SimpleHorizontalEntityBlock.FACING);

        int ds = (Direction.Plane.HORIZONTAL.stream().toList().indexOf(facing) + 1) & 3;
        int angle = ds * 90;


        poseStack.mulPose(new Quaternionf().rotateAxis(angle * Mth.DEG_TO_RAD, 0, 1, 0.1f));
        // poseStack.mulPose(new Quaternionf().rotateXYZ(0, 90* Mth.DEG_TO_RAD, 9* Mth.DEG_TO_RAD));

        poseStack.translate(0.0, 0, -0.4);
        float rotate = 0.15F;
        VertexConsumer builder = bufferIn.getBuffer(RenderType.guiOverlay());

        for (int i = 0; i < 3; ++i) {
            ItemStack stack = blockEntity.getInventory().getStackInSlot(i);

            if (!stack.isEmpty()) {
                Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, combinedLight, combinedOverlay, poseStack, bufferIn, blockEntity.getLevel(), 0);
            }
            poseStack.translate(0, 0, 0.4);
            rotate *= -1.2;
            // poseStack.rotate(10, rotate * 1.5F, 0, rotate);
            poseStack.mulPose(new Quaternionf().rotateAxis(10 * Mth.DEG_TO_RAD, rotate * 1.5F * Mth.DEG_TO_RAD, 0, rotate * Mth.DEG_TO_RAD));

        }
        poseStack.popPose();

        boolean need = false;
        if (Minecraft.getInstance().hitResult instanceof BlockHitResult blockHitResult) {
            need = blockHitResult.getBlockPos().compareTo(blockEntity.getBlockPos()) == 0;
        }
        // boolean need = Minecraft.getInstance().hitResult.getLocation().closerThan(Vec3.atLowerCornerOf(pBlockEntity.getBlockPos()).add(0.5,0.5,0.5), 0.7071f, 0.7071f);

        if (!need) return;

        poseStack.pushPose();
        Lighting.setupForFlatItems();
        GlStateManager._disableCull();
        bufferIn = Minecraft.getInstance().renderBuffers().bufferSource();
        builder = bufferIn.getBuffer(RenderType.guiOverlay());
        int colorRGB = Color.GRAY.getRGB();

        var entity = Minecraft.getInstance().cameraEntity;
        double d3 = entity.getX();
        double d4 = entity.getEyeY();
        double d5 = entity.getZ();

        // var vec=Minecraft.getInstance().cameraEntity.getLookAngle();

        double x = -d3 + blockEntity.getBlockPos().getX();
        double y = -d4 + blockEntity.getBlockPos().getY();
        double z = -d5 + blockEntity.getBlockPos().getZ();

        // GlStateManager.translate(dx, dy, dz);
        poseStack.translate(0.5, 0, 0.5);

        float scale = 0.8f / Mth.sqrt((float) (x * x + z * z));
        poseStack.translate(z * scale, 0.2, -x * scale);

        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        poseStack.mulPose(camera.rotation());

        // poseStack.translate(0.5f, 1.2f, 0);
        poseStack.scale(0.25f, 0.25f, 0.25f);

        poseStack.translate(0.8, 5.75, 0);
        for (int i = 0; i < 3; ++i) {
            ItemStack stack = blockEntity.getInventory().getStackInSlot(i);
            if (!stack.isEmpty()) {
                poseStack.translate(0, -1.25, 0);
                BakedModel bakedmodel = Minecraft.getInstance().getItemRenderer().getModel(stack, blockEntity.getLevel(), Minecraft.getInstance().player, 0);
                Minecraft.getInstance().getItemRenderer().render(stack, ItemDisplayContext.GUI, false,
                        poseStack, bufferIn, combinedLight, OverlayTexture.NO_OVERLAY, bakedmodel);
                // drawCircle(poseStack.last(), 0, 0, 5, 16, 0XFFFFFFFF);

                RenderSystem.enableBlend();
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                var buffer = Tesselator.getInstance().begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION);
                float r = 0.5f;
                float presice = 100;
                // float pef = (System.currentTimeMillis() % 5000) / 5000f;
                float pef = 1-blockEntity.getRemainPercent(i);
                for (int index = 0; index < presice * pef; index++) {
                    buffer.addVertex(poseStack.last(), 0, 0, 0).setColor(1, 1, 1, 1);
                    float rad = Mth.DEG_TO_RAD * ((index / presice) * 360 + 90);
                    buffer.addVertex(poseStack.last(), -Mth.cos(rad) * r, Mth.sin(rad) * r, 0).setColor(1, 1, 1, 1);
                    float rad2 = Mth.DEG_TO_RAD * (((index + 1) / presice) * 360 + 90);
                    buffer.addVertex(poseStack.last(), -Mth.cos(rad2) * r, Mth.sin(rad2) * r, 0).setColor(1, 1, 1, 1);
                }

                var meta = buffer.build();
                if (meta != null)
                    BufferUploader.drawWithShader(meta);
                RenderSystem.disableBlend();
            }
        }


        Lighting.setupFor3DItems();
        poseStack.popPose();
    }
}
