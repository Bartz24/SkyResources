package com.bartz24.skyresources.base.item;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.registry.ModCreativeTabs;

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
