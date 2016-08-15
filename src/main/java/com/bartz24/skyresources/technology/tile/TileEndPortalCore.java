package com.bartz24.skyresources.technology.tile;

import java.util.List;

import com.bartz24.skyresources.api.RedstoneCompatibleTile;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.registry.ModBlocks;

import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class TileEndPortalCore extends RedstoneCompatibleTile implements ITickable, IInventory
{
	private ItemStack[] inventory = new ItemStack[1];

	@Override
	public void update()
	{
		if (!worldObj.isRemote)
		{
			if (hasValidMultiblock())
			{
				spawnFish();

				if (receivedPulse())
				{
					List<EntityPlayerMP> list = worldObj.getEntitiesWithinAABB(EntityPlayerMP.class,
							new AxisAlignedBB(pos.getX(), pos.getY() + 1, pos.getZ(),
									pos.getX() + 1, pos.getY() + 2F, pos.getZ() + 1));

					for (EntityPlayerMP player : list)
					{
						if (inventory[0] != null
								&& inventory[0].isItemEqual(new ItemStack(Items.ENDER_EYE))
								&& inventory[0].stackSize >= 16)
						{
							if (player.dimension == 0)
							{
								player.changeDimension(1);
								inventory[0].stackSize -= 16;
								if (inventory[0].stackSize == 0)
									inventory[0] = null;
							}
						}
					}
				}
				prevRedstoneSignal = getRedstoneSignal();
			}
		}
	}
	
	void spawnFish()
	{
		List<EntitySilverfish> list = worldObj.getEntitiesWithinAABB(EntitySilverfish.class,
				new AxisAlignedBB(pos.getX()-4, pos.getY(), pos.getZ()-4,
						pos.getX() + 4, pos.getY() + 5F, pos.getZ() + 4));
		if (!ConfigOptions.endPussyMode && worldObj.rand.nextInt(90) == 0 && list.size() < 16)
		{
			EntitySilverfish fish = new EntitySilverfish(worldObj);
			fish.setDropChance(EntityEquipmentSlot.MAINHAND, 0);
			fish.setDropChance(EntityEquipmentSlot.HEAD, 0);
			fish.setDropChance(EntityEquipmentSlot.CHEST, 0);
			fish.setDropChance(EntityEquipmentSlot.LEGS, 0);
			fish.setDropChance(EntityEquipmentSlot.FEET, 0);
			ItemStack sword = new ItemStack(Items.IRON_SWORD);
			sword.addEnchantment(Enchantments.FIRE_ASPECT, 1);
			sword.addEnchantment(Enchantments.SHARPNESS, 3);
			fish.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, sword);
			fish.setItemStackToSlot(EntityEquipmentSlot.HEAD,
					new ItemStack(Items.DIAMOND_HELMET));
			fish.setItemStackToSlot(EntityEquipmentSlot.CHEST,
					new ItemStack(Items.DIAMOND_CHESTPLATE));
			fish.setItemStackToSlot(EntityEquipmentSlot.LEGS,
					new ItemStack(Items.DIAMOND_LEGGINGS));
			fish.setItemStackToSlot(EntityEquipmentSlot.FEET,
					new ItemStack(Items.DIAMOND_BOOTS));
			fish.setPosition(pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5);
			worldObj.spawnEntityInWorld(fish);
		}
	}

	public boolean hasValidMultiblock()
	{
		BlockPos[] goldBlockPoses = new BlockPos[]
		{ pos.north(), pos.south(), pos.west(), pos.east() };
		for (BlockPos pos : goldBlockPoses)
		{
			if (worldObj.getBlockState(pos).getBlock() != Blocks.GOLD_BLOCK)
				return false;
		}
		BlockPos[] endBlockPoses = new BlockPos[]
		{ pos.north(2).west(2), pos.north(2).east(2), pos.south(2).west(2), pos.south(2).east(2) };
		for (BlockPos pos : endBlockPoses)
		{
			if (worldObj.getBlockState(pos.up()).getBlock() != Blocks.END_BRICKS)
				return false;
			if (worldObj.getBlockState(pos.up(2)).getBlock() != Blocks.END_BRICKS)
				return false;
			if (worldObj.getBlockState(pos.up(3)).getBlock() != Blocks.GLOWSTONE)
				return false;
		}
		BlockPos[] diamBlockPoses = new BlockPos[]
		{ pos.north().west(), pos.north().east(), pos.south().west(), pos.south().east() };
		for (BlockPos pos : diamBlockPoses)
		{
			if (worldObj.getBlockState(pos).getBlock() != Blocks.DIAMOND_BLOCK)
				return false;
		}

		for (int x = pos.getX() - 2; x < pos.getX() + 3; x++)
		{
			for (int z = pos.getZ() - 2; z < pos.getZ() + 3; z++)
			{
				if (Math.abs(pos.getX() - x) > 1 || Math.abs(pos.getZ() - z) > 1)
				{

					if (worldObj.getBlockState(new BlockPos(x, pos.getY(), z))
							.getBlock() != ModBlocks.darkMatterBlock)
						return false;
				}
			}
		}
		return true;
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
		return "container.skyresources.endPortalCore";
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
		return 1;
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
		return stack.isItemEqual(new ItemStack(Items.ENDER_EYE));
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
}
