package com.bartz24.skymod.base.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.bartz24.skymod.References;
import com.bartz24.skymod.alchemy.block.CondenserBlock;
import com.bartz24.skymod.registry.ModBlocks;
import com.bartz24.skymod.registry.ModFluids;
import com.bartz24.skymod.registry.ModItems;

public class BlazePowderBlock extends BaseBlock
{

	public BlazePowderBlock(Material material, String unlocalizedName, String registryName, float hardness, float resistance, SoundType stepSound)
	{
		super(material, registryName, registryName, resistance, resistance, stepSound);
	}
	
	public void updateTick(World world, BlockPos pos, IBlockState state,
			Random rand)
	{
		super.updateTick(world, pos, state, rand);
		System.out.println("Here");
		if (!world.isRemote)
		{
			if (rand.nextInt(4) == 0 && world.getLightFromNeighbors(pos.up()) >= 14)
			{
				world.setBlockState(pos, Blocks.lava.getDefaultState());
		        world.scheduleUpdate(pos, this, tickRate(world));
			}
		}
	}
	
	@Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
        world.scheduleUpdate(pos, this, tickRate(world));
    }
}
