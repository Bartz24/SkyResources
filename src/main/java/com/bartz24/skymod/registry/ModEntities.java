package com.bartz24.skymod.registry;

import net.minecraftforge.fml.common.registry.GameRegistry;

import com.bartz24.skymod.alchemy.tile.CondenserTile;
import com.bartz24.skymod.technology.tile.CombustionHeaterTile;

public final class ModEntities
{
	public static void init()
	{
		GameRegistry.registerTileEntity(CombustionHeaterTile.class, "combustionHeaterTile");
		GameRegistry.registerTileEntity(CondenserTile.class, "condenserTile");
	}
}
