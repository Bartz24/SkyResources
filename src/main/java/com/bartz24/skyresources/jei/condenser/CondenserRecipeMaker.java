package com.bartz24.skyresources.jei.condenser;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.registry.ModBlocks;
import com.bartz24.skyresources.registry.ModFluids;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class CondenserRecipeMaker
{
	public static List<CondenserRecipeJEI> getRecipes()
	{
		ArrayList<CondenserRecipeJEI> recipes = new ArrayList<CondenserRecipeJEI>();

		for (int i = 0; i < ModFluids.crystalFluidNames().length; i++)
		{
			if (OreDictionary.doesOreNameExist("ingot" + RandomHelper
					.capatilizeString(ModFluids.crystalFluidNames()[i])))
			{
				ItemStack ingot = OreDictionary
						.getOres("ingot" + RandomHelper.capatilizeString(
								ModFluids.crystalFluidNames()[i]))
						.get(0).copy();
				ingot.stackSize = 1;
				CondenserRecipeJEI addRecipe = new CondenserRecipeJEI(ingot,
						ModBlocks.crystalFluidBlocks.get(i).getDefaultState(),
						ModFluids.crystalFluidRarity()[i]
								* ConfigOptions.condenserProcessTimeBase);
				recipes.add(addRecipe);
				CondenserRecipeJEI addDirtyRecipe = new CondenserRecipeJEI(
						ingot,
						ModBlocks.dirtyCrystalFluidBlocks.get(i)
								.getDefaultState(),
						ModFluids.crystalFluidRarity()[i]
								* ConfigOptions.condenserProcessTimeBase * 10);
				recipes.add(addDirtyRecipe);
			}
		}

		return recipes;
	}
}
