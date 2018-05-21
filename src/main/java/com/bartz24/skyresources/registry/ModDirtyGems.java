package com.bartz24.skyresources.registry;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ModDirtyGems {

    public static final Map<String, GemData> gemDataMap = defaultDataMap();

    private static Map<String, GemData> defaultDataMap() {
        Map<String, GemData> data = new HashMap();
        data.put("coal", new GemData("gemCoal", .03f, new Color(38, 38, 38)));
        data.put("emerald", new GemData("gemCoal", .03f, new Color(18, 219, 58)));
        data.put("diamond", new GemData("gemCoal", .03f, new Color(107, 255, 253)));
        data.put("quartz", new GemData("gemCoal", .03f, new Color(255, 255, 255)));
        data.put("lapis", new GemData("gemCoal", .03f, new Color(7, 91, 186)));
        data.put("ruby", new GemData("gemCoal", .03f, new Color(250, 30, 30)));
        data.put("sapphire", new GemData("gemCoal", .03f, new Color(30, 70, 250)));
        data.put("peridot", new GemData("gemCoal", .03f, new Color(28, 184, 0)));
        data.put("redgarnet", new GemData("gemCoal", .03f, new Color(201, 0, 20)));
        data.put("yellowgarnet", new GemData("gemCoal", .03f, new Color(247, 255, 15)));
        data.put("apatite", new GemData("gemCoal", .03f, new Color(43, 149, 255)));
        data.put("amber", new GemData("gemCoal", .03f, new Color(245, 204, 83)));
        data.put("lepidolite", new GemData("gemCoal", .03f, new Color(87, 0, 138)));
        data.put("malachite", new GemData("gemCoal", .03f, new Color(35, 173, 0)));
        data.put("onyx", new GemData("gemCoal", .03f, new Color(61, 61, 61)));
        data.put("moldavite", new GemData("gemCoal", .03f, new Color(173, 255, 153)));
        data.put("agate", new GemData("gemCoal", .03f, new Color(255, 99, 255)));
        data.put("opal", new GemData("gemCoal", .03f, new Color(222, 222, 222)));
        data.put("amethyst", new GemData("gemCoal", .03f, new Color(120, 0, 120)));
        data.put("jasper", new GemData("gemCoal", .03f, new Color(135, 72, 0)));
        data.put("aquamarine", new GemData("gemCoal", .03f, new Color(54, 231, 255)));
        data.put("heliodor", new GemData("gemCoal", .03f, new Color(255, 255, 125)));
        data.put("turquoise", new GemData("gemCoal", .03f, new Color(46, 242, 200)));
        data.put("moonstone", new GemData("gemCoal", .03f, new Color(1, 106, 138)));
        data.put("morganite", new GemData("gemCoal", .03f, new Color(250, 97, 255)));
        data.put("carnelian", new GemData("gemCoal", .03f, new Color(99, 6, 6)));
        data.put("beryl", new GemData("gemCoal", .03f, new Color(70, 227, 52)));
        data.put("goldenberyl", new GemData("gemCoal", .03f, new Color(214, 174, 43)));
        data.put("citrine", new GemData("gemCoal", .03f, new Color(135, 22, 22)));
        data.put("indicolite", new GemData("gemCoal", .03f, new Color(57, 230, 189)));
        data.put("garnet", new GemData("gemCoal", .03f, new Color(255, 153, 153)));
        data.put("topaz", new GemData("gemCoal", .03f, new Color(255, 211, 153)));
        data.put("ametrine", new GemData("gemCoal", .03f, new Color(163, 0, 191)));
        data.put("tanzanite", new GemData("gemCoal", .03f, new Color(0, 7, 110)));
        data.put("violetsapphire", new GemData("gemCoal", .03f, new Color(69, 18, 135)));
        data.put("alexandrite", new GemData("gemCoal", .03f, new Color(227, 227, 227)));
        data.put("bluetopaz", new GemData("gemCoal", .03f, new Color(16, 0, 196)));
        data.put("spinel", new GemData("gemCoal", .03f, new Color(117, 0, 0)));
        data.put("iolite", new GemData("gemCoal", .03f, new Color(149, 2, 207)));
        data.put("blackdiamond", new GemData("gemCoal", .03f, new Color(38, 38, 38)));
        data.put("chaos", new GemData("gemCoal", .03f, new Color(255, 230, 251)));
        data.put("enderessence", new GemData("gemCoal", .03f, new Color(53, 110, 25)));
        data.put("dark", new GemData("gemCoal", .03f, new Color(36, 36, 36)));
        data.put("quartzblack", new GemData("gemCoal", .03f, new Color(23, 23, 23)));
        data.put("certus", new GemData("gemCoal", .03f, new Color(176, 244, 247)));
    }

    public static class GemData {
        Color color;
        float dropChance;
        String oreDictType;
        ItemStack parentBlock;

        public GemData(String oreDictItem, float dropChance, Color color) {
            this(oreDictItem, dropChance, color, new ItemStack(Blocks.STONE));
        }

        public GemData(String oreDictItem, float dropChance, Color color, ItemStack parentBlock) {
            this.parentBlock = parentBlock;
            this.oreDictType = oreDictItem;
            this.color = color;
            this.dropChance = dropChance;
        }
    }

}
