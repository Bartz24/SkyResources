package com.bartz24.skyresources.technology.tile;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.base.tile.TileItemInventory;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;
import com.bartz24.skyresources.technology.block.CombustionHeaterBlock;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class TileCombustionHeater extends TileItemInventory implements ITickable
{
	public TileCombustionHeater()
	{
		super("combustionHeater", 1, null, new Integer[] { 0 });
	}

	public int currentHeatValue;
	public int fuelBurnTime;
	public int heatPerTick;
	public int currentItemBurnTime;

	public List<Material> ValidMaterialsForCrafting()
	{
		if (!(world.getBlockState(pos).getBlock() instanceof CombustionHeaterBlock))
			return null;
		IBlockState blockMeta = world.getBlockState(pos);
		List<Material> mats = new ArrayList<Material>();
		switch (world.getBlockState(pos).getBlock().getMetaFromState(blockMeta))
		{
		case 0: // WOOD
			mats.add(Material.WOOD);
			break;
		case 1: // IRON
			mats.add(Material.IRON);
			mats.add(Material.ROCK);
			break;
		}
		return mats;
	}

	public int getMaxHeat()
	{
		if (!(world.getBlockState(pos).getBlock() instanceof CombustionHeaterBlock))
			return 0;
		CombustionHeaterBlock block = (CombustionHeaterBlock) world.getBlockState(pos).getBlock();

		return block.getMaximumHeat(world.getBlockState(pos));
	}

	public int getMaxHeatPerTick()
	{
		if (!(world.getBlockState(pos).getBlock() instanceof CombustionHeaterBlock))
			return 0;
		CombustionHeaterBlock block = (CombustionHeaterBlock) world.getBlockState(pos).getBlock();

		switch (block.getMetaFromState(world.getBlockState(pos)))
		{
		case 0:
			return 8;
		case 1:
			return 16;
		}

		return 0;
	}

	public int getHeatPerTick(ItemStack stack)
	{
		int fuelTime = TileEntityFurnace.getItemBurnTime(stack);
		if (fuelTime > 0)
		{
			return (int) Math.cbrt((float) fuelTime * ConfigOptions.combustionHeatMultiplier);
		}

		return 0;
	}

	public int getFuelBurnTime(ItemStack stack)
	{
		if ((float) getHeatPerTick(stack) <= 0)
			return 0;

		return (int) ((float) Math.pow(TileEntityFurnace.getItemBurnTime(stack), 0.75F) / getHeatPerTick(stack));
	}

	public boolean isValidFuel(ItemStack stack)
	{
		if (TileEntityFurnace.getItemBurnTime(stack) <= 0 || getHeatPerTick(stack) <= 0
				|| getHeatPerTick(stack) > getMaxHeatPerTick() || getFuelBurnTime(stack) <= 0)
			return false;

		return true;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		compound.setInteger("heat", currentHeatValue);
		compound.setInteger("fuel", fuelBurnTime);
		compound.setInteger("item", currentItemBurnTime);
		compound.setInteger("hpt", heatPerTick);
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		currentHeatValue = compound.getInteger("heat");
		fuelBurnTime = compound.getInteger("fuel");
		currentItemBurnTime = compound.getInteger("item");
		heatPerTick = compound.getInteger("hpt");
	}

	@Override
	public void update()
	{
		if (receivedPulse() && hasValidMultiblock())
		{
			craftItem();
		}
		updateRedstone();

		if (fuelBurnTime > 0)
		{
			this.fuelBurnTime--;
		} else
			this.currentItemBurnTime = this.heatPerTick = this.fuelBurnTime = 0;

		if (!this.world.isRemote)
		{
			if (fuelBurnTime > 0 || this.getInventory().getStackInSlot(0) != ItemStack.EMPTY)
			{
				if (fuelBurnTime == 0 && currentHeatValue < getMaxHeat()
						&& isValidFuel(getInventory().getStackInSlot(0)))
				{
					this.currentItemBurnTime = this.fuelBurnTime = getFuelBurnTime(getInventory().getStackInSlot(0));
					heatPerTick = getHeatPerTick(getInventory().getStackInSlot(0));

					if (fuelBurnTime > 0)
					{
						if (this.getInventory().getStackInSlot(0) != ItemStack.EMPTY)
						{
							this.getInventory().getStackInSlot(0).shrink(1);

							if (this.getInventory().getStackInSlot(0).getCount() == 0)
							{
								this.getInventory().setStackInSlot(0, getInventory().getStackInSlot(0).getItem()
										.getContainerItem(getInventory().getStackInSlot(0)));
							}
						}
					}
				}

				if (fuelBurnTime > 0 && currentHeatValue < getMaxHeat())
				{
					currentHeatValue += heatPerTick;
				}
			}

			if (!hasValidMultiblock())
			{
				if (currentHeatValue > 0)
					currentHeatValue--;
			}

			if (currentHeatValue > getMaxHeat())
				currentHeatValue = getMaxHeat();

			this.markDirty();
		}
	}

	public TileCombustionCollector getCollector()
	{
		BlockPos[] poses = new BlockPos[] { pos.add(-1, 1, 0), pos.add(1, 1, 0), pos.add(0, 1, -1), pos.add(0, 1, 1),
				pos.add(0, 2, 0) };
		for (BlockPos p : poses)
		{
			TileEntity t = world.getTileEntity(p);
			if (t != null && t instanceof TileCombustionCollector)
				return (TileCombustionCollector) t;
		}
		return null;
	}

	public boolean hasValidMultiblock()
	{
		List<Material> materials = ValidMaterialsForCrafting();
		if (!isBlockValid(pos.add(-1, 1, 0)) || !isBlockValid(pos.add(1, 1, 0)) || !isBlockValid(pos.add(0, 2, 0))
				|| !isBlockValid(pos.add(0, 1, -1)) || !isBlockValid(pos.add(0, 1, 1))
				|| !world.isAirBlock(pos.add(0, 1, 0)))
			return false;
		return true;
	}

	boolean isBlockValid(BlockPos pos)
	{
		return ValidMaterialsForCrafting().contains(world.getBlockState(pos).getMaterial())
				&& world.isBlockFullCube(pos) && world.getBlockState(pos).isOpaqueCube();
	}

	void craftItem()
	{
		ProcessRecipe recipe = recipeToCraft();
		if (recipe != null)
		{
			this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, pos.getX(), pos.getY() + 1.5D, pos.getZ(),
					0.0D, 0.0D, 0.0D, new int[0]);
			this.world.playSound((EntityPlayer) null, pos.getX(), pos.getY() + 1.5D, pos.getZ(),
					SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F,
					(1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);

			if (!world.isRemote)
			{
				List<EntityItem> list = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.getX(),
						pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getY() + 2F, pos.getZ() + 1));
				ItemStack item1 = list.get(0).getEntityItem();
				ItemStack item2 = ItemStack.EMPTY;
				for (Object recStack : recipe.getInputs())
				{
					if (item1.isItemEqual((ItemStack) recStack))
						item2 = (ItemStack) recStack;
				}

				int timesToCraft = (int) Math.floor((float) item1.getCount() / (float) item2.getCount());

				for (int times = 0; times < timesToCraft; times++)
				{
					if (currentHeatValue < recipe.getIntParameter())
						break;

					List<ItemStack> inputs = new ArrayList<ItemStack>();
					for (Object o : recipe.getInputs())
						inputs.add(((ItemStack) o).copy());
					for (int i = 0; i < list.size(); i++)
					{
						ItemStack stack = list.get(i).getEntityItem();
						for (ItemStack i2 : inputs)
						{
							int count = Math.min(i2.getCount(), stack.getCount());
							if (stack.isItemEqual(i2))
							{
								stack.shrink(count);
								i2.shrink(count);
							}
						}
						for (int i2 = inputs.size() - 1; i2 >= 0; i2--)
						{
							if (inputs.get(i2).isEmpty())
								inputs.remove(i2);
						}
					}

					currentHeatValue *= (world.getBlockState(pos).getBlock()
							.getMetaFromState(world.getBlockState(pos)) == 0 ? 0.7F : 0.85F);

					ItemStack stack = recipe.getOutputs().get(0).copy();

					TileCombustionCollector collector = getCollector();
					if (collector != null)
					{
						for (int i = 0; i < 5; i++)
						{
							if (!stack.isEmpty())
								stack = collector.getInventory().insertItem(i, stack, false);
							else
								break;
						}
					}
					if (!stack.isEmpty())
					{
						Entity entity = new EntityItem(world, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F,
								stack);
						world.spawnEntity(entity);
					}
				}
			}
		}
	}

	public ProcessRecipe recipeToCraft()
	{
		List<EntityItem> list = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.getX(),
				pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1));

		List<Object> items = new ArrayList<Object>();

		for (EntityItem i : list)
		{
			items.add(i.getEntityItem());
		}

		ProcessRecipe recipe = ProcessRecipeManager.combustionRecipes.getMultiRecipe(items, currentHeatValue, true,
				true);

		return recipe;
	}
}
