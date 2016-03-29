package com.bartz24.skymod.alchemy.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.bartz24.skymod.References;
import com.bartz24.skymod.registry.ModCreativeTabs;
import com.bartz24.skymod.registry.ModFluids;
import com.bartz24.skymod.registry.ModItems;

public class MetalCrystalItem extends Item
{
	private static ArrayList<String> names = new ArrayList<String>();

	public MetalCrystalItem()
	{
		super();

		setUnlocalizedName(References.ModID + ".metalCrystal.");
		setRegistryName("MetalCrystal");
		setHasSubtypes(true);
		this.setCreativeTab(ModCreativeTabs.tabAlchemy);

		itemList();
	}

	private void itemList()
	{
		names.addAll(Arrays.asList(ModFluids.crystalFluidNames()));
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
		return new ItemStack(ModItems.metalCrystal, 1, names.indexOf(name));
	}

	public static ArrayList<String> getNames()
	{
		return names;
	}

	public String getItemStackDisplayName(ItemStack stack)
	{
		String base = ("" + I18n.translateToLocal("name.skymod.metal."
				+ ModFluids.crystalFluidNames()[stack.getMetadata()])).trim();
		
		String type = ("" + I18n.translateToLocal("item.skymod.metalCrystal.name")).trim();
		
		return base + " " + type;
	}
}
