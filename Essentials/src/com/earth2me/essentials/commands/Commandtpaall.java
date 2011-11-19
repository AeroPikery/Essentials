package com.earth2me.essentials.commands;

import com.earth2me.essentials.User;
import com.earth2me.essentials.Util;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Commandtpaall extends EssentialsCommand
{
	public Commandtpaall()
	{
		super("tpaall");
	}

	@Override
	public void run(final Server server, final CommandSender sender, final String commandLabel, final String[] args) throws Exception
	{
		if (args.length < 1)
		{
			if (sender instanceof Player)
			{
				teleportAAllPlayers(server, sender, ess.getUser(sender));
				return;
			}
			throw new NotEnoughArgumentsException();
		}

		final User player = getPlayer(server, args, 0);
		teleportAAllPlayers(server, sender, player);
	}

	private void teleportAAllPlayers(final Server server, final CommandSender sender, final User user)
	{
		sender.sendMessage(Util.i18n("teleportAAll"));
		for (Player onlinePlayer : server.getOnlinePlayers())
		{
			final User player = ess.getUser(onlinePlayer);
			if (user == player)
			{
				continue;
			}
			if (!player.isTeleportEnabled())
			{
				continue;
			}
			try
			{
				player.requestTeleport(user, true);
				player.sendMessage(Util.format("teleportHereRequest", user.getDisplayName()));
				player.sendMessage(Util.i18n("typeTpaccept"));
			}
			catch (Exception ex)
			{
				ess.showError(sender, ex, getName());
			}
		}
	}
}
