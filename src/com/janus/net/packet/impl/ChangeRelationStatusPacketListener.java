package com.janus.net.packet.impl;

import com.janus.model.PlayerRelations.PrivateChatStatus;
import com.janus.net.packet.Packet;
import com.janus.net.packet.PacketListener;
import com.janus.world.entity.impl.player.Player;

public class ChangeRelationStatusPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        int actionId = packet.readInt();
        player.getRelations().setStatus(PrivateChatStatus.forActionId(actionId), true);
    }

}
