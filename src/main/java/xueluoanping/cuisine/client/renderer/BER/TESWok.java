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
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import org.joml.Quaternionf;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.block.baseblock.SimpleHorizontalEntityBlock;
import xueluoanping.cuisine.blockentity.BasinBlockEntity;
import xueluoanping.cuisine.blockentity.firepit.BarbecueRackBlockEntity;
import xueluoanping.cuisine.blockentity.firepit.WokOnFirePitbBlockEntity;

import java.awt.*;

import static net.minecraft.world.inventory.InventoryMenu.BLOCK_ATLAS;

public class TESWok extends TESRFirePit<WokOnFirePitbBlockEntity> {


    public TESWok(BlockEntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public void render(WokOnFirePitbBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLight, int combinedOverlay) {
        super.render(blockEntity, partialTicks, poseStack, bufferIn, combinedLight, combinedOverlay);
        poseStack.pushPose();

        poseStack.translate(0.5, 0.36f, 0.5);
        poseStack.scale(0.5f, 0.5f, 0.5f);
        poseStack.scale(1, 0.45f, 1);
        poseStack.mulPose(new Quaternionf().rotateAxis(90 * Mth.DEG_TO_RAD, 1, 0, 0));
        int actionCycle = 2;
        int count = blockEntity.getInventory().getSlots();
        for (int i = 0; i < count; ++i) {
            ItemStack stack = blockEntity.getInventory().getStackInSlot(i);
            if (!stack.isEmpty()) {
                int seed = i * 439;
                double dz = -((seed % 56)) / 550D + 0.025;
                // poseStack.translate(-0.2*i, 0.5, 0.2*i);
                poseStack.scale(-1, -1, 1);
                double dx = ((seed % 100) - 50) / 250D;
                double dy = ((seed % 100) - 50) / 250D;
                poseStack.mulPose(new Quaternionf().rotateAxis(seed / 157f * Mth.DEG_TO_RAD, 0, 0, 1));
                poseStack.translate(dx, dy, dz);
                Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, combinedLight, combinedOverlay, poseStack, bufferIn, blockEntity.getLevel(), 0);

            }
            // poseStack.translate(-dx,-dy,0);


        }
        poseStack.popPose();


        poseStack.pushPose();
        Minecraft mc = Minecraft.getInstance();
        for (int i = 0; i < blockEntity.getSeasoningLiquids().getTanks(); i++) {
            FluidStack fluidStackDown = blockEntity.getSeasoningLiquids().getFluidInTank(i);
            if (fluidStackDown.isEmpty()) break;
            TextureAtlasSprite still = mc.getTextureAtlas(BLOCK_ATLAS).apply(IClientFluidTypeExtensions.of(fluidStackDown.getFluid()).getStillTexture());

            // RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
            int colorRGB = IClientFluidTypeExtensions.of(fluidStackDown.getFluid()).getTintColor(fluidStackDown);

            float height = (float) 0.006 * i+0.29f;
            VertexConsumer buffer = bufferIn.getBuffer(RenderType.translucent());

            RenderTool.addVertex(buffer, poseStack, 0.0625f, 0.0625f + height, 0.0625f, still.getU0(), still.getV0(), colorRGB, 1.0f, combinedLight);
            RenderTool.addVertex(buffer, poseStack, 0.0625f, 0.0625f + height, 0.9375f, still.getU1(), still.getV0(), colorRGB, 1.0f, combinedLight);
            RenderTool.addVertex(buffer, poseStack, 0.9375f, 0.0625f + height, 0.9375f, still.getU1(), still.getV1(), colorRGB, 1.0f, combinedLight);
            RenderTool.addVertex(buffer, poseStack, 0.9375f, 0.0625f + height, 0.0625f, still.getU0(), still.getV1(), colorRGB, 1.0f, combinedLight);

        }

        poseStack.popPose();


        if (!isLookingAt()) return;

        poseStack.pushPose();
        Lighting.setupForFlatItems();
        GlStateManager._disableCull();
        bufferIn = Minecraft.getInstance().renderBuffers().bufferSource();

        toGui(blockEntity.getBlockPos(), poseStack);

        // poseStack.translate(0.5f, 1.2f, 0);
        // poseStack.scale(0.25f, 0.25f, 0.25f);

        poseStack.translate(0.8, 5.75, 0);
        int use = 0;
        for (int i = 0; i < 4; ++i) {

            ItemStack stack = blockEntity.getInventory().getStackInSlot(i);
            if (!stack.isEmpty()) {
                use++;
                if (use == 4) {
                    poseStack.translate(1.25, 1.25 * 3, 0);
                }
                poseStack.translate(0, -1.25, 0);


                poseStack.pushPose();
                poseStack.scale(0.85f, 0.85f, 0.05f);
                BakedModel bakedmodel = Minecraft.getInstance().getItemRenderer().getModel(stack, blockEntity.getLevel(), Minecraft.getInstance().player, 0);
                Minecraft.getInstance().getItemRenderer().render(stack, ItemDisplayContext.GUI, false,
                        poseStack, bufferIn, combinedLight, OverlayTexture.NO_OVERLAY, bakedmodel);
                // poseStack.scale(1/0.72f,1/0.72f,1/0.72f);
                poseStack.popPose();

                float r = 0.5f;
                float presice = 100;
                float pef = (System.currentTimeMillis() % 5000) / 5000f;
                // float pef = 1-blockEntity.getRemainPercent(i);
                drawCircle(poseStack.last(), r, presice, pef);
            }
        }


        Lighting.setupFor3DItems();
        poseStack.popPose();


    }
}
