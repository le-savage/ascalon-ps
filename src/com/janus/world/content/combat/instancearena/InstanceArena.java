package com.janus.world.content.combat.instancearena;

import com.janus.engine.task.Task;
import com.janus.engine.task.TaskManager;
import com.janus.engine.task.impl.NPCDeathTask;
import com.janus.engine.task.impl.PoisonImmunityTask;
import com.janus.model.*;
import com.janus.world.World;
import com.janus.world.content.combat.weapon.CombatSpecial;
import com.janus.world.entity.impl.npc.NPC;
import com.janus.world.entity.impl.player.Player;

public class InstanceArena {


    private static int barrierID = 38144;
    private static int npcListInterface = 6028;
    public static final Position CENTER = new Position(2717, 5314);
    public static final int centerX = 2717;
    public static final int centerY = 5314;
    public static final Position FIGHT = new Position(2717, 5322);
    public static final int fightX = 2717;
    public static final int fightY = 5322;
    public static final Position ENTRANCE = new Position(2717, 5324);

    public static final int crabX = 2711;
    public static final int crabY = 5317;




    public static int getMaximumKills(Player player) {
        int maximumKills = 0;
        switch (player.getRights()) {
            case DONATOR:
                maximumKills = 10;
                break;
            case SUPER_DONATOR:
                maximumKills = 15;
                break;
            case EXTREME_DONATOR:
                maximumKills = 20;
                break;
            case LEGENDARY_DONATOR:
                maximumKills = 25;
                break;
            case UBER_DONATOR:
            case SUPPORT:
            case MODERATOR:
            case ADMINISTRATOR:
            case OWNER:
                maximumKills = 30;
                break;
        }
        return maximumKills;
    }


    public static void handleInstance(Player player, GameObject object) {
        if (object.getId() == barrierID) {
            if (player.getLocation() == Locations.Location.INSTANCE_ARENA || player.getRegionInstance() != null) {
                player.getPacketSender().sendInterfaceRemoval();
            }

            if (!player.getLocation().equals(Locations.Location.INSTANCE_ARENA) && player.getRegionInstance() == null) { //Outside the area
                player.getPacketSender().sendInterface(npcListInterface);
                player.setCurrentInstanceArenaKC(0);
            } else {
                player.getPacketSender().sendMessage("@red@ Exit using the button or ::exit");//Inside the area
            }
        }
    }

    public static void restoreHP(Player player) {
        if (player.getRights().isMember() || player.getRights().isStaff()) {
            if (player.getSkillManager().getCurrentLevel(Skill.CONSTITUTION) < player.getSkillManager().getMaxLevel(Skill.CONSTITUTION)) {
                player.getSkillManager().setCurrentLevel(Skill.CONSTITUTION, player.getSkillManager().getMaxLevel(Skill.CONSTITUTION), true);
            }
            if (player.getDonorMessages()) {
                player.getPacketSender().sendMessage("@red@ ::donormessages to disable these messages!");
                player.getPacketSender().sendMessage("<3 Health refilled as a donor benefit <3");
            }
        }
    }

    public static void restoreStats(Player player) {
        if (player.getRights().ordinal() >= PlayerRights.EXTREME_DONATOR.ordinal() || player.getRights().isStaff()) {
            if (player.getSkillManager().getCurrentLevel(Skill.PRAYER) < player.getSkillManager().getMaxLevel(Skill.PRAYER)) {
                player.getSkillManager().setCurrentLevel(Skill.PRAYER, player.getSkillManager().getMaxLevel(Skill.PRAYER), true);
                //player.getPacketSender().sendMessage("You recharge your Prayer points.");
            }
            if (player.getDonorMessages()) {
                player.getPacketSender().sendMessage("<3 Prayer recharged as an Extreme+ benefit <3");
            }
        }
    }

    public static void restoreSpec(Player player) {
        if (player.getRights().ordinal() >= PlayerRights.LEGENDARY_DONATOR.ordinal() || player.getRights().isStaff()) {
            player.setSpecialPercentage(100);
            CombatSpecial.updateBar(player);
            player.performGraphic(new Graphic(1302));
            if (player.getDonorMessages()) {
                player.getPacketSender().sendMessage("<3 Special Attack recharged as an Legendary+ benefit <3");
            }
        }
    }

    public static void restore(Player player) {
        restoreHP(player);
        restoreSpec(player);
        restoreStats(player);
    }


    public static void spawnMan(final Player player) {

        player.setRegionInstance(new RegionInstance(player, RegionInstance.RegionInstanceType.INSTANCE_ARENA));
        player.getPacketSender().sendInterfaceRemoval();
        final int CrazyManID = 3;
        if (player.getLocation() != Locations.Location.INSTANCE_ARENA) {
            player.moveTo(new Position(fightX, fightY, player.getIndex() * 4));
        }
        TaskManager.submit(new Task(2, player, false) {
            @Override
            public void execute() {
                if (player.getRegionInstance() == null || !player.isRegistered() || player.getLocation() != Locations.Location.INSTANCE_ARENA || !player.getRegionInstance().getNpcsList().isEmpty()) {
                    System.out.print("Failed to spawn. One of the variables was not correct.");
                    stop();
                    return;
                }
                NPC man = new NPC(CrazyManID, new Position(centerX, centerY, player.getPosition().getZ())).setSpawnedFor(player);
                World.register(man);
                player.getRegionInstance().getNpcsList().add(man);
                man.getCombatBuilder().attack(player);
                stop();
            }

        });
    }

