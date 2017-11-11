package com.bartz24.skyresources.jei;

import com.bartz24.skyresources.ItemHelper;
import com.bartz24.skyresources.References;
import com.bartz24.skyresources.base.MachineVariants;
import com.bartz24.skyresources.jei.cauldronclean.CauldronCleanRecipeCategory;
import com.bartz24.skyresources.jei.combustion.CombustionRecipeCategory;
import com.bartz24.skyresources.jei.condenser.CondenserRecipeCategory;
import com.bartz24.skyresources.jei.crucible.CrucibleRecipeCategory;
import com.bartz24.skyresources.jei.freezer.FreezerRecipeCategory;
import com.bartz24.skyresources.jei.fusion.FusionRecipeCategory;
import com.bartz24.skyresources.jei.heatsources.HeatSourceHandler;
import com.bartz24.skyresources.jei.heatsources.HeatSourcesRecipeCategory;
import com.bartz24.skyresources.jei.infusion.InfusionRecipeCategory;
import com.bartz24.skyresources.jei.rockgrinder.RockGrinderRecipeCategory;
import com.bartz24.skyresources.jei.waterextractor.WaterExtractorRecipeCategory;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;
import com.bartz24.skyresources.registry.ModBlocks;
import com.bartz24.skyresources.registry.ModItems;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.IRecipesGui;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
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
				new FreezerRecipeCategory(jeiHelpers.getGuiHelper()),
				new HeatSourcesRecipeCategory(jeiHelpers.getGuiHelper()),
				new WaterExtractorRecipeCategory(jeiHelpers.getGuiHelper()),
				new CauldronCleanRecipeCategory(jeiHelpers.getGuiHelper()),
				new CondenserRecipeCategory(jeiHelpers.getGuiHelper()),
				new FusionRecipeCategory(jeiHelpers.getGuiHelper()));
		registry.addRecipeHandlers(new ProcessRecipeHandler(), new HeatSourceHandler());

		registry.addRecipes(ProcessRecipeManager.infusionRecipes.getRecipes());
		registry.addRecipes(ProcessRecipeManager.combustionRecipes.getRecipes());
		registry.addRecipes(ProcessRecipeManager.rockGrinderRecipes.getRecipes());
		registry.addRecipes(ProcessRecipeManager.crucibleRecipes.getRecipes());
		registry.addRecipes(ProcessRecipeManager.freezerRecipes.getRecipes());
		registry.addRecipes(HeatSourceHandler.getSources());
		registry.addRecipes(ProcessRecipeManager.waterExtractorExtractRecipes.getRecipes());
		registry.addRecipes(ProcessRecipeManager.waterExtractorInsertRecipes.getRecipes());
		registry.addRecipes(ProcessRecipeManager.fusionRecipes.getRecipes());
		if (ProcessRecipeManager.cauldronCleanRecipes.getRecipes().size() > 0)
			registry.addRecipes(ProcessRecipeManager.cauldronCleanRecipes.getRecipes());
		registry.addRecipes(ProcessRecipeManager.condenserRecipes.getRecipes());

		registry.addRecipeCategoryCraftingItem(new ItemStack(Items.CAULDRON, 1, 0),
				References.ModID + ":cauldronclean");
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.rockCleaner),
				References.ModID + ":cauldronclean");
		for(int i = 0;i<MachineVariants.values().length;i++)
		{
			registry.addRecipeCategoryCraftingItem(new ItemStack(ModItems.condenser, 1, i),
					References.ModID + ":condenser");
			registry.addRecipeCategoryCraftingItem(new ItemStack(ModItems.combustionHeater, 1, i),
					References.ModID + ":combustion");
		}
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.crucible, 1, 0), References.ModID + ":crucible");
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.miniFreezer, 1, 0),
				References.ModID + ":freezer");
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.ironFreezer, 1, 0),
				References.ModID + ":freezer");
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.lightFreezer, 1, 0),
				References.ModID + ":freezer");
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModItems.waterExtractor),
				References.ModID + ":waterextractor");
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.aqueousConcentrator),
				References.ModID + ":waterextractor");
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.aqueousDeconcentrator),
				References.ModID + ":waterextractor");
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.dirtFurnace), "minecraft.smelting");
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.dirtFurnace), "minecraft.fuel");
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.fusionTable),
				References.ModID + ":fusion");
		for (ItemStack i : ItemHelper.getRockGrinders())
		{
			registry.addRecipeCategoryCraftingItem(i, References.ModID + ":rockgrinder");
		}
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.rockCrusher), References.ModID + ":rockgrinder");
		for (ItemStack i : ItemHelper.getInfusionStones())
		{
			registry.addRecipeCategoryCraftingItem(i, References.ModID + ":infusion");
		}
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.lifeInfuser), References.ModID + ":infusion");

		registry.addDescription(new ItemStack(ModItems.alchemyComponent, 1, 0), "jei.skyresources.desc.cactusNeedle");
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

	@Override
	public void registerCategories(IRecipeCategoryRegistration arg0)
	{
		
	}
}
