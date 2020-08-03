package com.janus.net.packet.impl;

import com.janus.engine.task.Task;
import com.janus.engine.task.TaskManager;
import com.janus.model.*;
import com.janus.model.Locations.Location;
import com.janus.net.packet.Packet;
import com.janus.net.packet.PacketListener;
import com.janus.util.Misc;
import com.janus.util.RandomUtility;
import com.janus.world.World;
import com.janus.world.content.*;
import com.janus.world.content.combat.range.DwarfMultiCannon;
import com.janus.world.content.dialogue.DialogueManager;
import com.janus.world.content.skill.SkillManager;
import com.janus.world.content.skill.impl.construction.Construction;
import com.janus.world.content.skill.impl.dungeoneering.ItemBinding;
import com.janus.world.content.skill.impl.herblore.Herblore;
import com.janus.world.content.skill.impl.herblore.IngridientsBook;
import com.janus.world.content.skill.impl.hunter.BoxTrap;
import com.janus.world.content.skill.impl.hunter.Hunter;
import com.janus.world.content.skill.impl.hunter.JarData;
import com.janus.world.content.skill.impl.hunter.PuroPuro;
import com.janus.world.content.skill.impl.hunter.SnareTrap;
import com.janus.world.content.skill.impl.hunter.Trap.TrapState;
import com.janus.world.content.skill.impl.prayer.Prayer;
import com.janus.world.content.skill.impl.runecrafting.Runecrafting;
import com.janus.world.content.skill.impl.runecrafting.RunecraftingPouches;
import com.janus.world.content.skill.impl.runecrafting.RunecraftingPouches.RunecraftingPouch;
import com.janus.world.content.skill.impl.slayer.SlayerDialogues;
import com.janus.world.content.skill.impl.slayer.SlayerTasks;
import com.janus.world.content.skill.impl.summoning.CharmingImp;
import com.janus.world.content.skill.impl.summoning.SummoningData;
import com.janus.world.content.skill.impl.woodcutting.BirdNests;
import com.janus.world.content.transportation.JewelryTeleporting;
import com.janus.world.content.transportation.TeleportHandler;
import com.janus.world.content.transportation.TeleportType;
import com.janus.world.entity.impl.player.Player;


public class ItemActionPacketListener implements PacketListener {


    public static void cancelCurrentActions(Player player) {
        player.getPacketSender().sendInterfaceRemoval();
        player.setTeleporting(false);
        player.setWalkToTask(null);
        player.setInputHandling(null);
        player.getSkillManager().stopSkilling();
        player.setEntityInteraction(null);
        player.getMovementQueue().setFollowCharacter(null);
        player.getCombatBuilder().cooldown(false);
        player.setResting(false);
    }

    public static boolean checkReqs(Player player, Location targetLocation) {
        if (player.getConstitution() <= 0)
            return false;
        if (player.getTeleblockTimer() > 0) {
            player.getPacketSender().sendMessage("A magical spell is blocking you from teleporting.");
            return false;
        }
        if (player.getLocation() != null && !player.getLocation().canTeleport(player))
            return false;
        if (player.isPlayerLocked() || player.isCrossingObstacle()) {
            player.getPacketSender().sendMessage("You cannot teleport right now.");
            return false;
        }
        return true;
    }

