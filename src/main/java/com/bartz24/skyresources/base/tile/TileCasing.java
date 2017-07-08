package com.bartz24.skyresources.base.tile;

import com.bartz24.skyresources.base.IHeatSource;
import com.bartz24.skyresources.base.gui.ItemHandlerSpecial;
import com.bartz24.skyresources.base.item.ItemMachine;

import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileCasing extends TileGenericPower implements IFluidHandler, ITickable, IHeatSource
{

	private FluidTank tank;
	public ItemStack machineStored = ItemStack.EMPTY;
	public NBTTagCompound machineData = new NBTTagCompound();

	public TileCasing()
	{
		super("machinecasing", 0, 0, 0);
		tank = new FluidTank(0);
	}

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
		return null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain)
	{
		return null;
	}

	public FluidTank getTank()
	{
		return tank;
	}

	@Override
	public void update()
	{
		if (!machineStored.isEmpty())
		{
			getMachine().update(getWorld(), getPos(), machineStored, machineData);
		} else
			machineData = new NBTTagCompound();
		updateRedstone();
		if (!getWorld().isRemote)
			this.markDirty();
	}

	public ItemMachine getMachine()
	{
		return (ItemMachine) machineStored.getItem();
	}

	public void setMachine(ItemStack machine)
	{
		super.dropInventory();
		machineStored = machine;
		updateHandlerData();
	}

	public void readFromNBT(NBTTagCompound compound)
	{
		NBTTagCompound stackTag = compound.getCompoundTag("Machine");
		if (stackTag != null)
			machineStored = new ItemStack(stackTag);
		machineData = compound.getCompoundTag("MachineData");
		tank.readFromNBT(compound);
		super.readFromNBT(compound);
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		NBTTagCompound stackTag = new NBTTagCompound();
		if (!machineStored.isEmpty())
			machineStored.writeToNBT(stackTag);
		compound.setTag("Machine", stackTag);
		compound.setTag("MachineData", machineData);
		tank.writeToNBT(compound);
		compound = super.writeToNBT(compound);
		return compound;
	}

	public void dropInventory()
	{
		if (!machineStored.isEmpty())
		{
			InventoryHelper.spawnItemStack(getWorld(), pos.getX(), pos.getY(), pos.getZ(), machineStored);
		}
		super.dropInventory();
	}

	public void updateHandlerData()
	{
		if (!machineStored.isEmpty())
		{
			this.maxEnergy = getMachine().getMaxEnergy(machineStored) != 0 ? getMachine().getMaxEnergy(machineStored)
					: 0;
			this.energy = getMachine().getMaxEnergy(machineStored) != 0 ? this.energy : 0;
			this.maxExtract = getMachine().getMaxEnergy(machineStored) != 0 ? getMachine().getMaxExtract(machineStored)
					: 0;
			this.maxReceive = getMachine().getMaxEnergy(machineStored) != 0 ? getMachine().getMaxReceive(machineStored)
					: 0;
			this.setInventory(new ItemHandlerSpecial(getMachine().getItemSlots(machineStored),
					getMachine().getInsertBlacklist(machineStored), getMachine().getExtractBlacklist(machineStored))
			{
				protected void onContentsChanged(int slot)
				{
					super.onContentsChanged(slot);
					TileCasing.this.markDirty();
				}
			});
			if (getMachine().getFluid(machineStored) != null)
				this.tank = new FluidTank(getMachine().getFluid(machineStored), 0,
						getMachine().getMaxFluid(machineStored));
		} else
		{
			this.maxEnergy = 0;
			this.energy = 0;
			this.maxExtract = 0;
			this.maxReceive = 0;
			this.setInventory(new ItemHandlerSpecial(0));
			this.tank = new FluidTank(0);
		}
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if (capability == CapabilityEnergy.ENERGY)
		{
			return machineStored.isEmpty() ? false : getMachine().getMaxEnergy(machineStored) != 0;
		} else if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return machineStored.isEmpty() ? false : getMachine().getItemSlots(machineStored) != 0;
		} else if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
		{
			return machineStored.isEmpty() ? false : getMachine().getFluid(machineStored) != null;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return (T) this.getInventory();
		} else if (hasCapability(capability, facing))
		{
			return (T) this;
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public int getHeatValue()
	{		
		return machineStored.isEmpty() ? 0 : getMachine().getHeatProv(machineStored, world, pos);
	}
}
