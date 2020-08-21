package com.janus.world.content.combat.tieredbosses;

import com.janus.model.Item;
import com.janus.model.container.impl.Inventory;
import com.janus.world.entity.impl.player.Player;

public class InventorySetups {

    /*** Contains the potions or items to give the players inventory ***/

    private static final Item[] ZERO = new Item[]{new Item(15332), new Item(1704), new Item(4091), new Item(139)};
    private static final Item[] ONE = new Item[]{new Item(15332), new Item(1704), new Item(4091)};
    private static final Item[] TWO = new Item[]{new Item(3755), new Item(1704), new Item(4091)};
    private static final Item[] THREE = new Item[]{new Item(169), new Item(892,100), new Item(2434,2)}; //range mode
    private static final Item[] FOUR = new Item[]{new Item(3040), new Item(556,400), new Item(554,300), new Item(565,80)}; //magic mode
            
    public static void giveItems(Player player) {
        Inventory invent = player.getInventory();
        switch (player.getKbdTier()) {
            case 0: // Easiest Mode
                invent.deleteAll();
                invent.addItems(ZERO, true);
                invent.add(391, 10);// Manta
                break;
            case 1:
                invent.deleteAll();
                invent.addItems(ONE, true);
                invent.add(7946, 10);// Monk
                break;
            case 2:
                invent.deleteAll();
                invent.addItems(TWO, true);
                invent.add(385, 8);// Shark
                break;
            case 3://ranged
                invent.deleteAll();
                invent.addItems(THREE, true);
                invent.add(379, 10);// Lobster
                break;
            case 4: // Magic
                invent.deleteAll();
                invent.addItems(FOUR, true);
                invent.add(315, 7);// Shrimp
                break;
        }
    }
}
