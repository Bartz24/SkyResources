package com.bartz24.skyresources;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.network.play.server.SPacketPlayerAbilities;
import net.minecraft.network.play.server.SPacketRespawn;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldServer;
import net.minecraft.world.end.DragonFightManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.ReflectionHelper.UnableToAccessFieldException;
import net.minecraftforge.fml.relauncher.ReflectionHelper.UnableToFindMethodException;

public class PortalTeleporterNether
{
	/**
	 * The axis along which the destination portal aligns. Is either EAST or
	 * SOUTH.
	 */
	private EnumFacing portalAxis;
	private BlockPos portalPos;
	private BlockPos entityPos;

	public PortalTeleporterNether()
	{
		this.portalPos = null;
		this.entityPos = null;
		this.portalAxis = EnumFacing.NORTH;
	}

	/**
	 * Teleports the entity to the given dimension (0 or -1). Finds an existing
	 * portal or creates a new portal.
	 * 
	 * @param entity
	 * @param dimension
	 *            Destination dimension (0 = Overworld or -1 = Nether)
	 * @param idealX
	 *            The requested ideal destination coordinates
	 * @param idealY
	 *            The requested ideal destination coordinates
	 * @param idealZ
	 *            The requested ideal destination coordinates
	 * @param portalSearchRadius
	 * @param placeInsidePortal
	 *            true to place the entity inside the portal blocks, false to
	 *            place the entity one block infront of the portal blocks
	 * @return the instance of the teleported entity, or null in case of failure
	 */
	public Entity travelToDimension(Entity entity, int dimension, BlockPos idealPos, int portalSearchRadius,
			boolean placeInsidePortal)
	{
		WorldServer worldServer = FMLCommonHandler.instance().getMinecraftServerInstance()
				.worldServerForDimension(dimension);

		if (this.searchForExistingPortal(worldServer, idealPos, portalSearchRadius) == false)
		{
			double origX = entity.posX;
			double origY = entity.posY;
			double origZ = entity.posZ;
			entity.posX = idealPos.getX() + 0.5d;
			entity.posY = idealPos.getY() + 0.5d;
			entity.posZ = idealPos.getZ() + 0.5d;
			worldServer.getDefaultTeleporter().makePortal(entity);
			entity.posX = origX;
			entity.posY = origY;
			entity.posZ = origZ;

			// Failed to create or find a portal. This shouldn't happen, but
			// better to be sure.
			// The vanilla method tries to create a portal inside a 16 block
			// radius of the player's position.
			if (this.searchForExistingPortal(worldServer, idealPos, 20) == false)
			{
				return null;
			}
		}

		this.findTeleportPosition(worldServer, placeInsidePortal);

		return teleportEntity(entity, this.entityPos.getX() + 0.5d, this.entityPos.getY() + 0.5d,
				this.entityPos.getZ() + 0.5d, dimension);
	}

	private static Entity teleportEntity(Entity entity, double x, double y, double z, int dimDst)
	{
		if (entity == null || entity.isDead == true || entity.world.isRemote == true)
		{
			return null;
		}

		// Post the event and check if the teleport should be allowed
		if (entity instanceof EntityLivingBase)
		{
			EnderTeleportEvent etpEvent = new EnderTeleportEvent((EntityLivingBase) entity, x, y, z, 0.0f);
			if (MinecraftForge.EVENT_BUS.post(etpEvent) == true)
			{
				return null;
			}
		}

		if (entity.world.isRemote == false && entity.world instanceof WorldServer)
		{
			WorldServer worldServerDst = FMLCommonHandler.instance().getMinecraftServerInstance()
					.worldServerForDimension(dimDst);
			if (worldServerDst == null)
			{
				return null;
			}

			// System.out.println("Is loaded: " +
			// worldServerDst.getChunkProvider().chunkExists((int)x >> 4, (int)z
			// >> 4));

			int chunkX = ((int) x) >> 4;
			int chunkZ = ((int) z) >> 4;

			if (worldServerDst.getChunkProvider().chunkExists(chunkX, chunkZ) == false)
			{
				worldServerDst.getChunkProvider().loadChunk(chunkX, chunkZ);
			}

			if (entity instanceof EntityLiving)
			{
				((EntityLiving) entity).setMoveForward(0.0f);
				((EntityLiving) entity).getNavigator().clearPathEntity();
			}

			if (entity.dimension != dimDst
					|| (entity.world instanceof WorldServer && entity.world != worldServerDst))
			{
				entity = transferEntityToDimension(entity, dimDst, x, y, z);
			} else if (entity instanceof EntityPlayerMP)
			{
				((EntityPlayerMP) entity).connection.setPlayerLocation(x, y, z, entity.rotationYaw,
						entity.rotationPitch);
			} else
			{
				// entity.setPositionAndUpdate(x, y, z);
				entity.setLocationAndAngles(x, y, z, entity.rotationYaw, entity.rotationPitch);
			}
		}

		return entity;
	}

