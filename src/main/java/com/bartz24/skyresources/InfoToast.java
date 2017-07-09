package com.bartz24.skyresources;

import net.minecraft.client.gui.toasts.GuiToast;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.ITextComponent;

public class InfoToast implements IToast
{
	private InfoToast.Type type;
	private int time;
	private String title;
	private String subtitle;
	private long firstDrawTime;
	private boolean newDisplay;

	public InfoToast(ITextComponent titleComponent, ITextComponent subtitleComponent, int time)
	{
		this.type = Type.Info;
		this.time = time;
		this.title = titleComponent.getUnformattedText();
		this.subtitle = subtitleComponent == null ? null : subtitleComponent.getUnformattedText();
	}

	public IToast.Visibility draw(GuiToast toastGui, long delta)
	{
		if (this.newDisplay)
		{
			this.firstDrawTime = delta;
			this.newDisplay = false;
		}

		toastGui.getMinecraft().getTextureManager().bindTexture(TEXTURE_TOASTS);
		GlStateManager.color(1.0F, 1.0F, 1.0F);
		toastGui.drawTexturedModalRect(0, 0, 0, 64, 160, 32);

		if (this.subtitle == null)
		{
			toastGui.getMinecraft().fontRenderer.drawString(this.title, 18, 12, -256);
		} else
		{
			toastGui.getMinecraft().fontRenderer.drawString(this.title, 18, 7, -256);
			toastGui.getMinecraft().fontRenderer.drawString(this.subtitle, 18, 18, -1);
		}

		return delta - this.firstDrawTime < time ? IToast.Visibility.SHOW : IToast.Visibility.HIDE;
	}

	public InfoToast.Type getType()
	{
		return this.type;
	}

	public static enum Type
	{
		Info;
	}
}
