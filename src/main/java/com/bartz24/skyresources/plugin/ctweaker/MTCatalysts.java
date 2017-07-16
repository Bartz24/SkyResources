package com.bartz24.skyresources.plugin.ctweaker;

import java.util.HashMap;
import java.util.Map;

import com.bartz24.skyresources.alchemy.FusionCatalysts;
import com.bartz24.skyresources.base.HeatSources;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.skyresources.catalysts")
public class MTCatalysts
{
	@ZenMethod
	public static void add(IItemStack stack, float value)
	{
		addRecipe(CraftTweakerPlugin.toStack(stack), value);
	}

	@ZenMethod
	public static void remove(IItemStack stack)
	{
		removeRecipe(CraftTweakerPlugin.toStack(stack));
	}

	public static void addRecipe(ItemStack stack, float val)
	{
		CraftTweakerAPI.apply(new AddCatalyst(stack, val));
	}

	public static void removeRecipe(ItemStack stack)
	{
		CraftTweakerAPI.apply(new RemoveCatalyst(stack));
	}

	private static class AddCatalyst implements IAction
	{
		private final ItemStack stack;
		private final float val;

		public AddCatalyst(ItemStack stack, float val)
		{
			this.stack = stack;
			this.val = val;
		}

		@Override
		public void apply()
		{
			FusionCatalysts.addCTCatalyst(stack, val);
		}

		@Override
		public String describe()
		{
			return "Adding catalyst value for " + stack.getDisplayName();
		}
	}

	public static class RemoveCatalyst implements IAction
	{
		private final ItemStack stack;

		public RemoveCatalyst(ItemStack stack)
		{
			this.stack = stack;
		}

		@Override
		public void apply()
		{
			FusionCatalysts.removeCTCatalyst(stack);
		}

		@Override
		public String describe()
		{
			return "Removing catalyst value for " + stack.getDisplayName();
		}
	}

}
