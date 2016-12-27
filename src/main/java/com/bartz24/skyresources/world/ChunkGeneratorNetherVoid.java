package com.bartz24.skyresources.world;

import java.util.List;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkProviderHell;

public class ChunkGeneratorNetherVoid extends ChunkProviderHell
{
	World world;
	public ChunkGeneratorNetherVoid(World par1World, long par2)
	{
		super(par1World, false, par2);
		world = par1World;
	}

	@Override
	public void prepareHeights(int p_185936_1_, int p_185936_2_, ChunkPrimer primer)
	{
	}

	@Override
	public void buildSurfaces(int p_185937_1_, int p_185937_2_, ChunkPrimer primer)
	{
	}

    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos)
    {
        Biome biome = world.getBiome(pos);
        return biome.getSpawnableList(creatureType);
    }

	@Override
	public void populate(int x, int z)
	{
	}
}
