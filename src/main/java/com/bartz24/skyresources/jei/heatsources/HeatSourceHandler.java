package com.bartz24.skyresources.jei.heatsources;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.base.HeatSources;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class HeatSourceHandler implements IRecipeHandler<HeatSourceJEI>
{
	public HeatSourceHandler()
	{
	}

	@Override
	public Class<HeatSourceJEI> getRecipeClass()
	{
		return HeatSourceJEI.class;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(HeatSourceJEI recipe)
	{
		return recipe;
	}

	@Override
	public boolean isRecipeValid(HeatSourceJEI recipe)
	{
		return true;
	}

	@Override
	public String getRecipeCategoryUid(HeatSourceJEI recipe)
	{
		return References.ModID + ":heatsources";
	}

	public static List<HeatSourceJEI> getSources()
	{
		List<HeatSourceJEI> sources = new ArrayList<>();
		for (IBlockState state : HeatSources.getHeatSources().keySet())
		{
			Item item = Item.getItemFromBlock(state.getBlock());
			if (item == Items.AIR)
				sources.add(new HeatSourceJEI(state.getBlock().getUnlocalizedName(),
						HeatSources.getHeatSourceValue(state)));
			else
				sources.add(new HeatSourceJEI(new ItemStack(item, 1, state.getBlock().getMetaFromState(state)),
						HeatSources.getHeatSourceValue(state)));

		}
		return sources;
	}
}
