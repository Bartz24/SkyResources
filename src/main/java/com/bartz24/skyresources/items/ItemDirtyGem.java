package com.bartz24.skyresources.items;

import com.bartz24.varyingmachina.base.item.ItemBase;
import com.bartz24.varyingmachina.base.machine.MachineVariant;
import com.bartz24.varyingmachina.base.models.MachineModelLoader;
import com.bartz24.varyingmachina.registry.MachineRegistry;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;

public class ItemDirtyGem extends ItemBase {


    @SideOnly(Side.CLIENT)
    public void initModel() {
        ResourceLocation baseLocation = new ResourceLocation(getModAddingMachine(), "item/" + getMachineID());
        MachineModelLoader loader = new MachineModelLoader(baseLocation, "all");
        Map<MachineVariant, ModelResourceLocation> locations = new HashMap();
        for (int i = 0; i < MachineRegistry.getAllVariantsRegistered().length; i++) {
            ModelResourceLocation location = new ModelResourceLocation(
                    getRegistryName().toString()
                            + MachineRegistry.getAllVariantsRegistered()[i].getRegistryName().getResourcePath(),
                    "inventory");
            locations.put(MachineRegistry.getAllVariantsRegistered()[i], location);
            loader.addVariant(location, MachineRegistry.getAllVariantsRegistered()[i].getTexturePath());
        }
        ModelLoader.registerItemVariants(this, locations.values().toArray(new ModelResourceLocation[locations.size()]));
        ModelLoader.setCustomMeshDefinition(this, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                MachineVariant variant = MachineVariant.readFromNBT(stack.getTagCompound());
                if (variant != null)
                    return locations.get(variant);
                return locations.get(0);
            }
        });
        ModelLoaderRegistry.registerLoader(loader);

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs creativeTab, NonNullList<ItemStack> list) {
        if (isInCreativeTab(creativeTab))
            for (MachineVariant variant : MachineVariant.REGISTRY.getValuesCollection()) {
                ItemStack stack = new ItemStack(this);
                MachineVariant.writeVariantToStack(stack, variant);
                list.add(stack);
            }
    }

    public NonNullList<ItemStack> getItemTypes() {
        NonNullList<ItemStack> list = NonNullList.create();
        for (MachineVariant variant : MachineVariant.REGISTRY.getValuesCollection()) {
            ItemStack stack = new ItemStack(this);
            MachineVariant.writeVariantToStack(stack, variant);
            list.add(stack);
        }
        return list;
    }
}
