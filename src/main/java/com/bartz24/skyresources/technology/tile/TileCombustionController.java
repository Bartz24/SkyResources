package com.bartz24.skyresources.technology.tile;

import com.bartz24.skyresources.ItemHelper;
import com.bartz24.skyresources.base.MachineVariants;
import com.bartz24.skyresources.base.tile.TileCasing;
import com.bartz24.skyresources.base.tile.TileItemInventory;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;
import com.bartz24.skyresources.technology.block.BlockCombustionController;
import com.bartz24.skyresources.technology.item.ItemCombustionHeater;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TileCombustionController extends TileItemInventory implements ITickable {

    public int cooldownTicks = 0;

    public TileCombustionController() {
        super("combustionController", 5);
        this.getInventory().setSlotLimit(1);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);

        compound.setInteger("cooldown", cooldownTicks);

        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        cooldownTicks = compound.getInteger("cooldown");
    }

    @Override
    public void update() {

        if (!this.world.isRemote) {
            if (this.getRedstoneSignal() == 0 && cooldownTicks <= 0) {
                craftSingleItem();
            } else if (cooldownTicks > 0)
                cooldownTicks--;
        }
        this.markDirty();
    }

    public ProcessRecipe recipeToCraft(float curHU) {
        List<EntityItem> list = getWorld().getEntitiesWithinAABB(EntityItem.class,
                new AxisAlignedBB(getPosBehind().getX(), getPosBehind().getY(), getPosBehind().getZ(),
                        getPosBehind().getX() + 1, getPosBehind().getY() + 1, getPosBehind().getZ() + 1));

        List<Object> items = new ArrayList<Object>();

        for (EntityItem i : list) {
            items.add(i.getItem());
        }

        List<ProcessRecipe> recipes = ProcessRecipeManager.combustionRecipes.getRecipes();

        for (int i = 0; i < getInventory().getSlots(); i++) {
            ItemStack stack = getInventory().getStackInSlot(i);
            if (!stack.isEmpty()) {
                for (ProcessRecipe r : recipes) {
                    if (ItemHelper.itemStacksEqualOD(r.getOutputs().get(0), stack)) {
                        if (ProcessRecipeManager.combustionRecipes.compareRecipeLess(items, curHU, true, r) != null)
                            return r;
                    }
                }
            }
        }

        return null;
    }

    void craftSingleItem() {
        if (getHeater() == null || getHeater().machineStored.isEmpty() || getHeaterMachine() == null)
            return;
        float curHU = getHeater().machineData.getFloat("curHU");
        ProcessRecipe recipe = recipeToCraft(curHU);
        if (recipe != null) {
            world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, getPosBehind().getX(), getPosBehind().getY() + 0.5D,
                    getPosBehind().getZ(), 0.0D, 0.0D, 0.0D, new int[0]);
            if (MachineVariants.values()[world.getBlockState(pos).getBlock()
                    .getMetaFromState(world.getBlockState(pos))].getRawSpeed() < 2.0)
                world.playSound((EntityPlayer) null, getPosBehind().getX() + 0.5, getPosBehind().getY() + 0.5D,
                        getPosBehind().getZ() + 0.5, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F,
                        (1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);

            if (!world.isRemote) {
                List<EntityItem> list = getWorld().getEntitiesWithinAABB(EntityItem.class,
                        new AxisAlignedBB(getPosBehind().getX(), getPosBehind().getY(), getPosBehind().getZ(),
                                getPosBehind().getX() + 1, getPosBehind().getY() + 1, getPosBehind().getZ() + 1));

                HashMap<ItemStack, Integer> items = new HashMap();
                for (EntityItem o : list) {
                    ItemStack i = o.getItem().copy();
                    int count = i.getCount();
                    i.setCount(1);
                    boolean added = false;
                    for (ItemStack i2 : items.keySet()) {
                        if (i2.isItemEqual(i)) {
                            items.put(i2, items.get(i2) + count);
                            added = true;
                        }
                    }
                    if (!added)
                        items.put(i, count);
                    o.setDead();

                }

                for (Object recStack : recipe.getInputs()) {
                    for (ItemStack item2 : items.keySet().toArray(new ItemStack[items.size()])) {
                        if (item2.isItemEqual((ItemStack) recStack)) {
                            items.put(item2, items.get(item2) - ((ItemStack) recStack).getCount());
                        }
                    }
                }

                float mult = 1f - (1f / (1.5f + 0.8f * getHeaterMachine()
                        .getMachineEfficiency(getHeater().machineStored, world, getPosBehind().down())));
                curHU *= mult;
                cooldownTicks = ConfigOptions.machineSettings.combustionControllerTicks;

                ItemStack stack = recipe.getOutputs().get(0).copy();

                TileCombustionCollector collector = getHeaterMachine().getCollector(world, getPosBehind().down());
                if (collector != null) {
                    for (int i = 0; i < 5; i++) {
                        if (!stack.isEmpty())
                            stack = collector.getInventory().insertItem(i, stack, false);
                        else
                            break;
                    }
                }
                if (!stack.isEmpty()) {
                    Entity entity = new EntityItem(world, getPosBehind().getX() + 0.5F, getPosBehind().getY() + 0.5F,
                            getPosBehind().getZ() + 0.5F, stack);
                    world.spawnEntity(entity);
                }
                for (ItemStack item2 : items.keySet().toArray(new ItemStack[items.size()])) {
                    if (items.get(item2) > 0) {
                        EntityItem entity = new EntityItem(world, getPosBehind().getX() + 0.5F,
                                getPosBehind().getY() + 0.5F, getPosBehind().getZ() + 0.5F, item2);
                        entity.getItem().setCount(items.get(item2));
                        world.spawnEntity(entity);
                    }
                }
            }
        }
        getHeater().machineData.setFloat("curHU", curHU);
    }

    BlockPos getPosBehind() {
        return getPos().add(getWorld().getBlockState(getPos()).getValue(BlockCombustionController.FACING).getOpposite()
                .getDirectionVec());
    }

    TileCasing getHeater() {
        TileEntity te = getWorld().getTileEntity(getPosBehind().down());
        return te instanceof TileCasing ? (TileCasing) te : null;
    }

    ItemCombustionHeater getHeaterMachine() {
        Item item = getHeater().getMachine();
        return item instanceof ItemCombustionHeater ? (ItemCombustionHeater) getHeater().getMachine() : null;
    }
}
