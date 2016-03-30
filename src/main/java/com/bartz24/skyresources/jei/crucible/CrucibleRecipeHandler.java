package com.bartz24.skyresources.jei.crucible;

import com.bartz24.skyresources.References;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class CrucibleRecipeHandler implements IRecipeHandler<CrucibleRecipeJEI>
{

	@Override
	public String getRecipeCategoryUid()
	{
		return References.ModID + ":crucible";
	}

	@Override
	public Class<CrucibleRecipeJEI> getRecipeClass()
	{
		return CrucibleRecipeJEI.class;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(CrucibleRecipeJEI recipe)
	{
		return recipe;
	}

	@Override
	public boolean isRecipeValid(CrucibleRecipeJEI recipe)
	{
		return recipe.getInputs().size() > 0
				&& recipe.getFluidOutputs().size() > 0;
	}

}
