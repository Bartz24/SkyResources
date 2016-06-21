package com.bartz24.skyresources.minetweaker;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.technology.combustion.CombustionRecipe;
import com.bartz24.skyresources.technology.combustion.CombustionRecipes;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import scala.actors.threadpool.Arrays;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.skyresources.combustion")
public class MTCombustionRecipe
{

	@ZenMethod
	public static void addRecipe(IItemStack output, int heatRequired,
			IIngredient[] input)
	{
		addRecipe(new CombustionRecipe(MinetweakerPlugin.toStack(output),
				heatRequired,
				Arrays.asList(MinetweakerPlugin.toObjects(input))));
	}

	public static void addRecipe(CombustionRecipe recipe)
	{
		MineTweakerAPI.apply(new AddCombustionRecipe(recipe));
	}

	@ZenMethod
	public static void removeRecipe(IItemStack output)
	{
		MineTweakerAPI.apply(
				new RemoveCombustionRecipe(MinetweakerPlugin.toStack(output)));
	}

	private static class AddCombustionRecipe implements IUndoableAction
	{
		private final CombustionRecipe recipe;

		public AddCombustionRecipe(CombustionRecipe recipe)
		{
			this.recipe = recipe;
		}

		@Override
		public void apply()
		{
			CombustionRecipes.addRecipe(recipe);
		}

		@Override
		public boolean canUndo()
		{
			return true;
		}

		@Override
		public void undo()
		{
			CombustionRecipes.removeRecipe(recipe);
		}

		@Override
		public String describe()
		{
			return "Adding Combustion recipe for "
					+ recipe.getOutput().getDisplayName();
		}

		@Override
		public String describeUndo()
		{
			return "Removing Combustion recipe for "
					+ recipe.getOutput().getDisplayName();
		}

		@Override
		public Object getOverrideKey()
		{
			return null;
		}
	}

	public static class RemoveCombustionRecipe implements IUndoableAction
	{
		private final ItemStack output;
		List<CombustionRecipe> removedRecipes = new ArrayList<CombustionRecipe>();

		public RemoveCombustionRecipe(ItemStack output)
		{
			this.output = output;
		}

		@Override
		public void apply()
		{
			removedRecipes.addAll(CombustionRecipes
					.removeRecipe(new CombustionRecipe(output, 0)));
		}

		@Override
		public void undo()
		{
			if (removedRecipes != null)
			{
				for (CombustionRecipe recipe : removedRecipes)
				{
					if (recipe != null)
					{
						CombustionRecipes.addRecipe(recipe);
					}
				}
			}

		}

		@Override
		public String describe()
		{
			return "Removing Combustion recipe for " + output.getDisplayName();
		}

		@Override
		public String describeUndo()
		{
			return "Re-Adding Combustion recipe for " + output.getDisplayName();
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
