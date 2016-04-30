package com.bartz24.skyresources.jei.heatsources;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.bartz24.skyresources.base.HeatSources;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class HeatSourcesRecipeMaker
{
	public static List<HeatSourcesRecipeJEI> getRecipes()
	{
		ArrayList<HeatSourcesRecipeJEI> recipes = new ArrayList<HeatSourcesRecipeJEI>();

		for (IBlockState key : HeatSources.getHeatSources().keySet())
		{
			IBlockState block = key;
			int val = (Integer) HeatSources.getHeatSources().get(key);
			HeatSourcesRecipeJEI addRecipe = new HeatSourcesRecipeJEI(
					block.getBlock().getLocalizedName(), val);
			recipes.add(addRecipe);
		}

		return recipes;
	}
}
