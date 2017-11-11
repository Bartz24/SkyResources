package com.bartz24.skyresources.config;

import java.util.HashMap;
import java.util.Map;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.alchemy.item.ItemOreAlchDust;
import com.bartz24.skyresources.base.MachineVariants;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = References.ModID)
public class ConfigOptions
{
	@Config.Comment("Config Settings for the machines and casings")
	public static ModularMachineSettings modularMachineSettings = new ModularMachineSettings();

	public static class ModularMachineSettings
	{
		@Config.Comment("Set Machine Speeds")
		public Map<String, Float> machineSpeed = defaultMachineSpeeds();
		@Config.Comment("Set Machine Efficiencies")
		public Map<String, Float> machineEfficiency = defaultMachineEfficiencies();
		@Config.Comment("Set Machine Max HU")
		public Map<String, Integer> machineMaxHU = defaultMachineMaxHUs();

		private Map<String, Float> defaultMachineSpeeds()
		{
			Map<String, Float> map = new HashMap();
			for (MachineVariants v : MachineVariants.values())
			{
				map.put(v.getName(), v.getDefaultRawSpeed());
			}
			return map;
		}

		private Map<String, Float> defaultMachineEfficiencies()
		{
			Map<String, Float> map = new HashMap();
			for (MachineVariants v : MachineVariants.values())
			{
				map.put(v.getName(), v.getDefaultRawEfficiency());
			}
			return map;
		}

		private Map<String, Integer> defaultMachineMaxHUs()
		{
			Map<String, Integer> map = new HashMap();
			for (MachineVariants v : MachineVariants.values())
			{
				map.put(v.getName(), v.getDefaultMaxHeat());
			}
			return map;
		}
	}

	@Config.Comment("Config Settings for Tools and Items")
	public static ToolSettings toolSettings = new ToolSettings();

	public static class ToolSettings
	{
		@Config.Comment("Multiplier of knife's material damage")
		public float knifeBaseDamage = 1.5f;
		@Config.Comment("Multiplier of knife's material durability")
		public float knifeBaseDurability = 0.7f;

		@Config.Comment("Multiplier of rock grinder's material damage")
		public float rockGrinderBaseDamage = 2.5f;
		@Config.Comment("Multiplier of rock grinder's material durability")
		public float rockGrinderBaseDurability = 0.8f;

		@Config.Comment("Max amount in Water Extractor")
		public int waterExtractorCapacity = 4000;

		@Config.Comment("Max Health a Health Gem can store")
		public int healthGemMaxHealth = 100;
		@Config.Comment("Percent of health in Health Gem to add to player's health")
		public float healthGemPercentage = 0.02f;

		@Config.Comment("Multiplier of infusion stone's material durability")
		public float infusionStoneBaseDurability = 1f;
		@Config.Comment("Allow infusion stones to bonemeal")
		public boolean infusionStoneBonemealCapability = true;
		@Config.Comment("Allow plant matter to bonemeal")
		public boolean plantMatterBonemealCapability = true;
	}

	@Config.Comment("Config Settings for Machines")
	public static MachineSettings machineSettings = new MachineSettings();

