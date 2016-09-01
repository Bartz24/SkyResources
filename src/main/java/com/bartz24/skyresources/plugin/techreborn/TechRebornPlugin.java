package com.bartz24.skyresources.plugin.techreborn;

import com.bartz24.skyresources.alchemy.infusion.InfusionRecipes;
import com.bartz24.skyresources.base.guide.SkyResourcesGuide;
import com.bartz24.skyresources.registry.ModItems;
import com.bartz24.skyresources.technology.combustion.CombustionRecipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class TechRebornPlugin
{

	public static void preInit()
	{

	}

	public static void init()
	{
		Item part = Item.REGISTRY.getObject(new ResourceLocation("techreborn", "part"));
		Item sapling = Item.REGISTRY.getObject(new ResourceLocation("techreborn", "rubberSapling"));

		CombustionRecipes.addRecipe(new ItemStack(part, 1, 31), 350,
				new ItemStack(Items.SLIME_BALL, 2), new ItemStack(ModItems.baseComponent, 4, 2));
		InfusionRecipes.addRecipe(new ItemStack(sapling, 1), new ItemStack(part, 4, 31),
				Blocks.SAPLING, 1, 18);

		SkyResourcesGuide.addPage("techreborn", "guide.skyresources.misc",
				new ItemStack(part, 1, 31));
	}

	public static void initRenderers()
	{

	}

	public static void postInit()
	{

	}
}
