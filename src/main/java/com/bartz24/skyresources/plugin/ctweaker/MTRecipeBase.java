package com.bartz24.skyresources.plugin.ctweaker;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;

public class MTRecipeBase
{

	public static void addRecipe(ProcessRecipe recipe, ProcessRecipeManager type)
	{
		MineTweakerAPI.apply(new AddSkyResourcesRecipe(recipe, type));
	}

	public static void removeRecipe(ProcessRecipe recipe, ProcessRecipeManager type)
	{
		MineTweakerAPI.apply(new RemoveSkyResourcesRecipe(recipe, type));
	}

	private static class AddSkyResourcesRecipe implements IUndoableAction
	{
		private final ProcessRecipe recipe;
		private final ProcessRecipeManager recipeType;

		public AddSkyResourcesRecipe(ProcessRecipe recipe, ProcessRecipeManager type)
		{
			this.recipe = recipe;
			recipeType = type;
		}

		@Override
		public void apply()
		{
			recipeType.addRecipe(recipe);
			MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(recipe);
		}

		@Override
		public boolean canUndo()
		{
			return true;
		}

		@Override
		public void undo()
		{
			recipeType.removeRecipe(recipe);
			MineTweakerAPI.getIjeiRecipeRegistry().removeRecipe(recipe);
		}

		@Override
		public String describe()
		{
			return "Adding Sky Resources recipe for " + (recipe.getOutputs().size() > 0 ? recipe.getOutputs().get(0)
					: (recipe.getFluidOutputs().size() > 0 ? recipe.getFluidOutputs().get(0) : "NULL"));
		}

		@Override
		public String describeUndo()
		{
			return "Removing Sky Resources recipe for " + (recipe.getOutputs().size() > 0 ? recipe.getOutputs().get(0)
					: (recipe.getFluidOutputs().size() > 0 ? recipe.getFluidOutputs().get(0) : "NULL"));
		}

		@Override
		public Object getOverrideKey()
		{
			return null;
		}
	}

	public static class RemoveSkyResourcesRecipe implements IUndoableAction
	{
		private final ProcessRecipe recipe;
		List<ProcessRecipe> removedRecipes = new ArrayList<ProcessRecipe>();
		private final ProcessRecipeManager recipeType;

		public RemoveSkyResourcesRecipe(ProcessRecipe recipe, ProcessRecipeManager recipeType)
		{
			this.recipe = recipe;
			this.recipeType = recipeType;
		}

		@Override
		public void apply()
		{
			removedRecipes.addAll(recipeType.removeRecipe(recipe));
			for (ProcessRecipe recipe2 : removedRecipes)
				MineTweakerAPI.getIjeiRecipeRegistry().removeRecipe(recipe2);
		}

		@Override
		public void undo()
		{
			if (removedRecipes != null)
			{
				for (ProcessRecipe recipe : removedRecipes)
				{
					if (recipe != null)
					{
						recipeType.addRecipe(recipe);
						MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(recipe);
					}
				}
			}

		}

		@Override
		public String describe()
		{
			return "Removing Sky Resources recipe for " + (recipe.getOutputs().size() > 0 ? recipe.getOutputs().get(0)
					: (recipe.getFluidOutputs().size() > 0 ? recipe.getFluidOutputs().get(0) : "NULL"));
		}

		@Override
		public String describeUndo()
		{
			return "Re-Adding Sky Resources recipe for " + (recipe.getOutputs().size() > 0 ? recipe.getOutputs().get(0)
					: (recipe.getFluidOutputs().size() > 0 ? recipe.getFluidOutputs().get(0) : "NULL"));
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
