package com.bartz24.skymod.jei.infusion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

public class InfusionRecipeJEI extends BlankRecipeWrapper
{
	private final List<ItemStack> inputs;

	private final ItemStack output;

	private final int healthReq;

	public InfusionRecipeJEI(ItemStack inputStack, ItemStack inputBlock, ItemStack output,
			int healthNeeded)
	{
		this.inputs = Arrays.asList(inputStack, inputBlock);
		this.output = output;
		healthReq = healthNeeded;
	}

	public List getInputs()
	{
		return inputs;
	}

	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX,
			int mouseY)
	{
		String s = "x" + Float.toString((float)healthReq/2F);
		FontRenderer fontRendererObj = minecraft.fontRendererObj;
		fontRendererObj.drawString(s, 80, 0, java.awt.Color.gray.getRGB());
	}

	public List getOutputs()
	{
		return Collections.singletonList(output);
	}

	public List<String> getTooltipStrings(int mouseX, int mouseY)
	{
		ArrayList<String> ret = new ArrayList<String>();
		if (mouseX >= 53 && mouseX <= 69 && mouseY >= 8 && mouseY <= 27)
		{
			ret.add(I18n.translateToLocal("jei.skymod.recipe.rightClickOn"));
			return ret;
		}
		return null;
	}
}
