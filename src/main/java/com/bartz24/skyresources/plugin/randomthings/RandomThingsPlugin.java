package com.bartz24.skyresources.plugin.randomthings;

import com.bartz24.skyresources.base.guide.SkyResourcesGuide;
import com.bartz24.skyresources.technology.combustion.CombustionRecipes;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RandomThingsPlugin
{

	public static void preInit()
	{

	}

	public static void init()
	{

		CombustionRecipes.addRecipe(new ItemStack(Items.CHORUS_FRUIT), 2350,
				new ItemStack(Items.DYE, 8, 5), new ItemStack(Items.ENDER_EYE, 2), new ItemStack(Items.NETHER_STAR));

		SkyResourcesGuide.addPage("temporaryRandomThings", "guide.skyresources.misc", "Random Things End Support",
				new ItemStack(Items.CHORUS_FRUIT),
				"Sky Resources provides for ways to get the <recipe,,minecraft:chorus_fruit:0> for the random things end portal. "
				+ "\n NOTE: This is temporary until Sky Resources adds in other resources and processes.");
	}

	public static void initRenderers()
	{

	}

	public static void postInit()
	{
		
	}
}
