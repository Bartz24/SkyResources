package com.bartz24.skyresources.registry;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.SkyResources;
import com.bartz24.skyresources.alchemy.block.CondenserBlock;
import com.bartz24.skyresources.alchemy.block.CoolingCrystallizerBlock;
import com.bartz24.skyresources.alchemy.block.CrucibleBlock;
import com.bartz24.skyresources.alchemy.block.LifeInfuserBlock;
import com.bartz24.skyresources.alchemy.block.PurificationVesselBlock;
import com.bartz24.skyresources.alchemy.fluid.FluidCrystalBlock;
import com.bartz24.skyresources.alchemy.fluid.FluidMoltenCrystalBlock;
import com.bartz24.skyresources.base.block.BaseBlock;
import com.bartz24.skyresources.base.block.BlazePowderBlock;
import com.bartz24.skyresources.base.block.BlockDryCactus;
import com.bartz24.skyresources.base.block.ItemBlockMeta;
import com.bartz24.skyresources.base.block.TransparentBlock;
import com.bartz24.skyresources.technology.block.BlockDarkMatterWarper;
import com.bartz24.skyresources.technology.block.BlockDirtFurnace;
import com.bartz24.skyresources.technology.block.BlockEndPortalCore;
import com.bartz24.skyresources.technology.block.BlockFreezer;
import com.bartz24.skyresources.technology.block.BlockMiniFreezer;
import com.bartz24.skyresources.technology.block.BlockPoweredHeater;
import com.bartz24.skyresources.technology.block.CombustionHeaterBlock;
import com.bartz24.skyresources.technology.block.ConcentratorBlock;
import com.bartz24.skyresources.technology.block.FluidDropperBlock;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks
{
	public static Block cactusFruitNeedle;

	public static Block combustionHeater;
	public static Block concentrator;
	public static Block crucible;
	public static Block fluidDropper;
	public static Block alchemicalCondenser;
	public static Block advancedCoolingCondenser;
	public static Block crystallizer;
	public static Block purificationVessel;
	public static Block miniFreezer;
	public static Block ironFreezer;
	public static Block poweredHeater;
	public static Block darkMatterWarper;
	public static Block endPortalCore;
	public static Block lifeInfuser;


	public static Block compressedCoalBlock;
	public static Block compressedCoalBlock2;
	public static Block coalInfusedBlock;
	public static Block compressedStone;
	public static Block compressedNetherrack;
	public static Block sandyNetherrack;
	public static Block darkMatterBlock;
	public static Block heavySnow;
	public static Block heavySnow2;

	public static Block blazePowderBlock;

	public static Block dryCactus;
	public static Block dirtFurnace;

	public static List<Block> crystalFluidBlocks;
	public static List<Block> dirtyCrystalFluidBlocks;
	public static List<Block> moltenCrystalFluidBlocks;

	public static void init()
	{
		crystalFluidBlocks = new ArrayList<Block>();
		dirtyCrystalFluidBlocks = new ArrayList<Block>();
		moltenCrystalFluidBlocks = new ArrayList<Block>();
		cactusFruitNeedle = registerBlock(new TransparentBlock(Material.PLANTS, "cactusFruitNeedle",
				"CactusFruitNeedle", 0.5F, 0.5F,
				new AxisAlignedBB(0.3D, 0D, 0.3D, 0.7D, 0.8D, 0.7D), SoundType.PLANT));

		compressedStone = registerBlock(new BaseBlock(Material.ROCK, "compressedStone",
				"CompressedStone", 6F, 6F, SoundType.STONE));

		compressedNetherrack = registerBlock(new BaseBlock(Material.ROCK, "compressedNetherrack",
				"CompressedNetherrack", 3F, 3F, SoundType.STONE));

		compressedCoalBlock = registerBlock(new BaseBlock(Material.ROCK, "compressedCoalBlock",
				"CompressedCoalBlock", 6F, 6F, SoundType.STONE));

		compressedCoalBlock2 = registerBlock(new BaseBlock(Material.ROCK, "compressedCoalBlock2",
				"CompressedCoalBlock2", 9F, 9F, SoundType.STONE));

		sandyNetherrack = registerBlock(new BaseBlock(Material.ROCK, "sandyNetherrack",
				"SandyNetherrack", 2F, 2F, SoundType.STONE));

		coalInfusedBlock = registerBlock(new BaseBlock(Material.ROCK, "coalInfusedBlock",
				"CoalInfusedBlock", 6F, 6F, SoundType.STONE));
		darkMatterBlock = registerBlock(new BaseBlock(Material.ROCK, "darkMatterBlock",
				"DarkMatterBlock", 10F, 12F, SoundType.STONE));

		blazePowderBlock = registerBlock(new BlazePowderBlock(Material.CLAY, "blazePowderBlock",
				"BlazePowderBlock", 0.5F, 0.5F, SoundType.GROUND));
		heavySnow = registerBlock(
				new BaseBlock(Material.CLAY, "heavySnow", "HeavySnow", 0.5F, 0.5F, SoundType.SNOW));
		heavySnow2 = registerBlock(
				new BaseBlock(Material.CLAY, "heavySnow2", "HeavySnow2", 1F, 1F, SoundType.SNOW));

		alchemicalCondenser = registerBlock(
				new CondenserBlock("alchemicalCondenser", "AlchemicalCondenser", 2F, 12F));
		advancedCoolingCondenser = registerBlock(
				new CondenserBlock("advancedCoolingCondenser", "AdvancedCoolingCondenser", 6F, 12F));
		crystallizer = registerBlock(
				new CoolingCrystallizerBlock("coolingCrystallizer", "CoolingCrystallizer", 6F, 12F));

		poweredHeater = registerBlock(
				new BlockPoweredHeater("poweredHeater", "PoweredHeater", 4F, 12F));
		darkMatterWarper = registerBlock(
				new BlockDarkMatterWarper("darkMatterWarper", "DarkMatterWarper", 8F, 12F));
		endPortalCore = registerBlock(
				new BlockEndPortalCore("endPortalCore", "EndPortalCore", 6F, 12F));
		lifeInfuser = registerBlock(
				new LifeInfuserBlock("lifeInfuser", "LifeInfuser", 6F, 12F));

		purificationVessel = registerBlock(
				new PurificationVesselBlock("purificationVessel", "PurificationVessel", 2F, 12F));

		miniFreezer = registerBlock(new BlockMiniFreezer("miniFreezer", "MiniFreezer", 0.5F, 0.5F));
		ironFreezer = registerBlock(new BlockFreezer("ironFreezer", "IronFreezer", 2F, 2F));

		crucible = registerBlock(new CrucibleBlock("crucible", "Crucible", 2F, 12F));

		concentrator = registerBlock(
				new ConcentratorBlock("concentrator", "Concentrator", 2F, 12F));

		fluidDropper = registerBlock(
				new FluidDropperBlock("fluidDropper", "FluidDropper", 2F, 12F));

		registerItemBlock(combustionHeater = new CombustionHeaterBlock("combustionHeater",
				"CombustionHeater", 2F, 12F));
		dryCactus = registerBlock(new BlockDryCactus());

		dirtFurnace = registerBlock(new BlockDirtFurnace("dirtFurnace", "DirtFurnace", 0.5F, 0.5F));

		for (int i = 0; i < ModFluids.crystalFluidInfos().length; i++)
		{
				crystalFluidBlocks.add(registerBlock(
						new FluidCrystalBlock(ModFluids.crystalFluids.get(i), Material.WATER,
								ModFluids.crystalFluidInfos()[i].name + "CrystalFluidBlock",
								RandomHelper.capatilizeString(ModFluids.crystalFluidInfos()[i].name)
										+ "CrystalFluidBlock")));
				dirtyCrystalFluidBlocks.add(registerBlock(
						new FluidCrystalBlock(ModFluids.dirtyCrystalFluids.get(i), Material.WATER,
								ModFluids.crystalFluidInfos()[i].name + "DirtyCrystalFluidBlock",
								RandomHelper.capatilizeString(ModFluids.crystalFluidInfos()[i].name)
										+ "DirtyCrystalFluidBlock")));
		}
		
		for (int i = 0; i < ModFluids.moltenCrystalFluidInfos().length; i++)
		{
				moltenCrystalFluidBlocks.add(registerBlock(
						new FluidMoltenCrystalBlock(ModFluids.moltenCrystalFluids.get(i), Material.LAVA,
								ModFluids.moltenCrystalFluidInfos()[i].name + "MoltenCrystalFluidBlock",
								RandomHelper.capatilizeString(ModFluids.moltenCrystalFluidInfos()[i].name)
										+ "MoltenCrystalFluidBlock")));
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
			SkyResources.logger.error(
					"Block {} doesn't have a registry name. Block will not be registered.",
					block.getClass().getCanonicalName());
			return block;
		}
		GameRegistry.registerBlock(block);

		return block;
	}

	public static void registerItemBlock(Block block)
	{
		GameRegistry.register(block);
		GameRegistry.register(new ItemBlockMeta(block).setRegistryName(block.getRegistryName()));

	}
}
