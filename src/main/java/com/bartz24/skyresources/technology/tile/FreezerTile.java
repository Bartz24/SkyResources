package com.bartz24.skyresources.technology.tile;

import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;
import com.bartz24.skyresources.registry.ModBlocks;
import com.bartz24.skyresources.technology.block.BlockFreezer;
import com.bartz24.skyresources.technology.block.BlockMiniFreezer;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public class FreezerTile extends MiniFreezerTile implements ITickable
{
	public FreezerTile()
	{
		super(6, new Integer[] { 3, 4, 5 }, new Integer[] { 0, 1, 2 });
	}

	public float getFreezerSpeed()
	{
		return 1;
	}

	void updateMulti2x1()
	{
		IBlockState state = this.world.getBlockState(pos);
		IBlockState stateUp = this.world.getBlockState(pos.up());
		IBlockState stateDown = this.world.getBlockState(pos.down());
		if (state.getBlock() instanceof BlockFreezer)
		{
			if (stateUp.getBlock() instanceof BlockFreezer
					&& state.getProperties().get(BlockFreezer.PART) == BlockFreezer.EnumPartType.BOTTOM
					&& stateUp.getProperties().get(BlockFreezer.PART) == BlockFreezer.EnumPartType.BOTTOM)
			{
				world.setBlockState(pos.up(), stateUp.withProperty(BlockFreezer.PART, BlockFreezer.EnumPartType.TOP));
			} else if (!(stateDown.getBlock() instanceof BlockFreezer)
					&& state.getProperties().get(BlockFreezer.PART) == BlockFreezer.EnumPartType.TOP)
			{
				world.setBlockState(pos, state.withProperty(BlockFreezer.PART, BlockFreezer.EnumPartType.BOTTOM));
			}
		}
	}

	public boolean hasValidMulti()
	{
		IBlockState state = this.world.getBlockState(pos);

		if (state.getBlock() instanceof BlockFreezer)
			return validMulti2x1();
		return false;
	}

	boolean validMulti2x1()
	{
		IBlockState state = this.world.getBlockState(pos);
		IBlockState stateUp = this.world.getBlockState(pos.up());

		if (!(state.getBlock() instanceof BlockFreezer))
			return false;

		if (!(stateUp.getBlock() instanceof BlockFreezer))
			return false;

		if (state.getProperties().get(BlockFreezer.FACING) != stateUp.getProperties().get(BlockFreezer.FACING))
			return false;

		if (state.getProperties().get(BlockFreezer.PART) != BlockFreezer.EnumPartType.BOTTOM
				|| stateUp.getProperties().get(BlockFreezer.PART) != BlockFreezer.EnumPartType.TOP)
			return false;
		return true;
	}
}
