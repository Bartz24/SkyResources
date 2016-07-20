package com.bartz24.skyresources.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Strings;

import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigOptions
{
	public static Configuration config;

	public static int worldSpawnType;
	public static int healthRingMaxHealth;
	public static float healthRingPercentage;

	public static int condenserProcessTimeBase;

	public static int crucibleCapacity;

	public static float knifeBaseDamage;
	public static float knifeBaseDurability;

	public static float rockGrinderBaseDamage;
	public static float rockGrinderBaseDurability;

	public static float combustionHeatMultiplier;

	public static int fluidDropperCapacity;

	public static int crystalConcentratorAmount;

	public static int islandDistance;
	public static int islandSize;
	public static int islandResetDistance;
	public static boolean spawnChest;
	public static boolean spawnIgloo;
	public static boolean netherVoid;

	public static String commandName;

	public static boolean oneChunk;
	public static boolean oneChunkCommandAllowed;
	
	public static boolean scaleGuideGui;
	public static boolean endPussyMode;

	public static List<String> startingItems;

	public static List<IConfigElement> getConfigElements()
	{
		List<IConfigElement> list = new ArrayList<IConfigElement>();

		list.addAll(new ConfigElement(
				config.getCategory(Configuration.CATEGORY_GENERAL))
						.getChildElements());
		list.addAll(new ConfigElement(config.getCategory("islands"))
				.getChildElements());
		list.addAll(new ConfigElement(config.getCategory("healthRing"))
				.getChildElements());
		list.addAll(new ConfigElement(config.getCategory("condenser"))
				.getChildElements());
		list.addAll(new ConfigElement(config.getCategory("crucible"))
				.getChildElements());
		list.addAll(new ConfigElement(config.getCategory("knife"))
				.getChildElements());
		list.addAll(new ConfigElement(config.getCategory("rockGrinder"))
				.getChildElements());
		list.addAll(new ConfigElement(config.getCategory("combustion"))
				.getChildElements());
		list.addAll(new ConfigElement(config.getCategory("fluidDropper"))
				.getChildElements());
		list.addAll(new ConfigElement(config.getCategory("concentrator"))
				.getChildElements());

		return list;
	}

	public static void loadConfigThenSave(FMLPreInitializationEvent e)
	{
		config = new Configuration(e.getSuggestedConfigurationFile());

		config.load();

		startingItems = new ArrayList<String>();

		Property worldTypeProperty = config.get(Configuration.CATEGORY_GENERAL,
				"WorldSpawnType", 0);

		worldTypeProperty.setComment(
				"0=random, 1=sand, 2=snow, 3=dirt (DIRT NOT IMPLEMENTED)");
		
		scaleGuideGui = config.get(Configuration.CATEGORY_GENERAL, "Rescale Guide Gui Down", true)
				.getBoolean(true);
		endPussyMode = config.get(Configuration.CATEGORY_GENERAL, "End Portal Pussy Mode", false)
				.getBoolean(false);

		worldSpawnType = worldTypeProperty.getInt();

		islandDistance = config.get("islands", "Island Gap Distance", 1000)
				.getInt(1000);

		islandResetDistance = Math.min(config
				.get("islands",
						"Island Reset Radius (Max 1/2 of Island Distance)", 500)
				.getInt(500), islandDistance / 2);

		islandSize = config.get("islands", "Island Width/Length", 3).getInt(3);

		spawnChest = config.get("islands", "Spawn Chest", false)
				.getBoolean(false);
		spawnIgloo = config.get("islands", "Spawn Igloo on Snow Island", false)
				.getBoolean(false);

		netherVoid = config
				.get("islands", "Nether Void World", true)
				.getBoolean(true);

		commandName = config.get("islands",
				"Name For Command (Default: platform)", "platform").getString();

		oneChunk = config.get("islands",
				"One Chunk Challenge (Required before world creation as it changes the spawn platform)",
				false).getBoolean(false);

		oneChunkCommandAllowed = config
				.get("islands", "Allow One Chunk Mode to be activated", false)
				.getBoolean(false);

		healthRingMaxHealth = config
				.get("healthRing", "Health Ring Max Health", 100).getInt(100);
		healthRingPercentage = (float) config
				.get("healthRing", "Health Ring Boost Percentage", 0.05)
				.getDouble(0.05);
		condenserProcessTimeBase = config
				.get("condenser", "Condenser Base Process Time", 750)
				.getInt(2000);
		crucibleCapacity = config.get("crucible", "Crucible Capacity", 4000)
				.getInt(4000);
		knifeBaseDamage = (float) config.get("knife", "Knife Base Damage", 1.5)
				.getDouble(1.5);
		knifeBaseDurability = (float) config
				.get("knife", "Knife Base Durability", 0.8).getDouble(0.8);
		rockGrinderBaseDamage = (float) config
				.get("rockGrinder", "Rock Grinder Base Damage", 2.5)
				.getDouble(2.5);
		rockGrinderBaseDurability = (float) config
				.get("rockGrinder", "Rock Grinder Base Durability", 0.6)
				.getDouble(0.6);
		combustionHeatMultiplier = (float) config
				.get("combustion", "Combustion Fuel Heat Multiplier", 0.5)
				.getDouble(0.5);
		fluidDropperCapacity = config
				.get("fluidDropper", "Fluid Dropper Capacity", 1000)
				.getInt(1000);
		crystalConcentratorAmount = config
				.get("concentrator", "Crystal Concentrator Amount", 1)
				.getInt(1);

		String[] array = new String[36];
		for (int i = 0; i < 36; i++)
			array[i] = "";

		startingItems = Arrays.asList(config.getStringList("Starting Inventory",
				"startingInv", array,
				"The starting inventory Format: modid:itemid:meta*amt"));

		config.save();
	}
}
