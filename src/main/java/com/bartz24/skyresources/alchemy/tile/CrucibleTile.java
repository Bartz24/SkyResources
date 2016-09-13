package com.bartz24.skyresources.alchemy.tile;

import java.util.List;

import com.bartz24.skyresources.alchemy.crucible.CrucibleRecipe;
import com.bartz24.skyresources.alchemy.crucible.CrucibleRecipes;
import com.bartz24.skyresources.alchemy.item.MetalCrystalItem;
import com.bartz24.skyresources.base.HeatSources;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.registry.ModFluids;

import mezz.jei.gui.ingredients.ItemStackHelper;
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
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class CrucibleTile extends TileEntity implements ITickable, IFluidHandler
{
	FluidTank tank;

	public static int tankCapacity = ConfigOptions.crucibleCapacity;

	public ItemStack itemIn;
	public int itemAmount;
	int maxItemAmount = ConfigOptions.crucibleCapacity;

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
					this.fill(null, new FluidStack(recipe.getOutput(), val), true);
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
		if(stackTag!=null)
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
}
