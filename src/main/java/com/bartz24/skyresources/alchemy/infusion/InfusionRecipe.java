package com.bartz24.skyresources.alchemy.infusion;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class InfusionRecipe
{
	private ItemStack output;
	private Block input;
	private int inputMeta;
	private ItemStack stack;
	private int health;
	private String oreDictBlock;

	public InfusionRecipe(ItemStack outputBlock, int healthNeeded,
			ItemStack rightClickStack, Block inputBlock, int inMetadata)
	{
		output = outputBlock;
		health = healthNeeded;
		stack = rightClickStack;
		input = inputBlock;
		inputMeta = inMetadata;
	}

	public InfusionRecipe(ItemStack outputBlock, int healthNeeded,
			ItemStack rightClickStack, String inputBlockOreDict)
	{
		output = outputBlock;
		health = healthNeeded;
		stack = rightClickStack;
		oreDictBlock = inputBlockOreDict;
	}

	public InfusionRecipe(ItemStack outputBlock, ItemStack rightClickStack,
			Object inputBlock, int inMetadata, int healthNeeded)
	{
		stack = rightClickStack;

		if (inputBlock instanceof Block)
		{
			input = (Block) inputBlock;
		} else if (inputBlock instanceof String)
		{
			oreDictBlock = (String) inputBlock;
		}

		health = healthNeeded;
		output = outputBlock;
		inputMeta = inMetadata;
	}

	public InfusionRecipe(ItemStack rightClickStack, int amt, Block inputBlock,
			int inMetadata)
	{
		stack = rightClickStack;

		if (inputBlock instanceof Block)
			input = (Block) inputBlock;

		inputMeta = inMetadata;
	}

	public boolean isInputRecipeEqualTo(InfusionRecipe recipe, World world)
	{
		return stackIsValid(recipe) && blockIsValid(recipe);
	}

	boolean stackIsValid(InfusionRecipe recipe)
	{
		if (recipe.stack == null && stack != null)
			return false;
		else if (recipe.stack != null && stack == null)
			return false;

		return recipe.stack.isItemEqual(stack)
				&& stack.stackSize >= recipe.stack.stackSize;
	}

	boolean blockIsValid(InfusionRecipe recipe)
	{
		if (input == null || input == Blocks.air)
			return false;

		for (int i : OreDictionary
				.getOreIDs(new ItemStack(input, 1, inputMeta)))
		{
			if (i == OreDictionary.getOreID(recipe.oreDictBlock))
				return true;
		}

		return recipe.input == input && recipe.inputMeta == inputMeta;
	}

	public ItemStack getOutput()
	{
		return output;
	}

	public ItemStack getInputStack()
	{		
		return stack;
	}

	public ItemStack getInputBlock()
	{
		if (input != Blocks.air && input != null)
			return new ItemStack(input, 1, inputMeta);
		else
		{
			List<ItemStack> inputs = OreDictionary.getOres(oreDictBlock);
			
			if (inputs.size() > 0)
				return inputs.get(0);
		}
		return null;
	}

	public int getHealthReq()
	{
		return health;
	}
}
