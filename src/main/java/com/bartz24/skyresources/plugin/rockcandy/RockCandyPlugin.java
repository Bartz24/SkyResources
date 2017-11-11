package com.bartz24.skyresources.plugin.rockcandy;

import java.util.ArrayList;
import java.util.Arrays;

import com.bartz24.skyresources.base.guide.SkyResourcesGuide;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.plugin.IModPlugin;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;
import com.bartz24.skyresources.registry.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RockCandyPlugin implements IModPlugin
{

	public void preInit()
	{

	}

	public void init()
	{
		Item candy = Item.REGISTRY.getObject(new ResourceLocation("rockcandy", "raw_rock_candy"));

		if (ConfigOptions.pluginSettings.rockCandySettings.addRockCandyRecipe)
			ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(candy, 4), 800, new ArrayList<Object>(Arrays
					.asList(new ItemStack(ModItems.alchemyComponent, 3, 1), new ItemStack(Items.SUGAR, 5), new ItemStack(Items.DYE, 3, 1))));

		SkyResourcesGuide.addPage("rockcandy", "guide.skyresources.misc", new ItemStack(candy));
	}

	public void postInit()
	{

	}

	public void initRenderers()
	{

	}

}
