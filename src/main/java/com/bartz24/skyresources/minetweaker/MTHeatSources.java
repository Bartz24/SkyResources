package com.bartz24.skyresources.minetweaker;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.ItemHelper;
import com.bartz24.skyresources.base.HeatSources;
import com.bartz24.skyresources.technology.concentrator.ConcentratorRecipe;
import com.bartz24.skyresources.technology.concentrator.ConcentratorRecipes;
import com.bartz24.skyresources.technology.rockgrinder.RockGrinderRecipe;
import com.bartz24.skyresources.technology.rockgrinder.RockGrinderRecipes;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.skyresources.heatsources")
public class MTHeatSources
{

	@ZenMethod
	public static void addHeatSource(IItemStack block, int heatValue)
	{
		if (!(MinetweakerPlugin.toStack(block).getItem() instanceof ItemBlock))
			MineTweakerAPI
					.logError("ItemStack is not block. Did not add recipe.");

		addRecipe(ItemHelper.getBlockStateFromStack(
				MinetweakerPlugin.toStack(block)), heatValue);
	}

	public static void addRecipe(IBlockState block, int heatValue)
	{
		MineTweakerAPI.apply(new AddHeatSource(block, heatValue));
	}

	@ZenMethod
	public static void removeHeatSource(IItemStack output)
	{
		MineTweakerAPI.apply(new RemoveHeatSource(ItemHelper
				.getBlockStateFromStack(MinetweakerPlugin.toStack(output))));
	}

	private static class AddHeatSource implements IUndoableAction
	{
		private final IBlockState blockState;
		private final int heat;

		public AddHeatSource(IBlockState block, int heatValue)
		{
			blockState = block;
			heat = heatValue;
		}

		@Override
		public void apply()
		{
			HeatSources.addHeatSource(blockState, heat);
		}

		@Override
		public boolean canUndo()
		{
			return true;
		}

		@Override
		public void undo()
		{
			HeatSources.removeHeatSource(blockState);
		}

		@Override
		public String describe()
		{
			return "Adding Heat Source for " + blockState;
		}

		@Override
		public String describeUndo()
		{
			return "Removing Heat Source for " + blockState;
		}

		@Override
		public Object getOverrideKey()
		{
			return null;
		}
	}

	public static class RemoveHeatSource implements IUndoableAction
	{
		List<IBlockState> removedBlocks = new ArrayList<IBlockState>();
		List<Integer> removedHeatValues = new ArrayList<Integer>();

		private final IBlockState block;

		public RemoveHeatSource(IBlockState block)
		{
			this.block = block;
		}

		@Override
		public void apply()
		{
			if (HeatSources.isValidHeatSource(block))
			{
				removedBlocks.add(block);
				removedHeatValues.add(HeatSources.getHeatSourceValue(block));
				HeatSources.removeHeatSource(block);
			}
		}

		@Override
		public void undo()
		{
			if (removedBlocks != null)
			{
				for (int i = 0; i < removedBlocks.size(); i++)
				{
					HeatSources.addHeatSource(removedBlocks.get(i),
							removedHeatValues.get(i));
				}
			}

		}

		@Override
		public String describe()
		{
			return "Removing Heat Source for " + block;
		}

		@Override
		public String describeUndo()
		{
			return "Re-Adding Heat Source for " + block;
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
