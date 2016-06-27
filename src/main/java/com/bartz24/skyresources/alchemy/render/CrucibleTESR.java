package com.bartz24.skyresources.alchemy.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.alchemy.tile.CrucibleTile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class CrucibleTESR extends TileEntitySpecialRenderer<CrucibleTile>
{

	@Override
	public void renderTileEntityAt(CrucibleTile te, double x, double y,
			double z, float partialTicks, int destroyStage)
	{
		GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5f, (float) y + 1.50f, (float) z + 0.5f);
        GL11.glRotatef(180f, 1, 0, 0);
        GL11.glPopMatrix();
        if (te.getTank().getFluid() != null && te.getTank().getFluidAmount() > 0) {
            GL11.glPushMatrix();

            GL11.glTranslatef((float) x, (float) y + 1f, (float) z + 1);
            GL11.glRotatef(180f, 1f, 0f, 0f);
            renderFluidContents(te, x, y, z);
            GL11.glPopMatrix();

        }
	}

	private void renderFluidContents(CrucibleTile crucible, double x, double y,
			double z)
	{
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1, 1, 1, 1);
		FluidStack fluidStack = crucible.getTank().getFluid();
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

		double liquid = crucible.getTank().getFluidAmount();
		double maxLiquid = crucible.getTank().getCapacity();
		double height = (liquid / maxLiquid) * 0.7;
		GL11.glRotated(180f, 1, 0, 0);
		RandomHelper.renderFluidCuboid(fluidStack, crucible.getPos(), 0, -0.85, -1, 0.1, 0,
				0.1, 1-0.1, 0.1+height, 1-0.1);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_BLEND);
	}
}
