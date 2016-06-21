package com.bartz24.skyresources.events;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModBucketHandler
{
	public Map<Fluid, Item> buckets = new HashMap<Fluid, Item>();

	public static void registerBuckets()
	{
	}

	@SubscribeEvent
	public void onBucketFill(FillBucketEvent event)
	{
		ItemStack result = fillCustomBucket(event.getWorld(),
				event.getTarget());

		if (result == null)
			return;

		event.setFilledBucket(result);
		event.setResult(Event.Result.ALLOW);
	}

	private ItemStack fillCustomBucket(World world, RayTraceResult pos)
	{
		if (pos != null && pos.getBlockPos() != null
				&& world.getBlockState(pos.getBlockPos()) != null)
		{
			Block block = world.getBlockState(pos.getBlockPos()).getBlock();
			if (block instanceof BlockFluidBase)
			{
				Fluid fluid = ((BlockFluidBase) block).getFluid();
				if (buckets.containsKey(fluid))
				{
					Item bucket = buckets.get(fluid);
					if (bucket != null)
					{
						world.setBlockToAir(pos.getBlockPos());
						return new ItemStack(bucket, 1);
					}
				}
			}
		}
		return null;
	}
}