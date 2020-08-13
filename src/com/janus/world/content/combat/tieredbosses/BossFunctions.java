package com.janus.world.content.combat.tieredbosses;

import com.janus.engine.task.Task;
import com.janus.engine.task.TaskManager;
import com.janus.model.*;
import com.janus.model.container.impl.Equipment;
import com.janus.net.packet.impl.EquipPacketListener;
import com.janus.world.World;
import com.janus.world.content.BonusManager;
import com.janus.world.content.skill.SkillManager;
import com.janus.world.entity.impl.player.Player;

import java.util.Arrays;

public class BossFunctions {

    public static final int ENTRY_DOOR_ID = 22945;

    public static final int EXIT_CAVE_ID = 5858;

    public static final Position DOOR = new Position(2747, 5374);

    public static int doorX = 2747;

    public static int doorY = 5374;

    public static final Position ARENA_ENTRANCE = new Position(2807, 10105);

    public static int entranceX = 2807;

    public static int entranceY = 10105;

    public static final Position ARENA_CENTRE = new Position(2776, 10089);

    public static boolean checkItems(Player player) {
        if (player.getInventory().getFreeSlots() != 28) {
            return false;
        }
        for (int i = 0; i < 14; i++) {
            if (player.getEquipment().get(i).getId() > 0)
                return false;
        }
        return true;
    }


    public static void handleDoor(Player player) {

        if (!checkItems(player)) {//This will not allow the player to enter the doors if they have items
            player.forceChat("I can't enter if I have items!");
            return;
        }

        player.forceChat("This feels dangerous...");
        player.getPacketSender().sendMessage("The ground begins to shake..");

        TaskManager.submit(new Task(4) {
            @Override
            protected void execute() {
                player.forceChat("Uh oh!");
                player.performAnimation(new Animation(1746));
                if (player.getLocation() != Locations.Location.BOSS_TIER_LOCATION) {
                    player.moveTo(new Position(entranceX, entranceY, player.getIndex() * 4));
                    player.setRegionInstance(new RegionInstance(player, RegionInstance.RegionInstanceType.BOSS_TIER_ARENA));
                }
                stop();
            }
        });

        if (!player.hasUsedBossTierTP()) {
            TaskManager.submit(new Task(9) {
                @Override
                protected void execute() {
                    player.forceChat("I think I should walk a little further..");
                    player.getPacketSender().sendMessage("@red@Visit the wiki for the guide!");
                    player.setHasUsedBossTierTP(true);
                    stop();
                }
            });
        }

    }

    public static void handleExit(Player player) {
        player.forceChat("Lets get out of here!");
        TaskManager.submit(new Task(3) {
            @Override
            protected void execute() {
                restoreOldStats(player);
                player.getInventory().deleteAll();
                player.getEquipment().deleteAll();
                player.getEquipment().refreshItems();
                player.moveTo(DOOR);
                destructBossTier(player);
                player.getUpdateFlag().flag(Flag.ANIMATION);
                stop();
            }
        });
    }

    public static boolean shouldDespawnNPCs(Player player) {
        if (player.getRegionInstance() != null && player.getRegionInstance().getNpcsList().isEmpty() && player.getLocation() == Locations.Location.BOSS_TIER_LOCATION) {
            return true;
        }
        return false;
    }

    public static boolean shouldDestroy(Player player) {
        return (player.getLocation() == Locations.Location.BOSS_TIER_LOCATION) && (player.getRegionInstance().equals(RegionInstance.RegionInstanceType.BOSS_TIER_ARENA));
    }

    public static void destructBossTier(final Player player) {

        if (shouldDestroy(player)) {
            System.out.println("DESTROYING INSTANCE FOR " + player.getUsername());

            int z = 0;
            Position nonInstance = new Position(doorX, doorY, z);
            player.moveTo(nonInstance);//Moves player to height 0
            System.out.println("Sending " + player.getUsername() + " to height 0");
            player.getRegionInstance().destruct();
        }

        if (shouldDespawnNPCs(player)) {
            System.out.println("DESPAWNING NPC'S FOR " + player.getUsername());
            player.getRegionInstance().getNpcsList().forEach(npc -> npc.removeInstancedNpcs(Locations.Location.BOSS_TIER_LOCATION, player.getPosition().getZ()));
            player.getRegionInstance().getNpcsList().forEach(npc -> World.deregister(npc));
        }
    }

