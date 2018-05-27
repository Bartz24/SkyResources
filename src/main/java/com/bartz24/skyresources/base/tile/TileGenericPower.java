package com.bartz24.skyresources.base.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileGenericPower extends TileItemInventory implements IEnergyStorage
{

	public TileGenericPower(String name, int maxPower, int maxIn, int maxOut)
	{
		super(name, 0);
		maxEnergy = maxPower;
		maxReceive = maxIn;
		maxExtract = maxOut;
	}

	public TileGenericPower(String name, int maxPower, int maxIn, int maxOut, int invSlots, int[] noInsert,
			int[] noExtract)
	{
		super(name, invSlots, noInsert, noExtract);
		maxEnergy = maxPower;
		maxReceive = maxIn;
		maxExtract = maxOut;
	}

	protected int energy;
	protected int maxEnergy;
	protected int maxReceive;
	protected int maxExtract;

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate)
	{
		int energyReceived = (int) Math.min(getMaxEnergyStored() - energy, Math.min(this.maxReceive, maxReceive));
		if (!simulate)
		{
			energy += energyReceived;
			this.markDirty();
		}
		return energyReceived;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate)
	{
		int energyExtract = (int) Math.min(Math.max(energy, 0), Math.min(this.maxExtract, maxExtract));		
		if (!simulate)
		{
			energy -= energyExtract;
			this.markDirty();
		}
		return energyExtract;
	}

	public int internalExtractEnergy(int extract, boolean simulate)
	{
		int energyExtract = (int) Math.min(Math.max(energy, 0), extract);
		if (!simulate)
		{
			energy -= energyExtract;
			this.markDirty();
		}
		return energyExtract;
	}

	@Override
	public int getEnergyStored()
	{
		return energy;
	}

	@Override
	public int getMaxEnergyStored()
	{
		return maxEnergy;
	}

	@Override
	public boolean canExtract()
	{
		return maxExtract != 0;
	}

	@Override
	public boolean canReceive()
	{
		return maxReceive != 0;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound = super.writeToNBT(compound);

		compound.setInteger("Energy", energy);

		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		energy = compound.getInteger("Energy");
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if (capability == CapabilityEnergy.ENERGY)
		{
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (capability == CapabilityEnergy.ENERGY)
		{
			return (T) this;
		}
		return super.getCapability(capability, facing);
	}
}
