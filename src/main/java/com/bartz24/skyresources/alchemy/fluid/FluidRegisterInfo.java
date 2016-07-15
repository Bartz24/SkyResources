package com.bartz24.skyresources.alchemy.fluid;

public class FluidRegisterInfo
{
	public enum CrystalFluidType
	{
		NORMAL, MOLTEN
	}

	public String name;
	public int color;
	public int rarity;

	public FluidRegisterInfo(String nameIn, int colorIn, int rarityIn)
	{
		name = nameIn;
		color = colorIn;
		rarity = rarityIn;
	}
}
