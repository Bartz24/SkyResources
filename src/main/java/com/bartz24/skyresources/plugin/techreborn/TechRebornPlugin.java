package com.bartz24.skyresources.plugin.techreborn;

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

public class TechRebornPlugin implements IModPlugin
{

	public void preInit()
	{

	}

	public void init()
	{
		Item part = Item.REGISTRY.getObject(new ResourceLocation("techreborn", "part"));
		Item sapling = Item.REGISTRY.getObject(new ResourceLocation("techreborn", "rubber_sapling"));

		if (ConfigOptions.pluginSettings.techRebornSettings.addRubberRecipes)
		{
			ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(part, 1, 31), 350, new ArrayList<Object>(
					Arrays.asList(new ItemStack(Items.SLIME_BALL, 2), new ItemStack(ModItems.baseComponent, 4, 0))));
			ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(sapling, 1), 12, new ArrayList<Object>(
					Arrays.asList(new ItemStack(part, 4, 31), new ItemStack(Blocks.SAPLING, 1, 1))));
		}
		SkyResourcesGuide.addPage("techreborn", "guide.skyresources.misc", new ItemStack(part, 1, 31));
	}

	public void initRenderers()
	{

	}

	public void postInit()
	{

	}
}
