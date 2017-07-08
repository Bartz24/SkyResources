package com.bartz24.skyresources.registry;

import com.bartz24.skyresources.References;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModFluids
{
	public static Fluid crystalFluid;

	public static void init()
	{
		registerCrystalFluid();
	}

	private static void registerCrystalFluid()
	{
		crystalFluid = new Fluid("srCrystalFluid", getStill("blocks/crystalFluid_still"),
				getFlowing("blocks/crystalFluid_flow"));
		FluidRegistry.addBucketForFluid(crystalFluid);
	}

	public static ResourceLocation getStill(String name)
	{
		return new ResourceLocation(References.ModID, name);
	}

	public static ResourceLocation getFlowing(String name)
	{
		return new ResourceLocation(References.ModID, name);
	}
}
