package com.bartz24.skymod.registry;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.bartz24.skymod.RandomHelper;
import com.bartz24.skymod.SkyMod;
import com.bartz24.skymod.alchemy.block.CondenserBlock;
import com.bartz24.skymod.alchemy.fluid.FluidCrystalBlock;
import com.bartz24.skymod.base.block.BaseBlock;
import com.bartz24.skymod.base.block.BlazePowderBlock;
import com.bartz24.skymod.base.block.BlockDryCactus;
import com.bartz24.skymod.base.block.ItemBlockMeta;
import com.bartz24.skymod.base.block.TransparentBlock;
import com.bartz24.skymod.technology.block.CombustionHeaterBlock;

public class ModBlocks
{
	public static Block cactusFruitNeedle;

	public static Block combustionHeater;
	public static Block alchemicalCondenser;

	public static Block compressedCoalBlock;
	public static Block compressedCoalBlock2;
	public static Block blazePowderBlock;

	public static Block dryCactus;

	public static List<Block> crystalFluidBlocks;

	public static void init()
	{
		crystalFluidBlocks = new ArrayList<Block>();
		cactusFruitNeedle = registerBlock(new TransparentBlock(Material.plants,
				"cactusFruitNeedle", "CactusFruitNeedle", 0.5F, 0.5F,
				new AxisAlignedBB(0.3D, 0D, 0.3D, 0.7D, 0.8D, 0.7D),
				SoundType.PLANT));

		compressedCoalBlock = registerBlock(new BaseBlock(Material.rock,
				"compressedCoalBlock", "CompressedCoalBlock", 6F, 6F,
				SoundType.STONE));

		compressedCoalBlock2 = registerBlock(new BaseBlock(Material.rock,
				"compressedCoalBlock2", "CompressedCoalBlock2", 9F, 9F,
				SoundType.STONE));

		blazePowderBlock = registerBlock(new BlazePowderBlock(Material.clay,
				"blazePowderBlock", "BlazePowderBlock", 0.5F, 0.5F,
				SoundType.GROUND));

		alchemicalCondenser = registerBlock(new CondenserBlock(
				"alchemicalCondenser", "AlchemicalCondenser", 2F, 12F));

		GameRegistry.registerBlock(
				combustionHeater = new CombustionHeaterBlock(
						"combustionHeater", "CombustionHeater", 2F, 12F),
				ItemBlockMeta.class, "combustionHeater");
		dryCactus = registerBlock(new BlockDryCactus());

		for (int i = 0; i < ModFluids.crystalFluidNames().length; i++)
		{
			crystalFluidBlocks
					.add(registerBlock(new FluidCrystalBlock(
							ModFluids.crystalFluids.get(i), Material.water,
							ModFluids.crystalFluidNames()[i]
									+ "CrystalFluidBlock", RandomHelper
									.capatilizeString(ModFluids
											.crystalFluidNames()[i])
									+ "CrystalFluidBlock")));
		}
	}

	public static Block registerBlock(Block block, String name)
	{
		GameRegistry.registerBlock(block, name);

		return block;
	}

	public static Block registerBlock(Block block)
	{
		if (block.getRegistryName() == null)
		{
			SkyMod.instance.logger
					.error("Block {} doesn't have a registry name. Block will not be registered.",
							block.getClass().getCanonicalName());
			return block;
		}
		GameRegistry.registerBlock(block);

		return block;
	}
}
