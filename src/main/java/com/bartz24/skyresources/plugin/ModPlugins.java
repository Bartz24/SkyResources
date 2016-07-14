package com.bartz24.skyresources.plugin;

import com.bartz24.skyresources.plugin.armorplus.ArmorPlusPlugin;
import com.bartz24.skyresources.plugin.forestry.ForestryPlugin;
import com.bartz24.skyresources.plugin.ic2.IC2Plugin;
import com.bartz24.skyresources.plugin.randomthings.RandomThingsPlugin;
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
			if (Loader.isModLoaded("randomthings"))
				RandomThingsPlugin.preInit();
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
		if (Loader.isModLoaded("randomthings"))
			RandomThingsPlugin.init();
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
		if (Loader.isModLoaded("randomthings"))
			RandomThingsPlugin.postInit();
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
		if (Loader.isModLoaded("randomthings"))
			RandomThingsPlugin.initRenderers();
	}
}
