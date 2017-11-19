package com.bartz24.skyresources.plugin;

import java.util.HashMap;
import java.util.Map;

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

import net.minecraftforge.fml.common.Loader;

public class ModPlugins
{
	public static Map<String, IModPlugin> plugins = new HashMap();

	public static void preInit()
	{
		addPlugin("actuallyadditions", ActAddPlugin.class);
		addPlugin("appliedenergistics2", AE2Plugin.class);
		addPlugin("armorplus", ArmorPlusPlugin.class);
		addPlugin("crafttweaker", CraftTweakerPlugin.class);
		addPlugin("draconicevolution", DEPlugin.class);
		addPlugin("embers", EmbersPlugin.class);
		// addPlugin("environmentaltech", new EnvTechPlugin());
		addPlugin("extrabees", ExtraBeesPlugin.class);
		addPlugin("bigreactors", ExtremeReactorsPlugin.class);
		addPlugin("forestry", ForestryPlugin.class);
		addPlugin("ic2", IC2Plugin.class);
		addPlugin("integrateddynamics", IntegratedDynamicsPlugin.class);
		addPlugin("rockcandy", RockCandyPlugin.class);
		addPlugin("techreborn", TechRebornPlugin.class);
		addPlugin("tconstruct", TConPlugin.class);
		addPlugin("theoneprobe", TOPPlugin.class);
		addPlugin("thermalfoundation", ThermalPlugin.class);

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

	public static void addPlugin(String modID, Class plugin)
	{
		if (Loader.isModLoaded(modID))
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
