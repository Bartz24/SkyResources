package com.bartz24.skyresources.registry;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.alchemy.fluid.FluidRegisterInfo;
import com.bartz24.skyresources.alchemy.fluid.FluidRegisterInfo.CrystalFluidType;
import com.bartz24.skyresources.config.ConfigOptions;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModFluids
{
	public static List<Fluid> crystalFluids;

	private static List<FluidRegisterInfo> crystalFluidInfos;

	private static int curIndex = 0;

	public static void init()
	{
		crystalFluidInfos = new ArrayList();

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
		ModFluids.addCrystalFluid("quartz", 0xFFFFFFFF, 2, CrystalFluidType.MOLTEN);
		ModFluids.addCrystalFluid("cobalt", 0xFF0045D9, 7, CrystalFluidType.MOLTEN);
		ModFluids.addCrystalFluid("ardite", 0xFFDE9000, 7, CrystalFluidType.MOLTEN);
		ModFluids.addCrystalFluid("osmium", 0xFF7F13C2, 5, CrystalFluidType.NORMAL);
		ModFluids.addCrystalFluid("lapis", 0xFF075BBA, 6, CrystalFluidType.NORMAL);
		ModFluids.addCrystalFluid("draconium", 0xFF9E6DCF, 10,
				ConfigOptions.draconiumType == 0 ? CrystalFluidType.NORMAL : CrystalFluidType.MOLTEN);
		ModFluids.addCrystalFluid("certus", 0xFFB0F4F7, 5, CrystalFluidType.NORMAL);

		registerCrystalFluid();
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
		return null;
	}

	public static void addCrystalFluid(String name, int color, int rarity, CrystalFluidType type)
	{
		crystalFluidInfos.add(new FluidRegisterInfo(name, color, rarity, curIndex, type));
		curIndex++;
	}

	private static void registerCrystalFluid()
	{
		crystalFluids = new ArrayList<Fluid>();
		for (int i = 0; i < crystalFluidInfos().length; i++)
		{
			String type = (crystalFluidInfos()[i].type == CrystalFluidType.MOLTEN ? "molten" : "") + "crystalfluid";
			final int val = i;
			Fluid fluid = new Fluid(crystalFluidInfos()[i].name + type, getStill("blocks/"+ type + "_still"),
					getFlowing("blocks/"+ type + "_flow"))
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
}
