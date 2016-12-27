package com.bartz24.skyresources.jei.infusion;

import com.bartz24.skyresources.References;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class InfusionRecipeHandler implements IRecipeHandler<InfusionRecipeJEI>
{

	@Override
	public Class<InfusionRecipeJEI> getRecipeClass()
	{
		return InfusionRecipeJEI.class;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(InfusionRecipeJEI recipe)
	{
		return recipe;
	}

	@Override
	public boolean isRecipeValid(InfusionRecipeJEI recipe)
	{
		return recipe.getInputs().size() > 0 && recipe.getOutputs().size() > 0;
	}

	@Override
	public String getRecipeCategoryUid(InfusionRecipeJEI arg0)
	{
		return References.ModID + ":infusion";
	}

}
