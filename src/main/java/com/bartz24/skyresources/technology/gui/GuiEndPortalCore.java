package com.bartz24.skyresources.technology.gui;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.base.gui.GuiHelper;
import com.bartz24.skyresources.technology.gui.container.ContainerEndPortalCore;
import com.bartz24.skyresources.technology.tile.TileEndPortalCore;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiEndPortalCore extends GuiContainer
{

	private IInventory playerInv;
	private TileEndPortalCore tile;

	public GuiEndPortalCore(IInventory playerInv, TileEndPortalCore te)
	{
		super(new ContainerEndPortalCore(playerInv, te));

		this.playerInv = playerInv;
		this.tile = te;

		this.xSize = 176;
		this.ySize = 166;
	}
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks,
			int mouseX, int mouseY)
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(
				References.ModID, "textures/gui/blankInventory.png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize,
				this.ySize);

		this.drawTexturedModalRect(this.guiLeft + 79,
				this.guiTop + 52, 7, 83, 18, 18);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String s = tile.getDisplayName().getUnformattedText();
		this.fontRenderer.drawString(s,
				88 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
		this.fontRenderer.drawString(
				this.playerInv.getDisplayName().getUnformattedText(), 8, 72,
				4210752);

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(
				References.ModID, "textures/gui/guiIcons.png"));
		this.drawTexturedModalRect(3, 12, 0, 16, 32, 28);
		if (tile.hasValidMultiblockTier2())
			this.drawTexturedModalRect(42, 20, 0, 0, 16, 16);
		if (tile.hasValidMultiblock())
			this.drawTexturedModalRect(35, 20, 0, 0, 16, 16);
		else
			this.drawTexturedModalRect(35, 20, 16, 0, 16, 16);


		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		if (GuiHelper.isMouseInRect(3 + guiLeft, 12 + guiTop, 50, 28, mouseX, mouseY))
		{
			List list = new ArrayList();
			if (tile.hasValidMultiblock())
				list.add("Multiblock Formed!");
			else
				list.add("Multiblock Not Formed.");
			this.drawHoveringText(list, mouseX - k, mouseY - l, fontRenderer);
		}
		
		
	}
}
