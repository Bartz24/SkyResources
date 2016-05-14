package com.bartz24.skyresources.jei.cauldron;

import com.bartz24.skyresources.References;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class CauldronRecipeHandler
		implements IRecipeHandler<CauldronRecipeJEI>
{

	@Override
	public String getRecipeCategoryUid()
	{
		return References.ModID + ":cauldron";
	}

	@Override
	public Class<CauldronRecipeJEI> getRecipeClass()
	{
		return CauldronRecipeJEI.class;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(CauldronRecipeJEI recipe)
	{
		return recipe;
	}

	@Override
	public boolean isRecipeValid(CauldronRecipeJEI recipe)
	{
		return recipe.getInputs().size() > 0 && recipe.getOutputs().size() > 0;
	}

}
