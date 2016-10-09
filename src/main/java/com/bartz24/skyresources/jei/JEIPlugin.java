package com.bartz24.skyresources.jei;

import com.bartz24.skyresources.ItemHelper;
import com.bartz24.skyresources.References;
import com.bartz24.skyresources.jei.cauldron.CauldronRecipeCategory;
import com.bartz24.skyresources.jei.cauldron.CauldronRecipeHandler;
import com.bartz24.skyresources.jei.cauldron.CauldronRecipeMaker;
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
import com.bartz24.skyresources.jei.freezer.FreezerRecipeCategory;
import com.bartz24.skyresources.jei.freezer.FreezerRecipeHandler;
import com.bartz24.skyresources.jei.freezer.FreezerRecipeMaker;
import com.bartz24.skyresources.jei.heatsources.HeatSourcesRecipeCategory;
import com.bartz24.skyresources.jei.heatsources.HeatSourcesRecipeHandler;
import com.bartz24.skyresources.jei.heatsources.HeatSourcesRecipeMaker;
import com.bartz24.skyresources.jei.infusion.InfusionRecipeCategory;
import com.bartz24.skyresources.jei.infusion.InfusionRecipeHandler;
import com.bartz24.skyresources.jei.infusion.InfusionRecipeMaker;
import com.bartz24.skyresources.jei.purificationVessel.PurificationVesselRecipeCategory;
import com.bartz24.skyresources.jei.purificationVessel.PurificationVesselRecipeHandler;
import com.bartz24.skyresources.jei.purificationVessel.PurificationVesselRecipeMaker;
import com.bartz24.skyresources.jei.rockgrinder.RockGrinderRecipeCategory;
import com.bartz24.skyresources.jei.rockgrinder.RockGrinderRecipeHandler;
import com.bartz24.skyresources.jei.rockgrinder.RockGrinderRecipeMaker;
import com.bartz24.skyresources.jei.waterextractor.WaterExtractorRecipeCategory;
import com.bartz24.skyresources.jei.waterextractor.extract.WaterExtractorExtractRecipeHandler;
import com.bartz24.skyresources.jei.waterextractor.extract.WaterExtractorExtractRecipeMaker;
import com.bartz24.skyresources.jei.waterextractor.insert.WaterExtractorInsertRecipeHandler;
import com.bartz24.skyresources.jei.waterextractor.insert.WaterExtractorInsertRecipeMaker;
import com.bartz24.skyresources.registry.ModBlocks;
import com.bartz24.skyresources.registry.ModItems;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.IRecipesGui;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.gui.Focus;
import mezz.jei.plugins.jei.JEIInternalPlugin;
import net.minecraft.init.Items;
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
				new CombustionRecipeCategory(jeiHelpers.getGuiHelper()),
				new RockGrinderRecipeCategory(jeiHelpers.getGuiHelper()),
				new CrucibleRecipeCategory(jeiHelpers.getGuiHelper()),
				new ConcentratorRecipeCategory(jeiHelpers.getGuiHelper()),
				new CondenserRecipeCategory(jeiHelpers.getGuiHelper()),
				new PurificationVesselRecipeCategory(jeiHelpers.getGuiHelper()),
				new FreezerRecipeCategory(jeiHelpers.getGuiHelper()),
				new HeatSourcesRecipeCategory(jeiHelpers.getGuiHelper()),
				new CauldronRecipeCategory(jeiHelpers.getGuiHelper()),
				new WaterExtractorRecipeCategory(jeiHelpers.getGuiHelper()));
		registry.addRecipeHandlers(new InfusionRecipeHandler(), new CombustionRecipeHandler(),
				new RockGrinderRecipeHandler(), new CrucibleRecipeHandler(), new ConcentratorRecipeHandler(),
				new CondenserRecipeHandler(), new PurificationVesselRecipeHandler(), new FreezerRecipeHandler(),
				new HeatSourcesRecipeHandler(), new CauldronRecipeHandler(), new WaterExtractorExtractRecipeHandler(),
				new WaterExtractorInsertRecipeHandler());

		registry.addRecipes(InfusionRecipeMaker.getRecipes());
		registry.addRecipes(CombustionRecipeMaker.getRecipes());
		registry.addRecipes(RockGrinderRecipeMaker.getRecipes());
		registry.addRecipes(CrucibleRecipeMaker.getRecipes());
		registry.addRecipes(ConcentratorRecipeMaker.getRecipes());
		registry.addRecipes(CondenserRecipeMaker.getRecipes());
		registry.addRecipes(PurificationVesselRecipeMaker.getRecipes());
		registry.addRecipes(FreezerRecipeMaker.getRecipes());
		registry.addRecipes(HeatSourcesRecipeMaker.getRecipes());
		registry.addRecipes(CauldronRecipeMaker.getRecipes());
		registry.addRecipes(WaterExtractorExtractRecipeMaker.getRecipes());
		registry.addRecipes(WaterExtractorInsertRecipeMaker.getRecipes());

		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.combustionHeater, 1, 0),
				References.ModID + ":combustion");
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.combustionHeater, 1, 1),
				References.ModID + ":combustion");
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.combustionHeater, 1, 2),
				References.ModID + ":combustion");
		registry.addRecipeCategoryCraftingItem(new ItemStack(Items.CAULDRON, 1, 0), References.ModID + ":cauldron");
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.concentrator, 1, 0),
				References.ModID + ":concentrator");
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.alchemicalCondenser, 1, 0),
				References.ModID + ":condenser");
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.advancedCoolingCondenser, 1, 0),
				References.ModID + ":condenser");
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.crucible, 1, 0), References.ModID + ":crucible");
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.miniFreezer, 1, 0),
				References.ModID + ":freezer");
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.ironFreezer, 1, 0),
				References.ModID + ":freezer");
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.purificationVessel, 1, 0),
				References.ModID + ":purificationVessel");
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModItems.waterExtractor),
				References.ModID + ":waterextractor");
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.dirtFurnace), "minecraft.smelting");
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.dirtFurnace), "minecraft.fuel");
		for (ItemStack i : ItemHelper.getRockGrinders())
		{
			registry.addRecipeCategoryCraftingItem(i, References.ModID + ":rockgrinder");
		}
		for (ItemStack i : ItemHelper.getInfusionStones())
		{
			registry.addRecipeCategoryCraftingItem(i, References.ModID + ":infusion");
		}
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.lifeInfuser), References.ModID + ":infusion");

		registry.addDescription(new ItemStack(ModItems.alchemyComponent, 1, 0), "jei.skyresources.desc.cactusNeedle");
		registry.addDescription(new ItemStack(ModItems.alchemyComponent, 1, 1),
				"jei.skyresources.desc.cactusNeedleBloody");
		registry.addDescription(new ItemStack(ModBlocks.blazePowderBlock, 1, 0),
				"jei.skyresources.desc.blazePowderBlock");
	}

	public static void openRecipesGui(ItemStack stack)
	{
		IRecipesGui gui = JEIInternalPlugin.jeiRuntime.getRecipesGui();
		gui.show(new Focus(mezz.jei.api.recipe.IFocus.Mode.OUTPUT, stack));
	}

	@Override
	public void registerIngredients(IModIngredientRegistration imodingredientregistration)
	{

	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistry isubtyperegistry)
	{

	}
}
