package com.bartz24.skyresources.base.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class TileBase extends TileEntity
{
	private String name;

	public TileBase(String name)
	{
		this.name = name;
	}

	public ITextComponent getDisplayName()
	{
		return new TextComponentTranslation("container.skyresources." + name);
	}

	private int prevRedstoneSignal;

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
				int redstoneSide = getWorld().getRedstonePower(getPos().offset(dir), dir);
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
			int redstoneSide = getWorld().getRedstonePower(getPos().offset(dir), dir);
			redstoneSignal = Math.max(redstoneSignal, redstoneSide);
		}
		return redstoneSignal;
	}

	public void updateRedstone()
	{
		if (!world.isRemote)
			prevRedstoneSignal = getRedstoneSignal();
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
	  public NBTTagCompound getUpdateTag()
	  {
	    NBTTagCompound nbtTagCompound = new NBTTagCompound();
	    writeToNBT(nbtTagCompound);
	    return nbtTagCompound;
	  }

	  @Override
	  public void handleUpdateTag(NBTTagCompound tag)
	  {
	    this.readFromNBT(tag);
	  }

	public void markDirty() {
		super.markDirty();
		if (world != null && !world.isRemote)
			world.notifyBlockUpdate(getPos(), world.getBlockState(getPos()), world.getBlockState(getPos()), 0);
	}

	public void markDirtyBlockUpdate() {
		super.markDirty();
		if (world != null && !world.isRemote) {
			world.notifyBlockUpdate(getPos(), world.getBlockState(getPos()), world.getBlockState(getPos()), 0);
			world.notifyBlockUpdate(getPos(), world.getBlockState(getPos()), world.getBlockState(getPos()), 3);
			world.notifyNeighborsOfStateChange(pos, blockType, true);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		compound.setInteger("pSignal", prevRedstoneSignal);
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		prevRedstoneSignal = compound.getInteger("pSignal");
	}
}
