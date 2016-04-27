package com.bartz24.skyresources.alchemy.render;

import org.lwjgl.opengl.GL11;

import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.alchemy.tile.CrucibleTile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fluids.FluidTank;

public class CrucibleTESR extends TileEntitySpecialRenderer<CrucibleTile>
{

	@Override
	public void renderTileEntityAt(CrucibleTile te, double x, double y,
			double z, float partialTicks, int destroyStage)
	{
		GlStateManager.pushAttrib();
		GlStateManager.pushMatrix();

		GlStateManager.translate(x, y, z);
		GlStateManager.disableRescaleNormal();

		renderFluidContents(te);

		GlStateManager.popMatrix();
		GlStateManager.popAttrib();

	}

	private void renderFluidContents(CrucibleTile crucible)
	{
		FluidTank tank = crucible.getTank();

		if (tank.getFluid() != null && tank.getFluid().getFluid() != null)
		{
			float height = MathHelper.clamp_float(
					(float) tank.getFluidAmount() * 0.65F / (float) tank.getCapacity() + 0.3F,
					0.3F, 0.95F);
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

			GL11.glEnable(GL11.GL_BLEND);
			RandomHelper.setGLColorFromIntPlusAlpha(color);

			renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			renderer.pos(1.0d, height, 1.0d).tex(maxU, maxV).endVertex();
			renderer.pos(1.0d, height, 0).tex(maxU, minV).endVertex();
			renderer.pos(0, height, 0).tex(minU, minV).endVertex();
			renderer.pos(0, height, 1.0d).tex(minU, maxV).endVertex();
			tessellator.draw();
			GL11.glDisable(GL11.GL_BLEND);

			GlStateManager.popMatrix();
		}
	}
}
