package com.AscalonPS.net.packet.impl;

import com.AscalonPS.net.packet.Packet;
import com.AscalonPS.net.packet.PacketListener;
import com.AscalonPS.world.entity.impl.player.Player;

public class CloseInterfacePacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        player.getPacketSender().sendClientRightClickRemoval();
        player.getPacketSender().sendInterfaceRemoval();
        //	player.getPacketSender().sendTabInterface(Constants.CLAN_CHAT_TAB, 29328); //Clan chat tab
        //player.getAttributes().setSkillGuideInterfaceData(null);
    }
}
