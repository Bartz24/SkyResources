package com.bartz24.skyresources.plugin.tconstruct;

import com.bartz24.skyresources.alchemy.infusion.InfusionRecipes;
import com.bartz24.skyresources.base.guide.SkyResourcesGuide;
import com.bartz24.skyresources.technology.combustion.CombustionRecipes;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class TConPlugin
{
	public static void preInit()
	{

	}

	public static void init()
	{
		Item edible = Item.REGISTRY.getObject(new ResourceLocation("tconstruct", "edible"));
		Item sapling = Item.REGISTRY.getObject(new ResourceLocation("tconstruct", "slime_sapling"));
		Block dirt = Block.REGISTRY.getObject(new ResourceLocation("tconstruct", "slime_dirt"));

		CombustionRecipes.addRecipe(new ItemStack(edible, 1, 1), 350,
				new ItemStack(Items.SLIME_BALL), new ItemStack(Items.DYE, 2, 4));
		CombustionRecipes.addRecipe(new ItemStack(edible, 1, 2), 350,
				new ItemStack(Items.SLIME_BALL), new ItemStack(Items.DYE, 2, 5));
		CombustionRecipes.addRecipe(new ItemStack(edible, 2, 4), 760,
				new ItemStack(Items.MAGMA_CREAM), new ItemStack(Items.SLIME_BALL));
		InfusionRecipes.addRecipe(new ItemStack(sapling, 1, 0), new ItemStack(edible, 16, 1),
				"treeSapling", 0, 12);
		InfusionRecipes.addRecipe(new ItemStack(sapling, 1, 1), new ItemStack(edible, 16, 2),
				"treeSapling", 0, 12);
		InfusionRecipes.addRecipe(new ItemStack(sapling, 1, 2), new ItemStack(edible, 16, 4),
				"treeSapling", 0, 16);
		InfusionRecipes.addRecipe(new ItemStack(dirt, 1, 0), new ItemStack(Items.SLIME_BALL, 4),
				Blocks.DIRT, 0, 8);
		InfusionRecipes.addRecipe(new ItemStack(dirt, 1, 1), new ItemStack(edible, 8, 1),
				Blocks.DIRT, 0, 8);
		InfusionRecipes.addRecipe(new ItemStack(dirt, 1, 2), new ItemStack(edible, 8, 2),
				Blocks.DIRT, 0, 8);
		InfusionRecipes.addRecipe(new ItemStack(dirt, 1, 3), new ItemStack(edible, 8, 4),
				Blocks.DIRT, 0, 8);

		SkyResourcesGuide.addPage("tinkers", "guide.skyresources.misc",
				new ItemStack(edible, 1, 2));
	}

	public static void initRenderers()
	{

	}

	public static void postInit()
	{

	}
}
