package com.bartz24.skyresources.plugin.dracevo;

import java.util.ArrayList;
import java.util.Arrays;

import com.bartz24.skyresources.alchemy.item.ItemOreAlchDust;
import com.bartz24.skyresources.base.guide.SkyResourcesGuide;
import com.bartz24.skyresources.plugin.IModPlugin;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;
import com.bartz24.skyresources.registry.ModCrafting;
import com.bartz24.skyresources.registry.ModFluids;
import com.bartz24.skyresources.registry.ModItems;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class DEPlugin implements IModPlugin
{

	public void preInit()
	{

	}

	public void init()
	{
		ItemStack ingotDraconium = OreDictionary.getOres("ingotDraconium").get(0).copy();
		ingotDraconium.setCount(1);
		ProcessRecipeManager.condenserRecipes.addRecipe(ingotDraconium,
				(float) Math.pow(1.4f, ItemOreAlchDust.oreInfos.get(13).rarity) * 50f, Arrays.asList(
						new ItemStack(ModItems.oreAlchDust, 1, 13), new FluidStack(ModFluids.crystalFluid, 1000)));
		ItemStack oreDraconium = new ItemStack(
				Block.REGISTRY.getObject(new ResourceLocation("draconicevolution", "draconium_ore")), 1, 2);
		oreDraconium.setCount(1);
		ProcessRecipeManager.condenserRecipes.addRecipe(oreDraconium,
				(float) Math.pow(1.6f, ItemOreAlchDust.oreInfos.get(13).rarity) * 50f,
				Arrays.asList(new ItemStack(ModItems.oreAlchDust, 1, 13), new ItemStack(Blocks.END_STONE)));
		ItemStack dustDraconium = OreDictionary.getOres("dustDraconium").get(0).copy();
		dustDraconium.setCount(1);
		ProcessRecipeManager.cauldronCleanRecipes.addRecipe(dustDraconium,
				1f / (((float) Math.pow((ItemOreAlchDust.oreInfos.get(13).rarity + 2.5f), 1.2f)) * 6.3f),
				new ItemStack(ModItems.techComponent, 1, 3));

		ProcessRecipeManager.fusionRecipes.addRecipe(new ItemStack(ModItems.oreAlchDust, 1, 13),
				ItemOreAlchDust.oreInfos.get(13).rarity * 0.0008f,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.ENDER_EYE),
						ModCrafting.getOreItemDust(ItemOreAlchDust.oreInfos.get(13).rarity))));
		ProcessRecipeManager.fusionRecipes.addRecipe(new ItemStack(ModItems.oreAlchDust, 1, 13),
				ItemOreAlchDust.oreInfos.get(13).rarity * 0.0021f, new ArrayList<Object>(Arrays.asList(dustDraconium,
						ModCrafting.getOreItemDust(ItemOreAlchDust.oreInfos.get(13).rarity))));
		SkyResourcesGuide.addPage("dracevo", "guide.skyresources.misc", ingotDraconium);
	}

	public void postInit()
	{

	}

	public void initRenderers()
	{

	}

}
