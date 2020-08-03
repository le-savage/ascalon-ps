package com.janus.world.content;

import com.janus.model.Skill;
import com.janus.world.entity.impl.player.Player;

public class DungeoneeringLamp {

    public static int lampID = 18348;

    public static void handleLamp(Player player, int item) {
        if (item == lampID) {
            if (player.getClickDelay().elapsed(2000)) {
                int xp = (player.getSkillManager().getCurrentLevel(Skill.DUNGEONEERING) * 1000) + 5000;
                player.getSkillManager().addExperience(Skill.DUNGEONEERING, xp);
                player.getPacketSender().sendMessage(xp + " Dungeoneering XP Claimed!");
                player.getInventory().delete(lampID, 1);
            }
        }
    }
}