package com.bartz24.skyresources.jei.condenser;

import java.util.ArrayList;
import java.util.Collections;
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

		for (int i = 0; i < ModFluids.crystalFluids.size(); i++)
		{
			if (OreDictionary
					.getOres("ingot" + RandomHelper
							.capatilizeString(ModFluids.crystalFluidInfos()[i].name)).size() > 0)
			{
				ItemStack ingot = OreDictionary
						.getOres("ingot" + RandomHelper
								.capatilizeString(ModFluids.crystalFluidInfos()[i].name))
						.get(0).copy();
				ingot.stackSize = 1;
				CondenserRecipeJEI addRecipe = new CondenserRecipeJEI(ingot,
						ModBlocks.crystalFluidBlocks.get(i).getDefaultState(),
						ModFluids.crystalFluidInfos()[i].rarity
								* ConfigOptions.condenserProcessTimeBase);
				recipes.add(addRecipe);
				CondenserRecipeJEI addDirtyRecipe = new CondenserRecipeJEI(ingot,
						ModBlocks.dirtyCrystalFluidBlocks.get(i).getDefaultState(),
						ModFluids.crystalFluidInfos()[i].rarity
								* ConfigOptions.condenserProcessTimeBase * 10);
				recipes.add(addDirtyRecipe);
			}
		}

		for (int i = 0; i < ModFluids.moltenCrystalFluids.size(); i++)
		{
			if (OreDictionary
					.getOres("ingot" + RandomHelper
							.capatilizeString(ModFluids.moltenCrystalFluidInfos()[i].name)).size() > 0)
			{
				ItemStack ingot = OreDictionary
						.getOres("ingot" + RandomHelper
								.capatilizeString(ModFluids.moltenCrystalFluidInfos()[i].name))
						.get(0).copy();
				ingot.stackSize = 1;
				CondenserRecipeJEI addRecipe = new CondenserRecipeJEI(ingot,
						ModBlocks.moltenCrystalFluidBlocks.get(i).getDefaultState(),
						ModFluids.moltenCrystalFluidInfos()[i].rarity
								* ConfigOptions.condenserProcessTimeBase * 20);
				recipes.add(addRecipe);
			}
		}

		return recipes;
	}
}
