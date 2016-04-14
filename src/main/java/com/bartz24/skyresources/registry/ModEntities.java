package com.bartz24.skyresources.registry;

import com.bartz24.skyresources.alchemy.tile.CondenserTile;
import com.bartz24.skyresources.alchemy.tile.CrucibleTile;
import com.bartz24.skyresources.alchemy.tile.PurificationVesselTile;
import com.bartz24.skyresources.technology.tile.CombustionHeaterTile;
import com.bartz24.skyresources.technology.tile.ConcentratorTile;
import com.bartz24.skyresources.technology.tile.FluidDropperTile;

import net.minecraftforge.fml.common.registry.GameRegistry;

public final class ModEntities
{
	public static void init()
	{
		GameRegistry.registerTileEntity(CombustionHeaterTile.class,
				"combustionHeaterTile");
		GameRegistry.registerTileEntity(FluidDropperTile.class, "fluidDropperTile");
		GameRegistry.registerTileEntity(CondenserTile.class, "condenserTile");
		GameRegistry.registerTileEntity(CrucibleTile.class, "crucibleTile");
		GameRegistry.registerTileEntity(ConcentratorTile.class, "concentratorTile");
		GameRegistry.registerTileEntity(PurificationVesselTile.class, "purificationVesselTile");
		
	}
}
