package com.bartz24.skyresources.alchemy.item;

import com.bartz24.skyresources.ItemHelper;
import com.bartz24.skyresources.References;
import com.bartz24.skyresources.alchemy.infusion.InfusionRecipe;
import com.bartz24.skyresources.alchemy.infusion.InfusionRecipes;
import com.bartz24.skyresources.registry.ModAchievements;
import com.bartz24.skyresources.registry.ModCreativeTabs;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
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
	public ItemInfusionStone(int durability, String unlocalizedName,
			String registryName)
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
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player,
			World world, BlockPos pos, EnumHand hand, EnumFacing side,
			float hitX, float hitY, float hitZ)
	{
		super.onItemUse(stack, player, world, pos, hand, side, hitX, hitY,
				hitZ);

		Block block = world.getBlockState(pos).getBlock();

		ItemStack offHand = player.getHeldItemOffhand();

		InfusionRecipe recipe = InfusionRecipes.getRecipe(offHand, block,
				block.getMetaFromState(world.getBlockState(pos)));

		if (recipe != null && recipe.getOutput() != null)
		{
			if (player.getMaxHealth() < recipe.getHealthReq())
			{
				if (world.isRemote)
					player.addChatMessage(new TextComponentString(
							"You are not strong enough to infuse. Your max health is too low."));
			}
			if (player.getHealth() >= recipe.getHealthReq())
			{
				if (!world.isRemote)
				{
					if (recipe.getOutput().getItem() == Item
							.getItemFromBlock(Blocks.SAPLING))
						player.addStat(ModAchievements.firstSapling, 1);

					player.attackEntityFrom(DamageSource.magic,
							recipe.getHealthReq());
					world.setBlockToAir(pos);
					player.dropItem(recipe.getOutput().copy(), false);
					if (offHand != null)
						offHand.stackSize -= recipe.getInputStack().stackSize;

					stack.damageItem(1, player);
				}
			} else
			{
				if (world.isRemote)
					player.addChatMessage(new TextComponentString(
							"Not enough health to infuse."));
			}
		}

		if (world.isRemote)
			player.swingArm(EnumHand.MAIN_HAND);

		return EnumActionResult.PASS;
	}
}
