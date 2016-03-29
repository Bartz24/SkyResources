package com.bartz24.skymod.events;

import scala.actors.threadpool.Arrays;

import com.bartz24.skymod.registry.ModBlocks;
import com.bartz24.skymod.registry.ModFluids;
import com.bartz24.skymod.registry.ModItems;

import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import akka.actor.FSM.Event;

public class ModBucketHandler
{
	public static void registerBuckets()
	{
		for (int i = 0; i < ModFluids.crystalFluidNames().length; i++)
		{
			FluidContainerRegistry
					.registerFluidContainer(ModFluids.crystalFluids.get(i), new ItemStack(
							ModItems.crystalFluidBuckets.get(i)), new ItemStack(Items.bucket));
		}
	}

	@SubscribeEvent
	public void onBucketFill(FillBucketEvent event)
	{
		Block block = event.getWorld().getBlockState(event.getTarget().getBlockPos()).getBlock();

		if (event.entityPlayer.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).getItem() == Items.bucket
				&& event.getTarget().typeOfHit == Type.BLOCK)
		{

			for (int i = 0; i < ModFluids.crystalFluidNames().length; i++)
			{
				if (block == ModBlocks.crystalFluidBlocks.get(i))
				{
					event.setResult(Result.ALLOW);
					event.setFilledBucket(new ItemStack(ModItems.crystalFluidBuckets.get(i)));
					event.getWorld().setBlockToAir(event.getTarget().getBlockPos());
					return;
				}
			}
		}
	}
}