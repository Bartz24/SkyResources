package com.bartz24.skyresources.jei.purificationVessel;

import com.bartz24.skyresources.References;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class PurificationVesselRecipeHandler implements IRecipeHandler<PurificationVesselRecipeJEI>
{

	@Override
	public String getRecipeCategoryUid()
	{
		return References.ModID + ":purificationVessel";
	}

	@Override
	public Class<PurificationVesselRecipeJEI> getRecipeClass()
	{
		return PurificationVesselRecipeJEI.class;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(PurificationVesselRecipeJEI recipe)
	{
		return recipe;
	}

	@Override
	public boolean isRecipeValid(PurificationVesselRecipeJEI recipe)
	{
		return recipe.getFluidInputs().size() > 0
				&& recipe.getFluidOutputs().size() > 0;
	}

}
