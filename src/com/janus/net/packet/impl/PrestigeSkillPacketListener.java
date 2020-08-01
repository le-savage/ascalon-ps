package com.janus.net.packet.impl;

import com.janus.model.Skill;
import com.janus.net.packet.Packet;
import com.janus.net.packet.PacketListener;
import com.janus.world.entity.impl.player.Player;

public class PrestigeSkillPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        int prestigeId = packet.readShort();
        Skill skill = Skill.forPrestigeId(prestigeId);
        if (skill == null) {
            return;
        }
        if (player.getInterfaceId() > 0) {
            player.getPacketSender().sendMessage("Please close all interfaces before doing this.");
            return;
        }
        player.getSkillManager().resetSkill(skill, true);
    }

}
