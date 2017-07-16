package com.bartz24.skyresources;

import org.apache.logging.log4j.Logger;

import com.bartz24.skyresources.proxy.CommonProxy;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = References.ModID, name = References.ModName, version = References.Version, useMetadata = true, guiFactory = "com.bartz24.skyresources.config.ConfigGuiFactory")
public class SkyResources
{
	@SidedProxy(clientSide = "com.bartz24.skyresources.proxy.ClientProxy", serverSide = "com.bartz24.skyresources.proxy.ServerProxy")
	public static CommonProxy proxy;

	@Mod.Instance
	public static SkyResources instance;

	public static Logger logger;

	public static ToolMaterial materialCactusNeedle = EnumHelper
			.addToolMaterial("CACTUSNEEDLE", 0, 4, 5, 1, 5);

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
		proxy.preInit(event);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init(event);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit(event);
	}

	public SkyResources()
	{
		FluidRegistry.enableUniversalBucket();
	}
}
