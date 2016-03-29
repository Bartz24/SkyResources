package com.bartz24.skyresources.jei.combustion;

import java.util.Collections;
import java.util.List;

import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

public class CombustionRecipeJEI extends BlankRecipeWrapper
{
	private final List<ItemStack> inputs;

	private final ItemStack output;

	private final int heatReq;

	public CombustionRecipeJEI(ItemStack output, List<ItemStack> input,	int heatNeeded)
	{
		this.inputs = input;
		this.output = output;
		heatReq = heatNeeded;
	}

	public List getInputs()
	{
		return inputs;
	}

	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX,
			int mouseY)
	{
		String s = Integer.toString(heatReq) + "°C";
		FontRenderer fontRendererObj = minecraft.fontRendererObj;
		int stringWidth = fontRendererObj.getStringWidth(s);
		fontRendererObj.drawString(s, 118-stringWidth, 8, java.awt.Color.gray.getRGB());
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
