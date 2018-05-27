package com.bartz24.skyresources.base.item;

import java.io.IOException;
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
import net.minecraftforge.fluids.FluidStack;
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
			tooltip.add(TextFormatting.YELLOW + this.getFuelDisplay(stack, worldIn, null)[0]);
			tooltip.add(TextFormatting.YELLOW + this.getFuelDisplay(stack, worldIn, null)[1]);
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

	public float getMachineEfficiency(ItemStack stack, @Nullable World world, BlockPos pos)
	{
		if (pos == null)
			return getVariant(stack).getRawEfficiency();

		return getVariant(stack).getRawEfficiency() * MachineVariants.values()[world.getBlockState(pos).getBlock()
				.getMetaFromState(world.getBlockState(pos))].getRawEfficiency();
	}

	/**
	 * 
	 * @param stack
	 * @param world
	 * @param pos
	 * @return float[] RF: 0=HU/tick 1=RF/tick 2=RF/HU. Fuel: 0=HU/tick 1=Fuel
	 *         Time % 2=Fuel Used/HU 3=Fuel Amount/tick
	 */
	public float[] getMachineFuelData(ItemStack stack, World world, BlockPos pos)
	{

		if (getVariant(stack).getFuelType().equals("RF"))
		{
			float[] data = new float[3];
			data[0] = getHUPerTick(stack, world, pos, useSpeedInfo);
			data[1] = rfgetRFPerTick(stack, world, pos, useFuelInfo, useSpeedInfo, useEfficiencyInfo);
			data[2] = rfgetRFPerHU(stack, world, pos, useFuelInfo, useEfficiencyInfo);
			return data;
		} else
		{
			float[] data = new float[4];
			data[0] = getHUPerTick(stack, world, pos, useSpeedInfo);
			data[1] = fuelgetFuelTimePercent(stack, world, pos, useFuelInfo, useSpeedInfo, useEfficiencyInfo);
			data[2] = fuelgetAmountPerHU(stack, world, pos, useSpeedInfo, useEfficiencyInfo);
			data[3] = fuelgetAmountPerTick();
			return data;
		}
	}

	public String[] getFuelDisplay(ItemStack stack, World world, BlockPos pos)
	{
		String[] list = new String[2];
		list[1] = getHUPerTick(stack, world, pos, useSpeedInfo) + " HU/tick";
		if (getVariant(stack).getFuelType().equals("RF"))
		{
			list[0] = Math.ceil(rfgetRFPerTick(stack, world, pos, useFuelInfo, useSpeedInfo, useEfficiencyInfo))
					+ " RF/tick";
		} else if (getVariant(stack).getFuelType().equals("Fuel"))
		{
			list[0] = "Furnace Fuels last for x" + Math.floor(
					fuelgetFuelTimePercent(stack, world, pos, useFuelInfo, useSpeedInfo, useEfficiencyInfo) * 100f)
					/ 100f + " ticks";
		} else if (getVariant(stack).getFuelType() instanceof ItemStack)
		{
			list[0] = ((ItemStack) getVariant(stack).getFuelType()).getDisplayName() + " lasts for " + Math.floor(
					fuelgetFuelTimePercent(stack, world, pos, useFuelInfo, useSpeedInfo, useEfficiencyInfo) * 100f)
					/ 100f + " ticks";
		} else if (getVariant(stack).getFuelType() instanceof Fluid)
		{
			list[0] = ((Fluid) getVariant(stack).getFuelType())
					.getLocalizedName(new FluidStack((Fluid) getVariant(stack).getFuelType(), 1)) + " lasts for "
					+ Math.floor(fuelgetFuelTimePercent(stack, world, pos, useFuelInfo, useSpeedInfo, useEfficiencyInfo)
							* 100f) / 100f
					+ " ticks";
		}
		return list;
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

	public void initGui(GuiCasing gui, List buttonList)
	{
	}

	public void actionPerformed(TileCasing tile, GuiCasing gui, int buttonClicked) throws IOException
	{
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

				float fuelAmt = getMachineFuelData(tile.machineStored, tile.getWorld(), tile.getPos())[1];
				if (!tile.getMachine().getVariant(tile.machineStored).getFuelType().equals("RF"))
					fuelAmt = getMachineFuelData(tile.machineStored, tile.getWorld(), tile.getPos())[3];

				String s = Float.toString(Math.round(fuelAmt * 100f) / 100f);
				fontRenderer.drawString(s, gui.getXSize() - fontRenderer.getStringWidth(s) - 24, y + 3, 0xFFF3FF17);
				if (GuiHelper.isMouseInRect(gui.getGuiLeft() + gui.getXSize() - fontRenderer.getStringWidth(s) - 24,
						gui.getGuiTop() + y, fontRenderer.getStringWidth(s) + 24, 16, mouseX, mouseY))
				{
					hoverString = "Fuel/Tick";
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

	public int[] getInsertBlacklist(ItemStack stack)
	{
		return new int[0];
	}

	public int[] getExtractBlacklist(ItemStack stack)
	{
		return new int[0];
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

	public float getHUPerTick(ItemStack stack, World world, BlockPos pos, boolean useSpeedInfo)
	{
		float spd = useSpeedInfo ? getMachineSpeed(stack, world, pos) : 1;

		return spd * 10;
	}

	// RF Calculations

	public float rfgetRFPerHU(ItemStack stack, World world, BlockPos pos, boolean useFuelInfo,
			boolean useEfficiencyInfo)
	{
		if (useFuelInfo)
		{
			float rate = getVariant(stack).getRawFuelRate();
			float eff = useEfficiencyInfo ? getMachineEfficiency(stack, world, pos) : 1;

			return rate / eff;
		}
		return 0;
	}

	public float rfgetRFPerTick(ItemStack stack, World world, BlockPos pos, boolean useFuelInfo, boolean useSpeedInfo,
			boolean useEfficiencyInfo)
	{
		return getHUPerTick(stack, world, pos, useSpeedInfo)
				* rfgetRFPerHU(stack, world, pos, useFuelInfo, useEfficiencyInfo);
	}

	// Fuel Calculations

	public float fuelgetAmountPerTick()
	{
		return 1;
	}

	public float fuelgetFuelTimePercent(ItemStack stack, World world, BlockPos pos, boolean useFuelInfo,
			boolean useSpeedInfo, boolean useEfficiencyInfo)
	{
		float rate = useFuelInfo
				? (getVariant(stack).getFuelType() instanceof ItemStack ? getVariant(stack).getRawFuelRate() : 1) : 1;
		float spd = useSpeedInfo ? getMachineSpeed(stack, world, pos) : 1;
		float eff = useEfficiencyInfo ? getMachineEfficiency(stack, world, pos) : 1;
		return rate / getHUPerTick(stack, world, pos, useSpeedInfo) * eff;
	}

	public float fuelgetAmountPerHU(ItemStack stack, World world, BlockPos pos, boolean useSpeedInfo,
			boolean useEfficiencyInfo)
	{
		return getHUPerTick(stack, world, pos, useSpeedInfo) * fuelgetAmountPerTick();
	}
}
