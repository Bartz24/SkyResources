package com.bartz24.skyresources.jei.waterextractor.insert;

import com.bartz24.skyresources.References;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class WaterExtractorInsertRecipeHandler
		implements IRecipeHandler<WaterExtractorInsertRecipeJEI>
{
	@Override
	public Class<WaterExtractorInsertRecipeJEI> getRecipeClass()
	{
		return WaterExtractorInsertRecipeJEI.class;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(WaterExtractorInsertRecipeJEI recipe)
	{
		return recipe;
	}

	@Override
	public boolean isRecipeValid(WaterExtractorInsertRecipeJEI recipe)
	{
		return recipe.getInputs().size() > 0 && recipe.getFluidInputs().size() > 0 && recipe.getOutputs().size() > 0;
	}

	@Override
	public String getRecipeCategoryUid(WaterExtractorInsertRecipeJEI arg0)
	{
		return References.ModID + ":waterextractor";
	}

}