    private static void firstAction(final Player player, Packet packet) {
        int interfaceId = packet.readUnsignedShort();
        int slot = packet.readShort();
        int itemId = packet.readShort();

        Location targetLocation = player.getLocation();

        if (interfaceId == 38274) {
            Construction.handleItemClick(itemId, player);
            return;
        }

        if (slot < 0 || slot > player.getInventory().capacity())
            return;
        if (player.getInventory().getItems()[slot].getId() != itemId)
            return;
        player.setInteractingItem(player.getInventory().getItems()[slot]);
        if (Prayer.isBone(itemId)) {
            Prayer.buryBone(player, itemId);
            return;
        }
        if (Consumables.isFood(player, itemId, slot))
            return;
        if (Consumables.isPotion(itemId)) {
            Consumables.handlePotion(player, itemId, slot);
            return;
        }
        if (BirdNests.isNest(itemId)) {
            BirdNests.searchNest(player, itemId);
            return;
        }
        if (Herblore.cleanHerb(player, itemId))
            return;
        if (MemberScrolls.handleScroll(player, itemId))
            return;
        //if(ClueScroll.handleCasket(player, itemId) || SearchScrolls.loadClueInterface(player, itemId) || MapScrolls.loadClueInterface(player, itemId) || Puzzle.loadClueInterface(player, itemId) || CoordinateScrolls.loadClueInterface(player, itemId) || DiggingScrolls.loadClueInterface(player, itemId))
        //return;
        if (Effigies.isEffigy(itemId)) {
            Effigies.handleEffigy(player, itemId);
            return;
        }
        if (ExperienceLamps.handleLamp(player, itemId)) {
            return;
        }

        if (itemId == BonusExperienceScroll.scrollID){
            BonusExperienceScroll.handleScroll(player, itemId);
            return;
        }

        if (itemId == DungeoneeringLamp.lampID){
            DungeoneeringLamp.handleLamp(player, itemId);
            return;
        }

        switch (itemId) {

            case 13663:
                if (player.getInterfaceId() > 0) {
                    player.getPacketSender().sendMessage("Please close the interface you have open before doing this.");
                    return;
                }
                player.setUsableObject(new Object[2]).setUsableObject(0, "reset");
                player.getPacketSender().sendString(38006, "Choose stat to reset!").sendMessage("@red@Please select a skill you wish to reset and then click on the 'Confim' button.").sendString(38090, "Which skill would you like to reset?");
                player.getPacketSender().sendInterface(38000);
                break;
            case 19670:
                if (player.busy()) {
                    player.getPacketSender().sendMessage("You can not do this right now.");
                    return;
                }
                player.setDialogueActionId(70);
                DialogueManager.start(player, player.getGameMode() == GameMode.NORMAL ? 108 : 109);
                break;
            case 8007: //Varrock
                if (player.inFFALobby) {
                    player.sendMessage("Use the portal to leave the ffa lobby!");
                    return;
                }
                if (player.inFFA) {
                    player.sendMessage("You can not teleport out of FFA!");
                    return;
                }
                if (!player.getClickDelay().elapsed(1200) || player.getMovementQueue().isLockMovement()) {
                    return;
                }
                if (player.getLocation() == Location.CONSTRUCTION) {
                    player.getPacketSender().sendMessage("Please use the portal to exit your house");
                    return;
                }


                if (!checkReqs(player, targetLocation)) {
                    return;
                }
                player.setTeleporting(true).getMovementQueue().setLockMovement(true).reset();
                cancelCurrentActions(player);
                player.performAnimation(new Animation(4731));
                player.performGraphic(new Graphic(678));
                player.getInventory().delete(8007, 1);
                player.getClickDelay().reset();

                TaskManager.submit(new Task(2, player, false) {
                    @Override
                    public void execute() {
                        Position position = new Position(3210, 3424, 0);
                        player.moveTo(position);
                        player.getMovementQueue().setLockMovement(false).reset();
                        this.stop();
                    }
                });


                break;
            case 8008: //Lumbridge
                if (player.inFFALobby) {
                    player.sendMessage("Use the portal to leave the ffa lobby!");
                    return;
                }
                if (player.inFFA) {
                    player.sendMessage("You can not teleport out of FFA!");
                    return;
                }
                if (!player.getClickDelay().elapsed(1200) || player.getMovementQueue().isLockMovement()) {
                    return;
                }
                if (player.getLocation() == Location.CONSTRUCTION) {
                    player.getPacketSender().sendMessage("Please use the portal to exit your house");
                    return;
                }


                if (!checkReqs(player, targetLocation)) {
                    return;
                }
                player.setTeleporting(true).getMovementQueue().setLockMovement(true).reset();
                cancelCurrentActions(player);
                player.performAnimation(new Animation(4731));
                player.performGraphic(new Graphic(678));
                player.getInventory().delete(8008, 1);
                player.getClickDelay().reset();

                TaskManager.submit(new Task(2, player, false) {
                    @Override
                    public void execute() {
                        Position position = new Position(3222, 3218, 0);
                        player.moveTo(position);
                        player.getMovementQueue().setLockMovement(false).reset();
                        this.stop();
                    }
                });


                break;
            case 8009: //Falador
                if (player.inFFALobby) {
                    player.sendMessage("Use the portal to leave the ffa lobby!");
                    return;
                }
                if (player.inFFA) {
                    player.sendMessage("You can not teleport out of FFA!");
                    return;
                }
                if (!player.getClickDelay().elapsed(1200) || player.getMovementQueue().isLockMovement()) {
                    return;
                }
                if (player.getLocation() == Location.CONSTRUCTION) {
                    player.getPacketSender().sendMessage("Please use the portal to exit your house");
                    return;
                }


                if (!checkReqs(player, targetLocation)) {
                    return;
                }
                player.setTeleporting(true).getMovementQueue().setLockMovement(true).reset();
                cancelCurrentActions(player);
                player.performAnimation(new Animation(4731));
                player.performGraphic(new Graphic(678));
                player.getInventory().delete(8009, 1);
                player.getClickDelay().reset();

                TaskManager.submit(new Task(2, player, false) {
                    @Override
                    public void execute() {
                        Position position = new Position(2964, 3378, 0);
                        player.moveTo(position);
                        player.getMovementQueue().setLockMovement(false).reset();
                        this.stop();
                    }
                });


                break;
            case 8010: //Camelot
                if (player.inFFALobby) {
                    player.sendMessage("Use the portal to leave the ffa lobby!");
                    return;
                }
                if (player.inFFA) {
                    player.sendMessage("You can not teleport out of FFA!");
                    return;
                }
                if (!player.getClickDelay().elapsed(1200) || player.getMovementQueue().isLockMovement()) {
                    return;
                }
                if (player.getLocation() == Location.CONSTRUCTION) {
                    player.getPacketSender().sendMessage("Please use the portal to exit your house");
                    return;
                }


                if (!checkReqs(player, targetLocation)) {
                    return;
                }
                player.setTeleporting(true).getMovementQueue().setLockMovement(true).reset();
                cancelCurrentActions(player);
                player.performAnimation(new Animation(4731));
                player.performGraphic(new Graphic(678));
                player.getInventory().delete(8010, 1);
                player.getClickDelay().reset();

                TaskManager.submit(new Task(2, player, false) {
                    @Override
                    public void execute() {
                        Position position = new Position(2757, 3477, 0);
                        player.moveTo(position);
                        player.getMovementQueue().setLockMovement(false).reset();
                        this.stop();
                    }
                });


                break;
            case 8011: //Ardy
                if (player.inFFALobby) {
                    player.sendMessage("Use the portal to leave the ffa lobby!");
                    return;
                }
                if (player.inFFA) {
                    player.sendMessage("You can not teleport out of FFA!");
                    return;
                }
                if (!player.getClickDelay().elapsed(1200) || player.getMovementQueue().isLockMovement()) {
                    return;
                }
                if (player.getLocation() == Location.CONSTRUCTION) {
                    player.getPacketSender().sendMessage("Please use the portal to exit your house");
                    return;
                }


                if (!checkReqs(player, targetLocation)) {
                    return;
                }
                player.setTeleporting(true).getMovementQueue().setLockMovement(true).reset();
                cancelCurrentActions(player);
                player.performAnimation(new Animation(4731));
                player.performGraphic(new Graphic(678));
                player.getInventory().delete(8011, 1);
                player.getClickDelay().reset();

                TaskManager.submit(new Task(2, player, false) {
                    @Override
                    public void execute() {
                        Position position = new Position(2662, 3305, 0);
                        player.moveTo(position);
                        player.getMovementQueue().setLockMovement(false).reset();
                        this.stop();
                    }
                });


                break;
            case 8012: //Watchtower Tele
                if (player.inFFALobby) {
                    player.sendMessage("Use the portal to leave the ffa lobby!");
                    return;
                }
                if (player.inFFA) {
                    player.sendMessage("You can not teleport out of FFA!");
                    return;
                }
                if (!player.getClickDelay().elapsed(1200) || player.getMovementQueue().isLockMovement()) {
                    return;
                }
                if (player.getLocation() == Location.CONSTRUCTION) {
                    player.getPacketSender().sendMessage("Please use the portal to exit your house");
                    return;
                }


                if (!checkReqs(player, targetLocation)) {
                    return;
                }
                player.setTeleporting(true).getMovementQueue().setLockMovement(true).reset();
                cancelCurrentActions(player);
                player.performAnimation(new Animation(4731));
                player.performGraphic(new Graphic(678));
                player.getInventory().delete(8012, 1);
                player.getClickDelay().reset();

                TaskManager.submit(new Task(2, player, false) {
                    @Override
                    public void execute() {
                        Position position = new Position(2728, 3349, 0);
                        player.moveTo(position);
                        player.getMovementQueue().setLockMovement(false).reset();
                        this.stop();
                    }
                });


                break;
            case 8013: //Home Tele
                TeleportHandler.teleportPlayer(player, new Position(3087, 3491), TeleportType.NORMAL);
                break;
            case 13598: //Runecrafting Tele
                TeleportHandler.teleportPlayer(player, new Position(2595, 4772), TeleportType.NORMAL);
                break;
            case 13599: //Air Altar Tele
                TeleportHandler.teleportPlayer(player, new Position(2845, 4832), TeleportType.NORMAL);
                break;
            case 13600: //Mind Altar Tele
                TeleportHandler.teleportPlayer(player, new Position(2796, 4818), TeleportType.NORMAL);
                break;
            case 13601: //Water Altar Tele
                TeleportHandler.teleportPlayer(player, new Position(2713, 4836), TeleportType.NORMAL);
                break;
            case 13602: //Earth Altar Tele
                TeleportHandler.teleportPlayer(player, new Position(2660, 4839), TeleportType.NORMAL);
                break;
            case 13603: //Fire Altar Tele
                TeleportHandler.teleportPlayer(player, new Position(2584, 4836), TeleportType.NORMAL);
                break;
            case 13604: //Body Altar Tele
                TeleportHandler.teleportPlayer(player, new Position(2527, 4833), TeleportType.NORMAL);
                break;
            case 13605: //Cosmic Altar Tele
                TeleportHandler.teleportPlayer(player, new Position(2162, 4833), TeleportType.NORMAL);
                break;
            case 13606: //Chaos Altar Tele
                TeleportHandler.teleportPlayer(player, new Position(2269, 4843), TeleportType.NORMAL);
                break;
            case 13607: //Nature Altar Tele
                TeleportHandler.teleportPlayer(player, new Position(2398, 4841), TeleportType.NORMAL);
                break;
            case 13608: //Law Altar Tele
                TeleportHandler.teleportPlayer(player, new Position(2464, 4834), TeleportType.NORMAL);
                break;
            case 13609: //Death Altar Tele
                TeleportHandler.teleportPlayer(player, new Position(2207, 4836), TeleportType.NORMAL);
                break;
            case 18809: //Rimmington Tele
                TeleportHandler.teleportPlayer(player, new Position(2957, 3214), TeleportType.NORMAL);
                break;
            case 18811: //Pollnivneach Tele
                TeleportHandler.teleportPlayer(player, new Position(3359, 2910), TeleportType.NORMAL);
                break;
            case 18812: //Rellekka Tele
                TeleportHandler.teleportPlayer(player, new Position(2659, 3661), TeleportType.NORMAL);
                break;
            case 18814: //Yanielle Tele
                TeleportHandler.teleportPlayer(player, new Position(2606, 3093), TeleportType.NORMAL);
                break;
            case 6542:
                ChristmasPresent.openBox(player);
                break;


            case 10025:
                CharmBox.open(player);
                break;


            case 1959:
                TrioBosses.eatPumpkin(player);
                break;
            case 2677:
            case 2678:
            case 2679:
            case 2680:
            case 2681:
            case 2682:
            case 2683:
            case 2684:
            case 2685:
                ClueScrolls.giveHint(player, itemId);
                break;
            case 7956:
                if (player.getRights() == PlayerRights.DONATOR) {
                    if (Misc.getRandom(15) == 5) {
                        player.getPacketSender().sendMessage("Casket has been saved as a donator benefit");
                    } else {
                        player.getInventory().delete(7956, 1);
                    }
                }
                if (player.getRights() == PlayerRights.SUPER_DONATOR || player.getRights() == PlayerRights.SUPPORT) {
                    if (Misc.getRandom(12) == 5) {
                        player.getPacketSender().sendMessage("Casket has been saved as a donator benefit");
                    } else {
                        player.getInventory().delete(7956, 1);
                    }
                }
                if (player.getRights() == PlayerRights.EXTREME_DONATOR || player.getRights() == PlayerRights.MODERATOR) {
                    if (Misc.getRandom(9) == 5) {
                        player.getPacketSender().sendMessage("Casket has been saved as a donator benefit");
                    } else {
                        player.getInventory().delete(7956, 1);
                    }
                }
                if (player.getRights() == PlayerRights.LEGENDARY_DONATOR || player.getRights() == PlayerRights.ADMINISTRATOR) {
                    if (Misc.getRandom(6) == 5) {
                        player.getPacketSender().sendMessage("Casket has been saved as a donator benefit");
                    } else {
                        player.getInventory().delete(7956, 1);
                    }
                }
                if (player.getRights() == PlayerRights.UBER_DONATOR || player.getRights() == PlayerRights.DEVELOPER) {
                    if (Misc.getRandom(3) == 2) {
                        player.getPacketSender().sendMessage("Casket has been saved as a donator benefit");
                    } else {
                        player.getInventory().delete(7956, 1);
                    }
                }
                if (player.getRights() == PlayerRights.PLAYER) {
                    player.getInventory().delete(7956, 1);
                }

                int[] rewards = {200, 202, 204, 206, 208, 210, 212, 214, 216, 218, 220, 2486, 3052, 1624, 1622, 1620, 1618, 1632, 1516, 1514, 454, 448, 450, 452, 378, 372, 7945, 384, 390, 15271, 533, 535, 537, 18831, 556, 558, 555, 554, 557, 559, 564, 562, 566, 9075, 563, 561, 560, 565, 888, 890, 892, 11212, 9142, 9143, 9144, 9341, 9244, 866, 867, 868, 2, 10589, 10564, 6809, 4131, 15126, 4153, 1704, 1149};
                int[] rewardsAmount = {200, 200, 200, 120, 50, 100, 70, 60, 90, 40, 30, 15, 10, 230, 140, 70, 20, 10, 400, 200, 400, 250, 100, 100, 1000, 800, 500, 200, 100, 50, 150, 100, 50, 5, 1500, 1500, 1500, 1500, 1500, 1500, 1000, 1000, 500, 500, 500, 500, 500, 500, 3000, 2500, 800, 300, 3500, 3500, 500, 150, 80, 3000, 1500, 400, 500, 1, 1, 1, 1, 1, 1, 1, 1};
                int rewardPos = Misc.getRandom(rewards.length - 1);
                player.getInventory().add(rewards[rewardPos], (int) ((rewardsAmount[rewardPos] * 0.5) + (Misc.getRandom(rewardsAmount[rewardPos]))));
                break;

            //Mystery Boxes
            case 14664://BEST IN SLOT MBOX
                RUBoxes.openBestInSlotBox(player);
                break;
            case 6829:
                RUBoxes.openCommonBox(player);
                break;
            case 6830:
                RUBoxes.openUncommonBox(player);
                player.getPacketSender().sendMessage("You open the box....");
                break;
            case 6831:
                RUBoxes.openRareBox(player);
                player.getPacketSender().sendMessage("You open the box....");
                break;
            case 6832:
                RUBoxes.openExtremeBox(player);
                player.getPacketSender().sendMessage("You open the box....");
                break;
            case 6833:
                RUBoxes.openLegendaryBox(player);
                player.getPacketSender().sendMessage("You open the box....");
                break;
            case 6183://Boss Pet Box box
                RUBoxes.openBossBox(player);
                player.getPacketSender().sendMessage("You open the box....");
                break;

            case 19890://AFK Title Scroll
                player.setTitle("@blu@" + "Afker");
                player.getUpdateFlag().flag(Flag.APPEARANCE);
                player.getInventory().delete(19890, 1);
                break;


            //Clue Scroll
            case 2714:
                ClueScrolls.addClueRewards(player);
                break;
            case 15387:
                player.getInventory().delete(15387, 1);
                rewards = new int[]{1377, 1149, 7158, 3000, 219, 5016, 6293, 6889, 2205, 3051, 269, 329, 3779, 6371, 2442, 347, 247};
                player.getInventory().add(rewards[RandomUtility.getRandom(rewards.length - 1)], 1);
                break;
            case 407:
                player.getInventory().delete(407, 1);
                if (RandomUtility.getRandom(3) < 3) {
                    player.getInventory().add(409, 1);
                } else if (RandomUtility.getRandom(4) < 4) {
                    player.getInventory().add(411, 1);
                } else
                    player.getInventory().add(413, 1);
                break;
            case 405:
                player.getInventory().delete(405, 1);
                if (RandomUtility.getRandom(1) < 1) {
                    int coins = RandomUtility.getRandom(30000);
                    player.getInventory().add(995, coins);
                    player.getPacketSender().sendMessage("The casket contained " + coins + " coins!");
                } else
                    player.getPacketSender().sendMessage("The casket was empty.");
                break;
            case 15084:
                Gambling.rollDice(player);
                break;
            case 6307:
                player.setDialogueActionId(79);
                DialogueManager.start(player, 0);
                break;
            case 299:
                player.getPacketSender().sendMessage("Disabled because you noobs can't handle losing");
                //Gambling.plantSeed(player);
                break;
            case 4155:
                if (player.getSlayer().getSlayerTask() == SlayerTasks.NO_TASK) {
                    player.getPacketSender().sendInterfaceRemoval();
                    player.getPacketSender().sendMessage("Your Enchanted gem will only work if you have a Slayer task.");
                    return;
                }
                DialogueManager.start(player, SlayerDialogues.dialogue(player));
                break;
            case 11858:
            case 11860:
            case 11862:
            case 11848:
            case 11856:
            case 11850:
            case 11854:
            case 11852:
            case 11846:
                if (!player.getClickDelay().elapsed(2000) || !player.getInventory().contains(itemId))
                    return;
                if (player.busy()) {
                    player.getPacketSender().sendMessage("You cannot open this right now.");
                    return;
                }

                int[] items = itemId == 11858 ? new int[]{10350, 10348, 10346, 10352} :
                        itemId == 11860 ? new int[]{10334, 10330, 10332, 10336} :
                                itemId == 11862 ? new int[]{10342, 10338, 10340, 10344} :
                                        itemId == 11848 ? new int[]{4716, 4720, 4722, 4718} :
                                                itemId == 11856 ? new int[]{4753, 4757, 4759, 4755} :
                                                        itemId == 11850 ? new int[]{4724, 4728, 4730, 4726} :
                                                                itemId == 11854 ? new int[]{4745, 4749, 4751, 4747} :
                                                                        itemId == 11852 ? new int[]{4732, 4734, 4736, 4738} :
                                                                                itemId == 11846 ? new int[]{4708, 4712, 4714, 4710} :
                                                                                        new int[]{itemId};

                if (player.getInventory().getFreeSlots() < items.length) {
                    player.getPacketSender().sendMessage("You do not have enough space in your inventory.");
                    return;
                }
                player.getInventory().delete(itemId, 1);
                for (int i : items) {
                    player.getInventory().add(i, 1);
                }
                player.getPacketSender().sendMessage("You open the set and find items inside.");
                player.getClickDelay().reset();
                break;
            case 952:
                Digging.dig(player);
                break;
            case 10006:
                // Hunter.getInstance().laySnare(client);
                Hunter.layTrap(player, new SnareTrap(new GameObject(19175, new Position(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ())), TrapState.SET, 200, player));
                break;
            case 10008:
                Hunter.layTrap(player, new BoxTrap(new GameObject(19187, new Position(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ())), TrapState.SET, 200, player));
                break;
            case 5509:
            case 5510:
            case 5512:
                RunecraftingPouches.fill(player, RunecraftingPouch.forId(itemId));
                break;
            case 292:
                IngridientsBook.readBook(player, 0, false);
                break;

            case 18768://Starter Mystery Box
                player.getInventory().delete(18768, 1);
                rewards = new int[]{11894, 11898, 11928, 11930, 11842, 19588, 19592, 19596, 7462, 11235, 989, 18782, 4151, 6585};
                player.getInventory().add(rewards[RandomUtility.getRandom(rewards.length - 1)], 1);
                break;

            case 6199:
                int rewards2[][] = {
                        {3749, 3753, 10828, 1215, 4587, 4091, 4093, 4095, 4097, 1079, 1127, 1093, 15332, 3024, 15272, 6685, 1434, 2, 536, 534, 1149, 1305, 1377, 1434, 1615, 3000, 3204, 2941, 2947, 2503, 15272, 2503, 10499, 6199, 6326, 861, 1163, 1201, 6111, 544, 6199, 542, 5574, 5575, 5576, 1215, 3105, 13734, 7400, 2572, 11118}, //Common, 0
                        {11133, 15126, 10828, 6199, 3751, 3753, 11884, 10589, 18782, 6739, 6739, 2577, 2581, 18782, 15332, 15332, 15332, 11732, 6199, 10564, 6809, 4587, 1249, 3204, 1305, 1377, 1434, 6528, 7158, 4153, 6, 8, 10, 12, 4675, 6914, 6889, 4716, 4718, 4720, 4722, 4745, 4747, 4749, 4751, 4708, 4710, 4712, 4714, 4732, 4734, 4736, 4738, 4724, 4726, 4728, 4730, 4151, 11732, 6585, 2577, 2581, 6737, 11235, 4675, 4087, 1187, 6914, 6889, 3140, 6916, 6918, 6920, 6922, 6924, 6731, 6735, 6733}, //Uncommon, 1
                        {3749, 3753, 10828, 1215, 4587, 4091, 4093, 4095, 4097, 1079, 1127, 6739, 15259, 15332, 15126, 11856, 11854, 11852, 11846, 11850, 11732, 11848, 2577, 2581, 2572, 18782, 6920, 6922, 11335, 15241, 15243, 6585, 4151, 11696, 11724, 11726, 11728, 11694, 11718, 11720, 11722, 11700, 11716, 11698, 11730, 11283, 18349, 18351, 18353, 18355, 18357, 18359, 2527, 12601, 15486, 15018, 15019, 15020, 15220} //Rare, 2
                };
                double numGen = Math.random();
                /** Chances
                 *  50% chance of Common Items - cheap gear, high-end consumables
                 *  40% chance of Uncommon Items - various high-end coin-bought gear
                 *  10% chance of Rare Items - Highest-end coin-bought gear, some voting-point/pk-point equipment
                 */
                int rewardGrade = numGen >= 0.5 ? 0 : numGen >= 0.20 ? 1 : 2;
                rewardPos = RandomUtility.getRandom(rewards2[rewardGrade].length - 1);
                player.getInventory().delete(6199, 1);
                player.getInventory().add(rewards2[rewardGrade][rewardPos], 1).refreshItems();
                break;

            case 15501:
                int superiorRewards[][] = {
                        {11133, 15126, 10828, 3751, 3753, 10589, 10564, 6809, 4587, 2581, 2577, 2577, 2577, 2581, 2581, 18782, 18782, 18782, 1249, 3204, 1305, 4151, 6585, 1377, 1434, 6528, 7158, 4153, 6, 8, 10, 12, 4675, 6914, 6889}, //Uncommon, 0
                        {6739, 15259, 15332, 2579, 6920, 6922, 15241, 11882, 4151, 13867, 6585, 6570, 11884, 11906, 11283, 20084}, //Rare, 1
                        {6570, 15018, 15019, 15020, 15220, 11730, 11718, 11720, 11722, 11724, 11726, 13902, 13857, 11283, 18349, 18353, 13896, 18357, 13899, 10551, 4151, 2577,}, //Epic, 2
                        {11235, 17273, 14484, 19023, 11696, 11698, 11700, 20000, 20001, 20002, 13899, 11613, 19780, 20072, 15486, 19336, 19337, 19338, 19339, 19340, 14009, 14010, 14008} //Legendary, 3
                };
                double superiorNumGen = Math.random();
                /*//** Chances
                 *  40% chance of Uncommon Items - various high-end coin-bought gear
                 *  30% chance of Rare Items - Highest-end coin-bought gear, Some poor voting-point/pk-point equipment
                 *  20% chance of Epic Items -Better voting-point/pk-point equipment
                 *  10% chance of Legendary Items - Only top-notch voting-point/pk-point equipment
                 */
                int superiorRewardGrade = superiorNumGen >= 0.60 ? 0 : superiorNumGen >= 0.30 ? 1 : superiorNumGen >= 0.10 ? 2 : 3;
                int superiorRewardPos = RandomUtility.getRandom(superiorRewards[superiorRewardGrade].length - 1);
                player.getInventory().delete(15501, 1);
                player.getInventory().add(superiorRewards[superiorRewardGrade][superiorRewardPos], 1).refreshItems();
                break;

            case 10934: // maxing scroll
                player.getInventory().delete(10934, 1);
                player.incrementAmountDonated(30);
                player.getPacketSender().sendMessage("+$30 added towards your donator rank!");
                for (Skill skill : Skill.values()) {
                    int level = SkillManager.getMaxAchievingLevel(skill);
                    player.getSkillManager().setCurrentLevel(skill, level).setMaxLevel(skill, level).setExperience(skill,
                            SkillManager.getExperienceForLevel(level == 99 ? 99 : 99));
                }
                if (player.getDifficulty() != Difficulty.Zezima) { //If player is not zezima mode, set to default to enable difficulty change
                    player.getPacketSender().sendMessage("YOUR DIFFICULTY HAS BEEN RESET SO YOU CAN CHANGE WITHOUT A STAT RESET <3");
                    player.setDifficulty(Difficulty.Default);
                }
                MemberScrolls.checkForRankUpdate(player);
                PlayerPanel.refreshPanel(player);
                World.sendMessage(player.getUsername() + " has just maxed their account! Woohoo!");
                player.getPacketSender().sendConsoleMessage("You are now a master of all skills!");
                Achievements.finishAchievement(player, Achievements.AchievementData.REACH_LEVEL_99_IN_ALL_SKILLS);
                player.getUpdateFlag().flag(Flag.APPEARANCE);
                break;

            case 19613: // afk points //Todo make all tokens redeem at once
                player.getPointsHandler().incrementAfkPoints(1);
                PlayerPanel.refreshPanel(player);
                player.getPacketSender().sendMessage("1 pts added. You now have " + player.getPointsHandler().getAfkPoints());
                break;


            case 20999://tanzanite helm
                if (player.getInventory().contains(12282)) {//add tanz to helm
                    player.getInventory().delete(20999, 1);
                    player.getInventory().delete(12282, 1);
                    player.getInventory().add(12278, 1);
                    World.sendMessage("<img=10> @red@[INFO]" + player.getUsername() + " has just created a @red@Tanzanite Helm!");
                } else {
                    player.getPacketSender().sendMessage("@red@You're missing something...");
                }
                if (player.getInventory().contains(12279)) {//add tanz to magma
                    player.getInventory().delete(20999, 1);
                    player.getInventory().delete(12279, 1);
                    player.getInventory().add(12278, 1);
                    player.getInventory().add(21000, 1);
                    //World.sendMessage("<img=10> @red@[INFO]" + player.getUsername() + " has just created a @red@Tanzanite Helm!");
                    player.getPacketSender().sendMessage("@red@You're swap mutagens...");
                } else {
                    player.getPacketSender().sendMessage("@red@You're missing something...");
                }
                break;

            case 21000:
                if (player.getInventory().contains(12926)) {//add magma to BP
                    player.getInventory().delete(21000, 1);
                    player.getInventory().delete(12926, 1);
                    player.getInventory().add(12927, 1);
                    World.sendMessage("<img=10> @red@[INFO]" + player.getUsername() + " has just created a @red@Magma Toxic Blowpipe!");
                } else {
                    player.getPacketSender().sendMessage("@red@You're missing something...");
                }
                if (player.getInventory().contains(12282)) {//add magma to helm
                    player.getInventory().delete(21000, 1);
                    player.getInventory().delete(12282, 1);
                    player.getInventory().add(12279, 1);
                    World.sendMessage("<img=10> @red@[INFO]" + player.getUsername() + " has just created a @red@Magma Helm!");
                } else {
                    player.getPacketSender().sendMessage("@red@You're missing something...");
                }
                /** Swap from tanz to magma **/
                if (player.getInventory().contains(12278)) {//add magma to tanz helm
                    player.getInventory().delete(21000, 1);
                    player.getInventory().delete(12278, 1);
                    player.getInventory().add(12279, 1);
                    player.getInventory().add(20999, 1);
                    //World.sendMessage("<img=10> @red@[INFO]" + player.getUsername() + " has just created a @red@Magma Helm!");
                    player.getPacketSender().sendMessage("@red@You swap mutagens..");
                } else {
                    player.getPacketSender().sendMessage("@red@You're missing something...");
                }
                break;

            case 13279://Overseer's book to make Bludgeon.. Holy shit it works!
                if (player.getInventory().contains(13274)) {
                    if (player.getInventory().contains(13275)) ;
                    {
                        if (player.getInventory().contains(13276)) ;
                        {
                            player.getInventory().delete(13274, 1);
                            player.getInventory().delete(13275, 1);
                            player.getInventory().delete(13276, 1);
                            player.getInventory().delete(13279, 1);
                            player.getInventory().add(13045, 1);
                            World.sendMessage("<img=10> @red@[INFO]" + player.getUsername() + " has just created an @red@Abyssal Bludgeon!");
                            player.getPacketSender().sendMessage("The book's power allows you to fuse the components into an Abyssal Bludgeon.");
                        }
                    }
                } else {
                    player.getPacketSender().sendMessage("@red@You're missing something...'");
                }
                break;

            case 19596://Ancient Plate Set (lg)
                player.getInventory().delete(19596, 1);
                player.getInventory().add(19407, 1).refreshItems();
                player.getInventory().add(19398, 1).refreshItems();
                player.getInventory().add(19401, 1).refreshItems();
                player.getInventory().add(19410, 1).refreshItems();
                break;
            case 19592://Bandos Plate Set (lg)
                player.getInventory().delete(19592, 1);
                player.getInventory().add(19437, 1).refreshItems();
                player.getInventory().add(19428, 1).refreshItems();
                player.getInventory().add(19431, 1).refreshItems();
                player.getInventory().add(19440, 1).refreshItems();
                break;
            case 19588://Armadyl Plate Set (lg)
                player.getInventory().delete(19588, 1);
                player.getInventory().add(19422, 1).refreshItems();
                player.getInventory().add(19413, 1).refreshItems();
                player.getInventory().add(19416, 1).refreshItems();
                player.getInventory().add(19425, 1).refreshItems();
                break;
            case 14529://Dragon Plate Set (lg)
                player.getInventory().delete(14529, 1);
                player.getInventory().add(11335, 1).refreshItems();
                player.getInventory().add(14479, 1).refreshItems();
                player.getInventory().add(4087, 1).refreshItems();
                player.getInventory().add(1187, 1).refreshItems();
                break;
            case 11938://Gilded Set (lg)
                player.getInventory().delete(11938, 1);
                player.getInventory().add(3486, 1).refreshItems();
                player.getInventory().add(3481, 1).refreshItems();
                player.getInventory().add(3483, 1).refreshItems();
                player.getInventory().add(3488, 1).refreshItems();
                break;
            case 11930://Zamorak Set (lg)
                player.getInventory().delete(11930, 1);
                player.getInventory().add(2657, 1).refreshItems();
                player.getInventory().add(2653, 1).refreshItems();
                player.getInventory().add(2655, 1).refreshItems();
                player.getInventory().add(2659, 1).refreshItems();
                break;
            case 11928://Saradomin Set (lg)
                player.getInventory().delete(11928, 1);
                player.getInventory().add(2665, 1).refreshItems();
                player.getInventory().add(2661, 1).refreshItems();
                player.getInventory().add(2663, 1).refreshItems();
                player.getInventory().add(2667, 1).refreshItems();
                break;
            case 11898://Rune G Set (lg)
                player.getInventory().delete(11898, 1);
                player.getInventory().add(2619, 1).refreshItems();
                player.getInventory().add(2615, 1).refreshItems();
                player.getInventory().add(2617, 1).refreshItems();
                player.getInventory().add(2621, 1).refreshItems();
                break;
            case 11894://Rune T Set (lg)
                player.getInventory().delete(11894, 1);
                player.getInventory().add(2627, 1).refreshItems();
                player.getInventory().add(2623, 1).refreshItems();
                player.getInventory().add(2625, 1).refreshItems();
                player.getInventory().add(2629, 1).refreshItems();
                break;
            case 11890://Addy G Set (lg)
                player.getInventory().delete(11890, 1);
                player.getInventory().add(2613, 1).refreshItems();
                player.getInventory().add(2607, 1).refreshItems();
                player.getInventory().add(2609, 1).refreshItems();
                player.getInventory().add(2611, 1).refreshItems();
                break;
            case 11886://Addy T Set (lg)
                player.getInventory().delete(11886, 1);
                player.getInventory().add(2605, 1).refreshItems();
                player.getInventory().add(2599, 1).refreshItems();
                player.getInventory().add(2601, 1).refreshItems();
                player.getInventory().add(2603, 1).refreshItems();
                break;
            case 11878://black T Set (lg)
                player.getInventory().delete(11878, 1);
                player.getInventory().add(2587, 1).refreshItems();
                player.getInventory().add(2583, 1).refreshItems();
                player.getInventory().add(2585, 1).refreshItems();
                player.getInventory().add(2589, 1).refreshItems();
                break;
            /* Armour Sets*/
            case 11834://Adamant Armour Set (lg)
                player.getInventory().delete(11834, 1);
                player.getInventory().add(1161, 1).refreshItems();
                player.getInventory().add(1123, 1).refreshItems();
                player.getInventory().add(1073, 1).refreshItems();
                player.getInventory().add(1199, 1).refreshItems();
                break;
            case 11830://Mithril Armour Set (lg)
                player.getInventory().delete(11830, 1);
                player.getInventory().add(1159, 1).refreshItems();
                player.getInventory().add(1121, 1).refreshItems();
                player.getInventory().add(1071, 1).refreshItems();
                player.getInventory().add(1197, 1).refreshItems();
                break;
            case 11826://black Armour Set (lg)
                player.getInventory().delete(11826, 1);
                player.getInventory().add(1165, 1).refreshItems();
                player.getInventory().add(1125, 1).refreshItems();
                player.getInventory().add(1077, 1).refreshItems();
                player.getInventory().add(1195, 1).refreshItems();
                break;
            case 11822://Steel Armour Set (lg)
                player.getInventory().delete(11822, 1);
                player.getInventory().add(1157, 1).refreshItems();
                player.getInventory().add(1119, 1).refreshItems();
                player.getInventory().add(1069, 1).refreshItems();
                player.getInventory().add(1193, 1).refreshItems();
                break;
            case 11814://Bronze Armour Set (lg)
                player.getInventory().delete(11814, 1);
                player.getInventory().add(1155, 1).refreshItems();
                player.getInventory().add(1117, 1).refreshItems();
                player.getInventory().add(1075, 1).refreshItems();
                player.getInventory().add(1189, 1).refreshItems();
                break;
            case 11818://Iron Armour Set (lg)
                player.getInventory().delete(11818, 1);
                player.getInventory().add(1153, 1).refreshItems();
                player.getInventory().add(1115, 1).refreshItems();
                player.getInventory().add(1067, 1).refreshItems();
                player.getInventory().add(1191, 1).refreshItems();
                break;
            case 11820://Iron Armour Set (sk)
                player.getInventory().delete(11820, 1);
                player.getInventory().add(1153, 1).refreshItems();
                player.getInventory().add(1115, 1).refreshItems();
                player.getInventory().add(1081, 1).refreshItems();
                player.getInventory().add(1191, 1).refreshItems();
                break;
            case 11842://Dragon Armour set (lg)
                player.getInventory().delete(11842, 1);
                player.getInventory().add(1149, 1).refreshItems();
                player.getInventory().add(3140, 1).refreshItems();
                player.getInventory().add(4087, 1).refreshItems();
                player.getInventory().add(1187, 1).refreshItems();
                break;
            case 11844://Dragon Armour set (sk)
                player.getInventory().delete(11844, 1);
                player.getInventory().add(1149, 1).refreshItems();
                player.getInventory().add(3140, 1).refreshItems();
                player.getInventory().add(4585, 1).refreshItems();
                player.getInventory().add(1187, 1).refreshItems();
                break;
            case 11838://Rune armour set (lg)
                player.getInventory().delete(11838, 1);
                player.getInventory().add(1163, 1).refreshItems();
                player.getInventory().add(1127, 1).refreshItems();
                player.getInventory().add(1079, 1).refreshItems();
                player.getInventory().add(1201, 1).refreshItems();
                break;
            case 11840://Rune armour set (sk)
                player.getInventory().delete(11840, 1);
                player.getInventory().add(1163, 1).refreshItems();
                player.getInventory().add(1127, 1).refreshItems();
                player.getInventory().add(1093, 1).refreshItems();
                player.getInventory().add(1201, 1).refreshItems();
                break;
            case 11882://Armour sets
                player.getInventory().delete(11882, 1);
                player.getInventory().add(2595, 1).refreshItems();
                player.getInventory().add(2591, 1).refreshItems();
                player.getInventory().add(2593, 1).refreshItems();
                player.getInventory().add(2597, 1).refreshItems();
                break;
            case 11884:
                player.getInventory().delete(11884, 1);
                player.getInventory().add(2595, 1).refreshItems();
                player.getInventory().add(2591, 1).refreshItems();
                player.getInventory().add(3473, 1).refreshItems();
                player.getInventory().add(2597, 1).refreshItems();
                break;
            case 11906:
                player.getInventory().delete(11906, 1);
                player.getInventory().add(7394, 1).refreshItems();
                player.getInventory().add(7390, 1).refreshItems();
                player.getInventory().add(7386, 1).refreshItems();
                break;
            case 15262:
                if (!player.getClickDelay().elapsed(1000))
                    return;
                player.getInventory().delete(15262, 1);
                player.getInventory().add(18016, 10000).refreshItems();
                player.getClickDelay().reset();
                break;
            case 6:
                DwarfMultiCannon.setupCannon(player);
                break;
        }
    }

