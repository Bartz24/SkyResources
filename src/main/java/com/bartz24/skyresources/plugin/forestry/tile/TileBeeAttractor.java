package com.bartz24.skyresources.plugin.forestry.tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.base.tile.TileGenericPower;

import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IHiveDrop;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.apiculture.PluginApiculture;
import forestry.apiculture.worldgen.Hive;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class TileBeeAttractor extends TileGenericPower implements ITickable, IFluidHandler
{
	private FluidTank tank;
	private int powerUsage = 100;
	private int fluidUsage = 20;
	int ticks = 200;

	public TileBeeAttractor()
	{
		super("beeAttractor", 100000, 2000, 0, 6, new Integer[] { 0, 1, 2, 3, 4, 5 }, null);
		tank = new FluidTank(4000);
	}

	@Override
	public void update()
	{
		if (!world.isRemote)
		{
			updateRedstone();
			if (this.getRedstoneSignal() == 0)
			{
				if (tank.getFluidAmount() >= fluidUsage && tank.getFluid().getFluid().getName().equals("seed.oil")
						&& energy >= powerUsage && !isInvFull())
				{
					tank.drain(fluidUsage, true);
					energy -= powerUsage;
					for (int i = 0; i < world.rand.nextInt(3) + 1; i++)
						if (!isInvFull())
						{
							ItemStack bee = getRandomBee();

							RandomHelper.fillInternalInventory(this.getInventory(), bee);
						}
				}
			}
		}
		markDirty();
		world.notifyBlockUpdate(getPos(), world.getBlockState(getPos()), world.getBlockState(getPos()), 3);
	}

	boolean isInvFull()
	{
		for (int i = 0; i < this.getInventory().getSlots(); i++)
		{
			if (this.getInventory().getStackInSlot(i).isEmpty())
				return false;
		}

		return true;
	}

	ItemStack getRandomBee()
	{
		if (world.rand.nextInt(15) == 0)
		{
			List<ItemStack> drops = new ArrayList();
			boolean smallChance = world.rand.nextFloat() <= 0.25f;
			while (drops.size() != 0)
			{
				for (Hive h : PluginApiculture.getHiveRegistry().getHives())
				{
					Biome biome = world.getBiomeForCoordsBody(getPos());
					if (smallChance
							|| (h.isGoodBiome(biome) && h.isGoodHumidity(EnumHumidity.getFromValue(biome.getRainfall()))
									&& h.isGoodTemperature(EnumTemperature.getFromValue(biome.getTemperature()))))
					{
						List<IHiveDrop> hiveDrops = h.getDrops();
						drops.addAll(getBeeDrops(hiveDrops));
					}
				}
				smallChance = true;
			}

			boolean princess = (world.rand.nextInt(5) == 0);

			ItemStack beeStack = ItemStack.EMPTY;
			int tries = 0;
			while (beeStack.isEmpty() && tries < 30)
			{
				ItemStack stack = drops.get(world.rand.nextInt(drops.size()));
				if (princess && stack.getItem() == PluginApiculture.getItems().beePrincessGE)
					beeStack = stack;
				else if (!princess && stack.getItem() == PluginApiculture.getItems().beeDroneGE)
					beeStack = stack;
				tries++;
			}
			return beeStack.isEmpty() ? ItemStack.EMPTY : beeStack.copy();
		}
		return ItemStack.EMPTY;
	}

	List<ItemStack> getBeeDrops(List<IHiveDrop> hiveDrops)
	{
		Collections.shuffle(hiveDrops);
		List<ItemStack> drops = new ArrayList();
		int tries = 0;
		boolean hasPrincess = false;
		label0: do
		{
			if (tries > 10 || hasPrincess)
				break;
			tries++;
			Iterator iterator = hiveDrops.iterator();
			IHiveDrop drop;
			do
			{
				if (!iterator.hasNext())
					continue label0;
				drop = (IHiveDrop) iterator.next();
			} while (world.rand.nextDouble() >= drop.getChance(world, pos, 0));
			IBee bee = drop.getBeeType(world, pos);
			if ((double) world.rand.nextFloat() < drop.getIgnobleChance(world, pos, 0))
				bee.setIsNatural(false);
			ItemStack princess = BeeManager.beeRoot.getMemberStack(bee, EnumBeeType.PRINCESS);
			drops.add(princess);
			hasPrincess = true;
		} while (true);
		Iterator iterator1 = hiveDrops.iterator();
		do
		{
			if (!iterator1.hasNext())
				break;
			IHiveDrop drop = (IHiveDrop) iterator1.next();
			if (world.rand.nextDouble() >= drop.getChance(world, pos, 0))
				continue;
			IBee bee = drop.getBeeType(world, pos);
			ItemStack drone = BeeManager.beeRoot.getMemberStack(bee, EnumBeeType.DRONE);
			drops.add(drone);
			break;
		} while (true);
		return drops;
	}

	@Override
	public IFluidTankProperties[] getTankProperties()
	{
		return tank.getTankProperties();
	}

	@Override
	public int fill(FluidStack resource, boolean doFill)
	{
		if (resource != null && resource.getFluid().getName().equals("seed.oil"))
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

		compound.setInteger("Ticks", ticks);
		tank.writeToNBT(compound);

		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		ticks = compound.getInteger("Ticks");
		tank.readFromNBT(compound);
	}

	public FluidTank getTank()
	{
		return tank;
	}
}
