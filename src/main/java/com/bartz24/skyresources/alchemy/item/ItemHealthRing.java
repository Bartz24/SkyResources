package com.bartz24.skyresources.alchemy.item;

import java.util.List;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.alchemy.effects.IHealthBoostItem;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.registry.ModCreativeTabs;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemHealthRing extends Item implements IHealthBoostItem
{
	private final int maxHealth = ConfigOptions.healthRingMaxHealth;

	public ItemHealthRing()
	{
		super();

		setUnlocalizedName(References.ModID + ".itemHealthRing");
		setRegistryName("ItemHealthRing");
		setHasSubtypes(true);
		this.setCreativeTab(ModCreativeTabs.tabAlchemy);
		this.setMaxStackSize(1);
	}

	@Override
	public int getHealthBoost(ItemStack stack)
	{
		return (int) (getCompound(stack).getInteger("health") * ConfigOptions.healthRingPercentage);
	}

	@Override
	public void onCreated(ItemStack itemStack, World world, EntityPlayer player)
	{
		itemStack.setTagCompound(new NBTTagCompound());
		itemStack.getTagCompound().setInteger("health", 0);
		itemStack.getTagCompound().setInteger("cooldown", 0);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack,
			World world, EntityPlayer player, EnumHand hand)
	{
		super.onItemRightClick(stack, world, player, hand);

		if (player.isSneaking())
		{
			if (getCompound(stack) != null)
			{
				if (stack.getTagCompound().getInteger("health") + 2 <= maxHealth
						&& stack.getTagCompound().getInteger("cooldown") == 0)
				{
					player.attackEntityFrom(DamageSource.generic, 2);
					stack.getTagCompound().setInteger("health",
							stack.getTagCompound().getInteger("health") + 2);
					stack.getTagCompound().setInteger("cooldown", 20);
				}
			} else
			{
				onCreated(stack, world, player);
			}
		}

		return new ActionResult(EnumActionResult.PASS, stack);
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack,
			ItemStack newStack, boolean slotChanged)
	{
		return false;
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player,
			List list, boolean par4)
	{
		if (itemStack.getTagCompound() != null)
		{
			list.add("Health Injected: "
					+ itemStack.getTagCompound().getInteger("health"));
		} else
			list.add("Health Injected: " + 0);
	}

	public NBTTagCompound getCompound(ItemStack stack)
	{
		NBTTagCompound com = stack.getTagCompound();
		if (com == null)
			onCreated(stack, null, null);
		com = stack.getTagCompound();

		return com;
	}
}
