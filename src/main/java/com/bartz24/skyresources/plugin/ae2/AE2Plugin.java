package com.bartz24.skyresources.plugin.ae2;

import java.util.List;

import com.bartz24.skyresources.base.guide.SkyResourcesGuide;
import com.bartz24.skyresources.registry.ModItems;
import com.bartz24.skyresources.technology.combustion.CombustionRecipes;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

public class AE2Plugin
{

	public static void preInit()
	{

	}

	public static void init()
	{
		Item material = Item.REGISTRY.getObject(new ResourceLocation("appliedenergistics2", "material"));
		Block skystone = Block.REGISTRY.getObject(new ResourceLocation("appliedenergistics2", "sky_stone_block"));
		
		CombustionRecipes.addRecipe(new ItemStack(material, 4, 0), 700, new ItemStack(ModItems.metalCrystal, 1, 19),
				new ItemStack(Items.FLINT, 3));

		CombustionRecipes.addRecipe(new ItemStack(material, 1, 1), 800, new ItemStack(material, 1, 0),
				new ItemStack(Items.REDSTONE, 4));

		CombustionRecipes.addRecipe(new ItemStack(skystone), 1400, new ItemStack(Blocks.OBSIDIAN),
				new ItemStack(ModItems.baseComponent, 1, 5));

		CombustionRecipes.addRecipe(new ItemStack(material, 1, 15), 1200,
				new ItemStack(Blocks.IRON_BLOCK), new ItemStack(Items.GOLD_INGOT));
		CombustionRecipes.addRecipe(new ItemStack(material, 1, 14), 1200,
				new ItemStack(Blocks.IRON_BLOCK), new ItemStack(Items.DIAMOND));
		CombustionRecipes.addRecipe(new ItemStack(material, 1, 13), 1200,
				new ItemStack(Blocks.IRON_BLOCK), new ItemStack(material, 1, 10));
		List<ItemStack> silicons = OreDictionary.getOres("itemSilicon");
		if(silicons.size() > 0)
		CombustionRecipes.addRecipe(new ItemStack(material, 1, 19), 1200,
				new ItemStack(Blocks.IRON_BLOCK), silicons.get(0).copy());
		SkyResourcesGuide.addPage("ae2", "guide.skyresources.misc", new ItemStack(material));
	}

	public static void postInit()
	{

	}

	public static void initRenderers()
	{

	}

}
