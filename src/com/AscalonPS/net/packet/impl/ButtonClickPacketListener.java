package com.AscalonPS.net.packet.impl;

import com.AscalonPS.GameSettings;
import com.AscalonPS.model.Difficulty;
import com.AscalonPS.model.Locations.Location;
import com.AscalonPS.model.PlayerRights;
import com.AscalonPS.model.Position;
import com.AscalonPS.model.Skill;
import com.AscalonPS.model.container.impl.Bank;
import com.AscalonPS.model.container.impl.Bank.BankSearchAttributes;
import com.AscalonPS.model.definitions.WeaponInterfaces.WeaponInterface;
import com.AscalonPS.model.input.impl.*;
import com.AscalonPS.net.packet.Packet;
import com.AscalonPS.net.packet.PacketListener;
import com.AscalonPS.util.Misc;
import com.AscalonPS.world.World;
import com.AscalonPS.world.content.*;
import com.AscalonPS.world.content.Sounds.Sound;
import com.AscalonPS.world.content.clan.ClanChat;
import com.AscalonPS.world.content.clan.ClanChatManager;
import com.AscalonPS.world.content.combat.instancearena.InstanceArena;
import com.AscalonPS.world.content.combat.magic.Autocasting;
import com.AscalonPS.world.content.combat.magic.MagicSpells;
import com.AscalonPS.world.content.combat.prayer.CurseHandler;
import com.AscalonPS.world.content.combat.prayer.PrayerHandler;
import com.AscalonPS.world.content.combat.weapon.CombatSpecial;
import com.AscalonPS.world.content.combat.weapon.FightType;
import com.AscalonPS.world.content.dailyreward.DailyRewardConstants;
import com.AscalonPS.world.content.dialogue.DialogueManager;
import com.AscalonPS.world.content.dialogue.DialogueOptions;
import com.AscalonPS.world.content.droppreview.*;
import com.AscalonPS.world.content.grandexchange.GrandExchange;
import com.AscalonPS.world.content.kill_log.KillLogInterface;
import com.AscalonPS.world.content.minigames.impl.Dueling;
import com.AscalonPS.world.content.minigames.impl.Nomad;
import com.AscalonPS.world.content.minigames.impl.PestControl;
import com.AscalonPS.world.content.minigames.impl.RecipeForDisaster;
import com.AscalonPS.world.content.skill.ChatboxInterfaceSkillAction;
import com.AscalonPS.world.content.skill.impl.construction.Construction;
import com.AscalonPS.world.content.skill.impl.crafting.LeatherMaking;
import com.AscalonPS.world.content.skill.impl.crafting.Tanning;
import com.AscalonPS.world.content.skill.impl.dungeoneering.Dungeoneering;
import com.AscalonPS.world.content.skill.impl.dungeoneering.DungeoneeringParty;
import com.AscalonPS.world.content.skill.impl.dungeoneering.ItemBinding;
import com.AscalonPS.world.content.skill.impl.fletching.Fletching;
import com.AscalonPS.world.content.skill.impl.herblore.IngridientsBook;
import com.AscalonPS.world.content.skill.impl.slayer.Slayer;
import com.AscalonPS.world.content.skill.impl.smithing.SmithingData;
import com.AscalonPS.world.content.skill.impl.summoning.PouchMaking;
import com.AscalonPS.world.content.skill.impl.summoning.SummoningTab;
import com.AscalonPS.world.content.teleportation.Teleporting;
import com.AscalonPS.world.content.transportation.TeleportHandler;
import com.AscalonPS.world.entity.impl.player.Player;

/**
 * This packet listener manages a button that the player has clicked upon.
 *
 * @author Gabriel Hannason
 */

public class ButtonClickPacketListener implements PacketListener {

    public static final int OPCODE = 185;

    private boolean checkHandlers(Player player, int id) {
        if (KillLogInterface.handleButton(player, id))
            return true;
        if (Construction.handleButtonClick(id, player)) {
            return true;
        }
        switch (id) {
            case 2494:
            case 2495:
            case 2496:
            case 2497:
            case 2498:
            case 2471:
            case 2472:
            case 2473:
            case 2461:
            case 2462:
            case 2482:
            case 2483:
            case 2484:
            case 2485:
                DialogueOptions.handle(player, id);
                return true;
        }
        if (player.isPlayerLocked() && id != 2458 && id != -12780 && id != -12779 && id != -12778 && id != -29767) {
            return true;
        }
        if (DropsInterface.handleButton(id)) {
            DropsInterface.handleButtonClick(player, id);
            return true;
        }
        if (Achievements.handleButton(player, id)) {
            return true;
        }
        if (Sounds.handleButton(player, id)) {
            return true;
        }
        if (PrayerHandler.isButton(id)) {
            PrayerHandler.togglePrayerWithActionButton(player, id);
            return true;
        }
        if (CurseHandler.isButton(player, id)) {
            return true;
        }
        if (StartScreen.handleButton(player, id)) {
            return true;
        }
        if (Autocasting.handleAutocast(player, id)) {
            return true;
        }
        if (SmithingData.handleButtons(player, id)) {
            return true;
        }
        if (PouchMaking.pouchInterface(player, id)) {
            return true;
        }
        if (LoyaltyProgramme.handleButton(player, id)) {
            return true;
        }
        if (Fletching.fletchingButton(player, id)) {
            return true;
        }
        if (LeatherMaking.handleButton(player, id) || Tanning.handleButton(player, id)) {
            return true;
        }
        if (Emotes.doEmote(player, id)) {
            return true;
        }
        if (PestControl.handleInterface(player, id)) {
            return true;
        }
        if (player.getLocation() == Location.DUEL_ARENA && Dueling.handleDuelingButtons(player, id)) {
            return true;
        }
        if (Slayer.handleRewardsInterface(player, id)) {
            return true;
        }
        if (ExperienceLamps.handleButton(player, id)) {
            return true;
        }
        if (PlayersOnlineInterface.handleButton(player, id)) {
            return true;
        }
        if (GrandExchange.handleButton(player, id)) {
            return true;
        }
        if (ClanChatManager.handleClanChatSetupButton(player, id)) {
            return true;
        }
        return false;
    }

    @Override
    public void handleMessage(Player player, Packet packet) {

        int id = packet.readShort();

        PlayerPanel.refreshPanel(player);

        if (player.getRights() == PlayerRights.OWNER) {
            player.getPacketSender().sendMessage("Clicked button: " + id);
        }

        if (checkHandlers(player, id))
            return;

        if (id >= 32623 && id <= 32722) {
            player.getPlayerOwnedShopManager().handleButton(id);
        }

        if (id >= 32410 && id <= 32460) {
            StaffList.handleButton(player, id);
        }

        if (id == DailyRewardConstants.CLAIM_BUTTON) {
            player.getDailyReward().claimReward();
            return;
        }

        switch (id) {

            case 22051:
                player.getPacketSender().sendString(22046, "1");
                player.setAmountToWithdraw(1);
                break;

            case 22050:
                player.getPacketSender().sendString(22046, "5");
                player.setAmountToWithdraw(5);
                break;

            case 22045:
                player.getPacketSender().sendString(22046, "10");
                player.setAmountToWithdraw(10);
                break;

            case 22044:
                player.setInputHandling(new EnterAmountToWithdraw());
                player.getPacketSender().sendEnterAmountPrompt("How many would you like to withdraw?");
                break;

            case 22043:
                player.getPacketSender().sendString(22046, "All");
                player.setAmountToWithdraw(Integer.MAX_VALUE);
                break;




            case 22052:
                int quantity = player.getAmountToWithdraw();
                int presses = player.getWithdrawButtonPresses();
                presses ++;
                player.setWithdrawButtonPresses(presses);

                if (quantity == 1) {
                    quantity = 5;
                    player.getPacketSender().sendString(22046, String.valueOf(quantity));
                    player.setAmountToWithdraw(quantity);
                }
                if (quantity == 5) {
                    quantity = 10;
                    player.getPacketSender().sendString(22046, String.valueOf(quantity));
                    player.setAmountToWithdraw(quantity);
                }
                if (quantity == 10) {
                    player.setInputHandling(new EnterAmountToWithdraw());
                    player.getPacketSender().sendEnterAmountPrompt("How many would you like to withdraw?");
                }
                if (quantity > 10 && quantity < Integer.MAX_VALUE) {
                    player.getPacketSender().sendString(22046, "All");
                    player.setAmountToWithdraw(Integer.MAX_VALUE);
                }
                if (quantity == Integer.MAX_VALUE) {
                    quantity = 1;
                    player.getPacketSender().sendString(22046, String.valueOf(quantity));
                    player.setAmountToWithdraw(quantity);
                }

               break;

            case 6033:
                if (!player.getClickDelay().elapsed(30000) || player.getLocation() == Location.INSTANCE_ARENA) {
                    return;
                }
                if (player.getRights() == PlayerRights.PLAYER) {
                    if (player.getMoneyInPouchAsInt() >= 5000000) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 5000000);
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                        player.getPacketSender().sendMessage("5m instance fee paid! Good luck.");
                        player.getPacketSender().sendMessage("Donors get free instances and perks! ::benefits to find out more <3");
                        InstanceArena.spawnMan(player);
                    } else {
                        player.getPacketSender().sendMessage("You need 5m in your pouch to buy an instance.");
                        return;
                    }
                }


