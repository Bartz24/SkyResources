package com.bartz24.skyresources.technology.tile;

import javax.annotation.Nonnull;

import com.bartz24.skyresources.alchemy.tile.CrucibleTile;
import com.bartz24.skyresources.base.gui.ItemHandlerSpecial;
import com.bartz24.skyresources.base.tile.TileItemInventory;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;

import net.minecraft.item.ItemStack;

public class TileCrucibleInserter extends TileItemInventory
{
	public TileCrucibleInserter()
	{
		super("crucibleInserter", 1);
		this.setInventory(new ItemHandlerSpecial(1)
		{
			protected void onContentsChanged(int slot)
			{
				super.onContentsChanged(slot);
				TileCrucibleInserter.this.markDirty();
			}

			public boolean isItemValid(int slot, ItemStack stack)
			{
				if (!(getWorld().getTileEntity(pos.down()) instanceof CrucibleTile))
					return false;
				ProcessRecipe recipe = ProcessRecipeManager.crucibleRecipes.getRecipe(stack, 0, false, false);

				int amount = recipe == null ? 0 : recipe.getFluidOutputs().get(0).amount;

				CrucibleTile tile = (CrucibleTile) getWorld().getTileEntity(pos.down());

				if (tile == null)
					return false;

				if (tile.getItemAmount() + amount <= ConfigOptions.crucibleCapacity && recipe != null)
				{
					ItemStack input = (ItemStack) recipe.getInputs().get(0);

					if (tile.getTank().getFluid() == null || tile.getTank().getFluid().getFluid() == null)
					{
						return true;
					} else if (tile.itemIn == input)
					{
						return true;
					}
				}
				return false;
			}

			@Override
			public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
			{
				if (!isItemValid(slot, stack))
				{
					return stack;
				}
				ProcessRecipe recipe = ProcessRecipeManager.crucibleRecipes.getRecipe(stack, 0, false, false);

				int amount = recipe == null ? 0 : recipe.getFluidOutputs().get(0).amount;

				CrucibleTile tile = (CrucibleTile) getWorld().getTileEntity(pos.down());

				if (tile == null)
					return stack;

				if (tile.getItemAmount() + amount <= ConfigOptions.crucibleCapacity && recipe != null)
				{
					ItemStack input = (ItemStack) recipe.getInputs().get(0);
					if (tile.getTank().getFluid() == null || tile.getTank().getFluid().getFluid() == null)
					{
						tile.itemIn = input;
					}

					if (tile.itemIn == input)
					{
						if (!simulate)
							tile.itemAmount += amount;
						stack.shrink(1);
					}
				}
				return stack;
			}

			protected int getStackLimit(int slot, @Nonnull ItemStack stack)
			{
				System.out.println("HERE");
				if (!(getWorld().getTileEntity(pos.down()) instanceof CrucibleTile))
					return 0;
				ProcessRecipe recipe = ProcessRecipeManager.crucibleRecipes.getRecipe(stack, 0, false, false);

				int amount = recipe == null ? 0 : recipe.getFluidOutputs().get(0).amount;

				CrucibleTile tile = (CrucibleTile) getWorld().getTileEntity(pos.down());

				if (tile == null)
					return 0;

				if (tile.getItemAmount() + amount <= ConfigOptions.crucibleCapacity && recipe != null)
				{
					ItemStack input = (ItemStack) recipe.getInputs().get(0);

					if (tile.getTank().getFluid() == null || tile.getTank().getFluid().getFluid() == null)
					{
						tile.itemIn = input;
					}

					if (tile.itemIn == input)
					{
						return (int) Math.floor((float) ConfigOptions.crucibleCapacity / (float) amount);
					}
				}
				return 0;
			}
		});
	}
}
