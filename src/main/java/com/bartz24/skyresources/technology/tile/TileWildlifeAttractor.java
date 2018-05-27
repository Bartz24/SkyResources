package com.bartz24.skyresources.technology.tile;

import com.bartz24.skyresources.base.tile.TileGenericPower;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.registry.ModItems;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class TileWildlifeAttractor extends TileGenericPower implements ITickable, IFluidHandler
{
	private FluidTank tank;
	private int powerUsage = ConfigOptions.machineSettings.wildlifeAttractorPowerUsage;
	private int matterTime = ConfigOptions.machineSettings.wildlifeAttractorMatterTime;
	private int fluidUsage = ConfigOptions.machineSettings.wildlifeAttractorWaterUsage;
	private int matterLeft = 0;

	public TileWildlifeAttractor()
	{
		super("wildlifeAttractor", 100000, 2000, 0, 1, null, new int[] { 0 });
		tank = new FluidTank(ConfigOptions.machineSettings.wildlifeAttractorWaterCapacity);
	}

	@Override
	public void update()
	{
		if (!world.isRemote)
		{
			if (this.getRedstoneSignal() == 0)
			{
				if (matterLeft <= 0)
				{
					if (!getInventory().getStackInSlot(0).isEmpty() && getInventory().getStackInSlot(0)
							.isItemEqual(new ItemStack(ModItems.baseComponent, 1, 0)))
					{
						getInventory().getStackInSlot(0).shrink(1);
						matterLeft = matterTime + 0;
					}
				}
				if (tank.getFluidAmount() >= fluidUsage && tank.getFluid().getFluid() == FluidRegistry.WATER
						&& energy >= powerUsage && matterLeft > 0)
				{
					spawnRandomAnimal();
					tank.drain(fluidUsage, true);
					energy -= powerUsage;
					matterLeft--;
				}
			}
		}
		markDirty();
	}

	public int getMatterLeft()
	{
		return matterLeft;
	}

	void spawnRandomAnimal()
	{
		if (world.rand.nextInt(600) == 0)
		{
			String randomID = ConfigOptions.machineSettings.wildlifeAttractorAnimalIDs[world.rand
					.nextInt(ConfigOptions.machineSettings.wildlifeAttractorAnimalIDs.length)];
			EntityLiving e = (EntityLiving) EntityList.createEntityByIDFromName(new ResourceLocation(randomID), world);
			e.setLocationAndAngles(pos.getX() + 0.5f, pos.getY() + 1f, pos.getZ() + 0.5f,
					world.rand.nextFloat() * 360.0F, 0.0F);

			net.minecraftforge.fml.common.eventhandler.Event.Result canSpawn = net.minecraftforge.event.ForgeEventFactory
					.canEntitySpawn(e, world, pos.getX() + 0.5f, pos.getY() + 1f, pos.getZ() + 0.5f, false);
			boolean isNotColliding = e.isNotColliding();
			if (canSpawn == net.minecraftforge.fml.common.eventhandler.Event.Result.ALLOW
					|| (canSpawn == net.minecraftforge.fml.common.eventhandler.Event.Result.DEFAULT && isNotColliding))
			{
				if (!net.minecraftforge.event.ForgeEventFactory.doSpecialSpawn(e, world, pos.getX() + 0.5f,
						pos.getY() + 1f, pos.getZ() + 0.5f))
					e.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(e)), null);

				if (e.isNotColliding())
				{
					world.spawnEntity(e);
				} else
				{
					e.setDead();
				}
			}

		}
	}

	@Override
	public IFluidTankProperties[] getTankProperties()
	{
		return tank.getTankProperties();
	}

	@Override
	public int fill(FluidStack resource, boolean doFill)
	{
		if (resource != null && resource.getFluid() == FluidRegistry.WATER)
		{
			int filled = tank.fill(resource, doFill);

			return filled;
		}

		return 0;
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

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound = super.writeToNBT(compound);

		compound.setInteger("matter", matterLeft);
		tank.writeToNBT(compound);

		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		matterLeft = compound.getInteger("matter");
		
		tank.readFromNBT(compound);
	}

	public FluidTank getTank()
	{
		return tank;
	}
}
