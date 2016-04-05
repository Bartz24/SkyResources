package com.bartz24.skyresources.jei;

import com.bartz24.skyresources.jei.combustion.CombustionRecipeCategory;
import com.bartz24.skyresources.jei.combustion.CombustionRecipeHandler;
import com.bartz24.skyresources.jei.combustion.CombustionRecipeMaker;
import com.bartz24.skyresources.jei.concentrator.ConcentratorRecipeCategory;
import com.bartz24.skyresources.jei.concentrator.ConcentratorRecipeHandler;
import com.bartz24.skyresources.jei.concentrator.ConcentratorRecipeMaker;
import com.bartz24.skyresources.jei.condenser.CondenserRecipeCategory;
import com.bartz24.skyresources.jei.condenser.CondenserRecipeHandler;
import com.bartz24.skyresources.jei.condenser.CondenserRecipeMaker;
import com.bartz24.skyresources.jei.crucible.CrucibleRecipeCategory;
import com.bartz24.skyresources.jei.crucible.CrucibleRecipeHandler;
import com.bartz24.skyresources.jei.crucible.CrucibleRecipeMaker;
import com.bartz24.skyresources.jei.infusion.InfusionRecipeCategory;
import com.bartz24.skyresources.jei.infusion.InfusionRecipeHandler;
import com.bartz24.skyresources.jei.infusion.InfusionRecipeMaker;
import com.bartz24.skyresources.jei.rockgrinder.RockGrinderRecipeCategory;
import com.bartz24.skyresources.jei.rockgrinder.RockGrinderRecipeHandler;
import com.bartz24.skyresources.jei.rockgrinder.RockGrinderRecipeMaker;
import com.bartz24.skyresources.registry.ModBlocks;
import com.bartz24.skyresources.registry.ModItems;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import net.minecraft.init.Blocks;
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
		registry.addRecipeCategories(
				new InfusionRecipeCategory(jeiHelpers.getGuiHelper()),
				new CombustionRecipeCategory(jeiHelpers.getGuiHelper()),
				new RockGrinderRecipeCategory(jeiHelpers.getGuiHelper()),
				new CrucibleRecipeCategory(jeiHelpers.getGuiHelper()),
				new ConcentratorRecipeCategory(jeiHelpers.getGuiHelper()),
				new CondenserRecipeCategory(jeiHelpers.getGuiHelper()));
		registry.addRecipeHandlers(new InfusionRecipeHandler(),
				new CombustionRecipeHandler(), new RockGrinderRecipeHandler(),
				new CrucibleRecipeHandler(), new ConcentratorRecipeHandler(),
				new CondenserRecipeHandler());

		registry.addRecipes(InfusionRecipeMaker.getRecipes());
		registry.addRecipes(CombustionRecipeMaker.getRecipes());
		registry.addRecipes(RockGrinderRecipeMaker.getRecipes());
		registry.addRecipes(CrucibleRecipeMaker.getRecipes());
		registry.addRecipes(ConcentratorRecipeMaker.getRecipes());
		registry.addRecipes(CondenserRecipeMaker.getRecipes());

		registry.addDescription(new ItemStack(ModItems.alchemyComponent, 1, 0),
				"jei.skyresources.desc.cactusNeedle");
		registry.addDescription(new ItemStack(ModItems.alchemyComponent, 1, 1),
				"jei.skyresources.desc.cactusNeedleBloody");
		registry.addDescription(new ItemStack(ModBlocks.dryCactus, 1, 0),
				"jei.skyresources.desc.dryCactus");
		registry.addDescription(new ItemStack(ModBlocks.blazePowderBlock, 1, 0),
				"jei.skyresources.desc.blazePowderBlock");
		registry.addDescription(new ItemStack(Blocks.clay, 1, 0),
				"jei.skyresources.desc.clay");
	}

}
