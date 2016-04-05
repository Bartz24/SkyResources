package com.bartz24.skyresources.jei.concentrator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

public class ConcentratorRecipeJEI extends BlankRecipeWrapper
{
	private final IBlockState inputBlock;

	private final IBlockState outputBlock;

	private final ItemStack inputStack;
	
	private final int heatRequired;

	public ConcentratorRecipeJEI(IBlockState output, IBlockState input,
			ItemStack stack, int heatReq)
	{
		inputBlock = input;
		outputBlock = output;
		inputStack = stack;
		heatRequired = heatReq;
	}

	@Override
	public List getInputs()
	{
		List list = new ArrayList();
		list.add(new ItemStack(inputBlock.getBlock(), 1,
				inputBlock.getBlock().getMetaFromState(inputBlock)));
		list.add(inputStack);
		return list;
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight,
			int mouseX, int mouseY)
	{
		String s = Integer.toString(heatRequired) + "°C";
		FontRenderer fontRendererObj = minecraft.fontRendererObj;
		int stringWidth = fontRendererObj.getStringWidth(s);
		fontRendererObj.drawString(s, 85 - stringWidth, 8,
				java.awt.Color.gray.getRGB());
	}

	@Override
	public List getOutputs()
	{
		return Collections.singletonList(new ItemStack(outputBlock.getBlock(), 1,
				outputBlock.getBlock().getMetaFromState(outputBlock)));
	}

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY)
	{
		return null;
	}
}
