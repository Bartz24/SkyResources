package com.bartz24.skyresources;

import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

public class RandomHelper
{
	public static String capatilizeString(String s)
	{
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	public static void spawnItemInWorld(World world, ItemStack stack,
			BlockPos pos)
	{
		Entity entity = new EntityItem(world, pos.getX() + 0.5F,
				pos.getY() + 0.5F, pos.getZ() + 0.5F, stack);
		world.spawnEntityInWorld(entity);
	}

	public static void dispatchTEToNearbyPlayers(TileEntity tile)
	{
		World world = tile.getWorld();
		List players = world.playerEntities;
		for (Object player : players)
			if (player instanceof EntityPlayerMP)
			{
				EntityPlayerMP mp = (EntityPlayerMP) player;
				if (Math.hypot(mp.posX - tile.getPos().getX() - 0.5,
						mp.posZ - tile.getPos().getZ() - 0.5) < 64)
					((EntityPlayerMP) player).connection
							.sendPacket(tile.getUpdatePacket());
			}
	}

	public static void dispatchTEToNearbyPlayers(World world, BlockPos pos)
	{
		TileEntity tile = world.getTileEntity(pos);
		if (tile != null)
			dispatchTEToNearbyPlayers(tile);
	}

	public static float pointDistancePlane(double x1, double y1, double x2,
			double y2)
	{
		return (float) Math.hypot(x1 - x2, y1 - y2);
	}

	public static void renderFluidCuboid(FluidStack fluid, BlockPos pos,
			double x, double y, double z, double x1, double y1, double z1,
			double x2, double y2, double z2)
	{
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer renderer = tessellator.getBuffer();
		renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
		Minecraft.getMinecraft().renderEngine
				.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		int color = fluid.getFluid().getColor(fluid);
		// RenderUtil.setColorRGBA(color);
		int brightness = Minecraft.getMinecraft().theWorld.getCombinedLight(pos,
				fluid.getFluid().getLuminosity());

		pre(x, y, z);

		TextureAtlasSprite still = Minecraft.getMinecraft()
				.getTextureMapBlocks()
				.getTextureExtry(fluid.getFluid().getStill(fluid).toString());
		TextureAtlasSprite flowing = Minecraft.getMinecraft()
				.getTextureMapBlocks()
				.getTextureExtry(fluid.getFluid().getFlowing(fluid).toString());

		// x/y/z2 - x/y/z1 is because we need the width/height/depth
		putTexturedQuad(renderer, still, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1,
				EnumFacing.DOWN, color, brightness, false);
		putTexturedQuad(renderer, flowing, x1, y1, z1, x2 - x1, y2 - y1,
				z2 - z1, EnumFacing.NORTH, color, brightness, true);
		putTexturedQuad(renderer, flowing, x1, y1, z1, x2 - x1, y2 - y1,
				z2 - z1, EnumFacing.EAST, color, brightness, true);
		putTexturedQuad(renderer, flowing, x1, y1, z1, x2 - x1, y2 - y1,
				z2 - z1, EnumFacing.SOUTH, color, brightness, true);
		putTexturedQuad(renderer, flowing, x1, y1, z1, x2 - x1, y2 - y1,
				z2 - z1, EnumFacing.WEST, color, brightness, true);
		putTexturedQuad(renderer, still, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1,
				EnumFacing.UP, color, brightness, false);

		tessellator.draw();

		post();
	}

	public static void putTexturedQuad(VertexBuffer renderer,
			TextureAtlasSprite sprite, double x, double y, double z, double w,
			double h, double d, EnumFacing face, int color, int brightness,
			boolean flowing)
	{
		int l1 = brightness >> 0x10 & 0xFFFF;
		int l2 = brightness & 0xFFFF;

		int a = color >> 24 & 0xFF;
		int r = color >> 16 & 0xFF;
		int g = color >> 8 & 0xFF;
		int b = color & 0xFF;

		putTexturedQuad(renderer, sprite, x, y, z, w, h, d, face, r, g, b, a,
				l1, l2, flowing);
	}

	// x and x+w has to be within [0,1], same for y/h and z/d
	public static void putTexturedQuad(VertexBuffer renderer,
			TextureAtlasSprite sprite, double x, double y, double z, double w,
			double h, double d, EnumFacing face, int r, int g, int b, int a,
			int light1, int light2, boolean flowing)
	{
		double minU;
		double maxU;
		double minV;
		double maxV;

		double size = 16f;
		if (flowing)
			size = 8f;

		double x1 = x;
		double x2 = x + w;
		double y1 = y;
		double y2 = y + h;
		double z1 = z;
		double z2 = z + d;

		double xt1 = x1 % 1d;
		double xt2 = xt1 + w;
		while (xt2 > 1f)
			xt2 -= 1f;
		double yt1 = y1 % 1d;
		double yt2 = yt1 + h;
		while (yt2 > 1f)
			yt2 -= 1f;
		double zt1 = z1 % 1d;
		double zt2 = zt1 + d;
		while (zt2 > 1f)
			zt2 -= 1f;

		// flowing stuff should start from the bottom, not from the start
		if (flowing)
		{
			double tmp = 1d - yt1;
			yt1 = 1d - yt2;
			yt2 = tmp;
		}

		switch (face)
		{
		case DOWN:
		case UP:
			minU = sprite.getInterpolatedU(xt1 * size);
			maxU = sprite.getInterpolatedU(xt2 * size);
			minV = sprite.getInterpolatedV(zt1 * size);
			maxV = sprite.getInterpolatedV(zt2 * size);
			break;
		case NORTH:
		case SOUTH:
			minU = sprite.getInterpolatedU(xt2 * size);
			maxU = sprite.getInterpolatedU(xt1 * size);
			minV = sprite.getInterpolatedV(yt1 * size);
			maxV = sprite.getInterpolatedV(yt2 * size);
			break;
		case WEST:
		case EAST:
			minU = sprite.getInterpolatedU(zt2 * size);
			maxU = sprite.getInterpolatedU(zt1 * size);
			minV = sprite.getInterpolatedV(yt1 * size);
			maxV = sprite.getInterpolatedV(yt2 * size);
			break;
		default:
			minU = sprite.getMinU();
			maxU = sprite.getMaxU();
			minV = sprite.getMinV();
			maxV = sprite.getMaxV();
		}

		switch (face)
		{
		case DOWN:
			renderer.pos(x1, y1, z1).color(r, g, b, a).tex(minU, minV)
					.lightmap(light1, light2).endVertex();
			renderer.pos(x2, y1, z1).color(r, g, b, a).tex(maxU, minV)
					.lightmap(light1, light2).endVertex();
			renderer.pos(x2, y1, z2).color(r, g, b, a).tex(maxU, maxV)
					.lightmap(light1, light2).endVertex();
			renderer.pos(x1, y1, z2).color(r, g, b, a).tex(minU, maxV)
					.lightmap(light1, light2).endVertex();
			break;
		case UP:
			renderer.pos(x1, y2, z1).color(r, g, b, a).tex(minU, minV)
					.lightmap(light1, light2).endVertex();
			renderer.pos(x1, y2, z2).color(r, g, b, a).tex(minU, maxV)
					.lightmap(light1, light2).endVertex();
			renderer.pos(x2, y2, z2).color(r, g, b, a).tex(maxU, maxV)
					.lightmap(light1, light2).endVertex();
			renderer.pos(x2, y2, z1).color(r, g, b, a).tex(maxU, minV)
					.lightmap(light1, light2).endVertex();
			break;
		case NORTH:
			renderer.pos(x1, y1, z1).color(r, g, b, a).tex(minU, maxV)
					.lightmap(light1, light2).endVertex();
			renderer.pos(x1, y2, z1).color(r, g, b, a).tex(minU, minV)
					.lightmap(light1, light2).endVertex();
			renderer.pos(x2, y2, z1).color(r, g, b, a).tex(maxU, minV)
					.lightmap(light1, light2).endVertex();
			renderer.pos(x2, y1, z1).color(r, g, b, a).tex(maxU, maxV)
					.lightmap(light1, light2).endVertex();
			break;
		case SOUTH:
			renderer.pos(x1, y1, z2).color(r, g, b, a).tex(maxU, maxV)
					.lightmap(light1, light2).endVertex();
			renderer.pos(x2, y1, z2).color(r, g, b, a).tex(minU, maxV)
					.lightmap(light1, light2).endVertex();
			renderer.pos(x2, y2, z2).color(r, g, b, a).tex(minU, minV)
					.lightmap(light1, light2).endVertex();
			renderer.pos(x1, y2, z2).color(r, g, b, a).tex(maxU, minV)
					.lightmap(light1, light2).endVertex();
			break;
		case WEST:
			renderer.pos(x1, y1, z1).color(r, g, b, a).tex(maxU, maxV)
					.lightmap(light1, light2).endVertex();
			renderer.pos(x1, y1, z2).color(r, g, b, a).tex(minU, maxV)
					.lightmap(light1, light2).endVertex();
			renderer.pos(x1, y2, z2).color(r, g, b, a).tex(minU, minV)
					.lightmap(light1, light2).endVertex();
			renderer.pos(x1, y2, z1).color(r, g, b, a).tex(maxU, minV)
					.lightmap(light1, light2).endVertex();
			break;
		case EAST:
			renderer.pos(x2, y1, z1).color(r, g, b, a).tex(minU, maxV)
					.lightmap(light1, light2).endVertex();
			renderer.pos(x2, y2, z1).color(r, g, b, a).tex(minU, minV)
					.lightmap(light1, light2).endVertex();
			renderer.pos(x2, y2, z2).color(r, g, b, a).tex(maxU, minV)
					.lightmap(light1, light2).endVertex();
			renderer.pos(x2, y1, z2).color(r, g, b, a).tex(maxU, maxV)
					.lightmap(light1, light2).endVertex();
			break;
		}
	}

	public static void pre(double x, double y, double z)
	{
		GlStateManager.pushMatrix();

		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA,
				GL11.GL_ONE_MINUS_SRC_ALPHA);

		if (Minecraft.isAmbientOcclusionEnabled())
		{
			GL11.glShadeModel(GL11.GL_SMOOTH);
		} else
		{
			GL11.glShadeModel(GL11.GL_FLAT);
		}

		GlStateManager.translate(x, y, z);
	}

