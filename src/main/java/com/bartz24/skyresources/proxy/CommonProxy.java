package com.bartz24.skyresources.proxy;

import com.bartz24.skyresources.SkyResources;
import com.bartz24.skyresources.alchemy.infusion.InfusionRecipes;
import com.bartz24.skyresources.base.HeatSources;
import com.bartz24.skyresources.events.EventHandler;
import com.bartz24.skyresources.events.ModBucketHandler;
import com.bartz24.skyresources.registry.ModBlocks;
import com.bartz24.skyresources.registry.ModCrafting;
import com.bartz24.skyresources.registry.ModEntities;
import com.bartz24.skyresources.registry.ModFluids;
import com.bartz24.skyresources.registry.ModGuiHandler;
import com.bartz24.skyresources.registry.ModItems;
import com.bartz24.skyresources.technology.combustion.CombustionRecipes;
import com.bartz24.skyresources.world.WorldTypeSky;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy
{
	EventHandler events = new EventHandler();
	public void preInit(FMLPreInitializationEvent e)
	{
		ModFluids.registerCrystalFluid();
		ModBlocks.init();
		ModItems.init();
		
		new HeatSources();
		new InfusionRecipes();
		new CombustionRecipes();
		new WorldTypeSky();

		MinecraftForge.EVENT_BUS.register(events);
		ModBucketHandler.registerBuckets();
		MinecraftForge.EVENT_BUS.register(new ModBucketHandler());
	}

	public void init(FMLInitializationEvent e)
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(SkyResources.instance, new ModGuiHandler());
		ModEntities.init();
		ModCrafting.init();		
	}

	public void postInit(FMLPostInitializationEvent e)
	{

	}
}
