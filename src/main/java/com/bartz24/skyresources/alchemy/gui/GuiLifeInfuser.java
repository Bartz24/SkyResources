package com.bartz24.skyresources.alchemy.gui;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.alchemy.gui.container.ContainerLifeInfuser;
import com.bartz24.skyresources.alchemy.tile.LifeInfuserTile;
import com.bartz24.skyresources.base.gui.GuiHelper;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiLifeInfuser extends GuiContainer
{

	private IInventory playerInv;
	private LifeInfuserTile tile;

	public GuiLifeInfuser(IInventory playerInv, LifeInfuserTile te)
	{
		super(new ContainerLifeInfuser(playerInv, te));

		this.playerInv = playerInv;
		this.tile = te;

		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager()
				.bindTexture(new ResourceLocation(References.ModID, "textures/gui/infuser.png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String s = tile.getDisplayName().getUnformattedText();
		this.fontRenderer.drawString(s, 88 - this.fontRenderer.getStringWidth(s) / 2, 6,
				4210752);
		this.fontRenderer.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, 72,
				4210752);

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager()
				.bindTexture(new ResourceLocation(References.ModID, "textures/gui/guiIcons.png"));
		this.drawTexturedModalRect(100, 50, 0, 16, 32, 32);
		if (tile.hasValidMultiblock())
			this.drawTexturedModalRect(132, 58, 0, 0, 16, 16);
		else
			this.drawTexturedModalRect(132, 58, 16, 0, 16, 16);

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/icons.png"));
		this.drawTexturedModalRect(120, 29, 53, 1, 8, 8);
		this.fontRenderer.drawString("x" + (float)tile.getHealthInGem()/2F, 130, 29,
				4210752);

		IBlockState state = tile.getWorld().getBlockState(tile.getPos().down());

		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		if (!state.getBlock().isAir(state, tile.getWorld(), tile.getPos().down()))
		{
			drawItem(new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state)),
					80 + guiLeft - k, 52 + guiTop - l);
		}

		if (GuiHelper.isMouseInRect(80 + guiLeft, 52 + guiTop, 16, 16, mouseX, mouseY)
				&& !state.getBlock().isAir(state, tile.getWorld(), tile.getPos().down()))
		{

			GlStateManager.disableLighting();
			GlStateManager.disableDepth();
			GlStateManager.colorMask(true, true, true, false);
			this.drawGradientRect(80, 52, 80 + 16, 52 + 16, -2130706433, -2130706433);
			GlStateManager.colorMask(true, true, true, true);
			GlStateManager.enableLighting();
			GlStateManager.enableDepth();

			this.renderToolTip(
					new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state)),
					mouseX - k, mouseY - l);
		}

		if (GuiHelper.isMouseInRect(108 + guiLeft, 52 + guiTop, 50, 28, mouseX, mouseY))
		{
			List list = new ArrayList();
			if (tile.hasValidMultiblock())
				list.add("Multiblock Formed!");
			else
				list.add("Multiblock Not Formed.");
			this.drawHoveringText(list, mouseX - k, mouseY - l, fontRenderer);
		}
	}

	void drawItem(ItemStack stack, int x, int y)
	{
		RenderHelper.enableGUIStandardItemLighting();
		this.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
		RenderHelper.disableStandardItemLighting();

	}
}
