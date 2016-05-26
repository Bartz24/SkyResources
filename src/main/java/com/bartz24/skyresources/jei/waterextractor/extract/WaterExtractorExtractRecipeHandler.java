package com.bartz24.skyresources.jei.waterextractor.extract;

import com.bartz24.skyresources.References;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class WaterExtractorExtractRecipeHandler
		implements IRecipeHandler<WaterExtractorExtractRecipeJEI>
{

	@Override
	public String getRecipeCategoryUid()
	{
		return References.ModID + ":waterextractor";
	}

	@Override
	public Class<WaterExtractorExtractRecipeJEI> getRecipeClass()
	{
		return WaterExtractorExtractRecipeJEI.class;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(WaterExtractorExtractRecipeJEI recipe)
	{
		return recipe;
	}

	@Override
	public boolean isRecipeValid(WaterExtractorExtractRecipeJEI recipe)
	{
		return recipe.getInputs().size() > 0 && recipe.getFluidOutputs().size() > 0;
	}

}
