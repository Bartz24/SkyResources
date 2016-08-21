package com.bartz24.skyresources.minetweaker;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.ItemHelper;
import com.bartz24.skyresources.base.waterextractor.WaterExtractorRecipe;
import com.bartz24.skyresources.base.waterextractor.WaterExtractorRecipes;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.oredict.OreDictionary;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.skyresources.waterextractor.extract")
public class MTWaterExtractorRecipeExtract
{

	@ZenMethod
	public static void addRecipe(int amount, IItemStack input, IItemStack output)
	{
		if (!(MinetweakerPlugin.toStack(input).getItem() instanceof ItemBlock))
			MineTweakerAPI.logError("ItemStack is not block. Did not add recipe.");
		if (!(MinetweakerPlugin.toStack(output).getItem() instanceof ItemBlock))
			MineTweakerAPI.logError("ItemStack is not block. Did not add recipe.");

		addRecipe(new WaterExtractorRecipe(amount,
				MinetweakerPlugin.toStack(input).getMetadata() == OreDictionary.WILDCARD_VALUE,
				ItemHelper.getBlockStateFromStack(MinetweakerPlugin.toStack(input)),
				ItemHelper.getBlockStateFromStack(MinetweakerPlugin.toStack(output))));
	}

	@ZenMethod
	public static void addRecipe(int amount, IItemStack input)
	{
		if (!(MinetweakerPlugin.toStack(input).getItem() instanceof ItemBlock))
			MineTweakerAPI.logError("ItemStack is not block. Did not add recipe.");


		addRecipe(new WaterExtractorRecipe(amount,
				MinetweakerPlugin.toStack(input).getMetadata() == OreDictionary.WILDCARD_VALUE,
				ItemHelper.getBlockStateFromStack(MinetweakerPlugin.toStack(input)),
				null));
	}

	public static void addRecipe(WaterExtractorRecipe recipe)
	{
		MineTweakerAPI.apply(new AddWaterExtractorRecipeExtract(recipe));
	}

	@ZenMethod
	public static void removeRecipe(IItemStack input, int amount)
	{
		MineTweakerAPI.apply(new RemoveWaterExtractorRecipeExtract(
				ItemHelper.getBlockStateFromStack(MinetweakerPlugin.toStack(input)), amount));
	}

	private static class AddWaterExtractorRecipeExtract implements IUndoableAction
	{
		private final WaterExtractorRecipe recipe;

		public AddWaterExtractorRecipeExtract(WaterExtractorRecipe recipe)
		{
			this.recipe = recipe;
		}

		@Override
		public void apply()
		{
			WaterExtractorRecipes.addRecipe(recipe);
		}

		@Override
		public boolean canUndo()
		{
			return true;
		}

		@Override
		public void undo()
		{
			WaterExtractorRecipes.removeRecipe(recipe);
		}

		@Override
		public String describe()
		{
			return "Adding Water Extractor Extract recipe for " + recipe.getOutput();
		}

		@Override
		public String describeUndo()
		{
			return "Removing Water Extractor Extract recipe for " + recipe.getOutput();
		}

		@Override
		public Object getOverrideKey()
		{
			return null;
		}
	}

	public static class RemoveWaterExtractorRecipeExtract implements IUndoableAction
	{
		private final IBlockState input;
		private final int amt;
		List<WaterExtractorRecipe> removedRecipes = new ArrayList<WaterExtractorRecipe>();

		public RemoveWaterExtractorRecipeExtract(IBlockState input, int amount)
		{
			this.input = input;
			amt=amount;
		}

		@Override
		public void apply()
		{
			removedRecipes.addAll(WaterExtractorRecipes
					.removeRecipe(new WaterExtractorRecipe(amt, false, input, null)));
		}

		@Override
		public void undo()
		{
			if (removedRecipes != null)
			{
				for (WaterExtractorRecipe recipe : removedRecipes)
				{
					if (recipe != null)
					{
						WaterExtractorRecipes.addRecipe(recipe);
					}
				}
			}

		}

		@Override
		public String describe()
		{
			return "Removing Water Extractor Extract recipe recipe for " + amt;
		}

		@Override
		public String describeUndo()
		{
			return "Re-Adding Water Extractor Extract recipe recipe for " + amt;
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
