package com.bartz24.skyresources.technology.tile;

import com.bartz24.skyresources.api.RedstoneCompatibleTile;
import com.bartz24.skyresources.config.ConfigOptions;

import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import java.util.Arrays;

public class FluidDropperTile extends RedstoneCompatibleTile implements ITickable, IFluidHandler
{
	FluidTank tank;

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

	public FluidDropperTile()
	{
		tank = new FluidTank(ConfigOptions.fluidDropperCapacity);
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

		tank.readFromNBT(compound);
	}

	@Override
	public void update()
	{
		if (!worldObj.isRemote)
		{
			pullFromAround();

			if (tank.getFluidAmount() >= 1000 && worldObj.isAirBlock(pos.down()))
			{
				worldObj.setBlockState(pos.down(),
						tank.getFluid().getFluid().getBlock().getDefaultState());
				tank.setFluid(null);
				worldObj.playSound(pos.getX(), pos.getY(), pos.getZ(),
						SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 0.5F, 1, true);
			}
		}
	}

	void pullFromAround()
	{
		BlockPos[] checkPoses = new BlockPos[]
		{ getPos().up(), getPos().north(), getPos().south(), getPos().west(), getPos().east() };
		if (this.getRedstoneSignal() == 0)
		{
			for (BlockPos pos : checkPoses)
			{
				TileEntity tile = worldObj.getTileEntity(pos);
				if (tile != null && tile instanceof IFluidHandler)
				{
					IFluidHandler fluidHand = (IFluidHandler) tile;

					for (FluidTankInfo f : fluidHand.getTankInfo(
							EnumFacing.VALUES[Arrays.asList(checkPoses).indexOf(pos) + 1]
									.getOpposite()))
					{
						FluidStack tankFluid = f.fluid;

						if (tankFluid == null)
							continue;

						this.fill(null, fluidHand.drain(
								EnumFacing.VALUES[Arrays.asList(checkPoses).indexOf(pos) + 1]
										.getOpposite(),
								new FluidStack(tankFluid.getFluid(),
										Math.min(ConfigOptions.fluidDropperCapacity
												- tank.getFluidAmount(), tankFluid.amount)),
								true), true);
						return;
					}
				}
			}
		}
	}
}
