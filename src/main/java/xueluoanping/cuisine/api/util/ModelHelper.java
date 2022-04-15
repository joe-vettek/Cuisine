package xueluoanping.cuisine.api.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import com.mojang.blaze3d.vertex.VertexFormatElement.Usage;
import com.mojang.math.Vector3f;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.pipeline.BakedQuadBuilder;
import net.minecraftforge.client.model.pipeline.VertexTransformer;

public class ModelHelper {
	private static final Map<Block, ResourceLocation> TEXTURE_NAME_CACHE = new ConcurrentHashMap();
	public static final ResourceManagerReloadListener LISTENER = (manager) -> {
		TEXTURE_NAME_CACHE.clear();
	};



	private static ResourceLocation getParticleTextureInternal(Block block) {
		return Minecraft.getInstance().getModelManager().getBlockModelShaper().getBlockModel(block.defaultBlockState()).getParticleIcon().getName();
	}

	public static ResourceLocation getParticleTexture(Block block) {
		return (ResourceLocation)TEXTURE_NAME_CACHE.computeIfAbsent(block, ModelHelper::getParticleTextureInternal);
	}

	public static <T> T arrayToObject(JsonObject json, String name, int size, Function<float[], T> mapper) {
		JsonArray array = GsonHelper.getAsJsonArray(json, name);
		if (array.size() != size) {
			throw new JsonParseException("Expected " + size + " " + name + " values, found: " + array.size());
		} else {
			float[] vec = new float[size];

			for(int i = 0; i < size; ++i) {
				vec[i] = GsonHelper.convertToFloat(array.get(i), name + "[" + i + "]");
			}

			return mapper.apply(vec);
		}
	}

	public static Vector3f arrayToVector(JsonObject json, String name) {
		return (Vector3f)arrayToObject(json, name, 3, (arr) -> {
			return new Vector3f(arr[0], arr[1], arr[2]);
		});
	}

	public static int getRotation(JsonObject json, String key) {
		int i = GsonHelper.getAsInt(json, key, 0);
		if (i >= 0 && i % 90 == 0 && i / 90 <= 3) {
			return i;
		} else {
			throw new JsonParseException("Invalid '" + key + "' " + i + " found, only 0/90/180/270 allowed");
		}
	}

	public static BakedQuad colorQuad(int color, BakedQuad quad) {
		ColorTransformer transformer = new ColorTransformer(color, quad);
		quad.pipe(transformer);
		return transformer.build();
	}

	private ModelHelper() {
	}

	private static class ColorTransformer extends VertexTransformer {
		private final float r;
		private final float g;
		private final float b;
		private final float a;

		public ColorTransformer(int color, BakedQuad quad) {
			super(new BakedQuadBuilder(quad.getSprite()));
			int a = color >> 24;
			if (a == 0) {
				a = 255;
			}

			int r = color >> 16 & 255;
			int g = color >> 8 & 255;
			int b = color >> 0 & 255;
			this.r = (float)r / 255.0F;
			this.g = (float)g / 255.0F;
			this.b = (float)b / 255.0F;
			this.a = (float)a / 255.0F;
		}

		public void put(int element, float... data) {
			Usage usage = ((VertexFormatElement)this.parent.getVertexFormat().getElements().get(element)).getUsage();
			if (usage == Usage.COLOR && data.length >= 4) {
				data[0] = this.r;
				data[1] = this.g;
				data[2] = this.b;
				data[3] = this.a;
			}

			super.put(element, data);
		}

		public BakedQuad build() {
			return ((BakedQuadBuilder)this.parent).build();
		}
	}
}

