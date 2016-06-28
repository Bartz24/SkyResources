package com.bartz24.skyresources.base.guide;

import net.minecraft.item.ItemStack;

public class GuidePage
{
	public String pageCategory;
	public String pageDisplay;
	public String pageId;
	public ItemStack pageItemDisplay;
	public String pageInfo;
	
	public GuidePage(String id, String category, String display, ItemStack stack, String info)
	{
		pageId = id;
		pageCategory = category;
		pageDisplay = display;
		pageItemDisplay = stack;
		pageInfo = info;
	}
}
