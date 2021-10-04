package com.AscalonPS.net.packet.impl;

import com.AscalonPS.model.definitions.NpcDefinition;
import com.AscalonPS.net.packet.Packet;
import com.AscalonPS.net.packet.PacketListener;
import com.AscalonPS.world.entity.impl.player.Player;

public class ExamineNpcPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        int npc = packet.readShort();
        if (npc <= 0) {
            return;
        }
        NpcDefinition npcDef = NpcDefinition.forId(npc);
        if ((player.getRights().isStaff()) && (npcDef != null)) {
            player.getPacketSender().sendMessage("ID: " + npcDef.getId());
        }
        if (npcDef != null) {
            player.getPacketSender().sendMessage(npcDef.getExamine());
        }
    }

}
