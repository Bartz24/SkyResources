package com.bartz24.skyresources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class IslandPos
{
	private int posX;
	private int posY;

	private ArrayList<String> playerNames;

	public IslandPos(int x, int y, String... names)
	{
		posX = x;
		posY = y;
		if (playerNames == null)
			playerNames = new ArrayList<String>();
		
		playerNames.addAll(Arrays.asList(names));

	}

	public void addNewPlayer(String playerName)
	{
		if (!playerNames.contains(playerName))
			playerNames.add(playerName);
	}
	
	public void removePlayer(String playerName)
	{
		if (playerNames.contains(playerName))
			playerNames.remove(playerName);
	}

	public int getX()
	{
		return posX;
	}

	public int getY()
	{
		return posY;
	}

	public List<String> getPlayerNames()
	{
		return playerNames;
	}

	public void writeToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("posX", posX);
		nbt.setInteger("posY", posY);

		NBTTagList list = new NBTTagList();
		for (int i = 0; i < playerNames.size(); i++)
		{
			NBTTagCompound stackTag = new NBTTagCompound();

			stackTag.setString("playerName", playerNames.get(i));

			list.appendTag(stackTag);
		}
		nbt.setTag("Names", list);
	}

	public void readFromNBT(NBTTagCompound nbt)
	{
		posX = nbt.getInteger("posX");
		posY = nbt.getInteger("posY");

		playerNames = new ArrayList<String>();

		NBTTagList list = nbt.getTagList("Names", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < list.tagCount(); ++i)
		{
			NBTTagCompound stackTag = list.getCompoundTagAt(i);

			String name = stackTag.getString("playerName");
			playerNames.add(name);
		}
	}
}