                InstanceArena.spawnMan(player);
                break;
            case 6035:
                if (!player.getClickDelay().elapsed(30000) || player.getLocation() == Location.INSTANCE_ARENA) {
                    return;
                }
                if (player.getRights() == PlayerRights.PLAYER) {
                    if (player.getMoneyInPouchAsInt() >= 5000000) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 5000000);
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                        player.getPacketSender().sendMessage("5m instance fee paid! Good luck.");
                        player.getPacketSender().sendMessage("Donors get free instances and perks! ::benefits to find out more <3");
                        InstanceArena.spawnKBD(player);
                    } else {
                        player.getPacketSender().sendMessage("You need 5m in your pouch to buy an instance.");
                        return;
                    }
                }

                InstanceArena.spawnKBD(player);
                break;
            case 6036:
                if (!player.getClickDelay().elapsed(30000) || player.getLocation() == Location.INSTANCE_ARENA) {
                    return;
                }
                if (player.getRights() == PlayerRights.PLAYER) {
                    if (player.getMoneyInPouchAsInt() >= 5000000) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 5000000);
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                        player.getPacketSender().sendMessage("5m instance fee paid! Good luck.");
                        player.getPacketSender().sendMessage("Donors get free instances and perks! ::benefits to find out more <3");
                        InstanceArena.spawnRockCrabMadness(player);
                    } else {
                        player.getPacketSender().sendMessage("You need 5m in your pouch to buy an instance.");
                        return;
                    }
                }

                InstanceArena.spawnRockCrabMadness(player);
                break;

            case 6037:
                if (!player.getClickDelay().elapsed(30000) || player.getLocation() == Location.INSTANCE_ARENA) {
                    return;
                }
                if (player.getRights() == PlayerRights.PLAYER) {
                    if (player.getMoneyInPouchAsInt() >= 5000000) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 5000000);
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                        player.getPacketSender().sendMessage("5m instance fee paid! Good luck.");
                        player.getPacketSender().sendMessage("Donors get free instances and perks! ::benefits to find out more <3");
                        InstanceArena.spawnIronDragon(player);
                    } else {
                        player.getPacketSender().sendMessage("You need 5m in your pouch to buy an instance.");
                        return;
                    }
                }

                InstanceArena.spawnIronDragon(player);
                break;

            case 6038:
                if (!player.getClickDelay().elapsed(30000) || player.getLocation() == Location.INSTANCE_ARENA) {
                    return;
                }
                if (player.getRights() == PlayerRights.PLAYER) {
                    if (player.getMoneyInPouchAsInt() >= 5000000) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 5000000);
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                        player.getPacketSender().sendMessage("5m instance fee paid! Good luck.");
                        player.getPacketSender().sendMessage("Donors get free instances and perks! ::benefits to find out more <3");
                        InstanceArena.spawnSteelDragon(player);
                    } else {
                        player.getPacketSender().sendMessage("You need 5m in your pouch to buy an instance.");
                        return;
                    }
                }

                InstanceArena.spawnSteelDragon(player);
                break;

            case 6039:
                if (!player.getClickDelay().elapsed(30000) || player.getLocation() == Location.INSTANCE_ARENA) {
                    return;
                }
                if (player.getRights() == PlayerRights.PLAYER) {
                    if (player.getMoneyInPouchAsInt() >= 5000000) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 5000000);
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                        player.getPacketSender().sendMessage("5m instance fee paid! Good luck.");
                        player.getPacketSender().sendMessage("Donors get free instances and perks! ::benefits to find out more <3");
                        InstanceArena.spawnFrostDragon(player);
                    } else {
                        player.getPacketSender().sendMessage("You need 5m in your pouch to buy an instance.");
                        return;
                    }
                }

                InstanceArena.spawnFrostDragon(player);
                break;

            case 6040:
                if (!player.getClickDelay().elapsed(30000) || player.getLocation() == Location.INSTANCE_ARENA) {
                    return;
                }
                if (player.getRights() == PlayerRights.PLAYER) {
                    if (player.getMoneyInPouchAsInt() >= 5000000) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 5000000);
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                        player.getPacketSender().sendMessage("5m instance fee paid! Good luck.");
                        player.getPacketSender().sendMessage("Donors get free instances and perks! ::benefits to find out more <3");
                        InstanceArena.spawnTzHaar(player);
                    } else {
                        player.getPacketSender().sendMessage("You need 5m in your pouch to buy an instance.");
                        return;
                    }
                }

                InstanceArena.spawnTzHaar(player);
                break;

            case 6041:
                if (!player.getClickDelay().elapsed(30000) || player.getLocation() == Location.INSTANCE_ARENA) {
                    return;
                }
                if (player.getRights() == PlayerRights.PLAYER) {
                    if (player.getMoneyInPouchAsInt() >= 5000000) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 5000000);
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                        player.getPacketSender().sendMessage("5m instance fee paid! Good luck.");
                        player.getPacketSender().sendMessage("Donors get free instances and perks! ::benefits to find out more <3");
                        InstanceArena.spawnSlashBash(player);
                    } else {
                        player.getPacketSender().sendMessage("You need 5m in your pouch to buy an instance.");
                        return;
                    }
                }

                InstanceArena.spawnSlashBash(player);
                break;

            case 6042:
                if (!player.getClickDelay().elapsed(30000) || player.getLocation() == Location.INSTANCE_ARENA) {
                    return;
                }
                if (player.getRights() == PlayerRights.PLAYER) {
                    if (player.getMoneyInPouchAsInt() >= 5000000) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 5000000);
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                        player.getPacketSender().sendMessage("5m instance fee paid! Good luck.");
                        player.getPacketSender().sendMessage("Donors get free instances and perks! ::benefits to find out more <3");
                        InstanceArena.spawnSmokeDevil(player);
                    } else {
                        player.getPacketSender().sendMessage("You need 5m in your pouch to buy an instance.");
                        return;
                    }
                }

                InstanceArena.spawnSmokeDevil(player);
                break;


            case 6043:
                if (!player.getClickDelay().elapsed(30000) || player.getLocation() == Location.INSTANCE_ARENA) {
                    return;
                }
                if (player.getRights() == PlayerRights.PLAYER) {
                    if (player.getMoneyInPouchAsInt() >= 5000000) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 5000000);
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                        player.getPacketSender().sendMessage("5m instance fee paid! Good luck.");
                        player.getPacketSender().sendMessage("Donors get free instances and perks! ::benefits to find out more <3");
                        InstanceArena.spawnSupreme(player);
                    } else {
                        player.getPacketSender().sendMessage("You need 5m in your pouch to buy an instance.");
                        return;
                    }
                }

                InstanceArena.spawnSupreme(player);
                break;

            case 6044:
                if (!player.getClickDelay().elapsed(30000) || player.getLocation() == Location.INSTANCE_ARENA) {
                    return;
                }
                if (player.getRights() == PlayerRights.PLAYER) {
                    if (player.getMoneyInPouchAsInt() >= 5000000) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 5000000);
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                        player.getPacketSender().sendMessage("5m instance fee paid! Good luck.");
                        player.getPacketSender().sendMessage("Donors get free instances and perks! ::benefits to find out more <3");
                        InstanceArena.spawnPrime(player);
                    } else {
                        player.getPacketSender().sendMessage("You need 5m in your pouch to buy an instance.");
                        return;
                    }
                }

                InstanceArena.spawnPrime(player);
                break;

            case 6045:
                if (!player.getClickDelay().elapsed(30000) || player.getLocation() == Location.INSTANCE_ARENA) {
                    return;
                }
                if (player.getRights() == PlayerRights.PLAYER) {
                    if (player.getMoneyInPouchAsInt() >= 5000000) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 5000000);
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                        player.getPacketSender().sendMessage("5m instance fee paid! Good luck.");
                        player.getPacketSender().sendMessage("Donors get free instances and perks! ::benefits to find out more <3");
                        InstanceArena.spawnRex(player);
                    } else {
                        player.getPacketSender().sendMessage("You need 5m in your pouch to buy an instance.");
                        return;
                    }
                }

                InstanceArena.spawnRex(player);
                break;

            case 6046:
                if (!player.getClickDelay().elapsed(30000) || player.getLocation() == Location.INSTANCE_ARENA) {
                    return;
                }
                if (player.getRights() == PlayerRights.PLAYER) {
                    if (player.getMoneyInPouchAsInt() >= 5000000) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 5000000);
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                        player.getPacketSender().sendMessage("5m instance fee paid! Good luck.");
                        player.getPacketSender().sendMessage("Donors get free instances and perks! ::benefits to find out more <3");
                        InstanceArena.spawnIceWorm(player);
                    } else {
                        player.getPacketSender().sendMessage("You need 5m in your pouch to buy an instance.");
                        return;
                    }
                }

                InstanceArena.spawnIceWorm(player);
                break;

            case 6047:
                if (!player.getClickDelay().elapsed(30000) || player.getLocation() == Location.INSTANCE_ARENA) {
                    return;
                }
                if (player.getRights() == PlayerRights.PLAYER) {
                    if (player.getMoneyInPouchAsInt() >= 5000000) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 5000000);
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                        player.getPacketSender().sendMessage("5m instance fee paid! Good luck.");
                        player.getPacketSender().sendMessage("Donors get free instances and perks! ::benefits to find out more <3");
                        InstanceArena.spawnDesertWorm(player);
                    } else {
                        player.getPacketSender().sendMessage("You need 5m in your pouch to buy an instance.");
                        return;
                    }
                }

                InstanceArena.spawnDesertWorm(player);
                break;

            case 6048:
                if (!player.getClickDelay().elapsed(30000) || player.getLocation() == Location.INSTANCE_ARENA) {
                    return;
                }
                if (player.getRights() == PlayerRights.PLAYER) {
                    if (player.getMoneyInPouchAsInt() >= 5000000) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 5000000);
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                        player.getPacketSender().sendMessage("5m instance fee paid! Good luck.");
                        player.getPacketSender().sendMessage("Donors get free instances and perks! ::benefits to find out more <3");
                        InstanceArena.spawnJungleWorm(player);
                    } else {
                        player.getPacketSender().sendMessage("You need 5m in your pouch to buy an instance.");
                        return;
                    }
                }

                InstanceArena.spawnJungleWorm(player);
                break;

            case 6049:
                if (!player.getClickDelay().elapsed(30000) || player.getLocation() == Location.INSTANCE_ARENA) {
                    return;
                }
                if (player.getRights() == PlayerRights.PLAYER) {
                    if (player.getMoneyInPouchAsInt() >= 5000000) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 5000000);
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                        player.getPacketSender().sendMessage("5m instance fee paid! Good luck.");
                        player.getPacketSender().sendMessage("Donors get free instances and perks! ::benefits to find out more <3");
                        InstanceArena.spawnCerberus(player);
                    } else {
                        player.getPacketSender().sendMessage("You need 5m in your pouch to buy an instance.");
                        return;
                    }
                }

                InstanceArena.spawnCerberus(player);
                break;

            case 6050:
                if (!player.getClickDelay().elapsed(30000) || player.getLocation() == Location.INSTANCE_ARENA) {
                    return;
                }
                if (player.getRights() == PlayerRights.PLAYER) {
                    if (player.getMoneyInPouchAsInt() >= 5000000) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 5000000);
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                        player.getPacketSender().sendMessage("5m instance fee paid! Good luck.");
                        player.getPacketSender().sendMessage("Donors get free instances and perks! ::benefits to find out more <3");
                        InstanceArena.spawnChaosDruid(player);
                    } else {
                        player.getPacketSender().sendMessage("You need 5m in your pouch to buy an instance.");
                        return;
                    }
                }

                InstanceArena.spawnChaosDruid(player);
                break;

            case 6051:
                if (!player.getClickDelay().elapsed(30000) || player.getLocation() == Location.INSTANCE_ARENA) {
                    return;
                }
                if (player.getRights() == PlayerRights.PLAYER) {
                    if (player.getMoneyInPouchAsInt() >= 5000000) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 5000000);
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                        player.getPacketSender().sendMessage("5m instance fee paid! Good luck.");
                        player.getPacketSender().sendMessage("Donors get free instances and perks! ::benefits to find out more <3");
                        InstanceArena.spawnTormentedDemon(player);
                    } else {
                        player.getPacketSender().sendMessage("You need 5m in your pouch to buy an instance.");
                        return;
                    }
                }

                InstanceArena.spawnTormentedDemon(player);
                break;

            case 6052:
                if (!player.getClickDelay().elapsed(30000) || player.getLocation() == Location.INSTANCE_ARENA) {
                    return;
                }
                if (player.getRights() == PlayerRights.PLAYER) {
                    if (player.getMoneyInPouchAsInt() >= 5000000) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 5000000);
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                        player.getPacketSender().sendMessage("5m instance fee paid! Good luck.");
                        player.getPacketSender().sendMessage("Donors get free instances and perks! ::benefits to find out more <3");
                        InstanceArena.spawnGorilla(player);
                    } else {
                        player.getPacketSender().sendMessage("You need 5m in your pouch to buy an instance.");
                        return;
                    }
                }

                InstanceArena.spawnGorilla(player);
                break;

            case 6053:
                if (!player.getClickDelay().elapsed(30000) || player.getLocation() == Location.INSTANCE_ARENA) {
                    return;
                }
                if (player.getRights() == PlayerRights.PLAYER) {
                    if (player.getMoneyInPouchAsInt() >= 5000000) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 5000000);
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                        player.getPacketSender().sendMessage("5m instance fee paid! Good luck.");
                        player.getPacketSender().sendMessage("Donors get free instances and perks! ::benefits to find out more <3");
                        InstanceArena.spawnBork(player);
                    } else {
                        player.getPacketSender().sendMessage("You need 5m in your pouch to buy an instance.");
                        return;
                    }
                }

                InstanceArena.spawnBork(player);
                break;

            case 6054:
                if (!player.getClickDelay().elapsed(30000) || player.getLocation() == Location.INSTANCE_ARENA) {
                    return;
                }
                if (player.getRights() == PlayerRights.PLAYER) {
                    if (player.getMoneyInPouchAsInt() >= 5000000) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 5000000);
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                        player.getPacketSender().sendMessage("5m instance fee paid! Good luck.");
                        player.getPacketSender().sendMessage("Donors get free instances and perks! ::benefits to find out more <3");
                        InstanceArena.spawnChaosElemental(player);
                    } else {
                        player.getPacketSender().sendMessage("You need 5m in your pouch to buy an instance.");
                        return;
                    }
                }

                InstanceArena.spawnChaosElemental(player);
                break;

            case 6055:
                if (!player.getClickDelay().elapsed(30000) || player.getLocation() == Location.INSTANCE_ARENA) {
                    return;
                }
                if (player.getRights() == PlayerRights.PLAYER) {
                    if (player.getMoneyInPouchAsInt() >= 5000000) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 5000000);
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                        player.getPacketSender().sendMessage("5m instance fee paid! Good luck.");
                        player.getPacketSender().sendMessage("Donors get free instances and perks! ::benefits to find out more <3");
                        InstanceArena.spawnLizardShaman(player);
                    } else {
                        player.getPacketSender().sendMessage("You need 5m in your pouch to buy an instance.");
                        return;
                    }
                }

                InstanceArena.spawnLizardShaman(player);
                break;

            case 6056:
                if (!player.getClickDelay().elapsed(30000) || player.getLocation() == Location.INSTANCE_ARENA) {
                    return;
                }
                if (player.getRights() == PlayerRights.PLAYER) {
                    if (player.getMoneyInPouchAsInt() >= 5000000) {
                        player.setMoneyInPouch(player.getMoneyInPouch() - 5000000);
                        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
                        player.getPacketSender().sendMessage("5m instance fee paid! Good luck.");
                        player.getPacketSender().sendMessage("Donors get free instances and perks! ::benefits to find out more <3");
                        InstanceArena.spawnZulrah(player);
                    } else {
                        player.getPacketSender().sendMessage("You need 5m in your pouch to buy an instance.");
                        return;
                    }
                }

                InstanceArena.spawnZulrah(player);
                break;


            case 3221:
                if (player.getDifficulty() == Difficulty.Default) {
                    player.setDifficulty(Difficulty.Easy);
                    player.getPacketSender().sendInterfaceRemoval();
                    player.getPacketSender().sendMessage("Difficulty :" + player.getDifficulty().toString());
                    PlayerPanel.refreshPanel(player);
                } else
                    DialogueManager.start(player, 175);
                player.getPacketSender().sendString(981, "Select Easy Difficulty?");
                player.getPacketSender().sendString(982, "To change modes later, you'd need to reset your skills!");
                player.getPacketSender().sendString(983, "You'll receive an XP Boost of @blu@300%");
                player.getPacketSender().sendString(984, "Your drop rate will not be boosted.");
                player.setDialogueActionId(79);
                break;
            case 3215:
                if (player.getDifficulty() == Difficulty.Easy) {
                    player.getPacketSender().sendMessage("You cannot change to a harder difficulty without resetting your stats!");
                    return;
                } else {
                    if (player.getDifficulty() == Difficulty.Default) {
                        player.setDifficulty(Difficulty.Medium);
                        player.getPacketSender().sendInterfaceRemoval();
                        player.getPacketSender().sendMessage("Difficulty :" + player.getDifficulty().toString());
                        PlayerPanel.refreshPanel(player);
                    } else
                        DialogueManager.start(player, 175);
                    player.getPacketSender().sendString(981, "Select Medium Difficulty?");
                    player.getPacketSender().sendString(982, "To up difficulty later, you'd need to reset your skills!");
                    player.getPacketSender().sendString(983, "You'll receive the standard XP rate.");
                    player.getPacketSender().sendString(984, "You'll receive a Drop Rate boost of @blu@2%");
                    player.setDialogueActionId(80);
                }
                break;
            case 3218:
                if (player.getDifficulty() == Difficulty.Easy || player.getDifficulty() == Difficulty.Medium) {
                    player.getPacketSender().sendMessage("You cannot change to a harder difficulty without resetting your stats!");
                    return;
                } else {
                    if (player.getDifficulty() == Difficulty.Default) {
                        player.setDifficulty(Difficulty.Hard);
                        player.getPacketSender().sendInterfaceRemoval();
                        player.getPacketSender().sendMessage("Difficulty :" + player.getDifficulty().toString());
                        PlayerPanel.refreshPanel(player);
                    } else
                        DialogueManager.start(player, 175);
                    player.getPacketSender().sendString(981, "Select Hard Difficulty?");
                    player.getPacketSender().sendString(982, "To up difficulty later, you'd need to reset your skills!");
                    player.getPacketSender().sendString(983, "You'll receive an XP Rate of @red@50%");
                    player.getPacketSender().sendString(984, "You'll receive a Drop Rate boost of @blu@5%");
                    player.setDialogueActionId(81);
                }
                break;
            case 3229:
                if (player.getDifficulty() == Difficulty.Easy || player.getDifficulty() == Difficulty.Medium
                        || player.getDifficulty() == Difficulty.Hard) {
                    player.getPacketSender().sendMessage("You cannot change to a harder difficulty without resetting your stats!");
                    return;
                } else {
                    if (player.getDifficulty() == Difficulty.Default) {
                        player.setDifficulty(Difficulty.Insane);
                        player.getPacketSender().sendInterfaceRemoval();
                        player.getPacketSender().sendMessage("Difficulty :" + player.getDifficulty().toString());
                        PlayerPanel.refreshPanel(player);
                    } else
                        DialogueManager.start(player, 175);
                    player.getPacketSender().sendString(981, "Select Insane Difficulty?");
                    player.getPacketSender().sendString(982, "To up difficulty later, you'd need to reset your skills!");
                    player.getPacketSender().sendString(983, "You'll receive an XP Rate of @red@25%");
                    player.getPacketSender().sendString(984, "You'll receive a Drop Rate boost of @blu@10%");
                    player.setDialogueActionId(82);
                }
                break;
            case 3235:
                if (player.getDifficulty() == Difficulty.Easy || player.getDifficulty() == Difficulty.Medium
                        || player.getDifficulty() == Difficulty.Hard || player.getDifficulty() == Difficulty.Insane) {
                    player.getPacketSender().sendMessage("You cannot change to a harder difficulty without resetting your stats!");
                    return;
                } else {
                    if (player.getDifficulty() == Difficulty.Default) {
                        player.setDifficulty(Difficulty.Zezima);
                        player.getPacketSender().sendInterfaceRemoval();
                        player.getPacketSender().sendMessage("Difficulty :" + player.getDifficulty().toString());
                        PlayerPanel.refreshPanel(player);
                    } else
                        DialogueManager.start(player, 175);
                    player.getPacketSender().sendString(981, "Select Zezima Difficulty?");
                    player.getPacketSender().sendString(982, "You can lower the difficulty at any time!");
                    player.getPacketSender().sendString(983, "You'll receive an XP Rate of @red@10%");
                    player.getPacketSender().sendString(984, "You'll receive a Drop Rate boost of @blu@20%");
                    player.setDialogueActionId(83);
                }
                break;
            case 3260:
                if (player.getEquipment().getFreeSlots() != player.getEquipment().capacity()) {
                    player.getPacketSender().sendMessage("@red@You need to remove all of your items first!");
                    player.getPacketSender().sendInterfaceRemoval();
                    return;
                } else {
                    DialogueManager.start(player, 177);
                    player.getPacketSender().sendString(358, "Yes, I want to increase my difficulty..");
                    player.setDialogueActionId(84);
                }
                break;


            case 26113:
                player.dropLogOrder = !player.dropLogOrder;
                if (player.dropLogOrder) {
                    player.getPA().sendFrame126(26113, "Oldest to Newest");
                } else {
                    player.getPA().sendFrame126(26113, "Newest to Oldest");
                }
                break;
            case -29031:
                ProfileViewing.rate(player, true);
                break;
            case -29028:
                ProfileViewing.rate(player, false);
                break;
            case -27454:
            case -27534:
            case 5384:
            case 12729:
                player.getPacketSender().sendInterfaceRemoval();
                player.getPacketSender().sendRichPresenceState("Exploring Janus..");
                player.getPacketSender().sendSmallImageKey("home");
                player.getPacketSender().sendRichPresenceSmallPictureText("Combat Lvl: " + player.getSkillManager().getCombatLevel());
                break;
            case -17631:
                KBD.closeInterface(player);
                break;

            case -11438:
                player.getPlayerOwnedShopManager().openEditor();
                break;

            case -17629:
                if (player.getLocation() == Location.KBD) {
                    KBD.nextItem(player);
                }
                if (player.getLocation() == Location.SLASH_BASH) {
                    SLASHBASH.nextItem(player);
                }
                if (player.getLocation() == Location.TORM_DEMONS) {
                    TDS.nextItem(player);
                }
                if (player.getLocation() == Location.CORPOREAL_BEAST) {
                    CORP.nextItem(player);
                }
                if (player.getLocation() == Location.DAGANNOTH_DUNGEON) {
                    DAGS.nextItem(player);
                }
                if (player.getLocation() == Location.BANDOS_AVATAR) {
                    AVATAR.nextItem(player);
                }
                if (player.getLocation() == Location.KALPHITE_QUEEN) {
                    KALPH.nextItem(player);
                }
                if (player.getLocation() == Location.PHOENIX) {
                    PHEON.nextItem(player);
                }
                if (player.getLocation() == Location.GLACORS) {
                    GLAC.nextItem(player);
                }
                if (player.getLocation() == Location.SKOTIZO) {
                    SKOT.nextItem(player);
                }
                if (player.getLocation() == Location.CERBERUS) {
                    CERB.nextItem(player);
                }
                if (player.getLocation() == Location.NEX) {
                    NEXX.nextItem(player);
                }
                if (player.getLocation() == Location.GODWARS_DUNGEON) {
                    GWD.nextItem(player);
                }
                if (player.getLocation() == Location.BORK) {
                    BORKS.nextItem(player);
                }
                if (player.getLocation() == Location.LIZARDMAN) {
                    LIZARD.nextItem(player);
                }
                if (player.getLocation() == Location.BARRELCHESTS) {
                    BARRELS.nextItem(player);
                }
                break;

            case -17630:
                if (player.getLocation() == Location.KBD) {
                    KBD.previousItem(player);
                }
                if (player.getLocation() == Location.SLASH_BASH) {
                    SLASHBASH.previousItem(player);
                }
                if (player.getLocation() == Location.TORM_DEMONS) {
                    TDS.previousItem(player);
                }
                if (player.getLocation() == Location.CORPOREAL_BEAST) {
                    CORP.previousItem(player);
                }
                if (player.getLocation() == Location.DAGANNOTH_DUNGEON) {
                    DAGS.previousItem(player);
                }
                if (player.getLocation() == Location.BANDOS_AVATAR) {
                    AVATAR.previousItem(player);
                }
                if (player.getLocation() == Location.KALPHITE_QUEEN) {
                    KALPH.previousItem(player);
                }
                if (player.getLocation() == Location.PHOENIX) {
                    PHEON.previousItem(player);
                }
                if (player.getLocation() == Location.GLACORS) {
                    GLAC.previousItem(player);
                }
                if (player.getLocation() == Location.SKOTIZO) {
                    SKOT.previousItem(player);
                }
                if (player.getLocation() == Location.CERBERUS) {
                    CERB.previousItem(player);
                }
                if (player.getLocation() == Location.NEX) {
                    NEXX.previousItem(player);
                }
                if (player.getLocation() == Location.GODWARS_DUNGEON) {
                    GWD.previousItem(player);
                }
                if (player.getLocation() == Location.BORK) {
                    BORKS.previousItem(player);
                }
                if (player.getLocation() == Location.LIZARDMAN) {
                    LIZARD.previousItem(player);
                }
                if (player.getLocation() == Location.BARRELCHESTS) {
                    BARRELS.previousItem(player);
                }
                break;


            case 25253:
                DropLog.open(player);
                break;
            case 1036:
                player.getPacketSender().sendRichPresenceState("AFK Zone");
                player.getPacketSender().sendSmallImageKey("afk");
                TeleportHandler.teleportPlayer(player, new Position(2730, 5329), player.getSpellbook().getTeleportType());
                break;
