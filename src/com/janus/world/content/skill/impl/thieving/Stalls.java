package com.janus.world.content.skill.impl.thieving;

import com.janus.model.*;
import com.janus.model.Locations.Location;
import com.janus.util.RandomUtility;
import com.janus.world.content.Achievements;
import com.janus.world.content.Achievements.AchievementData;
import com.janus.world.content.transportation.TeleportHandler;
import com.janus.world.entity.impl.player.Player;

public class Stalls {


    public static void stealFromStall(Player player, int lvlreq, int xp, int reward, String message) {
        player.getPacketSender().sendRichPresenceState("Stealing from Stalls!");
        player.getPacketSender().sendSmallImageKey("thieving");
        player.getPacketSender().sendRichPresenceSmallPictureText("Lvl: " + player.getSkillManager().getCurrentLevel(Skill.THIEVING));
        if (player.getInventory().getFreeSlots() < 1) {
            player.getPacketSender().sendMessage("You need some more inventory space to do this.");
            return;
        }
        if (player.getCombatBuilder().isBeingAttacked()) {
            player.getPacketSender().sendMessage("You must wait a few seconds after being out of combat before doing this.");
            return;
        }
        if (!player.getClickDelay().elapsed(2000))
            return;
        if (player.getSkillManager().getMaxLevel(Skill.THIEVING) < lvlreq) {
            player.getPacketSender().sendMessage("You need a Thieving level of at least " + lvlreq + " to steal from this stall.");
            return;
        }
        if (player.getLocation() == Location.DONATOR_ZONE) {
            if (RandomUtility.RANDOM.nextInt(35) == 5) {
                TeleportHandler.teleportPlayer(player, new Position(2338, 9800), player.getSpellbook().getTeleportType());
                player.getPacketSender().sendMessage("You were caught stealing and teleported away from the stall!");
                return;
            }
        }
        player.performAnimation(new Animation(881));
        player.getPacketSender().sendMessage(message);
        player.getPacketSender().sendInterfaceRemoval();
        player.getSkillManager().addExperience(Skill.THIEVING, xp);
        player.getClickDelay().reset();
        if (player.getRights() == PlayerRights.DONATOR) {
            player.getInventory().add(995, 3000);

        }
        if (player.getRights() == PlayerRights.SUPER_DONATOR || player.getRights() == PlayerRights.SUPPORT) {
            player.getInventory().add(995, 5000);
        }
        if (player.getRights() == PlayerRights.EXTREME_DONATOR || player.getRights() == PlayerRights.MODERATOR) {
            player.getInventory().add(995, 7500);
        }
        if (player.getRights() == PlayerRights.LEGENDARY_DONATOR || player.getRights() == PlayerRights.ADMINISTRATOR) {
            player.getInventory().add(995, 10000);
        }
        if (player.getRights() == PlayerRights.UBER_DONATOR) {
            player.getInventory().add(995, 15000);
        }

        if (player.getLocation() == Location.DONATOR_ZONE) {
            Item item = new Item(reward);
            int value = item.getDefinition().getValue();
            player.getInventory().add(995, value);

        } else {
            player.getInventory().add(reward, 1);

        }

        player.getSkillManager().stopSkilling();
        if (reward == 15009)
            Achievements.finishAchievement(player, AchievementData.STEAL_A_RING);
        else if (reward == 11998) {
            Achievements.doProgress(player, AchievementData.STEAL_140_SCIMITARS);
            Achievements.doProgress(player, AchievementData.STEAL_5000_SCIMITARS);
        }
    }


}
