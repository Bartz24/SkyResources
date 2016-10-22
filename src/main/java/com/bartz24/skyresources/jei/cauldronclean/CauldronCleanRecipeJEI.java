package com.bartz24.skyresources.jei.cauldronclean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

public class CauldronCleanRecipeJEI extends BlankRecipeWrapper
{
	private final ItemStack input;

	private final float outChance;
	private final ItemStack output;

	public CauldronCleanRecipeJEI(ItemStack out, float chance, ItemStack in)
	{
		input = in;
		output = out;
		outChance = chance;
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
		String s =  Float.toString(Math.round(outChance * 10000F)/100F) + "%";
		FontRenderer fontRendererObj = minecraft.fontRendererObj;
		fontRendererObj.drawString(s, 70, 10, java.awt.Color.gray.getRGB());
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

	@Override
	public void getIngredients(IIngredients ingredients)
	{
		ingredients.setInputs(ItemStack.class, getInputs());
		ingredients.setOutputs(ItemStack.class, getOutputs());
	}
}
