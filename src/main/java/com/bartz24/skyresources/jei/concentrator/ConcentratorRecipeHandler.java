package com.bartz24.skyresources.jei.concentrator;

import com.bartz24.skyresources.References;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class ConcentratorRecipeHandler
		implements IRecipeHandler<ConcentratorRecipeJEI>
{

	@Override
	public String getRecipeCategoryUid()
	{
		return References.ModID + ":concentrator";
	}

	@Override
	public Class<ConcentratorRecipeJEI> getRecipeClass()
	{
		return ConcentratorRecipeJEI.class;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(ConcentratorRecipeJEI recipe)
	{
		return recipe;
	}

	@Override
	public boolean isRecipeValid(ConcentratorRecipeJEI recipe)
	{
		return recipe.getInputs().size() > 0 && recipe.getOutputs().size() > 0;
	}

	@Override
	public String getRecipeCategoryUid(ConcentratorRecipeJEI arg0)
	{
		return References.ModID + ":concentrator";
	}

}
