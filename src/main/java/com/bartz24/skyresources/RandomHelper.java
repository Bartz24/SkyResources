package com.bartz24.skyresources;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RandomHelper
{
	public static String capatilizeString(String s)
	{
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}
	
	public static void spawnItemInWorld(World world, ItemStack stack, BlockPos pos)
	{
		Entity entity = new EntityItem(world, pos.getX() + 0.5F,
				pos.getY() + 1.5F, pos.getZ() + 0.5F, stack);
		world.spawnEntityInWorld(entity);
	}
}
