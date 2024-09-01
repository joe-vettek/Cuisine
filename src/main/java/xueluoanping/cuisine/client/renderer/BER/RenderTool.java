package xueluoanping.cuisine.client.renderer.BER;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import xueluoanping.cuisine.blockentity.AbstractBasinBlockEntity;
import xueluoanping.cuisine.blockentity.BasinBlockEntity;

import java.util.Optional;

import static net.minecraft.world.inventory.InventoryMenu.BLOCK_ATLAS;

public class RenderTool {
    public static void renderFluid(BlockEntity blockEntity, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLight, double partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        long gameTime = mc.level.getGameTime();
        double animationTime = (double) gameTime + (double) partialTicks;

        var iFluidHandler=blockEntity.getLevel().getCapability(Capabilities.FluidHandler.BLOCK, blockEntity.getBlockPos(), Direction.UP);
        if (iFluidHandler==null)return;
        FluidStack fluidStackDown = iFluidHandler.getFluidInTank(0);
        if (fluidStackDown.isEmpty()) return;

// 		Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        TextureAtlasSprite still = mc.getTextureAtlas(BLOCK_ATLAS).apply(IClientFluidTypeExtensions.of(fluidStackDown.getFluid()).getStillTexture());

        RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
        int colorRGB = IClientFluidTypeExtensions.of(fluidStackDown.getFluid()).getTintColor(fluidStackDown);

//			float height = (float) fluidStackDown.getAmount() / (float) fluidTankDown.getCapacity() * 0.75f;
        float height = (float) 0.3625 * fluidStackDown.getAmount() / BasinBlockEntity.Capacity;
//			float vHeight = (still.getV1() - still.getV0()) * (1f - (float) fluidStackDown.getAmount() / (float) fluidTankDown.getCapacity());
        float vHeight = (still.getV1() - still.getV0()) * (1f - (float) fluidStackDown.getAmount() / BasinBlockEntity.Capacity);
        GlStateManager._disableCull();
        GlStateManager._depthMask(true);

//		VertexConsumer buffer = bufferIn.getBuffer(RenderType.text(still.atlas().location()));
        VertexConsumer buffer = bufferIn.getBuffer(RenderType.translucent());
//		logger.info(mc.player.getLookAngle().toString() + "MCCUISINE");
//		logger.info(ItemBlockRenderTypes.getRenderType(blockEntity.getBlockState(), false) + "CUISINE"+blockEntity.getBlockState().getBlock().asItem().getRegistryName());

        addVertex(buffer, matrixStackIn, 0.0625f, 0.0625f + height, 0.0625f, still.getU0(), still.getV0(), colorRGB, 1.0f, combinedLight);
        addVertex(buffer, matrixStackIn, 0.0625f, 0.0625f + height, 0.9375f, still.getU1(), still.getV0(), colorRGB, 1.0f, combinedLight);
        addVertex(buffer, matrixStackIn, 0.9375f, 0.0625f + height, 0.9375f, still.getU1(), still.getV1(), colorRGB, 1.0f, combinedLight);
        addVertex(buffer, matrixStackIn, 0.9375f, 0.0625f + height, 0.0625f, still.getU0(), still.getV1(), colorRGB, 1.0f, combinedLight);


//		if (ItemBlockRenderTypes.getRenderType(blockEntity.getBlockState(), true) != RenderType.solid())
//         {
//             addVertex(buffer, matrixStackIn, 0.9375f, 0.0625f + height, 0.0625f, still.getU0(), still.getV0() + vHeight, colorRGB, 1.0f, combinedLight);
//             addVertex(buffer, matrixStackIn, 0.9375f, 0.0625f + height, 0.9375f, still.getU1(), still.getV0() + vHeight, colorRGB, 1.0f, combinedLight);
//             addVertex(buffer, matrixStackIn, 0.9375f, 0.0625f, 0.9375f, still.getU1(), still.getV1(), colorRGB, 1.0f, combinedLight);
//             addVertex(buffer, matrixStackIn, 0.9375f, 0.0625f, 0.0625f, still.getU0(), still.getV1(), colorRGB, 1.0f, combinedLight);
//
//             addVertex(buffer, matrixStackIn, 0.0625f, 0.0625f, 0.0625f, still.getU0(), still.getV0(), colorRGB, 1.0f, combinedLight);
//             addVertex(buffer, matrixStackIn, 0.0625f, 0.0625f, 0.9375f, still.getU1(), still.getV0(), colorRGB, 1.0f, combinedLight);
//             addVertex(buffer, matrixStackIn, 0.0625f, 0.0625f + height, 0.9375f, still.getU1(), still.getV1() - vHeight, colorRGB, 1.0f, combinedLight);
//             addVertex(buffer, matrixStackIn, 0.0625f, 0.0625f + height, 0.0625f, still.getU0(), still.getV1() - vHeight, colorRGB, 1.0f, combinedLight);
//
//             addVertex(buffer, matrixStackIn, 0.0625f, 0.0625f + height, 0.0625f, still.getU0(), still.getV0() + vHeight, colorRGB, 1.0f, combinedLight);
//             addVertex(buffer, matrixStackIn, 0.9375f, 0.0625f + height, 0.0625f, still.getU1(), still.getV0() + vHeight, colorRGB, 1.0f, combinedLight);
//             addVertex(buffer, matrixStackIn, 0.9375f, 0.0625f, 0.0625f, still.getU1(), still.getV1(), colorRGB, 1.0f, combinedLight);
//             addVertex(buffer, matrixStackIn, 0.0625f, 0.0625f, 0.0625f, still.getU0(), still.getV1(), colorRGB, 1.0f, combinedLight);
//
//
//             addVertex(buffer, matrixStackIn, 0.0625f, 0.0625f, 0.9375f, still.getU0(), still.getV0() + vHeight, colorRGB, 1.0f, combinedLight);
//             addVertex(buffer, matrixStackIn, 0.9375f, 0.0625f, 0.9375f, still.getU1(), still.getV0() + vHeight, colorRGB, 1.0f, combinedLight);
//             addVertex(buffer, matrixStackIn, 0.9375f, 0.0625f + height, 0.9375f, still.getU1(), still.getV1(), colorRGB, 1.0f, combinedLight);
//             addVertex(buffer, matrixStackIn, 0.0625f, 0.0625f + height, 0.9375f, still.getU0(), still.getV1(), colorRGB, 1.0f, combinedLight);
//
//
//             addVertex(buffer, matrixStackIn, 0.0625f, 0.0625f, 0.0625f, still.getU0(), still.getV0(), colorRGB, 1.0f, combinedLight);
//             addVertex(buffer, matrixStackIn, 0.0625f, 0.0625f, 0.9375f, still.getU1(), still.getV0(), colorRGB, 1.0f, combinedLight);
//             addVertex(buffer, matrixStackIn, 0.9375f, 0.0625f, 0.9375f, still.getU1(), still.getV1(), colorRGB, 1.0f, combinedLight);
//             addVertex(buffer, matrixStackIn, 0.9375f, 0.0625f, 0.0625f, still.getU0(), still.getV1(), colorRGB, 1.0f, combinedLight);
//
//         }
        GlStateManager._enableCull();
    }