    public static void spawnKBD(final Player player) {

        player.setRegionInstance(new RegionInstance(player, RegionInstance.RegionInstanceType.INSTANCE_ARENA));
        player.getPacketSender().sendInterfaceRemoval();
        final int KBD = 50;
        if (player.getLocation() != Locations.Location.INSTANCE_ARENA) {
            player.moveTo(new Position(fightX, fightY, player.getIndex() * 4));
        }
        TaskManager.submit(new Task(2, player, false) {
            @Override
            public void execute() {
                if (player.getRegionInstance() == null || !player.isRegistered() || player.getLocation() != Locations.Location.INSTANCE_ARENA || !player.getRegionInstance().getNpcsList().isEmpty()) {
                    System.out.print("Failed to spawn. One of the variables was not correct.");
                    stop();
                    return;
                }
                NPC kbd = new NPC(KBD, new Position(centerX, centerY, player.getPosition().getZ())).setSpawnedFor(player);
                World.register(kbd);
                player.getRegionInstance().getNpcsList().add(kbd);
                kbd.getCombatBuilder().attack(player);
                stop();
            }

        });
    }

    public static void spawnIronDragon(final Player player) {

        player.setRegionInstance(new RegionInstance(player, RegionInstance.RegionInstanceType.INSTANCE_ARENA));
        player.getPacketSender().sendInterfaceRemoval();
        final int IronID = 1591;
        if (player.getLocation() != Locations.Location.INSTANCE_ARENA) {
            player.moveTo(new Position(fightX, fightY, player.getIndex() * 4));
        }
        TaskManager.submit(new Task(2, player, false) {
            @Override
            public void execute() {
                if (player.getRegionInstance() == null || !player.isRegistered() || player.getLocation() != Locations.Location.INSTANCE_ARENA || !player.getRegionInstance().getNpcsList().isEmpty()) {
                    System.out.print("Failed to spawn. One of the variables was not correct.");
                    stop();
                    return;
                }
                NPC Iron = new NPC(IronID, new Position(centerX, centerY, player.getPosition().getZ())).setSpawnedFor(player);
                World.register(Iron);
                player.getRegionInstance().getNpcsList().add(Iron);
                Iron.getCombatBuilder().attack(player);
                stop();
            }

        });
    }

    public static void spawnSteelDragon(final Player player) {

        player.setRegionInstance(new RegionInstance(player, RegionInstance.RegionInstanceType.INSTANCE_ARENA));
        player.getPacketSender().sendInterfaceRemoval();
        final int SteelID = 1592;
        if (player.getLocation() != Locations.Location.INSTANCE_ARENA) {
            player.moveTo(new Position(fightX, fightY, player.getIndex() * 4));
        }
        TaskManager.submit(new Task(2, player, false) {
            @Override
            public void execute() {
                if (player.getRegionInstance() == null || !player.isRegistered() || player.getLocation() != Locations.Location.INSTANCE_ARENA || !player.getRegionInstance().getNpcsList().isEmpty()) {
                    System.out.print("Failed to spawn. One of the variables was not correct.");
                    stop();
                    return;
                }
                NPC Steel = new NPC(SteelID, new Position(centerX, centerY, player.getPosition().getZ())).setSpawnedFor(player);
                World.register(Steel);
                player.getRegionInstance().getNpcsList().add(Steel);
                Steel.getCombatBuilder().attack(player);
                stop();
            }

        });
    }

    public static void spawnFrostDragon(final Player player) {

        player.setRegionInstance(new RegionInstance(player, RegionInstance.RegionInstanceType.INSTANCE_ARENA));
        player.getPacketSender().sendInterfaceRemoval();
        final int frostID = 51;
        if (player.getLocation() != Locations.Location.INSTANCE_ARENA) {
            player.moveTo(new Position(fightX, fightY, player.getIndex() * 4));
        }
        TaskManager.submit(new Task(2, player, false) {
            @Override
            public void execute() {
                if (player.getRegionInstance() == null || !player.isRegistered() || player.getLocation() != Locations.Location.INSTANCE_ARENA || !player.getRegionInstance().getNpcsList().isEmpty()) {
                    System.out.print("Failed to spawn. One of the variables was not correct.");
                    stop();
                    return;
                }
                NPC Frost = new NPC(frostID, new Position(centerX, centerY, player.getPosition().getZ())).setSpawnedFor(player);
                World.register(Frost);
                player.getRegionInstance().getNpcsList().add(Frost);
                Frost.getCombatBuilder().attack(player);
                stop();
            }

        });
    }

