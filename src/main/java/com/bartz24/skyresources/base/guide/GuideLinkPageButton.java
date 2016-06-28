package com.bartz24.skyresources.base.guide;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.base.guide.gui.GuideGUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;

public class GuideLinkPageButton extends GuidePageButton
{	

	public GuideLinkPageButton(String linkId, String display, ItemStack stack)
	{
		super(display, stack);	
		id = linkId;
	}
	
	String id;
	
	public GuidePage getLinkPage()
	{
		return SkyResourcesGuide.getPage(id);
	}

	@Override
	public String getIdentifier()
	{
		return "link";
	}

	@Override
	public boolean onClicked()
	{
		GuiScreen screen = Minecraft.getMinecraft().currentScreen;
		if(screen instanceof GuideGUI)
		{
			((GuideGUI) screen).openPage(SkyResourcesGuide.getPage(id));
		}
		return true;
	}

	@Override
	public void setArguments(String[] allArgs)
	{
		id = allArgs[3];
	}

	@Override
	public GuidePageButton clone()
	{
		return new GuideLinkPageButton(this.id, this.display, this.stackDisplay);
	}

	@Override
	public List<String> getHoverDisplay()
	{
		List<String> list = new ArrayList<String>();
		list.add("Click to go to page");
		return list;
	}
}
