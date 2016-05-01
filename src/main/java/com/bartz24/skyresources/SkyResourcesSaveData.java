package com.bartz24.skyresources;

import java.util.Map;
import java.util.Map.Entry;

import com.bartz24.skyresources.events.EventHandler;
import com.sun.corba.se.pept.transport.Connection;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class SkyResourcesSaveData extends WorldSavedData
{
	private static SkyResourcesSaveData INSTANCE;
	public static final String dataName = "SkyResources-SaveData";

	public SkyResourcesSaveData(String s)
	{
		super(s);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		References.CurrentIslandsList.clear();
		NBTTagList list = nbt.getTagList("Positions", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < list.tagCount(); ++i)
		{
			NBTTagCompound stackTag = list.getCompoundTagAt(i);
			
			IslandPos pos = new IslandPos(0,0);
			pos.readFromNBT(stackTag);
			References.CurrentIslandsList.add(pos);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		NBTTagList list = new NBTTagList();
		for (int i = 0; i < References.CurrentIslandsList.size(); i++)
		{
			NBTTagCompound stackTag = new NBTTagCompound();

			References.CurrentIslandsList.get(i).writeToNBT(stackTag);
			
			list.appendTag(stackTag);
		}
		nbt.setTag("Positions", list);
	}

	public static void setDirty(int dimension)
	{
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER
				&& INSTANCE != null)
			INSTANCE.markDirty();
	}

	public static void setInstance(int dimension, SkyResourcesSaveData in)
	{
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
			INSTANCE = in;
	}
}
