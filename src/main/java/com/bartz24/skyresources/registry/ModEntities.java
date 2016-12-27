package com.bartz24.skyresources.registry;

import com.bartz24.skyresources.alchemy.tile.CondenserTile;
import com.bartz24.skyresources.alchemy.tile.CrucibleTile;
import com.bartz24.skyresources.alchemy.tile.CrystallizerTile;
import com.bartz24.skyresources.alchemy.tile.LifeInfuserTile;
import com.bartz24.skyresources.technology.tile.CombustionHeaterTile;
import com.bartz24.skyresources.technology.tile.DirtFurnaceTile;
import com.bartz24.skyresources.technology.tile.FluidDropperTile;
import com.bartz24.skyresources.technology.tile.FreezerTile;
import com.bartz24.skyresources.technology.tile.TileCrucibleInserter;
import com.bartz24.skyresources.technology.tile.TileDarkMatterWarper;
import com.bartz24.skyresources.technology.tile.TileEndPortalCore;
import com.bartz24.skyresources.technology.tile.TilePoweredCombustionHeater;
import com.bartz24.skyresources.technology.tile.TilePoweredHeater;

import net.minecraftforge.fml.common.registry.GameRegistry;

public final class ModEntities
{
	public static void init()
	{
		GameRegistry.registerTileEntity(CombustionHeaterTile.class, "combustionHeatertile");
		GameRegistry.registerTileEntity(FluidDropperTile.class, "fluidDroppertile");
		GameRegistry.registerTileEntity(CondenserTile.class, "condensertile");
		GameRegistry.registerTileEntity(CrucibleTile.class, "crucibletile");
		GameRegistry.registerTileEntity(FreezerTile.class, "freezertile");
		GameRegistry.registerTileEntity(DirtFurnaceTile.class, "dirtfurnacetile");
		GameRegistry.registerTileEntity(TilePoweredHeater.class, "poweredHeatertile");
		GameRegistry.registerTileEntity(TilePoweredCombustionHeater.class,
				"poweredcombustionheatertile");
		GameRegistry.registerTileEntity(CrystallizerTile.class, "crystallizertile");
		GameRegistry.registerTileEntity(TileDarkMatterWarper.class, "darkmatterwarpertile");
		GameRegistry.registerTileEntity(TileEndPortalCore.class, "endportalcoretile");
		GameRegistry.registerTileEntity(LifeInfuserTile.class, "lifeinfusertile");
		GameRegistry.registerTileEntity(TileCrucibleInserter.class, "crucibleinsertertile");
		//GameRegistry.registerTileEntity(TileBeeAttractor.class, "beeAttractorTile");


	}
}
