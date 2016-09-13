package com.bartz24.skyresources.registry;

import com.bartz24.skyresources.base.guide.SkyResourcesGuide;
import com.bartz24.skyresources.config.ConfigOptions;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

public class ModGuidePages
{
	public static void init()
	{
		SkyResourcesGuide.addPage("basics", "guide.skyresources.stage1",
				new ItemStack(Blocks.BEDROCK));

		SkyResourcesGuide.addPage("sandPlatform", "guide.skyresources.stage1",
				new ItemStack(Blocks.SAND, 1, 1));

		SkyResourcesGuide.addPage("lifeInfusion", "guide.skyresources.stage1",
				new ItemStack(ModItems.alchemicalInfusionStone));

		SkyResourcesGuide.addPage("snowPlatform", "guide.skyresources.stage1",
				new ItemStack(Blocks.SNOW));

		SkyResourcesGuide.addPage("miniFreezer", "guide.skyresources.stage1",
				new ItemStack(ModBlocks.miniFreezer));

		SkyResourcesGuide.addPage("grassPlatform", "guide.skyresources.stage1",
				new ItemStack(Blocks.GRASS));

		SkyResourcesGuide.addPage("combustionHeater", "guide.skyresources.stage2",
				new ItemStack(ModBlocks.combustionHeater));

		SkyResourcesGuide.addPage("waterExtractor", "guide.skyresources.stage2",
				new ItemStack(ModItems.waterExtractor));

		SkyResourcesGuide.addPage("plantMatter", "guide.skyresources.stage2",
				new ItemStack(ModItems.baseComponent, 1, 2));

		SkyResourcesGuide.addPage("dirtFurnace", "guide.skyresources.stage2",
				new ItemStack(ModBlocks.dirtFurnace));

		SkyResourcesGuide.addPage("lavaBlaze", "guide.skyresources.stage2",
				new ItemStack(ModBlocks.blazePowderBlock));

		SkyResourcesGuide.addPage("heatSources", "guide.skyresources.stage2",
				new ItemStack(Blocks.TORCH));

		SkyResourcesGuide.addPage("listHeatSources", "guide.skyresources.stage2",
				new ItemStack(Blocks.TORCH));

		SkyResourcesGuide.addPage("rockGrinder", "guide.skyresources.stage2",
				new ItemStack(ModItems.stoneGrinder));

		SkyResourcesGuide.addPage("metalCreation", "guide.skyresources.stage2",
				new ItemStack(ModItems.metalCrystal));

		SkyResourcesGuide.addPage("crucible", "guide.skyresources.stage2",
				new ItemStack(ModBlocks.crucible));

		SkyResourcesGuide.addPage("fluidDropper", "guide.skyresources.stage2",
				new ItemStack(ModBlocks.fluidDropper));

		SkyResourcesGuide.addPage("alchemicalCondenser", "guide.skyresources.stage2",
				new ItemStack(ModBlocks.alchemicalCondenser));

		SkyResourcesGuide.addPage("purificationVessel", "guide.skyresources.stage2",
				new ItemStack(ModBlocks.purificationVessel));

		SkyResourcesGuide.addPage("cleanCrystal", "guide.skyresources.stage2",
				new ItemStack(ModBlocks.crystalFluidBlocks.get(0)));

		SkyResourcesGuide.addPage("earlyMetalSetup", "guide.skyresources.stage2",
				new ItemStack(ModItems.metalCrystal));

		SkyResourcesGuide.addPage("crucibleAuto", "guide.skyresources.stage2",
				new ItemStack(ModBlocks.crucibleInserter));

		SkyResourcesGuide.addPage("gemProduction", "guide.skyresources.stage2",
				new ItemStack(Items.EMERALD));

		SkyResourcesGuide.addPage("alchemicalItems", "guide.skyresources.stage3",
				new ItemStack(ModItems.alchemyComponent, 1, 4));

		SkyResourcesGuide.addPage("concentrator", "guide.skyresources.stage3",
				new ItemStack(ModBlocks.concentrator));

		SkyResourcesGuide.addPage("ironFreezer", "guide.skyresources.stage3",
				new ItemStack(ModBlocks.ironFreezer));

		SkyResourcesGuide.addPage("poweredCombustion", "guide.skyresources.stage3",
				new ItemStack(ModBlocks.combustionHeater, 1, 2));

		SkyResourcesGuide.addPage("poweredHeater", "guide.skyresources.stage3",
				new ItemStack(ModBlocks.poweredHeater));

		SkyResourcesGuide.addPage("nether", "guide.skyresources.stage3",
				new ItemStack(Blocks.NETHERRACK));

		SkyResourcesGuide.addPage("betterLava", "guide.skyresources.stage3",
				new ItemStack(ModBlocks.compressedNetherrack));

		SkyResourcesGuide.addPage("netherMetalCreation", "guide.skyresources.stage3",
				new ItemStack(ModBlocks.advancedCoolingCondenser));

		SkyResourcesGuide.addPage("dmWarper", "guide.skyresources.stage3",
				new ItemStack(ModBlocks.darkMatterWarper));

		SkyResourcesGuide.addPage("end", "guide.skyresources.stage3",
				new ItemStack(Items.ENDER_EYE));

		SkyResourcesGuide.addPage("healthGem", "guide.skyresources.stage3",
				new ItemStack(ModItems.healthGem));

		SkyResourcesGuide.addPage("lifeInfuser", "guide.skyresources.stage3",
				new ItemStack(ModBlocks.lifeInfuser));

		SkyResourcesGuide.addPage("commands", "guide.skyresources.misc",
				new ItemStack(Blocks.COMMAND_BLOCK),
				I18n.translateToLocal("guide.skyresources.misc.commands.text").replace("\\n", "\n")
						.replace("{distance}", Integer.toString(ConfigOptions.islandDistance / 2)));

		SkyResourcesGuide.addPage("minetweaker", "guide.skyresources.misc",
				new ItemStack(Blocks.CRAFTING_TABLE));
	}
}
