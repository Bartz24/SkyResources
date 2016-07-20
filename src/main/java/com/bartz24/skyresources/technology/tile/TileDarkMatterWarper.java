package com.bartz24.skyresources.technology.tile;

import java.util.List;

import com.bartz24.skyresources.registry.ModItems;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.SkeletonType;
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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class TileDarkMatterWarper extends TileEntity implements ITickable, IInventory
{
	private ItemStack[] inventory = new ItemStack[1];
	private int burnTime;
	private int maxBurnTime = 3600;

	@Override
	public void update()
	{

		if (!worldObj.isRemote)
		{
			if (burnTime <= 0)
			{
				if (inventory[0] != null
						&& inventory[0].isItemEqual(new ItemStack(ModItems.baseComponent, 1, 5)))
				{
					inventory[0].stackSize--;
					if (inventory[0].stackSize == 0)
						inventory[0] = null;
					burnTime = maxBurnTime;
				}
			}

			List<EntityLivingBase> list = worldObj.getEntitiesWithinAABB(EntityLivingBase.class,
					new AxisAlignedBB(pos.getX() - 4, pos.getY() - 4, pos.getZ() - 4,
							pos.getX() + 4, pos.getY() + 4, pos.getZ() + 4));
			if (burnTime > 0)
			{
				burnTime--;

				for (EntityLivingBase entity : list)
				{
					if (!entity.isDead && entity instanceof EntitySkeleton)
					{
						if (((EntitySkeleton) entity).func_189771_df() == SkeletonType.NORMAL)
						{
							EntitySkeleton skeleton = (EntitySkeleton) entity;
							skeleton.func_189768_a(SkeletonType.WITHER);
							skeleton.setHealth(skeleton.getMaxHealth());
						}
					} else if (!entity.isDead && entity instanceof EntitySpider
							&& !(entity instanceof EntityCaveSpider))
					{
						EntitySpider spider = (EntitySpider) entity;
						spider.setDead();

						EntityCaveSpider caveSpider = new EntityCaveSpider(worldObj);
						caveSpider.setLocationAndAngles(spider.posX, spider.posY, spider.posZ,
								spider.rotationYaw, spider.rotationPitch);
						caveSpider.renderYawOffset = spider.renderYawOffset;
						caveSpider.setHealth(caveSpider.getMaxHealth());

						worldObj.spawnEntityInWorld(caveSpider);
					} else if (!entity.isDead && entity instanceof EntitySquid)
					{
						EntitySquid squid = (EntitySquid) entity;
						squid.setDead();

						EntityBlaze blaze = new EntityBlaze(worldObj);
						blaze.setLocationAndAngles(squid.posX, squid.posY, squid.posZ,
								squid.rotationYaw, squid.rotationPitch);
						blaze.renderYawOffset = squid.renderYawOffset;
						blaze.setHealth(blaze.getMaxHealth());

						worldObj.spawnEntityInWorld(blaze);
					} else if (!entity.isDead
							&& (entity instanceof EntityPlayer || entity instanceof EntityAnimal))
					{
						entity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 360, 1));
						entity.addPotionEffect(new PotionEffect(MobEffects.WITHER, 360, 1));
						entity.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 360, 1));
						entity.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 360, 1));
						entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 360, 1));
						entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 360, 1));
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
			return null;
		return this.inventory[index];
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

		compound.setInteger("fuel", burnTime);

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

		burnTime = compound.getInteger("fuel");

		NBTTagList list = compound.getTagList("Items", 10);
		for (int i = 0; i < list.tagCount(); ++i)
		{
			NBTTagCompound stackTag = list.getCompoundTagAt(i);
			int slot = stackTag.getByte("Slot") & 255;
			this.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(stackTag));
		}
	}
}
