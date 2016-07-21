package com.bartz24.skyresources.plugin.environmentaltech;

import com.bartz24.skyresources.alchemy.infusion.InfusionRecipes;
import com.bartz24.skyresources.base.guide.SkyResourcesGuide;
import com.bartz24.skyresources.registry.ModBlocks;
import com.bartz24.skyresources.technology.combustion.CombustionRecipes;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class EnvTechPlugin
{
	public static void preInit()
	{

	}

	public static void init()
	{
		Item alabaster = Item.REGISTRY.getObject(new ResourceLocation("environmentaltech", "alabaster"));
		Item hardenedStone = Item.REGISTRY.getObject(new ResourceLocation("environmentaltech", "hardened_stone"));
		Item basalt = Item.REGISTRY.getObject(new ResourceLocation("environmentaltech", "basalt"));

		CombustionRecipes.addRecipe(new ItemStack(alabaster, 2), 500,
				new ItemStack(Items.QUARTZ), new ItemStack(Blocks.STONE, 2, 3));
		CombustionRecipes.addRecipe(new ItemStack(hardenedStone, 4), 300,
				new ItemStack(ModBlocks.compressedStone), new ItemStack(Blocks.STONE, 2, 0));
		CombustionRecipes.addRecipe(new ItemStack(basalt, 4), 800,
				new ItemStack(Blocks.OBSIDIAN), new ItemStack(Blocks.STONE, 2, 0));

		SkyResourcesGuide.addPage("envtech", "guide.skyresources.misc", "Environmental Tech Support",
				new ItemStack(alabaster),
				"Sky Resources provides for ways to get Environmental Tech stone types. "
						+ "\n \n <recipe,,environmentaltech:alabaster:0> , <recipe,,environmentaltech:hardened_stone:0> , and <recipe,,environmentaltech:basalt:0>"
						+ "  are obtainable. ");
	}

	public static void initRenderers()
	{

	}

	
	public static void postInit()
	{
		
	}
}