    public static void addVertex(VertexConsumer renderer, PoseStack stack, float x, float y, float z, float u, float v, int RGBA, float alpha, int brightness) {
        float red = ((RGBA >> 16) & 0xFF) / 255f;
        float green = ((RGBA >> 8) & 0xFF) / 255f;
        float blue = ((RGBA >> 0) & 0xFF) / 255f;
        //		renderer.vertex(stack.last().pose(), x, y, z).color(red, green, blue, alpha).uv(u, v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880)/*.lightmap(0, 240)*/.normal(stack.last().normal(), 0, 1.0F, 0).endVertex();
        int light1 = brightness & '\uffff';
        int light2 = brightness >> 16 & '\uffff';
        renderer.addVertex(stack.last().pose(), x, y, z).setColor(red, green, blue, alpha).setUv(u, v).setUv2(light1, light2).setOverlay(OverlayTexture.NO_OVERLAY).setNormal(stack.last(), 0, 1.0F, 0);
    }

    public static void renderItemAir(PoseStack matrixStackIn, Direction direction) {
        // Center item above the cutting board
        //		matrixStackIn.translate(0.5D, 0.08D, 0.5D);

        // Rotate item to face the cutting board's front side
        float f = -direction.toYRot();
        matrixStackIn.mulPose(Axis.YP.rotationDegrees(f));
        // Rotate item flat on the cutting board. Use X and Y from now on
        matrixStackIn.mulPose(Axis.XP.rotationDegrees(90.0F));
        // Resize the item
        matrixStackIn.scale(0.6F, 0.6F, 0.6F);
    }

    public static void renderItemLayingDown(PoseStack matrixStackIn, Direction direction) {
        // Center item above the cutting board
        matrixStackIn.translate(0.5D, 0.08D, 0.5D);

        // Rotate item to face the cutting board's front side
        float f = -direction.toYRot();
        matrixStackIn.mulPose(Axis.YP.rotationDegrees(f));

        // Rotate item flat on the cutting board. Use X and Y from now on
        matrixStackIn.mulPose(Axis.XP.rotationDegrees(90.0F));

        // Resize the item
        matrixStackIn.scale(0.6F, 0.6F, 0.6F);
    }

    public static void renderBlock(PoseStack matrixStackIn, Direction direction) {
        // Center block above the cutting board
        matrixStackIn.translate(0.5D, 0.27D, 0.5D);

        // Rotate block to face the cutting board's front side
        float f = -direction.toYRot();
        matrixStackIn.mulPose(Axis.YP.rotationDegrees(f));

        // Resize the block
        matrixStackIn.scale(0.6F, 0.6F, 0.6F);
    }

    static void drawStackItems(ItemStack basinStack, Direction direction, Level level, int combinedLight, int combinedOverlay, PoseStack matrixStackIn, MultiBufferSource bufferIn, int posLong) {
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
            matrixStackIn.mulPose(Axis.YP.rotationDegrees(f));
            matrixStackIn.mulPose(Axis.XP.rotationDegrees(90.0F));
        }
        int max = basinStack.getCount() == 1 ? 1 : (basinStack.getCount() - 1) / (isBlockItem ? 16 : 8) + 1;
        for (int i = 0; i < max; i++) {
            if (isBlockItem) {
                // Block
                double translation = i % 2 == 0 ? -0.4 : 0.4;
                matrixStackIn.translate(translation, 0.2, translation);
                matrixStackIn.mulPose(Axis.YP.rotationDegrees(70));
            } else {
                // Item
                double translation = i % 2 == 0 ? -0.1 : 0.1;
                matrixStackIn.translate(translation, translation, -0.1);
                matrixStackIn.mulPose(Axis.ZP.rotationDegrees(70));
            }

            Minecraft.getInstance().getItemRenderer().renderStatic(basinStack, ItemDisplayContext.NONE, combinedLight, combinedOverlay, matrixStackIn, bufferIn,level, posLong);
        }
    }

    static void drawItemInWorld(ItemStack stack, int combinedLight, int combinedOverlay, PoseStack matrixStackIn, MultiBufferSource bufferIn, Level level, int posLong) {
        matrixStackIn.translate(0.5,0.5,0.5);
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.NONE, combinedLight, combinedOverlay, matrixStackIn, bufferIn,level, posLong);
    }
}
