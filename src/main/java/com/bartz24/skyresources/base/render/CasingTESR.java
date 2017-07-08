package com.bartz24.skyresources.base.render;

import org.lwjgl.opengl.GL11;

import com.bartz24.skyresources.base.tile.TileCasing;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class CasingTESR extends TileEntitySpecialRenderer<TileCasing>
{

	@Override
	public void render(TileCasing te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5f, (float) y + 0.5f, (float) z + 0.5f);
		if (!te.machineStored.isEmpty())
		{
			Minecraft.getMinecraft().getRenderItem().renderItem(te.machineStored,
					TransformType.NONE);
		}
		GL11.glPopMatrix();
	}
}