	private static EntityPlayer transferPlayerToDimension(EntityPlayerMP player, int dimDst, double x, double y,
			double z)
	{
		if (player == null || player.isDead == true || player.dimension == dimDst || player.world.isRemote == true)
		{
			return null;
		}

		// Post the event and check if the teleport should be allowed
		PlayerChangedDimensionEvent pcdEvent = new PlayerChangedDimensionEvent(player, player.dimension, dimDst);
		if (MinecraftForge.EVENT_BUS.post(pcdEvent) == true)
		{
			return null;
		}

		int dimSrc = player.dimension;
		x = MathHelper.clamp(x, -30000000.0d, 30000000.0d);
		z = MathHelper.clamp(z, -30000000.0d, 30000000.0d);
		player.setLocationAndAngles(x, y, z, player.rotationYaw, player.rotationPitch);

		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		WorldServer worldServerSrc = server.worldServerForDimension(dimSrc);
		WorldServer worldServerDst = server.worldServerForDimension(dimDst);

		if (worldServerSrc == null || worldServerDst == null)
		{
			return null;
		}

		player.dimension = dimDst;
		player.connection.sendPacket(new SPacketRespawn(player.dimension, player.world.getDifficulty(),
				player.world.getWorldInfo().getTerrainType(), player.interactionManager.getGameType()));
		player.mcServer.getPlayerList().updatePermissionLevel(player);
		// worldServerSrc.removePlayerEntityDangerously(player); // this crashes
		worldServerSrc.removeEntity(player);
		player.isDead = false;

		worldServerDst.spawnEntity(player);
		worldServerDst.updateEntityWithOptionalForce(player, false);
		player.setWorld(worldServerDst);
		player.mcServer.getPlayerList().preparePlayer(player, worldServerSrc); // remove
																				// player
																				// from
																				// the
																				// source
																				// world
		player.connection.setPlayerLocation(x, y, z, player.rotationYaw, player.rotationPitch);
		player.interactionManager.setWorld(worldServerDst);
		player.connection.sendPacket(new SPacketPlayerAbilities(player.capabilities));
		player.mcServer.getPlayerList().updateTimeAndWeatherForPlayer(player, worldServerDst);
		player.mcServer.getPlayerList().syncPlayerInventory(player);
		player.addExperienceLevel(0);
		player.setPlayerHealthUpdated();

		// FIXME 1.9 - Somewhat ugly way to clear the Boss Info stuff when
		// teleporting FROM The End
		if (worldServerSrc.provider instanceof WorldProviderEnd)
		{
			DragonFightManager manager = ((WorldProviderEnd) worldServerSrc.provider).getDragonFightManager();

			if (manager != null)
			{
				try
				{
					BossInfoServer bossInfo = ReflectionHelper.getPrivateValue(DragonFightManager.class, manager,
							"field_186109_c", "bossInfo");
					if (bossInfo != null)
					{
						bossInfo.removePlayer(player);
					}
				} catch (UnableToAccessFieldException e)
				{
					e.printStackTrace();
				}
			}
		}

		for (PotionEffect potioneffect : player.getActivePotionEffects())
		{
			player.connection.sendPacket(new SPacketEntityEffect(player.getEntityId(), potioneffect));
		}

		FMLCommonHandler.instance().firePlayerChangedDimensionEvent(player, dimSrc, dimDst);

		return player;
	}

