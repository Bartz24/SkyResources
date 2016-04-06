package com.bartz24.skyresources.alchemy.fluid;

import java.util.Random;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.alchemy.block.CondenserBlock;
import com.bartz24.skyresources.registry.ModBlocks;
import com.bartz24.skyresources.registry.ModFluids;
import com.bartz24.skyresources.registry.ModItems;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FluidCrystalBlock extends BlockFluidClassic
{

	public FluidCrystalBlock(Fluid fluid, Material material,
			String unlocalizedName, String registryName)
	{
		super(fluid, material);
		this.setUnlocalizedName(References.ModID + "." + unlocalizedName);
		this.setRegistryName(registryName);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state,
			Random rand)
	{
		super.updateTick(world, pos, state, rand);
		if (!world.isRemote)
		{
			if (!ModFluids.crystalFluids.contains(this))
				return;
			
			if (this.isSourceBlock(world, pos)
					&& isNotFlowing(world, pos, state)
					&& rand.nextInt(ModFluids
							.crystalFluidRarity()[ModBlocks.crystalFluidBlocks
									.indexOf(this)]) == 0
					&& !(world.getBlockState(pos.add(0, -1, 0))
							.getBlock() instanceof CondenserBlock))
			{
				ItemStack stack = new ItemStack(ModItems.metalCrystal, 1,
						ModBlocks.crystalFluidBlocks.indexOf(this));
				Entity entity = new EntityItem(world, pos.getX() + 0.5F,
						pos.getY() + 0.5F, pos.getZ() + 0.5F, stack);
				world.spawnEntityInWorld(entity);
				world.playSound((EntityPlayer) null, pos,
						SoundEvents.entity_arrow_hit_player,
						SoundCategory.BLOCKS, 1.0F,
						2.2F / (rand.nextFloat() * 0.2F + 0.9F));
				if (rand.nextInt(8 + ModFluids
						.crystalFluidRarity()[ModBlocks.crystalFluidBlocks
								.indexOf(this)]
						/ 2) >= 8)
					world.setBlockToAir(pos);
			}
		}
	}

	public boolean isNotFlowing(World world, BlockPos pos, IBlockState state)
	{
		BlockPos[] checkPos = new BlockPos[]
		{ pos.add(1, 0, 0), pos.add(-1, 0, 0), pos.add(0, 0, 1),
				pos.add(0, 0, -1) };
		for (BlockPos pos2 : checkPos)
		{
			if (world.getBlockState(pos2).getBlock() == this)
			{
				if (!isSourceBlock(world, pos2))
					return false;
			}
		}
		if (world.getBlockState(pos.add(0, 1, 0)).getBlock() == this
				|| world.getBlockState(pos.add(0, -1, 0)).getBlock() == this)
			return false;

		return true;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return null;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState blockState,
			World worldIn, BlockPos pos)
	{
		return NULL_AABB;
	}
}
