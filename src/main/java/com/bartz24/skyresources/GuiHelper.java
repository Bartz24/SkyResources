package com.bartz24.skyresources;

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
