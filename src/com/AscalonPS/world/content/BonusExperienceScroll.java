package com.AscalonPS.world.content;

import com.AscalonPS.engine.task.impl.BonusExperienceTask;
import com.AscalonPS.model.Difficulty;
import com.AscalonPS.model.PlayerRights;
import com.AscalonPS.world.entity.impl.player.Player;

public class BonusExperienceScroll {

    public static int scrollID = 18937;

    public static void handleScroll(Player player, int item) {

        if (item == scrollID) {
            Difficulty difficulty = player.getDifficulty();
            if (difficulty.highDifficulty()) {
                player.getPacketSender().sendMessage("Unable to use bonus XP on " + player.getDifficulty().toString() + " difficulty");
                return;
            }
            if (player.getRights().isMember() || player.getRights().isStaff()) {
                PlayerRights rights = player.getRights();
                player.getPacketSender().sendMessage("2 hours added because you're " + (rights.equals(PlayerRights.OWNER) ? "an " : "a ") + rights.toString() + "!");
                BonusExperienceTask.addBonusXp(player, 120);
            } else {
                BonusExperienceTask.addBonusXp(player, 60);
            }
            player.getInventory().delete(18937, 1);
        }
    }
}
