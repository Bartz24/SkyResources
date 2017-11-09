package com.bartz24.skyresources.registry;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.SkyResources;
import com.bartz24.skyresources.alchemy.tile.CrucibleTile;
import com.bartz24.skyresources.alchemy.tile.LifeInfuserTile;
import com.bartz24.skyresources.alchemy.tile.LifeInjectorTile;
import com.bartz24.skyresources.alchemy.tile.TileAlchemyFusionTable;
import com.bartz24.skyresources.base.entity.EntityHeavyExplosiveSnowball;
import com.bartz24.skyresources.base.entity.EntityHeavySnowball;
import com.bartz24.skyresources.base.tile.TileCasing;
import com.bartz24.skyresources.plugin.forestry.tile.TileBeeAttractor;
import com.bartz24.skyresources.technology.tile.DirtFurnaceTile;
import com.bartz24.skyresources.technology.tile.FluidDropperTile;
import com.bartz24.skyresources.technology.tile.FreezerTile;
import com.bartz24.skyresources.technology.tile.MiniFreezerTile;
import com.bartz24.skyresources.technology.tile.TileAqueousConcentrator;
import com.bartz24.skyresources.technology.tile.TileCombustionCollector;
import com.bartz24.skyresources.technology.tile.TileCombustionController;
import com.bartz24.skyresources.technology.tile.TileCrucibleInserter;
import com.bartz24.skyresources.technology.tile.TileDarkMatterWarper;
import com.bartz24.skyresources.technology.tile.TileEndPortalCore;
import com.bartz24.skyresources.technology.tile.TileQuickDropper;
import com.bartz24.skyresources.technology.tile.TileRockCleaner;
import com.bartz24.skyresources.technology.tile.TileRockCrusher;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class ModEntities
{
	public static void init()
	{
		GameRegistry.registerTileEntity(FluidDropperTile.class, References.ModID + ":fluidDroppertile");
		GameRegistry.registerTileEntity(CrucibleTile.class, References.ModID + ":crucibletile");
		GameRegistry.registerTileEntity(FreezerTile.class, References.ModID + ":freezertile");
		GameRegistry.registerTileEntity(MiniFreezerTile.class, References.ModID + ":minifreezertile");
		GameRegistry.registerTileEntity(DirtFurnaceTile.class, References.ModID + ":dirtfurnacetile");
		GameRegistry.registerTileEntity(TileCasing.class, References.ModID + ":tileCasing");
		GameRegistry.registerTileEntity(TileDarkMatterWarper.class, References.ModID + ":darkmatterwarpertile");
		GameRegistry.registerTileEntity(TileEndPortalCore.class, References.ModID + ":endportalcoretile");
		GameRegistry.registerTileEntity(LifeInfuserTile.class, References.ModID + ":lifeinfusertile");
		GameRegistry.registerTileEntity(LifeInjectorTile.class, References.ModID + ":lifeinjectortile");
		GameRegistry.registerTileEntity(TileCrucibleInserter.class, References.ModID + ":crucibleinsertertile");
		GameRegistry.registerTileEntity(TileRockCrusher.class, References.ModID + ":rockcrushertile");
		GameRegistry.registerTileEntity(TileRockCleaner.class, References.ModID + ":rockcleanertile");
		GameRegistry.registerTileEntity(TileCombustionCollector.class, References.ModID + ":combustioncollectortile");
		GameRegistry.registerTileEntity(TileCombustionController.class, References.ModID + ":combustioncontrollertile");
		GameRegistry.registerTileEntity(TileQuickDropper.class, References.ModID + ":quickdroppertile");
		GameRegistry.registerTileEntity(TileAqueousConcentrator.class, References.ModID + ":aqueousconcentratortile");
		GameRegistry.registerTileEntity(TileAlchemyFusionTable.class, References.ModID + ":fusiontable");
		GameRegistry.registerTileEntity(TileBeeAttractor.class, References.ModID + ":beeAttractorTile");

		EntityRegistry.registerModEntity(new ResourceLocation(References.ModID, "heavysnowball"),
				EntityHeavySnowball.class, References.ModID + ":heavysnowball", 1, SkyResources.instance, 64, 2, true);
		EntityRegistry.registerModEntity(new ResourceLocation(References.ModID, "heavyexplosivesnowball"),
				EntityHeavyExplosiveSnowball.class, References.ModID + ":heavyexplosivesnowball", 2,
				SkyResources.instance, 64, 2, true);

	}
}
