package com.bartz24.skyresources.jei;

import com.bartz24.skyresources.jei.combustion.CombustionRecipeCategory;
import com.bartz24.skyresources.jei.combustion.CombustionRecipeHandler;
import com.bartz24.skyresources.jei.combustion.CombustionRecipeMaker;
import com.bartz24.skyresources.jei.infusion.InfusionRecipeCategory;
import com.bartz24.skyresources.jei.infusion.InfusionRecipeHandler;
import com.bartz24.skyresources.jei.infusion.InfusionRecipeMaker;
import com.bartz24.skyresources.registry.ModBlocks;
import com.bartz24.skyresources.registry.ModItems;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import net.minecraft.item.ItemStack;

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
				"jei.skyresources.desc.cactusNeedle");
		registry.addDescription(new ItemStack(ModItems.alchemyComponent, 1, 1),
				"jei.skyresources.desc.cactusNeedleBloody");
		registry.addDescription(new ItemStack(ModBlocks.dryCactus, 1, 0),
				"jei.skyresources.desc.dryCactus");
		registry.addDescription(new ItemStack(ModBlocks.blazePowderBlock, 1, 0),
				"jei.skyresources.desc.blazePowderBlock");
	}

}
