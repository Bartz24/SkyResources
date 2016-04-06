package com.bartz24.skyresources.registry;

import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.alchemy.infusion.InfusionRecipes;
import com.bartz24.skyresources.base.HeatSources;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.technology.combustion.CombustionRecipes;
import com.bartz24.skyresources.technology.concentrator.ConcentratorRecipes;
import com.bartz24.skyresources.technology.rockgrinder.RockGrinderRecipes;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModCrafting
{
	public static void init()
	{
		GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(ModItems.cactusFruit, 1), new Object[]
				{ new ItemStack(Blocks.cactus, 1), "toolCuttingKnife" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(Items.melon, 9), new Object[]
				{ new ItemStack(Blocks.melon_block, 1), "toolCuttingKnife" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.cactusKnife), new Object[]
		{ " #", "# ", '#', new ItemStack(ModItems.alchemyComponent, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ironKnife), new Object[]
		{ "#  ", "#X ", " #X", 'X', new ItemStack(Items.stick), '#',
				new ItemStack(Items.iron_ingot) });
		GameRegistry.addRecipe(new ItemStack(ModItems.diamondKnife),
				new Object[]
				{ "#  ", "#X ", " #X", 'X', new ItemStack(Items.stick), '#', new ItemStack(Items.diamond) });
		GameRegistry.addRecipe(new ItemStack(ModItems.stoneGrinder),
				new Object[]
				{ "#  ", " X ", "  X", 'X', new ItemStack(Items.stick), '#', new ItemStack(Blocks.cobblestone) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ironGrinder), new Object[]
		{ "#  ", " X ", "  X", 'X', new ItemStack(Items.stick), '#',
				new ItemStack(Items.iron_ingot) });
		GameRegistry.addRecipe(new ItemStack(ModItems.diamondGrinder),
				new Object[]
				{ "#  ", " X ", "  X", 'X', new ItemStack(Items.stick), '#', new ItemStack(Items.diamond) });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.cactusFruitNeedle),
				new Object[]
				{ "X", "Y", 'X', new ItemStack(ModItems.cactusFruit), 'Y', new ItemStack(ModItems.alchemyComponent, 1, 0) });
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModItems.sandstoneInfusionStone), new Object[]
				{ "X", "Y", 'X', new ItemStack(ModItems.alchemyComponent, 1, 0), 'Y', "sandstone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModItems.redSandstoneInfusionStone), new Object[]
				{ "X", "Y", 'X', new ItemStack(ModItems.alchemyComponent, 1, 0), 'Y', new ItemStack(Blocks.red_sandstone, 1, OreDictionary.WILDCARD_VALUE) }));
		GameRegistry.addRecipe(new ItemStack(ModItems.alchemyComponent, 8, 0),
				new Object[]
				{ "X", 'X', new ItemStack(Blocks.cactus) });
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModItems.baseComponent, 1, 0), new Object[]
				{ "XXX", "XYX", "XXX", 'X', "plankWood", 'Y', Items.gunpowder }));
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModItems.baseComponent, 1, 1), new Object[]
				{ "XXX", "XYX", "XXX", 'X', "ingotIron", 'Y', Items.blaze_powder }));
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModBlocks.combustionHeater, 1, 0), new Object[]
				{ "XYX", "X X", "XXX", 'X', "logWood", 'Y', new ItemStack(ModItems.baseComponent, 1, 0) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModBlocks.combustionHeater, 1, 1), new Object[]
				{ "XYX", "X X", "XXX", 'X', "ingotIron", 'Y', new ItemStack(ModItems.baseComponent, 1, 1) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModBlocks.compressedCoalBlock), new Object[]
				{ "XXX", "XXX", "XXX", 'X', new ItemStack(Blocks.coal_block) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModBlocks.blazePowderBlock), new Object[]
				{ "XXX", "XXX", "XXX", 'X', new ItemStack(Items.blaze_powder) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModBlocks.alchemicalCondenser), new Object[]
				{ "XXX", "X X", "XYX", 'X', "cobblestone", 'Y', new ItemStack(ModItems.baseComponent, 1, 0) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModBlocks.compressedCoalBlock2), new Object[]
				{ "XXX", "XXX", "XXX", 'X', new ItemStack(ModBlocks.compressedCoalBlock) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModBlocks.compressedStone), new Object[]
				{ "XXX", "XXX", "XXX", 'X', new ItemStack(Blocks.stone) }));

		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModItems.waterExtractor), new Object[]
				{ "XXX", " XX", 'X', "plankWood" }));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.crucible), new Object[]
		{ "X X", "X X", "XXX", 'X', new ItemStack(Items.brick) });
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModBlocks.fluidDropper), new Object[]
				{ "XXX", "X X", "X X", 'X', "cobblestone" }));

		OreDictionary.registerOre("toolCuttingKnife", new ItemStack(
				ModItems.cactusKnife, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("toolCuttingKnife", new ItemStack(
				ModItems.ironKnife, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("toolCuttingKnife", new ItemStack(
				ModItems.diamondKnife, 1, OreDictionary.WILDCARD_VALUE));

		GameRegistry.addSmelting(ModBlocks.dryCactus,
				new ItemStack(Items.dye, 1, 7), 0.2F);

		InfusionRecipes.addRecipe(new ItemStack(Blocks.sapling, 1, 4),
				new ItemStack(ModItems.alchemyComponent, 10, 1),
				ModBlocks.cactusFruitNeedle, 0, 10);
		InfusionRecipes.addRecipe(new ItemStack(Blocks.sapling, 1, 0),
				new ItemStack(Items.apple, 20, 0), "treeSapling", 0, 20);
		InfusionRecipes.addRecipe(new ItemStack(Blocks.dirt, 1, 1),
				new ItemStack(ModItems.cactusFruit, 4), Blocks.sand, 1, 15);
		InfusionRecipes.addRecipe(new ItemStack(Blocks.cactus, 2, 0),
				new ItemStack(ModItems.alchemyComponent, 6, 1), Blocks.cactus,
				0, 8);
		InfusionRecipes.addRecipe(new ItemStack(Blocks.tallgrass, 1),
				new ItemStack(Items.rotten_flesh, 4), "treeSapling", 0, 10);
		InfusionRecipes.addRecipe(new ItemStack(Blocks.grass, 1),
				new ItemStack(Items.wheat_seeds, 4), Blocks.dirt, 0, 14);

		CombustionRecipes.addRecipe(new ItemStack(Items.coal, 1), 50,
				new ItemStack(Items.coal, 4, 1));
		CombustionRecipes.addRecipe(new ItemStack(Items.blaze_powder, 2), 75,
				new ItemStack(Items.gunpowder, 1));
		CombustionRecipes.addRecipe(new ItemStack(Items.diamond, 1, 0), 1000,
				new ItemStack(ModBlocks.compressedCoalBlock2, 2),
				new ItemStack(Blocks.obsidian, 1));
		CombustionRecipes.addRecipe(new ItemStack(Blocks.sand, 8, 1), 200,
				new ItemStack(Blocks.sand, 12), new ItemStack(Items.dye, 1, 1));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 0),
				90, new ItemStack(Blocks.glass, 6),
				new ItemStack(Items.rotten_flesh, 4),
				new ItemStack(Items.blaze_powder, 2));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 1),
				240, new ItemStack(ModItems.metalCrystal, 6, 0),
				new ItemStack(Items.iron_ingot, 2),
				new ItemStack(Items.redstone, 6));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 2),
				125, new ItemStack(ModItems.metalCrystal, 2, 0),
				new ItemStack(Items.iron_ingot, 1),
				new ItemStack(Items.gunpowder, 3));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 3),
				230, new ItemStack(ModItems.metalCrystal, 2, 2),
				new ItemStack(Items.iron_ingot, 2),
				new ItemStack(Items.blaze_powder, 4));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 4),
				400, new ItemStack(ModItems.metalCrystal, 3, 1),
				new ItemStack(Items.gold_ingot, 2),
				new ItemStack(Items.sugar, 6));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 5),
				180, new ItemStack(ModItems.metalCrystal, 4, 0),
				new ItemStack(Items.iron_ingot, 3),
				new ItemStack(Items.sugar, 2));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 6),
				420, new ItemStack(ModItems.metalCrystal, 6, 0),
				new ItemStack(Items.iron_ingot, 3),
				new ItemStack(Items.glowstone_dust, 3));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 7),
				600, new ItemStack(ModItems.metalCrystal, 5, 1),
				new ItemStack(Items.gold_ingot, 6),
				new ItemStack(Items.dye, 8, 4));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 8),
				160, new ItemStack(ModItems.metalCrystal, 3, 0),
				new ItemStack(Items.iron_ingot, 3),
				new ItemStack(Items.dye, 6, 15));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 9),
				300, new ItemStack(ModItems.metalCrystal, 5, 0),
				new ItemStack(Items.iron_ingot, 5),
				new ItemStack(Items.coal, 4));

		CombustionRecipes.addRecipe(new ItemStack(Items.redstone, 3), 400,
				new ItemStack(Items.gunpowder, 2),
				new ItemStack(Items.blaze_powder, 2));

		CombustionRecipes.addRecipe(new ItemStack(Items.dye, 32, 4), 600,
				new ItemStack(Items.diamond, 1), new ItemStack(Items.flint, 8));

		CombustionRecipes.addRecipe(new ItemStack(Items.wheat_seeds, 1), 50,
				new ItemStack(Blocks.tallgrass, 1),
				new ItemStack(Items.flint, 2));

		RockGrinderRecipes.addRecipe(new ItemStack(Blocks.sand), false,
				Blocks.cobblestone.getDefaultState());
		RockGrinderRecipes.addRecipe(new ItemStack(Blocks.gravel), false,
				Blocks.sandstone.getDefaultState());
		RockGrinderRecipes.addRecipe(new ItemStack(Items.flint), false,
				Blocks.gravel.getDefaultState());

		MinecraftForge.addGrassSeed(new ItemStack(Items.beetroot_seeds), 10);
		MinecraftForge.addGrassSeed(new ItemStack(Items.melon_seeds), 8);
		MinecraftForge.addGrassSeed(new ItemStack(Items.pumpkin_seeds), 8);

		HeatSources.addHeatSource(Blocks.fire.getDefaultState(), 20);
		HeatSources.addHeatSource(Blocks.lava.getDefaultState(), 15);
		HeatSources.addHeatSource(Blocks.torch.getDefaultState(), 5);

		for (int i = 0; i < ModFluids.crystalFluidNames().length; i++)
		{
			String oreName = "ore" + RandomHelper
					.capatilizeString(ModFluids.crystalFluidNames()[i]);

			if (OreDictionary.doesOreNameExist(oreName))
			{
				ConcentratorRecipes.addRecipe(
						Block.getBlockFromItem(
								OreDictionary.getOres(oreName).get(0).getItem())
								.getDefaultState(),
						ModFluids.crystalFluidRarity()[i] * 100,
						new ItemStack(ModItems.metalCrystal, ConfigOptions.crystalConcentratorAmount, i),
						ModBlocks.compressedStone.getDefaultState());
			}
		}
	}
}
