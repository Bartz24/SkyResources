package com.bartz24.skyresources.jei.combustion;

import com.bartz24.skyresources.References;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

public class CombustionRecipeCategory extends BlankRecipeCategory
{
	private static final int[] slotInputStacks = new int[]
	{ 0, 1, 2, 3, 4, 5, 6, 7, 8 };
	private static final int slotOutput = 9;

	private final IDrawable background;

	private IDrawable heatBar;

	private final String localizedName = I18n
			.translateToLocalFormatted("jei.skyresources.recipe.combustion");

	public CombustionRecipeCategory(IGuiHelper guiHelper)
	{
		super();
		background = guiHelper
				.createDrawable(
						new ResourceLocation(References.ModID,
								"textures/gui/jei/combustion.png"),
						0, 0, 137, 71);

		IDrawableStatic arrowDrawable = guiHelper
				.createDrawable(
						new ResourceLocation(References.ModID,
								"textures/gui/combustionHeater.png"),
						176, 14, 8, 69);
		heatBar = guiHelper.createAnimatedDrawable(arrowDrawable, 200,
				mezz.jei.api.gui.IDrawableAnimated.StartDirection.BOTTOM,
				false);
	}

	@Override
	public void drawAnimations(Minecraft minecraft)
	{
		heatBar.draw(minecraft, 128, 1);
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
		return References.ModID + ":combustion";
	}

	@Override
	public void setRecipe(IRecipeLayout layout, IRecipeWrapper wrapper)
	{
		for (int i : slotInputStacks)
		{
			layout.getItemStacks().init(i, true, (i % 3) * 18 + 3,
					(i / 3) * 18 + 9);

		}
		layout.getItemStacks().init(slotOutput, false, 97, 27);

		if (wrapper instanceof CombustionRecipeJEI)
		{
			CombustionRecipeJEI infusionRecipe = (CombustionRecipeJEI) wrapper;
			for (int i = 0; i < infusionRecipe.getInputs().size(); i++)
			{
				layout.getItemStacks().set(slotInputStacks[i],
						(ItemStack) infusionRecipe.getInputs().get(i));
			}
			layout.getItemStacks().set(slotOutput, infusionRecipe.getOutputs());
		}
	}

}
