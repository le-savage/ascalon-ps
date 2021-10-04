package com.AscalonPS.engine.task.impl;

import com.AscalonPS.engine.task.Task;
import com.AscalonPS.engine.task.TaskManager;
import com.AscalonPS.model.Animation;
import com.AscalonPS.model.Locations.Location;
import com.AscalonPS.model.PlayerRights;
import com.AscalonPS.model.definitions.NPCDrops;
import com.AscalonPS.world.World;
import com.AscalonPS.world.content.Achievements;
import com.AscalonPS.world.content.Achievements.AchievementData;
import com.AscalonPS.world.content.KillsTracker;
import com.AscalonPS.world.content.KillsTracker.KillsEntry;
import com.AscalonPS.world.content.TrioBosses;
import com.AscalonPS.world.content.WildyWyrmEvent;
import com.AscalonPS.world.content.combat.DailyNPCTask;
import com.AscalonPS.world.content.combat.instancearena.InstanceArena;
import com.AscalonPS.world.content.combat.strategy.impl.KalphiteQueen;
import com.AscalonPS.world.content.combat.strategy.impl.Nex;
import com.AscalonPS.world.content.combat.bossminigame.BossMinigameFunctions;
import com.AscalonPS.world.content.minigames.impl.NewBarrows;
import com.AscalonPS.world.entity.impl.npc.NPC;
import com.AscalonPS.world.entity.impl.player.Player;

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

    /**
     * The NPCDeathTask constructor.
     *
     * @param npc The npc being killed.
     */
    public NPCDeathTask(NPC npc) {
        super(1); //Changed to 1
        this.npc = npc;
        this.ticks = 2;
    }


    /*
     * The array which handles what bosses will give a player points
     * after death
     */
    private Set<Integer> BOSSES = new HashSet<>(Arrays.asList(
            1999, 2882, 2881, 2883, 7134, 6766, 5666, 7286, 4540, 6222, 6260, 6247, 6203, 8349, 50, 2001, 11558, 1158, 8133, 3200, 13447, 8549, 3851, 1382, 8133, 13447, 2000, 2009, 2006, 499, 2042, 3
    )); //use this array of npcs to change the npcs you want to give boss points
    /**
     * The amount of ticks on the task.
     */
    private int ticks = 2;


    /**
     * The player who killed the NPC
     */
    private Player killer = null;


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
                        if (npc.getLocation() != Location.BOSS_TIER_LOCATION) {
                            NPCDrops.dropItems(killer, npc);
                        }

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

        if (npc.getId() == DailyNPCTask.CHOSEN_NPC_ID) {
            DailyNPCTask.countPlayerKill(killer);
        }

        npc.setDying(false);

        //respawn
        if (npc.getDefinition().getRespawnTime() > 0 && npc.getLocation() != Location.GRAVEYARD && npc.getLocation() != Location.DUNGEONEERING && npc.getLocation() != Location.INSTANCE_ARENA && npc.getLocation() != Location.BOSS_TIER_LOCATION && npc.getLocation() != Location.BARROWS) {
            TaskManager.submit(new NPCRespawnTask(npc, npc.getDefinition().getRespawnTime()));
        }



        if (npc.getLocation() == Location.BARROWS) {
            System.out.println("PLAYER: " +killer.getUsername()+ "BARROWS KC BEFORE KILL : "+ killer.barrowsKC);
            killer.barrowsKC++;
            System.out.println("PLAYER: " +killer.getUsername()+ "KILLED A BARROWS NPC! NEW KC = "+killer.barrowsKC);
            if (killer.barrowsKC >= 6) {
                NewBarrows.rewardPlayer(killer);
                NewBarrows.resetBarrows(killer);
            }
        }

        World.deregister(npc);



        if (npc.getLocation() == Location.INSTANCE_ARENA) {


            if (killer.getRights() != PlayerRights.PLAYER) {

                /** This sets the maximum amount of kills before the instance is killed **/
                if (killer.getCurrentInstanceArenaKC() >= InstanceArena.getMaximumKills(killer)-1 && npc.getId() != 1265){ //if the the player has hit the maximum (Exclude rock crabs
                    if (killer.getLocation() == Location.INSTANCE_ARENA && killer.getRegionInstance() == null) { // Check if they're inside the room
                        killer.moveTo(InstanceArena.ENTRANCE); //Move them
                    }
                    InstanceArena.destructArena(killer); // Kill the instance (Includes method to set current KC to 0
                } else if (npc.getId() != 1265) { //If not,
                    killer.currentInstanceArenaKC++; //Add 1 to their kills
                    killer.getPacketSender().sendMessage("You have "+(InstanceArena.getMaximumKills(killer)-killer.getCurrentInstanceArenaKC())+"/"+InstanceArena.getMaximumKills(killer)+" kills remaining"); //Send message
                }

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

            if ((npc.getId() == 2605) || (npc.getId() == 2611) || (npc.getId() == 2600) || (npc.getId() == 2591) || (npc.getId() == 181)) {
                killer.getPacketSender().sendMessage("Nice job! Use the button or ::exit to leave!");
            }

            if (killer.getRights() == PlayerRights.PLAYER) {
                killer.getPacketSender().sendMessage("Nice job! Use the button or ::exit to leave!");
            }
        }

        if (npc.getLocation() == Location.BOSS_TIER_LOCATION) {
                if (killer.currentBossWave <= 4 && !killer.isShouldGiveBossReward()) {
                    killer.currentBossWave++;
                    killer.setShouldGiveBossReward(true);
                    killer.forceChat("I should leave now!");
                    BossMinigameFunctions.despawnNpcs(killer);
                }

                if (killer.currentBossWave <= 4) {
                    World.sendFilteredMessage("@bla@[@blu@" + killer.getUsername() + "@bla@]@red@ has just completed wave " + (killer.getCurrentBossWave()) + " at ::boss!");
                }
                if (killer.currentBossWave == 5) {
                    World.sendFilteredMessage("@bla@[@blu@" + killer.getUsername() + "@bla@]@red@ has just killed completed the final wave at ::boss!");
                }

                TaskManager.submit(new Task(2, killer, false) {
                    @Override
                    public void execute() {
                        killer.moveTo(BossMinigameFunctions.ARENA_ENTRANCE);
                        stop();
                    }
                });
            }

        if (npc.getId() == 1158 || npc.getId() == 1160) {
            KalphiteQueen.death(npc.getId(), npc.getPosition());
        }
        if (Nex.nexMob(npc.getId())) {
            Nex.death(npc.getId());
        }
    }
}
