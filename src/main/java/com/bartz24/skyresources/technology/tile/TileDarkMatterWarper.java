package com.bartz24.skyresources.technology.tile;

import java.util.List;

import com.bartz24.skyresources.registry.ModItems;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class TileDarkMatterWarper extends TileEntity implements ITickable, IInventory
{
	private NonNullList<ItemStack> inventory = NonNullList.<ItemStack> withSize(1, ItemStack.EMPTY);
	private int burnTime;
	private int maxBurnTime = 3600;

	@Override
	public void update()
	{

		if (!world.isRemote)
		{
			if (burnTime <= 0)
			{
				if (inventory.get(0) != ItemStack.EMPTY
						&& inventory.get(0).isItemEqual(new ItemStack(ModItems.baseComponent, 1, 5)))
				{
					inventory.get(0).shrink(1);
					if (inventory.get(0).getCount() == 0)
						inventory.set(0, ItemStack.EMPTY);
					burnTime = maxBurnTime;
				}
			}

			List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(
					pos.getX() - 4, pos.getY() - 4, pos.getZ() - 4, pos.getX() + 4, pos.getY() + 4, pos.getZ() + 4));
			if (burnTime > 0)
			{
				burnTime--;

				for (EntityLivingBase entity : list)
				{
					if (!entity.isDead && entity instanceof EntitySkeleton)
					{
						EntityWitherSkeleton skeleton = new EntityWitherSkeleton(world);
						skeleton.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw,
								entity.rotationPitch);
						skeleton.renderYawOffset = entity.renderYawOffset;
						skeleton.setHealth(skeleton.getMaxHealth());
						world.spawnEntity(skeleton);
					} else if (!entity.isDead && entity instanceof EntitySpider
							&& !(entity instanceof EntityCaveSpider))
					{
						EntitySpider spider = (EntitySpider) entity;
						spider.setDead();

						EntityCaveSpider caveSpider = new EntityCaveSpider(world);
						caveSpider.setLocationAndAngles(spider.posX, spider.posY, spider.posZ, spider.rotationYaw,
								spider.rotationPitch);
						caveSpider.renderYawOffset = spider.renderYawOffset;
						caveSpider.setHealth(caveSpider.getMaxHealth());

						world.spawnEntity(caveSpider);
					} else if (!entity.isDead && entity instanceof EntitySquid)
					{
						EntitySquid squid = (EntitySquid) entity;
						squid.setDead();

						EntityBlaze blaze = new EntityBlaze(world);
						blaze.setLocationAndAngles(squid.posX, squid.posY, squid.posZ, squid.rotationYaw,
								squid.rotationPitch);
						blaze.renderYawOffset = squid.renderYawOffset;
						blaze.setHealth(blaze.getMaxHealth());

						world.spawnEntity(blaze);
					} else if (!entity.isDead && (entity instanceof EntityPlayer || entity instanceof EntityAnimal))
					{
						entity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 360, 0));
						entity.addPotionEffect(new PotionEffect(MobEffects.WITHER, 360, 0));
						entity.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 360, 0));
						entity.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 360, 0));
						entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 360, 0));
						entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 360, 0));
					}
				}
			} else
			{
				for (EntityLivingBase entity : list)
				{
					if (!entity.isDead && entity instanceof EntityPlayer)
					{
						entity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 5, 2));
						entity.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 5, 2));
						entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 5, 2));
						entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 5, 2));
					}
				}
			}
		}
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
		return "container.skyresources.darkMatterWarper";
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

		this.inventory.set(index, stack);
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
		return stack.isItemEqual(new ItemStack(ModItems.baseComponent, 1, 5));
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

		compound.setInteger("fuel", burnTime);

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

		burnTime = compound.getInteger("fuel");

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
