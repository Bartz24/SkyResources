package com.bartz24.skyresources.jei.condenser;

import com.bartz24.skyresources.References;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class CondenserRecipeHandler
		implements IRecipeHandler<CondenserRecipeJEI>
{

	@Override
	public String getRecipeCategoryUid()
	{
		return References.ModID + ":condenser";
	}

	@Override
	public Class<CondenserRecipeJEI> getRecipeClass()
	{
		return CondenserRecipeJEI.class;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(CondenserRecipeJEI recipe)
	{
		return recipe;
	}

	@Override
	public boolean isRecipeValid(CondenserRecipeJEI recipe)
	{
		return recipe.getInputs().size() > 0 && recipe.getOutputs().size() > 0;
	}

}
