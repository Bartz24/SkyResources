package com.bartz24.skyresources.technology.gui;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.GuiHelper;
import com.bartz24.skyresources.References;
import com.bartz24.skyresources.technology.gui.container.ContainerCombustionHeater;
import com.bartz24.skyresources.technology.tile.CombustionHeaterTile;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiCombustionHeater extends GuiContainer
{

	private IInventory playerInv;
	private CombustionHeaterTile tile;

	public GuiCombustionHeater(IInventory playerInv, CombustionHeaterTile te)
	{
		super(new ContainerCombustionHeater(playerInv, te));

		this.playerInv = playerInv;
		this.tile = te;

		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks,
			int mouseX, int mouseY)
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(
				References.ModID, "textures/gui/combustionHeater.png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize,
				this.ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String s = tile.getDisplayName().getUnformattedText();
		this.fontRendererObj.drawString(s,
				88 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		this.fontRendererObj.drawString(
				this.playerInv.getDisplayName().getUnformattedText(), 8, 72,
				4210752);

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(
				References.ModID, "textures/gui/guiIcons.png"));
		this.drawTexturedModalRect(3, 12, 0, 16, 32, 32);
		if (tile.hasValidMultiblock())
			this.drawTexturedModalRect(35, 20, 0, 0, 16, 16);
		else
			this.drawTexturedModalRect(35, 20, 16, 0, 16, 16);

		this.mc.getTextureManager().bindTexture(new ResourceLocation(
				References.ModID, "textures/gui/combustionHeater.png"));

		int height = (int) (tile.getField(2) * 69F / 2000F);
		int maxHeatHeight = Math.round((tile.getMaxHeat() * 69F / 2000F));
		this.drawTexturedModalRect(157, 75 - height, 176, 83 - height, 8,
				height);

		this.drawTexturedModalRect(155, 75 - maxHeatHeight, 190, 0, 12, 3);

		if (GuiHelper.isMouseInRect(157 + guiLeft, 6 + guiTop, 8, 69, mouseX,
				mouseY))
		{
			int k = (this.width - this.xSize) / 2;
			int l = (this.height - this.ySize) / 2;
			List list = new ArrayList();
			list.add("§cCurrent Temp: " + tile.getField(2));
			list.add("§7Maximum Temp: " + tile.getMaxHeat());
			this.drawHoveringText(list, mouseX - k, mouseY - l,
					fontRendererObj);
		} else if (GuiHelper.isMouseInRect(3 + guiLeft, 12 + guiTop, 50, 32,
				mouseX, mouseY))
		{
			int k = (this.width - this.xSize) / 2;
			int l = (this.height - this.ySize) / 2;
			List list = new ArrayList();
			if (tile.hasValidMultiblock())
				list.add("Multiblock Formed!");
			else
				list.add("Multiblock Not Formed.");
			this.drawHoveringText(list, mouseX - k, mouseY - l,
					fontRendererObj);
		}
	}
}
