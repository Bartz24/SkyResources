package com.bartz24.skyresources.jei.cauldron;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.References;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.registry.ModBlocks;
import com.bartz24.skyresources.registry.ModFluids;
import com.bartz24.skyresources.registry.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class CauldronRecipeMaker
{
	public static List<CauldronRecipeJEI> getRecipes()
	{
		ArrayList<CauldronRecipeJEI> recipes = new ArrayList<CauldronRecipeJEI>();

		for (int i = 0; i < ModItems.gemList.size(); i++)
		{
			List<ItemStack> items = OreDictionary.getOres("gem"
					+ RandomHelper.capatilizeString(ModItems.gemList.get(i).name));
			if (items.size() > 0)
			{
				CauldronRecipeJEI addRecipe = new CauldronRecipeJEI(
						items.get(0), new ItemStack(ModItems.dirtyGem, 1, i));
						
				recipes.add(addRecipe);
			}
		}

		return recipes;
	}
}
