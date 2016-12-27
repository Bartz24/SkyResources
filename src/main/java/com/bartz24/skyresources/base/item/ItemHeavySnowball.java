package com.bartz24.skyresources.base.item;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.base.entity.EntityHeavySnowball;
import com.bartz24.skyresources.registry.ModCreativeTabs;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemHeavySnowball extends Item
{
	public ItemHeavySnowball(String unlocalizedName, String registryName)
	{
		this.maxStackSize = 4;
		this.setUnlocalizedName(References.ModID + "." + unlocalizedName);
		setRegistryName(registryName);
		this.setCreativeTab(ModCreativeTabs.tabMain);
	}

	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn,
			World worldIn, EntityPlayer playerIn, EnumHand hand)
	{
		if (!playerIn.capabilities.isCreativeMode)
		{
			itemStackIn.shrink(1);
		}

		worldIn.playSound((EntityPlayer) null, playerIn.posX, playerIn.posY,
				playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW,
				SoundCategory.NEUTRAL, 0.5F,
				0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!worldIn.isRemote)
		{
			EntityHeavySnowball entitysnowball = new EntityHeavySnowball(
					worldIn, playerIn);
			entitysnowball.setHeadingFromThrower(playerIn, playerIn.rotationPitch,
					playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
			worldIn.spawnEntity(entitysnowball);
		}

		return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
	}

}
