package com.bartz24.skyresources.registry;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.References;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import scala.collection.concurrent.Debug;

public class ModFluids
{
	public static List<Fluid> crystalFluids;
	public static List<Fluid> dirtyCrystalFluids;
	
	static List<String> crystalFluidNames;
	static List<Integer> crystalFluidColors;
	static List<Integer> crystalFluidRarities;
	
	public static void init()
	{
		crystalFluidNames = new ArrayList<String>();
		crystalFluidColors = new ArrayList<Integer>();
		crystalFluidRarities = new ArrayList<Integer>();
		
		ModFluids.addCrystalFluid("iron", 0xFFCC0000, 4);
		ModFluids.addCrystalFluid("gold", 0xFFCCCC00, 6);
		ModFluids.addCrystalFluid("copper", 0xFFFF6600, 2);
		ModFluids.addCrystalFluid("tin", 0xFFBFBFBF, 4);
		ModFluids.addCrystalFluid("silver", 0xFFD1F4FF, 5);
		ModFluids.addCrystalFluid("zinc", 0xFFFFF7C2, 3);
		ModFluids.addCrystalFluid("nickel", 0xFFFAF191, 6);
		ModFluids.addCrystalFluid("platinum", 0xFF44EAFC, 8);
		ModFluids.addCrystalFluid("aluminum", 0xFFF5FFFD, 4);
		ModFluids.addCrystalFluid("lead", 0xFF5B2EFF , 5);
		ModFluids.addCrystalFluid("mercury", 0xFFD1DCDE , 6);
	}
	
	public static void addCrystalFluid(String name, int color, int rarity)
	{
		crystalFluidNames.add(name);
		crystalFluidColors.add(color);
		crystalFluidRarities.add(rarity);
	}

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
			FluidRegistry.addBucketForFluid(crystalFluids.get(i));
		}
	}
	
	public static void registerDirtyCrystalFluid()
	{
		dirtyCrystalFluids = new ArrayList<Fluid>();
		for (int i = 0; i < crystalFluidNames().length; i++)
		{
			final int val = i;
			Fluid fluid = new Fluid(crystalFluidNames()[i] + "dirtycrystalfluid",
					getStill("blocks/dirtycrystalfluid_still"),
					getFlowing("blocks/dirtycrystalfluid_flow"))
			{
				@Override
				public int getColor()
				{
					return crystalFluidColors()[val];
				}
			};
			dirtyCrystalFluids.add(fluid);
			FluidRegistry.addBucketForFluid(dirtyCrystalFluids.get(i));
			
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
		return crystalFluidNames.toArray(new String[crystalFluidNames.size()]);
	}

	public static Integer[] crystalFluidColors()
	{
		return crystalFluidColors.toArray(new Integer[crystalFluidColors.size()]);
	}

	public static Integer[] crystalFluidRarity()
	{
		return crystalFluidRarities.toArray(new Integer[crystalFluidColors.size()]);
	}
}
