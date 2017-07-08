package com.bartz24.skyresources.jei.fusion;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.alchemy.FusionCatalysts;

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

public class FusionRecipeCategory extends BlankRecipeCategory
{
	private static final int[] slotInputStacks = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
	private static final int slotCatalyst = 9;
	private static final int slotOutput = 10;

	private final IDrawable background;

	private final String localizedName = I18n.translateToLocalFormatted("jei.skyresources.recipe.fusion");

	public FusionRecipeCategory(IGuiHelper guiHelper)
	{
		super();
		background = guiHelper.createDrawable(new ResourceLocation(References.ModID, "textures/gui/jei/combustion.png"),
				0, 0, 120, 71);
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
		return References.ModID + ":fusion";
	}

	@Override
	public void setRecipe(IRecipeLayout layout, IRecipeWrapper wrapper, IIngredients ingredients)
	{
		for (int i : slotInputStacks)
		{
			layout.getItemStacks().init(i, true, (i % 3) * 18 + 3, (i / 3) * 18 + 9);

		}
		layout.getItemStacks().init(slotCatalyst, true, 66, 7);
		layout.getItemStacks().init(slotOutput, false, 97, 27);

		List<ItemStack> catalysts = new ArrayList();
		for (ItemStack s : FusionCatalysts.getCatalysts().keySet())
		{
			catalysts.add(s);
		}
		List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
		for (int i = 0; i < inputs.size(); i++)
		{
			layout.getItemStacks().set(slotInputStacks[i], inputs.get(i));
		}
		layout.getItemStacks().set(slotCatalyst, catalysts);
		List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);
		layout.getItemStacks().set(slotOutput, outputs.get(0));
	}

	@Override
	public String getModName()
	{
		return References.ModName;
	}

}
