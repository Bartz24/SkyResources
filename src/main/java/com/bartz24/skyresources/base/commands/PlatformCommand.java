package com.bartz24.skyresources.base.commands;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.SkyResources;
import com.bartz24.skyresources.api.IslandPos;
import com.bartz24.skyresources.api.event.IslandCreateEvent;
import com.bartz24.skyresources.api.event.IslandHomeEvent;
import com.bartz24.skyresources.api.event.IslandInviteEvent;
import com.bartz24.skyresources.api.event.IslandLeaveEvent;
import com.bartz24.skyresources.api.event.IslandResetEvent;
import com.bartz24.skyresources.api.event.IslandSpawnEvent;
import com.bartz24.skyresources.api.event.IslandVisitEvent;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.events.EventHandler;
import com.bartz24.skyresources.registry.ModGuiHandler;
import com.bartz24.skyresources.world.WorldTypeSky;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraftforge.common.MinecraftForge;

public class PlatformCommand extends CommandBase implements ICommand
{
	private List<String> aliases;

	public PlatformCommand()
	{
		aliases = new ArrayList<String>();
		if (ConfigOptions.commandName.equals("platform"))
		{
			aliases.add("platform");
			aliases.add("plat");
		} else
			aliases.add(ConfigOptions.commandName);

	}

	@Override
	public List getCommandAliases()
	{
		return aliases;
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return true;
	}

	@Override
	public String getCommandName()
	{
		return aliases.get(0);
	}

	@Override
	public int getRequiredPermissionLevel()
	{
		return 2;
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return ConfigOptions.commandName;
	}

	@Override
	public List getTabCompletionOptions(MinecraftServer server, ICommandSender sender,
			String[] args, BlockPos pos)
	{
		return null;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args)
			throws CommandException
	{
		World world = sender.getEntityWorld();
		EntityPlayerMP player = (EntityPlayerMP) world
				.getPlayerEntityByName(sender.getCommandSenderEntity().getName());

		if (!(world.getWorldInfo().getTerrainType() instanceof WorldTypeSky))
		{
			player.addChatMessage(new TextComponentString("You are not in a sky world type."));
			return;
		}

		if (args.length == 0)
			showHelp(player);
		else
		{
			String subCommand = args[0];
			subCommand = subCommand.trim();

			if (subCommand.equals("create"))
			{
				newPlatform(player, args);
				MinecraftForge.EVENT_BUS.post(new IslandCreateEvent(player, References.getPlayerIsland(player.getName())));
			} else if (subCommand.equals("invite"))
			{
				inviteOther(player, args, world);
				MinecraftForge.EVENT_BUS.post(new IslandInviteEvent(player, References.getPlayerIsland(player.getName())));
			} else if (subCommand.equals("leave"))
			{
				IslandPos pos = References.getPlayerIsland(player.getName());
				leavePlatform(player, args);
				MinecraftForge.EVENT_BUS.post(new IslandLeaveEvent(player, pos));
			} else if (subCommand.equals("home"))
			{
				tpHome(player, args);
				MinecraftForge.EVENT_BUS.post(new IslandHomeEvent(player, References.getPlayerIsland(player.getName())));
			} else if (subCommand.equals("spawn"))
			{
				tpSpawn(player, args);
				MinecraftForge.EVENT_BUS.post(new IslandSpawnEvent(player));
			} else if (subCommand.equals("reset"))
			{
				reset(player, args, world);
				MinecraftForge.EVENT_BUS.post(new IslandResetEvent(player, References.getPlayerIsland(player.getName())));
			} else if (subCommand.equals("guide"))
			{
				guide(player, args, world);
			} else if (subCommand.equals("visit"))
			{
				visit(player, args);
				MinecraftForge.EVENT_BUS.post(new IslandVisitEvent(player, References.getPlayerIsland(player.getName())));
			} else if (subCommand.equals("onechunk"))
			{

				if (!ConfigOptions.oneChunkCommandAllowed)
				{
					player.addChatMessage(new TextComponentString("This command is not allowed!"));
					return;

				}

				if (References.worldOneChunk)
				{
					player.addChatMessage(new TextComponentString("Already in one chunk mode!"));
					return;
				}
				References.CurrentIslandsList.clear();

				References.CurrentIslandsList.add(new IslandPos(0, 0));
				WorldBorder border = world.getMinecraftServer().worldServers[0].getWorldBorder();

				border.setCenter(0, 0);
				border.setTransition(16);
				border.setWarningDistance(1);

				References.worldOneChunk = true;
				reset(player, args, world);
			}
		}

	}

