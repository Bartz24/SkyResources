package com.bartz24.skymod.events;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import akka.event.Logging.Debug;

import com.bartz24.skymod.SkyMod;
import com.bartz24.skymod.alchemy.effects.IHealthBoostItem;
import com.bartz24.skymod.alchemy.infusion.InfusionRecipe;
import com.bartz24.skymod.alchemy.infusion.InfusionRecipes;
import com.bartz24.skymod.alchemy.item.AlchemyItemComponent;
import com.bartz24.skymod.base.item.ItemKnife;
import com.bartz24.skymod.registry.ModBlocks;
import com.bartz24.skymod.registry.ModItems;
import com.bartz24.skymod.world.WorldTypeSky;

public class EventHandler
{
	@SubscribeEvent
	public void playerUpdate(LivingUpdateEvent event)
	{
		if (event.entityLiving instanceof EntityPlayer
				&& !event.entity.worldObj.isRemote)
		{
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			NBTTagCompound data = player.getEntityData();
			if (!data.hasKey(EntityPlayer.PERSISTED_NBT_TAG))
				data.setTag(EntityPlayer.PERSISTED_NBT_TAG,
						new NBTTagCompound());

			NBTTagCompound persist = data
					.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
			if (player.ticksExisted > 3 && !persist.getBoolean("worldCreated"))
			{
				World world = player.worldObj;
				if (world.getWorldInfo().getTerrainType() instanceof WorldTypeSky)
				{
					BlockPos spawn = world.getSpawnPoint();
					if (world.getBlockState(spawn.down(4)) != Blocks.bedrock
							&& world.provider.getDimension() == 0)
						spawnPlayer(player, spawn);
				}

				persist.setBoolean("worldCreated", true);
			}
		}
	}

