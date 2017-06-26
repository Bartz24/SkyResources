package com.bartz24.skyresources.plugin.theoneprobe;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.alchemy.tile.CondenserTile;
import com.bartz24.skyresources.alchemy.tile.CrucibleTile;
import com.bartz24.skyresources.base.HeatSources;
import com.bartz24.skyresources.base.IHeatSource;
import com.bartz24.skyresources.registry.ModBlocks;

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
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class HeatProvider implements IProbeInfoProvider
{

	@Override
	public String getID()
	{
		return References.ModID + ":" + "heat";
	}

	@Override
	public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world,
			IBlockState blockState, IProbeHitData data)
	{
		TileEntity te = world.getTileEntity(data.getPos());
		if (te instanceof CondenserTile || te instanceof CrucibleTile
				|| world.getBlockState(data.getPos()).getBlock().equals(ModBlocks.blazePowderBlock))
		{
			boolean v = Config.harvestStyleVanilla;
			int offs = v ? 16 : 0;
			int dim = v ? 13 : 16;
			probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER))
					.icon(new ResourceLocation("theoneprobe", "textures/gui/icons.png"),
							HeatSources.isValidHeatSource(data.getPos().down(), world) ? 0 : 16, offs, dim, dim,
							probeInfo.defaultIconStyle().width(v ? 18 : 20).height(v ? 14 : 16).textureWidth(32)
									.textureHeight(32))
					.text((HeatSources.isValidHeatSource(data.getPos().down(), world)
							? (TextFormatting.GREEN + "Valid ") : (TextFormatting.YELLOW + "Invalid ")) + "Heat Source");
		}

		te = world.getTileEntity(data.getPos());
		int curHeat = -1;
		if (te instanceof IHeatSource)
		{
			curHeat = ((IHeatSource) te).getHeatValue();
		} else if (HeatSources.isValidHeatSource(data.getPos(), world))
		{
			curHeat = HeatSources.getHeatSourceValue(data.getPos(), world);
		}
		if (curHeat >= 0f)
		{
			probeInfo.horizontal().text(TextFormatting.YELLOW + Integer.toString(curHeat) + " Heat Source Value");
		}
	}
}