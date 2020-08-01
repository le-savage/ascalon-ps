package com.janus.world.content;

import com.janus.model.Item;
import com.janus.model.definitions.ItemDefinition;
import com.janus.model.definitions.NPCDrops;
import com.janus.model.definitions.NpcDefinition;
import com.janus.model.input.impl.EnterSyntaxToSearchDropsFor;
import com.janus.util.Misc;
import com.janus.world.entity.impl.player.Player;

import java.util.ArrayList;
import java.util.List;

public class DropsInterface {

    public static int INTERFACE_ID = 59000;
    private static int SEARCHED_STRING = 59006, SEARCHED_BUTTON = -6530, CLOSE_BUTTON = -6534,
            ITEM_MODEL = 60010, ITEM_NAME = 60100, ITEM_AMOUNT = 60200, ITEM_CHANCE = 60300, ITEM_VALUE = 60400, STRING_AMOUNT = 60003,
            STRING_CHANCE = 60004, STRING_VALUE = 60005,
            NPC_BUTTON_START = -6528, NPC_BUTTON_END = -6512;

    public static boolean SearchedNpcString(int i) {
        if (i >= 59008 && i <= 59028) {
            return true;
        }
        return false;
    }

    public static boolean SearchedNpcButton(int i) {
        if (i >= NPC_BUTTON_START && i <= NPC_BUTTON_END) {
            return true;
        }
        return false;
    }

    public static boolean handleButton(int id) {
        if (id >= CLOSE_BUTTON && id <= NPC_BUTTON_END) {
            return true;
        }

        return false;
    }

    public static void buildRightSide(Player player, int npcId) {
        player.getPacketSender().sendString(SEARCHED_STRING, "@whi@" + NpcDefinition.forId(npcId).getName()); //Search button
        player.getPacketSender().sendString(STRING_AMOUNT, "Amount:");
        player.getPacketSender().sendString(STRING_CHANCE, "Chance:");
        player.getPacketSender().sendString(STRING_VALUE, "Value:");
        for (int i = 0; i < 80; i++) {
            if (i > NPCDrops.forId(npcId).getDropList().length - 1) {
//System.out.println("sending blank on "+i);
                player.getPacketSender().sendItemOnInterfaceInt(ITEM_MODEL + i, -1, 0, 1); //remove all item models
                player.getPacketSender().sendString(ITEM_NAME + i, ""); //remove all item names
                player.getPacketSender().sendString(ITEM_AMOUNT + i, "");
                player.getPacketSender().sendString(ITEM_CHANCE + i, "");
                player.getPacketSender().sendString(ITEM_VALUE + i, "");
            } else {
                Item item = NPCDrops.forId(npcId).getDropList()[i].getItem();
                if (item.getDefinition() == null) {
                    continue;
                }
                int amount = item.getAmount();
                int chance = NPCDrops.forId(npcId).getDropList()[i].getChance().getRandom();
                player.getPacketSender().sendItemOnInterfaceInt(ITEM_MODEL + i, item.getId(), 0, 1 * amount); //remove all item models TODO CHANGE FOR AMOUNT OF ITEMS
                player.getPacketSender().sendString(ITEM_NAME + i, item.getDefinition().getName()); //remove all item names
                player.getPacketSender().sendString(ITEM_AMOUNT + i, item.getAmount() + "");
                player.getPacketSender().sendString(ITEM_CHANCE + i, "1/" + (chance == 0 ? "1" : chance));
                player.getPacketSender().sendString(ITEM_VALUE + i, Misc.format(amount * item.getDefinition().getValue()) + "");
            }
        }
    }

    public static void handleButtonClick(Player player, int id) {
//System.out.println("Hey drop interface handling this.");
        if (id == CLOSE_BUTTON) {
            player.getPacketSender().sendInterfaceRemoval();
            return;
        }
        if (SearchedNpcButton(id)) {

            int index = (id - NPC_BUTTON_START);
            System.out.println("index: " + index);
            if (player.getLootList() != null) {
                if (player.getLootList().size() > index) {
                    if (player.getLootList().get(index) != null) {
                        buildRightSide(player, player.getLootList().get(index));
                        player.getPacketSender().sendMessage("building right side for npc: " + player.getLootList().get(index));
                    }
                }
            }

//player.getPacketSender().sendMessage("NpcButton");
            return;
        }
        if (id == SEARCHED_BUTTON) {
            resetLeft(player);
//resetRight(player);
//player.getPacketSender().sendMessage("Send text box");
            player.setInputHandling(new EnterSyntaxToSearchDropsFor());
            player.getPacketSender().sendEnterInputPrompt("Which monster are you searching for?");
            return;
        }

    }

