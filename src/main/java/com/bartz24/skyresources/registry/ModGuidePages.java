package com.bartz24.skyresources.registry;

import com.bartz24.skyresources.base.guide.SkyResourcesGuide;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

public class ModGuidePages
{
	public static void init()
	{
		SkyResourcesGuide.addPage("basics", "guide.skyresources.stage1", new ItemStack(Blocks.BEDROCK));

		SkyResourcesGuide.addPage("sandIsland", "guide.skyresources.stage1", new ItemStack(Blocks.SAND, 1, 1));

		SkyResourcesGuide.addPage("lifeInfusion", "guide.skyresources.stage1",
				new ItemStack(ModItems.alchemicalInfusionStone));

		SkyResourcesGuide.addPage("snowIsland", "guide.skyresources.stage1", new ItemStack(Blocks.SNOW));

		SkyResourcesGuide.addPage("miniFreezer", "guide.skyresources.stage1", new ItemStack(ModBlocks.miniFreezer));

		SkyResourcesGuide.addPage("woodIsland", "guide.skyresources.stage1", new ItemStack(Blocks.PLANKS, 1, 5));

		SkyResourcesGuide.addPage("grassIsland", "guide.skyresources.stage1", new ItemStack(Blocks.GRASS));

		SkyResourcesGuide.addPage("combustionHeater", "guide.skyresources.stage2",
				new ItemStack(ModBlocks.combustionHeater));

		SkyResourcesGuide.addPage("waterExtractor", "guide.skyresources.stage2",
				new ItemStack(ModItems.waterExtractor));

		SkyResourcesGuide.addPage("dirtFurnace", "guide.skyresources.stage2", new ItemStack(ModBlocks.dirtFurnace));

		SkyResourcesGuide.addPage("lavaBlaze", "guide.skyresources.stage2", new ItemStack(ModBlocks.blazePowderBlock));

		SkyResourcesGuide.addPage("heatSources", "guide.skyresources.stage2", new ItemStack(Blocks.TORCH));

		SkyResourcesGuide.addPage("rockGrinder", "guide.skyresources.stage2", new ItemStack(ModItems.stoneGrinder));

		SkyResourcesGuide.addPage("metalCreation", "guide.skyresources.stage2", new ItemStack(ModItems.metalCrystal));

		SkyResourcesGuide.addPage("crucible", "guide.skyresources.stage2", new ItemStack(ModBlocks.crucible));

		SkyResourcesGuide.addPage("fluidDropper", "guide.skyresources.stage2", new ItemStack(ModBlocks.fluidDropper));

		SkyResourcesGuide.addPage("alchemicalCondenser", "guide.skyresources.stage2",
				new ItemStack(ModBlocks.alchemicalCondenser));
		
		SkyResourcesGuide.addPage("crystallizer", "guide.skyresources.stage2",
				new ItemStack(ModBlocks.crystallizer));

		SkyResourcesGuide.addPage("crucibleInserter", "guide.skyresources.stage2",
				new ItemStack(ModBlocks.crucibleInserter));

		SkyResourcesGuide.addPage("combustionCollector", "guide.skyresources.stage2",
				new ItemStack(ModBlocks.combustionCollector));
		
		SkyResourcesGuide.addPage("quickDropper", "guide.skyresources.stage2", new ItemStack(ModBlocks.quickDropper));

		SkyResourcesGuide.addPage("crushedStone", "guide.skyresources.stage2",
				new ItemStack(ModItems.techComponent, 1, 0));

		SkyResourcesGuide.addPage("gemProduction", "guide.skyresources.stage2", new ItemStack(Items.EMERALD));

		SkyResourcesGuide.addPage("alchemicalItems", "guide.skyresources.stage3",
				new ItemStack(ModItems.alchemyComponent, 1, 4));

		SkyResourcesGuide.addPage("ironFreezer", "guide.skyresources.stage3", new ItemStack(ModBlocks.ironFreezer));

		SkyResourcesGuide.addPage("rockCrusher", "guide.skyresources.stage3", new ItemStack(ModBlocks.rockCrusher));
		
		SkyResourcesGuide.addPage("rockCleaner", "guide.skyresources.stage3", new ItemStack(ModBlocks.rockCleaner));

		SkyResourcesGuide.addPage("aqueous", "guide.skyresources.stage3", new ItemStack(ModBlocks.aqueousConcentrator));

		SkyResourcesGuide.addPage("poweredHeater", "guide.skyresources.stage3", new ItemStack(ModBlocks.poweredHeater));

		if (Loader.isModLoaded("voidislandcontrol"))
			SkyResourcesGuide.addPage("nether", "guide.skyresources.misc", new ItemStack(Blocks.NETHERRACK));

		SkyResourcesGuide.addPage("dmWarper", "guide.skyresources.stage3", new ItemStack(ModBlocks.darkMatterWarper));

		SkyResourcesGuide.addPage("healthGem", "guide.skyresources.stage3", new ItemStack(ModItems.healthGem));

		SkyResourcesGuide.addPage("lifeInfuser", "guide.skyresources.stage3", new ItemStack(ModBlocks.lifeInfuser));

		SkyResourcesGuide.addPage("end", "guide.skyresources.stage3", new ItemStack(Items.ENDER_EYE));

		SkyResourcesGuide.addPage("minetweaker", "guide.skyresources.misc", new ItemStack(Blocks.CRAFTING_TABLE));
	}
}
