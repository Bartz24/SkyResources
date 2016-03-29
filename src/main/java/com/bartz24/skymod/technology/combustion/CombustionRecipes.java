package com.bartz24.skymod.technology.combustion;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.InputSource;

import scala.actors.threadpool.Arrays;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bartz24.skymod.SkyMod;
import com.bartz24.skymod.alchemy.infusion.InfusionRecipe;

public class CombustionRecipes
{

	public CombustionRecipes()
	{
		Recipes = new ArrayList();
	}

	private static List<CombustionRecipe> Recipes;

	public static CombustionRecipe getRecipe(List<ItemStack> input, int heatValue)
	{		
		CombustionRecipe rec = new CombustionRecipe(heatValue, input);
		

		for (CombustionRecipe recipe : Recipes)
		{
			if (rec.isInputRecipeEqualTo(recipe))
			{
				System.out.println("HERE");
				return recipe;
			}
		}
		

		return null;
	}

	public static List<CombustionRecipe> getRecipes()
	{
		return Recipes;
	}

	public static void addRecipe(ItemStack output, int heatValue,
			List<ItemStack> input)
	{

		if (input == null || input.size() == 0)
		{
			SkyMod.instance.logger
					.error("Need input stacks for recipe. DID NOT ADD RECIPE.");
			return;
		}

		if (output == null)
		{
			SkyMod.instance.logger
					.error("Need a output for recipe. DID NOT ADD RECIPE FOR NULL.");
			return;
		}

		Recipes.add(new CombustionRecipe(output, heatValue, input));
	}
	
	public static void addRecipe(ItemStack output, int heatValue,
			ItemStack... input)
	{
		addRecipe(output, heatValue, Arrays.asList(input));
	}
}
