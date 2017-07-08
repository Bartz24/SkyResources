package com.bartz24.skyresources.alchemy.gui;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.alchemy.gui.container.ContainerLifeInjector;
import com.bartz24.skyresources.alchemy.tile.LifeInjectorTile;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiLifeInjector extends GuiContainer
{

	private IInventory playerInv;
	private LifeInjectorTile tile;

	public GuiLifeInjector(IInventory playerInv, LifeInjectorTile te)
	{
		super(new ContainerLifeInjector(playerInv, te));

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
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager()
				.bindTexture(new ResourceLocation(References.ModID, "textures/gui/blankInventory.png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		this.drawTexturedModalRect(this.guiLeft + 79, this.guiTop + 52, 7, 83, 18, 18);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String s = tile.getDisplayName().getUnformattedText();
		this.fontRenderer.drawString(s, 88 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
		this.fontRenderer.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, 72, 4210752);

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/icons.png"));
		this.drawTexturedModalRect(120, 29, 53, 1, 8, 8);
		this.fontRenderer.drawString("x" + (float) tile.getHealthInGem() / 2F, 130, 29, 4210752);
	}

	void drawItem(ItemStack stack, int x, int y)
	{
		RenderHelper.enableGUIStandardItemLighting();
		this.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
		RenderHelper.disableStandardItemLighting();

	}
}
