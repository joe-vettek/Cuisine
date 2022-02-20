package xueluoanping.cuisine.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;

import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import xueluoanping.cuisine.block.BlockBasin;
import xueluoanping.cuisine.block.tile.BasinBlockEntity;

public class TESRBasin implements BlockEntityRenderer<BasinBlockEntity> {
	public TESRBasin(BlockEntityRendererProvider.Context pContext) {
	}

	public static Logger logger = LogManager.getLogger();

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
		ItemStack boardStack = basinBlockEntity.getStoredItem();
		int posLong = (int) basinBlockEntity.getBlockPos().asLong();
		Minecraft mc = Minecraft.getInstance();
		long gameTime = mc.level.getGameTime();
		double animationTime = (double) gameTime + (double) partialTicks;

		if (!boardStack.isEmpty()) {
			matrixStackIn.pushPose();

			ItemRenderer itemRenderer = Minecraft.getInstance()
					.getItemRenderer();
			boolean isBlockItem = itemRenderer.getModel(boardStack, basinBlockEntity.getLevel(), null, 0)
					.isGui3d();

//				renderItemCarved(poseStack, direction, boardStack);
			if (isBlockItem) {
				renderBlock(matrixStackIn, direction);
			} else {
				renderItemLayingDown(matrixStackIn, direction);
			}

			Minecraft.getInstance().getItemRenderer().renderStatic(boardStack, ItemTransforms.TransformType.FIXED, combinedLight, combinedOverlay, matrixStackIn, bufferIn, posLong);
			matrixStackIn.popPose();
		} else {
			// render the fluid
			matrixStackIn.pushPose();
//			renderBlock(matrixStackIn, direction);
			renderFluid(basinBlockEntity, matrixStackIn, bufferIn, combinedLight,animationTime);
			matrixStackIn.popPose();

		}
	}


	private void renderFluid(BasinBlockEntity basinBlockEntity, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLight,double animationTime) {
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
		Player player=mc.player;
		TextureAtlasSprite still = mc.getBlockRenderer().getBlockModelShaper().getTexture(fluidStackDown.getFluid().defaultFluidState().createLegacyBlock(), basinBlockEntity.getLevel(), basinBlockEntity.getBlockPos());
		RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
		int colorRGB = fluidStackDown.getFluid().getAttributes().getColor();

//			float height = (float) fluidStackDown.getAmount() / (float) fluidTankDown.getCapacity() * 0.75f;
		float height = (float) 0.3625 * fluidStackDown.getAmount() / BasinBlockEntity.Capacity;
//			float vHeight = (still.getV1() - still.getV0()) * (1f - (float) fluidStackDown.getAmount() / (float) fluidTankDown.getCapacity());
		float vHeight = (still.getV1() - still.getV0()) * (1f - (float) fluidStackDown.getAmount() / BasinBlockEntity.Capacity);
		GlStateManager._disableCull();
//		GlStateManager._depthMask(true);

//		VertexConsumer buffer = bufferIn.getBuffer(RenderType.text(still.atlas().location()));
		VertexConsumer buffer = bufferIn.getBuffer(TinkerRenderTypes.SOLID);
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

	public void renderItemCarved(PoseStack matrixStackIn, Direction direction, ItemStack itemStack) {
		// Center item above the cutting board
		matrixStackIn.translate(0.5D, 0.23D, 0.5D);

		// Rotate item to face the cutting board's front side
		float f = -direction.toYRot() + 180;
		matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(f));

		// Rotate item to be carved on the surface, A little less so for hoes and pickaxes.
		Item toolItem = itemStack.getItem();
		float poseAngle;
		if (toolItem instanceof PickaxeItem || toolItem instanceof HoeItem) {
			poseAngle = 225.0F;
		} else if (toolItem instanceof TridentItem) {
			poseAngle = 135.0F;
		} else {
			poseAngle = 180.0F;
		}
		matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(poseAngle));

		// Resize the item
		matrixStackIn.scale(0.6F, 0.6F, 0.6F);
	}
	//    @Override
