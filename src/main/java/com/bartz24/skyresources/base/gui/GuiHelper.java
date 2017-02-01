package com.bartz24.skyresources.base.gui;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.References;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiHelper
{
	public static boolean isMouseInBounds(int x, int y, int xEnd, int yEnd, int mouseX, int mouseY)
	{
		return mouseX >= x && mouseY >= y && mouseX < xEnd && mouseY < yEnd;
	}

	public static boolean isMouseInRect(int x, int y, int width, int height, int mouseX, int mouseY)
	{
		return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
	}
}
