package com.bartz24.skyresources.alchemy.item;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.base.gui.GuiCasing;
import com.bartz24.skyresources.base.gui.SlotSpecial;
import com.bartz24.skyresources.base.item.ItemMachine;
import com.bartz24.skyresources.base.tile.TileCasing;
import com.bartz24.skyresources.events.ClientEventHandler;
import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;
import com.bartz24.skyresources.registry.ModCreativeTabs;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.InvWrapper;

public class ItemCondenser extends ItemMachine
{
	public ItemCondenser()
	{
		super("condenser", ModCreativeTabs.tabAlchemy, false, false, true, true);
	}

	public void update(World world, BlockPos pos, ItemStack machineStack, NBTTagCompound data)
	{
		condense(world, pos, machineStack, data);
	}

	void condense(World world, BlockPos pos, ItemStack machineStack, NBTTagCompound data)
	{
		int timeCondense = data.getInteger("time");
		float itemLeft = data.getFloat("left");
		ItemStack itemUsing = new ItemStack(data.getCompoundTag("itemUsing"));
		Random rand = world.rand;
		Block block = getBlockAbove(world, pos);
		if (getCasingTile(world, pos).getRedstoneSignal() == 0 && block != Blocks.AIR)
		{
			Object blockIn = null;
			if (block instanceof BlockFluidBase)
			{
				BlockFluidBase fluidBlock = (BlockFluidBase) block;
				if (fluidBlock.getMetaFromState(world.getBlockState(pos.up())) == 0)
					blockIn = new FluidStack(fluidBlock.getFluid(), 1000);
			} else if (block instanceof IFluidBlock)
			{
				IFluidBlock fluidBlock = (IFluidBlock) block;
				blockIn = new FluidStack(fluidBlock.getFluid(), 1000);
			} else
				blockIn = new ItemStack(block, 1, block.getMetaFromState(world.getBlockState(pos.up())));

			ItemStack dust = itemLeft > 0 ? itemUsing : this.getCasingTile(world, pos).getInventory().getStackInSlot(0);
			ProcessRecipe recipe = null;
			if (blockIn != null)
				recipe = ProcessRecipeManager.condenserRecipes.getRecipe(Arrays.asList(dust, blockIn),
						Integer.MAX_VALUE, false, false);

			if (recipe != null)
			{
				if (timeCondense < getTimeToCondense(world, pos, machineStack, recipe))
				{
					if (itemLeft <= 0)
					{
						if (!world.isRemote)
						{
							itemLeft = 1;
							if (!itemUsing.isItemEqual(this.getCasingTile(world, pos).getInventory().getStackInSlot(0)))
								timeCondense = 0;
							itemUsing = this.getCasingTile(world, pos).getInventory().getStackInSlot(0).copy();
							itemUsing.setCount(1);
							this.getCasingTile(world, pos).getInventory().getStackInSlot(0).shrink(1);
						}
					}
					world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + rand.nextFloat(),
							pos.getY() + 1.5D, pos.getZ() + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
					if (!world.isRemote)
					{
						timeCondense++;
						itemLeft -= Math.pow(recipe.getIntParameter(), 1.3f) / 50f / (2400f * recipe.getIntParameter()
								/ 50f * this.getMachineEfficiency(machineStack, world, pos));
					}
				}
			} else if (!world.isRemote)
				timeCondense = 0;

			boolean sound = false;
			if (recipe != null && timeCondense >= getTimeToCondense(world, pos, machineStack, recipe)
					&& !world.isRemote)
			{
				ItemStack stack = recipe.getOutputs().get(0).copy();
				if (ejectResultSlot(stack, world, pos, true))
				{
					sound = true;
					world.setBlockToAir(pos.add(0, 1, 0));
					ejectResultSlot(stack, world, pos, false);
					timeCondense = 0;
				}
			}
			if (sound)
				world.playSound(null, pos, SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.BLOCKS, 1.0F,
						2.2F / (rand.nextFloat() * 0.2F + 0.9F));
		}
		data.setInteger("time", timeCondense);
		data.setFloat("left", itemLeft);
		data.setTag("itemUsing", itemUsing.writeToNBT(new NBTTagCompound()));
	}

	boolean ejectResultSlot(ItemStack output, World world, BlockPos pos, boolean sim)
	{
		if (!world.isRemote)
		{
			ItemStack out = output.copy();
			BlockPos facingPos = pos.down();

			TileEntity tile = world.getTileEntity(facingPos);

			if (tile != null)
			{
				if (tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP))
				{
					return ItemHandlerHelper.insertItemStacked(
							tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP), out, sim)
							.isEmpty();
				} else if (tile instanceof IInventory)
				{
					return ItemHandlerHelper.insertItemStacked(new InvWrapper((IInventory) tile), out, sim).isEmpty();
				}
				return false;
			}

