package com.bartz24.skyresources.plugin.theoneprobe;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.alchemy.tile.LifeInfuserTile;
import com.bartz24.skyresources.base.tile.TileCasing;
import com.bartz24.skyresources.technology.block.BlockFreezer;
import com.bartz24.skyresources.technology.block.BlockFreezer.EnumPartType;
import com.bartz24.skyresources.technology.item.ItemCombustionHeater;
import com.bartz24.skyresources.technology.tile.FreezerTile;
import com.bartz24.skyresources.technology.tile.TileEndPortalCore;

import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.config.Config;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class MultiblockProvider implements IProbeInfoProvider
{

	@Override
	public String getID()
	{
		return References.ModID + ":" + "multiblock";
	}

	@Override
	public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world,
			IBlockState blockState, IProbeHitData data)
	{
		TileEntity te = world.getTileEntity(data.getPos());
		boolean hasVal = false;
		boolean valid = false;
		float maxHeat = 0;
		if (te instanceof TileEndPortalCore)
		{
			hasVal = true;
			valid = ((TileEndPortalCore) te).hasValidMultiblock();
		} else if (te instanceof LifeInfuserTile)
		{
			hasVal = true;
			valid = ((LifeInfuserTile) te).hasValidMultiblock();
		} else if (te instanceof FreezerTile)
		{
			BlockPos bottomPos = world.getBlockState(data.getPos()).getProperties()
					.get(BlockFreezer.PART) == EnumPartType.BOTTOM ? data.getPos() : data.getPos().down();
			hasVal = true;
			valid = ((FreezerTile) world.getTileEntity(bottomPos)).hasValidMulti();
		} else if (te instanceof TileCasing)
		{
			if (!((TileCasing) te).machineStored.isEmpty()
					&& ((TileCasing) te).getMachine() instanceof ItemCombustionHeater)
			{
				hasVal = true;
				valid = ((ItemCombustionHeater) ((TileCasing) te).getMachine()).hasValidMultiblock(te.getWorld(),
						te.getPos(), ((TileCasing) te).machineStored);
			}
		}
		if (hasVal)
		{

			boolean v = Config.harvestStyleVanilla;
			int offs = v ? 16 : 0;
			int dim = v ? 13 : 16;
			probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER))
					.icon(new ResourceLocation("theoneprobe", "textures/gui/icons.png"), valid ? 0 : 16, offs, dim, dim,
							probeInfo.defaultIconStyle().width(v ? 18 : 20).height(v ? 14 : 16).textureWidth(32)
									.textureHeight(32))
					.text((valid ? (TextFormatting.GREEN + "Valid ") : (TextFormatting.YELLOW + "Invalid "))
							+ "Multiblock");
		}
	}
}