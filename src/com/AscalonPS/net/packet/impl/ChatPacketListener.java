package com.AscalonPS.net.packet.impl;

import com.AscalonPS.model.ChatMessage.Message;
import com.AscalonPS.model.Flag;
import com.AscalonPS.net.packet.Packet;
import com.AscalonPS.net.packet.PacketListener;
import com.AscalonPS.util.Misc;
import com.AscalonPS.world.content.PlayerLogs;
import com.AscalonPS.world.content.PlayerPunishment;
import com.AscalonPS.world.entity.impl.player.Player;

/**
 * This packet listener manages the spoken text by a player.
 *
 * @author relex lawl
 */

public class ChatPacketListener implements PacketListener {


    @Override
    public void handleMessage(Player player, Packet packet) {
        int effects = packet.readUnsignedByteS();
        int color = packet.readUnsignedByteS();
        int size = packet.getSize();
        byte[] text = packet.readReversedBytesA(size);
        if (PlayerPunishment.muted(player.getUsername()) || PlayerPunishment.IPMuted(player.getHostAddress())) {
            player.getPacketSender().sendMessage("You are muted and cannot chat.");
            return;
        }
        String str = Misc.textUnpack(text, size).toLowerCase().replaceAll(";", ".");
        //	PlayerLogs.log(player.getUsername(),str);
        /*PlayerLogs.log(player.getUsername(),"[PUBLIC]: "+str);*/
        //DiscordMessenger.sendChatLog("[PUBLIC]: "+player.getUsername()+ ": "+str);
        PlayerLogs.logMasterChatLog("PublicChatlog", "[PUBLIC]: " + player.getUsername() + ": " + str);
        //PlayerLogs.logMasterChatLog("MasterChatLog", "[PUBLIC]: "+player.getUsername()+ ": "+str);
        if (Misc.blockedWord(str)) {
            //DialogueManager.sendStatement(player, "A word was blocked in your sentence. Please do not repeat it!");
            //return;
        }
        player.getChatMessages().set(new Message(color, effects, text));
        player.getUpdateFlag().flag(Flag.CHAT);
    }

}
