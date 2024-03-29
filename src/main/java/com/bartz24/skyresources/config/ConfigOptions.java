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
		public Map<String, Double> machineSpeed = defaultMachineSpeeds();
		@Config.Comment("Set Machine Efficiencies")
		public Map<String, Double> machineEfficiency = defaultMachineEfficiencies();
		@Config.Comment("Set Machine Max HU")
		public Map<String, Integer> machineMaxHU = defaultMachineMaxHUs();

		private Map<String, Double> defaultMachineSpeeds()
		{
			Map<String, Double> map = new HashMap();
			for (MachineVariants v : MachineVariants.values())
			{
				map.put(v.getName(), (double) v.getDefaultRawSpeed());
			}
			return map;
		}

		private Map<String, Double> defaultMachineEfficiencies()
		{
			Map<String, Double> map = new HashMap();
			for (MachineVariants v : MachineVariants.values())
			{
				map.put(v.getName(), (double) v.getDefaultRawEfficiency());
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
		public double knifeBaseDamage = 1.5f;
		@Config.Comment("Multiplier of knife's material durability")
		public double knifeBaseDurability = 0.7f;

		@Config.Comment("Multiplier of rock grinder's material damage")
		public double rockGrinderBaseDamage = 2.5f;
		@Config.Comment("Multiplier of rock grinder's material durability")
		public double rockGrinderBaseDurability = 0.8f;

		@Config.Comment("Max amount in Water Extractor")
		public int waterExtractorCapacity = 4000;

		@Config.Comment("Max Health a Health Gem can store")
		public int healthGemMaxHealth = 100;
		@Config.Comment("Percent of health in Health Gem to add to player's health")
		public double healthGemPercentage = 0.02f;

		@Config.Comment("Multiplier of infusion stone's material durability")
		public double infusionStoneBaseDurability = 1f;
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
		public double miniFreezerSpeed = 0.25f;
		@Config.Comment("Iron Freezer Speed")
		public double ironFreezerSpeed = 1f;
		@Config.Comment("Light Freezer Speed")
		public double lightFreezerSpeed = 100f;
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
		@Config.Comment("Wildlife Attractor RF Rate")
		public int wildlifeAttractorPowerUsage = 40;
		@Config.Comment("Wildlife Attractor Water Usage Rate")
		public int wildlifeAttractorWaterUsage = 20;
		@Config.Comment("Wildlife Attractor Plant Matter Usage Time")
		public int wildlifeAttractorMatterTime = 320;
		@Config.Comment("Wildlife Attractor Water Capacity")
		public int wildlifeAttractorWaterCapacity = 4000;
		@Config.Comment("Wildlife Attractor Animal Ids")
		public String[] wildlifeAttractorAnimalIDs = new String[] { "minecraft:sheep", "minecraft:cow",
				"minecraft:chicken", "minecraft:pig", "minecraft:rabbit", "minecraft:squid", "minecraft:horse",
				"minecraft:parrot" };

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
		@Config.Comment("Mod priorities for materials like ingots and dusts")
		public String[] modResourcePriorities = new String[] { "minecraft", "thermalfoundation", "immersiveengineering", "mekanism",
				"tconstruct", "ic2", "techreborn", "forestry", "embers" };
	}

	@Config.Comment("Config Settings for plugins")
	public static PluginSettings pluginSettings = new PluginSettings();

	public static class PluginSettings
	{
		@Config.Comment("Config Settings for Actually Additions")
		public ActuallyAdditionsSettings actuallyAdditionsSettings = new ActuallyAdditionsSettings();

		public class ActuallyAdditionsSettings
		{
			@Config.Comment("Disable the Actually Additions plugin from loading")
			public boolean disableActuallyAdditionsPlugin = false;
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
			@Config.Comment("Disable the Applied Energistics plugin from loading")
			public boolean disableAppliedEnergisticsPlugin = false;
			@Config.Comment("Add inscriber press combustion recipes")
			public boolean addPressRecipes = true;
		}

		@Config.Comment("Config Settings for Armor Plus")
		public ArmorPlusSettings armorPlusSettings = new ArmorPlusSettings();

		public class ArmorPlusSettings
		{
			@Config.Comment("Disable the Armor Plus plugin from loading")
			public boolean disableArmorPlusPlugin = false;
			@Config.Comment("Add lava crystal combustion recipe")
			public boolean addLavaCrystalRecipe = true;
		}

		@Config.Comment("Config Settings for Embers")
		public EmbersSettings embersSettings = new EmbersSettings();

		public class EmbersSettings
		{
			@Config.Comment("Disable the Embers plugin from loading")
			public boolean disableEmbersPlugin = false;
			@Config.Comment("Add ember shard combustion recipe")
			public boolean addEmberShardRecipe = true;
		}

		@Config.Comment("Config Settings for Forestry")
		public ForestrySettings forestrySettings = new ForestrySettings();

		public class ForestrySettings
		{
			@Config.Comment("Disable the Forestry plugin from loading")
			public boolean disableForestryPlugin = false;
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
			@Config.Comment("Disable the Integrated Dynamics plugin from loading")
			public boolean disableIntegratedDynamicsPlugin = false;
			@Config.Comment("Add menril berry/sapling life infusion recipes")
			public boolean addMenrilRecipes = true;
		}

		@Config.Comment("Config Settings for Tinkers Construct")
		public TinkersConstructSettings tinkersConstructSettings = new TinkersConstructSettings();

		public class TinkersConstructSettings
		{
			@Config.Comment("Disable the Tinkers Construct plugin from loading")
			public boolean disableTinkersConstructPlugin = false;
			@Config.Comment("Add slime ball/sapling life infusion and combustion recipes")
			public boolean addSlimeRecipes = true;
		}

		@Config.Comment("Config Settings for Tech Reborn")
		public TechRebornSettings techRebornSettings = new TechRebornSettings();

		public class TechRebornSettings
		{
			@Config.Comment("Disable the Tech Reborn plugin from loading")
			public boolean disableTechRebornPlugin = false;
			@Config.Comment("Add rubber life infusion and combustion recipes")
			public boolean addRubberRecipes = true;
		}

		@Config.Comment("Config Settings for Thermal Expansion")
		public ThermalExpansionSettings thermalExpansionSettings = new ThermalExpansionSettings();

		public class ThermalExpansionSettings
		{
			@Config.Comment("Disable the Thermal Expansion plugin from loading")
			public boolean disableThermalExpansionPlugin = false;
			@Config.Comment("Add crystal fluid and lava recipes to magma crucible")
			public boolean addSpecialMagmaCrucibleRecipes = true;
			@Config.Comment("Add pyrotheum as heat source")
			public boolean addPyrotheumHeatSource = true;
		}

		@Config.Comment("Config Settings for Industrial Craft 2")
		public IndustrialCraftSettings industrialCraftSettings = new IndustrialCraftSettings();

		public class IndustrialCraftSettings
		{
			@Config.Comment("Disable the Industrial Craft plugin from loading")
			public boolean disableIndustrialCraftPlugin = false;
			@Config.Comment("Add rubber life infusion and combustion recipes")
			public boolean addRubberRecipes = true;
		}

		@Config.Comment("Config Settings for Rock Candy")
		public RockCandySettings rockCandySettings = new RockCandySettings();

		public class RockCandySettings
		{
			@Config.Comment("Disable the Rock Candy plugin from loading")
			public boolean disableRockCandyPlugin = false;
			@Config.Comment("Add rock candy combustion recipe")
			public boolean addRockCandyRecipe = true;
		}

		@Config.Comment("Config Settings for Void Island Control")
		public VoidIslandControlSettings voidIslandControlSettings = new VoidIslandControlSettings();

		public class VoidIslandControlSettings
		{
			@Config.Comment("Disable the Void Island Control plugin from loading")
			public boolean disableVoidIslandControlPlugin = false;
			@Config.Comment("Enable the special Void Island Control magma island")
			public boolean enableMagmaIsland = true;
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
