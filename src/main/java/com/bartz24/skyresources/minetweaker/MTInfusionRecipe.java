package com.bartz24.skyresources.minetweaker;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.ItemHelper;
import com.bartz24.skyresources.alchemy.infusion.InfusionRecipe;
import com.bartz24.skyresources.alchemy.infusion.InfusionRecipes;
import com.bartz24.skyresources.technology.concentrator.ConcentratorRecipe;
import com.bartz24.skyresources.technology.concentrator.ConcentratorRecipes;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.skyresources.infusion")
public class MTInfusionRecipe
{

	@ZenMethod
	public static void addRecipe(IItemStack output, int healthRequired,
			IItemStack input, IItemStack inputStack)
	{
		if (!(MinetweakerPlugin.toStack(input).getItem() instanceof ItemBlock))
			MineTweakerAPI
					.logError("ItemStack is not block. Did not add recipe.");

		addRecipe(new InfusionRecipe(MinetweakerPlugin.toStack(output),
				healthRequired, MinetweakerPlugin.toStack(inputStack),
				ItemHelper.getBlockStateFromStack(
						MinetweakerPlugin.toStack(input)).getBlock(),
				MinetweakerPlugin.toStack(input).getMetadata()));
	}

	public static void addRecipe(InfusionRecipe recipe)
	{
		MineTweakerAPI.apply(new AddInfusionRecipe(recipe));
	}

	@ZenMethod
	public static void removeRecipe(IItemStack output)
	{
		MineTweakerAPI.apply(new RemoveInfusionRecipe(
				MinetweakerPlugin.toStack(output)));
	}

	private static class AddInfusionRecipe implements IUndoableAction
	{
		private final InfusionRecipe recipe;

		public AddInfusionRecipe(InfusionRecipe recipe)
		{
			this.recipe = recipe;
		}

		@Override
		public void apply()
		{
			InfusionRecipes.addRecipe(recipe);
		}

		@Override
		public boolean canUndo()
		{
			return true;
		}

		@Override
		public void undo()
		{
			InfusionRecipes.removeRecipe(recipe);
		}

		@Override
		public String describe()
		{
			return "Adding Infusion recipe for " + recipe.getOutput();
		}

		@Override
		public String describeUndo()
		{
			return "Removing Infusion recipe for " + recipe.getOutput();
		}

		@Override
		public Object getOverrideKey()
		{
			return null;
		}
	}

	public static class RemoveInfusionRecipe implements IUndoableAction
	{
		private final ItemStack output;
		List<InfusionRecipe> removedRecipes = new ArrayList<InfusionRecipe>();

		public RemoveInfusionRecipe(ItemStack output)
		{
			this.output = output;
		}

		@Override
		public void apply()
		{
			removedRecipes.addAll(InfusionRecipes.removeRecipe(
					new InfusionRecipe(output, 0, null, null)));
		}

		@Override
		public void undo()
		{
			if (removedRecipes != null)
			{
				for (InfusionRecipe recipe : removedRecipes)
				{
					if (recipe != null)
					{
						InfusionRecipes.addRecipe(recipe);
					}
				}
			}

		}

		@Override
		public String describe()
		{
			return "Removing Infusion recipe for " + output;
		}

		@Override
		public String describeUndo()
		{
			return "Re-Adding Infusion recipe for " + output;
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
