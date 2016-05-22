package com.bartz24.skyresources.jei.rockgrinder;

import java.util.Collections;
import java.util.List;

import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RockGrinderRecipeJEI extends BlankRecipeWrapper
{
	private final IBlockState inputBlock;

	private final ItemStack output;

	private final boolean fuzzyInput;
	private final float outChance;

	public RockGrinderRecipeJEI(ItemStack output, IBlockState input,
			boolean fuzzyIn, float chance)
	{
		inputBlock = input;
		this.output = output;
		fuzzyInput = fuzzyIn;
		outChance = chance;
	}

	@Override
	public List getInputs()
	{
		return Collections.singletonList(new ItemStack(inputBlock.getBlock(), 1,
				fuzzyInput ? OreDictionary.WILDCARD_VALUE
						: inputBlock.getBlock().getMetaFromState(inputBlock)));
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight,
			int mouseX, int mouseY)
	{
		String s =  Float.toString(outChance * 100) + "%";
		FontRenderer fontRendererObj = minecraft.fontRendererObj;
		fontRendererObj.drawString(s, 70, 0, java.awt.Color.gray.getRGB());
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