//		case -26376:
            // PlayersOnlineInterface.showInterface(player);
//			break;
            case -26376:
                player.getPacketSender().sendTabInterface(GameSettings.QUESTS_TAB, 46343);
                StaffList.updateInterface(player);
                PlayerPanel.refreshPanel(player);
                break;
            case 32388:
                player.getPacketSender().sendTabInterface(GameSettings.QUESTS_TAB, 639); // 26600
                PlayerPanel.refreshPanel(player);
                break;
            case -26359:
                player.setExperienceLocked(!player.experienceLocked());
                player.sendMessage("Your experience is now: " + (player.experienceLocked() ? "locked" : "unlocked"));
                break;
            case -26360:
                player.sendMessage("You have donated a total of: $" + player.getAmountDonated() + "!");
                break;
            case 27229:
                DungeoneeringParty.create(player);
                break;
		/*case 3229:
			player.sendMessage("Common Costs 50 Janus Points.");
			break;
		case 3218:
			player.sendMessage("Uncommon Package Costs 100 Janus Points.");
			break;
		case 3215:
			player.sendMessage("Extreme Package Costs 200 Janus Points.");
			break;
		case 3221:
			player.sendMessage("Rare Package Costs 150 Janus Points.");
			break;
		case 3235:
			player.sendMessage("Legendary Package Costs 250 Janus Points.");
			break;
		case 3204:
			if(player.getJanusPoints() >= 150) {
				 player.getInventory().add(15371, 1);
				 player.incrementJanusPoints(150);
				 PlayerPanel.refreshPanel(player);
			}
			break;
		case 3206:
			if(player.getJanusPoints() >= 200) {
				 player.getInventory().add(15372, 1);
				 player.incrementJanusPoints(200);
				 PlayerPanel.refreshPanel(player);
			}
			break;*/

		/*case 3208:
			if(player.getJanusPoints() >= 100) {
				 player.getInventory().add(15370, 1);
				 player.incrementJanusPoints(100);
				 PlayerPanel.refreshPanel(player);
			}
			break;
		case 3225:
			if(player.getJanusPoints() >= 50) {
				 player.getInventory().add(15369, 1);
				 player.incrementJanusPoints(50);
				 PlayerPanel.refreshPanel(player);
			}
			break;
		case 3240:
			if(player.getJanusPoints() >= 250) {
				 player.getInventory().add(15373, 1);
				 player.incrementJanusPoints(250);
				 PlayerPanel.refreshPanel(player);
			}
			break;*/
            case 26226:
            case 26229:
                if (Dungeoneering.doingDungeoneering(player)) {
                    DialogueManager.start(player, 114);
                    player.setDialogueActionId(71);
                } else {
                    Dungeoneering.leave(player, false, true);
                }
                break;
            case 26244:
            case 26247:
                if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty() != null) {
                    if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty().getOwner().getUsername()
                            .equals(player.getUsername())) {
                        DialogueManager.start(player, id == 26247 ? 106 : 105);
                        player.setDialogueActionId(id == 26247 ? 68 : 67);
                    } else {
                        player.getPacketSender().sendMessage("Only the party owner can change this setting.");
                    }
                }
                break;
            case 28180:
                player.getPacketSender().sendInterfaceRemoval();
                if (player.getSummoning().getFamiliar() != null) {
                    player.getPacketSender()
                            .sendMessage("You must dismiss your familiar before being allowed to enter a dungeon.");
                    player.getPacketSender().sendMessage("You must dismiss your familiar before joining the dungeon");
                    return;
                }

                TeleportHandler.teleportPlayer(player, new Position(3450, 3715), player.getSpellbook().getTeleportType());
                break;
            case 14176:
                player.setUntradeableDropItem(null);
                player.getPacketSender().sendInterfaceRemoval();
                break;
            case 14175:
                player.getPacketSender().sendInterfaceRemoval();
                if (player.getUntradeableDropItem() != null
                        && player.getInventory().contains(player.getUntradeableDropItem().getId())) {
                    ItemBinding.unbindItem(player, player.getUntradeableDropItem().getId());
                    player.getInventory().delete(player.getUntradeableDropItem());
                    player.getPacketSender().sendMessage("Your item vanishes as it hits the floor.");
                    Sounds.sendSound(player, Sound.DROP_ITEM);
                }
                player.setUntradeableDropItem(null);
                break;
            case 1013:
                player.getSkillManager().setTotalGainedExp(0);
                break;
            case -26369:
                if (WellOfGoodwill.isActive()) {
                    player.getPacketSender().sendMessage(
                            "<img=10> <col=008FB2>The Fountain of Goodwill is granting 30% bonus experience for another "
                                    + WellOfGoodwill.getMinutesRemaining() + " minutes.");
                } else {
                    player.getPacketSender()
                            .sendMessage("<img=10> <col=008FB2>The Fountain of Goodwill needs another "
                                    + Misc.insertCommasToNumber("" + WellOfGoodwill.getMissingAmount())
                                    + " coins before becoming full.");
                }
                break;
            case -26368:

                break;
            case -26374:
                KillsTracker.open(player);
                break;
            case -26373:
                DropLog.open(player);
                break;

            case -30281:
                player.getPacketSender().sendInterfaceRemoval();
                break;
            case 26614:
                DropLog.open(player);
                break;

            case -10531:
                if (player.isKillsTrackerOpen()) {
                    player.setKillsTrackerOpen(false);
                    player.getPacketSender().sendTabInterface(GameSettings.QUESTS_TAB, 639);
                    PlayerPanel.refreshPanel(player);
                }
                break;

            case 28177:
                if (!TeleportHandler.checkReqs(player, null)) {
                    player.getSkillManager().stopSkilling();
                    return;
                }
                if (!player.getClickDelay().elapsed(4500) || player.getMovementQueue().isLockMovement()) {
                    player.getSkillManager().stopSkilling();
                    return;
                }
                if (player.getLocation() == Location.CONSTRUCTION) {
                    player.getSkillManager().stopSkilling();
                    return;
                }
                player.getPacketSender().sendRichPresenceState("Training Construction!");
                player.getPacketSender().sendSmallImageKey("construction");
                player.getPacketSender().sendRichPresenceSmallPictureText("Lvl: " + player.getSkillManager().getCurrentLevel(Skill.CONSTRUCTION));
                Construction.newHouse(player);
                Construction.enterHouse(player, player, true, true);
                player.getSkillManager().stopSkilling();
                break;
            case -30282:
                KillsTracker.openBoss(player);
                break;
            case -10283:
                KillsTracker.open(player);
                break;

            case 11014:
                Teleporting.openTab(player, -4928);
                break;
