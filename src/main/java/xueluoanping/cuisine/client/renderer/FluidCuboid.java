package xueluoanping.cuisine.client.renderer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.math.Vector3f;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.core.Direction;
import net.minecraft.util.GsonHelper;

import xueluoanping.cuisine.util.JsonHelper;
import xueluoanping.cuisine.util.ModelHelper;


public class FluidCuboid {
	protected static final Map<Direction, FluidFace> DEFAULT_FACES = new EnumMap(Direction.class);
	private final Vector3f from;
	private final Vector3f to;
	private final Map<Direction, FluidFace> faces;
	@Nullable
	private Vector3f fromScaled;
	@Nullable
	private Vector3f toScaled;

	public FluidCuboid(Vector3f from, Vector3f to, Map<Direction, FluidFace> faces) {
		this.from = from;
		this.to = to;
		this.faces = faces;
	}

	@Nullable
	public FluidFace getFace(Direction face) {
		return (FluidFace)this.faces.get(face);
	}

	public Vector3f getFromScaled() {
		if (this.fromScaled == null) {
			this.fromScaled = this.from.copy();
			this.fromScaled.mul(0.0625F);
		}

		return this.fromScaled;
	}

	public Vector3f getToScaled() {
		if (this.toScaled == null) {
			this.toScaled = this.to.copy();
			this.toScaled.mul(0.0625F);
		}

		return this.toScaled;
	}

	public static FluidCuboid fromJson(JsonObject json) {
		Vector3f from = ModelHelper.arrayToVector(json, "from");
		Vector3f to = ModelHelper.arrayToVector(json, "to");
		Map<Direction, FluidFace> faces = getFaces(json);
		return new FluidCuboid(from, to, faces);
	}

	public static List<FluidCuboid> listFromJson(JsonObject parent, String key) {
		JsonElement json = parent.get(key);
		if (json.isJsonObject()) {
			return Collections.singletonList(fromJson(json.getAsJsonObject()));
		} else if (json.isJsonArray()) {
			return JsonHelper.parseList(json.getAsJsonArray(), key, FluidCuboid::fromJson);
		} else {
			throw new JsonSyntaxException("Invalid fluid '" + key + "', must be an array or an object");
		}
	}

	protected static Map<Direction, FluidFace> getFaces(JsonObject json) {
		if (!json.has("faces")) {
			return DEFAULT_FACES;
		} else {
			Map<Direction, FluidFace> faces = new EnumMap(Direction.class);
			JsonObject object = GsonHelper.getAsJsonObject(json, "faces");
			Iterator var3 = object.entrySet().iterator();

			while(var3.hasNext()) {
				Entry<String, JsonElement> entry = (Entry)var3.next();
				String name = (String)entry.getKey();
				Direction dir = Direction.byName(name);
				if (dir == null) {
					throw new JsonSyntaxException("Unknown face '" + name + "'");
				}

				JsonObject face = GsonHelper.convertToJsonObject((JsonElement)entry.getValue(), name);
				boolean flowing = GsonHelper.getAsBoolean(face, "flowing", false);
				int rotation = ModelHelper.getRotation(face, "rotation");
				faces.put(dir, new FluidFace(flowing, rotation));
			}

			return faces;
		}
	}

	public Vector3f getFrom() {
		return this.from;
	}

	public Vector3f getTo() {
		return this.to;
	}

	public Map<Direction, FluidFace> getFaces() {
		return this.faces;
	}

	static {
		Direction[] var0 = Direction.values();
		int var1 = var0.length;

		for(int var2 = 0; var2 < var1; ++var2) {
			Direction direction = var0[var2];
			DEFAULT_FACES.put(direction, FluidFace.NORMAL);
		}

	}

	public static record FluidFace(boolean isFlowing, int rotation) {
		public static final FluidFace NORMAL = new FluidFace(false, 0);

		public FluidFace(boolean isFlowing, int rotation) {
			this.isFlowing = isFlowing;
			this.rotation = rotation;
		}

		public boolean isFlowing() {
			return this.isFlowing;
		}

		public int rotation() {
			return this.rotation;
		}
	}
}

