package com.bartz24.skyresources.registry;

import java.util.ArrayList;

import com.bartz24.skyresources.SkyResources;
import com.bartz24.skyresources.alchemy.item.AlchemyItemComponent;
import com.bartz24.skyresources.alchemy.item.DirtyGemItem;
import com.bartz24.skyresources.alchemy.item.ItemHealthGem;
import com.bartz24.skyresources.alchemy.item.ItemInfusionStone;
import com.bartz24.skyresources.alchemy.item.MetalCrystalItem;
import com.bartz24.skyresources.base.item.BaseItemComponent;
import com.bartz24.skyresources.base.item.ItemHeavyExplosiveSnowball;
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
	public static Item healthGem;

	public static Item cactusFruit;
	public static Item fleshySnowNugget;

	public static Item waterExtractor;

	public static Item heavySnowball;
	public static Item heavyExpSnowball;

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
		ModItems.addGem("amber", 0xFFF5CC53, 0.007F);
		ModItems.addGem("lepidolite", 0xFF57008A, 0.007F);
		ModItems.addGem("malachite", 0xFF23AD00, 0.007F);
		ModItems.addGem("onyx", 0xFF3D3D3D, 0.007F);
		ModItems.addGem("moldavite", 0xFFADFF99, 0.007F);
		ModItems.addGem("agate", 0xFFFF63FF, 0.007F);
		ModItems.addGem("opal", 0xFFDEDEDE, 0.007F);
		ModItems.addGem("amethyst", 0xFF780078, 0.006F);
		ModItems.addGem("jasper", 0xFF874800, 0.006F);
		ModItems.addGem("aquamarine", 0xFF36E7FF, 0.006F);
		ModItems.addGem("heliodor", 0xFFFFFF7D, 0.006F);
		ModItems.addGem("turquoise", 0xFF2EF2C8, 0.006F);
		ModItems.addGem("moonstone", 0xFF016A8A, 0.006F);
		ModItems.addGem("morganite", 0xFFFA61FF, 0.006F);
		ModItems.addGem("carnelian", 0xFF630606, 0.006F);
		ModItems.addGem("beryl", 0xFF46E334, 0.005F);
		ModItems.addGem("goldenBeryl", 0xFFD6AE2B, 0.005F);
		ModItems.addGem("citrine", 0xFF871616, 0.005F);
		ModItems.addGem("indicolite", 0xFF39E6BD, 0.005F);
		ModItems.addGem("garnet", 0xFFFF9999, 0.005F);
		ModItems.addGem("topaz", 0xFFFFD399, 0.005F);
		ModItems.addGem("ametrine", 0xFFA300BF, 0.005F);
		ModItems.addGem("tanzanite", 0xFF00076E, 0.005F);
		ModItems.addGem("violetSapphire", 0xFF451287, 0.004F);
		ModItems.addGem("alexandrite", 0xFFE3E3E3, 0.004F);
		ModItems.addGem("blueTopaz", 0xFF1000C4, 0.004F);
		ModItems.addGem("spinel", 0xFF750000, 0.004F);
		ModItems.addGem("iolite", 0xFF9502CF, 0.004F);
		ModItems.addGem("blackDiamond", 0xFF262626, 0.003F);
		ModItems.addGem("chaos", 0xFFFFE6FB, 0.003F);
		ModItems.addGem("enderEssence", 0xFF356E19, 0.003F);

		alchemyComponent = registerItem(new AlchemyItemComponent());
		metalCrystal = registerItem(new MetalCrystalItem());
		dirtyGem = registerItem(new DirtyGemItem());
		baseComponent = registerItem(new BaseItemComponent());
		techComponent = registerItem(new TechItemComponent());
		healthGem = registerItem(new ItemHealthGem());
		waterExtractor = registerItem(new ItemWaterExtractor());
		heavySnowball = registerItem(new ItemHeavySnowball("heavySnowball", "HeavySnowball"));
		heavyExpSnowball = registerItem(new ItemHeavyExplosiveSnowball("heavyExplosiveSnowball", "HeavyExplosiveSnowball"));
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