//		case -26333:
//			player.getPacketSender().sendString(1, "www.janus.rip");
//			player.getPacketSender().sendMessage("Attempting to open: www.janus.rip");
//			break;
		/*case -26332:
			player.getPacketSender().sendString(1, "www.janus.rip");
			player.getPacketSender().sendMessage("Attempting to open: www.janus.rip");
			break;
		case -26331:
			player.getPacketSender().sendString(1, "www.janus.rip");
			player.getPacketSender().sendMessage("Attempting to open: www.janus.rip");
			break;
		case -26330:
			player.getPacketSender().sendString(1, "www.janus.rip");
			player.getPacketSender().sendMessage("Attempting to open: www.janus.rip");
			break;
		case -26329:
			player.getPacketSender().sendString(1, "www.janus.rip");
			player.getPacketSender().sendMessage("Attempting to open: www.janus.rip");
			break;
		case -26328:
			player.getPacketSender().sendString(1, "www.janus.rip");
			player.getPacketSender().sendMessage("Attempting to open: www.janus.rip");
			break;*/
            case -26337:
                RecipeForDisaster.openQuestLog(player);
                break;
            case -26336:
                Nomad.openQuestLog(player);
                break;
            case -26375:
                ProfileViewing.view(player, player);
                break;
            case 350:
                player.getPacketSender()
                        .sendMessage("To autocast a spell, please right-click it and choose the autocast option.")
                        .sendTab(GameSettings.MAGIC_TAB).sendConfig(108, player.getAutocastSpell() == null ? 3 : 1);
                break;
            case 12162:
                DialogueManager.start(player, 61);
                player.setDialogueActionId(28);
                break;
            case 29335:
                if (player.getInterfaceId() > 0) {
                    player.getPacketSender()
                            .sendMessage("Please close the interface you have open before opening another one.");
                    return;
                }
                DialogueManager.start(player, 60);
                player.setDialogueActionId(27);
                break;
            case 29455:
                if (player.getInterfaceId() > 0) {
                    player.getPacketSender()
                            .sendMessage("Please close the interface you have open before opening another one.");
                    return;
                }
                ClanChatManager.toggleLootShare(player);
                break;
            case 8658:
                DialogueManager.start(player, 55);
                player.setDialogueActionId(26);
                break;
            case 11001:
                TeleportHandler.teleportPlayer(player, GameSettings.DEFAULT_POSITION.copy(),
                        player.getSpellbook().getTeleportType());
                player.getPacketSender().sendRichPresenceState("At home!");
                player.getPacketSender().sendSmallImageKey("home");
                player.getPacketSender().sendRichPresenceSmallPictureText("Combat Lvl: " + player.getSkillManager().getCombatLevel());
                break;
            case 8667:
                TeleportHandler.teleportPlayer(player, new Position(2742, 3443), player.getSpellbook().getTeleportType());
                break;
            case 8672:
                player.getPacketSender().sendRichPresenceState("Runecrafting");
                player.getPacketSender().sendSmallImageKey("runecrafting");
                player.getPacketSender().sendRichPresenceSmallPictureText("Lvl: " + player.getSkillManager().getCurrentLevel(Skill.RUNECRAFTING));
                TeleportHandler.teleportPlayer(player, new Position(2595, 4772), player.getSpellbook().getTeleportType());
                player.getPacketSender().sendMessage(
                        "<img=10> To get started with Runecrafting, buy a talisman and use the locate option on it.");
                break;
            case 8861:
                TeleportHandler.teleportPlayer(player, new Position(2914, 3450), player.getSpellbook().getTeleportType());
                break;
            case 8656:
                player.setDialogueActionId(47);
                DialogueManager.start(player, 86);
                break;
            case 8659:
                TeleportHandler.teleportPlayer(player, new Position(2734, 5354), player.getSpellbook().getTeleportType());
                break;
            case 8664:
                TeleportHandler.teleportPlayer(player, new Position(2698, 5352), player.getSpellbook().getTeleportType());
                break;
            case 8666:
                TeleportHandler.teleportPlayer(player, new Position(2696, 5332), player.getSpellbook().getTeleportType());
                break;

            /*
             * Teleporting Called Below
             */

            case -4914:
            case -4842:
            case -4911:
            case -4905:
            case -4908:
            case -4899:
            case -4896:
            case -4893:
            case -4890:
            case -4845:
            case -4839:
                Teleporting.teleport(player, id);
                break;

            case -4902:
                if (player.getSummoning().getFamiliar() != null) {
                    player.getPacketSender().sendMessage("You must dismiss your familiar before teleporting to the arena!");
                } else {
                    Teleporting.teleport(player, id);
                }
                break;

            case 10003:
                Teleporting.openTab(player, -4934);
                break;
            case -4934:
                Teleporting.openTab(player, -4934);
                break;
            case -4931:
                Teleporting.openTab(player, -4931);
                break;
            case -4928:
                Teleporting.openTab(player, -4928);
                break;
            case -4925:
                Teleporting.openTab(player, -4925);
                break;
            case -4922:
                Teleporting.openTab(player, -4922);
                break;
            case -4919:
                Teleporting.openTab(player, -4919);
                break;

            /*
             * End Teleporting
             */

            case 8671:
                player.setDialogueActionId(56);
                DialogueManager.start(player, 89);
                break;
            case 8670:
                TeleportHandler.teleportPlayer(player, new Position(2717, 3499), player.getSpellbook().getTeleportType());
                break;
            case 8668:
                TeleportHandler.teleportPlayer(player, new Position(2713, 5334), player.getSpellbook().getTeleportType());
                break;
            case 8665:
                TeleportHandler.teleportPlayer(player, new Position(3079, 3495), player.getSpellbook().getTeleportType());
                break;
            case 8662:
                TeleportHandler.teleportPlayer(player, new Position(2716, 5354), player.getSpellbook().getTeleportType());
                break;
            case 13928:
                player.getPacketSender().sendRichPresenceState("Farming..");
                player.getPacketSender().sendSmallImageKey("farming");
                player.getPacketSender().sendRichPresenceSmallPictureText("Lvl: " + player.getSkillManager().getCurrentLevel(Skill.FARMING));
                TeleportHandler.teleportPlayer(player, new Position(3052, 3304), player.getSpellbook().getTeleportType());
                break;
            case 28179:
                TeleportHandler.teleportPlayer(player, new Position(2704, 5365), player.getSpellbook().getTeleportType());
                player.getPacketSender().sendRichPresenceState("At home!");
                player.getPacketSender().sendSmallImageKey("home");
                player.getPacketSender().sendRichPresenceSmallPictureText("Combat Lvl: " + player.getSkillManager().getCombatLevel());
                break;
            case 28178:
                DialogueManager.start(player, 54);
                player.setDialogueActionId(25);
                break;
            case 1159: // Bones to Bananas
            case 15877:// Bones to peaches
            case 30306:
                MagicSpells.handleMagicSpells(player, id);
                break;
            case 10001:
                if (player.getInterfaceId() == -1) {
                    Consumables.handleHealAction(player);
                } else {
                    player.getPacketSender().sendMessage("You cannot heal yourself right now.");
                }
                break;
            case 18025:
                if (PrayerHandler.isActivated(player, PrayerHandler.AUGURY)) {
                    PrayerHandler.deactivatePrayer(player, PrayerHandler.AUGURY);
                } else {
                    PrayerHandler.activatePrayer(player, PrayerHandler.AUGURY);
                }
                break;
            case 18018:
                if (PrayerHandler.isActivated(player, PrayerHandler.RIGOUR)) {
                    PrayerHandler.deactivatePrayer(player, PrayerHandler.RIGOUR);
                } else {
                    PrayerHandler.activatePrayer(player, PrayerHandler.RIGOUR);
                }
                break;
            case 10000:
            case 913:
            case 950:
                if (player.getInterfaceId() < 0)
                    player.getPacketSender().sendInterface(40030);
                else
                    player.getPacketSender().sendMessage("Please close the interface you have open before doing this.");
                break;
            case 3546:
            case 3420:
                if (System.currentTimeMillis() - player.getTrading().lastAction <= 300)
                    return;
                player.getTrading().lastAction = System.currentTimeMillis();
                if (player.getTrading().inTrade()) {
                    player.getTrading().acceptTrade(id == 3546 ? 2 : 1);
                } else {
                    player.getPacketSender().sendInterfaceRemoval();
                }
                break;
            case 10162:
            case -18269:
            case 11729:
                player.getPacketSender().sendInterfaceRemoval();
                break;
            case 841:
                IngridientsBook.readBook(player, player.getCurrentBookPage() + 2, true);
                break;
            case 839:
                IngridientsBook.readBook(player, player.getCurrentBookPage() - 2, true);
                break;
            case 14922:
                player.getPacketSender().sendClientRightClickRemoval().sendInterfaceRemoval();
                break;
            case 14921:
                player.getPacketSender().sendMessage("Please visit the forums and ask for help in the support section.");
                break;
            /*case 5294:
                player.getPacketSender().sendClientRightClickRemoval().sendInterfaceRemoval();
                player.setDialogueActionId(player.getBankPinAttributes().hasBankPin() ? 8 : 7);
                DialogueManager.start(player,
                        DialogueManager.getDialogues().get(player.getBankPinAttributes().hasBankPin() ? 12 : 9));
                break;*/
            case 27653:
                if (!player.busy() && !player.getCombatBuilder().isBeingAttacked()
                        && !Dungeoneering.doingDungeoneering(player)) {
                    player.getSkillManager().stopSkilling();
                    player.getPriceChecker().open();
                } else {
                    player.getPacketSender().sendMessage("You cannot open this right now.");
                }
                break;
            case 2735:
            case 1511:
                if (player.getSummoning().getBeastOfBurden() != null) {
                    player.getSummoning().toInventory();
                    player.getPacketSender().sendInterfaceRemoval();
                } else {
                    player.getPacketSender().sendMessage("You do not have a familiar who can hold items.");
                }
                break;
            case -11501:
            case -11504:
            case -11498:
            case -11507:
            case 1020:
            case 1021:
            case 1019:
            case 1018:
                if (id == 1020 || id == -11504)
                    SummoningTab.renewFamiliar(player);
                else if (id == 1019 || id == -11501)
                    SummoningTab.callFollower(player);
                else if (id == 1021 || id == -11498)
                    SummoningTab.handleDismiss(player, false);
                else if (id == -11507)
                    player.getSummoning().store();
                else if (id == 1018)
                    player.getSummoning().toInventory();
                break;
            case 11004:
                player.getPacketSender().sendTab(GameSettings.SKILLS_TAB);
                DialogueManager.sendStatement(player, "Simply press on the skill you want to train in the skills tab.");
                player.setDialogueActionId(-1);
                break;
            case 8654:
            case 8657:
            case 8655:
            case 8663:
            case 8669:
            case 8660:
            case 11008:
                Teleporting.openTab(player, -4934);
                break;
            case 11017:
                Teleporting.openTab(player, -4931);
                break;
            case 11011:
                Teleporting.openTab(player, -4919);
                break;
            case 11020:
                Teleporting.openTab(player, -4922);
                break;
            case 2799:
            case 2798:
            case 1747:
            case 1748:
            case 8890:
            case 8886:
            case 8875:
            case 8871:
            case 8894:
                ChatboxInterfaceSkillAction.handleChatboxInterfaceButtons(player, id);
                break;
            case 14873:
            case 14874:
            case 14875:
            case 14876:
            case 14877:
            case 14878:
            case 14879:
            case 14880:
            case 14881:
            case 14882:
                BankPin.clickedButton(player, id);
                break;
            case 27005:
            case 22012:
                if (!player.isBanking() || player.getInterfaceId() != 5292)
                    return;
                Bank.depositItems(player, id == 27005 ? player.getEquipment() : player.getInventory(), false);
                break;
            case 27023:
                if (!player.isBanking() || player.getInterfaceId() != 5292)
                    return;
                if (player.getSummoning().getBeastOfBurden() == null) {
                    player.getPacketSender().sendMessage("You do not have a familiar which can hold items.");
                    return;
                }
                Bank.depositItems(player, player.getSummoning().getBeastOfBurden(), false);
                break;
            case 5294:
                if (!player.isBanking() || player.getInterfaceId() != 5292)
                    return;
                if (!player.getRights().isStaff()){
                    player.getPacketSender().sendMessage("This feature is being tested and will be enabled shortly!");
                    return;
                }

                player.setPlaceholders(!player.placeholdersEnabled());
                player.getPacketSender().sendToggle(116, player.placeholdersEnabled() ? 1 : 0);
                //System.out.println("Pressed button. Updated status. Placeholders now "+player.placeholdersEnabled());
                break;
            case 22008:
                if (!player.isBanking() || player.getInterfaceId() != 5292)
                    return;
                player.setNoteWithdrawal(!player.isNoteWithdrawal());
                player.getPacketSender().sendToggle(115, player.isNoteWithdrawal() ? 1 : 0);
                //System.out.println("Pressed button. Updated status. Note withdraw now "+player.isNoteWithdrawal());
                break;
            case 22053:
                if (!player.isBanking() || player.getInterfaceId() != 5292)
                    return;
                player.setInsertMode(!player.isInsertMode());
                player.getPacketSender().sendToggle(304, player.isInsertMode() ? 1 : 0);
                break;

            case 22047:
                if (!player.isBanking() || player.getInterfaceId() != 5292)
                    return;

                    player.getPacketSender().sendMessage("This feature is being tested and will be enabled shortly!"); //TODO Finish view equipment
                break;

            case 27009:
                MoneyPouch.toBank(player);
                break;
            case 27014:
            case 27015:
            case 27016:
            case 27017:
            case 27018:
            case 27019:
            case 27020:
            case 27021:
            case 27022:
                if (!player.isBanking())
                    return;
                if (player.getBankSearchingAttribtues().isSearchingBank())
                    BankSearchAttributes.stopSearch(player, true);
                int bankId = id - 27014;
                boolean empty = bankId > 0 ? Bank.isEmpty(player.getBank(bankId)) : false;

                if (!empty || bankId == 0) {
                    player.setCurrentBankTab(bankId);
                    player.getPacketSender().sendString(5385, "scrollreset");
                    player.getPacketSender().sendString(27002, Integer.toString(player.getCurrentBankTab()));
                    player.getPacketSender().sendString(27000, "1");
                    player.getBank(bankId).open();
                } else
                    player.getPacketSender().sendMessage("To create a new tab, please drag an item here.");
                break;
            case 22004:
                if (!player.isBanking())
                    return;
                if (!player.getBankSearchingAttribtues().isSearchingBank()) {
                    player.getBankSearchingAttribtues().setSearchingBank(true);
                    player.setInputHandling(new EnterSyntaxToBankSearchFor());
                    player.getPacketSender().sendEnterInputPrompt("What would you like to search for?");
                } else {
                    BankSearchAttributes.stopSearch(player, true);
                }
                break;
            case 32602:
                player.setInputHandling(new PosInput());
                player.getPacketSender().sendEnterInputPrompt("What/Who would you like to search for?");
                break;
            case 22845:
            case 24115:
            case 24010:
            case 24041:
            case 150:
                player.setAutoRetaliate(!player.isAutoRetaliate());
                break;
            case 29332:
                ClanChat clan = player.getCurrentClanChat();
                if (clan == null) {
                    player.getPacketSender().sendMessage("You are not in a clanchat channel.");
                    return;
                }
                ClanChatManager.leave(player, false);
                player.setClanChatName(null);
                break;
            case 29329:
                if (player.getInterfaceId() > 0) {
                    player.getPacketSender()
                            .sendMessage("Please close the interface you have open before opening another one.");
                    return;
                }
                player.setInputHandling(new EnterClanChatToJoin());
                player.getPacketSender().sendEnterInputPrompt("Enter the name of the clanchat channel you wish to join:");
                break;
            case 19158:
            case 152:
                if (player.getRunEnergy() <= 1) {
                    player.getPacketSender().sendMessage("You do not have enough energy to do this.");
                    player.setRunning(false);
                } else
                    player.setRunning(!player.isRunning());
                player.getPacketSender().sendRunStatus();
                break;

            case -282:
                DropLog.openRare(player);
                break;


            case 27658:
                player.setExperienceLocked(!player.experienceLocked());
                String type = player.experienceLocked() ? "locked" : "unlocked";
                player.getPacketSender().sendMessage("Your experience is now " + type + ".");
                PlayerPanel.refreshPanel(player);
                break;
            case 27651:
            case 21341:
                if (player.getInterfaceId() == -1) {
                    player.getSkillManager().stopSkilling();
                    BonusManager.update(player);
                    player.getPacketSender().sendInterface(21172);
                } else
                    player.getPacketSender().sendMessage("Please close the interface you have open before doing this.");
                break;
            case 27654:
                if (player.getInterfaceId() > 0) {
                    player.getPacketSender()
                            .sendMessage("Please close the interface you have open before opening another one.");
                    return;
                }
                player.getSkillManager().stopSkilling();
                ItemsKeptOnDeath.sendInterface(player);
                break;
            case 2458: // Logout
                if (player.logout()) {
                    World.getPlayers().remove(player);
                }
                break;
            case 29138:
            case 29038:
            case 29063:
            case 29113:
            case 29163:
            case 29188:
            case 29213:
            case 29238:
            case 30007:
            case 48023:
            case 33033:
            case 30108:
            case 7473:
            case 7562:
            case 7487:
            case 7788:
            case 8481:
            case 7612:
            case 7587:
            case 7662:
            case 7462:
            case 7548:
            case 7687:
            case 7537:
            case 12322:
            case 7637:
            case 12311:
            case -24530:
                CombatSpecial.activate(player);
                break;
            case 1772: // shortbow & longbow
                if (player.getWeapon() == WeaponInterface.SHORTBOW) {
                    player.setFightType(FightType.SHORTBOW_ACCURATE);
                } else if (player.getWeapon() == WeaponInterface.LONGBOW) {
                    player.setFightType(FightType.LONGBOW_ACCURATE);
                } else if (player.getWeapon() == WeaponInterface.CROSSBOW) {
                    player.setFightType(FightType.CROSSBOW_ACCURATE);
                }
                break;
            case 1771:
                if (player.getWeapon() == WeaponInterface.SHORTBOW) {
                    player.setFightType(FightType.SHORTBOW_RAPID);
                } else if (player.getWeapon() == WeaponInterface.LONGBOW) {
                    player.setFightType(FightType.LONGBOW_RAPID);
                } else if (player.getWeapon() == WeaponInterface.CROSSBOW) {
                    player.setFightType(FightType.CROSSBOW_RAPID);
                }
                break;
            case 1770:
                if (player.getWeapon() == WeaponInterface.SHORTBOW) {
                    player.setFightType(FightType.SHORTBOW_LONGRANGE);
                } else if (player.getWeapon() == WeaponInterface.LONGBOW) {
                    player.setFightType(FightType.LONGBOW_LONGRANGE);
                } else if (player.getWeapon() == WeaponInterface.CROSSBOW) {
                    player.setFightType(FightType.CROSSBOW_LONGRANGE);
                }
                break;
            case 2282: // dagger & sword
                if (player.getWeapon() == WeaponInterface.DAGGER) {
                    player.setFightType(FightType.DAGGER_STAB);
                } else if (player.getWeapon() == WeaponInterface.SWORD) {
                    player.setFightType(FightType.SWORD_STAB);
                }
                break;
            case 2285:
                if (player.getWeapon() == WeaponInterface.DAGGER) {
                    player.setFightType(FightType.DAGGER_LUNGE);
                } else if (player.getWeapon() == WeaponInterface.SWORD) {
                    player.setFightType(FightType.SWORD_LUNGE);
                }
                break;
            case 2284:
                if (player.getWeapon() == WeaponInterface.DAGGER) {
                    player.setFightType(FightType.DAGGER_SLASH);
                } else if (player.getWeapon() == WeaponInterface.SWORD) {
                    player.setFightType(FightType.SWORD_SLASH);
                }
                break;
            case 2283:
                if (player.getWeapon() == WeaponInterface.DAGGER) {
                    player.setFightType(FightType.DAGGER_BLOCK);
                } else if (player.getWeapon() == WeaponInterface.SWORD) {
                    player.setFightType(FightType.SWORD_BLOCK);
                }
                break;
            case 2429: // scimitar & longsword
                if (player.getWeapon() == WeaponInterface.SCIMITAR) {
                    player.setFightType(FightType.SCIMITAR_CHOP);
                } else if (player.getWeapon() == WeaponInterface.LONGSWORD) {
                    player.setFightType(FightType.LONGSWORD_CHOP);
                }
                break;
            case 2432:
                if (player.getWeapon() == WeaponInterface.SCIMITAR) {
                    player.setFightType(FightType.SCIMITAR_SLASH);
                } else if (player.getWeapon() == WeaponInterface.LONGSWORD) {
                    player.setFightType(FightType.LONGSWORD_SLASH);
                }
                break;
            case 2431:
                if (player.getWeapon() == WeaponInterface.SCIMITAR) {
                    player.setFightType(FightType.SCIMITAR_LUNGE);
                } else if (player.getWeapon() == WeaponInterface.LONGSWORD) {
                    player.setFightType(FightType.LONGSWORD_LUNGE);
                }
                break;
            case 2430:
                if (player.getWeapon() == WeaponInterface.SCIMITAR) {
                    player.setFightType(FightType.SCIMITAR_BLOCK);
                } else if (player.getWeapon() == WeaponInterface.LONGSWORD) {
                    player.setFightType(FightType.LONGSWORD_BLOCK);
                }
                break;
            case 3802: // mace
                player.setFightType(FightType.MACE_POUND);
                break;
            case 3805:
                player.setFightType(FightType.MACE_PUMMEL);
                break;
            case 3804:
                player.setFightType(FightType.MACE_SPIKE);
                break;
            case 3803:
                player.setFightType(FightType.MACE_BLOCK);
                break;
            case 4454: // knife, thrownaxe, dart & javelin
                if (player.getWeapon() == WeaponInterface.KNIFE) {
                    player.setFightType(FightType.KNIFE_ACCURATE);
                } else if (player.getWeapon() == WeaponInterface.THROWNAXE) {
                    player.setFightType(FightType.THROWNAXE_ACCURATE);
                } else if (player.getWeapon() == WeaponInterface.DART) {
                    player.setFightType(FightType.DART_ACCURATE);
                } else if (player.getWeapon() == WeaponInterface.JAVELIN) {
                    player.setFightType(FightType.JAVELIN_ACCURATE);
                }
                break;
            case 4453:
                if (player.getWeapon() == WeaponInterface.KNIFE) {
                    player.setFightType(FightType.KNIFE_RAPID);
                } else if (player.getWeapon() == WeaponInterface.THROWNAXE) {
                    player.setFightType(FightType.THROWNAXE_RAPID);
                } else if (player.getWeapon() == WeaponInterface.DART) {
                    player.setFightType(FightType.DART_RAPID);
                } else if (player.getWeapon() == WeaponInterface.JAVELIN) {
                    player.setFightType(FightType.JAVELIN_RAPID);
                }
                break;
            case 4452:
                if (player.getWeapon() == WeaponInterface.KNIFE) {
                    player.setFightType(FightType.KNIFE_LONGRANGE);
                } else if (player.getWeapon() == WeaponInterface.THROWNAXE) {
                    player.setFightType(FightType.THROWNAXE_LONGRANGE);
                } else if (player.getWeapon() == WeaponInterface.DART) {
                    player.setFightType(FightType.DART_LONGRANGE);
                } else if (player.getWeapon() == WeaponInterface.JAVELIN) {
                    player.setFightType(FightType.JAVELIN_LONGRANGE);
                }
                break;
            case 4685: // spear
                player.setFightType(FightType.SPEAR_LUNGE);
                break;
            case 4688:
                player.setFightType(FightType.SPEAR_SWIPE);
                break;
            case 4687:
                player.setFightType(FightType.SPEAR_POUND);
                break;
            case 4686:
                player.setFightType(FightType.SPEAR_BLOCK);
                break;
            case 4711: // 2h sword
                player.setFightType(FightType.TWOHANDEDSWORD_CHOP);
                break;
            case 4714:
                player.setFightType(FightType.TWOHANDEDSWORD_SLASH);
                break;
            case 4713:
                player.setFightType(FightType.TWOHANDEDSWORD_SMASH);
                break;
            case 4712:
                player.setFightType(FightType.TWOHANDEDSWORD_BLOCK);
                break;
            case 5576: // pickaxe
                player.setFightType(FightType.PICKAXE_SPIKE);
                break;
            case 5579:
                player.setFightType(FightType.PICKAXE_IMPALE);
                break;
            case 5578:
                player.setFightType(FightType.PICKAXE_SMASH);
                break;
            case 5577:
                player.setFightType(FightType.PICKAXE_BLOCK);
                break;
            case 7768: // claws
                player.setFightType(FightType.CLAWS_CHOP);
                break;
            case 7771:
                player.setFightType(FightType.CLAWS_SLASH);
                break;
            case 7770:
                player.setFightType(FightType.CLAWS_LUNGE);
                break;
            case 7769:
                player.setFightType(FightType.CLAWS_BLOCK);
                break;
            case 8466: // halberd
                player.setFightType(FightType.HALBERD_JAB);
                break;
            case 8468:
                player.setFightType(FightType.HALBERD_SWIPE);
                break;
            case 8467:
                player.setFightType(FightType.HALBERD_FEND);
                break;
            case 5862: // unarmed
                player.setFightType(FightType.UNARMED_PUNCH);
                break;
            case 5861:
                player.setFightType(FightType.UNARMED_KICK);
                break;
            case 5860:
                player.setFightType(FightType.UNARMED_BLOCK);
                break;
            case 12298: // whip
                player.setFightType(FightType.WHIP_FLICK);
                break;
            case 12297:
                player.setFightType(FightType.WHIP_LASH);
                break;
            case 12296:
                player.setFightType(FightType.WHIP_DEFLECT);
                break;
            case 336: // staff
                player.setFightType(FightType.STAFF_BASH);
                break;
            case 335:
                player.setFightType(FightType.STAFF_POUND);
                break;
            case 334:
                player.setFightType(FightType.STAFF_FOCUS);
                break;
            case 433: // warhammer
                player.setFightType(FightType.WARHAMMER_POUND);
                break;
            case 432:
                player.setFightType(FightType.WARHAMMER_PUMMEL);
                break;
            case 431:
                player.setFightType(FightType.WARHAMMER_BLOCK);
                break;
            case 782: // scythe
                player.setFightType(FightType.SCYTHE_REAP);
                break;
            case 784:
                player.setFightType(FightType.SCYTHE_CHOP);
                break;
            case 785:
                player.setFightType(FightType.SCYTHE_JAB);
                break;
            case 783:
                player.setFightType(FightType.SCYTHE_BLOCK);
                break;
            case 1704: // battle axe
                player.setFightType(FightType.BATTLEAXE_CHOP);
                break;
            case 1707:
                player.setFightType(FightType.BATTLEAXE_HACK);
                break;
            case 1706:
                player.setFightType(FightType.BATTLEAXE_SMASH);
                break;
            case 1705:
                player.setFightType(FightType.BATTLEAXE_BLOCK);
                break;
        }
    }
}
