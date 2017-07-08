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

	public static int healthGemMaxHealth;
	public static float healthGemPercentage;

	public static int condenserProcessTimeBase;

	public static int crucibleCapacity;

	public static float knifeBaseDamage;
	public static float knifeBaseDurability;

	public static float rockGrinderBaseDamage;
	public static float rockGrinderBaseDurability;

	public static float combustionHeatMultiplier;

	public static int fluidDropperCapacity;

	public static boolean endWussMode;
	public static boolean recipeDifficulty;

	public static boolean allowGuide;
	public static boolean rememberGuide;
	public static int draconiumType;
	public static boolean allowAllGemTypes;
	public static boolean displayFirstChatInfo;

	public static String lastGuidePage;
	public static String lastGuideCat;
	public static String lastGuideSearch;

	public static List<IConfigElement> getConfigElements()
	{
		List<IConfigElement> list = new ArrayList<IConfigElement>();

		list.addAll(new ConfigElement(config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements());
		list.addAll(new ConfigElement(config.getCategory("healthRing")).getChildElements());
		list.addAll(new ConfigElement(config.getCategory("condenser")).getChildElements());
		list.addAll(new ConfigElement(config.getCategory("crucible")).getChildElements());
		list.addAll(new ConfigElement(config.getCategory("knife")).getChildElements());
		list.addAll(new ConfigElement(config.getCategory("rockGrinder")).getChildElements());
		list.addAll(new ConfigElement(config.getCategory("combustion")).getChildElements());
		list.addAll(new ConfigElement(config.getCategory("fluidDropper")).getChildElements());
		list.addAll(new ConfigElement(config.getCategory("concentrator")).getChildElements());
		list.addAll(new ConfigElement(config.getCategory("guide")).getChildElements());

		return list;
	}

	public static void setConfigSettings()
	{
		Property worldTypeProperty = config.get(Configuration.CATEGORY_GENERAL, "WorldSpawnType", 0,
				"0=random, 1=sand, 2=snow, 3=wood, 4=grass (Not in random choices)");

		endWussMode = config.get(Configuration.CATEGORY_GENERAL, "End Portal Wuss Mode", false,
				"Makes the end portal less dangerous").getBoolean(false);
		recipeDifficulty = config.get(Configuration.CATEGORY_GENERAL, "Difficult Recipes", false,
				"Harder Recipes using higher tier materials from other mods").getBoolean(false);

		draconiumType = config.get(Configuration.CATEGORY_GENERAL, "Draconium Crystal Type", 1, "0=overworld, 1=nether")
				.getInt(1);

		healthGemMaxHealth = config
				.get("health", "Health Gem Max Health Infusion", 100, "Max health Health Gem can store").getInt(100);
		healthGemPercentage = (float) config.get("health", "Health Gem Boost Percentage", 0.02,
				"Percentage (min 0) of health stored to boost player health").getDouble(0.02);
		condenserProcessTimeBase = config
				.get("condenser", "Condenser Base Process Time", 250, "Base time for condensers to process")
				.getInt(250);
		crucibleCapacity = config.get("crucible", "Crucible Capacity", 4000).getInt(4000);
		knifeBaseDamage = (float) config.get("knife", "Knife Base Damage", 1.5).getDouble(1.5);
		knifeBaseDurability = (float) config.get("knife", "Knife Base Durability", 0.8).getDouble(0.8);
		rockGrinderBaseDamage = (float) config.get("rockGrinder", "Rock Grinder Base Damage", 2.5).getDouble(2.5);
		rockGrinderBaseDurability = (float) config.get("rockGrinder", "Rock Grinder Base Durability", 0.8)
				.getDouble(0.8);
		combustionHeatMultiplier = (float) config
				.get("combustion", "Combustion Fuel Heat Multiplier", 0.5, "Amount of heat from fuel gained")
				.getDouble(0.5);
		fluidDropperCapacity = config.get("fluidDropper", "Fluid Dropper Capacity", 1000).getInt(1000);

		allowAllGemTypes = config.get(Configuration.CATEGORY_GENERAL, "All Dirty Gem Types", false,
				"Allows all dirty gem types to be obtainable").getBoolean(false);

		displayFirstChatInfo = config
				.get(Configuration.CATEGORY_GENERAL, "Login Mod Info", true, "Display mod info in chat on login")
				.getBoolean(true);

		rememberGuide = config.get("guide", "Remember Current Guide Page", true).getBoolean(true);
		allowGuide = config.get("guide", "Allow guide to be opened", true).getBoolean(true);

		if (config.hasChanged())
			config.save();
	}

	public static void loadConfigThenSave(FMLPreInitializationEvent e)
	{
		config = new Configuration(e.getSuggestedConfigurationFile());

		config.load();
		setConfigSettings();
		config.save();
	}

	public static void reloadConfigs()
	{
		setConfigSettings();
		if (config.hasChanged())
			config.save();
	}
}
