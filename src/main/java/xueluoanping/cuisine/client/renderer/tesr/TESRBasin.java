package xueluoanping.cuisine.client.renderer.tesr;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import xueluoanping.cuisine.block.BlockBasin;
import xueluoanping.cuisine.block.entity.BasinBlockEntity;
import xueluoanping.cuisine.register.BlockEntityRegister;

import static net.minecraft.world.inventory.InventoryMenu.BLOCK_ATLAS;


public class TESRBasin implements BlockEntityRenderer<BasinBlockEntity> {
    public TESRBasin(BlockEntityRendererProvider.Context pContext) {
    }

    @Override
    public void render(BasinBlockEntity basinBlockEntity, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLight, int combinedOverlay) {
//		BlockDefinition def = BlockDefinition.fromBlock(basinBlockEntity.getBlockState(), basinBlockEntity, basinBlockEntity.getLevel(), basinBlockEntity.getBlockPos());
//		if (def != null)
//			basinBlockEntity.setTexture("particle", def);
//		Minecraft mc = Minecraft.getInstance();
//
//
//		logger.info(mc.player.getLookAngle().toString() + "MCCUISINE");
        //Only render in the world
        if (!basinBlockEntity.hasLevel() || basinBlockEntity == null)
            return;


        Direction direction = basinBlockEntity.getBlockState().getValue(BlockBasin.FACING).getOpposite();
        ItemStack basinStack = basinBlockEntity.getStoredItem();
        int posLong = (int) basinBlockEntity.getBlockPos().asLong();
        Minecraft mc = Minecraft.getInstance();
        long gameTime = mc.level.getGameTime();
        double animationTime = (double) gameTime + (double) partialTicks;
        if (!basinBlockEntity.hasNoFluid()) {
            // render the fluid
            matrixStackIn.pushPose();
            renderFluid(basinBlockEntity, matrixStackIn, bufferIn, combinedLight, animationTime);
            matrixStackIn.popPose();

        }
        if (!basinStack.isEmpty()) {
            matrixStackIn.pushPose();
            // Center item above the cutting board
            matrixStackIn.translate(0.5D, 0.12D, 0.5D);
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            boolean isBlockItem = itemRenderer.getModel(basinStack, basinBlockEntity.getLevel(), null, 0).isGui3d();
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
            matrixStackIn.popPose();
        }




        matrixStackIn.pushPose();
        matrixStackIn.translate(0.5,0.5,0.5);
        ItemStack stack = BlockEntityRegister.basin.get().getCloneItemStack(null,null,basinBlockEntity.getLevel(),basinBlockEntity.getBlockPos(),null);

        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.NONE, combinedLight, combinedOverlay, matrixStackIn, bufferIn, posLong);
        matrixStackIn.popPose();
    }



    private void renderFluid(BasinBlockEntity basinBlockEntity, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLight, double animationTime) {
        FluidStack fluidStackDown = null;
        final FluidStack[] fluidStack = new FluidStack[1];
        basinBlockEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, Direction.UP)
                .ifPresent(handler -> {
                    fluidStack[0] = handler.getFluidInTank(0);
                });
        if (fluidStack[0] == null || fluidStack[0].getFluid() == Fluids.EMPTY) return;
        else fluidStackDown = fluidStack[0];
//		logger.info(MODID + fluidStack[0].getFluid().getRegistryName());
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        TextureAtlasSprite still = mc.getTextureAtlas(BLOCK_ATLAS).apply(fluidStackDown.getFluid().getAttributes().getStillTexture());

        RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
        int colorRGB = fluidStackDown.getFluid().getAttributes().getColor(fluidStackDown);

//			float height = (float) fluidStackDown.getAmount() / (float) fluidTankDown.getCapacity() * 0.75f;
        float height = (float) 0.3625 * fluidStackDown.getAmount() / BasinBlockEntity.Capacity;
