package com.bartz24.skyresources.plugin.extremereactors;

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

public class ExtremeReactorsPlugin implements IModPlugin
{

	public void preInit()
	{

	}

	public void init()
	{
		Item dust = Item.REGISTRY.getObject(new ResourceLocation("bigreactors", "dustmetals"));
		Item ingot = Item.REGISTRY.getObject(new ResourceLocation("bigreactors", "ingotmetals"));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(dust, 5), 1200, new ArrayList<Object>(
				Arrays.asList(new ItemStack(Items.REDSTONE, 2), new ItemStack(Items.GLOWSTONE_DUST, 1), new ItemStack(ModItems.techComponent, 2, 1))));

		SkyResourcesGuide.addPage("extremereactors", "guide.skyresources.misc", new ItemStack(ingot, 1));
	}

	public void initRenderers()
	{

	}

	public void postInit()
	{

	}
}
