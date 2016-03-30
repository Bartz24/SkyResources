package com.bartz24.skyresources.alchemy.crucible;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.alchemy.tile.CrucibleTile;
import com.bartz24.skyresources.registry.ModFluids;
import com.google.common.base.Function;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
					(float) tank.getFluidAmount() / (float) tank.getCapacity(),
					0.3F, 0.95F);
			if (tank.getFluidAmount() == 0)
				height = 0;


			GlStateManager.pushMatrix();

			TextureAtlasSprite texture = Minecraft.getMinecraft()
					.getTextureMapBlocks().getAtlasSprite(
							tank.getFluid().getFluid().getStill().toString());

			double minU = (double) texture.getMinU();
			double maxU = (double) texture.getMaxU();
			double minV = (double) texture.getMinV();
			double maxV = (double) texture.getMaxV();

			Tessellator tessellator = Tessellator.getInstance();
			VertexBuffer renderer = tessellator.getBuffer();
			
			Color color = new Color(ModFluids.crystalFluidColors()[ModFluids.crystalFluids.indexOf(tank.getFluid().getFluid())]);
			
			System.out.println(color.toString());
			
			GlStateManager.color(color.getRed(), color.getGreen(), color.getBlue(), 0.25F);

			renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			renderer.pos(1.0d, height, 1.0d).tex(maxU, maxV).endVertex();
			renderer.pos(1.0d, height, 0).tex(maxU, minV).endVertex();
			renderer.pos(0, height, 0).tex(minU, minV).endVertex();
			renderer.pos(0, height, 1.0d).tex(minU, maxV).endVertex();
			tessellator.draw();

			GlStateManager.popMatrix();
		}
	}
}
