package com.bartz24.skyresources.alchemy.block;

import java.util.List;

import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.References;
import com.bartz24.skyresources.alchemy.tile.PurificationVesselTile;
import com.bartz24.skyresources.registry.ModCreativeTabs;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PurificationVesselBlock extends BlockContainer
{
	public PurificationVesselBlock(String unlocalizedName, String registryName,
			float hardness, float resistance)
	{
		super(Material.glass);
		this.setUnlocalizedName(References.ModID + "." + unlocalizedName);
		this.setCreativeTab(ModCreativeTabs.tabAlchemy);
		this.setHardness(hardness);
		this.setResistance(resistance);
		this.setRegistryName(registryName);
		this.isBlockContainer = true;
		this.setStepSound(SoundType.GLASS);
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn,
			BlockPos pos, AxisAlignedBB p_185477_4_,
			List<AxisAlignedBB> p_185477_5_, Entity p_185477_6_)
	{
		addCollisionBoxToList(pos, p_185477_4_, p_185477_5_,
				new AxisAlignedBB(0.125D, 0D, 0.125D, 0.875D, 1.0D, 0.875D));
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source,
			BlockPos pos)
	{
		return new AxisAlignedBB(0.125D, 0D, 0.125D, 0.875D, 1.0D, 0.875D);
	}

	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
	{
		return true;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new PurificationVesselTile();
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos,
			IBlockState state, EntityPlayer player, EnumHand hand,
			ItemStack heldItem, EnumFacing side, float hitX, float hitY,
			float hitZ)
	{
		if (!world.isRemote)
		{
			if (player == null)
			{
				return false;
			}

			PurificationVesselTile tile = (PurificationVesselTile) world
					.getTileEntity(pos);
			ItemStack item = player.getHeldItem(hand);

			if (item != null && tile != null)
			{
				if (FluidContainerRegistry.isEmptyContainer(item))
				{
					ItemStack full = FluidContainerRegistry.fillFluidContainer(
							tile.getUpperTank().getFluid(), item);

					if (full != null)
					{
						if (player != null)
						{
							if (!player.capabilities.isCreativeMode)
							{
								if (item.stackSize > 1)
								{
									item.stackSize--;
									RandomHelper.spawnItemInWorld(world, full,
											player.getPosition());
								} else
								{
									player.setHeldItem(hand, full);
								}
							}
						}

						tile.drain(EnumFacing.UP, 1000, true);
						return true;
					}
					
					//drain lower
					
					full = FluidContainerRegistry.fillFluidContainer(
							tile.getLowerTank().getFluid(), item);

					if (full != null)
					{
						if (player != null)
						{
							if (!player.capabilities.isCreativeMode)
							{
								if (item.stackSize > 1)
								{
									item.stackSize--;
									RandomHelper.spawnItemInWorld(world, full,
											player.getPosition());
								} else
								{
									player.setHeldItem(hand, full);
								}
							}
						}

						tile.getLowerTank().drain(1000, true);
						return true;
					}
				} 
				else if (FluidContainerRegistry.isFilledContainer(item))
				{

					int capacity = tile.fill(EnumFacing.DOWN,
							FluidContainerRegistry.getFluidForFilledItem(item),
							false);

					if (capacity > 0)
					{
						tile.fill(EnumFacing.DOWN, FluidContainerRegistry
								.getFluidForFilledItem(item), true);

						if (!player.capabilities.isCreativeMode)
						{
							if (item.getItem() == Items.potionitem
									&& item.getItemDamage() == 0)
							{
								player.inventory.setInventorySlotContents(
										player.inventory.currentItem,
										new ItemStack(Items.glass_bottle, 1,
												0));
							} else
							{
								player.inventory.setInventorySlotContents(
										player.inventory.currentItem,
										FluidContainerRegistry
												.drainFluidContainer(item));
							}
						}
					}
				}

				ItemStack contents = item.copy();
				contents.stackSize = 1;
			}
			return true;
		}
		return true;
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
