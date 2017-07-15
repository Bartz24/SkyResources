package com.bartz24.skyresources.plugin.ctweaker;

import com.bartz24.skyresources.base.HeatSources;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
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
	@ZenMethod
	public static void add(IItemStack stack, int heatValue)
	{
		if (!(CraftTweakerPlugin.toStack(stack).getItem() instanceof ItemBlock))
		{
			CraftTweakerAPI.logError("Input block is not block. Did not add source.");
			return;
		}
		addRecipe(CraftTweakerPlugin.toStack(stack), heatValue);
	}

	@ZenMethod
	public static void remove(IItemStack stack)
	{
		if (!(CraftTweakerPlugin.toStack(stack).getItem() instanceof ItemBlock))
		{
			CraftTweakerAPI.logError("Input block is not block. Did not remove source.");
			return;
		}
		removeRecipe(CraftTweakerPlugin.toStack(stack));
	}

	public static void addRecipe(ItemStack stack, int val)
	{
		CraftTweakerAPI.apply(new AddHeatSource(stack, val));
	}

	public static void removeRecipe(ItemStack stack)
	{
		CraftTweakerAPI.apply(new RemoveHeatSource(stack));
	}

	private static class AddHeatSource implements IAction
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
		}

		@Override
		public String describe()
		{
			return "Adding Heat Source value for " + stack.getDisplayName();
		}
	}

	public static class RemoveHeatSource implements IAction
	{
		private final ItemStack stack;

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
					HeatSources.removeHeatSource(block.getStateFromMeta(stack.getMetadata()));
					break;
				}

			}
		}

		@Override
		public String describe()
		{
			return "Removing Heat Source value for " + stack.getDisplayName();
		}
	}

}
