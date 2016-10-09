package com.bartz24.skyresources.alchemy.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileWaterConcentrator extends TileEntity implements ITickable, IFluidHandler
{
	FluidTank tank;

	public static int tankCapacity = 10000;
	public boolean concentrateMode;
	
	@Override
	public int fill(EnumFacing from, FluidStack resource, boolean doFill)
	{

		if (resource != null && canFill(from, resource.getFluid()))
		{
			int filled = tank.fill(resource, doFill);

			return filled;
		}

		return 0;
	}

	@Override
	public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain)
	{
		if (resource != null && canDrain(from, resource.getFluid()))
		{
			return tank.drain(resource.amount, doDrain);
		}

		return null;
	}

	@Override
	public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain)
	{
		if (canDrain(from, null))
		{
			return tank.drain(maxDrain, doDrain);
		}

		return null;
	}

	@Override
	public boolean canFill(EnumFacing from, Fluid fluid)
	{
		return tank.getFluid() == null || tank.getFluid().getFluid() == fluid;
	}

	@Override
	public boolean canDrain(EnumFacing from, Fluid fluid)
	{
		if (tank != null)
		{
			if (fluid == null || tank.getFluid() != null && tank.getFluid().getFluid() == fluid)
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(EnumFacing from)
	{
		return new FluidTankInfo[]
		{ tank.getInfo() };
	}

	public FluidTank getTank()
	{
		return tank;
	}

	public TileWaterConcentrator(boolean concentratorMode)
	{
		tank = new FluidTank(FluidRegistry.WATER, 0, tankCapacity);
		concentrateMode = concentratorMode;		
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
	public void update()
	{
		if (!worldObj.isRemote)
		{
		}
		markDirty();
		worldObj.notifyBlockUpdate(getPos(), worldObj.getBlockState(getPos()),
				worldObj.getBlockState(getPos()), 3);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		tank.writeToNBT(compound);

		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		tank.setFluid(new FluidStack(FluidRegistry.WATER, compound.getInteger("Amount")));
	}

	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState,
			IBlockState newState)
	{
		return oldState.getBlock() != newState.getBlock();
	}

	public boolean hasValidMultiblock()
	{
		return true;
	}
}
