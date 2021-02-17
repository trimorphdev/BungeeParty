package com.trimorph.party;

import com.trimorph.party.commands.PartyChatCommand;
import com.trimorph.party.commands.PartyCommand;

import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin implements Listener {
	public void onEnable() {
		getProxy().getPluginManager().registerListener(this, this);
		
		getProxy().getPluginManager().registerCommand(this, new PartyCommand());
		getProxy().getPluginManager().registerCommand(this, new PartyChatCommand());
	}
}
