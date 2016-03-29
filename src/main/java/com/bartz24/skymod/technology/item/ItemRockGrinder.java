package com.bartz24.skymod.technology.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.bartz24.skymod.RandomHelper;
import com.bartz24.skymod.References;
import com.bartz24.skymod.registry.ModCreativeTabs;
import com.google.common.collect.Multimap;

public class ItemRockGrinder extends Item
{
	private float damageVsEntity;
	
	ToolMaterial toolMaterial;

	public ItemRockGrinder(ToolMaterial material, String unlocalizedName,
			String registryName)
	{
		toolMaterial = material;
		this.setMaxDamage((int) (material.getMaxUses() * 1.4F));
		this.damageVsEntity = 2.5F + material.getDamageVsEntity();
		this.setUnlocalizedName(References.ModID + "." + unlocalizedName);
		setRegistryName(registryName);
		this.setMaxStackSize(1);
		this.setNoRepair();
		this.setCreativeTab(ModCreativeTabs.tabTech);
		this.setHarvestLevel("rockGrinder", material.getHarvestLevel());
	}

	public float getStrVsBlock(ItemStack stack, IBlockState state)
	{
		Block block = state.getBlock();
		Material material = block.getMaterial(state);
		if(toolMaterial.getHarvestLevel()<block.getHarvestLevel(state)) return 0.5F;
		return material != Material.rock ? 1.0F : toolMaterial.getEfficiencyOnProperMaterial();
	}

	@Override
	public boolean onBlockStartBreak(ItemStack item, BlockPos pos,
			EntityPlayer player)
	{
		World world = player.worldObj;
		IBlockState state = world.getBlockState(pos);

		if (state.getBlock() == Blocks.cobblestone
				|| state.getBlock() == Blocks.sandstone)
		{
			if (!world.isRemote)
			{
				if (state.getBlock() == Blocks.cobblestone)
					RandomHelper.spawnItemInWorld(world, new ItemStack(
							Blocks.sand), pos);
				else if (state.getBlock() == Blocks.sandstone)
					RandomHelper.spawnItemInWorld(world, new ItemStack(
							Blocks.gravel), pos);
				world.destroyBlock(pos, false);
			}

			item.damageItem(1, player);
			return true;
		}

		return false;
	}

	public Multimap<String, AttributeModifier> getItemAttributeModifiers(
			EntityEquipmentSlot equipmentSlot)
	{

		Multimap<String, AttributeModifier> multimap = super
				.getItemAttributeModifiers(equipmentSlot);

		if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
		{
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE
					.getAttributeUnlocalizedName(), new AttributeModifier(
					ATTACK_DAMAGE_MODIFIER, "Weapon modifier",
					(double) this.damageVsEntity, 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED
					.getAttributeUnlocalizedName(), new AttributeModifier(
					ATTACK_SPEED_MODIFIER, "Weapon modifier",
					-2.4000000953674316D, 0));
		}

		return multimap;
	}
}
