package com.bartz24.skyresources.technology.block;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.base.block.BlockMachine;
import com.bartz24.skyresources.registry.ModCreativeTabs;
import com.bartz24.skyresources.technology.tile.FluidDropperTile;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FluidDropperBlock extends BlockMachine
{

	public FluidDropperBlock(String unlocalizedName,
			String registryName, float hardness, float resistance)
	{
		super(Material.ROCK);
		this.setUnlocalizedName(References.ModID + "." + unlocalizedName);
		this.setCreativeTab(ModCreativeTabs.tabTech);
		this.setHardness(hardness);
		this.setResistance(resistance);
		this.setRegistryName(registryName);
		this.hasTileEntity = true;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new FluidDropperTile();
		
	}
}
