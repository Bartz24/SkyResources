package com.bartz24.skyresources.registry;

import java.util.ArrayList;

import com.bartz24.skyresources.SkyResources;
import com.bartz24.skyresources.alchemy.item.AlchemyItemComponent;
import com.bartz24.skyresources.alchemy.item.DirtyGemItem;
import com.bartz24.skyresources.alchemy.item.ItemHealthRing;
import com.bartz24.skyresources.alchemy.item.ItemInfusionStone;
import com.bartz24.skyresources.alchemy.item.MetalCrystalItem;
import com.bartz24.skyresources.base.item.BaseItemComponent;
import com.bartz24.skyresources.base.item.ItemHeavySnowball;
import com.bartz24.skyresources.base.item.ItemKnife;
import com.bartz24.skyresources.base.item.ItemWaterExtractor;
import com.bartz24.skyresources.base.item.ModItemFood;
import com.bartz24.skyresources.technology.item.GemRegisterInfo;
import com.bartz24.skyresources.technology.item.ItemRockGrinder;
import com.bartz24.skyresources.technology.item.TechItemComponent;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems
{
	public static Item alchemyComponent;
	public static Item metalCrystal;
	public static Item baseComponent;
	public static Item techComponent;
	public static Item healthRing;

	public static Item cactusFruit;
	public static Item fleshySnowNugget;

	public static Item waterExtractor;

	public static Item heavySnowball;

	public static Item dirtyGem;

	public static Item cactusKnife;
	public static Item ironKnife;
	public static Item diamondKnife;

	public static Item stoneGrinder;
	public static Item ironGrinder;
	public static Item diamondGrinder;

	public static Item sandstoneInfusionStone;
	public static Item redSandstoneInfusionStone;
	public static Item alchemicalInfusionStone;

	public static ArrayList<GemRegisterInfo> gemList = new ArrayList<GemRegisterInfo>();

	public static void init()
	{
		ModItems.addGem("emerald", 0xFF12DB3A, 0.005F);
		ModItems.addGem("ruby", 0xFFFA1E1E, 0.005F);
		ModItems.addGem("sapphire", 0xFF1E46FA, 0.005F);
		ModItems.addGem("peridot", 0xFF1CB800, 0.005F);
		ModItems.addGem("redGarnet", 0xFFC90014, 0.005F);
		ModItems.addGem("yellowGarnet", 0xFFF7FF0F, 0.005F);
		ModItems.addGem("apatite", 0xFF2B95FF, 0.200F);

		alchemyComponent = registerItem(new AlchemyItemComponent());
		metalCrystal = registerItem(new MetalCrystalItem());
		dirtyGem = registerItem(new DirtyGemItem());
		baseComponent = registerItem(new BaseItemComponent());
		techComponent = registerItem(new TechItemComponent());
		healthRing = registerItem(new ItemHealthRing());
		waterExtractor = registerItem(new ItemWaterExtractor());
		heavySnowball = registerItem(new ItemHeavySnowball("heavySnowball", "HeavySnowball"));
		cactusFruit = registerItem(new ModItemFood(3, 2F, false, "cactusFruit", "CactusFruit"));
		fleshySnowNugget = registerItem(
				new ModItemFood(4, 1.5F, false, "fleshySnowNugget", "FleshySnowNugget"));
		cactusKnife = registerItem(new ItemKnife(SkyResources.materialCactusNeedle,
				"cactusCuttingKnife", "CactusCuttingKnife"));
		ironKnife = registerItem(
				new ItemKnife(ToolMaterial.IRON, "ironCuttingKnife", "IronCuttingKnife"));
		diamondKnife = registerItem(
				new ItemKnife(ToolMaterial.DIAMOND, "diamondCuttingKnife", "DiamondCuttingKnife"));

		stoneGrinder = registerItem(
				new ItemRockGrinder(ToolMaterial.STONE, "stoneGrinder", "StoneGrinder"));
		ironGrinder = registerItem(
				new ItemRockGrinder(ToolMaterial.IRON, "ironGrinder", "IronGrinder"));
		diamondGrinder = registerItem(
				new ItemRockGrinder(ToolMaterial.DIAMOND, "diamondGrinder", "DiamondGrinder"));

		sandstoneInfusionStone = registerItem(
				new ItemInfusionStone(100, "sandstoneInfusionStone", "SandstoneInfusionStone"));
		redSandstoneInfusionStone = registerItem(new ItemInfusionStone(80,
				"redSandstoneInfusionStone", "RedSandstoneInfusionStone"));
		alchemicalInfusionStone = registerItem(
				new ItemInfusionStone(1500, "alchemicalInfusionStone", "AlchemicalInfusionStone"));
	}

	private static Item registerItem(Item item, String name)
	{
		GameRegistry.registerItem(item, name);

		return item;
	}

	private static Item registerItem(Item item)
	{
		if (item.getRegistryName() == null)
		{
			SkyResources.logger.error(
					"Item {} doesn't have a registry name. Item will not be registered.",
					item.getClass().getCanonicalName());
			return item;
		}
		GameRegistry.registerItem(item);

		return item;
	}

	public static void addGem(String name, int color, float rarity)
	{
		gemList.add(new GemRegisterInfo(name, color, rarity));
	}
}
