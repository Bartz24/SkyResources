package com.bartz24.skyresources.plugin.thermal;

import com.bartz24.skyresources.base.HeatSources;
import com.bartz24.skyresources.base.guide.SkyResourcesGuide;
import com.bartz24.skyresources.plugin.IModPlugin;
import com.bartz24.skyresources.registry.ModBlocks;
import com.bartz24.skyresources.registry.ModFluids;
import com.bartz24.skyresources.registry.ModItems;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class ThermalPlugin implements IModPlugin
{

	public void preInit()
	{

	}

	public void init()
	{
		addCrucibleRecipe(new FluidStack(ModFluids.crystalFluid, 1000), new ItemStack(ModItems.alchemyComponent, 1, 1),
				3500);
		addCrucibleRecipe(new FluidStack(FluidRegistry.LAVA, 1000), new ItemStack(ModBlocks.blazePowderBlock),
				55000);

		Item wrench = Item.REGISTRY.getObject(new ResourceLocation("thermalfoundation", "wrench"));
		Block pyrotheum = Block.REGISTRY.getObject(new ResourceLocation("thermalfoundation", "fluid_pyrotheum"));
		
		HeatSources.addHeatSource(pyrotheum.getDefaultState(), 10);
		
		SkyResourcesGuide.addPage("thermal", "guide.skyresources.misc", new ItemStack(wrench));
	}

	public void postInit()
	{

	}

	public void initRenderers()
	{

	}

	public void addCrucibleRecipe(FluidStack out, ItemStack in, int energy)
	{
		NBTTagCompound tag = new NBTTagCompound();
		tag.setTag("output", out.writeToNBT(new NBTTagCompound()));
		tag.setTag("input", in.writeToNBT(new NBTTagCompound()));
		tag.setInteger("energy", energy);
		
        FMLInterModComms.sendMessage("thermalexpansion", "addcruciblerecipe", tag);
	}
}
