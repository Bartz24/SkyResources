package com.bartz24.skyresources.alchemy.item;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.registry.ModCreativeTabs;
import com.bartz24.skyresources.registry.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemOreAlchDust extends Item
{

	public static List<OreRegisterInfo> oreInfos = new ArrayList();

	public static void init()
	{
		addOreInfo("iron", 0xFFCC0000, 3); //0
		addOreInfo("gold", 0xFFCCCC00, 5); //1
		addOreInfo("copper", 0xFFFF6600, 1); //2
		addOreInfo("tin", 0xFFBFBFBF, 3); //3
		addOreInfo("silver", 0xFFD1F4FF, 4); //4
		addOreInfo("zinc", 0xFFFFF7C2, 2); //5
		addOreInfo("nickel", 0xFFFAF191, 5); //6
		addOreInfo("platinum", 0xFF44EAFC, 7); //7
		addOreInfo("aluminum", 0xFFF5FFFD, 4); //8
		addOreInfo("lead", 0xFF5B2EFF, 4); //9
		addOreInfo("cobalt", 0xFF0045D9, 6, new ItemStack(Blocks.NETHERRACK)); //10
		addOreInfo("ardite", 0xFFDE9000, 6, new ItemStack(Blocks.NETHERRACK)); //11
		addOreInfo("osmium", 0xFF7F13C2, 3); //12
		addOreInfo("draconium", 0xFF9E6DCF, 9, new ItemStack(Blocks.END_STONE), false); //13
		addOreInfo("titanium", 0xFFBABABA, 6); //14
		addOreInfo("tungsten", 0xFF464659, 6, new ItemStack(Blocks.END_STONE)); //15
		addOreInfo("chrome", 0xFFD6D6D6, 8); //16
		addOreInfo("iridium", 0xFFE3E3E3, 11); //17
		addOreInfo("boron", 0xFF9E9E9E, 5); //18
		addOreInfo("lithium", 0xFFF2F2F2, 7); //19
		addOreInfo("magnesium", 0xFFFFD4D4, 5); //20
		addOreInfo("mithril", 0xFF45BCCC, 9); //21
		addOreInfo("yellorium", 0xFFFFFF2B, 6, new ItemStack(Blocks.STONE), false); //22
		addOreInfo("uranium", 0xFF16BA00, 6, new ItemStack(Blocks.STONE), false); //23
		addOreInfo("thorium", 0xFF2B4010, 7, new ItemStack(Blocks.STONE), false); //24
	}

	public static void addOreInfo(String name, int color, int rarity)
	{
		addOreInfo(name, color, rarity, new ItemStack(Blocks.STONE));
	}

	public static void addOreInfo(String name, int color, int rarity, ItemStack block)
	{
		addOreInfo(name, color, rarity, block, true);
	}

	public static void addOreInfo(String name, int color, int rarity, ItemStack block, boolean autoAdd)
	{
		oreInfos.add(new OreRegisterInfo(name, color, rarity, oreInfos.size(), block, autoAdd));
	}

	public static OreRegisterInfo getFluidInfo(int index)
	{
		for (OreRegisterInfo f : oreInfos)
		{
			if (f.dustIndex == index)
			{
				return f;
			}
		}
		return null;
	}

	private static ArrayList<String> names = new ArrayList<String>();

	public ItemOreAlchDust()
	{
		setUnlocalizedName(References.ModID + ".oreAlchDust.");
		setRegistryName("OreAlchDust");
		setHasSubtypes(true);
		this.setCreativeTab(ModCreativeTabs.tabAlchemy);

		itemList();
	}

	private void itemList()
	{
		if (names.size() == 0)
			for (int i = 0; i < oreInfos.size(); i++)
			{
				names.add(oreInfos.get(i).name);
			}
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
		return new ItemStack(ModItems.oreAlchDust, 1, names.indexOf(name));
	}

	public static ArrayList<String> getNames()
	{
		return names;
	}
}