	private static Entity transferEntityToDimension(Entity entitySrc, int dimDst, double x, double y, double z)
	{
		if (entitySrc == null || entitySrc.isDead == true || entitySrc.dimension == dimDst
				|| entitySrc.world.isRemote == true)
		{
			return null;
		}

		if (entitySrc instanceof EntityPlayerMP)
		{
			return transferPlayerToDimension((EntityPlayerMP) entitySrc, dimDst, x, y, z);
		}

		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		WorldServer worldServerSrc = server.worldServerForDimension(entitySrc.dimension);
		WorldServer worldServerDst = server.worldServerForDimension(dimDst);

		if (worldServerSrc == null || worldServerDst == null)
		{
			return null;
		}

		entitySrc.dimension = dimDst;
		Entity entityDst = EntityList.createEntityByIDFromName(new ResourceLocation(EntityList.getEntityString(entitySrc)), worldServerDst);
		if (entityDst == null)
		{
			return null;
		}

		// entityDst.copyDataFromOld(entitySrc);
		copyDataFromOld(entityDst, entitySrc);

		// FIXME ugly special case to prevent the chest minecart etc from duping
		// items
		if (entitySrc instanceof EntityMinecartContainer)
		{
			entitySrc.isDead = true;
		} else
		{
			entitySrc.world.removeEntity(entitySrc); // Note: this will also
														// remove any entity
														// mounts
		}

		x = MathHelper.clamp(x, -30000000.0d, 30000000.0d);
		z = MathHelper.clamp(z, -30000000.0d, 30000000.0d);
		entityDst.setLocationAndAngles(x, y, z, entitySrc.rotationYaw, entitySrc.rotationPitch);
		worldServerDst.spawnEntity(entityDst);
		worldServerDst.updateEntityWithOptionalForce(entityDst, false);
		entityDst.setWorld(worldServerDst);

		// Debug: this actually kills the original entity, commenting it will
		// make clones
		entitySrc.isDead = true;

		worldServerSrc.resetUpdateEntityTick();
		worldServerDst.resetUpdateEntityTick();

		return entityDst;
	}

