package com.bartz24.skyresources.jei.heatsources;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.jei.freezer.FreezerRecipeJEI;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

public class HeatSourcesRecipeCategory extends BlankRecipeCategory
{
	private static final int slotInputStack = 0;
	private final IDrawable background;

	private final String localizedName = I18n
			.translateToLocalFormatted("jei.skyresources.recipe.heatsources");

	public HeatSourcesRecipeCategory(IGuiHelper guiHelper)
	{
		super();
		background = guiHelper.createDrawable(
				new ResourceLocation(References.ModID, "textures/gui/jei/concentrator.png"), 20, 0,
				70, 25);
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
		return References.ModID + ":heatsources";
	}

	@Override
	public void setRecipe(IRecipeLayout layout, IRecipeWrapper wrapper)
	{
		layout.getItemStacks().init(slotInputStack, true, 1, 5);

		if (wrapper instanceof HeatSourcesRecipeJEI)
		{
			HeatSourcesRecipeJEI recipe = (HeatSourcesRecipeJEI) wrapper;
			if (recipe.getInputs() != null)
			{
				for (int i = 0; i < recipe.getInputs().size(); i++)
				{
					layout.getItemStacks().set(slotInputStack,
							(ItemStack) recipe.getInputs().get(i));
				}
			}
		}

	}

}
