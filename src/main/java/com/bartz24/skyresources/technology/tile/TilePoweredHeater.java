package com.bartz24.skyresources.technology.tile;

import com.bartz24.skyresources.api.RedstoneCompatibleTile;
import com.bartz24.skyresources.base.IHeatSource;
import com.bartz24.skyresources.technology.block.BlockPoweredHeater;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TilePoweredHeater extends RedstoneCompatibleTile
		implements ITickable, IEnergyStorage, IHeatSource
{
	private int energy;
	private int maxEnergy = 100000;
	private int maxReceive = 2000;
	private int powerUsage = 120;

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate)
	{
		int energyReceived = (int) Math.min(getMaxEnergyStored() - energy, Math.min(this.maxReceive, maxReceive));
		if (!simulate)
		{
			energy += energyReceived;
		}
		return energyReceived;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate)
	{
		return 0;
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
		return false;
	}

	@Override
	public boolean canReceive()
	{
		return true;
	}

	@Override
	public void update()
	{
		if (!world.isRemote)
		{
			if (energy >= powerUsage && this.getRedstoneSignal() > 0)
			{
				energy -= powerUsage;
				world.setBlockState(getPos(), world.getBlockState(getPos())
						.withProperty(BlockPoweredHeater.RUNNING, true), 3);
			} else
				world.setBlockState(getPos(), world.getBlockState(getPos())
						.withProperty(BlockPoweredHeater.RUNNING, false), 3);

		}
		markDirty();
		world.notifyBlockUpdate(getPos(), world.getBlockState(getPos()),
				world.getBlockState(getPos()), 3);
	}

	@Override
	public int getHeatValue()
	{
		if (energy >= 0 && this.getRedstoneSignal() > 0)
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

	@Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return (T) this;
        }
        return super.getCapability(capability, facing);
    }
}
