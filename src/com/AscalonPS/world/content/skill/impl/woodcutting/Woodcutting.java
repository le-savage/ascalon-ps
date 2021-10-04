package com.AscalonPS.world.content.skill.impl.woodcutting;

import com.AscalonPS.engine.task.Task;
import com.AscalonPS.engine.task.TaskManager;
import com.AscalonPS.model.Animation;
import com.AscalonPS.model.GameObject;
import com.AscalonPS.model.Skill;
import com.AscalonPS.model.container.impl.Equipment;
import com.AscalonPS.util.Misc;
import com.AscalonPS.world.content.Achievements;
import com.AscalonPS.world.content.Achievements.AchievementData;
import com.AscalonPS.world.content.CustomObjects;
import com.AscalonPS.world.content.EvilTrees;
import com.AscalonPS.world.content.Sounds;
import com.AscalonPS.world.content.Sounds.Sound;
import com.AscalonPS.world.content.skill.impl.firemaking.Logdata;
import com.AscalonPS.world.content.skill.impl.firemaking.Logdata.logData;
import com.AscalonPS.world.content.skill.impl.woodcutting.WoodcuttingData.Hatchet;
import com.AscalonPS.world.content.skill.impl.woodcutting.WoodcuttingData.Trees;
import com.AscalonPS.world.entity.impl.player.Player;

public class Woodcutting {

    public static void cutWood(final Player player, final GameObject object, boolean restarting) {
        if (!restarting)
            player.getSkillManager().stopSkilling();
        if (player.getInventory().getFreeSlots() == 0) {
            player.getPacketSender().sendMessage("You don't have enough free inventory space.");
            return;
        }
        player.setPositionToFace(object.getPosition());
        final int objId = object.getId();
        final Hatchet hatchet = Hatchet.forId(WoodcuttingData.getHatchet(player));


        if (hatchet != null) {
            if (player.getSkillManager().getCurrentLevel(Skill.WOODCUTTING) >= hatchet.getRequiredLevel()) {
                final WoodcuttingData.Trees tree = WoodcuttingData.Trees.forId(objId);
                if (tree != null) {
                    player.setEntityInteraction(object);
                    if (player.getSkillManager().getCurrentLevel(Skill.WOODCUTTING) >= tree.getReq()) {
                        player.performAnimation(new Animation(hatchet.getAnim()));
                        int delay = Misc.getRandom(tree.getTicks() - WoodcuttingData.getChopTimer(player, hatchet)) + 1;
                        player.setCurrentTask(new Task(1, player, false) {
                            int cycle = 0, reqCycle = delay >= 2 ? delay : Misc.getRandom(1) + 1;

                            @Override
                            public void execute() {
                                player.getPacketSender().sendRichPresenceState("Woodcutting");
                                player.getPacketSender().sendSmallImageKey("woodcutting");
                                player.getPacketSender().sendRichPresenceSmallPictureText("Lvl: " + player.getSkillManager().getCurrentLevel(Skill.WOODCUTTING));
                                if (player.getInventory().getFreeSlots() == 0) {
                                    player.performAnimation(new Animation(65535));
                                    player.getPacketSender().sendMessage("You don't have enough free inventory space.");
                                    this.stop();
                                    return;
                                }
                                if (cycle != reqCycle) {
                                    cycle++;
                                    player.performAnimation(new Animation(hatchet.getAnim()));
                                } else {
                                    int xp = tree.getXp();
                                    if (lumberJack(player))
                                        xp *= 1.5;
                                    player.getSkillManager().addExperience(Skill.WOODCUTTING, xp);
                                    cycle = 0;
                                    this.stop();

                                    if (object.getId() == 11434) {
                                        if (EvilTrees.SPAWNED_TREE == null || EvilTrees.SPAWNED_TREE.getTreeObject().getCutAmount() >= EvilTrees.MAX_CUT_AMOUNT) {
                                            player.getPacketSender().sendClientRightClickRemoval();
                                            player.getSkillManager().stopSkilling();
                                            return;
                                        } else {
                                            EvilTrees.SPAWNED_TREE.getTreeObject().incrementCutAmount();
                                        }
                                    }

                                    if (!tree.isMulti() || !tree.equals(Trees.EVIL_TREE) && Misc.getRandom(10) == 4) {
                                        //player.performAnimation(new Animation(65535));


                                            treeRespawn(player, object);
                                            player.getPacketSender().sendMessage("You've chopped the tree down.");


                                    } else {
                                        cutWood(player, object, true);
                                        if (tree == Trees.EVIL_TREE) {
                                            player.getPacketSender().sendMessage("You cut the Evil Tree...");
                                        } else {
                                            player.getPacketSender().sendMessage("You get some logs..");
                                        }
                                    }
                                    Sounds.sendSound(player, Sound.WOODCUT);
                                    if (!(infernoAdze(player) && Misc.getRandom(5) <= 2)) {
                                        player.getInventory().add(tree.getReward(), 1);
                                    } else if (Misc.getRandom(5) <= 2) {
                                        logData fmLog = Logdata.getLogData(player, tree.getReward());
                                        if (fmLog != null) {
                                            player.getSkillManager().addExperience(Skill.FIREMAKING, fmLog.getXp());
                                            player.getPacketSender().sendMessage("Your Inferno Adze burns the log, granting you Firemaking experience.");
                                            if (fmLog == logData.OAK) {
                                                Achievements.finishAchievement(player, AchievementData.BURN_AN_OAK_LOG);
                                            } else if (fmLog == logData.MAGIC) {
                                                Achievements.doProgress(player, AchievementData.BURN_100_MAGIC_LOGS);
                                                Achievements.doProgress(player, AchievementData.BURN_2500_MAGIC_LOGS);
                                            }
                                        }
                                    }
                                    if (tree == Trees.OAK) {
                                        Achievements.finishAchievement(player, AchievementData.CUT_AN_OAK_TREE);
                                    } else if (tree == Trees.MAGIC) {
                                        Achievements.doProgress(player, AchievementData.CUT_100_MAGIC_LOGS);
                                        Achievements.doProgress(player, AchievementData.CUT_5000_MAGIC_LOGS);
                                    }
                                }
                            }
                        });
                        TaskManager.submit(player.getCurrentTask());
                    } else {
                        player.getPacketSender().sendMessage("You need a Woodcutting level of at least " + tree.getReq() + " to cut this tree.");
                    }
                }
            } else {
                player.getPacketSender().sendMessage("You do not have a hatchet which you have the required Woodcutting level to use.");
            }
        } else {
            player.getPacketSender().sendMessage("You do not have a hatchet that you can use.");
        }
    }

