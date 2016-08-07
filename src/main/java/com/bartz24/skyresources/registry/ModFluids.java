package com.bartz24.skyresources.registry;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.alchemy.fluid.FluidRegisterInfo;
import com.bartz24.skyresources.alchemy.fluid.FluidRegisterInfo.CrystalFluidType;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModFluids
{
	public static List<Fluid> crystalFluids;
	public static List<Fluid> dirtyCrystalFluids;
	public static List<Fluid> moltenCrystalFluids;

	private static List<FluidRegisterInfo> crystalFluidInfos;
	private static List<FluidRegisterInfo> moltenCrystalFluidInfos;

	private static int curIndex = 0;
	public static void init()
	{
		crystalFluidInfos = new ArrayList();
		moltenCrystalFluidInfos = new ArrayList();

		ModFluids.addCrystalFluid("iron", 0xFFCC0000, 4, CrystalFluidType.NORMAL);
		ModFluids.addCrystalFluid("gold", 0xFFCCCC00, 6, CrystalFluidType.NORMAL);
		ModFluids.addCrystalFluid("copper", 0xFFFF6600, 2, CrystalFluidType.NORMAL);
		ModFluids.addCrystalFluid("tin", 0xFFBFBFBF, 4, CrystalFluidType.NORMAL);
		ModFluids.addCrystalFluid("silver", 0xFFD1F4FF, 5, CrystalFluidType.NORMAL);
		ModFluids.addCrystalFluid("zinc", 0xFFFFF7C2, 3, CrystalFluidType.NORMAL);
		ModFluids.addCrystalFluid("nickel", 0xFFFAF191, 6, CrystalFluidType.NORMAL);
		ModFluids.addCrystalFluid("platinum", 0xFF44EAFC, 8, CrystalFluidType.NORMAL);
		ModFluids.addCrystalFluid("aluminum", 0xFFF5FFFD, 4, CrystalFluidType.NORMAL);
		ModFluids.addCrystalFluid("lead", 0xFF5B2EFF, 5, CrystalFluidType.NORMAL);
		ModFluids.addCrystalFluid("mercury", 0xFFD1DCDE, 6, CrystalFluidType.NORMAL);
		ModFluids.addCrystalFluid("quartz", 0xFFFFFFFF, 4, CrystalFluidType.MOLTEN);
		ModFluids.addCrystalFluid("cobalt", 0xFF0045D9, 7, CrystalFluidType.MOLTEN);
		ModFluids.addCrystalFluid("ardite", 0xFFDE9000, 7, CrystalFluidType.MOLTEN);
		ModFluids.addCrystalFluid("adamantine", 0xFF363636, 8, CrystalFluidType.MOLTEN);
		ModFluids.addCrystalFluid("coldiron", 0xFF84EAF0, 6, CrystalFluidType.MOLTEN);
		ModFluids.addCrystalFluid("osmium", 0xFF7F13C2, 5, CrystalFluidType.NORMAL);
		ModFluids.addCrystalFluid("lapis", 0xFF075BBA, 6, CrystalFluidType.NORMAL);

		registerCrystalFluid();
		registerDirtyCrystalFluid();
		registerMoltenCrystalFluid();
	}
	
	public static FluidRegisterInfo getFluidInfo(int index)
	{
		for (FluidRegisterInfo f : ModFluids.crystalFluidInfos())
		{
			if (f.crystalIndex == index)
			{
				return f;
			}
		}
		for (FluidRegisterInfo f : ModFluids.moltenCrystalFluidInfos())
		{
			if (f.crystalIndex == index)
			{
				return f;
			}
		}
		return null;
	}

	public static void addCrystalFluid(String name, int color, int rarity, CrystalFluidType type)
	{
		if (type == CrystalFluidType.NORMAL)
			crystalFluidInfos.add(new FluidRegisterInfo(name, color, rarity, curIndex));
		else if (type == CrystalFluidType.MOLTEN)
			moltenCrystalFluidInfos.add(new FluidRegisterInfo(name, color, rarity, curIndex));
		else
			return;
		curIndex++;
	}

	private static void registerCrystalFluid()
	{
		crystalFluids = new ArrayList<Fluid>();
		for (int i = 0; i < crystalFluidInfos().length; i++)
		{
				final int val = i;
				Fluid fluid = new Fluid(crystalFluidInfos()[i].name + "crystalfluid",
						getStill("blocks/crystalfluid_still"),
						getFlowing("blocks/crystalfluid_flow"))
				{
					@Override
					public int getColor()
					{
						return crystalFluidInfos()[val].color;
					}
				};
				crystalFluids.add(fluid);
				FluidRegistry.addBucketForFluid(crystalFluids.get(i));
			}
	}

	private static void registerDirtyCrystalFluid()
	{
		dirtyCrystalFluids = new ArrayList<Fluid>();
		for (int i = 0; i < crystalFluidInfos().length; i++)
		{
				final int val = i;
				Fluid fluid = new Fluid(crystalFluidInfos()[i].name + "dirtycrystalfluid",
						getStill("blocks/dirtycrystalfluid_still"),
						getFlowing("blocks/dirtycrystalfluid_flow"))
				{
					@Override
					public int getColor()
					{
						return crystalFluidInfos()[val].color;
					}
				};
				dirtyCrystalFluids.add(fluid);
				FluidRegistry.addBucketForFluid(dirtyCrystalFluids.get(i));
			}
	}

	private static void registerMoltenCrystalFluid()
	{
		moltenCrystalFluids = new ArrayList<Fluid>();
		for (int i = 0; i < moltenCrystalFluidInfos().length; i++)
		{
				final int val = i;
				Fluid fluid = new Fluid(moltenCrystalFluidInfos()[i].name + "moltencrystalfluid",
						getStill("blocks/moltencrystalfluid_still"),
						getFlowing("blocks/moltencrystalfluid_flow"))
				{
					@Override
					public int getColor()
					{
						return moltenCrystalFluidInfos()[val].color;
					}
				};
				moltenCrystalFluids.add(fluid);
				FluidRegistry.addBucketForFluid(moltenCrystalFluids.get(i));
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

	public static FluidRegisterInfo[] crystalFluidInfos()
	{
		return crystalFluidInfos.toArray(new FluidRegisterInfo[crystalFluidInfos.size()]);
	}

	public static FluidRegisterInfo[] moltenCrystalFluidInfos()
	{
		return moltenCrystalFluidInfos.toArray(new FluidRegisterInfo[moltenCrystalFluidInfos.size()]);
	}
}
