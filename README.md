# BungeeParty
A simple party system for BungeeCord.

## Getting Started
First, download the latest version of BungeeParty from [the Releases page](https://github.com/trimorphdev/BungeeParty/releases) and drag it to your BungeeCord server's `plugins` folder.

Now restart your BungeeCord server and *voila!*  You now should have a working copy of BungeeParty.

## Commands
- `/party` or `/p`: The command that manages parties.  It has several subcommands that allows the party's leader and/or players manage the party.
- - `/p help`: Shows a list of commands and what they do.
- - `/p create`: Creates a party.  Once a party is created, you can use any of the party management subcommands.
- - `/p invite <username>`: Searches across the BungeeCord server and invites a player.  This command will fail if there is no player online with a matching username.
- - `/p leave`: If you are in a party, it removes you from the party.
- - `/p warp`: Sends all players to your server.  You can only run this command if you're the leader of the party.
- - `/p disband`: Disbands the party.  It kicks all players, including the leader, and prevents new players from joining.  You can only run this command if you're the leader of the party.
- - `/p acceptinvite <player>`: Accepts an invite from a player.