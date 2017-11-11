package com.bartz24.skyresources.alchemy.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.config.ConfigOptions;
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

	public static Map<String, Integer> defaultOreRarities()
	{
		Map<String, Integer> map = new HashMap();
		map.put("iron", 3);
		map.put("gold", 5);
		map.put("copper", 1);
		map.put("tin", 3);
		map.put("silver", 4);
		map.put("zinc", 2);
		map.put("nickel", 5);
		map.put("platinum", 7);
		map.put("aluminum", 4);
		map.put("lead", 4);
		map.put("cobalt", 6);
		map.put("ardite", 6);
		map.put("osmium", 3);
		map.put("draconium", 9);
		map.put("titanium", 6);
		map.put("tungsten", 6);
		map.put("chrome", 8);
		map.put("iridium", 11);
		map.put("boron", 5);
		map.put("lithium", 7);
		map.put("magnesium", 5);
		map.put("mithril", 9);
		map.put("yellorium", 6);
		map.put("uranium", 6);
		map.put("thorium", 7);

		return map;
	}

	public static void init()
	{
		addOreInfo("iron", 0xFFCC0000); // 0
		addOreInfo("gold", 0xFFCCCC00); // 1
		addOreInfo("copper", 0xFFFF6600); // 2
		addOreInfo("tin", 0xFFBFBFBF); // 3
		addOreInfo("silver", 0xFFD1F4FF); // 4
		addOreInfo("zinc", 0xFFFFF7C2); // 5
		addOreInfo("nickel", 0xFFFAF191); // 6
		addOreInfo("platinum", 0xFF44EAFC); // 7
		addOreInfo("aluminum", 0xFFF5FFFD); // 8
		addOreInfo("lead", 0xFF5B2EFF); // 9
		addOreInfo("cobalt", 0xFF0045D9, new ItemStack(Blocks.NETHERRACK)); // 10
		addOreInfo("ardite", 0xFFDE9000, new ItemStack(Blocks.NETHERRACK)); // 11
		addOreInfo("osmium", 0xFF7F13C2); // 12
		addOreInfo("draconium", 0xFF9E6DCF, new ItemStack(Blocks.END_STONE), false); // 13
		addOreInfo("titanium", 0xFFBABABA); // 14
		addOreInfo("tungsten", 0xFF464659, new ItemStack(Blocks.END_STONE)); // 15
		addOreInfo("chrome", 0xFFD6D6D6); // 16
		addOreInfo("iridium", 0xFFE3E3E3); // 17
		addOreInfo("boron", 0xFF9E9E9E); // 18
		addOreInfo("lithium", 0xFFF2F2F2); // 19
		addOreInfo("magnesium", 0xFFFFD4D4); // 20
		addOreInfo("mithril", 0xFF45BCCC); // 21
		addOreInfo("yellorium", 0xFFFFFF2B, new ItemStack(Blocks.STONE), false); // 22
		addOreInfo("uranium", 0xFF16BA00, new ItemStack(Blocks.STONE), false); // 23
		addOreInfo("thorium", 0xFF2B4010, new ItemStack(Blocks.STONE), false); // 24
	}

	public static void addOreInfo(String name, int color)
	{
		addOreInfo(name, color, new ItemStack(Blocks.STONE));
	}

	public static void addOreInfo(String name, int color, ItemStack block)
	{
		addOreInfo(name, color, block, true);
	}

	public static void addOreInfo(String name, int color, ItemStack block, boolean autoAdd)
	{
		oreInfos.add(new OreRegisterInfo(name, color, ConfigOptions.alchemicalOreRarities.get(name), oreInfos.size(),
				block, autoAdd));
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
