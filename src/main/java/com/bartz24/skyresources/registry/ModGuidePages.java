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
				I18n.translateToLocal("guide.skyresources.stage1.basics.title").replace("\\n", "\n"),
				new ItemStack(Blocks.BEDROCK),
				I18n.translateToLocal("guide.skyresources.stage1.basics.text").replace("\\n", "\n"));

		SkyResourcesGuide.addPage("sandPlatform", "guide.skyresources.stage1",
				I18n.translateToLocal("guide.skyresources.stage1.sandPlatform.title").replace("\\n", "\n"),
				new ItemStack(Blocks.SAND, 1, 1),
				I18n.translateToLocal("guide.skyresources.stage1.sandPlatform.text").replace("\\n", "\n"));

		SkyResourcesGuide.addPage("lifeInfusion", "guide.skyresources.stage1",
				I18n.translateToLocal("guide.skyresources.stage1.lifeInfusion.title").replace("\\n", "\n"),
				new ItemStack(ModItems.alchemicalInfusionStone),
				I18n.translateToLocal("guide.skyresources.stage1.lifeInfusion.text").replace("\\n", "\n"));

		SkyResourcesGuide.addPage("snowPlatform", "guide.skyresources.stage1",
				I18n.translateToLocal("guide.skyresources.stage1.snowPlatform.title").replace("\\n", "\n"),
				new ItemStack(Blocks.SNOW),
				I18n.translateToLocal("guide.skyresources.stage1.snowPlatform.text").replace("\\n", "\n"));

		SkyResourcesGuide.addPage("miniFreezer", "guide.skyresources.stage1",
				I18n.translateToLocal("guide.skyresources.stage1.miniFreezer.title").replace("\\n", "\n"),
				new ItemStack(ModBlocks.miniFreezer),
				I18n.translateToLocal("guide.skyresources.stage1.miniFreezer.text").replace("\\n", "\n"));

		SkyResourcesGuide.addPage("grassPlatform", "guide.skyresources.stage1",
				I18n.translateToLocal("guide.skyresources.stage1.grassPlatform.title").replace("\\n", "\n"),
				new ItemStack(Blocks.GRASS),
				I18n.translateToLocal("guide.skyresources.stage1.grassPlatform.text").replace("\\n", "\n"));

		SkyResourcesGuide.addPage("combustionHeater", "guide.skyresources.stage2",
				I18n.translateToLocal("guide.skyresources.stage2.combustionHeater.title").replace("\\n", "\n"),
				new ItemStack(ModBlocks.combustionHeater),
				I18n.translateToLocal("guide.skyresources.stage2.combustionHeater.text").replace("\\n", "\n"));

		SkyResourcesGuide.addPage("waterExtractor", "guide.skyresources.stage2",
				I18n.translateToLocal("guide.skyresources.stage2.waterExtractor.title").replace("\\n", "\n"),
				new ItemStack(ModItems.waterExtractor),
				I18n.translateToLocal("guide.skyresources.stage2.waterExtractor.text").replace("\\n", "\n"));

		SkyResourcesGuide.addPage("plantMatter", "guide.skyresources.stage2",
				I18n.translateToLocal("guide.skyresources.stage2.plantMatter.title").replace("\\n", "\n"),
				new ItemStack(ModItems.baseComponent, 1, 2),
				I18n.translateToLocal("guide.skyresources.stage2.plantMatter.text").replace("\\n", "\n"));

		SkyResourcesGuide.addPage("dirtFurnace", "guide.skyresources.stage2",
				I18n.translateToLocal("guide.skyresources.stage2.dirtFurnace.title").replace("\\n", "\n"),
				new ItemStack(ModBlocks.dirtFurnace),
				I18n.translateToLocal("guide.skyresources.stage2.dirtFurnace.text").replace("\\n", "\n"));

		SkyResourcesGuide.addPage("lavaBlaze", "guide.skyresources.stage2",
				I18n.translateToLocal("guide.skyresources.stage2.lavaBlaze.title").replace("\\n", "\n"),
				new ItemStack(ModBlocks.blazePowderBlock),
				I18n.translateToLocal("guide.skyresources.stage2.lavaBlaze.text").replace("\\n", "\n"));

		SkyResourcesGuide.addPage("heatSources", "guide.skyresources.stage2",
				I18n.translateToLocal("guide.skyresources.stage2.heatSources.title").replace("\\n", "\n"),
				new ItemStack(Blocks.TORCH),
				I18n.translateToLocal("guide.skyresources.stage2.heatSources.text").replace("\\n", "\n"));

		SkyResourcesGuide.addPage("listHeatSources", "guide.skyresources.stage2",
				I18n.translateToLocal("guide.skyresources.stage2.listHeatSources.title").replace("\\n", "\n"),
				new ItemStack(Blocks.TORCH),
				I18n.translateToLocal("guide.skyresources.stage2.listHeatSources.text").replace("\\n", "\n"));

		SkyResourcesGuide.addPage("rockGrinder", "guide.skyresources.stage2",
				I18n.translateToLocal("guide.skyresources.stage2.rockGrinder.title").replace("\\n", "\n"),
				new ItemStack(ModItems.stoneGrinder),
				I18n.translateToLocal("guide.skyresources.stage2.rockGrinder.text").replace("\\n", "\n"));

		SkyResourcesGuide.addPage("metalCreation", "guide.skyresources.stage2",
				I18n.translateToLocal("guide.skyresources.stage2.metalCreation.title").replace("\\n", "\n"),
				new ItemStack(ModItems.metalCrystal),
				I18n.translateToLocal("guide.skyresources.stage2.metalCreation.text").replace("\\n", "\n"));

		SkyResourcesGuide.addPage("crucible", "guide.skyresources.stage2",
				I18n.translateToLocal("guide.skyresources.stage2.crucible.title").replace("\\n", "\n"),
				new ItemStack(ModBlocks.crucible),
				I18n.translateToLocal("guide.skyresources.stage2.crucible.text").replace("\\n", "\n"));

		SkyResourcesGuide.addPage("fluidDropper", "guide.skyresources.stage2",
				I18n.translateToLocal("guide.skyresources.stage2.fluidDropper.title").replace("\\n", "\n"),
				new ItemStack(ModBlocks.fluidDropper),
				I18n.translateToLocal("guide.skyresources.stage2.fluidDropper.text").replace("\\n", "\n"));

		SkyResourcesGuide.addPage("alchemicalCondenser", "guide.skyresources.stage2",
				I18n.translateToLocal("guide.skyresources.stage2.alchemicalCondenser.title").replace("\\n", "\n"),
				new ItemStack(ModBlocks.alchemicalCondenser),
				I18n.translateToLocal("guide.skyresources.stage2.alchemicalCondenser.text").replace("\\n", "\n"));

		SkyResourcesGuide.addPage("purificationVessel", "guide.skyresources.stage2",
				I18n.translateToLocal("guide.skyresources.stage2.purificationVessel.title").replace("\\n", "\n"),
				new ItemStack(ModBlocks.purificationVessel),
				I18n.translateToLocal("guide.skyresources.stage2.purificationVessel.text").replace("\\n", "\n"));

		SkyResourcesGuide.addPage("cleanCrystal", "guide.skyresources.stage2",
				I18n.translateToLocal("guide.skyresources.stage2.cleanCrystal.title").replace("\\n", "\n"),
				new ItemStack(ModBlocks.crystalFluidBlocks.get(0)),
				I18n.translateToLocal("guide.skyresources.stage2.cleanCrystal.text").replace("\\n", "\n"));

		SkyResourcesGuide.addPage("earlyMetalSetup", "guide.skyresources.stage2",
				I18n.translateToLocal("guide.skyresources.stage2.earlyMetalSetup.title").replace("\\n", "\n"),
				new ItemStack(ModItems.metalCrystal),
				I18n.translateToLocal("guide.skyresources.stage2.earlyMetalSetup.text").replace("\\n", "\n"));

		SkyResourcesGuide.addPage("gemProduction", "guide.skyresources.stage2",
				I18n.translateToLocal("guide.skyresources.stage2.gemProduction.title").replace("\\n", "\n"),
				new ItemStack(Items.EMERALD),
				I18n.translateToLocal("guide.skyresources.stage2.gemProduction.text").replace("\\n", "\n"));

		SkyResourcesGuide.addPage("alchemicalItems", "guide.skyresources.stage3",
				I18n.translateToLocal("guide.skyresources.stage3.alchemicalItems.title").replace("\\n", "\n"),
				new ItemStack(ModItems.alchemyComponent, 1, 4),
				I18n.translateToLocal("guide.skyresources.stage3.alchemicalItems.text").replace("\\n", "\n"));

		SkyResourcesGuide.addPage("concentrator", "guide.skyresources.stage3",
				I18n.translateToLocal("guide.skyresources.stage3.concentrator.title").replace("\\n", "\n"),
				new ItemStack(ModBlocks.concentrator),
				I18n.translateToLocal("guide.skyresources.stage3.concentrator.text").replace("\\n", "\n"));

		SkyResourcesGuide.addPage("ironFreezer", "guide.skyresources.stage3",
				I18n.translateToLocal("guide.skyresources.stage3.ironFreezer.title").replace("\\n", "\n"),
				new ItemStack(ModBlocks.ironFreezer),
				I18n.translateToLocal("guide.skyresources.stage3.ironFreezer.text").replace("\\n", "\n"));

		SkyResourcesGuide.addPage("poweredCombustion", "guide.skyresources.stage3",
				I18n.translateToLocal("guide.skyresources.stage3.poweredCombustion.title").replace("\\n", "\n"),
				new ItemStack(ModBlocks.combustionHeater, 1, 2),
				I18n.translateToLocal("guide.skyresources.stage3.poweredCombustion.text").replace("\\n", "\n"));

		SkyResourcesGuide.addPage("poweredHeater", "guide.skyresources.stage3",
				I18n.translateToLocal("guide.skyresources.stage3.poweredHeater.title").replace("\\n", "\n"),
				new ItemStack(ModBlocks.poweredHeater),
				I18n.translateToLocal("guide.skyresources.stage3.poweredHeater.text").replace("\\n", "\n"));

		SkyResourcesGuide.addPage("nether", "guide.skyresources.stage3",
				I18n.translateToLocal("guide.skyresources.stage3.nether.title").replace("\\n", "\n"),
				new ItemStack(Blocks.NETHERRACK),
				I18n.translateToLocal("guide.skyresources.stage3.nether.text").replace("\\n", "\n"));

		SkyResourcesGuide.addPage("betterLava", "guide.skyresources.stage3",
				I18n.translateToLocal("guide.skyresources.stage3.betterLava.title").replace("\\n", "\n"),
				new ItemStack(ModBlocks.compressedNetherrack),
				I18n.translateToLocal("guide.skyresources.stage3.betterLava.text").replace("\\n", "\n"));

		SkyResourcesGuide.addPage("netherMetalCreation", "guide.skyresources.stage3",
				I18n.translateToLocal("guide.skyresources.stage3.netherMetalCreation.title").replace("\\n", "\n"),
				new ItemStack(ModBlocks.advancedCoolingCondenser),
				I18n.translateToLocal("guide.skyresources.stage3.netherMetalCreation.text").replace("\\n", "\n"));

		SkyResourcesGuide.addPage("dmWarper", "guide.skyresources.stage3",
				I18n.translateToLocal("guide.skyresources.stage3.dmWarper.title").replace("\\n", "\n"),
				new ItemStack(ModBlocks.darkMatterWarper),
				I18n.translateToLocal("guide.skyresources.stage3.dmWarper.text").replace("\\n", "\n"));

		SkyResourcesGuide.addPage("end", "guide.skyresources.stage3",
				I18n.translateToLocal("guide.skyresources.stage3.end.title").replace("\\n", "\n"),
				new ItemStack(Items.ENDER_EYE),
				I18n.translateToLocal("guide.skyresources.stage3.end.text").replace("\\n", "\n"));

		SkyResourcesGuide.addPage("healthGem", "guide.skyresources.stage3",
				I18n.translateToLocal("guide.skyresources.stage3.healthGem.title").replace("\\n", "\n"),
				new ItemStack(ModItems.healthGem),
				I18n.translateToLocal("guide.skyresources.stage3.healthGem.text").replace("\\n", "\n"));

		SkyResourcesGuide.addPage("lifeInfuser", "guide.skyresources.stage3",
				I18n.translateToLocal("guide.skyresources.stage3.lifeInfuser.title").replace("\\n", "\n"),
				new ItemStack(ModBlocks.lifeInfuser),
				I18n.translateToLocal("guide.skyresources.stage3.lifeInfuser.text").replace("\\n", "\n"));

		SkyResourcesGuide.addPage("commands", "guide.skyresources.misc",
				I18n.translateToLocal("guide.skyresources.misc.commands.title").replace("\\n", "\n"),
				new ItemStack(Blocks.COMMAND_BLOCK),
				I18n.translateToLocal("guide.skyresources.misc.commands.text").replace("\\n", "\n")
				.replace("{distance}", Integer.toString(ConfigOptions.islandDistance / 2)));

		SkyResourcesGuide.addPage("minetweaker", "guide.skyresources.misc",
				I18n.translateToLocal("guide.skyresources.misc.minetweaker.title").replace("\\n", "\n"),
				new ItemStack(Blocks.CRAFTING_TABLE),
				I18n.translateToLocal("guide.skyresources.misc.minetweaker.text").replace("\\n", "\n"));
	}
}
