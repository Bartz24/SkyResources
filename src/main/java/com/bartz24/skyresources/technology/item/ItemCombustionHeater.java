package com.bartz24.skyresources.technology.item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.References;
import com.bartz24.skyresources.base.MachineVariants;
import com.bartz24.skyresources.base.gui.GuiCasing;
import com.bartz24.skyresources.base.gui.GuiHelper;
import com.bartz24.skyresources.base.gui.SlotSpecial;
import com.bartz24.skyresources.base.item.ItemMachine;
import com.bartz24.skyresources.base.tile.TileCasing;
import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;
import com.bartz24.skyresources.registry.ModCreativeTabs;
import com.bartz24.skyresources.technology.tile.TileCombustionCollector;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class ItemCombustionHeater extends ItemMachine
{
	public ItemCombustionHeater()
	{
		super("combustionheater", ModCreativeTabs.tabTech, false, true, true, true);
	}

	public List<Material> ValidMaterialsForCrafting(ItemStack machineStack)
	{
		List<Material> mats = new ArrayList<Material>();
		switch (machineStack.getMetadata())
		{
		case 0:
			mats.add(Material.WOOD);
			mats.add(Material.GLASS);
			break;
		case 1:
			mats.add(Material.ROCK);
			mats.add(Material.GLASS);
			break;
		default:
			mats.add(Material.IRON);
			mats.add(Material.ROCK);
			mats.add(Material.GLASS);
			break;
		}
		return mats;
	}

	public int getMaxHU(World world, BlockPos pos)
	{
		return MachineVariants.values()[world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos))]
				.getMaxHeat();
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

	private void heatUp(ItemStack machineStack, World world, BlockPos pos, NBTTagCompound data)
	{
		float curHU = data.getFloat("curHU");
		float itemHU = data.getFloat("itemHU");
		float huTick = data.getFloat("huTick");
		float huAdded = 0;

		boolean added = true;
		while (added && (huAdded < huTick || huTick == 0))
		{
			added = false;
			if (curHU < getMaxHU(world, pos) && itemHU > 0)
			{
				float amt = Math.max(Math.min(Math.min(getMaxHU(world, pos) - curHU, huTick - huAdded), itemHU), 0);
				if (amt > 0)
				{
					curHU += amt;
					huAdded += amt;
					itemHU -= amt;
					added = true;
				}
			} else if (itemHU <= 0)
			{
				data.setFloat("itemHU", itemHU);
				data.setFloat("huTick", huTick);
				getFuel(machineStack, world, pos, data);
				itemHU = data.getFloat("itemHU");
				huTick = data.getFloat("huTick");
				if (itemHU > 0)
					added = true;
			}

		}

		data.setFloat("curHU", curHU);
		data.setFloat("itemHU", itemHU);
		data.setFloat("huTick", huTick);
	}

	private void getFuel(ItemStack machineStack, World world, BlockPos pos, NBTTagCompound data)
	{
		if (getVariant(machineStack).getFuelType().equals("RF"))
		{
			float extract = this.getCasingTile(world, pos)
					.internalExtractEnergy((int) getMachineFuelData(machineStack, world, pos)[1], false);
			if (extract > 0)
			{
				data.setFloat("itemHU", extract / getMachineFuelData(machineStack, world, pos)[2]);
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

	public void update(World world, BlockPos pos, ItemStack machineStack, NBTTagCompound data)
	{
		if (this.getCasingTile(world, pos).receivedPulse() && hasValidMultiblock(world, pos, machineStack))
		{
			craftItem(world, pos, machineStack, data);
		}

		if (!world.isRemote)
		{
			this.heatUp(machineStack, world, pos, data);
			float curHU = data.getFloat("curHU");
			if (!hasValidMultiblock(world, pos, machineStack) && curHU > 0)
				curHU--;
			curHU = Math.max(0, Math.min(curHU, getMaxHU(world, pos)));
			data.setFloat("curHU", curHU);
		}
	}

	public TileCombustionCollector getCollector(World world, BlockPos pos)
	{
		BlockPos[] poses = new BlockPos[] { pos.add(-1, 1, 0), pos.add(1, 1, 0), pos.add(0, 1, -1), pos.add(0, 1, 1),
				pos.add(0, 2, 0) };
		for (BlockPos p : poses)
		{
			TileEntity t = world.getTileEntity(p);
			if (t != null && t instanceof TileCombustionCollector)
				return (TileCombustionCollector) t;
		}
		return null;
	}

	public boolean hasValidMultiblock(World world, BlockPos pos, ItemStack machineStack)
	{
		List<Material> materials = ValidMaterialsForCrafting(machineStack);
		if (!isBlockValid(world, machineStack, pos.up(), pos.add(-1, 1, 0))
				|| !isBlockValid(world, machineStack, pos.up(), pos.add(1, 1, 0))
				|| !isBlockValid(world, machineStack, pos.up(), pos.add(0, 2, 0))
				|| !isBlockValid(world, machineStack, pos.up(), pos.add(0, 1, -1))
				|| !isBlockValid(world, machineStack, pos.up(), pos.add(0, 1, 1)) || !world.isAirBlock(pos.up()))
			return false;
		return true;
	}

	boolean isBlockValid(World world, ItemStack machineStack, BlockPos center, BlockPos pos)
	{
		BlockPos dir = center.subtract(pos);
		return ValidMaterialsForCrafting(machineStack).contains(world.getBlockState(pos).getMaterial())
				&& world.getBlockState(pos).getBlockFaceShape(world, pos,
						EnumFacing.getFacingFromVector(dir.getX(), dir.getY(), dir.getZ())) == BlockFaceShape.SOLID;
	}

	void craftItem(World world, BlockPos pos, ItemStack machineStack, NBTTagCompound data)
	{
		float curHU = data.getFloat("curHU");
		ProcessRecipe recipe = recipeToCraft(world, pos, curHU);
		if (recipe != null)
		{
			world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, pos.getX(), pos.getY() + 1.5D, pos.getZ(), 0.0D,
					0.0D, 0.0D, new int[0]);
			world.playSound((EntityPlayer) null, pos.getX() + 0.5, pos.getY() + 1.5D, pos.getZ() + 0.5,
					SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F,
					(1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);

			if (!world.isRemote)
			{
				List<EntityItem> list = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.getX(),
						pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getY() + 2F, pos.getZ() + 1));

				HashMap<ItemStack, Integer> items = new HashMap();
				for (EntityItem o : list)
				{
					ItemStack i = o.getItem().copy();
					int count = i.getCount();
					i.setCount(1);
					boolean added = false;
					for (ItemStack i2 : items.keySet())
					{
						if (i2.isItemEqual(i))
						{
							items.put(i2, items.get(i2) + count);
							added = true;
						}
					}
					if (!added)
						items.put(i, count);
					o.setDead();

				}
				ItemStack item1 = items.keySet().toArray(new ItemStack[items.size()])[0];
				int ratio = 0;
				for (Object recStack : recipe.getInputs())
				{
					if (item1.isItemEqual((ItemStack) recStack))
					{
						ratio = items.get(item1) / ((ItemStack) recStack).getCount();
						break;
					}
				}

				for (int times = 0; times < ratio; times++)
				{
					if (curHU < recipe.getIntParameter())
						break;

					for (Object recStack : recipe.getInputs())
					{
						for (ItemStack item2 : items.keySet().toArray(new ItemStack[items.size()]))
						{
							if (item2.isItemEqual((ItemStack) recStack))
							{
								items.put(item2, items.get(item2) - ((ItemStack) recStack).getCount());
							}
						}
					}

					float mult = 1f - (1f / (1.5f + 0.8f * this.getMachineEfficiency(machineStack, world, pos)));
					curHU *= mult;

					ItemStack stack = recipe.getOutputs().get(0).copy();

					TileCombustionCollector collector = getCollector(world, pos);
					if (collector != null)
					{
						for (int i = 0; i < 5; i++)
						{
							if (!stack.isEmpty())
								stack = collector.getInventory().insertItem(i, stack, false);
							else
								break;
						}
					}
					if (!stack.isEmpty())
					{
						Entity entity = new EntityItem(world, pos.getX() + 0.5F, pos.getY() + 1.5F, pos.getZ() + 0.5F,
								stack);
						world.spawnEntity(entity);
					}
				}
				for (ItemStack item2 : items.keySet().toArray(new ItemStack[items.size()]))
				{
					if (items.get(item2) > 0)
					{
						EntityItem entity = new EntityItem(world, pos.getX() + 0.5F, pos.getY() + 1.5F,
								pos.getZ() + 0.5F, item2);
						entity.getItem().setCount(items.get(item2));
						world.spawnEntity(entity);
					}
				}
			}
		}
		data.setFloat("curHU", curHU);
	}

	public ProcessRecipe recipeToCraft(World world, BlockPos pos, float curHU)
	{
		List<EntityItem> list = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.getX(),
				pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1));

		List<Object> items = new ArrayList<Object>();

		for (EntityItem i : list)
		{
			items.add(i.getItem());
		}

		ProcessRecipe recipe = ProcessRecipeManager.combustionRecipes.getMultiRecipe(items, curHU);
		return recipe;
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

	public int[] getExtractBlacklist(ItemStack stack)
	{
		return getItemSlots(stack) == 1 ? new int[] { 0 } : new int[0];
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

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		Minecraft.getMinecraft().getTextureManager()
				.bindTexture(new ResourceLocation(References.ModID, "textures/gui/guiicons.png"));
		gui.drawTexturedModalRect(gui.getGuiLeft() + 8, gui.getGuiTop() + 24, 73, 0, 10, 45);
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
		float curHU = tile.machineData.getFloat("curHU");
		float itemHU = tile.machineData.getFloat("itemHU");
		float maxHU = tile.machineData.getFloat("maxHU");
		float huTick = tile.machineData.getFloat("huTick");
		fontRenderer.drawString("HU: ", 19, 24, 0xFFF3FF17);
		fontRenderer.drawString("" + (int) curHU, 42, 24, 0xFFF3FF17);
		fontRenderer.drawString("+ " + (int) huTick + " HU/t",
				gui.getXSize()-8 - fontRenderer.getStringWidth("+ " + (int) huTick + " HU/t"), 64, 0xFFF3FF17);
		fontRenderer.drawString("Max: ", 19, 32, 0xFFF3FF17);
		fontRenderer.drawString("" + (int) getMaxHU(tile.getWorld(), tile.getPos()), 42, 32, 0xFFF3FF17);

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		Minecraft.getMinecraft().getTextureManager()
				.bindTexture(new ResourceLocation(References.ModID, "textures/gui/guiicons.png"));
		gui.drawTexturedModalRect(9, 68 - (int) (curHU / getMaxHU(tile.getWorld(), tile.getPos()) * 43f), 59,
				82 - (int) (curHU / getMaxHU(tile.getWorld(), tile.getPos()) * 43f), 9,
				(int) (curHU / getMaxHU(tile.getWorld(), tile.getPos()) * 43f));

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		Minecraft.getMinecraft().getTextureManager()
				.bindTexture(new ResourceLocation(References.ModID, "textures/gui/guiicons.png"));
		gui.drawTexturedModalRect(100, 52, 29, 60, 3, 18);

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		Minecraft.getMinecraft().getTextureManager()
				.bindTexture(new ResourceLocation(References.ModID, "textures/gui/guiicons.png"));
		gui.drawTexturedModalRect(101, 69 - (int) (16f * itemHU / maxHU), 26, 77 - (int) (16f * itemHU / maxHU), 1,
				(int) (16f * itemHU / maxHU));

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

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		Minecraft.getMinecraft().getTextureManager()
				.bindTexture(new ResourceLocation(References.ModID, "textures/gui/guiIcons.png"));
		gui.drawTexturedModalRect(20, 40, 0, 16, 32, 28);
		if (hasValidMultiblock(tile.getWorld(), tile.getPos(), tile.machineStored))
			gui.drawTexturedModalRect(52, 48, 0, 0, 16, 16);
		else
			gui.drawTexturedModalRect(52, 48, 16, 0, 16, 16);

		super.drawForegroundGui(tile, gui, fontRenderer, mouseX, mouseY);

		if (GuiHelper.isMouseInRect(20 + gui.getGuiLeft(), 40 + gui.getGuiTop(), 50, 28, mouseX, mouseY))
		{
			List list = new ArrayList();
			if (hasValidMultiblock(tile.getWorld(), tile.getPos(), tile.machineStored))
				list.add("Multiblock Formed!");
			else
				list.add("Multiblock Not Formed.");
			int k = (gui.width - gui.getXSize()) / 2;
			int l = (gui.height - gui.getYSize()) / 2;
			gui.drawHoveringText(list, mouseX - k, mouseY - l, fontRenderer);
		}
	}
}
