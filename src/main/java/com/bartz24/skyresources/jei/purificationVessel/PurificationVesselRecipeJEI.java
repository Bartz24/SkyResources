package com.bartz24.skyresources.jei.purificationVessel;

import java.util.Collections;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class PurificationVesselRecipeJEI extends BlankRecipeWrapper
{
	private final FluidStack input;

	private final FluidStack output;

	public PurificationVesselRecipeJEI(FluidStack input, FluidStack output)
	{
		this.input = input;
		this.output = output;
	}

	@Override
	public List getFluidInputs()
	{
		return Collections.singletonList(input);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight,
			int mouseX, int mouseY)
	{
	}

	@Override
	public List getFluidOutputs()
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
		ingredients.setOutputs(FluidStack.class, getFluidOutputs());
	}
}
