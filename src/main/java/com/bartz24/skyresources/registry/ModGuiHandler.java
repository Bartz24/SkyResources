package com.bartz24.skyresources.registry;

import com.bartz24.skyresources.alchemy.gui.GuiLifeInfuser;
import com.bartz24.skyresources.alchemy.gui.container.ContainerLifeInfuser;
import com.bartz24.skyresources.alchemy.tile.LifeInfuserTile;
import com.bartz24.skyresources.base.guide.gui.GuideGUI;
import com.bartz24.skyresources.technology.gui.GuiCombustionHeater;
import com.bartz24.skyresources.technology.gui.GuiDarkMatterWarper;
import com.bartz24.skyresources.technology.gui.GuiDirtFurnace;
import com.bartz24.skyresources.technology.gui.GuiEndPortalCore;
import com.bartz24.skyresources.technology.gui.GuiFreezer;
import com.bartz24.skyresources.technology.gui.container.ContainerCombustionHeater;
import com.bartz24.skyresources.technology.gui.container.ContainerDarkMatterWarper;
import com.bartz24.skyresources.technology.gui.container.ContainerDirtFurnace;
import com.bartz24.skyresources.technology.gui.container.ContainerEndPortalCore;
import com.bartz24.skyresources.technology.gui.container.ContainerFreezer;
import com.bartz24.skyresources.technology.tile.CombustionHeaterTile;
import com.bartz24.skyresources.technology.tile.DirtFurnaceTile;
import com.bartz24.skyresources.technology.tile.FreezerTile;
import com.bartz24.skyresources.technology.tile.TileDarkMatterWarper;
import com.bartz24.skyresources.technology.tile.TileEndPortalCore;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ModGuiHandler implements IGuiHandler
{
	public static final int CombustionHeaterGUI = 0;
	public static final int FreezerGUI = 2;
	public static final int FurnaceGUI = 3;
	public static final int DarkMatterWarperGUI = 4;
	public static final int EndPortalCoreGUI = 5;
	public static final int LifeInfuserGUI = 6;
	public static final int BeeAttractorGUI = 10;
	public static final int GuideGUI = 25;

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		if (id == CombustionHeaterGUI)
			return new ContainerCombustionHeater(player.inventory,
					(CombustionHeaterTile) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == FreezerGUI)
			return new ContainerFreezer(player.inventory, (FreezerTile) world.getTileEntity(new BlockPos(x, y, z)));
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
		/*
		 * else if (id == BeeAttractorGUI) return new
		 * ContainerBeeAttractor(player.inventory, (TileBeeAttractor) world
		 * .getTileEntity(new BlockPos(x, y, z)));
		 */
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		if (id == CombustionHeaterGUI)
			return new GuiCombustionHeater(player.inventory,
					(CombustionHeaterTile) world.getTileEntity(new BlockPos(x, y, z)));
		else if (id == FreezerGUI)
			return new GuiFreezer(player.inventory, (FreezerTile) world.getTileEntity(new BlockPos(x, y, z)));
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
		/*
		 * else if (id == BeeAttractorGUI) return new
		 * GuiBeeAttractor(player.inventory, (TileBeeAttractor) world
		 * .getTileEntity(new BlockPos(x, y, z)));
		 */
		else if (id == GuideGUI)
			return new GuideGUI();

		return null;
	}
}
