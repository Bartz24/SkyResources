package com.bartz24.skyresources.registry;

import java.util.HashMap;
import java.util.Map;

import com.bartz24.skyresources.base.guide.SkyResourcesGuide;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.Loader;

public class ModGuidePages
{
	public static Map<String, Map<BlockPos, IBlockState>> imageDesigns = new HashMap();

	public static void init()
	{
		SkyResourcesGuide.addPage("stage1", "guide.skyresources.stage1", new ItemStack(Blocks.BEDROCK));

		SkyResourcesGuide.addPage("sandIsland", "guide.skyresources.stage1", new ItemStack(Blocks.SAND, 1, 1));

		SkyResourcesGuide.addPage("lifeInfusion", "guide.skyresources.stage1",
				new ItemStack(ModItems.alchemicalInfusionStone));
		
		SkyResourcesGuide.addPage("knife", "guide.skyresources.stage1",
				new ItemStack(ModItems.cactusKnife));

		SkyResourcesGuide.addPage("snowIsland", "guide.skyresources.stage1", new ItemStack(Blocks.SNOW));

		SkyResourcesGuide.addPage("freezer", "guide.skyresources.stage1", new ItemStack(ModBlocks.miniFreezer));
		addImagePos("ironFreezer", new BlockPos(0, -1, 0), ModBlocks.ironFreezer.getStateFromMeta(8));
		addImagePos("ironFreezer", new BlockPos(0, 0, 0), ModBlocks.ironFreezer.getStateFromMeta(0));

		SkyResourcesGuide.addPage("heavySnowball", "guide.skyresources.stage1", new ItemStack(ModItems.heavySnowball));

		SkyResourcesGuide.addPage("woodIsland", "guide.skyresources.stage1", new ItemStack(Blocks.PLANKS, 1, 5));

		SkyResourcesGuide.addPage("survFish", "guide.skyresources.stage1",
				new ItemStack(ModItems.survivalistFishingRod));

		SkyResourcesGuide.addPage("grassIsland", "guide.skyresources.stage1", new ItemStack(Blocks.GRASS));

		SkyResourcesGuide.addPage("magmaIsland", "guide.skyresources.stage1", new ItemStack(ModBlocks.petrifiedWood));

		SkyResourcesGuide.addPage("magmaStone", "guide.skyresources.stage1", new ItemStack(ModBlocks.magmafiedStone));

		SkyResourcesGuide.addPage("stage2", "guide.skyresources.stage2", new ItemStack(Blocks.LOG));

		SkyResourcesGuide.addPage("casing", "guide.skyresources.stage2", new ItemStack(ModBlocks.casing));

		SkyResourcesGuide.addPage("combustionHeater", "guide.skyresources.stage2",
				new ItemStack(ModItems.combustionHeater));
		addImagePos("combustion", new BlockPos(0, -1, 0), ModBlocks.casing.getDefaultState());
		addImagePos("combustion", new BlockPos(1, 0, 0), Blocks.PLANKS.getDefaultState());
		addImagePos("combustion", new BlockPos(-1, 0, 0), Blocks.PLANKS.getDefaultState());
		addImagePos("combustion", new BlockPos(0, 0, 1), Blocks.PLANKS.getDefaultState());
		addImagePos("combustion", new BlockPos(0, 0, -1), Blocks.PLANKS.getDefaultState());
		addImagePos("combustion", new BlockPos(0, 1, 0), Blocks.PLANKS.getDefaultState());
		addImagePos("combustion", new BlockPos(0, -1, -1), Blocks.WOODEN_BUTTON.getDefaultState());

		SkyResourcesGuide.addPage("waterExtractor", "guide.skyresources.stage2",
				new ItemStack(ModItems.waterExtractor));

		SkyResourcesGuide.addPage("dirtFurnace", "guide.skyresources.stage2", new ItemStack(ModBlocks.dirtFurnace));

		SkyResourcesGuide.addPage("lavaBlaze", "guide.skyresources.stage2", new ItemStack(ModBlocks.blazePowderBlock));
		addImagePos("lava", new BlockPos(0, -1, 0), Blocks.TORCH.getDefaultState());
		addImagePos("lava", new BlockPos(0, 0, 0), ModBlocks.blazePowderBlock.getDefaultState());

		SkyResourcesGuide.addPage("heatSources", "guide.skyresources.stage2", new ItemStack(Blocks.TORCH));

		SkyResourcesGuide.addPage("heatProvider", "guide.skyresources.stage2", new ItemStack(ModItems.heatProvider));

		SkyResourcesGuide.addPage("rockGrinder", "guide.skyresources.stage2", new ItemStack(ModItems.stoneGrinder));

		SkyResourcesGuide.addPage("metalCreation", "guide.skyresources.stage2", new ItemStack(ModItems.oreAlchDust));
		addImagePos("crystalSetup", new BlockPos(0, -2, 0), Blocks.HOPPER.getDefaultState());
		addImagePos("crystalSetup", new BlockPos(0, -1, 0), ModBlocks.casing.getDefaultState());
		addImagePos("crystalSetup", new BlockPos(-1, 0, 0), Blocks.GLASS.getDefaultState());
		addImagePos("crystalSetup", new BlockPos(0, 0, 1), Blocks.GLASS.getDefaultState());
		addImagePos("crystalSetup", new BlockPos(0, 0, -1), Blocks.GLASS.getDefaultState());
		addImagePos("crystalSetup", new BlockPos(1, 0, 0), Blocks.GLASS.getDefaultState());
		addImagePos("crystalSetup", new BlockPos(0, 1, 0), ModBlocks.fluidDropper.getDefaultState());
		addImagePos("crystalSetup", new BlockPos(0, 2, 0), ModBlocks.fluidDropper.getDefaultState());
		addImagePos("crystalSetup", new BlockPos(1, 2, 0), ModBlocks.crucible.getDefaultState());
		addImagePos("crystalSetup", new BlockPos(1, 1, 0), Blocks.TORCH.getDefaultState());

		SkyResourcesGuide.addPage("crucible", "guide.skyresources.stage2", new ItemStack(ModBlocks.crucible));

		SkyResourcesGuide.addPage("fluidDropper", "guide.skyresources.stage2", new ItemStack(ModBlocks.fluidDropper));

		SkyResourcesGuide.addPage("fusionTable", "guide.skyresources.stage2", new ItemStack(ModBlocks.fusionTable));

		SkyResourcesGuide.addPage("condenser", "guide.skyresources.stage2", new ItemStack(ModItems.condenser));

		SkyResourcesGuide.addPage("crucibleInserter", "guide.skyresources.stage2",
				new ItemStack(ModBlocks.crucibleInserter));

		SkyResourcesGuide.addPage("combustionCollector", "guide.skyresources.stage2",
				new ItemStack(ModBlocks.combustionCollector));

		SkyResourcesGuide.addPage("crushedStone", "guide.skyresources.stage2",
				new ItemStack(ModItems.techComponent, 1, 0));

		SkyResourcesGuide.addPage("gemProduction", "guide.skyresources.stage2", new ItemStack(Items.EMERALD));

		SkyResourcesGuide.addPage("wildlifeAttractor", "guide.skyresources.stage2",
				new ItemStack(ModBlocks.wildlifeAttractor));

		SkyResourcesGuide.addPage("seeds", "guide.skyresources.stage2", new ItemStack(Items.PUMPKIN_SEEDS));

		SkyResourcesGuide.addPage("stage3", "guide.skyresources.stage3", new ItemStack(Blocks.GLOWSTONE));

		SkyResourcesGuide.addPage("quickDropper", "guide.skyresources.stage3", new ItemStack(ModBlocks.quickDropper));

		SkyResourcesGuide.addPage("rockCrusher", "guide.skyresources.stage3", new ItemStack(ModBlocks.rockCrusher));

		SkyResourcesGuide.addPage("rockCleaner", "guide.skyresources.stage3", new ItemStack(ModBlocks.rockCleaner));

		SkyResourcesGuide.addPage("combustionController", "guide.skyresources.stage3",
				new ItemStack(ModBlocks.combustionController));

		SkyResourcesGuide.addPage("aqueous", "guide.skyresources.stage3", new ItemStack(ModBlocks.aqueousConcentrator));

		SkyResourcesGuide.addPage("stage4", "guide.skyresources.stage4", new ItemStack(ModItems.baseComponent, 1, 3));

		SkyResourcesGuide.addPage("dmWarper", "guide.skyresources.stage4", new ItemStack(ModBlocks.darkMatterWarper));

		SkyResourcesGuide.addPage("end", "guide.skyresources.stage4", new ItemStack(Items.ENDER_EYE));
		addImagePos("end", new BlockPos(0, -1, 0), ModBlocks.endPortalCore.getDefaultState());
		addImagePos("end", new BlockPos(-1, -1, 0), Blocks.GOLD_BLOCK.getDefaultState());
		addImagePos("end", new BlockPos(1, -1, 0), Blocks.GOLD_BLOCK.getDefaultState());
		addImagePos("end", new BlockPos(0, -1, -1), Blocks.GOLD_BLOCK.getDefaultState());
		addImagePos("end", new BlockPos(0, -1, 1), Blocks.GOLD_BLOCK.getDefaultState());
		addImagePos("end", new BlockPos(-1, -1, -1), Blocks.DIAMOND_BLOCK.getDefaultState());
		addImagePos("end", new BlockPos(1, -1, -1), Blocks.DIAMOND_BLOCK.getDefaultState());
		addImagePos("end", new BlockPos(-1, -1, 1), Blocks.DIAMOND_BLOCK.getDefaultState());
		addImagePos("end", new BlockPos(1, -1, 1), Blocks.DIAMOND_BLOCK.getDefaultState());
		for (int x = -2; x <= 2; x++)
		{
			for (int z = -2; z <= 2; z++)
			{
				if (Math.abs(x) == 2 || Math.abs(z) == 2)
					addImagePos("end", new BlockPos(x, -1, z), ModBlocks.darkMatterBlock.getDefaultState());
			}
		}
		addImagePos("end", new BlockPos(-2, 0, -2), Blocks.END_BRICKS.getDefaultState());
		addImagePos("end", new BlockPos(-2, 1, -2), Blocks.END_BRICKS.getDefaultState());
		addImagePos("end", new BlockPos(-2, 2, -2), Blocks.GLOWSTONE.getDefaultState());
		addImagePos("end", new BlockPos(2, 0, -2), Blocks.END_BRICKS.getDefaultState());
		addImagePos("end", new BlockPos(2, 1, -2), Blocks.END_BRICKS.getDefaultState());
		addImagePos("end", new BlockPos(2, 2, -2), Blocks.GLOWSTONE.getDefaultState());
		addImagePos("end", new BlockPos(-2, 0, 2), Blocks.END_BRICKS.getDefaultState());
		addImagePos("end", new BlockPos(-2, 1, 2), Blocks.END_BRICKS.getDefaultState());
		addImagePos("end", new BlockPos(-2, 2, 2), Blocks.GLOWSTONE.getDefaultState());
		addImagePos("end", new BlockPos(2, 0, 2), Blocks.END_BRICKS.getDefaultState());
		addImagePos("end", new BlockPos(2, 1, 2), Blocks.END_BRICKS.getDefaultState());
		addImagePos("end", new BlockPos(2, 2, 2), Blocks.GLOWSTONE.getDefaultState());
		addImagePos("end", new BlockPos(0, 0, 0), Blocks.STONE_BUTTON.getStateFromMeta(5));

		SkyResourcesGuide.addPage("end2", "guide.skyresources.stage4", new ItemStack(ModBlocks.silverfishDisruptor));
		addImagePos("end2", new BlockPos(0, -1, 0), ModBlocks.endPortalCore.getDefaultState());
		addImagePos("end2", new BlockPos(-1, -1, 0), Blocks.GOLD_BLOCK.getDefaultState());
		addImagePos("end2", new BlockPos(1, -1, 0), Blocks.GOLD_BLOCK.getDefaultState());
		addImagePos("end2", new BlockPos(0, -1, -1), Blocks.GOLD_BLOCK.getDefaultState());
		addImagePos("end2", new BlockPos(0, -1, 1), Blocks.GOLD_BLOCK.getDefaultState());
		addImagePos("end2", new BlockPos(-1, -1, -1), Blocks.DIAMOND_BLOCK.getDefaultState());
		addImagePos("end2", new BlockPos(1, -1, -1), Blocks.DIAMOND_BLOCK.getDefaultState());
		addImagePos("end2", new BlockPos(-1, -1, 1), Blocks.DIAMOND_BLOCK.getDefaultState());
		addImagePos("end2", new BlockPos(1, -1, 1), Blocks.DIAMOND_BLOCK.getDefaultState());
		for (int x = -3; x <= 3; x++)
		{
			for (int z = -3; z <= 3; z++)
			{
				if (Math.abs(x) == 3 || Math.abs(z) == 3)
					addImagePos("end2", new BlockPos(x, -1, z), ModBlocks.lightMatterBlock.getDefaultState());
				else if (Math.abs(x) == 2 || Math.abs(z) == 2)
					addImagePos("end2", new BlockPos(x, -1, z), ModBlocks.darkMatterBlock.getDefaultState());
			}
		}
		IBlockState purpurState = Blocks.PURPUR_PILLAR.getDefaultState().withProperty(BlockRotatedPillar.AXIS,
				EnumFacing.Axis.Y);
		addImagePos("end2", new BlockPos(-2, 0, -2), Blocks.END_BRICKS.getDefaultState());
		addImagePos("end2", new BlockPos(-2, 1, -2), Blocks.END_BRICKS.getDefaultState());
		addImagePos("end2", new BlockPos(-2, 2, -2), Blocks.GLOWSTONE.getDefaultState());
		addImagePos("end2", new BlockPos(-2, 3, -2), ModBlocks.silverfishDisruptor.getDefaultState());
		addImagePos("end2", new BlockPos(2, 0, -2), Blocks.END_BRICKS.getDefaultState());
		addImagePos("end2", new BlockPos(2, 1, -2), Blocks.END_BRICKS.getDefaultState());
		addImagePos("end2", new BlockPos(2, 2, -2), Blocks.GLOWSTONE.getDefaultState());
		addImagePos("end2", new BlockPos(2, 3, -2), ModBlocks.silverfishDisruptor.getDefaultState());
		addImagePos("end2", new BlockPos(-2, 0, 2), Blocks.END_BRICKS.getDefaultState());
		addImagePos("end2", new BlockPos(-2, 1, 2), Blocks.END_BRICKS.getDefaultState());
		addImagePos("end2", new BlockPos(-2, 2, 2), Blocks.GLOWSTONE.getDefaultState());
		addImagePos("end2", new BlockPos(-2, 3, 2), ModBlocks.silverfishDisruptor.getDefaultState());
		addImagePos("end2", new BlockPos(2, 0, 2), Blocks.END_BRICKS.getDefaultState());
		addImagePos("end2", new BlockPos(2, 1, 2), Blocks.END_BRICKS.getDefaultState());
		addImagePos("end2", new BlockPos(2, 2, 2), Blocks.GLOWSTONE.getDefaultState());
		addImagePos("end2", new BlockPos(2, 3, 2), ModBlocks.silverfishDisruptor.getDefaultState());
		addImagePos("end2", new BlockPos(-3, 0, -3), purpurState);
		addImagePos("end2", new BlockPos(-3, 1, -3), purpurState);
		addImagePos("end2", new BlockPos(-3, 2, -3), purpurState);
		addImagePos("end2", new BlockPos(-3, 3, -3), purpurState);
		addImagePos("end2", new BlockPos(-3, 4, -3), Blocks.END_ROD.getDefaultState());
		addImagePos("end2", new BlockPos(3, 0, -3), purpurState);
		addImagePos("end2", new BlockPos(3, 1, -3), purpurState);
		addImagePos("end2", new BlockPos(3, 2, -3), purpurState);
		addImagePos("end2", new BlockPos(3, 3, -3), purpurState);
		addImagePos("end2", new BlockPos(3, 4, -3), Blocks.END_ROD.getDefaultState());
		addImagePos("end2", new BlockPos(-3, 0, 3), purpurState);
		addImagePos("end2", new BlockPos(-3, 1, 3), purpurState);
		addImagePos("end2", new BlockPos(-3, 2, 3), purpurState);
		addImagePos("end2", new BlockPos(-3, 3, 3), purpurState);
		addImagePos("end2", new BlockPos(-3, 4, 3), Blocks.END_ROD.getDefaultState());
		addImagePos("end2", new BlockPos(3, 0, 3), purpurState);
		addImagePos("end2", new BlockPos(3, 1, 3), purpurState);
		addImagePos("end2", new BlockPos(3, 2, 3), purpurState);
		addImagePos("end2", new BlockPos(3, 3, 3), purpurState);
		addImagePos("end2", new BlockPos(3, 4, 3), Blocks.END_ROD.getDefaultState());
		addImagePos("end2", new BlockPos(0, 0, 0), Blocks.STONE_BUTTON.getStateFromMeta(5));

		SkyResourcesGuide.addPage("healthGem", "guide.skyresources.stage4", new ItemStack(ModItems.healthGem));

		SkyResourcesGuide.addPage("lifeInfuser", "guide.skyresources.stage4", new ItemStack(ModBlocks.lifeInfuser));
		addImagePos("infuser", new BlockPos(-1, -1, -1), Blocks.LOG.getDefaultState());
		addImagePos("infuser", new BlockPos(-1, 0, -1), Blocks.LOG.getDefaultState());
		addImagePos("infuser", new BlockPos(1, -1, -1), Blocks.LOG.getDefaultState());
		addImagePos("infuser", new BlockPos(1, 0, -1), Blocks.LOG.getDefaultState());
		addImagePos("infuser", new BlockPos(-1, -1, 1), Blocks.LOG.getDefaultState());
		addImagePos("infuser", new BlockPos(-1, 0, 1), Blocks.LOG.getDefaultState());
		addImagePos("infuser", new BlockPos(1, -1, 1), Blocks.LOG.getDefaultState());
		addImagePos("infuser", new BlockPos(1, 0, 1), Blocks.LOG.getDefaultState());
		addImagePos("infuser", new BlockPos(-1, 1, -1), Blocks.LEAVES.getDefaultState());
		addImagePos("infuser", new BlockPos(0, 1, -1), Blocks.LEAVES.getDefaultState());
		addImagePos("infuser", new BlockPos(1, 1, -1), Blocks.LEAVES.getDefaultState());
		addImagePos("infuser", new BlockPos(1, 1, 0), Blocks.LEAVES.getDefaultState());
		addImagePos("infuser", new BlockPos(1, 1, 1), Blocks.LEAVES.getDefaultState());
		addImagePos("infuser", new BlockPos(0, 1, 1), Blocks.LEAVES.getDefaultState());
		addImagePos("infuser", new BlockPos(-1, 1, 1), Blocks.LEAVES.getDefaultState());
		addImagePos("infuser", new BlockPos(-1, 1, 0), Blocks.LEAVES.getDefaultState());
		addImagePos("infuser", new BlockPos(0, 0, 0), ModBlocks.lifeInfuser.getDefaultState());
		addImagePos("infuser", new BlockPos(0, 1, 0), ModBlocks.darkMatterBlock.getDefaultState());
		addImagePos("infuser", new BlockPos(0, 2, 0), Blocks.STONE_BUTTON.getStateFromMeta(5));

		SkyResourcesGuide.addPage("lifeInjector", "guide.skyresources.stage4", new ItemStack(ModBlocks.lifeInjector));

		if (Loader.isModLoaded("voidislandcontrol"))
			SkyResourcesGuide.addPage("nether", "guide.skyresources.misc", new ItemStack(Blocks.NETHERRACK));
	}

	private static void addImagePos(String id, BlockPos pos, IBlockState block)
	{
		if (imageDesigns.get(id) == null)
			imageDesigns.put(id, new HashMap());
		Map map = imageDesigns.get(id);
		map.put(pos, block);
		imageDesigns.put(id, map);
	}
}
