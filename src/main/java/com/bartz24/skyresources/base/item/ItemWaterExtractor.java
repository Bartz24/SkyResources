package com.bartz24.skyresources.base.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;
import com.bartz24.skyresources.registry.ModCreativeTabs;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class ItemWaterExtractor extends Item implements IFluidHandler
{
	public static final int maxAmount = ConfigOptions.toolSettings.waterExtractorCapacity;

	FluidTank tank;

	public static final String[] extractorIcons = new String[] { "empty", "full1", "full2", "full3", "full4", "full5",
			"full6" };

	public ItemWaterExtractor()
	{
		this.maxStackSize = 1;
		this.setCreativeTab(ModCreativeTabs.tabMain);
		tank = new FluidTank(new FluidStack(FluidRegistry.WATER, 0), maxAmount);
		setUnlocalizedName(References.ModID + ".waterExtractor");
		setRegistryName("waterextractor");
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.BOW;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 70000;
	}

	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn)
	{
		return stack;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		player.setActiveHand(hand);
		return new ActionResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entity, int timeLeft)
	{
		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;
			if (!world.isRemote && timeLeft <= getMaxItemUseDuration(stack) - 25)
			{
				Vec3d vec3d = player.getPositionVector().addVector(0, player.eyeHeight, 0);
				Vec3d vec3d1 = player.getLookVec();
				Vec3d vec3d2 = vec3d.addVector(vec3d1.x * 5, vec3d1.y * 5, vec3d1.z * 5);
				RayTraceResult rayTrace = world.rayTraceBlocks(vec3d, vec3d2, false, false, true);

				if ((rayTrace != null))
				{
					BlockPos pos = rayTrace.getBlockPos();

					EnumFacing blockHitSide = rayTrace.sideHit;

					Block block = world.getBlockState(pos).getBlock();

					ProcessRecipe recipe = ProcessRecipeManager.waterExtractorExtractRecipes.getRecipe(
							new ItemStack(block, 1, block.getMetaFromState(world.getBlockState(pos))), 0, false, false);

					if (recipe != null)
					{
						IBlockState recipeOut = Block.getBlockFromItem(recipe.getOutputs().get(0).getItem())
								.getStateFromMeta(recipe.getOutputs().get(0).getMetadata());
						getCompound(stack).setInteger("amount", getCompound(stack).getInteger("amount")
								+ tank.fill(recipe.getFluidOutputs().get(0).copy(), true));
						tank.getFluid().amount = getCompound(stack).getInteger("amount");
						world.setBlockState(pos, recipe.getOutputs().get(0) == ItemStack.EMPTY
								? Blocks.AIR.getDefaultState() : recipeOut, 3);
						world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ,
								SoundEvents.ENTITY_PLAYER_SPLASH, SoundCategory.NEUTRAL, 1.0F,
								1.0F / (itemRand.nextFloat() * 0.4F + 1.2F));
						return;
					}
					if (world.getBlockState(pos.add(blockHitSide.getDirectionVec())).getBlock() == Blocks.WATER
							&& getCompound(stack).getInteger("amount") < maxAmount)
					{
						world.setBlockToAir(pos.add(blockHitSide.getDirectionVec()));
						getCompound(stack).setInteger("amount", getCompound(stack).getInteger("amount") + 1000);
						world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ,
								SoundEvents.ENTITY_PLAYER_SPLASH, SoundCategory.NEUTRAL, 1.0F,
								1.0F / (itemRand.nextFloat() * 0.4F + 1.2F));
					}

				}
			}
		}
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer playerIn, World world, BlockPos pos, EnumHand hand, EnumFacing side,
			float hitX, float hitY, float hitZ)
	{
		IBlockState iblockstate = world.getBlockState(pos);
		Block block = iblockstate.getBlock();
		ItemStack stack = playerIn.getHeldItem(hand);
		tank.setFluid(new FluidStack(FluidRegistry.WATER, getCompound(stack).getInteger("amount")));
		if (world.getTileEntity(pos) instanceof IFluidHandler)
		{
			IFluidHandler tile = (IFluidHandler) world.getTileEntity(pos);
			getCompound(stack).setInteger("amount",
					stack.getTagCompound().getInteger("amount") - tile.fill(tank.getFluid(), true));
			tank.getFluid().amount = getCompound(stack).getInteger("amount");
			return EnumActionResult.SUCCESS;
		}

		ProcessRecipe recipe = ProcessRecipeManager.waterExtractorInsertRecipes.getRecipe(new ArrayList<Object>(
				Arrays.asList(new ItemStack(block, 1, block.getMetaFromState(world.getBlockState(pos))),
						tank.getFluid().copy())),
				0, false, false);

		if (recipe != null)
		{
			IBlockState recipeOut = Block.getBlockFromItem(recipe.getOutputs().get(0).getItem())
					.getStateFromMeta(recipe.getOutputs().get(0).getMetadata());
			world.setBlockState(pos, recipeOut, 3);
			getCompound(stack).setInteger("amount",
					stack.getTagCompound().getInteger("amount") - recipe.getFluidInputs().get(0).amount);
			tank.getFluid().amount = getCompound(stack).getInteger("amount");
			world.playSound((EntityPlayer) null, playerIn.posX, playerIn.posY, playerIn.posZ,
					SoundEvents.ENTITY_PLAYER_SPLASH, SoundCategory.NEUTRAL, 1.0F,
					1.0F / (itemRand.nextFloat() * 0.4F + 1.2F));

			return EnumActionResult.SUCCESS;
		}

		// Place water
		if (getCompound(stack).getInteger("amount") >= 1000 && playerIn.isSneaking())
		{
			if (block == Blocks.SNOW_LAYER && iblockstate.getValue(BlockSnow.LAYERS).intValue() < 1)
			{
				side = EnumFacing.UP;
			} else if (!block.isReplaceable(world, pos))
			{
				pos = pos.offset(side);
			}

			if (!playerIn.canPlayerEdit(pos, side, stack) || stack.getCount() == 0)
			{
				return EnumActionResult.FAIL;
			} else
			{
				if (FluidUtil.tryPlaceFluid(playerIn, world, pos, tank, tank.getFluid()))
				{
					getCompound(stack).setInteger("amount", tank.getFluid() == null ? 0 : tank.getFluid().amount);
					return EnumActionResult.SUCCESS;
				}

				return EnumActionResult.FAIL;
			}
		}
		return EnumActionResult.FAIL;
	}

	public NBTTagCompound getCompound(ItemStack stack)
	{
		NBTTagCompound com = stack.getTagCompound();
		if (com == null)
			onCreated(stack, null, null);
		com = stack.getTagCompound();

		return com;
	}

	@Override
	public void onCreated(ItemStack itemStack, World world, EntityPlayer player)
	{
		itemStack.setTagCompound(new NBTTagCompound());
		itemStack.getTagCompound().setInteger("amount", 0);
		tank.getFluid().amount = itemStack.getTagCompound().getInteger("amount");

	}

	@Override
	public IFluidTankProperties[] getTankProperties()
	{
		return tank.getTankProperties();
	}

	@Override
	public int fill(FluidStack resource, boolean doFill)
	{
		if (resource != null)
		{
			int filled = tank.fill(resource, doFill);

			return filled;
		}

		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain)
	{
		if (resource != null)
		{
			return tank.drain(resource.amount, doDrain);
		}

		return null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain)
	{
		return tank.drain(maxDrain, doDrain);
	}

	public FluidTank getTank()
	{
		return tank;
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if (stack.getTagCompound() != null)
		{
			tooltip.add("Water: " + stack.getTagCompound().getInteger("amount") + " mB");
		} else
			tooltip.add("Water: 0 mB");
	}

	public int getMaxAmount()
	{
		return maxAmount;
	}
}
