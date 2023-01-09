package xueluoanping.cuisine.data.lang;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import xueluoanping.cuisine.register.ModConstant;

public abstract class LangHelper extends LanguageProvider {
    public LangHelper(DataGenerator gen, String modid, String locale) {
        super(gen, modid, locale);
    }

    public void addDebugKey(String key, String value) {
        add(ModConstant.DebugKey.getRealKey(key), value);
    }

    protected abstract void addTranslations();
}
