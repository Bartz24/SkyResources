package com.bartz24.skyresources.registry;

import com.bartz24.skyresources.base.guide.SkyResourcesGuide;
import com.bartz24.skyresources.config.ConfigOptions;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ModGuidePages
{
	public static void init()
	{
		SkyResourcesGuide.addPage("basics", "guide.skyresources.stage1", "The Basics", new ItemStack(Blocks.BEDROCK), 
				"Welcome to Sky Resources! "
				+ "\n To get started, type /platform create (optional type) for your own island. "
				+ "<link,Additional_Commands,minecraft:command_block:0,commands> "
				+ "\n Depending on the platform you spawn on, choose one of the platforms to get started. "
				+ "\n <link,Sand_Platform,minecraft:sand:1,sandPlatform> "
				+ "\n <link,Snow_Platform,minecraft:snow:0,snowPlatform> "
				+ "\n If you don't have JEI, I don't know how you're going to get through modded MC. I'm not giving info on crafting if it's in JEI at all in this guide. "
				+ "\n Note that some recipes may be inaccurate or non-existant due to MineTweaker/CraftTweaker.");
		
		SkyResourcesGuide.addPage("sandPlatform", "guide.skyresources.stage1", "Sand Platform", new ItemStack(Blocks.SAND, 1, 1), 
				"To get started on the sand platform, you're going to need some <recipe,,skyresources:AlchemyItemComponent:0> . "
				+ "\n Now, you need <recipe,,skyresources:AlchemyItemComponent:1> . I would recommend keeping 1 cactus at all times if you want to progress. "
				+ "Using a <recipe,,skyresources:CactusCuttingKnife:0> , make some <recipe,,skyresources:CactusFruit:0> , which has high saturation for regenerating. "
				+ "\n A <recipe,,skyresources:RedSandstoneInfusionStone:0> is needed for <link,Life_Infusion,skyresources:AlchemicalInfusionStone:0,lifeInfusion> . "
				+ "\n Using <link,Life_Infusion,skyresources:AlchemicalInfusionStone:0,lifeInfusion> , you can duplicate <recipe,,minecraft:cactus:0> . "
				+ "You can also use it on a <recipe,,skyresources:CactusFruitNeedle:0> to get a <recipe,,minecraft:sapling:4> . "
				+ "Also, use <link,Life_Infusion,skyresources:AlchemicalInfusionStone:0,lifeInfusion> to get <recipe,,minecraft:dirt:1> . "
				+ "\n \n <image,Sand_Platform_Flowchart_Guide,minecraft:cactus:0,skyresources:textures/guide/sandFlowchart.png>");
		
		SkyResourcesGuide.addPage("snowPlatform", "guide.skyresources.stage1", "Snow Platform", new ItemStack(Blocks.SNOW), 
				"To get started on the sand platform, you're going to need some <recipe,,minecraft:snowball:0> . ");
		
		
		
		SkyResourcesGuide.addPage("combustionHeater", "guide.skyresources.stage2", "Combustion Heater", new ItemStack(ModBlocks.combustionHeater), 
				"!");

		SkyResourcesGuide.addPage("earlyMetalSetup", "guide.skyresources.stage2", "Pre-Bucket Early Metal Setup (SPOILERS)", new ItemStack(ModItems.metalCrystal), 
				"This shows a setup that is semi-automated."
				+ " \n A <link,crucible,skyresources:Crucible:0,crucible> attached to 2 <link,fluid_droppers,skyresources:FluidDropper:0,fluidDropper> , "
				+ "which drop the dirty fluid on top of an <link,alchemical_condenser,skyresources:AlchemicalCondenser:0,alchemicalCondenser> ."
				+ " \n <image,View_Setup,skyresources:MetalCrystal:0,skyresources:textures/guide/earlyMetalSetup.png>");
		
		
		
		SkyResourcesGuide.addPage("commands", "guide.skyresources.misc", "Commands", new ItemStack(Blocks.COMMAND_BLOCK), 
				"The /platform command has several subcommands. \n "
				+ "create (optional int/string)<type> : Spawn a new platform. Must not already be on an island. \n "
				+ "invite <player> : Have another player join your island. Player must not already be on an island. \n "
				+ "leave : Leave your island, clear inventory, and go to spawn. \n      (If you are the last person, no one can claim that island again.) \n "
				+ "home : Teleport back to your home island. Must be at least "
						+ ConfigOptions.islandDistance / 2 + " blocks away. \n "
				+ "spawn : Teleport back to spawn (0, 0). \n "
				+ "reset (optional int/string)<type> : Resets the platform and clears the players' inventory. \n      "
				+ "(If it doesn't clear everything, be nice and toss the rest? Maybe? Not recommended unless all players for that island are online) \n "
				+ "onechunk : Play in one chunk, on one island. Also resets the spawn chunk.");
		
	}
}
