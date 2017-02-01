package com.bartz24.skyresources.recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.SkyResources;

import net.minecraft.item.ItemStack;

public class ProcessRecipeManager
{

	public static ProcessRecipeManager combustionRecipes = new ProcessRecipeManager();
	public static ProcessRecipeManager infusionRecipes = new ProcessRecipeManager();
	public static ProcessRecipeManager rockGrinderRecipes = new ProcessRecipeManager();
	public static ProcessRecipeManager crucibleRecipes = new ProcessRecipeManager();
	public static ProcessRecipeManager freezerRecipes = new ProcessRecipeManager();
	public static ProcessRecipeManager waterExtractorInsertRecipes = new ProcessRecipeManager();
	public static ProcessRecipeManager waterExtractorExtractRecipes = new ProcessRecipeManager();
	public static ProcessRecipeManager cauldronCleanRecipes = new ProcessRecipeManager();

	public ProcessRecipeManager()
	{
		recipes = new ArrayList();
	}

	private List<ProcessRecipe> recipes;

	public ProcessRecipe getRecipe(List<Object> input, float intVal, boolean forceEqual, boolean mergeStacks)
	{
		input = mergeStacks ? mergeStacks(input) : input;

		ProcessRecipe rec = new ProcessRecipe(input, intVal);

		for (ProcessRecipe recipe : recipes)
		{
			if (rec.isInputRecipeEqualTo(recipe, forceEqual))
			{
				return recipe;
			}
		}

		return null;
	}
	
	public ProcessRecipe getRecipe(Object input, float intVal, boolean forceEqual, boolean mergeStacks)
	{
		List<Object> inputs = mergeStacks ? mergeStacks(Collections.singletonList(input)) : Collections.singletonList(input);

		ProcessRecipe rec = new ProcessRecipe(inputs, intVal);

		for (ProcessRecipe recipe : recipes)
		{
			if (rec.isInputRecipeEqualTo(recipe, forceEqual))
			{
				return recipe;
			}
		}

		return null;
	}

	public ProcessRecipe getMultiRecipe(List<Object> input, float intVal, boolean forceEqual, boolean mergeStacks)
	{
		input = mergeStacks ? mergeStacks(input) : input;

		ProcessRecipe rec = new ProcessRecipe(input, intVal);

		for (ProcessRecipe recipe : recipes)
		{
			if (rec.isInputMultiRecipeEqualTo(recipe))
			{
				return recipe;
			}
		}

		return null;
	}

	private List<Object> mergeStacks(List<Object> input)
	{
		int checks = 0;
		boolean merged = true;
		while (merged && checks < 50)
		{
			merged = false;
			for (int i = 0; i < input.size(); i++)
			{
				if (!(input.get(i) instanceof ItemStack))
					continue;
				ItemStack stack1 = (ItemStack) input.get(i);
				for (int i2 = i + 1; i2 < input.size(); i2++)
				{
					if (!(input.get(i2) instanceof ItemStack))
						continue;
					ItemStack stack2 = (ItemStack) input.get(i2);

					int moveAmt = RandomHelper.mergeStacks(stack2, stack1, false);
					if (moveAmt > 0)
					{
						stack1.grow(moveAmt);
						stack2.shrink(moveAmt);
						if (stack2.getCount() <= 0)
							stack2 = ItemStack.EMPTY;
						merged = true;
						break;
					}
				}
				if (merged)
					break;
			}

			for (int i = input.size() - 1; i >= 0; i--)
			{
				if (!(input.get(i) instanceof ItemStack))
					continue;
				ItemStack stack = (ItemStack) input.get(i);
				if (stack == ItemStack.EMPTY)
					input.remove(i);
			}

			checks++;
		}

		return input;
	}

	public List<ProcessRecipe> getRecipes()
	{
		return recipes;
	}

	public void addRecipe(List<Object> output, float intVal, List<Object> input)
	{

		if (input == null)
		{
			SkyResources.logger.error("Need inputs for recipe. DID NOT ADD RECIPE.");
			return;
		}

		if (output == null)
		{
			SkyResources.logger.error("Need outputs for recipe. DID NOT ADD RECIPE.");
			return;
		}

		recipes.add(new ProcessRecipe(output, input, intVal));
	}

