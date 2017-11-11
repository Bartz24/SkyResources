package com.bartz24.skyresources.technology.tile;

import com.bartz24.skyresources.base.tile.TileItemInventory;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MiniFreezerTile extends TileItemInventory implements ITickable
{
	public MiniFreezerTile()
	{
		super("freezer", 2, new Integer[] { 1 }, new Integer[] { 0 });
	}

	public MiniFreezerTile(int slots, Integer[] noInsert, Integer[] noExtract)
	{
		super("freezer", slots, noInsert, noExtract);
	}

	float[] timeFreeze;

	public float getFreezerSpeed()
	{
		return ConfigOptions.machineSettings.miniFreezerSpeed;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

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

		timeFreeze = new float[getInventory().getSlots()];
		for (int i = 0; i < this.getInventory().getSlots(); i++)
		{
			timeFreeze[i] = compound.getFloat("time" + i);
		}
	}

	@Override
	public void update()
	{
		if (!world.isRemote)
		{
			if (timeFreeze == null)
			{
				timeFreeze = new float[getInventory().getSlots()];
				return;
			}
			updateMulti2x1();
			if (!hasValidMulti())
				return;

			for (int i = 0; i < this.getInventory().getSlots() / 2; i++)
			{
				ProcessRecipe recipe = recipeToCraft(i);

				if (recipe != null
						&& canProcess(recipe.getOutputs().get(0).copy(), i + this.getInventory().getSlots() / 2))
				{
					if (timeFreeze[i] >= getTimeReq(recipe, this.getInventory().getStackInSlot(i)))
					{
						int amtProcessed = 0;
						for (int amt = 0; amt < getGroupsFreezing(recipe, this.getInventory().getStackInSlot(i)); amt++)
						{
							if (ejectResultSlot(recipe.getOutputs().get(0).copy(), i))
								amtProcessed++;
						}
						this.getInventory().getStackInSlot(i)
								.shrink(((ItemStack) recipe.getInputs().get(0)).getCount() * amtProcessed);
						if (this.getInventory().getStackInSlot(i).getCount() <= 0)
							this.getInventory().setStackInSlot(i, ItemStack.EMPTY);
						timeFreeze[i] = 0;
					} else
						timeFreeze[i] += getFreezerSpeed() * 10;
				} else
					timeFreeze[i] = 0;

			}
			this.markDirty();
		}
	}

	private boolean canProcess(ItemStack output, int slotOut)
	{
		if (this.getInventory().getStackInSlot(slotOut).isEmpty())
			return true;
		if (!this.getInventory().getStackInSlot(slotOut).isItemEqual(output))
			return false;
		int result = getInventory().getStackInSlot(slotOut).getCount() + output.getCount();
		return result <= getInventory().getSlotLimit(slotOut)
				&& result <= getInventory().getStackInSlot(slotOut).getMaxStackSize();

	}

	int getGroupsFreezing(ProcessRecipe recipe, ItemStack input)
	{
		return (int) Math.floor((float) input.getCount() / (float) ((ItemStack) recipe.getInputs().get(0)).getCount());
	}

	public int getTimeReq(ProcessRecipe recipe, ItemStack input)
	{
		return (int) (recipe.getIntParameter() * getGroupsFreezing(recipe, input));
	}

	boolean ejectResultSlot(ItemStack output, int inSlot)
	{
		if (canProcess(output, inSlot + this.getInventory().getSlots() / 2))
		{
			this.getInventory().insertInternalItem(inSlot + this.getInventory().getSlots() / 2, output, false);
			return true;
		}
		return false;
	}

	void updateMulti2x1()
	{
	}

	public boolean hasValidMulti()
	{
		return true;
	}

	public ProcessRecipe recipeToCraft(int slot)
	{
		if (slot >= getInventory().getSlots())
			return null;
		ProcessRecipe recipe = ProcessRecipeManager.freezerRecipes.getRecipe(getInventory().getStackInSlot(slot),
				Integer.MAX_VALUE, false, false);

		return recipe;
	}

	public int getUpdateIdSize()
	{
		return getInventory().getSlots();
	}

	public int getUpdateData(int id)
	{
		if (timeFreeze == null)
			return 0;
		return (int) timeFreeze[id];
	}

	@SideOnly(Side.CLIENT)
	public void setClientUpdate(int id, int data)
	{
		if (timeFreeze != null)
			timeFreeze[id] = data;
	}
}
