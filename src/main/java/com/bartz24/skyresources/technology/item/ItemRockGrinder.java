package com.bartz24.skyresources.technology.item;

import com.bartz24.skyresources.ItemHelper;
import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.References;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.registry.ModCreativeTabs;
import com.bartz24.skyresources.technology.rockgrinder.RockGrinderRecipe;
import com.bartz24.skyresources.technology.rockgrinder.RockGrinderRecipes;
import com.google.common.collect.Multimap;

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

public class ItemRockGrinder extends Item
{
	private float damageVsEntity;

	ToolMaterial toolMaterial;

	public ItemRockGrinder(ToolMaterial material, String unlocalizedName,
			String registryName)
	{
		toolMaterial = material;
		this.setMaxDamage((int) (material.getMaxUses() * ConfigOptions.rockGrinderBaseDurability));
		this.damageVsEntity = ConfigOptions.rockGrinderBaseDamage + material.getDamageVsEntity();
		this.setUnlocalizedName(References.ModID + "." + unlocalizedName);
		setRegistryName(registryName);
		this.setMaxStackSize(1);
		this.setCreativeTab(ModCreativeTabs.tabTech);
		this.setHarvestLevel("rockGrinder", material.getHarvestLevel());

		ItemHelper.addRockGrinder(this);
	}

	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState state)
	{
		Block block = state.getBlock();
		Material material = block.getMaterial(state);
		if (toolMaterial.getHarvestLevel() < block.getHarvestLevel(state))
			return 0.5F;
		return material != Material.rock ? 1.0F
				: toolMaterial.getEfficiencyOnProperMaterial();
	}

	@Override
	public boolean onBlockStartBreak(ItemStack item, BlockPos pos,
			EntityPlayer player)
	{
		World world = player.worldObj;
		IBlockState state = world.getBlockState(pos);
		item.attemptDamageItem(1, this.itemRand);

			if (!world.isRemote)
			{
				RockGrinderRecipe recipe = RockGrinderRecipes.getRecipe(state);

				if (recipe != null && recipe.getOutput() != null)
				{
					RandomHelper.spawnItemInWorld(world,
							recipe.getOutput().copy(), pos);

					world.destroyBlock(pos, false);
				} else
					world.destroyBlock(pos, true);
				return true;
			}


		return false;
	}

	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(
			EntityEquipmentSlot equipmentSlot)
	{

		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(
				equipmentSlot);

		if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
		{
			multimap.put(
					SharedMonsterAttributes.ATTACK_DAMAGE
							.getAttributeUnlocalizedName(),
					new AttributeModifier(ATTACK_DAMAGE_MODIFIER,
							"Weapon modifier", this.damageVsEntity, 0));
			multimap.put(
					SharedMonsterAttributes.ATTACK_SPEED
							.getAttributeUnlocalizedName(),
					new AttributeModifier(ATTACK_SPEED_MODIFIER,
							"Weapon modifier", -2.4000000953674316D, 0));
		}

		return multimap;
	}
}
