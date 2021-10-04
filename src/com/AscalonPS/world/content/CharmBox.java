package com.AscalonPS.world.content;

import com.AscalonPS.util.Misc;
import com.AscalonPS.world.entity.impl.player.Player;

public class CharmBox {

    public static int blueCharm = 12163;
    public static int greenCharm = 12159;
    public static int redCharm = 12160;
    public static int goldCharm = 12158;


    public static void open(Player player) {
        if (player.getInventory().getFreeSlots() > 4) {
            int amount = 30 + Misc.getRandom(170);
            player.getInventory().delete(10025, 1);
            player.getPacketSender().sendMessage("You open the charm box and receive some charms.");
            player.getInventory().add(blueCharm, amount);
            player.getInventory().add(redCharm, amount);
            player.getInventory().add(goldCharm, amount);
            player.getInventory().add(greenCharm, amount);
        } else {
            player.getPacketSender().sendMessage("You need 4 free inventory spaces to open the box!");
        }
    }

}
