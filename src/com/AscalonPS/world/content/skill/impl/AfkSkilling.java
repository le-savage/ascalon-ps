package com.AscalonPS.world.content.skill.impl;

import com.AscalonPS.engine.task.Task;
import com.AscalonPS.engine.task.TaskManager;
import com.AscalonPS.model.Animation;
import com.AscalonPS.model.Skill;
import com.AscalonPS.world.content.combat.weapon.FightStyle;
import com.AscalonPS.world.entity.impl.player.Player;

public class AfkSkilling {

    public static int afkToken = 12852;

    public static boolean canEarnAfkXP(Player player, int skillid) { //Used to determine if the player has 99 in the specified skill. They'll only get XP if they are.
        return player.getSkillManager().getMaxLevel(Skill.forId(skillid)) >= 99;
    }

    public static void afkSkilling(Player player, int req, int xp, int skillid, int anim) {

        if (player.getCombatBuilder().isBeingAttacked()) {
            player.getPacketSender().sendMessage("You must wait a few seconds after being out of combat before doing this.");
            return;
        }
        player.setCurrentTask(new Task(2, player, true) {
            @Override
            public void execute() {
                if (player.getInteractingObject() != null) {
                    player.setPositionToFace(player.getInteractingObject().getPosition().copy());
                }
                player.performAnimation(new Animation(anim));

                if (canEarnAfkXP(player, skillid)) {
                    if ((player.getRights().isMember() || player.getRights().isStaff())) { //Double XP for Donors / Staff
                        player.getSkillManager().addExperience(Skill.forId(skillid), xp * 2);
                    } else {
                        player.getSkillManager().addExperience(Skill.forId(skillid), xp);
                    }
                }
                player.getInventory().add(afkToken, 1);
            }

            @Override
            public void stop() {
                setEventRunning(false);
                player.performAnimation(new Animation(65535));
            }
        });
        TaskManager.submit(player.getCurrentTask());
    }

    public static void afkCombat(Player player, int req, int xp, int skillid) {

        if (player.getSkillManager().getMaxLevel(Skill.forId(skillid)) < req) {
            player.getPacketSender().sendMessage("You need to be level " + req + " in " + Skill.forId(skillid).getName() + " to AFK here.");
            return;
        }
        if (player.getCombatBuilder().isBeingAttacked()) {
            player.getPacketSender().sendMessage("You must wait a few seconds after being out of combat before doing this.");
            return;
        }
        player.setCurrentTask(new Task(2, player, true) {
            @Override
            public void execute() {
                if (player.getInteractingObject() != null) {
                    player.setPositionToFace(player.getInteractingObject().getPosition().copy());
                }
                player.performAnimation(new Animation(423));

                if (player.getFightType().getStyle() == FightStyle.AGGRESSIVE) {
                    player.getPacketSender().sendRichPresenceState("AFK Strength Training");
                    player.getPacketSender().sendSmallImageKey("strength");
                    player.getPacketSender().sendRichPresenceSmallPictureText("Lvl: " + player.getSkillManager().getCurrentLevel(Skill.STRENGTH));
                    if (canEarnAfkXP(player, skillid)) {
                        if ((player.getRights().isMember() || player.getRights().isStaff())) {
                            player.getSkillManager().addExperience(Skill.STRENGTH, xp * 2);
                        } else
                            player.getSkillManager().addExperience(Skill.STRENGTH, xp);
                    }
                } else if (player.getFightType().getStyle() == FightStyle.CONTROLLED) {
                    player.getPacketSender().sendRichPresenceState("AFK Combat Training");
                    player.getPacketSender().sendSmallImageKey("attack");
                    player.getPacketSender().sendRichPresenceSmallPictureText("Lvl: " + player.getSkillManager().getCurrentLevel(Skill.ATTACK));
                    if (canEarnAfkXP(player,skillid)) {
                        if ((player.getRights().isMember() || player.getRights().isStaff())) {
                            player.getSkillManager().addExperience(Skill.ATTACK, (xp * 2) / 3);
                            player.getSkillManager().addExperience(Skill.STRENGTH, (xp * 2) / 3);
                            player.getSkillManager().addExperience(Skill.STRENGTH, (xp * 2) / 3);
                        } else
                            player.getSkillManager().addExperience(Skill.ATTACK, xp / 3);
                        player.getSkillManager().addExperience(Skill.STRENGTH, xp / 3);
                        player.getSkillManager().addExperience(Skill.DEFENCE, xp / 3);
                    }
                } else if (player.getFightType().getStyle() == FightStyle.DEFENSIVE) {
                    player.getPacketSender().sendRichPresenceState("AFK Defence Training");
                    player.getPacketSender().sendSmallImageKey("defence");
                    player.getPacketSender().sendRichPresenceSmallPictureText("Lvl: " + player.getSkillManager().getCurrentLevel(Skill.DEFENCE));
                    if (canEarnAfkXP(player,skillid)) {
                        if ((player.getRights().isMember() || player.getRights().isStaff())) {
                            player.getSkillManager().addExperience(Skill.DEFENCE, xp * 2);
                        } else
                            player.getSkillManager().addExperience(Skill.DEFENCE, xp);
                    }
                } else if (player.getFightType().getStyle() == FightStyle.ACCURATE) {
                    player.getPacketSender().sendRichPresenceState("AFK Attack Training");
                    player.getPacketSender().sendSmallImageKey("attack");
                    player.getPacketSender().sendRichPresenceSmallPictureText("Lvl: " + player.getSkillManager().getCurrentLevel(Skill.ATTACK));
                    if (canEarnAfkXP(player, skillid)) {
                        if ((player.getRights().isMember() || player.getRights().isStaff())) {
                            player.getSkillManager().addExperience(Skill.ATTACK, xp * 2);
                        } else
                            player.getSkillManager().addExperience(Skill.ATTACK, xp);
                    }
                }
                player.getInventory().add(afkToken, 1);
            }

            @Override
            public void stop() {
                setEventRunning(false);
                player.performAnimation(new Animation(65535));
            }
        });
        TaskManager.submit(player.getCurrentTask());
    }


}