    public static void spawnSlashBash(final Player player) {

        player.setRegionInstance(new RegionInstance(player, RegionInstance.RegionInstanceType.INSTANCE_ARENA));
        player.getPacketSender().sendInterfaceRemoval();
        final int slashID = 2060;
        if (player.getLocation() != Locations.Location.INSTANCE_ARENA) {
            player.moveTo(new Position(fightX, fightY, player.getIndex() * 4));
        }
        TaskManager.submit(new Task(2, player, false) {
            @Override
            public void execute() {
                if (player.getRegionInstance() == null || !player.isRegistered() || player.getLocation() != Locations.Location.INSTANCE_ARENA || !player.getRegionInstance().getNpcsList().isEmpty()) {
                    System.out.print("Failed to spawn. One of the variables was not correct.");
                    stop();
                    return;
                }
                NPC Slash = new NPC(slashID, new Position(centerX, centerY, player.getPosition().getZ())).setSpawnedFor(player);
                World.register(Slash);
                player.getRegionInstance().getNpcsList().add(Slash);
                Slash.getCombatBuilder().attack(player);
                stop();
            }

        });
    }

    public static void spawnSmokeDevil(final Player player) {

        player.setRegionInstance(new RegionInstance(player, RegionInstance.RegionInstanceType.INSTANCE_ARENA));
        player.getPacketSender().sendInterfaceRemoval();
        final int smokeID = 499;
        if (player.getLocation() != Locations.Location.INSTANCE_ARENA) {
            player.moveTo(new Position(fightX, fightY, player.getIndex() * 4));
        }
        TaskManager.submit(new Task(2, player, false) {
            @Override
            public void execute() {
                if (player.getRegionInstance() == null || !player.isRegistered() || player.getLocation() != Locations.Location.INSTANCE_ARENA || !player.getRegionInstance().getNpcsList().isEmpty()) {
                    System.out.print("Failed to spawn. One of the variables was not correct.");
                    stop();
                    return;
                }
                NPC SmokeDevil = new NPC(smokeID, new Position(centerX, centerY, player.getPosition().getZ())).setSpawnedFor(player);
                World.register(SmokeDevil);
                player.getRegionInstance().getNpcsList().add(SmokeDevil);
                SmokeDevil.getCombatBuilder().attack(player);
                stop();
            }

        });
    }

    public static void spawnSupreme(final Player player) {

        player.setRegionInstance(new RegionInstance(player, RegionInstance.RegionInstanceType.INSTANCE_ARENA));
        player.getPacketSender().sendInterfaceRemoval();
        final int supremeID = 2881;
        if (player.getLocation() != Locations.Location.INSTANCE_ARENA) {
            player.moveTo(new Position(fightX, fightY, player.getIndex() * 4));
        }
        TaskManager.submit(new Task(2, player, false) {
            @Override
            public void execute() {
                if (player.getRegionInstance() == null || !player.isRegistered() || player.getLocation() != Locations.Location.INSTANCE_ARENA || !player.getRegionInstance().getNpcsList().isEmpty()) {
                    System.out.print("Failed to spawn. One of the variables was not correct.");
                    stop();
                    return;
                }
                NPC DagSupreme = new NPC(supremeID, new Position(centerX, centerY, player.getPosition().getZ())).setSpawnedFor(player);
                World.register(DagSupreme);
                player.getRegionInstance().getNpcsList().add(DagSupreme);
                DagSupreme.getCombatBuilder().attack(player);
                stop();
            }

        });
    }

    public static void spawnPrime(final Player player) {

        player.setRegionInstance(new RegionInstance(player, RegionInstance.RegionInstanceType.INSTANCE_ARENA));
        player.getPacketSender().sendInterfaceRemoval();
        final int primeID = 2882;
        if (player.getLocation() != Locations.Location.INSTANCE_ARENA) {
            player.moveTo(new Position(fightX, fightY, player.getIndex() * 4));
        }
        TaskManager.submit(new Task(2, player, false) {
            @Override
            public void execute() {
                if (player.getRegionInstance() == null || !player.isRegistered() || player.getLocation() != Locations.Location.INSTANCE_ARENA || !player.getRegionInstance().getNpcsList().isEmpty()) {
                    System.out.print("Failed to spawn. One of the variables was not correct.");
                    stop();
                    return;
                }
                NPC DagPrime = new NPC(primeID, new Position(centerX, centerY, player.getPosition().getZ())).setSpawnedFor(player);
                World.register(DagPrime);
                player.getRegionInstance().getNpcsList().add(DagPrime);
                DagPrime.getCombatBuilder().attack(player);
                stop();
            }

        });
    }

