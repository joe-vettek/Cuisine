package xueluoanping.cuisine.plugin.jei.category;


import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.FluidStack;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.craft.BasinSqueezingRecipe;
import xueluoanping.cuisine.plugin.jei.JEICompact;
import xueluoanping.cuisine.register.BlockEntityRegister;
import xueluoanping.cuisine.register.RecipeRegister;

import java.util.ArrayList;


public class SqueezingCategory implements IRecipeCategory<BasinSqueezingRecipe> {
    private final Component title;
    private final IDrawable icon;
    private final IDrawable background;
    protected final IDrawable itemContainer;
    protected final IDrawable fluidContainer;
    protected final IDrawable arrow;
    protected final IDrawable arrowOverlay;

    public SqueezingCategory(IGuiHelper helper) {
        IDrawable basin = helper.createDrawable(JEICompact.CUISINE_RECIPE_GUI, 0, 33, 20, 10);
        this.title = Component.translatable("jei.squeezing");
        ResourceLocation backgroundImage = Cuisine.rl("textures/gui/jei.png");
        background = helper.createDrawable(backgroundImage, 100, 100, 74, 32);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BlockEntityRegister.basin.get()));
        itemContainer = helper.createDrawable(JEICompact.VANILLA_RECIPE_GUI, 0, 96, 16, 16);
        fluidContainer = helper.createDrawable(backgroundImage, 0, 33, 20, 10);
        arrow = helper.createDrawable(JEICompact.VANILLA_RECIPE_GUI, 25, 133, 22, 15);
        arrowOverlay = helper.drawableBuilder(JEICompact.VANILLA_RECIPE_GUI, 82, 128, 24, 17).buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);

        Cuisine.logger(backgroundImage);
    }


    @Override
    public RecipeType<BasinSqueezingRecipe> getRecipeType() {
        return JEICompact.SQUEEZING_RECIPE_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return this.title;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void draw(BasinSqueezingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        PoseStack stack = guiGraphics.pose();
        stack.pushPose();
        stack.translate(0, 22, 0);
        fluidContainer.draw(guiGraphics);
        stack.popPose();

        stack.pushPose();
        stack.translate(54, 22, 0);
        fluidContainer.draw(guiGraphics);
        stack.popPose();

        stack.pushPose();
        stack.translate(26, 10, 0);
        arrow.draw(guiGraphics);
        arrowOverlay.draw(guiGraphics);
        stack.popPose();
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
    }

    public void setRecipe(IRecipeLayoutBuilder builder, BasinSqueezingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 2, 3)
                .setBackground(itemContainer, 0, 0)
                .addIngredients((Ingredient) recipe.getIngredients().get(0));
        FluidStack showing = recipe.getResult().get(0).copy();
//		if(showing.getAmount()>500)
//		showing.setAmount(500);
        ArrayList<FluidStack> fluidStackArrayList = new ArrayList<>();
        fluidStackArrayList.add(showing);
//		showing.setAmount(showing.getAmount()/2);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 56, 22)
                .addIngredients(NeoForgeTypes.FLUID_STACK, fluidStackArrayList)
                .setFluidRenderer(500, false, 16, 8);

    }


//	@Override
//	public void setRecipe(IRecipeLayoutBuilder builder, DebugRecipe recipe, IFocusGroup focuses) {
//		IRecipeSlotBuilder inputSlot = builder.addSlot(RecipeIngredientRole.INPUT, 0, 0)
//				.addItemStacks(List.of(
//						new ItemStack(Items.BUCKET),
//						new ItemStack(Items.WATER_BUCKET),
//						new ItemStack(Items.LAVA_BUCKET),
//						new ItemStack(Items.POWDER_SNOW_BUCKET),
//						new ItemStack(Items.AXOLOTL_BUCKET),
//						new ItemStack(Items.SALMON_BUCKET),
//						new ItemStack(Items.COD_BUCKET),
//						new ItemStack(Items.PUFFERFISH_BUCKET),
//						new ItemStack(Items.TROPICAL_FISH_BUCKET)
//				));
//
//		IRecipeSlotBuilder outputSlot = builder.addSlot(RecipeIngredientRole.OUTPUT, 20, 0)
//				.addItemStack(ItemStack.EMPTY)
//				.addIngredients(VanillaTypes.FLUID, List.of(
//						new FluidStack(Fluids.WATER, FluidAttributes.BUCKET_VOLUME),
//						new FluidStack(Fluids.LAVA, FluidAttributes.BUCKET_VOLUME)
//				))
//				.addItemStacks(List.of(
//						new ItemStack(Items.SNOW_BLOCK),
//						new ItemStack(Items.AXOLOTL_SPAWN_EGG),
//						new ItemStack(Items.SALMON),
//						new ItemStack(Items.COD),
//						new ItemStack(Items.PUFFERFISH),
//						new ItemStack(Items.TROPICAL_FISH)
//				));
//
//		builder.createFocusLink(inputSlot, outputSlot);
//	}
}