	void visit(EntityPlayerMP player, String[] args) throws CommandException
	{
		if (args.length != 2)
		{
			player.addChatMessage(new TextComponentString("Must have 1 argument."));
			return;
		}
		if (References.worldOneChunk)
		{
			player.addChatMessage(new TextComponentString("Can't use this command in this mode."));
			return;
		}
		if (References.initialIslandDistance != ConfigOptions.islandDistance)
		{
			player.addChatMessage(new TextComponentString(
					"This isn't going to work. The island distance has changed!"));
			return;
		}

		IslandPos isPos = References.getPlayerIsland(args[1]);

		if (args[1].equals(player.getName()))
		{
			player.addChatMessage(new TextComponentString("Can't visit your own island."));
			return;
		}

		if (isPos == null)
		{
			player.addChatMessage(new TextComponentString(
					"Player doesn't exist or player doesn't have an island."));
			return;
		}

		BlockPos visitPos = new BlockPos(isPos.getX() * ConfigOptions.islandDistance, 88,
				isPos.getY() * ConfigOptions.islandDistance);

		player.setGameType(GameType.SPECTATOR);

		if (player.connection.playerEntity.dimension != 0)
			player.connection.playerEntity.changeDimension(0);
		player.connection.setPlayerLocation(visitPos.getX() + 0.5, visitPos.getY(),
				visitPos.getZ() + 0.5, player.rotationYaw, player.rotationPitch);

	}

	void guide(EntityPlayerMP player, String[] args, World world) throws CommandException
	{
		if (Minecraft.getMinecraft().thePlayer.worldObj.isRemote)
		{
			Minecraft.getMinecraft().thePlayer.openGui(SkyResources.instance,
					ModGuiHandler.GuideGUI, player.worldObj, player.getPosition().getX(),
					player.getPosition().getY(), player.getPosition().getZ());
		}
	}

	void reset(EntityPlayerMP player, String[] args, World world) throws CommandException
	{
		if (!References.worldOneChunk)
		{
			leavePlatform(player, new String[]
			{ "" });
			newPlatform(player, args);
		} else
		{

			PlayerList players = world.getMinecraftServer().getPlayerList();
			for (EntityPlayerMP p : players.getPlayerList())
			{
				player.addChatMessage(new TextComponentString("Lag incoming for reset!"));
			}
			for (int x = -8; x < 9; x++)
			{
				for (int z = -8; z < 9; z++)
				{
					for (int y = 0; y < 256; y++)
					{
						world.setBlockState(new BlockPos(x, y, z), Blocks.AIR.getDefaultState(), 2);
					}
				}
			}
			if (args.length > 1)
			{
				Integer i = -1;

				try
				{
					i = Integer.parseInt(args[1]);
				} catch (Exception e)
				{
					if (args[1].equals("sand"))
						i = 0;
					else if (args[1].equals("snow"))
						i = 1;
					else if (args[1].equals("grass"))
						i = 2;
				}

				if (i > -1 && i < 3)
				{

					if (player.connection.playerEntity.dimension != 0)
						player.connection.playerEntity.changeDimension(0);
					EventHandler.spawnPlayer(player, new BlockPos(0, 88, 0), i);
				}
			} else
			{
				EventHandler.createSpawn(world, new BlockPos(0, 88, 0));
			}
			for (EntityPlayerMP p : players.getPlayerList())
			{
				p.inventory.clear();

				if (player.connection.playerEntity.dimension != 0)
					player.connection.playerEntity.changeDimension(0);
				EventHandler.spawnPlayer(p, new BlockPos(0, 88, 0), false);
				p.addChatMessage(new TextComponentString("Chunk Reset!"));
			}
		}
	}

