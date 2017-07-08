package com.bartz24.skyresources.plugin.actuallyadditions;

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
import net.minecraftforge.common.MinecraftForge;

public class ActAddPlugin implements IModPlugin
{

	public void preInit()
	{

	}

	public void init()
	{
		Item canola = Item.REGISTRY.getObject(new ResourceLocation("actuallyadditions", "item_canola_seed"));
		Item coffee = Item.REGISTRY.getObject(new ResourceLocation("actuallyadditions", "item_coffee_seed"));
		Item flax = Item.REGISTRY.getObject(new ResourceLocation("actuallyadditions", "item_flax_seed"));
		Item rice = Item.REGISTRY.getObject(new ResourceLocation("actuallyadditions", "item_rice_seed"));
		Item misc = Item.REGISTRY.getObject(new ResourceLocation("actuallyadditions", "item_misc"));
		
		MinecraftForge.addGrassSeed(new ItemStack(canola), 10);
		MinecraftForge.addGrassSeed(new ItemStack(coffee), 10);
		MinecraftForge.addGrassSeed(new ItemStack(flax), 10);
		MinecraftForge.addGrassSeed(new ItemStack(rice), 10);

		SkyResourcesGuide.addPage("actadd", "guide.skyresources.misc", new ItemStack(misc, 1, 5));
	}

	public void postInit()
	{

	}

	public void initRenderers()
	{

	}

}
