package com.AscalonPS.net.packet.impl;

import com.AscalonPS.model.PlayerRights;
import com.AscalonPS.net.packet.Packet;
import com.AscalonPS.net.packet.PacketListener;
import com.AscalonPS.world.content.clan.ClanChatManager;
import com.AscalonPS.world.entity.impl.player.Player;

public class ClickTextMenuPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {

        int interfaceId = packet.readShort();
        int menuId = packet.readByte();

        if (player.getRights() == PlayerRights.DEVELOPER) {
            player.getPacketSender().sendConsoleMessage("Clicked text menu: " + interfaceId + ", menuId: " + menuId);
        }

        if (interfaceId >= 29344 && interfaceId <= 29443) { // Clan chat list
            int index = interfaceId - 29344;
            ClanChatManager.handleMemberOption(player, index, menuId);
        }

    }

    public static final int OPCODE = 222;
}