	public static void spawnPlayer(EntityPlayer player, BlockPos pos)
	{
		NBTTagCompound data = player.getEntityData();
		if (!data.hasKey(EntityPlayer.PERSISTED_NBT_TAG))
			data.setTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());
		NBTTagCompound persist = data
				.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);

		if (!persist.getBoolean("worldCreated"))
		{
			createSpawn(player.worldObj, pos);

			if (player instanceof EntityPlayerMP)
			{
				EntityPlayerMP pmp = (EntityPlayerMP) player;
				pmp.setPositionAndUpdate(pos.getX() + 0.5, pos.getY() + 1.6,
						pos.getZ() + 0.5);
				pmp.setSpawnChunk(pos, true,
						player.worldObj.provider.getDimension());
			}
		} else
		{
			double posX = persist.getDouble("spawnX");
			double posY = persist.getDouble("spawnY");
			double posZ = persist.getDouble("spawnZ");

			if (player instanceof EntityPlayerMP)
			{
				EntityPlayerMP pmp = (EntityPlayerMP) player;
				pmp.setPositionAndUpdate(posX, posY, posZ);
			}
		}
	}

	private static void createSpawn(World world, BlockPos spawn)
	{
		Random random = world.rand;
		switch (random.nextInt(3))
		{
		case 0:
			sandSpawn(world, spawn);
			break;
		case 1:
			dirtSpawn(world, spawn);
			break;
		case 2:
			snowSpawn(world, spawn);
			break;
		}
	}

	private static void sandSpawn(World world, BlockPos spawn)
	{
		for (int x = -1; x < 2; x++)
		{
			for (int z = -1; z < 2; z++)
			{
				BlockPos pos = new BlockPos(spawn.getX() + x, spawn.getY(),
						spawn.getZ() + z);
				world.setBlockState(pos.down(3),
						Blocks.sand.getStateFromMeta(1));
				world.setBlockState(pos.down(4),
						Blocks.bedrock.getDefaultState());
			}
		}
		BlockPos pos = new BlockPos(spawn.getX() + -1, spawn.getY(),
				spawn.getZ() + 1);
		world.setBlockState(pos.down(2), Blocks.cactus.getDefaultState());
		world.setBlockState(pos.down(1), Blocks.cactus.getDefaultState());
		world.setBlockState(pos, Blocks.cactus.getDefaultState());
	}

	private static void dirtSpawn(World world, BlockPos spawn)
	{
		for (int x = -1; x < 2; x++)
		{
			for (int z = -1; z < 2; z++)
			{
				BlockPos pos = new BlockPos(spawn.getX() + x, spawn.getY(),
						spawn.getZ() + z);
				world.setBlockState(pos.down(3),
						Blocks.dirt.getStateFromMeta(1));
				world.setBlockState(pos.down(4),
						Blocks.bedrock.getDefaultState());
			}
		}
		BlockPos pos = new BlockPos(spawn.getX() + -1, spawn.getY(),
				spawn.getZ() + 1);
		world.setBlockState(pos.down(2), Blocks.yellow_flower.getDefaultState());
	}

	private static void snowSpawn(World world, BlockPos spawn)
	{
		for (int x = -1; x < 2; x++)
		{
			for (int z = -1; z < 2; z++)
			{
				BlockPos pos = new BlockPos(spawn.getX() + x, spawn.getY(),
						spawn.getZ() + z);
				world.setBlockState(pos.down(3), Blocks.snow.getDefaultState());
				world.setBlockState(pos.down(4),
						Blocks.bedrock.getDefaultState());

				if (x == -1 && z == 1)
					world.setBlockState(pos.down(2),
							Blocks.pumpkin.getDefaultState());
				else
					world.setBlockState(pos.down(2),
							Blocks.snow_layer.getDefaultState());
			}
		}
	}

	
	//TODO Not currently implemented
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		ItemStack equip = event.entityPlayer.getHeldItem(EnumHand.MAIN_HAND);
		if (event.pos == null)
			return;
		Block block = event.world.getBlockState(event.pos).getBlock();
		if (event.action == Action.RIGHT_CLICK_BLOCK && equip == null
				&& event.entityPlayer.isSneaking())
		{
			if (block == Blocks.cactus)
			{
				if (event.world.isRemote)
					event.entityPlayer.swingArm(EnumHand.MAIN_HAND);
				else
				{
					event.entityPlayer
							.dropPlayerItemWithRandomChoice(
									new ItemStack(
											ModItems.alchemyComponent,
											1,
											((AlchemyItemComponent) ModItems.alchemyComponent)
													.getNames().indexOf(
															"cactusNeedle")),
									false);
					event.entityPlayer.attackEntityFrom(DamageSource.cactus, 2);
				}
			}
		}
	}

	@SubscribeEvent
	public void onItemCraft(ItemCraftedEvent event)
	{
		for (int i = 0; i < event.craftMatrix.getSizeInventory(); i++)
		{
			if (event.craftMatrix.getStackInSlot(i) != null)
			{
				ItemStack j = event.craftMatrix.getStackInSlot(i);
				if (j.getItem() != null && j.getItem() instanceof ItemKnife)
				{
					ItemStack k = new ItemStack(j.getItem(), 2,
							(j.getItemDamage() + 1));
					if (k.getItemDamage() >= k.getMaxDamage())
					{
						k.stackSize--;
					}
					event.craftMatrix.setInventorySlotContents(i, k);
				}
			}
		}
	}

	@SubscribeEvent
	public void onPlayerTickEvent(PlayerTickEvent event)
	{
		if (!event.player.worldObj.isRemote)
		{
			EntityPlayer player = event.player;

			int healthToAdd = 0;

			for (int i = 0; i <= player.inventory.mainInventory.length; i++)
			{
				ItemStack stack = player.inventory.getStackInSlot(i);

				if (stack == null)
					continue;

				if (stack.getItem() instanceof IHealthBoostItem)
				{
					healthToAdd += ((IHealthBoostItem) stack.getItem())
							.getHealthBoost(stack);
				}

				if (stack.isItemEqual(new ItemStack(ModItems.healthRing, 1)))
				{
					if (stack.getTagCompound().getInteger("cooldown") > 0)
					{
						stack.getTagCompound()
								.setInteger(
										"cooldown",
										stack.getTagCompound().getInteger(
												"cooldown") - 1);
					}
				}
			}

			player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH)
					.setBaseValue(20 + healthToAdd);
			if (player.getHealth() > player.getMaxHealth())
				player.setHealth(player.getMaxHealth());
		}

	}
}
