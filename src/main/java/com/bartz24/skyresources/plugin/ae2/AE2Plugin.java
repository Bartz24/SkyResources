package com.bartz24.skyresources.plugin.ae2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bartz24.skyresources.base.guide.SkyResourcesGuide;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.plugin.IModPlugin;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;
import com.bartz24.skyresources.registry.ModItems;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

public class AE2Plugin implements IModPlugin
{

	public void preInit()
	{

	}

	public void init()
	{
		Item material = Item.REGISTRY.getObject(new ResourceLocation("appliedenergistics2", "material"));
		Block skystone = Block.REGISTRY.getObject(new ResourceLocation("appliedenergistics2", "sky_stone_block"));

		if (ConfigOptions.pluginSettings.appliedEnergisticsSettings.addPressRecipes)
		{
			ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(material, 1, 1), 900, new ArrayList<Object>(
					Arrays.asList(new ItemStack(material, 1, 0), new ItemStack(Items.REDSTONE, 4))));
			ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(skystone), 2200, new ArrayList<Object>(
					Arrays.asList(new ItemStack(Blocks.OBSIDIAN), new ItemStack(ModItems.alchemyComponent, 4, 6))));

			ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(material, 1, 15), 1200,
					new ArrayList<Object>(
							Arrays.asList(new ItemStack(Blocks.IRON_BLOCK), new ItemStack(Items.GOLD_INGOT))));
			ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(material, 1, 14), 1200,
					new ArrayList<Object>(
							Arrays.asList(new ItemStack(Blocks.IRON_BLOCK), new ItemStack(Items.DIAMOND))));
			ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(material, 1, 13), 1200,
					new ArrayList<Object>(
							Arrays.asList(new ItemStack(Blocks.IRON_BLOCK), new ItemStack(material, 1, 10))));

			List<ItemStack> silicons = OreDictionary.getOres("itemSilicon");
			if (silicons.size() > 0)
				ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(material, 1, 19), 1200,
						new ArrayList<Object>(Arrays.asList(new ItemStack(Blocks.IRON_BLOCK), silicons.get(0).copy())));
		}
		SkyResourcesGuide.addPage("ae2", "guide.skyresources.misc", new ItemStack(material));
	}

	public void postInit()
	{

	}

	public void initRenderers()
	{

	}

}
