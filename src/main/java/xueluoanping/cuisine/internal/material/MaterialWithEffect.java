package xueluoanping.cuisine.internal.material;


import net.minecraft.world.item.crafting.Ingredient;
import xueluoanping.cuisine.api.CookingVessel;
import xueluoanping.cuisine.api.Effect;
import xueluoanping.cuisine.api.MaterialCategory;
import xueluoanping.cuisine.api.prefab.SimpleMaterialImpl;

public class MaterialWithEffect extends SimpleMaterialImpl
{
    private final Effect effect;

    public MaterialWithEffect(String id, Effect effect, int rawColor, int cookedColor, int waterValue, int oilValue, int heatValue)
    {
        this(id, effect, rawColor, cookedColor, waterValue, oilValue, heatValue, 0.1F);
    }

    public MaterialWithEffect(String id, Effect effect, int rawColor, int cookedColor, int waterValue, int oilValue, int heatValue, float foodSaturationModifier)
    {
        super(id, rawColor, cookedColor, waterValue, oilValue, heatValue, foodSaturationModifier);
        this.effect = effect;
    }

    public MaterialWithEffect(String id, Effect effect, int rawColor, int cookedColor, int waterValue, int oilValue, int heatValue, float foodSaturationModifier, MaterialCategory... categories)
    {
        super(id, rawColor, cookedColor, waterValue, oilValue, heatValue, foodSaturationModifier, categories);
        this.effect = effect;
    }

    public MaterialWithEffect(String id, Effect effect, int rawColor, int cookedColor, int waterValue, int oilValue, int heatValue, float foodSaturationModifier, float boilHeat, int boilTime, MaterialCategory... categories)
    {
        super(id, rawColor, cookedColor, waterValue, oilValue, heatValue, foodSaturationModifier, boilHeat, boilTime, categories);
        this.effect = effect;
    }

    // @Override
    // public void onMade(CompositeFood.Builder<?> dish, Ingredient ingredient, CookingVessel vessel, final EffectCollector collector)
    // {
    //     if (ingredient.hasTrait(IngredientTrait.UNDERCOOKED) || ingredient.hasTrait(IngredientTrait.OVERCOOKED))
    //     {
    //         return;
    //     }
    //     ingredient.addEffect(effect);
    // }
}
