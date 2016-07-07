package com.bartz24.skyresources.forestry.tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.bartz24.skyresources.RandomHelper;

import cofh.api.energy.IEnergyReceiver;
import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IHiveDrop;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.apiculture.PluginApiculture;
import forestry.apiculture.items.ItemBeeGE;
import forestry.apiculture.worldgen.Hive;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileBeeAttractor extends TileEntity
		implements ITickable, IFluidHandler, IInventory, IEnergyReceiver
{
	private ItemStack[] inventory = new ItemStack[6];

	private FluidTank tank;
	private int powerUsage = 100;
	private int fluidUsage = 2;

	private int energy;

	private int maxReceive = 200;

	int ticks = 200;

	public TileBeeAttractor()
	{
		tank = new FluidTank(4000);
	}

	@Override
	public void update()
	{
		if (!worldObj.isRemote)
		{

			if (tank.getFluidAmount() >= fluidUsage
					&& tank.getFluid().getFluid().getName().equals("seed.oil")
					&& energy >= powerUsage && !isInvFull())
			{
				tank.drain(fluidUsage, true);
				energy -= powerUsage;
				if (ticks == 0)
				{
					ItemStack bee = getRandomBee();
					System.out.println(
							bee == null ? "Null Bee" : bee.getDisplayName());

					RandomHelper.fillInventory(this, bee);

					ticks = 200;
				} else
					ticks--;
			}
		}
		markDirty();
		worldObj.notifyBlockUpdate(getPos(), worldObj.getBlockState(getPos()),
				worldObj.getBlockState(getPos()), 3);
	}
	
	boolean isInvFull()
	{
		for(ItemStack i : this.inventory)
		{
			if(i == null) return false;
		}
			
		return true;
	}

	ItemStack getRandomBee()
	{
		if (worldObj.rand.nextInt(5) == 0)
		{
			List<ItemStack> drops = new ArrayList();
			for (Hive h : PluginApiculture.hiveRegistry.getHives())
			{
				Biome biome = worldObj.getBiomeForCoordsBody(getPos());
				if (h.isGoodBiome(biome)
						&& h.isGoodHumidity(
								EnumHumidity.getFromValue(biome.getRainfall()))
						&& h.isGoodTemperature(EnumTemperature
								.getFromValue(biome.getTemperature())))
				{
					List<IHiveDrop> hiveDrops = h.getDrops();
					drops.addAll(getBeeDrops(hiveDrops));
				}
			}

			boolean princess = (worldObj.rand.nextInt(8) == 0);

			ItemStack beeStack = null;
			int tries = 0;
			while (beeStack == null && tries < 30)
			{
				ItemStack stack = drops
						.get(worldObj.rand.nextInt(drops.size()));
				if (princess && stack
						.getItem() == PluginApiculture.items.beePrincessGE)
					beeStack = stack;
				else if (!princess
						&& stack.getItem() == PluginApiculture.items.beeDroneGE)
					beeStack = stack;
				tries++;
			}
			return beeStack.copy();
		}
		return null;
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
			} while (worldObj.rand.nextDouble() >= drop.getChance(worldObj, pos,
					0));
			IBee bee = drop.getBeeType(worldObj, pos);
			if ((double) worldObj.rand.nextFloat() < drop
					.getIgnobleChance(worldObj, pos, 0))
				bee.setIsNatural(false);
			ItemStack princess = BeeManager.beeRoot.getMemberStack(bee,
					EnumBeeType.PRINCESS);
			drops.add(princess);
			hasPrincess = true;
		} while (true);
		Iterator iterator1 = hiveDrops.iterator();
		do
		{
			if (!iterator1.hasNext())
				break;
			IHiveDrop drop = (IHiveDrop) iterator1.next();
			if (worldObj.rand.nextDouble() >= drop.getChance(worldObj, pos, 0))
				continue;
			IBee bee = drop.getBeeType(worldObj, pos);
			ItemStack drone = BeeManager.beeRoot.getMemberStack(bee,
					EnumBeeType.DRONE);
			drops.add(drone);
			break;
		} while (true);
		return drops;
	}

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
		return null;
	}

	@Override
	public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain)
	{
		return null;
	}

	@Override
	public boolean canFill(EnumFacing from, Fluid fluid)
	{
		if (!fluid.getName().equals("seed.oil"))
			return false;

		return tank.getFluid() == null || tank.getFluid().getFluid() == fluid;
	}

	@Override
	public boolean canDrain(EnumFacing from, Fluid fluid)
	{
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(EnumFacing from)
	{
		return new FluidTankInfo[]
		{ tank.getInfo() };
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
	public ItemStack getStackInSlot(int index)
	{
		if (index < 0 || index >= this.getSizeInventory())
			return null;
		return this.inventory[index];
	}

	@Override
	public String getName()
	{
		return "container.skyresources.beeAttractor";
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return new TextComponentTranslation(this.getName());
	}

	@Override
	public int getSizeInventory()
	{
		return 6;
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		if (this.getStackInSlot(index) != null)
		{
			ItemStack itemstack;

			if (this.getStackInSlot(index).stackSize <= count)
			{
				itemstack = this.getStackInSlot(index);
				this.setInventorySlotContents(index, null);
				this.markDirty();
				return itemstack;
			} else
			{
				itemstack = this.getStackInSlot(index).splitStack(count);

				if (this.getStackInSlot(index).stackSize <= 0)
				{
					this.setInventorySlotContents(index, null);
				} else
				{
					// Just to show that changes happened
					this.setInventorySlotContents(index,
							this.getStackInSlot(index));
				}

				this.markDirty();
				return itemstack;
			}
		} else
		{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		if (index < 0 || index >= this.getSizeInventory())
			return;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit())
			stack.stackSize = this.getInventoryStackLimit();

		if (stack != null && stack.stackSize == 0)
			stack = null;

		this.inventory[index] = stack;
		this.markDirty();

	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return this.worldObj.getTileEntity(this.getPos()) == this
				&& player.getDistanceSq(this.pos.add(0.5, 0.5, 0.5)) <= 64;
	}

	@Override
	public void openInventory(EntityPlayer player)
	{
	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return false;
	}

	@Override
	public int getField(int id)
	{
		return 0;
	}

	@Override
	public void setField(int id, int value)
	{
	}

	@Override
	public int getFieldCount()
	{
		return 0;
	}

	@Override
	public void clear()
	{
		for (int i = 0; i < this.getSizeInventory(); i++)
			this.setInventorySlotContents(i, null);
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(this.inventory, index);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound = super.writeToNBT(compound);

		compound.setInteger("Energy", energy);
		compound.setInteger("Ticks", ticks);
		NBTTagList list = new NBTTagList();
		for (int i = 0; i < this.getSizeInventory(); ++i)
		{
			if (this.getStackInSlot(i) != null)
			{
				NBTTagCompound stackTag = new NBTTagCompound();
				stackTag.setByte("Slot", (byte) i);
				this.getStackInSlot(i).writeToNBT(stackTag);
				list.appendTag(stackTag);
			}
		}
		compound.setTag("Items", list);
		tank.writeToNBT(compound);

		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		energy = compound.getInteger("Energy");
		ticks = compound.getInteger("Ticks");
		NBTTagList list = compound.getTagList("Items", 10);
		for (int i = 0; i < list.tagCount(); ++i)
		{
			NBTTagCompound stackTag = list.getCompoundTagAt(i);
			int slot = stackTag.getByte("Slot") & 255;
			this.setInventorySlotContents(slot,
					ItemStack.loadItemStackFromNBT(stackTag));
		}
		tank.readFromNBT(compound);
	}

	public FluidTank getTank()
	{
		return tank;
	}

	@Override
	public int getEnergyStored(EnumFacing from)
	{
		return energy;
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from)
	{
		return 50000;
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from)
	{
		return true;
	}

	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate)
	{
		int energyReceived = (int) Math.min(getMaxEnergyStored(from) - energy,
				Math.min(this.maxReceive, maxReceive));
		if (!simulate)
		{
			energy += energyReceived;
		}
		return energyReceived;
	}

	public boolean shouldRefresh(World world, BlockPos pos,
			IBlockState oldState, IBlockState newState)
	{
		return oldState.getBlock() != newState.getBlock();
	}
}