//    public void render(TileBasin te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
//    {
//        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
//
//        Minecraft mc = Minecraft.getMinecraft();
//        if (y > mc.player.eyeHeight)
//        {
//            return;
//        }
//
//        FluidStack fluid = te.getFluidForRendering(partialTicks);
//        ItemStack item = te.stacks.getStackInSlot(0);
//        if (fluid == null && item.isEmpty())
//        {
//            return;
//        }
//
//        GlStateManager.pushMatrix();
//
//        if (Minecraft.isAmbientOcclusionEnabled())
//        {
//            GL11.glShadeModel(GL11.GL_SMOOTH);
//        }
//        else
//        {
//            GL11.glShadeModel(GL11.GL_FLAT);
//        }
//
//        GlStateManager.translate(x, y, z);
//
//        if (!item.isEmpty() && te.hasWorld())
//        {
//            GlStateManager.pushMatrix();
//
//            GlStateManager.translate(0.5, 0.0625, 0.5);
//            RenderItem renderItem = mc.getRenderItem();
//            IBakedModel bakedModel = renderItem.getItemModelWithOverrides(item, te.getWorld(), null);
//            if (bakedModel.isGui3d())
//            {
//                // Block
//                GlStateManager.scale(0.4, 0.4, 0.4);
//                GlStateManager.translate(0.2, 0, 0.2);
//            }
//            else
//            {
//                // Item
//                GlStateManager.scale(0.5, 0.5, 0.5);
//                GlStateManager.rotate(90, 1, 0, 0);
//            }
//            int max = item.getCount() == 1 ? 1 : (item.getCount() - 1) / (bakedModel.isGui3d() ? 16 : 8) + 1;
//            for (int i = 0; i < max; i++)
//            {
//                if (bakedModel.isGui3d())
//                {
//                    // Block
//                    double translation = i % 2 == 0 ? -0.4 : 0.4;
//                    GlStateManager.translate(translation, 0.2, translation);
//                    GlStateManager.rotate(70, 0, 1, 0);
//                }
//                else
//                {
//                    // Item
//                    double translation = i % 2 == 0 ? -0.1 : 0.1;
//                    GlStateManager.translate(translation, translation, -0.1);
//                    GlStateManager.rotate(70, 0, 0, 1);
//                }
//                renderItem.renderItem(item, ItemCameraTransforms.TransformType.NONE);
//            }
//            GlStateManager.popMatrix();
//        }
//
//        if (fluid != null)
//        {
//
//            RenderHelper.disableStandardItemLighting();
//            GlStateManager.enableBlend();
//            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
//            GlStateManager.pushMatrix();
//            mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
//            Tessellator tessellator = Tessellator.getInstance();
//            BufferBuilder buffer = tessellator.getBuffer();
//            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
//            TextureAtlasSprite still = mc.getTextureMapBlocks().getTextureExtry(fluid.getFluid().getStill(fluid).toString());
//            if (still == null)
//            {
//                still = mc.getTextureMapBlocks().getMissingSprite();
//            }
//
//            int brightness = mc.world.getCombinedLight(te.getPos(), fluid.getFluid().getLuminosity(fluid));
//            int lx = brightness >> 0x10 & 0xFFFF;
//            int ly = brightness & 0xFFFF;
//
//            int color = fluid.getFluid().getColor(fluid);
//            int r = color >> 16 & 0xFF;
//            int g = color >> 8 & 0xFF;
//            int b = color & 0xFF;
//            int a = color >> 24 & 0xFF;
//
//            double height = 0.0625 + 0.437 * ((double) fluid.amount / te.tank.getCapacity());
//
//            buffer.pos(0.0625, height, 0.0625).color(r, g, b, a).tex(still.getMinU(), still.getMinV()).lightmap(lx, ly).endVertex();
//            buffer.pos(0.0625, height, 0.9375).color(r, g, b, a).tex(still.getMinU(), still.getMaxV()).lightmap(lx, ly).endVertex();
//            buffer.pos(0.9375, height, 0.9375).color(r, g, b, a).tex(still.getMaxU(), still.getMaxV()).lightmap(lx, ly).endVertex();
//            buffer.pos(0.9375, height, 0.0625).color(r, g, b, a).tex(still.getMaxU(), still.getMinV()).lightmap(lx, ly).endVertex();
//
//            tessellator.draw();
//            GlStateManager.popMatrix();
//        }
//
//        GlStateManager.disableBlend();
//        GlStateManager.popMatrix();
//        RenderHelper.enableStandardItemLighting();
//    }

}