    public static void spawnRex(final Player player) {

        player.setRegionInstance(new RegionInstance(player, RegionInstance.RegionInstanceType.INSTANCE_ARENA));
        player.getPacketSender().sendInterfaceRemoval();
        final int rexID = 2883;
        if (player.getLocation() != Locations.Location.INSTANCE_ARENA) {
            player.moveTo(new Position(fightX, fightY, player.getIndex() * 4));
        }
        TaskManager.submit(new Task(2, player, false) {
            @Override
            public void execute() {
                if (player.getRegionInstance() == null || !player.isRegistered() || player.getLocation() != Locations.Location.INSTANCE_ARENA || !player.getRegionInstance().getNpcsList().isEmpty()) {
                    System.out.print("Failed to spawn. One of the variables was not correct.");
                    stop();
                    return;
                }
                NPC DagRex = new NPC(rexID, new Position(centerX, centerY, player.getPosition().getZ())).setSpawnedFor(player);
                World.register(DagRex);
                player.getRegionInstance().getNpcsList().add(DagRex);
                DagRex.getCombatBuilder().attack(player);
                stop();
            }

        });
    }

    public static void spawnIceWorm(final Player player) {

        player.setRegionInstance(new RegionInstance(player, RegionInstance.RegionInstanceType.INSTANCE_ARENA));
        player.getPacketSender().sendInterfaceRemoval();
        final int iceID = 9463;
        if (player.getLocation() != Locations.Location.INSTANCE_ARENA) {
            player.moveTo(new Position(fightX, fightY, player.getIndex() * 4));
        }
        TaskManager.submit(new Task(2, player, false) {
            @Override
            public void execute() {
                if (player.getRegionInstance() == null || !player.isRegistered() || player.getLocation() != Locations.Location.INSTANCE_ARENA || !player.getRegionInstance().getNpcsList().isEmpty()) {
                    System.out.print("Failed to spawn. One of the variables was not correct.");
                    stop();
                    return;
                }
                NPC iceWorm = new NPC(iceID, new Position(centerX, centerY, player.getPosition().getZ())).setSpawnedFor(player);
                World.register(iceWorm);
                player.getRegionInstance().getNpcsList().add(iceWorm);
                iceWorm.getCombatBuilder().attack(player);
                stop();
            }

        });
    }

    public static void spawnDesertWorm(final Player player) {

        player.setRegionInstance(new RegionInstance(player, RegionInstance.RegionInstanceType.INSTANCE_ARENA));
        player.getPacketSender().sendInterfaceRemoval();
        final int desertID = 9465;
        if (player.getLocation() != Locations.Location.INSTANCE_ARENA) {
            player.moveTo(new Position(fightX, fightY, player.getIndex() * 4));
        }
        TaskManager.submit(new Task(2, player, false) {
            @Override
            public void execute() {
                if (player.getRegionInstance() == null || !player.isRegistered() || player.getLocation() != Locations.Location.INSTANCE_ARENA || !player.getRegionInstance().getNpcsList().isEmpty()) {
                    System.out.print("Failed to spawn. One of the variables was not correct.");
                    stop();
                    return;
                }
                NPC desertWorm = new NPC(desertID, new Position(centerX, centerY, player.getPosition().getZ())).setSpawnedFor(player);
                World.register(desertWorm);
                player.getRegionInstance().getNpcsList().add(desertWorm);
                desertWorm.getCombatBuilder().attack(player);
                stop();
            }

        });
    }

    public static void spawnJungleWorm(final Player player) {

        player.setRegionInstance(new RegionInstance(player, RegionInstance.RegionInstanceType.INSTANCE_ARENA));
        player.getPacketSender().sendInterfaceRemoval();
        final int jungleID = 9467;
        if (player.getLocation() != Locations.Location.INSTANCE_ARENA) {
            player.moveTo(new Position(fightX, fightY, player.getIndex() * 4));
        }
        TaskManager.submit(new Task(2, player, false) {
            @Override
            public void execute() {
                if (player.getRegionInstance() == null || !player.isRegistered() || player.getLocation() != Locations.Location.INSTANCE_ARENA || !player.getRegionInstance().getNpcsList().isEmpty()) {
                    System.out.print("Failed to spawn. One of the variables was not correct.");
                    stop();
                    return;
                }
                NPC jungleWorm = new NPC(jungleID, new Position(centerX, centerY, player.getPosition().getZ())).setSpawnedFor(player);
                World.register(jungleWorm);
                player.getRegionInstance().getNpcsList().add(jungleWorm);
                jungleWorm.getCombatBuilder().attack(player);
                stop();
            }

        });
    }

    public static void spawnCerberus(final Player player) {

        player.setRegionInstance(new RegionInstance(player, RegionInstance.RegionInstanceType.INSTANCE_ARENA));
        player.getPacketSender().sendInterfaceRemoval();
        final int cerberusID = 1999;
        if (player.getLocation() != Locations.Location.INSTANCE_ARENA) {
            player.moveTo(new Position(fightX, fightY, player.getIndex() * 4));
        }
        TaskManager.submit(new Task(2, player, false) {
            @Override
            public void execute() {
                if (player.getRegionInstance() == null || !player.isRegistered() || player.getLocation() != Locations.Location.INSTANCE_ARENA || !player.getRegionInstance().getNpcsList().isEmpty()) {
                    System.out.print("Failed to spawn. One of the variables was not correct.");
                    stop();
                    return;
                }
                NPC cerb = new NPC(cerberusID, new Position(centerX, centerY, player.getPosition().getZ())).setSpawnedFor(player);
                World.register(cerb);
                player.getRegionInstance().getNpcsList().add(cerb);
                cerb.getCombatBuilder().attack(player);
                stop();
            }

        });
    }

