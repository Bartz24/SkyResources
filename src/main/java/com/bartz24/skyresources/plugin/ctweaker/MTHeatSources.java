package com.bartz24.skyresources.plugin.ctweaker;

import java.util.HashMap;
import java.util.Map;

import com.bartz24.skyresources.base.HeatSources;
import com.bartz24.skyresources.jei.heatsources.HeatSourceJEI;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.skyresources.heatsources")
public class MTHeatSources
{
	//ACTS FUNNY WITH JEI
	
	
	@ZenMethod
	public static void add(IItemStack stack, int heatValue)
	{
		if (!(CraftTweakerPlugin.toStack(stack).getItem() instanceof ItemBlock))
		{
			MineTweakerAPI.logError("Input block is not block. Did not add source.");
			return;
		}
		addRecipe(CraftTweakerPlugin.toStack(stack), heatValue);
	}

	@ZenMethod
	public static void remove(IItemStack stack)
	{
		if (!(CraftTweakerPlugin.toStack(stack).getItem() instanceof ItemBlock))
		{
			MineTweakerAPI.logError("Input block is not block. Did not remove source.");
			return;
		}
		removeRecipe(CraftTweakerPlugin.toStack(stack));
	}

	public static void addRecipe(ItemStack stack, int val)
	{
		MineTweakerAPI.apply(new AddHeatSource(stack, val));
	}

	public static void removeRecipe(ItemStack stack)
	{
		MineTweakerAPI.apply(new RemoveHeatSource(stack));
	}

	private static class AddHeatSource implements IUndoableAction
	{
		private final ItemStack stack;
		private final int val;

		public AddHeatSource(ItemStack stack, int val)
		{
			this.stack = stack;
			this.val = val;
		}

		@Override
		public void apply()
		{
			Block block = Block.getBlockFromItem(stack.getItem());
			HeatSources.addHeatSource(block.getStateFromMeta(stack.getMetadata()), val);
			MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(new HeatSourceJEI(stack, val));
		}

		@Override
		public boolean canUndo()
		{
			return true;
		}

		@Override
		public void undo()
		{

			Block block = Block.getBlockFromItem(stack.getItem());
			for (IBlockState state : HeatSources.getHeatSources().keySet())
			{
				if (state == block.getStateFromMeta(stack.getMetadata()))
				{
					Item item2 = Item.getItemFromBlock(state.getBlock());
					MineTweakerAPI.getIjeiRecipeRegistry().removeRecipe(
							new HeatSourceJEI(new ItemStack(item2, 1, state.getBlock().getMetaFromState(state)),
									HeatSources.getHeatSourceValue(state)));
					HeatSources.removeHeatSource(block.getStateFromMeta(stack.getMetadata()));
					break;
				}

			}
		}

		@Override
		public String describe()
		{
			return "Adding Heat Source value for " + stack.getDisplayName();
		}

		@Override
		public String describeUndo()
		{
			return "Removing Heat Source value for " + stack.getDisplayName();
		}

		@Override
		public Object getOverrideKey()
		{
			return null;
		}
	}

	public static class RemoveHeatSource implements IUndoableAction
	{
		private final ItemStack stack;
		Map<ItemStack, Integer> removed = new HashMap<ItemStack, Integer>();

		public RemoveHeatSource(ItemStack stack)
		{
			this.stack = stack;
		}

		@Override
		public void apply()
		{
			Block block = Block.getBlockFromItem(stack.getItem());
			for (IBlockState state : HeatSources.getHeatSources().keySet())
			{
				if (state == block.getStateFromMeta(stack.getMetadata()))
				{
					Item item2 = Item.getItemFromBlock(state.getBlock());
					MineTweakerAPI.getIjeiRecipeRegistry().removeRecipe(
							new HeatSourceJEI(new ItemStack(item2, 1, state.getBlock().getMetaFromState(state)),
									HeatSources.getHeatSourceValue(state)));
					removed.put(stack, HeatSources.getHeatSourceValue(block.getStateFromMeta(stack.getMetadata())));
					HeatSources.removeHeatSource(block.getStateFromMeta(stack.getMetadata()));
					break;
				}

			}
		}

		@Override
		public void undo()
		{
			if (removed != null)
			{
				for (ItemStack stack : removed.keySet())
				{
					if (stack != ItemStack.EMPTY)
					{
						Block block = Block.getBlockFromItem(stack.getItem());
						HeatSources.addHeatSource(block.getStateFromMeta(stack.getMetadata()), removed.get(stack));
						MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(new HeatSourceJEI(stack, removed.get(stack)));
					}
				}
			}

		}

		@Override
		public String describe()
		{
			return "Removing Heat Source value for " + stack.getDisplayName();
		}

		@Override
		public String describeUndo()
		{
			return "Adding Heat Source value for " + stack.getDisplayName();
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
