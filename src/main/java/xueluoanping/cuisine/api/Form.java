package xueluoanping.cuisine.api;


import com.google.gson.annotations.SerializedName;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum Form implements StringRepresentable
{
    @SerializedName("full")
    FULL, // 完整
    @SerializedName("cubed")
    CUBED, // 块
    @SerializedName("sliced")
    SLICED, //片
    @SerializedName("shredded")
    SHREDDED, //丝
    @SerializedName("diced")
    DICED, //丁
    @SerializedName("minced")
    MINCED, //碎
    @SerializedName("paste")
    PASTE, //酱
    @SerializedName("juice")
    JUICE; //汁

    public static final EnumSet<Form> ALL_FORMS = EnumSet.complementOf(EnumSet.of(Form.FULL, Form.JUICE));
    public static final EnumSet<Form> ALL_FORMS_INCLUDING_JUICE = EnumSet.complementOf(EnumSet.of(Form.FULL));
    public static final EnumSet<Form> JUICE_ONLY = EnumSet.of(Form.JUICE);

    // Remember to adjust the initial size when there are new forms
    private static final Map<String, Form> LOOKUP_TABLE = new HashMap<>(Form.values().length);

    static
    {
        for (Form form : Form.values())
        {

            LOOKUP_TABLE.put(form.name(), form);
        }
    }

    /**
     * Exception-free version of {@link Enum#valueOf(Class, String)}.
     * <p>
     * Return value can be null, and care must be taken for processing its return value.
     * </p>
     * @param name Name of enum constant
     * @return Corresponding enum constant, or null if not found.
     */
    @Nullable
    public static Form of(String name)
    {
        return LOOKUP_TABLE.get(name);
    }

    private final double heatAbsorptionModifier;

    /**
     * @deprecated use {@link Form#Form(double)} to explicitly specify the
     *             heat absorption modifier
     */
    @Deprecated
    Form()
    {
        this(1D);
    }

    Form(double heatAbsorptionModifier)
    {
        this.heatAbsorptionModifier = heatAbsorptionModifier;
    }

    public double getHeatAbsorptionModifier()
    {
        return heatAbsorptionModifier;
    }


    public static Form byActions(int horizontal, int vertical)
    {
        int min = Math.min(horizontal, vertical);
        int max = Math.max(horizontal, vertical);
        if (max + min > 18)
            return PASTE;
        if (max + min > 15)
            return MINCED;
        if (max > 5 && min > 5)
            return DICED;
        if (max > 5 && min > 1)
            return SHREDDED;
        if (max > 5)
            return SLICED;
        if (max > 0)
            return CUBED;
        return FULL;
    }

    public int[] getStandardActions()
    {
        switch (this)
        {
        case PASTE:
            return new int[] { 10, 10 };
        case MINCED:
            return new int[] { 8, 8 };
        case SLICED:
            return new int[] { 6, 0 };
        case SHREDDED:
            return new int[] { 6, 2 };
        case DICED:
            return new int[] { 6, 6 };
        case CUBED:
            return new int[] { 1, 1 };
        default:
            return new int[] { 0, 0 };
        }
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name().toLowerCase(Locale.ENGLISH);
    }
}
