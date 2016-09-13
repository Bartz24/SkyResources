package com.bartz24.skyresources.minetweaker;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.ItemHelper;
import com.bartz24.skyresources.technology.rockgrinder.RockGrinderRecipe;
import com.bartz24.skyresources.technology.rockgrinder.RockGrinderRecipes;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.skyresources.rockgrinder")
public class MTRockGrinderRecipe
{

	@ZenMethod
	public static void addRecipe(IItemStack output, IItemStack input, boolean oreDictionaryInput)
	{
		if (!(MinetweakerPlugin.toStack(input).getItem() instanceof ItemBlock))
			MineTweakerAPI
					.logError("ItemStack is not block. Did not add recipe.");

		addRecipe(new RockGrinderRecipe(

				MinetweakerPlugin.toStack(output),
				oreDictionaryInput,
				ItemHelper.getBlockStateFromStack(
						MinetweakerPlugin.toStack(input))));
	}

	@ZenMethod
	public static void addRecipe(IItemStack output, IItemStack input, boolean oreDictionaryInput, float chance)
	{
		if (!(MinetweakerPlugin.toStack(input).getItem() instanceof ItemBlock))
			MineTweakerAPI
					.logError("ItemStack is not block. Did not add recipe.");

		addRecipe(new RockGrinderRecipe(

				MinetweakerPlugin.toStack(output), chance,
				oreDictionaryInput,
				ItemHelper.getBlockStateFromStack(
						MinetweakerPlugin.toStack(input))));
	}

	public static void addRecipe(RockGrinderRecipe recipe)
	{
		MineTweakerAPI.apply(new AddRockGrinderRecipe(recipe));
	}

	@ZenMethod
	public static void removeRecipe(IItemStack output)
	{
		MineTweakerAPI.apply(new RemoveRockGrinderRecipe(MinetweakerPlugin.toStack(output)));
	}

	private static class AddRockGrinderRecipe implements IUndoableAction
	{
		private final RockGrinderRecipe recipe;

		public AddRockGrinderRecipe(RockGrinderRecipe recipe)
		{
			this.recipe = recipe;
		}

		@Override
		public void apply()
		{
			RockGrinderRecipes.addRecipe(recipe);
		}

		@Override
		public boolean canUndo()
		{
			return true;
		}

		@Override
		public void undo()
		{
			RockGrinderRecipes.removeRecipe(recipe);
		}

		@Override
		public String describe()
		{
			return "Adding Rock Grinder recipe for " + recipe.getOutput();
		}

		@Override
		public String describeUndo()
		{
			return "Removing Rock Grinder recipe for " + recipe.getOutput();
		}

		@Override
		public Object getOverrideKey()
		{
			return null;
		}
	}

	public static class RemoveRockGrinderRecipe implements IUndoableAction
	{
		private final ItemStack output;
		List<RockGrinderRecipe> removedRecipes = new ArrayList<RockGrinderRecipe>();

		public RemoveRockGrinderRecipe(ItemStack output)
		{
			this.output = output;
		}

		@Override
		public void apply()
		{
			removedRecipes.addAll(RockGrinderRecipes.removeRecipe(
					new RockGrinderRecipe(output, false, null)));
		}

		@Override
		public void undo()
		{
			if (removedRecipes != null)
			{
				for (RockGrinderRecipe recipe : removedRecipes)
				{
					if (recipe != null)
					{
						RockGrinderRecipes.addRecipe(recipe);
					}
				}
			}

		}

		@Override
		public String describe()
		{
			return "Removing Rock Grinder recipe for " + output;
		}

		@Override
		public String describeUndo()
		{
			return "Re-Adding Rock Grinder recipe for " + output;
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
