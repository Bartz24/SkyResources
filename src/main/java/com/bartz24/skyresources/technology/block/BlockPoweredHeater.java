package com.bartz24.skyresources.technology.block;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.ChatHelper;
import com.bartz24.skyresources.References;
import com.bartz24.skyresources.SkyResources;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.registry.ModCreativeTabs;
import com.bartz24.skyresources.registry.ModGuiHandler;
import com.bartz24.skyresources.technology.block.BlockFreezer.EnumPartType;
import com.bartz24.skyresources.technology.tile.TilePoweredHeater;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class BlockPoweredHeater extends BlockContainer
{

	public static final PropertyBool RUNNING = PropertyBool.create("running");

	public BlockPoweredHeater(String unlocalizedName, String registryName, float hardness,
			float resistance)
	{
		super(Material.IRON);
		this.setUnlocalizedName(References.ModID + "." + unlocalizedName);
		this.setCreativeTab(ModCreativeTabs.tabTech);
		this.setHardness(hardness);
		this.setResistance(resistance);
		this.setRegistryName(registryName);
		this.isBlockContainer = true;
		this.setDefaultState(this.blockState.getBaseState().withProperty(RUNNING, false));
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TilePoweredHeater();
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state,
			EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX,
			float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			if (!player.isSneaking())
			{
				List<ITextComponent> toSend = new ArrayList();			
				TilePoweredHeater tile = (TilePoweredHeater) world.getTileEntity(pos);
				toSend.add(new TextComponentString(TextFormatting.RED + "RF Stored: "
						+ tile.getEnergyStored(null) + " / " + tile.getMaxEnergyStored(null)));
				ChatHelper.sendNoSpamMessages(toSend.toArray(new ITextComponent[toSend.size()]));
			}
		}
		return true;
	}

	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX,
			float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getDefaultState().withProperty(RUNNING, false);
	}

	public IBlockState getStateFromMeta(int meta)
	{
		if (meta == 1)
			return this.getDefaultState().withProperty(RUNNING, true);
		return this.getDefaultState().withProperty(RUNNING, false);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState state)
	{

		if (state.getValue(RUNNING) == true)
		{
			return 1;
		}

		return 0;
	}

	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[]
		{ RUNNING });
	}
}
