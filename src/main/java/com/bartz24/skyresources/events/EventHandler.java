package com.bartz24.skyresources.events;

import java.util.Random;

import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.alchemy.effects.IHealthBoostItem;
import com.bartz24.skyresources.alchemy.item.AlchemyItemComponent;
import com.bartz24.skyresources.base.item.ItemKnife;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.registry.ModItems;
import com.bartz24.skyresources.world.WorldTypeSky;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class EventHandler
{
	@SubscribeEvent
	public void playerUpdate(LivingUpdateEvent event)
	{
		if (event.getEntityLiving() instanceof EntityPlayer
				&& !event.getEntity().worldObj.isRemote)
		{
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			NBTTagCompound data = player.getEntityData();
			if (!data.hasKey(EntityPlayer.PERSISTED_NBT_TAG))
				data.setTag(EntityPlayer.PERSISTED_NBT_TAG,
						new NBTTagCompound());

			NBTTagCompound persist = data
					.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
			if (player.ticksExisted > 3 && !persist.getBoolean("worldCreated"))
			{
				World world = player.worldObj;
				if (world.getWorldInfo()
						.getTerrainType() instanceof WorldTypeSky)
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
		int type = ConfigOptions.worldSpawnType == 0 ? random.nextInt(2)
				: ConfigOptions.worldSpawnType - 1;
		switch (type)
		{
		case 0:
			sandSpawn(world, spawn);
			break;
		case 1:
			snowSpawn(world, spawn);
			break;
		/*
		 * case 2: dirtSpawn(world, spawn); break;
		 */
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
		world.setBlockState(pos.down(2),
				Blocks.yellow_flower.getDefaultState());
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

				if ((x == -1 && z == 1) || (x == 1 && z == 1))
					world.setBlockState(pos.down(2),
							Blocks.pumpkin.getDefaultState());
				else
					world.setBlockState(pos.down(2),
							Blocks.snow_layer.getDefaultState());
			}
		}
	}

	@SubscribeEvent
	public void onPlayerRightClick(RightClickBlock event)
	{
		if (!event.getWorld().isRemote)
		{
			ItemStack equip = event.getEntityPlayer()
					.getHeldItem(event.getHand());
			if (event.getPos() == null)
				return;
			Block block = event.getWorld().getBlockState(event.getPos())
					.getBlock();
			if (block != null && equip == null
					&& event.getEntityPlayer().isSneaking())
			{
				if (block == Blocks.cactus)
				{
					RandomHelper.spawnItemInWorld(event.getWorld(),
							new ItemStack(ModItems.alchemyComponent, 1,
									((AlchemyItemComponent) ModItems.alchemyComponent)
											.getNames()
											.indexOf("cactusNeedle")),
							event.getEntityPlayer().getPosition());
					event.getEntityPlayer()
							.attackEntityFrom(DamageSource.cactus, 4);
				} else if (block == Blocks.snow_layer)
				{
					RandomHelper.spawnItemInWorld(event.getWorld(),
							new ItemStack(Items.snowball), event.getPos());
					event.getEntityPlayer().addPotionEffect(
							new PotionEffect(MobEffects.digSlowdown,
									event.getWorld().rand.nextInt(750) + 750,
									event.getWorld().rand.nextInt(4)));

					int meta = Blocks.snow_layer.getMetaFromState(
							event.getWorld().getBlockState(event.getPos()));

					if (meta <= 1)
						event.getWorld().setBlockToAir(event.getPos());
					else
						event.getWorld().setBlockState(event.getPos(),
								Blocks.snow_layer.getStateFromMeta(meta - 2));
				} else if (block == Blocks.snow)
				{
					RandomHelper.spawnItemInWorld(event.getWorld(),
							new ItemStack(Items.snowball), event.getPos());
					event.getEntityPlayer().addPotionEffect(
							new PotionEffect(MobEffects.digSlowdown,
									event.getWorld().rand.nextInt(750) + 750,
									event.getWorld().rand.nextInt(4)));

					event.getWorld().setBlockState(event.getPos(),
							Blocks.snow_layer.getStateFromMeta(5));
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
						stack.getTagCompound().setInteger("cooldown",
								stack.getTagCompound().getInteger("cooldown")
										- 1);
					}
				}
			}

			player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH)
					.setBaseValue(20 + healthToAdd);
			if (player.getHealth() > player.getMaxHealth())
				player.setHealth(player.getMaxHealth());
		}

	}

	@SubscribeEvent
	public void onPlayerJoinEvent(PlayerLoggedInEvent event)
	{
		event.player.addChatMessage(new TextComponentString(
				"Need help or a guide? Go to §9https://github.com/Bartz24/SkyResources/wiki"));
	}

}
