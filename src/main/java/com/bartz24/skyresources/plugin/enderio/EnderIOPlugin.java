package com.bartz24.skyresources.plugin.enderio;

import com.bartz24.skyresources.base.guide.SkyResourcesGuide;
import com.bartz24.skyresources.registry.ModBlocks;
import com.bartz24.skyresources.registry.ModItems;
import com.bartz24.skyresources.technology.combustion.CombustionRecipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;

public class EnderIOPlugin
{

	public static void preInit()
	{

	}

	public static void init()
	{
		Item spawner = Item.REGISTRY
				.getObject(new ResourceLocation("enderio", "itemBrokenSpawner"));
		Item vial = Item.REGISTRY.getObject(new ResourceLocation("enderio", "itemSoulVessel"));
		Item darkBars = Item.REGISTRY
				.getObject(new ResourceLocation("enderio", "blockDarkIronBars"));

		ItemStack zombieSpawner = new ItemStack(spawner);

		CombustionRecipes.addRecipe(zombieSpawner, 2400, new ItemStack(Items.ROTTEN_FLESH, 16),
				new ItemStack(Items.BONE, 16), new ItemStack(Items.GUNPOWDER, 16),
				new ItemStack(darkBars, 32), new ItemStack(Items.SKULL, 4, 2),
				new ItemStack(Items.DRAGON_BREATH, 4), new ItemStack(ModBlocks.darkMatterBlock));

		SkyResourcesGuide.addPage("enderio", "guide.skyresources.misc",
				zombieSpawner);
	}

	public static void postInit()
	{

	}

	public static void initRenderers()
	{

	}
}
