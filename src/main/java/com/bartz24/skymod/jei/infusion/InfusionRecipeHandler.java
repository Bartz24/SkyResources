package com.bartz24.skymod.jei.infusion;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

import com.bartz24.skymod.References;

public class InfusionRecipeHandler implements IRecipeHandler<InfusionRecipeJEI>
{

	@Override
	public String getRecipeCategoryUid()
	{
		return References.ModID + ":infusion";
	}

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

}
