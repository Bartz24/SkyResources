package com.bartz24.skyresources.events;

import com.bartz24.skyresources.registry.ModBlocks;
import com.bartz24.skyresources.registry.ModFluids;
import com.bartz24.skyresources.registry.ModItems;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModBucketHandler
{
	public static void registerBuckets()
	{
		for (int i = 0; i < ModFluids.crystalFluidNames().length; i++)
		{
			FluidContainerRegistry.registerFluidContainer(
					ModFluids.crystalFluids.get(i),
					new ItemStack(ModItems.crystalFluidBuckets.get(i)),
					new ItemStack(Items.bucket));
			FluidContainerRegistry
					.registerFluidContainer(ModFluids.dirtyCrystalFluids.get(i),
							new ItemStack(
									ModItems.dirtyCrystalFluidBuckets.get(i)),
							new ItemStack(Items.bucket));
		}
	}

	@SubscribeEvent
	public void onBucketFill(FillBucketEvent event)
	{
		if (event.getTarget() == null || event.getTarget().getBlockPos() == null
				|| event.getWorld()
						.getBlockState(event.getTarget().getBlockPos()) == null)
			return;
		Block block = event.getWorld()
				.getBlockState(event.getTarget().getBlockPos()).getBlock();
		if (block == null || event.getEntityPlayer()
				.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND) == null)
			return;

		if (event.getEntityPlayer()
				.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND)
				.getItem() == Items.bucket
				&& event.getTarget().typeOfHit == Type.BLOCK)
		{

			for (int i = 0; i < ModFluids.crystalFluidNames().length; i++)
			{
				if (block == ModBlocks.crystalFluidBlocks.get(i))
				{
					event.setResult(Result.ALLOW);
					event.setFilledBucket(
							new ItemStack(ModItems.crystalFluidBuckets.get(i)));
					event.getWorld()
							.setBlockToAir(event.getTarget().getBlockPos());
					return;
				} else if (block == ModBlocks.dirtyCrystalFluidBlocks.get(i))
				{
					event.setResult(Result.ALLOW);
					event.setFilledBucket(new ItemStack(
							ModItems.dirtyCrystalFluidBuckets.get(i)));
					event.getWorld()
							.setBlockToAir(event.getTarget().getBlockPos());
					return;
				}
			}
		}
	}
}