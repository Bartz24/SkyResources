package com.bartz24.skyresources.alchemy.tile;

import java.util.List;

import com.bartz24.skyresources.alchemy.crucible.CrucibleRecipe;
import com.bartz24.skyresources.alchemy.crucible.CrucibleRecipes;
import com.bartz24.skyresources.base.HeatSources;
import com.bartz24.skyresources.config.ConfigOptions;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class CrucibleTile extends TileEntity implements ITickable, IFluidHandler
{
	FluidTank tank;

	public static int tankCapacity = ConfigOptions.crucibleCapacity;

	ItemStack itemIn;
	int itemAmount;
	int maxItemAmount = ConfigOptions.crucibleCapacity;

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
		if (!worldObj.isRemote)
		{
			List<EntityItem> list = worldObj.getEntitiesWithinAABB(EntityItem.class,
					new AxisAlignedBB(pos.getX(), pos.getY() + 0.2, pos.getZ(), pos.getX() + 1,
							pos.getY() + 1, pos.getZ() + 1));

			for (EntityItem entity : list)
			{
				ItemStack stack = entity.getEntityItem();

				CrucibleRecipe recipe = CrucibleRecipes.getRecipe(stack);

				int amount = recipe == null ? 0 : recipe.getOutput().amount;

				if (itemAmount + amount <= maxItemAmount && recipe != null)
				{
					ItemStack input = recipe.getInput();

					if (tank.getFluid() == null || tank.getFluid().getFluid() == null)
					{
						this.itemIn = input;
					}

					if (itemIn == input)
					{
						itemAmount += amount;
						stack.stackSize--;
					}
				}
			}
			if (itemAmount > 0)
			{
				int val = Math.min(getHeatSourceVal(), itemAmount);
				if (itemIn != null && val > 0 && tank.getFluidAmount() + val <= tank.getCapacity())
				{
					CrucibleRecipe recipe = CrucibleRecipes.getRecipe(itemIn);
					this.fill(new FluidStack(recipe.getOutput(), val), true);
					itemAmount -= val;
				}

				if (tank.getFluidAmount() == 0 && itemAmount == 0)
					itemIn = null;

			}
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

		compound.setInteger("amount", itemAmount);
		NBTTagCompound stackTag = new NBTTagCompound();
		if (itemIn != null)
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
			itemIn = ItemStack.loadItemStackFromNBT(stackTag);
	}

	int getHeatSourceVal()
	{
		if (HeatSources.isValidHeatSource(pos.down(), worldObj))
		{
			if (HeatSources.getHeatSourceValue(pos.down(), worldObj) > 0)
				return Math.max(HeatSources.getHeatSourceValue(pos.down(), worldObj) / 5, 1);
		}
		return 0;
	}

	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState,
			IBlockState newState)
	{
		return oldState.getBlock() != newState.getBlock();
	}

	public int getItemAmount()
	{
		return itemAmount;
	}

	public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing)
    {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return (T) this;
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> capability, net.minecraft.util.EnumFacing facing)
    {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
    }
}
