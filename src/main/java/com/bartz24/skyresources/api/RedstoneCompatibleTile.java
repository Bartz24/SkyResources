package com.bartz24.skyresources.api;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class RedstoneCompatibleTile extends TileEntity
{
	public int prevRedstoneSignal;

	public boolean receivedPulse()
	{
		return getRedstoneSignal() > 0 && prevRedstoneSignal == 0;
	}

	public boolean canAcceptRedstone()
	{
		return true;
	};

	public int getRedstoneSignal()
	{
		int signal = 0;
		if (canAcceptRedstone())
		{
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				int redstoneSide = getWorld()
						.getRedstonePower(getPos().offset(dir), dir);
				signal = Math.max(signal, redstoneSide);
			}
		}
		return signal;
	}

	public int getRedstoneSignalFromSide(EnumFacing dir)
	{
		int redstoneSignal = 0;
		if (canAcceptRedstone())
		{
			int redstoneSide = getWorld().getRedstonePower(getPos().offset(dir),
					dir);
			redstoneSignal = Math.max(redstoneSignal, redstoneSide);
		}
		return redstoneSignal;
	}

	@Override
	public void writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		compound.setInteger("pSignal", prevRedstoneSignal);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		prevRedstoneSignal = compound.getInteger("pSignal");
	}
}
