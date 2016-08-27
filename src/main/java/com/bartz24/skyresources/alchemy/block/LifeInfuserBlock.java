package com.bartz24.skyresources.alchemy.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.SkyResources;
import com.bartz24.skyresources.alchemy.tile.LifeInfuserTile;
import com.bartz24.skyresources.registry.ModCreativeTabs;
import com.bartz24.skyresources.registry.ModGuiHandler;
import com.bartz24.skyresources.technology.tile.CombustionHeaterTile;
import com.bartz24.skyresources.technology.tile.TilePoweredCombustionHeater;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class LifeInfuserBlock extends BlockContainer
{

	public LifeInfuserBlock(String unlocalizedName, String registryName, float hardness,
			float resistance)
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
		return new LifeInfuserTile();
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		LifeInfuserTile te = (LifeInfuserTile) world.getTileEntity(pos);
		InventoryHelper.dropInventoryItems(world, pos, te);

		super.breakBlock(world, pos, state);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state,
			EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX,
			float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			player.openGui(SkyResources.instance, ModGuiHandler.LifeInfuserGUI, world,
					pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
}
