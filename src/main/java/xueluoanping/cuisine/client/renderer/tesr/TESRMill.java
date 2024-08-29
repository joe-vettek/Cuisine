package xueluoanping.cuisine.client.renderer.tesr;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import xueluoanping.cuisine.blockentity.MillBlockEntity;
import xueluoanping.cuisine.register.BlockEntityRegister;

public class TESRMill implements BlockEntityRenderer<MillBlockEntity> {
    public TESRMill(BlockEntityRendererProvider.Context pContext) {
    }

    @Override
    public void render(MillBlockEntity millBlockEntity, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLight, int combinedOverlay) {
        int posLong = (int) millBlockEntity.getBlockPos().asLong();
        matrixStackIn.pushPose();
        matrixStackIn.translate(0.5, 0.5, 0.5);


//        matrixStackIn.translate(-0.5,0,-0.5);

        ItemStack stack = new ItemStack(BlockEntityRegister.mill.get());
        stack.getOrCreateTag().putFloat("CustomModelData", 1);
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.NONE, combinedLight, combinedOverlay, matrixStackIn, bufferIn, posLong);

        ItemStack stack2 = new ItemStack(BlockEntityRegister.mill.get());
        stack2.getOrCreateTag().putFloat("CustomModelData", 2);

        matrixStackIn.mulPose(new Quaternion(0, millBlockEntity.getProgress(), 0, true));
        Minecraft.getInstance().getItemRenderer().renderStatic(stack2, ItemTransforms.TransformType.NONE, combinedLight, combinedOverlay, matrixStackIn, bufferIn, posLong);
//        Minecraft.getInstance().getEntityRenderDispatcher().re

        matrixStackIn.popPose();

//        这个是为了砧板做准备的
//        matrixStackIn.pushPose();
//        matrixStackIn.translate(0.5, 0.05, 0.5);
//        matrixStackIn.scale((float) 0.6, (float) 0.1, (float) 0.6);
//        Minecraft.getInstance().getItemRenderer().renderStatic(new ItemStack(Blocks.CRIMSON_STEM), ItemTransforms.TransformType.NONE, combinedLight, combinedOverlay, matrixStackIn, bufferIn, posLong);
//        matrixStackIn.popPose();
    }
}
