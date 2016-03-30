package com.bartz24.skyresources.alchemy.tile;

import java.util.List;

import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.alchemy.item.MetalCrystalItem;
import com.bartz24.skyresources.base.HeatSources;
import com.bartz24.skyresources.registry.ModFluids;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class CrucibleTile extends TileEntity implements ITickable, IFluidHandler
{
	FluidTank tank;

	public static int tankCapacity = 4000;

	int itemAmount;
	int maxItemAmount = 4000;
	int currentType;

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
	public FluidStack drain(EnumFacing from, FluidStack resource,
			boolean doDrain)
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
			if (fluid == null || tank.getFluid() != null
					&& tank.getFluid().getFluid() == fluid)
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

	public CrucibleTile()
	{
		tank = new FluidTank(tankCapacity);
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbtTag = new NBTTagCompound();
		this.writeToNBT(nbtTag);
		return new SPacketUpdateTileEntity(getPos(), 200, nbtTag);
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
			if (itemAmount <= maxItemAmount - 1000)
			{
				List<EntityItem> list = worldObj.getEntitiesWithinAABB(
						EntityItem.class,
						new AxisAlignedBB(pos.getX(), pos.getY() + 0.2,
								pos.getZ(), pos.getX() + 1, pos.getY() + 1,
								pos.getZ() + 1));

				for (EntityItem entity : list)
				{
					ItemStack stack = entity.getEntityItem();

					if (itemAmount + 1000 <= maxItemAmount
							&& stack.getItem() instanceof MetalCrystalItem)
					{
						int itemType = stack.getItem().getMetadata(stack);

						if (tank.getFluid() == null
								|| tank.getFluid().getFluid() == null)
						{
							currentType = itemType;
						}

						if (currentType == itemType)
						{
							itemAmount += 1000;
							stack.stackSize--;
						}
					}
				}
			}
			if (itemAmount > 0)
			{
				int val = Math.min(getHeatSourceVal(), itemAmount);
				if (currentType >= 0 && val > 0
						&& tank.getFluidAmount() + val <= tank.getCapacity())
				{
					this.fill(null,
							new FluidStack(
									ModFluids.crystalFluids.get(currentType),
									val),
							true);
					itemAmount -= val;
				}

				if (tank.getFluidAmount() == 0 && itemAmount == 0)
					currentType = -1;

			}
			RandomHelper.dispatchTEToNearbyPlayers(worldObj, pos);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		tank.writeToNBT(compound);

		compound.setInteger("amount", itemAmount);
		compound.setInteger("type", currentType);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		tank.readFromNBT(compound);

		itemAmount = compound.getInteger("amount");
		currentType = compound.getInteger("type");
	}

	int getHeatSourceVal()
	{
		if (HeatSources.isValidHeatSource(worldObj.getBlockState(pos.down())))
		{
			return HeatSources
					.getHeatSourceValue(worldObj.getBlockState(pos.down())) / 5;
		}
		return 0;
	}
}
