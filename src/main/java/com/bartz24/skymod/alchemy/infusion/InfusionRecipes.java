package com.bartz24.skymod.alchemy.infusion;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bartz24.skymod.SkyMod;

public class InfusionRecipes
{
	public InfusionRecipes()
	{
		Recipes = new ArrayList();
	}

	private static List<InfusionRecipe> Recipes;

	public static InfusionRecipe getRecipe(ItemStack inputStack,
			Block inputBlock, int inputBlockMeta, World world)
	{
		if (inputBlock == null)
		{
			SkyMod.instance.logger.error("Need a block input for recipe.");
			return null;
		}

		InfusionRecipe rec = new InfusionRecipe(inputStack,
				inputStack == null ? 0 : inputStack.stackSize, inputBlock,
				inputBlockMeta);
		

		for (InfusionRecipe recipe : Recipes)
		{
			if (rec.isInputRecipeEqualTo(recipe, world))
			{
				return recipe;
			}
		}

		return null;
	}

	public static List<InfusionRecipe> getRecipes()
	{
		return Recipes;
	}

	public static void addRecipe(ItemStack output, ItemStack inputStack,
			Object inputBlock, int inputBlockMeta, int healthReq)
	{
		if (inputBlock == null)
		{
			SkyMod.instance.logger
					.error("Need a block input for recipe. DID NOT ADD RECIPE FOR NULL.");
			return;
		}

		if (!(inputBlock instanceof Block) && !(inputBlock instanceof String))
		{
			SkyMod.instance.logger
					.error("Need a input block as a Block or String for recipe. DID NOT ADD RECIPE FOR "
							+ inputBlock.getClass().getName() + ".");
			return;
		}

		if (output == null)
		{
			SkyMod.instance.logger
					.error("Need a output for recipe. DID NOT ADD RECIPE FOR NULL.");
			return;
		}

		Recipes.add(new InfusionRecipe(output, inputStack, inputBlock,
				inputBlockMeta, healthReq));
	}
}
