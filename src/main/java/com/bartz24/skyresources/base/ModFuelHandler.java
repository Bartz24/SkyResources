package com.bartz24.skyresources.base;

import com.bartz24.skyresources.registry.ModBlocks;
import com.bartz24.skyresources.registry.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.oredict.OreDictionary;

public class ModFuelHandler implements IFuelHandler
{
	@Override
	public int getBurnTime(ItemStack fuel)
	{

		if(fuel.isItemEqual(new ItemStack(ModItems.alchemyComponent, 1, 6)))
			return 3000;
		else if(fuel.isItemEqual(new ItemStack(ModBlocks.coalInfusedBlock)))
			return 30000;
		else if(fuel.isItemEqual(new ItemStack(ModBlocks.compressedCoalBlock)))
			return 128000;
		else if(fuel.isItemEqual(new ItemStack(ModBlocks.petrifiedWood)))
			return 2400;
		else if(fuel.isItemEqual(new ItemStack(ModBlocks.petrifiedPlanks)))
			return 600;
		
		return 0;
	}

}