	public static class MachineSettings
	{
		@Config.Comment("Max amount in Fluid Dropper")
		public int fluidDropperCapacity = 1000;
		@Config.Comment("Dirt Furnace Fuel Rate")
		public int dirtFurnaceFuelRate = 3;
		@Config.Comment("Dirt Furnace Speed")
		public int dirtFurnaceSpeed = 4;
		@Config.Comment("Mini Freezer Speed")
		public float miniFreezerSpeed = 0.25f;
		@Config.Comment("Iron Freezer Speed")
		public float ironFreezerSpeed = 1f;
		@Config.Comment("Light Freezer Speed")
		public float lightFreezerSpeed = 100f;
		@Config.Comment("Aqueous Concentrator Speed")
		public int aqueousConcentratorSpeed = 5;
		@Config.Comment("Aqueous Concentrator RF Rate")
		public int aqueousConcentratorPowerUsage = 80;
		@Config.Comment("Aqueous Deconcentrator Speed")
		public int aqueousDeconcentratorSpeed = 10;
		@Config.Comment("Aqueous Deconcentrator RF Rate")
		public int aqueousDeconcentratorPowerUsage = 80;
		@Config.Comment("Dark Matter Warper Fuel Time")
		public int darkMatterWarperFuelTime = 3600;
		@Config.Comment("Dark Matter Warper Effect Players")
		public boolean darkMatterWarperEffectPlayers = true;
		@Config.Comment("Dark Matter Warper Effect With no Fuel")
		public boolean darkMatterWarperEffectNoFuel = true;
		@Config.Comment("End Portal Difficulty Level Normal(Armed Silverfish), Easy(Unarmed Silverfish), Wuss(No Silverfish)")
		public EndPortalDifficultyLevel endPortalMode = EndPortalDifficultyLevel.NORMAL;
		@Config.Comment("Rock Cleaner Speed")
		public int rockCleanerSpeed = 5;
		@Config.Comment("Rock Cleaner RF Rate")
		public int rockCleanerPowerUsage = 80;
		@Config.Comment("Rock Crusher Speed")
		public int rockCrusherSpeed = 2;
		@Config.Comment("Rock Crusher RF Rate")
		public int rockCrusherPowerUsage = 100;
		@Config.Comment("Base Crucible Speed")
		public int crucibleSpeed = 8;
		@Config.Comment("Max amount in Crucible")
		public int crucibleCapacity = 4000;
		@Config.Comment("Base Fusion Speed")
		public int fusionSpeed = 8;
		@Config.Comment("Smart Combustion Controller Craft Time Rate (Ticks)")
		public int combustionControllerTicks = 20;

		public enum EndPortalDifficultyLevel
		{
			NORMAL, EASY, WUSS
		}
	}

	@Config.Comment("Config Settings for the Guide")
	public static GuideSettings guideSettings = new GuideSettings();

	public static class GuideSettings
	{
		@Config.Comment("Display info about Guide in a notification")
		public boolean displayGuideMessage;
		@Config.Comment("Enable the in-game Guide")
		public boolean allowGuide = true;
		@Config.Comment("Remember page open in Guide")
		public boolean rememberGuide = true;
	}

	@Config.Comment("Misc Config Settings")
	public static MiscSettings miscSettings = new MiscSettings();

	public static class MiscSettings
	{
		@Config.Comment("Heavy Snowball Damage Dealt")
		public int heavySnowballDamage = 8;
		@Config.Comment("Explosive Heavy Snowball Damage Dealt")
		public int explosiveHeavySnowballDamage = 12;
		@Config.Comment("Makes certain recipes require more advanced components")
		public boolean advancedRecipes = false;
		@Config.Comment("Forces all gem types to be enabled")
		public boolean allowAllGemTypes = false;
		@Config.Comment("Add beetroot seeds to grass drops")
		public boolean addBeetrootSeedDrop = true;
		@Config.Comment("Add melon seeds to grass drops")
		public boolean addMelonSeedDrop = true;
		@Config.Comment("Add pumpkin seeds to grass drops")
		public boolean addPumpkinSeedDrop = true;
		@Config.Comment("Add cocoa beans to grass drops")
		public boolean addCocoaBeanDrop = true;
		@Config.Comment("Add carrots to grass drops")
		public boolean addCarrotDrop = true;
		@Config.Comment("Add potatoes to grass drops")
		public boolean addPotatoDrop = true;
	}

	@Config.Comment("Config Settings for plugins")
	public static PluginSettings pluginSettings = new PluginSettings();

	public static class PluginSettings
	{
		@Config.Comment("Config Settings for Actually Additions")
		public ActuallyAdditionsSettings actuallyAdditionsSettings = new ActuallyAdditionsSettings();

		public class ActuallyAdditionsSettings
		{
			@Config.Comment("Add canola to grass drops")
			public boolean addCanolaDrop = true;
			@Config.Comment("Add coffee to grass drops")
			public boolean addCoffeeDrop = true;
			@Config.Comment("Add flax to grass drops")
			public boolean addFlaxDrop = true;
			@Config.Comment("Add rice to grass drops")
			public boolean addRiceDrop = true;
		}
		
