package com.bartz24.skyresources;

import java.util.List;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
				pos.getY() + 1.5F, pos.getZ() + 0.5F, stack);
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
	
	public static void setGLColorFromIntPlusAlpha(int color) {
        float alpha = (color >> 24 & 255) / 255.0F;
        float red = (color >> 16 & 255) / 255.0F;
        float green = (color >> 8 & 255) / 255.0F;
        float blue = (color & 255) / 255.0F;

        GlStateManager.color(red, green, blue, alpha);
    }
}
