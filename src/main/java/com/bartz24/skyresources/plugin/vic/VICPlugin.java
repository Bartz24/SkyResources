package com.bartz24.skyresources.plugin.vic;

import com.bartz24.skyresources.base.guide.SkyResourcesGuide;
import com.bartz24.skyresources.plugin.IModPlugin;
import com.bartz24.skyresources.registry.ModBlocks;
import com.bartz24.skyresources.registry.ModFluids;
import com.bartz24.voidislandcontrol.api.IslandGen;
import com.bartz24.voidislandcontrol.api.IslandManager;
import com.bartz24.voidislandcontrol.config.ConfigOptions;
import com.bartz24.voidislandcontrol.config.ConfigOptions.IslandSettings.BottomBlockType;

import net.minecraft.block.BlockNetherWart;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;

public class VICPlugin implements IModPlugin
{

	public void preInit()
	{
		if (com.bartz24.skyresources.config.ConfigOptions.pluginSettings.voidIslandControlSettings.enableMagmaIsland)
		{
			IslandManager.registerIsland(new IslandGen("magma")
			{
				public void generate(World world, BlockPos spawn)
				{

					for (int x = -(int) Math
							.floor((float) ConfigOptions.islandSettings.islandSize / 2F); x <= (int) Math
									.floor((float) ConfigOptions.islandSettings.islandSize / 2F); x++)
					{
						for (int z = -(int) Math
								.floor((float) ConfigOptions.islandSettings.islandSize / 2F); z <= (int) Math
										.floor((float) ConfigOptions.islandSettings.islandSize / 2F); z++)
						{
							BlockPos pos = new BlockPos(spawn.getX() + x, spawn.getY(), spawn.getZ() + z);
							if (ConfigOptions.islandSettings.bottomBlockType == BottomBlockType.BEDROCK)
								world.setBlockState(pos.down(4), Blocks.BEDROCK.getDefaultState(), 2);
							else if (ConfigOptions.islandSettings.bottomBlockType == BottomBlockType.SECONDARYBLOCK)
								world.setBlockState(pos.down(4), ModBlocks.magmafiedStone.getDefaultState(), 2);
						}
					}
					BlockPos pos = new BlockPos(spawn.getX(), spawn.getY() - 3, spawn.getZ());
					world.setBlockState(pos.east().south().up(), ModBlocks.petrifiedWood.getDefaultState(), 2);
					world.setBlockState(pos.east().south().up(2), ModBlocks.petrifiedWood.getDefaultState(), 2);
					world.setBlockState(pos.east().south().up(3), ModBlocks.petrifiedWood.getDefaultState(), 2);
					world.setBlockState(pos.south().up(4), ModBlocks.petrifiedWood.getDefaultState(), 2);
					world.setBlockState(pos.west().up(), ModBlocks.petrifiedWood.getDefaultState(), 2);
					world.setBlockState(pos.west(2), ModBlocks.petrifiedWood.getDefaultState(), 2);
					world.setBlockState(pos.west(2).down(), ModBlocks.petrifiedWood.getDefaultState(), 2);
					world.setBlockState(pos.west(2).down(2), ModBlocks.petrifiedWood.getDefaultState(), 2);
					world.setBlockState(pos.west().north().up(), ModBlocks.petrifiedWood.getDefaultState(), 2);

					world.setBlockState(pos.east().north().up(),
							Blocks.NETHER_WART.getDefaultState().withProperty(BlockNetherWart.AGE, 3), 2);

					world.setBlockState(pos.east().south(), Blocks.SOUL_SAND.getDefaultState(), 2);
					world.setBlockState(pos.south(), Blocks.SOUL_SAND.getDefaultState(), 2);
					FluidUtil.tryPlaceFluid(null, world, pos.west().south(),
							new FluidTank(ModFluids.crystalFluid, 1000, 100),
							new FluidStack(ModFluids.crystalFluid, 1000));
					world.setBlockState(pos.east(), ModBlocks.magmafiedStone.getDefaultState(), 2);
					world.setBlockState(pos, ModBlocks.magmafiedStone.getDefaultState(), 2);
					world.setBlockState(pos.west(), Blocks.SOUL_SAND.getDefaultState(), 2);
					world.setBlockState(pos.east().north(), Blocks.SOUL_SAND.getDefaultState(), 2);
					world.setBlockState(pos.north(), ModBlocks.magmafiedStone.getDefaultState(), 2);
					world.setBlockState(pos.west().north(), ModBlocks.magmafiedStone.getDefaultState(), 2);

					changeBiome(spawn.getX(), spawn.getZ(), world);
				}
			});
		}

	}

	public void init()
	{
		SkyResourcesGuide.addPage("vic", "guide.skyresources.misc", new ItemStack(ModBlocks.magmafiedStone));
	}

	public void postInit()
	{

	}

	public void initRenderers()
	{

	}

	public static void initIslands()
	{

	}

	private void changeBiome(int xIs, int zIs, World world)
	{
		if (ConfigOptions.islandSettings.islandBiomeID >= 0)
		{
			for (int x = xIs - (int) Math.floor((float) ConfigOptions.islandSettings.islandBiomeRange / 2F); x <= xIs
					+ (int) Math.floor((float) ConfigOptions.islandSettings.islandBiomeRange / 2F); x++)
			{
				for (int z = zIs
						- (int) Math.floor((float) ConfigOptions.islandSettings.islandBiomeRange / 2F); z <= zIs
								+ (int) Math.floor((float) ConfigOptions.islandSettings.islandBiomeRange / 2F); z++)
				{
					world.getChunkFromBlockCoords(new BlockPos(x, 64, z))
							.getBiomeArray()[(new BlockPos(x, 64, z).getZ() & 15) << 4 | (new BlockPos(x, 64, z).getX()
									& 15)] = (byte) ConfigOptions.islandSettings.islandBiomeID;
				}
			}
		}
	}

}
