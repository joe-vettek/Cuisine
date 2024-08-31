package xueluoanping.cuisine.client.renderer.tesr;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import xueluoanping.cuisine.block.BlockBasin;
import xueluoanping.cuisine.blockentity.BasinBlockEntity;


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
		// Cuisine.logger(BlockEntityRegister.getAllBasinBlock());
        if (!basinBlockEntity.hasLevel() || basinBlockEntity == null)
            return;


        Direction direction = basinBlockEntity.getBlockState().getValue(BlockBasin.FACING).getOpposite();
        ItemStack basinStack = basinBlockEntity.getStoredItem();
        int posLong = (int) basinBlockEntity.getBlockPos().asLong();

        if (!basinBlockEntity.hasNoFluid()) {
            // render the fluid
			matrixStackIn.pushPose();
            TESRBasic.renderFluid(basinBlockEntity, matrixStackIn, bufferIn, combinedLight, partialTicks);
            matrixStackIn.popPose();
        }
        if (!basinStack.isEmpty()) {
            matrixStackIn.pushPose();
            // Center item above the cutting board
			TESRBasinBase.drawBasinStack(basinStack,direction,basinBlockEntity.getLevel(),combinedLight, combinedOverlay, matrixStackIn, bufferIn, posLong);
            matrixStackIn.popPose();
        }
        matrixStackIn.pushPose();
        ItemStack stack = basinBlockEntity.getBlockState().getBlock().asItem().getDefaultInstance();
		TESRBasinBase.drawBasin(stack,combinedLight, combinedOverlay, matrixStackIn, bufferIn,basinBlockEntity.getLevel(), posLong);
        matrixStackIn.popPose();
    }


}
