package com.bartz24.skyresources.registry;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.ItemHelper;
import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.References;
import com.bartz24.skyresources.SkyResources;
import com.bartz24.skyresources.alchemy.item.AlchemyItemComponent;
import com.bartz24.skyresources.alchemy.item.ItemHealthRing;
import com.bartz24.skyresources.alchemy.item.ItemInfusionStone;
import com.bartz24.skyresources.alchemy.item.MetalCrystalItem;
import com.bartz24.skyresources.base.item.BaseItemComponent;
import com.bartz24.skyresources.base.item.ItemKnife;
import com.bartz24.skyresources.base.item.ItemWaterExtractor;
import com.bartz24.skyresources.base.item.ModItemFood;
import com.bartz24.skyresources.technology.item.ItemRockGrinder;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems
{
	public static Item alchemyComponent;
	public static Item metalCrystal;
	public static Item baseComponent;
	public static Item healthRing;

	public static Item cactusFruit;

	public static Item waterExtractor;

	public static Item cactusKnife;
	public static Item ironKnife;
	public static Item diamondKnife;

	public static Item stoneGrinder;
	public static Item ironGrinder;
	public static Item diamondGrinder;

	public static Item sandstoneInfusionStone;
	public static Item redSandstoneInfusionStone;

	public static List<Item> crystalFluidBuckets;

	public static void init()
	{
		crystalFluidBuckets = new ArrayList<Item>();
		alchemyComponent = registerItem(new AlchemyItemComponent());
		metalCrystal = registerItem(new MetalCrystalItem());
		baseComponent = registerItem(new BaseItemComponent());
		healthRing = registerItem(new ItemHealthRing());
		waterExtractor = registerItem(new ItemWaterExtractor());
		cactusFruit = registerItem(new ModItemFood(3, 2F, false, "cactusFruit",
				"CactusFruit"));
		cactusKnife = registerItem(new ItemKnife(SkyResources.materialCactusNeedle,
				"cactusCuttingKnife", "CactusCuttingKnife"));
		ironKnife = registerItem(new ItemKnife(ToolMaterial.IRON,
				"ironCuttingKnife", "IronCuttingKnife"));
		diamondKnife = registerItem(new ItemKnife(ToolMaterial.DIAMOND,
				"diamondCuttingKnife", "DiamondCuttingKnife"));

		stoneGrinder = registerItem(new ItemRockGrinder(ToolMaterial.STONE,
				"stoneGrinder", "StoneGrinder"));
		ironGrinder = registerItem(new ItemRockGrinder(ToolMaterial.IRON,
				"ironGrinder", "IronGrinder"));
		diamondGrinder = registerItem(new ItemRockGrinder(ToolMaterial.DIAMOND,
				"diamondGrinder", "DiamondGrinder"));

		sandstoneInfusionStone = registerItem(new ItemInfusionStone(100,
				"sandstoneInfusionStone", "SandstoneInfusionStone"));
		redSandstoneInfusionStone = registerItem(new ItemInfusionStone(80,
				"redSandstoneInfusionStone", "RedSandstoneInfusionStone"));

		for (int i = 0; i < ModFluids.crystalFluidNames().length; i++)
		{
			final int val = i;
			Item bucket = new ItemBucket(ModBlocks.crystalFluidBlocks.get(i))
			{
				public String getItemStackDisplayName(ItemStack stack)
				{
					String base = ("" + I18n
							.translateToLocal("name.skyresources.metal."
									+ ModFluids.crystalFluidNames()[val]))
							.trim();

					String type = ("" + I18n
							.translateToLocal("item.skyresources.crystalFluidBucket.name"))
							.trim();

					return base + " " + type;
				}

			};
			crystalFluidBuckets.add(registerItem(bucket
					.setUnlocalizedName(
							References.ModID + "."
									+ ModFluids.crystalFluidNames()[i]
									+ "CrystalFluidBucket")
					.setRegistryName(
							RandomHelper.capatilizeString(ModFluids
									.crystalFluidNames()[i])
									+ "CrystalFluidBucket")
					.setContainerItem(Items.bucket).setCreativeTab(ModCreativeTabs.tabAlchemy)));
		}

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
			SkyResources.instance.logger
					.error("Item {} doesn't have a registry name. Item will not be registered.",
							item.getClass().getCanonicalName());
			return item;
		}
		GameRegistry.registerItem(item);

		return item;
	}
}
