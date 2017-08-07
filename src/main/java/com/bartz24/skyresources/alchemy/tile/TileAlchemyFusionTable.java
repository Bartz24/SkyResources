package com.bartz24.skyresources.alchemy.tile;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.alchemy.FusionCatalysts;
import com.bartz24.skyresources.base.gui.ItemHandlerSpecial;
import com.bartz24.skyresources.base.tile.TileItemInventory;
import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.oredict.OreDictionary;

public class TileAlchemyFusionTable extends TileItemInventory implements ITickable
{
	private NonNullList<ItemStack> filter = NonNullList.withSize(9, ItemStack.EMPTY);

	private float yieldAmount;
	private float curCatalystYield;
	private float curCatalystLeft;
	private int curProgress;

	public TileAlchemyFusionTable()
	{
		super("fusionTable", 11, new Integer[] { 10 }, new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 });
		this.setInventory(
				new ItemHandlerSpecial(11, new Integer[] { 10 }, new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 })
				{
					protected void onContentsChanged(int slot)
					{
						super.onContentsChanged(slot);
						TileAlchemyFusionTable.this.markDirty();
					}

					public boolean isItemValid(int slot, ItemStack stack)
					{
						if (slot == 0)
							return FusionCatalysts.isCatalyst(stack);
						else if (slot < 10)
							return stack.isItemEqual(TileAlchemyFusionTable.this.filter.get(slot - 1));
						return super.isItemValid(slot, stack);
					}
				});
	}

	private void updateCrafting()
	{
		ProcessRecipe recipe = ProcessRecipeManager.fusionRecipes.getRecipe(getStacksForRecipe(), Integer.MAX_VALUE,
				false, false);

		if (curProgress < 100 && recipe != null && this.curCatalystLeft >= recipe.getIntParameter()
				&& this.getInventory().insertInternalItem(10, recipe.getOutputs().get(0).copy(), true).isEmpty())
		{
			curProgress += 1;
			this.curCatalystLeft -= recipe.getIntParameter();
		} else
			curProgress = 0;
		if (recipe != null && this.curCatalystLeft < recipe.getIntParameter())
		{
			if (FusionCatalysts.isCatalyst(this.getInventory().getStackInSlot(0)))
			{
				this.curCatalystLeft += 1;
				this.curCatalystYield = FusionCatalysts.getCatalystValue(this.getInventory().getStackInSlot(0));
				this.getInventory().getStackInSlot(0).shrink(1);
			}
		}
		if (curProgress >= 100)
		{
			this.yieldAmount += this.curCatalystYield;
			if (this.yieldAmount >= 1)
			{
				ItemStack output = recipe.getOutputs().get(0).copy();
				output.setCount((int) (output.getCount() * Math.floor(this.yieldAmount)));
				this.getInventory().insertInternalItem(10, output, false);
				this.yieldAmount -= Math.floor(this.yieldAmount);
			}
			for (int i = 0; i < 9; i++)
			{
				for (Object o : recipe.getInputs())
				{
					List<ItemStack> stacks = new ArrayList();
					if (o instanceof String && OreDictionary.getOres((String) o).size() > 0)
						for(ItemStack s : OreDictionary.getOres((String) o))
						stacks.add(s);
					else if (o instanceof ItemStack)
						stacks.add((ItemStack) o);
					boolean success = false;
					for (ItemStack s : stacks)
						if (!s.isEmpty() && s.isItemEqual(this.getInventory().getStackInSlot(i + 1)))
						{
							this.getInventory().getStackInSlot(i + 1).shrink(s.getCount());
							success = true;
							break;
						}
					if (success)
						break;
				}

			}

			curProgress = 0;
		}
	}

	private List<Object> getStacksForRecipe()
	{
		List<Object> stacks = new ArrayList();
		for (int i = 0; i < 9; i++)
		{
			if (!this.getInventory().getStackInSlot(i + 1).isEmpty())
			{
				stacks.add(this.getInventory().getStackInSlot(i + 1));
			}
		}
		return stacks;
	}

	@Override
	public void update()
	{
		if (!world.isRemote)
		{
			updateCrafting();
			this.markDirty();
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound = super.writeToNBT(compound);

		compound.setFloat("yield", yieldAmount);
		compound.setFloat("itemYield", curCatalystYield);
		compound.setFloat("itemLeft", curCatalystLeft);
		compound.setInteger("progress", curProgress);
		compound.setTag("filter", writeFilter());
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		yieldAmount = compound.getFloat("yield");
		curCatalystYield = compound.getFloat("itemYield");
		curCatalystLeft = compound.getFloat("itemLeft");
		curProgress = compound.getInteger("progress");
		readFilter(compound.getCompoundTag("filter"));
	}

	public NBTTagCompound writeFilter()
	{
		NBTTagList nbtTagList = new NBTTagList();
		for (int i = 0; i < filter.size(); i++)
		{
			if (!filter.get(i).isEmpty())
			{
				NBTTagCompound itemTag = new NBTTagCompound();
				itemTag.setInteger("Slot", i);
				filter.get(i).writeToNBT(itemTag);
				nbtTagList.appendTag(itemTag);
			}
		}
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setTag("Items", nbtTagList);
		nbt.setInteger("Size", filter.size());
		return nbt;
	}

	public void readFilter(NBTTagCompound nbt)
	{
		filter = NonNullList.withSize(
				nbt.hasKey("Size", Constants.NBT.TAG_INT) ? nbt.getInteger("Size") : filter.size(), ItemStack.EMPTY);
		NBTTagList tagList = nbt.getTagList("Items", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound itemTags = tagList.getCompoundTagAt(i);
			int slot = itemTags.getInteger("Slot");

			if (slot >= 0 && slot < filter.size())
			{
				filter.set(slot, new ItemStack(itemTags));
			}
		}
	}

	public void setFilter(int index, ItemStack val)
	{
		this.filter.set(index, val);
	}

	public ItemStack getFilterStack(int index)
	{
		return filter.get(index);
	}

	public int getProgress()
	{
		return curProgress;
	}

	public float getCurYield()
	{
		return yieldAmount;
	}

	public float getCurItemYield()
	{
		return curCatalystYield;
	}

	public float getCurItemLeft()
	{
		return curCatalystLeft;
	}
}
