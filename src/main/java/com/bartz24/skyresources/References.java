package com.bartz24.skyresources;

import java.util.ArrayList;

import com.bartz24.skyresources.api.IslandPos;
import com.bartz24.skyresources.config.ConfigOptions;
import com.google.common.base.Strings;

import net.minecraft.command.CommandGive;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class References
{
	public static final String ModID = "skyresources";
	public static final String ModName = "Sky Resources";

	public static ArrayList<IslandPos> CurrentIslandsList = new ArrayList<IslandPos>();

	public static ArrayList<String> spawnedPlayers = new ArrayList<String>();

	public static boolean worldOneChunk = false;
	public static int initialIslandDistance = ConfigOptions.islandDistance;

	public static IslandPos getNextIsland()
	{
		int size = (int) Math.floor(Math.sqrt(CurrentIslandsList.size()));
		if (size % 2 == 0 && size > 0)
			size--;

		size = (size + 1) / 2;
		for (int x = -size; x <= size; x++)
		{
			for (int z = -size; z <= size; z++)
			{
				if (!hasPosition(x, z))
				{
					return new IslandPos(x, z);
				}
			}
		}
		return null;
	}

	public static IslandPos getPlayerIsland(String playerName)
	{
		for (IslandPos pos : CurrentIslandsList)
		{
			if (pos.getPlayerNames().contains(playerName))
				return pos;
		}
		return null;
	}

	public static boolean hasPosition(int x, int y)
	{
		for (IslandPos pos : CurrentIslandsList)
		{
			if (pos.getX() == x && pos.getY() == y)
				return true;
		}

		return false;
	}

	public static boolean playerHasIsland(String playerName)
	{
		for (IslandPos pos : CurrentIslandsList)
		{
			if (pos.getPlayerNames().contains(playerName))
				return true;
		}

		return false;
	}

	public static void addPlayer(String playerName, IslandPos posAdd)
	{
		for (IslandPos pos : CurrentIslandsList)
		{
			if (pos.getX() == posAdd.getX() && pos.getY() == posAdd.getY())
			{
				pos.addNewPlayer(playerName);
				return;
			}
		}
	}

	public static void removePlayer(String playerName)
	{
		IslandPos pos = getPlayerIsland(playerName);
		pos.removePlayer(playerName);
	}

	public static boolean hasPlayerSpawned(String playerName)
	{
		return spawnedPlayers.contains(playerName);
	}

	public static void setStartingInv(EntityPlayerMP player)
	{
		player.inventory.clear();

		try
		{
			for (int i = 0; i < ConfigOptions.startingItems.size(); i++)
			{
				String s = ConfigOptions.startingItems.get(i);
				if (!Strings.isNullOrEmpty(s) && s.contains(":") && s.contains("*"))
				{
					String trimmed = s.replaceAll(" ", "");
					String itemName = trimmed.split(":")[0] + ":" + trimmed.split(":")[1];
					int meta = Integer.parseInt(trimmed.split(":")[2].split("\\*")[0]);
					int amt = Integer.parseInt(trimmed.split(":")[2].split("\\*")[1]);

					Item item = CommandGive.getItemByText(player, itemName);

					ItemStack stack = new ItemStack(item, amt, meta);

					player.inventory.setInventorySlotContents(i, stack);
				}
			}
		} catch (Exception e)
		{
			player.inventory.clear();
			player.addChatMessage(new TextComponentString(
					TextFormatting.RED + "Error getting starting inventory.\n" + e.toString()));
		}
	}

	public static void tpPlayerToPos(EntityPlayer player, BlockPos pos)
	{
		if (!player.worldObj.isAirBlock(pos) && !player.worldObj.isAirBlock(pos.up()))
		{
			pos = player.worldObj.getTopSolidOrLiquidBlock(pos);

			player.addChatComponentMessage(new TextComponentString(
					"Failed to spawn. Sent to top block of platform spawn."));
		}
		player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 60, 20, false, false));

		player.setPositionAndUpdate(pos.getX() + 0.5, pos.getY() + 2.6, pos.getZ() + 0.5);
	}

	public static void tpPlayerToPosSpawn(EntityPlayer player, BlockPos pos)
	{
		tpPlayerToPos(player, pos);
		player.setSpawnPoint(pos, true);
	}
}
