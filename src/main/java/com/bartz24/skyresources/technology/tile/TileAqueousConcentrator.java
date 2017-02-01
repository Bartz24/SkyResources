package com.bartz24.skyresources.technology.tile;

import java.util.Arrays;

import com.bartz24.skyresources.base.tile.TileGenericPower;
import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;
import com.bartz24.skyresources.registry.ModBlocks;
import com.bartz24.skyresources.technology.block.BlockAqueousConcentrator;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class TileAqueousConcentrator extends TileGenericPower implements ITickable, IFluidHandler
{
	public TileAqueousConcentrator()
	{
		super("aqueousConcentrator", 100000, 2000, 0, 2, new Integer[] { 1 }, new Integer[] { 0 });
		tank = new FluidTank(4000);
	}

	private int powerUsage = 80;
	private int curProgress;

	@Override
	public void update()
	{
		if (!this.world.isRemote)
		{
			if (concentratorMode())
				this.updateConcentrate();
			else
				this.updateDeconcentrate();
			this.markDirty();
		}

	}

	public int getProgress()
	{
		return curProgress;
	}

	private void updateConcentrate()
	{
		if (tank.getFluid() != null)
		{
			ProcessRecipe recipe = ProcessRecipeManager.waterExtractorInsertRecipes.getRecipe(
					Arrays.asList(new Object[] { this.getInventory().getStackInSlot(0), tank.getFluid().copy() }), 0,
					false, false);
			if (curProgress < 100 && getEnergyStored() >= powerUsage && recipe != null
					&& tank.getFluidAmount() >= recipe.getIntParameter()
					&& this.getInventory().insertItem(1, recipe.getOutputs().get(0).copy(), true).isEmpty())
			{
				internalExtractEnergy(powerUsage, false);
				curProgress += 5;
			} else if (recipe == null)
				curProgress = 0;
			if (curProgress >= 100
					&& this.getInventory().insertItem(1, recipe.getOutputs().get(0).copy(), true).isEmpty()
					&& recipe != null)
			{
				this.getInventory().insertItem(1, recipe.getOutputs().get(0).copy(), false);
				tank.drain((int) recipe.getFluidInputs().get(0).amount, true);
				this.getInventory().getStackInSlot(0).shrink(1);
				curProgress = 0;
			}
		}
	}

	private void updateDeconcentrate()
	{
		ProcessRecipe recipe = ProcessRecipeManager.waterExtractorExtractRecipes
				.getRecipe(this.getInventory().getStackInSlot(0), 0, false, false);
		if (curProgress < 100 && getEnergyStored() >= powerUsage && recipe != null
				&& tank.getFluidAmount() + recipe.getFluidOutputs().get(0).amount <= tank.getCapacity()
				&& this.getInventory().insertItem(1, recipe.getOutputs().get(0).copy(), true).isEmpty())
		{
			internalExtractEnergy(powerUsage, false);
			curProgress += 10;
		} else if (recipe == null)
			curProgress = 0;
		if (curProgress >= 100 && this.getInventory().insertItem(1, recipe.getOutputs().get(0).copy(), true).isEmpty()
				&& recipe != null)
		{
			this.getInventory().insertItem(1, recipe.getOutputs().get(0).copy(), false);
			tank.fill(recipe.getFluidOutputs().get(0), true);
			this.getInventory().getStackInSlot(0).shrink(1);
			curProgress = 0;
		}

	}

	public boolean concentratorMode()
	{
		return world.getBlockState(pos).getBlock() == ModBlocks.aqueousConcentrator;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound = super.writeToNBT(compound);

		compound.setInteger("progress", curProgress);

		tank.writeToNBT(compound);
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		curProgress = compound.getInteger("progress");

		tank.readFromNBT(compound);
	}

	FluidTank tank;

	@Override
	public IFluidTankProperties[] getTankProperties()
	{
		return tank.getTankProperties();
	}

	@Override
	public int fill(FluidStack resource, boolean doFill)
	{
		if (concentratorMode() && resource != null && resource.getFluid() == FluidRegistry.WATER)
		{
			int filled = tank.fill(resource, doFill);

			return filled;
		}

		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain)
	{
		if (!concentratorMode())
		{
			return tank.drain(resource, doDrain);
		}

		return null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain)
	{
		if (!concentratorMode())
		{
			return tank.drain(maxDrain, doDrain);
		}

		return null;
	}

	public FluidTank getTank()
	{
		return tank;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
		{
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
		{
			return (T) this;
		}
		return super.getCapability(capability, facing);
	}
}
