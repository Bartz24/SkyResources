package com.bartz24.skyresources.jei.rockgrinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import scala.actors.threadpool.Arrays;

public class RockGrinderRecipeJEI extends BlankRecipeWrapper
{
	private final IBlockState inputBlock;

	private final ItemStack output;

	private final boolean fuzzyInput;

	public RockGrinderRecipeJEI(ItemStack output, IBlockState input,
			boolean fuzzyIn)
	{
		inputBlock = input;
		this.output = output;
		fuzzyInput = fuzzyIn;
	}

	public List getInputs()
	{
		return Collections.singletonList(new ItemStack(inputBlock.getBlock(), 1,
				fuzzyInput ? OreDictionary.WILDCARD_VALUE
						: inputBlock.getBlock().getMetaFromState(inputBlock)));
	}

	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight,
			int mouseX, int mouseY)
	{
	}

	public List getOutputs()
	{
		return Collections.singletonList(output);
	}

	public List<String> getTooltipStrings(int mouseX, int mouseY)
	{
		return null;
	}
}
