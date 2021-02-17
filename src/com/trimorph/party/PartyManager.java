package com.trimorph.party;

import java.util.ArrayList;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PartyManager {
	private static ArrayList<Party> partyList = new ArrayList<Party>();
	
	/**
	 * Add a party to the list.
	 * @param party
	 */
	public static void addParty(Party party) {
		partyList.add(party);
	}
	
	/**
	 * Gets a party from the list by member.
	 * @param member
	 * @return
	 */
	public static Party getPartyByMember(ProxiedPlayer member) {
		for (Party party : partyList) {
			if (party.players.contains(member) || party.leader.equals(member)) {
				return party;
			}
		}
		
		return null;
	}
	
	public static void removeParty(Party party) {
		partyList.remove(party);
	}
}
