package com.bartz24.skyresources.technology.block;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.registry.ModCreativeTabs;
import com.bartz24.skyresources.technology.tile.TileCrucibleInserter;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;

public class BlockCrucibleInserter extends BlockContainer
{
	public BlockCrucibleInserter(String unlocalizedName, String registryName, float hardness,
			float resistance)
	{
		super(Material.ROCK);
		this.setUnlocalizedName(References.ModID + "." + unlocalizedName);
		this.setCreativeTab(ModCreativeTabs.tabTech);
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
		return new TileCrucibleInserter();
	}
}
