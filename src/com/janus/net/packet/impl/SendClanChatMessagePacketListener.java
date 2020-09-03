package com.janus.net.packet.impl;

import com.janus.net.packet.Packet;
import com.janus.net.packet.PacketListener;
import com.janus.util.Misc;
import com.janus.world.content.PlayerLogs;
import com.janus.world.content.PlayerPunishment;
import com.janus.world.content.clan.ClanChatManager;
import com.janus.world.content.dialogue.DialogueManager;
import com.janus.world.entity.impl.player.Player;

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