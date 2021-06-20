package com.janus.world.content.combat.tieredbosses;

import com.janus.model.Item;
import com.janus.model.container.impl.Inventory;
import com.janus.world.entity.impl.player.Player;

public class InventorySetups {

    /*** Contains the potions or items to give the players inventory ***/

    private static final Item[] WAVE_ONE_ITEMS = new Item[]{};
    private static final Item[] WAVE_TWO_ITEMS = new Item[]{};
    private static final Item[] WAVE_THREE_ITEMS = new Item[]{};
    private static final Item[] WAVE_FOUR_ITEMS = new Item[]{new Item(892, 100)}; //range mode
    private static final Item[] WAVE_FIVE_ITEMS = new Item[]{new Item(556, 200), new Item(554, 300), new Item(560, 80)}; //magic mode

    public static void giveItems(Player player) {
        Inventory invent = player.getInventory();
        switch (player.getKbdTier()) {
            case 0: // Wave One
                invent.addItems(WAVE_ONE_ITEMS, true); // THIS ADDS EVERYTHING FROM THE LISTS ABOVE
                invent.add(391, 5);// Manta
                break;
            case 1: // Wave Two
                invent.addItems(WAVE_TWO_ITEMS, true);
                invent.add(7946, 6);// Monk
                break;
            case 2: // Wave Three
                invent.addItems(WAVE_THREE_ITEMS, true);
                invent.add(385, 4);// Shark
                break;
            case 3://ranged
                invent.addItems(WAVE_FOUR_ITEMS, true);
                invent.add(379, 9);// Lobster
                break;
            case 4: // Magic
                invent.addItems(WAVE_FIVE_ITEMS, true);
                invent.add(315, 12);// Shrimp
                break;
        }
    }
}
