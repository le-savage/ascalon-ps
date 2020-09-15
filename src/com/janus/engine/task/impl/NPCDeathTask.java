package com.janus.engine.task.impl;

import com.janus.GameSettings;
import com.janus.engine.task.Task;
import com.janus.engine.task.TaskManager;
import com.janus.model.Animation;
import com.janus.model.Locations.Location;
import com.janus.model.PlayerRights;
import com.janus.model.definitions.NPCDrops;
import com.janus.world.World;
import com.janus.world.content.Achievements;
import com.janus.world.content.Achievements.AchievementData;
import com.janus.world.content.KillsTracker;
import com.janus.world.content.KillsTracker.KillsEntry;
import com.janus.world.content.TrioBosses;
import com.janus.world.content.WildyWyrmEvent;
import com.janus.world.content.combat.instancearena.InstanceArena;
import com.janus.world.content.combat.strategy.impl.KalphiteQueen;
import com.janus.world.content.combat.strategy.impl.Nex;
import com.janus.world.content.combat.tieredbosses.BossFunctions;
import com.janus.world.content.combat.tieredbosses.BossNPCData;
import com.janus.world.content.combat.tieredbosses.BossRewardBoxes;
import com.janus.world.content.minigames.impl.NewBarrows;
import com.janus.world.entity.impl.npc.NPC;
import com.janus.world.entity.impl.player.Player;
import mysql.BossKillTracker;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents an npc's death task, which handles everything
 * an npc does before and after their death animation (including it),
 * such as dropping their drop table items.
 *
 * @author relex lawl
 */

public class NPCDeathTask extends Task {
    /**
     * The npc setting off the death task.
     */
    private final NPC npc;
    /*
     * The array which handles what bosses will give a player points
     * after death
     */
    private Set<Integer> BOSSES = new HashSet<>(Arrays.asList(
            2044, 1999, 2882, 2881, 2883, 7134, 6766, 5666, 7286, 4540, 6222, 6260, 6247, 6203, 8349, 50, 2001, 11558, 1158, 8133, 3200, 13447, 8549, 3851, 1382, 8133, 13447, 2000, 2009, 2006, 499, 2042, 3
    )); //use this array of npcs to change the npcs you want to give boss points
    /**
     * The amount of ticks on the task.
     */
    private int ticks = 2;
    /**
     * The player who killed the NPC
     */
    private Player killer = null;

    /**
     * The NPCDeathTask constructor.
     *
     * @param npc The npc being killed.
     */
    public NPCDeathTask(NPC npc) {
        super(2);
        this.npc = npc;
        this.ticks = 2;
    }

