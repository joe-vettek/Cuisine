package xueluoanping.cuisine.client.renderer.block;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;
import xueluoanping.cuisine.block.baseblock.SimpleHorizontalEntityBlock;
import xueluoanping.cuisine.blockentity.firepit.BarbecueRackBlockEntity;

public class BarbecueRackRenderer extends FirePitRenderer<BarbecueRackBlockEntity> {


    public BarbecueRackRenderer(BlockEntityRendererProvider.Context pContext) {
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

        if (!isLookingAt()) return;

        poseStack.pushPose();
        Lighting.setupForFlatItems();
        GlStateManager._disableCull();
        bufferIn = Minecraft.getInstance().renderBuffers().bufferSource();

        toGui(blockEntity.getBlockPos(),poseStack);

        // poseStack.translate(0.5f, 1.2f, 0);
        // poseStack.scale(0.25f, 0.25f, 0.25f);

        poseStack.translate(0.8, 5.75, 0);
        for (int i = 0; i < 3; ++i) {
            ItemStack stack = blockEntity.getInventory().getStackInSlot(i);
            if (!stack.isEmpty()) {
                poseStack.translate(0, -1.25, 0);
                BakedModel bakedmodel = Minecraft.getInstance().getItemRenderer().getModel(stack, blockEntity.getLevel(), Minecraft.getInstance().player, 0);
                Minecraft.getInstance().getItemRenderer().render(stack, ItemDisplayContext.GUI, false,
                        poseStack, bufferIn, combinedLight, OverlayTexture.NO_OVERLAY, bakedmodel);


                float r = 0.5f;
                float presice = 100;
                // float pef = (System.currentTimeMillis() % 5000) / 5000f;
                float pef = 1 - blockEntity.getRemainPercent(i);
                drawCircle(poseStack.last(), r, presice, pef);
            }
        }


        Lighting.setupFor3DItems();
        poseStack.popPose();
    }


}
