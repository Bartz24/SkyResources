package com.bartz24.skyresources.base.item;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.registry.ModCreativeTabs;
import com.bartz24.skyresources.registry.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseItemComponent extends Item
{
	private static ArrayList<String> names = new ArrayList<String>();

	public static final String woodHeatComp = "woodHeatComponent";
	public static final String ironHeatComp = "ironHeatComponent";
	public static final String plantMatter = "plantMatter";

	public BaseItemComponent()
	{
		super();

		setUnlocalizedName(References.ModID + ".baseItemComponent.");
		setRegistryName("BaseItemComponent");
		setHasSubtypes(true);
		this.setCreativeTab(ModCreativeTabs.tabMain);

		itemList();
	}

	private void itemList()
	{
		names.add(0, woodHeatComp);
		names.add(1, ironHeatComp);
		names.add(2, plantMatter);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return super.getUnlocalizedName(stack)
				+ names.get(stack.getItemDamage());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item id, CreativeTabs creativeTab,
			List<ItemStack> list)
	{
		for (int i = 0; i < names.size(); i++)
			list.add(new ItemStack(id, 1, i));
	}

	public static ItemStack getStack(String name)
	{
		return new ItemStack(ModItems.baseComponent, 1, names.indexOf(name));
	}

	public static ArrayList<String> getNames()
	{
		return names;
	}

	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn,
			World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing,
			float hitX, float hitY, float hitZ)
	{
		if (!playerIn.canPlayerEdit(pos.offset(facing), facing, stack))
		{
			return EnumActionResult.FAIL;
		} else
		{
			if (stack.getMetadata() == names.indexOf(plantMatter))
			{
				if (applyBonemeal(stack, worldIn, pos, playerIn))
				{
					if (!worldIn.isRemote)
					{
						worldIn.playEvent(2005, pos, 0);
					}

					return EnumActionResult.SUCCESS;
				}
			}

			return EnumActionResult.PASS;
		}
	}

	public static boolean applyBonemeal(ItemStack stack, World worldIn,
			BlockPos target)
	{
		if (worldIn instanceof net.minecraft.world.WorldServer)
			return applyBonemeal(stack, worldIn, target,
					net.minecraftforge.common.util.FakePlayerFactory
							.getMinecraft(
									(net.minecraft.world.WorldServer) worldIn));
		return false;
	}

	public static boolean applyBonemeal(ItemStack stack, World worldIn,
			BlockPos target, EntityPlayer player)
	{
		IBlockState iblockstate = worldIn.getBlockState(target);

		int hook = net.minecraftforge.event.ForgeEventFactory
				.onApplyBonemeal(player, worldIn, target, iblockstate, stack);
		if (hook != 0)
			return hook > 0;

		if (iblockstate.getBlock() instanceof IGrowable)
		{
			IGrowable igrowable = (IGrowable) iblockstate.getBlock();

			if (igrowable.canGrow(worldIn, target, iblockstate,
					worldIn.isRemote))
			{
				if (!worldIn.isRemote)
				{
					if (igrowable.canUseBonemeal(worldIn, worldIn.rand, target,
							iblockstate))
					{
						igrowable.grow(worldIn, worldIn.rand, target,
								iblockstate);
					}

					--stack.stackSize;
				}

				return true;
			}
		}

		return false;
	}

	@SideOnly(Side.CLIENT)
	public static void spawnBonemealParticles(World worldIn, BlockPos pos,
			int amount)
	{
		if (amount == 0)
		{
			amount = 15;
		}

		IBlockState iblockstate = worldIn.getBlockState(pos);

		if (iblockstate.getMaterial() != Material.AIR)
		{
			for (int i = 0; i < amount; ++i)
			{
				double d0 = itemRand.nextGaussian() * 0.02D;
				double d1 = itemRand.nextGaussian() * 0.02D;
				double d2 = itemRand.nextGaussian() * 0.02D;
				worldIn.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY,
						(double) ((float) pos.getX() + itemRand.nextFloat()),
						(double) pos.getY() + (double) itemRand.nextFloat()
								* iblockstate.getBoundingBox(worldIn, pos).maxY,
						(double) ((float) pos.getZ() + itemRand.nextFloat()),
						d0, d1, d2, new int[0]);
			}
		} else
		{
			for (int i1 = 0; i1 < amount; ++i1)
			{
				double d0 = itemRand.nextGaussian() * 0.02D;
				double d1 = itemRand.nextGaussian() * 0.02D;
				double d2 = itemRand.nextGaussian() * 0.02D;
				worldIn.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY,
						(double) ((float) pos.getX() + itemRand.nextFloat()),
						(double) pos.getY()
								+ (double) itemRand.nextFloat() * 1.0f,
						(double) ((float) pos.getZ() + itemRand.nextFloat()),
						d0, d1, d2, new int[0]);
			}
		}
	}

	public void addInformation(ItemStack stack, EntityPlayer par2EntityPlayer,
			List list, boolean par4)
	{
		if (stack.getMetadata() == names.indexOf(plantMatter))
			list.add(TextFormatting.DARK_GRAY + "Acts as bonemeal");
	}
}
