package com.bartz24.skyresources.technology.item;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.registry.ModCreativeTabs;
import com.bartz24.skyresources.registry.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TechItemComponent extends Item
{
	private static ArrayList<String> names = new ArrayList<String>();

	public static final String stoneCrushed = "stoneCrushed";
	public static final String radioactiveMix = "radioactiveMix";

	public TechItemComponent()
	{
		super();

		setUnlocalizedName(References.ModID + ".techItemComponent.");
		setRegistryName("TechItemComponent");
		setHasSubtypes(true);
		this.setCreativeTab(ModCreativeTabs.tabTech);

		itemList();
	}

	private void itemList()
	{
		names.add(0, stoneCrushed);
		names.add(1, radioactiveMix);
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
		return new ItemStack(ModItems.techComponent, 1, names.indexOf(name));
	}

	public static ArrayList<String> getNames()
	{
		return names;
	}
}
