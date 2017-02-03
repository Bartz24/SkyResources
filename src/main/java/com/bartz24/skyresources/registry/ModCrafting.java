package com.bartz24.skyresources.registry;

import java.util.ArrayList;
import java.util.Arrays;

import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.References;
import com.bartz24.skyresources.alchemy.fluid.FluidRegisterInfo;
import com.bartz24.skyresources.alchemy.fluid.FluidRegisterInfo.CrystalFluidType;
import com.bartz24.skyresources.base.HeatSources;
import com.bartz24.skyresources.base.ModFuelHandler;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;
import com.bartz24.skyresources.technology.item.GemRegisterInfo;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModCrafting
{
	public static void init()
	{
		GameRegistry.addRecipe(new ItemStack(Blocks.SNOW_LAYER, 3),
				new Object[] { "XX", 'X', new ItemStack(Items.SNOWBALL) });
		GameRegistry.addRecipe(new ItemStack(ModItems.survivalistFishingRod),
				new Object[] { " X", "XY", 'X', new ItemStack(Items.STICK), 'Y', new ItemStack(Items.STRING) });
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.cactusFruit, 2),
				new Object[] { new ItemStack(Blocks.CACTUS), "toolCuttingKnife" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.MELON, 9),
				new Object[] { new ItemStack(Blocks.MELON_BLOCK, 1), "toolCuttingKnife" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.cactusKnife),
				new Object[] { " #", "# ", '#', new ItemStack(ModItems.alchemyComponent, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ironKnife), new Object[] { "#  ", "#X ", " #X", 'X',
				new ItemStack(Items.STICK), '#', new ItemStack(Items.IRON_INGOT) });
		GameRegistry.addRecipe(new ItemStack(ModItems.diamondKnife), new Object[] { "#  ", "#X ", " #X", 'X',
				new ItemStack(Items.STICK), '#', new ItemStack(Items.DIAMOND) });
		GameRegistry.addRecipe(new ItemStack(ModItems.stoneGrinder), new Object[] { "#  ", " # ", "  X", 'X',
				new ItemStack(Items.STICK), '#', new ItemStack(Blocks.COBBLESTONE) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ironGrinder), new Object[] { "#  ", " # ", "  X", 'X',
				new ItemStack(Items.STICK), '#', new ItemStack(Items.IRON_INGOT) });
		GameRegistry.addRecipe(new ItemStack(ModItems.diamondGrinder), new Object[] { "#  ", " # ", "  X", 'X',
				new ItemStack(Items.STICK), '#', new ItemStack(Items.DIAMOND) });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.cactusFruitNeedle), new Object[] { "X", "Y", 'X',
				new ItemStack(ModItems.cactusFruit), 'Y', new ItemStack(ModItems.alchemyComponent, 1, 0) });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.sandstoneInfusionStone),
				new Object[] { "X", "Y", 'X', new ItemStack(ModItems.alchemyComponent, 1, 0), 'Y',
						new ItemStack(Blocks.SANDSTONE, 1, OreDictionary.WILDCARD_VALUE) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.redSandstoneInfusionStone),
				new Object[] { "X", "Y", 'X', new ItemStack(ModItems.alchemyComponent, 1, 0), 'Y',
						new ItemStack(Blocks.RED_SANDSTONE, 1, OreDictionary.WILDCARD_VALUE) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.alchemicalInfusionStone),
				new Object[] { "X", "Y", 'X', new ItemStack(ModItems.alchemyComponent, 1, 5), 'Y',
						new ItemStack(ModItems.alchemyComponent, 1, 4) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.alchemyComponent, 1, 5),
				new Object[] { "X", "X", 'X', new ItemStack(ModItems.alchemyComponent, 1, 4) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.baseComponent, 1, 0),
				new Object[] { "XXX", "XYX", "XXX", 'X', "plankWood", 'Y', Items.GUNPOWDER }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.baseComponent, 4, 6), Items.ROTTEN_FLESH,
				new ItemStack(Items.DYE, 1, 15), new ItemStack(Items.DYE, 1, 15), new ItemStack(Items.DYE, 1, 15)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.baseComponent, 1, 1),
				new Object[] { "XXX", "XYX", "XXX", 'X', "ingotIron", 'Y', Items.BLAZE_POWDER }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.combustionHeater, 1, 0), new Object[] {
				"XYX", "X X", "XXX", 'X', "logWood", 'Y', new ItemStack(ModItems.baseComponent, 1, 0) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.combustionHeater, 1, 1), new Object[] {
				"XYX", "X X", "XXX", 'X', "ingotIron", 'Y', new ItemStack(ModItems.baseComponent, 1, 1) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.combustionHeater, 1, 3),
				new Object[] { "XYX", "X X", "XZX", 'X', new ItemStack(ModItems.baseComponent, 1, 5), 'Y',
						new ItemStack(ModItems.baseComponent, 1, 1), 'Z',
						new ItemStack(ModItems.baseComponent, 1, 3) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.compressedCoalBlock), new Object[] { "XXX",
				"XYX", "XXX", 'X', new ItemStack(Blocks.COAL_BLOCK), 'Y', new ItemStack(Items.COAL) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blazePowderBlock),
				new Object[] { "XXX", "XXX", "XXX", 'X', new ItemStack(Items.BLAZE_POWDER) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.heavySnow),
				new Object[] { "XX", "XX", 'X', new ItemStack(ModItems.heavySnowball) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.miniFreezer),
				new Object[] { "X", "X", 'X', new ItemStack(Blocks.SNOW) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.alchemicalCondenser, 1, 0), new Object[] {
				"XXX", "X X", "XYX", 'X', "cobblestone", 'Y', new ItemStack(ModItems.baseComponent, 1, 0) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.alchemicalCondenser, 1, 1), new Object[] {
				"XXX", "X X", "XYX", 'X', "ingotIron", 'Y', new ItemStack(ModItems.baseComponent, 1, 4) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.alchemicalCondenser, 1, 3),
				new Object[] { "XXX", "X X", "XYX", 'X', new ItemStack(ModItems.baseComponent, 1, 5), 'Y',
						new ItemStack(ModItems.baseComponent, 1, 4) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.coalInfusedBlock),
				new Object[] { "XXX", "XXX", "XXX", 'X', new ItemStack(ModItems.alchemyComponent, 1, 1) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.alchemyComponent, 9, 1),
				new Object[] { "X", 'X', new ItemStack(ModBlocks.coalInfusedBlock) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.darkMatterBlock),
				new Object[] { "XXX", "XXX", "XXX", 'X', new ItemStack(ModItems.baseComponent, 1, 5) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.baseComponent, 9, 5),
				new Object[] { "X", 'X', new ItemStack(ModBlocks.darkMatterBlock) }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.heavySnow2),
				new Object[] { new ItemStack(ModBlocks.heavySnow), new ItemStack(ModBlocks.heavySnow),
						new ItemStack(Items.ROTTEN_FLESH) }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Blocks.SAPLING, 1, 1),
				new Object[] { new ItemStack(Blocks.DIRT, 1, OreDictionary.WILDCARD_VALUE),
						new ItemStack(Items.PUMPKIN_SEEDS), new ItemStack(Items.PUMPKIN_SEEDS),
						new ItemStack(Items.DYE, 1, 15) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.baseComponent, 3, 2),
				new Object[] { " X ", "XXX", " X ", 'X', "treeSapling" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.baseComponent, 3, 2),
				new Object[] { " X ", "XXX", " X ", 'X', "cropWheat" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.baseComponent, 3, 2),
				new Object[] { " X ", "XXX", " X ", 'X', "treeLeaves" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.baseComponent, 3, 2),
				new Object[] { " X ", "XXX", " X ", 'X', ModItems.cactusFruit }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.baseComponent, 3, 2),
				new Object[] { " X ", "XXX", " X ", 'X', "cropPotato" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.baseComponent, 3, 2),
				new Object[] { " X ", "XXX", " X ", 'X', "cropCarrot" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.baseComponent, 3, 2),
				new Object[] { " X ", "XXX", " X ", 'X', "sugarcane" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.baseComponent, 3, 2),
				new Object[] { " X ", "XXX", " X ", 'X', "dustWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.ironFreezer), new Object[] { "XXX", "XZX",
				"XXX", 'X', "ingotFrozenIron", 'Z', new ItemStack(ModBlocks.miniFreezer) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.dirtFurnace),
				new Object[] { "X", "Y", 'X', "dirt", 'Y', new ItemStack(ModItems.baseComponent, 1, 0) }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.waterExtractor),
				new Object[] { "XXX", " XX", 'X', "plankWood" }));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.crucible),
				new Object[] { "X X", "X X", "XXX", 'X', new ItemStack(Items.BRICK) });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.fluidDropper),
				new Object[] { "XXX", "X X", "X X", 'X', "cobblestone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.fleshySnowNugget, 3), new Object[] { "XX",
				"XY", 'X', new ItemStack(Items.SNOWBALL), 'Y', new ItemStack(Items.ROTTEN_FLESH) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.baseComponent, 1, 4), new Object[] { "XXX",
				"XYX", "XXX", 'X', "ingotFrozenIron", 'Y', new ItemStack(Items.GLOWSTONE_DUST) }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.crystallizer, 1, 0), new Object[] { "XYX",
				"X X", "X X", 'X', "cobblestone", 'Y', new ItemStack(ModItems.baseComponent, 1, 0) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.crystallizer, 1, 1), new Object[] { "XYX",
				"X X", "X X", 'X', "ingotIron", 'Y', new ItemStack(ModItems.baseComponent, 1, 4) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.crystallizer, 1, 3),
				new Object[] { "XYX", "X X", "X X", 'X', new ItemStack(ModItems.baseComponent, 1, 5), 'Y',
						new ItemStack(ModItems.baseComponent, 1, 4) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.combustionCollector),
				new Object[] { "XXX", "XYX", "XXX", 'X', "ingotIron", 'Y', new ItemStack(Blocks.HOPPER) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.quickDropper), new Object[] { "XXX", "XZX",
				"XYX", 'X', "ingotIron", 'Y', new ItemStack(Blocks.DROPPER), 'Z', new ItemStack(Blocks.GLOWSTONE) }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.darkMatterWarper), new Object[] { "XXX",
				"XYX", "XXX", 'X', new ItemStack(Blocks.OBSIDIAN), 'Y', new ItemStack(ModItems.baseComponent, 1, 5) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.endPortalCore),
				new Object[] { "XZX", "AYA", "XZX", 'X', new ItemStack(ModItems.baseComponent, 1, 5), 'Y',
						new ItemStack(Items.ENDER_EYE), 'Z', new ItemStack(ModItems.techComponent, 1, 2), 'A',
						new ItemStack(ModItems.alchemyComponent, 1, 4) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.sandyNetherrack, 4),
				new Object[] { "XY", "ZX", 'X', new ItemStack(Blocks.SAND), 'Y', new ItemStack(Items.NETHER_WART), 'Z',
						new ItemStack(Blocks.NETHERRACK) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.lifeInfuser),
				new Object[] { "XXX", " X ", " Y ", 'X', "logWood", 'Y', new ItemStack(ModItems.healthGem) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.lifeInjector),
				new Object[] { " Y ", " X ", "XXX", 'X', "logWood", 'Y', new ItemStack(Items.DIAMOND_SWORD) }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.heavyExpSnowball, 3),
				new ItemStack(ModItems.heavySnowball), new ItemStack(ModItems.heavySnowball),
				new ItemStack(ModItems.heavySnowball), new ItemStack(Items.GUNPOWDER)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.crucibleInserter),
				new Object[] { "XXX", "XYX", " X ", 'X', "ingotIron", 'Y', new ItemStack(Blocks.DROPPER) }));

		String steelIngot = OreDictionary.getOres("ingotSteel").size() > 0 ? "ingotSteel"
				: OreDictionary.getOres("ingotElectricalSteel").size() > 0  ? "ingotElectricalSteel" : "ingotIron";
		String coalDust = OreDictionary.getOres("dustCoal").size() > 0 ? "dustCoal" : "dustRedstone";
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.baseComponent, 1, 3),
					new Object[] { "XZX", "XYX", "XZX", 'X', steelIngot, 'Y', Blocks.REDSTONE_BLOCK, 'Z', coalDust }));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.combustionHeater, 1, 2),
					new Object[] { "XZX", "XYX", "XYX", 'X', steelIngot, 'Y',
							new ItemStack(ModItems.baseComponent, 1, 3), 'Z',
							new ItemStack(ModItems.baseComponent, 1, 1) }));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.poweredHeater),
					new Object[] { "XZX", "XYX", "XXX", 'X', steelIngot, 'Y',
							new ItemStack(ModItems.baseComponent, 1, 3), 'Z',
							new ItemStack(ModItems.baseComponent, 1, 1) }));
			GameRegistry
					.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.rockCrusher),
							new Object[] { "XXX", "XYX", "XZX", 'X', steelIngot, 'Y',
									new ItemStack(ModItems.diamondGrinder), 'Z',
									new ItemStack(ModItems.baseComponent, 1, 3) }));
			GameRegistry
					.addRecipe(
							new ShapedOreRecipe(new ItemStack(ModBlocks.rockCleaner),
									new Object[] { "XXX", "XYX", "XZX", 'X', steelIngot, 'Y',
											new ItemStack(Items.CAULDRON), 'Z',
											new ItemStack(ModItems.baseComponent, 1, 3) }));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.alchemicalCondenser, 1, 2),
					new Object[] { "XXX", "XZX", "XYX", 'X', steelIngot, 'Y',
							new ItemStack(ModItems.baseComponent, 1, 4), 'Z',
							new ItemStack(ModItems.baseComponent, 1, 1) }));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.crystallizer, 1, 2),
					new Object[] { "XYX", "XZX", "X X", 'X', steelIngot, 'Y',
							new ItemStack(ModItems.baseComponent, 1, 4), 'Z',
							new ItemStack(ModItems.baseComponent, 1, 1) }));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.aqueousConcentrator),
					new Object[] { "XAX", "XZX", "XYX", 'X', steelIngot, 'Y',
							new ItemStack(ModItems.baseComponent, 1, 3), 'Z', new ItemStack(ModItems.waterExtractor),
							'A', new ItemStack(Blocks.SNOW) }));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.aqueousDeconcentrator),
					new Object[] { "XAX", "XZX", "XYX", 'X', steelIngot, 'Y',
							new ItemStack(ModItems.baseComponent, 1, 3), 'Z', new ItemStack(ModItems.waterExtractor),
							'A', new ItemStack(Blocks.SAND) }));
		}
		GameRegistry.addSmelting(ModBlocks.dryCactus, new ItemStack(Items.DYE, 1, 7), 0.2F);

		GameRegistry.addSmelting(new ItemStack(ModItems.baseComponent, 1, 2), new ItemStack(Items.COAL, 1, 1), 0.1F);

		ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(Blocks.SAPLING, 1, 4), 10,
				new ArrayList<Object>(Arrays.asList(new ItemStack(ModItems.alchemyComponent, 10, 0),
						new ItemStack(ModBlocks.cactusFruitNeedle))));

		ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(Blocks.SAPLING, 1, 0), 10,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.APPLE, 10, 0), "treeSapling")));

		ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(Blocks.DIRT, 1, 1), 15, new ArrayList<Object>(
				Arrays.asList(new ItemStack(ModItems.cactusFruit, 4), new ItemStack(Blocks.SAND, 1, 1))));

		ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(Blocks.CACTUS, 3), 8,
				new ArrayList<Object>(Arrays.asList(new ItemStack(ModItems.alchemyComponent, 6, 0),
						new ItemStack(Blocks.CACTUS, 1, OreDictionary.WILDCARD_VALUE))));

		ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(Blocks.DEADBUSH), 10,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.ROTTEN_FLESH, 4), "treeSapling")));

		ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(Blocks.GRASS, 1), 14,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.WHEAT_SEEDS, 4), new ItemStack(Blocks.DIRT))));

		ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(Items.APPLE), 10,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.SUGAR, 10), new ItemStack(Blocks.HAY_BLOCK))));

		ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(Blocks.SAPLING, 1, 5), 20, new ArrayList<Object>(
				Arrays.asList(new ItemStack(Items.GUNPOWDER, 10), new ItemStack(Blocks.SAPLING, 1, 0))));

		ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(Blocks.SAPLING, 1, 3), 20,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.DYE, 10, 3), "treeSapling")));

		ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(Blocks.SAPLING, 1, 2), 20,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.DYE, 10, 15), "treeSapling")));

		ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(Items.REEDS), 20,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.SPECKLED_MELON, 3),
						new ItemStack(Blocks.PUMPKIN, 1, OreDictionary.WILDCARD_VALUE))));

		ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(Items.NETHER_WART), 20,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.SPIDER_EYE, 8),
						new ItemStack(Blocks.RED_MUSHROOM, 1, OreDictionary.WILDCARD_VALUE))));

		ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(Blocks.RED_MUSHROOM), 15,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.DYE, 8, 1),
						new ItemStack(Blocks.TALLGRASS, 1, OreDictionary.WILDCARD_VALUE))));

		ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(Blocks.BROWN_MUSHROOM), 15,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.DYE, 8, 3),
						new ItemStack(Blocks.TALLGRASS, 1, OreDictionary.WILDCARD_VALUE))));

		ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(ModItems.healthGem), 15,
				new ArrayList<Object>(Arrays.asList(new ItemStack(ModItems.alchemyComponent, 1, 3),
						new ItemStack(Blocks.CHORUS_FLOWER, 1, OreDictionary.WILDCARD_VALUE))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(Items.COAL, 1), 50,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.COAL, 1, 1))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(Items.BLAZE_POWDER, 3), 75,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.GUNPOWDER))));
		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(Items.GUNPOWDER), 120,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.FLINT))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(Items.DIAMOND, 1), 1000,
				new ArrayList<Object>(Arrays.asList(new ItemStack(ModBlocks.compressedCoalBlock, 1))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(Blocks.SAND, 12, 1), 200,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Blocks.SAND, 12), new ItemStack(Items.DYE, 1, 1))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 0), 90,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Blocks.GLASS, 6),
						new ItemStack(Items.ROTTEN_FLESH, 4), new ItemStack(Items.BLAZE_POWDER, 2))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 1), 240,
				new ArrayList<Object>(Arrays.asList(new ItemStack(ModItems.metalCrystal, 1, 0),
						new ItemStack(Items.IRON_INGOT, 2), new ItemStack(Items.REDSTONE, 6))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 2), 125,
				new ArrayList<Object>(Arrays.asList(new ItemStack(ModItems.metalCrystal, 1, 0),
						new ItemStack(Items.IRON_INGOT, 1), new ItemStack(Items.GUNPOWDER, 3))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 3), 230,
				new ArrayList<Object>(Arrays.asList(new ItemStack(ModItems.metalCrystal, 1, 2),
						new ItemStack(Items.IRON_INGOT, 2), new ItemStack(Items.BLAZE_POWDER, 4))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 4), 400,
				new ArrayList<Object>(Arrays.asList(new ItemStack(ModItems.metalCrystal, 1, 1),
						new ItemStack(Items.GOLD_INGOT, 2), new ItemStack(Items.SUGAR, 6))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 5), 180,
				new ArrayList<Object>(Arrays.asList(new ItemStack(ModItems.metalCrystal, 1, 0),
						new ItemStack(Items.IRON_INGOT, 3), new ItemStack(Items.SUGAR, 2))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 6), 420,
				new ArrayList<Object>(Arrays.asList(new ItemStack(ModItems.metalCrystal, 1, 0),
						new ItemStack(Items.IRON_INGOT, 3), new ItemStack(Items.GLOWSTONE_DUST, 3))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 7), 600,
				new ArrayList<Object>(Arrays.asList(new ItemStack(ModItems.metalCrystal, 1, 1),
						new ItemStack(Items.GOLD_INGOT, 4), new ItemStack(Items.DYE, 8, 4))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 8), 160,
				new ArrayList<Object>(Arrays.asList(new ItemStack(ModItems.metalCrystal, 1, 0),
						new ItemStack(Items.IRON_INGOT, 3), new ItemStack(Items.DYE, 6, 15))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 9), 300,
				new ArrayList<Object>(Arrays.asList(new ItemStack(ModItems.metalCrystal, 1, 0),
						new ItemStack(Items.IRON_INGOT, 5), new ItemStack(Items.COAL, 4))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 10), 700,
				new ArrayList<Object>(Arrays.asList(new ItemStack(ModItems.metalCrystal, 1, 1),
						new ItemStack(Items.GOLD_INGOT, 2), new ItemStack(ModItems.techComponent, 3, 1))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 11), 600,
				new ArrayList<Object>(
						Arrays.asList(new ItemStack(ModItems.metalCrystal, 1, 2), new ItemStack(Items.SUGAR, 8),
								new ItemStack(Items.FLINT, 6), new ItemStack(Items.GOLD_INGOT, 3))));

		ProcessRecipeManager.combustionRecipes
				.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 12), 1200,
						new ArrayList<Object>(Arrays.asList(new ItemStack(ModItems.metalCrystal, 1, 11),
								new ItemStack(Items.DYE, 9, 4), new ItemStack(Items.QUARTZ, 3),
								new ItemStack(Items.GOLD_INGOT, 2))));

		ProcessRecipeManager.combustionRecipes
				.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 13), 1200,
						new ArrayList<Object>(Arrays.asList(new ItemStack(ModItems.metalCrystal, 1, 11),
								new ItemStack(Items.MAGMA_CREAM, 5), new ItemStack(Items.QUARTZ, 2),
								new ItemStack(Items.GOLD_INGOT, 3))));

		ProcessRecipeManager.combustionRecipes
				.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 14), 800,
						new ArrayList<Object>(Arrays.asList(new ItemStack(ModItems.metalCrystal, 1, 1),
								new ItemStack(Items.GOLD_INGOT, 3), new ItemStack(Items.QUARTZ, 2),
								new ItemStack(Items.REDSTONE, 7))));

		ProcessRecipeManager.combustionRecipes
				.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 15), 1200,
						new ArrayList<Object>(Arrays.asList(new ItemStack(ModItems.metalCrystal, 1, 0),
								new ItemStack(Items.GOLD_INGOT, 2), new ItemStack(Items.GOLD_NUGGET, 7),
								new ItemStack(Items.DIAMOND, 1))));

		ProcessRecipeManager.combustionRecipes
				.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 16), 2000,
						new ArrayList<Object>(Arrays.asList(new ItemStack(ModItems.metalCrystal, 1, 1),
								new ItemStack(Items.GOLD_INGOT, 6), new ItemStack(Items.ENDER_EYE, 3),
								new ItemStack(Items.DIAMOND, 1))));

		ProcessRecipeManager.combustionRecipes
				.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 17), 700,
						new ArrayList<Object>(Arrays.asList(new ItemStack(ModItems.metalCrystal, 1, 11),
								new ItemStack(Items.REDSTONE, 8), new ItemStack(Items.FLINT, 3),
								new ItemStack(Blocks.STONE, 6))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(ModBlocks.dryCactus), 400,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Blocks.BONE_BLOCK), new ItemStack(Items.DYE, 8, 7),
						new ItemStack(Blocks.LEAVES, 8, 1))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(Blocks.CACTUS), 300,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.SPIDER_EYE, 1),
						new ItemStack(Items.SNOWBALL, 16), new ItemStack(ModBlocks.dryCactus))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(Items.REDSTONE, 4), 400, new ArrayList<Object>(
				Arrays.asList(new ItemStack(Items.GUNPOWDER, 2), new ItemStack(Items.BLAZE_POWDER, 2))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(ModItems.alchemyComponent, 4, 1), 400,
				new ArrayList<Object>(
						Arrays.asList(new ItemStack(Items.COAL, 3), new ItemStack(ModItems.alchemyComponent, 1, 2))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(ModItems.alchemyComponent, 1, 3), 1200,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.DIAMOND, 1),
						new ItemStack(ModItems.alchemyComponent, 8, 2))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(ModItems.alchemyComponent, 1, 4), 1000,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.GOLD_INGOT, 1),
						new ItemStack(ModItems.alchemyComponent, 4, 2))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(ModItems.alchemyComponent, 4, 2), 700,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.REDSTONE, 1), new ItemStack(Items.DYE, 1, 4),
						new ItemStack(Items.GLOWSTONE_DUST, 1), new ItemStack(Items.BLAZE_POWDER, 1))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(Items.WHEAT_SEEDS, 1), 50,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Blocks.DEADBUSH, 1), new ItemStack(Items.FLINT, 2))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(Blocks.DIRT), 100,
				new ArrayList<Object>(Arrays.asList(new ItemStack(ModItems.baseComponent, 8, 2))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(Items.SLIME_BALL), 200, new ArrayList<Object>(
				Arrays.asList(new ItemStack(ModItems.baseComponent, 8, 2), new ItemStack(Items.SNOWBALL))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(ModItems.techComponent, 2, 1), 1400,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.POISONOUS_POTATO, 3),
						new ItemStack(Items.FERMENTED_SPIDER_EYE, 2), new ItemStack(Items.SLIME_BALL, 1),
						new ItemStack(Items.GUNPOWDER, 2))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(Items.PRISMARINE_SHARD, 4), 800,
				new ArrayList<Object>(
						Arrays.asList(new ItemStack(Items.QUARTZ, 4), new ItemStack(Blocks.MOSSY_COBBLESTONE))));
		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(Items.PRISMARINE_CRYSTALS, 4), 1200,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.QUARTZ, 4), new ItemStack(Blocks.GLASS, 3))));

		ItemStack waterBottle = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER);

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(Items.SNOWBALL), 200,
				new ArrayList<Object>(Arrays.asList(waterBottle)));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(Blocks.NETHERRACK, 4), 800,
				new ArrayList<Object>(
						Arrays.asList(new ItemStack(Blocks.COBBLESTONE, 8), new ItemStack(Items.BLAZE_POWDER, 2))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(ModItems.baseComponent, 1, 5), 1400,
				new ArrayList<Object>(
						Arrays.asList(new ItemStack(Blocks.OBSIDIAN, 3), new ItemStack(Items.NETHERBRICK, 6),
								new ItemStack(OreDictionary.getOres(steelIngot).get(0).getItem(), 3,
										OreDictionary.getOres(steelIngot).get(0).getMetadata()))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(Items.GLOWSTONE_DUST, 5), 500,
				new ArrayList<Object>(
						Arrays.asList(new ItemStack(Items.REDSTONE, 4), new ItemStack(Items.BLAZE_POWDER, 2))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(Items.QUARTZ, 6), 600, new ArrayList<Object>(
				Arrays.asList(new ItemStack(ModItems.metalCrystal, 1, 11), new ItemStack(Items.FLINT, 3))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(Items.DYE, 8, 4), 800, new ArrayList<Object>(
				Arrays.asList(new ItemStack(ModItems.metalCrystal, 1, 15), new ItemStack(Items.FLINT, 3))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(Blocks.END_STONE, 1), 1400,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Blocks.STONE, 6, 3), new ItemStack(Items.SUGAR, 2),
						new ItemStack(Items.ENDER_PEARL, 4), new ItemStack(Items.QUARTZ, 2),
						new ItemStack(Blocks.BONE_BLOCK, 4))));

		ProcessRecipeManager.rockGrinderRecipes.addRecipe(new ItemStack(Blocks.GRAVEL), 1,
				new ItemStack(Blocks.COBBLESTONE));
		ProcessRecipeManager.rockGrinderRecipes.addRecipe(new ItemStack(Blocks.SAND), 1, new ItemStack(Blocks.GRAVEL));
		ProcessRecipeManager.rockGrinderRecipes.addRecipe(new ItemStack(Items.FLINT), .3f,
				new ItemStack(Blocks.GRAVEL));
		ProcessRecipeManager.rockGrinderRecipes.addRecipe(new ItemStack(ModItems.techComponent, 1, 0), 1,
				new ItemStack(Blocks.STONE));
		ProcessRecipeManager.rockGrinderRecipes.addRecipe(new ItemStack(ModItems.techComponent, 1, 3), 1,
				new ItemStack(Blocks.NETHERRACK));
		ProcessRecipeManager.rockGrinderRecipes.addRecipe(new ItemStack(ModItems.baseComponent, 1, 7), 1.5f,
				new ItemStack(Blocks.LOG, 1, OreDictionary.WILDCARD_VALUE));
		ProcessRecipeManager.rockGrinderRecipes.addRecipe(new ItemStack(ModItems.baseComponent, 1, 7), 1.5f,
				new ItemStack(Blocks.LOG2, 1, OreDictionary.WILDCARD_VALUE));

		for (int i = 0; i < ModItems.gemList.size(); i++)
		{
			if (ConfigOptions.allowAllGemTypes || OreDictionary
					.getOres("gem" + RandomHelper.capatilizeString(ModItems.gemList.get(i).name)).size() > 0)
				ProcessRecipeManager.rockGrinderRecipes.addRecipe(new ItemStack(ModItems.dirtyGem, 1, i),
						ModItems.gemList.get(i).rarity, new ItemStack(Blocks.STONE));
		}

		for (int i = 0; i < ModFluids.crystalFluidInfos().length; i++)
		{
			ProcessRecipeManager.crucibleRecipes.addRecipe(new FluidStack(ModFluids.crystalFluids.get(i), 1000), 0,
					new ItemStack(ModItems.metalCrystal, 1, ModFluids.crystalFluidInfos()[i].crystalIndex));
		}
		ProcessRecipeManager.crucibleRecipes.addRecipe(new FluidStack(FluidRegistry.LAVA, 200), 0,
				new ItemStack(Blocks.NETHERRACK));

		ProcessRecipeManager.crucibleRecipes.addRecipe(new FluidStack(FluidRegistry.LAVA, 1000), 0,
				new ItemStack(ModBlocks.blazePowderBlock));

		ProcessRecipeManager.waterExtractorExtractRecipes.addRecipe(
				new ArrayList<Object>(
						Arrays.asList(new ItemStack(ModBlocks.dryCactus), new FluidStack(FluidRegistry.WATER, 50))),
				0, new ItemStack(Blocks.CACTUS));
		ProcessRecipeManager.waterExtractorExtractRecipes.addRecipe(
				new ArrayList<Object>(Arrays.asList(ItemStack.EMPTY, new FluidStack(FluidRegistry.WATER, 50))), 0,
				new ItemStack(Blocks.SNOW));
		ProcessRecipeManager.waterExtractorExtractRecipes.addRecipe(
				new ArrayList<Object>(Arrays.asList(ItemStack.EMPTY, new FluidStack(FluidRegistry.WATER, 20))), 0,
				new ItemStack(Blocks.LEAVES));
		ProcessRecipeManager.waterExtractorExtractRecipes.addRecipe(
				new ArrayList<Object>(Arrays.asList(ItemStack.EMPTY, new FluidStack(FluidRegistry.WATER, 20))), 0,
				new ItemStack(Blocks.LEAVES2));

		ProcessRecipeManager.waterExtractorInsertRecipes.addRecipe(new ItemStack(Blocks.CLAY), 0, new ArrayList<Object>(
				Arrays.asList(new ItemStack(Blocks.DIRT), new FluidStack(FluidRegistry.WATER, 200))));

		ProcessRecipeManager.freezerRecipes.addRecipe(new ItemStack(ModItems.heavySnowball), 40,
				new ItemStack(Items.SNOWBALL, 4));
		ProcessRecipeManager.freezerRecipes.addRecipe(new ItemStack(Blocks.DIRT, 1, 1), 800,
				new ItemStack(ModBlocks.heavySnow2));
		ProcessRecipeManager.freezerRecipes.addRecipe(new ItemStack(Blocks.ICE), 2000, waterBottle);
		ProcessRecipeManager.freezerRecipes.addRecipe(new ItemStack(ModItems.techComponent, 1, 2), 3000,
				new ItemStack(Items.IRON_INGOT));
		ProcessRecipeManager.freezerRecipes.addRecipe(new ItemStack(Blocks.SOUL_SAND), 1500,
				new ItemStack(ModBlocks.sandyNetherrack));

		MinecraftForge.addGrassSeed(new ItemStack(Items.BEETROOT_SEEDS), 10);
		MinecraftForge.addGrassSeed(new ItemStack(Items.MELON_SEEDS), 8);
		MinecraftForge.addGrassSeed(new ItemStack(Items.PUMPKIN_SEEDS), 8);
		MinecraftForge.addGrassSeed(new ItemStack(Items.DYE, 1, 3), 4);

		HeatSources.addHeatSource(Blocks.FIRE.getDefaultState(), 7);
		HeatSources.addHeatSource(Blocks.LAVA.getDefaultState(), 5);
		HeatSources.addHeatSource(Blocks.FLOWING_LAVA.getDefaultState(), 3);
		HeatSources.addHeatSource(Blocks.TORCH.getDefaultState(), 1);
		HeatSources.addHeatSource(Blocks.OBSIDIAN.getDefaultState(), 4);
		Block magmaBlock = Block.REGISTRY.getObject(new ResourceLocation("minecraft", "magma"));
		HeatSources.addHeatSource(magmaBlock.getDefaultState(), 8);

		GameRegistry.registerFuelHandler(new ModFuelHandler());

		/*
		 * for (int i = 0; i < ModFluids.crystalFluidInfos().length; i++) {
		 * String oreName = "ore" +
		 * RandomHelper.capatilizeString(ModFluids.crystalFluidInfos()[i].name);
		 * 
		 * if (OreDictionary.getOres(oreName).size() > 0) {
		 * 
		 * ProcessRecipeManager.concentratorRecipes.addRecipe(
		 * Block.getBlockFromItem(OreDictionary.getOres(oreName).get(0).
		 * getItem()) .getStateFromMeta(OreDictionary.getOres(oreName).get(0).
		 * getMetadata()), ModFluids.crystalFluidInfos()[i].rarity * 100, new
		 * ItemStack(ModItems.metalCrystal,
		 * ConfigOptions.crystalConcentratorAmount,
		 * ModFluids.crystalFluidInfos()[i].crystalIndex),
		 * ModBlocks.compressedStone.getDefaultState());
		 * 
		 * } }
		 */

		/*
		 * ConcentratorRecipes.addRecipe(Blocks.COAL_ORE.getDefaultState(), 100,
		 * new ItemStack(ModItems.alchemyComponent, 1, 2),
		 * ModBlocks.compressedStone.getDefaultState());
		 * 
		 * ConcentratorRecipes.addRecipe(Blocks.DIAMOND_ORE.getDefaultState(),
		 * 800, new ItemStack(ModItems.alchemyComponent, 1, 4),
		 * ModBlocks.compressedStone.getDefaultState());
		 * 
		 * ConcentratorRecipes.addRecipe(Blocks.REDSTONE_ORE.getDefaultState(),
		 * 700, new ItemStack(ModItems.alchemyComponent, 12, 3),
		 * ModBlocks.compressedStone.getDefaultState());
		 * 
		 * if (OreDictionary.getOres("oreUranium").size() > 0) {
		 * ConcentratorRecipes.addRecipe(
		 * Block.getBlockFromItem(OreDictionary.getOres("oreUranium").get(0).
		 * getItem())
		 * .getStateFromMeta(OreDictionary.getOres("oreUranium").get(0).
		 * getMetadata()), 1500, new ItemStack(ModItems.techComponent, 4, 1),
		 * ModBlocks.compressedStone.getDefaultState()); }
		 */
		for (FluidRegisterInfo i : ModFluids.crystalFluidInfos())
		{
			String dust = "dust" + RandomHelper.capatilizeString(i.name);
			if (OreDictionary.getOres(dust).size() > 0 && i.type == CrystalFluidType.NORMAL)
			{
				ProcessRecipeManager.cauldronCleanRecipes.addRecipe(OreDictionary.getOres(dust).get(0),
						1F / ((float) i.rarity * 2F), new ItemStack(ModItems.techComponent, 1, 0));
			} else if (OreDictionary.getOres(dust).size() > 0 && i.type == CrystalFluidType.MOLTEN)
			{
				ProcessRecipeManager.cauldronCleanRecipes.addRecipe(OreDictionary.getOres(dust).get(0),
						1F / ((float) i.rarity * 3F), new ItemStack(ModItems.techComponent, 1, 3));
			}
		}
		for (GemRegisterInfo i : ModItems.gemList)
		{
			String gem = "gem" + RandomHelper.capatilizeString(i.name);
			if (OreDictionary.getOres(gem).size() > 0)
			{
				ProcessRecipeManager.cauldronCleanRecipes.addRecipe(OreDictionary.getOres(gem).get(0), 1F,
						new ItemStack(ModItems.dirtyGem, 1, ModItems.gemList.indexOf(i)));
			}
		}

		LootTableList.register(new ResourceLocation(References.ModID, "gameplay/fishingsurvivalist"));
		LootTableList.register(new ResourceLocation(References.ModID, "gameplay/fishing/survivalistjunk"));
	}

	public static void initOreDict()
	{

		OreDictionary.registerOre("toolCuttingKnife",
				new ItemStack(ModItems.cactusKnife, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("toolCuttingKnife",
				new ItemStack(ModItems.ironKnife, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("toolCuttingKnife",
				new ItemStack(ModItems.diamondKnife, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("ingotFrozenIron", new ItemStack(ModItems.techComponent, 1, 2));
		OreDictionary.registerOre("dustWood", new ItemStack(ModItems.baseComponent, 1, 7));

	}
}
