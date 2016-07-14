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
		beeAttractor = ModBlocks.registerBlock(
				new BlockBeeAttractor("beeAttractor", "BeeAttractor", 2F, 12F));
	}

	public void init()
	{
		RecipeManagers.carpenterManager.addRecipe(25,
				FluidRegistry.getFluidStack("seed.oil", 500), null,
				new ItemStack(beeAttractor), new Object[]
				{ "XXX", "XYX", "ZZZ", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), PluginCore.items.impregnatedCasing, Character.valueOf('Z'), "ingotBronze" });
		
		
		
		SkyResourcesGuide.addPage("forestry", "guide.skyresources.misc",
				"Forestry Support", new ItemStack(PluginApiculture.items.beeQueenGE),
				"Sky Resources provides a way to get <recipe,,forestry:beePrincessGE:0> and <recipe,,forestry:beeDroneGE:0> . "
				+ "\n This is done by making a <recipe,,skyresources:BeeAttractor:0> . "
				+ "\n The <recipe,,skyresources:BeeAttractor:0> requires RF and <recipe,,forestry:fluid.seed.oil:0> . "
				+ "\n Every 10 seconds, there is a chance for a <recipe,,forestry:beePrincessGE:0> or <recipe,,forestry:beeDroneGE:0> to be in the <recipe,,skyresources:BeeAttractor:0> . "
				+ "\n The species depends on the biome, just like hives. "
				+ "\n Note that a <recipe,,forestry:beePrincessGE:0> is rarer than a <recipe,,forestry:beeDroneGE:0> .");
	}

	public void initRenderers()
	{
		ModRenderers.registerItemRenderer(Item.getItemFromBlock(beeAttractor));
	}

	@Override
	public void postInit()
	{
		
	}

	@Override
	public String getModID()
	{
		return "forestry";
	}
}
