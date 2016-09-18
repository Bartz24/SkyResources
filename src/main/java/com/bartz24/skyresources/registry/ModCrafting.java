package com.bartz24.skyresources.registry;

import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.References;
import com.bartz24.skyresources.SkyResources;
import com.bartz24.skyresources.alchemy.crucible.CrucibleRecipes;
import com.bartz24.skyresources.alchemy.fluid.FluidRegisterInfo;
import com.bartz24.skyresources.alchemy.fluid.FluidRegisterInfo.CrystalFluidType;
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
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelFluid.FluidLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModCrafting
{
	public static void init()
	{

		Block boneBlock = Block.REGISTRY.getObject(new ResourceLocation("minecraft", "bone_block"));
		GameRegistry.addRecipe(
				new ShapelessOreRecipe(new ItemStack(ModItems.cactusFruit, 2), new Object[]
				{ new ItemStack(Blocks.CACTUS), "toolCuttingKnife" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.MELON, 9), new Object[]
		{ new ItemStack(Blocks.MELON_BLOCK, 1), "toolCuttingKnife" }));
		GameRegistry.addRecipe(new ItemStack(ModItems.cactusKnife), new Object[]
		{ " #", "# ", '#', new ItemStack(ModItems.alchemyComponent, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ironKnife), new Object[]
		{ "#  ", "#X ", " #X", 'X', new ItemStack(Items.STICK), '#',
				new ItemStack(Items.IRON_INGOT) });
		GameRegistry.addRecipe(new ItemStack(ModItems.diamondKnife), new Object[]
		{ "#  ", "#X ", " #X", 'X', new ItemStack(Items.STICK), '#',
				new ItemStack(Items.DIAMOND) });
		GameRegistry.addRecipe(new ItemStack(ModItems.stoneGrinder), new Object[]
		{ "#  ", " # ", "  X", 'X', new ItemStack(Items.STICK), '#',
				new ItemStack(Blocks.COBBLESTONE) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ironGrinder), new Object[]
		{ "#  ", " # ", "  X", 'X', new ItemStack(Items.STICK), '#',
				new ItemStack(Items.IRON_INGOT) });
		GameRegistry.addRecipe(new ItemStack(ModItems.diamondGrinder), new Object[]
		{ "#  ", " # ", "  X", 'X', new ItemStack(Items.STICK), '#',
				new ItemStack(Items.DIAMOND) });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.cactusFruitNeedle), new Object[]
		{ "X", "Y", 'X', new ItemStack(ModItems.cactusFruit), 'Y',
				new ItemStack(ModItems.alchemyComponent, 1, 0) });
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModItems.sandstoneInfusionStone), new Object[]
				{ "X", "Y", 'X', new ItemStack(ModItems.alchemyComponent, 1, 0), 'Y', new ItemStack(Blocks.SANDSTONE, 1, OreDictionary.WILDCARD_VALUE) }));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModItems.redSandstoneInfusionStone), new Object[]
				{ "X", "Y", 'X', new ItemStack(ModItems.alchemyComponent, 1, 0), 'Y', new ItemStack(Blocks.RED_SANDSTONE, 1, OreDictionary.WILDCARD_VALUE) }));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModItems.alchemicalInfusionStone), new Object[]
				{ "X", "Y", 'X', new ItemStack(ModItems.alchemyComponent, 1, 6), 'Y', new ItemStack(ModItems.alchemyComponent, 1, 4) }));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModItems.alchemyComponent, 1, 6), new Object[]
				{ "X", "X", 'X', new ItemStack(ModItems.alchemyComponent, 1, 5) }));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModItems.baseComponent, 1, 0), new Object[]
				{ "XXX", "XYX", "XXX", 'X', "plankWood", 'Y', Items.GUNPOWDER }));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModItems.baseComponent, 1, 1), new Object[]
				{ "XXX", "XYX", "XXX", 'X', "ingotIron", 'Y', Items.BLAZE_POWDER }));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModBlocks.combustionHeater, 1, 0), new Object[]
				{ "XYX", "X X", "XXX", 'X', "logWood", 'Y', new ItemStack(ModItems.baseComponent, 1, 0) }));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModBlocks.combustionHeater, 1, 1), new Object[]
				{ "XYX", "X X", "XXX", 'X', "ingotIron", 'Y', new ItemStack(ModItems.baseComponent, 1, 1) }));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModBlocks.compressedCoalBlock), new Object[]
				{ "XXX", "XYX", "XXX", 'X', new ItemStack(Blocks.COAL_BLOCK), 'Y', new ItemStack(Items.COAL) }));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModBlocks.blazePowderBlock), new Object[]
				{ "XXX", "XXX", "XXX", 'X', new ItemStack(Items.BLAZE_POWDER) }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.heavySnow), new Object[]
		{ "XX", "XX", 'X', new ItemStack(ModItems.heavySnowball) }));
		GameRegistry
				.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.miniFreezer), new Object[]
		{ "X", "X", 'X', new ItemStack(Blocks.SNOW) }));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModBlocks.alchemicalCondenser), new Object[]
				{ "XXX", "X X", "XYX", 'X', "cobblestone", 'Y', new ItemStack(ModItems.baseComponent, 1, 0) }));
		GameRegistry
				.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.concentrator), new Object[]
		{ "XXX", "XYX", "XZX", 'X', "ingotIron", 'Y', new ItemStack(ModBlocks.alchemicalCondenser),
				'Z', new ItemStack(ModItems.alchemyComponent, 1, 4) }));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModBlocks.compressedCoalBlock2), new Object[]
				{ "XXX", "XXX", "XXX", 'X', new ItemStack(ModBlocks.compressedCoalBlock) }));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModBlocks.compressedStone), new Object[]
				{ "XXX", "XYX", "XXX", 'X', new ItemStack(Blocks.STONE), 'Y', new ItemStack(Blocks.COBBLESTONE) }));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModBlocks.compressedNetherrack), new Object[]
				{ "XXX", "XYX", "XXX", 'X', new ItemStack(Blocks.NETHERRACK), 'Y', new ItemStack(Items.NETHERBRICK) }));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModBlocks.coalInfusedBlock), new Object[]
				{ "XXX", "XXX", "XXX", 'X', new ItemStack(ModItems.alchemyComponent, 1, 2) }));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModItems.alchemyComponent, 9, 2), new Object[]
				{ "X", 'X', new ItemStack(ModBlocks.coalInfusedBlock) }));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModBlocks.darkMatterBlock), new Object[]
				{ "XXX", "XXX", "XXX", 'X', new ItemStack(ModItems.baseComponent, 1, 5) }));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModItems.baseComponent, 9, 5), new Object[]
				{ "X", 'X', new ItemStack(ModBlocks.darkMatterBlock) }));
		GameRegistry
				.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.heavySnow2), new Object[]
		{ new ItemStack(ModBlocks.heavySnow), new ItemStack(ModBlocks.heavySnow),
				new ItemStack(Items.ROTTEN_FLESH) }));
		GameRegistry
				.addRecipe(new ShapelessOreRecipe(new ItemStack(Blocks.SAPLING, 1, 1), new Object[]
		{ new ItemStack(Blocks.DIRT, 1, OreDictionary.WILDCARD_VALUE),
				new ItemStack(Items.PUMPKIN_SEEDS), new ItemStack(Items.PUMPKIN_SEEDS),
				new ItemStack(Items.DYE, 1, 15) }));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModItems.baseComponent, 2, 2), new Object[]
				{ " X ", "XXX", " X ", 'X', "treeSapling" }));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModItems.baseComponent, 2, 2), new Object[]
				{ " X ", "XXX", " X ", 'X', "cropWheat" }));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModItems.baseComponent, 1, 2), new Object[]
				{ " X ", "XXX", " X ", 'X', "treeLeaves" }));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModItems.baseComponent, 2, 2), new Object[]
				{ " X ", "XXX", " X ", 'X', ModItems.cactusFruit }));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModItems.baseComponent, 2, 2), new Object[]
				{ " X ", "XXX", " X ", 'X', "cropPotato" }));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModItems.baseComponent, 2, 2), new Object[]
				{ " X ", "XXX", " X ", 'X', "cropCarrot" }));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModItems.baseComponent, 1, 2), new Object[]
				{ " X ", "XXX", " X ", 'X', "sugarcane" }));
		GameRegistry
				.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.ironFreezer), new Object[]
		{ "XXX", "XZX", "XXX", 'X', "ingotFrozenIron", 'Z',
				new ItemStack(ModBlocks.miniFreezer) }));
		GameRegistry
				.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.dirtFurnace), new Object[]
		{ "X", "Y", 'X', "dirt", 'Y', new ItemStack(ModItems.baseComponent, 1, 0) }));

		GameRegistry
				.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.waterExtractor), new Object[]
		{ "XXX", " XX", 'X', "plankWood" }));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.crucible), new Object[]
		{ "X X", "X X", "XXX", 'X', new ItemStack(Items.BRICK) });
		GameRegistry
				.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.fluidDropper), new Object[]
		{ "XXX", "X X", "X X", 'X', "cobblestone" }));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModBlocks.purificationVessel), new Object[]
				{ "XXX", "XYX", "XXX", 'X', "blockGlass", 'Y', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE }));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModItems.fleshySnowNugget, 3), new Object[]
				{ "XX", "XY", 'X', new ItemStack(Items.SNOWBALL), 'Y', new ItemStack(Items.ROTTEN_FLESH) }));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModItems.baseComponent, 1, 4), new Object[]
				{ "XXX", "XYX", "XXX", 'X', "ingotFrozenIron", 'Y', new ItemStack(Items.GLOWSTONE_DUST) }));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModBlocks.advancedCoolingCondenser), new Object[]
				{ "XXX", "X X", "XYX", 'X', "ingotIron", 'Y', new ItemStack(ModItems.baseComponent, 1, 4) }));

		GameRegistry
				.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.crystallizer), new Object[]
		{ "XYX", "X X", "XZX", 'X', "ingotIron", 'Y', new ItemStack(ModItems.baseComponent, 1, 4),
				'Z', new ItemStack(Blocks.HOPPER) }));

		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModBlocks.darkMatterWarper), new Object[]
				{ "XXX", "XYX", "XXX", 'X', new ItemStack(Blocks.OBSIDIAN), 'Y', new ItemStack(ModItems.baseComponent, 1, 5) }));
		GameRegistry
				.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.endPortalCore), new Object[]
		{ "XZX", "AYA", "XZX", 'X', new ItemStack(ModItems.baseComponent, 1, 5), 'Y',
				new ItemStack(Items.NETHER_STAR), 'Z', new ItemStack(ModItems.techComponent, 1, 2),
				'A', new ItemStack(ModItems.alchemyComponent, 1, 5) }));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModBlocks.sandyNetherrack, 2), new Object[]
				{ "XY", "ZX", 'X', new ItemStack(Blocks.SAND), 'Y', new ItemStack(Items.NETHER_WART), 'Z', new ItemStack(Blocks.NETHERRACK) }));
		GameRegistry
				.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.lifeInfuser), new Object[]
		{ "XXX", "X X", "XYX", 'X', "logWood", 'Y', new ItemStack(ModItems.healthGem) }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.heavyExpSnowball, 3),
				new ItemStack(ModItems.heavySnowball), new ItemStack(ModItems.heavySnowball),
				new ItemStack(ModItems.heavySnowball),
				new ItemStack(Items.GUNPOWDER)));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(ModBlocks.crucibleInserter), new Object[]
				{ "XXX", "XYX", "X X", 'X', "ingotIron", 'Y', new ItemStack(Blocks.DROPPER) }));

		if (OreDictionary.doesOreNameExist("ingotSteel")
				&& OreDictionary.doesOreNameExist("dustCoal"))
		{
			GameRegistry.addRecipe(
					new ShapedOreRecipe(new ItemStack(ModItems.baseComponent, 1, 3), new Object[]
					{ "XZX", "XYX", "XZX", 'X', "ingotSteel", 'Y', Blocks.REDSTONE_BLOCK, 'Z', "dustCoal" }));
			GameRegistry.addRecipe(new ShapedOreRecipe(
					new ItemStack(ModBlocks.combustionHeater, 1, 2), new Object[]
					{ "XZX", "XYX", "XYX", 'X', "ingotSteel", 'Y', new ItemStack(ModItems.baseComponent, 1, 3), 'Z', new ItemStack(ModItems.baseComponent, 1, 1) }));
			GameRegistry.addRecipe(
					new ShapedOreRecipe(new ItemStack(ModBlocks.poweredHeater), new Object[]
					{ "XZX", "XYX", "XXX", 'X', "ingotSteel", 'Y', new ItemStack(ModItems.baseComponent, 1, 3), 'Z', new ItemStack(ModItems.baseComponent, 1, 1) }));
		} else
		{
			SkyResources.logger.warn(
					"Recipes for steel powered machines have not been added as they require steel and coal dust.");
		}

		GameRegistry.addSmelting(ModBlocks.dryCactus, new ItemStack(Items.DYE, 1, 7), 0.2F);

		GameRegistry.addSmelting(new ItemStack(ModItems.baseComponent, 1, 2),
				new ItemStack(Items.COAL, 1, 1), 0.1F);

		InfusionRecipes.addRecipe(new ItemStack(Blocks.SAPLING, 1, 4),
				new ItemStack(ModItems.alchemyComponent, 10, 1), ModBlocks.cactusFruitNeedle, 0,
				10);
		InfusionRecipes.addRecipe(new ItemStack(Blocks.SAPLING, 1, 0),
				new ItemStack(Items.APPLE, 10, 0), "treeSapling", 0, 20);
		InfusionRecipes.addRecipe(new ItemStack(Blocks.DIRT, 1, 1),
				new ItemStack(ModItems.cactusFruit, 4), Blocks.SAND, 1, 15);
		InfusionRecipes.addRecipe(new ItemStack(Blocks.CACTUS, 3),
				new ItemStack(ModItems.alchemyComponent, 6, 1), Blocks.CACTUS,
				OreDictionary.WILDCARD_VALUE, 8);
		InfusionRecipes.addRecipe(new ItemStack(Blocks.DEADBUSH),
				new ItemStack(Items.ROTTEN_FLESH, 4), "treeSapling", 0, 10);
		InfusionRecipes.addRecipe(new ItemStack(Blocks.GRASS, 1),
				new ItemStack(Items.WHEAT_SEEDS, 4), Blocks.DIRT, 0, 14);
		InfusionRecipes.addRecipe(new ItemStack(Items.APPLE), new ItemStack(Items.SUGAR, 10),
				Blocks.HAY_BLOCK, 0, 10);
		InfusionRecipes.addRecipe(new ItemStack(Blocks.SAPLING, 1, 5),
				new ItemStack(Items.GUNPOWDER, 10), Blocks.SAPLING, 0, 20);
		InfusionRecipes.addRecipe(new ItemStack(Blocks.SAPLING, 1, 3),
				new ItemStack(Items.DYE, 10, 3), "treeSapling", 0, 20);
		InfusionRecipes.addRecipe(new ItemStack(Blocks.SAPLING, 1, 2),
				new ItemStack(Items.DYE, 10, 15), "treeSapling", 0, 20);
		InfusionRecipes.addRecipe(new ItemStack(Items.REEDS),
				new ItemStack(Items.SPECKLED_MELON, 3), Blocks.PUMPKIN,
				OreDictionary.WILDCARD_VALUE, 20);
		InfusionRecipes.addRecipe(new ItemStack(Items.NETHER_WART),
				new ItemStack(Items.SPIDER_EYE, 8), Blocks.RED_MUSHROOM,
				OreDictionary.WILDCARD_VALUE, 20);
		InfusionRecipes.addRecipe(new ItemStack(Blocks.RED_MUSHROOM),
				new ItemStack(Items.DYE, 8, 1), Blocks.TALLGRASS,
				OreDictionary.WILDCARD_VALUE, 15);
		InfusionRecipes.addRecipe(new ItemStack(Blocks.BROWN_MUSHROOM),
				new ItemStack(Items.DYE, 8, 3), Blocks.TALLGRASS,
				OreDictionary.WILDCARD_VALUE, 15);
		InfusionRecipes.addRecipe(new ItemStack(ModItems.healthGem),
				new ItemStack(ModItems.alchemyComponent, 1, 4), Blocks.CHORUS_FLOWER,
				OreDictionary.WILDCARD_VALUE, 19);

		CombustionRecipes.addRecipe(new ItemStack(Items.COAL, 1), 50,
				new ItemStack(Items.COAL, 1, 1));
		CombustionRecipes.addRecipe(new ItemStack(Items.BLAZE_POWDER, 1), 75,
				new ItemStack(Items.GUNPOWDER, 2));
		CombustionRecipes.addRecipe(new ItemStack(Items.DIAMOND, 1, 0), 1000,
				new ItemStack(ModBlocks.compressedCoalBlock, 2));
		CombustionRecipes.addRecipe(new ItemStack(Blocks.SANDSTONE, 8, 1), 200,
				new ItemStack(Blocks.SAND, 12), new ItemStack(Items.DYE, 1, 1));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 0), 90,
				new ItemStack(Blocks.GLASS, 6), new ItemStack(Items.ROTTEN_FLESH, 4),
				new ItemStack(Items.BLAZE_POWDER, 2));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 1), 240,
				new ItemStack(ModItems.metalCrystal, 6, 0), new ItemStack(Items.IRON_INGOT, 2),
				new ItemStack(Items.REDSTONE, 6));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 2), 125,
				new ItemStack(ModItems.metalCrystal, 2, 0), new ItemStack(Items.IRON_INGOT, 1),
				new ItemStack(Items.GUNPOWDER, 3));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 3), 230,
				new ItemStack(ModItems.metalCrystal, 2, 2), new ItemStack(Items.IRON_INGOT, 2),
				new ItemStack(Items.BLAZE_POWDER, 4));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 4), 400,
				new ItemStack(ModItems.metalCrystal, 3, 1), new ItemStack(Items.GOLD_INGOT, 2),
				new ItemStack(Items.SUGAR, 6));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 5), 180,
				new ItemStack(ModItems.metalCrystal, 4, 0), new ItemStack(Items.IRON_INGOT, 3),
				new ItemStack(Items.SUGAR, 2));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 6), 420,
				new ItemStack(ModItems.metalCrystal, 6, 0), new ItemStack(Items.IRON_INGOT, 3),
				new ItemStack(Items.GLOWSTONE_DUST, 3));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 7), 600,
				new ItemStack(ModItems.metalCrystal, 5, 1), new ItemStack(Items.GOLD_INGOT, 6),
				new ItemStack(Items.DYE, 8, 4));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 8), 160,
				new ItemStack(ModItems.metalCrystal, 3, 0), new ItemStack(Items.IRON_INGOT, 3),
				new ItemStack(Items.DYE, 6, 15));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 9), 300,
				new ItemStack(ModItems.metalCrystal, 5, 0), new ItemStack(Items.IRON_INGOT, 5),
				new ItemStack(Items.COAL, 4));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 10), 700,
				new ItemStack(ModItems.metalCrystal, 4, 1), new ItemStack(Items.GOLD_INGOT, 2),
				new ItemStack(ModItems.techComponent, 3, 1));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 11), 600,
				new ItemStack(ModItems.metalCrystal, 6, 2), new ItemStack(Items.SUGAR, 8),
				new ItemStack(Items.FLINT, 6), new ItemStack(Items.GOLD_INGOT, 4));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 12), 1200,
				new ItemStack(ModItems.metalCrystal, 5, 11), new ItemStack(Items.DYE, 9, 4),
				new ItemStack(Items.QUARTZ, 3), new ItemStack(Items.GOLD_INGOT, 6));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 13), 1200,
				new ItemStack(ModItems.metalCrystal, 5, 11), new ItemStack(Items.MAGMA_CREAM, 5),
				new ItemStack(Items.QUARTZ, 2), new ItemStack(Items.GOLD_INGOT, 6),
				new ItemStack(Blocks.NETHER_BRICK));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 14), 1400,
				new ItemStack(ModItems.metalCrystal, 7, 11), new ItemStack(Items.MAGMA_CREAM, 5),
				new ItemStack(Items.DYE, 4, 0), new ItemStack(ModItems.baseComponent, 2, 5),
				new ItemStack(Items.GOLD_INGOT, 4));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 15), 1100,
				new ItemStack(ModItems.metalCrystal, 3, 11), new ItemStack(Blocks.ICE, 5),
				new ItemStack(ModItems.techComponent, 4, 2), new ItemStack(Items.SNOWBALL, 6));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 16), 800,
				new ItemStack(ModItems.metalCrystal, 6, 1), new ItemStack(Items.GOLD_INGOT, 5),
				new ItemStack(Items.REDSTONE, 7), new ItemStack(Items.IRON_INGOT, 3));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 17), 1200,
				new ItemStack(ModItems.metalCrystal, 9, 0), new ItemStack(Items.GOLD_INGOT, 2),
				new ItemStack(Items.GOLD_NUGGET, 7), new ItemStack(Items.DIAMOND, 1));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.metalCrystal, 1, 18), 2000,
				new ItemStack(ModItems.metalCrystal, 8, 1), new ItemStack(Items.GOLD_INGOT, 6),
				new ItemStack(Items.ENDER_EYE, 4), new ItemStack(Items.DIAMOND));
		CombustionRecipes.addRecipe(new ItemStack(ModBlocks.dryCactus), 1300,
				new ItemStack(boneBlock), new ItemStack(Items.DYE, 8, 7),
				new ItemStack(Blocks.LEAVES, 8, 1));
		CombustionRecipes.addRecipe(new ItemStack(Blocks.CACTUS), 900,
				new ItemStack(Items.SPIDER_EYE, 1), new ItemStack(Items.SNOWBALL, 16),
				new ItemStack(ModBlocks.dryCactus, 4));

		CombustionRecipes.addRecipe(new ItemStack(Items.REDSTONE, 4), 400,
				new ItemStack(Items.GUNPOWDER, 2), new ItemStack(Items.BLAZE_POWDER, 2));

		CombustionRecipes.addRecipe(new ItemStack(ModItems.alchemyComponent, 2, 2), 400,
				new ItemStack(Items.COAL, 2), new ItemStack(ModItems.alchemyComponent, 1, 3));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.alchemyComponent, 1, 4), 1200,
				new ItemStack(Items.DIAMOND, 1), new ItemStack(ModItems.alchemyComponent, 8, 3));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.alchemyComponent, 1, 5), 1000,
				new ItemStack(Items.GOLD_INGOT, 1), new ItemStack(ModItems.alchemyComponent, 4, 3));

		CombustionRecipes.addRecipe(new ItemStack(ModItems.alchemyComponent, 4, 3), 700,
				new ItemStack(Items.REDSTONE, 1), new ItemStack(Items.DYE, 1, 4),
				new ItemStack(Items.GLOWSTONE_DUST, 1), new ItemStack(Items.BLAZE_POWDER, 1));

		CombustionRecipes.addRecipe(new ItemStack(Items.WHEAT_SEEDS, 1), 50,
				new ItemStack(Blocks.DEADBUSH, 1), new ItemStack(Items.FLINT, 2));
		CombustionRecipes.addRecipe(new ItemStack(Blocks.DIRT), 100,
				new ItemStack(ModItems.baseComponent, 8, 2));
		CombustionRecipes.addRecipe(new ItemStack(Items.SLIME_BALL), 200,
				new ItemStack(Items.SNOWBALL, 4), new ItemStack(ModItems.baseComponent, 4, 2));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.techComponent, 1, 1), 1400,
				new ItemStack(Items.POISONOUS_POTATO, 3),
				new ItemStack(Items.FERMENTED_SPIDER_EYE, 2), new ItemStack(Items.SLIME_BALL, 1),
				new ItemStack(Items.GUNPOWDER, 2));
		CombustionRecipes.addRecipe(new ItemStack(Items.SNOWBALL), 1234,
				new ItemStack(Items.POTIONITEM, 1, 0), new ItemStack(Items.POTIONITEM, 1, 0),
				new ItemStack(Items.POTIONITEM, 1, 0), new ItemStack(Items.QUARTZ, 8));
		CombustionRecipes.addRecipe(new ItemStack(Blocks.NETHERRACK, 4), 700,
				new ItemStack(Blocks.COBBLESTONE, 8), new ItemStack(Items.REDSTONE, 1),
				new ItemStack(Items.BLAZE_POWDER, 2));
		CombustionRecipes.addRecipe(new ItemStack(ModItems.baseComponent, 1, 5), 1500,
				new ItemStack(Blocks.OBSIDIAN, 3), new ItemStack(Items.FIRE_CHARGE, 3),
				new ItemStack(Items.NETHERBRICK, 6), new ItemStack(Items.IRON_INGOT, 2));
		CombustionRecipes.addRecipe(new ItemStack(Items.GLOWSTONE_DUST, 5), 900,
				new ItemStack(Items.REDSTONE, 4), new ItemStack(Items.BLAZE_POWDER, 2),
				new ItemStack(Items.FIRE_CHARGE));
		CombustionRecipes.addRecipe(new ItemStack(Items.QUARTZ, 4), 750,
				new ItemStack(ModItems.metalCrystal, 1, 11), new ItemStack(Items.FLINT, 6),
				new ItemStack(Items.FIRE_CHARGE));
		CombustionRecipes.addRecipe(new ItemStack(Items.DYE, 8, 4), 988,
				new ItemStack(ModItems.metalCrystal, 1, 17), new ItemStack(Items.FLINT, 6),
				new ItemStack(Blocks.STONE, 3));

		CombustionRecipes.addRecipe(new ItemStack(Blocks.END_STONE, 1), 1369,
				new ItemStack(Blocks.STONE, 6, 3), new ItemStack(Items.SUGAR, 2),
				new ItemStack(Items.ENDER_PEARL, 4), new ItemStack(Items.QUARTZ, 2),
				new ItemStack(boneBlock, 4));

		RockGrinderRecipes.addRecipe(new ItemStack(Blocks.SAND), false,
				Blocks.COBBLESTONE.getDefaultState());
		RockGrinderRecipes.addRecipe(new ItemStack(Blocks.GRAVEL), false,
				Blocks.SANDSTONE.getDefaultState());
		RockGrinderRecipes.addRecipe(new ItemStack(Items.FLINT), false,
				Blocks.GRAVEL.getDefaultState());
		RockGrinderRecipes.addRecipe(new ItemStack(ModItems.techComponent, 1, 0), false,
				Blocks.STONE.getDefaultState());

		for (int i = 0; i < ModItems.gemList.size(); i++)
		{
			if (ConfigOptions.allowAllGemTypes
					|| OreDictionary
							.getOres("gem"
									+ RandomHelper.capatilizeString(ModItems.gemList.get(i).name))
							.size() > 0)
				RockGrinderRecipes.addRecipe(new ItemStack(ModItems.dirtyGem, 1, i), false,
						Blocks.STONE.getDefaultState(), ModItems.gemList.get(i).rarity);
		}

		for (int i = 0; i < ModFluids.crystalFluidInfos().length; i++)
		{
			CrucibleRecipes.addRecipe(new FluidStack(ModFluids.dirtyCrystalFluids.get(i), 1000),
					new ItemStack(ModItems.metalCrystal, 1,
							ModFluids.crystalFluidInfos()[i].crystalIndex));
		}
		for (int i = 0; i < ModFluids.moltenCrystalFluidInfos().length; i++)
		{
			CrucibleRecipes.addRecipe(new FluidStack(ModFluids.moltenCrystalFluids.get(i), 1000),
					new ItemStack(ModItems.metalCrystal, 1,
							ModFluids.moltenCrystalFluidInfos()[i].crystalIndex));
		}
		CrucibleRecipes.addRecipe(new FluidStack(FluidRegistry.LAVA, 200),
				new ItemStack(ModBlocks.compressedNetherrack));

		CrucibleRecipes.addRecipe(new FluidStack(FluidRegistry.LAVA, 250),
				new ItemStack(ModBlocks.blazePowderBlock));

		WaterExtractorRecipes.addExtractRecipe(50, true, Blocks.CACTUS.getDefaultState(),
				ModBlocks.dryCactus.getDefaultState());

		WaterExtractorRecipes.addExtractRecipe(50, true, Blocks.SNOW.getDefaultState(), null);

		WaterExtractorRecipes.addExtractRecipe(20, true, Blocks.LEAVES.getDefaultState(), null);

		WaterExtractorRecipes.addExtractRecipe(20, true, Blocks.LEAVES2.getDefaultState(), null);

		WaterExtractorRecipes.addInsertRecipe(Blocks.CLAY.getDefaultState(), false,
				Blocks.DIRT.getDefaultState(), 1000);

		FreezerRecipes.addRecipe(new ItemStack(ModItems.heavySnowball), 100,
				new ItemStack(Items.SNOWBALL, 4));
		FreezerRecipes.addRecipe(new ItemStack(Blocks.DIRT, 1, 1), 800,
				new ItemStack(ModBlocks.heavySnow2, 2));
		FreezerRecipes.addRecipe(new ItemStack(Items.SLIME_BALL), 1200,
				new ItemStack(Items.MAGMA_CREAM));
		FreezerRecipes.addRecipe(new ItemStack(Blocks.ICE), 2000,
				new ItemStack(Items.POTIONITEM, 1, 0));
		FreezerRecipes.addRecipe(new ItemStack(ModItems.techComponent, 1, 2), 3000,
				new ItemStack(Items.IRON_INGOT));
		FreezerRecipes.addRecipe(new ItemStack(Blocks.SOUL_SAND), 1500,
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

		for (int i = 0; i < ModFluids.crystalFluidInfos().length; i++)
		{
			String oreName = "ore"
					+ RandomHelper.capatilizeString(ModFluids.crystalFluidInfos()[i].name);

			if (OreDictionary.getOres(oreName).size() > 0)
			{
				ConcentratorRecipes.addRecipe(
						Block.getBlockFromItem(OreDictionary.getOres(oreName).get(0).getItem())
								.getStateFromMeta(
										OreDictionary.getOres(oreName).get(0).getMetadata()),
						ModFluids.crystalFluidInfos()[i].rarity * 100,
						new ItemStack(ModItems.metalCrystal,
								ConfigOptions.crystalConcentratorAmount,
								ModFluids.crystalFluidInfos()[i].crystalIndex),
						ModBlocks.compressedStone.getDefaultState());
			}
		}

		for (int i = 0; i < ModFluids.moltenCrystalFluidInfos().length; i++)
		{
			String oreName = "ore"
					+ RandomHelper.capatilizeString(ModFluids.moltenCrystalFluidInfos()[i].name);

			if (OreDictionary.getOres(oreName).size() > 0)
			{
				ConcentratorRecipes.addRecipe(
						Block.getBlockFromItem(OreDictionary.getOres(oreName).get(0).getItem())
								.getStateFromMeta(
										OreDictionary.getOres(oreName).get(0).getMetadata()),
						ModFluids.moltenCrystalFluidInfos()[i].rarity * 100,
						new ItemStack(ModItems.metalCrystal,
								ConfigOptions.crystalConcentratorAmount,
								ModFluids.moltenCrystalFluidInfos()[i].crystalIndex),
						ModBlocks.compressedNetherrack.getDefaultState());
			}
		}

		ConcentratorRecipes.addRecipe(Blocks.COAL_ORE.getDefaultState(), 100,
				new ItemStack(ModItems.alchemyComponent, 1, 2),
				ModBlocks.compressedStone.getDefaultState());

		ConcentratorRecipes.addRecipe(Blocks.DIAMOND_ORE.getDefaultState(), 800,
				new ItemStack(ModItems.alchemyComponent, 1, 4),
				ModBlocks.compressedStone.getDefaultState());

		ConcentratorRecipes.addRecipe(Blocks.REDSTONE_ORE.getDefaultState(), 700,
				new ItemStack(ModItems.alchemyComponent, 12, 3),
				ModBlocks.compressedStone.getDefaultState());

		if (OreDictionary.getOres("oreUranium").size() > 0)
		{
			ConcentratorRecipes.addRecipe(
					Block.getBlockFromItem(OreDictionary.getOres("oreUranium").get(0).getItem())
							.getStateFromMeta(
									OreDictionary.getOres("oreUranium").get(0).getMetadata()),
					1500, new ItemStack(ModItems.techComponent, 4, 1),
					ModBlocks.compressedStone.getDefaultState());
		}
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

	}
}
