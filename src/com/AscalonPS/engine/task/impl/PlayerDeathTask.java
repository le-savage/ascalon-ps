package com.AscalonPS.engine.task.impl;

import com.AscalonPS.GameLoader;
import com.AscalonPS.GameSettings;
import com.AscalonPS.engine.task.Task;
import com.AscalonPS.model.*;
import com.AscalonPS.model.Locations.Location;
import com.AscalonPS.util.Misc;
import com.AscalonPS.util.RandomUtility;
import com.AscalonPS.world.World;
import com.AscalonPS.world.content.ItemsKeptOnDeath;
import com.AscalonPS.world.content.minigames.impl.FreeForAll;
import com.AscalonPS.world.entity.impl.GroundItemManager;
import com.AscalonPS.world.entity.impl.npc.NPC;
import com.AscalonPS.world.entity.impl.player.Player;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Represents a player's death task, through which the process of dying is handled,
 * the animation, dropping items, etc.
 *
 * @author relex lawl, redone by Gabbe.
 */

public class PlayerDeathTask extends Task {

    Position oldPosition;
    Location loc;
    ArrayList<Item> itemsToKeep = null;
    NPC death;
    private Player player;
    private int ticks = 5;
    private boolean dropItems = true;
    /**
     * The PlayerDeathTask constructor.
     *
     * @param player The player setting off the task.
     */
    public PlayerDeathTask(Player player) {
        super(1, player, false);
        this.player = player;
    }

    public static NPC getDeathNpc(Player player) {
        NPC death = new NPC(2862, new Position(player.getPosition().getX() + 1, player.getPosition().getY() + 1));
        World.register(death);
        death.setEntityInteraction(player);
        death.performAnimation(new Animation(401));
        death.forceChat(randomDeath(player.getUsername()));
        return death;
    }

    public static String randomDeath(String name) {
        switch (RandomUtility.getRandom(8)) {
            case 0:
                return "Flub has told me to spare you " + Misc.formatText(name)
                        + "...";
            case 1:
                return "You owe me....";
            case 2:
                return "You belong to me!";
            case 3:
                return "Beware mortals, " + Misc.formatText(name)
                        + " travels with me!";
            case 4:
                return "Your time here is over, " + Misc.formatText(name)
                        + "!";
            case 5:
                return "Now is the time you die, " + Misc.formatText(name)
                        + "!";
            case 6:
                return "I claim " + Misc.formatText(name) + " as my own!";
            case 7:
                return "" + Misc.formatText(name) + " is mine!";
            case 8:
                return "Let me escort you back to Edgeville, "
                        + Misc.formatText(name) + "!";
            case 9:
                return "I have come for you, " + Misc.formatText(name)
                        + "!";
        }
        return "";
    }

