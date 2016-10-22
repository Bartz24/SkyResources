package com.bartz24.skyresources.jei.cauldronclean;

import com.bartz24.skyresources.References;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class CauldronCleanRecipeHandler
		implements IRecipeHandler<CauldronCleanRecipeJEI>
{

	@Override
	public Class<CauldronCleanRecipeJEI> getRecipeClass()
	{
		return CauldronCleanRecipeJEI.class;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(CauldronCleanRecipeJEI recipe)
	{
		return recipe;
	}

	@Override
	public boolean isRecipeValid(CauldronCleanRecipeJEI recipe)
	{
		return recipe.getInputs().size() > 0 && recipe.getOutputs().size() > 0;
	}

	@Override
	public String getRecipeCategoryUid(CauldronCleanRecipeJEI arg0)
	{
		return References.ModID + ":cauldronclean";
	}

	@Override
	public String getRecipeCategoryUid()
	{
		return References.ModID + ":cauldronclean";
	}

}
