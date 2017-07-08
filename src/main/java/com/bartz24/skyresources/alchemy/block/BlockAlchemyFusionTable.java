package com.bartz24.skyresources.alchemy.block;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.SkyResources;
import com.bartz24.skyresources.alchemy.tile.TileAlchemyFusionTable;
import com.bartz24.skyresources.registry.ModCreativeTabs;
import com.bartz24.skyresources.registry.ModGuiHandler;

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

public class BlockAlchemyFusionTable extends BlockContainer
{

	public BlockAlchemyFusionTable(String unlocalizedName, String registryName, float hardness, float resistance)
	{
		super(Material.WOOD);
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
		return new TileAlchemyFusionTable();
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		TileAlchemyFusionTable te = (TileAlchemyFusionTable) world.getTileEntity(pos);
		te.dropInventory();

		super.breakBlock(world, pos, state);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			player.openGui(SkyResources.instance, ModGuiHandler.FusionGUI, world, pos.getX(), pos.getY(), pos.getZ());

		}
		return true;
	}
}
