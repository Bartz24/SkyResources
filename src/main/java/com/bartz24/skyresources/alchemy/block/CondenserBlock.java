package com.bartz24.skyresources.alchemy.block;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.alchemy.tile.CondenserTile;
import com.bartz24.skyresources.registry.ModCreativeTabs;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CondenserBlock extends BlockContainer
{

	public CondenserBlock(String unlocalizedName, String registryName,
			float hardness, float resistance)
	{
		super(Material.rock);
		this.setUnlocalizedName(References.ModID + "." + unlocalizedName);
		this.setCreativeTab(ModCreativeTabs.tabAlchemy);
		this.setHardness(hardness);
		this.setResistance(resistance);
		this.setRegistryName(registryName);
		this.isBlockContainer = true;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new CondenserTile();
	}

	@Override
	public boolean onBlockEventReceived(World worldIn, BlockPos pos,
			IBlockState state, int eventID, int eventParam)
	{
		super.onBlockEventReceived(worldIn, pos, state, eventID, eventParam);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		return tileentity == null ? false
				: tileentity.receiveClientEvent(eventID, eventParam);
	}
}
