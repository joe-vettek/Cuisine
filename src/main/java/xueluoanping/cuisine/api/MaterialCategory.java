package xueluoanping.cuisine.api;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nullable;

public enum MaterialCategory
{
    @SerializedName("grain")
    GRAIN("grain"),
    @SerializedName("vegetables")
    VEGETABLES("veggie"), // This includes seaweed and the alike.
    @SerializedName("fruit")
    FRUIT("fruit"),
    @SerializedName("fish")
    FISH("fish"),
    @SerializedName("meat")
    MEAT(), // You cant tell whether it is raw or cooked
    @SerializedName("protein")
    PROTEIN(), // Dairy and things like egg which contains good protein but doesn't count as meat
    @SerializedName("nut")
    NUT("nut"),
    @SerializedName("seafood")
    SEAFOOD("seafood"), // Things that are from sea but are not fish, for example calms and shrimps
    @SerializedName("super_natural")
    SUPERNATURAL(), // Things like enderperal and chrous fruit
    @SerializedName("unknown")
    UNKNOWN(); // Things that cannot even be categorized

    private final String ore;

    MaterialCategory()
    {
        this(null);
    }

    MaterialCategory(@Nullable String ore)
    {
        this.ore = ore;
    }

    @Nullable
    public String getOreName()
    {
        return ore;
    }
}
