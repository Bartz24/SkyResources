package com.bartz24.skymod.base.item;

import com.bartz24.skymod.References;
import com.bartz24.skymod.registry.ModCreativeTabs;

import net.minecraft.item.ItemFood;

public class ModItemFood extends ItemFood
{

	public ModItemFood(int amount, float saturation, boolean isWolfFood, String unlocalizedName, String registryName)
	{
		super(amount, saturation, isWolfFood);
        this.setUnlocalizedName(References.ModID + "." + unlocalizedName);
        setRegistryName(registryName);
		this.setCreativeTab(ModCreativeTabs.tabMain);
	}

}
