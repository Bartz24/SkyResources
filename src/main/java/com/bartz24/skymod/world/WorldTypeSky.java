package com.bartz24.skymod.world;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderFlat;

public class WorldTypeSky extends WorldType
{
	public WorldTypeSky()
	{
		super("skyworld");
	}

	public boolean showWorldInfoNotice()
	{
		return true;
	}

	public int getMinimumSpawnHeight(World world)
	{
		return 86;
	}

	public int getSpawnFuzz()
	{
		return 2;
	}

	public IChunkGenerator getChunkGenerator(World world, String generatorOptions)
	{
		return new ChunkProviderFlat(world, world.getSeed(), false, "2;1x0;");
	}
}