    public static void updateSkills(Player player) {
        System.out.println("Updating Skills for " + player.getUsername());
        for (Skill skill : Skill.values())
            player.getSkillManager().updateSkill(skill);
    }

    public static void saveOldStats(Player player) {
        System.out.println("SAVING OLD STATS FOR " + player.getUsername());

        SkillManager.Skills currentSkills = player.getSkillManager().getSkills();
        player.bossGameLevels = currentSkills.level;
        player.bossGameSkillXP = currentSkills.experience;
        player.bossGameMaxLevels = currentSkills.maxLevel;

        System.out.println("SAVING " + Arrays.toString(player.getSkillManager().getSkills().level));
        System.out.println("SAVING " + Arrays.toString(player.getSkillManager().getSkills().experience));
        System.out.println("SAVING " + Arrays.toString(player.getSkillManager().getSkills().maxLevel));

    }


    public static void restoreOldStats(Player player) {
        System.out.println("Attempting to restore stats for " + player.getUsername());
        player.getInventory().deleteAll();
        player.getEquipment().deleteAll();
        player.getSkillManager().getSkills().level = player.bossGameLevels;
        player.getSkillManager().getSkills().experience = player.bossGameSkillXP;
        player.getSkillManager().getSkills().maxLevel = player.bossGameMaxLevels;

        player.getUpdateFlag().flag(Flag.ANIMATION);
        player.getEquipment().refreshItems();
        updateSkills(player);
    }

    public static void setNewStats(Player player, int attack, int defence, int strength, int ranged, int magic, int constitution, int prayer) {
        saveOldStats(player);
        player.getSkillManager().newSkillManager();
        updateSkills(player);
        player.getSkillManager().setMaxLevel(Skill.ATTACK, attack);
        player.getSkillManager().setMaxLevel(Skill.DEFENCE, defence);
        player.getSkillManager().setMaxLevel(Skill.STRENGTH, strength);
        player.getSkillManager().setMaxLevel(Skill.RANGED, ranged);
        player.getSkillManager().setMaxLevel(Skill.MAGIC, magic);
        player.getSkillManager().setMaxLevel(Skill.CONSTITUTION, constitution);
        player.getSkillManager().setMaxLevel(Skill.PRAYER, prayer);
        for (Skill skill : Skill.values()) {
            player.getSkillManager().setCurrentLevel(skill, player.getSkillManager().getMaxLevel(skill));
            player.getSkillManager().setExperience(skill, SkillManager.getExperienceForLevel(player.getSkillManager().getMaxLevel(skill)));
        }
    }

    public static void setEquipment(Player player, int weapon, int shield, int helm, int body, int legs, int neck, int cape, int hands, int feet) {
        player.getEquipment().set(Equipment.WEAPON_SLOT, new Item(weapon, 1));
        player.getEquipment().set(Equipment.SHIELD_SLOT, new Item(shield, 1000));
        player.getEquipment().set(Equipment.HEAD_SLOT, new Item(helm, 1));
        player.getEquipment().set(Equipment.BODY_SLOT, new Item(body, 1));
        player.getEquipment().set(Equipment.LEG_SLOT, new Item(legs, 1));
        player.getEquipment().set(Equipment.AMULET_SLOT, new Item(neck, 1));
        player.getEquipment().set(Equipment.CAPE_SLOT, new Item(cape, 1));
        player.getEquipment().set(Equipment.HANDS_SLOT, new Item(hands, 1));
        player.getEquipment().set(Equipment.FEET_SLOT, new Item(feet, 1));
        player.getEquipment().refreshItems();
        player.getUpdateFlag().flag(Flag.APPEARANCE);
        EquipPacketListener.resetWeapon(player);
        BonusManager.update(player);
    }


}
