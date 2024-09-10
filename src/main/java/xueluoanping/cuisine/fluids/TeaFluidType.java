package xueluoanping.cuisine.fluids;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.event.DataListenerClient;
import xueluoanping.cuisine.items.base.ItemHolder;
import xueluoanping.cuisine.register.FluidRegister;
import xueluoanping.cuisine.register.ModCapabilities;

public class TeaFluidType extends FluidType {

    private ResourceLocation STILL_TEXTURE;
    private ResourceLocation FLOWING_TEXTURE;
    private int colourTint = 0XFFFFFFFF;

    public TeaFluidType(Properties properties) {
        this(properties, FluidRegister.WATER_STILL_TEXTURE, FluidRegister.WATER_FLOW_TEXTURE);
    }

    public TeaFluidType(Properties properties, ResourceLocation stillTexture, ResourceLocation flowingTexture) {
        super(properties);
        this.STILL_TEXTURE = stillTexture;
        this.FLOWING_TEXTURE = flowingTexture;
    }


    public TeaFluidType color(int colourTint) {
        this.colourTint = colourTint;
        return this;
    }

    public static IClientFluidTypeExtensions getIClientFluidTypeExtensions(TeaFluidType teaFluidType) {
        return new TeaFluidTypeExtension(teaFluidType.STILL_TEXTURE, teaFluidType.FLOWING_TEXTURE, teaFluidType.colourTint);
    }

    public static IClientFluidTypeExtensions getCuisineIClientFluidTypeExtensions(TeaFluidType teaFluidType) {
        return new CuisineFluidType(teaFluidType.STILL_TEXTURE, teaFluidType.FLOWING_TEXTURE, teaFluidType.colourTint);
    }

    public TeaFluidType texture(String fluid) {
        this.STILL_TEXTURE = Cuisine.rl("block/" + fluid + "_still");
        this.FLOWING_TEXTURE = Cuisine.rl("block/" + fluid + "_flow");
        return this;
    }

    public static class TeaFluidTypeExtension implements IClientFluidTypeExtensions {
        private final ResourceLocation FLOWING_TEXTURE;
        private final ResourceLocation STILL_TEXTURE;
        private final int colourTint;

        public TeaFluidTypeExtension(ResourceLocation STILL_TEXTURE,
                                     ResourceLocation FLOWING_TEXTURE,
                                     int colourTint) {
            this.STILL_TEXTURE = STILL_TEXTURE;
            this.FLOWING_TEXTURE = FLOWING_TEXTURE;
            this.colourTint = colourTint;
        }

        @Override
        public ResourceLocation getStillTexture() {
            return this.STILL_TEXTURE;
        }

        @Override
        public ResourceLocation getFlowingTexture() {
            return this.FLOWING_TEXTURE;
        }


        @Override
        public int getTintColor() {
            // return colourTint > 0 ? colourTint : IClientFluidTypeExtensions.super.getTintColor();
            return colourTint;
        }

        @Override
        public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
            int color = this.getTintColor();
            var Red = ((float) (((color >> 16) & 255) / 255.0));
            var Green = ((float) (((color >> 8) & 255) / 255.0));
            var Blue = ((float) ((color & 255) / 255.0));
            return new Vector3f(Red, Green, Blue);

        }

        @Override
        public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
            // RenderSystem.setShaderFogShape(FogShape.CYLINDER);
            int color = this.getTintColor();
            var alpha = ((float) (((color >> 24) & 255) / 255.0));
            RenderSystem.setShaderFogStart(0.125F);
            RenderSystem.setShaderFogEnd(2.0F + 3.0F * (1 - alpha));
        }
    }


    public static class CuisineFluidType extends TeaFluidTypeExtension {

        public CuisineFluidType(ResourceLocation STILL_TEXTURE, ResourceLocation FLOWING_TEXTURE, int colourTint) {
            super(STILL_TEXTURE, FLOWING_TEXTURE, colourTint);
        }

        @Override
        public int getTintColor(FluidStack fluidStack) {
            var itemStack = fluidStack.getOrDefault(ModCapabilities.SIMPLE_ITEM, new ItemHolder(ItemStack.EMPTY)).stack();
            if (!itemStack.isEmpty()) {
                Item item = itemStack.getItem();
                return DataListenerClient.getFluidColorsMap().get(item).getRGB();
            }
            return super.getTintColor(fluidStack);
        }
    }

}
