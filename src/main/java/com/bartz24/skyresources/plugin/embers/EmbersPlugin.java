package com.bartz24.skyresources.plugin.embers;

import java.util.ArrayList;
import java.util.Arrays;

import com.bartz24.skyresources.base.guide.SkyResourcesGuide;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.plugin.IModPlugin;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;
import com.bartz24.skyresources.registry.ModItems;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class EmbersPlugin implements IModPlugin
{

	public void preInit()
	{

	}

	public void init()
	{
		Item shard = Item.REGISTRY.getObject(new ResourceLocation("embers", "shard_ember"));

		if (ConfigOptions.pluginSettings.embersSettings.addEmberShardRecipe)
			ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(shard, 7), 1400, new ArrayList<Object>(Arrays
					.asList(new ItemStack(ModItems.alchemyComponent, 2, 1), new ItemStack(Items.BLAZE_POWDER, 13))));

		SkyResourcesGuide.addPage("embers", "guide.skyresources.misc", new ItemStack(shard));
	}

	public void postInit()
	{

	}

	public void initRenderers()
	{

	}

}
