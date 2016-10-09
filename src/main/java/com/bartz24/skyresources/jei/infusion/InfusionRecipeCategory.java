package com.bartz24.skyresources.jei.infusion;

import java.util.List;

import com.bartz24.skyresources.ItemHelper;
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

public class InfusionRecipeCategory extends BlankRecipeCategory
{
	private static final int slotInputStack = 0;
	private static final int slotInputBlock = 1;
	private static final int slotOutput = 2;
	private static final int slotInfusionStones = 3;

	private final IDrawable background;

	private IDrawable heartIcon;

	private final String localizedName = I18n.translateToLocalFormatted("jei.skyresources.recipe.infusion");

	public InfusionRecipeCategory(IGuiHelper guiHelper)
	{
		super();
		background = guiHelper.createDrawable(new ResourceLocation(References.ModID, "textures/gui/jei/infusion.png"),
				0, 0, 130, 48);
		heartIcon = guiHelper.createDrawable(new ResourceLocation("textures/gui/icons.png"), 53, 1, 8, 8);
	}

	@Override
	public void drawAnimations(Minecraft arg0)
	{

	}

	@Override
	public void drawExtras(Minecraft minecraft)
	{
		heartIcon.draw(minecraft, 70, 0);
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
		return References.ModID + ":infusion";
	}

	@Override
	public void setRecipe(IRecipeLayout layout, IRecipeWrapper wrapper, IIngredients ingredients)
	{
		layout.getItemStacks().init(slotInputStack, true, 0, 1);
		layout.getItemStacks().init(slotInfusionStones, true, 32, 1);
		layout.getItemStacks().init(slotInputBlock, true, 53, 29);
		layout.getItemStacks().init(slotOutput, false, 106, 15);

		List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
		layout.getItemStacks().set(slotInputStack, inputs.get(0));
		layout.getItemStacks().set(slotInputBlock, inputs.get(1));
		List<ItemStack> outputs = ingredients.getOutputs(ItemStack.class);
		layout.getItemStacks().set(slotOutput, outputs);
		layout.getItemStacks().set(slotInfusionStones, ItemHelper.getInfusionStones());
	}

}
