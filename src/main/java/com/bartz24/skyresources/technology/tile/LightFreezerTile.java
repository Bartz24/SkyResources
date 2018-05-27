package com.bartz24.skyresources.technology.tile;

import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.technology.block.BlockFreezer.EnumPartType;
import com.bartz24.skyresources.technology.block.BlockLightFreezer;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;

public class LightFreezerTile extends MiniFreezerTile implements ITickable
{
	public LightFreezerTile()
	{
		super(10, new int[] { 5, 6, 7, 8, 9 }, new int[] { 0, 1, 2, 3, 4 });
	}

	public float getFreezerSpeed()
	{
		return (float) ConfigOptions.machineSettings.lightFreezerSpeed;
	}

	void updateMulti2x1()
	{
		IBlockState state = this.world.getBlockState(pos);
		IBlockState stateUp = this.world.getBlockState(pos.up());
		IBlockState stateDown = this.world.getBlockState(pos.down());
		if (state.getBlock() instanceof BlockLightFreezer)
		{
			if (stateUp.getBlock() instanceof BlockLightFreezer
					&& state.getProperties().get(BlockLightFreezer.PART) == BlockLightFreezer.EnumPartType.BOTTOM
					&& stateUp.getProperties().get(BlockLightFreezer.PART) == BlockLightFreezer.EnumPartType.BOTTOM)
			{
				world.setBlockState(pos.up(), stateUp.withProperty(BlockLightFreezer.PART, BlockLightFreezer.EnumPartType.TOP));
			} else if (!(stateDown.getBlock() instanceof BlockLightFreezer)
					&& state.getProperties().get(BlockLightFreezer.PART) == BlockLightFreezer.EnumPartType.TOP)
			{
				world.setBlockState(pos, state.withProperty(BlockLightFreezer.PART, BlockLightFreezer.EnumPartType.BOTTOM));
			}
		}
	}

	public boolean hasValidMulti()
	{
		IBlockState state = this.world.getBlockState(pos);

		if (state.getBlock() instanceof BlockLightFreezer)
			return validMulti2x1();
		return false;
	}

	boolean validMulti2x1()
	{
		IBlockState state = this.world.getBlockState(pos);
		IBlockState stateUp = this.world.getBlockState(pos.up());

		if (!(state.getBlock() instanceof BlockLightFreezer))
			return false;

		if (!(stateUp.getBlock() instanceof BlockLightFreezer))
			return false;

		if (state.getProperties().get(BlockLightFreezer.FACING) != stateUp.getProperties().get(BlockLightFreezer.FACING))
			return false;

		if (state.getProperties().get(BlockLightFreezer.PART) != BlockLightFreezer.EnumPartType.BOTTOM
				|| stateUp.getProperties().get(BlockLightFreezer.PART) != BlockLightFreezer.EnumPartType.TOP)
			return false;
		return true;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (world.getBlockState(pos).getProperties().get(BlockLightFreezer.PART) == EnumPartType.TOP
				&& world.getTileEntity(pos.down()) instanceof LightFreezerTile)
		{
			return (T) world.getTileEntity(pos.down()).getCapability(capability, facing);
		}
		return super.getCapability(capability, facing);
	}
}
