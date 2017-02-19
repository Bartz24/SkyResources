package com.bartz24.skyresources.technology.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.base.gui.GuiHelper;
import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;
import com.bartz24.skyresources.technology.block.BlockMiniFreezer;
import com.bartz24.skyresources.technology.gui.container.ContainerFreezer;
import com.bartz24.skyresources.technology.tile.MiniFreezerTile;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiFreezer extends GuiContainer
{

	private IInventory playerInv;
	private MiniFreezerTile tile;

	public GuiFreezer(IInventory playerInv, MiniFreezerTile te)
	{
		super(new ContainerFreezer(playerInv, te));

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
				.bindTexture(new ResourceLocation(References.ModID, "textures/gui/blankInventory.png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		for (int y = 0; y < Math.floor((float) tile.getInventory().getSlots() / 2f / 5f) + 1; ++y)
		{
			for (int x = 0; x < Math.min(5, tile.getInventory().getSlots() / 2f - y * 5); ++x)
			{
				this.drawTexturedModalRect(this.guiLeft + 52 + x * 18, this.guiTop + 21 + y * 18, 7, 83, 18, 18);
				this.drawTexturedModalRect(this.guiLeft + 52 + x * 18, this.guiTop + 39 + y * 18, 7, 83, 18, 18);
			}
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String s = tile.getDisplayName().getUnformattedText();
		this.fontRenderer.drawString(s, 88 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
		this.fontRenderer.drawString("Speed: x" + tile.getFreezerSpeed(), 110, 56, 4210752);
		this.fontRenderer.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, 72, 4210752);

		drawProgress();

		if (!(tile.getWorld().getBlockState(tile.getPos()).getBlock() instanceof BlockMiniFreezer))
		{

			GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
			this.mc.getTextureManager()
					.bindTexture(new ResourceLocation(References.ModID, "textures/gui/guiIcons.png"));
			this.drawTexturedModalRect(3, 12, 0, 16, 32, 32);

			if (tile.hasValidMulti())
				this.drawTexturedModalRect(35, 20, 0, 0, 16, 16);
			else
				this.drawTexturedModalRect(35, 20, 16, 0, 16, 16);

			if (GuiHelper.isMouseInRect(3 + guiLeft, 12 + guiTop, 50, 32, mouseX, mouseY))
			{
				int k = (this.width - this.xSize) / 2;
				int l = (this.height - this.ySize) / 2;
				List list = new ArrayList();
				if (tile.hasValidMulti())
					list.add("Multiblock Formed!");
				else
					list.add("Multiblock Not Formed.");
				this.drawHoveringText(list, mouseX - k, mouseY - l, fontRenderer);
			}
		}
	}

	void drawProgress()
	{
		GL11.glEnable(GL11.GL_BLEND);
		GlStateManager.color(1.0f, 1.0f, 1.0f, 0.9f);
		this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/blocks/ice_packed.png"));

		for (int y = 0; y < Math.floor((float) tile.getInventory().getSlots() / 5f) + 1; ++y)
		{
			for (int x = 0; x < Math.min(5, tile.getInventory().getSlots() / 2 - y * 5); ++x)
			{
				ProcessRecipe recipe = ProcessRecipeManager.freezerRecipes
						.getRecipe(tile.getInventory().getStackInSlot(y * 5 + x), Integer.MAX_VALUE, false, false);
				if (tile.getUpdateData(y * 5 + x) <= 0 || recipe == null)
					continue;

				int height = (int) ((float) tile.getUpdateData(y * 5 + x) * 16F
						/ (float) tile.getTimeReq(recipe, tile.getInventory().getStackInSlot(y * 5 + x)));
				this.drawTexturedModalRect(53 + x * 18, +22 + y * 18 + 16 - height, 0, 16 - height, 16, height);
			}
		}
		GL11.glDisable(GL11.GL_BLEND);
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
	}
}
