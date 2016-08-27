package com.bartz24.skyresources.alchemy.tile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bartz24.skyresources.alchemy.infusion.InfusionRecipe;
import com.bartz24.skyresources.alchemy.infusion.InfusionRecipes;
import com.bartz24.skyresources.alchemy.item.ItemHealthGem;
import com.bartz24.skyresources.alchemy.item.ItemInfusionStone;
import com.bartz24.skyresources.api.RedstoneCompatibleTile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.oredict.OreDictionary;

public class LifeInfuserTile extends RedstoneCompatibleTile implements IInventory, ITickable
{
	// 0 = Health Gem, 1 = Infusion Stone, 2 = Off-hand
	private ItemStack[] inventory = new ItemStack[3];
	private static final Map<String, List<ItemStack>> oreMap = new HashMap<String, List<ItemStack>>();

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		NBTTagList list = new NBTTagList();
		for (int i = 0; i < this.getSizeInventory(); ++i)
		{
			if (this.getStackInSlot(i) != null)
			{
				NBTTagCompound stackTag = new NBTTagCompound();
				stackTag.setByte("Slot", (byte) i);
				this.getStackInSlot(i).writeToNBT(stackTag);
				list.appendTag(stackTag);
			}
		}
		compound.setTag("Items", list);
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		NBTTagList list = compound.getTagList("Items", 10);
		for (int i = 0; i < list.tagCount(); ++i)
		{
			NBTTagCompound stackTag = list.getCompoundTagAt(i);
			int slot = stackTag.getByte("Slot") & 255;
			this.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(stackTag));
		}
	}

	@Override
	public void update()
	{
		if (receivedPulse() && hasValidMultiblock())
		{
			craftItem();
		}

		if (!worldObj.isRemote)
			prevRedstoneSignal = getRedstoneSignal();
	}

	public boolean hasValidMultiblock()
	{
		BlockPos[] pillarPoses = new BlockPos[]
		{ pos.north(1).west(1), pos.north(1).east(1), pos.south(1).west(1), pos.south(1).east(1) };
		for (BlockPos pos : pillarPoses)
		{
			ItemStack stack = new ItemStack(worldObj.getBlockState(pos).getBlock(), 1, worldObj
					.getBlockState(pos).getBlock().damageDropped(worldObj.getBlockState(pos)));
			if (!isOreDict(stack, "logWood"))
				return false;
			stack = new ItemStack(worldObj.getBlockState(pos.down()).getBlock(), 1,
					worldObj.getBlockState(pos.down()).getBlock()
							.damageDropped(worldObj.getBlockState(pos.down())));
			if (!isOreDict(stack, "logWood"))
				return false;
		}
		for (int x = -1; x < 2; x++)
		{
			for (int z = -1; z < 2; z++)
			{
				BlockPos posCheck = new BlockPos(x, 0, z).add(pos);
				ItemStack stack = new ItemStack(worldObj.getBlockState(posCheck.up()).getBlock(), 1,
						worldObj.getBlockState(posCheck.up()).getBlock()
								.damageDropped(worldObj.getBlockState(posCheck.up())));
				if (!isOreDict(stack, "treeLeaves"))
					return false;
				if(x!=0 && z != 0 && worldObj.getBlockState(posCheck.down(2)).getBlock() != Blocks.DIRT)
					return false;
			}
		}
		return true;
	}

	private boolean isOreDict(ItemStack stack, String entry)
	{
		if (stack == null || stack.getItem() == null)
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
		InfusionRecipe recipe = recipeToCraft();
		if (recipe != null)
		{
			this.worldObj.spawnParticle(EnumParticleTypes.HEART, pos.getX() + 0.5D, pos.getY() - 0.5D,
					pos.getZ()+0.5D, 0.0D, 0.0D, 0.0D, new int[0]);

			if (!worldObj.isRemote)
			{

				this.decrStackSize(2, recipe.getInputStack().stackSize);
				inventory[1].setItemDamage(inventory[1].getItemDamage() + 1);
				if (inventory[1].getItemDamage() >= inventory[1].getMaxDamage())
					inventory[1] = null;
				worldObj.setBlockToAir(pos.down(1));
				ItemStack gemStack = inventory[0];
				if (gemStack != null && gemStack.getItem() instanceof ItemHealthGem)
				{
					ItemHealthGem healthGem = (ItemHealthGem) gemStack.getItem();
					gemStack.getTagCompound().setInteger("health",
							gemStack.getTagCompound().getInteger("health") - recipe.getHealthReq());
				}

				ItemStack stack = recipe.getOutput().copy();

				Entity entity = new EntityItem(worldObj, pos.getX() + 0.5F, pos.getY() - 0.5F,
						pos.getZ() + 0.5F, stack);
				worldObj.spawnEntityInWorld(entity);
			}
		}
	}

	public int getHealthInGem()
	{
		ItemStack gemStack = inventory[0];
		if (gemStack != null && gemStack.getItem() instanceof ItemHealthGem)
		{
			ItemHealthGem healthGem = (ItemHealthGem) gemStack.getItem();
			return healthGem.getHealthInjected(gemStack);
		}
		return 0;
	}

	public InfusionRecipe recipeToCraft()
	{
		IBlockState state = this.worldObj.getBlockState(this.pos.down(1));
		if (state != null)
		{
			InfusionRecipe recipe = InfusionRecipes.getRecipe(inventory[2], state.getBlock(),
					state.getBlock().getMetaFromState(state));

			if (recipe != null && recipe.getHealthReq() <= this.getHealthInGem()
					&& inventory[1] != null && inventory[1].getItem() instanceof ItemInfusionStone)
				return recipe;
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		if (index < 0 || index >= this.getSizeInventory())
			return null;
		return this.inventory[index];
	}

	@Override
	public String getName()
	{
		return "container.skyresources.lifeInfuser";
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return new TextComponentTranslation(this.getName());
	}

	@Override
	public int getSizeInventory()
	{
		return 3;
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		if (this.getStackInSlot(index) != null)
		{
			ItemStack itemstack;

			if (this.getStackInSlot(index).stackSize <= count)
			{
				itemstack = this.getStackInSlot(index);
				this.setInventorySlotContents(index, null);
				this.markDirty();
				return itemstack;
			} else
			{
				itemstack = this.getStackInSlot(index).splitStack(count);

				if (this.getStackInSlot(index).stackSize <= 0)
				{
					this.setInventorySlotContents(index, null);
				} else
				{
					// Just to show that changes happened
					this.setInventorySlotContents(index, this.getStackInSlot(index));
				}

				this.markDirty();
				return itemstack;
			}
		} else
		{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		if (index < 0 || index >= this.getSizeInventory())
			return;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit())
			stack.stackSize = this.getInventoryStackLimit();

		if (stack != null && stack.stackSize == 0)
			stack = null;

		this.inventory[index] = stack;
		this.markDirty();

	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return this.worldObj.getTileEntity(this.getPos()) == this
				&& player.getDistanceSq(this.pos.add(0.5, 0.5, 0.5)) <= 64;
	}

	@Override
	public void openInventory(EntityPlayer player)
	{
	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return index == 0 ? (stack.getItem() instanceof ItemHealthGem)
				: (index == 1 ? (stack.getItem() instanceof ItemInfusionStone) : true);
	}

	@Override
	public int getField(int id)
	{
		return 0;
	}

	@Override
	public void setField(int id, int value)
	{
	}

	@Override
	public int getFieldCount()
	{
		return 0;
	}

	@Override
	public void clear()
	{
		for (int i = 0; i < this.getSizeInventory(); i++)
			this.setInventorySlotContents(i, null);
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(this.inventory, index);
	}
}
