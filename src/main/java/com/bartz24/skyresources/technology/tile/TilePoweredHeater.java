package com.bartz24.skyresources.technology.tile;

import com.bartz24.skyresources.api.RedstoneCompatibleTile;
import com.bartz24.skyresources.base.IHeatSource;
import com.bartz24.skyresources.technology.block.BlockPoweredHeater;

import cofh.api.energy.IEnergyReceiver;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TilePoweredHeater extends RedstoneCompatibleTile
		implements ITickable, IEnergyReceiver, IHeatSource
{
	private int energy;
	private int maxEnergy = 100000;
	private int maxReceive = 2000;
	private int powerUsage = 120;

	@Override
	public int getEnergyStored(EnumFacing from)
	{
		return energy;
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from)
	{
		return maxEnergy;
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from)
	{
		return from != EnumFacing.UP;
	}

	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate)
	{
		int energyReceived = (int) Math.min(getMaxEnergyStored(from) - energy,
				Math.min(this.maxReceive, maxReceive));
		if (!simulate)
		{
			energy += energyReceived;
		}
		return energyReceived;
	}

	@Override
	public void update()
	{
		if (!worldObj.isRemote)
		{
			if (energy >= powerUsage && this.getRedstoneSignal() > 0)
			{
				energy -= powerUsage;
				worldObj.setBlockState(getPos(), worldObj.getBlockState(getPos())
						.withProperty(BlockPoweredHeater.RUNNING, true), 3);
			} else
				worldObj.setBlockState(getPos(), worldObj.getBlockState(getPos())
						.withProperty(BlockPoweredHeater.RUNNING, false), 3);

		}
		markDirty();
		worldObj.notifyBlockUpdate(getPos(), worldObj.getBlockState(getPos()),
				worldObj.getBlockState(getPos()), 3);
	}

	@Override
	public int getHeatValue()
	{
		if (energy >= powerUsage && this.getRedstoneSignal() > 0)
			return 30;
		return 0;
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

	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState,
			IBlockState newState)
	{
		return oldState.getBlock() != newState.getBlock();
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		NBTTagCompound nbtTag = new NBTTagCompound();
		this.writeToNBT(nbtTag);
		return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet)
	{
		super.onDataPacket(net, packet);
		this.readFromNBT(packet.getNbtCompound());
	}

}
