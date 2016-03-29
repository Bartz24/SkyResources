package com.bartz24.skymod.jei;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import net.minecraft.item.ItemStack;

import com.bartz24.skymod.jei.combustion.CombustionRecipeCategory;
import com.bartz24.skymod.jei.combustion.CombustionRecipeHandler;
import com.bartz24.skymod.jei.combustion.CombustionRecipeMaker;
import com.bartz24.skymod.jei.infusion.InfusionRecipeCategory;
import com.bartz24.skymod.jei.infusion.InfusionRecipeHandler;
import com.bartz24.skymod.jei.infusion.InfusionRecipeMaker;
import com.bartz24.skymod.registry.ModBlocks;
import com.bartz24.skymod.registry.ModItems;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin
{
	@Override
	public void onRuntimeAvailable(IJeiRuntime arg0)
	{

	}

	@Override
	public void register(IModRegistry registry)
	{
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		registry.addRecipeCategories(new InfusionRecipeCategory(jeiHelpers.getGuiHelper()),
				new CombustionRecipeCategory(jeiHelpers.getGuiHelper()));
		registry.addRecipeHandlers(new InfusionRecipeHandler(), new CombustionRecipeHandler());

		registry.addRecipes(InfusionRecipeMaker.getRecipes());
		registry.addRecipes(CombustionRecipeMaker.getRecipes());

		registry.addDescription(new ItemStack(ModItems.alchemyComponent, 1, 0),
				"jei.skymod.desc.cactusNeedle");
		registry.addDescription(new ItemStack(ModItems.alchemyComponent, 1, 1),
				"jei.skymod.desc.cactusNeedleBloody");
		registry.addDescription(new ItemStack(ModBlocks.dryCactus, 1, 0),
				"jei.skymod.desc.dryCactus");
	}

}
