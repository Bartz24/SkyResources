package com.bartz24.skymod.jei.combustion;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

import com.bartz24.skymod.References;

public class CombustionRecipeHandler implements IRecipeHandler<CombustionRecipeJEI>
{

	@Override
	public String getRecipeCategoryUid()
	{
		return References.ModID + ":combustion";
	}

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

}
