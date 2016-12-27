package com.bartz24.skyresources.jei.heatsources;

import java.util.Collections;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class HeatSourcesRecipeJEI extends BlankRecipeWrapper
{
	private final IBlockState input;

	private final int value;

	public HeatSourcesRecipeJEI(IBlockState input, int heatValue)
	{
		this.input = input;
		value = heatValue;
	}

	public List getInputs()
	{
		ItemStack stack = new ItemStack(input.getBlock(), 1, input.getBlock().getMetaFromState(input));
		return Collections.singletonList(
				(stack == ItemStack.EMPTY || !(stack.getItem() instanceof Item)) ? ItemStack.EMPTY : stack);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
	{
		ItemStack stack = new ItemStack(input.getBlock(), 1, input.getBlock().getMetaFromState(input));
		FontRenderer fontRendererObj = minecraft.fontRendererObj;
		String s = input.getBlock().getLocalizedName();
		fontRendererObj.drawString(s, 20, 12, java.awt.Color.gray.getRGB());
		s = Integer.toString(value) + " Heat";
		fontRendererObj = minecraft.fontRendererObj;
		fontRendererObj.drawString(s, 20, 24, java.awt.Color.gray.getRGB());
	}

	public List getOutputs()
	{
		return null;
	}

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY)
	{
		return null;
	}

	@Override
	public void getIngredients(IIngredients ingredients)
	{
		if (getInputs() != null)
			ingredients.setInputs(ItemStack.class, getInputs());
	}
}
