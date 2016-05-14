package com.bartz24.skyresources.jei.cauldron;

import com.bartz24.skyresources.ItemHelper;
import com.bartz24.skyresources.References;
import com.bartz24.skyresources.registry.ModBlocks;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

public class CauldronRecipeCategory extends BlankRecipeCategory
{
	private static final int slotInput = 0;
	private static final int slotOutput = 1;
	private static final int slotCauldron = 2;

	private final IDrawable background;

	private final String localizedName = I18n
			.translateToLocalFormatted("jei.skyresources.recipe.cauldron");

	public CauldronRecipeCategory(IGuiHelper guiHelper)
	{
		super();
		background = guiHelper
				.createDrawable(
						new ResourceLocation(References.ModID,
								"textures/gui/jei/condenser.png"),
						0, 0, 86, 50);
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
		return References.ModID + ":cauldron";
	}

	@Override
	public void setRecipe(IRecipeLayout layout, IRecipeWrapper wrapper)
	{
		layout.getItemStacks().init(slotInput, true, 1, 17);
		layout.getItemStacks().init(slotOutput, false, 64, 28);
		layout.getItemStacks().init(slotCauldron, true, 1, 35);

		if (wrapper instanceof CauldronRecipeJEI)
		{
			CauldronRecipeJEI recipe = (CauldronRecipeJEI) wrapper;
			layout.getItemStacks().set(slotInput,
					(ItemStack) recipe.getInputs().get(0));
			layout.getItemStacks().set(slotOutput, recipe.getOutputs());
			layout.getItemStacks().set(slotCauldron,
					new ItemStack(Items.cauldron));
		}
	}

}
