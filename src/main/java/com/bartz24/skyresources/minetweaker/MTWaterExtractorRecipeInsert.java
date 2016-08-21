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

@ZenClass("mods.skyresources.waterextractor.insert")
public class MTWaterExtractorRecipeInsert
{

	@ZenMethod
	public static void addRecipe(IItemStack output, int amount, IItemStack input)
	{
		if (!(MinetweakerPlugin.toStack(input).getItem() instanceof ItemBlock))
			MineTweakerAPI.logError("ItemStack is not block. Did not add recipe.");
		if (!(MinetweakerPlugin.toStack(output).getItem() instanceof ItemBlock))
			MineTweakerAPI.logError("ItemStack is not block. Did not add recipe.");

		addRecipe(new WaterExtractorRecipe(ItemHelper.getBlockStateFromStack(MinetweakerPlugin.toStack(output)),
				MinetweakerPlugin.toStack(input).getMetadata() == OreDictionary.WILDCARD_VALUE,
						ItemHelper.getBlockStateFromStack(MinetweakerPlugin.toStack(input)), amount));
	}

	public static void addRecipe(WaterExtractorRecipe recipe)
	{
		MineTweakerAPI.apply(new AddWaterExtractorRecipeInsert(recipe));
	}

	@ZenMethod
	public static void removeRecipe(IItemStack output)
	{
		MineTweakerAPI.apply(new RemoveWaterExtractorRecipeInsert(ItemHelper.getBlockStateFromStack(MinetweakerPlugin.toStack(output))));
	}

	private static class AddWaterExtractorRecipeInsert implements IUndoableAction
	{
		private final WaterExtractorRecipe recipe;

		public AddWaterExtractorRecipeInsert(WaterExtractorRecipe recipe)
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
			return "Adding Water Extractor Insert recipe for " + recipe.getOutput();
		}

		@Override
		public String describeUndo()
		{
			return "Removing Water Extractor Insert recipe for " + recipe.getOutput();
		}

		@Override
		public Object getOverrideKey()
		{
			return null;
		}
	}

	public static class RemoveWaterExtractorRecipeInsert implements IUndoableAction
	{
		private final IBlockState output;
		List<WaterExtractorRecipe> removedRecipes = new ArrayList<WaterExtractorRecipe>();

		public RemoveWaterExtractorRecipeInsert(IBlockState output)
		{
			this.output = output;
		}

		@Override
		public void apply()
		{
			removedRecipes.addAll(WaterExtractorRecipes.removeRecipe(new WaterExtractorRecipe(output, false, null, 0)));
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
			return "Removing Water Extractor Insert recipe recipe for " + output;
		}

		@Override
		public String describeUndo()
		{
			return "Re-Adding Water Extractor Insert recipe recipe for " + output;
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
