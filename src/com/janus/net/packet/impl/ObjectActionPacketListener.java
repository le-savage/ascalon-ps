package com.janus.net.packet.impl;

import com.janus.GameSettings;
import com.janus.engine.task.Task;
import com.janus.engine.task.TaskManager;
import com.janus.engine.task.impl.WalkToTask;
import com.janus.engine.task.impl.WalkToTask.FinalizedMovementTask;
import com.janus.model.*;
import com.janus.model.Locations.Location;
import com.janus.model.container.impl.Bank;
import com.janus.model.container.impl.Equipment;
import com.janus.model.definitions.GameObjectDefinition;
import com.janus.model.input.impl.DonateToWell;
import com.janus.model.input.impl.EnterAmountOfLogsToAdd;
import com.janus.net.packet.Packet;
import com.janus.net.packet.PacketListener;
import com.janus.util.Misc;
import com.janus.util.RandomUtility;
import com.janus.world.World;
import com.janus.world.clip.region.RegionClipping;
import com.janus.world.content.*;
import com.janus.world.content.combat.instancearena.InstanceArena;
import com.janus.world.content.combat.magic.Autocasting;
import com.janus.world.content.combat.prayer.CurseHandler;
import com.janus.world.content.combat.prayer.PrayerHandler;
import com.janus.world.content.combat.range.DwarfMultiCannon;
import com.janus.world.content.combat.tieredbosses.BossMinigameFunctions;
import com.janus.world.content.combat.tieredbosses.BossRewardChest;
import com.janus.world.content.combat.weapon.CombatSpecial;
import com.janus.world.content.dialogue.DialogueManager;
import com.janus.world.content.minigames.impl.*;
import com.janus.world.content.minigames.impl.Dueling.DuelRule;
import com.janus.world.content.skill.impl.AfkSkilling;
import com.janus.world.content.skill.impl.agility.Agility;
import com.janus.world.content.skill.impl.construction.Construction;
import com.janus.world.content.skill.impl.crafting.Flax;
import com.janus.world.content.skill.impl.crafting.Jewellery;
import com.janus.world.content.skill.impl.dungeoneering.Dungeoneering;
import com.janus.world.content.skill.impl.fishing.Fishing;
import com.janus.world.content.skill.impl.fishing.Fishing.Spot;
import com.janus.world.content.skill.impl.hunter.Hunter;
import com.janus.world.content.skill.impl.hunter.PuroPuro;
import com.janus.world.content.skill.impl.mining.Mining;
import com.janus.world.content.skill.impl.mining.MiningData;
import com.janus.world.content.skill.impl.mining.Prospecting;
import com.janus.world.content.skill.impl.runecrafting.Runecrafting;
import com.janus.world.content.skill.impl.runecrafting.RunecraftingData;
import com.janus.world.content.skill.impl.smithing.EquipmentMaking;
import com.janus.world.content.skill.impl.smithing.Smelting;
import com.janus.world.content.skill.impl.thieving.Stalls;
import com.janus.world.content.skill.impl.woodcutting.Woodcutting;
import com.janus.world.content.skill.impl.woodcutting.WoodcuttingData;
import com.janus.world.content.skill.impl.woodcutting.WoodcuttingData.Hatchet;
import com.janus.world.content.transportation.TeleportHandler;
import com.janus.world.content.transportation.TeleportType;
import com.janus.world.entity.impl.player.Player;


/**
 * This packet listener is called when a player clicked
 * on a game object.
 *
 * @author relex lawl
 */

public class ObjectActionPacketListener implements PacketListener {

    public static final int FIRST_CLICK = 132, SECOND_CLICK = 252, THIRD_CLICK = 70, FOURTH_CLICK = 234, FIFTH_CLICK = 228;

    private static void secondClick(final Player player, Packet packet) {
        final int id = packet.readLEShortA();
        final int y = packet.readLEShort();
        final int x = packet.readUnsignedShortA();
        final Position position = new Position(x, y, player.getPosition().getZ());
        final GameObject gameObject = new GameObject(id, position);
        if (id > 0 && id != 6 && !RegionClipping.objectExists(gameObject)) {
            //player.getPacketSender().sendMessage("An error occured. Error code: "+id).sendMessage("Please report the error to a staff member.");
            return;
        }
        if ((player.getRights() == PlayerRights.DEVELOPER) || (player.getRights() == PlayerRights.OWNER))
            player.getPacketSender().sendMessage("Second click object id; [id, position] : [" + id + ", " + position.toString() + "]");
        player.setPositionToFace(gameObject.getPosition());
        int distanceX = (player.getPosition().getX() - position.getX());
        int distanceY = (player.getPosition().getY() - position.getY());
        if (distanceX < 0)
            distanceX = -(distanceX);
        if (distanceY < 0)
            distanceY = -(distanceY);
        int size = distanceX > distanceY ? distanceX : distanceY;
        gameObject.setSize(size);
        player.setInteractingObject(gameObject).setWalkToTask(new WalkToTask(player, position, gameObject.getSize(), new FinalizedMovementTask() {
            public void execute() {
                if (MiningData.forRock(gameObject.getId()) != null) {
                    Prospecting.prospectOre(player, id);
                    return;
                }
                if (player.getFarming().click(player, x, y, 1))
                    return;
                switch (gameObject.getId()) {

                    case 6189:
                        Smelting.openInterface(player);
                        break;


                    case -16000://afk firemaking
                        player.getPacketSender().sendRichPresenceState("AFK Firemaking");
                        player.getPacketSender().sendSmallImageKey("firemaking");
                        player.getPacketSender().sendRichPresenceSmallPictureText("Lvl: " + player.getSkillManager().getCurrentLevel(Skill.FIREMAKING));
                        AfkSkilling.afkSkilling(player, 99, 250, 11, 733);
                        break;

                    case -28577://afk magic
                        player.getPacketSender().sendRichPresenceState("AFK Magic");
                        player.getPacketSender().sendSmallImageKey("magic");
                        player.getPacketSender().sendRichPresenceSmallPictureText("Lvl: " + player.getSkillManager().getCurrentLevel(Skill.MAGIC));
                        if (player.getEquipment().getFreeSlots() != player.getEquipment().capacity()) {
                            player.getPacketSender().sendMessage("Please unequip all your items first.");
                            return;
                        } else {
                            AfkSkilling.afkSkilling(player, 99, 250, 6, 716);
                        }
                        break;

                    case -16014://afk cooking
                        player.getPacketSender().sendRichPresenceState("AFK Cooking");
                        player.getPacketSender().sendSmallImageKey("cooking");
                        player.getPacketSender().sendRichPresenceSmallPictureText("Lvl: " + player.getSkillManager().getCurrentLevel(Skill.COOKING));
                        AfkSkilling.afkSkilling(player, 99, 250, 7, 896);
                        break;

                    case 884:
                        player.setDialogueActionId(41);
                        player.setInputHandling(new DonateToWell());
                        player.getPacketSender().sendInterfaceRemoval().sendEnterAmountPrompt("How much money would you like to contribute with?");
                        break;
                    case 21505:
                    case 21507:
                        player.moveTo(new Position(2328, 3804));
                        break;
                    case 2646:
                    case 312:
                        if (!player.getClickDelay().elapsed(1200))
                            return;
                        if (player.getInventory().isFull()) {
                            player.getPacketSender().sendMessage("You don't have enough free inventory space.");
                            return;
                        }
                        String type = gameObject.getId() == 312 ? "Potato" : "Flax";
                        player.performAnimation(new Animation(827));
                        player.getInventory().add(gameObject.getId() == 312 ? 1942 : 1779, 1);
                        player.getPacketSender().sendMessage("You pick some " + type + "..");
                        gameObject.setPickAmount(gameObject.getPickAmount() + 1);
                        if (RandomUtility.getRandom(3) == 1 && gameObject.getPickAmount() >= 1 || gameObject.getPickAmount() >= 6) {
                            player.getPacketSender().sendClientRightClickRemoval();
                            gameObject.setPickAmount(0);
                            CustomObjects.globalObjectRespawnTask(new GameObject(-1, gameObject.getPosition()), gameObject, 10);
                        }
                        player.getClickDelay().reset();
                        break;
                    case 2644:
                        Flax.showSpinInterface(player);
                        break;
                    case 6:
                        DwarfCannon cannon = player.getCannon();
                        if (cannon == null || cannon.getOwnerIndex() != player.getIndex()) {
                            player.getPacketSender().sendMessage("This is not your cannon!");
                        } else {
                            DwarfMultiCannon.pickupCannon(player, cannon, false);
                        }
                        break;

                    case 4875:
                        Stalls.stealFromStall(player, 1, 5100, 18199, "You steal a banana.");
                        break;
                    case 4874:
                        Stalls.stealFromStall(player, 30, 6130, 15009, "You steal a golden ring.");
                        break;
                    case 4876:
                        Stalls.stealFromStall(player, 60, 7370, 17401, "You steal a damaged hammer.");
                        break;
                    case 4877:
                        Stalls.stealFromStall(player, 65, 7990, 1389, "You steal a staff.");
                        break;
                    case 4878:
                        Stalls.stealFromStall(player, 80, 9230, 11998, "You steal a scimitar.");
                        break;
                    case 2152:
                    case 22823:
                        player.performAnimation(new Animation(8502));
                        player.performGraphic(new Graphic(1308));
                        player.getSkillManager().setCurrentLevel(Skill.SUMMONING, player.getSkillManager().getMaxLevel(Skill.SUMMONING));
                        player.getPacketSender().sendMessage("You renew your Summoning points.");
                        break;
                }
            }
        }));
    }

    private static void thirdClick(final Player player, Packet packet) {
        final int id = packet.readUnsignedShortA();
        final int y = packet.readUnsignedShortA();
        final int x = packet.readShort();
        final Position position = new Position(x, y, player.getPosition().getZ());
        final GameObject gameObject = new GameObject(id, position);
        if ((player.getRights() == PlayerRights.DEVELOPER) || (player.getRights() == PlayerRights.OWNER))
            player.getPacketSender().sendMessage("Third click object id; [id, position] : [" + id + ", " + position.toString() + "]");
    }