	public static void copyDataFromOld(Entity target, Entity old)
	{
		Method method = ReflectionHelper.findMethod(Entity.class, target,
				new String[] { "func_180432_n", "copyDataFromOld", "a" }, Entity.class);
		try
		{
			method.invoke(target, old);
		} catch (UnableToFindMethodException e)
		{
			e.printStackTrace();
		} catch (InvocationTargetException e)
		{
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Searches for an existing Nether Portal inside the given radius around the
	 * ideal position. If a valid portal is found, the coordinates of it are
	 * stored.
	 * 
	 * @param world
	 * @param idealX
	 * @param idealY
	 * @param idealZ
	 * @param searchRadius
	 * @return true if an existing portal is found
	 */
	public boolean searchForExistingPortal(World world, BlockPos idealPos, int searchRadius)
	{
		BlockPos pos = null;
		double distance = -1.0D;

		for (int x = idealPos.getX() - searchRadius; x <= idealPos.getX() + searchRadius; ++x)
		{
			double dx = (double) x + 0.5D - idealPos.getX();

			for (int z = (int) idealPos.getZ() - searchRadius; z <= idealPos.getZ() + searchRadius; ++z)
			{
				double dz = (double) z + 0.5D - idealPos.getZ();

				for (int y = world.getActualHeight() - 1; y >= 0; --y)
				{
					if (world.getBlockState(new BlockPos(x, y, z)).getBlock() == Blocks.PORTAL)
					{
						while (world.getBlockState(new BlockPos(x, y - 1, z)).getBlock() == Blocks.PORTAL)
						{
							--y;
						}

						double dy = (double) y + 0.5D - idealPos.getY();
						double distTemp = dx * dx + dy * dy + dz * dz;

						if (distance < 0.0D || distTemp < distance)
						{
							distance = distTemp;
							pos = new BlockPos(x, y, z);
						}
					}
				}
			}
		}

		// Portal block found
		if (distance >= 0.0D)
		{
			this.portalPos = pos;
			this.getPortalOrientation(world);
		}

		return distance >= 0.0D;
	}

	public void getPortalOrientation(World world)
	{
		BlockPos pos = this.portalPos;
		if (world.getBlockState(pos.west()).getBlock() == Blocks.PORTAL
				|| world.getBlockState(pos.east()).getBlock() == Blocks.PORTAL)
		{
			this.portalAxis = EnumFacing.EAST;
		} else if (world.getBlockState(pos.north()).getBlock() == Blocks.PORTAL
				|| world.getBlockState(pos.south()).getBlock() == Blocks.PORTAL)
		{
			this.portalAxis = EnumFacing.SOUTH;
		}
	}

	public void findTeleportPosition(World world, boolean placeInsidePortal)
	{
		if (placeInsidePortal == true)
		{
			this.entityPos = this.portalPos;
		} else
		{
			EnumFacing dirSide = this.portalAxis.rotateY();
			EnumFacing dirPortal = this.portalAxis;

			// Get the axis where there are more portal blocks (if only 2 wide
			// portal)
			BlockPos posTmp = this.portalPos.add(dirPortal.getFrontOffsetX(), 0, dirPortal.getFrontOffsetZ());
			if (world.getBlockState(posTmp).getBlock() != Blocks.PORTAL)
			{
				dirPortal = dirPortal.getOpposite();
			}

			List<BlockPos> list = new ArrayList<BlockPos>();
			int xOff = dirSide.getFrontOffsetX();
			int zOff = dirSide.getFrontOffsetZ();
			list.add(this.portalPos.add(xOff, -1, zOff));
			list.add(this.portalPos.add(xOff, -2, zOff));
			list.add(this.portalPos.add(xOff + dirPortal.getFrontOffsetX(), -1, zOff + dirPortal.getFrontOffsetZ()));
			list.add(this.portalPos.add(xOff + dirPortal.getFrontOffsetX(), -2, zOff + dirPortal.getFrontOffsetZ()));
			list.add(this.portalPos.add(-xOff, -1, -zOff));
			list.add(this.portalPos.add(-xOff, -2, -zOff));
			list.add(this.portalPos.add(-xOff + dirPortal.getFrontOffsetX(), -1, -zOff + dirPortal.getFrontOffsetZ()));
			list.add(this.portalPos.add(-xOff + dirPortal.getFrontOffsetX(), -2, -zOff + dirPortal.getFrontOffsetZ()));

			// Try to find a suitable position on either side of the portal
			for (BlockPos pos : list)
			{
				if (world.isSideSolid(pos, EnumFacing.UP) == true && world.isAirBlock(pos.offset(EnumFacing.UP, 1))
						&& world.isAirBlock(pos.offset(EnumFacing.UP, 2)))
				{
					this.entityPos = pos.up();
					return;
				}
			}

			// No suitable positions found, try to add a solid block to teleport
			// to
			for (BlockPos pos : list)
			{
				if (world.isAirBlock(pos) == true && world.isAirBlock(pos.offset(EnumFacing.UP, 1))
						&& world.isAirBlock(pos.offset(EnumFacing.UP, 2)))
				{
					world.setBlockState(pos, Blocks.STONE.getDefaultState(), 3);
					this.entityPos = pos.up();
					return;
				}
			}

			// No suitable positions found on either side of the portal, what
			// should we do here??
			// Let's just stick the player to wherever he ends up on the side of
			// the portal for now...
			this.entityPos = this.portalPos.add(dirSide.getFrontOffsetX(), 0, dirSide.getFrontOffsetZ());
		}
	}
}
