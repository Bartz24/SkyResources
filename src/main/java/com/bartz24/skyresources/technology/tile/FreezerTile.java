package com.bartz24.skyresources.technology.tile;

import com.bartz24.skyresources.registry.ModBlocks;
import com.bartz24.skyresources.technology.block.BlockFreezer;
import com.bartz24.skyresources.technology.block.BlockFreezer.EnumPartType;
import com.bartz24.skyresources.technology.freezer.FreezerRecipe;
import com.bartz24.skyresources.technology.freezer.FreezerRecipes;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class FreezerTile extends TileEntity implements IInventory, ITickable
{
	float[] timeFreeze;
	private ItemStack[] inventory;

	public float getFreezerSpeed()
	{
		if (worldObj.getBlockState(pos).getBlock() == ModBlocks.miniFreezer)
			return 0.25f;
		else if (worldObj.getBlockState(pos)
				.getBlock() == ModBlocks.ironFreezer)
			return 1f;

		return 1;

	}

	@Override
	public void writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		if (timeFreeze != null)
		{
			for (float i : timeFreeze)
				compound.setFloat("time" + i, i);
		}

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

		if (inventory != null)
		{
			for (int i = 0; i < this.getSizeInventory(); i++)
			{
				timeFreeze[i] = compound.getFloat("time" + i);
			}
		}

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
		updateMulti2x1();
		if (!validMulti2x1())
			return;
		if (inventory == null)
		{
			inventory = new ItemStack[this.getSizeInventory()];
			timeFreeze = new float[this.getSizeInventory()];
		}

		for (int i = 0; i < this.getSizeInventory(); i++)
		{
			FreezerRecipe recipe = recipeToCraft(i);

			if (recipe != null)
			{
				if (timeFreeze[i] >= recipe.getTimeReq())
				{
					this.inventory[i] = recipe.getOutput().copy();
				} else
					timeFreeze[i] += getFreezerSpeed();
			} else
				timeFreeze[i] = 0;

		}
	}

	void updateMulti2x1()
	{
		IBlockState state = this.worldObj.getBlockState(pos);
		IBlockState stateUp = this.worldObj.getBlockState(pos.up());
		IBlockState stateDown = this.worldObj.getBlockState(pos.down());
		if (state.getBlock() instanceof BlockFreezer)
		{
			if (stateUp.getBlock() instanceof BlockFreezer
					&& state.getProperties()
							.get(BlockFreezer.PART) == BlockFreezer.EnumPartType.BOTTOM
					&& stateUp.getProperties().get(
							BlockFreezer.PART) == BlockFreezer.EnumPartType.BOTTOM)
			{
				worldObj.setBlockState(pos.up(), stateUp.withProperty(
						BlockFreezer.PART, BlockFreezer.EnumPartType.TOP));
			} else if (!(stateDown.getBlock() instanceof BlockFreezer)
					&& state.getProperties().get(
							BlockFreezer.PART) == BlockFreezer.EnumPartType.TOP)
			{
				worldObj.setBlockState(pos, state.withProperty(
						BlockFreezer.PART, BlockFreezer.EnumPartType.BOTTOM));
			}
		}
	}

	boolean validMulti2x1()
	{
		IBlockState state = this.worldObj.getBlockState(pos);
		IBlockState stateUp = this.worldObj.getBlockState(pos.up());

		if (!(state.getBlock() instanceof BlockFreezer))
			return true;

		if (!(stateUp.getBlock() instanceof BlockFreezer))
			return false;

		if (state.getProperties()
				.get(BlockFreezer.PART) != BlockFreezer.EnumPartType.BOTTOM
				|| stateUp.getProperties().get(
						BlockFreezer.PART) != BlockFreezer.EnumPartType.TOP)
			return false;

		return true;
	}

	public FreezerRecipe recipeToCraft(int slot)
	{
		FreezerRecipe recipe = FreezerRecipes.getRecipe(inventory[slot]);

		return recipe;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		if (inventory == null || index < 0 || index >= this.getSizeInventory())
			return null;
		return this.inventory[index];
	}

	@Override
	public String getName()
	{
		return "container.skyresources.freezer";
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
		if (worldObj.getBlockState(pos).getBlock() == ModBlocks.miniFreezer)
			return 1;
		else if (worldObj.getBlockState(pos)
				.getBlock() == ModBlocks.ironFreezer)
			return 3;

		return 0;
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
		return true;
	}

	@Override
	public int getField(int id)
	{
		if (timeFreeze == null || id >= timeFreeze.length)
			return 0;
		return (int) timeFreeze[id];
	}

	@Override
	public void setField(int id, int value)
	{
		if (timeFreeze == null || id >= timeFreeze.length)
			return;
		timeFreeze[id] = value;
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
