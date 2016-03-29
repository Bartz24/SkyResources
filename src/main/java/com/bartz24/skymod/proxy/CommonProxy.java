package com.bartz24.skymod.proxy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.bartz24.skymod.SkyMod;
import com.bartz24.skymod.alchemy.infusion.InfusionRecipes;
import com.bartz24.skymod.base.HeatSources;
import com.bartz24.skymod.events.EventHandler;
import com.bartz24.skymod.events.ModBucketHandler;
import com.bartz24.skymod.registry.ModBlocks;
import com.bartz24.skymod.registry.ModCrafting;
import com.bartz24.skymod.registry.ModEntities;
import com.bartz24.skymod.registry.ModFluids;
import com.bartz24.skymod.registry.ModGuiHandler;
import com.bartz24.skymod.registry.ModItems;
import com.bartz24.skymod.technology.combustion.CombustionRecipes;
import com.bartz24.skymod.world.WorldTypeSky;

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
		NetworkRegistry.INSTANCE.registerGuiHandler(SkyMod.instance, new ModGuiHandler());
		ModEntities.init();
		ModCrafting.init();		
	}

	public void postInit(FMLPostInitializationEvent e)
	{

	}
}
