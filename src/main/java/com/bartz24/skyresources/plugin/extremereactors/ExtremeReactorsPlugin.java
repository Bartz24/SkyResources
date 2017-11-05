package com.bartz24.skyresources.plugin.extremereactors;

import java.util.ArrayList;
import java.util.Arrays;

import com.bartz24.skyresources.alchemy.item.ItemOreAlchDust;
import com.bartz24.skyresources.base.guide.SkyResourcesGuide;
import com.bartz24.skyresources.plugin.IModPlugin;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;
import com.bartz24.skyresources.registry.ModFluids;
import com.bartz24.skyresources.registry.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class ExtremeReactorsPlugin implements IModPlugin
{

	public void preInit()
	{

	}

	public void init()
	{
		ItemStack ingot = OreDictionary.getOres("ingotYellorium").get(0).copy();
		ingot.setCount(1);
		
		ProcessRecipeManager.condenserRecipes.addRecipe(ingot,
				(float) Math.pow(ItemOreAlchDust.oreInfos.get(22).rarity * 1.05f, 1.4f) * 50f, Arrays.asList(
						new ItemStack(ModItems.oreAlchDust, 1, 22), new FluidStack(ModFluids.crystalFluid, 1000)));
		ItemStack ore = OreDictionary.getOres("oreYellorium").get(0).copy();
		ore.setCount(1);
		ProcessRecipeManager.condenserRecipes.addRecipe(ore,
				(float) Math.pow(ItemOreAlchDust.oreInfos.get(22).rarity * 1.05f, 1.8f) * 50f,
				Arrays.asList(new ItemStack(ModItems.oreAlchDust, 1, 22), new ItemStack(Blocks.STONE)));
		ItemStack dust = OreDictionary.getOres("dustYellorium").get(0).copy();
		dust.setCount(1);
		ProcessRecipeManager.cauldronCleanRecipes.addRecipe(dust,
				1f / (((float) Math.pow((ItemOreAlchDust.oreInfos.get(22).rarity + 2.5f), 3.1f)) * 14.4f),
				new ItemStack(ModItems.techComponent, 1, 0));

		ProcessRecipeManager.fusionRecipes.addRecipe(new ItemStack(ModItems.oreAlchDust, 1, 22),
				ItemOreAlchDust.oreInfos.get(22).rarity * 0.0008f,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.DYE, 1, 11),
						new ItemStack(ModItems.techComponent, 1, 1))));
		ProcessRecipeManager.fusionRecipes.addRecipe(new ItemStack(ModItems.oreAlchDust, 1, 22),
				ItemOreAlchDust.oreInfos.get(22).rarity * 0.0021f, new ArrayList<Object>(Arrays.asList(dust,
						new ItemStack(ModItems.techComponent, 1, 1))));
		
		SkyResourcesGuide.addPage("extremereactors", "guide.skyresources.misc", ingot);
	}

	public void initRenderers()
	{

	}

	public void postInit()
	{

	}
}
