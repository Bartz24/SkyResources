package com.bartz24.skyresources.alchemy.block;

import java.util.Locale;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.alchemy.tile.CrystallizerTile;
import com.bartz24.skyresources.base.block.IMetaBlockName;
import com.bartz24.skyresources.registry.ModCreativeTabs;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class CrystallizerBlock extends BlockContainer implements IMetaBlockName
{
	public static final PropertyEnum<CrystallizerVariants> variant = PropertyEnum.create("variant",
			CrystallizerVariants.class);

	private String[] crystallizerTypes = new String[] { "stone", "iron", "steel", "darkmatter" };

	public CrystallizerBlock(String unlocalizedName, String registryName, float hardness, float resistance)
	{
		super(Material.ROCK);
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
		return new CrystallizerTile();
	}

	@Override
	public BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, variant);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(variant).ordinal();
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return getMetaFromState(state);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		if (meta >= CrystallizerVariants.values().length || meta < 0)
		{
			meta = 0;
		}
		return getDefaultState().withProperty(variant, CrystallizerVariants.values()[meta]);
	}

	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2, NonNullList<ItemStack> par3)
	{
		par3.add(new ItemStack(par1, 1, 0));
		par3.add(new ItemStack(par1, 1, 1));
		par3.add(new ItemStack(par1, 1, 2));
		par3.add(new ItemStack(par1, 1, 3));
	}

	public enum CrystallizerVariants implements IStringSerializable
	{
		STONE, IRON, STEEL, DARKMATTER;

		@Override
		public String getName()
		{
			return name().toLowerCase(Locale.ROOT);
		}

	}

	@Override
	public String getSpecialName(ItemStack stack)
	{
		return crystallizerTypes[stack.getItemDamage()];
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
			EntityPlayer player)
	{
		return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(world.getBlockState(pos)));
	}
}
