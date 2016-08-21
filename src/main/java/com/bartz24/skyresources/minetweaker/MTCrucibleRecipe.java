package com.bartz24.skyresources.minetweaker;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.ItemHelper;
import com.bartz24.skyresources.alchemy.crucible.CrucibleRecipe;
import com.bartz24.skyresources.alchemy.crucible.CrucibleRecipes;
import com.bartz24.skyresources.alchemy.infusion.InfusionRecipe;
import com.bartz24.skyresources.alchemy.infusion.InfusionRecipes;
import com.bartz24.skyresources.minetweaker.MTInfusionRecipe.RemoveInfusionRecipe;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.skyresources.crucible")
public class MTCrucibleRecipe
{

	@ZenMethod
	public static void addRecipe(ILiquidStack output, IItemStack inputStack)
	{

		addRecipe(new CrucibleRecipe(MinetweakerPlugin.toFluidStack(output), MinetweakerPlugin.toStack(inputStack)));
	}

	public static void addRecipe(CrucibleRecipe recipe)
	{
		MineTweakerAPI.apply(new AddCrucibleRecipe(recipe));
	}

	@ZenMethod
	public static void removeRecipe(ILiquidStack output)
	{
		MineTweakerAPI.apply(new RemoveCrucibleRecipe(
				MinetweakerPlugin.toFluidStack(output)));
	}

	private static class AddCrucibleRecipe implements IUndoableAction
	{
		private final CrucibleRecipe recipe;

		public AddCrucibleRecipe(CrucibleRecipe recipe)
		{
			this.recipe = recipe;
		}

		@Override
		public void apply()
		{
			CrucibleRecipes.addRecipe(recipe);
		}

		@Override
		public boolean canUndo()
		{
			return true;
		}

		@Override
		public void undo()
		{
			CrucibleRecipes.removeRecipe(recipe);
		}

		@Override
		public String describe()
		{
			return "Adding Crucible recipe for " + recipe.getOutput();
		}

		@Override
		public String describeUndo()
		{
			return "Removing Crucible recipe for " + recipe.getOutput();
		}

		@Override
		public Object getOverrideKey()
		{
			return null;
		}
	}

	public static class RemoveCrucibleRecipe implements IUndoableAction
	{
		private final FluidStack output;
		List<CrucibleRecipe> removedRecipes = new ArrayList<CrucibleRecipe>();

		public RemoveCrucibleRecipe(FluidStack output)
		{
			this.output = output;
		}

		@Override
		public void apply()
		{
			removedRecipes.addAll(CrucibleRecipes.removeRecipe(
					new CrucibleRecipe(output, null)));
		}

		@Override
		public void undo()
		{
			if (removedRecipes != null)
			{
				for (CrucibleRecipe recipe : removedRecipes)
				{
					if (recipe != null)
					{
						CrucibleRecipes.addRecipe(recipe);
					}
				}
			}

		}

		@Override
		public String describe()
		{
			return "Removing Crucible recipe for " + output;
		}

		@Override
		public String describeUndo()
		{
			return "Re-Adding Crucible recipe for " + output;
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
