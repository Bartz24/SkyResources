package com.bartz24.skyresources.plugin.armorplus;

import com.bartz24.skyresources.alchemy.infusion.InfusionRecipes;
import com.bartz24.skyresources.base.guide.SkyResourcesGuide;
import com.bartz24.skyresources.plugin.IModPlugin;
import com.bartz24.skyresources.registry.ModItems;
import com.bartz24.skyresources.technology.combustion.CombustionRecipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ArmorPlusPlugin implements IModPlugin
{

	@Override
	public void preInit()
	{
		
	}

	@Override
	public void init()
	{
		Item crystal = Item.REGISTRY.getObject(new ResourceLocation("armorplus", "lava_crystal"));

		CombustionRecipes.addRecipe(new ItemStack(crystal), 1200,
				new ItemStack(ModItems.metalCrystal, 16, 0), new ItemStack(Blocks.OBSIDIAN), new ItemStack(Items.LAVA_BUCKET));

		SkyResourcesGuide.addPage("armorplus", "guide.skyresources.misc", "Armor Plus Support",
				new ItemStack(crystal),
				"Sky Resources provides for ways to get the <recipe,,armorplus:lava_crystal:0>");		
	}

	@Override
	public void postInit()
	{
		
	}

	@Override
	public void initRenderers()
	{
		
	}

	@Override
	public String getModID()
	{
		return "armorplus";
	}

}