    public static void spawnTormentedDemon(final Player player) {

        player.setRegionInstance(new RegionInstance(player, RegionInstance.RegionInstanceType.INSTANCE_ARENA));
        player.getPacketSender().sendInterfaceRemoval();
        final int tormID = 8349;
        if (player.getLocation() != Locations.Location.INSTANCE_ARENA) {
            player.moveTo(new Position(fightX, fightY, player.getIndex() * 4));
        }
        TaskManager.submit(new Task(2, player, false) {
            @Override
            public void execute() {
                if (player.getRegionInstance() == null || !player.isRegistered() || player.getLocation() != Locations.Location.INSTANCE_ARENA || !player.getRegionInstance().getNpcsList().isEmpty()) {
                    System.out.print("Failed to spawn. One of the variables was not correct.");
                    stop();
                    return;
                }
                NPC tormDemon = new NPC(tormID, new Position(centerX, centerY, player.getPosition().getZ())).setSpawnedFor(player);
                World.register(tormDemon);
                player.getRegionInstance().getNpcsList().add(tormDemon);
                tormDemon.getCombatBuilder().attack(player);
                stop();
            }

        });
    }

    public static void spawnZulrah(final Player player) {

        player.setRegionInstance(new RegionInstance(player, RegionInstance.RegionInstanceType.INSTANCE_ARENA));
        player.getPacketSender().sendInterfaceRemoval();
        final int zulID = 2044;
        if (player.getLocation() != Locations.Location.INSTANCE_ARENA) {
            player.moveTo(new Position(fightX, fightY, player.getIndex() * 4));
        }
        TaskManager.submit(new Task(2, player, false) {
            @Override
            public void execute() {
                if (player.getRegionInstance() == null || !player.isRegistered() || player.getLocation() != Locations.Location.INSTANCE_ARENA || !player.getRegionInstance().getNpcsList().isEmpty()) {
                    System.out.print("Failed to spawn. One of the variables was not correct.");
                    stop();
                    return;
                }
                NPC zulrah = new NPC(zulID, new Position(centerX, centerY, player.getPosition().getZ())).setSpawnedFor(player);
                World.register(zulrah);
                player.getRegionInstance().getNpcsList().add(zulrah);
                zulrah.getCombatBuilder().attack(player);
                stop();
            }

        });
    }

    public static void spawnGorilla(final Player player) {

        player.setRegionInstance(new RegionInstance(player, RegionInstance.RegionInstanceType.INSTANCE_ARENA));
        player.getPacketSender().sendInterfaceRemoval();
        final int gorillaID = 1459;
        if (player.getLocation() != Locations.Location.INSTANCE_ARENA) {
            player.moveTo(new Position(fightX, fightY, player.getIndex() * 4));
        }
        TaskManager.submit(new Task(2, player, false) {
            @Override
            public void execute() {
                if (player.getRegionInstance() == null || !player.isRegistered() || player.getLocation() != Locations.Location.INSTANCE_ARENA || !player.getRegionInstance().getNpcsList().isEmpty()) {
                    System.out.print("Failed to spawn. One of the variables was not correct.");
                    stop();
                    return;
                }
                NPC gorilla = new NPC(gorillaID, new Position(centerX, centerY, player.getPosition().getZ())).setSpawnedFor(player);
                World.register(gorilla);
                player.getRegionInstance().getNpcsList().add(gorilla);
                gorilla.getCombatBuilder().attack(player);
                stop();
            }

        });
    }

    public static void spawnBork(final Player player) {

        player.setRegionInstance(new RegionInstance(player, RegionInstance.RegionInstanceType.INSTANCE_ARENA));
        player.getPacketSender().sendInterfaceRemoval();
        final int borkID = 7134;
        if (player.getLocation() != Locations.Location.INSTANCE_ARENA) {
            player.moveTo(new Position(fightX, fightY, player.getIndex() * 4));
        }
        TaskManager.submit(new Task(2, player, false) {
            @Override
            public void execute() {
                if (player.getRegionInstance() == null || !player.isRegistered() || player.getLocation() != Locations.Location.INSTANCE_ARENA || !player.getRegionInstance().getNpcsList().isEmpty()) {
                    System.out.print("Failed to spawn. One of the variables was not correct.");
                    stop();
                    return;
                }
                NPC bork = new NPC(borkID, new Position(centerX, centerY, player.getPosition().getZ())).setSpawnedFor(player);
                World.register(bork);
                player.getRegionInstance().getNpcsList().add(bork);
                bork.getCombatBuilder().attack(player);
                stop();
            }

        });
    }