    public static boolean lumberJack(Player player) {
        return player.getEquipment().get(Equipment.HEAD_SLOT).getId() == 10941 && player.getEquipment().get(Equipment.BODY_SLOT).getId() == 10939 && player.getEquipment().get(Equipment.LEG_SLOT).getId() == 10940 && player.getEquipment().get(Equipment.FEET_SLOT).getId() == 10933;
    }

    public static boolean infernoAdze(Player player) {
        return player.getEquipment().get(Equipment.WEAPON_SLOT).getId() == 13661;
    }

    public static void treeRespawn(final Player player, final GameObject oldTree) {
        if (oldTree == null || oldTree.getPickAmount() >= 1 || oldTree.getId() == 11434)
            return;
        oldTree.setPickAmount(1);
        for (Player players : player.getLocalPlayers()) {
            if (players == null)
                continue;
            if (players.getInteractingObject() != null && players.getInteractingObject().getPosition().equals(player.getInteractingObject().getPosition().copy())) {
                players.getSkillManager().stopSkilling();
                players.getPacketSender().sendClientRightClickRemoval();
            }
        }
        player.getPacketSender().sendClientRightClickRemoval();
        player.getSkillManager().stopSkilling();
        CustomObjects.globalObjectRespawnTask(new GameObject(1343, oldTree.getPosition().copy(), 10, 0), oldTree, 20 + Misc.getRandom(10));
    }

}
