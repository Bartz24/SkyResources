package com.bartz24.skyresources.base.commands;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.IslandPos;
import com.bartz24.skyresources.References;
import com.bartz24.skyresources.SkyResourcesSaveData;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.events.EventHandler;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class CreatePlatformCommand extends CommandBase implements ICommand
{
	private List<String> aliases;

	public CreatePlatformCommand()
	{
		aliases = new ArrayList<String>();
		aliases.add("platform");
		aliases.add("plat");
	}

	@Override
	public List getCommandAliases()
	{
		return aliases;
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
		return "platform";
	}

	@Override
	public List getTabCompletionOptions(MinecraftServer server,
			ICommandSender sender, String[] args, BlockPos pos)
	{
		return null;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender,
			String[] args) throws CommandException
	{
		World world = sender.getEntityWorld();
		EntityPlayerMP player = (EntityPlayerMP) world.getPlayerEntityByName(
				sender.getCommandSenderEntity().getName());

		if (args.length == 0)
			showHelp(player);
		else
		{
			String subCommand = args[0];
			subCommand = subCommand.trim();

			if (subCommand.equals("create"))
			{
				newPlatform(player, args);
			} else if (subCommand.equals("invite"))
			{
				inviteOther(player, args, world);
			} else if (subCommand.equals("leave"))
			{
				leavePlatform(player, args);
			} else if (subCommand.equals("home"))
			{
				tpHome(player, args);
			}
		}

	}

	void showHelp(EntityPlayerMP player)
	{

		player.addChatMessage(new TextComponentString(
				"create : Spawn a new platform. Must not already be on an island."));

		player.addChatMessage(new TextComponentString(
				"invite <player> : Have another player join your island. Player must not already be on an island"));

		player.addChatMessage(new TextComponentString(
				"leave : Leave your island. Must be someone else on the island to leave it to."));

		player.addChatMessage(new TextComponentString(
				"home : Teleport back to your home island. Must be at least "
						+ ConfigOptions.islandDistance / 2 + " blocks away."));
	}

	void newPlatform(EntityPlayerMP player, String[] args)
			throws CommandException
	{
		if (args.length > 1)
		{
			player.addChatMessage(
					new TextComponentString("Must have no arguments"));
			return;
		}

		if (References.playerHasIsland(player.getName()))
		{
			player.addChatMessage(
					new TextComponentString("You already have an island!"));
			return;
		}

		IslandPos position = References.getNextIsland();

		EventHandler.spawnPlayer(player,
				new BlockPos(position.getX() * ConfigOptions.islandDistance, 86,
						position.getY() * ConfigOptions.islandDistance),
				true);

		References.CurrentIslandsList.add(new IslandPos(position.getX(),
				position.getY(), player.getName()));
	}

	void inviteOther(EntityPlayerMP player, String[] args, World world)
			throws CommandException
	{
		if (args.length != 2)
		{
			player.addChatMessage(
					new TextComponentString("Must have 1 argument"));
			return;
		}
		String player2Name = args[1];
		if (player2Name.equals(player.getName()))
		{
			player.addChatMessage(
					new TextComponentString(player2Name + " is yourself."));
			return;
		}

		if (References.playerHasIsland(player2Name))
		{
			player.addChatMessage(new TextComponentString(
					player2Name + " already has an island!"));
			return;
		}

		EntityPlayerMP player2 = (EntityPlayerMP) world
				.getPlayerEntityByName(player2Name);
		if (player2 == null)
		{
			player.addChatMessage(
					new TextComponentString(player2Name + " doesn't exist."));
			return;
		}

		IslandPos position = References.getPlayerIsland(player.getName());

		References.addPlayer(player2Name, position);

		position = References.getPlayerIsland(player.getName());

		for (String name : position.getPlayerNames())
		{
			EntityPlayerMP p = (EntityPlayerMP) world
					.getPlayerEntityByName(name);
			if (p != null)
				p.addChatMessage(new TextComponentString(
						player2Name + " joined your island!"));
		}

		EventHandler.spawnPlayer(player2,
				new BlockPos(position.getX() * ConfigOptions.islandDistance, 86,
						position.getY() * ConfigOptions.islandDistance),
				false);
	}

	void leavePlatform(EntityPlayerMP player, String[] args)
			throws CommandException
	{
		if (args.length > 1)
		{
			player.addChatMessage(
					new TextComponentString("Must have no arguments"));
			return;
		}

		if (References.getPlayerIsland(player.getName()).getPlayerNames()
				.size() == 1)
		{
			player.addChatMessage(new TextComponentString(
					"You can't abandon the island! There has to be someone to stay on it!"));
			return;
		}

		References.removePlayer(player.getName());
		player.addChatMessage(new TextComponentString(
				"You are now free to join another island!"));
	}

	void tpHome(EntityPlayerMP player, String[] args) throws CommandException
	{
		if (args.length > 1)
		{
			player.addChatMessage(
					new TextComponentString("Must have no arguments"));
			return;
		}

		IslandPos isPos = References.getPlayerIsland(player.getName());

		BlockPos home = new BlockPos(
				isPos.getX() * ConfigOptions.islandDistance, 86,
				isPos.getY() * ConfigOptions.islandDistance);

		if (Math.hypot(player.posX - home.getX() - 0.5,
				player.posZ - home.getZ() - 0.5) < ConfigOptions.islandDistance
						/ 2)
		{
			player.addChatMessage(new TextComponentString(
					"You are too close to home!\nYou must be at least "
							+ (ConfigOptions.islandDistance / 2)
							+ " blocks away!"));
			return;
		}

		player.teleportTo_(home.getX() + 0.5, home.getY(), home.getZ() + 0.5);
	}
}
