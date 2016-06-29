package com.bartz24.skyresources.registry;

import com.bartz24.skyresources.base.guide.SkyResourcesGuide;
import com.bartz24.skyresources.config.ConfigOptions;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ModGuidePages
{
	public static void init()
	{
		SkyResourcesGuide.addPage("basics", "guide.skyresources.stage1",
				"The Basics", new ItemStack(Blocks.BEDROCK),
				"Welcome to Sky Resources! "
						+ "\n To get started, type /platform create (optional type) for your own island. "
						+ "<link,Additional_Commands,minecraft:command_block:0,commands> "
						+ "\n Depending on the platform you spawn on, choose one of the platforms to get started. "
						+ "\n <link,Sand_Platform,minecraft:sand:1,sandPlatform> "
						+ "\n <link,Snow_Platform,minecraft:snow:0,snowPlatform> "
						+ "\n If you don't have JEI, I don't know how you're going to get through modded MC. I'm not giving info on crafting if it's in JEI at all in this guide. "
						+ "\n Note that some recipes may be inaccurate or non-existant due to MineTweaker/CraftTweaker.");

		SkyResourcesGuide.addPage("sandPlatform", "guide.skyresources.stage1",
				"Sand Platform", new ItemStack(Blocks.SAND, 1, 1),
				"To get started on the sand platform, you're going to need some <recipe,,skyresources:AlchemyItemComponent:0> . "
						+ "\n Now, you need <recipe,,skyresources:AlchemyItemComponent:1> . I would recommend keeping 1 cactus at all times if you want to progress. "
						+ "Using a <recipe,,skyresources:CactusCuttingKnife:0> , make some <recipe,,skyresources:CactusFruit:0> , which has high saturation for regenerating. "
						+ "\n A <recipe,,skyresources:RedSandstoneInfusionStone:0> is needed for <link,Life_Infusion,skyresources:AlchemicalInfusionStone:0,lifeInfusion> . "
						+ "\n Using <link,Life_Infusion,skyresources:AlchemicalInfusionStone:0,lifeInfusion> , you can duplicate <recipe,,minecraft:cactus:0> . "
						+ "You can also use it on a <recipe,,skyresources:CactusFruitNeedle:0> to get a <recipe,,minecraft:sapling:4> . "
						+ "Also, use <link,Life_Infusion,skyresources:AlchemicalInfusionStone:0,lifeInfusion> to get <recipe,,minecraft:dirt:1> . "
						+ "\n \n <image,Sand_Platform_Flowchart_Guide,minecraft:cactus:0,skyresources:textures/guide/sandFlowchart.png>");

		SkyResourcesGuide.addPage("lifeInfusion", "guide.skyresources.stage1",
				"Life Infusion",
				new ItemStack(ModItems.alchemicalInfusionStone),
				"In order to do life infusion, you need a few things to do it. "
						+ "\n First, you need an infusion stone. There are 3 by default. "
						+ "\n <recipe,,skyresources:RedSandstoneInfusionStone:0> <recipe,,skyresources:SandstoneInfusionStone:0> <recipe,,skyresources:AlchemicalInfusionStone:0> "
						+ "\n Next, look in JEI for a recipe and put the items in the off-hand slot (the slot with the shield icon) in your off-hand slot. "
						+ "\n Hold the infusion stone in your main hand and right click the block shown in JEI to get your result.");

		SkyResourcesGuide.addPage("snowPlatform", "guide.skyresources.stage1",
				"Snow Platform", new ItemStack(Blocks.SNOW),
				"To get started on the sand platform, you're going to need some <recipe,,minecraft:snowball:0> and a <recipe,,minecraft:pumpkin:0> . "
						+ "\n In order to get <recipe,,minecraft:snowball:0> , shift right-click the snow to get them, and don't use the second pumpkin as it is needed for later. "
						+ "\n Make a snow golem and make sure it doesn't fall off the edge or die. Get more snow from the snow golem. "
						+ "\n Next, you need to make a <link,Mini_Freezer,skyresources:MiniFreezer:0,miniFreezer> , "
						+ "which is used to make <link,heavy_snowballs,skyresources:HeavySnowball:0,heavySnowball> . "
						+ "\n Make a mob farm using the snow, or <recipe,,skyresources:HeavySnow:0> blocks for rotten flesh and bones. "
						+ "This will be used to make <recipe,,skyresources:HeavySnow2:0> , which can then be froze into <recipe,,minecraft:dirt:1> . "
						+ "\n Another block of <recipe,,minecraft:dirt:1> is needed for a <recipe,,minecraft:sapling:1> . "
						+ "\n \n <image,Snow_Platform_Flowchart_Guide,skyresources:HeavySnow:0,skyresources:textures/guide/snowFlowchart.png>");

		SkyResourcesGuide.addPage("miniFreezer", "guide.skyresources.stage1",
				"Mini Freezer", new ItemStack(ModBlocks.miniFreezer),
				"The <recipe,,skyresources:MiniFreezer:0> is the first tier of freezers. The <link,Iron_Freezer,skyresources:IronFreezer:0,ironFreezer> is the next tier. "
						+ "\n This freezer runs at 0.25x the speed of the <link,Iron_Freezer,skyresources:IronFreezer:0,ironFreezer> . 1x is the normal speed shown in JEI."
						+ " This freezer can also only freeze 1 stack of items at a time. "
						+ "\n Items will pop out the front or into an adjecent inventory.");

		SkyResourcesGuide.addPage("combustionHeater",
				"guide.skyresources.stage2", "Combustion Heater",
				new ItemStack(ModBlocks.combustionHeater),
				"There are 2 types by default. The <recipe,,skyresources:CombustionHeater:0> and <recipe,,skyresources:CombustionHeater:1> "
						+ "are part of the combustion multiblock structures. "
						+ "\n To setup the multiblock, place down the heater, have an empty space above, "
						+ "and surround the empty space with valid blocks for that type of heater. "
						+ "\n \n <image,Wooden_Heater_Setup,skyresources:CombustionHeater:0,skyresources:textures/guide/combustionWood.png> "
						+ "\n The wood version requires blocks made of wood material for the other blocks. It has a maximum temperature of 100 C. "
						+ "\n Fuels with relatively high burn times will not work in the wooden heater. "
						+ "\n \n <image,Iron_Heater_Setup,skyresources:CombustionHeater:1,skyresources:textures/guide/combustionIron.png> "
						+ "\n The iron version requires blocks made of stone or iron material for the other blocks. It has a maximum temperature of 1538 C. "
						+ "\n Fuels with extremely high burn times will not work in the iron heater. "
						+ "\n \n The crafting process is as follows: "
						+ "\n Drop a set of items into the center for crafting (NOTE: ONLY 1 SET). "
						+ "\n Provide the heater with fuel to get a high enough temperature. "
						+ "\n Give the heater a redstone signal (Ex: button). If there was an explosion, it worked!");

		SkyResourcesGuide.addPage("waterExtractor", "guide.skyresources.stage2",
				"Water Extractor", new ItemStack(ModItems.waterExtractor),
				"The <recipe,,skyresources:WaterExtractor:0> is used for storing, extracting, and placing, and inserting water. It can hold 4000mB of water. "
						+ "\n To extract water from blocks, hold right-click for a little bit and let go. It can also pick up water source blocks. "
						+ "\n To place water, you must have 1000mB of water and shift-right click to place it in the world. "
						+ "\n To insert water into blocks, you must have the required water amount and right click the block. "
						+ "\n All recipes for extracting and inserting are in JEI. Check the uses of the <recipe,,skyresources:WaterExtractor:0> .");

		SkyResourcesGuide.addPage("plantMatter", "guide.skyresources.stage2",
				"Plant Matter", new ItemStack(ModItems.baseComponent, 1, 2),
				"<recipe,,skyresources:BaseItemComponent:2> is mainly used for crafting <recipe,,minecraft:dirt:0> . "
						+ "\n It also acts like bonemeal.");

		SkyResourcesGuide.addPage("dirtFurnace", "guide.skyresources.stage2",
				"Dirt Furnace", new ItemStack(ModBlocks.dirtFurnace),
				"Use the <recipe,,skyresources:DirtFurnace:0> to get access to torches, "
						+ "and other smeltable items without a cobblestone generator.");

		SkyResourcesGuide.addPage("lavaBlaze", "guide.skyresources.stage2",
				"Lava/Blaze Powder Block",
				new ItemStack(ModBlocks.blazePowderBlock),
				"The <recipe,,skyresources:BlazePowderBlock:0> is used for creating lava. "
						+ "\n To get lava, place the blaze powder block above a <link,heat_source,minecraft:torch:0,heatSources> . "
						+ "\n Note that you will want to be able to setup your first lava for a cobblestone generator.");

		SkyResourcesGuide.addPage("heatSources", "guide.skyresources.stage2",
				"Heat Sources", new ItemStack(Blocks.TORCH),
				"Heat Sources are used for heating many functional blocks. "
						+ "\n Heat Sources go under these blocks: "
						+ "\n <link,Crucible,skyresources:Crucible:0,crucible> "
						+ "\n <link,Purification_Vessel,skyresources:PurificationVessel:0,purificationVessel> "
						+ "\n <link,Alchemical_Condenser,skyresources:AlchemicalCondenser:0,alchemicalCondenser> "
						+ "\n <link,Blaze_Powder_Block,skyresources:BlazePowderBlock:0,lavaBlaze> "
						+ "\n \n Heat Sources are shown in JEI, but in order to get to them, you have to cycle through the categories at "
						+ "the top by clicking the current category shown and clicking left or right. "
						+ "\n Better heat sources make the blocks mentioned earlier run faster.");

		SkyResourcesGuide.addPage("rockGrinder", "guide.skyresources.stage2",
				"Rock Grinder", new ItemStack(ModItems.stoneGrinder),
				"The rock grinder allows for the player to break cobblestone down into sand, and sandstone down into gravel, by default. "
						+ "\n There are 3 by default, which are the <recipe,,skyresources:StoneGrinder:0> , <recipe,,skyresources:IronGrinder:0> ,"
						+ " and <recipe,,skyresources:DiamondGrinder:0> . "
						+ "\n \n The rock grinder is also used for getting dirty gems for <link,gem_production,minecraft:emerald:0,gemProduction> .");

		SkyResourcesGuide.addPage("metalCreation", "guide.skyresources.stage2",
				"Metal Creation", new ItemStack(ModItems.metalCrystal),
				"To get started in metal creation you're going to need to get <recipe,,skyresources:MetalCrystal:0> for iron "
						+ "through <link,combustion,skyresources:CombustionHeater:1,combustionHeater> . "
						+ "\n The rest of the process is done using the following blocks (Pre-Bucket): "
						+ "\n <link,Crucible,skyresources:Crucible:0,crucible> "
						+ "\n <link,Fluid_Dropper,skyresources:FluidDropper:0,fluidDropper> "
						+ "\n <link,Alchemical_Condenser,skyresources:AlchemicalCondenser:0,alchemicalCondenser> "
						+ "\n \n After obtaining iron for a bucket, you can improve the process with these: "
						+ "\n <link,Purification_Vessel,skyresources:PurificationVessel:0,purificationVessel> "
						+ "\n <link,Clean_Crystal_Fluid,skyresources:IronCrystalFluidBlock:0,cleanCrystal> "
						+ "\n \n For stage 3 metal creation, you need a <link,Concentrator,skyresources:Concentrator:0,concentrator> .");

		SkyResourcesGuide.addPage("crucible", "guide.skyresources.stage2",
				"Crucible", new ItemStack(ModBlocks.crucible),
				"The <recipe,,skyresources:Crucible:0> is used for creating dirty crystal fluid from crystal shards. "
						+ "\n This requires a <link,heat_source,minecraft:torch:0,heatSources> underneath to function. "
						+ "\n Drop the crystal shards in and it will turn into dirty crystal fluid. "
						+ "\n Dirty crystal fluid can be condensed using the <link,Alchemical_Condenser,skyresources:AlchemicalCondenser:0,alchemicalCondenser> , "
						+ "but takes 10x as long as clean fluid. Place dirty crystal fluid in the <link,Purification_Vessel,skyresources:PurificationVessel:0,purificationVessel> to get "
						+ "<link,clean_crystal_fluid,skyresources:IronCrystalFluidBlock:0,cleanCrystal> .");

		SkyResourcesGuide.addPage("fluidDropper", "guide.skyresources.stage2",
				"Fluid Dropper", new ItemStack(ModBlocks.fluidDropper),
				"The <recipe,,skyresources:FluidDropper:0> will pull fluids from tanks next to it and place the source below it when it has 1 bucket. "
						+ "\n This is useful for pulling out of the <link,Crucible,skyresources:Crucible:0,crucible> before getting a bucket.");

		SkyResourcesGuide.addPage("alchemicalCondenser",
				"guide.skyresources.stage2", "Alchemical Condenser",
				new ItemStack(ModBlocks.alchemicalCondenser),
				"The <recipe,,skyresources:AlchemicalCondenser:0> is used for turning crystal fluid (dirty or clean) into ingots. "
						+ "\n Dirty crystal fluid takes 10x as long as <link,clean_crystal_fluid,skyresources:IronCrystalFluidBlock:0,cleanCrystal> . "
						+ "\n This requires a <link,heat_source,minecraft:torch:0,heatSources> underneath to function. "
						+ "\n Also, the fluid can't be flowing and must not move. "
						+ "\n Fluids will not condense into their ingot form if it isn't in ore dictionary.");

		SkyResourcesGuide.addPage("purificationVessel",
				"guide.skyresources.stage2", "Purification Vessel",
				new ItemStack(ModBlocks.purificationVessel),
				"The <recipe,,skyresources:PurificationVessel:0> is used for turning dirty crystal fluid into "
						+ "<link,clean_crystal_fluid,skyresources:IronCrystalFluidBlock:0,cleanCrystal> . "
						+ "\n This requires a <link,heat_source,minecraft:torch:0,heatSources> underneath to function. "
						+ "\n And then place the fluid in for it to convert.");

		SkyResourcesGuide.addPage("cleanCrystal", "guide.skyresources.stage2",
				"Clean Crystal Fluid",
				new ItemStack(ModBlocks.crystalFluidBlocks.get(0)),
				"Clean Crystal Fluid is used for obtaining more crystal shards, "
						+ "and can be condensed into ingots with the <link,Alchemical_Condenser,skyresources:AlchemicalCondenser:0,alchemicalCondenser> . "
						+ "\n For it to work, the fluid has to be still and flowing, and cannot have an <link,Alchemical_Condenser,skyresources:AlchemicalCondenser:0,alchemicalCondenser> "
						+ "underneath it. "
						+ "\n As long as that is true, crystal shards will randomly pop and that will cause the fluid to have a chance to run out and disappear. "
						+ "Rarer fluids pop less crystals slower, and have a higher chance to run out and disappear. "
						+ "\n Use this to setup a crystal shard farm.");

		SkyResourcesGuide.addPage("earlyMetalSetup",
				"guide.skyresources.stage2",
				"Pre-Bucket Early Metal Setup (SPOILERS)",
				new ItemStack(ModItems.metalCrystal),
				"This shows a setup that is semi-automated."
						+ " \n A <link,crucible,skyresources:Crucible:0,crucible> attached to 2 <link,fluid_droppers,skyresources:FluidDropper:0,fluidDropper> , "
						+ "which drop the dirty fluid on top of an <link,alchemical_condenser,skyresources:AlchemicalCondenser:0,alchemicalCondenser> ."
						+ " \n <image,View_Setup,skyresources:MetalCrystal:0,skyresources:textures/guide/earlyMetalSetup.png>");

		SkyResourcesGuide.addPage("gemProduction", "guide.skyresources.stage2",
				"Gem Production", new ItemStack(Items.EMERALD),
				"Gems like emeralds, rubies, sapphires, peridots, and red and yellow garnets are obtained through this process. "
						+ "\n First, use the <link,rock_grinder,skyresources:StoneGrinder:0,rockGrinder> on stone to get dirty gems like the "
						+ "<recipe,,skyresources:DirtyGem:0> . These have a chance to drop. "
						+ "\n Next, right-click the dirty gems on to a <recipe,,minecraft:cauldron:0> filled with water to get the clean, and usable gems. "
						+ "This will not work if the clean gems do not exist in ore dictionary.");

		SkyResourcesGuide.addPage("alchemicalItems",
				"guide.skyresources.stage3", "Alchemical Items",
				new ItemStack(ModItems.alchemyComponent, 1, 4),
				"There are 4 alchemical items used for advanced crafting currently. "
						+ "\n <recipe,,skyresources:AlchemyItemComponent:3> "
						+ "\n <recipe,,skyresources:AlchemyItemComponent:2> "
						+ "\n <recipe,,skyresources:AlchemyItemComponent:4> "
						+ "\n <recipe,,skyresources:AlchemyItemComponent:5> "
						+ "\n \n These are used for the <link,Concentrator,skyresources:Concentrator:0,concentrator> "
						+ "and the <link,Alchemical_Infusion_Stone,skyresources:AlchemicalInfusionStone:0,lifeInfusion> .");

		SkyResourcesGuide.addPage("concentrator", "guide.skyresources.stage3",
				"Concentrator", new ItemStack(ModBlocks.concentrator),
				"The <recipe,,skyresources:Concentrator:0> uses an advanced metal creation process that turns items tossed above it into an ore block . "
						+ "\n It has a maximum temperature of 1538 C and fuels with extremely high burn times will not work in the iron heater. "
						+ "\n To make an ore, toss the items required on top, and place down <recipe,,skyresources:CompressedStone:0> (usually) "
						+ "under it to turn it into an ore in 500 ticks.");

		SkyResourcesGuide.addPage("ironFreezer", "guide.skyresources.stage3",
				"Iron Freezer", new ItemStack(ModBlocks.ironFreezer),
				"The <recipe,,skyresources:IronFreezer:0> is the second tier of freezers. "
						+ "\n To setup the <recipe,,skyresources:IronFreezer:0> , place 2 of these blocks vertically to form the multiblock. "
						+ "\n This freezer runs at 1x speed."
						+ " This freezer can freeze 3 stacks of items at a time. "
						+ "\n Items will pop out the front of the bottom block or into an adjecent inventory the bottom block.");

		SkyResourcesGuide.addPage("commands", "guide.skyresources.misc",
				"Commands", new ItemStack(Blocks.COMMAND_BLOCK),
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

		SkyResourcesGuide.addPage("minetweaker", "guide.skyresources.misc",
				"Minetweaker/CraftTweaker",
				new ItemStack(Blocks.CRAFTING_TABLE),
				"For Minetweaker/CraftTweaker support, go to "
						+ "\n https://github.com/Bartz24/SkyResources/wiki/Minetweaker-Support");

	}
}
