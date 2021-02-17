package com.trimorph.party.commands;

import com.trimorph.party.Party;
import com.trimorph.party.PartyManager;
import com.trimorph.party.speaker.Speak;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class PartyChatCommand extends Command {
	public PartyChatCommand() {
		super("partychat", null, "pc", "pchat");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			if (args.length > 0)
				PartyChatCommand.chat((ProxiedPlayer) sender, args);
		}
	}
	
	/**
	 * Chats in the party's channel.
	 * @param sender
	 * @param args
	 */
	public static void chat(ProxiedPlayer sender, String[] args) {
		if (sender != null) {
			String message = sender.getDisplayName() + ": " + String.join(" ", args);
			Party party = PartyManager.getPartyByMember(sender);
			
			if (party != null) {
				party.broadcast(message);
			} else
				Speak.sendMessage(sender, ChatColor.RED + "You must be in a party to run this command.");
		}
	}
}
