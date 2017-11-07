package com.bartz24.skyresources.plugin.integdyn;

import java.util.ArrayList;
import java.util.Arrays;

import com.bartz24.skyresources.base.guide.SkyResourcesGuide;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.plugin.IModPlugin;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class IntegratedDynamicsPlugin implements IModPlugin
{

	public void preInit()
	{

	}

	public void init()
	{
		Item berries = Item.REGISTRY.getObject(new ResourceLocation("integrateddynamics", "menril_berries"));
		Item sapling = Item.REGISTRY.getObject(new ResourceLocation("integrateddynamics", "menril_sapling"));

		if (ConfigOptions.pluginSettings.integratedDynamicsSettings.addMenrilRecipes)
		{
			ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(berries, 1), 12, new ArrayList<Object>(
					Arrays.asList(new ItemStack(Items.DYE, 4, 12), new ItemStack(Blocks.RED_MUSHROOM))));
			ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(sapling, 1), 12, new ArrayList<Object>(
					Arrays.asList(new ItemStack(berries, 4), new ItemStack(Blocks.SAPLING, 1, 2))));
		}
		SkyResourcesGuide.addPage("integdyn", "guide.skyresources.misc", new ItemStack(sapling, 1));
	}

	public void initRenderers()
	{

	}

	public void postInit()
	{

	}
}
