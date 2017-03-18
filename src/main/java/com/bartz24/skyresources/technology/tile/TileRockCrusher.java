package com.bartz24.skyresources.technology.tile;

import java.util.Collections;
import java.util.List;

import com.bartz24.skyresources.base.gui.ItemHandlerSpecial;
import com.bartz24.skyresources.base.tile.TileGenericPower;
import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;
import com.bartz24.skyresources.technology.item.ItemRockGrinder;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.oredict.OreDictionary;

public class TileRockCrusher extends TileGenericPower implements ITickable
{
	public TileRockCrusher()
	{
		super("rockCrusher", 100000, 2000, 0, 5, new Integer[] { 2, 3, 4 }, new Integer[] { 0, 1 });
		this.setInventory(new ItemHandlerSpecial(5, new Integer[] { 2, 3, 4 }, new Integer[] { 0, 1 })
		{
			protected void onContentsChanged(int slot)
			{
				super.onContentsChanged(slot);
				TileRockCrusher.this.markDirty();
			}

			public boolean isItemValid(int slot, ItemStack stack)
			{
				if (slot == 0)
					return stack.getItem() instanceof ItemRockGrinder;
				return super.isItemValid(slot, stack);
			}
		});
	}

	private int powerUsage = 80;
	private int curProgress;

	private NonNullList<ItemStack> bufferStacks = NonNullList.create();

	@Override
	public void update()
	{
		if (!this.world.isRemote)
		{
			if (bufferStacks.size() > 0 && !fullOutput())
			{
				this.addToOutput(2);
				this.addToOutput(3);
				this.addToOutput(4);
			} else
			{
				boolean hasRecipes = hasRecipes();
				if (curProgress < 100 && getEnergyStored() >= powerUsage && hasRecipes && hasRockGrinder()
						&& bufferStacks.size() == 0)
				{
					internalExtractEnergy(powerUsage, false);
					int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY,
							this.getInventory().getStackInSlot(0));
					int base = (int) ((ItemRockGrinder) this.getInventory().getStackInSlot(0).getItem())
							.getToolMaterial().getEfficiencyOnProperMaterial();
					curProgress += Math.floor(Math.sqrt(base * (level + 1)));
				} else if (!hasRecipes)
					curProgress = 0;
				if (curProgress >= 100 && hasRecipes && hasRockGrinder())
				{
					ProcessRecipe recMachine = new ProcessRecipe(Collections.singletonList(this.getInventory().getStackInSlot(1)),
							Integer.MAX_VALUE, "rockgrinder");
					for (ProcessRecipe r : ProcessRecipeManager.rockGrinderRecipes.getRecipes())
					{
						if (r != null && recMachine.isInputRecipeEqualTo(r, false))
						{
							int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE,
									this.getInventory().getStackInSlot(0));
							float chance = r.getIntParameter() * (((float) level + 3F) / 3F);
							while (chance >= 1)
							{
								bufferStacks.add(r.getOutputs().get(0).copy());
								chance -= 1;
							}
							if (this.world.rand.nextFloat() <= chance)
								bufferStacks.add(r.getOutputs().get(0).copy());
						}
					}
					this.getInventory().getStackInSlot(1).shrink(1);
					if (this.getInventory().getStackInSlot(0).attemptDamageItem(1, this.world.rand))
						this.getInventory().setStackInSlot(0, ItemStack.EMPTY);
					curProgress = 0;
				}
			}
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
		return !this.getInventory().getStackInSlot(2).isEmpty() && !this.getInventory().getStackInSlot(3).isEmpty()
				&& !this.getInventory().getStackInSlot(4).isEmpty();
	}

	public boolean hasRecipes()
	{
		if (this.getInventory().getStackInSlot(1).isEmpty())
			return false;
		ProcessRecipe recMachine = new ProcessRecipe(Collections.singletonList(this.getInventory().getStackInSlot(1)),
				Integer.MAX_VALUE, "rockgrinder");
		for (ProcessRecipe r : ProcessRecipeManager.rockGrinderRecipes.getRecipes())
		{
			if (r != null && recMachine.isInputRecipeEqualTo(r, false))
				return true;
		}
		return false;
	}

	public boolean hasRockGrinder()
	{
		if (this.getInventory().getStackInSlot(0).getItem() instanceof ItemRockGrinder)
		{
			return this.getInventory().getStackInSlot(0).getItemDamage() <= this.getInventory().getStackInSlot(0)
					.getMaxDamage();
		}
		return false;
	}

	public int getProgress()
	{
		return curProgress;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound = super.writeToNBT(compound);

		compound.setTag("buffer", bufferListWrite());
		compound.setInteger("progress", curProgress);

		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		bufferListRead(compound.getCompoundTag("buffer"));
		curProgress = compound.getInteger("progress");
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
