package com.trimorph.party.commands;

import com.trimorph.party.Party;
import com.trimorph.party.PartyManager;
import com.trimorph.party.speaker.Speak;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class PartyCommand extends Command {
	public PartyCommand() {
		super("party", null, "p");
	}
	
	public void execute(CommandSender sender, String[] args) {
		if (sender != null && sender instanceof ProxiedPlayer) {
			ProxiedPlayer plr = (ProxiedPlayer) sender;
			
			if (args.length == 0)
				PartyCommand.main(plr);
			else {
				switch (args[0]) {
				case "help":
				case "?":
					PartyCommand.main(plr);
					
					break;
					
				case "create":
					PartyCommand.create(plr);
					
					break;
				
				case "leave":
					PartyCommand.leave(plr);
					
					break;
				
				case "invite":
				case "i":
					if (args.length > 1) {
						PartyCommand.invite(plr, args[1]);
					} else
						Speak.sendMessage(plr, ChatColor.RED + "Usage: /party invite <player>");
					
					break;
				
				case "acceptinvite":
					if (args.length > 1) {
						PartyCommand.acceptinvite(plr, args[1]);
					} else
						Speak.sendMessage(plr, ChatColor.RED + "Usage: /party acceptinvite <player>"); 
					
					break;
				
				case "warp":
					PartyCommand.warp(plr);
					
					break;
				
				case "disband":
					PartyCommand.disband(plr);
					
					break;
					
				default:
					PartyCommand.invite(plr, args[0]);
					
					break;
				}
			}
		}
	}
	
	/**
	 * The main/help command.
	 * @param sender
	 */
	public static void main(ProxiedPlayer sender) {
		Speak.sendMessage(sender, "/p help - Shows this list.");
		Speak.sendMessage(sender, "/p create - Creates a party.");
		Speak.sendMessage(sender, "/p invite <player> - Invites a player to the party.");
		Speak.sendMessage(sender, "/p disband - Disbands the party.");
		Speak.sendMessage(sender, "/p leave - Makes you leave the party you're in.");
		Speak.sendMessage(sender, "/p acceptinvite <player> - Accepts a party invite.");
		Speak.sendMessage(sender, "/p warp - Sends all players to your current server.");
	}
	
	/**
	 * Creates a new party.
	 * @param sender
	 */
	public static void create(ProxiedPlayer sender) {
		Party party = PartyManager.getPartyByMember(sender);
		if (party == null) {
			Party.create(sender);
			Speak.sendMessage(sender, "Created party.");
		} else
			Speak.sendMessage(sender, ChatColor.RED + "You must not be in a party to run this command.");
	}
	
	/**
	 * Leaves the party.
	 * @param sender
	 */
	public static void leave(ProxiedPlayer sender) {
		Party party = PartyManager.getPartyByMember(sender);
		
		if (party != null) {
			if (party.leader.equals(sender)) {
				party.broadcast(sender.getName() + " left the party.  Since they were the party leader, the party has been disbanded.");
				party.disband();
			} else {
				party.players.remove(sender);
				party.broadcast(sender.getName() + " left the party.");
			}
		}
	}
	
	/**
	 * Invites a player to the party.
	 * @param sender
	 * @param invited
	 */
	public static void invite(ProxiedPlayer sender, String invited) {
		ProxiedPlayer plr = ProxyServer.getInstance().getPlayer(invited);
		Party p = PartyManager.getPartyByMember(sender);
		
		if (plr != null) {
			if (p != null) {
				if (sender.equals(plr)) {
					Speak.sendMessage(sender, ChatColor.RED + "You cannot invite yourself to the party.");
					return;
				} else if (p.leader.equals(plr) || p.players.contains(plr)) {
					Speak.sendMessage(sender, ChatColor.RED + "That player is already in the party!");
					return;
				} else if (p.invited.contains(plr)) {
					Speak.sendMessage(sender, ChatColor.RED + "You have already invited that player!");
					return;
				}
				
				
				p.invitePlayer(plr);
				Speak.sendClickableMessage(plr, "You have been invited to " + p.leader.getName() + "'s party.  Click here to join!", "/p acceptinvite " + party.leader.getName());
				
				p.broadcast(plr.getName() + " has been invited to the party.");
			} else
				Speak.sendMessage(sender, ChatColor.RED + "You must be in a party to run this command.");
		} else
			Speak.sendMessage(sender, ChatColor.RED + "That player isn't online.");
	}
	
	/**
	 * Accepts a party invite.
	 * @param sender
	 * @param leaderName
	 */
	public static void acceptinvite(ProxiedPlayer sender, String leaderName) {
		ProxiedPlayer leader = ProxyServer.getInstance().getPlayer(leaderName);
		
		if (leader != null) {
			Party party = PartyManager.getPartyByMember(leader);
			Party party1 = PartyManager.getPartyByMember(sender);
			
			if (party1 == null) {
				if (party != null) {
					if (party.invited.contains(sender)) {
						party.players.add(sender);
					} else
						Speak.sendMessage(sender, ChatColor.RED + "You weren't invited to that party.");
				} else
					Speak.sendMessage(sender, ChatColor.RED + "You weren't invited to that party.");
			} else
				Speak.sendMessage(sender, ChatColor.RED + "You are already in a party!");
		} else
			Speak.sendMessage(sender, ChatColor.RED + "You weren't invited to that party.");
	}
	
	/**
	 * Warps all members of a party to the server that the party leader is in.
	 * @param sender
	 */
	public static void warp(ProxiedPlayer sender) {
		Party p = PartyManager.getPartyByMember(sender);
		
		if (p != null) {
			if (p.leader.equals(sender)) {
				for (ProxiedPlayer plr : p.players) {
					plr.connect(p.leader.getServer().getInfo());
				}
				
				p.broadcast("You have been warped to the server that the leader of the party is in.");
			} else
				Speak.sendMessage(sender, ChatColor.RED + "You must be the party leader to run this command.");
		} else
			Speak.sendMessage(sender, ChatColor.RED + "You must be in a party to run this command.");
	}
	
	/**
	 * Disbands the party.
	 * @param sender
	 */
	public static void disband(ProxiedPlayer sender) {
		Party p = PartyManager.getPartyByMember(sender);
		
		if (p != null) {
			if (p.leader.equals(sender)) {
				p.broadcast(ChatColor.RED + "The party has been disbanded!");
				p.disband();
			} else
				Speak.sendMessage(sender, ChatColor.RED + "You must be the party leader to run this command.");
		} else
			Speak.sendMessage(sender, ChatColor.RED + "You must be in a party to run this command.");
	}
}
