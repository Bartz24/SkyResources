package com.bartz24.skyresources.technology.block;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.SkyResources;
import com.bartz24.skyresources.registry.ModCreativeTabs;
import com.bartz24.skyresources.registry.ModGuiHandler;
import com.bartz24.skyresources.technology.tile.TileCombustionCollector;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCombustionCollector extends BlockContainer
{

	public BlockCombustionCollector(String unlocalizedName, String registryName, float hardness, float resistance)
	{
		super(Material.IRON);
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
		return new TileCombustionCollector();
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		TileCombustionCollector te = (TileCombustionCollector) world.getTileEntity(pos);
		te.dropInventory();

		super.breakBlock(world, pos, state);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			player.openGui(SkyResources.instance, ModGuiHandler.CombustionCollectorGUI, world, pos.getX(), pos.getY(),
					pos.getZ());

		}
		return true;
	}
}
