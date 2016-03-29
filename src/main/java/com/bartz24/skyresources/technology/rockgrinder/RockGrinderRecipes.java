package com.bartz24.skyresources.technology.rockgrinder;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.SkyResources;
import com.bartz24.skyresources.technology.combustion.CombustionRecipe;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import scala.actors.threadpool.Arrays;

public class RockGrinderRecipes
{

	public RockGrinderRecipes()
	{
		Recipes = new ArrayList();
	}

	private static List<RockGrinderRecipe> Recipes;

	public static RockGrinderRecipe getRecipe(IBlockState block)
	{		
		RockGrinderRecipe rec = new RockGrinderRecipe(block);
		

		for (RockGrinderRecipe recipe : Recipes)
		{
			if (rec.isInputRecipeEqualTo(recipe))
			{
				return recipe;
			}
		}
		

		return null;
	}

	public static List<RockGrinderRecipe> getRecipes()
	{
		return Recipes;
	}

	public static void addRecipe(ItemStack output, boolean fuzzyInput,
			IBlockState block)
	{

		if (block == null)
		{
			SkyResources.instance.logger
					.error("Need input block for recipe. DID NOT ADD RECIPE.");
			return;
		}

		if (output == null)
		{
			SkyResources.instance.logger
					.error("Need a output for recipe. DID NOT ADD RECIPE FOR NULL.");
			return;
		}

		Recipes.add(new RockGrinderRecipe(output, fuzzyInput, block));
	}
	
}
