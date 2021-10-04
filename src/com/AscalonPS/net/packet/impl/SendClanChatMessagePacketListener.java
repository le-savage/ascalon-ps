package com.AscalonPS.net.packet.impl;

/*public class SendClanChatMessagePacketListener implements PacketListener {

	@Override
	public void handleMessage(Player player, Packet packet) {
		String clanMessage = Misc.readString(packet.getBuffer());

						String[] notAllowed = {String.valueOf('\u0022'),String.valueOf('\u002A'),String.valueOf('\u0021'),String.valueOf('\u0023'),String.valueOf('\u0024'),String.valueOf('\u0025'),String.valueOf('\u005E'),String.valueOf('\u0026'),String.valueOf('\u002D'),String.valueOf('\u002F'),String.valueOf('\u005B'),String.valueOf('\u005D'),String.valueOf('\u007D'),String.valueOf('\u007B'),String.valueOf('\u223C'),String.valueOf('\u0060'),String.valueOf('\u003B'),String.valueOf('\u007C'),String.valueOf('\u0040'),"~","=","_","<",">"};
						for (String s : notAllowed) {
											if (clanMessage.contains(s)) {
																player.getPacketSender().sendMessage("You can't use special symbols in Clan Chat, sorry.");
																return;
											}
											clanMessage.replaceAll("\\\\", "");
						}
		*//*PlayerLogs.log(player.getUsername(),"[CLANCHAT]: "+clanMessage);*//*
		PlayerLogs.logMasterChatLog("ClanChatLog", "[CLANCHAT]: "+player.getUsername()+ ": "+clanMessage);
		//PlayerLogs.logMasterChatLog("MasterChatLog", "[CLANCHAT]: "+player.getUsername()+ ": "+clanMessage);
		//DiscordMessenger.sendChatLog("[CHANCC]: "+player.getUsername()+ ": "+clanMessage);
		if(clanMessage == null || clanMessage.length() < 1)
			return;
		if(PlayerPunishment.muted(player.getUsername()) || PlayerPunishment.IPMuted(player.getHostAddress())) {
			player.getPacketSender().sendMessage("You are muted and cannot chat.");
			return;
		}
		if(Misc.blockedWord(clanMessage)) {
			DialogueManager.sendStatement(player, "A word was blocked in your sentence. Please do not repeat it!");
			return;
		}
		ClanChatManager.sendMessage(player, clanMessage);
	}

}*/

import com.AscalonPS.net.packet.Packet;
import com.AscalonPS.net.packet.PacketListener;
import com.AscalonPS.util.Misc;
import com.AscalonPS.world.content.PlayerLogs;
import com.AscalonPS.world.content.PlayerPunishment;
import com.AscalonPS.world.content.clan.ClanChatManager;
import com.AscalonPS.world.content.dialogue.DialogueManager;
import com.AscalonPS.world.entity.impl.player.Player;

public class SendClanChatMessagePacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        String clanMessage = Misc.readString(packet.getBuffer());
        if (clanMessage == null || clanMessage.length() < 1)
            return;
        if (PlayerPunishment.muted(player.getUsername()) || PlayerPunishment.IPMuted(player.getHostAddress())) {
            player.getPacketSender().sendMessage("You are muted and cannot chat.");
            return;
        }
        if (Misc.blockedWord(clanMessage) && !(player.getRights().isStaff())) {
            DialogueManager.sendStatement(player, "A word was blocked in your sentence. Please do not repeat it!");
            return;
        }
        PlayerLogs.logMasterChatLog("ClanChatLog", "[CLANCHAT]: " + player.getUsername() + ": " + clanMessage);
        ClanChatManager.sendMessage(player, clanMessage);
    }

}