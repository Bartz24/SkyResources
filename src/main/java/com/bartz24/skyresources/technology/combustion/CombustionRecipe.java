package com.bartz24.skyresources.technology.combustion;

import java.util.List;

import net.minecraft.item.ItemStack;
import scala.actors.threadpool.Arrays;

public class CombustionRecipe
{
	private ItemStack output;
	private List<ItemStack> inputs;
	private int heatRequired;

	public CombustionRecipe(ItemStack output, int heatNeeded, ItemStack... input)
	{
		this.output = output;
		this.heatRequired = heatNeeded;
		inputs = Arrays.asList(input);
	}
	
	public CombustionRecipe(ItemStack output, int heatNeeded, List<ItemStack> input)
	{
		this.output = output;
		this.heatRequired = heatNeeded;
		inputs = input;
	}

	public CombustionRecipe(int currentHeat, Object... input)
	{
		inputs = Arrays.asList(input);
		this.heatRequired = currentHeat;
	}

	public CombustionRecipe(int currentHeat, List<ItemStack> input)
	{
		inputs = input;
		this.heatRequired = currentHeat;
	}

	public boolean isInputRecipeEqualTo(CombustionRecipe recipe)
	{
		return stacksAreValid(recipe) && heatHighEnough(recipe);
	}

	boolean stacksAreValid(CombustionRecipe recipe)
	{
		if(inputs.size() == 0) return false;
		for (ItemStack i : inputs)
		{
			boolean valid = false;
			for (ItemStack i2 : recipe.inputs)
			{
				if(i.isItemEqual(i2) && i.stackSize < i2.stackSize) return false;
				else if(i.isItemEqual(i2) && i.stackSize >= i2.stackSize) valid = true;
			}
			if(!valid)
			return false;
		}
		
		return true;
	}

	boolean heatHighEnough(CombustionRecipe recipe)
	{
		System.out.println("Here" + (heatRequired >= recipe.heatRequired));
		return heatRequired >= recipe.heatRequired;
	}
	
	public ItemStack getOutput()
	{
		return output;
	}

	public List<ItemStack> getInputStacks()
	{		
		return inputs;
	}

	public int getHeatReq()
	{
		return heatRequired;
	}
}
