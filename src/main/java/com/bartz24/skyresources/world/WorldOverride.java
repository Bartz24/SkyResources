package com.bartz24.skyresources.world;

import com.bartz24.skyresources.config.ConfigOptions;

import ic2.core.WorldData;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProviderHell;
import net.minecraftforge.common.DimensionManager;

public class WorldOverride
{
	public static void registerWorldProviders()
	{
		if (ConfigOptions.netherVoid)
		{
			DimensionManager.unregisterDimension(-1);
			DimensionManager.registerDimension(-1, DimensionType.register("Nether", "_nether", -1,
					WorldProviderNetherVoid.class, true));
		}
	}
}
