package com.bartz24.skyresources.jei.waterextractor.extract;

import java.util.Collections;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class WaterExtractorExtractRecipeJEI extends BlankRecipeWrapper
{
	private final ItemStack inputBlock;

	private final ItemStack output;

	private final boolean fuzzyInput;
	private final int fluidOut;

	public WaterExtractorExtractRecipeJEI(int outAmt, boolean fuzzy,
			ItemStack inputState, ItemStack outputState)
	{
		inputBlock = inputState;
		output = outputState;
		fuzzyInput = fuzzy;
		fluidOut = outAmt;
	}

	public List getInputs()
	{
		return Collections.singletonList(inputBlock);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight,
			int mouseX, int mouseY)
	{
		FontRenderer fontRendererObj = minecraft.fontRenderer;
		fontRendererObj.drawString("Extracting", 65, 0, java.awt.Color.gray.getRGB());
	}

	public List getOutputs()
	{
		return Collections.singletonList(output);
	}

    public List getFluidOutputs()
    {
        return Collections.singletonList(new FluidStack(FluidRegistry.WATER, fluidOut));
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
		ingredients.setOutputs(FluidStack.class, getFluidOutputs());
	}
}
