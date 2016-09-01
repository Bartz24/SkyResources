package com.bartz24.skyresources.plugin.ic2;

import com.bartz24.skyresources.alchemy.infusion.InfusionRecipes;
import com.bartz24.skyresources.base.guide.SkyResourcesGuide;
import com.bartz24.skyresources.registry.ModItems;
import com.bartz24.skyresources.technology.combustion.CombustionRecipes;

import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.Recipes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class IC2Plugin
{
	public static void preInit()
	{

	}

	public static void init()
	{
		Item misc = Item.REGISTRY.getObject(new ResourceLocation("IC2", "misc_resource"));
		Item dust = Item.REGISTRY.getObject(new ResourceLocation("IC2", "dust"));
		Item sapling = Item.REGISTRY.getObject(new ResourceLocation("IC2", "sapling"));
		Item nuclear = Item.REGISTRY.getObject(new ResourceLocation("IC2", "nuclear"));
		Item laser = Item.REGISTRY.getObject(new ResourceLocation("IC2", "mining_laser"));
		Item crafting = Item.REGISTRY.getObject(new ResourceLocation("IC2", "crafting"));
		NBTTagCompound centrifugeTag = new NBTTagCompound();

		CombustionRecipes.addRecipe(new ItemStack(misc, 1, 4), 350,
				new ItemStack(Items.SLIME_BALL, 2), new ItemStack(crafting, 4, 20));
		InfusionRecipes.addRecipe(new ItemStack(sapling, 1), new ItemStack(misc, 4, 4),
				Blocks.SAPLING, 1, 18);
		Recipes.macerator.addRecipe(
				new RecipeInputItemStack(new ItemStack(ModItems.techComponent, 1, 0)), null, false,
				new ItemStack(dust, 1, 15));

		centrifugeTag.setInteger("minHeat", 1000);
		Recipes.centrifuge.addRecipe(
				new RecipeInputItemStack(new ItemStack(ModItems.techComponent, 2, 1)),
				centrifugeTag, false, new ItemStack(nuclear, 1, 5), new ItemStack(nuclear, 4, 2));

		SkyResourcesGuide.addPage("ic2", "guide.skyresources.misc", new ItemStack(laser));
	}

	public static void initRenderers()
	{

	}

	public static void postInit()
	{

	}
}
