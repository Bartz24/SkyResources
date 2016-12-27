package com.bartz24.skyresources.jei.combustion;

import com.bartz24.skyresources.References;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class CombustionRecipeHandler
		implements IRecipeHandler<CombustionRecipeJEI>
{

	@Override
	public Class<CombustionRecipeJEI> getRecipeClass()
	{
		return CombustionRecipeJEI.class;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(CombustionRecipeJEI recipe)
	{
		return recipe;
	}

	@Override
	public boolean isRecipeValid(CombustionRecipeJEI recipe)
	{
		return recipe.getInputs().size() > 0 && recipe.getOutputs().size() > 0;
	}

	@Override
	public String getRecipeCategoryUid(CombustionRecipeJEI arg0)
	{
		return References.ModID + ":combustion";
	}

}
