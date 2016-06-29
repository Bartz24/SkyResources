package com.bartz24.skyresources.events;

import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import com.bartz24.skyresources.IslandPos;
import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.References;
import com.bartz24.skyresources.SkyResources;
import com.bartz24.skyresources.SkyResourcesSaveData;
import com.bartz24.skyresources.alchemy.effects.IHealthBoostItem;
import com.bartz24.skyresources.alchemy.item.AlchemyItemComponent;
import com.bartz24.skyresources.base.ModKeyBindings;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.registry.ModGuiHandler;
import com.bartz24.skyresources.registry.ModItems;
import com.bartz24.skyresources.world.WorldTypeSky;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
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
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.ClickEvent.Action;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.world.WorldEvent.Save;
import net.minecraftforge.event.world.WorldEvent.Unload;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
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

						if (ConfigOptions.oneChunk)
						{
							WorldBorder border = event.getEntityLiving()
									.getEntityWorld()
									.getMinecraftServer().worldServers[0]
											.getWorldBorder();

							border.setCenter(0, 0);
							border.setTransition(16);
							border.setWarningDistance(1);

							References.worldOneChunk = true;
						}

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
		if (spawnPlat)
			createSpawn(player.worldObj, pos);

		if (player instanceof EntityPlayerMP)
		{
			EntityPlayerMP pmp = (EntityPlayerMP) player;
			pmp.setPositionAndUpdate(pos.getX() + 0.5, pos.getY() + 1.6,
					pos.getZ() + 0.5);
			pmp.setSpawnPoint(pos, true);

			References.setStartingInv(pmp);
		}
	}

	public static void spawnPlayer(EntityPlayer player, BlockPos pos,
			int forceType)
	{
		spawnPlayer(player, pos, false);

		spawnPlat(player.worldObj, pos, forceType);
	}

	public static void createSpawn(World world, BlockPos spawn)
	{
		if (spawn.getX() == 0 && spawn.getZ() == 0 && !References.worldOneChunk)
		{
			mainSpawn(world, spawn);
			return;
		}

		Random random = world.rand;
		int type = ConfigOptions.worldSpawnType == 0 ? random.nextInt(2)
				: ConfigOptions.worldSpawnType - 1;

		spawnPlat(world, spawn, type);
	}

	private static void spawnPlat(World world, BlockPos spawn, int type)
	{
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
		for (int x = -(int) Math
				.floor((float) ConfigOptions.islandSize / 2F); x <= (int) Math
						.floor((float) ConfigOptions.islandSize / 2F); x++)
		{
			for (int z = -(int) Math.floor(
					(float) ConfigOptions.islandSize / 2F); z <= (int) Math
							.floor((float) ConfigOptions.islandSize / 2F); z++)
			{
				BlockPos pos = new BlockPos(spawn.getX() + x, spawn.getY(),
						spawn.getZ() + z);
				world.setBlockState(pos.down(3),
						Blocks.BEDROCK.getDefaultState(), 2);
				world.setBlockState(pos.down(4),
						Blocks.BEDROCK.getDefaultState(), 2);
			}
		}
	}

	private static void sandSpawn(World world, BlockPos spawn)
	{
		for (int x = -(int) Math
				.floor((float) ConfigOptions.islandSize / 2F); x <= (int) Math
						.floor((float) ConfigOptions.islandSize / 2F); x++)
		{
			for (int z = -(int) Math.floor(
					(float) ConfigOptions.islandSize / 2F); z <= (int) Math
							.floor((float) ConfigOptions.islandSize / 2F); z++)
			{
				BlockPos pos = new BlockPos(spawn.getX() + x, spawn.getY(),
						spawn.getZ() + z);
				world.setBlockState(pos.down(3),
						Blocks.SAND.getStateFromMeta(1), 2);
				world.setBlockState(pos.down(4),
						Blocks.BEDROCK.getDefaultState(), 2);
			}
		}
		BlockPos pos = new BlockPos(spawn.getX() - 1, spawn.getY(),
				spawn.getZ() + 1);
		world.setBlockState(pos.down(2), Blocks.CACTUS.getDefaultState(), 2);
		world.setBlockState(pos.down(1), Blocks.CACTUS.getDefaultState(), 2);
		world.setBlockState(pos, Blocks.CACTUS.getDefaultState(), 2);
		if (ConfigOptions.spawnChest)
		{
			pos = new BlockPos(spawn.getX(), spawn.getY() - 2,
					spawn.getZ() - 1);
			world.setBlockState(pos, Blocks.CHEST.getDefaultState());
		}
	}

	private static void dirtSpawn(World world, BlockPos spawn)
	{
		for (int x = -(int) Math
				.floor((float) ConfigOptions.islandSize / 2F); x <= (int) Math
						.floor((float) ConfigOptions.islandSize / 2F); x++)
		{
			for (int z = -(int) Math.floor(
					(float) ConfigOptions.islandSize / 2F); z <= (int) Math
							.floor((float) ConfigOptions.islandSize / 2F); z++)
			{
				BlockPos pos = new BlockPos(spawn.getX() + x, spawn.getY(),
						spawn.getZ() + z);
				world.setBlockState(pos.down(3),
						Blocks.DIRT.getStateFromMeta(1), 2);
				world.setBlockState(pos.down(4),
						Blocks.BEDROCK.getDefaultState(), 2);
			}
		}
		BlockPos pos = new BlockPos(spawn.getX() + -1, spawn.getY() - 2,
				spawn.getZ() + 1);
		world.setBlockState(pos.down(2), Blocks.YELLOW_FLOWER.getDefaultState(),
				2);
		if (ConfigOptions.spawnChest)
		{
			pos = new BlockPos(spawn.getX(), spawn.getY(), spawn.getZ() - 1);
			world.setBlockState(pos, Blocks.CHEST.getDefaultState());
		}
	}

	private static void snowSpawn(World world, BlockPos spawn)
	{
		for (int x = -(int) Math.floor((float) ConfigOptions.islandSize / 2F)
				- 1; x <= (int) Math
						.floor((float) ConfigOptions.islandSize / 2F) + 1; x++)
		{
			for (int z = -(int) Math
					.floor((float) ConfigOptions.islandSize / 2F)
					- 1; z <= (int) Math
							.floor((float) ConfigOptions.islandSize / 2F)
							+ 1; z++)
			{
				BlockPos pos = new BlockPos(spawn.getX() + x, spawn.getY(),
						spawn.getZ() + z);

				if (x == -(int) Math
						.floor((float) ConfigOptions.islandSize / 2F) - 1
						|| x == (int) Math
								.floor((float) ConfigOptions.islandSize / 2F)
								+ 1
						|| z == -(int) Math
								.floor((float) ConfigOptions.islandSize / 2F)
								- 1
						|| z == (int) Math
								.floor((float) ConfigOptions.islandSize / 2F)
								+ 1)
				{
					if (ConfigOptions.spawnIgloo)
					{
						world.setBlockState(pos.down(3),
								Blocks.PACKED_ICE.getDefaultState(), 2);

						world.setBlockState(pos.down(2),
								Blocks.PACKED_ICE.getDefaultState(), 2);

						world.setBlockState(pos.down(1),
								Blocks.PACKED_ICE.getDefaultState(), 2);
					}
				} else
				{
					if (!(x == 0 && z == 0) && ConfigOptions.spawnIgloo)
						world.setBlockState(pos,
								Blocks.PACKED_ICE.getDefaultState(), 2);
					world.setBlockState(pos.down(3),
							Blocks.SNOW.getDefaultState(), 2);
					world.setBlockState(pos.down(4),
							Blocks.BEDROCK.getDefaultState(), 2);
					if ((x == -1 && z == 1) || (x == 1 && z == 1))
						world.setBlockState(pos.down(2),
								Blocks.PUMPKIN.getDefaultState(), 2);
					else
						world.setBlockState(pos.down(2),
								Blocks.SNOW_LAYER.getDefaultState(), 2);
				}
			}
		}
		if (ConfigOptions.spawnChest)
		{
			BlockPos pos = new BlockPos(spawn.getX(), spawn.getY() - 2,
					spawn.getZ() - 1);
			world.setBlockState(pos, Blocks.CHEST.getDefaultState());
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
				if (block == Blocks.CACTUS)
				{
					RandomHelper.spawnItemInWorld(event.getWorld(),
							new ItemStack(ModItems.alchemyComponent, 1,
									((AlchemyItemComponent) ModItems.alchemyComponent)
											.getNames()
											.indexOf("cactusNeedle")),
							event.getEntityPlayer().getPosition());
					event.getEntityPlayer()
							.attackEntityFrom(DamageSource.cactus, 4);
				} else if (block == Blocks.SNOW_LAYER)
				{
					RandomHelper.spawnItemInWorld(event.getWorld(),
							new ItemStack(Items.SNOWBALL), event.getPos());
					event.getEntityPlayer().addPotionEffect(
							new PotionEffect(MobEffects.MINING_FATIGUE,
									event.getWorld().rand.nextInt(750) + 750,
									event.getWorld().rand.nextInt(4)));

					int meta = Blocks.SNOW_LAYER.getMetaFromState(
							event.getWorld().getBlockState(event.getPos()));

					if (meta <= 1)
						event.getWorld().setBlockToAir(event.getPos());
					else
						event.getWorld().setBlockState(event.getPos(),
								Blocks.SNOW_LAYER.getStateFromMeta(meta - 2));
				} else if (block == Blocks.SNOW)
				{
					RandomHelper.spawnItemInWorld(event.getWorld(),
							new ItemStack(Items.SNOWBALL), event.getPos());
					event.getEntityPlayer().addPotionEffect(
							new PotionEffect(MobEffects.MINING_FATIGUE,
									event.getWorld().rand.nextInt(750) + 750,
									event.getWorld().rand.nextInt(4)));

					event.getWorld().setBlockState(event.getPos(),
							Blocks.SNOW_LAYER.getStateFromMeta(5));
				}
			} else if (block != null && equip == null)
			{
				if (block == Blocks.CAULDRON)
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

		if (!References.playerHasIsland(player.getName())
				&& !References.worldOneChunk)
			player.addChatMessage(new TextComponentString(
					"Type " + TextFormatting.AQUA.toString() + "/"
							+ ConfigOptions.commandName + " create"
							+ TextFormatting.WHITE.toString()
							+ " to create your starting island"));
		
		TextComponentString text = new TextComponentString(
				"Need help or a guide? \nPress your " + TextFormatting.AQUA
						+ "Open Guide Key"
						+ TextFormatting.WHITE
						+ " to open the Sky Resources in-game guide!");
		player.addChatMessage(text);
	}

	@SubscribeEvent
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		EntityPlayer player = event.player;

		if (player.getBedLocation() == null
				|| player.getBedSpawnLocation(player.worldObj,
						player.getBedLocation(), true) == null)
		{
			System.out.println("HERE");

			IslandPos iPos = References.getPlayerIsland(player.getName());

			BlockPos pos = new BlockPos(
					iPos.getX() * ConfigOptions.islandDistance, 86,
					iPos.getY() * ConfigOptions.islandDistance);

			if (!player.worldObj.isAirBlock(pos)
					&& !player.worldObj.isAirBlock(pos.up()))
			{
				pos = player.worldObj.getTopSolidOrLiquidBlock(pos);

				player.setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ());

				player.addChatComponentMessage(new TextComponentString(
						"Failed to respawn. Sent to top block of platform spawn."));
				return;
			}

			player.setPositionAndUpdate(pos.getX() + 0.5, pos.getY() + 1.6,
					pos.getZ() + 0.5);
			player.setSpawnPoint(pos, true);
		}
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

	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event)
	{
		if (ModKeyBindings.guideKey.isPressed())
		{
			EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;

			if (player.worldObj.isRemote)
			{
				player.openGui(SkyResources.instance, ModGuiHandler.GuideGUI,
						player.worldObj, player.getPosition().getX(),
						player.getPosition().getY(),
						player.getPosition().getZ());
			}
		}
	}

}
