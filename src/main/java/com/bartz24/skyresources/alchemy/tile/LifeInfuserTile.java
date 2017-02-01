package com.bartz24.skyresources.alchemy.tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bartz24.skyresources.alchemy.item.ItemHealthGem;
import com.bartz24.skyresources.alchemy.item.ItemInfusionStone;
import com.bartz24.skyresources.base.gui.ItemHandlerSpecial;
import com.bartz24.skyresources.base.tile.TileItemInventory;
import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;
import com.bartz24.skyresources.registry.ModBlocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.oredict.OreDictionary;

public class LifeInfuserTile extends TileItemInventory implements ITickable
{
	public LifeInfuserTile()
	{
		super("lifeInfuser", 3, null, new Integer[] { 1, 2 });
		this.setInventory(new ItemHandlerSpecial(3, null, new Integer[] { 1, 2 })
		{
			protected void onContentsChanged(int slot)
			{
				super.onContentsChanged(slot);
				LifeInfuserTile.this.markDirty();
			}

			public boolean isItemValid(int slot, ItemStack stack)
			{
				if (slot == 0)
					return stack.getItem() instanceof ItemHealthGem;
				else if (slot == 1)
					return stack.getItem() instanceof ItemInfusionStone;
				return super.isItemValid(slot, stack);
			}
		});
	}

	private static final Map<String, List<ItemStack>> oreMap = new HashMap<String, List<ItemStack>>();

	@Override
	public void update()
	{
		if (receivedPulse() && hasValidMultiblock())
		{
			craftItem();
		}

		updateRedstone();
	}

	public boolean hasValidMultiblock()
	{
		BlockPos[] pillarPoses = new BlockPos[] { pos.north(1).west(1), pos.north(1).east(1), pos.south(1).west(1),
				pos.south(1).east(1) };
		for (BlockPos pos : pillarPoses)
		{
			ItemStack stack = new ItemStack(world.getBlockState(pos).getBlock(), 1,
					world.getBlockState(pos).getBlock().damageDropped(world.getBlockState(pos)));
			if (!isOreDict(stack, "logWood"))
				return false;
			stack = new ItemStack(world.getBlockState(pos.down()).getBlock(), 1,
					world.getBlockState(pos.down()).getBlock().damageDropped(world.getBlockState(pos.down())));
			if (!isOreDict(stack, "logWood"))
				return false;
		}
		for (int x = -1; x < 2; x++)
		{
			for (int z = -1; z < 2; z++)
			{
				BlockPos posCheck = new BlockPos(x, 0, z).add(pos);
				ItemStack stack = new ItemStack(world.getBlockState(posCheck.up()).getBlock(), 1, world
						.getBlockState(posCheck.up()).getBlock().damageDropped(world.getBlockState(posCheck.up())));
				if (x == 0 && z == 0)
				{
					if (world.getBlockState(posCheck.up()).getBlock() != ModBlocks.darkMatterBlock)
						return false;
				} else if (!isOreDict(stack, "treeLeaves"))
					return false;

			}
		}
		return true;
	}

	private boolean isOreDict(ItemStack stack, String entry)
	{
		if (stack == ItemStack.EMPTY || stack.getItem() == null)
			return false;

		List<ItemStack> ores;
		if (oreMap.containsKey(entry))
			ores = oreMap.get(entry);
		else
		{
			ores = OreDictionary.getOres(entry);
			oreMap.put(entry, ores);
		}

		for (ItemStack ostack : ores)
		{
			ItemStack cstack = ostack.copy();
			if (cstack.getItemDamage() == Short.MAX_VALUE)
				cstack.setItemDamage(stack.getItemDamage());

			if (stack.isItemEqual(cstack))
				return true;
		}

		return false;
	}

	void craftItem()
	{
		ProcessRecipe recipe = recipeToCraft();
		boolean worked = false;
		if (recipe != null)
		{
			if (!world.isRemote)
			{
				getInventory().extractItem(2, ((ItemStack) recipe.getInputs().get(0)).getCount(), false);
				getInventory().getStackInSlot(1).setItemDamage(getInventory().getStackInSlot(1).getItemDamage() + 1);
				if (getInventory().getStackInSlot(1).getItemDamage() >= getInventory().getStackInSlot(1).getMaxDamage())
					getInventory().setStackInSlot(1, ItemStack.EMPTY);
				world.setBlockToAir(pos.down(1));
				ItemStack gemStack = getInventory().getStackInSlot(0);
				if (gemStack != ItemStack.EMPTY && gemStack.getItem() instanceof ItemHealthGem)
				{
					ItemHealthGem healthGem = (ItemHealthGem) gemStack.getItem();
					gemStack.getTagCompound().setInteger("health",
							gemStack.getTagCompound().getInteger("health") - (int) recipe.getIntParameter());
				}

				ItemStack stack = recipe.getOutputs().get(0).copy();

				Entity entity = new EntityItem(world, pos.getX() + 0.5F, pos.getY() - 0.5F, pos.getZ() + 0.5F, stack);
				world.spawnEntity(entity);
			}
			if (worked)
			{
				this.world.spawnParticle(EnumParticleTypes.HEART, pos.getX() + 0.5D, pos.getY() - 0.5D,
						pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D, new int[0]);
			}
		}
	}

	public int getHealthInGem()
	{
		ItemStack gemStack = getInventory().getStackInSlot(0);
		if (gemStack != ItemStack.EMPTY && gemStack.getItem() instanceof ItemHealthGem)
		{
			ItemHealthGem healthGem = (ItemHealthGem) gemStack.getItem();
			return healthGem.getHealthInjected(gemStack);
		}
		return 0;
	}

	public ProcessRecipe recipeToCraft()
	{
		IBlockState state = this.world.getBlockState(this.pos.down(1));
		if (state != null)
		{
			ProcessRecipe recipe = ProcessRecipeManager.infusionRecipes
					.getRecipe(
							new ArrayList<Object>(Arrays.asList((Object) getInventory().getStackInSlot(2),
									(Object) new ItemStack(state.getBlock(), 1,
											state.getBlock().getMetaFromState(state)))),
							this.getHealthInGem(), false, false);

			if (recipe != null && recipe.getIntParameter() <= this.getHealthInGem()
					&& getInventory().getStackInSlot(1) != ItemStack.EMPTY
					&& getInventory().getStackInSlot(1).getItem() instanceof ItemInfusionStone)
				return recipe;
		}
		return null;
	}
}
