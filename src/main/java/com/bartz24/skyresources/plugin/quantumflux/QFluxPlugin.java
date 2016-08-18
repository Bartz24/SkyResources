package com.bartz24.skyresources.plugin.quantumflux;

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

public class QFluxPlugin
{
	public static void preInit()
	{

	}

	public static void init()
	{
		Item graphite = Item.REGISTRY.getObject(new ResourceLocation("quantumflux", "graphiteDust"));

		CombustionRecipes.addRecipe(new ItemStack(graphite, 2), 700,
				new ItemStack(Items.GUNPOWDER, 4), new ItemStack(ModBlocks.compressedCoalBlock));

		SkyResourcesGuide.addPage("quantumflux", "guide.skyresources.misc", "Quantum Flux Support",
				new ItemStack(graphite),
				"Sky Resources provides for ways to get Quantum Flux items. "
						+ "\n \n <recipe,,quantumflux:graphiteDust:0> is obtainable. ");
	}

	public static void initRenderers()
	{

	}

	
	public static void postInit()
	{
		
	}
}
