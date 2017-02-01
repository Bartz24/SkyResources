package com.bartz24.skyresources.plugin.forestry;

import com.bartz24.skyresources.base.guide.SkyResourcesGuide;
import com.bartz24.skyresources.plugin.IModPlugin;
import com.bartz24.skyresources.plugin.forestry.block.BlockBeeAttractor;
import com.bartz24.skyresources.registry.ModBlocks;
import com.bartz24.skyresources.registry.ModRenderers;

import forestry.api.recipes.RecipeManagers;
import forestry.apiculture.PluginApiculture;
import forestry.core.PluginCore;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;

public class ForestryPlugin implements IModPlugin
{

	public static Block beeAttractor;

	public void preInit()
	{
		beeAttractor = ModBlocks.registerBlock(new BlockBeeAttractor("beeAttractor", "BeeAttractor", 2F, 12F));
	}

	public void init()
	{
		RecipeManagers.carpenterManager.addRecipe(25, FluidRegistry.getFluidStack("seed.oil", 500), ItemStack.EMPTY,
				new ItemStack(beeAttractor),
				new Object[] { "XXX", "XYX", "ZZZ", Character.valueOf('X'), "plankWood", Character.valueOf('Y'),
						PluginCore.items.impregnatedCasing, Character.valueOf('Z'), "ingotBronze" });

		SkyResourcesGuide.addPage("forestry", "guide.skyresources.misc",
				new ItemStack(PluginApiculture.getItems().beeQueenGE));
	}

	public void initRenderers()
	{
		ModRenderers.registerItemRenderer(Item.getItemFromBlock(beeAttractor));
	}

	public void postInit()
	{

	}
}
