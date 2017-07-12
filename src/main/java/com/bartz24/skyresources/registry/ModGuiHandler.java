package com.bartz24.skyresources.registry;

import com.bartz24.skyresources.alchemy.gui.GuiFusionTable;
import com.bartz24.skyresources.alchemy.gui.GuiLifeInfuser;
import com.bartz24.skyresources.alchemy.gui.GuiLifeInjector;
import com.bartz24.skyresources.alchemy.gui.container.ContainerFusionTable;
import com.bartz24.skyresources.alchemy.gui.container.ContainerLifeInfuser;
import com.bartz24.skyresources.alchemy.gui.container.ContainerLifeInjector;
import com.bartz24.skyresources.alchemy.tile.LifeInfuserTile;
import com.bartz24.skyresources.alchemy.tile.LifeInjectorTile;
import com.bartz24.skyresources.alchemy.tile.TileAlchemyFusionTable;
import com.bartz24.skyresources.base.gui.ContainerCasing;
import com.bartz24.skyresources.base.gui.GuiCasing;
import com.bartz24.skyresources.base.guide.gui.GuideGUI;
import com.bartz24.skyresources.base.tile.TileCasing;
import com.bartz24.skyresources.plugin.forestry.gui.GuiBeeAttractor;
import com.bartz24.skyresources.plugin.forestry.gui.container.ContainerBeeAttractor;
import com.bartz24.skyresources.plugin.forestry.tile.TileBeeAttractor;
import com.bartz24.skyresources.technology.gui.GuiAqueousConcentrator;
import com.bartz24.skyresources.technology.gui.GuiCombustionCollector;
import com.bartz24.skyresources.technology.gui.GuiCrucibleInserter;
import com.bartz24.skyresources.technology.gui.GuiDarkMatterWarper;
import com.bartz24.skyresources.technology.gui.GuiDirtFurnace;
import com.bartz24.skyresources.technology.gui.GuiEndPortalCore;
import com.bartz24.skyresources.technology.gui.GuiFreezer;
import com.bartz24.skyresources.technology.gui.GuiQuickDropper;
import com.bartz24.skyresources.technology.gui.GuiRockCleaner;
import com.bartz24.skyresources.technology.gui.GuiRockCrusher;
import com.bartz24.skyresources.technology.gui.container.ContainerAqueousConcentrator;
import com.bartz24.skyresources.technology.gui.container.ContainerCombustionCollector;
import com.bartz24.skyresources.technology.gui.container.ContainerCrucibleInserter;
import com.bartz24.skyresources.technology.gui.container.ContainerDarkMatterWarper;
import com.bartz24.skyresources.technology.gui.container.ContainerDirtFurnace;
import com.bartz24.skyresources.technology.gui.container.ContainerEndPortalCore;
import com.bartz24.skyresources.technology.gui.container.ContainerFreezer;
import com.bartz24.skyresources.technology.gui.container.ContainerQuickDropper;
import com.bartz24.skyresources.technology.gui.container.ContainerRockCleaner;
import com.bartz24.skyresources.technology.gui.container.ContainerRockCrusher;
import com.bartz24.skyresources.technology.tile.DirtFurnaceTile;
import com.bartz24.skyresources.technology.tile.MiniFreezerTile;
import com.bartz24.skyresources.technology.tile.TileAqueousConcentrator;
import com.bartz24.skyresources.technology.tile.TileCombustionCollector;
import com.bartz24.skyresources.technology.tile.TileCrucibleInserter;
import com.bartz24.skyresources.technology.tile.TileDarkMatterWarper;
import com.bartz24.skyresources.technology.tile.TileEndPortalCore;
import com.bartz24.skyresources.technology.tile.TileQuickDropper;
import com.bartz24.skyresources.technology.tile.TileRockCleaner;
import com.bartz24.skyresources.technology.tile.TileRockCrusher;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ModGuiHandler implements IGuiHandler
{
	public static final int FusionGUI = 0;
	public static final int CasingGUI = 1;
	public static final int FreezerGUI = 2;
	public static final int FurnaceGUI = 3;
	public static final int DarkMatterWarperGUI = 4;
	public static final int EndPortalCoreGUI = 5;
	public static final int LifeInfuserGUI = 6;
	public static final int LifeInjectorGUI = 7;
	public static final int RockCrusherGUI = 8;
	public static final int RockCleanerGUI = 9;
	public static final int CombustionCollectorGUI = 10;
	public static final int QuickDropperGUI = 11;
	public static final int AqueousConcentratorGUI = 12;
	public static final int CrucibleInserterGUI = 13;
	public static final int BeeAttractorGUI = 15;
	public static final int GuideGUI = 25;

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		if (id == CasingGUI)
			return new ContainerCasing(player.inventory, (TileCasing) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == FreezerGUI)
			return new ContainerFreezer(player.inventory, (MiniFreezerTile) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == FurnaceGUI)
			return new ContainerDirtFurnace(player.inventory,
					(DirtFurnaceTile) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == DarkMatterWarperGUI)
			return new ContainerDarkMatterWarper(player.inventory,
					(TileDarkMatterWarper) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == EndPortalCoreGUI)
			return new ContainerEndPortalCore(player.inventory,
					(TileEndPortalCore) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == LifeInfuserGUI)
			return new ContainerLifeInfuser(player.inventory,
					(LifeInfuserTile) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == LifeInjectorGUI)
			return new ContainerLifeInjector(player.inventory,
					(LifeInjectorTile) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == RockCrusherGUI)
			return new ContainerRockCrusher(player.inventory,
					(TileRockCrusher) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == RockCleanerGUI)
			return new ContainerRockCleaner(player.inventory,
					(TileRockCleaner) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == CombustionCollectorGUI)
			return new ContainerCombustionCollector(player.inventory,
					(TileCombustionCollector) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == QuickDropperGUI)
			return new ContainerQuickDropper(player.inventory,
					(TileQuickDropper) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == AqueousConcentratorGUI)
			return new ContainerAqueousConcentrator(player.inventory,
					(TileAqueousConcentrator) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == BeeAttractorGUI)
			return new ContainerBeeAttractor(player.inventory,
					(TileBeeAttractor) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == FusionGUI)
			return new ContainerFusionTable(player.inventory,
					(TileAlchemyFusionTable) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == CrucibleInserterGUI)
			return new ContainerCrucibleInserter(player.inventory,
					(TileCrucibleInserter) world.getTileEntity(new BlockPos(x, y, z)));
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		if (id == CasingGUI)
			return new GuiCasing(player.inventory, (TileCasing) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == FreezerGUI)
			return new GuiFreezer(player.inventory, (MiniFreezerTile) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == FurnaceGUI)
			return new GuiDirtFurnace(player.inventory, (DirtFurnaceTile) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == DarkMatterWarperGUI)
			return new GuiDarkMatterWarper(player.inventory,
					(TileDarkMatterWarper) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == EndPortalCoreGUI)
			return new GuiEndPortalCore(player.inventory,
					(TileEndPortalCore) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == LifeInfuserGUI)
			return new GuiLifeInfuser(player.inventory, (LifeInfuserTile) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == LifeInjectorGUI)
			return new GuiLifeInjector(player.inventory, (LifeInjectorTile) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == RockCrusherGUI)
			return new GuiRockCrusher(player.inventory, (TileRockCrusher) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == RockCleanerGUI)
			return new GuiRockCleaner(player.inventory, (TileRockCleaner) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == CombustionCollectorGUI)
			return new GuiCombustionCollector(player.inventory,
					(TileCombustionCollector) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == QuickDropperGUI)
			return new GuiQuickDropper(player.inventory, (TileQuickDropper) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == AqueousConcentratorGUI)
			return new GuiAqueousConcentrator(player.inventory,
					(TileAqueousConcentrator) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == BeeAttractorGUI)
			return new GuiBeeAttractor(player.inventory, (TileBeeAttractor) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == FusionGUI)
			return new GuiFusionTable(player.inventory,
					(TileAlchemyFusionTable) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == CrucibleInserterGUI)
			return new GuiCrucibleInserter(player.inventory,
					(TileCrucibleInserter) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == GuideGUI)
			return new GuideGUI();

		return null;
	}
}
