package com.bartz24.skyresources.jei.rockgrinder;

import com.bartz24.skyresources.References;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class RockGrinderRecipeHandler
		implements IRecipeHandler<RockGrinderRecipeJEI>
{

	@Override
	public String getRecipeCategoryUid()
	{
		return References.ModID + ":rockgrinder";
	}

	@Override
	public Class<RockGrinderRecipeJEI> getRecipeClass()
	{
		return RockGrinderRecipeJEI.class;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(RockGrinderRecipeJEI recipe)
	{
		return recipe;
	}

	@Override
	public boolean isRecipeValid(RockGrinderRecipeJEI recipe)
	{
		return recipe.getInputs().size() > 0 && recipe.getOutputs().size() > 0;
	}

}
