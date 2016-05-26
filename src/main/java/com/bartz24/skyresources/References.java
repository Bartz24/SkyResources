package com.bartz24.skyresources;

import java.util.ArrayList;
import java.util.HashMap;

public class References
{
	public static final String ModID = "skyresources";
	public static final String ModName = "Sky Resources";

	public static ArrayList<IslandPos> CurrentIslandsList = new ArrayList<IslandPos>();
	
	public static ArrayList<String> spawnedPlayers = new ArrayList<String>();
	
	public static boolean worldOneChunk = false;

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
			if (pos.getX() == posAdd.getX() && pos.getY() == pos.getY())
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
	

	public static ArrayList<String> gemList = new ArrayList<String>();
	public static ArrayList<Integer> gemColorList = new ArrayList<Integer>();
}
