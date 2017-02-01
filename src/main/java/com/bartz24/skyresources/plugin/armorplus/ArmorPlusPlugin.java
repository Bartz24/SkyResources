package com.bartz24.skyresources.plugin.armorplus;

import java.util.ArrayList;
import java.util.Arrays;

import com.bartz24.skyresources.base.guide.SkyResourcesGuide;
import com.bartz24.skyresources.plugin.IModPlugin;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;
import com.bartz24.skyresources.registry.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ArmorPlusPlugin implements IModPlugin
{

	public void preInit()
	{

	}

	public void init()
	{
		Item crystal = Item.REGISTRY.getObject(new ResourceLocation("armorplus", "lava_crystal"));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(crystal), 1200,
				new ArrayList<Object>(Arrays.asList(new ItemStack(ModItems.metalCrystal, 16, 0),
						new ItemStack(Blocks.OBSIDIAN), new ItemStack(Items.LAVA_BUCKET))));

		SkyResourcesGuide.addPage("armorplus", "guide.skyresources.misc", new ItemStack(crystal));
	}

	public void postInit()
	{

	}

	public void initRenderers()
	{

	}

}