	public void addRecipe(Object output, float intVal, Object input)
	{
		if (input == null)
		{
			SkyResources.logger.error("Need inputs for recipe. DID NOT ADD RECIPE.");
			return;
		}

		if (output == null)
		{
			SkyResources.logger.error("Need outputs for recipe. DID NOT ADD RECIPE.");
			return;
		}

		recipes.add(new ProcessRecipe(Collections.singletonList(output), Collections.singletonList(input), intVal));
	}

	public void addRecipe(List<Object> output, float intVal, Object input)
	{
		if (input == null)
		{
			SkyResources.logger.error("Need inputs for recipe. DID NOT ADD RECIPE.");
			return;
		}

		if (output == null)
		{
			SkyResources.logger.error("Need outputs for recipe. DID NOT ADD RECIPE.");
			return;
		}

		recipes.add(new ProcessRecipe(output, Collections.singletonList(input), intVal));
	}

	public void addRecipe(Object output, float intVal, List<Object> input)
	{

		if (input == null)
		{
			SkyResources.logger.error("Need inputs for recipe. DID NOT ADD RECIPE.");
			return;
		}

		if (output == null)
		{
			SkyResources.logger.error("Need outputs for recipe. DID NOT ADD RECIPE.");
			return;
		}

		recipes.add(new ProcessRecipe(Collections.singletonList(output), input, intVal));
	}

	public void addRecipe(ProcessRecipe recipe)
	{

		if (recipe.getInputs() == null || recipe.getInputs().size() == 0 || recipe.getFluidInputs() == null
				|| recipe.getFluidInputs().size() == 0)
		{
			SkyResources.logger.error("Need inputs for recipe. DID NOT ADD RECIPE.");
			return;
		}

		if (recipe.getOutputs() == null || recipe.getOutputs().size() == 0 || recipe.getFluidOutputs() == null
				|| recipe.getFluidOutputs().size() == 0)
		{
			SkyResources.logger.error("Need outputs for recipe. DID NOT ADD RECIPE.");
			return;
		}

		recipes.add(recipe);
	}

	public List<ProcessRecipe> removeRecipe(ProcessRecipe recipe, boolean forceEqual)
	{

		if (recipe.getOutputs() == null || recipe.getOutputs().size() == 0)
		{
			SkyResources.logger.error("Need outputs for recipe. DID NOT REMOVE RECIPE.");
			return null;
		}

		if (recipe.getInputs() == null || recipe.getInputs().size() == 0 || recipe.getFluidInputs() == null
				|| recipe.getFluidInputs().size() == 0)
		{

			List<Integer> recipesToRemoveAt = new ArrayList<Integer>();
			List<ProcessRecipe> recipesToRemove = new ArrayList<ProcessRecipe>();
			for (int i = 0; i < recipes.size(); i++)
			{
				boolean valid = true;
				for (ItemStack iOut : recipes.get(i).getOutputs())
				{
					for (ItemStack rOut : recipe.getOutputs())
					{
						if (!iOut.isItemEqual(rOut))
							valid = false;
					}
				}
				if (valid)
					recipesToRemoveAt.add(i);
			}
			for (int i = recipesToRemoveAt.size() - 1; i >= 0; i--)
			{
				recipesToRemove.add(recipes.get(recipesToRemoveAt.get(i)));
				recipes.remove((int) recipesToRemoveAt.get(i));
			}
			return recipesToRemove;
		}

		List<Integer> recipesToRemoveAt = new ArrayList<Integer>();
		List<ProcessRecipe> recipesToRemove = new ArrayList<ProcessRecipe>();
		for (int i = 0; i < recipes.size(); i++)
		{
			if (recipes.get(i).isInputRecipeEqualTo(recipe, forceEqual))
			{
				boolean valid = true;
				for (ItemStack iOut : recipes.get(i).getOutputs())
				{
					for (ItemStack rOut : recipe.getOutputs())
					{
						if (!iOut.isItemEqual(rOut))
							valid = false;
					}
				}
				if (valid)
					recipesToRemoveAt.add(i);
			}
		}
		for (int i = recipesToRemoveAt.size() - 1; i >= 0; i--)
		{
			recipesToRemove.add(recipes.get(recipesToRemoveAt.get(i)));
			recipes.remove((int) recipesToRemoveAt.get(i));
		}
		return recipesToRemove;
	}
}
