package com.bartz24.skyresources.plugin.ctweaker;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;

public class MTRecipeBase
{

	public static void addRecipe(ProcessRecipe recipe, ProcessRecipeManager type)
	{
		CraftTweakerAPI.apply(new AddSkyResourcesRecipe(recipe, type));
	}

	public static void removeRecipe(ProcessRecipe recipe, ProcessRecipeManager type)
	{
		CraftTweakerAPI.apply(new RemoveSkyResourcesRecipe(recipe, type));
	}

	private static class AddSkyResourcesRecipe implements IAction
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
			recipeType.addCTRecipe(recipe);
		}

		@Override
		public String describe()
		{
			return "Adding Sky Resources recipe for " + (recipe.getOutputs().size() > 0 ? recipe.getOutputs().get(0)
					: (recipe.getFluidOutputs().size() > 0 ? recipe.getFluidOutputs().get(0) : "NULL"));
		}
	}

	public static class RemoveSkyResourcesRecipe implements IAction
	{
		private final ProcessRecipe recipe;
		private final ProcessRecipeManager recipeType;

		public RemoveSkyResourcesRecipe(ProcessRecipe recipe, ProcessRecipeManager recipeType)
		{
			this.recipe = recipe;
			this.recipeType = recipeType;
		}

		@Override
		public void apply()
		{
			recipeType.removeCTRecipe(recipe);
		}

		@Override
		public String describe()
		{
			return "Removing Sky Resources recipe for " + (recipe.getOutputs().size() > 0 ? recipe.getOutputs().get(0)
					: (recipe.getFluidOutputs().size() > 0 ? recipe.getFluidOutputs().get(0) : "NULL"));
		}
	}
}
