package com.bartz24.skyresources.alchemy;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FusionCatalysts {
    static HashMap<ItemStack, Float> catalysts = new HashMap();

    public FusionCatalysts() {
        catalysts = new HashMap<ItemStack, Float>();
        ctAdded = new HashMap<ItemStack, Float>();
        ctRemoved = new ArrayList();
    }

    public static void addCatalyst(ItemStack stack, float value) {
        catalysts.put(stack, value);
    }

    public static boolean isCatalyst(ItemStack stack) {
        for (ItemStack key : catalysts.keySet()) {
            if (stack.isItemEqual(key))
                return true;
        }

        return false;
    }

    public static float getCatalystValue(ItemStack stack) {
        for (ItemStack key : catalysts.keySet()) {
            if (stack.isItemEqual(key))
                return catalysts.get(key);
        }
        return 0;
    }

    public static void removeCatalyst(ItemStack catalyst) {
        for (ItemStack stack : catalysts.keySet()) {
            if (stack.isItemEqual(catalyst))
                catalysts.remove(stack);
        }
    }

    public static HashMap<ItemStack, Float> getCatalysts() {
        return catalysts;
    }

    private static List<ItemStack> ctRemoved;
    private static HashMap<ItemStack, Float> ctAdded;

    public static void removeCTCatalyst(ItemStack stack) {
        ctRemoved.add(stack);
    }

    public static void addCTCatalyst(ItemStack stack, float value) {
        ctAdded.put(stack, value);
    }

    public static void ctRecipes() {
        for (ItemStack s : ctRemoved)
            removeCatalyst(s);
        for (ItemStack s : ctAdded.keySet())
            addCatalyst(s, ctAdded.get(s));
    }
}