    public static void secondAction(Player player, Packet packet) {
        int interfaceId = packet.readLEShortA();
        int slot = packet.readLEShort();
        int itemId = packet.readShortA();
        if (slot < 0 || slot > player.getInventory().capacity())
            return;
        if (player.getInventory().getItems()[slot].getId() != itemId)
            return;
        if (SummoningData.isPouch(player, itemId, 2))
            return;
        switch (itemId) {
            case 6500:
                if (player.getCombatBuilder().isAttacking() || player.getCombatBuilder().isBeingAttacked()) {
                    player.getPacketSender().sendMessage("You cannot configure this right now.");
                    return;
                }
                player.getPacketSender().sendInterfaceRemoval();
                DialogueManager.start(player, 101);
                player.setDialogueActionId(60);
                break;
            case 12926:
            case 12927:
                player.getBlowpipeLoading().handleUnloadBlowpipe();
                break;

            case 11724:
                if (player.getInventory().contains(7774)) {
                    player.getInventory().delete(7774, 250);
                    player.getInventory().delete(11724, 1);
                    player.getInventory().add(897, 1);
                    World.sendMessage("<img=10> <col=008FB2>[Upgrade]" + player.getUsername() + " has just upgraded his @red@Bandos Chestplate@la@ to tier 1!");
                    player.getPacketSender().sendMessage("Your Bandos Items Has been Upgraded");
                } else {
                    player.getPacketSender().sendMessage("@red@You Need 250 Upgrade Tokens To Do This");
                }
                break;
            case 11726:
                if (player.getInventory().contains(7774)) {
                    player.getInventory().delete(7774, 250);
                    player.getInventory().delete(11726, 1);
                    player.getInventory().add(894, 1);
                    World.sendMessage("<img=10> <col=008FB2>[Upgrade]" + player.getUsername() + " has just upgraded his @red@Bandos Tassets@la@ to tier 1!");
                    player.getPacketSender().sendMessage("Your Bandos Items Has been Upgraded");
                } else {
                    player.getPacketSender().sendMessage("@red@You Need 250 Upgrade Tokens To Do This");
                }
                break;
            case 894:
                if (player.getInventory().contains(7774)) {
                    player.getInventory().delete(7774, 500);
                    player.getInventory().delete(894, 1);
                    player.getInventory().add(895, 1);
                    World.sendMessage("<img=10> <col=008FB2>[Upgrade]" + player.getUsername() + " has just upgraded his @red@Bandos Chestplate@la@ to tier 2!");
                    player.getPacketSender().sendMessage("Your Bandos Items Has been Upgraded");
                } else {
                    player.getPacketSender().sendMessage("@red@You Need 500 Upgrade Tokens To Do This");
                }
                break;
            case 895:
                if (player.getInventory().contains(7774)) {
                    player.getInventory().delete(7774, 1000);
                    player.getInventory().delete(895, 1);
                    player.getInventory().add(896, 1);
                    World.sendMessage("<img=10> <col=008FB2>[Upgrade]" + player.getUsername() + " has just upgraded his @red@Bandos Tassets@la@ to tier 3!");
                    player.getPacketSender().sendMessage("Your Bandos Items Has been Upgraded");
                } else {
                    player.getPacketSender().sendMessage("@red@You Need 1000 Upgrade Tokens To Do This");
                }
                break;
            case 897:
                if (player.getInventory().contains(7774)) {
                    player.getInventory().delete(7774, 500);
                    player.getInventory().delete(897, 1);
                    player.getInventory().add(898, 1);
                    World.sendMessage("<img=10> <col=008FB2>[Upgrade]" + player.getUsername() + " has just upgraded his @red@Bandos Chestplate@la@ to tier 2!");
                    player.getPacketSender().sendMessage("Your Bandos Items Has been Upgraded");
                } else {
                    player.getPacketSender().sendMessage("@red@You Need 500 Upgrade Tokens To Do This");
                }
                break;
            case 898:
                if (player.getInventory().contains(7774)) {
                    player.getInventory().delete(7774, 1000);
                    player.getInventory().add(899, 1);
                    player.getInventory().delete(898, 1);
                    World.sendMessage("<img=10> <col=008FB2>[Upgrade]" + player.getUsername() + " has just upgraded his @red@Bandos Chestplate@la@ to tier 3!");
                    player.getPacketSender().sendMessage("Your Bandos Items Has been Upgraded");
                } else {
                    player.getPacketSender().sendMessage("@red@You Need 1000 Upgrade Tokens To Do This");
                }
                break;


            case 1712:
            case 1710:
            case 1708:
            case 1706:
            case 11118:
            case 11120:
            case 11122:
            case 11124:
                JewelryTeleporting.rub(player, itemId);
                break;
            case 1704:
                player.getPacketSender().sendMessage("Your amulet has run out of charges.");
                break;
            case 11126:
                player.getPacketSender().sendMessage("Your bracelet has run out of charges.");
                break;
            case 13281:
            case 13282:
            case 13283:
            case 13284:
            case 13285:
            case 13286:
            case 13287:
            case 13288:
                player.getSlayer().handleSlayerRingTP(itemId);
                break;
            case 5509:
            case 5510:
            case 5512:
                RunecraftingPouches.check(player, RunecraftingPouch.forId(itemId));
                break;
            case 995:
                MoneyPouch.depositMoney(player, player.getInventory().getAmount(995));
                break;
            case 1438:
            case 1448:
            case 1440:
            case 1442:
            case 1444:
            case 1446:
            case 1454:
            case 1452:
            case 1462:
            case 1458:
            case 1456:
            case 1450:
                Runecrafting.handleTalisman(player, itemId);
                break;
        }
    }

