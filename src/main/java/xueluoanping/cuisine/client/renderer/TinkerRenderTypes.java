package xueluoanping.cuisine.client.renderer;

import java.util.OptionalDouble;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;

import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;


import static xueluoanping.cuisine.Cuisine.MODID;

public class TinkerRenderTypes extends RenderType {
	public TinkerRenderTypes(String name, VertexFormat format, Mode mode, int bufferSize, boolean affectsCrumbling, boolean sort, Runnable setupState, Runnable clearState) {
		super(name, format, mode, bufferSize, affectsCrumbling, sort, setupState, clearState);
	}
	private static RenderType.CompositeState translucentState(RenderStateShard.ShaderStateShard p_173208_) {
		return RenderType.CompositeState.builder()
				.setLightmapState(LIGHTMAP)
				.setShaderState(p_173208_)
				.setTextureState(BLOCK_SHEET_MIPPED)
				.setTransparencyState(TRANSLUCENT_TRANSPARENCY)
				.setOutputState(TRANSLUCENT_TARGET)
				.setCullState(NO_CULL)
				.createCompositeState(true);
	}
	public static String resourceString(String res) {
		return String.format("%s:%s", MODID, res);
	}

	/**
	 * Render type for the error block that is seen through everything, mostly based on {@link RenderType#LINES}
	 */
	public static final RenderType ERROR_BLOCK = RenderType.create(
			resourceString("lines"), DefaultVertexFormat.POSITION_COLOR_NORMAL, Mode.LINES, 256, false, false,
			CompositeState.builder()
					.setShaderState(RENDERTYPE_LINES_SHADER)
					.setLineState(new LineStateShard(OptionalDouble.empty()))
					.setLayeringState(VIEW_OFFSET_Z_LAYERING)
					.setTransparencyState(TRANSLUCENT_TRANSPARENCY)
					.setOutputState(ITEM_ENTITY_TARGET)
					.setWriteMaskState(COLOR_DEPTH_WRITE)
					.setCullState(NO_CULL)
					.setDepthTestState(NO_DEPTH_TEST)
					.createCompositeState(false));

	public static final RenderType SMELTERY_FLUID = RenderType.create(
			resourceString("smeltery_fluid"),
			DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, Mode.QUADS, 256, true, true,
			CompositeState.builder()
					.setTextureState(BLOCK_SHEET)
					.setShaderState(RENDERTYPE_TRANSLUCENT_SHADER)
					.setLightmapState(LIGHTMAP)
					.setTextureState(BLOCK_SHEET_MIPPED)
					.setTransparencyState(TRANSLUCENT_TRANSPARENCY)
					.setOutputState(TRANSLUCENT_TARGET)
					.setCullState(NO_CULL)
					.createCompositeState(false));

//	public static final RenderType SOLID = create(
//			"solid",
//			DefaultVertexFormat.BLOCK,
//			VertexFormat.Mode.QUADS,
//			2097152,
//			true,
//			true,
//			RenderType.CompositeState.builder()
//					.setLightmapState(LIGHTMAP).
//					setShaderState(RENDERTYPE_SOLID_SHADER)
//					.setTextureState(BLOCK_SHEET_MIPPED)
//					.setOutputState(TRANSLUCENT_TARGET)
//					.setCullState(NO_CULL)
//					.createCompositeState(false));

	public static final RenderType SOLID = create("solid", DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 2097152, true, true, RenderType.CompositeState.builder().setLightmapState(LIGHTMAP).setShaderState(RENDERTYPE_SOLID_SHADER).setTextureState(BLOCK_SHEET_MIPPED).setCullState(NO_CULL).createCompositeState(false));


}
