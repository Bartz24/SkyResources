package com.bartz24.skyresources.technology.tile;

import java.util.List;

import com.bartz24.skyresources.base.tile.TileItemInventory;
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
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class TileDarkMatterWarper extends TileItemInventory implements ITickable
{
	public TileDarkMatterWarper()
	{
		super("darkMatterWarper", 1, null, new Integer[] { 0 });
	}

	private int burnTime;
	private int maxBurnTime = 3600;

	@Override
	public void update()
	{

		if (!world.isRemote)
		{
			if (burnTime <= 0)
			{
				if (getInventory().getStackInSlot(0) != ItemStack.EMPTY
						&& getInventory().getStackInSlot(0).isItemEqual(new ItemStack(ModItems.baseComponent, 1, 5)))
				{
					getInventory().getStackInSlot(0).shrink(1);
					if (getInventory().getStackInSlot(0).getCount() == 0)
						getInventory().setStackInSlot(0, ItemStack.EMPTY);
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
						if (entity instanceof EntityPlayer && ((EntityPlayer) entity).isCreative())
							continue;
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
						if (entity instanceof EntityPlayer && ((EntityPlayer) entity).isCreative())
							continue;
						entity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 5, 2));
						entity.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 5, 2));
						entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 5, 2));
						entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 5, 2));
					}
				}
			}

			this.markDirty();
		}
	}

	public int getBurnTime()
	{
		return burnTime;
	}

	public int getMaxBurnTime()
	{
		return maxBurnTime;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		compound.setInteger("burn", burnTime);
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		burnTime = compound.getInteger("burn");
	}
}
