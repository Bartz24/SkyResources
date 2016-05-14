package com.bartz24.skyresources.jei.cauldron;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

public class CauldronRecipeJEI extends BlankRecipeWrapper
{
	private final ItemStack input;

	private final ItemStack output;

	public CauldronRecipeJEI(ItemStack out, ItemStack in)
	{
		input = in;
		output = out;
	}

	@Override
	public List getInputs()
	{
		return Collections.singletonList(input);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight,
			int mouseX, int mouseY)
	{
	}

	@Override
	public List getOutputs()
	{
		return Collections.singletonList(output);
	}

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY)
	{
		return null;
	}
}