    public static void spawnChaosElemental(final Player player) {

        player.setRegionInstance(new RegionInstance(player, RegionInstance.RegionInstanceType.INSTANCE_ARENA));
        player.getPacketSender().sendInterfaceRemoval();
        final int chaosID = 3200;
        if (player.getLocation() != Locations.Location.INSTANCE_ARENA) {
            player.moveTo(new Position(fightX, fightY, player.getIndex() * 4));
        }
        TaskManager.submit(new Task(2, player, false) {
            @Override
            public void execute() {
                if (player.getRegionInstance() == null || !player.isRegistered() || player.getLocation() != Locations.Location.INSTANCE_ARENA || !player.getRegionInstance().getNpcsList().isEmpty()) {
                    System.out.print("Failed to spawn. One of the variables was not correct.");
                    stop();
                    return;
                }
                NPC chaosEle = new NPC(chaosID, new Position(centerX, centerY, player.getPosition().getZ())).setSpawnedFor(player);
                World.register(chaosEle);
                player.getRegionInstance().getNpcsList().add(chaosEle);
                chaosEle.getCombatBuilder().attack(player);
                stop();
            }

        });
    }

    public static void spawnLizardShaman(final Player player) {

        player.setRegionInstance(new RegionInstance(player, RegionInstance.RegionInstanceType.INSTANCE_ARENA));
        player.getPacketSender().sendInterfaceRemoval();
        final int shaman = 6766;
        if (player.getLocation() != Locations.Location.INSTANCE_ARENA) {
            player.moveTo(new Position(fightX, fightY, player.getIndex() * 4));
        }
        TaskManager.submit(new Task(2, player, false) {
            @Override
            public void execute() {
                if (player.getRegionInstance() == null || !player.isRegistered() || player.getLocation() != Locations.Location.INSTANCE_ARENA || !player.getRegionInstance().getNpcsList().isEmpty()) {
                    System.out.print("Failed to spawn. One of the variables was not correct.");
                    stop();
                    return;
                }
                NPC lizard = new NPC(shaman, new Position(centerX, centerY, player.getPosition().getZ())).setSpawnedFor(player);
                World.register(lizard);
                player.getRegionInstance().getNpcsList().add(lizard);
                lizard.getCombatBuilder().attack(player);
                stop();
            }

        });
    }

    public static void spawnChaosDruid(final Player player) {

        player.setRegionInstance(new RegionInstance(player, RegionInstance.RegionInstanceType.INSTANCE_ARENA));
        player.getPacketSender().sendInterfaceRemoval();
        final int druidID = 181;
        if (player.getLocation() != Locations.Location.INSTANCE_ARENA) {
            player.moveTo(new Position(fightX, fightY, player.getIndex() * 4));
        }
        TaskManager.submit(new Task(2, player, false) {
            @Override
            public void execute() {
                if (player.getRegionInstance() == null || !player.isRegistered() || player.getLocation() != Locations.Location.INSTANCE_ARENA || !player.getRegionInstance().getNpcsList().isEmpty()) {
                    System.out.print("Failed to spawn. One of the variables was not correct.");
                    stop();
                    return;
                }
                NPC druid1 = new NPC(druidID, new Position(centerX, centerY, player.getPosition().getZ())).setSpawnedFor(player);
                NPC druid2 = new NPC(druidID, new Position(centerX + -2, centerY, player.getPosition().getZ())).setSpawnedFor(player);
                NPC druid3 = new NPC(druidID, new Position(centerX + 2, centerY, player.getPosition().getZ())).setSpawnedFor(player);
                NPC druid4 = new NPC(druidID, new Position(centerX + 4, centerY, player.getPosition().getZ())).setSpawnedFor(player);
                NPC druid5 = new NPC(druidID, new Position(centerX, centerY + 1, player.getPosition().getZ())).setSpawnedFor(player);
                NPC druid6 = new NPC(druidID, new Position(centerX + -2, centerY + 1, player.getPosition().getZ())).setSpawnedFor(player);
                NPC druid7 = new NPC(druidID, new Position(centerX + 2, centerY + 1, player.getPosition().getZ())).setSpawnedFor(player);
                NPC druid8 = new NPC(druidID, new Position(centerX + 4, centerY + 1, player.getPosition().getZ())).setSpawnedFor(player);
                World.register(druid1);
                World.register(druid2);
                World.register(druid3);
                World.register(druid4);
                World.register(druid5);
                World.register(druid6);
                World.register(druid7);
                World.register(druid8);
                player.getRegionInstance().getNpcsList().add(druid1);
                player.getRegionInstance().getNpcsList().add(druid2);
                player.getRegionInstance().getNpcsList().add(druid3);
                player.getRegionInstance().getNpcsList().add(druid4);
                player.getRegionInstance().getNpcsList().add(druid5);
                player.getRegionInstance().getNpcsList().add(druid6);
                player.getRegionInstance().getNpcsList().add(druid7);
                player.getRegionInstance().getNpcsList().add(druid8);
                player.getRegionInstance().getNpcsList().forEach(npc -> npc.getCombatBuilder().attack(player));
                stop();
            }

        });
    }