	public static void post()
	{
		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
		GlStateManager.popMatrix();
	}

	public static boolean canStacksMerge(ItemStack stack1, ItemStack stack2)
	{
		if (stack1 == null || stack2 == null)
		{
			return false;
		}
		if (!stack1.isItemEqual(stack2))
		{
			return false;
		}
		if (!ItemStack.areItemStackTagsEqual(stack1, stack2))
		{
			return false;
		}
		return true;

	}

	public static int mergeStacks(ItemStack mergeSource, ItemStack mergeTarget,
			boolean doMerge)
	{
		if (!canStacksMerge(mergeSource, mergeTarget))
		{
			return 0;
		}
		int mergeCount = Math.min(
				mergeTarget.getMaxStackSize() - mergeTarget.stackSize,
				mergeSource.stackSize);
		if (mergeCount < 1)
		{
			return 0;
		}
		if (doMerge)
		{
			mergeTarget.stackSize += mergeCount;
		}
		return mergeCount;
	}

	public static ItemStack fillInventory(IInventory inv, ItemStack stack)
	{
		if (inv != null)
		{
			for (int i = 0; i < inv.getSizeInventory(); i++)
			{
				if (stack == null || stack.stackSize <= 0)
					return null;
				ItemStack inside = inv.getStackInSlot(i);
				if (inside == null || inside.stackSize <= 0)
				{
					inv.setInventorySlotContents(i, stack);
					return null;
				} else if (RandomHelper.canStacksMerge(inside, stack))
				{
					stack.stackSize -= RandomHelper.mergeStacks(stack, inside,
							true);
				}
			}
		}
		return stack;

	}
	
