package com.bartz24.skyresources.alchemy.item;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.registry.ModCreativeTabs;
import com.bartz24.skyresources.registry.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AlchemyItemComponent extends Item
{
	private static ArrayList<String> names = new ArrayList<String>();

	public static final String cactusNeedle = "cactusNeedle";
	public static final String cactusNeedleBloody = "cactusNeedleBloody";
	public static final String coalAlchemical = "coalAlchemical";
	public static final String dustAlchemical = "dustAlchemical";
	public static final String diamondAlchemical = "diamondAlchemical";
	public static final String goldIngotAlchemical = "goldIngotAlchemical";
	public static final String goldNeedleAlchemical = "goldNeedleAlchemical";

	public AlchemyItemComponent()
	{
		super();

		setUnlocalizedName(References.ModID + ".alchemyItemComponent.");
		setRegistryName("alchemyitemcomponent");
		setHasSubtypes(true);
		this.setCreativeTab(ModCreativeTabs.tabAlchemy);

		itemList();
	}

	private void itemList()
	{
		names.add(0, cactusNeedle);
		names.add(1, cactusNeedleBloody);
		names.add(2, coalAlchemical);
		names.add(3, dustAlchemical);
		names.add(4, diamondAlchemical);
		names.add(5, goldIngotAlchemical);
		names.add(6, goldNeedleAlchemical);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return super.getUnlocalizedName(stack)
				+ names.get(stack.getItemDamage());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item id, CreativeTabs tab, NonNullList<ItemStack> list)
	{
		for (int i = 0; i < names.size(); i++)
			list.add(new ItemStack(id, 1, i));
	}

	public static ItemStack getStack(String name)
	{
		return new ItemStack(ModItems.alchemyComponent, 1, names.indexOf(name));
	}

	public static ArrayList<String> getNames()
	{
		return names;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		super.onItemRightClick(world, player, hand);

		ItemStack stack = player.getHeldItem(hand);
		if (player.isSneaking())
		{
			if (stack.getMetadata() == names.indexOf(cactusNeedle))
			{
				stack.shrink(1);

				if (!player.inventory.addItemStackToInventory(
						new ItemStack(ModItems.alchemyComponent, 1,
								names.indexOf(cactusNeedleBloody))))
				{
					player.dropItem(
							new ItemStack(ModItems.alchemyComponent, 1,
									names.indexOf(cactusNeedleBloody)),
							false);
				}

				player.attackEntityFrom(DamageSource.CACTUS, 1);
			}
		}

		return new ActionResult(EnumActionResult.SUCCESS, stack);
	}
}
