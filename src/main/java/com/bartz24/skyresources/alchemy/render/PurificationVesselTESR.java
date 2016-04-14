package com.bartz24.skyresources.alchemy.render;

import org.lwjgl.opengl.GL11;

import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.alchemy.tile.PurificationVesselTile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fluids.FluidTank;

public class PurificationVesselTESR
		extends TileEntitySpecialRenderer<PurificationVesselTile>
{

	@Override
	public void renderTileEntityAt(PurificationVesselTile te, double x,
			double y, double z, float partialTicks, int destroyStage)
	{
		GlStateManager.pushAttrib();
		GlStateManager.pushMatrix();

		GlStateManager.translate(x, y, z);
		GlStateManager.disableRescaleNormal();

		renderFluidContents(te);
		renderUpperFluidContents(te);

		GlStateManager.popMatrix();
		GlStateManager.popAttrib();

	}

	private void renderFluidContents(PurificationVesselTile tile)
	{
		FluidTank tank = tile.getLowerTank();

		if (tank.getFluid() != null && tank.getFluid().getFluid() != null && tank.getFluidAmount() > 0)
		{
			float height = MathHelper.clamp_float(
					(float) tank.getFluidAmount() * 9F / 16F
							/ (float) tank.getCapacity() + 1F / 16F,
					1F / 16F, 10F / 16F);
			if (tank.getFluidAmount() == 0)
				height = 0;

			GlStateManager.pushMatrix();

			TextureAtlasSprite texture = Minecraft.getMinecraft()
					.getTextureMapBlocks().getAtlasSprite(
							tank.getFluid().getFluid().getStill().toString());

			double minU = texture.getMinU();
			double maxU = texture.getMaxU();
			double minV = texture.getMinV();
			double maxV = texture.getMaxV();

			Tessellator tessellator = Tessellator.getInstance();
			VertexBuffer renderer = tessellator.getBuffer();

			int color;
			color = tank.getFluid().getFluid().getColor(tank.getFluid());

			RandomHelper.setGLColorFromIntPlusAlpha(color);

			renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			renderer.pos(13d / 16d, height, 13d / 16d).tex(maxU, maxV)
					.endVertex();
			renderer.pos(13d / 16d, height, 3d / 16d).tex(maxU, minV)
					.endVertex();
			renderer.pos(3d / 16d, height, 3d / 16d).tex(minU, minV)
					.endVertex();
			renderer.pos(3d / 16d, height, 13d / 16d).tex(minU, maxV)
					.endVertex();

			renderer.pos(13d / 16d, 1F / 16F, 13d / 16d).tex(minU, maxV)
					.endVertex();
			renderer.pos(13d / 16d, 1F / 16F, 3d / 16d).tex(minU, minV)
					.endVertex();
			renderer.pos(13d / 16d, height, 3d / 16d).tex(maxU, minV)
					.endVertex();
			renderer.pos(13d / 16d, height, 13d / 16d).tex(maxU, maxV)
					.endVertex();

			renderer.pos(3d / 16d, 1F / 16F, 13d / 16d).tex(minU, maxV)
					.endVertex();
			renderer.pos(13d / 16d, 1F / 16F, 13d / 16d).tex(minU, minV)
					.endVertex();
			renderer.pos(13d / 16d, height, 13d / 16d).tex(maxU, minV)
					.endVertex();
			renderer.pos(3d / 16d, height, 13d / 16d).tex(maxU, maxV)
					.endVertex();

			renderer.pos(3d / 16d, height, 3d / 16d).tex(maxU, maxV)
					.endVertex();
			renderer.pos(13d / 16d, height, 3d / 16d).tex(maxU, minV)
					.endVertex();
			renderer.pos(13d / 16d, 1F / 16F, 3d / 16d).tex(minU, minV)
					.endVertex();
			renderer.pos(3d / 16d, 1F / 16F, 3d / 16d).tex(minU, maxV)
					.endVertex();

			renderer.pos(3d / 16d, 1F / 16F, 3d / 16d).tex(minU, maxV)
					.endVertex();
			renderer.pos(3d / 16d, 1F / 16F, 13d / 16d).tex(minU, minV)
					.endVertex();
			renderer.pos(3d / 16d, height, 13d / 16d).tex(maxU, minV)
					.endVertex();
			renderer.pos(3d / 16d, height, 3d / 16d).tex(maxU, maxV)
					.endVertex();

			renderer.pos(3d / 16d, 1F / 16F, 13d / 16d).tex(minU, maxV)
					.endVertex();
			renderer.pos(3d / 16d, 1F / 16F, 3d / 16d).tex(minU, minV)
					.endVertex();
			renderer.pos(13d / 16d, 1F / 16F, 3d / 16d).tex(maxU, minV)
					.endVertex();
			renderer.pos(13d / 16d, 1F / 16F, 13d / 16d).tex(maxU, maxV)
					.endVertex();
			tessellator.draw();

			GlStateManager.popMatrix();
		}
	}

	private void renderUpperFluidContents(PurificationVesselTile tile)
	{
		FluidTank tank = tile.getUpperTank();

		if (tank.getFluid() != null && tank.getFluid().getFluid() != null && tank.getFluidAmount() > 0)
		{
			float height = MathHelper.clamp_float(
					(float) tank.getFluidAmount() * 4F / 16F
							/ (float) tank.getCapacity() + 11F / 16F,
					11F / 16F, 15F / 16F);
			if (tank.getFluidAmount() == 0)
				height = 0;

			GlStateManager.pushMatrix();

			TextureAtlasSprite texture = Minecraft.getMinecraft()
					.getTextureMapBlocks().getAtlasSprite(
							tank.getFluid().getFluid().getStill().toString());

			double minU = texture.getMinU();
			double maxU = texture.getMaxU();
			double minV = texture.getMinV();
			double maxV = texture.getMaxV();

			Tessellator tessellator = Tessellator.getInstance();
			VertexBuffer renderer = tessellator.getBuffer();

			int color;
			color = tank.getFluid().getFluid().getColor(tank.getFluid());

			RandomHelper.setGLColorFromIntPlusAlpha(color);

			renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			renderer.pos(13d / 16d, height, 13d / 16d).tex(maxU, maxV)
					.endVertex();
			renderer.pos(13d / 16d, height, 3d / 16d).tex(maxU, minV)
					.endVertex();
			renderer.pos(3d / 16d, height, 3d / 16d).tex(minU, minV)
					.endVertex();
			renderer.pos(3d / 16d, height, 13d / 16d).tex(minU, maxV)
					.endVertex();

			renderer.pos(13d / 16d, 11F / 16F, 13d / 16d).tex(minU, maxV)
					.endVertex();
			renderer.pos(13d / 16d, 11F / 16F, 3d / 16d).tex(minU, minV)
					.endVertex();
			renderer.pos(13d / 16d, height, 3d / 16d).tex(maxU, minV)
					.endVertex();
			renderer.pos(13d / 16d, height, 13d / 16d).tex(maxU, maxV)
					.endVertex();

			renderer.pos(3d / 16d, 11F / 16F, 13d / 16d).tex(minU, maxV)
					.endVertex();
			renderer.pos(13d / 16d, 11F / 16F, 13d / 16d).tex(minU, minV)
					.endVertex();
			renderer.pos(13d / 16d, height, 13d / 16d).tex(maxU, minV)
					.endVertex();
			renderer.pos(3d / 16d, height, 13d / 16d).tex(maxU, maxV)
					.endVertex();

			renderer.pos(3d / 16d, height, 3d / 16d).tex(maxU, maxV)
					.endVertex();
			renderer.pos(13d / 16d, height, 3d / 16d).tex(maxU, minV)
					.endVertex();
			renderer.pos(13d / 16d, 11F / 16F, 3d / 16d).tex(minU, minV)
					.endVertex();
			renderer.pos(3d / 16d, 11F / 16F, 3d / 16d).tex(minU, maxV)
					.endVertex();

			renderer.pos(3d / 16d, 11F / 16F, 3d / 16d).tex(minU, maxV)
					.endVertex();
			renderer.pos(3d / 16d, 11F / 16F, 13d / 16d).tex(minU, minV)
					.endVertex();
			renderer.pos(3d / 16d, height, 13d / 16d).tex(maxU, minV)
					.endVertex();
			renderer.pos(3d / 16d, height, 3d / 16d).tex(maxU, maxV)
					.endVertex();
			tessellator.draw();

			GlStateManager.popMatrix();
		}
	}
}
