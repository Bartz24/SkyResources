package com.bartz24.skyresources.technology.item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
		super("heatprovider", ModCreativeTabs.tabTech, false, true, false, true);
	}

	public void update(World world, BlockPos pos, ItemStack machineStack, NBTTagCompound data)
	{
		if (!world.isRemote)
		{
			if (this.getCasingTile(world, pos).getRedstoneSignal() == 0 && data.getFloat("huTick") <= 0)
				getFuel(machineStack, world, pos, data);
			else
			{
				if (data.getFloat("huTick") <= 0)
					data.setFloat("itemHU", 0);
				data.setFloat("huTick", data.getFloat("huTick") - 1);
			}
		}
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
		if (getVariant(machineStack).getFuelType().equals("FE"))
		{
			float extract = this.getCasingTile(world, pos)
					.internalExtractEnergy((int) getMachineFuelPerTick(machineStack, world, pos), true);
			if (extract > getMachineFuelPerHeat(machineStack, world, pos))
			{
				this.getCasingTile(world, pos).internalExtractEnergy((int) extract, false);
				data.setFloat("itemHU", (float) Math.sqrt(getMachineFuelPerHeat(machineStack, world, pos)
						* getMachineEfficiency(machineStack, world, pos)));
				data.setFloat("huTick", 1);
				return;
			}
		} else if (getVariant(machineStack).getFuelType() instanceof ItemStack
				&& isValidFuel(machineStack, this.getCasingTile(world, pos).getInventory().getStackInSlot(0)))
		{
			data.setFloat("itemHU", getMachineFuelPerHeat(machineStack, world, pos) / 16f);
			this.getCasingTile(world, pos).getInventory().getStackInSlot(0).shrink(1);
			data.setFloat("huTick", this.getMachineFuelPerTick(machineStack, world, pos));
			return;
		} else if (getVariant(machineStack).getFuelType() instanceof Fluid
				&& isValidFuel(machineStack, this.getCasingTile(world, pos).getTank().getFluid())
				&& this.getCasingTile(world, pos).getTank().drainInternal(1, false) != null
				&& this.getCasingTile(world, pos).getTank().drainInternal(1, true).amount > 0)
		{
			data.setFloat("itemHU", getMachineFuelPerHeat(machineStack, world, pos) * 5);
			data.setFloat("huTick", 1);
			return;
		} else if (getVariant(machineStack).getFuelType().equals("Fuel")
				&& isValidFuel(machineStack, this.getCasingTile(world, pos).getInventory().getStackInSlot(0)))
		{
			data.setFloat("itemHU",
					(float) Math.sqrt(getMachineFuelPerHeat(machineStack, world, pos)
							* TileEntityFurnace
									.getItemBurnTime(this.getCasingTile(world, pos).getInventory().getStackInSlot(0))
							/ 1600f));
			this.getCasingTile(world, pos).getInventory().getStackInSlot(0).shrink(1);
			data.setFloat("huTick", this.getMachineFuelPerTick(machineStack, world, pos));
			return;
		}
		data.setFloat("itemHU", 0);
		data.setFloat("huTick", 0);
	}

	public int getHeatProv(ItemStack stack, World world, BlockPos pos)
	{
		return (int) Math.pow(this.getCasingTile(world, pos).machineData.getFloat("itemHU"), 0.8f);
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
		return getVariant(stack).values()[stack.getMetadata()].getFuelType().equals("FE") ? 100000 : 0;
	}

	public int getMaxReceive(ItemStack stack)
	{
		return getVariant(stack).values()[stack.getMetadata()].getFuelType().equals("FE") ? 10000 : 0;
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
