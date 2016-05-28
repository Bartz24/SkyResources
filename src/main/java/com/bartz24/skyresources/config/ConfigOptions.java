package com.bartz24.skyresources.config;

import java.util.ArrayList;
import java.util.List;

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
	public static int crucibleCrystalAmount;

	public static float knifeBaseDamage;
	public static float knifeBaseDurability;

	public static float rockGrinderBaseDamage;
	public static float rockGrinderBaseDurability;

	public static float combustionHeatMultiplier;

	public static int fluidDropperCapacity;

	public static int crystalConcentratorAmount;

	public static int islandDistance;
	public static int islandSize;

	public static String commandName;

	public static boolean oneChunk;
	public static boolean oneChunkCommandAllowed;
	

	public static List<IConfigElement> getConfigElements()
	{
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		
		list.addAll(new ConfigElement(config
				.getCategory(Configuration.CATEGORY_GENERAL))
						.getChildElements());
		list.addAll(new ConfigElement(config
				.getCategory("islands"))
						.getChildElements());
		list.addAll(new ConfigElement(config
				.getCategory("healthRing"))
						.getChildElements());
		list.addAll(new ConfigElement(config
				.getCategory("condenser"))
						.getChildElements());
		list.addAll(new ConfigElement(config
				.getCategory("crucible"))
						.getChildElements());
		list.addAll(new ConfigElement(config
				.getCategory("knife"))
						.getChildElements());
		list.addAll(new ConfigElement(config
				.getCategory("rockGrinder"))
						.getChildElements());
		list.addAll(new ConfigElement(config
				.getCategory("combustion"))
						.getChildElements());
		list.addAll(new ConfigElement(config
				.getCategory("fluidDropper"))
						.getChildElements());
		list.addAll(new ConfigElement(config
				.getCategory("concentrator"))
						.getChildElements());
		
		return list;
	}

	public static void loadConfigThenSave(FMLPreInitializationEvent e)
	{
		config = new Configuration(
				e.getSuggestedConfigurationFile());

		config.load();

		Property worldTypeProperty = config.get(Configuration.CATEGORY_GENERAL,
				"WorldSpawnType", 0);

		worldTypeProperty.setComment(
				"0=random, 1=sand, 2=snow, 3=dirt (DIRT NOT IMPLEMENTED)");

		worldSpawnType = worldTypeProperty.getInt();

		islandDistance = config.get("islands", "Island Gap Distance", 1000)
				.getInt(1000);
		
		islandSize = config.get("islands", "Island Width/Length", 3)
				.getInt(3);

		commandName = config.get("islands",
				"Name For Command (Default: platform)", "platform").getString();

		oneChunk = config.get("islands",
				"One Chunk Challenge (Required before world creation as it changes the spawn platform)",
				false).getBoolean(false);
		
		oneChunkCommandAllowed = config.get("islands",
				"Allow One Chunk Mode to be activated",
				false).getBoolean(false);

		healthRingMaxHealth = config
				.get("healthRing", "Health Ring Max Health", 100).getInt(100);
		healthRingPercentage = (float) config
				.get("healthRing", "Health Ring Boost Percentage", 0.05)
				.getDouble(0.05);
		condenserProcessTimeBase = config
				.get("condenser", "Condenser Base Process Time", 2000)
				.getInt(2000);
		crucibleCapacity = config.get("crucible", "Crucible Capacity", 4000)
				.getInt(4000);
		crucibleCrystalAmount = config
				.get("crucible", "Crucible Crystal Value", 1000).getInt(1000);
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

		config.save();
	}
}
