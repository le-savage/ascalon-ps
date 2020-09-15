package com.janus.world.content.combat.tieredbosses;

import com.janus.model.Item;
import com.janus.model.container.impl.Inventory;
import com.janus.world.entity.impl.player.Player;

public class InventorySetups {

    /*** Contains the potions or items to give the players inventory ***/

    private static final Item[] ZERO = new Item[]{}; //Easiest Mode
    private static final Item[] ONE = new Item[]{};
    private static final Item[] TWO = new Item[]{};
    private static final Item[] THREE = new Item[]{new Item(892, 100)}; //range mode
    private static final Item[] FOUR = new Item[]{new Item(556, 200), new Item(554, 300), new Item(560, 80)}; //magic mode

    public static void giveItems(Player player) {
        Inventory invent = player.getInventory();
        switch (player.getKbdTier()) {
            case 0: // Easiest Mode
                invent.addItems(ZERO, true);
                invent.add(391, 5);// Manta
                break;
            case 1:
                invent.addItems(ONE, true);
                invent.add(7946, 6);// Monk
                break;
            case 2:
                invent.addItems(TWO, true);
                invent.add(385, 4);// Shark
                break;
            case 3://ranged
                invent.addItems(THREE, true);
                invent.add(379, 9);// Lobster
                break;
            case 4: // Magic
                invent.addItems(FOUR, true);
                invent.add(351, 9);// pike
                break;
        }
    }
}
