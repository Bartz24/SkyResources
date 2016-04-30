package com.bartz24.skyresources.jei.heatsources;

import java.util.Collections;
import java.util.List;

import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class HeatSourcesRecipeJEI extends BlankRecipeWrapper
{
	private final String input;
	
	private final int value;

	public HeatSourcesRecipeJEI(String input, int heatValue)
	{
		this.input = input;
		value = heatValue;
	}

	@Override
	public List getInputs()
	{
		return null;
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight,
			int mouseX, int mouseY)
	{
		String s = input + " :";
		FontRenderer fontRendererObj = minecraft.fontRendererObj;
		fontRendererObj.drawString(s, 10, 12, java.awt.Color.gray.getRGB());
		s = Integer.toString(value) + " Heat";
		fontRendererObj = minecraft.fontRendererObj;
		fontRendererObj.drawString(s, 20, 24, java.awt.Color.gray.getRGB());
	}

	@Override
	public List getOutputs()
	{
		return null;
	}

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY)
	{
		return null;
	}
}
