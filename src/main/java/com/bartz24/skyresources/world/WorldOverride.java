package com.bartz24.skyresources.world;

import com.bartz24.skyresources.SkyResources;
import com.bartz24.skyresources.config.ConfigOptions;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class WorldOverride
{
	public static void registerWorldProviders()
	{
		if (ConfigOptions.netherVoid)
		{
			try
			{
				DimensionManager.unregisterDimension(-1);
				DimensionManager.registerDimension(-1, DimensionType.register("VoidNether",
						"_nether", -1, WorldProviderNetherVoid.class, true));
			} catch (Exception e)
			{
				SkyResources.logger.error("Could not override the nether dimension to be void!");
			}
		}
	}
}
