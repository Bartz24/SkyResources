package com.bartz24.skyresources.jei.freezer;

import com.bartz24.skyresources.References;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class FreezerRecipeHandler
		implements IRecipeHandler<FreezerRecipeJEI>
{

	@Override
	public Class<FreezerRecipeJEI> getRecipeClass()
	{
		return FreezerRecipeJEI.class;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(FreezerRecipeJEI recipe)
	{
		return recipe;
	}

	@Override
	public boolean isRecipeValid(FreezerRecipeJEI recipe)
	{
		return recipe.getInputs().size() > 0 && recipe.getOutputs().size() > 0;
	}

	@Override
	public String getRecipeCategoryUid(FreezerRecipeJEI arg0)
	{
		return References.ModID + ":freezer";
	}

}
