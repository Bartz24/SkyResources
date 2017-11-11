package com.bartz24.skyresources.technology.block;

import com.bartz24.skyresources.technology.tile.LightFreezerTile;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockLightFreezer extends BlockFreezer
{
	public BlockLightFreezer(String unlocalizedName, String registryName, float hardness, float resistance)
	{
		super(unlocalizedName, registryName, hardness, resistance);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new LightFreezerTile();
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		LightFreezerTile te = (LightFreezerTile) world.getTileEntity(pos);
		te.dropInventory();
		super.breakBlock(world, pos, state);
	}
}
