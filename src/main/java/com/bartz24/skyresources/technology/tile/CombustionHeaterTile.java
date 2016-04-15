package com.bartz24.skyresources.technology.tile;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.api.RedstoneCompatibleTile;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.technology.block.CombustionHeaterBlock;
import com.bartz24.skyresources.technology.combustion.CombustionRecipe;
import com.bartz24.skyresources.technology.combustion.CombustionRecipes;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class CombustionHeaterTile extends RedstoneCompatibleTile
		implements IInventory, ITickable
{
	private ItemStack[] inventory = new ItemStack[1];

	public int currentHeatValue;

	private int fuelBurnTime;
	private int heatPerTick;

	private int currentItemBurnTime;

	public List<Material> ValidMaterialsForCrafting()
	{
		if (!(worldObj.getBlockState(pos)
				.getBlock() instanceof CombustionHeaterBlock))
			return null;
		IBlockState blockMeta = worldObj.getBlockState(pos);
		List<Material> mats = new ArrayList<Material>();
		switch (worldObj.getBlockState(pos).getBlock()
				.getMetaFromState(blockMeta))
		{
		case 0: // WOOD
			mats.add(Material.wood);
			break;
		case 1: // IRON
			mats.add(Material.clay);
			mats.add(Material.iron);
			mats.add(Material.rock);
			break;
		}
		return mats;
	}

	public int getMaxHeat()
	{
		if (!(worldObj.getBlockState(pos)
				.getBlock() instanceof CombustionHeaterBlock))
			return 0;
		CombustionHeaterBlock block = (CombustionHeaterBlock) worldObj
				.getBlockState(pos).getBlock();

		return block.getMaximumHeat(worldObj.getBlockState(pos));
	}

	public int getMaxHeatPerTick()
	{
		if (!(worldObj.getBlockState(pos)
				.getBlock() instanceof CombustionHeaterBlock))
			return 0;
		CombustionHeaterBlock block = (CombustionHeaterBlock) worldObj
				.getBlockState(pos).getBlock();

		switch (block.getMetaFromState(worldObj.getBlockState(pos)))
		{
		case 0:
			return 8;
		case 1:
			return 16;
		}

		return 0;
	}

	public int getHeatPerTick(ItemStack stack)
	{
		int fuelTime = TileEntityFurnace.getItemBurnTime(stack);
		if (fuelTime > 0)
		{
			return (int) Math.cbrt((float) fuelTime * ConfigOptions.combustionHeatMultiplier);
		}

		return 0;
	}

	public int getFuelBurnTime(ItemStack stack)
	{
		if ((float) getHeatPerTick(stack) <= 0)
			return 0;

		return (int) ((float) Math.pow(TileEntityFurnace.getItemBurnTime(stack),
				0.75F) / getHeatPerTick(stack));
	}

	public boolean isValidFuel(ItemStack stack)
	{
		if (TileEntityFurnace.getItemBurnTime(stack) <= 0
				|| getHeatPerTick(stack) <= 0
				|| getHeatPerTick(stack) > getMaxHeatPerTick()
				|| getFuelBurnTime(stack) <= 0)
			return false;

		return true;
	}

	@Override
	public void writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		compound.setInteger("heat", currentHeatValue);
		compound.setInteger("fuel", fuelBurnTime);
		compound.setInteger("item", currentItemBurnTime);
		compound.setInteger("hpt", heatPerTick);

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
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		currentHeatValue = compound.getInteger("heat");
		fuelBurnTime = compound.getInteger("fuel");
		currentItemBurnTime = compound.getInteger("item");
		heatPerTick = compound.getInteger("hpt");

		NBTTagList list = compound.getTagList("Items", 10);
		for (int i = 0; i < list.tagCount(); ++i)
		{
			NBTTagCompound stackTag = list.getCompoundTagAt(i);
			int slot = stackTag.getByte("Slot") & 255;
			this.setInventorySlotContents(slot,
					ItemStack.loadItemStackFromNBT(stackTag));
		}
	}

	@Override
	public void update()
	{
		if (receivedPulse() && hasValidMultiblock())
		{
			craftItem();
		}

		if (!worldObj.isRemote)
			prevRedstoneSignal = getRedstoneSignal();

		if (fuelBurnTime > 0)
		{
			this.fuelBurnTime--;
		} else
			this.currentItemBurnTime = this.heatPerTick = this.fuelBurnTime = 0;

		if (!this.worldObj.isRemote)
		{
			if (fuelBurnTime > 0 || this.inventory[0] != null)
			{
				if (fuelBurnTime == 0 && currentHeatValue < getMaxHeat()
						&& isValidFuel(inventory[0]))
				{
					this.currentItemBurnTime = this.fuelBurnTime = getFuelBurnTime(
							inventory[0]);
					heatPerTick = getHeatPerTick(inventory[0]);

					if (fuelBurnTime > 0)
					{
						if (this.inventory[0] != null)
						{
							this.inventory[0].stackSize--;

							if (this.inventory[0].stackSize == 0)
							{
								this.inventory[0] = inventory[0].getItem()
										.getContainerItem(inventory[0]);
							}
						}
					}
				}

				if (fuelBurnTime > 0 && currentHeatValue < getMaxHeat())
				{
					currentHeatValue += heatPerTick;
				}
			}

			if (!hasValidMultiblock())
			{
				if (currentHeatValue > 0)
					currentHeatValue--;
			}

			if (currentHeatValue > getMaxHeat())
				currentHeatValue = getMaxHeat();
		}
	}

	public boolean hasValidMultiblock()
	{
		List<Material> materials = ValidMaterialsForCrafting();
		if (!materials.contains(
				worldObj.getBlockState(pos.add(-1, 1, 0)).getMaterial())
				|| !worldObj.isBlockFullCube(pos.add(-1, 1, 0))
				|| !materials.contains(
						worldObj.getBlockState(pos.add(1, 1, 0)).getMaterial())
				|| !worldObj.isBlockFullCube(pos.add(1, 1, 0))
				|| !materials.contains(
						worldObj.getBlockState(pos.add(0, 2, 0)).getMaterial())
				|| !worldObj.isBlockFullCube(pos.add(0, 2, 0))
				|| !materials.contains(
						worldObj.getBlockState(pos.add(0, 1, -1)).getMaterial())
				|| !worldObj.isBlockFullCube(pos.add(0, 1, -1))
				|| !materials.contains(
						worldObj.getBlockState(pos.add(0, 1, 1)).getMaterial())
				|| !worldObj.isBlockFullCube(pos.add(0, 1, 1))
				|| !worldObj.isAirBlock(pos.add(0, 1, 0)))
			return false;
		return true;
	}

	void craftItem()
	{
		CombustionRecipe recipe = recipeToCraft();
		if (recipe != null)
		{
			System.out.println("HERE");
			this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL,
					pos.getX(), pos.getY() + 1.5D, pos.getZ(), 0.0D, 0.0D, 0.0D,
					new int[0]);
			this.worldObj
					.playSound((EntityPlayer) null, pos.getX(),
							pos.getY() + 1.5D, pos.getZ(),
							SoundEvents.entity_generic_explode,
							SoundCategory.BLOCKS, 4.0F,
							(1.0F + (this.worldObj.rand.nextFloat()
									- this.worldObj.rand.nextFloat()) * 0.2F)
									* 0.7F);

			if (!worldObj.isRemote)
			{
				List<EntityItem> list = worldObj.getEntitiesWithinAABB(
						EntityItem.class,
						new AxisAlignedBB(pos.getX(), pos.getY() + 1,
								pos.getZ(), pos.getX() + 1, pos.getY() + 1.5F,
								pos.getZ() + 1));

				List<String> itemIgnores = new ArrayList<String>();
				for (EntityItem i : list)
				{
					ItemStack stack = i.getEntityItem();

					for (ItemStack s : recipe.getInputStacks())
					{
						if (stack.isItemEqual(s) && !itemIgnores
								.contains(s.getUnlocalizedName()))
						{
							stack.stackSize -= s.stackSize;
							itemIgnores.add(s.getUnlocalizedName());
						}
					}
				}

				currentHeatValue *= 0.8F;

				ItemStack stack = recipe.getOutput().copy();

				Entity entity = new EntityItem(worldObj, pos.getX() + 0.5F,
						pos.getY() + 0.5F, pos.getZ() + 0.5F, stack);
				worldObj.spawnEntityInWorld(entity);
			}
		}
	}

	public CombustionRecipe recipeToCraft()
	{
		List<EntityItem> list = worldObj.getEntitiesWithinAABB(EntityItem.class,
				new AxisAlignedBB(pos.getX(), pos.getY() + 1, pos.getZ(),
						pos.getX() + 1, pos.getY() + 1.5F, pos.getZ() + 1));

		List<ItemStack> items = new ArrayList<ItemStack>();

		for (EntityItem i : list)
		{
			items.add(i.getEntityItem());
		}

		CombustionRecipe recipe = CombustionRecipes.getRecipe(items,
				currentHeatValue);

		return recipe;
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
		return "container.skyresources.combustionHeater";
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
		return 1;
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
		return this.isValidFuel(stack);
	}

	@Override
	public int getField(int id)
	{
		switch (id)
		{
		case 0:
			return fuelBurnTime;
		case 1:
			return currentItemBurnTime;
		case 2:
			return currentHeatValue;
		case 3:
			return heatPerTick;
		}
		return 0;
	}

	@Override
	public void setField(int id, int value)
	{
		switch (id)
		{
		case 0:
			fuelBurnTime = value;
		case 1:
			currentItemBurnTime = value;
		case 2:
			currentHeatValue = value;
		case 3:
			heatPerTick = value;
		}
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
		return ItemStackHelper.func_188383_a(this.inventory, index);
	}
}
