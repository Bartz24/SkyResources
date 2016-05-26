package com.bartz24.skyresources.jei.waterextractor;

import com.bartz24.skyresources.ItemHelper;
import com.bartz24.skyresources.References;
import com.bartz24.skyresources.alchemy.tile.CrucibleTile;
import com.bartz24.skyresources.base.item.ItemWaterExtractor;
import com.bartz24.skyresources.jei.waterextractor.extract.WaterExtractorExtractRecipeJEI;
import com.bartz24.skyresources.registry.ModItems;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

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
	public void setRecipe(IRecipeLayout layout, IRecipeWrapper wrapper)
	{
		layout.getItemStacks().init(slotInputExtractor, true, 32, 1);
		layout.getItemStacks().init(slotInputStack, true, 53, 29);
		layout.getItemStacks().init(slotOutput, false, 106, 15);
		layout.getFluidStacks().init(slotInputFluid, false, 3, 4, 14, 42,
				ItemWaterExtractor.maxAmount, true, null);
		layout.getFluidStacks().init(slotOutputFluid, false, 132, 4, 14, 42,
				ItemWaterExtractor.maxAmount, false, null);

		IRecipeWrapper recipe = wrapper;
		layout.getItemStacks().set(slotInputStack,
				(ItemStack) recipe.getInputs().get(0));
		layout.getItemStacks().set(slotInputExtractor,
				new ItemStack(ModItems.waterExtractor));
		layout.getItemStacks().set(slotOutput, recipe.getOutputs());
		layout.getFluidStacks().set(slotInputFluid, recipe.getFluidInputs());
		layout.getFluidStacks().set(slotOutputFluid, recipe.getFluidOutputs());
	}

}
