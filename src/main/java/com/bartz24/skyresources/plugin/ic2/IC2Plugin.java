package com.bartz24.skyresources.plugin.ic2;

import java.util.ArrayList;
import java.util.Arrays;

import com.bartz24.skyresources.base.guide.SkyResourcesGuide;
import com.bartz24.skyresources.plugin.IModPlugin;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class IC2Plugin implements IModPlugin
{
	public void preInit()
	{

	}

	public void init()
	{
		Item misc = Item.REGISTRY.getObject(new ResourceLocation("ic2", "misc_resource"));
		Item sapling = Item.REGISTRY.getObject(new ResourceLocation("ic2", "sapling"));
		Item laser = Item.REGISTRY.getObject(new ResourceLocation("ic2", "mining_laser"));
		Item crafting = Item.REGISTRY.getObject(new ResourceLocation("ic2", "crafting"));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(misc, 1, 4), 350, new ArrayList<Object>(
				Arrays.asList(new ItemStack(Items.SLIME_BALL, 2), new ItemStack(crafting, 4, 20))));
		ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(sapling, 1), 18,
				new ArrayList<Object>(Arrays.asList(new ItemStack(misc, 4, 4), new ItemStack(Blocks.SAPLING, 1, 1))));

		SkyResourcesGuide.addPage("ic2", "guide.skyresources.misc", new ItemStack(laser));
	}

	public void initRenderers()
	{

	}

	public void postInit()
	{

	}
}
