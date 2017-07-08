package com.bartz24.skyresources.alchemy.item;

import net.minecraft.item.ItemStack;

public class OreRegisterInfo
{
	public String name;
	public int color;
	public int rarity;
	public int dustIndex;
	public ItemStack parentBlock;

	public OreRegisterInfo(String nameIn, int colorIn, int rarityIn, int index, ItemStack parent)
	{
		name = nameIn;
		color = colorIn;
		rarity = rarityIn;
		dustIndex = index;
		parentBlock = parent;
	}
}