		@Config.Comment("Config Settings for Applied Energistics")
		public AppliedEnergisticsSettings appliedEnergisticsSettings = new AppliedEnergisticsSettings();

		public class AppliedEnergisticsSettings
		{
			@Config.Comment("Add inscriber press combustion recipes")
			public boolean addPressRecipes = true;
		}
		
		@Config.Comment("Config Settings for Armor Plus")
		public ArmorPlusSettings armorPlusSettings = new ArmorPlusSettings();

		public class ArmorPlusSettings
		{
			@Config.Comment("Add lava crystal combustion recipe")
			public boolean addLavaCrystalRecipe = true;
		}
		
		@Config.Comment("Config Settings for Embers")
		public EmbersSettings embersSettings = new EmbersSettings();

		public class EmbersSettings
		{
			@Config.Comment("Add ember shard combustion recipe")
			public boolean addEmberShardRecipe = true;
		}
		
		@Config.Comment("Config Settings for Forestry")
		public ForestrySettings forestrySettings = new ForestrySettings();

		public class ForestrySettings
		{
			@Config.Comment("Bee Attractor Time per cycle")
			public int beeAttractorTime = 200;
			@Config.Comment("Bee Attractor RF Rate")
			public int beeAttractorPowerUsage = 100;
			@Config.Comment("Bee Attractor Seed Oil Usage Rate")
			public int beeAttractorSeedOilUsage = 20;
			@Config.Comment("Bee Attractor Seed Oil Capacity")
			public int beeAttractorSeedOilCapacity = 4000;
		}
		
		@Config.Comment("Config Settings for Integrated Dynamics")
		public IntegratedDynamicsSettings integratedDynamicsSettings = new IntegratedDynamicsSettings();

		public class IntegratedDynamicsSettings
		{
			@Config.Comment("Add menril berry/sapling life infusion recipes")
			public boolean addMenrilRecipes = true;
		}
		
		@Config.Comment("Config Settings for Tinkers Construct")
		public TinkersConstructSettings tinkersConstructSettings = new TinkersConstructSettings();

		public class TinkersConstructSettings
		{
			@Config.Comment("Add slime ball/sapling life infusion and combustion recipes")
			public boolean addSlimeRecipes = true;
		}
		
		@Config.Comment("Config Settings for Tech Reborn")
		public TechRebornSettings techRebornSettings = new TechRebornSettings();

		public class TechRebornSettings
		{
			@Config.Comment("Add rubber life infusion and combustion recipes")
			public boolean addRubberRecipes = true;
		}
		
		@Config.Comment("Config Settings for Thermal Expansion")
		public ThermalExpansionSettings thermalExpansionSettings = new ThermalExpansionSettings();

		public class ThermalExpansionSettings
		{
			@Config.Comment("Add crystal fluid and lava recipes to magma crucible")
			public boolean addSpecialMagmaCrucibleRecipes = true;
			@Config.Comment("Add pyrotheum as heat source")
			public boolean addPyrotheumHeatSource = true;
		}
		
		@Config.Comment("Config Settings for Industrial Craft 2")
		public IndustrialCraftSettings industrialCraftSettings = new IndustrialCraftSettings();

		public class IndustrialCraftSettings
		{
			@Config.Comment("Add rubber life infusion and combustion recipes")
			public boolean addRubberRecipes = true;
		}
		
		@Config.Comment("Config Settings for Rock Candy")
		public RockCandySettings rockCandySettings = new RockCandySettings();

		public class RockCandySettings
		{
			@Config.Comment("Add rock candy combustion recipe")
			public boolean addRockCandyRecipe = true;
		}
	}

	@Config.Comment("Config Settings for the Alchemical Ore Rarity values. Higher numbers are rarer.")
	public static Map<String, Integer> alchemicalOreRarities = ItemOreAlchDust.defaultOreRarities();

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if (event.getModID().equals(References.ModID))
		{
			ConfigManager.sync(References.ModID, Config.Type.INSTANCE);
		}
	}
}
