package com.bartz24.skyresources;

import org.apache.logging.log4j.Logger;

import com.bartz24.skyresources.base.commands.PlatformCommand;
import com.bartz24.skyresources.proxy.CommonProxy;
import com.bartz24.skyresources.waila.WailaPlugin;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = References.ModID, name = References.ModName, useMetadata = true)
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
	public void serverLoading(FMLServerStartingEvent event)
	{
		logger.info("Registering Sky Resources commands.");
		event.registerServerCommand(new PlatformCommand());
		logger.info("Finished registering Sky Resources commands.");

		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
		{
			References.CurrentIslandsList.clear();
			References.spawnedPlayers.clear();
			References.worldOneChunk = false;
			World world = event.getServer().getEntityWorld();
			if (!world.isRemote)
			{
				SkyResourcesSaveData worldData = (SkyResourcesSaveData) world
						.loadItemData(SkyResourcesSaveData.class,
								SkyResourcesSaveData.dataName);
				
				if (worldData == null)
				{
					worldData = new SkyResourcesSaveData(
							SkyResourcesSaveData.dataName);
					world.setItemData(SkyResourcesSaveData.dataName, worldData);
				}
				
				SkyResourcesSaveData.setInstance(world.provider.getDimension(),
						worldData);
			}
		}
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
		proxy.preInit(event);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		if (Loader.isModLoaded("Waila"))
		{
			WailaPlugin.initialize();
		}
		proxy.init(event);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit(event);
	}
}