    public static void spawnRockCrabMadness(final Player player) {

        player.setRegionInstance(new RegionInstance(player, RegionInstance.RegionInstanceType.INSTANCE_ARENA));
        player.getPacketSender().sendInterfaceRemoval();
        final int crabID = 1265;
        if (player.getLocation() != Locations.Location.INSTANCE_ARENA) {
            player.moveTo(new Position(fightX, fightY, player.getIndex() * 4));
        }
        TaskManager.submit(new Task(2, player, false) {
            @Override
            public void execute() {
                if (player.getRegionInstance() == null || !player.isRegistered() || player.getLocation() != Locations.Location.INSTANCE_ARENA || !player.getRegionInstance().getNpcsList().isEmpty()) {
                    System.out.print("Failed to spawn. One of the variables was not correct.");
                    stop();
                    return;
                }
                NPC crab = new NPC(crabID, new Position(crabX, crabY, player.getPosition().getZ())).setSpawnedFor(player);
                NPC crab1 = new NPC(crabID, new Position(crabX + 1, crabY, player.getPosition().getZ())).setSpawnedFor(player);
                NPC crab2 = new NPC(crabID, new Position(crabX + 2, crabY, player.getPosition().getZ())).setSpawnedFor(player);
                NPC crab3 = new NPC(crabID, new Position(crabX + 3, crabY, player.getPosition().getZ())).setSpawnedFor(player);
                NPC crab4 = new NPC(crabID, new Position(crabX + 4, crabY, player.getPosition().getZ())).setSpawnedFor(player);
                NPC crab5 = new NPC(crabID, new Position(crabX + 5, crabY, player.getPosition().getZ())).setSpawnedFor(player);
                NPC crab6 = new NPC(crabID, new Position(crabX + 6, crabY, player.getPosition().getZ())).setSpawnedFor(player);
                NPC crab7 = new NPC(crabID, new Position(crabX + 7, crabY, player.getPosition().getZ())).setSpawnedFor(player);
                NPC crab8 = new NPC(crabID, new Position(crabX + 8, crabY, player.getPosition().getZ())).setSpawnedFor(player);
                NPC crab9 = new NPC(crabID, new Position(crabX + 9, crabY, player.getPosition().getZ())).setSpawnedFor(player);
                NPC crab10 = new NPC(crabID, new Position(crabX + 10, crabY, player.getPosition().getZ())).setSpawnedFor(player);
                NPC crab11 = new NPC(crabID, new Position(crabX + 11, crabY, player.getPosition().getZ())).setSpawnedFor(player);
                NPC crab12 = new NPC(crabID, new Position(crabX + 12, crabY, player.getPosition().getZ())).setSpawnedFor(player);
                NPC crab13 = new NPC(crabID, new Position(crabX, crabY - 1, player.getPosition().getZ())).setSpawnedFor(player);
                NPC crab14 = new NPC(crabID, new Position(crabX + 1, crabY - 1, player.getPosition().getZ())).setSpawnedFor(player);
                NPC crab15 = new NPC(crabID, new Position(crabX + 2, crabY - 1, player.getPosition().getZ())).setSpawnedFor(player);
                NPC crab16 = new NPC(crabID, new Position(crabX + 3, crabY - 1, player.getPosition().getZ())).setSpawnedFor(player);
                NPC crab17 = new NPC(crabID, new Position(crabX + 4, crabY - 1, player.getPosition().getZ())).setSpawnedFor(player);
                NPC crab18 = new NPC(crabID, new Position(crabX + 5, crabY - 1, player.getPosition().getZ())).setSpawnedFor(player);
                NPC crab19 = new NPC(crabID, new Position(crabX + 6, crabY - 1, player.getPosition().getZ())).setSpawnedFor(player);
                NPC crab20 = new NPC(crabID, new Position(crabX + 7, crabY - 1, player.getPosition().getZ())).setSpawnedFor(player);
                NPC crab21 = new NPC(crabID, new Position(crabX + 8, crabY - 1, player.getPosition().getZ())).setSpawnedFor(player);
                NPC crab22 = new NPC(crabID, new Position(crabX + 9, crabY - 1, player.getPosition().getZ())).setSpawnedFor(player);
                NPC crab23 = new NPC(crabID, new Position(crabX + 10, crabY - 1, player.getPosition().getZ())).setSpawnedFor(player);
                NPC crab24 = new NPC(crabID, new Position(crabX + 11, crabY - 1, player.getPosition().getZ())).setSpawnedFor(player);
                NPC crab25 = new NPC(crabID, new Position(crabX + 12, crabY, player.getPosition().getZ())).setSpawnedFor(player);
                World.register(crab);
                World.register(crab1);
                World.register(crab2);
                World.register(crab3);
                World.register(crab4);
                World.register(crab5);
                World.register(crab6);
                World.register(crab7);
                World.register(crab8);
                World.register(crab9);
                World.register(crab10);
                World.register(crab11);
                World.register(crab12);
                World.register(crab13);
                World.register(crab14);
                World.register(crab15);
                World.register(crab16);
                World.register(crab17);
                World.register(crab18);
                World.register(crab19);
                World.register(crab20);
                World.register(crab21);
                World.register(crab22);
                World.register(crab23);
                World.register(crab24);
                World.register(crab25);
                player.getRegionInstance().getNpcsList().add(crab);
                player.getRegionInstance().getNpcsList().add(crab1);
                player.getRegionInstance().getNpcsList().add(crab2);
                player.getRegionInstance().getNpcsList().add(crab3);
                player.getRegionInstance().getNpcsList().add(crab4);
                player.getRegionInstance().getNpcsList().add(crab5);
                player.getRegionInstance().getNpcsList().add(crab6);
                player.getRegionInstance().getNpcsList().add(crab7);
                player.getRegionInstance().getNpcsList().add(crab8);
                player.getRegionInstance().getNpcsList().add(crab9);
                player.getRegionInstance().getNpcsList().add(crab10);
                player.getRegionInstance().getNpcsList().add(crab11);
                player.getRegionInstance().getNpcsList().add(crab12);
                player.getRegionInstance().getNpcsList().add(crab13);
                player.getRegionInstance().getNpcsList().add(crab14);
                player.getRegionInstance().getNpcsList().add(crab15);
                player.getRegionInstance().getNpcsList().add(crab16);
                player.getRegionInstance().getNpcsList().add(crab17);
                player.getRegionInstance().getNpcsList().add(crab18);
                player.getRegionInstance().getNpcsList().add(crab19);
                player.getRegionInstance().getNpcsList().add(crab20);
                player.getRegionInstance().getNpcsList().add(crab21);
                player.getRegionInstance().getNpcsList().add(crab22);
                player.getRegionInstance().getNpcsList().add(crab23);
                player.getRegionInstance().getNpcsList().add(crab24);
                player.getRegionInstance().getNpcsList().add(crab25);
                //crab.getCombatBuilder().attack(player);
                player.getRegionInstance().getNpcsList().forEach(npc -> npc.getCombatBuilder().attack(player));
                stop();
            }

        });
    }

