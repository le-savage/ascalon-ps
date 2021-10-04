package com.AscalonPS.net.packet.impl;

import com.AscalonPS.model.PlayerRelations.PrivateChatStatus;
import com.AscalonPS.net.packet.Packet;
import com.AscalonPS.net.packet.PacketListener;
import com.AscalonPS.world.entity.impl.player.Player;

public class ChangeRelationStatusPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        int actionId = packet.readInt();
        player.getRelations().setStatus(PrivateChatStatus.forActionId(actionId), true);
    }

}
