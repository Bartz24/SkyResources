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
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class TileEndPortalCore extends RedstoneCompatibleTile implements ITickable, IInventory
{
	private NonNullList<ItemStack> inventory = NonNullList.<ItemStack> withSize(1, ItemStack.EMPTY);

	@Override
	public void update()
	{
		if (!world.isRemote)
		{
			if (hasValidMultiblock())
			{
				spawnFish();

				if (receivedPulse())
				{
					List<EntityPlayerMP> list = world.getEntitiesWithinAABB(EntityPlayerMP.class,
							new AxisAlignedBB(pos.getX(), pos.getY() + 1, pos.getZ(),
									pos.getX() + 1, pos.getY() + 2F, pos.getZ() + 1));

					for (EntityPlayerMP player : list)
					{
						if (inventory.get(0) != ItemStack.EMPTY
								&& inventory.get(0).isItemEqual(new ItemStack(Items.ENDER_EYE))
								&& inventory.get(0).getCount() >= 16)
						{
							if (player.dimension == 0)
							{
								player.changeDimension(1);
								inventory.get(0).shrink(16);
								if (inventory.get(0).getCount() == 0)
									inventory.set(0, ItemStack.EMPTY);
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
		List<EntitySilverfish> list = world.getEntitiesWithinAABB(EntitySilverfish.class,
				new AxisAlignedBB(pos.getX() - 4, pos.getY(), pos.getZ() - 4, pos.getX() + 4,
						pos.getY() + 5F, pos.getZ() + 4));
		if (!ConfigOptions.endPussyMode && world.rand.nextInt(90) == 0 && list.size() < 16)
		{
			EntitySilverfish fish = new EntitySilverfish(world);
			if (!ConfigOptions.easyMode)
			{
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
			}
			fish.setPosition(pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5);
			world.spawnEntity(fish);
		}
	}

	public boolean hasValidMultiblock()
	{
		BlockPos[] goldBlockPoses = new BlockPos[]
		{ pos.north(), pos.south(), pos.west(), pos.east() };
		for (BlockPos pos : goldBlockPoses)
		{
			if (world.getBlockState(pos).getBlock() != Blocks.GOLD_BLOCK)
				return false;
		}
		BlockPos[] endBlockPoses = new BlockPos[]
		{ pos.north(2).west(2), pos.north(2).east(2), pos.south(2).west(2), pos.south(2).east(2) };
		for (BlockPos pos : endBlockPoses)
		{
			if (world.getBlockState(pos.up()).getBlock() != Blocks.END_BRICKS)
				return false;
			if (world.getBlockState(pos.up(2)).getBlock() != Blocks.END_BRICKS)
				return false;
			if (world.getBlockState(pos.up(3)).getBlock() != Blocks.GLOWSTONE)
				return false;
		}
		BlockPos[] diamBlockPoses = new BlockPos[]
		{ pos.north().west(), pos.north().east(), pos.south().west(), pos.south().east() };
		for (BlockPos pos : diamBlockPoses)
		{
			if (world.getBlockState(pos).getBlock() != Blocks.DIAMOND_BLOCK)
				return false;
		}

		for (int x = pos.getX() - 2; x < pos.getX() + 3; x++)
		{
			for (int z = pos.getZ() - 2; z < pos.getZ() + 3; z++)
			{
				if (Math.abs(pos.getX() - x) > 1 || Math.abs(pos.getZ() - z) > 1)
				{

					if (world.getBlockState(new BlockPos(x, pos.getY(), z))
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
			return ItemStack.EMPTY;
		return this.inventory.get(index);
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
		if (this.getStackInSlot(index) != ItemStack.EMPTY)
		{
			ItemStack itemstack;

			if (this.getStackInSlot(index).getCount() <= count)
			{
				itemstack = this.getStackInSlot(index);
				this.setInventorySlotContents(index, ItemStack.EMPTY);
				this.markDirty();
				return itemstack;
			} else
			{
				itemstack = this.getStackInSlot(index).splitStack(count);

				if (this.getStackInSlot(index).getCount() <= 0)
				{
					this.setInventorySlotContents(index, ItemStack.EMPTY);
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
			return ItemStack.EMPTY;
		}
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		if (index < 0 || index >= this.getSizeInventory())
			return;

		if (stack != ItemStack.EMPTY && stack.getCount() > this.getInventoryStackLimit())
			stack.setCount(this.getInventoryStackLimit());

		if (stack != ItemStack.EMPTY && stack.getCount() == 0)
			stack = ItemStack.EMPTY;

		this.inventory.set(prevRedstoneSignal, stack);
		this.markDirty();

	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return this.world.getTileEntity(this.getPos()) == this
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
			this.setInventorySlotContents(i, ItemStack.EMPTY);
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
			if (this.getStackInSlot(i) != ItemStack.EMPTY)
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
			this.setInventorySlotContents(slot, new ItemStack(stackTag));
		}
	}

	@Override
	public boolean isEmpty()
	{
		for (ItemStack stack : this.inventory)
		{
			if (!stack.isEmpty())
			{
				return false;
			}
		}
		return true;
	}
}
