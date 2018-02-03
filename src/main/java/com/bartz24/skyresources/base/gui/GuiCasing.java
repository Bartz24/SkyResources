package com.bartz24.skyresources.base.gui;

import java.io.IOException;
import java.util.List;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.base.tile.TileCasing;
import com.bartz24.skyresources.network.DumpMessage;
import com.bartz24.skyresources.network.SkyResourcesPacketHandler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiCasing extends GuiContainer
{

	private IInventory playerInv;
	private TileCasing tile;

	public GuiCasing(IInventory playerInv, TileCasing te)
	{
		super(new ContainerCasing(playerInv, te));

		this.playerInv = playerInv;
		this.tile = te;

		this.xSize = te.machineStored.isEmpty() ? 176 : te.getMachine().getGuiSize(te.machineStored)[0];
		this.ySize = te.machineStored.isEmpty() ? 166 : te.getMachine().getGuiSize(te.machineStored)[0];
	}
	
	public void initGui()
	{
		super.initGui();
		if(!tile.machineStored.isEmpty())
			tile.getMachine().initGui(this, buttonList);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		super.actionPerformed(button);
		if(!tile.machineStored.isEmpty())
			tile.getMachine().actionPerformed(tile, this, button.id);
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
		if(!tile.machineStored.isEmpty())
			tile.getMachine().drawBackgroundGui(tile, this, fontRenderer, mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		IBlockState state = tile.getWorld().getBlockState(tile.getPos());
		this.fontRenderer.drawString(
				new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state)).getDisplayName(), 8, 6,
				4210752);
		this.fontRenderer.drawString(tile.machineStored.getDisplayName(), 8, 14, 4210752);
		this.fontRenderer.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8,
				72 + (tile.machineStored.isEmpty() ? 0 : tile.getMachine().getInvPos(tile.machineStored)[1]), 4210752);
		if(!tile.machineStored.isEmpty())
			tile.getMachine().drawForegroundGui(tile, this, fontRenderer, mouseX, mouseY);

	}

	public void drawHoveringText(List<String> textLines, int x, int y, FontRenderer font)
	{
		super.drawHoveringText(textLines, x, y, font);
	}
}
