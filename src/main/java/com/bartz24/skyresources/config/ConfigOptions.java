package com.bartz24.skyresources.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigOptions
{
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

	public static void loadConfigThenSave(FMLPreInitializationEvent e)
	{
		Configuration config = new Configuration(
				e.getSuggestedConfigurationFile());

		config.load();

		Property worldTypeProperty = config.get(Configuration.CATEGORY_GENERAL, "WorldSpawnType", 0);
		
		worldTypeProperty.setComment("0=random, 1=sand, 2=snow, 3=dirt (DIRT NOT IMPLEMENTED)");
		
		worldSpawnType = worldTypeProperty.getInt();
		
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
