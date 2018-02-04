package com.bartz24.skyresources.registry;

import javax.annotation.Nonnull;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.alchemy.item.AlchemyItemComponent;
import com.bartz24.skyresources.alchemy.item.DirtyGemItem;
import com.bartz24.skyresources.alchemy.item.ItemOreAlchDust;
import com.bartz24.skyresources.alchemy.render.CrucibleTESR;
import com.bartz24.skyresources.alchemy.tile.CrucibleTile;
import com.bartz24.skyresources.base.MachineVariants;
import com.bartz24.skyresources.base.entity.EntityHeavyExplosiveSnowball;
import com.bartz24.skyresources.base.entity.EntityHeavySnowball;
import com.bartz24.skyresources.base.entity.RenderEntityItem;
import com.bartz24.skyresources.base.item.BaseItemComponent;
import com.bartz24.skyresources.base.item.ItemWaterExtractor;
import com.bartz24.skyresources.base.render.CasingTESR;
import com.bartz24.skyresources.base.tile.TileCasing;
import com.bartz24.skyresources.technology.item.TechItemComponent;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ModRenderers
{
	public static void preInit()
	{

		mapFluidState(ModFluids.crystalFluid);

		for (int i = 0; i < AlchemyItemComponent.getNames().size(); i++)
		{
			registerItemRenderer(ModItems.alchemyComponent, i);
		}

		for (int i = 0; i < ItemOreAlchDust.getNames().size(); i++)
		{
			registerItemRenderer(ModItems.oreAlchDust, i, true);
		}

		for (int i = 0; i < DirtyGemItem.getNames().size(); i++)
		{
			registerItemRenderer(ModItems.dirtyGem, i, true);
		}
		for (int i = 0; i < BaseItemComponent.getNames().size(); i++)
		{
			registerItemRenderer(ModItems.baseComponent, i);
		}
		for (int i = 0; i < TechItemComponent.getNames().size(); i++)
		{
			registerItemRenderer(ModItems.techComponent, i);
		}
		registerItemRenderer(ModItems.cactusFruit);
		registerItemRenderer(ModItems.fleshySnowNugget);
		registerItemRenderer(ModItems.heavySnowball);
		registerItemRenderer(ModItems.heavyExpSnowball);
		registerItemRenderer(ModItems.cactusKnife);
		registerItemRenderer(ModItems.stoneKnife);
		registerItemRenderer(ModItems.ironKnife);
		registerItemRenderer(ModItems.diamondKnife);
		registerItemRenderer(ModItems.stoneGrinder);
		registerItemRenderer(ModItems.ironGrinder);
		registerItemRenderer(ModItems.diamondGrinder);
		registerItemRenderer(ModItems.alchemicalInfusionStone);
		registerItemRenderer(ModItems.healthGem);
		registerItemRenderer(ModItems.survivalistFishingRod);
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.cactusFruitNeedle));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.compressedCoalBlock));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.coalInfusedBlock));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.sandyNetherrack));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.darkMatterBlock));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.lightMatterBlock));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.heavySnow));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.dryCactus));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.silverfishDisruptor));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.miniFreezer));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.ironFreezer));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.lightFreezer));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.crucible));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.fluidDropper));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.blazePowderBlock));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.petrifiedWood));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.petrifiedPlanks));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.magmafiedStone));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.dirtFurnace));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.darkMatterWarper));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.endPortalCore));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.lifeInfuser));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.lifeInjector));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.crucibleInserter));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.rockCrusher));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.rockCleaner));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.combustionCollector));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.combustionController));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.quickDropper));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.aqueousConcentrator));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.aqueousDeconcentrator));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.fusionTable));
		registerItemRenderer(Item.getItemFromBlock(ModBlocks.wildlifeAttractor));

		registerItemRenderer(ModItems.sandstoneInfusionStone);
		registerItemRenderer(ModItems.redSandstoneInfusionStone);

		registerVariantsDefaulted(ModBlocks.casing, MachineVariants.class, "variant");

		registerVariantsDefaulted(ModItems.condenser, MachineVariants.class, "variant");
		registerVariantsDefaulted(ModItems.combustionHeater, MachineVariants.class, "variant");
		registerVariantsDefaulted(ModItems.heatProvider, MachineVariants.class, "variant");
		registerVariantsDefaulted(ModItems.alchComponent, MachineVariants.class, "variant");
		registerVariantsDefaulted(ModItems.heatComponent, MachineVariants.class, "variant");

		ModelBakery.registerItemVariants(ModItems.waterExtractor,
				new ModelResourceLocation("skyresources:WaterExtractor.empty", "inventory"),
				new ModelResourceLocation("skyresources:WaterExtractor.full1", "inventory"),
				new ModelResourceLocation("skyresources:WaterExtractor.full2", "inventory"),

				new ModelResourceLocation("skyresources:WaterExtractor.full3", "inventory"),
				new ModelResourceLocation("skyresources:WaterExtractor.full4", "inventory"),
				new ModelResourceLocation("skyresources:WaterExtractor.full5", "inventory"),
				new ModelResourceLocation("skyresources:WaterExtractor.full6", "inventory"));

		ModelLoader.setCustomMeshDefinition(ModItems.waterExtractor, new ItemMeshDefinition()
		{
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack)
			{
				NBTTagCompound tagCompound = stack.getTagCompound();
				int amount = ((ItemWaterExtractor) stack.getItem()).getTank().getFluidAmount();

				int level = (int) (amount * 6F / ((ItemWaterExtractor) stack.getItem()).getMaxAmount());
				if (level < 0)
					level = 0;
				else if (level > 6)
					level = 6;

				return new ModelResourceLocation(
						stack.getItem().getRegistryName() + "." + ItemWaterExtractor.extractorIcons[level],
						"inventory");
			}
		});

		RenderingRegistry.registerEntityRenderingHandler(EntityHeavySnowball.class,
				new IRenderFactory<EntityHeavySnowball>()
				{
					@Override
					public RenderEntityItem createRenderFor(RenderManager manager)
					{
						return new RenderEntityItem(manager, new ItemStack(ModItems.heavySnowball));
					}
				});

		RenderingRegistry.registerEntityRenderingHandler(EntityHeavyExplosiveSnowball.class,
				new IRenderFactory<EntityHeavyExplosiveSnowball>()
				{
					@Override
					public RenderEntityItem createRenderFor(RenderManager manager)
					{
						return new RenderEntityItem(manager, new ItemStack(ModItems.heavyExpSnowball));
					}
				});
	}

	public static void init()
	{
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor()
		{

			@Override
			public int colorMultiplier(ItemStack stack, int tintIndex)
			{
				if (stack.getMetadata() < 0 || stack.getMetadata() >= ItemOreAlchDust.getNames().size())
					return -1;

				return ItemOreAlchDust.oreInfos.get(stack.getMetadata()).color;
			}

		}, ModItems.oreAlchDust);

		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor()
		{

			@Override
			public int colorMultiplier(ItemStack stack, int tintIndex)
			{
				if (stack.getMetadata() < 0 || stack.getMetadata() >= ModItems.gemList.size())
					return -1;

				return ModItems.gemList.get(stack.getMetadata()).color;
			}

		}, ModItems.dirtyGem);

		ClientRegistry.bindTileEntitySpecialRenderer(CrucibleTile.class, new CrucibleTESR());
		ClientRegistry.bindTileEntitySpecialRenderer(TileCasing.class, new CasingTESR());
	}

	public static void registerItemRenderer(Item item, int meta, ResourceLocation name)
	{
		ModelBakery.registerItemVariants(item, name);
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(name, "inventory"));
	}

	public static void registerItemRenderer(Item item, int meta)
	{
		registerItemRenderer(item, meta, new ResourceLocation(item.getRegistryName().toString() + meta));
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

	public static void registerItemRenderer(Item item, ResourceLocation name)
	{
		registerItemRenderer(item, 0, name);
	}

	public static void registerBlockRenderer(Block block, IStateMapper mapper, int... metadata)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getBlockModelShapes()
				.registerBlockWithStateMapper(block, mapper);
	}

	private static <T extends Enum<T> & IStringSerializable> void registerVariantsDefaulted(Block b, Class<T> enumclazz,
			String variantHeader)
	{
		Item item = Item.getItemFromBlock(b);
		for (T e : enumclazz.getEnumConstants())
		{
			String variantName = variantHeader + "=" + e.getName().toLowerCase();
			ModelLoader.setCustomModelResourceLocation(item, e.ordinal(),
					new ModelResourceLocation(b.getRegistryName(), variantName));
		}
	}

	private static <T extends Enum<T> & IStringSerializable> void registerVariantsDefaulted(Item item,
			Class<T> enumclazz, String variantHeader)
	{
		for (T e : enumclazz.getEnumConstants())
		{
			String variantName = variantHeader + "=" + e.getName().toLowerCase();
			ModelLoader.setCustomModelResourceLocation(item, e.ordinal(),
					new ModelResourceLocation(item.getRegistryName(), variantName));
		}
	}

	public static void mapFluidState(Fluid fluid)
	{
		Block block = fluid.getBlock();
		Item item = Item.getItemFromBlock(block);
		FluidStateMapper mapper = new FluidStateMapper(fluid);
		if (item != null)
		{
			ModelLoader.registerItemVariants(item);
			ModelLoader.setCustomMeshDefinition(item, mapper);
		}
		ModelLoader.setCustomStateMapper(block, mapper);
	}

	static class FluidStateMapper extends StateMapperBase implements ItemMeshDefinition
	{
		public final ModelResourceLocation location;

		public FluidStateMapper(Fluid fluid)
		{
			this.location = new ModelResourceLocation(References.ModID + ":fluid_block", fluid.getName());
		}

		@Nonnull
		@Override
		protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state)
		{
			return location;
		}

		@Nonnull
		@Override
		public ModelResourceLocation getModelLocation(@Nonnull ItemStack stack)
		{
			return location;
		}
	}
}
