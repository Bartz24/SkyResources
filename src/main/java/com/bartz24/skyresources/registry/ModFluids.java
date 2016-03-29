package com.bartz24.skyresources.registry;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.References;

import scala.actors.threadpool.Arrays;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class ModFluids
{
	public static List<Fluid> crystalFluids;

	public static void registerCrystalFluid()
	{
		crystalFluids = new ArrayList<Fluid>();
		for (int i = 0; i < crystalFluidNames().length; i++)
		{
			final int val = i;
			Fluid fluid = new Fluid(crystalFluidNames()[i] + "crystalfluid",
					getStill("blocks/crystalfluid_still"),
					getFlowing("blocks/crystalfluid_flow"))
			{
				@Override
				public int getColor()
			    {
			        return crystalFluidColors()[val];
			    }
			};
			crystalFluids.add(fluid);
			FluidRegistry.registerFluid(fluid);
		}
	}

	public static ResourceLocation getStill(String name)
	{
		return new ResourceLocation(References.ModID, name);
	}

	public static ResourceLocation getFlowing(String name)
	{
		return new ResourceLocation(References.ModID, name);
	}

	public static String[] crystalFluidNames()
	{
		return new String[]
		{ "iron", "gold", "copper", "tin", "silver", "zinc" };
	}

	public static int[] crystalFluidColors()
	{
		return new int[]
		{ 0xFFCC0000, 0xFFCCCC00, 0xFFFF6600, 0xFFBFBFBF, 0xFFD1F4FF, 0xFFFFF7C2 };
	}

	public static int[] crystalFluidRarity()
	{
		return new int[]
		{ 4, 6, 2, 4, 5, 3 };
	}
}