    @SuppressWarnings("incomplete-switch")
    @Override
    public void execute() {
        try {
            npc.setEntityInteraction(null);
            switch (ticks) {
                case 2:
                    npc.getMovementQueue().setLockMovement(true).reset();
                    killer = npc.getCombatBuilder().getKiller(npc.getId() != 3334);
                    if (!(npc.getId() >= 6142 && npc.getId() <= 6145) && !(npc.getId() > 5070 && npc.getId() < 5081))
                        npc.performAnimation(new Animation(npc.getDefinition().getDeathAnimation()));

                    /** CUSTOM NPC DEATHS **/
                    if (npc.getId() == 13447) {
                        Nex.handleDeath();
                    }

                    break;
                case 0:
                    if (killer != null) {

                        boolean boss = (npc.getDefaultConstitution() > 2000);
                        if (!Nex.nexMinion(npc.getId()) && npc.getId() != 1158 && !(npc.getId() >= 3493 && npc.getId() <= 3497)) {
                            KillsTracker.submit(killer, new KillsEntry(npc.getDefinition().getId(), 1, boss));
                            if (boss) {
                                Achievements.doProgress(killer, AchievementData.DEFEAT_500_BOSSES);
                            }
                        }
                        if (BOSSES.contains(npc.getId())) {
                            killer.setBossPoints(killer.getBossPoints() + 1);
                            killer.sendMessage("<img=0>You now have @red@" + killer.getBossPoints() + " Boss Points!");
                        }
                        if (npc.getId() == 2436) {
                            killer.setJanusPoints(killer.getJanusPoints() + 1);
                            killer.sendMessage("<img=0>You now have @red@" + killer.getJanusPoints() + " Janus Points!");
                        }
                        Achievements.doProgress(killer, AchievementData.DEFEAT_10000_MONSTERS);
                        if (npc.getId() == 50) {
                            Achievements.finishAchievement(killer, AchievementData.DEFEAT_THE_KING_BLACK_DRAGON);
                        } else if (npc.getId() == 3200) {
                            Achievements.finishAchievement(killer, AchievementData.DEFEAT_THE_CHAOS_ELEMENTAL);
                        } else if (npc.getId() == 8349) {
                            Achievements.finishAchievement(killer, AchievementData.DEFEAT_A_TORMENTED_DEMON);
                        } else if (npc.getId() == 3491) {
                            Achievements.finishAchievement(killer, AchievementData.DEFEAT_THE_CULINAROMANCER);
                        } else if (npc.getId() == 8528) {
                            Achievements.finishAchievement(killer, AchievementData.DEFEAT_NOMAD);
                        } else if (npc.getId() == 2745) {
                            Achievements.finishAchievement(killer, AchievementData.DEFEAT_JAD);
                        } else if (npc.getId() == 4540) {
                            Achievements.finishAchievement(killer, AchievementData.DEFEAT_BANDOS_AVATAR);
                        } else if (npc.getId() == 6260) {
                            Achievements.finishAchievement(killer, AchievementData.DEFEAT_GENERAL_GRAARDOR);
                            killer.getAchievementAttributes().setGodKilled(0, true);
                        } else if (npc.getId() == 6222) {
                            Achievements.finishAchievement(killer, AchievementData.DEFEAT_KREE_ARRA);
                            killer.getAchievementAttributes().setGodKilled(1, true);
                        } else if (npc.getId() == 6247) {
                            Achievements.finishAchievement(killer, AchievementData.DEFEAT_COMMANDER_ZILYANA);
                            killer.getAchievementAttributes().setGodKilled(2, true);
                        } else if (npc.getId() == 6203) {
                            Achievements.finishAchievement(killer, AchievementData.DEFEAT_KRIL_TSUTSAROTH);
                            killer.getAchievementAttributes().setGodKilled(3, true);
                        } else if (npc.getId() == 8133) {
                            Achievements.finishAchievement(killer, AchievementData.DEFEAT_THE_CORPOREAL_BEAST);
                        } else if (npc.getId() == 13447) {
                            Achievements.finishAchievement(killer, AchievementData.DEFEAT_NEX);
                            killer.getAchievementAttributes().setGodKilled(4, true);
                        }
                        /** ACHIEVEMENTS **/
                        switch (killer.getLastCombatType()) {
                            case MAGIC:
                                Achievements.finishAchievement(killer, AchievementData.KILL_A_MONSTER_USING_MAGIC);
                                break;
                            case MELEE:
                                Achievements.finishAchievement(killer, AchievementData.KILL_A_MONSTER_USING_MELEE);
                                break;
                            case RANGED:
                                Achievements.finishAchievement(killer, AchievementData.KILL_A_MONSTER_USING_RANGED);
                                break;
                        }

                        /** LOCATION KILLS **/
                        if (npc.getLocation().handleKilledNPC(killer, npc)) {
                            stop();
                            return;
                        }

                        if (npc.getId() == GameSettings.CURRENT_BOSS) {
                            new Thread(new BossKillTracker.CountKills(killer)).start();
                        }
                        /*
                         * Halloween event dropping
                         */

                        if (npc.getId() == 1973) {
                            TrioBosses.handleSkeleton(killer, npc.getPosition());
                        }
                        if (npc.getId() == 75) {
                            TrioBosses.handleZombie(killer, npc.getPosition());
                        }
                        if (npc.getId() == 103) {
                            TrioBosses.handleGhost(killer, npc.getPosition());
                        }

                        /*
                         * End Halloween event dropping
                         */


                        /** PARSE DROPS **/
                        NPCDrops.dropItems(killer, npc);
                        if (npc.getId() == 3334) {
                            WildyWyrmEvent.handleDrop(npc);
                        }
                        /** SLAYER **/
                        killer.getSlayer().killedNpc(npc);
                    }
                    stop();
                    break;
            }
            ticks--;
        } catch (Exception e) {
            e.printStackTrace();
            stop();
        }
    }

