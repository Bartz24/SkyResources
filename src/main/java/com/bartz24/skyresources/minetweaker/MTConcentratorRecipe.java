package com.bartz24.skyresources.minetweaker;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.ItemHelper;
import com.bartz24.skyresources.technology.concentrator.ConcentratorRecipe;
import com.bartz24.skyresources.technology.concentrator.ConcentratorRecipes;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.skyresources.concentrator")
public class MTConcentratorRecipe
{

	@ZenMethod
	public static void addRecipe(IItemStack output, int heatRequired,
			IItemStack input, IItemStack inputStack)
	{
		if (!(MinetweakerPlugin.toStack(output).getItem() instanceof ItemBlock)
				|| !(MinetweakerPlugin.toStack(input)
						.getItem() instanceof ItemBlock))
			MineTweakerAPI
					.logError("ItemStack is not block. Did not add recipe.");

		addRecipe(new ConcentratorRecipe(
				ItemHelper.getBlockStateFromStack(
						MinetweakerPlugin.toStack(output)),
				heatRequired, MinetweakerPlugin.toStack(inputStack),
				ItemHelper.getBlockStateFromStack(
						MinetweakerPlugin.toStack(input))));
	}

	public static void addRecipe(ConcentratorRecipe recipe)
	{
		MineTweakerAPI.apply(new AddConcentratorRecipe(recipe));
	}

	@ZenMethod
	public static void removeRecipe(IItemStack output)
	{
		if (!(MinetweakerPlugin.toStack(output).getItem() instanceof ItemBlock))
			MineTweakerAPI
					.logError("ItemStack is not block. Did not add recipe.");

		MineTweakerAPI.apply(new RemoveConcentratorRecipe(ItemHelper
				.getBlockStateFromStack(MinetweakerPlugin.toStack(output))));
	}

	private static class AddConcentratorRecipe implements IUndoableAction
	{
		private final ConcentratorRecipe recipe;

		public AddConcentratorRecipe(ConcentratorRecipe recipe)
		{
			this.recipe = recipe;
		}

		@Override
		public void apply()
		{
			ConcentratorRecipes.addRecipe(recipe);
		}

		@Override
		public boolean canUndo()
		{
			return true;
		}

		@Override
		public void undo()
		{
			ConcentratorRecipes.removeRecipe(recipe);
		}

		@Override
		public String describe()
		{
			return "Adding Concentrator recipe for " + recipe.getOutput();
		}

		@Override
		public String describeUndo()
		{
			return "Removing Concentrator recipe for " + recipe.getOutput();
		}

		@Override
		public Object getOverrideKey()
		{
			return null;
		}
	}

	public static class RemoveConcentratorRecipe implements IUndoableAction
	{
		private final IBlockState output;
		List<ConcentratorRecipe> removedRecipes = new ArrayList<ConcentratorRecipe>();

		public RemoveConcentratorRecipe(IBlockState output)
		{
			this.output = output;
		}

		@Override
		public void apply()
		{
			removedRecipes.addAll(ConcentratorRecipes.removeRecipe(
					new ConcentratorRecipe(output, 0, null, null)));
		}

		@Override
		public void undo()
		{
			if (removedRecipes != null)
			{
				for (ConcentratorRecipe recipe : removedRecipes)
				{
					if (recipe != null)
					{
						ConcentratorRecipes.addRecipe(recipe);
					}
				}
			}

		}

		@Override
		public String describe()
		{
			return "Removing Concentrator recipe for " + output;
		}

		@Override
		public String describeUndo()
		{
			return "Re-Adding Concentrator recipe for " + output;
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
