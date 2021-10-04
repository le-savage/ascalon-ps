package com.AscalonPS.net.packet.impl;

import com.AscalonPS.net.packet.Packet;
import com.AscalonPS.net.packet.PacketListener;
import com.AscalonPS.world.entity.impl.player.Player;

//CALLED EVERY 3 MINUTES OF INACTIVITY

public class IdleLogoutPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
		/*if(player.getRights() == PlayerRights.MODERATOR || player.getRights() == PlayerRights.ADMINISTRATOR || player.getRights() == PlayerRights.OWNER || player.getRights() == PlayerRights.DEVELOPER)
			return;*/
		/*if(player.logout() && (player.getSkillManager().getSkillAttributes().getCurrentTask() == null || !player.getSkillManager().getSkillAttributes().getCurrentTask().isRunning())) {
			World.getPlayers().remove(player);
		}*/
        player.setInactive(true);
    }
}
