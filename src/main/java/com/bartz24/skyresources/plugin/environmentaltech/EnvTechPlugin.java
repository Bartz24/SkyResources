package com.bartz24.skyresources.plugin.environmentaltech;

import java.util.ArrayList;
import java.util.Arrays;

import com.bartz24.skyresources.base.guide.SkyResourcesGuide;
import com.bartz24.skyresources.plugin.IModPlugin;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;
import com.bartz24.skyresources.registry.ModBlocks;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class EnvTechPlugin implements IModPlugin
{
	public void preInit()
	{

	}

	public void init()
	{
		Item alabaster = Item.REGISTRY.getObject(new ResourceLocation("environmentaltech", "alabaster"));
		Item basalt = Item.REGISTRY.getObject(new ResourceLocation("environmentaltech", "basalt"));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(alabaster, 2), 500,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.QUARTZ), new ItemStack(Blocks.STONE, 2, 3))));
		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(basalt, 4), 800, new ArrayList<Object>(
				Arrays.asList(new ItemStack(Blocks.OBSIDIAN), new ItemStack(Blocks.STONE, 2, 0))));

		SkyResourcesGuide.addPage("envtech", "guide.skyresources.misc", new ItemStack(alabaster));
	}

	public void initRenderers()
	{

	}

	public void postInit()
	{

	}
}
