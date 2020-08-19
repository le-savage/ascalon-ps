package com.janus.model.input.impl;

import com.google.common.collect.Range;
import com.janus.engine.task.Task;
import com.janus.engine.task.TaskManager;
import com.janus.model.Animation;
import com.janus.model.Graphic;
import com.janus.model.Item;
import com.janus.model.input.EnterAmount;
import com.janus.util.Misc;
import com.janus.world.content.PlayerLogs;
import com.janus.world.entity.impl.player.Player;

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
