package com.bartz24.skyresources.base;

import java.util.Locale;

import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.registry.ModItems;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fluids.FluidRegistry;

public enum MachineVariants implements IStringSerializable
{
	WOOD(100, 0.5f, 0.4f, "Fuel", 10), 
	STONE(600, 1.0f, 0.8f, "Fuel", 10), 
	BRONZE(950, 1.4f, 1.2f, "RF", 10), 
	IRON(1538, 1.2f, 1.0f, "Fuel", 10), 
	STEEL(1370, 1.7f, 1.4f, "Fuel", 10), 
	ELECTRUM(1878, 1.3f, 2.4f, "RF", 10), 
	NETHERBRICK(3072, 0.4f, 1.6f, new ItemStack(Items.BLAZE_POWDER), 300), 
	LEAD(328, 0.9f, 2.9f, new ItemStack(ModItems.techComponent, 1, 1), 6000), 
	MANYULLYN(2324, 3.6f, 1.9f, FluidRegistry.LAVA, 1), 
	SIGNALUM(1362, 1.8f, 3.6f, "RF", 10), 
	ENDSTONE(2164, 6.6f, 6.6f, new ItemStack(Items.ENDER_PEARL), 2200), 
	ENDERIUM(3166, 6.8f, 8.0f, "RF", 10), 
	DARKMATTER(4042, 1f, 100f, new ItemStack(ModItems.baseComponent, 1, 5), 31415), 
	LIGHTMATTER(1566, 100f,	1f, new ItemStack(ModItems.baseComponent, 1, 7), 27183);

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
		return ConfigOptions.modularMachineSettings.machineMaxHU.get(getName());
	}

	public float getRawEfficiency()
	{
		return (float) (double) ConfigOptions.modularMachineSettings.machineEfficiency.get(getName());
	}

	public float getRawSpeed()
	{
		return (float) (double) ConfigOptions.modularMachineSettings.machineSpeed.get(getName());
	}

	public Object getFuelType()
	{
		return fuelType;
	}

	public int getRawFuelRate()
	{
		return fuelRate;
	}

	public int getDefaultMaxHeat()
	{
		return maxHeat;
	}

	public float getDefaultRawEfficiency()
	{
		return efficiency;
	}

	public float getDefaultRawSpeed()
	{
		return speed;
	}

	public void setFuel(Object fuelType, int fuelRate)
	{
		this.fuelType = fuelType;
		this.fuelRate = fuelRate;
	}

}
