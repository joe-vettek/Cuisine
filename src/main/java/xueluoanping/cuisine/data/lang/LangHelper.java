package xueluoanping.cuisine.data.lang;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.Main;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.Resource;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;
import xueluoanping.cuisine.register.ModConstant;

import java.io.IOException;
import java.io.InputStreamReader;

public abstract class LangHelper extends LanguageProvider {
	private final DataGenerator gen;
	private final ExistingFileHelper helper;

	public LangHelper(DataGenerator gen0, ExistingFileHelper helper, String modid, String locale) {
		super(gen0, modid, locale);
		gen = gen0;
		this.helper = helper;
	}

	public void addDebugKey(String key, String value) {
		add(ModConstant.DebugKey.getRealKey(key), value);
	}

	protected abstract void addTranslations();


	// Thanks khjxiaogu, inspire me the right way to deal with it
	public JsonObject loadLang(String str) {
		try {
			Resource rc = helper.getResource(new ResourceLocation("lang/" + str + ".json"),
					PackType.CLIENT_RESOURCES);
			JsonObject jo = JsonParser.parseReader(new InputStreamReader(rc.getInputStream(), "UTF-8")).getAsJsonObject();

			return jo;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new JsonObject();
	}


}
