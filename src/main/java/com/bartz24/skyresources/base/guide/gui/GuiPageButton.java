package com.bartz24.skyresources.base.guide.gui;

import com.bartz24.skyresources.base.guide.GuidePageButton;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.text.translation.I18n;

public class GuiPageButton extends GuiButton
{

	GuidePageButton buttonInfo;

	public GuiPageButton(int buttonId, int x, int y, GuidePageButton button)
	{
		super(buttonId, x, y, 200, 16, "");
		buttonInfo = button;
	}

	public void drawButton(Minecraft mc, int mouseX, int mouseY, int partialTicks)
	{
		RenderHelper.enableGUIStandardItemLighting();

		this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width
				&& mouseY < this.y + this.height;

		GlStateManager.enableDepth();
		mc.getRenderItem().renderItemAndEffectIntoGUI(buttonInfo.getItemDisplay(), this.x, this.y);

		mc.fontRenderer.drawString(I18n.translateToLocal(buttonInfo.getDisplay()), this.x + 20, this.y + 4, 16777215);

		resetWidth();

		if (this.hovered)
		{
			GlStateManager.disableDepth();
			this.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, 2138733178);
			GlStateManager.color(1, 1, 1, 1);
			GlStateManager.enableDepth();
		}

		RenderHelper.disableStandardItemLighting();
	}

	public void drawScrollButton(Minecraft mc, int x, int y, boolean highlighted)
	{
		RenderHelper.enableGUIStandardItemLighting();

		mc.getRenderItem().renderItemAndEffectIntoGUI(buttonInfo.getItemDisplay(), x, y);

		mc.fontRenderer.drawString(I18n.translateToLocal(buttonInfo.getDisplay()), x + 20, y + 4, 16777215);

		RenderHelper.disableStandardItemLighting();
	}

	public void resetWidth()
	{
		this.width = Minecraft.getMinecraft().fontRenderer.getStringWidth(buttonInfo.getDisplay()) + 22;
	}

	public boolean onPressed()
	{
		return buttonInfo.onClicked();
	}

}
