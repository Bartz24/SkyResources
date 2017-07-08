package com.bartz24.skyresources.base.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.base.MachineVariants;
import com.bartz24.skyresources.base.gui.GuiCasing;
import com.bartz24.skyresources.base.gui.GuiHelper;
import com.bartz24.skyresources.base.tile.TileCasing;
import com.google.common.base.Strings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMachine extends Item
{
	public boolean useHeatInfo;
	public boolean useFuelInfo;
	public boolean useSpeedInfo;
	public boolean useEfficiencyInfo;

	public ItemMachine(String baseName, CreativeTabs tab, boolean showHeatInfo, boolean showFuelInfo,
			boolean showSpeedInfo, boolean showEfficiencyInfo)
	{
		setUnlocalizedName(References.ModID + "." + baseName + ".");
		setRegistryName(baseName);
		setHasSubtypes(true);
		this.setCreativeTab(tab);
		this.useHeatInfo = showHeatInfo;
		this.useFuelInfo = showFuelInfo;
		this.useSpeedInfo = showSpeedInfo;
		this.useEfficiencyInfo = showEfficiencyInfo;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return super.getUnlocalizedName(stack) + MachineVariants.values()[stack.getMetadata()].getName();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs creativeTab, NonNullList<ItemStack> list)
	{
		if (isInCreativeTab(creativeTab))
			for (int i = 0; i < MachineVariants.values().length; i++)
				list.add(new ItemStack(this, 1, i));
	}

	public ItemStack getStack(MachineVariants variant)
	{
		return new ItemStack(this, 1, Arrays.asList(MachineVariants.values()).indexOf(variant));
	}

	public MachineVariants getVariant(ItemStack stack)
	{
		return MachineVariants.values()[stack.getMetadata()];
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if (useSpeedInfo)
		{
			tooltip.add(
					TextFormatting.BLUE + "Speed: " + (int) Math.ceil(getVariant(stack).getRawSpeed() * 100f) + "%");
		}
		if (useEfficiencyInfo)
		{
			tooltip.add(TextFormatting.GREEN + "Efficiency: "
					+ (int) Math.ceil(getVariant(stack).getRawEfficiency() * 100f) + "%");
		}
		if (useHeatInfo)
		{
			tooltip.add(TextFormatting.RED + "Max HU: " + getVariant(stack).getMaxHeat());
		}
		if (useFuelInfo)
		{
			tooltip.add(
					TextFormatting.YELLOW + getVariant(stack).getFuelPerHeatDisplay(useFuelInfo, useEfficiencyInfo));
			tooltip.add(TextFormatting.YELLOW
					+ getVariant(stack).getFuelPerTickDisplay(useFuelInfo, useSpeedInfo, useEfficiencyInfo));
		}
	}

	// Start machine functions

	public void update(World world, BlockPos pos, ItemStack machineStack, NBTTagCompound data)
	{

	}

	public TileCasing getCasingTile(World world, BlockPos pos)
	{
		return (TileCasing) world.getTileEntity(pos);
	}

	public float getMachineSpeed(ItemStack stack, World world, BlockPos pos)
	{
		return getVariant(stack).getRawSpeed();
	}

	public float getMachineEfficiency(ItemStack stack, World world, BlockPos pos)
	{
		return getVariant(stack).getRawEfficiency() * MachineVariants.values()[world.getBlockState(pos).getBlock()
				.getMetaFromState(world.getBlockState(pos))].getRawEfficiency();
	}

	public float getMachineFuelPerHeat(ItemStack stack, World world, BlockPos pos)
	{
		if (MachineVariants.values()[stack.getMetadata()].getFuelType().equals("FE"))
			return getVariant(stack).getHeatNum(useFuelInfo, useEfficiencyInfo) / MachineVariants.values()[world
					.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos))].getRawEfficiency();

		return getVariant(stack).getHeatNum(useFuelInfo, useEfficiencyInfo) * MachineVariants.values()[world
				.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos))].getRawEfficiency();
	}

	public float getMachineFuelPerTick(ItemStack stack, World world, BlockPos pos)
	{
		if (MachineVariants.values()[stack.getMetadata()].getFuelType().equals("FE"))
		{
			return getVariant(stack).getFuelPerTick(useFuelInfo, useSpeedInfo, useEfficiencyInfo)
					/ MachineVariants.values()[world.getBlockState(pos).getBlock()
							.getMetaFromState(world.getBlockState(pos))].getRawEfficiency();
		}

		return getVariant(stack).getFuelPerTick(useFuelInfo, useSpeedInfo, useEfficiencyInfo)
				* MachineVariants.values()[world.getBlockState(pos).getBlock()
						.getMetaFromState(world.getBlockState(pos))].getRawEfficiency();
	}

	// GUI

	public List<Slot> getSlots(TileCasing tile)
	{
		return new ArrayList();
	}

	public int[] getInvPos(ItemStack stack)
	{
		return new int[] { 0, 0 };
	}

	public int[] getGuiSize(ItemStack stack)
	{
		return new int[] { 176, 166 };
	}

	public void drawBackgroundGui(TileCasing tile, GuiCasing gui, FontRenderer fontRenderer, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		Minecraft.getMinecraft().getTextureManager()
				.bindTexture(new ResourceLocation(References.ModID, "textures/gui/blankInventory.png"));
		gui.drawTexturedModalRect(gui.getGuiLeft(), gui.getGuiTop(), 0, 0, gui.getXSize(), gui.getYSize());
	}

	public void drawForegroundGui(TileCasing tile, GuiCasing gui, FontRenderer fontRenderer, int mouseX, int mouseY)
	{
		if (!tile.machineStored.isEmpty())
		{
			int y = 20;
			int hoverY = 0;
			int hoverX = 0;
			String hoverString = "";
			if (tile.getMachine().useSpeedInfo)
			{
				GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
				Minecraft.getMinecraft().getTextureManager()
						.bindTexture(new ResourceLocation(References.ModID, "textures/gui/guiicons.png"));
				gui.drawTexturedModalRect(gui.getXSize() - 18, y, 0, 44, 14, 14);
				String s = (int) Math.ceil(
						tile.getMachine().getMachineSpeed(tile.machineStored, tile.getWorld(), tile.getPos()) * 100f)
						+ "%";
				fontRenderer.drawString(s, gui.getXSize() - fontRenderer.getStringWidth(s) - 24, y + 3, 0xFF0020D4);
				if (GuiHelper.isMouseInRect(gui.getGuiLeft() + gui.getXSize() - fontRenderer.getStringWidth(s) - 24,
						gui.getGuiTop() + y, fontRenderer.getStringWidth(s) + 24, 16, mouseX, mouseY))
				{
					hoverString = "Speed";
					hoverY = y;
					hoverX = fontRenderer.getStringWidth(s) + 24;
				}
				y += 14;
			}
			if (tile.getMachine().useEfficiencyInfo)
			{
				GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
				Minecraft.getMinecraft().getTextureManager()
						.bindTexture(new ResourceLocation(References.ModID, "textures/gui/guiicons.png"));
				gui.drawTexturedModalRect(gui.getXSize() - 18, y, 18, 44, 14, 14);
				String s = (int) Math
						.ceil(tile.getMachine().getMachineEfficiency(tile.machineStored, tile.getWorld(), tile.getPos())
								* 100f)
						+ "%";
				fontRenderer.drawString(s, gui.getXSize() - fontRenderer.getStringWidth(s) - 24, y + 3, 0xFF00DB04);
				if (GuiHelper.isMouseInRect(gui.getGuiLeft() + gui.getXSize() - fontRenderer.getStringWidth(s) - 24,
						gui.getGuiTop() + y, fontRenderer.getStringWidth(s) + 24, 16, mouseX, mouseY))
				{
					hoverString = "Efficiency/Production";
					hoverY = y;
					hoverX = fontRenderer.getStringWidth(s) + 24;
				}
				y += 14;
			}
			if (tile.getMachine().useFuelInfo)
			{
				GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
				Minecraft.getMinecraft().getTextureManager()
						.bindTexture(new ResourceLocation(References.ModID, "textures/gui/guiicons.png"));
				gui.drawTexturedModalRect(gui.getXSize() - 18, y, 0, 58, 14, 14);
				String s = Float.toString(Math.round(
						tile.getMachine().getMachineFuelPerTick(tile.machineStored, tile.getWorld(), tile.getPos())
								* 100f)
						/ 100f);
				fontRenderer.drawString(s, gui.getXSize() - fontRenderer.getStringWidth(s) - 24, y + 3, 0xFFF3FF17);
				if (GuiHelper.isMouseInRect(gui.getGuiLeft() + gui.getXSize() - fontRenderer.getStringWidth(s) - 24,
						gui.getGuiTop() + y, fontRenderer.getStringWidth(s) + 24, 16, mouseX, mouseY))
				{
					hoverString = "Fuel";
					hoverY = y;
					hoverX = fontRenderer.getStringWidth(s) + 24;
				}
			}
			if (!Strings.isNullOrEmpty(hoverString) && GuiHelper.isMouseInRect(
					gui.getGuiLeft() + gui.getXSize() - hoverX, gui.getGuiTop() + hoverY, hoverX, 16, mouseX, mouseY))
			{
				int k = (gui.width - gui.getXSize()) / 2;
				int l = (gui.height - gui.getYSize()) / 2;
				List list = new ArrayList();
				list.add(hoverString);
				gui.drawHoveringText(list, mouseX - k, mouseY - l, fontRenderer);
			}
		}
	}

	// RF Handler
	public int getMaxEnergy(ItemStack stack)
	{
		return 0;
	}

	public int getMaxExtract(ItemStack stack)
	{
		return 0;
	}

	public int getMaxReceive(ItemStack stack)
	{
		return 0;
	}

	// Item Handler
	public int getItemSlots(ItemStack stack)
	{
		return 0;
	}

	public Integer[] getInsertBlacklist(ItemStack stack)
	{
		return new Integer[0];
	}

	public Integer[] getExtractBlacklist(ItemStack stack)
	{
		return new Integer[0];
	}

	// Fluid Handler
	public Fluid getFluid(ItemStack stack)
	{
		return null;
	}

	public int getMaxFluid(ItemStack stack)
	{
		return 0;
	}

	// Heat Source
	public int getHeatProv(ItemStack stack, World world, BlockPos pos)
	{
		return 0;
	}
}
