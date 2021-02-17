package com.trimorph.party;

import java.util.ArrayList;

import com.trimorph.party.speaker.Speak;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Party {
	/**
	 * The leader of the party.
	 */
	public ProxiedPlayer leader;
	
	/**
	 * Players that have been invited to the party.
	 */
	public ArrayList<ProxiedPlayer> invited = new ArrayList<ProxiedPlayer>();
	
	/**
	 * Members of the party.
	 */
	public ArrayList<ProxiedPlayer> players = new ArrayList<ProxiedPlayer>();
	
	/**
	 * Creates a new party.
	 * @param player The owner of the party.
	 * @return The party that was created.
	 */
	public static Party create(ProxiedPlayer player) {
		Party party = new Party();
		party.leader = player;
		
		PartyManager.addParty(party);
		
		return party;
	}
	
	/**
	 * Adds a player to the player list.
	 * @param player
	 */
	public void addPlayer(ProxiedPlayer player) {
		players.add(player);
	}
	
	/**
	 * Adds a player to the invited player list.
	 * @param player
	 */
	public void invitePlayer(ProxiedPlayer player) {
		invited.add(player);
	}
	
	/**
	 * Disbands the party.
	 */
	public void disband() {
		PartyManager.removeParty(this);
	}
	
	/**
	 * Sends message to all members of the party.
	 * @param message
	 */
	public void broadcast(String message) {
		for (ProxiedPlayer plr : this.players) {
			Speak.sendMessage(plr, message);
		}
		Speak.sendMessage(this.leader, message);
	}
}
