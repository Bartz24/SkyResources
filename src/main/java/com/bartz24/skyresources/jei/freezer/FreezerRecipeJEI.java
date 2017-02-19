package com.bartz24.skyresources.jei.freezer;

import java.util.Collections;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class FreezerRecipeJEI extends BlankRecipeWrapper
{
	private final ItemStack input;

	private final ItemStack output;
	
	private final int time;

	public FreezerRecipeJEI(ItemStack output, ItemStack input, int ticks)
	{
		this.input = input;
		this.output = output;
		time = ticks;
	}

	public List getInputs()
	{
		return Collections.singletonList(input);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight,
			int mouseX, int mouseY)
	{
		String s = Float.toString(time) + " ticks";
		FontRenderer fontRendererObj = minecraft.fontRenderer;
		fontRendererObj.drawString(s, 1, -5, java.awt.Color.gray.getRGB());
	}

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
