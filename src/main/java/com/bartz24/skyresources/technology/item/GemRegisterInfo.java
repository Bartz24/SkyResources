package com.bartz24.skyresources.technology.item;

import net.minecraft.item.ItemStack;

public class GemRegisterInfo
{
	public String name;
	public int color;
	public float rarity;
	public ItemStack parentBlock;
	public String oreOverride;

	public GemRegisterInfo(String nameIn, int colorIn, float rarityIn, ItemStack parent)
	{
		name = nameIn;
		color = colorIn;
		rarity = rarityIn;
		parentBlock = parent;		
	}
	
	public GemRegisterInfo(String nameIn, int colorIn, float rarityIn, ItemStack parent, String override)
	{
		name = nameIn;
		color = colorIn;
		rarity = rarityIn;
		parentBlock = parent;	
		oreOverride = override;
	}
}