	void showHelp(EntityPlayerMP player)
	{

		player.addChatMessage(new TextComponentString(
				"create (optional int/string)<type> : Spawn a new platform. Must not already be on an island."));

		player.addChatMessage(new TextComponentString(
				"invite <player> : Have another player join your island. Player must not already be on an island."));

		player.addChatMessage(new TextComponentString(
				"leave : Leave your island, clear inventory, and go to spawn.\n      (If you are the last person, no one can claim that island again.)"));

		player.addChatMessage(new TextComponentString(
				"home : Teleport back to your home island. Must be at least "
						+ ConfigOptions.islandDistance / 2 + " blocks away."));

		player.addChatMessage(new TextComponentString("spawn : Teleport back to spawn (0, 0)."));

		player.addChatMessage(new TextComponentString(
				"reset (optional int/string)<type> : Resets the platform and clears the players' inventory.\n      (If it doesn't clear everything, be nice and toss the rest? Maybe?\nNot recommended unless all players for that island are online)"));

		player.addChatMessage(new TextComponentString(
				"onechunk : Play in one chunk, on one island. Also resets the spawn chunk."
						+ (ConfigOptions.oneChunkCommandAllowed ? ""
								: TextFormatting.RED
										+ "\n THE COMMAND IS NOT ALLOWED TO BE USED. SET THE CONFIG OPTION TO TRUE.")));

		player.addChatMessage(new TextComponentString("guide : Opens the guide."));

		player.addChatMessage(new TextComponentString(
				"visit <player> : Visit another player's island in spectator mode."));
	}

	void newPlatform(EntityPlayerMP player, String[] args) throws CommandException
	{
		if (args.length > 2)
		{
			player.addChatMessage(new TextComponentString("Must have 0 or 1 argument"));
			return;
		}
		if (References.worldOneChunk)
		{
			player.addChatMessage(new TextComponentString("Can't use this command in this mode."));
			return;
		}
		if (References.initialIslandDistance != ConfigOptions.islandDistance)
		{
			player.addChatMessage(new TextComponentString(
					"This isn't going to work. The island distance has changed!"));
			return;
		}

		if (References.playerHasIsland(player.getName()))
		{
			player.addChatMessage(new TextComponentString("You already have an island!"));
			return;
		}

		IslandPos position = References.getNextIsland();

		if (args.length > 1 && ConfigOptions.worldSpawnType == 0)
		{
			Integer i = -1;

			try
			{
				i = Integer.parseInt(args[1]);
			} catch (Exception e)
			{
				if (args[1].equals("sand"))
					i = 0;
				else if (args[1].equals("snow"))
					i = 1;
				else if (args[1].equals("grass"))
					i = 2;
			}

			if (i > -1 && i < 3)
			{

				if (player.connection.playerEntity.dimension != 0)
					player.connection.playerEntity.changeDimension(0);

				EventHandler.spawnPlayer(player,
						new BlockPos(position.getX() * ConfigOptions.islandDistance, 88,
								position.getY() * ConfigOptions.islandDistance),
						i);
			}
		} else
		{
			if (args.length > 1)
			{
				player.addChatMessage(new TextComponentString(
						"You can't pick your island as the config overrides it!"));
			}

			if (player.connection.playerEntity.dimension != 0)
				player.connection.playerEntity.changeDimension(0);
			EventHandler.spawnPlayer(player,
					new BlockPos(position.getX() * ConfigOptions.islandDistance, 88,
							position.getY() * ConfigOptions.islandDistance),
					true);
		}
		References.CurrentIslandsList
				.add(new IslandPos(position.getX(), position.getY(), player.getName()));
	}

