package xueluoanping.cuisine.client.renderer;


import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.inventory.InventoryMenu;

public class MantleRenderTypes extends RenderType {
	public static final RenderType FLUID;
//	public static final VertexFormat BLOCK_WITH_OVERLAY;
//	public static final RenderType TRANSLUCENT_FULLBRIGHT;

	private MantleRenderTypes(String name, VertexFormat format, Mode mode, int bufferSize, boolean useDelegate, boolean needsSorting, Runnable setupTaskIn, Runnable clearTaskIn) {
		super(name, format, mode, bufferSize, useDelegate, needsSorting, setupTaskIn, clearTaskIn);
	}

	static {
		FLUID = create("mantle:block_render_type", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, Mode.QUADS, 256, true, true, CompositeState.builder().setTextureState(new TextureStateShard(InventoryMenu.BLOCK_ATLAS, false, false)).setLightmapState(LIGHTMAP).setShaderState(RENDERTYPE_TRANSLUCENT_SHADER).setLightmapState(LIGHTMAP).setTextureState(BLOCK_SHEET_MIPPED).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setOutputState(TRANSLUCENT_TARGET).createCompositeState(false));
//		BLOCK_WITH_OVERLAY = new VertexFormat(ImmutableMap.builder().put("Position", DefaultVertexFormat.ELEMENT_POSITION).put("Color", DefaultVertexFormat.ELEMENT_COLOR).put("UV0", DefaultVertexFormat.ELEMENT_UV0).put("UV1", DefaultVertexFormat.ELEMENT_UV1).put("UV2", DefaultVertexFormat.ELEMENT_UV2).put("Normal", DefaultVertexFormat.ELEMENT_NORMAL).put("Padding", DefaultVertexFormat.ELEMENT_PADDING).build());
//		TRANSLUCENT_FULLBRIGHT = create("mantle:translucent_fullbright", BLOCK_WITH_OVERLAY, Mode.QUADS, 256, false, false, CompositeState.builder().setShaderState(new ShaderStateShard(MantleShaders::getBlockFullBrightShader)).setLightmapState(new LightmapStateShard(false)).setOverlayState(OVERLAY).setTextureState(BLOCK_SHEET_MIPPED).setTransparencyState(TRANSLUCENT_TRANSPARENCY).createCompositeState(false));
	}
}
