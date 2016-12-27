package com.bartz24.skyresources.alchemy.item;

import java.util.ArrayList;
import java.util.Arrays;

import com.bartz24.skyresources.ItemHelper;
import com.bartz24.skyresources.References;
import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;
import com.bartz24.skyresources.registry.ModCreativeTabs;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemInfusionStone extends Item
{
	public ItemInfusionStone(int durability, String unlocalizedName, String registryName)
	{
		this.setMaxDamage(durability);
		this.setUnlocalizedName(References.ModID + "." + unlocalizedName);
		setRegistryName(registryName);
		this.setMaxStackSize(1);
		this.setNoRepair();
		this.setCreativeTab(ModCreativeTabs.tabAlchemy);

		ItemHelper.addInfusionStone(this);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side,
			float hitX, float hitY, float hitZ)
	{
		super.onItemUse(player, world, pos, hand, side, hitX, hitY, hitZ);
		ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);		
		Block block = world.getBlockState(pos).getBlock();

		ItemStack offHand = player.getHeldItemOffhand();

		ProcessRecipe recipe = ProcessRecipeManager.infusionRecipes.getRecipe(
				new ArrayList<Object>(Arrays.asList((Object) offHand,
						(Object) new ItemStack(block, 1, block.getMetaFromState(world.getBlockState(pos))))),
				player.getHealth(), false, false);

		if (recipe != null && recipe.getOutputs().get(0) != ItemStack.EMPTY)
		{
			if (player.getMaxHealth() < recipe.getIntParameter())
			{
				if (world.isRemote)
					player.sendMessage(new TextComponentString(
							"You are not strong enough to infuse. Your max health is too low."));
			}
			if (player.getHealth() >= recipe.getIntParameter())
			{
				if (!world.isRemote)
				{
					player.attackEntityFrom(DamageSource.MAGIC, recipe.getIntParameter());
					world.setBlockToAir(pos);
					player.dropItem(recipe.getOutputs().get(0).copy(), false);
					if (offHand != ItemStack.EMPTY)
						offHand.shrink(recipe.getInputs().get(0) instanceof ItemStack
								? ((ItemStack) recipe.getInputs().get(0)).getCount() : 1);

					stack.damageItem(1, player);
				}
			} else
			{
				if (world.isRemote)
					player.sendMessage(new TextComponentString("Not enough health to infuse."));
			}
		}

		if (world.isRemote)
			player.swingArm(EnumHand.MAIN_HAND);

		return EnumActionResult.PASS;
	}
}
