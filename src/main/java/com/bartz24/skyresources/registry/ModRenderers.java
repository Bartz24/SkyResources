package com.bartz24.skyresources.registry;

import com.bartz24.skyresources.alchemy.crucible.CrucibleTESR;
import com.bartz24.skyresources.alchemy.item.AlchemyItemComponent;
import com.bartz24.skyresources.alchemy.item.MetalCrystalItem;
import com.bartz24.skyresources.alchemy.tile.CrucibleTile;
import com.bartz24.skyresources.base.item.BaseItemComponent;
import com.bartz24.skyresources.base.item.ItemWaterExtractor;
import com.bartz24.skyresources.technology.block.CombustionHeaterBlock.CombustionHeaterVariants;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelDynBucket;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ModRenderers
{
	public static void preInit()
	{

		for (int i = 0; i < ModFluids.crystalFluidNames().length; i++)
		{
			final ModelResourceLocation fluidModelLocation = new ModelResourceLocation(
					ModBlocks.crystalFluidBlocks.get(i).getRegistryName(),
					"fluid");

			ModelBakery.registerItemVariants(
					Item.getItemFromBlock(ModBlocks.crystalFluidBlocks.get(i)),
					fluidModelLocation);

			ModelLoader.setCustomMeshDefinition(
					Item.getItemFromBlock(ModBlocks.crystalFluidBlocks.get(i)),
					new ItemMeshDefinition()
					{
						@Override
						public ModelResourceLocation getModelLocation(
								ItemStack stack)
						{
							return fluidModelLocation;
						}
					});
			ModelLoader.setCustomStateMapper(
					ModBlocks.crystalFluidBlocks.get(i), new StateMapperBase()
					{
						@Override
						protected ModelResourceLocation getModelResourceLocation(
								IBlockState state)
						{
							return fluidModelLocation;
						}
					});

			ModelLoader.setCustomMeshDefinition(
					ModItems.crystalFluidBuckets.get(i),
					new ItemMeshDefinition()
					{
						@Override
						public ModelResourceLocation getModelLocation(
								ItemStack stack)
						{
							return ModelDynBucket.LOCATION;
						}
					});
			ModelBakery.registerItemVariants(
					ModItems.crystalFluidBuckets.get(i),
					ModelDynBucket.LOCATION);
		}

		for (int i = 0; i < AlchemyItemComponent.getNames().size(); i++)
		{
			registerItemRenderer(ModItems.alchemyComponent, i);
		}

		for (int i = 0; i < MetalCrystalItem.getNames().size(); i++)
		{
			registerItemRenderer(ModItems.metalCrystal, i, true);
		}
		for (int i = 0; i < BaseItemComponent.getNames().size(); i++)
		{
			registerItemRenderer(ModItems.baseComponent, i);
		}
		registerItemRenderer(ModItems.cactusFruit);
		registerItemRenderer(ModItems.cactusKnife);
		registerItemRenderer(ModItems.ironKnife);
		registerItemRenderer(ModItems.diamondKnife);
		registerItemRenderer(ModItems.stoneGrinder);
		registerItemRenderer(ModItems.ironGrinder);
		registerItemRenderer(ModItems.diamondGrinder);
		registerItemRenderer(ModItems.healthRing);
		registerItemRenderer(
				Item.getItemFromBlock(ModBlocks.cactusFruitNeedle));
		registerItemRenderer(
				Item.getItemFromBlock(ModBlocks.compressedCoalBlock));
		registerItemRenderer(
				Item.getItemFromBlock(ModBlocks.compressedCoalBlock2));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.dryCactus));
		registerItemRenderer(
				Item.getItemFromBlock(ModBlocks.alchemicalCondenser));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.crucible));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.fluidDropper));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.blazePowderBlock));

		registerItemRenderer(ModItems.sandstoneInfusionStone);
		registerItemRenderer(ModItems.redSandstoneInfusionStone);

		ModelBakery.registerItemVariants(ModItems.waterExtractor,
				new ModelResourceLocation("skyresources:WaterExtractor.empty",
						"inventory"),
				new ModelResourceLocation("skyresources:WaterExtractor.full1",
						"inventory"),
				new ModelResourceLocation("skyresources:WaterExtractor.full2",
						"inventory"),
				new ModelResourceLocation("skyresources:WaterExtractor.full3",
						"inventory"),
				new ModelResourceLocation("skyresources:WaterExtractor.full4",
						"inventory"),
				new ModelResourceLocation("skyresources:WaterExtractor.full5",
						"inventory"),
				new ModelResourceLocation("skyresources:WaterExtractor.full6",
						"inventory"));

		registerVariantsDefaulted(ModBlocks.combustionHeater,
				CombustionHeaterVariants.class, "variant");

		ModelLoader.setCustomMeshDefinition(ModItems.waterExtractor,
				new ItemMeshDefinition()
				{
					@Override
					public ModelResourceLocation getModelLocation(
							ItemStack stack)
					{
						NBTTagCompound tagCompound = stack.getTagCompound();
						int amount = 0;
						if (tagCompound != null)
						{
							amount = tagCompound.getInteger("amount");
						}

						int level = (int) (amount * 6F
								/ ((ItemWaterExtractor) stack.getItem())
										.getMaxAmount());
						if (level < 0)
							level = 0;
						else if (level > 6)
							level = 6;

						return new ModelResourceLocation(
								stack.getItem().getRegistryName() + "."
										+ ItemWaterExtractor.extractorIcons[level],
								"inventory");
					}
				});
	}

	public static void init()
	{
		Minecraft.getMinecraft().getItemColors()
				.registerItemColorHandler(new IItemColor()
				{

					@Override
					public int getColorFromItemstack(ItemStack stack,
							int tintIndex)
					{
						if (stack.getMetadata() < 0
								|| stack.getMetadata() >= ModFluids
										.crystalFluidColors().length)
							return -1;

						return ModFluids.crystalFluidColors()[stack
								.getMetadata()];
					}

				}, ModItems.metalCrystal);

		ClientRegistry.bindTileEntitySpecialRenderer(CrucibleTile.class,
				new CrucibleTESR());
	}

	public static void registerItemRenderer(Item item, int meta, String name)
	{
		ResourceLocation resource = new ResourceLocation(name);

		ModelBakery.registerItemVariants(item, resource);
		ModelLoader.setCustomModelResourceLocation(item, meta,
				new ModelResourceLocation(resource, "inventory"));
	}

	public static void registerItemRenderer(Item item, int meta)
	{
		registerItemRenderer(item, meta, item.getRegistryName() + meta);
	}

	public static void registerItemRenderer(Item item, int meta, boolean global)
	{
		if (!global)
			registerItemRenderer(item, meta);
		else
			registerItemRenderer(item, meta, item.getRegistryName());
	}

	public static void registerItemRenderer(Item item)
	{
		registerItemRenderer(item, item.getRegistryName());
	}

	public static void registerItemRenderer(Item item, String name)
	{
		registerItemRenderer(item, 0, name);
	}

	public static void registerBlockRenderer(Block block, IStateMapper mapper,
			int... metadata)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.getModelManager().getBlockModelShapes()
				.registerBlockWithStateMapper(block, mapper);
	}

	private static <T extends Enum<T> & IStringSerializable> void registerVariantsDefaulted(
			Block b, Class<T> enumclazz, String variantHeader)
	{
		Item item = Item.getItemFromBlock(b);
		for (T e : enumclazz.getEnumConstants())
		{
			String variantName = variantHeader + "="
					+ e.getName().toLowerCase();
			ModelLoader.setCustomModelResourceLocation(item, e.ordinal(),
					new ModelResourceLocation(b.getRegistryName(),
							variantName));
		}
	}
}
