package com.bartz24.skyresources.jei.condenser;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.alchemy.fluid.FluidRegisterInfo.CrystalFluidType;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.registry.ModFluids;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class CondenserRecipeMaker
{
	public static List<CondenserRecipeJEI> getRecipes()
	{
		ArrayList<CondenserRecipeJEI> recipes = new ArrayList<CondenserRecipeJEI>();

		for (int i = 0; i < ModFluids.crystalFluids.size(); i++)
		{
			if (OreDictionary.getOres("ingot" + RandomHelper.capatilizeString(ModFluids.crystalFluidInfos()[i].name))
					.size() > 0)
			{
				ItemStack ingot = OreDictionary
						.getOres("ingot" + RandomHelper.capatilizeString(ModFluids.crystalFluidInfos()[i].name)).get(0)
						.copy();
				ingot.setCount(1);
				CondenserRecipeJEI addRecipe = new CondenserRecipeJEI(ingot,
						new FluidStack(ModFluids.crystalFluids.get(i), 1000),
						ModFluids.crystalFluidInfos()[i].rarity * ConfigOptions.condenserProcessTimeBase
								* (ModFluids.crystalFluidInfos()[i].type == CrystalFluidType.MOLTEN ? 20 : 1));
				recipes.add(addRecipe);
			}
		}

		return recipes;
	}
}