    @Override
    public void stop() {
        setEventRunning(false);

        npc.setDying(false);

        //respawn
        if (npc.getDefinition().getRespawnTime() > 0 && npc.getLocation() != Location.GRAVEYARD && npc.getLocation() != Location.DUNGEONEERING && npc.getLocation() != Location.INSTANCE_ARENA && npc.getLocation() != Location.BOSS_TIER_LOCATION && npc.getLocation() != Location.BARROWS) {
            TaskManager.submit(new NPCRespawnTask(npc, npc.getDefinition().getRespawnTime()));
        }

        World.deregister(npc);

        if (npc.getLocation() == Location.BARROWS) {
            System.out.println("PLAYER: " +killer.getUsername()+ "BARROWS KC BEFORE KILL : "+ killer.barrowsKC);
            killer.barrowsKC++;
            System.out.println("PLAYER: " +killer.getUsername()+ "KILLED A BARROWS NPC! NEW KC = "+killer.barrowsKC);
            if (killer.barrowsKC >= 6) {
                NewBarrows.rewardPlayer(killer);
                NewBarrows.resetBarrows(killer);
            }
        }


        if (npc.getLocation() == Location.INSTANCE_ARENA) {

            /** Setting the maximum number of kills a player
             * can have in the instance
             * arena before being teleported out.
             * Woohoo - Saving the economy..
             */

            int currentKC = killer.getInstanceKC();

            System.out.println(killer.getUsername() + " has a current KC of "+ currentKC);

            int maxKC = 0;

            if (killer.getRights().equals(PlayerRights.DONATOR))
                maxKC = 10;
            else if (killer.getRights().equals(PlayerRights.SUPER_DONATOR))
                maxKC = 20;
            else if (killer.getRights().equals(PlayerRights.EXTREME_DONATOR))
                maxKC = 30;
            else if (killer.getRights().equals(PlayerRights.LEGENDARY_DONATOR))
                maxKC = 40;
            else if (killer.getRights().equals(PlayerRights.UBER_DONATOR))
                maxKC = 50;
            else if (killer.getRights().isStaff())
                maxKC = 30;

            System.out.println("Player : "+killer.getUsername() + " is rank : "+killer.getRights().toString() + " and is allowed : "+maxKC);


            /** Comparing the current KC with the max KC
             * We also remove the regular players
             * since they are now allowed respawns
             */

            if (currentKC >= maxKC && killer.getRights() != PlayerRights.PLAYER) {
                System.out.println("Player "+killer.getUsername() + " has now got "+currentKC + " / "+maxKC+" Destroying.");
                if (killer.getLocation() == Location.INSTANCE_ARENA && killer.getRegionInstance() == null) {
                    killer.moveTo(InstanceArena.ENTRANCE);
                }
                InstanceArena.destructArena(killer);
                return;
            } else {
                currentKC++;
                System.out.println("Instance KC incremented for "+killer.getUsername());
                killer.setInstanceKC(currentKC);
                System.out.println("New KC for "+killer.getUsername() + " is "+killer.getInstanceKC());
            }


            if (killer.getRights() != PlayerRights.PLAYER) {

                if (npc.getId() == 3) {
                    InstanceArena.spawnMan(killer);
                }
                if (npc.getId() == 50) {
                    InstanceArena.spawnKBD(killer);
                }
                if (npc.getId() == 1591) {
                    InstanceArena.spawnIronDragon(killer);
                }
                if (npc.getId() == 1592) {
                    InstanceArena.spawnSteelDragon(killer);
                }
                if (npc.getId() == 51) {
                    InstanceArena.spawnFrostDragon(killer);
                }
                if (npc.getId() == 2060) {
                    InstanceArena.spawnSlashBash(killer);
                }
                if (npc.getId() == 499) {
                    InstanceArena.spawnSmokeDevil(killer);
                }
                if (npc.getId() == 2881) {
                    InstanceArena.spawnSupreme(killer);
                }
                if (npc.getId() == 2882) {
                    InstanceArena.spawnPrime(killer);
                }
                if (npc.getId() == 2883) {
                    InstanceArena.spawnRex(killer);
                }
                if (npc.getId() == 9463) {
                    InstanceArena.spawnIceWorm(killer);
                }
                if (npc.getId() == 9465) {
                    InstanceArena.spawnDesertWorm(killer);
                }
                if (npc.getId() == 9467) {
                    InstanceArena.spawnJungleWorm(killer);
                }
                if (npc.getId() == 1999) {
                    InstanceArena.spawnCerberus(killer);
                }
                if (npc.getId() == 8349) {
                    InstanceArena.spawnTormentedDemon(killer);
                }
                if (npc.getId() == 1459) {
                    InstanceArena.spawnGorilla(killer);
                }
                if (npc.getId() == 7134) {
                    InstanceArena.spawnBork(killer);
                }
                if (npc.getId() == 3200) {
                    InstanceArena.spawnChaosElemental(killer);
                }
                if (npc.getId() == 6766) {
                    InstanceArena.spawnLizardShaman(killer);
                }
                if (npc.getId() == 2044) {
                    InstanceArena.spawnZulrah(killer);
                }
            }

            if ((npc.getId() == 1265) || (npc.getId() == 2605) || (npc.getId() == 2611) || (npc.getId() == 2600) || (npc.getId() == 2591) || (npc.getId() == 181)) {
                killer.getPacketSender().sendMessage("Nice job! Use the button or ::exit to leave!");
            }

            if (killer.getRights() == PlayerRights.PLAYER) {
                killer.getPacketSender().sendMessage("Nice job! Use the button or ::exit to leave!");
            }
        }

        if (npc.getLocation() == Location.BOSS_TIER_LOCATION) {
            if (npc.getId() == BossNPCData.KING_BLACK_DRAGON.getLevel1ID()) {
                if (killer.kbdTier <= 4 && !killer.shouldGiveBossReward() && !BossRewardBoxes.hasExistingBox(killer)) {
                    killer.kbdTier++;
                    killer.setShouldGiveBossReward(true);
                    killer.forceChat("I should leave now!");
                    BossFunctions.despawnNpcs(killer);
                }
                if (killer.kbdTier == 3 || killer.kbdTier == 4){ // Unfreeze the last two tiers
                    killer.setFreezeDelay(-1);
                    killer.setResetMovementQueue(true);
                }
                if (killer.kbdTier <= 4) {
                    World.sendFilteredMessage("@bla@[@blu@" + killer.getUsername() + "@bla@]@red@ has just completed tier " + (killer.getKbdTier() - 1) + " at ::boss!");
                }
                if (killer.kbdTier == 5) {
                    World.sendFilteredMessage("@bla@[@blu@" + killer.getUsername() + "@bla@]@red@ has just killed the final tier " + (killer.getKbdTier() - 1) + " at ::boss!");
                }
                TaskManager.submit(new Task(2, killer, false) {
                    @Override
                    public void execute() {
                        killer.moveTo(BossFunctions.ARENA_ENTRANCE);
                        stop();
                    }
                });
            }
        }


        if (npc.getId() == 1158 || npc.getId() == 1160) {
            KalphiteQueen.death(npc.getId(), npc.getPosition());
        }
        if (Nex.nexMob(npc.getId())) {
            Nex.death(npc.getId());
        }
    }
}
