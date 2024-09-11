package xueluoanping.cuisine.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;

import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import xueluoanping.cuisine.blockentity.ChoppingBoardBlockEntity;
import xueluoanping.cuisine.client.renderer.RenderTool;

public class ChoppingBoardRenderer implements BlockEntityRenderer<ChoppingBoardBlockEntity> {
    public ChoppingBoardRenderer(BlockEntityRendererProvider.Context pContext) {
    }

    @Override
    public void render(ChoppingBoardBlockEntity choppingBoardBlockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLight, int combinedOverlay) {

        if (!choppingBoardBlockEntity.hasLevel() || choppingBoardBlockEntity == null)
            return;
        var itemRenderer = Minecraft.getInstance().getItemRenderer();
        var level = choppingBoardBlockEntity.getLevel();
        int posLong = (int) choppingBoardBlockEntity.getBlockPos().asLong();
        poseStack.pushPose();
        ItemStack stack = Blocks.OAK_LOG.asItem().getDefaultInstance();
        // poseStack.translate(0.1f,0,0.1f);

        poseStack.translate(0.5, 0.25f*0.5f, 0.5);

        poseStack.pushPose();
        poseStack.scale(0.75f, 4 / 16f, 0.75f);
        itemRenderer
                .renderStatic(stack, ItemDisplayContext.NONE, combinedLight, combinedOverlay, poseStack, bufferIn, choppingBoardBlockEntity.getLevel(), posLong);
        poseStack.popPose();

        poseStack.pushPose();
        var itemstack = choppingBoardBlockEntity.getInventory().getStackInSlot(0);
        BakedModel bakedmodel = itemRenderer.getModel(itemstack, level, Minecraft.getInstance().player, 0);
        poseStack.translate(0, 0.25f*0.5f, 0);
        poseStack.scale(0.375F, 0.375F, 0.375F);
        poseStack.translate(0, bakedmodel.isGui3d() ? 4/16F  : 0, 0);
        if (!bakedmodel.isGui3d())
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
        itemRenderer.renderStatic(itemstack, ItemDisplayContext.FIXED, combinedLight, combinedOverlay, poseStack, bufferIn, level, 0);
        poseStack.popPose();

        poseStack.popPose();
    }


}
