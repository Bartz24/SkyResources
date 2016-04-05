package com.bartz24.skyresources.jei.concentrator;

import com.bartz24.skyresources.ItemHelper;
import com.bartz24.skyresources.References;
import com.bartz24.skyresources.registry.ModBlocks;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

public class ConcentratorRecipeCategory extends BlankRecipeCategory
{
	private static final int slotInputStack = 0;
	private static final int slotInputBlock = 1;
	private static final int slotOutput = 2;
	private static final int slotConcentrator = 3;

	private final IDrawable background;

	private IDrawable heatBar;

	private final String localizedName = I18n
			.translateToLocalFormatted("jei.skyresources.recipe.concentrator");

	public ConcentratorRecipeCategory(IGuiHelper guiHelper)
	{
		super();
		background = guiHelper
				.createDrawable(
						new ResourceLocation(References.ModID,
								"textures/gui/jei/concentrator.png"),
						0, 0, 104, 72);
		IDrawableStatic arrowDrawable = guiHelper
				.createDrawable(
						new ResourceLocation(References.ModID,
								"textures/gui/combustionHeater.png"),
						176, 14, 8, 69);
		heatBar = guiHelper.createAnimatedDrawable(arrowDrawable, 200,
				mezz.jei.api.gui.IDrawableAnimated.StartDirection.BOTTOM,
				false);
	}

	@Override
	public void drawAnimations(Minecraft minecraft)
	{
		heatBar.draw(minecraft, 95, 1);
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
		return References.ModID + ":concentrator";
	}

	@Override
	public void setRecipe(IRecipeLayout layout, IRecipeWrapper wrapper)
	{
		layout.getItemStacks().init(slotInputBlock, true, 1, 45);
		layout.getItemStacks().init(slotInputStack, true, 1, 9);
		layout.getItemStacks().init(slotOutput, false, 64, 45);
		layout.getItemStacks().init(slotConcentrator, true, 1, 27);

		if (wrapper instanceof ConcentratorRecipeJEI)
		{
			ConcentratorRecipeJEI infusionRecipe = (ConcentratorRecipeJEI) wrapper;
			layout.getItemStacks().set(slotInputBlock,
					(ItemStack) infusionRecipe.getInputs().get(0));
				layout.getItemStacks().set(slotInputStack,
						(ItemStack) infusionRecipe.getInputs().get(1));
			layout.getItemStacks().set(slotOutput, infusionRecipe.getOutputs());
			layout.getItemStacks().set(slotConcentrator, new ItemStack(ModBlocks.concentrator));
		}
	}

}
