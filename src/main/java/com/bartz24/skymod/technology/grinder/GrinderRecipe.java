package com.bartz24.skymod.technology.grinder;

import java.util.ArrayList;
import java.util.List;

import scala.actors.threadpool.Arrays;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class GrinderRecipe
{
	IBlockState inputBlock;
	
	boolean fuzzyInput;
	
	ItemStack output;
	
	public GrinderRecipe(IBlockState inputState, boolean fuzzy, ItemStack outputStack)
	{
		inputBlock = inputState;
		fuzzyInput = fuzzy;
		output = outputStack;
	}
}
