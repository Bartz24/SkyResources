package com.bartz24.skyresources.technology.tile;

import java.util.Collections;

import com.bartz24.skyresources.base.gui.ItemHandlerSpecial;
import com.bartz24.skyresources.base.tile.TileGenericPower;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;

public class TileRockCrusher extends TileGenericPower implements ITickable
{
	public TileRockCrusher()
	{
		super("rockCrusher", 100000, 2000, 0, 4, new Integer[] { 1, 2, 3 }, new Integer[] { 0 });
		this.setInventory(new ItemHandlerSpecial(4, new Integer[] { 1, 2, 3 }, new Integer[] { 0 })
		{
			protected void onContentsChanged(int slot)
			{
				super.onContentsChanged(slot);
				TileRockCrusher.this.markDirty();
			}
		});
	}

	private int powerUsage = ConfigOptions.machineSettings.rockCrusherPowerUsage;
	private float curProgress;

	private NonNullList<ItemStack> bufferStacks = NonNullList.create();

	@Override
	public void update()
	{
		if (!this.world.isRemote)
		{
			if (this.getRedstoneSignal() == 0)
			{
				if (bufferStacks.size() > 0 && !fullOutput())
				{
					this.addToOutput(1);
					this.addToOutput(2);
					this.addToOutput(3);
				} else
				{
					boolean hasRecipes = hasRecipes();
					if (curProgress < 100 && getEnergyStored() >= powerUsage && hasRecipes && bufferStacks.size() == 0)
					{
						internalExtractEnergy(powerUsage, false);
						curProgress += ConfigOptions.machineSettings.rockCrusherSpeed;
					} else if (!hasRecipes)
						curProgress = 0;
					if (curProgress >= 100 && hasRecipes)
					{
						ProcessRecipe recMachine = new ProcessRecipe(
								Collections.singletonList(this.getInventory().getStackInSlot(0)), Integer.MAX_VALUE,
								"rockgrinder");
						for (ProcessRecipe r : ProcessRecipeManager.rockGrinderRecipes.getRecipes())
						{
							if (r != null && recMachine.isInputRecipeEqualTo(r, false))
							{
								float chance = r.getIntParameter() * 3f;
								while (chance >= 1)
								{
									bufferStacks.add(r.getOutputs().get(0).copy());
									chance -= 1;
								}
								if (this.world.rand.nextFloat() <= chance)
									bufferStacks.add(r.getOutputs().get(0).copy());
							}
						}
						this.getInventory().getStackInSlot(0).shrink(1);
						curProgress = 0;
					}
				}
			}
			updateRedstone();
			this.markDirty();
		}
	}

	public void addToOutput(int slot)
	{
		if (bufferStacks.size() > 0)
		{
			ItemStack stack = this.getInventory().insertInternalItem(slot, bufferStacks.get(bufferStacks.size() - 1),
					false);
			bufferStacks.set(bufferStacks.size() - 1, stack);
			if (bufferStacks.get(bufferStacks.size() - 1).isEmpty())
				bufferStacks.remove(bufferStacks.size() - 1);
		}
	}

	public boolean fullOutput()
	{
		return !this.getInventory().getStackInSlot(1).isEmpty() && !this.getInventory().getStackInSlot(2).isEmpty()
				&& !this.getInventory().getStackInSlot(3).isEmpty();
	}

	public boolean hasRecipes()
	{
		if (this.getInventory().getStackInSlot(0).isEmpty())
			return false;
		ProcessRecipe recMachine = new ProcessRecipe(Collections.singletonList(this.getInventory().getStackInSlot(0)),
				Integer.MAX_VALUE, "rockgrinder");
		for (ProcessRecipe r : ProcessRecipeManager.rockGrinderRecipes.getRecipes())
		{
			if (r != null && recMachine.isInputRecipeEqualTo(r, false))
				return true;
		}
		return false;
	}

	public int getProgress()
	{
		return (int) curProgress;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound = super.writeToNBT(compound);

		compound.setTag("buffer", bufferListWrite());
		compound.setFloat("progress", curProgress);

		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		bufferListRead(compound.getCompoundTag("buffer"));
		curProgress = compound.getFloat("progress");
	}

	public NBTTagCompound bufferListWrite()
	{
		NBTTagList nbtTagList = new NBTTagList();
		for (int i = 0; i < bufferStacks.size(); i++)
		{
			if (!bufferStacks.get(i).isEmpty())
			{
				NBTTagCompound itemTag = new NBTTagCompound();
				bufferStacks.get(i).writeToNBT(itemTag);
				nbtTagList.appendTag(itemTag);
			}
		}
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setTag("Items", nbtTagList);
		return nbt;
	}

	public void bufferListRead(NBTTagCompound nbt)
	{
		NBTTagList tagList = nbt.getTagList("Items", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound itemTags = tagList.getCompoundTagAt(i);
			bufferStacks.add(new ItemStack(itemTags));
		}
	}
}
