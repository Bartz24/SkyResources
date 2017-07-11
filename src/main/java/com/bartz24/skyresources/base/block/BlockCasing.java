package com.bartz24.skyresources.base.block;

import java.util.List;

import javax.annotation.Nullable;

import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.References;
import com.bartz24.skyresources.SkyResources;
import com.bartz24.skyresources.base.MachineVariants;
import com.bartz24.skyresources.base.item.ItemMachine;
import com.bartz24.skyresources.base.tile.TileCasing;
import com.bartz24.skyresources.registry.ModCreativeTabs;
import com.bartz24.skyresources.registry.ModGuiHandler;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCasing extends BlockContainer implements IMetaBlockName
{
	public static final PropertyEnum<MachineVariants> variant = PropertyEnum.create("variant", MachineVariants.class);

	public BlockCasing(String unlocalizedName, String registryName, float hardness, float resistance)
	{
		super(Material.ROCK);
		this.setUnlocalizedName(References.ModID + "." + unlocalizedName);
		this.setCreativeTab(ModCreativeTabs.tabMain);
		this.setHardness(hardness);
		this.setResistance(resistance);
		this.setRegistryName(registryName);
		this.isBlockContainer = true;
	}

	public Material getMaterial(IBlockState state)
	{
		return this.getMetaFromState(state) == 0 ? Material.WOOD : Material.ROCK;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileCasing();
	}

	@Override
	public BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, variant);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(variant).ordinal();
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return getMetaFromState(state);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		if (meta >= MachineVariants.values().length || meta < 0)
		{
			meta = 0;
		}
		return getDefaultState().withProperty(variant, MachineVariants.values()[meta]);
	}

	@Override
	public void getSubBlocks(CreativeTabs par2, NonNullList<ItemStack> par3)
	{
		for (int i = 0; i < MachineVariants.values().length; i++)
			par3.add(new ItemStack(this, 1, i));
	}

	@Override
	public String getSpecialName(ItemStack stack)
	{
		return MachineVariants.values()[stack.getMetadata()].getName();
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
			EntityPlayer player)
	{
		return new ItemStack(this, 1, this.getMetaFromState(world.getBlockState(pos)));
	}

	public MachineVariants getVariant(ItemStack stack)
	{
		return MachineVariants.values()[stack.getMetadata()];
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		tooltip.add(TextFormatting.GREEN + "Efficiency: "
				+ (int) (Math.ceil(getVariant(stack).getRawEfficiency() * 10f) * 10f) + "%");

		tooltip.add(TextFormatting.RED + "Max HU: " + getVariant(stack).getMaxHeat());
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			if (((TileCasing) world.getTileEntity(pos)).machineStored.isEmpty())
			{
				if (player.getHeldItem(hand).getItem() instanceof ItemMachine)
				{
					ItemStack machine = player.getHeldItem(hand).copy();
					machine.setCount(1);
					((TileCasing) world.getTileEntity(pos)).setMachine(machine, player);
					player.getHeldItem(hand).shrink(1);
				}
				return true;
			} else
			{
				if (player.isSneaking() && player.getHeldItem(hand).isEmpty())
				{
					RandomHelper.spawnItemInWorld(world, ((TileCasing) world.getTileEntity(pos)).machineStored,
							new BlockPos(player.posX, player.posY, player.posZ));
					((TileCasing) world.getTileEntity(pos)).setMachine(ItemStack.EMPTY, player);
				} else
					player.openGui(SkyResources.instance, ModGuiHandler.CasingGUI, world, pos.getX(), pos.getY(),
							pos.getZ());
				return true;
			}
		}
		return true;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		TileCasing te = (TileCasing) world.getTileEntity(pos);
		te.dropInventory();

		super.breakBlock(world, pos, state);
	}
}