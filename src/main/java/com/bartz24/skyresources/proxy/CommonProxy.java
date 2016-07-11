package com.bartz24.skyresources.proxy;

import com.bartz24.skyresources.SkyResources;
import com.bartz24.skyresources.alchemy.infusion.InfusionRecipes;
import com.bartz24.skyresources.base.HeatSources;
import com.bartz24.skyresources.base.guide.SkyResourcesGuide;
import com.bartz24.skyresources.base.waterextractor.WaterExtractorRecipes;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.events.EventHandler;
import com.bartz24.skyresources.events.ModBucketHandler;
import com.bartz24.skyresources.forestry.ForestryPlugin;
import com.bartz24.skyresources.ic2.IC2Plugin;
import com.bartz24.skyresources.minetweaker.MinetweakerPlugin;
import com.bartz24.skyresources.registry.ModAchievements;
import com.bartz24.skyresources.registry.ModBlocks;
import com.bartz24.skyresources.registry.ModCrafting;
import com.bartz24.skyresources.registry.ModEntities;
import com.bartz24.skyresources.registry.ModFluids;
import com.bartz24.skyresources.registry.ModGuiHandler;
import com.bartz24.skyresources.registry.ModGuidePages;
import com.bartz24.skyresources.registry.ModItems;
import com.bartz24.skyresources.technology.combustion.CombustionRecipes;
import com.bartz24.skyresources.technology.concentrator.ConcentratorRecipes;
import com.bartz24.skyresources.technology.freezer.FreezerRecipes;
import com.bartz24.skyresources.technology.rockgrinder.RockGrinderRecipes;
import com.bartz24.skyresources.world.WorldTypeSky;

import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy
{
	EventHandler events = new EventHandler();

	public void preInit(FMLPreInitializationEvent e)
	{
		ConfigOptions.loadConfigThenSave(e);
		ModFluids.init();
		ModFluids.registerCrystalFluid();
		ModFluids.registerDirtyCrystalFluid();
		ModBlocks.init();
		ModItems.init();
		
		if(Loader.isModLoaded("forestry"))
		{
			ForestryPlugin.preInit();
		}
		if(Loader.isModLoaded("IC2"))
		{
			IC2Plugin.preInit();
		}

		new HeatSources();
		new InfusionRecipes();
		new CombustionRecipes();
		new ConcentratorRecipes();
		new RockGrinderRecipes();
		new FreezerRecipes();
		new WaterExtractorRecipes();
		new WorldTypeSky();
		new SkyResourcesGuide();

		ModGuidePages.init();

	}

	public void init(FMLInitializationEvent e)
	{
		MinecraftForge.EVENT_BUS.register(events);
		MinecraftForge.EVENT_BUS.register(new ModBucketHandler());
		NetworkRegistry.INSTANCE.registerGuiHandler(SkyResources.instance,
				new ModGuiHandler());
		ModEntities.init();
		ModCrafting.initOreDict();
		
		if(Loader.isModLoaded("forestry"))
		{
			ForestryPlugin.init();
		}
		if(Loader.isModLoaded("IC2"))
		{
			IC2Plugin.init();
		}

		AchievementPage.registerAchievementPage(ModAchievements.modAchievePage);
	}

	public void postInit(FMLPostInitializationEvent e)
	{
		ModCrafting.init();
		if (Loader.isModLoaded("MineTweaker3"))
			MinetweakerPlugin.postInit(e);
	}
}
