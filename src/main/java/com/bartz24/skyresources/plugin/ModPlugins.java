package com.bartz24.skyresources.plugin;

import com.bartz24.skyresources.plugin.armorplus.ArmorPlusPlugin;
import com.bartz24.skyresources.plugin.enderio.EnderIOPlugin;
import com.bartz24.skyresources.plugin.environmentaltech.EnvTechPlugin;
import com.bartz24.skyresources.plugin.forestry.ForestryPlugin;
import com.bartz24.skyresources.plugin.ic2.IC2Plugin;
import com.bartz24.skyresources.plugin.quantumflux.QFluxPlugin;
import com.bartz24.skyresources.plugin.tconstruct.TConPlugin;
import com.bartz24.skyresources.plugin.techreborn.TechRebornPlugin;

import net.minecraftforge.fml.common.Loader;

public class ModPlugins
{

	public static void preInit()
	{
			if (Loader.isModLoaded("forestry"))
				ForestryPlugin.preInit();
			if (Loader.isModLoaded("IC2"))
				IC2Plugin.preInit();
			if (Loader.isModLoaded("armorplus"))
				ArmorPlusPlugin.preInit();
			if (Loader.isModLoaded("techreborn"))
				TechRebornPlugin.preInit();
			if (Loader.isModLoaded("tconstruct"))
				TConPlugin.preInit();
			if (Loader.isModLoaded("EnvironmentalTech"))
				EnvTechPlugin.preInit();
			if (Loader.isModLoaded("EnderIO"))
				EnderIOPlugin.preInit();
			if (Loader.isModLoaded("quantumflux"))
				QFluxPlugin.preInit();
	}

	public static void init()
	{
		if (Loader.isModLoaded("forestry"))
			ForestryPlugin.init();
		if (Loader.isModLoaded("IC2"))
			IC2Plugin.init();
		if (Loader.isModLoaded("armorplus"))
			ArmorPlusPlugin.init();
		if (Loader.isModLoaded("techreborn"))
			TechRebornPlugin.init();
		if (Loader.isModLoaded("tconstruct"))
			TConPlugin.init();
		if (Loader.isModLoaded("EnvironmentalTech"))
			EnvTechPlugin.init();
		if (Loader.isModLoaded("EnderIO"))
			EnderIOPlugin.init();
		if (Loader.isModLoaded("quantumflux"))
			QFluxPlugin.init();
	}

	public static void postInit()
	{
		if (Loader.isModLoaded("forestry"))
			ForestryPlugin.postInit();
		if (Loader.isModLoaded("IC2"))
			IC2Plugin.postInit();
		if (Loader.isModLoaded("armorplus"))
			ArmorPlusPlugin.postInit();
		if (Loader.isModLoaded("techreborn"))
			TechRebornPlugin.postInit();
		if (Loader.isModLoaded("tconstruct"))
			TConPlugin.postInit();
		if (Loader.isModLoaded("EnvironmentalTech"))
			EnvTechPlugin.postInit();
		if (Loader.isModLoaded("EnderIO"))
			EnderIOPlugin.postInit();
		if (Loader.isModLoaded("quantumflux"))
			QFluxPlugin.postInit();
	}

	public static void initRenderers()
	{
		if (Loader.isModLoaded("forestry"))
			ForestryPlugin.initRenderers();
		if (Loader.isModLoaded("IC2"))
			IC2Plugin.initRenderers();
		if (Loader.isModLoaded("armorplus"))
			ArmorPlusPlugin.initRenderers();
		if (Loader.isModLoaded("techreborn"))
			TechRebornPlugin.initRenderers();
		if (Loader.isModLoaded("tconstruct"))
			TConPlugin.initRenderers();
		if (Loader.isModLoaded("EnvironmentalTech"))
			EnvTechPlugin.initRenderers();
		if (Loader.isModLoaded("EnderIO"))
			EnderIOPlugin.initRenderers();
		if (Loader.isModLoaded("quantumflux"))
			QFluxPlugin.initRenderers();
	}
}
