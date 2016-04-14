package com.bartz24.skyresources.waila;

import java.util.List;

import com.bartz24.skyresources.alchemy.block.CrucibleBlock;
import com.bartz24.skyresources.alchemy.block.PurificationVesselBlock;
import com.bartz24.skyresources.alchemy.tile.CrucibleTile;
import com.bartz24.skyresources.alchemy.tile.PurificationVesselTile;
import com.bartz24.skyresources.base.HeatSources;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class WailaPlugin implements IWailaDataProvider
{
	public static void initialize()
	{
		FMLInterModComms.sendMessage("Waila", "register",
				"com.bartz24.skyresources.waila.WailaPlugin.register");
	}

	public static void register(IWailaRegistrar registrar)
	{
		WailaPlugin instance = new WailaPlugin();
		registrar.registerBodyProvider(instance,
				PurificationVesselTile.class);
		registrar.registerBodyProvider(instance,
				CrucibleTile.class);
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity tile,
			NBTTagCompound compound, World world, BlockPos pos)
	{
		return compound;
	}

	@Override
	public List<String> getWailaBody(ItemStack stack, List<String> currentTip,
			IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		if (accessor.getBlock() instanceof PurificationVesselBlock)
		{
			PurificationVesselTile tile = (PurificationVesselTile) accessor
					.getTileEntity();
			addBodyPurificationVessel(tile, currentTip);
		}
		else if (accessor.getBlock() instanceof CrucibleBlock)
		{
			CrucibleTile tile = (CrucibleTile) accessor
					.getTileEntity();
			addBodyCrucible(tile, currentTip);
		}

		return currentTip;
	}

	@Override
	public List<String> getWailaHead(ItemStack stack, List<String> currentTip,
			IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		return currentTip;
	}

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor,
			IWailaConfigHandler config)
	{
		return null;
	}

	@Override
	public List<String> getWailaTail(ItemStack stack, List<String> currentTip,
			IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		return currentTip;
	}

	public void addBodyPurificationVessel(PurificationVesselTile tile,
			List<String> tip)
	{
		if (HeatSources.isValidHeatSource(
				tile.getWorld().getBlockState(tile.getPos().down())))
			tip.add("Valid Heat Source");
		else
			tip.add("Invalid Heat Source");

		String lowerTankName = tile.getLowerTank().getFluid() == null ? "Empty"
				: tile.getLowerTank().getFluid().getLocalizedName();
		String lowerTankAmount = tile.getLowerTank().getFluid() == null ? ""
				: " " + tile.getLowerTank().getFluidAmount();
		tip.add("Lower Tank Fluid:");
		tip.add(lowerTankName + lowerTankAmount);

		String upperTankName = tile.getUpperTank().getFluid() == null ? "Empty"
				: tile.getUpperTank().getFluid().getLocalizedName();
		String upperTankAmount = tile.getUpperTank().getFluid() == null ? ""
				: " " + tile.getUpperTank().getFluidAmount();
		tip.add("Upper Tank Fluid:");
		tip.add(upperTankName + upperTankAmount);
	}

	public void addBodyCrucible(CrucibleTile tile,
			List<String> tip)
	{
		if (HeatSources.isValidHeatSource(
				tile.getWorld().getBlockState(tile.getPos().down())))
			tip.add("Valid Heat Source");
		else
			tip.add("Invalid Heat Source");

		String lowerTankName = tile.getTank().getFluid() == null ? "Empty"
				: tile.getTank().getFluid().getLocalizedName();
		String lowerTankAmount = tile.getTank().getFluid() == null ? ""
				: " " + tile.getTank().getFluidAmount();
		tip.add("Tank Fluid:");
		tip.add(lowerTankName + lowerTankAmount);
	}
}
