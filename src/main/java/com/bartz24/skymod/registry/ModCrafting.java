package com.bartz24.skymod.registry;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import com.bartz24.skymod.alchemy.infusion.InfusionRecipes;
import com.bartz24.skymod.alchemy.item.AlchemyItemComponent;
import com.bartz24.skymod.base.HeatSources;
import com.bartz24.skymod.technology.combustion.CombustionRecipes;

public class ModCrafting
{
	public static void init()
	{
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.cactusFruit, 1),
				new Object[]
				{ new ItemStack(Blocks.cactus, 1), "toolCuttingKnife" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.melon, 9), new Object[]
		{ new ItemStack(Blocks.melon_block, 1), "toolCuttingKnife" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.cactusKnife), new Object[]
		{ " #", "# ", '#', new ItemStack(ModItems.alchemyComponent, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ironKnife), new Object[]
		{ "#  ", "#X ", " #X", 'X', new ItemStack(Items.stick), '#',
				new ItemStack(Items.iron_ingot) });
		GameRegistry
				.addRecipe(new ItemStack(ModItems.diamondKnife), new Object[]
				{ "#  ", "#X ", " #X", 'X', new ItemStack(Items.stick), '#',
						new ItemStack(Items.diamond) });

		GameRegistry.addRecipe(new ItemStack(ModBlocks.cactusFruitNeedle), new Object[]
		{ "X", "Y", 'X', new ItemStack(ModItems.cactusFruit), 'Y',
				new ItemStack(ModItems.alchemyComponent, 1, 0) });

		GameRegistry.addRecipe(new ItemStack(ModItems.sandstoneInfusionStone), new Object[]
		{ "X", "Y", 'X', new ItemStack(ModItems.alchemyComponent, 1, 0), 'Y',
				new ItemStack(Blocks.sandstone) });

		GameRegistry.addRecipe(new ItemStack(ModItems.alchemyComponent, 8, 0), new Object[]
		{ "X", 'X', new ItemStack(Blocks.cactus) });
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.baseComponent, 1, 0),
				new Object[]
				{ "XXX", "XYX", "XXX", 'X', "plankWood", 'Y', Items.gunpowder }));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.baseComponent, 1, 1),
				new Object[]
				{ "XXX", "XYX", "XXX", 'X', "ingotIron", 'Y', Items.blaze_powder }));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.combustionHeater, 1, 0),
				new Object[]
				{ "XYX", "X X", "XXX", 'X', "logWood", 'Y', new ItemStack(ModItems.baseComponent, 1, 0) }));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.combustionHeater, 1, 1),
				new Object[]
				{ "XYX", "X X", "XXX", 'X', "ingotIron", 'Y', new ItemStack(ModItems.baseComponent, 1, 1) }));
		
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.compressedCoalBlock),
				new Object[]
				{ "XXX", "XXX", "XXX", 'X', new ItemStack(Blocks.coal_block) }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blazePowderBlock),
				new Object[]
				{ "XXX", "XXX", "XXX", 'X', new ItemStack(Items.blaze_powder) }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.alchemicalCondenser),
				new Object[]
				{ "XXX", "X X", "XYX", 'X', "cobblestone", 'Y', new ItemStack(ModItems.baseComponent, 1, 0) }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.compressedCoalBlock2),
				new Object[]
				{ "XXX", "XXX", "XXX", 'X', new ItemStack(ModBlocks.compressedCoalBlock) }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.waterExtractor),
				new Object[]
				{ "XXX", " XX", 'X', "plankWood" }));

		OreDictionary.registerOre("toolCuttingKnife", new ItemStack(ModItems.cactusKnife, 1,
				OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("toolCuttingKnife", new ItemStack(ModItems.ironKnife, 1,
				OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("toolCuttingKnife", new ItemStack(ModItems.diamondKnife, 1,
				OreDictionary.WILDCARD_VALUE));
		GameRegistry.addSmelting(ModBlocks.dryCactus, new ItemStack(Items.dye, 2, 2), 0.2F);
		
		

		InfusionRecipes.addRecipe(new ItemStack(Blocks.sapling, 1, 4), new ItemStack(
				ModItems.alchemyComponent, 10, 1), ModBlocks.cactusFruitNeedle, 0, 10);
		InfusionRecipes.addRecipe(new ItemStack(Blocks.sapling, 1, 0), new ItemStack(Items.apple,
				20, 0), "treeSapling", 0, 20);
		InfusionRecipes.addRecipe(new ItemStack(Blocks.sand, 1, 1), new ItemStack(Items.dye, 1, 1),
				Blocks.sand, 0, 2);
		InfusionRecipes.addRecipe(new ItemStack(Blocks.sand, 1, 0),
				new ItemStack(Items.dye, 1, 15), Blocks.sand, 1, 2);
		InfusionRecipes.addRecipe(new ItemStack(Blocks.dirt, 1, 1), new ItemStack(
				ModItems.cactusFruit, 4), Blocks.sand, 1, 15);
		InfusionRecipes.addRecipe(new ItemStack(Blocks.cactus, 2, 0), new ItemStack(
				ModItems.alchemyComponent, 6, 1), Blocks.cactus, 0, 8);
		
		CombustionRecipes.addRecipe(new ItemStack(Items.coal, 1), 50, new ItemStack(Items.coal, 4, 1));
		CombustionRecipes.addRecipe(new ItemStack(Items.blaze_powder, 2), 75, new ItemStack(Items.gunpowder, 1));
		CombustionRecipes.addRecipe(new ItemStack(Items.diamond, 1, 0), 1000, new ItemStack(ModBlocks.compressedCoalBlock2, 2), new ItemStack(
				Blocks.obsidian, 1));
		
		HeatSources.addHeatSource(Blocks.fire.getDefaultState());
		HeatSources.addHeatSource(Blocks.lava.getDefaultState());
	}
}
