package com.bartz24.skyresources.alchemy.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.alchemy.tile.TileWaterConcentrator;
import com.bartz24.skyresources.base.block.IMetaBlockName;
import com.bartz24.skyresources.registry.ModCreativeTabs;
import com.bartz24.skyresources.technology.tile.CombustionHeaterTile;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class BlockWaterConcentrator extends BlockContainer implements IMetaBlockName
{
	public static final PropertyEnum<WaterConcentratorVariants> variant = PropertyEnum
			.create("variant", WaterConcentratorVariants.class);

	private String[] types = new String[]
	{ "concentrator", "deconcentrator" };

	public BlockWaterConcentrator(String unlocalizedName, String registryName, float hardness,
			float resistance)
	{
		super(Material.IRON);
		this.setUnlocalizedName(References.ModID + "." + unlocalizedName);
		this.setCreativeTab(ModCreativeTabs.tabAlchemy);
		this.setHardness(hardness);
		this.setResistance(resistance);
		this.setRegistryName(registryName);
		this.isBlockContainer = true;

		setDefaultState(blockState.getBaseState().withProperty(variant,
				WaterConcentratorVariants.CONCENTRATOR));
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileWaterConcentrator(meta == 0);
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
		if (meta >= WaterConcentratorVariants.values().length || meta < 0)
		{
			meta = 0;
		}
		return getDefaultState().withProperty(variant, WaterConcentratorVariants.values()[meta]);
	}

	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2, List<ItemStack> par3)
	{
		par3.add(new ItemStack(par1, 1, 0));
		par3.add(new ItemStack(par1, 1, 1));
	}

	public enum WaterConcentratorVariants implements IStringSerializable
	{
		CONCENTRATOR, DECONCENTRATOR;

		@Override
		public String getName()
		{
			return name().toLowerCase(Locale.ROOT);
		}

	}

	@Override
	public String getSpecialName(ItemStack stack)
	{
		return types[stack.getItemDamage()];
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world,
			BlockPos pos, EntityPlayer player)
	{
		return new ItemStack(Item.getItemFromBlock(this), 1,
				this.getMetaFromState(world.getBlockState(pos)));
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state,
			EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX,
			float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			TileWaterConcentrator tile = (TileWaterConcentrator) world.getTileEntity(pos);
			if (player.getHeldItemMainhand() == null && !player.isSneaking())
			{
				List<ITextComponent> toSend = new ArrayList();

				toSend.add(new TextComponentString(tile.concentrateMode == true ? "Concentrate" : "Deconcentrate"));
				toSend.add(new TextComponentString(TextFormatting.BLUE + "Water Stored: "
						+ tile.getTank().getFluidAmount() + " / " + tile.getTank().getCapacity()));
				if (tile.hasValidMultiblock())
					toSend.add(new TextComponentString(TextFormatting.GREEN + "Valid Multiblock!"));
				else
					toSend.add(new TextComponentString(TextFormatting.RED + "Invalid Multiblock."));

				for (ITextComponent text : toSend)
				{
					player.addChatComponentMessage(text);
				}

			}
		}
		return true;
	}
}
