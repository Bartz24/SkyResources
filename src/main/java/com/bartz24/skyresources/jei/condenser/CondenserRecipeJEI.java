package com.bartz24.skyresources.jei.condenser;

import java.util.Collections;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class CondenserRecipeJEI extends BlankRecipeWrapper
{
	private final FluidStack input;

	private final ItemStack output;

	private final int time;

	public CondenserRecipeJEI(ItemStack out, FluidStack in, int timeCondense)
	{
		input = in;
		output = out;
		time = timeCondense;
	}

	public List getFluidInputs()
	{
		return Collections.singletonList(input);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
	{
		String s = Integer.toString(time) + " base ticks";
		FontRenderer fontRendererObj = minecraft.fontRendererObj;
		int stringWidth = fontRendererObj.getStringWidth(s);
		fontRendererObj.drawString(s, 80 - stringWidth, 8, java.awt.Color.gray.getRGB());
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
		ingredients.setInputs(FluidStack.class, getFluidInputs());
		ingredients.setOutputs(ItemStack.class, getOutputs());
	}
}
