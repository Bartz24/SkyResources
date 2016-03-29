package com.bartz24.skymod.registry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModCreativeTabs
{	
	
	public static CreativeTabs tabMain = new CreativeTabs("skymod.tabMain") {
	    @Override
	    @SideOnly(Side.CLIENT)
	    public Item getTabIconItem() {
	        return ModItems.ironKnife;
	    }
	};
	
	public static CreativeTabs tabAlchemy = new CreativeTabs("skymod.tabAlchemy") {
	    @Override
	    @SideOnly(Side.CLIENT)
	    public Item getTabIconItem() {
	        return ModItems.sandstoneInfusionStone;
	    }
	};
	
	public static CreativeTabs tabTech = new CreativeTabs("skymod.tabTech") {
	    @Override
	    @SideOnly(Side.CLIENT)
	    public Item getTabIconItem() {
	        return Item.getItemFromBlock(ModBlocks.combustionHeater);
	    }
	};
}
