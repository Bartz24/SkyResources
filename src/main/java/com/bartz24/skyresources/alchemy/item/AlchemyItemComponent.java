package com.bartz24.skyresources.alchemy.item;

import java.util.ArrayList;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.registry.ModCreativeTabs;
import com.bartz24.skyresources.registry.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AlchemyItemComponent extends Item
{
	private static ArrayList<String> names = new ArrayList<String>();

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
		names.add(0, "cactusNeedle");
		names.add(1, "crystalShard");
		names.add(2, "alchDust1");
		names.add(3, "alchDust2");
		names.add(4, "alchDust3");
		names.add(5, "alchDust4");
		names.add(6, "alchCoal");
		names.add(7, "alchGoldIngot");
		names.add(8, "alchIronIngot");
		names.add(9, "alchGoldNeedle");
		names.add(10, "alchDiamond");
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return super.getUnlocalizedName(stack) + names.get(stack.getItemDamage());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list)
	{
		if (isInCreativeTab(tab))
			for (int i = 0; i < names.size(); i++)
				list.add(new ItemStack(this, 1, i));
	}

	public static ItemStack getStack(String name)
	{
		return new ItemStack(ModItems.alchemyComponent, 1, names.indexOf(name));
	}

	public static ArrayList<String> getNames()
	{
		return names;
	}
}
