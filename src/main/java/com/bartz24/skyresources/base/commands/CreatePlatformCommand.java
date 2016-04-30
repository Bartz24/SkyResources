package com.bartz24.skyresources.base.commands;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.events.EventHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class CreatePlatformCommand extends CommandBase implements ICommand
{
	private List<String> aliases;

	public CreatePlatformCommand()
	{
		aliases = new ArrayList<String>();
		aliases.add("platformCreate");
		aliases.add("platCreate");
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
		return "platformCreate <x> <y>";
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

		if (args.length > 2 || args.length <= 1)
		{
			player.addChatMessage(new TextComponentString(
					"Must have  2 arguments"));
			return;
		}

		System.out.println(args.length);
		System.out.println(Integer.parseInt(args[0]) + ", " + Integer.parseInt(args[1]));

		EventHandler.spawnPlayer(player, new BlockPos(Integer.parseInt(args[0]),
				86, Integer.parseInt(args[1])));

	}
}
