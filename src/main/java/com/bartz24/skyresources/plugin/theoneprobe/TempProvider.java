package com.bartz24.skyresources.plugin.theoneprobe;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.base.tile.TileCasing;
import com.bartz24.skyresources.technology.item.ItemCombustionHeater;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TempProvider implements IProbeInfoProvider
{

	@Override
	public String getID()
	{
		return References.ModID + ":" + "temp";
	}

	@Override
	public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world,
			IBlockState blockState, IProbeHitData data)
	{
		TileEntity te = world.getTileEntity(data.getPos());
		float curHeat = -1;
		float maxHeat = 0;
		if (te instanceof TileCasing)
		{
			if (!((TileCasing) te).machineStored.isEmpty()
					&& ((TileCasing) te).getMachine() instanceof ItemCombustionHeater)
			{
				maxHeat = ((ItemCombustionHeater) ((TileCasing) te).getMachine()).getMaxHU(world, te.getPos());
				curHeat = ((TileCasing) te).machineData.getFloat("curHU");
			}
		}
		if (curHeat >= 0f)
		{
			if (!player.isSneaking())
				probeInfo.horizontal().progress((int) (curHeat / maxHeat * 100f), 100, probeInfo.defaultProgressStyle()
						.suffix("% HU").filledColor(0xFFDEB81F).alternateFilledColor(0xFFFFD633));
			else
				probeInfo.horizontal().progress((int) (curHeat), (int) maxHeat, probeInfo.defaultProgressStyle()
						.suffix(" / " + maxHeat + " HU").filledColor(0xFFDEB81F).alternateFilledColor(0xFFFFD633));
		}
	}
}