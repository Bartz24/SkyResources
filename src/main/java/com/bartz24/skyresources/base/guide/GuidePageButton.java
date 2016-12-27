package com.bartz24.skyresources.base.guide;

import java.util.List;

import com.google.common.base.Strings;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public abstract class GuidePageButton
{
	public GuidePageButton(String display, ItemStack stack)
	{
		this.display = display;
		stackDisplay = stack;
		SkyResourcesGuide.addButtonType(this);
	}

	String display;
	ItemStack stackDisplay;

	public String getDisplay()
	{
		return display;
	}

	public ItemStack getItemDisplay()
	{
		return stackDisplay;
	}

	public void setDisplay(String val)
	{
		display = val;
	}

	public void setItemDisplay(String stackInfo)
	{
		if (!Strings.isNullOrEmpty(stackInfo) && stackInfo.contains(":"))
		{
			String trimmed = stackInfo.replaceAll(" ", "");
			String itemName = trimmed.split(":")[0] + ":" + trimmed.split(":")[1];
			int meta = Integer.parseInt(trimmed.split(":")[2].split("\\*")[0]);

			ResourceLocation resourcelocation = new ResourceLocation(itemName);
			Item item = (Item) Item.REGISTRY.getObject(resourcelocation);

			if (item == null)
				stackDisplay = ItemStack.EMPTY;
			else
				stackDisplay = new ItemStack(item, 1, meta);
		}

	}

	public abstract void setArguments(String[] allArgs);

	/**
	 * 
	 * @return boolean for if GUI should reset or close
	 */
	public abstract boolean onClicked();

	public abstract String getIdentifier();

	public abstract GuidePageButton clone();

	public abstract List<String> getHoverDisplay();
}
