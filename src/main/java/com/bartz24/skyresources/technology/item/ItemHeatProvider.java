package com.bartz24.skyresources.technology.item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.References;
import com.bartz24.skyresources.base.MachineVariants;
import com.bartz24.skyresources.base.gui.GuiCasing;
import com.bartz24.skyresources.base.gui.SlotSpecial;
import com.bartz24.skyresources.base.item.ItemMachine;
import com.bartz24.skyresources.base.tile.TileCasing;
import com.bartz24.skyresources.registry.ModCreativeTabs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class ItemHeatProvider extends ItemMachine
{
	public ItemHeatProvider()
	{
		super("heatprovider", ModCreativeTabs.tabTech, false, true, true, true);
	}

	public void update(World world, BlockPos pos, ItemStack machineStack, NBTTagCompound data)
	{
		if (!world.isRemote)
		{
			if (this.getCasingTile(world, pos).getRedstoneSignal() == 0 && data.getFloat("itemHU") <= 0)
				getFuel(machineStack, world, pos, data);
			if (data.getFloat("itemHU") <= 0)
				data.setFloat("huTick", 0);
			else
				data.setFloat("itemHU", data.getFloat("itemHU") - 1);
		}
	}

	public String[] getFuelDisplay(ItemStack stack, @Nullable World world, BlockPos pos)
	{
		String[] list = super.getFuelDisplay(stack, world, pos);
		list[1] = getHUPerTick(stack, world, pos, useSpeedInfo) + " Heat Value";
		return list;
	}

	public boolean isValidFuel(ItemStack machineStack, Object stack)
	{
		if (getVariant(machineStack).getFuelType().equals("Fuel") && stack instanceof ItemStack
				&& TileEntityFurnace.getItemBurnTime((ItemStack) stack) <= 0)
			return false;
		else if (getVariant(machineStack).getFuelType() instanceof ItemStack && stack instanceof ItemStack
				&& !((ItemStack) getVariant(machineStack).getFuelType()).isItemEqual((ItemStack) stack))
			return false;
		else if (getVariant(machineStack).getFuelType() instanceof Fluid && stack instanceof FluidStack
				&& (Fluid) getVariant(machineStack).getFuelType() != ((FluidStack) stack).getFluid())
			return false;
		return true;
	}

	private void getFuel(ItemStack machineStack, World world, BlockPos pos, NBTTagCompound data)
	{
		if (getVariant(machineStack).getFuelType().equals("RF"))
		{
			float extract = this.getCasingTile(world, pos)
					.internalExtractEnergy((int) Math.ceil(getMachineFuelData(machineStack, world, pos)[1]), false);
			if (extract > 0)
			{
				data.setFloat("itemHU", (float) (extract / getMachineFuelData(machineStack, world, pos)[1])
						/ getMachineFuelData(machineStack, world, pos)[2]);
				data.setFloat("huTick", getMachineFuelData(machineStack, world, pos)[0]);
			}
		} else if (getVariant(machineStack).getFuelType() instanceof ItemStack
				&& isValidFuel(machineStack, this.getCasingTile(world, pos).getInventory().getStackInSlot(0)))
		{

			data.setFloat("itemHU",
					getMachineFuelData(machineStack, world, pos)[1] * getMachineFuelData(machineStack, world, pos)[0]);
			data.setFloat("huTick", getMachineFuelData(machineStack, world, pos)[0]);
			this.getCasingTile(world, pos).getInventory().getStackInSlot(0).shrink(1);
		} else if (getVariant(machineStack).getFuelType() instanceof Fluid
				&& isValidFuel(machineStack, this.getCasingTile(world, pos).getTank().getFluid())
				&& this.getCasingTile(world, pos).getTank().drainInternal(1, false) != null
				&& this.getCasingTile(world, pos).getTank().drainInternal(1, true).amount > 0)
		{
			data.setFloat("itemHU",
					getMachineFuelData(machineStack, world, pos)[1] * getMachineFuelData(machineStack, world, pos)[0]);
			data.setFloat("huTick", getMachineFuelData(machineStack, world, pos)[0]);
		} else if (getVariant(machineStack).getFuelType().equals("Fuel")
				&& isValidFuel(machineStack, this.getCasingTile(world, pos).getInventory().getStackInSlot(0)))
		{
			float huStored = getMachineFuelData(machineStack, world, pos)[1]
					* getMachineFuelData(machineStack, world, pos)[0] * TileEntityFurnace
							.getItemBurnTime(this.getCasingTile(world, pos).getInventory().getStackInSlot(0));
			System.out.println(huStored + ", " + getMachineFuelData(machineStack, world, pos)[0]);
			data.setFloat("itemHU",
					getMachineFuelData(machineStack, world, pos)[1] * getMachineFuelData(machineStack, world, pos)[0]
							* TileEntityFurnace
									.getItemBurnTime(this.getCasingTile(world, pos).getInventory().getStackInSlot(0)));
			data.setFloat("huTick", getMachineFuelData(machineStack, world, pos)[0]);
			this.getCasingTile(world, pos).getInventory().getStackInSlot(0).shrink(1);
		} else
		{
			data.setFloat("itemHU", 0);
			data.setFloat("huTick", 0);
		}
		data.setFloat("maxHU", data.getFloat("itemHU"));
	}

	public int getHeatProv(ItemStack stack, World world, BlockPos pos)
	{
		return (int) this.getCasingTile(world, pos).machineData.getFloat("huTick");
	}

	public List<Slot> getSlots(TileCasing tile)
	{
		if (getItemSlots(tile.machineStored) == 1)
			return Collections.singletonList(new SlotSpecial(tile.getInventory(), 0, 80, 53));
		return new ArrayList();
	}

	// RF Handler
	public int getMaxEnergy(ItemStack stack)
	{
		return getVariant(stack).values()[stack.getMetadata()].getFuelType().equals("RF") ? 100000 : 0;
	}

	public int getMaxReceive(ItemStack stack)
	{
		return getVariant(stack).values()[stack.getMetadata()].getFuelType().equals("RF") ? 10000 : 0;
	}

	// Item Handler
	public int getItemSlots(ItemStack stack)
	{
		MachineVariants variant = getVariant(stack).values()[stack.getMetadata()];
		return variant.getFuelType().equals("Fuel") || variant.getFuelType() instanceof ItemStack ? 1 : 0;
	}

	public Integer[] getExtractBlacklist(ItemStack stack)
	{
		return getItemSlots(stack) == 1 ? new Integer[] { 0 } : new Integer[0];
	}

	// Fluid Handler
	public Fluid getFluid(ItemStack stack)
	{
		MachineVariants variant = getVariant(stack).values()[stack.getMetadata()];
		return variant.getFuelType() instanceof Fluid ? (Fluid) variant.getFuelType() : null;
	}

	public int getMaxFluid(ItemStack stack)
	{
		return getFluid(stack) != null ? 4000 : 0;
	}

	public void drawBackgroundGui(TileCasing tile, GuiCasing gui, FontRenderer fontRenderer, int mouseX, int mouseY)
	{
		super.drawBackgroundGui(tile, gui, fontRenderer, mouseX, mouseY);

		if (getItemSlots(tile.machineStored) == 1)
		{
			GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
			Minecraft.getMinecraft().getTextureManager()
					.bindTexture(new ResourceLocation(References.ModID, "textures/gui/blankInventory.png"));

			gui.drawTexturedModalRect(gui.getGuiLeft() + 79, gui.getGuiTop() + 52, 7, 83, 18, 18);
		} else
		{
			GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
			Minecraft.getMinecraft().getTextureManager()
					.bindTexture(new ResourceLocation(References.ModID, "textures/gui/guiicons.png"));
			gui.drawTexturedModalRect(gui.getGuiLeft() + 83, gui.getGuiTop() + 24, 73, 0, 10, 45);
		}
	}

	public void drawForegroundGui(TileCasing tile, GuiCasing gui, FontRenderer fontRenderer, int mouseX, int mouseY)
	{
		fontRenderer.drawString(getHeatProv(tile.machineStored, tile.getWorld(), tile.getPos()) + " Heat", 19, 24,
				0xFFF3FF17);

		if (this.getMaxEnergy(tile.machineStored) > 0)
		{
			GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
			Minecraft.getMinecraft().getTextureManager()
					.bindTexture(new ResourceLocation(References.ModID, "textures/gui/guiicons.png"));
			gui.drawTexturedModalRect(84,
					68 - (int) (43f * (float) tile.getEnergyStored() / (float) this.getMaxEnergy(tile.machineStored)),
					51,
					59 - (int) (43f * (float) tile.getEnergyStored() / (float) this.getMaxEnergy(tile.machineStored)),
					8, (int) (43f * (float) tile.getEnergyStored() / (float) this.getMaxEnergy(tile.machineStored)));
		} else if (this.getMaxFluid(tile.machineStored) > 0)
		{
			RandomHelper.renderGuiTank(tile.getTank().getFluid(), this.getMaxFluid(tile.machineStored),
					tile.getTank().getFluidAmount(), 84, 25, 8, 43);
		}

		super.drawForegroundGui(tile, gui, fontRenderer, mouseX, mouseY);
	}
}
