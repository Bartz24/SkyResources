package com.bartz24.skyresources.jei.condenser;

import java.util.List;

import com.bartz24.skyresources.References;

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

public class CondenserRecipeCategory extends BlankRecipeCategory
{
	private static final int slotInputBlock = 0;
	private static final int slotInput = 1;
	private static final int slotOutput = 2;

	private final IDrawable background;

	private final String localizedName = I18n.translateToLocalFormatted("jei.skyresources.recipe.condenser");

	public CondenserRecipeCategory(IGuiHelper guiHelper)
	{
		super();
		background = guiHelper.createDrawable(new ResourceLocation(References.ModID, "textures/gui/jei/condenser.png"),
				0, 0, 86, 65);
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
		return References.ModID + ":condenser";
	}

	@Override
	public void setRecipe(IRecipeLayout layout, IRecipeWrapper wrapper, IIngredients ingredients)
	{
		layout.getItemStacks().init(slotOutput, false, 64, 11);
		layout.getItemStacks().init(slotInput, true, 1, 18);

		List<List<FluidStack>> fluidInputs = ingredients.getInputs(FluidStack.class);
		List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
		if (fluidInputs.size() > 0)
		{
			layout.getFluidStacks().init(slotInputBlock, true, 2, 1);
			layout.getFluidStacks().set(slotInputBlock, fluidInputs.get(0));
		} else
		{
			layout.getItemStacks().init(slotInputBlock, true, 1, 0);
			layout.getItemStacks().set(slotInputBlock, inputs.get(1));
		}

		List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);
		layout.getItemStacks().set(slotOutput, outputs.get(0));
		layout.getItemStacks().set(slotInput, inputs.get(0));
	}

	@Override
	public String getModName()
	{
		return References.ModName;
	}

}