	public static void renderGuiTank(FluidStack fluid, int capacity, int amount, double x, double y, double width, double height) {
	    if (fluid == null || fluid.getFluid() == null || fluid.amount <= 0) {
	      return;
	    }


	    TextureAtlasSprite icon = Minecraft.getMinecraft()
				.getTextureMapBlocks()
				.getTextureExtry(fluid.getFluid().getStill(fluid).toString());;
	    if (icon == null) {
	      return;
	    }

	    int renderAmount = (int) Math.max(Math.min(height, amount * height / capacity), 1);
	    int posY = (int) (y + height - renderAmount);

	    Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
	    int color = fluid.getFluid().getColor(fluid);
	    GL11.glColor3ub((byte) (color >> 16 & 0xFF), (byte) (color >> 8 & 0xFF), (byte) (color & 0xFF));
	    
	    GlStateManager.enableBlend();    
	    for (int i = 0; i < width; i += 16) {
	      for (int j = 0; j < renderAmount; j += 16) {
	        int drawWidth = (int) Math.min(width - i, 16);
	        int drawHeight = Math.min(renderAmount - j, 16);

	        int drawX = (int) (x + i);
	        int drawY = posY + j;

	        double minU = icon.getMinU();
	        double maxU = icon.getMaxU();
	        double minV = icon.getMinV();
	        double maxV = icon.getMaxV();

	        Tessellator tessellator = Tessellator.getInstance();
	        VertexBuffer tes = tessellator.getBuffer();
	        tes.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
	        tes.pos(drawX, drawY + drawHeight, 0).tex(minU, minV + (maxV - minV) * drawHeight / 16F).endVertex();
	        tes.pos(drawX + drawWidth, drawY + drawHeight, 0).tex(minU + (maxU - minU) * drawWidth / 16F, minV + (maxV - minV) * drawHeight / 16F).endVertex();
	        tes.pos(drawX + drawWidth, drawY, 0).tex(minU + (maxU - minU) * drawWidth / 16F, minV).endVertex();
	        tes.pos(drawX, drawY, 0).tex(minU, minV).endVertex();
	        tessellator.draw();
	      }
	    }
	    GlStateManager.disableBlend();
	  }
}
