package com.janus.world.content;


import com.janus.world.entity.impl.player.Player;

/**
 * Charming imp
 *
 * @author Kova+
 * Redone by Gabbe
 */
public class CoinCollector {

    public static final int COINS = 995;


    public static boolean handleCoinDrop(Player player, int itemId, int amount) {
        sendToInvo(player, itemId, amount);
        return true;
    }


    private static boolean sendToInvo(Player player, int itemId, int amount) {
        if (!player.getInventory().contains(itemId) && player.getInventory().getFreeSlots() == 0) {
            player.getPacketSender().sendMessage("Your inventory is full, no more coins can be collected!");
            return false;
        }
        player.getInventory().add(itemId, amount);
        return true;
    }

}
