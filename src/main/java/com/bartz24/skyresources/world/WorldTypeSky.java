package com.bartz24.skyresources.world;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiCreateFlatWorld;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.gen.ChunkProviderFlat;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldTypeSky extends WorldType
{
	public WorldTypeSky()
	{
		super("skyworld");
	}

	@Override
	public boolean showWorldInfoNotice()
	{
		return true;
	}

	@Override
	public int getMinimumSpawnHeight(World world)
	{
		return 86;
	}

	public int getSpawnFuzz()
	{
		return 2;
	}

	@Override
	public float getCloudHeight()
	{
		return 32.0F;
	}

	@Override
	public double getHorizon(World world)
	{
		return 40.0D;
	}

	@Override
	public IChunkGenerator getChunkGenerator(World world,
			String generatorOptions)
	{
		return new ChunkProviderFlat(world, world.getSeed(), false, "2;1x0;");
	}
}
