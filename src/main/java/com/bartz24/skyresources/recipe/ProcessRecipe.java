package com.bartz24.skyresources.recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.bartz24.skyresources.ItemHelper;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class ProcessRecipe
{
	private List<ItemStack> outputs;
	private List<Object> inputs;
	private List<FluidStack> fluidOutputs;
	private List<FluidStack> fluidInputs;
	private float intParameter;
	private String type;

	public ProcessRecipe(List<Object> output, List<Object> input, float param, String type)
	{
		outputs = new ArrayList<ItemStack>();
		inputs = new ArrayList<Object>();
		fluidOutputs = new ArrayList<FluidStack>();
		fluidInputs = new ArrayList<FluidStack>();
		intParameter = param;
		this.type = type;

		for (Object o : output)
		{
			if (o instanceof ItemStack)
				outputs.add((ItemStack) o);
			else if (o instanceof FluidStack)
				fluidOutputs.add((FluidStack) o);
		}
		for (Object o : input)
		{
			if (o instanceof ItemStack || o instanceof String)
				inputs.add(o);
			else if (o instanceof FluidStack)
				fluidInputs.add((FluidStack) o);
		}
	}

	public ProcessRecipe(List<Object> input, float param, String type)
	{
		outputs = new ArrayList<ItemStack>();
		inputs = new ArrayList<Object>();
		fluidOutputs = new ArrayList<FluidStack>();
		fluidInputs = new ArrayList<FluidStack>();
		intParameter = param;
		this.type = type;
		for (Object o : input)
		{
			if (o instanceof ItemStack || o instanceof String)
				inputs.add(o);
			else if (o instanceof FluidStack)
				fluidInputs.add((FluidStack) o);
		}
	}

	public String getRecipeType()
	{
		return type;
	}

	public boolean isInputRecipeEqualTo(ProcessRecipe recipe, boolean forceEqual)
	{
		return stacksAreValid(recipe, forceEqual) && fluidsValid(recipe, forceEqual) && intValid(recipe);
	}

	public boolean isInputMultiRecipeEqualTo(ProcessRecipe recipe)
	{
		return stacksAreValidMulti(recipe) && intValid(recipe);
	}

	boolean fluidsValid(ProcessRecipe recipe, boolean forceEqual)
	{
		if (fluidInputs.size() != recipe.fluidInputs.size())
			return false;

		int fluidsChecked = 0;
		for (FluidStack i : fluidInputs)
		{
			boolean valid = false;
			for (FluidStack i2 : recipe.fluidInputs)
			{
				if (i.isFluidEqual(i2) && (forceEqual ? i.amount == i2.amount : i.amount >= i2.amount))
					valid = true;
			}
			if (!valid)
				return false;
			fluidsChecked++;
		}
		return fluidsChecked == fluidInputs.size();
	}

	boolean stacksAreValidMulti(ProcessRecipe recipe)
	{
		HashMap<ItemStack, Integer> items = new HashMap();
		for (Object o : inputs)
		{
			if (!(o instanceof String))
			{
				ItemStack i = ((ItemStack) o).copy();
				int count = i.getCount();
				i.setCount(1);
				boolean added = false;
				for (ItemStack i2 : items.keySet())
				{
					if (i2.isItemEqual(i))
					{
						items.put(i2, items.get(i2) + count);
						added = true;
					}
				}
				if (!added)
					items.put(i, count);
			}

		}

		if (items.size() != recipe.inputs.size())
			return false;

		int itemsChecked = 0;
		for (ItemStack i : items.keySet())
		{
			boolean valid = false;
			float ratio = -1;
			for (Object o2 : recipe.inputs)
			{
				if (o2 instanceof String)
				{
					int[] ids = OreDictionary.getOreIDs((ItemStack) i);
					for (int id : ids)
						if (id == OreDictionary.getOreID(o2.toString()))
							valid = true;
				} else if (o2 instanceof ItemStack)
				{
					ItemStack i2 = (ItemStack) o2;
					if (ItemHelper.itemStacksEqualOD(i, i2) && items.get(i) >= i2.getCount()
							&& (ratio == -1 || ((float) items.get(i) / (float) i2.getCount()) == ratio))
					{
						valid = true;
						if (ratio == -1)
							ratio = (float) items.get(i) / (float) i2.getCount();
					}
				}
			}
			if (!valid)
				return false;
			itemsChecked++;
		}

		return itemsChecked == items.size();
	}

	boolean stacksAreValid(ProcessRecipe recipe, boolean forceEqual)
	{
		if (inputs.size() != recipe.inputs.size())
			return false;

		int itemsChecked = 0;
		for (Object i : inputs)
		{
			boolean valid = false;
			for (Object i2 : recipe.inputs)
			{
				if (i instanceof ItemStack && i2 instanceof String)
				{
					int[] ids = OreDictionary.getOreIDs((ItemStack) i);
					for (int id : ids)
						if (id == OreDictionary.getOreID(i2.toString()))
							valid = true;
				} else if (i instanceof ItemStack && i2 instanceof ItemStack)
					if (ItemHelper.itemStacksEqualOD((ItemStack) i, (ItemStack) i2)
							&& (forceEqual ? ((ItemStack) i).getCount() == ((ItemStack) i2).getCount()
									: ((ItemStack) i).getCount() >= ((ItemStack) i2).getCount()))
						valid = true;
			}
			if (!valid)
				return false;
			itemsChecked++;
		}

		return itemsChecked == inputs.size();
	}

	boolean intValid(ProcessRecipe recipe)
	{
		return intParameter >= recipe.intParameter;
	}

	public List<ItemStack> getOutputs()
	{
		return outputs;
	}

	public List<Object> getInputs()
	{
		return inputs;
	}

	public List<FluidStack> getFluidOutputs()
	{
		return fluidOutputs;
	}

	public List<FluidStack> getFluidInputs()
	{
		return fluidInputs;
	}

	public float getIntParameter()
	{
		return intParameter;
	}
}
