package com.bartz24.skyresources.jei.crucible;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.alchemy.tile.CrucibleTile;

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

public class CrucibleRecipeCategory extends BlankRecipeCategory
{
	private static final int slotInputStack = 0;
	private static final int slotOutputFluid = 1;

	private final IDrawable background;

	private final String localizedName = I18n
			.translateToLocalFormatted("jei.skyresources.recipe.crucible");

	public CrucibleRecipeCategory(IGuiHelper guiHelper)
	{
		super();
		background = guiHelper
				.createDrawable(
						new ResourceLocation(References.ModID,
								"textures/gui/jei/crucible.png"),
						0, 0, 70, 46);
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
		return References.ModID + ":crucible";
	}

	@Override
	public void setRecipe(IRecipeLayout layout, IRecipeWrapper wrapper)
	{
		layout.getFluidStacks().init(slotOutputFluid, false, 54, 2, 14, 42,
				CrucibleTile.tankCapacity, true, null);

		layout.getItemStacks().init(slotInputStack, true, 3, 9);

		if (wrapper instanceof CrucibleRecipeJEI)
		{
			CrucibleRecipeJEI recipe = (CrucibleRecipeJEI) wrapper;
			layout.getItemStacks().set(slotInputStack,
					(ItemStack) recipe.getInputs().get(0));
			layout.getFluidStacks().set(slotOutputFluid,
					recipe.getFluidOutputs());
		}
	}

}
