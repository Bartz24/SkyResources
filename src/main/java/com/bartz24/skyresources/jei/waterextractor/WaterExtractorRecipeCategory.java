package com.bartz24.skyresources.jei.waterextractor;

import java.util.List;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.base.item.ItemWaterExtractor;
import com.bartz24.skyresources.registry.ModItems;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fluids.FluidStack;

public class WaterExtractorRecipeCategory extends BlankRecipeCategory
{
	private static final int slotInputStack = 0;
	private static final int slotInputExtractor = 1;
	private static final int slotOutput = 2;
	private static final int slotInputFluid = 3;
	private static final int slotOutputFluid = 4;

	private final IDrawable background;

	private final String localizedName = I18n.translateToLocalFormatted(
			"jei.skyresources.recipe.waterextractor");

	public WaterExtractorRecipeCategory(IGuiHelper guiHelper)
	{
		super();
		background = guiHelper
				.createDrawable(
						new ResourceLocation(References.ModID,
								"textures/gui/jei/extractor.png"),
						0, 0, 150, 71);
	}

	@Override
	public void drawAnimations(Minecraft minecraft)
	{
	}

	@Override
	public void drawExtras(Minecraft minecraft)
	{
	}

	@Override
	public IDrawable getBackground()
	{
		return background;
	}

	@Override
	public String getTitle()
	{
		return localizedName;
	}

	@Override
	public String getUid()
	{
		return References.ModID + ":waterextractor";
	}

	@Override
	public void setRecipe(IRecipeLayout layout, IRecipeWrapper wrapper, IIngredients ingredients)
	{
		layout.getItemStacks().init(slotInputExtractor, true, 32, 1);
		layout.getItemStacks().init(slotInputStack, true, 53, 29);
		layout.getItemStacks().init(slotOutput, false, 106, 15);
		layout.getFluidStacks().init(slotInputFluid, false, 3, 4, 14, 42,
				ItemWaterExtractor.maxAmount, true, null);
		layout.getFluidStacks().init(slotOutputFluid, false, 132, 4, 14, 42,
				ItemWaterExtractor.maxAmount, false, null);

		List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
		List<List<FluidStack>> finputs = ingredients.getInputs(FluidStack.class);
		layout.getItemStacks().set(slotInputStack,
				inputs.get(0));
		layout.getItemStacks().set(slotInputExtractor,
				new ItemStack(ModItems.waterExtractor));
		List<ItemStack> outputs = ingredients.getOutputs(ItemStack.class);
		List<FluidStack> foutputs = ingredients.getOutputs(FluidStack.class);
		layout.getItemStacks().set(slotOutput, outputs);
		layout.getFluidStacks().set(slotInputFluid, finputs.get(0));
		layout.getFluidStacks().set(slotOutputFluid, foutputs);
	}

}
