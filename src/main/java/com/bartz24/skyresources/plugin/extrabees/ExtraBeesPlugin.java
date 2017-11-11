package com.bartz24.skyresources.plugin.extrabees;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.base.guide.SkyResourcesGuide;
import com.bartz24.skyresources.plugin.IModPlugin;

import binnie.extrabees.blocks.type.EnumHiveType;
import forestry.api.apiculture.IHiveDrop;
import forestry.apiculture.ModuleApiculture;
import net.minecraft.item.ItemStack;

public class ExtraBeesPlugin implements IModPlugin
{

	public void preInit()
	{

	}

	public void init()
	{
		SkyResourcesGuide.addPage("extrabees", "guide.skyresources.misc",
				new ItemStack(ModuleApiculture.getItems().beeQueenGE));
	}

	public void postInit()
	{

	}

	public void initRenderers()
	{

	}

	public List<IHiveDrop> getAllHiveDrops()
	{
		List<IHiveDrop> drops = new ArrayList();
		
		drops.addAll(EnumHiveType.WATER.getDrops());
		drops.addAll(EnumHiveType.ROCK.getDrops());
		drops.addAll(EnumHiveType.MARBLE.getDrops());
		drops.addAll(EnumHiveType.NETHER.getDrops());
		
		return drops;
	}
}
