package com.bartz24.skyresources.alchemy.gui;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.alchemy.gui.container.ContainerFusionTable;
import com.bartz24.skyresources.alchemy.tile.TileAlchemyFusionTable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiFusionTable extends GuiContainer
{

	private IInventory playerInv;
	private TileAlchemyFusionTable tile;

	public GuiFusionTable(IInventory playerInv, TileAlchemyFusionTable te)
	{
		super(new ContainerFusionTable(playerInv, te));

		this.playerInv = playerInv;
		this.tile = te;

		this.xSize = 176;
		this.ySize = 181;
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(References.ModID, "textures/gui/fusiontable.png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String s = tile.getDisplayName().getUnformattedText();
		this.fontRenderer.drawString(s, 88 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
		this.fontRenderer.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, 87, 4210752);

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(References.ModID, "textures/gui/fusiontable.png"));
		int height = (int) ((float) tile.getProgress() / 100f * 17f);
		this.drawTexturedModalRect(7, 51, 0, 181, 162, height);

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(References.ModID, "textures/gui/guiicons.png"));
		this.drawTexturedModalRect(134, 73, 29, 60, 3, 18);

		this.fontRenderer.drawString((Math.round(tile.getCurItemYield() * 100f)) + "%", 140, 78, 4210752);

		height = (int) ((float) tile.getCurItemLeft() * 16f);
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		Minecraft.getMinecraft().getTextureManager()
				.bindTexture(new ResourceLocation(References.ModID, "textures/gui/guiicons.png"));
		this.drawTexturedModalRect(135, 90 - height, 26, 77 - height, 1, height);

		height = (int) ((float) tile.getCurYield() * 26f);
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(References.ModID, "textures/gui/fusiontable.png"));
		this.drawTexturedModalRect(103, 69, 176, 0, 3, 26);
		this.drawTexturedModalRect(104, 95 - height, 179, 26 - height, 1, height);

		for (int i = 0; i < 9; i++)
		{
			if (tile.getInventory().getStackInSlot(i + 1).isEmpty() && !tile.getFilterStack(i).isEmpty())
			{
				RenderHelper.enableGUIStandardItemLighting();
				ItemStack stack = tile.getFilterStack(i);
				stack.setCount(1);
				this.itemRender.renderItemAndEffectIntoGUI(stack, 8 + i * 18, 34);
				this.fontRenderer.drawString("0", 8 + i * 18, 52, 0xFFFF0000);
				RenderHelper.disableStandardItemLighting();
			}
		}
	}
}
