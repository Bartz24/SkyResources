package com.bartz24.skyresources.registry;

import com.bartz24.skyresources.alchemy.tile.CondenserTile;
import com.bartz24.skyresources.alchemy.tile.CrucibleTile;
import com.bartz24.skyresources.alchemy.tile.CrystallizerTile;
import com.bartz24.skyresources.alchemy.tile.PurificationVesselTile;
import com.bartz24.skyresources.plugin.forestry.tile.TileBeeAttractor;
import com.bartz24.skyresources.technology.tile.CombustionHeaterTile;
import com.bartz24.skyresources.technology.tile.ConcentratorTile;
import com.bartz24.skyresources.technology.tile.DirtFurnaceTile;
import com.bartz24.skyresources.technology.tile.FluidDropperTile;
import com.bartz24.skyresources.technology.tile.FreezerTile;
import com.bartz24.skyresources.technology.tile.TilePoweredCombustionHeater;
import com.bartz24.skyresources.technology.tile.TilePoweredHeater;

import net.minecraftforge.fml.common.registry.GameRegistry;

public final class ModEntities
{
	public static void init()
	{
		GameRegistry.registerTileEntity(CombustionHeaterTile.class, "combustionHeaterTile");
		GameRegistry.registerTileEntity(FluidDropperTile.class, "fluidDropperTile");
		GameRegistry.registerTileEntity(CondenserTile.class, "condenserTile");
		GameRegistry.registerTileEntity(CrucibleTile.class, "crucibleTile");
		GameRegistry.registerTileEntity(ConcentratorTile.class, "concentratorTile");
		GameRegistry.registerTileEntity(PurificationVesselTile.class, "purificationVesselTile");
		GameRegistry.registerTileEntity(FreezerTile.class, "freezerTile");
		GameRegistry.registerTileEntity(DirtFurnaceTile.class, "furnaceTile");
		GameRegistry.registerTileEntity(TileBeeAttractor.class, "beeAttractorTile");
		GameRegistry.registerTileEntity(TilePoweredHeater.class, "poweredHeaterTile");
		GameRegistry.registerTileEntity(TilePoweredCombustionHeater.class,
				"poweredCombustionHeaterTile");
		GameRegistry.registerTileEntity(CrystallizerTile.class,
				"crystallizerTile");

	}
}
