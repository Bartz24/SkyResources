package com.bartz24.skyresources.registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.References;
import com.bartz24.skyresources.alchemy.FusionCatalysts;
import com.bartz24.skyresources.alchemy.item.ItemOreAlchDust;
import com.bartz24.skyresources.base.HeatSources;
import com.bartz24.skyresources.base.MachineVariants;
import com.bartz24.skyresources.base.ModFuelHandler;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;
import com.bartz24.skyresources.technology.item.GemRegisterInfo;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class ModCrafting
{
	public static void init()
	{
		CraftingRegistry.addShapedOreRecipe(new ItemStack(Blocks.SNOW_LAYER, 3),
				new Object[] { "XX", 'X', new ItemStack(Items.SNOWBALL) });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModItems.survivalistFishingRod),
				new Object[] { " X", "XY", 'X', new ItemStack(Items.STICK), 'Y', new ItemStack(Items.STRING) });
		CraftingRegistry.addShapelessOreRecipe(new ItemStack(ModItems.cactusFruit, 2),
				new Object[] { new ItemStack(Blocks.CACTUS), "toolCuttingKnife" });
		CraftingRegistry.addShapelessOreRecipe(new ItemStack(Items.MELON, 9),
				new Object[] { new ItemStack(Blocks.MELON_BLOCK, 1), "toolCuttingKnife" });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModItems.cactusKnife),
				new Object[] { " #", "# ", '#', new ItemStack(ModItems.alchemyComponent, 1, 0) });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModItems.ironKnife), new Object[] { "#  ", "#X ", " #X", 'X',
				new ItemStack(Items.STICK), '#', new ItemStack(Items.IRON_INGOT) });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModItems.diamondKnife), new Object[] { "#  ", "#X ", " #X",
				'X', new ItemStack(Items.STICK), '#', new ItemStack(Items.DIAMOND) });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModItems.stoneGrinder), new Object[] { "#  ", " # ", "  X",
				'X', new ItemStack(Items.STICK), '#', new ItemStack(Blocks.COBBLESTONE) });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModItems.ironGrinder), new Object[] { "#  ", " # ", "  X",
				'X', new ItemStack(Items.STICK), '#', new ItemStack(Items.IRON_INGOT) });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModItems.diamondGrinder), new Object[] { "#  ", " # ", "  X",
				'X', new ItemStack(Items.STICK), '#', new ItemStack(Items.DIAMOND) });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModBlocks.cactusFruitNeedle), new Object[] { "X", "Y", 'X',
				new ItemStack(ModItems.cactusFruit), 'Y', new ItemStack(ModItems.alchemyComponent, 1, 0) });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModItems.sandstoneInfusionStone),
				new Object[] { "X", "Y", 'X', new ItemStack(ModItems.alchemyComponent, 1, 0), 'Y',
						new ItemStack(Blocks.SANDSTONE, 1, OreDictionary.WILDCARD_VALUE) });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModItems.redSandstoneInfusionStone),
				new Object[] { "X", "Y", 'X', new ItemStack(ModItems.alchemyComponent, 1, 0), 'Y',
						new ItemStack(Blocks.RED_SANDSTONE, 1, OreDictionary.WILDCARD_VALUE) });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModItems.alchemicalInfusionStone),
				new Object[] { "X", "Y", 'X', new ItemStack(ModItems.alchemyComponent, 1, 9), 'Y',
						new ItemStack(ModItems.alchemyComponent, 1, 10) });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModItems.alchemyComponent, 1, 9),
				new Object[] { "X", "X", 'X', new ItemStack(ModItems.alchemyComponent, 1, 7) });
		CraftingRegistry.addShapelessOreRecipe(new ItemStack(ModItems.baseComponent, 4, 4), Items.ROTTEN_FLESH,
				new ItemStack(Items.DYE, 1, 15), new ItemStack(Items.DYE, 1, 15), new ItemStack(Items.DYE, 1, 15));
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModBlocks.compressedCoalBlock), new Object[] { "XXX", "XYX",
				"XXX", 'X', new ItemStack(Blocks.COAL_BLOCK), 'Y', new ItemStack(Items.COAL) });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModBlocks.blazePowderBlock),
				new Object[] { "XX", "XX", 'X', new ItemStack(Items.BLAZE_POWDER) });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModBlocks.heavySnow, 4),
				new Object[] { "XX", "XX", 'X', new ItemStack(ModItems.heavySnowball) });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModBlocks.miniFreezer),
				new Object[] { "X", "X", 'X', new ItemStack(Blocks.SNOW) });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModBlocks.coalInfusedBlock),
				new Object[] { "XXX", "XXX", "XXX", 'X', new ItemStack(ModItems.alchemyComponent, 1, 6) });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModItems.alchemyComponent, 9, 6),
				new Object[] { "X", 'X', new ItemStack(ModBlocks.coalInfusedBlock) });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModBlocks.darkMatterBlock),
				new Object[] { "XXX", "XXX", "XXX", 'X', new ItemStack(ModItems.baseComponent, 1, 3) });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModItems.baseComponent, 9, 3),
				new Object[] { "X", 'X', new ItemStack(ModBlocks.darkMatterBlock) });
		CraftingRegistry.addShapelessOreRecipe(new ItemStack(Blocks.SAPLING, 1, 1),
				new Object[] { new ItemStack(Blocks.DIRT, 1, OreDictionary.WILDCARD_VALUE),
						new ItemStack(Items.PUMPKIN_SEEDS), new ItemStack(Items.PUMPKIN_SEEDS),
						new ItemStack(Items.DYE, 1, 15) });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModItems.baseComponent, 3, 0),
				new Object[] { " X ", "XXX", " X ", 'X', "treeSapling" });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModItems.baseComponent, 3, 0),
				new Object[] { " X ", "XXX", " X ", 'X', "cropWheat" });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModItems.baseComponent, 3, 0),
				new Object[] { " X ", "XXX", " X ", 'X', "treeLeaves" });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModItems.baseComponent, 3, 0),
				new Object[] { " X ", "XXX", " X ", 'X', ModItems.cactusFruit });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModItems.baseComponent, 3, 0),
				new Object[] { " X ", "XXX", " X ", 'X', "cropPotato" });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModItems.baseComponent, 3, 0),
				new Object[] { " X ", "XXX", " X ", 'X', "cropCarrot" });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModItems.baseComponent, 3, 0),
				new Object[] { " X ", "XXX", " X ", 'X', "sugarcane" });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModItems.baseComponent, 3, 0),
				new Object[] { " X ", "XXX", " X ", 'X', "dustWood" });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModBlocks.ironFreezer), new Object[] { "XXX", "XZX", "XXX",
				'X', "ingotFrozenIron", 'Z', new ItemStack(ModBlocks.miniFreezer) });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModBlocks.dirtFurnace),
				new Object[] { "X", "Y", 'X', "dirt", 'Y', new ItemStack(ModItems.heatComponent, 1, 0) });

		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModItems.waterExtractor),
				new Object[] { "XXX", " XX", 'X', "plankWood" });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModBlocks.crucible),
				new Object[] { "X X", "X X", "XXX", 'X', new ItemStack(Items.BRICK) });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModBlocks.fluidDropper),
				new Object[] { "XXX", "X X", "X X", 'X', "cobblestone" });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModItems.fleshySnowNugget, 3), new Object[] { "XX", "XY", 'X',
				new ItemStack(Items.SNOWBALL), 'Y', new ItemStack(Items.ROTTEN_FLESH) });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModItems.baseComponent, 1, 2),
				new Object[] { "XXX", "XYX", "XXX", 'X', "ingotFrozenIron", 'Y', new ItemStack(Items.GLOWSTONE_DUST) });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModBlocks.combustionCollector),
				new Object[] { "XXX", "XYX", "XXX", 'X', "ingotIron", 'Y', new ItemStack(Blocks.HOPPER) });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModBlocks.quickDropper), new Object[] { "XXX", "XZX", "XYX",
				'X', "ingotIron", 'Y', new ItemStack(Blocks.DROPPER), 'Z', new ItemStack(Blocks.GLOWSTONE) });

		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModBlocks.darkMatterWarper), new Object[] { "XXX", "XYX",
				"XXX", 'X', new ItemStack(Blocks.OBSIDIAN), 'Y', new ItemStack(ModItems.baseComponent, 1, 3) });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModBlocks.endPortalCore),
				new Object[] { "XZX", "AYA", "XZX", 'X', new ItemStack(ModItems.baseComponent, 1, 3), 'Y',
						new ItemStack(Items.ENDER_EYE), 'Z', new ItemStack(ModItems.techComponent, 1, 2), 'A',
						new ItemStack(ModItems.alchemyComponent, 1, 7) });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModBlocks.sandyNetherrack, 4),
				new Object[] { "XY", "ZX", 'X', new ItemStack(Blocks.SAND), 'Y', new ItemStack(Items.NETHER_WART), 'Z',
						new ItemStack(Blocks.NETHERRACK) });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModBlocks.lifeInfuser),
				new Object[] { "XXX", " X ", " Y ", 'X', "logWood", 'Y', new ItemStack(ModItems.alchemicalInfusionStone) });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModBlocks.lifeInjector),
				new Object[] { " Y ", " X ", "XXX", 'X', "logWood", 'Y', new ItemStack(Items.DIAMOND_SWORD) });
		CraftingRegistry.addShapelessOreRecipe(new ItemStack(ModItems.heavyExpSnowball, 3),
				new ItemStack(ModItems.heavySnowball), new ItemStack(ModItems.heavySnowball),
				new ItemStack(ModItems.heavySnowball), new ItemStack(Items.GUNPOWDER));
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModBlocks.crucibleInserter),
				new Object[] { "XYX", "X X", "X X", 'X', "ingotIron", 'Y', new ItemStack(Blocks.DROPPER) });
		CraftingRegistry.addShapedOreRecipe(new ItemStack(ModBlocks.fusionTable),
				new Object[] { "XZX", "XYX", "X X", 'X', "plankWood", 'Y',
						new ItemStack(ModItems.alchComponent, 1, 1), 'Z',
						new ItemStack(ModItems.alchemyComponent, 1, 2) });

		Object advComponent = getModMaterial("Steel", ConfigOptions.recipeDifficulty) != null
				? getModMaterial("Steel", ConfigOptions.recipeDifficulty)
				: getModMaterial("ElectricalSteel", ConfigOptions.recipeDifficulty) != null
						? getModMaterial("ElectricalSteel", ConfigOptions.recipeDifficulty)
						: getModMaterial("Iron", ConfigOptions.recipeDifficulty);
		String coalDust = OreDictionary.getOres("dustCoal").size() > 0 ? "dustCoal" : "dustRedstone";
		String circuit = (ConfigOptions.recipeDifficulty ? OreDictionary.getOres("circuitAdvanced")
				: OreDictionary.getOres("circuitBasic")).size() > 0
						? (ConfigOptions.recipeDifficulty ? "circuitAdvanced" : "circuitBasic") : "blockRedstone";
		{
			CraftingRegistry.addShapedOreRecipe(new ItemStack(ModItems.baseComponent, 1, 1),
					new Object[] { "XZX", "XYX", "XZX", 'X', advComponent, 'Y', circuit, 'Z', coalDust });
			CraftingRegistry
					.addShapedOreRecipe(new ItemStack(ModBlocks.rockCrusher),
							new Object[] { "XXX", "XYX", "XZX", 'X', advComponent, 'Y',
									new ItemStack(ModItems.diamondGrinder), 'Z',
									new ItemStack(ModItems.baseComponent, 1, 1) });
			CraftingRegistry.addShapedOreRecipe(new ItemStack(ModBlocks.rockCleaner),
					new Object[] { "XXX", "XYX", "XZX", 'X', advComponent, 'Y', new ItemStack(Items.CAULDRON), 'Z',
							new ItemStack(ModItems.baseComponent, 1, 1) });
			CraftingRegistry.addShapedOreRecipe(new ItemStack(ModBlocks.aqueousConcentrator),
					new Object[] { "XAX", "XZX", "XYX", 'X', advComponent, 'Y',
							new ItemStack(ModItems.baseComponent, 1, 1), 'Z', new ItemStack(ModItems.waterExtractor),
							'A', new ItemStack(Blocks.SNOW) });
			CraftingRegistry.addShapedOreRecipe(new ItemStack(ModBlocks.aqueousDeconcentrator),
					new Object[] { "XAX", "XZX", "XYX", 'X', advComponent, 'Y',
							new ItemStack(ModItems.baseComponent, 1, 1), 'Z', new ItemStack(ModItems.waterExtractor),
							'A', new ItemStack(Blocks.SAND) });
		}
		GameRegistry.addSmelting(ModBlocks.dryCactus, new ItemStack(Items.DYE, 1, 7), 0.2F);

		GameRegistry.addSmelting(new ItemStack(ModItems.baseComponent, 1, 0), new ItemStack(Items.COAL, 1, 1), 0.1F);

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
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.SUGAR, 3), new ItemStack(Blocks.HAY_BLOCK))));

		ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(Blocks.SAPLING, 1, 5), 19, new ArrayList<Object>(
				Arrays.asList(new ItemStack(Items.GUNPOWDER, 10), new ItemStack(Blocks.SAPLING, 1, 0))));

		ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(Blocks.SAPLING, 1, 3), 19,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.DYE, 10, 3), "treeSapling")));

		ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(Blocks.SAPLING, 1, 2), 19,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.DYE, 10, 15), "treeSapling")));

		ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(Items.REEDS), 19,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.SPECKLED_MELON, 3),
						new ItemStack(Blocks.PUMPKIN, 1, OreDictionary.WILDCARD_VALUE))));

		ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(Items.NETHER_WART), 19,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.SPIDER_EYE, 8),
						new ItemStack(Blocks.RED_MUSHROOM, 1, OreDictionary.WILDCARD_VALUE))));

		ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(Blocks.RED_MUSHROOM), 15,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.DYE, 8, 1),
						new ItemStack(Blocks.TALLGRASS, 1, OreDictionary.WILDCARD_VALUE))));

		ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(Blocks.BROWN_MUSHROOM), 15,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.DYE, 8, 3),
						new ItemStack(Blocks.TALLGRASS, 1, OreDictionary.WILDCARD_VALUE))));

		ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(ModItems.healthGem), 15,
				new ArrayList<Object>(Arrays.asList(new ItemStack(ModItems.alchemyComponent, 1, 10),
						new ItemStack(Blocks.CHORUS_FLOWER, 1, OreDictionary.WILDCARD_VALUE))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(Items.COAL, 1), 170,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.COAL, 1, 1))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(Items.BLAZE_POWDER, 3), 75,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.GUNPOWDER))));
		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(Items.GUNPOWDER), 1120,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.FLINT))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(Items.DIAMOND, 1), 1600,
				new ArrayList<Object>(Arrays.asList(new ItemStack(ModBlocks.compressedCoalBlock, 1))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(Blocks.SAND, 12, 1), 200,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Blocks.SAND, 12), new ItemStack(Items.DYE, 1, 1))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(ModBlocks.dryCactus), 400,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Blocks.BONE_BLOCK), new ItemStack(Items.DYE, 8, 7),
						new ItemStack(Blocks.LEAVES, 8, 1))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(Blocks.CACTUS), 300,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.SPIDER_EYE, 1),
						new ItemStack(Items.SNOWBALL, 16), new ItemStack(ModBlocks.dryCactus))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(Items.REDSTONE, 4), 880, new ArrayList<Object>(
				Arrays.asList(new ItemStack(Items.GUNPOWDER, 2), new ItemStack(Items.BLAZE_POWDER, 2))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(Items.WHEAT_SEEDS, 1), 50,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Blocks.DEADBUSH, 1), new ItemStack(Items.FLINT, 2))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(Blocks.DIRT), 100,
				new ArrayList<Object>(Arrays.asList(new ItemStack(ModItems.baseComponent, 8, 0))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(Items.SLIME_BALL), 200, new ArrayList<Object>(
				Arrays.asList(new ItemStack(ModItems.baseComponent, 8, 0), new ItemStack(Items.SNOWBALL))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(ModItems.techComponent, 4, 1), 2700,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.POISONOUS_POTATO, 3),
						new ItemStack(Items.FERMENTED_SPIDER_EYE, 2), new ItemStack(Items.GUNPOWDER, 2))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(Items.PRISMARINE_SHARD, 4), 1900,
				new ArrayList<Object>(
						Arrays.asList(new ItemStack(Items.QUARTZ, 4), new ItemStack(Blocks.MOSSY_COBBLESTONE))));
		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(Items.PRISMARINE_CRYSTALS, 4), 2100,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.QUARTZ, 4), new ItemStack(Blocks.GLASS, 3))));

		ItemStack waterBottle = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER);

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(Items.SNOWBALL), 400,
				new ArrayList<Object>(Arrays.asList(waterBottle)));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(Blocks.NETHERRACK, 4), 920,
				new ArrayList<Object>(
						Arrays.asList(new ItemStack(Blocks.COBBLESTONE, 8), new ItemStack(Items.BLAZE_POWDER, 2))));

		String hardestIngot = "ingotIron";
		if (ConfigOptions.recipeDifficulty && OreDictionary.getOres("ingotTungstensteel").size() > 0)
			hardestIngot = "ingotTungstensteel";
		else if (ConfigOptions.recipeDifficulty && OreDictionary.getOres("ingotEnderium").size() > 0)
			hardestIngot = "ingotEnderium";
		else if (ConfigOptions.recipeDifficulty && OreDictionary.getOres("ingotPlatinum").size() > 0)
			hardestIngot = "ingotPlatinum";
		else if (ConfigOptions.recipeDifficulty && OreDictionary.getOres("ingotTungsten").size() > 0)
			hardestIngot = "ingotTungsten";
		else if (OreDictionary.getOres("ingotSteel").size() > 0)
			hardestIngot = "ingotSteel";

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(ModItems.baseComponent, 1, 3), 2900,
				new ArrayList<Object>(
						Arrays.asList(new ItemStack(Blocks.OBSIDIAN, 3), new ItemStack(Items.NETHERBRICK, 6),
								new ItemStack(OreDictionary.getOres(hardestIngot).get(0).getItem(), 3,
										OreDictionary.getOres(hardestIngot).get(0).getMetadata()))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(Items.GLOWSTONE_DUST, 5), 1700,
				new ArrayList<Object>(
						Arrays.asList(new ItemStack(Items.REDSTONE, 4), new ItemStack(Items.BLAZE_POWDER, 2))));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(Blocks.END_STONE, 1), 1800,
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
		ProcessRecipeManager.rockGrinderRecipes.addRecipe(new ItemStack(ModItems.baseComponent, 1, 5), 1.5f, "logWood");

		for (int i = 0; i < ModItems.gemList.size(); i++)
		{
			if (ConfigOptions.allowAllGemTypes || OreDictionary
					.getOres("gem" + RandomHelper.capatilizeString(ModItems.gemList.get(i).name)).size() > 0)
				ProcessRecipeManager.rockGrinderRecipes.addRecipe(new ItemStack(ModItems.dirtyGem, 1, i),
						ModItems.gemList.get(i).rarity, new ItemStack(Blocks.STONE));
		}

		ProcessRecipeManager.crucibleRecipes.addRecipe(new FluidStack(ModFluids.crystalFluid, 1000), 0,
				new ItemStack(ModItems.alchemyComponent, 1, 1));

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
				"treeLeaves");

		ProcessRecipeManager.waterExtractorInsertRecipes.addRecipe(new ItemStack(Blocks.CLAY), 0,
				Arrays.asList(new ItemStack(Blocks.DIRT), new FluidStack(FluidRegistry.WATER, 200)));

		ProcessRecipeManager.freezerRecipes.addRecipe(new ItemStack(ModItems.heavySnowball), 40,
				new ItemStack(Items.SNOWBALL, 4));
		ProcessRecipeManager.freezerRecipes.addRecipe(new ItemStack(Blocks.DIRT, 1, 1), 800,
				new ItemStack(ModBlocks.heavySnow));
		ProcessRecipeManager.freezerRecipes.addRecipe(new ItemStack(Blocks.ICE), 2000, waterBottle);
		ProcessRecipeManager.freezerRecipes.addRecipe(new ItemStack(ModItems.techComponent, 1, 2), 3000,
				new ItemStack(Items.IRON_INGOT));
		ProcessRecipeManager.freezerRecipes.addRecipe(new ItemStack(Blocks.SOUL_SAND), 1500,
				new ItemStack(ModBlocks.sandyNetherrack));

		for (int i = 0; i < ItemOreAlchDust.oreInfos.size(); i++)
		{
			String itemIngot = "ingot" + RandomHelper.capatilizeString(ItemOreAlchDust.oreInfos.get(i).name);
			if (OreDictionary.getOres(itemIngot).size() > 0)
			{
				ItemStack output = OreDictionary.getOres(itemIngot).get(0).copy();
				output.setCount(1);
				ProcessRecipeManager.condenserRecipes.addRecipe(output,
						(float) Math.pow(ItemOreAlchDust.oreInfos.get(i).rarity * 1.05f, 1.4f),
						Arrays.asList(new ItemStack(ModItems.oreAlchDust, 1, i),
								new FluidStack(ModFluids.crystalFluid, 1000)));
			}
			String itemOre = "ore" + RandomHelper.capatilizeString(ItemOreAlchDust.oreInfos.get(i).name);
			if (OreDictionary.getOres(itemOre).size() > 0)
			{
				ItemStack output = OreDictionary.getOres(itemOre).get(0).copy();
				output.setCount(1);
				ProcessRecipeManager.condenserRecipes.addRecipe(output,
						(float) Math.pow(ItemOreAlchDust.oreInfos.get(i).rarity * 1.05f, 2.3f),
						Arrays.asList(new ItemStack(ModItems.oreAlchDust, 1, i),
								ItemOreAlchDust.oreInfos.get(i).parentBlock));
			}
			String itemDust = "dust" + RandomHelper.capatilizeString(ItemOreAlchDust.oreInfos.get(i).name);
			if (OreDictionary.getOres(itemDust).size() > 0)
			{
				ItemStack output = OreDictionary.getOres(itemDust).get(0).copy();
				output.setCount(1);
				ProcessRecipeManager.cauldronCleanRecipes.addRecipe(output,
						1f / ((float)Math.pow((ItemOreAlchDust.oreInfos.get(i).rarity+2.5f) * 2.9f, 2.1f)),
						new ItemStack(ModItems.techComponent, 1, ItemOreAlchDust.oreInfos.get(i).parentBlock
								.isItemEqual(new ItemStack(Blocks.NETHERRACK)) ? 3 : 0));
			}
		}

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(ModItems.alchemyComponent, 5, 2), 335f,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.GUNPOWDER, 3),
						new ItemStack(Items.BLAZE_POWDER, 2), new ItemStack(Items.COAL, 1, 1))));
		ProcessRecipeManager.fusionRecipes.addRecipe(new ItemStack(ModItems.alchemyComponent, 5, 3), 0.0025f,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.REDSTONE, 2),
						new ItemStack(Items.BLAZE_POWDER, 2), new ItemStack(ModItems.alchemyComponent, 1, 8))));
		ProcessRecipeManager.fusionRecipes.addRecipe(new ItemStack(ModItems.alchemyComponent, 5, 4), 0.004f,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.GLOWSTONE_DUST, 2),
						new ItemStack(Items.DYE, 2, 4), new ItemStack(ModItems.alchemyComponent, 1, 7))));
		ProcessRecipeManager.fusionRecipes
				.addRecipe(new ItemStack(ModItems.alchemyComponent, 5, 5), 0.01f,
						new ArrayList<Object>(Arrays.asList(new ItemStack(ModItems.baseComponent, 1, 3),
								new ItemStack(Items.GLOWSTONE_DUST, 6),
								new ItemStack(ModItems.alchemyComponent, 3, 6))));

		ProcessRecipeManager.fusionRecipes.addRecipe(new ItemStack(ModItems.alchemyComponent, 1, 6), 0.0015f,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Items.COAL, 1), new ItemStack(Items.GUNPOWDER, 3))));
		ProcessRecipeManager.fusionRecipes.addRecipe(new ItemStack(ModItems.alchemyComponent, 1, 8), 0.002f,
				new ArrayList<Object>(
						Arrays.asList(new ItemStack(Items.IRON_INGOT, 1), new ItemStack(Items.BLAZE_POWDER, 3))));
		ProcessRecipeManager.fusionRecipes.addRecipe(new ItemStack(ModItems.alchemyComponent, 1, 7), 0.005f,
				new ArrayList<Object>(
						Arrays.asList(new ItemStack(Items.GOLD_INGOT, 1), new ItemStack(Items.GLOWSTONE_DUST, 3))));
		ProcessRecipeManager.fusionRecipes.addRecipe(new ItemStack(ModItems.alchemyComponent, 1, 10), 0.03f,
				new ArrayList<Object>(
						Arrays.asList(new ItemStack(Items.DIAMOND, 1), new ItemStack(Items.REDSTONE, 8))));

		ProcessRecipeManager.fusionRecipes.addRecipe(new ItemStack(ModItems.alchemyComponent, 1, 1), 0.001f,
				new ArrayList<Object>(Arrays.asList(new ItemStack(Blocks.GLASS, 2))));

		List<ItemStack> componentsOreDust = new ArrayList();
		componentsOreDust.add(new ItemStack(Items.ROTTEN_FLESH, 2));
		componentsOreDust.add(new ItemStack(Items.WHEAT, 4));
		componentsOreDust.add(new ItemStack(Items.PUMPKIN_SEEDS, 1));
		componentsOreDust.add(new ItemStack(Items.DYE, 3, 15));
		componentsOreDust.add(new ItemStack(Items.SUGAR, 3));
		componentsOreDust.add(new ItemStack(Items.WHEAT, 2));
		componentsOreDust.add(new ItemStack(Items.IRON_INGOT, 2));
		componentsOreDust.add(new ItemStack(Items.GOLD_INGOT, 3));
		componentsOreDust.add(new ItemStack(Items.BONE, 2));
		componentsOreDust.add(new ItemStack(ModItems.techComponent, 2, 1));
		componentsOreDust.add(new ItemStack(Items.GOLD_INGOT, 4));
		componentsOreDust.add(new ItemStack(Items.MAGMA_CREAM, 3));
		componentsOreDust.add(new ItemStack(Items.CLAY_BALL, 4));
		componentsOreDust.add(new ItemStack(Items.ENDER_EYE, 6));
		componentsOreDust.add(new ItemStack(Items.COAL, 3, 1));
		componentsOreDust.add(new ItemStack(Items.IRON_INGOT, 4));
		componentsOreDust.add(new ItemStack(Items.SUGAR, 4));
		componentsOreDust.add(new ItemStack(ModItems.techComponent, 4, 2));
		componentsOreDust.add(new ItemStack(Blocks.STONE, 8));
		componentsOreDust.add(new ItemStack(Items.PRISMARINE_SHARD, 1));
		componentsOreDust.add(new ItemStack(ModItems.baseComponent, 3, 4));
		componentsOreDust.add(new ItemStack(Items.DIAMOND, 2));

		for (int i = 0; i < ItemOreAlchDust.oreInfos.size(); i++)
		{
			String ingot = "ingot" + RandomHelper.capatilizeString(ItemOreAlchDust.oreInfos.get(i).name);
			if (OreDictionary.getOres(ingot).size() > 0)
			{
				ProcessRecipeManager.fusionRecipes.addRecipe(new ItemStack(ModItems.oreAlchDust, 1, i),
						ItemOreAlchDust.oreInfos.get(i).rarity * 0.0008f,
						new ArrayList<Object>(Arrays.asList(componentsOreDust.get(i),
								getOreItemDust(ItemOreAlchDust.oreInfos.get(i).rarity))));
				String dust = "dust" + RandomHelper.capatilizeString(ItemOreAlchDust.oreInfos.get(i).name);
				if (OreDictionary.getOres(dust).size() > 0)
				{
					ProcessRecipeManager.fusionRecipes.addRecipe(new ItemStack(ModItems.oreAlchDust, 1, i),
							ItemOreAlchDust.oreInfos.get(i).rarity * 0.004f, new ArrayList<Object>(
									Arrays.asList(dust, getOreItemDust(ItemOreAlchDust.oreInfos.get(i).rarity))));
				}
			}
		}

		MinecraftForge.addGrassSeed(new ItemStack(Items.BEETROOT_SEEDS), 10);
		MinecraftForge.addGrassSeed(new ItemStack(Items.MELON_SEEDS), 12);
		MinecraftForge.addGrassSeed(new ItemStack(Items.PUMPKIN_SEEDS), 12);
		MinecraftForge.addGrassSeed(new ItemStack(Items.DYE, 1, 3), 4);

		HeatSources.addHeatSource(Blocks.FIRE.getDefaultState(), 7);
		HeatSources.addHeatSource(Blocks.LAVA.getDefaultState(), 4);
		HeatSources.addHeatSource(Blocks.FLOWING_LAVA.getDefaultState(), 3);
		HeatSources.addHeatSource(Blocks.TORCH.getDefaultState(), 1);
		HeatSources.addHeatSource(Blocks.OBSIDIAN.getDefaultState(), 3);
		HeatSources.addHeatSource(Blocks.MAGMA.getDefaultState(), 6);

		FusionCatalysts.addCatalyst(new ItemStack(ModItems.alchemyComponent, 1, 2), 0.75f);
		FusionCatalysts.addCatalyst(new ItemStack(ModItems.alchemyComponent, 1, 3), 1.75f);
		FusionCatalysts.addCatalyst(new ItemStack(ModItems.alchemyComponent, 1, 4), 4.50f);
		FusionCatalysts.addCatalyst(new ItemStack(ModItems.alchemyComponent, 1, 5), 20.00f);

		GameRegistry.registerFuelHandler(new ModFuelHandler());

		if (OreDictionary.getOres("dustUranium").size() > 0)
		{
			for (ItemStack s : OreDictionary.getOres("dustUranium"))
			{
				if (Loader.isModLoaded("bigreactors")
						&& s.getItem() == Item.REGISTRY.getObject(new ResourceLocation("bigreactors", "dustmetals")))
					continue;
				ProcessRecipeManager.combustionRecipes
						.addRecipe(new ItemStack(s.getItem(), 5, s.getItemDamage()), 1100,
								new ArrayList<Object>(Arrays.asList(new ItemStack(Items.REDSTONE, 2),
										new ItemStack(Items.BLAZE_POWDER, 1),
										new ItemStack(ModItems.techComponent, 2, 1))));
			}
		}

		if (OreDictionary.getOres("dustThorium").size() > 0)
		{
			ItemStack s = OreDictionary.getOres("dustThorium").get(0);
			ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(s.getItem(), 5, s.getItemDamage()), 1500,
					new ArrayList<Object>(Arrays.asList(new ItemStack(Items.REDSTONE, 2),
							new ItemStack(Items.SPIDER_EYE, 1), new ItemStack(Items.BLAZE_POWDER, 3),
							new ItemStack(ModItems.techComponent, 5, 1))));
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

		for (int i = 0; i < MachineVariants.values().length; i++)
		{
			Object material = getMaterial(i, ConfigOptions.recipeDifficulty);
			Object altMaterial = getAltMaterial(i, ConfigOptions.recipeDifficulty);
			Object materialDust = getMaterialDust(i, false);
			Object materialAlch = getMaterialDust(i, true);
			Object materialGear = ConfigOptions.recipeDifficulty ? getGear(i) : null;

			if (material != null)
			{
				CraftingRegistry.addShapedOreRecipe(new ItemStack(ModItems.heatComponent, 1, i),
						new Object[] { "XXX", "XYX", "XXX", 'X', altMaterial, 'Y', materialDust });
				CraftingRegistry.addShapedOreRecipe(new ItemStack(ModItems.alchComponent, 1, i),
						new Object[] { "XXX", "XYX", "XXX", 'X', altMaterial, 'Y', materialAlch });
				if (i >= 4)
				{
					CraftingRegistry.addShapedOreRecipe(new ItemStack(ModItems.condenser, 1, i),
							new Object[] { "XYX", "XZX", "X X", 'X', material, 'Y',
									new ItemStack(ModItems.alchComponent, 1, i), 'Z',
									new ItemStack(ModItems.baseComponent, 1, 2) });
					CraftingRegistry.addShapedOreRecipe(new ItemStack(ModItems.combustionHeater, 1, i),
							new Object[] { "XXX", "XYX", "XZX", 'X', material, 'Y',
									new ItemStack(ModItems.heatComponent, 1, i), 'Z',
									new ItemStack(ModItems.baseComponent, 1, 2) });
				} else
				{
					CraftingRegistry.addShapedOreRecipe(new ItemStack(ModItems.condenser, 1, i), new Object[] { "XYX",
							"X X", "X X", 'X', material, 'Y', new ItemStack(ModItems.alchComponent, 1, i) });
					CraftingRegistry.addShapedOreRecipe(new ItemStack(ModItems.combustionHeater, 1, i), new Object[] {
							"XXX", "X X", "XYX", 'X', material, 'Y', new ItemStack(ModItems.heatComponent, 1, i) });

				}
				CraftingRegistry.addShapedOreRecipe(new ItemStack(ModItems.heatProvider, 1, i), new Object[] {
						"XYX", "XYX", "X X", 'X', material, 'Y', new ItemStack(ModItems.heatComponent, 1, i) });
				if (materialGear != null)
					CraftingRegistry.addShapedOreRecipe(new ItemStack(ModBlocks.casing, 1, i),
							new Object[] { "XXX", "XYX", "XXX", 'X', material, 'Y', materialGear });
				else
					CraftingRegistry.addShapedOreRecipe(new ItemStack(ModBlocks.casing, 1, i),
							new Object[] { "XXX", "X X", "XXX", 'X', material });
			}
		}

		LootTableList.register(new ResourceLocation(References.ModID, "gameplay/fishingsurvivalist"));
		LootTableList.register(new ResourceLocation(References.ModID, "gameplay/fishing/survivalistjunk"));

		MachineVariants.DARKMATTER.setFuel(new ItemStack(ModItems.baseComponent, 1, 3), 31415);
		MachineVariants.LEAD.setFuel(new ItemStack(ModItems.techComponent, 1, 1), 900);
	}

	private static ItemStack getOreItemDust(int rarity)
	{
		if (rarity <= 2)
			return new ItemStack(Items.GUNPOWDER, 2);
		else if (rarity <= 4)
			return new ItemStack(Items.BLAZE_POWDER, 2);
		else if (rarity <= 6)
			return new ItemStack(Items.GLOWSTONE_DUST, 2);
		else if (rarity <= 8)
			return new ItemStack(Items.DYE, 2, 4);
		else
			return new ItemStack(ModItems.baseComponent, 2, 3);
	}

	private static ItemStack getMaterialDust(int type, boolean alch)
	{
		if (type <= 3)
			return alch ? new ItemStack(ModItems.alchemyComponent, 1, 2) : new ItemStack(Items.GUNPOWDER, 1);
		else if (type <= 7)
			return alch ? new ItemStack(ModItems.alchemyComponent, 1, 3) : new ItemStack(Items.BLAZE_POWDER, 1);
		else if (type <= 9)
			return alch ? new ItemStack(ModItems.alchemyComponent, 1, 4) : new ItemStack(Items.REDSTONE, 1);
		else
			return alch ? new ItemStack(ModItems.alchemyComponent, 1, 5) : new ItemStack(Items.GLOWSTONE_DUST, 1);
	}

	private static Object getAltMaterial(int type, boolean diff)
	{
		if (type == 0)
			return "plankWood";

		return getMaterial(type, diff);
	}

	private static Object getMaterial(int type, boolean diff)
	{
		switch (type)
		{
		case 0:
			return "logWood";
		case 1:
			return new ItemStack(Blocks.STONE);
		case 2:
			return getModMaterial("Bronze", diff);
		case 3:
			return getModMaterial("Iron", diff);
		case 4:
			return getModMaterial("Steel", diff);
		case 5:
			return getModMaterial("Electrum", diff);
		case 6:
			return new ItemStack(Items.NETHERBRICK);
		case 7:
			return getModMaterial("Lead", diff);
		case 8:
			return getModMaterial("Manyullyn", diff);
		case 9:
			return getModMaterial("Signalum", diff);
		case 10:
			return new ItemStack(Blocks.END_STONE);
		case 11:
			return getModMaterial("Enderium", diff);
		case 12:
			return new ItemStack(ModItems.baseComponent, 1, 3);
		}
		return null;
	}

	private static Object getGear(int type)
	{
		switch (type)
		{
		case 0:
			return getModGear("Wood") != null ? getModGear("Wood") : "plankWood";
		case 1:
			return getModGear("Stone") != null ? getModGear("Stone") : new ItemStack(Blocks.STONEBRICK);
		case 2:
			return getModGear("Bronze");
		case 3:
			return getModGear("Iron");
		case 4:
			return getModGear("Steel");
		case 5:
			return getModGear("Electrum");
		case 6:
			return new ItemStack(Blocks.NETHER_BRICK);
		case 7:
			return getModGear("Lead");
		case 8:
			return getModGear("Manyullyn");
		case 9:
			return getModGear("Signalum");
		case 10:
			return new ItemStack(Blocks.PURPUR_BLOCK);
		case 11:
			return getModGear("Enderium");
		case 12:
			return new ItemStack(Items.NETHER_STAR);
		}
		return null;
	}

	private static Object getModMaterial(String type, boolean diff)
	{
		return (diff && OreDictionary.getOres("plate" + type).size() > 0) ? ("plate" + type)
				: (OreDictionary.getOres("ingot" + type).size() > 0 ? ("ingot" + type) : null);
	}

	private static Object getModGear(String type)
	{
		return OreDictionary.getOres("gear" + type).size() > 0 ? ("gear" + type) : null;
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
		OreDictionary.registerOre("dustWood", new ItemStack(ModItems.baseComponent, 1, 5));

	}
}
