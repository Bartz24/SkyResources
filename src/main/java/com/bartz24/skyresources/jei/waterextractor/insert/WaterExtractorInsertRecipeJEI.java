package com.bartz24.skyresources.jei.waterextractor.insert;

import java.util.Collections;
import java.util.List;

import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class WaterExtractorInsertRecipeJEI extends BlankRecipeWrapper
{
	private final IBlockState inputBlock;

	private final IBlockState output;

	private final boolean fuzzyInput;
	private final int fluidIn;

	public WaterExtractorInsertRecipeJEI(IBlockState outputState, boolean fuzzy,
			IBlockState inputState, int amtReq)
	{
		inputBlock = inputState;
		output = outputState;
		fuzzyInput = fuzzy;
		fluidIn = amtReq;
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
		FontRenderer fontRendererObj = minecraft.fontRendererObj;
		fontRendererObj.drawString("Inserting", 65, 0, java.awt.Color.gray.getRGB());
	}

	@Override
	public List getOutputs()
	{
		return Collections.singletonList(new ItemStack(output.getBlock(), 1,
				output.getBlock().getMetaFromState(output)));
	}

	@Override
    public List getFluidInputs()
    {
        return Collections.singletonList(new FluidStack(FluidRegistry.WATER, fluidIn));
    }

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY)
	{
		return null;
	}
}
