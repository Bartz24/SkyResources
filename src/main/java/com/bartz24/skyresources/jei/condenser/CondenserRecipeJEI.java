package com.bartz24.skyresources.jei.condenser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

public class CondenserRecipeJEI extends BlankRecipeWrapper
{
	private final IBlockState inputBlock;

	private final ItemStack output;
	
	private final int time;

	public CondenserRecipeJEI(ItemStack out, IBlockState input, int timeCondense)
	{
		inputBlock = input;
		output = out;
		time = timeCondense;
	}

	@Override
	public List getInputs()
	{
		List list = new ArrayList();
		list.add(new ItemStack(inputBlock.getBlock(), 1,
				inputBlock.getBlock().getMetaFromState(inputBlock)));
		return list;
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight,
			int mouseX, int mouseY)
	{
		String s = Integer.toString(time) + " ticks";
		FontRenderer fontRendererObj = minecraft.fontRendererObj;
		int stringWidth = fontRendererObj.getStringWidth(s);
		fontRendererObj.drawString(s, 80 - stringWidth, 8,
				java.awt.Color.gray.getRGB());
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