//			float vHeight = (still.getV1() - still.getV0()) * (1f - (float) fluidStackDown.getAmount() / (float) fluidTankDown.getCapacity());
        float vHeight = (still.getV1() - still.getV0()) * (1f - (float) fluidStackDown.getAmount() / BasinBlockEntity.Capacity);
        GlStateManager._disableCull();
		GlStateManager._depthMask(true);

//		VertexConsumer buffer = bufferIn.getBuffer(RenderType.text(still.atlas().location()));
        VertexConsumer buffer = bufferIn.getBuffer(RenderType.translucent());
//		logger.info(mc.player.getLookAngle().toString() + "MCCUISINE");
//		logger.info(ItemBlockRenderTypes.getRenderType(basinBlockEntity.getBlockState(), false) + "CUISINE"+basinBlockEntity.getBlockState().getBlock().asItem().getRegistryName());

        addVertex(buffer, matrixStackIn, 0.0625f, 0.0625f + height, 0.0625f, still.getU0(), still.getV0(), colorRGB, 1.0f, combinedLight);
        addVertex(buffer, matrixStackIn, 0.0625f, 0.0625f + height, 0.9375f, still.getU1(), still.getV0(), colorRGB, 1.0f, combinedLight);
        addVertex(buffer, matrixStackIn, 0.9375f, 0.0625f + height, 0.9375f, still.getU1(), still.getV1(), colorRGB, 1.0f, combinedLight);
        addVertex(buffer, matrixStackIn, 0.9375f, 0.0625f + height, 0.0625f, still.getU0(), still.getV1(), colorRGB, 1.0f, combinedLight);


