package com.bartz24.skyresources.base;

import java.util.Locale;

import com.bartz24.skyresources.registry.ModItems;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public enum MachineVariants implements IStringSerializable
{
	WOOD(100, 0.5f, 0.4f, "Fuel", 75), 
	STONE(600, 1.0f, 0.8f, "Fuel", 90), 
	BRONZE(950, 1.4f, 1.2f, "FE", 20), 
	IRON(1538, 1.2f, 1.0f, "Fuel", 110), 
	STEEL(1370, 1.7f, 1.4f, "Fuel", 140), 
	ELECTRUM(1878, 1.3f, 2.4f, "FE", 60), 
	NETHERBRICK(3072, 0.4f, 0.5f, new ItemStack(Items.BLAZE_POWDER), 300), 
	LEAD(328, 1.1f, 2.9f, new ItemStack(ModItems.techComponent, 1, 1), 6000), 
	MANYULLYN(2324, 3.6f, 1.9f, FluidRegistry.LAVA, 1), 
	SIGNALUM(1362, 1.8f, 3.6f, "FE", 80), 
	ENDSTONE(2164, 6.6f, 6.6f, new ItemStack(Items.ENDER_PEARL), 2200), 
	ENDERIUM(3166, 9.8f, 8.0f, "FE", 1200), 
	DARKMATTER(4042, 100f, 100f, new ItemStack(ModItems.baseComponent, 1, 5), 31415);

	private int maxHeat;
	private float efficiency;
	private float speed;
	private Object fuelType;
	private int fuelRate;

	MachineVariants(int maxHeat, float efficiency, float speed, Object fuelType, int fuelRate)
	{
		this.maxHeat = maxHeat;
		this.efficiency = efficiency;
		this.speed = speed;
		this.fuelType = fuelType;
		this.fuelRate = fuelRate;
	}

	@Override
	public String getName()
	{
		return name().toLowerCase(Locale.ROOT);
	}

	public int getMaxHeat()
	{
		return maxHeat;
	}

	public float getRawEfficiency()
	{
		return efficiency;
	}

	public float getRawSpeed()
	{
		return speed;
	}

	public Object getFuelType()
	{
		return fuelType;
	}

	public int getRawFuelRate()
	{
		return fuelRate;
	}

	public void setFuel(Object fuelType, int fuelRate)
	{
		this.fuelType = fuelType;
		this.fuelRate = fuelRate;
	}

	public int getHeatNum(boolean useFuelInfo, boolean useEfficiencyInfo)
	{
		if (useFuelInfo)
		{
			float rate = getRawFuelRate();
			float eff = useEfficiencyInfo ? getRawEfficiency() : 1;
			if (getFuelType().equals("FE"))
				return (int) (rate / eff);
			else if (getFuelType() instanceof ItemStack || getFuelType() instanceof Fluid
					|| getFuelType().equals("Fuel"))
				return (int) (rate * eff);
		}
		return 0;
	}

	public int getFuelPerTick(boolean useFuelInfo, boolean useSpeedInfo, boolean useEfficiencyInfo)
	{
		if (useFuelInfo)
		{
			float spd = useSpeedInfo ? getRawSpeed() : 1;
			if (getFuelType() instanceof ItemStack || getFuelType() instanceof Fluid)
				return (int) ((float) Math.pow(getHeatNum(useFuelInfo, useEfficiencyInfo), 0.7f) * spd);
			else if (getFuelType().equals("FE"))
				return (int) ((float) getHeatNum(useFuelInfo, useEfficiencyInfo) * 100 * spd);

			return (int) ((float) getHeatNum(useFuelInfo, useEfficiencyInfo) * spd);

		}
		return 0;
	}

	public String getFuelPerHeatDisplay(boolean useFuelInfo, boolean useEfficiencyInfo)
	{
		if (useFuelInfo)
		{
			if (getFuelType().equals("Fuel"))
				return "Furnace Fuels Have " + getHeatNum(useFuelInfo, useEfficiencyInfo) + "% HU";
			else if (getFuelType().equals("FE"))
				return getHeatNum(useFuelInfo, useEfficiencyInfo) + " FE Per HU";
			else if (getFuelType() instanceof ItemStack)
				return ((ItemStack) getFuelType()).getDisplayName() + " gives "
						+ getHeatNum(useFuelInfo, useEfficiencyInfo) + " HU";
			else if (getFuelType() instanceof Fluid)
				return "1 mB " + ((Fluid) getFuelType()).getLocalizedName(new FluidStack((Fluid) getFuelType(), 0))
						+ " gives " + getHeatNum(useFuelInfo, useEfficiencyInfo) + " HU";
		}
		return "";
	}

	public String getFuelPerTickDisplay(boolean useFuelInfo, boolean useSpeedInfo, boolean useEfficiencyInfo)
	{
		if (useFuelInfo)
		{
			if (getFuelType().equals("Fuel"))
				return "Furnace Fuels Used At " + getFuelPerTick(useFuelInfo, useSpeedInfo, useEfficiencyInfo)
						+ "% Rate";
			else if (getFuelType().equals("FE"))
				return getFuelPerTick(useFuelInfo, useSpeedInfo, useEfficiencyInfo) + " FE Per Tick";
			else if (getFuelType() instanceof ItemStack || getFuelType() instanceof Fluid)
				return "Fuel Used for "
						+ ((float) getFuelPerTick(useFuelInfo, useSpeedInfo, useEfficiencyInfo) + " HU Per Tick");
		}
		return "";
	}
}
