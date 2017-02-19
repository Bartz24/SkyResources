package com.bartz24.skyresources.plugin.ctweaker;

import java.util.ArrayList;
import java.util.Arrays;

import com.bartz24.skyresources.base.guide.SkyResourcesGuide;
import com.bartz24.skyresources.plugin.IModPlugin;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;
import com.bartz24.skyresources.registry.ModBlocks;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CraftTweakerPlugin implements IModPlugin
{
	public void preInit()
	{

	}

	public void init()
	{
		//MinetweakerPlugin.init(e);
		SkyResourcesGuide.addPage("minetweaker", "guide.skyresources.misc", new ItemStack(Blocks.CRAFTING_TABLE));
	}

	public void initRenderers()
	{

	}

	public void postInit()
	{

	}
}
