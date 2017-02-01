package com.bartz24.skyresources.plugin;

import java.util.ArrayList;

import com.bartz24.skyresources.plugin.armorplus.ArmorPlusPlugin;
import com.bartz24.skyresources.plugin.environmentaltech.EnvTechPlugin;
import com.bartz24.skyresources.plugin.forestry.ForestryPlugin;
import com.bartz24.skyresources.plugin.techreborn.TechRebornPlugin;

import net.minecraftforge.fml.common.Loader;

public class ModPlugins
{
	public static ArrayList<IModPlugin> plugins = new ArrayList<IModPlugin>();

	public static void preInit()
	{
		if (Loader.isModLoaded("forestry"))
			plugins.add(new ForestryPlugin());
		if (Loader.isModLoaded("techreborn"))
			plugins.add(new TechRebornPlugin());
		if (Loader.isModLoaded("environmentaltech"))
			plugins.add(new EnvTechPlugin());
		if (Loader.isModLoaded("armorplus"))
			plugins.add(new ArmorPlusPlugin());

		for (IModPlugin p : plugins)
		{
			p.preInit();
		}
		/*
		 * if (Loader.isModLoaded("IC2")) IC2Plugin.preInit(); if
		 * (Loader.isModLoaded("tconstruct")) TConPlugin.preInit(); if
		 * (Loader.isModLoaded("EnderIO")) EnderIOPlugin.preInit(); if
		 * (Loader.isModLoaded("quantumflux")) QFluxPlugin.preInit(); if
		 * (Loader.isModLoaded("appliedenergistics2")) AE2Plugin.preInit();
		 */
	}

	public static void init()
	{
		for (IModPlugin p : plugins)
		{
			p.init();
		}
	}

	public static void postInit()
	{
		for (IModPlugin p : plugins)
		{
			p.postInit();
		}
	}

	public static void initRenderers()
	{
		for (IModPlugin p : plugins)
		{
			p.initRenderers();
		}
	}
}