    private static void fourthClick(Player player, Packet packet) {
        final int id = packet.readUnsignedShortA();
        final int y = packet.readUnsignedShortA();
        final int x = packet.readShort();
        final Position position = new Position(x, y, player.getPosition().getZ());
        final GameObject gameObject = new GameObject(id, position);
        if ((player.getRights() == PlayerRights.DEVELOPER) || (player.getRights() == PlayerRights.OWNER))
            player.getPacketSender().sendMessage("Fourth click object id; [id, position] : [" + id + ", " + position.toString() + "]");
    }

    private static void fifthClick(final Player player, Packet packet) {
        final int id = packet.readUnsignedShortA();
        final int y = packet.readUnsignedShortA();
        final int x = packet.readShort();
        final Position position = new Position(x, y, player.getPosition().getZ());
        final GameObject gameObject = new GameObject(id, position);
        if ((player.getRights() == PlayerRights.DEVELOPER) || (player.getRights() == PlayerRights.OWNER))
            player.getPacketSender().sendMessage("Fith click object id; [id, position] : [" + id + ", " + position.toString() + "]");
        if (!Construction.buildingHouse(player)) {
            if (id > 0 && !RegionClipping.objectExists(gameObject)) {
                //player.getPacketSender().sendMessage("An error occured. Error code: "+id).sendMessage("Please report the error to a staff member.");
                return;
            }
        }
        player.setPositionToFace(gameObject.getPosition());
        int distanceX = (player.getPosition().getX() - position.getX());
        int distanceY = (player.getPosition().getY() - position.getY());
        if (distanceX < 0)
            distanceX = -(distanceX);
        if (distanceY < 0)
            distanceY = -(distanceY);
        int size = distanceX > distanceY ? distanceX : distanceY;
        gameObject.setSize(size);
        player.setInteractingObject(gameObject);
        player.setWalkToTask(new WalkToTask(player, position, gameObject.getSize(), new FinalizedMovementTask() {
            @Override
            public void execute() {
                switch (id) {
                }
                Construction.handleFifthObjectClick(x, y, id, player);
            }
        }));
    }

    @Override
    public void handleMessage(Player player, Packet packet) {
        if (player.isTeleporting() || player.isPlayerLocked() || player.getMovementQueue().isLockMovement())
            return;
        switch (packet.getOpcode()) {
            case FIRST_CLICK:
                firstClick(player, packet);
                break;
            case SECOND_CLICK:
                secondClick(player, packet);
                break;
            case THIRD_CLICK:
                //thirdClick(player, packet);
                break;
            case FOURTH_CLICK:
                //fourthClick(player, packet);
                break;
            case FIFTH_CLICK:
                fifthClick(player, packet);
                break;
        }
    }

