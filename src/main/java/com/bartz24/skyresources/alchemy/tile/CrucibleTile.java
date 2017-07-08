package com.bartz24.skyresources.alchemy.tile;

import java.util.List;

import com.bartz24.skyresources.base.HeatSources;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class CrucibleTile extends TileEntity implements ITickable, IFluidHandler
{
	FluidTank tank;

	public static int tankCapacity = ConfigOptions.crucibleCapacity;

	public ItemStack itemIn = ItemStack.EMPTY;
	public int itemAmount;
	int maxItemAmount = ConfigOptions.crucibleCapacity;

	@Override
	public IFluidTankProperties[] getTankProperties()
	{
		return tank.getTankProperties();
	}

	@Override
	public int fill(FluidStack resource, boolean doFill)
	{
		if (resource != null)
		{
			int filled = tank.fill(resource, doFill);

			return filled;
		}

		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain)
	{
		if (resource != null)
		{
			return tank.drain(resource.amount, doDrain);
		}

		return null;
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

	public CrucibleTile()
	{
		tank = new FluidTank(tankCapacity);
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
		if (!world.isRemote)
		{
			List<EntityItem> list = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.getX(),
					pos.getY() + 0.2, pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1));

			for (EntityItem entity : list)
			{
				ItemStack stack = entity.getItem();

				ProcessRecipe recipe = ProcessRecipeManager.crucibleRecipes.getRecipe(stack, 0, false, false);

				int amount = recipe == null ? 0 : recipe.getFluidOutputs().get(0).amount;
				if (itemAmount + amount <= maxItemAmount && recipe != null)
				{
					ItemStack input = (ItemStack) recipe.getInputs().get(0);

					if (tank.getFluid() == null || tank.getFluid().getFluid() == null)
					{
						this.itemIn = input;
					}

					if (itemIn == input)
					{
						itemAmount += amount;
						stack.shrink(1);
					}
				}
			}
			if (itemAmount > 0)
			{
				int val = Math.min(getHeatSourceVal(), itemAmount);
				if (itemIn != ItemStack.EMPTY && val > 0 && tank.getFluidAmount() + val <= tank.getCapacity())
				{
					ProcessRecipe recipe = ProcessRecipeManager.crucibleRecipes.getRecipe(itemIn, 0, false, false);
					tank.fill(new FluidStack(recipe.getFluidOutputs().get(0), val), true);
					itemAmount -= val;
				}

				if (tank.getFluidAmount() == 0 && itemAmount == 0)
					itemIn = ItemStack.EMPTY;

			}
			markDirty();
			world.notifyBlockUpdate(getPos(), world.getBlockState(getPos()), world.getBlockState(getPos()), 3);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		tank.writeToNBT(compound);

		compound.setInteger("amount", itemAmount);
		NBTTagCompound stackTag = new NBTTagCompound();
		if (itemIn != ItemStack.EMPTY)
			itemIn.writeToNBT(stackTag);
		compound.setTag("Item", stackTag);

		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		tank.readFromNBT(compound);

		itemAmount = compound.getInteger("amount");
		NBTTagCompound stackTag = compound.getCompoundTag("Item");
		if (stackTag != null)
			itemIn = new ItemStack(stackTag);
	}

	int getHeatSourceVal()
	{
		if (HeatSources.isValidHeatSource(pos.down(), world))
		{
			if (HeatSources.getHeatSourceValue(pos.down(), world) > 0)
				return Math.max(HeatSources.getHeatSourceValue(pos.down(), world) / 3, 1);
		}
		return 0;
	}

	public int getItemAmount()
	{
		return itemAmount;
	}

	@Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return (T) this;
        }
        return super.getCapability(capability, facing);
    }
}
