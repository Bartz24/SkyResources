package com.bartz24.skyresources.alchemy.item;

import java.util.ArrayList;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.registry.ModCreativeTabs;
import com.bartz24.skyresources.registry.ModItems;
import com.bartz24.skyresources.technology.item.GemRegisterInfo;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DirtyGemItem extends Item
{
	private static ArrayList<String> names = new ArrayList<String>();

	public DirtyGemItem()
	{
		super();

		setUnlocalizedName(References.ModID + ".dirtyGem.");
		setRegistryName("DirtyGem");
		setHasSubtypes(true);
		this.setCreativeTab(ModCreativeTabs.tabTech);

		itemList();
	}

	private void itemList()
	{
		for (GemRegisterInfo g : ModItems.gemList)
			names.add(g.name);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return super.getUnlocalizedName(stack) + names.get(stack.getItemDamage());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item id, CreativeTabs creativeTab, NonNullList<ItemStack> list)
	{
		for (int i = 0; i < names.size(); i++)
			list.add(new ItemStack(id, 1, i));
	}

	public static ItemStack getStack(String name)
	{
		return new ItemStack(ModItems.dirtyGem, 1, names.indexOf(name));
	}

	public static ArrayList<String> getNames()
	{
		return names;
	}
}