    public static void spawnTzHaar(final Player player) {

        player.setRegionInstance(new RegionInstance(player, RegionInstance.RegionInstanceType.INSTANCE_ARENA));
        player.getPacketSender().sendInterfaceRemoval();
        final int minionid1 = 2605;
        final int minionid2 = 2611;
        final int minionid3 = 2600;
        final int minionid4 = 2591;
        if (player.getLocation() != Locations.Location.INSTANCE_ARENA) {
            player.moveTo(new Position(fightX, fightY, player.getIndex() * 4));
        }
        TaskManager.submit(new Task(2, player, false) {
            @Override
            public void execute() {
                if (player.getRegionInstance() == null || !player.isRegistered() || player.getLocation() != Locations.Location.INSTANCE_ARENA || !player.getRegionInstance().getNpcsList().isEmpty()) {
                    System.out.print("Failed to spawn. One of the variables was not correct.");
                    stop();
                    return;
                }
                NPC minion1 = new NPC(minionid1, new Position(centerX, centerY, player.getPosition().getZ())).setSpawnedFor(player);
                NPC minion2 = new NPC(minionid2, new Position(centerX + -2, centerY, player.getPosition().getZ())).setSpawnedFor(player);
                NPC minion3 = new NPC(minionid3, new Position(centerX + 2, centerY, player.getPosition().getZ())).setSpawnedFor(player);
                NPC minion4 = new NPC(minionid4, new Position(centerX + 4, centerY, player.getPosition().getZ())).setSpawnedFor(player);
                World.register(minion1);
                World.register(minion2);
                World.register(minion3);
                World.register(minion4);
                player.getRegionInstance().getNpcsList().add(minion1);
                player.getRegionInstance().getNpcsList().add(minion2);
                player.getRegionInstance().getNpcsList().add(minion3);
                player.getRegionInstance().getNpcsList().add(minion4);
                player.getRegionInstance().getNpcsList().forEach(npc -> npc.getCombatBuilder().attack(player));
                stop();
            }

        });
    }


    public static void destructArena(final Player player) {
        if ((player.getLocation() != Locations.Location.INSTANCE_ARENA) || (!player.getRegionInstance().equals(RegionInstance.RegionInstanceType.INSTANCE_ARENA)) || (player.getRegionInstance().getNpcsList().isEmpty())) {
            return;
        } else {
            player.moveTo(ENTRANCE);
            System.out.println("Destroying Arena for " + player.getUsername());
            player.getRegionInstance().getNpcsList().forEach(npc -> npc.removeInstancedNpcs(Locations.Location.INSTANCE_ARENA, player.getPosition().getZ()));
            player.getRegionInstance().getNpcsList().forEach(npc -> World.deregister(npc));
            player.getRegionInstance().destruct();
            restore(player);
            PoisonImmunityTask.makeImmune(player, 0);
            player.setCurrentInstanceArenaKC(0);
        }
    }
}

