package com.bartz24.skyresources.technology.tile;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.api.RedstoneCompatibleTile;
import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;
import com.bartz24.skyresources.technology.block.CombustionHeaterBlock;

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
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TilePoweredCombustionHeater extends RedstoneCompatibleTile implements ITickable, IEnergyStorage
{

	private int energy;
	private int maxEnergy = 100000;
	private int maxReceive = 2000;
	private int powerUsage = 800;
	public int currentHeatValue;
	private int heatPerTick = 20;

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate)
	{
		int energyReceived = (int) Math.min(getMaxEnergyStored() - energy, Math.min(this.maxReceive, maxReceive));
		if (!simulate)
		{
			energy += energyReceived;
		}
		return energyReceived;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate)
	{
		return 0;
	}

	@Override
	public int getEnergyStored()
	{
		return energy;
	}

	@Override
	public int getMaxEnergyStored()
	{
		return maxEnergy;
	}

	@Override
	public boolean canExtract()
	{
		return false;
	}

	@Override
	public boolean canReceive()
	{
		return true;
	}

	public int getMaxHeat()
	{
		if (!(world.getBlockState(pos).getBlock() instanceof CombustionHeaterBlock))
			return 0;
		CombustionHeaterBlock block = (CombustionHeaterBlock) world.getBlockState(pos).getBlock();

		return block.getMaximumHeat(world.getBlockState(pos));
	}

	@Override
	public void update()
	{
		if (receivedPulse() && hasValidMultiblock())
		{
			craftItem();
		}

		if (!this.world.isRemote)
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
					currentHeatValue -= 2;
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
		if (!isBlockValid(pos.add(-1, 1, 0)) || !isBlockValid(pos.add(1, 1, 0)) || !isBlockValid(pos.add(0, 2, 0))
				|| !isBlockValid(pos.add(0, 1, -1)) || !isBlockValid(pos.add(0, 1, 1))
				|| !world.isAirBlock(pos.add(0, 1, 0)))
			return false;
		return true;
	}

	public List<Material> ValidMaterialsForCrafting()
	{
		if (!(world.getBlockState(pos).getBlock() instanceof CombustionHeaterBlock))
			return null;
		IBlockState blockMeta = world.getBlockState(pos);
		List<Material> mats = new ArrayList<Material>();
		switch (world.getBlockState(pos).getBlock().getMetaFromState(blockMeta))
		{
		case 2: // STEEL
			mats.add(Material.ROCK);
			mats.add(Material.IRON);
			break;
		}
		return mats;
	}

	boolean isBlockValid(BlockPos pos)
	{
		return ValidMaterialsForCrafting().contains(world.getBlockState(pos).getMaterial())
				&& world.isBlockFullCube(pos) && world.getBlockState(pos).isOpaqueCube();
	}

	void craftItem()
	{
		ProcessRecipe recipe = recipeToCraft();
		if (recipe != null)
		{
			this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, pos.getX(), pos.getY() + 1.5D, pos.getZ(),
					0.0D, 0.0D, 0.0D, new int[0]);
			this.world.playSound((EntityPlayer) null, pos.getX(), pos.getY() + 1.5D, pos.getZ(),
					SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F,
					(1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);

			if (!world.isRemote)
			{
				List<EntityItem> list = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.getX(),
						pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getY() + 2F, pos.getZ() + 1));
				ItemStack item1 = list.get(0).getEntityItem();
				ItemStack item2 = ItemStack.EMPTY;
				for (Object recStack : recipe.getInputs())
				{
					if (item1.isItemEqual((ItemStack) recStack))
						item2 = (ItemStack) recStack;
				}

				int timesToCraft = (int) Math.floor((float) item1.getCount() / (float) item2.getCount());
				
				for (int times = 0; times < timesToCraft; times++)
				{
					if (currentHeatValue < recipe.getIntParameter())
						break;
					for (int i = 0; i < list.size(); i++)
					{
						ItemStack stack = list.get(i).getEntityItem();
						for (Object i2 : recipe.getInputs())
						{
							if (stack.isItemEqual((ItemStack) i2))
								stack.shrink(((ItemStack) i2).getCount());
						}
					}

					currentHeatValue *= 0.95F;

					ItemStack stack = recipe.getOutputs().get(0).copy();

					Entity entity = new EntityItem(world, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F,
							stack);
					world.spawnEntity(entity);
				}
			}
		}
	}

	public ProcessRecipe recipeToCraft()
	{
		List<EntityItem> list = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.getX(),
				pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1));

		List<Object> items = new ArrayList<Object>();

		for (EntityItem i : list)
		{
			items.add(i.getEntityItem());
		}

		ProcessRecipe recipe = ProcessRecipeManager.combustionRecipes.getMultiRecipe(items, currentHeatValue, true,
				true);

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

	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
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

	@Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return (T) this;
        }
        return super.getCapability(capability, facing);
    }
}
