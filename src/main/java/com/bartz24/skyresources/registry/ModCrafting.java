package com.bartz24.skyresources.registry;

import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.References;
import com.bartz24.skyresources.alchemy.infusion.InfusionRecipes;
import com.bartz24.skyresources.base.HeatSources;
import com.bartz24.skyresources.base.ModFuelHandler;
import com.bartz24.skyresources.base.waterextractor.WaterExtractorRecipes;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.technology.combustion.CombustionRecipes;
import com.bartz24.skyresources.technology.concentrator.ConcentratorRecipes;
import com.bartz24.skyresources.technology.freezer.FreezerRecipes;
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
				{ new ItemStack(Blocks.CACTUS, 1), "toolCuttingKnife" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(Items.MELON, 9), new Object[]
				{ new ItemStack(Blocks.MELON_BLOCK, 1), "toolCuttingKnife" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.cactusKnife), new Object[]
		{ " #", "# ", '#', new ItemStack(ModItems.alchemyComponent, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ironKnife), new Object[]
		{ "#  ", "#X ", " #X", 'X', new ItemStack(Items.STICK), '#',
				new ItemStack(Items.IRON_INGOT) });
		GameRegistry.addRecipe(new ItemStack(ModItems.diamondKnife),
				new Object[]
				{ "#  ", "#X ", " #X", 'X', new ItemStack(Items.STICK), '#', new ItemStack(Items.DIAMOND) });
		GameRegistry.addRecipe(new ItemStack(ModItems.stoneGrinder),
				new Object[]
				{ "#  ", " X ", "  X", 'X', new ItemStack(Items.STICK), '#', new ItemStack(Blocks.COBBLESTONE) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ironGrinder), new Object[]
		{ "#  ", " X ", "  X", 'X', new ItemStack(Items.STICK), '#',
				new ItemStack(Items.IRON_INGOT) });
		GameRegistry.addRecipe(new ItemStack(ModItems.diamondGrinder),
				new Object[]
				{ "#  ", " X ", "  X", 'X', new ItemStack(Items.STICK), '#', new ItemStack(Items.DIAMOND) });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.cactusFruitNeedle),
				new Object[]
				{ "X", "Y", 'X', new ItemStack(ModItems.cactusFruit), 'Y', new ItemStack(ModItems.alchemyComponent, 1, 0) });
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModItems.sandstoneInfusionStone), new Object[]
				{ "X", "Y", 'X', new ItemStack(ModItems.alchemyComponent, 1, 0), 'Y', new ItemStack(Blocks.SANDSTONE, 1, OreDictionary.WILDCARD_VALUE) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModItems.redSandstoneInfusionStone), new Object[]
				{ "X", "Y", 'X', new ItemStack(ModItems.alchemyComponent, 1, 0), 'Y', new ItemStack(Blocks.RED_SANDSTONE, 1, OreDictionary.WILDCARD_VALUE) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModItems.alchemicalInfusionStone), new Object[]
				{ "X", "Y", 'X', new ItemStack(ModItems.alchemyComponent, 1, 6), 'Y', new ItemStack(ModItems.alchemyComponent, 1, 4) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModItems.alchemyComponent, 1, 6), new Object[]
				{ "X", "X", 'X', new ItemStack(ModItems.alchemyComponent, 1, 5) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModItems.baseComponent, 1, 0), new Object[]
				{ "XXX", "XYX", "XXX", 'X', "plankWood", 'Y', Items.GUNPOWDER }));
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModItems.baseComponent, 1, 1), new Object[]
				{ "XXX", "XYX", "XXX", 'X', "ingotIron", 'Y', Items.BLAZE_POWDER }));
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModBlocks.combustionHeater, 1, 0), new Object[]
				{ "XYX", "X X", "XXX", 'X', "logWood", 'Y', new ItemStack(ModItems.baseComponent, 1, 0) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModBlocks.combustionHeater, 1, 1), new Object[]
				{ "XYX", "X X", "XXX", 'X', "ingotIron", 'Y', new ItemStack(ModItems.baseComponent, 1, 1) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModBlocks.compressedCoalBlock), new Object[]
				{ "XXX", "XXX", "XXX", 'X', new ItemStack(Blocks.COAL_BLOCK) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModBlocks.blazePowderBlock), new Object[]
				{ "XXX", "XXX", "XXX", 'X', new ItemStack(Items.BLAZE_POWDER) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModBlocks.heavySnow), new Object[]
				{ "XX", "XX", 'X', new ItemStack(ModItems.heavySnowball) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModBlocks.miniFreezer), new Object[]
				{ "X", "X", 'X', new ItemStack(Blocks.SNOW) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModBlocks.alchemicalCondenser), new Object[]
				{ "XXX", "X X", "XYX", 'X', "cobblestone", 'Y', new ItemStack(ModItems.baseComponent, 1, 0) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModBlocks.concentrator), new Object[]
				{ "XXX", "XYX", "XZX", 'X', "ingotIron", 'Y', new ItemStack(ModBlocks.alchemicalCondenser), 'Z', new ItemStack(ModItems.alchemyComponent, 1, 4) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModBlocks.compressedCoalBlock2), new Object[]
				{ "XXX", "XXX", "XXX", 'X', new ItemStack(ModBlocks.compressedCoalBlock) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModBlocks.compressedStone), new Object[]
				{ "XXX", "XXX", "XXX", 'X', new ItemStack(Blocks.STONE) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModBlocks.coalInfusedBlock), new Object[]
				{ "XXX", "XXX", "XXX", 'X', new ItemStack(ModItems.alchemyComponent, 1, 2) }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(ModBlocks.heavySnow2), new Object[]
				{ new ItemStack(ModBlocks.heavySnow), new ItemStack(ModBlocks.heavySnow), new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.ROTTEN_FLESH) }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(Blocks.SAPLING, 1, 1), new Object[]
				{ new ItemStack(Blocks.DIRT, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.PUMPKIN_SEEDS), new ItemStack(Items.PUMPKIN_SEEDS), new ItemStack(Items.DYE, 1, 15) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModItems.baseComponent, 1, 2), new Object[]
				{ " X ", "XXX", " X ", 'X', "treeSapling" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModItems.baseComponent, 1, 2), new Object[]
				{ " X ", "XXX", " X ", 'X', "cropWheat" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModItems.baseComponent, 1, 2), new Object[]
				{ " X ", "XXX", " X ", 'X', "treeLeaves" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModItems.alchemyComponent, 9, 2), new Object[]
				{ "X", 'X', new ItemStack(ModBlocks.coalInfusedBlock) }));

		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModItems.waterExtractor), new Object[]
				{ "XXX", " XX", 'X', "plankWood" }));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.crucible), new Object[]
		{ "X X", "X X", "XXX", 'X', new ItemStack(Items.BRICK) });
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModBlocks.fluidDropper), new Object[]
				{ "XXX", "X X", "X X", 'X', "cobblestone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModBlocks.purificationVessel), new Object[]
				{ "XXX", "XYX", "XXX", 'X', "blockGlass", 'Y', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE }));

		GameRegistry.addSmelting(ModBlocks.dryCactus,
				new ItemStack(Items.DYE, 1, 7), 0.2F);

		GameRegistry.addSmelting(new ItemStack(ModItems.baseComponent, 1, 2),
				new ItemStack(Items.COAL, 1, 1), 0.1F);

		InfusionRecipes.addRecipe(new ItemStack(Blocks.SAPLING, 1, 4),
				new ItemStack(ModItems.alchemyComponent, 10, 1),
				ModBlocks.cactusFruitNeedle, 0, 10);
		InfusionRecipes.addRecipe(new ItemStack(Blocks.SAPLING, 1, 0),
				new ItemStack(Items.APPLE, 10, 0), "treeSapling", 0, 20);
		InfusionRecipes.addRecipe(new ItemStack(Blocks.DIRT, 1, 1),
				new ItemStack(ModItems.cactusFruit, 4), Blocks.SAND, 1, 15);
		InfusionRecipes.addRecipe(new ItemStack(Blocks.CACTUS, 2, 0),
				new ItemStack(ModItems.alchemyComponent, 6, 1), Blocks.CACTUS,
				OreDictionary.WILDCARD_VALUE, 8);
		InfusionRecipes.addRecipe(new ItemStack(Blocks.TALLGRASS, 1),
				new ItemStack(Items.ROTTEN_FLESH, 4), "treeSapling", 0, 10);
		InfusionRecipes.addRecipe(new ItemStack(Blocks.GRASS, 1),
				new ItemStack(Items.WHEAT_SEEDS, 4), Blocks.DIRT, 0, 14);
		InfusionRecipes.addRecipe(new ItemStack(Items.APPLE),
				new ItemStack(Items.SUGAR, 10), Blocks.HAY_BLOCK, 0, 10);
		InfusionRecipes.addRecipe(new ItemStack(Blocks.SAPLING, 1, 5),
				new ItemStack(Items.GUNPOWDER, 10), Blocks.SAPLING, 0, 20);
		InfusionRecipes.addRecipe(new ItemStack(Blocks.SAPLING, 1, 3),
				new ItemStack(Items.DYE, 10, 3), "treeSapling", 0, 20);
		InfusionRecipes.addRecipe(new ItemStack(Blocks.SAPLING, 1, 2),
				new ItemStack(Items.DYE, 10, 15), "treeSapling", 0, 20);

		CombustionRecipes.addRecipe(new ItemStack(Items.COAL, 1), 50,
				new ItemStack(Items.COAL, 2, 1));
		CombustionRecipes.addRecipe(new ItemStack(Items.BLAZE_POWDER, 2), 75,
				new ItemStack(Items.GUNPOWDER, 1));
		CombustionRecipes.addRecipe(new ItemStack(Items.DIAMOND, 1, 0), 1000,
				new ItemStack(ModBlocks.compressedCoalBlock2, 2),
				new ItemStack(Blocks.OBSIDIAN, 1));
		CombustionRecipes.addRecipe(new ItemStack(Blocks.SANDSTONE, 8, 1), 200,
				new ItemStack(Blocks.SAND, 12), new ItemStack(Items.DYE, 1, 1));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 0),
				90, new ItemStack(Blocks.GLASS, 6),
				new ItemStack(Items.ROTTEN_FLESH, 4),
				new ItemStack(Items.BLAZE_POWDER, 2));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 1),
				240, new ItemStack(ModItems.metalCrystal, 6, 0),
				new ItemStack(Items.IRON_INGOT, 2),
				new ItemStack(Items.REDSTONE, 6));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 2),
				125, new ItemStack(ModItems.metalCrystal, 2, 0),
				new ItemStack(Items.IRON_INGOT, 1),
				new ItemStack(Items.GUNPOWDER, 3));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 3),
				230, new ItemStack(ModItems.metalCrystal, 2, 2),
				new ItemStack(Items.IRON_INGOT, 2),
				new ItemStack(Items.BLAZE_POWDER, 4));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 4),
				400, new ItemStack(ModItems.metalCrystal, 3, 1),
				new ItemStack(Items.GOLD_INGOT, 2),
				new ItemStack(Items.SUGAR, 6));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 5),
				180, new ItemStack(ModItems.metalCrystal, 4, 0),
				new ItemStack(Items.IRON_INGOT, 3),
				new ItemStack(Items.SUGAR, 2));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 6),
				420, new ItemStack(ModItems.metalCrystal, 6, 0),
				new ItemStack(Items.IRON_INGOT, 3),
				new ItemStack(Items.GLOWSTONE_DUST, 3));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 7),
				600, new ItemStack(ModItems.metalCrystal, 5, 1),
				new ItemStack(Items.GOLD_INGOT, 6),
				new ItemStack(Items.DYE, 8, 4));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 8),
				160, new ItemStack(ModItems.metalCrystal, 3, 0),
				new ItemStack(Items.IRON_INGOT, 3),
				new ItemStack(Items.DYE, 6, 15));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 9),
				300, new ItemStack(ModItems.metalCrystal, 5, 0),
				new ItemStack(Items.IRON_INGOT, 5),
				new ItemStack(Items.COAL, 4));

		CombustionRecipes.addRecipe(new ItemStack(Items.REDSTONE, 3), 400,
				new ItemStack(Items.GUNPOWDER, 2),
				new ItemStack(Items.BLAZE_POWDER, 2));

		CombustionRecipes.addRecipe(
				new ItemStack(ModItems.alchemyComponent, 2, 2), 400,
				new ItemStack(Items.COAL, 2),
				new ItemStack(ModItems.alchemyComponent, 1, 3));
		CombustionRecipes.addRecipe(
				new ItemStack(ModItems.alchemyComponent, 1, 4), 1200,
				new ItemStack(Items.DIAMOND, 1),
				new ItemStack(ModItems.alchemyComponent, 8, 3));
		CombustionRecipes.addRecipe(
				new ItemStack(ModItems.alchemyComponent, 1, 5), 1000,
				new ItemStack(Items.GOLD_INGOT, 1),
				new ItemStack(ModItems.alchemyComponent, 4, 3));

		CombustionRecipes.addRecipe(
				new ItemStack(ModItems.alchemyComponent, 4, 3), 700,
				new ItemStack(Items.REDSTONE, 1),
				new ItemStack(Items.DYE, 1, 4),
				new ItemStack(Items.GLOWSTONE_DUST, 1),
				new ItemStack(Items.BLAZE_POWDER, 1));

		CombustionRecipes.addRecipe(new ItemStack(Items.DYE, 32, 4), 600,
				new ItemStack(Items.DIAMOND, 1), new ItemStack(Items.FLINT, 8));

		CombustionRecipes.addRecipe(new ItemStack(Items.WHEAT_SEEDS, 1), 50,
				new ItemStack(Blocks.DEADBUSH, 1),
				new ItemStack(Items.FLINT, 2));
		CombustionRecipes.addRecipe(new ItemStack(Blocks.DIRT), 150,
				new ItemStack(ModItems.baseComponent, 12, 2));

		RockGrinderRecipes.addRecipe(new ItemStack(Blocks.SAND), false,
				Blocks.COBBLESTONE.getDefaultState());
		RockGrinderRecipes.addRecipe(new ItemStack(Blocks.GRAVEL), false,
				Blocks.SANDSTONE.getDefaultState());
		RockGrinderRecipes.addRecipe(new ItemStack(Items.FLINT), false,
				Blocks.GRAVEL.getDefaultState());
		RockGrinderRecipes.addRecipe(
				new ItemStack(ModItems.techComponent, 1, 0), false,
				Blocks.STONE.getDefaultState());

		for (int i = 0; i < References.gemList.size(); i++)
		{
			RockGrinderRecipes.addRecipe(new ItemStack(ModItems.dirtyGem, 1, i),
					false, Blocks.STONE.getDefaultState(), 0.005F);
		}

		WaterExtractorRecipes.addExtractRecipe(50, true,
				Blocks.CACTUS.getDefaultState(),
				ModBlocks.dryCactus.getDefaultState());

		WaterExtractorRecipes.addExtractRecipe(50, true,
				Blocks.SNOW.getDefaultState(),
				null);

		WaterExtractorRecipes.addExtractRecipe(20, true,
				Blocks.LEAVES.getDefaultState(),
				null);

		WaterExtractorRecipes.addExtractRecipe(20, true,
				Blocks.LEAVES2.getDefaultState(),
				null);
		
		WaterExtractorRecipes.addInsertRecipe(Blocks.CLAY.getDefaultState(), false, Blocks.DIRT.getDefaultState(), 1000);

		FreezerRecipes.addRecipe(new ItemStack(ModItems.heavySnowball), 100,
				new ItemStack(Items.SNOWBALL, 4));
		FreezerRecipes.addRecipe(new ItemStack(Blocks.DIRT, 1, 1), 800,
				new ItemStack(ModBlocks.heavySnow2, 4));

		MinecraftForge.addGrassSeed(new ItemStack(Items.BEETROOT_SEEDS), 10);
		MinecraftForge.addGrassSeed(new ItemStack(Items.MELON_SEEDS), 8);
		MinecraftForge.addGrassSeed(new ItemStack(Items.PUMPKIN_SEEDS), 8);
		MinecraftForge.addGrassSeed(new ItemStack(Items.DYE, 1, 3), 1);

		HeatSources.addHeatSource(Blocks.FIRE.getDefaultState(), 20);
		HeatSources.addHeatSource(Blocks.LAVA.getDefaultState(), 15);
		HeatSources.addHeatSource(Blocks.FLOWING_LAVA.getDefaultState(), 10);
		HeatSources.addHeatSource(Blocks.TORCH.getDefaultState(), 5);
		HeatSources.addHeatSource(Blocks.OBSIDIAN.getDefaultState(), 12);

		GameRegistry.registerFuelHandler(new ModFuelHandler());

		for (int i = 0; i < ModFluids.crystalFluidNames().length; i++)
		{
			String oreName = "ore" + RandomHelper
					.capatilizeString(ModFluids.crystalFluidNames()[i]);

			if (OreDictionary.getOres(oreName).size() > 0)
			{
				ConcentratorRecipes.addRecipe(
						Block.getBlockFromItem(
								OreDictionary.getOres(oreName).get(0).getItem())
								.getStateFromMeta(OreDictionary.getOres(oreName)
										.get(0).getMetadata()),
						ModFluids.crystalFluidRarity()[i] * 100,
						new ItemStack(ModItems.metalCrystal,
								ConfigOptions.crystalConcentratorAmount, i),
						ModBlocks.compressedStone.getDefaultState());
			}
		}

		ConcentratorRecipes.addRecipe(Blocks.COAL_ORE.getDefaultState(), 100,
				new ItemStack(ModItems.alchemyComponent, 1, 2),
				ModBlocks.compressedStone.getDefaultState());

		ConcentratorRecipes.addRecipe(Blocks.DIAMOND_ORE.getDefaultState(), 800,
				new ItemStack(ModItems.alchemyComponent, 1, 4),
				ModBlocks.compressedStone.getDefaultState());

		ConcentratorRecipes.addRecipe(Blocks.REDSTONE_ORE.getDefaultState(),
				700, new ItemStack(ModItems.alchemyComponent, 12, 3),
				ModBlocks.compressedStone.getDefaultState());

		ConcentratorRecipes.addRecipe(Blocks.LAPIS_ORE.getDefaultState(), 400,
				new ItemStack(ModItems.alchemyComponent, 1, 5),
				ModBlocks.compressedStone.getDefaultState());
	}

	public static void initOreDict()
	{

		OreDictionary.registerOre("toolCuttingKnife", new ItemStack(
				ModItems.cactusKnife, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("toolCuttingKnife", new ItemStack(
				ModItems.ironKnife, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("toolCuttingKnife", new ItemStack(
				ModItems.diamondKnife, 1, OreDictionary.WILDCARD_VALUE));

	}
}
