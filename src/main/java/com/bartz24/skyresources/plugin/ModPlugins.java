package com.bartz24.skyresources.plugin;

import java.util.HashMap;
import java.util.Map;

import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.plugin.actuallyadditions.ActAddPlugin;
import com.bartz24.skyresources.plugin.ae2.AE2Plugin;
import com.bartz24.skyresources.plugin.armorplus.ArmorPlusPlugin;
import com.bartz24.skyresources.plugin.ctweaker.CraftTweakerPlugin;
import com.bartz24.skyresources.plugin.dracevo.DEPlugin;
import com.bartz24.skyresources.plugin.embers.EmbersPlugin;
import com.bartz24.skyresources.plugin.extrabees.ExtraBeesPlugin;
import com.bartz24.skyresources.plugin.extremereactors.ExtremeReactorsPlugin;
import com.bartz24.skyresources.plugin.forestry.ForestryPlugin;
import com.bartz24.skyresources.plugin.ic2.IC2Plugin;
import com.bartz24.skyresources.plugin.integdyn.IntegratedDynamicsPlugin;
import com.bartz24.skyresources.plugin.rockcandy.RockCandyPlugin;
import com.bartz24.skyresources.plugin.tconstruct.TConPlugin;
import com.bartz24.skyresources.plugin.techreborn.TechRebornPlugin;
import com.bartz24.skyresources.plugin.theoneprobe.TOPPlugin;
import com.bartz24.skyresources.plugin.thermal.ThermalPlugin;
import com.bartz24.skyresources.plugin.vic.VICPlugin;

import net.minecraftforge.fml.common.Loader;

public class ModPlugins
{
	public static Map<String, IModPlugin> plugins = new HashMap();

	public static void preInit()
	{
		addPlugin("actuallyadditions", ActAddPlugin.class, ConfigOptions.pluginSettings.actuallyAdditionsSettings.disableActuallyAdditionsPlugin);
		addPlugin("appliedenergistics2", AE2Plugin.class, ConfigOptions.pluginSettings.appliedEnergisticsSettings.disableAppliedEnergisticsPlugin);
		addPlugin("armorplus", ArmorPlusPlugin.class, ConfigOptions.pluginSettings.armorPlusSettings.disableArmorPlusPlugin);
		addPlugin("crafttweaker", CraftTweakerPlugin.class, false);
		addPlugin("draconicevolution", DEPlugin.class, false);
		addPlugin("embers", EmbersPlugin.class, ConfigOptions.pluginSettings.embersSettings.disableEmbersPlugin);
		// addPlugin("environmentaltech", new EnvTechPlugin());
		addPlugin("extrabees", ExtraBeesPlugin.class, ConfigOptions.pluginSettings.forestrySettings.disableForestryPlugin);
		addPlugin("bigreactors", ExtremeReactorsPlugin.class, false);
		addPlugin("forestry", ForestryPlugin.class, ConfigOptions.pluginSettings.forestrySettings.disableForestryPlugin);
		addPlugin("ic2", IC2Plugin.class, ConfigOptions.pluginSettings.industrialCraftSettings.disableIndustrialCraftPlugin);
		addPlugin("integrateddynamics", IntegratedDynamicsPlugin.class, ConfigOptions.pluginSettings.integratedDynamicsSettings.disableIntegratedDynamicsPlugin);
		addPlugin("rockcandy", RockCandyPlugin.class, ConfigOptions.pluginSettings.rockCandySettings.disableRockCandyPlugin);
		addPlugin("techreborn", TechRebornPlugin.class, ConfigOptions.pluginSettings.techRebornSettings.disableTechRebornPlugin);
		addPlugin("tconstruct", TConPlugin.class, ConfigOptions.pluginSettings.tinkersConstructSettings.disableTinkersConstructPlugin);
		addPlugin("theoneprobe", TOPPlugin.class, false);
		addPlugin("thermalfoundation", ThermalPlugin.class, ConfigOptions.pluginSettings.thermalExpansionSettings.disableThermalExpansionPlugin);
		addPlugin("voidislandcontrol", VICPlugin.class, ConfigOptions.pluginSettings.voidIslandControlSettings.disableVoidIslandControlPlugin);

		for (IModPlugin p : plugins.values())
		{
			p.preInit();
		}

		/*
		 * if (Loader.isModLoaded("EnderIO")) EnderIOPlugin.preInit(); if
		 * (Loader.isModLoaded("quantumflux")) QFluxPlugin.preInit();
		 */
	}

	public static void init()
	{
		for (IModPlugin p : plugins.values())
		{
			p.init();
		}
	}

	public static void postInit()
	{
		for (IModPlugin p : plugins.values())
		{
			p.postInit();
		}
	}

	public static void initRenderers()
	{
		for (IModPlugin p : plugins.values())
		{
			p.initRenderers();
		}
	}

	public static void addPlugin(String modID, Class plugin, boolean disabled)
	{
		if (Loader.isModLoaded(modID) && !disabled)
		{
			try
			{
				plugins.put(modID, (IModPlugin) plugin.newInstance());
			} catch (InstantiationException e)
			{
				e.printStackTrace();
			} catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
	}
}