    /**
     * The PacketListener logger to debug information and print out errors.
     */
    //private final static Logger logger = Logger.getLogger(ObjectActionPacketListener.class);
    private static void firstClick(final Player player, Packet packet) {
        final int x = packet.readLEShortA();
        final int id = packet.readUnsignedShort();
        final int y = packet.readUnsignedShortA();
        final Position position = new Position(x, y, player.getPosition().getZ());
        final GameObject gameObject = new GameObject(id, position);
        if (player.getLocation() != Location.CONSTRUCTION) {
            if (id > 0 && id != 6 && !RegionClipping.objectExists(gameObject) && id != 9294) {
                //	player.getPacketSender().sendMessage("An error occured. Error code: "+id).sendMessage("Please report the error to a staff member.");
                return;
            }
        }
        int distanceX = (player.getPosition().getX() - position.getX());
        int distanceY = (player.getPosition().getY() - position.getY());
        if (distanceX < 0)
            distanceX = -(distanceX);
        if (distanceY < 0)
            distanceY = -(distanceY);
        int size = distanceX > distanceY ? GameObjectDefinition.forId(id).getSizeX() : GameObjectDefinition.forId(id).getSizeY();
        if (size <= 0)
            size = 1;
        gameObject.setSize(size);
        if (player.getMovementQueue().isLockMovement())
            return;
        if ((player.getRights() == PlayerRights.DEVELOPER) || (player.getRights() == PlayerRights.OWNER))
            player.getPacketSender().sendMessage("First click object id; [id, position] : [" + id + ", " + position.toString() + "]");
        player.setInteractingObject(gameObject).setWalkToTask(new WalkToTask(player, position, gameObject.getSize(), new FinalizedMovementTask() {
            @Override
            public void execute() {
                player.setPositionToFace(gameObject.getPosition());


                if (player.getRegionInstance() != null) {
                    Construction.handleFifthObjectClick(x, y, id, player);
                }
                if (WoodcuttingData.Trees.forId(id) != null) {
                    Woodcutting.cutWood(player, gameObject, false);
                    return;
                }
                if (MiningData.forRock(gameObject.getId()) != null) {
                    Mining.startMining(player, gameObject);
                    return;
                }
                if (player.getFarming().click(player, x, y, 1))
                    return;
                if (Runecrafting.runecraftingAltar(player, gameObject.getId())) {
                    RunecraftingData.RuneData rune = RunecraftingData.RuneData.forId(gameObject.getId());
                    if (rune == null)
                        return;
                    Runecrafting.craftRunes(player, rune);
                    return;
                }
                if (Agility.handleObject(player, gameObject)) {
                    return;
                }
                if (Barrows.handleObject(player, gameObject)) {
                    return;
                }
                if (player.getLocation() == Location.WILDERNESS && WildernessObelisks.handleObelisk(gameObject.getId())) {
                    return;
                }
                if (id == BossMinigameFunctions.ENTRY_DOOR_ID) {
                    BossMinigameFunctions.handleDoor(player);
                }
                if (id == BossMinigameFunctions.EXIT_CAVE_ID) {
                    BossMinigameFunctions.handleExit(player);
                }
                /** Here we handle opening the reward chest at ::boss **/
                if (id == BossRewardChest.rewardChestID) {
                    BossRewardChest.clickChest(player);
                }
                switch (id) {

                    case 24600: //Instance Barrier Exit
                        if (player.getLocation() == Location.INSTANCE_ARENA && player.getRegionInstance() == null) {
                            player.moveTo(InstanceArena.ENTRANCE);
                        }
                        InstanceArena.destructArena(player);
                        break;

                    case 38144:
                        if (!player.getClickDelay().elapsed(10000))
                            return;
                        InstanceArena.handleInstance(player, gameObject);
                        break;

                    case 13132:
                        player.getPacketSender().sendMessage("There's no escape!");
                        break;


                    case 54259://afk smithing
                        AfkSkilling.afkSkilling(player, 99, 250, 13, 898);
                        player.getPacketSender().sendRichPresenceState("AFK Smithing");
                        player.getPacketSender().sendSmallImageKey("smithing");
                        player.getPacketSender().sendRichPresenceSmallPictureText("Lvl: " + player.getSkillManager().getCurrentLevel(Skill.SMITHING));
                        break;

                    case 19336://afk hunter
                        player.getPacketSender().sendRichPresenceState("AFK Hunter");
                        player.getPacketSender().sendSmallImageKey("hunter");
                        player.getPacketSender().sendRichPresenceSmallPictureText("Lvl: " + player.getSkillManager().getCurrentLevel(Skill.HUNTER));
                        AfkSkilling.afkSkilling(player, 99, 250, 22, 827);
                        break;

                    case 49536://afk fletching
                        player.getPacketSender().sendRichPresenceState("AFK Fletching");
                        player.getPacketSender().sendSmallImageKey("fletching");
                        player.getPacketSender().sendRichPresenceSmallPictureText("Lvl: " + player.getSkillManager().getCurrentLevel(Skill.FLETCHING));
                        AfkSkilling.afkSkilling(player, 99, 250, 9, 1248);
                        break;

                    case 5896://afk rock mining
                        player.getPacketSender().sendRichPresenceState("AFK Mining");
                        player.getPacketSender().sendSmallImageKey("mining");
                        player.getPacketSender().sendRichPresenceSmallPictureText("Lvl: " + player.getSkillManager().getCurrentLevel(Skill.MINING));
                        AfkSkilling.afkSkilling(player, 99, 250, 14, 10226);
                        break;

                    case 2023://afk tree
                        player.getPacketSender().sendRichPresenceState("AFK Woodcutting");
                        player.getPacketSender().sendSmallImageKey("woodcutting");
                        player.getPacketSender().sendRichPresenceSmallPictureText("Lvl: " + player.getSkillManager().getCurrentLevel(Skill.WOODCUTTING));
                        AfkSkilling.afkSkilling(player, 99, 250, 8, 10227);
                        break;
                    case 49522://afk fishing
                        player.getPacketSender().sendRichPresenceState("AFK Fishing");
                        player.getPacketSender().sendSmallImageKey("fishing");
                        player.getPacketSender().sendRichPresenceSmallPictureText("Lvl: " + player.getSkillManager().getCurrentLevel(Skill.FISHING));
                        AfkSkilling.afkSkilling(player, 99, 250, 10, 623);
                        break;

                    case 36959://afk melee TODO Check if removing the fightstyle check is still ok
                            AfkSkilling.afkCombat(player, 99, 250, 0);
                        break;

                    case 9391:
                        player.getPacketSender().sendInterface(3200);
                        break;


                    case 11601:
                        Jewellery.jewelleryInterface(player);
                        break;

                    case 38828:
                        EnterAmountOfLogsToAdd.openInterface(player);
                        break;
                    case 38660:
                        if (ShootingStar.CRASHED_STAR != null) {
                            player.getPacketSender().sendRichPresenceState("Mining Crashed Star");
                            player.getPacketSender().sendSmallImageKey("mining");
                            player.getPacketSender().sendRichPresenceSmallPictureText("Lvl: " + player.getSkillManager().getCurrentLevel(Skill.MINING));
                        }
                        break;
                    case 11434:
                        if (EvilTrees.SPAWNED_TREE != null) {
                            player.getPacketSender().sendRichPresenceState("Chopping Evil Tree!");
                            player.getPacketSender().sendSmallImageKey("woodcutting");
                            player.getPacketSender().sendRichPresenceSmallPictureText("Lvl: " + player.getSkillManager().getCurrentLevel(Skill.WOODCUTTING));

                        }
                        break;

                    case 56859:
                        player.getPacketSender().sendRichPresenceState("AFK Thieving!");
                        player.getPacketSender().sendSmallImageKey("thieving");
                        player.getPacketSender().sendRichPresenceSmallPictureText("Lvl: " + player.getSkillManager().getCurrentLevel(Skill.THIEVING));
                        AfkSkilling.afkSkilling(player, 99, 250, 17, 881);
                        break;


                    case 6045://Mine Carts
                        Bank.depositItems(player, player.getInventory(), true);
                        break;
                    case 2079:
                        TrioBosses.openChest(player);
                        break;
                    case 2465:
                        player.inFFALobby = false;
                        player.moveTo(new Position(3208, 3426, 0));
                        FreeForAll.removePlayer(player);
                        return;

                    case 5259:
                        if (player.getPosition().getX() >= 3653) {
                            player.moveTo(new Position(3652, player.getPosition().getY()));
                        } else {
                            player.setDialogueActionId(73);
                            DialogueManager.start(player, 115);
                        }
                        break;

                    case 13405:
                        if (!TeleportHandler.checkReqs(player, null)) {
                            return;
                        }
                        if (!player.getClickDelay().elapsed(4500) || player.getMovementQueue().isLockMovement()) {
                            return;
                        }
                        if (player.getLocation() == Location.CONSTRUCTION) {
                            player.moveTo(GameSettings.DEFAULT_POSITION.copy());
                            return;
                        }
                        Construction.newHouse(player);
                        Construction.enterHouse(player, player, true, true);
                        player.getPacketSender().sendMessage("@red@If your construction area map bugs out, teleport home and back in!");
                        break;
				/*case 8799:
			        GrandExchange.open(player);
			        break;*/

                    /*** Teleports at home ***/
                    case 13615: // VARROCK
                        player.moveTo(new Position(3209, 3429, player.getPosition().getZ()));
                        break;
                    case 13616: // LUMBRIDGE
                        player.moveTo(new Position(3222, 3218, player.getPosition().getZ()));
                        break;
                    case 13617: // FALADOR
                        player.moveTo(new Position(2964, 3378, player.getPosition().getZ()));
                        break;
                    case 13618: // CAMELOT
                        player.sendMessage("Ew - You Camelot");
                        player.moveTo(new Position(2737, 3482, player.getPosition().getZ()));
                        break;
                    case 13620: // YANILLE
                        player.moveTo(new Position(2606, 3093, player.getPosition().getZ()));
                        break;
                    case 13626: // ARDOUGNE
                        player.moveTo(new Position(2662, 3305, player.getPosition().getZ()));
                        break;


                    case 28779: // Chaos Tunnels - FUCK MY LIFE
                        if (gameObject.getPosition().getX() == 3142 && gameObject.getPosition().getY() == 5545) { // 1 to bork
                            player.moveTo(new Position(3115, 5525, 0));//east
                        } else if (gameObject.getPosition().getX() == 3147 && gameObject.getPosition().getY() == 5541) { // 1 -> 6
                            player.moveTo(new Position(3143, 5535, 0));
                        } else if (gameObject.getPosition().getX() == 3143 && gameObject.getPosition().getY() == 5535) {
                            player.moveTo(new Position(3147, 5541, 0));
                        } else if (gameObject.getPosition().getX() == 3158 && gameObject.getPosition().getY() == 5561) { // 1 -> 2
                            player.moveTo(new Position(3162, 5557, 0));
                        } else if (gameObject.getPosition().getX() == 3162 && gameObject.getPosition().getY() == 5557) {
                            player.moveTo(new Position(3158, 5561, 0));
                        } else if (gameObject.getPosition().getX() == 3174 && gameObject.getPosition().getY() == 5558) { // 2 -> 3
                            player.moveTo(new Position(3180, 5557, 0));
                        } else if (gameObject.getPosition().getX() == 3180 && gameObject.getPosition().getY() == 5557) {
                            player.moveTo(new Position(3174, 5558, 0));
                        } else if (gameObject.getPosition().getX() == 3190 && gameObject.getPosition().getY() == 5554) { // 3 -> 4
                            player.moveTo(new Position(3190, 5549, 0));
                        } else if (gameObject.getPosition().getX() == 3190 && gameObject.getPosition().getY() == 5549) { // 3 -> 4
                            player.moveTo(new Position(3190, 5554, 0));
                        } else if (gameObject.getPosition().getX() == 3171 && gameObject.getPosition().getY() == 5542) { // 4 -> 5
                            player.moveTo(new Position(3168, 5541, 0));
                        } else if (gameObject.getPosition().getX() == 3168 && gameObject.getPosition().getY() == 5541) { // 4 -> 5
                            player.moveTo(new Position(3171, 5542, 0));
                        } else if (gameObject.getPosition().getX() == 3153 && gameObject.getPosition().getY() == 5537) { // 5 -> 6
                            player.moveTo(new Position(3148, 5533, 0));
                        } else if (gameObject.getPosition().getX() == 3148 && gameObject.getPosition().getY() == 5533) { // 5 -> 6
                            player.moveTo(new Position(3153, 5537, 0));
                        } else if (gameObject.getPosition().getX() == 3152 && gameObject.getPosition().getY() == 5520) { // 6 -> 7
                            player.moveTo(new Position(3156, 5523, 0));
                        } else if (gameObject.getPosition().getX() == 3156 && gameObject.getPosition().getY() == 5523) { // 6 -> 7
                            player.moveTo(new Position(3152, 5520, 0));
                        } else if (gameObject.getPosition().getX() == 3173 && gameObject.getPosition().getY() == 5530) { // 7 -> 8
                            player.moveTo(new Position(3165, 5515, 0));
                        } else if (gameObject.getPosition().getX() == 3165 && gameObject.getPosition().getY() == 5515) { // 7 -> 8
                            player.moveTo(new Position(3173, 5530, 0));
                        } else if (gameObject.getPosition().getX() == 3181 && gameObject.getPosition().getY() == 5517) { // dead portal 8
                            player.sendMessage("Hmm.. I should find another way..");
                        } else if (gameObject.getPosition().getX() == 3182 && gameObject.getPosition().getY() == 5530) { //8 -> 9
                            player.moveTo(new Position(3187, 5531, 0));
                        } else if (gameObject.getPosition().getX() == 3187 && gameObject.getPosition().getY() == 5531) { //8 -> 9
                            player.moveTo(new Position(3182, 5530, 0));
                        } else if (gameObject.getPosition().getX() == 3190 && gameObject.getPosition().getY() == 5519) { // 9 -> 10
                            player.moveTo(new Position(3185, 5518, 0));
                        } else if (gameObject.getPosition().getX() == 3185 && gameObject.getPosition().getY() == 5518) { // 9 -> 10
                            player.moveTo(new Position(3190, 5519, 0));
                        } else if (gameObject.getPosition().getX() == 3190 && gameObject.getPosition().getY() == 5515) { // dead portal 10
                            player.sendMessage("Hmm.. I should find another way..");
                        } else if (gameObject.getPosition().getX() == 3196 && gameObject.getPosition().getY() == 5512) { // dead portal 10
                            player.sendMessage("Hmm.. I should find another way..");
                        } else if (gameObject.getPosition().getX() == 3202 && gameObject.getPosition().getY() == 5515) { // dead portal 11
                            player.sendMessage("Hmm.. I should find another way..");
                        } else if (gameObject.getPosition().getX() == 3211 && gameObject.getPosition().getY() == 5523) { // 11 -> 12
                            player.moveTo(new Position(3208, 5527, 0));
                        } else if (gameObject.getPosition().getX() == 3208 && gameObject.getPosition().getY() == 5527) { // 11 -> 12
                            player.moveTo(new Position(3211, 5523, 0));
                        } else if (gameObject.getPosition().getX() == 3201 && gameObject.getPosition().getY() == 5531) { // dead portal 12
                            player.sendMessage("Hmm.. I should find another way..");
                        } else if (gameObject.getPosition().getX() == 3197 && gameObject.getPosition().getY() == 5529) { // dead portal 12
                            player.sendMessage("Hmm.. I should find another way..");
                        } else if (gameObject.getPosition().getX() == 3211 && gameObject.getPosition().getY() == 5533) { // 12 -> 14
                            player.moveTo(new Position(3214, 5533, 0));
                        } else if (gameObject.getPosition().getX() == 3214 && gameObject.getPosition().getY() == 5533) { // 12 -> 14
                            player.moveTo(new Position(3211, 5533, 0));
                        } else if (gameObject.getPosition().getX() == 3204 && gameObject.getPosition().getY() == 5546) { // 12 -> 13
                            player.moveTo(new Position(3206, 5553, 0));
                        } else if (gameObject.getPosition().getX() == 3206 && gameObject.getPosition().getY() == 5553) { // 12 -> 13
                            player.moveTo(new Position(3204, 5546, 0));
                        } else if (gameObject.getPosition().getX() == 3226 && gameObject.getPosition().getY() == 5553) { // 13 -> 14
                            player.moveTo(new Position(3230, 5547, 0));
                        } else if (gameObject.getPosition().getX() == 3230 && gameObject.getPosition().getY() == 5547) { // 13 -> 14
                            player.moveTo(new Position(3226, 5553, 0));
                        } else if (gameObject.getPosition().getX() == 3238 && gameObject.getPosition().getY() == 5507) { // 14 -> 41
                            player.moveTo(new Position(3239, 5498, 0));
                        } else if (gameObject.getPosition().getX() == 3239 && gameObject.getPosition().getY() == 5498) { // 14 -> 41
                            player.moveTo(new Position(3238, 5507, 0));
                        } else if (gameObject.getPosition().getX() == 3241 && gameObject.getPosition().getY() == 5529) { // 14 -> 15
                            player.moveTo(new Position(3243, 5526, 0));
                        } else if (gameObject.getPosition().getX() == 3243 && gameObject.getPosition().getY() == 5526) { // 14 -> 15
                            player.moveTo(new Position(3241, 5529, 0));
                        } else if (gameObject.getPosition().getX() == 3252 && gameObject.getPosition().getY() == 5543) { // 15 -> 16
                            player.moveTo(new Position(3249, 5546, 0));
                        } else if (gameObject.getPosition().getX() == 3249 && gameObject.getPosition().getY() == 5546) { // 15 -> 16
                            player.moveTo(new Position(3252, 5543, 0));
                        } else if (gameObject.getPosition().getX() == 3261 && gameObject.getPosition().getY() == 5536) { // 15 -> 19
                            player.moveTo(new Position(3268, 5534, 0));
                        } else if (gameObject.getPosition().getX() == 3268 && gameObject.getPosition().getY() == 5534) { // 15 -> 19
                            player.moveTo(new Position(3261, 5536, 0));
                        } else if (gameObject.getPosition().getX() == 3253 && gameObject.getPosition().getY() == 5561) { // 16 -> 17
                            player.moveTo(new Position(3256, 5561, 0));
                        } else if (gameObject.getPosition().getX() == 3256 && gameObject.getPosition().getY() == 5561) { // 16 -> 17
                            player.moveTo(new Position(3253, 5561, 0));
                        } else if (gameObject.getPosition().getX() == 3262 && gameObject.getPosition().getY() == 5552) { //17 -> 18
                            player.moveTo(new Position(3266, 5552, 0));
                        } else if (gameObject.getPosition().getX() == 3266 && gameObject.getPosition().getY() == 5552) { //17 -> 18
                            player.moveTo(new Position(3262, 5552, 0));
                        } else if (gameObject.getPosition().getX() == 3285 && gameObject.getPosition().getY() == 5556) { //18 -> 21
                            player.moveTo(new Position(3291, 5555, 0));
                        } else if (gameObject.getPosition().getX() == 3291 && gameObject.getPosition().getY() == 5555) { //18 -> 21
                            player.moveTo(new Position(3285, 5556, 0));
                        } else if (gameObject.getPosition().getX() == 3288 && gameObject.getPosition().getY() == 5536) { //18 -> 20
                            player.moveTo(new Position(3289, 5533, 0));
                        } else if (gameObject.getPosition().getX() == 3289 && gameObject.getPosition().getY() == 5533) { //18 -> 20
                            player.moveTo(new Position(3288, 5536, 0));
                        } else if (gameObject.getPosition().getX() == 3282 && gameObject.getPosition().getY() == 5531) { //19 -> 20
                            player.moveTo(new Position(3285, 5527, 0));
                        } else if (gameObject.getPosition().getX() == 3285 && gameObject.getPosition().getY() == 5527) { //19 -> 20
                            player.moveTo(new Position(3282, 5531, 0));
                        } else if (gameObject.getPosition().getX() == 3285 && gameObject.getPosition().getY() == 5508) { //20 -> 42
                            player.moveTo(new Position(3280, 5501, 0));
                        } else if (gameObject.getPosition().getX() == 3280 && gameObject.getPosition().getY() == 5501) { //20 -> 42
                            player.moveTo(new Position(3285, 5508, 0));
                        } else if (gameObject.getPosition().getX() == 3300 && gameObject.getPosition().getY() == 5514) { //20 -> 23
                            player.moveTo(new Position(3297, 5510, 0));
                        } else if (gameObject.getPosition().getX() == 3297 && gameObject.getPosition().getY() == 5510) { //20 -> 23
                            player.moveTo(new Position(3300, 5514, 0));
                        } else if (gameObject.getPosition().getX() == 3297 && gameObject.getPosition().getY() == 5536) { //21 -> 22
                            player.moveTo(new Position(3299, 5533, 0));
                        } else if (gameObject.getPosition().getX() == 3299 && gameObject.getPosition().getY() == 5533) { //21 -> 22
                            player.moveTo(new Position(3297, 5536, 0));
                        } else if (gameObject.getPosition().getX() == 3321 && gameObject.getPosition().getY() == 5554) { //21 -> 22
                            player.moveTo(new Position(3315, 5552, 0));
                        } else if (gameObject.getPosition().getX() == 3315 && gameObject.getPosition().getY() == 5552) { //21 -> 22
                            player.moveTo(new Position(3321, 5554, 0));
                        } else if (gameObject.getPosition().getX() == 3323 && gameObject.getPosition().getY() == 5531) { //22 -> 23
                            player.moveTo(new Position(3325, 5518, 0));
                        } else if (gameObject.getPosition().getX() == 3325 && gameObject.getPosition().getY() == 5518) { //22 -> 23
                            player.moveTo(new Position(3323, 5531, 0));
                        } else if (gameObject.getPosition().getX() == 3169 && gameObject.getPosition().getY() == 5510) { // 8 -> 24
                            player.moveTo(new Position(3159, 5501, 0));
                        } else if (gameObject.getPosition().getX() == 3159 && gameObject.getPosition().getY() == 5501) { // 8 -> 24
                            player.moveTo(new Position(3169, 5510, 0));
                        } else if (gameObject.getPosition().getX() == 3142 && gameObject.getPosition().getY() == 5489) { // 24 -> 25
                            player.moveTo(new Position(3141, 5480, 0));
                        } else if (gameObject.getPosition().getX() == 3141 && gameObject.getPosition().getY() == 5480) { // 24 -> 25
                            player.moveTo(new Position(3142, 5489, 0));
                        } else if (gameObject.getPosition().getX() == 3167 && gameObject.getPosition().getY() == 5478) { // 25 -> 30
                            player.moveTo(new Position(3171, 5478, 0));
                        } else if (gameObject.getPosition().getX() == 3171 && gameObject.getPosition().getY() == 5478) { // 25 -> 30
                            player.moveTo(new Position(3167, 5478, 0));
                        } else if (gameObject.getPosition().getX() == 3142 && gameObject.getPosition().getY() == 5462) { // 25 -> 26
                            player.moveTo(new Position(3154, 5462, 0));
                        } else if (gameObject.getPosition().getX() == 3154 && gameObject.getPosition().getY() == 5462) { // 25 -> 26
                            player.moveTo(new Position(3142, 5462, 0));
                        } else if (gameObject.getPosition().getX() == 3143 && gameObject.getPosition().getY() == 5543) { // 26 -> 27
                            player.moveTo(new Position(3155, 5449, 0));
                        } else if (gameObject.getPosition().getX() == 3155 && gameObject.getPosition().getY() == 5449) { // 26 -> 27
                            player.moveTo(new Position(3143, 5543, 0));
                        } else if (gameObject.getPosition().getX() == 3191 && gameObject.getPosition().getY() == 5495) { // 28 -> 29
                            player.moveTo(new Position(3194, 5490, 0));
                        } else if (gameObject.getPosition().getX() == 3194 && gameObject.getPosition().getY() == 5490) { // 28 -> 29
                            player.moveTo(new Position(3191, 5495, 0));
                        } else if (gameObject.getPosition().getX() == 3191 && gameObject.getPosition().getY() == 5482) { // 28 -> 30
                            player.moveTo(new Position(3185, 5478, 0));
                        } else if (gameObject.getPosition().getX() == 3185 && gameObject.getPosition().getY() == 5478) { // 28 -> 30
                            player.moveTo(new Position(3191, 5482, 0));
                        } else if (gameObject.getPosition().getX() == 3210 && gameObject.getPosition().getY() == 5477) { // 29 -> 35
                            player.moveTo(new Position(3208, 5471, 0));
                        } else if (gameObject.getPosition().getX() == 3208 && gameObject.getPosition().getY() == 5471) { // 29 -> 35
                            player.moveTo(new Position(3210, 5477, 0));
                        } else if (gameObject.getPosition().getX() == 3215 && gameObject.getPosition().getY() == 5475) { // 35 -> 36
                            player.moveTo(new Position(3218, 5478, 0));
                        } else if (gameObject.getPosition().getX() == 3218 && gameObject.getPosition().getY() == 5478) { // 35 -> 36
                            player.moveTo(new Position(3215, 5475, 0));
                        } else if (gameObject.getPosition().getX() == 3214 && gameObject.getPosition().getY() == 5456) { // 35 -> 34
                            player.moveTo(new Position(3212, 5452, 0));
                        } else if (gameObject.getPosition().getX() == 3212 && gameObject.getPosition().getY() == 5452) { // 35 -> 34
                            player.moveTo(new Position(3214, 5456, 0));
                        } else if (gameObject.getPosition().getX() == 3204 && gameObject.getPosition().getY() == 5445) { // 34 -> 33
                            player.moveTo(new Position(3197, 5448, 0));
                        } else if (gameObject.getPosition().getX() == 3197 && gameObject.getPosition().getY() == 5448) { // 34 -> 33
                            player.moveTo(new Position(3204, 5445, 0));
                        } else if (gameObject.getPosition().getX() == 3189 && gameObject.getPosition().getY() == 5444) { // 33 -> 32
                            player.moveTo(new Position(3187, 5460, 0));
                        } else if (gameObject.getPosition().getX() == 3187 && gameObject.getPosition().getY() == 5460) { // 33 -> 32
                            player.moveTo(new Position(3189, 5444, 0));
                        } else if (gameObject.getPosition().getX() == 3178 && gameObject.getPosition().getY() == 5460) { // 32 -> 31
                            player.moveTo(new Position(3168, 5456, 0));
                        } else if (gameObject.getPosition().getX() == 3168 && gameObject.getPosition().getY() == 5456) { // 32 -> 31
                            player.moveTo(new Position(3178, 5460, 0));
                        } else if (gameObject.getPosition().getX() == 3167 && gameObject.getPosition().getY() == 5471) { // 31 -> 30
                            player.moveTo(new Position(3171, 5473, 0));
                        } else if (gameObject.getPosition().getX() == 3171 && gameObject.getPosition().getY() == 5473) { // 31 -> 30
                            player.moveTo(new Position(3167, 5471, 0));
                        } else if (gameObject.getPosition().getX() == 3186 && gameObject.getPosition().getY() == 5472) { // 30 -> 33
                            player.moveTo(new Position(3192, 5472, 0));
                        } else if (gameObject.getPosition().getX() == 3192 && gameObject.getPosition().getY() == 5472) { // 30 -> 33
                            player.moveTo(new Position(3186, 5472, 0));
                        } else if (gameObject.getPosition().getX() == 3224 && gameObject.getPosition().getY() == 5479) { // 36 -> 37
                            player.moveTo(new Position(3222, 5474, 0));
                        } else if (gameObject.getPosition().getX() == 3222 && gameObject.getPosition().getY() == 5474) {
                            player.moveTo(new Position(3224, 5479, 0));
                        } else if (gameObject.getPosition().getX() == 3222 && gameObject.getPosition().getY() == 5488) { //36 -> 41
                            player.moveTo(new Position(3218, 5497, 0));
                        } else if (gameObject.getPosition().getX() == 3218 && gameObject.getPosition().getY() == 5497) { //36 -> 41
                            player.moveTo(new Position(3222, 5488, 0));
                        } else if (gameObject.getPosition().getX() == 3239 && gameObject.getPosition().getY() == 5498) { //41 -> 40
                            player.moveTo(new Position(3244, 5495, 0));
                        } else if (gameObject.getPosition().getX() == 3244 && gameObject.getPosition().getY() == 5495) { //41 -> 40
                            player.moveTo(new Position(3239, 5498, 0));
                        } else if (gameObject.getPosition().getX() == 3260 && gameObject.getPosition().getY() == 5491) { // dead portal 40
                            player.sendMessage("Hmm.. I should find another way..");
                        } else if (gameObject.getPosition().getX() == 3265 && gameObject.getPosition().getY() == 5491) { // dead portal 42
                            player.sendMessage("Hmm.. I should find another way..");
                        } else if (gameObject.getPosition().getX() == 3233 && gameObject.getPosition().getY() == 5470) { //40 -> 39
                            player.moveTo(new Position(3241, 5469, 0));
                        } else if (gameObject.getPosition().getX() == 3241 && gameObject.getPosition().getY() == 5469) { //40 -> 39
                            player.moveTo(new Position(3233, 5470, 0));
                        } else if (gameObject.getPosition().getX() == 3254 && gameObject.getPosition().getY() == 5451) { //39 -> 38
                            player.moveTo(new Position(3250, 5448, 0));
                        } else if (gameObject.getPosition().getX() == 3250 && gameObject.getPosition().getY() == 5448) { //39 -> 38
                            player.moveTo(new Position(3254, 5451, 0));
                        } else if (gameObject.getPosition().getX() == 3259 && gameObject.getPosition().getY() == 5446) { // dead portal 39
                            player.sendMessage("Hmm.. I should find another way..");
                        } else if (gameObject.getPosition().getX() == 3266 && gameObject.getPosition().getY() == 5446) { // dead portal 39
                            player.sendMessage("Hmm.. I should find another way..");
                        } else if (gameObject.getPosition().getX() == 3241 && gameObject.getPosition().getY() == 5445) { //38 -> 34
                            player.moveTo(new Position(3233, 5445, 0));
                        } else if (gameObject.getPosition().getX() == 3233 && gameObject.getPosition().getY() == 5445) { //38 -> 34
                            player.moveTo(new Position(3241, 5445, 0));
                        } else if (gameObject.getPosition().getX() == 3235 && gameObject.getPosition().getY() == 5457) { //38 -> 37
                            player.moveTo(new Position(3229, 5454, 0));
                        } else if (gameObject.getPosition().getX() == 3229 && gameObject.getPosition().getY() == 5454) { //38 -> 37
                            player.moveTo(new Position(3235, 5457, 0));
                        } else if (gameObject.getPosition().getX() == 3283 && gameObject.getPosition().getY() == 5448) { //47 -> 48
                            player.moveTo(new Position(3287, 5448, 0));
                        } else if (gameObject.getPosition().getX() == 3287 && gameObject.getPosition().getY() == 5448) { //47 -> 48
                            player.moveTo(new Position(3283, 5448, 0));
                        } else if (gameObject.getPosition().getX() == 3280 && gameObject.getPosition().getY() == 5460) { //47 -> 46
                            player.moveTo(new Position(3273, 5460, 0));
                        } else if (gameObject.getPosition().getX() == 3273 && gameObject.getPosition().getY() == 5460) { //47 -> 46
                            player.moveTo(new Position(3280, 5460, 0));
                        } else if (gameObject.getPosition().getX() == 3285 && gameObject.getPosition().getY() == 5474) { //46 -> 45
                            player.moveTo(new Position(3286, 5470, 0));
                        } else if (gameObject.getPosition().getX() == 3286 && gameObject.getPosition().getY() == 5470) { //46 -> 45
                            player.moveTo(new Position(3285, 5474, 0));
                        } else if (gameObject.getPosition().getX() == 3290 && gameObject.getPosition().getY() == 5463) { //45 -> 49
                            player.moveTo(new Position(3302, 5469, 0));
                        } else if (gameObject.getPosition().getX() == 3302 && gameObject.getPosition().getY() == 5469) { //45 -> 49
                            player.moveTo(new Position(3290, 5463, 0));
                        } else if (gameObject.getPosition().getX() == 3303 && gameObject.getPosition().getY() == 5477) { //45 -> 43
                            player.moveTo(new Position(3299, 5484, 0));
                        } else if (gameObject.getPosition().getX() == 3299 && gameObject.getPosition().getY() == 5484) { //45 -> 43
                            player.moveTo(new Position(3303, 5477, 0));
                        } else if (gameObject.getPosition().getX() == 3317 && gameObject.getPosition().getY() == 5496) { //43 -> 42
                            player.moveTo(new Position(3307, 5496, 0));
                        } else if (gameObject.getPosition().getX() == 3307 && gameObject.getPosition().getY() == 5496) { //43 -> 42
                            player.moveTo(new Position(3317, 5496, 0));
                        } else if (gameObject.getPosition().getX() == 3318 && gameObject.getPosition().getY() == 5481) { //43 -> 44
                            player.moveTo(new Position(3322, 5480, 0));
                        } else if (gameObject.getPosition().getX() == 3322 && gameObject.getPosition().getY() == 5480) { //43 -> 44
                            player.moveTo(new Position(3318, 5481, 0));
                        } else if (gameObject.getPosition().getX() == 3296 && gameObject.getPosition().getY() == 5455) { //48 -> 49
                            player.moveTo(new Position(3299, 5450, 0));
                        } else if (gameObject.getPosition().getX() == 3299 && gameObject.getPosition().getY() == 5450) { //48 -> 49
                            player.moveTo(new Position(3296, 5455, 0));
                        }
                        break;


                    case 28782: // CHAOS TUNNEL EXIT ROPES!!!!!!!
						/*if (gameObject.getPosition().getX() == 3234 && gameObject.getPosition().getY() == 5559) { // ROOM 16
							//player.moveTo(new Position(3261, 5536, 0));
						}

						else if (gameObject.getPosition().getX() == 3291 && gameObject.getPosition().getY() == 5538) { // ROOM 18
							//player.moveTo(new Position(3261, 5536, 0));
						}

						else if (gameObject.getPosition().getX() == 3183 && gameObject.getPosition().getY() == 5470) { // ROOM 30 ENTRANCE ROOM
							//player.moveTo(new Position(3261, 5536, 0));
						}

						else if (gameObject.getPosition().getX() == 3248 && gameObject.getPosition().getY() == 5490) { // ROOM 40 MASSIVE ROOM
							//player.moveTo(new Position(3261, 5536, 0));
						}

						else if (gameObject.getPosition().getX() == 3292 && gameObject.getPosition().getY() == 5479) { // ROOM 45
							//player.moveTo(new Position(3261, 5536, 0));
						}*/
                        player.sendMessage("That looks too hard to climb..");
                        break;

                    case 1815:
                        if (gameObject.getPosition().getX() == 3153 && gameObject.getPosition().getY() == 3923) { // lvl 51 wilderness lever
                            player.moveTo(new Position(2704, 5349));//home
                            player.getPacketSender().sendSmallImageKey("home");
                            player.getPacketSender().sendRichPresenceSmallPictureText("Combat Lvl: " + player.getSkillManager().getCombatLevel());

                        }
                        break;

                    case 52858: //
                        player.moveTo(new Position(2694, 9456)); // RESOURCE DUNGEON LVL 70 Dungeoneering - Tele to metal dragons
                        break;

                    case 21505:
                    case 21507:
                        player.moveTo(new Position(2329, 3804));
                        break;
                    case 6420:
                        KeysEvent.openChest(player);
                        break;

                    case 38700:
                        player.moveTo(new Position(3092, 3502));
                        break;
                    case 45803:
                    case 1767:
                        DialogueManager.start(player, 114);
                        player.setDialogueActionId(72);
                        break;
                    case 7352:
                        if (Dungeoneering.doingDungeoneering(player) && player.getMinigameAttributes().getDungeoneeringAttributes().getParty().getGatestonePosition() != null) {
                            player.moveTo(player.getMinigameAttributes().getDungeoneeringAttributes().getParty().getGatestonePosition());
                            player.setEntityInteraction(null);
                            player.getPacketSender().sendMessage("You are teleported to your party's gatestone.");
                            player.performGraphic(new Graphic(1310));
                        } else
                            player.getPacketSender().sendMessage("Your party must drop a Gatestone somewhere in the dungeon to use this portal.");
                        break;
                    case 7353:
                        player.moveTo(new Position(2439, 4956, player.getPosition().getZ()));
                        break;
                    case 7321:
                        player.moveTo(new Position(2452, 4944, player.getPosition().getZ()));
                        break;
                    case 7322:
                        player.moveTo(new Position(2455, 4964, player.getPosition().getZ()));
                        break;
                    case 7315:
                        player.moveTo(new Position(2447, 4956, player.getPosition().getZ()));
                        break;
                    case 7316:
                        player.moveTo(new Position(2471, 4956, player.getPosition().getZ()));
                        break;
                    case 7318:
                        player.moveTo(new Position(2464, 4963, player.getPosition().getZ()));
                        break;
                    case 7319:
                        player.moveTo(new Position(2467, 4940, player.getPosition().getZ()));
                        break;
                    case 7324:
                        player.moveTo(new Position(2481, 4956, player.getPosition().getZ()));
                        break;
                    case 11356:
                        player.moveTo(new Position(2860, 9741));
                        player.getPacketSender().sendMessage("You step through the portal..");
                        break;
                    case 47180:

                        player.getPacketSender().sendMessage("You activate the device..");
                        player.moveTo(new Position(2586, 3912));
                        break;
                    case 10091:
                    case 8702:
                        if (player.getInteractingObject() != null) {
                            player.setPositionToFace(player.getInteractingObject().getPosition().copy());
                        }
                        Fishing.setupFishing(player, Spot.ROCKTAIL);
                        break;
                    case 9319:
                        if (player.getSkillManager().getCurrentLevel(Skill.AGILITY) < 61) {
                            player.getPacketSender().sendMessage("You need an Agility level of at least 61 or higher to climb this");
                            return;
                        }
                        if (player.getPosition().getZ() == 0)
                            player.moveTo(new Position(3422, 3549, 1));
                        else if (player.getPosition().getZ() == 1) {
                            if (gameObject.getPosition().getX() == 3447)
                                player.moveTo(new Position(3447, 3575, 2));
                            else
                                player.moveTo(new Position(3447, 3575, 0));
                        }
                        break;

                    case 9320:
                        if (player.getSkillManager().getCurrentLevel(Skill.AGILITY) < 61) {
                            player.getPacketSender().sendMessage("You need an Agility level of at least 61 or higher to climb this");
                            return;
                        }
                        if (player.getPosition().getZ() == 1)
                            player.moveTo(new Position(3422, 3549, 0));
                        else if (player.getPosition().getZ() == 0)
                            player.moveTo(new Position(3447, 3575, 1));
                        else if (player.getPosition().getZ() == 2)
                            player.moveTo(new Position(3447, 3575, 1));
                        player.performAnimation(new Animation(828));
                        break;
                    case 2274:
                        if (gameObject.getPosition().getX() == 2912 && gameObject.getPosition().getY() == 5300) {
                            player.moveTo(new Position(2914, 5300, 1));
                        } else if (gameObject.getPosition().getX() == 2914 && gameObject.getPosition().getY() == 5300) {
                            player.moveTo(new Position(2912, 5300, 2));
                        } else if (gameObject.getPosition().getX() == 2919 && gameObject.getPosition().getY() == 5276) {
                            player.moveTo(new Position(2918, 5274));
                        } else if (gameObject.getPosition().getX() == 2918 && gameObject.getPosition().getY() == 5274) {
                            player.moveTo(new Position(2919, 5276, 1));
                        } else if (gameObject.getPosition().getX() == 3001 && gameObject.getPosition().getY() == 3931 || gameObject.getPosition().getX() == 3652 && gameObject.getPosition().getY() == 3488) {
                            player.moveTo(GameSettings.DEFAULT_POSITION.copy());
                            player.getPacketSender().sendMessage("The portal teleports you to Edgeville.");
                        }
                        break;
                    case 7836:
                    case 7808:
                        int amt = player.getInventory().getAmount(6055);
                        if (amt > 0) {
                            player.getInventory().delete(6055, amt);
                            player.getPacketSender().sendMessage("You put the weed in the compost bin.");
                            player.getSkillManager().addExperience(Skill.FARMING, 20 * amt);
                        } else {
                            player.getPacketSender().sendMessage("You do not have any weeds in your inventory.");
                        }
                        break;
                    case 5960: //Levers
                    case 5959:
                        player.setDirection(Direction.WEST);
                        TeleportHandler.teleportPlayer(player, new Position(3090, 3475), TeleportType.LEVER);
                        break;
                    case 5096:
                        if (gameObject.getPosition().getX() == 2644 && gameObject.getPosition().getY() == 9593)
                            player.moveTo(new Position(2649, 9591));
                        break;

                    case 5094:
                        if (gameObject.getPosition().getX() == 2648 && gameObject.getPosition().getY() == 9592)
                            player.moveTo(new Position(2643, 9594, 2));
                        break;

                    case 5098:
                        if (gameObject.getPosition().getX() == 2635 && gameObject.getPosition().getY() == 9511)
                            player.moveTo(new Position(2637, 9517));
                        break;

                    case 5097:
                        if (gameObject.getPosition().getX() == 2635 && gameObject.getPosition().getY() == 9514)
                            player.moveTo(new Position(2636, 9510, 2));
                        break;
                    case 26428:
                    case 26426:
                    case 26425:
                    case 26427:
                        String bossRoom = "Armadyl";
                        boolean leaveRoom = player.getPosition().getY() > 5295;
                        int index = 0;
                        Position movePos = new Position(2839, !leaveRoom ? 5296 : 5295, 2);
                        if (id == 26425) {
                            bossRoom = "Bandos";
                            leaveRoom = player.getPosition().getX() > 2863;
                            index = 1;
                            movePos = new Position(!leaveRoom ? 2864 : 2863, 5354, 2);
                        } else if (id == 26427) {
                            bossRoom = "Saradomin";
                            leaveRoom = player.getPosition().getX() < 2908;
                            index = 2;
                            movePos = new Position(leaveRoom ? 2908 : 2907, 5265);
                        } else if (id == 26428) {
                            bossRoom = "Zamorak";
                            leaveRoom = player.getPosition().getY() <= 5331;
                            index = 3;
                            movePos = new Position(2925, leaveRoom ? 5332 : 5331, 2);
                        }
                        if (!leaveRoom && (!player.getRights().isStaff() || !player.getRights().isMember()) && player.getMinigameAttributes().getGodwarsDungeonAttributes().getKillcount()[index] < 20) {
                            player.getPacketSender().sendMessage("You need " + Misc.anOrA(bossRoom) + " " + bossRoom + " killcount of at least 20 to enter this room.");
                            return;
                        }
                        player.moveTo(movePos);
                        player.getMinigameAttributes().getGodwarsDungeonAttributes().setHasEnteredRoom(leaveRoom ? false : true);
                        player.getMinigameAttributes().getGodwarsDungeonAttributes().getKillcount()[index] = 0;
                        player.getPacketSender().sendString(16216 + index, "0");
                        break;
                    case 26289:
                    case 26286:
                    case 26288:
                    case 26287:
                        if (System.currentTimeMillis() - player.getMinigameAttributes().getGodwarsDungeonAttributes().getAltarDelay() < 600000) {
                            player.getPacketSender().sendMessage("");
                            player.getPacketSender().sendMessage("You can only pray at a God's altar once every 10 minutes.");
                            player.getPacketSender().sendMessage("You must wait another " + (int) ((600 - (System.currentTimeMillis() - player.getMinigameAttributes().getGodwarsDungeonAttributes().getAltarDelay()) * 0.001)) + " seconds before being able to do this again.");
                            return;
                        }
                        int itemCount = id == 26289 ? Equipment.getItemCount(player, "Bandos", false) : id == 26286 ? Equipment.getItemCount(player, "Zamorak", false) : id == 26288 ? Equipment.getItemCount(player, "Armadyl", false) : id == 26287 ? Equipment.getItemCount(player, "Saradomin", false) : 0;
                        int toRestore = player.getSkillManager().getMaxLevel(Skill.PRAYER) + (itemCount * 10);
                        if (player.getSkillManager().getCurrentLevel(Skill.PRAYER) >= toRestore) {
                            player.getPacketSender().sendMessage("You do not need to recharge your Prayer points at the moment.");
                            return;
                        }
                        player.performAnimation(new Animation(645));
                        player.getSkillManager().setCurrentLevel(Skill.PRAYER, toRestore);
                        player.getMinigameAttributes().getGodwarsDungeonAttributes().setAltarDelay(System.currentTimeMillis());
                        break;
                    case 23093:
                        if (player.getSkillManager().getCurrentLevel(Skill.AGILITY) < 70) {
                            player.getPacketSender().sendMessage("You need an Agility level of at least 70 to go through this portal.");
                            return;
                        }
                        if (!player.getClickDelay().elapsed(2000))
                            return;
                        int plrHeight = player.getPosition().getZ();
                        if (plrHeight == 2)
                            player.moveTo(new Position(2914, 5300, 1));
                        else if (plrHeight == 1) {
                            int x = gameObject.getPosition().getX();
                            int y = gameObject.getPosition().getY();
                            if (x == 2914 && y == 5300)
                                player.moveTo(new Position(2912, 5299, 2));
                            else if (x == 2920 && y == 5276)
                                player.moveTo(new Position(2920, 5274, 0));
                        } else if (plrHeight == 0)
                            player.moveTo(new Position(2920, 5276, 1));
                        player.getClickDelay().reset();
                        break;
                    case 26439:
                        if (player.getSkillManager().getMaxLevel(Skill.CONSTITUTION) <= 700) {
                            player.getPacketSender().sendMessage("You need a Constitution level of at least 70 to swim across.");
                            return;
                        }
                        if (!player.getClickDelay().elapsed(1000))
                            return;
                        if (player.isCrossingObstacle())
                            return;
                        final String startMessage = "You jump into the icy cold water..";
                        final String endMessage = "You climb out of the water safely.";
                        final int jumpGFX = 68;
                        final int jumpAnimation = 772;
                        player.setSkillAnimation(773);
                        player.setCrossingObstacle(true);
                        player.getUpdateFlag().flag(Flag.APPEARANCE);
                        player.performAnimation(new Animation(3067));
                        final boolean goBack2 = player.getPosition().getY() >= 5344;
                        player.getPacketSender().sendMessage(startMessage);
                        player.moveTo(new Position(2885, !goBack2 ? 5335 : 5342, 2));
                        player.setDirection(goBack2 ? Direction.SOUTH : Direction.NORTH);
                        player.performGraphic(new Graphic(jumpGFX));
                        player.performAnimation(new Animation(jumpAnimation));
                        TaskManager.submit(new Task(1, player, false) {
                            int ticks = 0;

                            @Override
                            public void execute() {
                                ticks++;
                                player.getMovementQueue().walkStep(0, goBack2 ? -1 : 1);
                                if (ticks >= 10)
                                    stop();
                            }

                            @Override
                            public void stop() {
                                player.setSkillAnimation(-1);
                                player.setCrossingObstacle(false);
                                player.getUpdateFlag().flag(Flag.APPEARANCE);
                                player.getPacketSender().sendMessage(endMessage);
                                player.moveTo(new Position(2885, player.getPosition().getY() < 5340 ? 5333 : 5345, 2));
                                setEventRunning(false);
                            }
                        });
                        player.getClickDelay().reset((System.currentTimeMillis() + 9000));
                        break;
                    case 26384:
                        if (player.isCrossingObstacle())
                            return;
                        if (!player.getInventory().contains(2347)) {
                            player.getPacketSender().sendMessage("You need to have a hammer to bang on the door with.");
                            return;
                        }
                        player.setCrossingObstacle(true);
                        final boolean goBack = player.getPosition().getX() <= 2850;
                        player.performAnimation(new Animation(377));
                        TaskManager.submit(new Task(2, player, false) {
                            @Override
                            public void execute() {
                                player.moveTo(new Position(goBack ? 2851 : 2850, 5333, 2));
                                player.setCrossingObstacle(false);
                                stop();
                            }
                        });
                        break;
                    case 26303:
                        if (!player.getClickDelay().elapsed(1200))
                            return;
                        if (player.getSkillManager().getCurrentLevel(Skill.RANGED) < 70)
                            player.getPacketSender().sendMessage("You need a Ranged level of at least 70 to swing across here.");
                        else if (!player.getInventory().contains(9418)) {
                            player.getPacketSender().sendMessage("You need a Mithril grapple to swing across here. Explorer Jack might have one.");
                            return;
                        } else {
                            player.performAnimation(new Animation(789));
                            TaskManager.submit(new Task(2, player, false) {
                                @Override
                                public void execute() {
                                    player.getPacketSender().sendMessage("You throw your Mithril grapple over the pillar and move across.");
                                    player.moveTo(new Position(2871, player.getPosition().getY() <= 5270 ? 5279 : 5269, 2));
                                    stop();
                                }
                            });
                            player.getClickDelay().reset();
                        }
                        break;
                    case 4493:
                        if (player.getPosition().getX() >= 3432) {
                            player.moveTo(new Position(3433, 3538, 1));
                        }
                        break;
                    case 4494:
                        player.moveTo(new Position(3438, 3538, 0));
                        break;
                    case 4495:
                        player.moveTo(new Position(3417, 3541, 2));
                        break;
                    case 4496:
                        player.moveTo(new Position(3412, 3541, 1));
                        break;
                    case 2491:
                        player.setDialogueActionId(48);
                        DialogueManager.start(player, 87);
                        break;
                    case 25339:
                    case 25340:
                        player.moveTo(new Position(1778, 5346, player.getPosition().getZ() == 0 ? 1 : 0));
                        break;
                    case 10229:
                    case 10230:
                        boolean up = id == 10229;
                        player.performAnimation(new Animation(up ? 828 : 827));
                        player.getPacketSender().sendMessage("You climb " + (up ? "up" : "down") + " the ladder..");
                        TaskManager.submit(new Task(1, player, false) {
                            @Override
                            protected void execute() {
                                player.moveTo(up ? new Position(1912, 4367) : new Position(2900, 4449));
                                stop();
                            }
                        });
                        break;
                    case 1568:
                        player.moveTo(new Position(3097, 9868));
                        break;
                    case 5103: //Brimhaven vines
                    case 5104:
                    case 5105:
                    case 5106:
                    case 5107:
                        if (!player.getClickDelay().elapsed(4000))
                            return;
                        if (player.getSkillManager().getCurrentLevel(Skill.WOODCUTTING) < 30) {
                            player.getPacketSender().sendMessage("You need a Woodcutting level of at least 30 to do this.");
                            return;
                        }
                        if (WoodcuttingData.getHatchet(player) < 0) {
                            player.getPacketSender().sendMessage("You do not have a hatchet which you have the required Woodcutting level to use.");
                            return;
                        }
                        final Hatchet axe = Hatchet.forId(WoodcuttingData.getHatchet(player));
                        player.performAnimation(new Animation(axe.getAnim()));
                        gameObject.setFace(-1);
                        TaskManager.submit(new Task(3 + RandomUtility.getRandom(4), player, false) {
                            @Override
                            protected void execute() {
                                if (player.getMovementQueue().isMoving()) {
                                    stop();
                                    return;
                                }
                                int x = 0;
                                int y = 0;
                                if (player.getPosition().getX() == 2689 && player.getPosition().getY() == 9564) {
                                    x = 2;
                                    y = 0;
                                } else if (player.getPosition().getX() == 2691 && player.getPosition().getY() == 9564) {
                                    x = -2;
                                    y = 0;
                                } else if (player.getPosition().getX() == 2683 && player.getPosition().getY() == 9568) {
                                    x = 0;
                                    y = 2;
                                } else if (player.getPosition().getX() == 2683 && player.getPosition().getY() == 9570) {
                                    x = 0;
                                    y = -2;
                                } else if (player.getPosition().getX() == 2674 && player.getPosition().getY() == 9479) {
                                    x = 2;
                                    y = 0;
                                } else if (player.getPosition().getX() == 2676 && player.getPosition().getY() == 9479) {
                                    x = -2;
                                    y = 0;
                                } else if (player.getPosition().getX() == 2693 && player.getPosition().getY() == 9482) {
                                    x = 2;
                                    y = 0;
                                } else if (player.getPosition().getX() == 2672 && player.getPosition().getY() == 9499) {
                                    x = 2;
                                    y = 0;
                                } else if (player.getPosition().getX() == 2674 && player.getPosition().getY() == 9499) {
                                    x = -2;
                                    y = 0;
                                }
                                CustomObjects.objectRespawnTask(player, new GameObject(-1, gameObject.getPosition().copy()), gameObject, 10);
                                player.getPacketSender().sendMessage("You chop down the vines..");
                                player.getSkillManager().addExperience(Skill.WOODCUTTING, 45);
                                player.performAnimation(new Animation(65535));
                                player.getMovementQueue().walkStep(x, y);
                                stop();
                            }
                        });
                        player.getClickDelay().reset();
                        break;

                    case 22937:
                        TaskManager.submit(new Task(1, player, false) {
                            @Override
                            protected void execute() {
                                player.moveTo(new Position(2731, 5346, 1));//up
                                stop();
                            }
                        });
                        break;

                    case 22938:
                        TaskManager.submit(new Task(1, player, false) {
                            @Override
                            protected void execute() {
                                player.moveTo(new Position(2727, 5347, 0));//DOWN
                                stop();
                            }
                        });
                        break;

                    case 22939:
                        TaskManager.submit(new Task(1, player, false) {
                            @Override
                            protected void execute() {
                                player.moveTo(new Position(2721, 5362, 1));
                                stop();
                            }
                        });
                        break;

                    case 22940:
                        TaskManager.submit(new Task(1, player, false) {
                            @Override
                            protected void execute() {
                                player.moveTo(new Position(2720, 5358, 0));//DOWN
                                stop();
                            }
                        });
                        break;


                    case 29942:
                        if (player.getSkillManager().getCurrentLevel(Skill.SUMMONING) == player.getSkillManager().getMaxLevel(Skill.SUMMONING)) {
                            player.getPacketSender().sendMessage("You do not need to recharge your Summoning points right now.");
                            return;
                        }
                        player.performGraphic(new Graphic(1517));
                        player.getSkillManager().setCurrentLevel(Skill.SUMMONING, player.getSkillManager().getMaxLevel(Skill.SUMMONING), true);
                        player.getPacketSender().sendString(18045, " " + player.getSkillManager().getCurrentLevel(Skill.SUMMONING) + "/" + player.getSkillManager().getMaxLevel(Skill.SUMMONING));
                        player.getPacketSender().sendMessage("You recharge your Summoning points.");
                        break;
                    case 57225: //check todo
                        if (!player.getMinigameAttributes().getGodwarsDungeonAttributes().hasEnteredRoom()) {
                            player.setDialogueActionId(44);
                            DialogueManager.start(player, 79);
                        } else {
                            player.moveTo(new Position(2906, 5204));
                            player.getMinigameAttributes().getGodwarsDungeonAttributes().setHasEnteredRoom(false);
                        }
                        break;
                    case 884:
                    case 880://well of goodwill
                        player.setDialogueActionId(41);
                        DialogueManager.start(player, 75);
                        break;

                    case 22914://Donator Room
                        if (player.getRights().isStaff() || player.getRights().isMember()) {
                            player.moveTo(new Position(player.getPosition().getX() - 1, player.getPosition().getY(), 0));
                        } else {
                            player.getPacketSender().sendMessage("You need to be a donator to enter this room.");
                        }
                        break;

                    case 9294:
                        if (player.getSkillManager().getCurrentLevel(Skill.AGILITY) < 80) {
                            player.getPacketSender().sendMessage("You need an Agility level of at least 80 to use this shortcut.");
                            return;
                        }
                        player.performAnimation(new Animation(769));
                        TaskManager.submit(new Task(1, player, false) {
                            @Override
                            protected void execute() {
                                player.moveTo(new Position(player.getPosition().getX() >= 2880 ? 2878 : 2880, 9813));
                                stop();
                            }
                        });
                        break;
                    case 9293:
                        boolean back = player.getPosition().getX() > 2888;
                        player.moveTo(back ? new Position(2886, 9799) : new Position(2891, 9799));
                        break;
                    case 2320:
                        back = player.getPosition().getY() == 9969 || player.getPosition().getY() == 9970;
                        player.moveTo(back ? new Position(3120, 9963) : new Position(3120, 9969));
                        break;
                    case 1755:
                        player.performAnimation(new Animation(828));
                        player.getPacketSender().sendMessage("You climb the stairs..");
                        TaskManager.submit(new Task(1, player, false) {
                            @Override
                            protected void execute() {
                                if (gameObject.getPosition().getX() == 2547 && gameObject.getPosition().getY() == 9951) {
                                    player.moveTo(new Position(2548, 3551));
                                } else if (gameObject.getPosition().getX() == 3005 && gameObject.getPosition().getY() == 10363) {
                                    player.moveTo(new Position(3005, 3962));
                                } else if (gameObject.getPosition().getX() == 3084 && gameObject.getPosition().getY() == 9672) {
                                    player.moveTo(new Position(3117, 3244));
                                } else if (gameObject.getPosition().getX() == 3097 && gameObject.getPosition().getY() == 9867) {
                                    player.moveTo(new Position(3096, 3468));
                                }
                                stop();
                            }
                        });
                        break;
                    case 5110:
                        player.moveTo(new Position(2647, 9557));
                        player.getPacketSender().sendMessage("You pass the stones..");
                        break;
                    case 5111:
                        player.moveTo(new Position(2649, 9562));
                        player.getPacketSender().sendMessage("You pass the stones..");
                        break;
                    case 6434:
                        player.performAnimation(new Animation(827));
                        player.getPacketSender().sendMessage("You enter the trapdoor..");
                        TaskManager.submit(new Task(1, player, false) {
                            @Override
                            protected void execute() {
                                player.moveTo(new Position(3085, 9672));
                                stop();
                            }
                        });
                        break;
                    case 19187:
                    case 19175:
                        Hunter.dismantle(player, gameObject);
                        break;
                    case 25029:
                        PuroPuro.goThroughWheat(player, gameObject);
                        break;
                    case 47976:
                        Nomad.endFight(player, false);
                        break;
                    case 2182:
                        if (!player.getMinigameAttributes().getRecipeForDisasterAttributes().hasFinishedPart(0)) {
                            player.getPacketSender().sendMessage("You have no business with this chest. Talk to the Gypsy first!");
                            return;
                        }
                        RecipeForDisaster.openRFDShop(player);
                        break;
                    case 12356:
                        if (!player.getMinigameAttributes().getRecipeForDisasterAttributes().hasFinishedPart(0)) {
                            player.getPacketSender().sendMessage("You have no business with this portal. Talk to the Gypsy first!");
                            return;
                        }
                        if (player.getPosition().getZ() > 0) {
                            RecipeForDisaster.leave(player);
                        } else {
                            player.getMinigameAttributes().getRecipeForDisasterAttributes().setPartFinished(1, true);
                            RecipeForDisaster.enter(player);
                        }
                        break;
                    case 9369:
                        if (player.getPosition().getY() > 5175) {
                            FightPit.addPlayer(player);
                        } else {
                            FightPit.removePlayer(player, "leave room");
                        }
                        break;
                    case 9368:
                        if (player.getPosition().getY() < 5169) {
                            FightPit.removePlayer(player, "leave game");
                        }
                        break;
                    case 357:

                        break;
                    case 1:

                        break;
                    case 5262:
                        if (player.getLocation() == Location.KRAKEN) {
                            player.getPacketSender().sendMessage("You leave the cave and end up at home.");
                            player.moveTo(GameSettings.DEFAULT_POSITION.copy());
                        }
                        break;
                    case 9357:
                        FightCave.leaveCave(player, false);
                        break;
                    case 9356:
                        FightCave.enterCave(player);
                        break;
                    case 6704:
                        player.moveTo(new Position(3577, 3282, 0));
                        break;
                    case 6706:
                        player.moveTo(new Position(3554, 3283, 0));
                        break;
                    case 6705:
                        player.moveTo(new Position(3566, 3275, 0));
                        break;
                    case 6702:
                        player.moveTo(new Position(3564, 3289, 0));
                        break;
                    case 6703:
                        player.moveTo(new Position(3574, 3298, 0));
                        break;
                    case 6707:
                        player.moveTo(new Position(3556, 3298, 0));
                        break;
                    case 3203:
                        if (player.getLocation() == Location.DUEL_ARENA && player.getDueling().duelingStatus == 5) {
                            if (Dueling.checkRule(player, DuelRule.NO_FORFEIT)) {
                                player.getPacketSender().sendMessage("Forfeiting has been disabled in this duel.");
                                return;
                            }
                            player.getCombatBuilder().reset(true);
                            if (player.getDueling().duelingWith > -1) {
                                Player duelEnemy = World.getPlayers().get(player.getDueling().duelingWith);
                                if (duelEnemy == null)
                                    return;
                                duelEnemy.getCombatBuilder().reset(true);
                                duelEnemy.getMovementQueue().reset();
                                duelEnemy.getDueling().duelVictory();
                            }
                            player.moveTo(new Position(3368 + RandomUtility.getRandom(5), 3267 + RandomUtility.getRandom(3), 0));
                            player.getDueling().reset();
                            player.getCombatBuilder().reset(true);
                            player.restart();
                        }
                        break;
                    case 14315:
                        PestControl.boardBoat(player);
                        break;
                    case 14314:
                        if (player.getLocation() == Location.PEST_CONTROL_BOAT) {
                            player.getLocation().leave(player);
                        }
                        break;
                    case 1738:
                        if (player.getLocation() == Location.LUMBRIDGE && player.getPosition().getZ() == 0) {
                            player.moveTo(new Position(player.getPosition().getX(), player.getPosition().getY(), 1));
                        } else {
                            player.moveTo(new Position(2840, 3539, 2));
                        }
                        break;
                    case 15638:
                        player.moveTo(new Position(2840, 3539, 0));
                        break;
                    case 15644:
                    case 15641:
                        switch (player.getPosition().getZ()) {
                            case 0:
                                player.moveTo(new Position(2855, player.getPosition().getY() >= 3546 ? 3545 : 3546));
                                break;
                            case 2:
                                if (player.getPosition().getX() == 2846) {
                                    if (player.getInventory().getAmount(8851) < 70) {
                                        player.getPacketSender().sendMessage("You need at least 70 tokens to enter this area.");
                                        return;
                                    }
                                    DialogueManager.start(player, WarriorsGuild.warriorsGuildDialogue(player));
                                    player.moveTo(new Position(2847, player.getPosition().getY(), 2));
                                    WarriorsGuild.handleTokenRemoval(player);
                                } else if (player.getPosition().getX() == 2847) {
                                    WarriorsGuild.resetCyclopsCombat(player);
                                    player.moveTo(new Position(2846, player.getPosition().getY(), 2));
                                    player.getMinigameAttributes().getWarriorsGuildAttributes().setEnteredTokenRoom(false);
                                }
                                break;
                        }
                        break;
                    case 28714:
                        player.performAnimation(new Animation(828));
                        player.delayedMoveTo(new Position(3089, 3492), 2);
                        break;
                    case 1746:
                        player.performAnimation(new Animation(827));
                        player.delayedMoveTo(new Position(2209, 5348), 2);
                        break;
                    case 19191:
                    case 19189:
                    case 19180:
                    case 19184:
                    case 19182:
                    case 19178:
                        Hunter.lootTrap(player, gameObject);
                        break;
                    case 13493:
                        double c = Math.random() * 100;
                        int reward = c >= 70 ? 13001 : c >= 45 ? 4129 : c >= 35 ? 1123 : c >= 25 ? 1145 : c >= 18 ? 1161 : c >= 12 ? 2362 : c >= 5 ? 1199 : 1123;
                        Stalls.stealFromStall(player, 95, 24800, reward, "You've stolen something!");
                        break;
                    case 3192:
                        player.setDialogueActionId(11);
                        DialogueManager.start(player, 20);
                        break;
                    case 22823:
                    case 28716:
                        if (!player.busy()) {
                            player.getSkillManager().updateSkill(Skill.SUMMONING);
                            player.getPacketSender().sendInterface(63471);
                        } else
                            player.getPacketSender().sendMessage("Please finish what you're doing before opening this.");
                        break;
                    case 6:
                        DwarfCannon cannon = player.getCannon();
                        if (cannon == null || cannon.getOwnerIndex() != player.getIndex()) {
                            player.getPacketSender().sendMessage("This is not your cannon!");
                        } else {
                            DwarfMultiCannon.startFiringCannon(player, cannon);
                        }
                        break;
                    case 2:
                        player.moveTo(new Position(player.getPosition().getX() > 2690 ? 2687 : 2694, 3714));
                        player.getPacketSender().sendMessage("You walk through the entrance..");
                        break;
                    case 2026:
                    case 2028:
                    case 2029:
                    case 2030:
                    case 2031:
                        player.setEntityInteraction(gameObject);
                        Fishing.setupFishing(player, Fishing.forSpot(gameObject.getId(), false));
                        return;
                    case 12692:
                    case 2783:
                    case 4306:
                    case 22725:
                        player.setInteractingObject(gameObject);
                        EquipmentMaking.handleAnvil(player);
                        EquipmentMaking.handleNewAnvil(player);
                        if ((player.getRights().isStaff()) || (player.getRights().isMember())) {
                            player.getPacketSender().sendMessage("@red@You don't need a hammer due to your rank <3");
                        } else {
                            player.getPacketSender().sendMessage("@red@Donator's don't need a hammer to smith!");
                        }

                        break;
                    case 409:
                    case 4859:
                    case 27661:
                    case 2640:
                    case 36972:
                        player.performAnimation(new Animation(645));
                        if (player.getSkillManager().getCurrentLevel(Skill.PRAYER) < player.getSkillManager().getMaxLevel(Skill.PRAYER)) {
                            player.getSkillManager().setCurrentLevel(Skill.PRAYER, player.getSkillManager().getMaxLevel(Skill.PRAYER), true);
                            player.getPacketSender().sendMessage("You recharge your Prayer points.");
                        }
                        break;
                    case 8749:

                        player.setSpecialPercentage(100);
                        CombatSpecial.updateBar(player);
                        player.getPacketSender().sendMessage("Your special attack energy has been restored.");
                        player.performGraphic(new Graphic(1302));
                        break;
                    case 411:
                        if (player.getSkillManager().getMaxLevel(Skill.DEFENCE) < 30) {
                            player.getPacketSender().sendMessage("You need a Defence level of at least 30 to use this altar.");
                            return;
                        }
                        player.performAnimation(new Animation(645));
                        if (player.getPrayerbook() == Prayerbook.NORMAL) {
                            player.getPacketSender().sendMessage("You sense a surge of power flow through your body!");
                            player.setPrayerbook(Prayerbook.CURSES);
                        } else {
                            player.getPacketSender().sendMessage("You sense a surge of purity flow through your body!");
                            player.setPrayerbook(Prayerbook.NORMAL);
                        }
                        player.getPacketSender().sendTabInterface(GameSettings.PRAYER_TAB, player.getPrayerbook().getInterfaceId());
                        PrayerHandler.deactivateAll(player);
                        CurseHandler.deactivateAll(player);
                        break;
                    case 6552:
                        player.performAnimation(new Animation(645));
                        player.setSpellbook(player.getSpellbook() == MagicSpellbook.ANCIENT ? MagicSpellbook.NORMAL : MagicSpellbook.ANCIENT);
                        player.getPacketSender().sendTabInterface(GameSettings.MAGIC_TAB, player.getSpellbook().getInterfaceId()).sendMessage("Your magic spellbook is changed..");
                        Autocasting.resetAutocast(player, true);
                        break;
                    case 13179:
                        if (player.getSkillManager().getMaxLevel(Skill.DEFENCE) < 40) {
                            player.getPacketSender().sendMessage("You need a Defence level of at least 40 to use this altar.");
                            return;
                        }
                        player.performAnimation(new Animation(645));
                        player.setSpellbook(player.getSpellbook() == MagicSpellbook.LUNAR ? MagicSpellbook.NORMAL : MagicSpellbook.LUNAR);
                        player.getPacketSender().sendTabInterface(GameSettings.MAGIC_TAB, player.getSpellbook().getInterfaceId()).sendMessage("Your magic spellbook is changed..");
                        ;
                        Autocasting.resetAutocast(player, true);
                        break;
                    case 172:
                        CrystalChest.handleChest(player, gameObject);
                        break;
                    case 6910:
                    case 4483:
                    case 3193:
                    case 2213:
                    case 11758:
                    case 14367:
                    case 42192:
                    case 75:
                    case 22819:
                    case 22822:
                        player.getBank(player.getCurrentBankTab()).open();
                        break;


                    case 26814:
                    case 11666:
                    case 56031:
                    case 56032:
                    case 56034:
                        Smelting.openInterface(player);
                        break;
                }
            }
        }));
    }
}
