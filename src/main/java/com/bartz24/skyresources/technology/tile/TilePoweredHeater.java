package com.bartz24.skyresources.technology.tile;

import com.bartz24.skyresources.base.IHeatSource;
import com.bartz24.skyresources.base.tile.TileGenericPower;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TilePoweredHeater extends TileGenericPower implements ITickable, IEnergyStorage, IHeatSource
{
	public TilePoweredHeater()
	{
		super("poweredHeater", 100000, 2000, 0);
	}

	private int powerUsage = 120;

	@Override
	public void update()
	{
		if (!world.isRemote)
		{
			if (getEnergyStored() >= powerUsage && this.getRedstoneSignal() > 0)
			{
				internalExtractEnergy(powerUsage, false);
				world.setBlockState(getPos(),
						world.getBlockState(getPos()).withProperty(BlockPoweredHeater.RUNNING, true), 3);
			} else
				world.setBlockState(getPos(),
						world.getBlockState(getPos()).withProperty(BlockPoweredHeater.RUNNING, false), 3);

			this.markDirty();
		}
	}

	@Override
	public int getHeatValue()
	{
		if (getEnergyStored() >= powerUsage && this.getRedstoneSignal() > 0)
			return 30;
		return 0;
	}

	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		return oldState.getBlock() != newState.getBlock();
	}
}