//		if (ItemBlockRenderTypes.getRenderType(basinBlockEntity.getBlockState(), true) != RenderType.solid())
        {
            addVertex(buffer, matrixStackIn, 0.9375f, 0.0625f + height, 0.0625f, still.getU0(), still.getV0() + vHeight, colorRGB, 1.0f, combinedLight);
            addVertex(buffer, matrixStackIn, 0.9375f, 0.0625f + height, 0.9375f, still.getU1(), still.getV0() + vHeight, colorRGB, 1.0f, combinedLight);
            addVertex(buffer, matrixStackIn, 0.9375f, 0.0625f, 0.9375f, still.getU1(), still.getV1(), colorRGB, 1.0f, combinedLight);
            addVertex(buffer, matrixStackIn, 0.9375f, 0.0625f, 0.0625f, still.getU0(), still.getV1(), colorRGB, 1.0f, combinedLight);

            addVertex(buffer, matrixStackIn, 0.0625f, 0.0625f, 0.0625f, still.getU0(), still.getV0(), colorRGB, 1.0f, combinedLight);
            addVertex(buffer, matrixStackIn, 0.0625f, 0.0625f, 0.9375f, still.getU1(), still.getV0(), colorRGB, 1.0f, combinedLight);
            addVertex(buffer, matrixStackIn, 0.0625f, 0.0625f + height, 0.9375f, still.getU1(), still.getV1() - vHeight, colorRGB, 1.0f, combinedLight);
            addVertex(buffer, matrixStackIn, 0.0625f, 0.0625f + height, 0.0625f, still.getU0(), still.getV1() - vHeight, colorRGB, 1.0f, combinedLight);

            addVertex(buffer, matrixStackIn, 0.0625f, 0.0625f + height, 0.0625f, still.getU0(), still.getV0() + vHeight, colorRGB, 1.0f, combinedLight);
            addVertex(buffer, matrixStackIn, 0.9375f, 0.0625f + height, 0.0625f, still.getU1(), still.getV0() + vHeight, colorRGB, 1.0f, combinedLight);
            addVertex(buffer, matrixStackIn, 0.9375f, 0.0625f, 0.0625f, still.getU1(), still.getV1(), colorRGB, 1.0f, combinedLight);
            addVertex(buffer, matrixStackIn, 0.0625f, 0.0625f, 0.0625f, still.getU0(), still.getV1(), colorRGB, 1.0f, combinedLight);


            addVertex(buffer, matrixStackIn, 0.0625f, 0.0625f, 0.9375f, still.getU0(), still.getV0() + vHeight, colorRGB, 1.0f, combinedLight);
            addVertex(buffer, matrixStackIn, 0.9375f, 0.0625f, 0.9375f, still.getU1(), still.getV0() + vHeight, colorRGB, 1.0f, combinedLight);
            addVertex(buffer, matrixStackIn, 0.9375f, 0.0625f + height, 0.9375f, still.getU1(), still.getV1(), colorRGB, 1.0f, combinedLight);
            addVertex(buffer, matrixStackIn, 0.0625f, 0.0625f + height, 0.9375f, still.getU0(), still.getV1(), colorRGB, 1.0f, combinedLight);


            addVertex(buffer, matrixStackIn, 0.0625f, 0.0625f, 0.0625f, still.getU0(), still.getV0(), colorRGB, 1.0f, combinedLight);
            addVertex(buffer, matrixStackIn, 0.0625f, 0.0625f, 0.9375f, still.getU1(), still.getV0(), colorRGB, 1.0f, combinedLight);
            addVertex(buffer, matrixStackIn, 0.9375f, 0.0625f, 0.9375f, still.getU1(), still.getV1(), colorRGB, 1.0f, combinedLight);
            addVertex(buffer, matrixStackIn, 0.9375f, 0.0625f, 0.0625f, still.getU0(), still.getV1(), colorRGB, 1.0f, combinedLight);

        }
        GlStateManager._enableCull();

    }

    public static void addVertex(VertexConsumer renderer, PoseStack stack, float x, float y, float z, float u, float v, int RGBA, float alpha, int brightness) {
        float red = ((RGBA >> 16) & 0xFF) / 255f;
        float green = ((RGBA >> 8) & 0xFF) / 255f;
        float blue = ((RGBA >> 0) & 0xFF) / 255f;
//		renderer.vertex(stack.last().pose(), x, y, z).color(red, green, blue, alpha).uv(u, v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880)/*.lightmap(0, 240)*/.normal(stack.last().normal(), 0, 1.0F, 0).endVertex();
        int light1 = brightness & '\uffff';
        int light2 = brightness >> 16 & '\uffff';
        renderer.vertex(stack.last().pose(), x, y, z).color(red, green, blue, alpha).uv(u, v).uv2(light1, light2).overlayCoords(OverlayTexture.NO_OVERLAY).normal(stack.last().normal(), 0, 1.0F, 0).endVertex();
    }

    public void renderItemAir(PoseStack matrixStackIn, Direction direction) {
        // Center item above the cutting board
//		matrixStackIn.translate(0.5D, 0.08D, 0.5D);

        // Rotate item to face the cutting board's front side
        float f = -direction.toYRot();
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(f));
        // Rotate item flat on the cutting board. Use X and Y from now on
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(90.0F));
        // Resize the item
        matrixStackIn.scale(0.6F, 0.6F, 0.6F);
    }

    public void renderItemLayingDown(PoseStack matrixStackIn, Direction direction) {
        // Center item above the cutting board
        matrixStackIn.translate(0.5D, 0.08D, 0.5D);

        // Rotate item to face the cutting board's front side
        float f = -direction.toYRot();
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(f));

        // Rotate item flat on the cutting board. Use X and Y from now on
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(90.0F));

        // Resize the item
        matrixStackIn.scale(0.6F, 0.6F, 0.6F);
    }

    public void renderBlock(PoseStack matrixStackIn, Direction direction) {
        // Center block above the cutting board
        matrixStackIn.translate(0.5D, 0.27D, 0.5D);

        // Rotate block to face the cutting board's front side
        float f = -direction.toYRot();
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(f));

        // Resize the block
        matrixStackIn.scale(0.6F, 0.6F, 0.6F);
    }


}
