package com.bartz24.skyresources.alchemy.render;

import org.lwjgl.opengl.GL11;

import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.alchemy.tile.PurificationVesselTile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class PurificationVesselTESR
		extends TileEntitySpecialRenderer<PurificationVesselTile>
{

	@Override
	public void renderTileEntityAt(PurificationVesselTile te, double x,
			double y, double z, float partialTicks, int destroyStage)
	{
		GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5f, (float) y + 1.50f, (float) z + 0.5f);
        GL11.glRotatef(180f, 1, 0, 0);
        GL11.glPopMatrix();
        if (te.getLowerTank().getFluid() != null && te.getLowerTank().getFluidAmount() > 0) {
            GL11.glPushMatrix();

            GL11.glTranslatef((float) x, (float) y + 1f, (float) z + 1);
            GL11.glRotatef(180f, 1f, 0f, 0f);
            renderLowerFluidContents(te, x, y, z);
            GL11.glPopMatrix();

        }
        if (te.getUpperTank().getFluid() != null && te.getUpperTank().getFluidAmount() > 0) {
            GL11.glPushMatrix();

            GL11.glTranslatef((float) x, (float) y + 1f, (float) z + 1);
            GL11.glRotatef(180f, 1f, 0f, 0f);
            renderUpperFluidContents(te, x, y, z);
            GL11.glPopMatrix();

        }
	}

	private void renderLowerFluidContents(PurificationVesselTile tile, double x, double y,
			double z)
	{
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1, 1, 1, 1);
		FluidStack fluidStack = tile.getLowerTank().getFluid();
		final Fluid fluid = fluidStack.getFluid();

		ResourceLocation textureRL = fluid.getStill();
		TextureAtlasSprite texture = Minecraft.getMinecraft().getRenderItem()
				.getItemModelMesher().getModelManager().getTextureMap()
				.getAtlasSprite(textureRL.getResourceDomain() + ":"
						+ textureRL.getResourcePath());

		final int color;

		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		if (texture != null)
		{
			color = fluid.getColor(fluidStack);
		} else
		{
			color = 0xFFFFFFFF;
		}

		double liquid = tile.getLowerTank().getFluidAmount();
		double maxLiquid = tile.getLowerTank().getCapacity();
		double height = (liquid / maxLiquid) * 9d/16d;
		double offset = 3d/16d;
		GL11.glRotated(180f, 1, 0, 0);
		RandomHelper.renderFluidCuboid(fluidStack, tile.getPos(), 0, -1, -1, offset, 1d/16d,
				offset, 1-offset, 1d/16d+height, 1-offset);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_BLEND);		
	}

	private void renderUpperFluidContents(PurificationVesselTile tile, double x, double y,
			double z)
	{
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1, 1, 1, 1);
		FluidStack fluidStack = tile.getUpperTank().getFluid();
		final Fluid fluid = fluidStack.getFluid();

		ResourceLocation textureRL = fluid.getStill();
		TextureAtlasSprite texture = Minecraft.getMinecraft().getRenderItem()
				.getItemModelMesher().getModelManager().getTextureMap()
				.getAtlasSprite(textureRL.getResourceDomain() + ":"
						+ textureRL.getResourcePath());

		final int color;

		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		if (texture != null)
		{
			color = fluid.getColor(fluidStack);
		} else
		{
			color = 0xFFFFFFFF;
		}


		double liquid = tile.getUpperTank().getFluidAmount();
		double maxLiquid = tile.getUpperTank().getCapacity();
		double height = (liquid / maxLiquid) * 4d/16d;
		double offset = 3d/16d;
		GL11.glRotated(180f, 1, 0, 0);
		RandomHelper.renderFluidCuboid(fluidStack, tile.getPos(), 0, -1, -1, offset, 11d/16d,
				offset, 1-offset, 11d/16d+height, 1-offset);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_BLEND);			
	}
}
