package com.bartz24.skyresources.technology.tile;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.api.RedstoneCompatibleTile;
import com.bartz24.skyresources.technology.block.CombustionHeaterBlock;
import com.bartz24.skyresources.technology.combustion.CombustionRecipe;
import com.bartz24.skyresources.technology.combustion.CombustionRecipes;

import cofh.api.energy.IEnergyReceiver;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TilePoweredCombustionHeater extends RedstoneCompatibleTile
		implements ITickable, IEnergyReceiver
{

	private int energy;
	private int maxEnergy = 100000;
	private int maxReceive = 2000;
	private int powerUsage = 800;
	public int currentHeatValue;
	private int heatPerTick = 20;

	@Override
	public int getEnergyStored(EnumFacing from)
	{
		return energy;
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from)
	{
		return maxEnergy;
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from)
	{
		return from != EnumFacing.UP;
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

	public int getMaxHeat()
	{
		if (!(worldObj.getBlockState(pos).getBlock() instanceof CombustionHeaterBlock))
			return 0;
		CombustionHeaterBlock block = (CombustionHeaterBlock) worldObj.getBlockState(pos)
				.getBlock();

		return block.getMaximumHeat(worldObj.getBlockState(pos));
	}

	@Override
	public void update()
	{
		if (receivedPulse() && hasValidMultiblock())
		{
			craftItem();
		}

		if (!this.worldObj.isRemote)
		{
			prevRedstoneSignal = getRedstoneSignal();
			if (energy >= powerUsage && currentHeatValue < getMaxHeat())
			{
				energy -= powerUsage;
				currentHeatValue += heatPerTick;
			}

			if (!hasValidMultiblock())
			{
				if (currentHeatValue > 0)
					currentHeatValue -= 4;
			}

			if (currentHeatValue < 0)
				currentHeatValue = 0;

			if (currentHeatValue > getMaxHeat())
				currentHeatValue = getMaxHeat();
		}
	}

	public boolean hasValidMultiblock()
	{
		List<Material> materials = ValidMaterialsForCrafting();
		if (!isBlockValid(pos.add(-1, 1, 0)) || !isBlockValid(pos.add(1, 1, 0))
				|| !isBlockValid(pos.add(0, 2, 0)) || !isBlockValid(pos.add(0, 1, -1))
				|| !isBlockValid(pos.add(0, 1, 1)) || !worldObj.isAirBlock(pos.add(0, 1, 0)))
			return false;
		return true;
	}

	public List<Material> ValidMaterialsForCrafting()
	{
		if (!(worldObj.getBlockState(pos).getBlock() instanceof CombustionHeaterBlock))
			return null;
		IBlockState blockMeta = worldObj.getBlockState(pos);
		List<Material> mats = new ArrayList<Material>();
		switch (worldObj.getBlockState(pos).getBlock().getMetaFromState(blockMeta))
		{
		case 2: // STEEL
			mats.add(Material.ROCK);
			mats.add(Material.IRON);
			mats.add(Material.GLASS);
			break;
		}
		return mats;
	}

	boolean isBlockValid(BlockPos pos)
	{
		return ValidMaterialsForCrafting().contains(worldObj.getBlockState(pos).getMaterial())
				&& worldObj.isBlockFullCube(pos) && worldObj.getBlockState(pos).isOpaqueCube();
	}

	void craftItem()
	{
		CombustionRecipe recipe = recipeToCraft();
		if (recipe != null)
		{
			this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, pos.getX(),
					pos.getY() + 1.5D, pos.getZ(), 0.0D, 0.0D, 0.0D, new int[0]);
			this.worldObj.playSound((EntityPlayer) null, pos.getX(), pos.getY() + 1.5D, pos.getZ(),
					SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F,
					(1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat())
							* 0.2F) * 0.7F);

			if (!worldObj.isRemote)
			{
				List<EntityItem> list = worldObj.getEntitiesWithinAABB(EntityItem.class,
						new AxisAlignedBB(pos.getX(), pos.getY() + 1, pos.getZ(), pos.getX() + 1,
								pos.getY() + 1.5F, pos.getZ() + 1));

				List<String> itemIgnores = new ArrayList<String>();
				for (EntityItem i : list)
				{
					ItemStack stack = i.getEntityItem();

					for (ItemStack s : recipe.getInputStacks())
					{
						if (stack.isItemEqual(s) && !itemIgnores.contains(s.getUnlocalizedName()))
						{
							stack.stackSize -= s.stackSize;
							itemIgnores.add(s.getUnlocalizedName());
						}
					}
				}

				currentHeatValue *= 0.4F;

				ItemStack stack = recipe.getOutput().copy();

				Entity entity = new EntityItem(worldObj, pos.getX() + 0.5F, pos.getY() + 0.5F,
						pos.getZ() + 0.5F, stack);
				worldObj.spawnEntityInWorld(entity);
			}
		}
	}

	public CombustionRecipe recipeToCraft()
	{
		List<EntityItem> list = worldObj.getEntitiesWithinAABB(EntityItem.class,
				new AxisAlignedBB(pos.getX(), pos.getY() + 1, pos.getZ(), pos.getX() + 1,
						pos.getY() + 1.5F, pos.getZ() + 1));

		List<ItemStack> items = new ArrayList<ItemStack>();

		for (EntityItem i : list)
		{
			items.add(i.getEntityItem());
		}

		CombustionRecipe recipe = CombustionRecipes.getRecipe(items, currentHeatValue);

		return recipe;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound = super.writeToNBT(compound);

		compound.setInteger("Energy", energy);
		compound.setInteger("heat", currentHeatValue);

		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		energy = compound.getInteger("Energy");
		currentHeatValue = compound.getInteger("heat");
	}

	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState,
			IBlockState newState)
	{
		return oldState.getBlock() != newState.getBlock();
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
}
