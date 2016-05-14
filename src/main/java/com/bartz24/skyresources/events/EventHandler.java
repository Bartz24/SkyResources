package com.bartz24.skyresources.events;

import java.util.List;
import java.util.Random;

import com.bartz24.skyresources.IslandPos;
import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.References;
import com.bartz24.skyresources.SkyResourcesSaveData;
import com.bartz24.skyresources.alchemy.effects.IHealthBoostItem;
import com.bartz24.skyresources.alchemy.item.AlchemyItemComponent;
import com.bartz24.skyresources.base.item.ItemKnife;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.registry.ModItems;
import com.bartz24.skyresources.world.WorldTypeSky;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
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
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.world.WorldEvent.Save;
import net.minecraftforge.event.world.WorldEvent.Unload;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.oredict.OreDictionary;

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

			if (!References.hasPlayerSpawned(player.getName()))
			{
				World world = player.worldObj;
				if (world.getWorldInfo()
						.getTerrainType() instanceof WorldTypeSky)
				{
					if (world.getSpawnPoint().getX() != 0
							&& world.getSpawnPoint().getY() != 0)
						world.setSpawnPoint(new BlockPos(0, 86, 0));
					BlockPos spawn = world.getSpawnPoint();

					if (!References.hasPosition(0, 0))
					{
						References.CurrentIslandsList.add(new IslandPos(0, 0));

						spawnPlayer(player, spawn, true);
					} else
						spawnPlayer(player, spawn, false);

					References.spawnedPlayers.add(player.getName());

				}
			}
		}
	}

	public static void spawnPlayer(EntityPlayer player, BlockPos pos,
			boolean spawnPlat)
	{
		NBTTagCompound data = player.getEntityData();
		if (!data.hasKey(EntityPlayer.PERSISTED_NBT_TAG))
			data.setTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());
		NBTTagCompound persist = data
				.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);

		if (spawnPlat)
			createSpawn(player.worldObj, pos);

		if (player instanceof EntityPlayerMP)
		{
			EntityPlayerMP pmp = (EntityPlayerMP) player;
			pmp.setPositionAndUpdate(pos.getX() + 0.5, pos.getY() + 1.6,
					pos.getZ() + 0.5);
			pmp.setSpawnPoint(pos, true);
		}
	}

	private static void createSpawn(World world, BlockPos spawn)
	{
		if (spawn.getX() == 0 && spawn.getZ() == 0)
		{
			mainSpawn(world, spawn);
			return;
		}

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

	private static void mainSpawn(World world, BlockPos spawn)
	{
		for (int x = -1; x < 2; x++)
		{
			for (int z = -1; z < 2; z++)
			{
				BlockPos pos = new BlockPos(spawn.getX() + x, spawn.getY(),
						spawn.getZ() + z);
				world.setBlockState(pos.down(3),
						Blocks.bedrock.getDefaultState());
				world.setBlockState(pos.down(4),
						Blocks.bedrock.getDefaultState());
			}
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
			} else if (block != null && equip == null)
			{
				if (block == Blocks.cauldron)
				{

					int i = ((Integer) event.getWorld()
							.getBlockState(event.getPos())
							.getValue(BlockCauldron.LEVEL)).intValue();
					ItemStack item = event.getEntityPlayer()
							.getHeldItem(EnumHand.MAIN_HAND);
					if (i > 0 && item != null
							&& item.getItem() == ModItems.dirtyGem)
					{
						List<ItemStack> items = OreDictionary
								.getOres("gem" + RandomHelper
										.capatilizeString(References.gemList
												.get(item.getMetadata())));

						if (items.size() > 0)
						{
							item.stackSize--;
							if (item.stackSize == 0)
								event.getEntityPlayer()
										.setHeldItem(EnumHand.MAIN_HAND, null);
							RandomHelper.spawnItemInWorld(event.getWorld(),
									new ItemStack(items.get(0).getItem(), 1,
											items.get(0).getMetadata()),
									event.getPos());

							new BlockCauldron().setWaterLevel(event.getWorld(),
									event.getPos(), event.getWorld()
											.getBlockState(event.getPos()),
									i - 1);
						}
					}
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
		EntityPlayer player = event.player;

		if (!References.playerHasIsland(player.getName()))
			player.addChatMessage(new TextComponentString(
					"Type " + TextFormatting.AQUA.toString() + "/"
							+ ConfigOptions.commandName + " create"
							+ TextFormatting.WHITE.toString()
							+ " to create your starting island"));

		player.addChatMessage(new TextComponentString(
				"Need help or a guide? Go to\n" + TextFormatting.BLUE.toString()
						+ "https://github.com/Bartz24/SkyResources/wiki"));
	}

	@SubscribeEvent
	public void onSave(Save event)
	{
		SkyResourcesSaveData.setDirty(0);
	}

	@SubscribeEvent
	public void onUnload(Unload event)
	{
		SkyResourcesSaveData.setDirty(0);
	}

}
