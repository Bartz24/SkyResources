package com.bartz24.skyresources.technology.tile;

import java.util.Arrays;

import com.bartz24.skyresources.api.RedstoneCompatibleTile;
import com.bartz24.skyresources.config.ConfigOptions;

import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class FluidDropperTile extends RedstoneCompatibleTile implements ITickable, IFluidHandler
{
	FluidTank tank;

	@Override
	public IFluidTankProperties[] getTankProperties()
	{
		return tank.getTankProperties();
	}

	@Override
	public int fill(FluidStack resource, boolean doFill)
	{
		return tank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain)
	{
		return tank.drain(resource, doDrain);
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain)
	{
		return tank.drain(maxDrain, doDrain);
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
					for (IFluidTankProperties tankProp : fluidHand.getTankProperties())
					{
						FluidStack tankFluid = tankProp.getContents();

						if (tankFluid == null)
							continue;

						this.fill(
								fluidHand.drain(new FluidStack(tankFluid.getFluid(),
										Math.min(ConfigOptions.fluidDropperCapacity
												- tank.getFluidAmount(), tankFluid.amount)),
										true),
								true);
						return;
					}
				}
			}
		}
	}
}
