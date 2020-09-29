package com.janus.net.packet.impl;

import com.janus.model.definitions.NpcDefinition;
import com.janus.net.packet.Packet;
import com.janus.net.packet.PacketListener;
import com.janus.world.entity.impl.player.Player;

public class ExamineNpcPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        int npc = packet.readShort();
        if (npc <= 0) {
            return;
        }
        NpcDefinition npcDef = NpcDefinition.forId(npc);
        if ((player.getRights().isStaff()) && (npcDef != null)) {
            player.getPacketSender().sendMessage("ID: " + npcDef.getId() + " Player Coords [X: "+ player.getPosition().getX() + ", Y: "+ player.getPosition().getY()+"]");
        }
        if (npcDef != null) {
            player.getPacketSender().sendMessage(npcDef.getExamine());
        }
    }

}
