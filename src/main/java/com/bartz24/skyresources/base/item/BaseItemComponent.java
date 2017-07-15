package com.bartz24.skyresources.base.item;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.registry.ModCreativeTabs;
import com.bartz24.skyresources.registry.ModItems;

import net.minecraft.block.IGrowable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseItemComponent extends Item
{
	private static ArrayList<String> names = new ArrayList<String>();

	public static final String plantMatter = "plantMatter";
	public static final String steelPowerComp = "steelPowerComponent";
	public static final String frozenIronComp = "frozenIronCoolingComponent";
	public static final String darkMatter = "darkMatter";
	public static final String enrichedBonemeal = "enrichedBonemeal";
	public static final String sawdust = "sawdust";
	public static final String quartzAmp = "quartzAmp";

	public BaseItemComponent()
	{
		super();

		setUnlocalizedName(References.ModID + ".baseItemComponent.");
		setRegistryName("baseitemcomponent");
		setHasSubtypes(true);
		this.setCreativeTab(ModCreativeTabs.tabMain);

		itemList();
	}

	private void itemList()
	{
		names.add(0, plantMatter);
		names.add(1, steelPowerComp);
		names.add(2, frozenIronComp);
		names.add(3, darkMatter);
		names.add(4, enrichedBonemeal);
		names.add(5, sawdust);
		names.add(6, quartzAmp);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return super.getUnlocalizedName(stack) + names.get(stack.getItemDamage());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs creativeTab, NonNullList<ItemStack> list)
	{
		if (isInCreativeTab(creativeTab))
			for (int i = 0; i < names.size(); i++)
				list.add(new ItemStack(this, 1, i));
	}

	public static ItemStack getStack(String name)
	{
		return new ItemStack(ModItems.baseComponent, 1, names.indexOf(name));
	}

	public static ArrayList<String> getNames()
	{
		return names;
	}

	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (!playerIn.canPlayerEdit(pos.offset(facing), facing, playerIn.getHeldItem(hand)))
		{
			return EnumActionResult.FAIL;
		} else
		{
			if (playerIn.getHeldItem(hand).getMetadata() == names.indexOf(plantMatter)
					|| playerIn.getHeldItem(hand).getMetadata() == names.indexOf(enrichedBonemeal))
			{
				if (applyBonemeal(playerIn.getHeldItem(hand), worldIn, pos, playerIn))
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

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if (stack.getMetadata() == names.indexOf(plantMatter))
		{
			tooltip.add(TextFormatting.GRAY + "Acts as bone meal");
			tooltip.add(TextFormatting.GRAY + "Grows instantly");
		} else if (stack.getMetadata() == names.indexOf(enrichedBonemeal))
		{
			tooltip.add(TextFormatting.GRAY + "Grows instantly");
		}
	}

	public static boolean applyBonemeal(ItemStack stack, World worldIn, BlockPos target, EntityPlayer player)
	{

		if (worldIn.getBlockState(target).getBlock() instanceof IGrowable)
		{
			int tries = 100;
			while (worldIn.getBlockState(target).getBlock() instanceof IGrowable && tries > 0)
			{
				tries--;
				IGrowable igrowable = (IGrowable) worldIn.getBlockState(target).getBlock();
				igrowable.grow(worldIn, worldIn.rand, target, worldIn.getBlockState(target));
			}

			stack.shrink(1);

			return true;
		}

		return false;
	}
}