	void inviteOther(EntityPlayerMP player, String[] args, World world) throws CommandException
	{
		if (args.length != 2)
		{
			player.addChatMessage(new TextComponentString("Must have 1 argument"));
			return;
		}
		if (References.worldOneChunk)
		{
			player.addChatMessage(new TextComponentString("Can't use this command in this mode."));
			return;
		}
		if (References.initialIslandDistance != ConfigOptions.islandDistance)
		{
			player.addChatMessage(new TextComponentString(
					"This isn't going to work. The island distance has changed!"));
			return;
		}
		String player2Name = args[1];
		if (player2Name.equals(player.getName()))
		{
			player.addChatMessage(new TextComponentString(player2Name + " is yourself."));
			return;
		}

		if (!References.playerHasIsland(player.getName()))
		{
			player.addChatMessage(new TextComponentString("You don't have an island."));
			return;
		}

		if (References.playerHasIsland(player2Name))
		{
			player.addChatMessage(new TextComponentString(player2Name + " already has an island!"));
			return;
		}

		EntityPlayerMP player2 = (EntityPlayerMP) world.getPlayerEntityByName(player2Name);
		if (player2 == null)
		{
			player.addChatMessage(new TextComponentString(player2Name + " doesn't exist."));
			return;
		}

		IslandPos position = References.getPlayerIsland(player.getName());

		References.addPlayer(player2Name, position);

		position = References.getPlayerIsland(player.getName());

		for (String name : position.getPlayerNames())
		{
			EntityPlayerMP p = (EntityPlayerMP) world.getPlayerEntityByName(name);
			if (p != null)
				p.addChatMessage(new TextComponentString(player2Name + " joined your island!"));
		}

		if (player.connection.playerEntity.dimension != 0)
			player.connection.playerEntity.changeDimension(0);
		References.tpPlayerToPosSpawn(player2, new BlockPos(position.getX() * ConfigOptions.islandDistance, 88,
				position.getY() * ConfigOptions.islandDistance));
	}

	void leavePlatform(EntityPlayerMP player, String[] args) throws CommandException
	{
		if (args.length > 1)
		{
			player.addChatMessage(new TextComponentString("Must have no arguments"));
			return;
		}
		if (References.worldOneChunk)
		{
			player.addChatMessage(new TextComponentString("Can't use this command in this mode."));
			return;
		}
		if (References.initialIslandDistance != ConfigOptions.islandDistance)
		{
			player.addChatMessage(new TextComponentString(
					"This isn't going to work. The island distance has changed!"));
			return;
		}

		if (!References.playerHasIsland(player.getName()))
		{
			player.addChatMessage(new TextComponentString("You don't have an island!"));
			return;
		}

		References.removePlayer(player.getName());
		player.addChatMessage(new TextComponentString("You are now free to join another island!"));

		player.inventory.clear();

		if (player.connection.playerEntity.dimension != 0)
			player.connection.playerEntity.changeDimension(0);
		References.tpPlayerToPosSpawn(player, new BlockPos(0, 88, 0));
	}

	void tpHome(EntityPlayerMP player, String[] args) throws CommandException
	{
		if (args.length > 1)
		{
			player.addChatMessage(new TextComponentString("Must have no arguments"));
			return;
		}
		if (References.worldOneChunk)
		{
			player.addChatMessage(new TextComponentString("Can't use this command in this mode."));
			return;
		}
		if (References.initialIslandDistance != ConfigOptions.islandDistance)
		{
			player.addChatMessage(new TextComponentString(
					"This isn't going to work. The island distance has changed!"));
			return;
		}

		IslandPos isPos = References.getPlayerIsland(player.getName());

		if (isPos == null)
		{
			player.addChatMessage(new TextComponentString("You don't have an island yet."));
			return;
		}

		BlockPos home = new BlockPos(isPos.getX() * ConfigOptions.islandDistance, 88,
				isPos.getY() * ConfigOptions.islandDistance);

		if (Math.hypot(player.posX - home.getX() - 0.5,
				player.posZ - home.getZ() - 0.5) < ConfigOptions.islandDistance / 2)
		{
			player.addChatMessage(
					new TextComponentString("You are too close to home!\nYou must be at least "
							+ (ConfigOptions.islandDistance / 2) + " blocks away!"));
			return;
		}

		if (player.isSpectator())
		{
			player.setGameType(GameType.SURVIVAL);
		}

		if (player.connection.playerEntity.dimension != 0)
			player.connection.playerEntity.changeDimension(0);
		References.tpPlayerToPos(player, home);

	}

	void tpSpawn(EntityPlayerMP player, String[] args) throws CommandException
	{
		if (References.worldOneChunk)
		{
			player.addChatMessage(new TextComponentString("Can't use this command in this mode."));
			return;
		}

		if (player.isSpectator())
		{
			player.setGameType(GameType.SURVIVAL);
		}

		if (player.connection.playerEntity.dimension != 0)
			player.connection.playerEntity.changeDimension(0);
		References.tpPlayerToPos(player, new BlockPos(0, 88, 0));
	}
}
