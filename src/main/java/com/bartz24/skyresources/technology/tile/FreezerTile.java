package com.bartz24.skyresources.technology.tile;

import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;
import com.bartz24.skyresources.registry.ModBlocks;
import com.bartz24.skyresources.technology.block.BlockFreezer;
import com.bartz24.skyresources.technology.block.BlockMiniFreezer;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class FreezerTile extends TileEntity implements IInventory, ITickable
{
	float[] timeFreeze;
	private NonNullList<ItemStack> inventory;

	public float getFreezerSpeed()
	{
		if (world.getBlockState(pos).getBlock() == ModBlocks.miniFreezer)
			return 0.25f;
		else if (world.getBlockState(pos).getBlock() == ModBlocks.ironFreezer)
			return 1f;

		return 1;

	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		NBTTagList list = new NBTTagList();
		if (inventory != null)
		{
			for (int i = 0; i < inventory.size(); ++i)
			{
				if (this.getStackInSlot(i) != ItemStack.EMPTY)
				{
					NBTTagCompound stackTag = new NBTTagCompound();
					stackTag.setByte("Slot", (byte) i);
					this.getStackInSlot(i).writeToNBT(stackTag);
					list.appendTag(stackTag);
				} else
				{
					NBTTagCompound stackTag = new NBTTagCompound();
					stackTag.setByte("Slot", (byte) i);
					list.appendTag(stackTag);
				}
			}
			compound.setTag("Items", list);
		}

		if (timeFreeze != null)
		{
			for (int i = 0; i < timeFreeze.length; i++)
				compound.setFloat("time" + i, timeFreeze[i]);
		}
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		NBTTagList list = compound.getTagList("Items", 10);
		if (list.tagCount() > 0)
			inventory = NonNullList.<ItemStack> withSize(list.tagCount(), ItemStack.EMPTY);
		for (int i = 0; i < list.tagCount(); ++i)
		{
			NBTTagCompound stackTag = list.getCompoundTagAt(i);
			int slot = stackTag.getByte("Slot") & 255;
			System.out.println(stackTag.hasKey("id"));
			if (stackTag.hasKey("id"))
				this.setInventorySlotContents(slot, new ItemStack(stackTag));
		}

		if (inventory != null)
		{
			timeFreeze = new float[inventory.size()];
			for (int i = 0; i < this.inventory.size(); i++)
			{
				timeFreeze[i] = compound.getFloat("time" + i);
			}
		}
	}

	@Override
	public void update()
	{
		if (inventory == null)
		{
			inventory = NonNullList.<ItemStack> withSize(this.getSizeInventory(), ItemStack.EMPTY);
			return;
		} else if (timeFreeze == null)
		{
			timeFreeze = new float[this.getSizeInventory()];
			return;
		}
		updateMulti2x1();
		if (!hasValidMulti())
			return;

		for (int i = 0; i < this.getSizeInventory(); i++)
		{
			ProcessRecipe recipe = recipeToCraft(i);

			if (recipe != null)
			{
				if (timeFreeze[i] >= getTimeReq(recipe, this.inventory.get(i)))
				{
					for (int amt = 0; amt < getGroupsFreezing(recipe, this.inventory.get(i)); amt++)
					{
						ejectResultSlot(recipe.getOutputs().get(0).copy());
					}
					this.inventory.get(i).shrink(((ItemStack) recipe.getInputs().get(0)).getCount()
							* getGroupsFreezing(recipe, this.inventory.get(i)));
					if (this.inventory.get(i).getCount() <= 0)
						this.inventory.set(i, ItemStack.EMPTY);
					timeFreeze[i] = 0;
				} else
					timeFreeze[i] += getFreezerSpeed();
			} else
				timeFreeze[i] = 0;

		}
	}

	int getGroupsFreezing(ProcessRecipe recipe, ItemStack input)
	{
		return (int) Math.floor((float) input.getCount() / (float) ((ItemStack) recipe.getInputs().get(0)).getCount());
	}

	public int getTimeReq(ProcessRecipe recipe, ItemStack input)
	{
		return (int) (recipe.getIntParameter() * getGroupsFreezing(recipe, input));
	}

	void ejectResultSlot(ItemStack output)
	{
		if (!world.isRemote)
		{
			BlockPos[] checkPoses = new BlockPos[] { getPos().north(), getPos().south(), getPos().west(),
					getPos().east(), getPos().up(), getPos().down() };

			EnumFacing facing = world.getBlockState(pos).getValue(BlockFreezer.FACING);

			BlockPos facingPos = getPos().add(facing.getDirectionVec());

			TileEntity tile = world.getTileEntity(facingPos);
			if (tile instanceof IInventory)
			{
				output = RandomHelper.fillInventory((IInventory) tile, output);
			}

			for (BlockPos pos : checkPoses)
			{
				TileEntity tile2 = world.getTileEntity(pos);
				if (pos.equals(facingPos) || tile2 instanceof FreezerTile)
					continue;
				if (tile2 instanceof IInventory)
				{
					output = RandomHelper.fillInventory((IInventory) tile2, output);
				}

			}

			if (output != ItemStack.EMPTY && output.getCount() > 0)
				RandomHelper.spawnItemInWorld(world, output, facingPos);
		}
	}

	void updateMulti2x1()
	{
		IBlockState state = this.world.getBlockState(pos);
		IBlockState stateUp = this.world.getBlockState(pos.up());
		IBlockState stateDown = this.world.getBlockState(pos.down());
		if (state.getBlock() instanceof BlockFreezer)
		{
			if (stateUp.getBlock() instanceof BlockFreezer
					&& state.getProperties().get(BlockFreezer.PART) == BlockFreezer.EnumPartType.BOTTOM
					&& stateUp.getProperties().get(BlockFreezer.PART) == BlockFreezer.EnumPartType.BOTTOM)
			{
				world.setBlockState(pos.up(), stateUp.withProperty(BlockFreezer.PART, BlockFreezer.EnumPartType.TOP));
			} else if (!(stateDown.getBlock() instanceof BlockFreezer)
					&& state.getProperties().get(BlockFreezer.PART) == BlockFreezer.EnumPartType.TOP)
			{
				world.setBlockState(pos, state.withProperty(BlockFreezer.PART, BlockFreezer.EnumPartType.BOTTOM));
			}
		}
	}

	public boolean hasValidMulti()
	{
		IBlockState state = this.world.getBlockState(pos);

		if (state.getBlock() instanceof BlockMiniFreezer)
			return true;
		else if (state.getBlock() instanceof BlockFreezer)
			return validMulti2x1();
		return false;
	}

	boolean validMulti2x1()
	{
		IBlockState state = this.world.getBlockState(pos);
		IBlockState stateUp = this.world.getBlockState(pos.up());

		if (!(state.getBlock() instanceof BlockFreezer))
			return false;

		if (!(stateUp.getBlock() instanceof BlockFreezer))
			return false;

		if (state.getProperties().get(BlockFreezer.FACING) != stateUp.getProperties().get(BlockFreezer.FACING))
			return false;

		if (state.getProperties().get(BlockFreezer.PART) != BlockFreezer.EnumPartType.BOTTOM
				|| stateUp.getProperties().get(BlockFreezer.PART) != BlockFreezer.EnumPartType.TOP)
			return false;
		return true;
	}

	public ProcessRecipe recipeToCraft(int slot)
	{
		if (slot >= inventory.size())
			return null;
		ProcessRecipe recipe = ProcessRecipeManager.freezerRecipes.getRecipe(inventory.get(slot), Integer.MAX_VALUE,
				false, false);

		return recipe;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		IBlockState state = this.world.getBlockState(pos);

		FreezerTile tile = this;

		if (world.getBlockState(pos).getBlock() instanceof BlockFreezer
				&& state.getProperties().get(BlockFreezer.PART) == BlockFreezer.EnumPartType.TOP)
			tile = (FreezerTile) world.getTileEntity(pos.down());

		if (tile.getInv() == null || index < 0 || index >= this.getSizeInventory())
			return ItemStack.EMPTY;
		return tile.getInv().get(index);
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
		if (world.getBlockState(pos).getBlock() == ModBlocks.miniFreezer)
			return 1;
		else if (world.getBlockState(pos).getBlock() == ModBlocks.ironFreezer)
			return 3;

		return 0;
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		if (this.getStackInSlot(index) != ItemStack.EMPTY)
		{
			ItemStack itemstack;

			if (this.getStackInSlot(index).getCount() <= count)
			{
				itemstack = this.getStackInSlot(index);
				this.setInventorySlotContents(index, ItemStack.EMPTY);
				this.markDirty();
				return itemstack;
			} else
			{
				itemstack = this.getStackInSlot(index).splitStack(count);

				if (this.getStackInSlot(index).getCount() <= 0)
				{
					this.setInventorySlotContents(index, ItemStack.EMPTY);
				} else
				{
					this.setInventorySlotContents(index, this.getStackInSlot(index));
				}

				this.markDirty();
				return itemstack;
			}
		} else
		{
			return ItemStack.EMPTY;
		}
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		IBlockState state = null;
		if (this.world != null && this.pos != null)
			state = this.world.getBlockState(pos);

		FreezerTile tile = this;
		if (tile.inventory != null)
		{

			if (state != null && state.getBlock() instanceof BlockFreezer
					&& state.getProperties().get(BlockFreezer.PART) == BlockFreezer.EnumPartType.TOP)
				tile = (FreezerTile) world.getTileEntity(pos.down());

			if (index < 0 || index >= tile.inventory.size())
				return;

			if (stack != ItemStack.EMPTY && stack.getCount() > this.getInventoryStackLimit())
				stack.setCount(this.getInventoryStackLimit());
			if (stack != ItemStack.EMPTY && stack.getCount() == 0)
				stack = ItemStack.EMPTY;

			tile.inventory.set(index, stack);
			tile.markDirty();
		}

	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return this.world.getTileEntity(this.getPos()) == this
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
		IBlockState state = this.world.getBlockState(pos);

		if (world.getBlockState(pos).getBlock() instanceof BlockFreezer
				&& state.getProperties().get(BlockFreezer.PART) == BlockFreezer.EnumPartType.TOP)
			return world.getTileEntity(pos.down()) instanceof FreezerTile
					&& ((FreezerTile) world.getTileEntity(pos.down())).isItemValidForSlot(index, stack);

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
			this.setInventorySlotContents(i, ItemStack.EMPTY);
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(this.inventory, index);
	}

	public NonNullList<ItemStack> getInv()
	{
		return inventory;
	}

	@Override
	public boolean isEmpty()
	{
		for (ItemStack stack : this.inventory)
		{
			if (!stack.isEmpty())
			{
				return false;
			}
		}
		return true;
	}
}
