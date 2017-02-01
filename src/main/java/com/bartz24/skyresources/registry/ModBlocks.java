package com.bartz24.skyresources.registry;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.alchemy.block.CondenserBlock;
import com.bartz24.skyresources.alchemy.block.CrucibleBlock;
import com.bartz24.skyresources.alchemy.block.CrystallizerBlock;
import com.bartz24.skyresources.alchemy.block.LifeInfuserBlock;
import com.bartz24.skyresources.alchemy.block.LifeInjectorBlock;
import com.bartz24.skyresources.alchemy.fluid.FluidCrystalBlock;
import com.bartz24.skyresources.alchemy.fluid.FluidRegisterInfo.CrystalFluidType;
import com.bartz24.skyresources.base.block.BaseBlock;
import com.bartz24.skyresources.base.block.BlazePowderBlock;
import com.bartz24.skyresources.base.block.BlockDryCactus;
import com.bartz24.skyresources.base.block.ItemBlockMeta;
import com.bartz24.skyresources.base.block.TransparentBlock;
import com.bartz24.skyresources.technology.block.BlockAqueousConcentrator;
import com.bartz24.skyresources.technology.block.BlockAqueousDeconcentrator;
import com.bartz24.skyresources.technology.block.BlockCombustionCollector;
import com.bartz24.skyresources.technology.block.BlockCrucibleInserter;
import com.bartz24.skyresources.technology.block.BlockDarkMatterWarper;
import com.bartz24.skyresources.technology.block.BlockDirtFurnace;
import com.bartz24.skyresources.technology.block.BlockEndPortalCore;
import com.bartz24.skyresources.technology.block.BlockFreezer;
import com.bartz24.skyresources.technology.block.BlockMiniFreezer;
import com.bartz24.skyresources.technology.block.BlockPoweredHeater;
import com.bartz24.skyresources.technology.block.BlockQuickDropper;
import com.bartz24.skyresources.technology.block.BlockRockCleaner;
import com.bartz24.skyresources.technology.block.BlockRockCrusher;
import com.bartz24.skyresources.technology.block.CombustionHeaterBlock;
import com.bartz24.skyresources.technology.block.FluidDropperBlock;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks
{
	public static Block cactusFruitNeedle;

	public static Block combustionHeater;
	public static Block crucible;
	public static Block fluidDropper;
	public static Block alchemicalCondenser;
	public static Block crystallizer;
	public static Block miniFreezer;
	public static Block ironFreezer;
	public static Block poweredHeater;
	public static Block darkMatterWarper;
	public static Block endPortalCore;
	public static Block lifeInfuser;
	public static Block lifeInjector;
	public static Block crucibleInserter;
	public static Block rockCrusher;
	public static Block rockCleaner;
	public static Block combustionCollector;
	public static Block quickDropper;
	public static Block aqueousConcentrator;
	public static Block aqueousDeconcentrator;

	public static Block compressedCoalBlock;
	public static Block coalInfusedBlock;
	public static Block sandyNetherrack;
	public static Block darkMatterBlock;
	public static Block heavySnow;
	public static Block heavySnow2;

	public static Block blazePowderBlock;

	public static Block dryCactus;
	public static Block dirtFurnace;

	public static List<Block> crystalFluidBlocks;

	public static void init()
	{
		crystalFluidBlocks = new ArrayList<Block>();
		cactusFruitNeedle = registerBlock(new TransparentBlock(Material.PLANTS, "cactusFruitNeedle",
				"CactusFruitNeedle", 0.5F, 0.5F, new AxisAlignedBB(0.3D, 0D, 0.3D, 0.7D, 0.8D, 0.7D), SoundType.PLANT));

		compressedCoalBlock = registerBlock(
				new BaseBlock(Material.ROCK, "compressedCoalBlock", "CompressedCoalBlock", 6F, 6F, SoundType.STONE));

		sandyNetherrack = registerBlock(
				new BaseBlock(Material.ROCK, "sandyNetherrack", "SandyNetherrack", 2F, 2F, SoundType.STONE));

		coalInfusedBlock = registerBlock(
				new BaseBlock(Material.ROCK, "coalInfusedBlock", "CoalInfusedBlock", 6F, 6F, SoundType.STONE));
		darkMatterBlock = registerBlock(
				new BaseBlock(Material.ROCK, "darkMatterBlock", "DarkMatterBlock", 10F, 12F, SoundType.STONE));

		blazePowderBlock = registerBlock(new BlazePowderBlock(Material.CLAY, "blazePowderBlock", "BlazePowderBlock",
				0.5F, 0.5F, SoundType.GROUND));
		heavySnow = registerBlock(new BaseBlock(Material.CLAY, "heavySnow", "HeavySnow", 0.5F, 0.5F, SoundType.SNOW));
		heavySnow2 = registerBlock(new BaseBlock(Material.CLAY, "heavySnow2", "HeavySnow2", 1F, 1F, SoundType.SNOW));

		registerItemBlock(
				alchemicalCondenser = new CondenserBlock("alchemicalCondenser", "AlchemicalCondenser", 2F, 12F));

		registerItemBlock(crystallizer = new CrystallizerBlock("crystallizer", "crystallizer", 6F, 12F));

		poweredHeater = registerBlock(new BlockPoweredHeater("poweredHeater", "PoweredHeater", 4F, 12F));
		darkMatterWarper = registerBlock(new BlockDarkMatterWarper("darkMatterWarper", "DarkMatterWarper", 8F, 12F));
		endPortalCore = registerBlock(new BlockEndPortalCore("endPortalCore", "EndPortalCore", 6F, 12F));
		rockCrusher = registerBlock(new BlockRockCrusher("rockCrusher", "RockCrusher", 6F, 12F));
		rockCleaner = registerBlock(new BlockRockCleaner("rockCleaner", "RockCleaner", 6F, 12F));
		combustionCollector = registerBlock(
				new BlockCombustionCollector("combustionCollector", "CombustionCollector", 6F, 12F));
		quickDropper = registerBlock(new BlockQuickDropper("quickDropper", "QuickDropper", 6F, 12F));
		lifeInfuser = registerBlock(new LifeInfuserBlock("lifeInfuser", "LifeInfuser", 6F, 12F));
		lifeInjector = registerBlock(new LifeInjectorBlock("lifeInjector", "LifeInjector", 6F, 12F));

		miniFreezer = registerBlock(new BlockMiniFreezer("miniFreezer", "MiniFreezer", 0.5F, 0.5F));
		ironFreezer = registerBlock(new BlockFreezer("ironFreezer", "IronFreezer", 2F, 2F));

		crucible = registerBlock(new CrucibleBlock("crucible", "Crucible", 2F, 12F));

		fluidDropper = registerBlock(new FluidDropperBlock("fluidDropper", "FluidDropper", 2F, 12F));

		crucibleInserter = registerBlock(new BlockCrucibleInserter("crucibleInserter", "CrucibleInserter", 2F, 12F));
		aqueousConcentrator = registerBlock(
				new BlockAqueousConcentrator("aqueousConcentrator", "AqueousConcentrator", 2F, 12F));
		aqueousDeconcentrator = registerBlock(
				new BlockAqueousDeconcentrator("aqueousDeconcentrator", "AqueousDeconcentrator", 2F, 12F));

		registerItemBlock(
				combustionHeater = new CombustionHeaterBlock("combustionHeater", "CombustionHeater", 2F, 12F));
		dryCactus = registerBlock(new BlockDryCactus());

		dirtFurnace = registerBlock(new BlockDirtFurnace("dirtFurnace", "DirtFurnace", 0.5F, 0.5F));

		for (int i = 0; i < ModFluids.crystalFluidInfos().length; i++)
		{
			CrystalFluidType type = ModFluids.crystalFluidInfos()[i].type;
			crystalFluidBlocks.add(registerBlockOnly(new FluidCrystalBlock(ModFluids.crystalFluids.get(i),
					type == CrystalFluidType.NORMAL ? Material.WATER : Material.LAVA,
					ModFluids.crystalFluidInfos()[i].name + (type == CrystalFluidType.NORMAL ? "" : "Molten")
							+ "CrystalFluidBlock",
					ModFluids.crystalFluidInfos()[i].name + (type == CrystalFluidType.NORMAL ? "" : "molten")
							+ "crystalfluidblock")));
		}
	}

	public static Block registerBlockOnly(Block block, String name)
	{
		GameRegistry.register(block, new ResourceLocation(References.ModID, name));

		return block;
	}

	public static Block registerBlock(Block block, String name)
	{
		GameRegistry.register(block, new ResourceLocation(References.ModID, name));
		GameRegistry.register(new ItemBlock(block).setRegistryName(new ResourceLocation(References.ModID, name)));

		return block;
	}

	public static Block registerBlockOnly(Block block)
	{
		GameRegistry.register(block);

		return block;
	}

	public static Block registerBlock(Block block)
	{
		GameRegistry.register(block);
		GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));

		return block;
	}

	public static void registerItemBlock(Block block)
	{
		GameRegistry.register(block);
		GameRegistry.register(new ItemBlockMeta(block).setRegistryName(block.getRegistryName()));

	}
}
