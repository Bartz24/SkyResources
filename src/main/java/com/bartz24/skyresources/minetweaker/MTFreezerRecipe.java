package com.bartz24.skyresources.minetweaker;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.ItemHelper;
import com.bartz24.skyresources.technology.freezer.FreezerRecipe;
import com.bartz24.skyresources.technology.freezer.FreezerRecipes;
import com.bartz24.skyresources.technology.rockgrinder.RockGrinderRecipe;
import com.bartz24.skyresources.technology.rockgrinder.RockGrinderRecipes;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.skyresources.freezer")
public class MTFreezerRecipe
{

	@ZenMethod
	public static void addRecipe(IItemStack output, IItemStack input, int time)
	{
		addRecipe(new FreezerRecipe(
				MinetweakerPlugin.toStack(output), time,
				MinetweakerPlugin.toStack(input)));
	}

	public static void addRecipe(FreezerRecipe recipe)
	{
		MineTweakerAPI.apply(new AddFreezerRecipe(recipe));
	}

	@ZenMethod
	public static void removeRecipe(IItemStack output)
	{
		MineTweakerAPI.apply(
				new RemoveFreezerRecipe(MinetweakerPlugin.toStack(output)));
	}

	private static class AddFreezerRecipe implements IUndoableAction
	{
		private final FreezerRecipe recipe;

		public AddFreezerRecipe(FreezerRecipe recipe)
		{
			this.recipe = recipe;
		}

		@Override
		public void apply()
		{
			FreezerRecipes.addRecipe(recipe);
		}

		@Override
		public boolean canUndo()
		{
			return true;
		}

		@Override
		public void undo()
		{
			FreezerRecipes.removeRecipe(recipe);
		}

		@Override
		public String describe()
		{
			return "Adding Freezer recipe for " + recipe.getOutput();
		}

		@Override
		public String describeUndo()
		{
			return "Removing Freezer recipe for " + recipe.getOutput();
		}

		@Override
		public Object getOverrideKey()
		{
			return null;
		}
	}

	public static class RemoveFreezerRecipe implements IUndoableAction
	{
		private final ItemStack output;
		List<FreezerRecipe> removedRecipes = new ArrayList<FreezerRecipe>();

		public RemoveFreezerRecipe(ItemStack output)
		{
			this.output = output;
		}

		@Override
		public void apply()
		{
			removedRecipes.addAll(
					FreezerRecipes.removeRecipe(new FreezerRecipe(output, 0, null)));
		}

		@Override
		public void undo()
		{
			if (removedRecipes != null)
			{
				for (FreezerRecipe recipe : removedRecipes)
				{
					if (recipe != null)
					{
						FreezerRecipes.addRecipe(recipe);
					}
				}
			}

		}

		@Override
		public String describe()
		{
			return "Removing Freezer recipe for " + output;
		}

		@Override
		public String describeUndo()
		{
			return "Re-Adding Freezer recipe for " + output;
		}

		@Override
		public Object getOverrideKey()
		{
			return null;
		}

		@Override
		public boolean canUndo()
		{
			return true;
		}
	}
}
