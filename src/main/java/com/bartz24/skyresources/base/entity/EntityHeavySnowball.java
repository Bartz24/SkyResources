package com.bartz24.skyresources.base.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityHeavySnowball extends EntitySnowball
{
    public EntityHeavySnowball(World worldIn)
    {
        super(worldIn);
    }

    public EntityHeavySnowball(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
        this.moveRelative(1, .5f, 1);
    }

    public EntityHeavySnowball(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void onImpact(RayTraceResult result)
    {
        if (result.entityHit != null)
        {
            int i = 4;

            if (result.entityHit instanceof EntityBlaze)
            {
                i = 12;
            }

            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float)i);
        }

        for (int j = 0; j < 32; ++j)
        {
            this.world.spawnParticle(EnumParticleTypes.SNOWBALL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
        }

        if (!this.world.isRemote)
        {
            this.setDead();
        }
    }

}