    public static void resetRight(Player player) {
        player.getPacketSender().sendString(SEARCHED_STRING, "@whi@Search for an NPC"); //Search button
        player.getPacketSender().sendString(STRING_AMOUNT, "");
        player.getPacketSender().sendString(STRING_CHANCE, "");
        player.getPacketSender().sendString(STRING_VALUE, "");
        for (int i = 0; i < 80; i++) {
            player.getPacketSender().sendItemOnInterfaceInt(ITEM_MODEL + i, -1, 0, 1); //remove all item models
            player.getPacketSender().sendString(ITEM_NAME + i, ""); //remove all item names
            player.getPacketSender().sendString(ITEM_AMOUNT + i, "");
            player.getPacketSender().sendString(ITEM_CHANCE + i, "");
            player.getPacketSender().sendString(ITEM_VALUE + i, "");
        }
    }

    public static void resetLeft(Player player) {
        for (int i = 59008; i <= 59028; i++) {
            player.getPacketSender().sendString(i, ""); //All of the results on the left
        }

    }

    public static void populateNpcOptions(Player player) {
        List<Integer> list = player.getLootList();
        if (list == null) {
            player.getPacketSender().sendMessage("Unable to load your loot list.");
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            if (i + 59008 > 59028) {
                break;
            }
            player.getPacketSender().sendString(i + 59008, "@yel@" + NpcDefinition.forId(list.get(i)).getName());

        }
    }

    public static void handleSearchInput(Player player, String syntax) {
        player.getPacketSender().sendClientRightClickRemoval();
//System.out.println("Searching for "+syntax);
        List<Integer> list = getList(syntax);
        player.getPacketSender().sendString(SEARCHED_STRING, "@whi@" + syntax);
        if (!list.isEmpty()) {
            player.setLootList(list);
            populateNpcOptions(player);
        } else {
            player.getPacketSender().sendMessage("No results found, please refine your NPC search.");
            return;
        }
    }

    public static void open(Player player) {
//resetInterface(player);
        resetRight(player);
        if (player.getLootList() != null) {
            populateNpcOptions(player);
        } else {
            resetLeft(player);
        }
        player.getPacketSender().sendInterface(INTERFACE_ID);
//List<Integer> list = getList(search);
    }

    public static List<Integer> getList(String search) {
        List<Integer> list = new ArrayList<Integer>();
        boolean dupe = false;
        search = search.toLowerCase();
//System.out.println("We will be searching for "+search);

        for (int i = 0; i < NpcDefinition.getDefinitions().length; i++) {

            if (NpcDefinition.forId(i) == null || NpcDefinition.forId(i).getName() == null || NpcDefinition.forId(i).getName().equalsIgnoreCase("null")
                    || NpcDefinition.forId(i).getName().equalsIgnoreCase("") || NPCDrops.forId(i) == null
                    || NPCDrops.forId(i).getDropList() == null || NPCDrops.forId(i).getDropList().length <= 0) {
                /*System.out.println("Skipping "+i);*/
                continue;
            }

            if (!NpcDefinition.forId(i).getName().toLowerCase().contains(search)) {
                /*System.out.println("Skipping "+NpcDefinition.forId(i).getName().toLowerCase());*/
                continue;
            }

            for (int l = 0; l < list.size(); l++) {
                if (NpcDefinition.forId(list.get(l)).getName().equalsIgnoreCase(NpcDefinition.forId(i).getName())) {
                    dupe = true;
//System.out.println("We got a dupe!");
                    break;
                }
            }

            if (!dupe) {
//System.out.println("Adding "+i+", "+NpcDefinition.forId(i).getName()+" to list.");
                list.add(i);
            }

        }

//printList(list);

        return list;
    }

    public static void printList(List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {

/*System.out.println("List(" + i + "): " + list.get(i) + ", " + NpcDefinition.forId(list.get(i)).getName());

 System.out.println("Added pair for id: "+i+", name ="+ ItemDefinition.forId(i).getName());*/

        }
    }

}

