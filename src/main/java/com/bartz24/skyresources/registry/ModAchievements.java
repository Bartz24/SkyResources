package com.bartz24.skyresources.registry;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.AchievementPage;

public class ModAchievements
{
	public static final Achievement cactusNeedle = new Achievement("cactusNeedle", "CactusNeedle",
			0, 1, new ItemStack(ModItems.alchemyComponent, 1, 1), AchievementList.openInventory).registerStat();
	
	public static final Achievement firstSapling = new Achievement("firstSapling", "FirstSapling",
			2, 2, new ItemStack(Blocks.sapling, 1, 4), cactusNeedle).registerStat();
	
	public static final Achievement firstWater = new Achievement("firstWater", "FirstWater",
			4, 1, new ItemStack(ModItems.waterExtractor), firstSapling).registerStat();
	
	public static final Achievement lavaMelting = new Achievement("lavaMelting", "LavaMelting",
			4, 3, new ItemStack(ModBlocks.blazePowderBlock), firstSapling).registerStat();
	
	public static AchievementPage modAchievePage = new AchievementPage("Sky Resources", cactusNeedle, firstSapling, firstWater, lavaMelting);
}