    @Override
    public void execute() {
        if (player == null) {
            stop();
            return;
        }
        try {
            switch (ticks) {
                case 5:
                    player.getPacketSender().sendInterfaceRemoval();
                    player.getMovementQueue().setLockMovement(true).reset();
                    break;
                case 3:
                    player.performAnimation(new Animation(0x900));
                    player.getPacketSender().sendMessage("Oh dear, you are dead!");
                    this.death = getDeathNpc(player);
                    break;
                case 1:
                    this.oldPosition = player.getPosition().copy();
                    this.loc = player.getLocation();
                    if (player.inFFA) {
                        Player killer = player.getCombatBuilder().getKiller(true);
                        FreeForAll.leaveGame(player);
                        killer.getSkillManager().setCurrentLevel(Skill.CONSTITUTION, killer.getSkillManager().getMaxLevel(Skill.CONSTITUTION));
                        killer.setSpecialPercentage(100);
                    }
                    if (loc != Location.DUNGEONEERING && !player.inFFA && loc != Location.PEST_CONTROL_GAME && loc != Location.DUEL_ARENA && loc != Location.FREE_FOR_ALL_ARENA && loc != Location.FREE_FOR_ALL_WAIT && loc != Location.SOULWARS && loc != Location.FIGHT_PITS && loc != Location.FIGHT_PITS_WAIT_ROOM && loc != Location.FIGHT_CAVES && loc != Location.RECIPE_FOR_DISASTER && loc != Location.GRAVEYARD) {
                        Player killer = player.getCombatBuilder().getKiller(true);
                        if (player.getRights().equals(PlayerRights.OWNER) || player.getRights().equals(PlayerRights.DEVELOPER))
                            dropItems = false;

                        if (loc == Location.WILDERNESS && killer != null) { //Wilderness rules
                            if (killer.getRights().equals(PlayerRights.OWNER) || killer.getRights().equals(PlayerRights.DEVELOPER)) {
                                dropItems = false;
                            }


                            if (player.getHostAddress().equals(killer.getHostAddress())) {
                                dropItems = false;
                                World.sendStaffMessage(player.getUsername().toUpperCase() + " just tried to kill their alt: " + killer.getUsername().toUpperCase() + " in the wildy!");
                            }

                            if (killer.getGameMode() != GameMode.NORMAL) {
                                player.getPacketSender().sendMessage("No items dropped due to killer being an " + Misc.formatText(killer.getGameMode().name()));
                                killer.getPacketSender().sendMessage("No items dropped due to killer being an " + Misc.formatText(killer.getGameMode().name()));
                                dropItems = false;
                            }


                        }
                        if (loc != Location.WILDERNESS) {
                            dropItems = false;
                        }

                        if (killer != null) {
                            if (killer.getRights().equals(PlayerRights.OWNER) || killer.getRights().equals(PlayerRights.DEVELOPER)) {
                                dropItems = false;
                            }
                        }
                        boolean spawnItems = loc != Location.NOMAD;
                        if (dropItems) {
                            itemsToKeep = ItemsKeptOnDeath.getItemsToKeep(player);
                            final CopyOnWriteArrayList<Item> playerItems = new CopyOnWriteArrayList<Item>();
                            playerItems.addAll(player.getInventory().getValidItems());
                            playerItems.addAll(player.getEquipment().getValidItems());
                            final Position position = player.getPosition();
                            for (Item item : playerItems) {
                                if (!item.tradeable() || itemsToKeep.contains(item)) {
                                    if (!itemsToKeep.contains(item)) {
                                        itemsToKeep.add(item);
                                    }
                                    continue;
                                }
                                if (spawnItems) {
                                    if (item.getId() > 0 && item.getAmount() > 0 && killer != null && killer.getGameMode() == GameMode.NORMAL) {
                                        GroundItemManager.spawnGroundItem((killer), new GroundItem(item, position, killer.getUsername(), player.getHostAddress(), false, 150, true, 150));
                                    }
                                }
                            }
                            if (killer != null) {
                                killer.getPlayerKillingAttributes().add(player);
                                killer.getPlayerKillingAttributes()
                                        .setPlayerKills(killer.getPlayerKillingAttributes().getPlayerKills()
                                                + GameLoader.getDay() == GameLoader.TUESDAY ? 2 : 1);
                                player.getPlayerKillingAttributes()
                                        .setPlayerDeaths(player.getPlayerKillingAttributes().getPlayerDeaths() + 1);
                                player.getPlayerKillingAttributes().setPlayerKillStreak(0);
                                player.getPointsHandler().refreshPanel();
                            }
                            player.getInventory().resetItems().refreshItems();
                            player.getEquipment().resetItems().refreshItems();
                        }
                    } else
                        dropItems = false;
                    player.getPacketSender().sendInterfaceRemoval();
                    player.setEntityInteraction(null);
                    player.getMovementQueue().setFollowCharacter(null);
                    player.getCombatBuilder().cooldown(false);
                    player.setTeleporting(false);
                    player.setWalkToTask(null);
                    player.getSkillManager().stopSkilling();
                    break;
                case 0:
                    if (dropItems) {
                        if (player.getGameMode() == GameMode.HARDCORE_IRONMAN) {
                            //GameMode.set(player, player.getGameMode(), true);
                        } else {
                            if (itemsToKeep != null) {
                                for (Item it : itemsToKeep) {
                                    player.getInventory().add(it.getId(), 1);
                                }
                                itemsToKeep.clear();
                            }
                        }
                    }
                    if (death != null) {
                        World.deregister(death);
                    }
                    player.restart();
                    player.getUpdateFlag().flag(Flag.APPEARANCE);
                    loc.onDeath(player);
                    if (loc != Location.DUNGEONEERING && loc != Location.BOSS_TIER_LOCATION) {
                        if (player.getPosition().equals(oldPosition))
                            player.moveTo(GameSettings.DEFAULT_POSITION.copy());
                        player.getPacketSender().sendRichPresenceState("I Just Died!");
                    }
                    player = null;
                    oldPosition = null;
                    stop();
                    break;
            }
            ticks--;
        } catch (Exception e) {
            setEventRunning(false);
            e.printStackTrace();
            if (player != null) {
                player.moveTo(GameSettings.DEFAULT_POSITION.copy());
                player.setConstitution(player.getSkillManager().getMaxLevel(Skill.CONSTITUTION));
            }
        }
    }

}
