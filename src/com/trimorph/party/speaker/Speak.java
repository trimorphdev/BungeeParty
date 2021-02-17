package com.trimorph.party.speaker;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Speak {
	/**
	 * Sends a message to a player.
	 * @param receiver
	 * @param message
	 */
	public static void sendMessage(ProxiedPlayer receiver, String message) {
		ComponentBuilder cb = new ComponentBuilder(ChatColor.BLUE + "Party > " + ChatColor.RESET + message);
		receiver.sendMessage(cb.create());
	}
	
	/**
	 * Sends a message to a player, that when clicked will run the specified command.
	 * @param receiver
	 * @param message
	 * @param command
	 */
	public static void sendClickableMessage(ProxiedPlayer receiver, String message, String cmd) {
		ComponentBuilder cb = new ComponentBuilder(ChatColor.BLUE + "Party > " + ChatColor.RESET + message);
		
		cb.event(new ClickEvent(
			ClickEvent.Action.RUN_COMMAND,
			cmd
		));
		
		receiver.sendMessage(cb.create());
	}
}
