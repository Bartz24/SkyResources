package com.bartz24.skyresources.technology.block;

import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.References;
import com.bartz24.skyresources.SkyResources;
import com.bartz24.skyresources.registry.ModCreativeTabs;
import com.bartz24.skyresources.registry.ModGuiHandler;
import com.bartz24.skyresources.technology.tile.TileWildlifeAttractor;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidUtil;

public class BlockWildlifeAttractor extends BlockContainer
{

	public BlockWildlifeAttractor(String unlocalizedName, String registryName, float hardness, float resistance)
	{
		super(Material.GRASS);
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
		return new TileWildlifeAttractor();
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		TileWildlifeAttractor te = (TileWildlifeAttractor) world.getTileEntity(pos);
		te.dropInventory();

		super.breakBlock(world, pos, state);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			if (!player.getHeldItem(hand).isEmpty() && FluidUtil.getFluidContained(player.getHeldItem(hand)) != null
					&& FluidUtil.getFluidContained(player.getHeldItem(hand)).getFluid() == FluidRegistry.WATER)
			{
				TileWildlifeAttractor tile = (TileWildlifeAttractor) world.getTileEntity(pos);
				FluidActionResult result = FluidUtil
				.tryEmptyContainer(player.getHeldItem(hand), tile.getTank(), 1000, player, true);
				
				if (result.success)
				{
					if (player.getHeldItem(hand).getCount() > 1)
					{
						player.getHeldItem(hand).shrink(1);
						RandomHelper.spawnItemInWorld(world, result.getResult(), player.getPosition());
					} else
					{
						player.setHeldItem(hand, result.getResult());
					}
					return true;
				}

				ItemStack contents = player.getHeldItem(hand).copy();
				contents.setCount(1);
				return true;
			}

			player.openGui(SkyResources.instance, ModGuiHandler.WildlifeAttractorGUI, world, pos.getX(), pos.getY(),
					pos.getZ());
		}
		return true;
	}
}
