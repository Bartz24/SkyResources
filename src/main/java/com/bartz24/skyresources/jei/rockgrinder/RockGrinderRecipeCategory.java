package com.bartz24.skyresources.jei.rockgrinder;

import java.util.List;

import com.bartz24.skyresources.ItemHelper;
import com.bartz24.skyresources.References;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

public class RockGrinderRecipeCategory extends BlankRecipeCategory
{
	private static final int slotInputStack = 0;
	private static final int slotInputGrinder = 1;
	private static final int slotOutput = 2;

	private final IDrawable background;

	private final String localizedName = I18n.translateToLocalFormatted("jei.skyresources.recipe.rockgrinder");

	public RockGrinderRecipeCategory(IGuiHelper guiHelper)
	{
		super();
		background = guiHelper.createDrawable(new ResourceLocation(References.ModID, "textures/gui/jei/infusion.png"),
				32, 0, 95, 71);
	}

	@Override
	public void drawAnimations(Minecraft minecraft)
	{
	}

	@Override
	public void drawExtras(Minecraft minecraft)
	{
	}

	@Override
	public IDrawable getBackground()
	{
		return background;
	}

	@Override
	public String getTitle()
	{
		return localizedName;
	}

	@Override
	public String getUid()
	{
		return References.ModID + ":rockgrinder";
	}

	@Override
	public void setRecipe(IRecipeLayout layout, IRecipeWrapper wrapper, IIngredients ingredients)
	{
		layout.getItemStacks().init(slotInputGrinder, true, 0, 1);
		layout.getItemStacks().init(slotInputStack, true, 21, 29);
		layout.getItemStacks().init(slotOutput, false, 74, 15);

		List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
		layout.getItemStacks().set(slotInputStack, inputs.get(0));
		layout.getItemStacks().set(slotInputGrinder, ItemHelper.getRockGrinders());
		List<ItemStack> outputs = ingredients.getOutputs(ItemStack.class);
		layout.getItemStacks().set(slotOutput, outputs);
	}

}
