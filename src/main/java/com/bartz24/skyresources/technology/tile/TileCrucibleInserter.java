package com.bartz24.skyresources.technology.tile;

import com.bartz24.skyresources.alchemy.tile.CrucibleTile;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class TileCrucibleInserter extends TileEntity implements IInventory
{
	@Override
	public ItemStack getStackInSlot(int index)
	{
		return ItemStack.EMPTY;
	}

	@Override
	public String getName()
	{
		return "container.skyresources.crucibleinserter";
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
		return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		ProcessRecipe recipe = ProcessRecipeManager.crucibleRecipes.getRecipe(stack, 0, false, false);

		int amount = recipe == null ? 0 : recipe.getFluidOutputs().get(0).amount;

		CrucibleTile tile = (CrucibleTile) this.world.getTileEntity(pos.down());

		if (tile == null)
			return;

		if (tile.getItemAmount() + amount <= ConfigOptions.crucibleCapacity && recipe != null)
		{
			ItemStack input = (ItemStack) recipe.getInputs().get(0);

			if (tile.getTank().getFluid() == null || tile.getTank().getFluid().getFluid() == null)
			{
				tile.itemIn = input;
			}

			if (tile.itemIn == input)
			{
				tile.itemAmount += amount;
				stack.shrink(1);
			}
		}
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 1;
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
		ProcessRecipe recipe = ProcessRecipeManager.crucibleRecipes.getRecipe(stack, 0, false, false);

		int amount = recipe == null ? 0 : recipe.getFluidOutputs().get(0).amount;

		CrucibleTile tile = (CrucibleTile) this.world.getTileEntity(pos.down());

		if (tile == null)
			return false;

		if (tile.getItemAmount() + amount <= ConfigOptions.crucibleCapacity && recipe != null)
		{
			ItemStack input = (ItemStack) recipe.getInputs().get(0);

			if (tile.getTank().getFluid() == null || tile.getTank().getFluid().getFluid() == null)
			{
				tile.itemIn = input;
			}

			if (tile.itemIn == input)
			{
				return true;
			}
		}
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
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStack.EMPTY;
	}

	@Override
	public boolean isEmpty()
	{
		return true;
	}
}
