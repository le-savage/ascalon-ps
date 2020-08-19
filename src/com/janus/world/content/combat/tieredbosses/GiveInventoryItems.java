package com.janus.world.content.combat.tieredbosses;

import com.janus.model.Item;
import com.janus.model.container.impl.Inventory;
import com.janus.world.entity.impl.player.Player;

public class GiveInventoryItems {

    /*** Contains the potions or items to give the players inventory ***/

    private static final Item[] first = new Item[]{new Item(15332), new Item(3024), new Item(6685), new Item(139)}; //Easiest Mode
    private static final Item[] second = new Item[]{new Item(15332), new Item(1704), new Item(4091), new Item(139)};
    private static final Item[] third = new Item[]{new Item(15332), new Item(1704), new Item(4091), new Item(139)};
    private static final Item[] fourth = new Item[]{new Item(15332), new Item(1704), new Item(4091)};
    private static final Item[] fifth = new Item[]{new Item(3755), new Item(1704), new Item(4091)}; //Hardest Mode


    public static void giveItems(Player player) {
        Inventory invent = player.getInventory();
        switch (player.getKbdTier()) {
            case 0: // Easiest Mode
                invent.addItems(first, true);
                invent.add(391, 10);// Manta
                break;
            case 1:
                invent.addItems(second, true);
                invent.add(7946, 10);// Monk
                break;
            case 2:
                invent.addItems(third, true);
                invent.add(385, 7);// Shark
                break;
            case 3:
                invent.addItems(fourth, true);
                invent.add(379, 7);// Lobster
                break;
            case 4: // Hardest Mode
                invent.addItems(fifth, true);
                invent.add(315, 7);// Shrimp
                break;
        }
    }
}
