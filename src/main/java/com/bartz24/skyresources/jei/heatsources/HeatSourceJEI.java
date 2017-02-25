package com.bartz24.skyresources.jei.heatsources;

import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class HeatSourceJEI extends BlankRecipeWrapper
{
	ItemStack stack;
	int value;
	String name;

	public HeatSourceJEI(ItemStack stack, int val)
	{
		this.stack = stack;
		value = val;
		this.name = "";
	}

	public HeatSourceJEI(String name, int val)
	{
		stack = ItemStack.EMPTY;
		value = val;
		this.name = name;
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
	{
		FontRenderer fontRendererObj = minecraft.fontRenderer;
		String s = stack.isEmpty() ? I18n.format(name) : stack.getDisplayName();
		fontRendererObj.drawString(s, 20, 12, java.awt.Color.gray.getRGB());
		s = Integer.toString(value) + " Heat";
		fontRendererObj = minecraft.fontRenderer;
		fontRendererObj.drawString(s, 20, 24, java.awt.Color.gray.getRGB());
	}

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY)
	{
		return null;
	}

	@Override
	public void getIngredients(IIngredients ingredients)
	{
		ingredients.setInput(ItemStack.class, stack);
	}
}
