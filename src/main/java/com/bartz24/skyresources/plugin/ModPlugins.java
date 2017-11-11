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
		addPlugin("actuallyadditions", new ActAddPlugin());
		addPlugin("appliedenergistics2", new AE2Plugin());
		addPlugin("armorplus", new ArmorPlusPlugin());
		addPlugin("crafttweaker", new CraftTweakerPlugin());
		addPlugin("draconicevolution", new DEPlugin());
		addPlugin("embers", new EmbersPlugin());
		//addPlugin("environmentaltech", new EnvTechPlugin());
		addPlugin("extrabees", new ExtraBeesPlugin());
		addPlugin("bigreactors", new ExtremeReactorsPlugin());
		addPlugin("forestry", new ForestryPlugin());
		addPlugin("ic2", new IC2Plugin());
		addPlugin("integrateddynamics", new IntegratedDynamicsPlugin());
		addPlugin("rockcandy", new RockCandyPlugin());
		addPlugin("techreborn", new TechRebornPlugin());
		addPlugin("tconstruct", new TConPlugin());
		addPlugin("theoneprobe", new TOPPlugin());
		addPlugin("thermalfoundation", new ThermalPlugin());

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
	
	public static void addPlugin(String modID, IModPlugin plugin)
	{
		if (Loader.isModLoaded(modID))
			plugins.put(modID, plugin);		
	}
}
