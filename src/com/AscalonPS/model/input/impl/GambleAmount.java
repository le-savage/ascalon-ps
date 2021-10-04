package com.AscalonPS.model.input.impl;

import com.google.common.collect.Range;
import com.AscalonPS.engine.task.Task;
import com.AscalonPS.engine.task.TaskManager;
import com.AscalonPS.model.Animation;
import com.AscalonPS.model.Graphic;
import com.AscalonPS.model.Item;
import com.AscalonPS.model.input.EnterAmount;
import com.AscalonPS.util.Misc;
import com.AscalonPS.world.content.PlayerLogs;
import com.AscalonPS.world.entity.impl.player.Player;

public class GambleAmount extends EnterAmount {

    private static final Range<Integer> DICE_RANGE = Range.closed(1, 100);

    @Override
    public void handleAmount(Player player, int amount) {
        if (amount > 1000000000) {
            player.getPacketSender().sendMessage("You can not gamble over 1b of any item");
            return;
        }

        player.getPacketSender().sendInterfaceRemoval();
        int cost = amount;

        if (player.getInventory().getAmount(995) < cost) {
            player.getPacketSender().sendMessage("You do not have enough money in your @red@inventory@bla@ to gamble that amount.");
            return;
        }
        PlayerLogs.log(player.getUsername(), "Player gambled " + amount + "Coins");
        player.getPacketSender().sendMessage("Rolling...");
        player.performAnimation(new Animation(11900));
        player.performGraphic(new Graphic(2075));
        player.getInventory().delete(995, amount);
        //int roll = RandomUtility.inclusiveRandom(DICE_RANGE.lowerEndpoint(), DICE_RANGE.upperEndpoint());
        int roll = 30 + Misc.getRandom(50);

        TaskManager.submit(new Task(2, player, false) {
            @Override
            public void execute() {
                if (roll >= 55) {
                    player.forceChat("I Rolled A " + roll + " And Have Won!");
                    player.getInventory().add(new Item(995, amount * 2));
                } else {
                    player.forceChat("I Rolled A " + roll + " And Have Lost!");
                }
                this.stop();
            }
        });

    }
}
