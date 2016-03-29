package com.bartz24.skyresources.world;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkGenerator;
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
	
	public float getCloudHeight()
    {
        return 32.0F;
    }
	
	public double getHorizon(World world)
    {
		return 40.0D;
    }


	public IChunkGenerator getChunkGenerator(World world, String generatorOptions)
	{
		return new ChunkProviderFlat(world, world.getSeed(), false, "2;1x0;");
	}
}
