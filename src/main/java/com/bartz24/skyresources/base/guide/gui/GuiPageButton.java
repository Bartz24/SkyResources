package com.bartz24.skyresources.base.guide.gui;

import com.bartz24.skyresources.base.guide.GuidePageButton;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.config.GuiUtils;

public class GuiPageButton extends GuiButton
{

	GuidePageButton buttonInfo;

	public GuiPageButton(int buttonId, int x, int y, GuidePageButton button)
	{
		super(buttonId, x, y, 200, 16, "");
		buttonInfo = button;
	}

	public void drawButton(Minecraft mc, int mouseX, int mouseY)
	{
		RenderHelper.enableGUIStandardItemLighting();

		this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition
				&& mouseX < this.xPosition + this.width
				&& mouseY < this.yPosition + this.height;

		mc.getRenderItem().renderItemAndEffectIntoGUI(
				buttonInfo.getItemDisplay(), this.xPosition, this.yPosition);

		mc.fontRendererObj.drawString(buttonInfo.getDisplay(),
				this.xPosition + 20, this.yPosition + 4, 16777215);

		resetWidth();

		if (this.hovered)
		{
			GlStateManager.disableDepth();
			this.drawRect(this.xPosition, this.yPosition,
					this.xPosition + this.width, this.yPosition + this.height,
					2138733178);
			GlStateManager.color(1, 1, 1, 1);
			GlStateManager.enableDepth();

			GuiUtils.drawHoveringText(buttonInfo.getHoverDisplay(),
					Minecraft.getMinecraft().currentScreen.width
							- mc.fontRendererObj.getStringWidth(
									buttonInfo.getHoverDisplay().get(0)) -16,
					16, Minecraft.getMinecraft().displayWidth,
					Minecraft.getMinecraft().displayHeight, 100,
					Minecraft.getMinecraft().getRenderManager()
							.getFontRenderer());
		}

		RenderHelper.disableStandardItemLighting();
	}

	public void resetWidth()
	{
		this.width = Minecraft.getMinecraft().fontRendererObj
				.getStringWidth(buttonInfo.getDisplay()) + 22;
	}
	
	public boolean onPressed()
	{
		return buttonInfo.onClicked();
	}

}