    public void thirdClickAction(Player player, Packet packet) {
        int itemId = packet.readShortA();
        int slot = packet.readLEShortA();
        int interfaceId = packet.readLEShortA();
        if (slot < 0 || slot > player.getInventory().capacity())
            return;
        if (player.getInventory().getItems()[slot].getId() != itemId)
            return;
        if (JarData.forJar(itemId) != null) {
            PuroPuro.lootJar(player, new Item(itemId, 1), JarData.forJar(itemId));
            return;
        }
        if (SummoningData.isPouch(player, itemId, 3)) {
            return;
        }
        if (ItemBinding.isBindable(itemId)) {
            ItemBinding.bindItem(player, itemId);
            return;
        }
        switch (itemId) {
            case 14019:
            case 14022:
                player.getPacketSender().sendInterface(60000);
                break;
            case 12926:
            case 12927:
                player.getBlowpipeLoading().handleCheckBlowpipe();
                break;
            case 19670:
                if (player.busy()) {
                    player.getPacketSender().sendMessage("You can not do this right now.");
                    return;
                }
                player.setDialogueActionId(71);
                DialogueManager.start(player, player.getGameMode() == GameMode.NORMAL ? 108 : 109);
                break;
            case 6500:
                CharmingImp.sendConfig(player);
                break;
            case 4155:
                player.getPacketSender().sendInterfaceRemoval();
                DialogueManager.start(player, 103);
                player.setDialogueActionId(65);
                break;
            case 13281:
            case 13282:
            case 13283:
            case 13284:
            case 13285:
            case 13286:
            case 13287:
            case 13288:
                player.getPacketSender().sendInterfaceRemoval();
                player.getPacketSender().sendMessage(player.getSlayer().getSlayerTask() == SlayerTasks.NO_TASK ? ("You do not have a Slayer task.") : ("Your current task is to kill another " + (player.getSlayer().getAmountToSlay()) + " " + Misc.formatText(player.getSlayer().getSlayerTask().toString().toLowerCase().replaceAll("_", " ")) + "s."));
                break;
            case 6570:
                if (player.getInventory().contains(6570) && player.getInventory().getAmount(6529) >= 50000) {
                    player.getInventory().delete(6570, 1).delete(6529, 50000).add(19111, 1);
                    player.getPacketSender().sendMessage("You have upgraded your Fire cape into a TokHaar-Kal cape!");
                } else {
                    player.getPacketSender().sendMessage("You need at least 50.000 Tokkul to upgrade your Fire Cape into a TokHaar-Kal cape.");
                }
                break;
            case 15262:
                if (!player.getClickDelay().elapsed(1300))
                    return;
                int amt = player.getInventory().getAmount(15262);
                if (amt > 0)
                    player.getInventory().delete(15262, amt).add(18016, 10000 * amt);
                player.getClickDelay().reset();
                break;
            case 5509:
            case 5510:
            case 5512:
                RunecraftingPouches.empty(player, RunecraftingPouch.forId(itemId));
                break;
            case 11283: //DFS
                player.getPacketSender().sendMessage("Your Dragonfire shield has " + player.getDfsCharges() + "/20 dragon-fire charges.");
                break;
            case 11613: //dkite
                player.getPacketSender().sendMessage("Your Dragonfire shield has " + player.getDfsCharges() + "/20 dragon-fire charges.");
                break;
        }
    }

    @Override
    public void handleMessage(Player player, Packet packet) {
        if (player.getConstitution() <= 0)
            return;
        switch (packet.getOpcode()) {
            case SECOND_ITEM_ACTION_OPCODE:
                secondAction(player, packet);
                break;
            case FIRST_ITEM_ACTION_OPCODE:
                firstAction(player, packet);
                break;
            case THIRD_ITEM_ACTION_OPCODE:
                thirdClickAction(player, packet);
                break;
        }
    }

    public static final int SECOND_ITEM_ACTION_OPCODE = 75;

    public static final int FIRST_ITEM_ACTION_OPCODE = 122;

    public static final int THIRD_ITEM_ACTION_OPCODE = 16;

}