			if (!sim && out != ItemStack.EMPTY && out.getCount() > 0)
			{
				EntityItem item = new EntityItem(world, pos.down().getX() + 0.5f, pos.down().getY() + 0.5f,
						pos.down().getZ() + 0.5f, out.copy());
				item.motionY = 0;
				item.motionX = 0;
				item.motionZ = 0;
				world.spawnEntity(item);
			}
		}
		return true;
	}

	public int getTimeToCondense(World world, BlockPos pos, ItemStack machineStack, ProcessRecipe recipe)
	{
		return Math.round(recipe.getIntParameter() / this.getMachineSpeed(machineStack, world, pos));
	}

	public Block getBlockAbove(World world, BlockPos pos)
	{
		return world.getBlockState(pos.add(0, 1, 0)).getBlock();
	}

	public int getItemSlots(ItemStack stack)
	{
		return 1;
	}

	public Integer[] getExtractBlacklist(ItemStack stack)
	{
		return new Integer[] { 0 };
	}

	public List<Slot> getSlots(TileCasing tile)
	{
		return Collections.singletonList(new SlotSpecial(tile.getInventory(), 0, 80, 53));
	}

	public void initGui(GuiCasing gui, List buttonList)
	{
		ClientEventHandler.initGui(gui, buttonList);
	}

	public void actionPerformed(TileCasing tile, GuiCasing gui, int buttonClicked) throws IOException
	{
		ClientEventHandler.actionPerformed(0, tile, gui, buttonClicked);
	}

	public void drawBackgroundGui(TileCasing tile, GuiCasing gui, FontRenderer fontRenderer, int mouseX, int mouseY)
	{
		super.drawBackgroundGui(tile, gui, fontRenderer, mouseX, mouseY);

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		Minecraft.getMinecraft().getTextureManager()
				.bindTexture(new ResourceLocation(References.ModID, "textures/gui/blankInventory.png"));

		gui.drawTexturedModalRect(gui.getGuiLeft() + 79, gui.getGuiTop() + 52, 7, 83, 18, 18);
	}

	public void drawForegroundGui(TileCasing tile, GuiCasing gui, FontRenderer fontRenderer, int mouseX, int mouseY)
	{

		int timeCondense = tile.machineData.getInteger("time");
		float itemLeft = tile.machineData.getFloat("left");
		ItemStack itemUsing = new ItemStack(tile.machineData.getCompoundTag("itemUsing"));
		Object blockIn;
		Block block = getBlockAbove(tile.getWorld(), tile.getPos());
		if (block instanceof IFluidBlock)
		{
			IFluidBlock fluidBlock = (IFluidBlock) block;
			blockIn = new FluidStack(fluidBlock.getFluid(), 1000);
		} else
			blockIn = new ItemStack(block, 1,
					block.getMetaFromState(tile.getWorld().getBlockState(tile.getPos().up())));

		ItemStack dust = itemLeft > 0 ? itemUsing : tile.getInventory().getStackInSlot(0);

		ProcessRecipe recipe = ProcessRecipeManager.condenserRecipes.getRecipe(Arrays.asList(dust, blockIn),
				Integer.MAX_VALUE, false, false);

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		Minecraft.getMinecraft().getTextureManager()
				.bindTexture(new ResourceLocation(References.ModID, "textures/gui/guiicons.png"));
		gui.drawTexturedModalRect(100, 52, 29, 60, 3, 18);

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		Minecraft.getMinecraft().getTextureManager()
				.bindTexture(new ResourceLocation(References.ModID, "textures/gui/guiicons.png"));
		gui.drawTexturedModalRect(101, 69 - (int) (16f * itemLeft), 26, 77 - (int) (16f * itemLeft), 1,
				(int) (16f * itemLeft));

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		Minecraft.getMinecraft().getTextureManager()
				.bindTexture(new ResourceLocation(References.ModID, "textures/gui/guiicons.png"));
		gui.drawTexturedModalRect(80, 27, 18, 80, 16, 24);

		if (recipe != null)
		{
			GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
			Minecraft.getMinecraft().getTextureManager()
					.bindTexture(new ResourceLocation(References.ModID, "textures/gui/guiicons.png"));
			gui.drawTexturedModalRect(80,
					51 - (int) ((float) timeCondense
							/ (float) getTimeToCondense(tile.getWorld(), tile.getPos(), tile.machineStored, recipe)
							* 24f),
					1,
					104 - (int) ((float) timeCondense
							/ (float) getTimeToCondense(tile.getWorld(), tile.getPos(), tile.machineStored, recipe)
							* 24f),
					16,
					(int) ((float) timeCondense
							/ (float) getTimeToCondense(tile.getWorld(), tile.getPos(), tile.machineStored, recipe)
							* 24f));
		}

		super.drawForegroundGui(tile, gui, fontRenderer, mouseX, mouseY);
	}
}
