package com.bartz24.skyresources.plugin;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.plugin.armorplus.ArmorPlusPlugin;
import com.bartz24.skyresources.plugin.forestry.ForestryPlugin;
import com.bartz24.skyresources.plugin.ic2.IC2Plugin;
import com.bartz24.skyresources.plugin.techreborn.TechRebornPlugin;

import net.minecraftforge.fml.common.Loader;

public class ModPlugins
{
	public static List<IModPlugin> plugins = new ArrayList();

	public static void preInit()
	{		
		plugins.add(new ForestryPlugin());
		plugins.add(new IC2Plugin());
		plugins.add(new TechRebornPlugin());
		plugins.add(new ArmorPlusPlugin());
		
		for (IModPlugin plugin : plugins)
		{
			if(Loader.isModLoaded(plugin.getModID()))
			plugin.preInit();
		}
	}

	public static void init()
	{

		for (IModPlugin plugin : plugins)
		{
			if(Loader.isModLoaded(plugin.getModID()))
			plugin.init();
		}
	}

	public static void postInit()
	{

		for (IModPlugin plugin : plugins)
		{
			if(Loader.isModLoaded(plugin.getModID()))
			plugin.postInit();
		}
	}

	public static void initRenderers()
	{

		for (IModPlugin plugin : plugins)
		{
			if(Loader.isModLoaded(plugin.getModID()))
			plugin.initRenderers();
		}
	}
}
